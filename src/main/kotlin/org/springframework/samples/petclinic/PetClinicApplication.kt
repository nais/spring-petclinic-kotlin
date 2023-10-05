/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic

import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Metrics
import java.util.*
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

/**
 * PetClinic Spring Boot Application.
 *
 * @author Dave Syer
 * @author Antoine Rey
 */
@SpringBootApplication
class PetClinicApplication {
    // Unregister the OpenTelemetryMeterRegistry from Metrics.globalRegistry and make it available
    // as a Spring bean instead.
    @Bean
    @ConditionalOnClass(name = ["io.opentelemetry.javaagent.OpenTelemetryAgent"])
    fun otelRegistry(): MeterRegistry? {
        val otelRegistry =
                Metrics.globalRegistry
                        .getRegistries()
                        .stream()
                        .filter { r -> r.javaClass.name.contains("OpenTelemetryMeterRegistry") }
                        .findAny()
        otelRegistry.ifPresent { r -> Metrics.globalRegistry.remove(r) }
        return otelRegistry.orElse(null)
    }
}

fun main(args: Array<String>) {
    runApplication<PetClinicApplication>(*args)
}
