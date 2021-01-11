FROM adoptopenjdk/openjdk11:alpine-jre
MAINTAINER NVCM

RUN apk add --update \
    curl \
    && rm -rf /var/cache/apk/*

ADD ./target/catalog-service.jar /app/

CMD ["java", "-Xmx200m", "-jar", "/app/catalog-service.jar"]

EXPOSE 8080 8089