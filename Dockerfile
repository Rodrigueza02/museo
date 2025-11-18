# ---------- BUILD ----------
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app
COPY . .

# Asegurar permisos del wrapper
RUN chmod +x mvnw

# Ejecutar el build
RUN ./mvnw -DskipTests package

# ---------- RUN ----------
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]