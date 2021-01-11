FROM adoptopenjdk/openjdk11:alpine-jre
MAINTAINER NVCM

RUN apk add --update \
    curl \
    && rm -rf /var/cache/apk/*

ADD ./target/discovery-server.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/discovery-server.jar"]

EXPOSE 8080 8089