# Étape 1 : Cache des dépendances
FROM eclipse-temurin:24-jdk AS dependencies

WORKDIR /app

# Copie uniquement les fichiers nécessaires pour les dépendances
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Téléchargement et cache des dépendances (cette couche sera mise en cache)
RUN ./mvnw dependency:go-offline -B

# Étape 2 : Build de l'application
FROM dependencies AS build

# Copie du code source (seulement si le code change)
COPY src ./src

# Compilation (dépendances déjà en cache)
RUN ./mvnw package -DskipTests

# Étape 3 : Runtime optimisé
FROM eclipse-temurin:24-jre-alpine AS runtime

WORKDIR /app

# Création d'un utilisateur non-root pour la sécurité
RUN addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup

# Ajout de métadonnées
LABEL maintainer="Othmane El Ouajdi <oelouajdi@hotmail.com>"
LABEL version="1.0"
LABEL description="Hairbook backend Spring Boot app"

# Copie de l'artifact généré
COPY --from=build --chown=appuser:appgroup /app/target/*.jar app.jar

# Création du dossier uploads
RUN mkdir -p /app/uploads && chown -R appuser:appgroup /app

# Passage à l'utilisateur non-root
USER appuser

# Optimisations JVM pour conteneur
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:+UseG1GC -XX:+UseStringDeduplication"

# Exposition du port dynamique
EXPOSE ${SERVER_PORT:-8082}

# Lancement optimisé de l'application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
