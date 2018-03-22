FROM openjdk:8

RUN mkdir /opt/locationservice

ADD build/libs/locations-service.jar /opt/locationservice
ADD application.yml /opt/locationservice
ADD logback.groovy /opt/locationservice
WORKDIR /opt/locationservice
EXPOSE 9200
ENTRYPOINT ["java", "-Dspring.config=.", "-jar", "locations-service.jar"]