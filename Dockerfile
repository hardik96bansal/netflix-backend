FROM openjdk:11
ADD target/docker-netflix-backend.jar docker-netflix-backend.jar
ADD src/main/resources/static/netflix_titles.csv netflix_titles.csv
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "docker-netflix-backend.jar"]