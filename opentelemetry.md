# Scratch pad for OpenTelemetry instrumentation

* [OpenTelemetry Java Getting Started Guide](https://opentelemetry.io/docs/instrumentation/java/getting-started/)
* [OpenTelemetry Java Manual Instrumentation](https://opentelemetry.io/docs/instrumentation/java/manual)

## Libraries

* [open-telemetry/opentelemetry-java](https://github.com/open-telemetry/opentelemetry-java)
  * [autoconfigure](https://github.com/open-telemetry/opentelemetry-java/tree/main/sdk-extensions/autoconfigure)
* [open-telemetry/opentelemetry-java-instrumentation](https://github.com/open-telemetry/opentelemetry-java-instrumentation)
  * [supported libraries](https://github.com/open-telemetry/opentelemetry-java-instrumentation/blob/main/docs/supported-libraries.md)

## Issues and Discussions

* [Using Prometheus otel exporter with SpringBoot and Micrometer](https://github.com/open-telemetry/opentelemetry-java-instrumentation/discussions/6114)

## Examples

Here are some open (and reasonably up to date) examples that have been useful:

* [open-telemetry/opentelemetry-java-examples](https://github.com/open-telemetry/opentelemetry-java-examples/tree/main)
  * [autoconfigure](https://github.com/open-telemetry/opentelemetry-java-examples/tree/main/autoconfigure)
  * [metrics](https://github.com/open-telemetry/opentelemetry-java-examples/tree/main/metrics)
  * [micrometer-shim](https://github.com/open-telemetry/opentelemetry-java-examples/tree/main/micrometer-shim)
  * [prometheus](https://github.com/open-telemetry/opentelemetry-java-examples/tree/main/prometheus)
  * [javaagent](https://github.com/open-telemetry/opentelemetry-java-examples/tree/main/javaagent)
* [open-telemetry/opentelemetry-java-instrumentation](https://github.com/open-telemetry/opentelemetry-java-instrumentation)
  * [spring-boot-autoconfigure](https://github.com/open-telemetry/opentelemetry-java-instrumentation/tree/main/instrumentation/spring/spring-boot-autoconfigure)
* demo projects
  * [seguri/java-tutorials/spring-opentelemetry](https://github.com/seguri/java-tutorials/tree/master/spring-opentelemetry)
  * [blueswen/spring-boot-observability](https://github.com/blueswen/spring-boot-observability)
  * [dcxp/opentelemetry-kotlin](https://github.com/dcxp/opentelemetry-kotlin)

## Grafana

### Guides

* [How to capture Spring Boot metrics with the OpenTelemetry Java Instrumentation Agent](https://grafana.com/blog/2022/05/04/how-to-capture-spring-boot-metrics-with-the-opentelemetry-java-instrumentation-agent/#bridging-opentelemetry-and-micrometer)
* [Intro to exemplars, which enable Grafana Tempo’s distributed tracing at massive scale](https://grafana.com/blog/2021/03/31/intro-to-exemplars-which-enable-grafana-tempos-distributed-tracing-at-massive-scale/)

### Tempo

* [Span metrics](https://grafana.com/docs/tempo/latest/metrics-generator/span_metrics/)
* [Service Graph and Service Graph view](https://grafana.com/docs/grafana/latest/datasources/tempo/service-graph/)
