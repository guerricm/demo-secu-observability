
## ✅ Spring WebFlux – Correct Spring Security spans for Actuator endpoints on `management.server.port`

### Context

This project demonstrates a **working setup with Spring WebFlux** where **Spring Security spans and OpenTelemetry traces are correctly created**, including for Actuator endpoints exposed on a **dedicated management port**.

It is intended as a **reference / comparison project** for a tracing issue observed with **Spring MVC (`spring-web`)**, which does **not** occur with Spring WebFlux.

The application uses **Grafana OTEL LGTM** with **Tempo** to collect and visualize traces.

---

### 🧪 Reproduction steps

#### 1. Run OTEL LGTM stack

```bash
docker run -d --name otel-lgtm \
  -p 3000:3000 \
  -p 4318:4318 \
  grafana/otel-lgtm:latest
```

---

#### 2. Open Grafana Tempo

```
http://localhost:3000/explore
```

Select **Tempo** as the data source.

---

#### 3. Call a regular application endpoint

Use basic authentication: `demo / demo`

```
http://localhost:8080/demo/1
```

✅ **Result**
The trace is correctly created and visible in Tempo.

---

#### 4. Configure a dedicated management port

```yaml
management:
  server:
    port: 8089
```

---

#### 5. Call the actuator endpoint on the management port

Use basic authentication: `demo / demo`

```
http://localhost:8089/actuator
```

✅ **Result**
The trace is **correctly created and visible** in Tempo.


---

### 🔄 Comparison test

#### Remove the management port configuration

```yaml
# management:
#   server:
#     port: 8089
```

#### Call actuator on the main port

Use basic authentication: `demo / demo`

```
http://localhost:8080/actuator
```

✅ **Result**
The trace is also correctly created and visible in Tempo.



---

### ✅ Observed behavior

With **Spring WebFlux**:

* Application endpoints are traced correctly
* Actuator endpoints are traced correctly
* Tracing works **with or without** `management.server.port`
* Spring Security spans are **consistent and properly parented**
* No broken or orphan spans observed in Tempo

---

### 🎯 Purpose of this project

This project serves as:

* A **working reference** for Spring Security + Micrometer + OpenTelemetry
* A **comparison baseline** against Spring MVC, where tracing breaks when using a dedicated management port
* Evidence that the issue is **not related to OTEL, Tempo, or configuration**, but is **specific to Spring MVC**

---

### 🧠 Additional notes

* OTEL exporter is correctly configured and functional
* Spring Security spans behave as expected
* No custom filters or manual instrumentation
* Issue does **not** reproduce with `spring-webflux`

