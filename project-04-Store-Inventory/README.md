# Store Inventory System — Spring Core Project 4

## Project Structure
```
src/main/java/com/shop/
│
├── infra/
│   └── InfraConfig.java                  ← @Configuration + @Bean — Clock and NumberFormat (outside the scan)
│
└── inventory/
    ├── model/
    │   ├── Product.java                  ← record — data only
    │   └── StockMovement.java            ← record — SALE / RESTOCK
    │
    ├── pricing/
    │   ├── DiscountPolicy.java           ← Interface
    │   ├── NoDiscount.java               ← @Component @Primary
    │   ├── ClearanceDiscount.java        ← @Component @Qualifier("clearance") — 30% off
    │   └── PricingService.java           ← @Service — picks the policy per product
    │
    ├── repository/
    │   ├── InventoryRepository.java      ← @Repository Bean — in-memory storage
    │   └── DataSeeder.java               ← @Component + @PostConstruct — loads the products
    │
    ├── service/
    │   ├── InventoryService.java         ← @Service — business logic (sell / restock)
    │   └── InsufficientStockException.java ← Custom RuntimeException
    │
    ├── report/
    │   └── InventoryReporter.java        ← @Component + @PreDestroy — final report
    │
    ├── AppConfig.java                    ← @Configuration + @ComponentScan + @Import
    └── StoreApp.java                     ← Main
```

---

## Topics Covered

| Topic | Where in the Code |
|---|---|
| `@ComponentScan` (default package) | In `AppConfig` — no package given, so it scans only `com.shop.inventory` and its sub-packages |
| `@Import` | In `AppConfig` — pulls in `InfraConfig`, which lives in `com.shop.infra` outside the scan |
| `@Bean` for external classes | In `InfraConfig` — `Clock` and `NumberFormat` are JDK classes, so they can't be annotated with `@Component` |
| `@Primary` | On `NoDiscount` — the default `DiscountPolicy` |
| `@Qualifier` | On `ClearanceDiscount` itself and in the `PricingService` constructor |
| `@Primary` + `@Qualifier` together | In `PricingService` — the unqualified parameter gets the `@Primary` bean, the other gets `"clearance"` |
| Strategy Pattern | In `PricingService.finalPrice()` — picks the `DiscountPolicy` at runtime based on `product.clearance()` |
| `@Service` / `@Repository` / `@Component` | `InventoryService`, `PricingService`, `InventoryRepository`, `DataSeeder`, `InventoryReporter` |
| Constructor Injection | In every bean — no `@Autowired` needed because each has a single constructor |
| Injection by Type | `InventoryReporter` receives `NumberFormat` as `priceFormat` — Spring injects by type, not by name |
| Dependency Chain | `InventoryReporter` → `InventoryService` → `PricingService` → `DiscountPolicy` |
| `@PostConstruct` | In `DataSeeder.seed()` — loads 8 products as soon as the context starts |
| `@PreDestroy` | In `InventoryReporter.printFinalReport()` — prints the stock and total sales when the context closes |
| try-with-resources | In Main — the context closes automatically, which triggers `@PreDestroy` |
| Injecting `Clock` | In `InventoryService` — `LocalDateTime.now(clock)` instead of `now()` so the time zone is configurable (`Africa/Cairo`) |
| Records as POJOs | `Product` and `StockMovement` — plain data, not beans |
| Custom Exception | `InsufficientStockException` — thrown when the requested quantity is more than the stock |
| Validation | In `InventoryService` — an unknown product or a quantity `<= 0` throws `IllegalArgumentException` |
| Error Handling | In Main — `attempt()` catches the exceptions and prints `REJECTED` without stopping the program |

---
