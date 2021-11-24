FROM navikt/java:17

COPY target/pam-tsbx-api-*.jar /app/app.jar

EXPOSE 9211
