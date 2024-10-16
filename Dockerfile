FROM openjdk:21
LABEL authors="MoGreene"

COPY ./build/libs/app.jar /usr/local/bin/app.jar
COPY ./scouter /usr/local/bin/scouter

RUN chmod +x /usr/local/bin/app.jar

CMD ["java", "-javaagent:/usr/local/bin/scouter/scouter.agent.jar", "-jar", "/usr/local/bin/app.jar"]