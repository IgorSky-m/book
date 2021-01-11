FROM adoptopenjdk/openjdk11:alpine-jre
MAINTAINER NVCM

RUN apk add --update \
    curl \
    && rm -rf /var/cache/apk/*

ADD ./target/config-server.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/config-server.jar"]

#HEALTHCHECK --interval=30s --timeout=30s CMD curl -f http://localhost:8888/actuator/health || exit 1

EXPOSE 8080 8089