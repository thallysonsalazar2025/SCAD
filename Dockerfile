# --- Estágio 1: Build do Frontend (Angular) ---
FROM node:20 AS frontend-build

WORKDIR /app-ui

# Copia os arquivos do projeto Angular
COPY scad-ui/package*.json ./
RUN npm install

COPY scad-ui/ ./
# Compila o Angular (gera a pasta dist/scad-ui)
RUN npm run build -- --configuration production

# --- Estágio 2: Build do Backend (Java) ---
FROM maven:3.9-amazoncorretto-21 AS backend-build

WORKDIR /app

# Copia o pom.xml e baixa dependências
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o código fonte Java
COPY src ./src

# Copia o build do Angular (Estágio 1) para a pasta static do Spring Boot
COPY --from=frontend-build /app-ui/dist/scad-ui ./src/main/resources/static

# Compila o projeto Java (agora contendo o frontend)
RUN mvn clean package -DskipTests

# --- Estágio 3: Runtime (Execução) ---
FROM amazoncorretto:21

WORKDIR /app

# Copia o .jar gerado
COPY --from=backend-build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]