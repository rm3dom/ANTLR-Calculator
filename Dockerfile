FROM gradle:6.8.3-jdk11 AS builder
WORKDIR .
RUN gradle build
COPY app/build/distributions/app.tar /

FROM adoptopenjdk:11-jdk-hotspot
COPY --from=builder /app.tar /opt/
RUN tar -xf /opt/app.tar -C /opt/ && rm /opt/app.tar
WORKDIR /opt/app
CMD ["./bin/app"]