package com.hairbook.hairbook_backend.service;

import com.hairbook.hairbook_backend.dto.loyalty.LoyaltyPointsDto;
import com.hairbook.hairbook_backend.dto.loyalty.LoyaltySummaryDto;
import com.hairbook.hairbook_backend.entity.Appointment;
import com.hairbook.hairbook_backend.entity.AppointmentStatus;
import com.hairbook.hairbook_backend.entity.LoyaltyPoints;
import com.hairbook.hairbook_backend.entity.LoyaltyPoints.PointsType;
import com.hairbook.hairbook_backend.entity.User;
import com.hairbook.hairbook_backend.exception.ResourceNotFoundException;
import com.hairbook.hairbook_backend.repository.AppointmentRepository;
import com.hairbook.hairbook_backend.repository.LoyaltyPointsRepository;
import com.hairbook.hairbook_backend.repository.UserRepository;
import com.hairbook.hairbook_backend.util.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.stream.Collectors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Service de gestion des points de fidélité des utilisateurs.
 * Permet l'accumulation, l'utilisation, l'expiration, l'ajustement et le suivi des points.
 */
@Service
public class LoyaltyService {

    /** Niveaux de fidélité (palier) */
    private static final Map<Integer, String> LOYALTY_TIERS = new HashMap<>();
    /** Seuils des paliers (points nécessaires pour chaque niveau) */
    private static final Map<Integer, Integer> TIER_THRESHOLDS = new HashMap<>();

    static {
        LOYALTY_TIERS.put(1, "Bronze");
        LOYALTY_TIERS.put(2, "Argent");
        LOYALTY_TIERS.put(3, "Or");
        LOYALTY_TIERS.put(4, "Platine");
        LOYALTY_TIERS.put(5, "Diamant");

        TIER_THRESHOLDS.put(1, 0);
        TIER_THRESHOLDS.put(2, 500);
        TIER_THRESHOLDS.put(3, 1500);
        TIER_THRESHOLDS.put(4, 3000);
        TIER_THRESHOLDS.put(5, 6000);
    }

    /** Points gagnés pour chaque euro dépensé */
    private static final int POINTS_PER_EURO = 10;
    /** Taux de conversion pour obtenir 1€ de réduction */
    private static final int POINTS_FOR_ONE_EURO_DISCOUNT = 100;

    @Autowired private LoyaltyPointsRepository loyaltyPointsRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private AppointmentRepository appointmentRepository;
    @Autowired private NotificationService notificationService;

    /**
     * Ajoute des points de fidélité pour un rendez-vous complété.
     *
     * @param appointmentId identifiant du rendez-vous
     * @return DTO des points attribués
     */
    @Transactional
    public LoyaltyPointsDto addPointsForAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Rendez-vous", "id", appointmentId));

        if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Les points ne peuvent être attribués que pour les rendez-vous complétés");
        }

        BigDecimal servicePrice = appointment.getService().getPrice();
        int pointsToAdd = servicePrice.multiply(BigDecimal.valueOf(POINTS_PER_EURO)).intValue();

        LoyaltyPoints loyaltyPoints = new LoyaltyPoints();
        loyaltyPoints.setUser(appointment.getUser());
        loyaltyPoints.setPoints(pointsToAdd);
        loyaltyPoints.setType(PointsType.EARNED);
        loyaltyPoints.setDescription("Points gagnés pour le rendez-vous #" + appointmentId);
        loyaltyPoints.setAppointment(appointment);

        loyaltyPoints = loyaltyPointsRepository.save(loyaltyPoints);

        notificationService.createSystemNotification(appointment.getUser().getId(),
                "Points de fidélité ajoutés",
                "Félicitations ! Vous avez gagné " + pointsToAdd + " points de fidélité pour votre rendez-vous.",
                "/profile/loyalty");

        checkAndNotifyTierChange(appointment.getUser().getId());

        return convertToDto(loyaltyPoints);
    }

    /**
     * Utilise les points de fidélité d'un utilisateur pour une réduction.
     *
     * @param userId         identifiant de l'utilisateur
     * @param pointsToRedeem nombre de points à échanger
     * @param description    description facultative
     * @return DTO des points utilisés
     */
    @Transactional
    public LoyaltyPointsDto redeemPointsForDiscount(Long userId, Integer pointsToRedeem, String description) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", userId));

        Integer availablePoints = loyaltyPointsRepository.getAvailablePointsByUserId(userId);
        if (availablePoints == null || availablePoints < pointsToRedeem) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Points insuffisants. Disponible: " + availablePoints + ", Demandé: " + pointsToRedeem);
        }

        BigDecimal discountValue = BigDecimal.valueOf(pointsToRedeem)
                .divide(BigDecimal.valueOf(POINTS_FOR_ONE_EURO_DISCOUNT));

        LoyaltyPoints loyaltyPoints = new LoyaltyPoints();
        loyaltyPoints.setUser(user);
        loyaltyPoints.setPoints(pointsToRedeem);
        loyaltyPoints.setType(PointsType.REDEEMED);
        loyaltyPoints.setDescription(description != null ? description :
                "Points échangés contre une réduction de " + discountValue + "€");

        loyaltyPoints = loyaltyPointsRepository.save(loyaltyPoints);

        notificationService.createSystemNotification(userId,
                "Points de fidélité utilisés",
                "Vous avez utilisé " + pointsToRedeem + " points pour une réduction de " + discountValue + "€.",
                "/profile/loyalty");

        return convertToDto(loyaltyPoints);
    }

    /**
     * Ajuste les points de fidélité d'un utilisateur (crédit ou débit manuel).
     *
     * @param userId      identifiant de l'utilisateur
     * @param points      nombre de points à ajouter ou retirer
     * @param description description facultative
     * @return DTO des points ajustés
     */
    @Transactional
    public LoyaltyPointsDto adjustPoints(Long userId, Integer points, String description) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", userId));

        LoyaltyPoints loyaltyPoints = new LoyaltyPoints();
        loyaltyPoints.setUser(user);
        loyaltyPoints.setPoints(points);
        loyaltyPoints.setType(PointsType.ADJUSTED);
        loyaltyPoints.setDescription(description != null ? description :
                "Ajustement manuel de points: " + points);

        loyaltyPoints = loyaltyPointsRepository.save(loyaltyPoints);

        String message = points >= 0 ?
                "Votre compte a été crédité de " + points + " points de fidélité." :
                "Votre compte a été débité de " + Math.abs(points) + " points de fidélité.";

        notificationService.createSystemNotification(userId, "Ajustement de points de fidélité", message, "/profile/loyalty");

        checkAndNotifyTierChange(userId);

        return convertToDto(loyaltyPoints);
    }

    /**
     * Récupère l'historique complet des points de fidélité pour un utilisateur.
     *
     * @param userId identifiant de l'utilisateur
     * @return liste des points sous forme de DTO
     */
    public List<LoyaltyPointsDto> getUserLoyaltyHistory(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("Utilisateur", "id", userId);
        }

        return loyaltyPointsRepository.findByUserId(userId)
                .stream().map(this::convertToDto).collect(Collectors.toList());
    }

    /**
     * Récupère l'historique des points avec pagination.
     *
     * @param userId   identifiant de l'utilisateur
     * @param pageable pagination
     * @return page de DTO de points
     */
    public Page<LoyaltyPointsDto> getUserLoyaltyHistoryPaginated(Long userId, Pageable pageable) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("Utilisateur", "id", userId);
        }

        return loyaltyPointsRepository.findByUserId(userId, pageable).map(this::convertToDto);
    }

    /**
     * Récupère un résumé des points de fidélité d'un utilisateur.
     *
     * @param userId identifiant de l'utilisateur
     * @return résumé des points de fidélité
     */
    public LoyaltySummaryDto getUserLoyaltySummary(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", userId));

        Integer totalPoints = Optional.ofNullable(loyaltyPointsRepository.getTotalPointsByUserId(userId)).orElse(0);
        Integer pointsEarned = Optional.ofNullable(loyaltyPointsRepository.getTotalPointsByUserIdAndType(userId, PointsType.EARNED)).orElse(0);
        Integer pointsRedeemed = Optional.ofNullable(loyaltyPointsRepository.getTotalPointsByUserIdAndType(userId, PointsType.REDEEMED)).orElse(0);
        Integer pointsExpired = Optional.ofNullable(loyaltyPointsRepository.getTotalPointsByUserIdAndType(userId, PointsType.EXPIRED)).orElse(0);
        Integer availablePoints = Optional.ofNullable(loyaltyPointsRepository.getAvailablePointsByUserId(userId)).orElse(0);
        LocalDateTime lastActivity = loyaltyPointsRepository.getLastActivityDateByUserId(userId);
        LocalDateTime memberSince = loyaltyPointsRepository.getFirstActivityDateByUserId(userId);

        int tier = 1;
        for (int i = 5; i >= 1; i--) {
            if (totalPoints >= TIER_THRESHOLDS.get(i)) {
                tier = i;
                break;
            }
        }

        int pointsToNextTier = (tier < 5) ? TIER_THRESHOLDS.get(tier + 1) - totalPoints : 0;

        LoyaltySummaryDto summary = new LoyaltySummaryDto();
        summary.setUserId(userId);
        summary.setUserName(user.getFirstName() + " " + user.getLastName());
        summary.setTotalPoints(totalPoints);
        summary.setPointsEarned(pointsEarned);
        summary.setPointsRedeemed(pointsRedeemed);
        summary.setPointsExpired(pointsExpired);
        summary.setAvailablePoints(availablePoints);
        summary.setTier(tier);
        summary.setTierName(LOYALTY_TIERS.get(tier));
        summary.setPointsToNextTier(pointsToNextTier);
        summary.setLastActivity(lastActivity);
        summary.setMemberSince(memberSince);

        return summary;
    }

    /**
     * Vérifie et notifie un éventuel changement de niveau de fidélité.
     *
     * @param userId identifiant de l'utilisateur
     */
    private void checkAndNotifyTierChange(Long userId) {
        LoyaltySummaryDto summary = getUserLoyaltySummary(userId);
        if (summary.getPointsToNextTier() <= 0 && summary.getTier() > 1) {
            String message = "Vous avez atteint le niveau " + summary.getTierName() + " ! Profitez de nouveaux avantages exclusifs.";
            notificationService.createSystemNotification(userId, "Félicitations ! Nouveau niveau de fidélité", message, "/profile/loyalty");

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", userId));

            notificationService.sendEmailNotification(userId,
                    "Hairbook - Nouveau niveau de fidélité atteint !",
                    "Bonjour " + user.getFirstName() + ",\n\n" + message + "\n\nMerci pour votre fidélité.\n\nL'équipe Hairbook",
                    "/profile/loyalty");
        }
    }

    /**
     * Tâche planifiée : expire les points inactifs depuis 2 ans.
     * Exécutée tous les jours à minuit.
     */
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void expireOldPoints() {
        LoggingService.logInfo(this.getClass(), "Début de l'expiration des points de fidélité anciens");

        for (User user : userRepository.findAll()) {
            Integer availablePoints = loyaltyPointsRepository.getAvailablePointsByUserId(user.getId());
            if (availablePoints == null || availablePoints <= 0) continue;

            LocalDateTime lastActivity = loyaltyPointsRepository.getLastActivityDateByUserId(user.getId());
            if (lastActivity == null || lastActivity.isAfter(LocalDateTime.now().minusYears(2))) continue;

            LoyaltyPoints expiredPoints = new LoyaltyPoints();
            expiredPoints.setUser(user);
            expiredPoints.setPoints(availablePoints);
            expiredPoints.setType(PointsType.EXPIRED);
            expiredPoints.setDescription("Points expirés après 2 ans d'inactivité");

            loyaltyPointsRepository.save(expiredPoints);

            notificationService.createSystemNotification(user.getId(),
                    "Points de fidélité expirés",
                    "Vos " + availablePoints + " points de fidélité ont expiré après 2 ans d'inactivité.",
                    "/profile/loyalty");

            LoggingService.logInfo(this.getClass(), "Points expirés pour l'utilisateur {}: {} points", user.getId(), availablePoints);
        }

        LoggingService.logInfo(this.getClass(), "Fin de l'expiration des points de fidélité anciens");
    }

    /**
     * Convertit une entité LoyaltyPoints en DTO.
     *
     * @param loyaltyPoints entité LoyaltyPoints
     * @return DTO correspondant
     */
    private LoyaltyPointsDto convertToDto(LoyaltyPoints loyaltyPoints) {
        LoyaltyPointsDto dto = new LoyaltyPointsDto();
        dto.setId(loyaltyPoints.getId());
        dto.setUserId(loyaltyPoints.getUser().getId());
        dto.setUserName(loyaltyPoints.getUser().getFirstName() + " " + loyaltyPoints.getUser().getLastName());
        dto.setPoints(loyaltyPoints.getPoints());
        dto.setType(loyaltyPoints.getType());
        dto.setDescription(loyaltyPoints.getDescription());
        if (loyaltyPoints.getAppointment() != null) {
            dto.setAppointmentId(loyaltyPoints.getAppointment().getId());
        }
        dto.setCreatedAt(loyaltyPoints.getCreatedAt());
        return dto;
    }
}
