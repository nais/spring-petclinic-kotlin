FROM bellsoft/liberica-openjdk-alpine:17 as BUILDER
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY build.gradle.kts settings.gradle.kts gradlew $APP_HOME
COPY gradle $APP_HOME/gradle
RUN ./gradlew build -x test || return 0
COPY . .
RUN ./gradlew build -x test

FROM bellsoft/liberica-openjdk-alpine:17
RUN apk add --no-cache bash
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY --from=BUILDER $APP_HOME/build/libs/app.jar .
EXPOSE 8080:8080
#CMD ["dumb-init", "--"]
ENTRYPOINT ["java","-jar", "app.jar", "-Dotel.java.global-autoconfigure.enabled=true"]