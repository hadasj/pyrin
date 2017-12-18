FROM openjdk:8-jdk-alpine

ADD @project.build.finalName@.jar pyrin.jar

RUN mkdir -p /app/app_logs
ENV JAVA_OPTS=""
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar pyrin.jar