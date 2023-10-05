package org.springframework.samples.petclinic.config

import io.micrometer.core.instrument.MeterRegistry
import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.instrumentation.micrometer.v1_5.OpenTelemetryMeterRegistry
import io.opentelemetry.instrumentation.spring.autoconfigure.EnableOpenTelemetry
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

// https://github.com/open-telemetry/opentelemetry-java-instrumentation/blob/main/instrumentation/spring/spring-boot-autoconfigure/README.md#usage-1
@Configuration
@EnableOpenTelemetry
public class OtelConfig {
  @Bean
  fun meterRegistry(openTelemetry: OpenTelemetry): MeterRegistry {
    return OpenTelemetryMeterRegistry.builder(openTelemetry).setPrometheusMode(true).build()
  }
}
