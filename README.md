# Spring Core Projects

A collection of hands-on projects for practicing the core of the **Spring Framework**: the IoC container, dependency injection, bean configuration, and the bean lifecycle.

Every project is plain Spring (no Spring Boot). Each one is a standalone Maven project that starts an `AnnotationConfigApplicationContext` from a regular `main` method, so you can see exactly what the container does.

---

## Tech Stack

| | |
|---|---|
| Language | Java 25 |
| Framework | Spring Framework 7.0.x (`spring-context`) — 7.0.7 in projects 01–04, 7.0.10 in projects 05–06 |
| Lifecycle annotations | Jakarta Annotations 3.0.0 (`@PostConstruct`, `@PreDestroy`) |
| Build tool | Maven |

---

## Projects

| # | Project | Main Focus |
|---|---|---|
| 01 | [Library Management System](project-01-library-management) | Named `@Bean`s, `@Primary`, splitting config with `@Import` |
| 02 | [Notification System](project-02-Notification-system) | `@Qualifier`, the three injection types, `Map` injection, lifecycle callbacks |
| 03 | [Vehicle Fleet System](project-03-Vehicle-Fleet-System) | Registering beans at runtime with `BeanRegistrar` |
| 04 | [Store Inventory System](project-04-Store-Inventory) | `@Primary` + `@Qualifier` together, `@Bean` for JDK classes, lifecycle hooks in a real flow |
| 05 | [Order & Payment Hub](project-05-Order-Payment-Hub) | `@Profile`, external config with `@PropertySource` / `@Value`, `Map` + `List` injection, optional beans |
| 06 | [Checkout Engine](project-06-Checkout-Engine) | Prototype scope + `ObjectProvider`, `@DependsOn`, `@Lazy`, thread-safe singletons under concurrent load |

---

### 01 — Library Management System
Manages a collection of books and magazines. The books and magazines are declared as named `@Bean`s in separate configuration classes and combined through one main config.

- Multiple beans of the same type and the `NoUniqueBeanDefinitionException` they cause
- Custom bean names with `@Bean(name = "...")`
- `@Primary` to choose the default bean
- Splitting configuration with `@Import` + `@ComponentScan`
- `@Service` / `@Repository` stereotypes and constructor injection
- Singleton scope proof

**Main class:** `com.libraryProject.LibraryApp`

---

### 02 — Notification System
Sends messages through Email, SMS, and Push channels, all implementing a single `NotificationService` interface.

- `@Primary` (Email as the default) vs `@Qualifier` (pick a specific channel)
- Constructor, setter, and field injection side by side
- Injecting a `Map<String, NotificationService>` of all implementations to choose a channel at runtime
- `@Bean` vs `@Component`
- `@PostConstruct` / `@PreDestroy`

**Main class:** `com.notificationSystem.NotificationApp`

---

### 03 — Vehicle Fleet System
Manages a fleet of electric or petrol vehicles. The vehicle beans are registered programmatically, and the fleet type is decided by the `fleet.type` property.

- `BeanRegistrar` for registering beans dynamically at startup
- Reading properties from the `Environment`
- Constructor injection and autowiring
- `@PostConstruct` / `@PreDestroy`
- `@Import` + `@ComponentScan`

**Main class:** `com.vehicleFleetSystem.FleetApp`

> Run with `-Dfleet.type=electric` to switch the fleet from petrol (default) to electric.

---

### 04 — Store Inventory System
A small store that sells and restocks products, applies discounts, records every stock movement, and prints a final sales report on shutdown.

- `@Primary` + `@Qualifier` in the same constructor to pick between `DiscountPolicy` implementations
- `@Bean` for JDK classes (`Clock`, `NumberFormat`) that can't be annotated
- `@Import` for a config class that lives outside the component-scan package
- `@PostConstruct` to seed data, `@PreDestroy` to print the final report
- Records as data models, custom exceptions, and input validation

**Main class:** `com.shop.inventory.StoreApp`

---

### 05 — Order & Payment Hub
A checkout system with a product catalog, cart, tax and shipping calculation, several payment gateways (Cash on Delivery, Fawry, card), multi-channel notifications, loyalty points, and refunds. Runs as a scripted scenario or as an interactive console menu.

- `@Profile` (`dev` / `prod` / `loyalty`) to switch the card gateway and notification channels, and to turn loyalty points on
- `@PropertySource` + `@Value` (with defaults) + `Environment` lookups for externalized configuration
- Secrets from environment variables (`PAYMOB_API_KEY`) that fail fast when missing
- `Map<String, PaymentGateway>` and `List<NotificationChannel>` injection
- Optional dependency with `@Autowired(required = false)` setter injection
- `@Bean(initMethod, destroyMethod)` for a third-party class, `@PostConstruct` to seed data
- Records with compact constructors, immutable updates, and validation before side effects

**Main class:** `com.shop.hub.StoreApp`

> Run with `-Dspring.profiles.active=dev` (card payments are rejected when no profile is active), and add `-Dexec.args=menu` for the interactive menu. The project README has step-by-step run instructions for every profile (none, `dev`, `prod`, `loyalty`).

---

### 06 — Checkout Engine
A console checkout engine for a Black Friday flash sale. Three VIP orders run one by one, then 90 customers check out at the same time on virtual threads, and a report checks three guarantees: no overselling, unique order IDs, and every invoice has only its own lines.

- Prototype scope inside a singleton with `ObjectProvider` (`InvoiceBuilder` in `InvoiceService`)
- Thread safety: `AtomicInteger` for order IDs, `ConcurrentHashMap.compute` per SKU for stock
- `@DependsOn` so `SchemaMigrator` creates the tables before `CatalogSeeder` fills them
- `@Lazy` on the class and on the injection point (`FraudCheckClient`)
- An `ExecutorService` `@Bean` that Spring shuts down when the context closes
- Strict placeholders with a `static` `PropertySourcesPlaceholderConfigurer`
- Five deliberate bugs, each documented with its symptom and fix

**Main class:** `com.shop.engine.EngineApp`

> Run with `-Dflash.customers=30000` (or the environment variable `FLASH_CUSTOMERS=30000`) to scale the flash sale. Stock stays at 60 units, so all three checks still pass.

---

## Concepts Map

| Concept | 01 | 02 | 03 | 04 | 05 | 06 |
|---|:---:|:---:|:---:|:---:|:---:|:---:|
| `@Configuration` / `@Bean` | ✅ | ✅ | | ✅ | ✅ | ✅ |
| `@ComponentScan` | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| `@Import` | ✅ | ✅ | ✅ | ✅ | | |
| `@Component` / `@Service` / `@Repository` | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| `@Primary` | ✅ | ✅ | | ✅ | | |
| `@Qualifier` | | ✅ | | ✅ | | |
| Constructor Injection | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Setter / Field Injection | | ✅ | | | ✅ | |
| Collection (`Map` / `List`) Injection | | ✅ | | | ✅ | |
| Optional Dependency (`required = false`) | | | | | ✅ | |
| `@PostConstruct` / `@PreDestroy` | | ✅ | ✅ | ✅ | ✅ | ✅ |
| `@Bean(initMethod, destroyMethod)` | | | | | ✅ | |
| `BeanRegistrar` | | | ✅ | | | |
| `Environment` | | | ✅ | | ✅ | |
| `@PropertySource` / `@Value` | | | | | ✅ | ✅ |
| `static` `PropertySourcesPlaceholderConfigurer` | | | | | ✅ | ✅ |
| `@Profile` | | | | | ✅ | |
| Singleton Scope Proof | ✅ | ✅ | ✅ | | | |
| Prototype Scope + `ObjectProvider` | | | | | | ✅ |
| `@DependsOn` | | | | | | ✅ |
| `@Lazy` (class + injection point) | | | | | | ✅ |
| Thread Safety (`AtomicInteger` / `ConcurrentHashMap`) | | | | | | ✅ |
| `ExecutorService` `@Bean` closed by Spring | | | | | | ✅ |

---

## How to Run

Each project is independent. Open a project folder in your IDE and run its main class, or use Maven from inside the project folder:

```bash
cd project-04-Store-Inventory
mvn compile
mvn exec:java -Dexec.mainClass=com.shop.inventory.StoreApp
```

> Requires **JDK 25** and **Maven**.

---

## Repository Structure

```
spring-core-projects/
├── project-01-library-management/
├── project-02-Notification-system/
├── project-03-Vehicle-Fleet-System/
├── project-04-Store-Inventory/
├── project-05-Order-Payment-Hub/
├── project-06-Checkout-Engine/
└── README.md
```

Each project has its own `README.md` with the full package structure and a table showing where each topic appears in the code.
