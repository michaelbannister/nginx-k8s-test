FROM openjdk:8u131-jdk-alpine

WORKDIR /opt/spring
RUN addgroup spring && \
    adduser -D -H -u 1000 -G spring spring

EXPOSE 8080

COPY entrypoint.sh /opt/spring/entrypoint.sh
COPY build/libs/spring-jetty*.jar /opt/spring/spring-jetty.jar

RUN chown -R spring:spring /opt/spring

USER spring

# Using entrypoint this way allows easy passing of args to 'docker run' (as CMD) while still allowing
# expansion of $JVM_OPTS for the java command in the entrypoint script.
ENTRYPOINT [ "/opt/spring/entrypoint.sh" ]
