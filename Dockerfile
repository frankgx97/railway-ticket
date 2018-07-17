FROM openjdk:8-jre-alpine3.8

WORKDIR /app

COPY ./target/TicketSystem-0.0.1-SNAPSHOT.jar .

CMD java -jar /app/TicketSystem-0.0.1-SNAPSHOT.jar