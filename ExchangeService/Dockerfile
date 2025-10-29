# --- ESTÁGIO 1: Compilação (Build) ---
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /workspace

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN ./mvnw dependency:go-offline

COPY src/ ./src

RUN ./mvnw package -DskipTests

# --- ESTÁGIO 2: Imagem Final (Runtime) ---
FROM ufrn/exchange-rest:v2

WORKDIR /app

EXPOSE 8080

# Copia APENAS o JAR compilado do estágio 'build' para a nossa imagem final.
COPY --from=build /workspace/target/Exchange-REST.jar app.jar

# Comando que será executado quando o contêiner iniciar
ENTRYPOINT ["java", "-jar", "app.jar"]