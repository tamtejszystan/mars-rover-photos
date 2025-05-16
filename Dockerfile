# ---------- build stage ----------
FROM maven:3.9.7-eclipse-temurin-17 AS build
WORKDIR /app

# copy Maven descriptor first – this lets Docker cache the dependency layer
COPY pom.xml .
RUN mvn -q dependency:go-offline

# copy the rest of the source and build
COPY src ./src
RUN mvn -q clean package -DskipTests

# ---------- runtime stage ----------
FROM tomcat:10.1-jdk17-temurin

# optional: keep the image small by deleting Tomcat's default apps
RUN rm -rf /usr/local/tomcat/webapps/*

# copy the WAR produced in the build stage and make it the root context
COPY --from=build /app/target/marsRoverPhotos*.war \
     /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
