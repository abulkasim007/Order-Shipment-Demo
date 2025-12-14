# Order-Shipment-Demo

Multi-module Java project demonstrating order and shipment services with supporting modules (commands, events, domain, query, coordinator).
This project uses libraries from the https://github.com/abulkasim007/axiom-libraries


## Project structure (high level)

Key modules:
- `order-commands`, `order-events`, `order-domain`, `order-app`, `order-service`, `order-query`, `order-coordinator`
- `shipment-commands`, `shipment-events`, `shipment-domain`, `shipment-app`, `shipment-service`

Application entry points:
- `com.demo.order.service.Main` (order-service)
- `com.demo.shipment.service.Main` (shipment-service)
- `com.demo.order.coordinator.Main` (order-coordinator)
- `com.demo.order.query.Main` (order-query)

Dependency/version catalog:
- Versions are managed via `gradle/libs.versions.toml` (see `gradle/libs.versions.toml`).

## Common tasks

Run the fat JAR produced by an application subproject (example paths):
```powershell
# After building
java -jar order-service\build\libs\order-service-1.0-SNAPSHOT.jar
# or
java -jar shipment-service\build\libs\shipment-service-1.0-SNAPSHOT.jar
```
