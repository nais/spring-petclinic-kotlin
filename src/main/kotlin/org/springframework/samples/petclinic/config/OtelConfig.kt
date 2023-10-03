package org.springframework.samples.petclinic.config

import io.opentelemetry.instrumentation.spring.autoconfigure.EnableOpenTelemetry
import org.springframework.context.annotation.Configuration

// https://github.com/open-telemetry/opentelemetry-java-instrumentation/blob/main/instrumentation/spring/spring-boot-autoconfigure/README.md#usage-1
@Configuration @EnableOpenTelemetry public class OtelConfig {}
