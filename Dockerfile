# --- Estágio 1: Build (Compilação) ---
# Usamos uma imagem que já tem Maven e JDK 21 instalados
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Define o diretório de trabalho dentro do container de build
WORKDIR /app

# Copia o arquivo pom.xml e baixa as dependências (isso aproveita o cache do Docker)
COPY pom.xml .
# Baixa as dependências sem copiar o código fonte ainda (para otimizar cache)
RUN mvn dependency:go-offline

# Copia todo o código fonte do projeto
COPY src ./src

# Compila o projeto e gera o arquivo .jar (pula os testes para agilizar)
RUN mvn clean package -DskipTests

# --- Estágio 2: Runtime (Execução) ---
# Usamos uma imagem leve apenas com o JRE/JDK para rodar a aplicação
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Copia o .jar gerado no estágio anterior (build) para a imagem final
# O --from=build refere-se ao alias que demos na primeira linha
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta 8080
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
