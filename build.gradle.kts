import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


description = "Kotlin version of the Spring Petclinic application"
group = "org.springframework.samples"
// Align with Spring Version
version = "3.1.3"

java.sourceCompatibility = JavaVersion.VERSION_17

plugins {
    val kotlinVersion = "1.8.22"
    id("org.springframework.boot") version "3.1.3"
    id("io.spring.dependency-management") version "1.1.0"
    id("com.google.cloud.tools.jib") version "3.1.4"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
}

// WebJars versions are also referenced in src/main/resources/templates/fragments/layout.html for resource URLs
val boostrapVersion = "5.1.3"
val fontAwesomeVersion = "4.7.0"
var otelSpringStarterVersion = "1.30.0-alpha"
var otelVersion = "1.30.1"
var otelExportersOtlpVersion = "0.9.1"
var otelSemconvVersion = "1.21.0-alpha"

tasks {
    jar {
        archiveFileName.set("app-plain.jar")
    }

    bootJar {
        archiveFileName.set("app.jar")
    }

    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }

    withType<Test> {
        useJUnitPlatform()
        environment("SPRING_PROFILES_ACTIVE", "default")
        environment("OTEL_METRICS_EXPORTER", "none")
        environment("OTEL_LOGS_EXPORTER", "none")
        environment("OTEL_TRACES_EXPORTER", "none")
    }

    bootRun {
        environment("SPRING_PROFILES_ACTIVE", "default")
        environment("OTEL_METRICS_EXPORTER", "prometheus")
        environment("OTEL_LOGS_EXPORTER", "none")
        environment("OTEL_TRACES_EXPORTER", "otlp")
        environment("OTEL_EXPORTER_METRICS_ENABLED", "false")
        environment("OTEL_METRICS_EXEMPLAR_FILTER", "ALWAYS_ON")
        environment("OTEL_SERVICE_NAME", "petclinic")
        environment("OTEL_RESOURCE_ATTRIBUTES", "service.namespace=myteam")

        jvmArgs = listOf(
            "-Dotel.java.global-autoconfigure.enabled=true",
        )
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/snapshot") }
    maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
    //api("com.google.cloud:sqlcommenter:2.0.1")

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-aop")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.glassfish.jaxb:jaxb-runtime")
    implementation("javax.cache:cache-api")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.webjars.npm:bootstrap:$boostrapVersion")
    implementation("org.webjars.npm:font-awesome:$fontAwesomeVersion")

    // https://github.com/open-telemetry/opentelemetry-java-instrumentation/tree/main/instrumentation/spring/spring-boot-autoconfigure
    // https://opentelemetry.io/docs/instrumentation/java/manual/#automatic-configuration
    implementation("io.opentelemetry.instrumentation:opentelemetry-spring-boot-starter:$otelSpringStarterVersion")
    implementation("io.opentelemetry.semconv:opentelemetry-semconv:$otelSemconvVersion")
    implementation("io.opentelemetry:opentelemetry-api:$otelVersion")
    implementation("io.opentelemetry:opentelemetry-exporter-logging:$otelVersion");
    implementation("io.opentelemetry:opentelemetry-exporters-otlp:$otelExportersOtlpVersion")
    implementation("io.opentelemetry:opentelemetry-extension-annotations:1.18.0")
    implementation("io.opentelemetry:opentelemetry-sdk-extension-autoconfigure-spi:$otelVersion");
    implementation("io.opentelemetry:opentelemetry-sdk-extension-autoconfigure:$otelVersion");
    implementation("io.opentelemetry:opentelemetry-sdk-metrics:$otelVersion");
    implementation("io.opentelemetry:opentelemetry-sdk:$otelVersion");

    implementation("io.opentelemetry.instrumentation:opentelemetry-micrometer-1.5:1.30.0-alpha")
    implementation("io.opentelemetry:opentelemetry-exporter-prometheus:1.30.1-alpha")
    //implementation("io.micrometer:micrometer-core:1.11.4")
    implementation("io.micrometer:micrometer-registry-prometheus:1.11.4")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    runtimeOnly("com.h2database:h2")
    runtimeOnly("com.mysql:mysql-connector-j")
    runtimeOnly("org.postgresql:postgresql")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation(kotlin("stdlib-jdk8"))
}

jib {
    to {
        image = "springcommunity/spring-petclinic-kotlin"
        tags = setOf(project.version.toString(), "latest")
    }
}

