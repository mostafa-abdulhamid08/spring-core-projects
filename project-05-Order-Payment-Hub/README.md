# Order & Payment Hub — Spring Core Project 5

## Project Structure
```
src/main/java/com/shop/
│
├── external/
│   └── SmsClient.java                    ← Plain class, no annotations — acts like a third-party library
│
└── hub/
    ├── config/
    │   └── InfraConfig.java              ← @Configuration + @Bean(initMethod, destroyMethod) — SmsClient
    │
    ├── catalog/
    │   ├── Product.java                  ← record — data only
    │   ├── ProductRepository.java        ← @Repository — in-memory storage
    │   └── CatalogSeeder.java            ← @Component + @PostConstruct — loads 5 products
    │
    ├── cart/
    │   └── Cart.java                     ← Plain class (not a bean) — created with new
    │
    ├── pricing/
    │   ├── TaxCalculator.java            ← @Component + @Value — tax rate from app.properties
    │   └── ShippingService.java          ← @Service + @Value defaults + Environment lookups
    │
    ├── payment/
    │   ├── PaymentGateway.java           ← Interface
    │   ├── CashOnDeliveryGateway.java    ← @Component("cod") — always active
    │   ├── FawryGateway.java             ← @Component("fawry") — always active
    │   ├── FakeCardGateway.java          ← @Component("card") + @Profile("dev")
    │   ├── PaymobCardGateway.java        ← @Component("card") + @Profile("prod") — needs PAYMOB_API_KEY
    │   └── PaymentService.java           ← @Service — Map<String, PaymentGateway> injection
    │
    ├── notification/
    │   ├── NotificationChannel.java      ← Interface
    │   ├── SmsNotifier.java              ← @Component — always active, sends through SmsClient
    │   ├── ConsoleNotifier.java          ← @Component + @Profile("dev")
    │   ├── EmailNotifier.java            ← @Component + @Profile("prod")
    │   └── NotificationService.java      ← @Service — List<NotificationChannel> injection
    │
    ├── loyalty/
    │   └── LoyaltyService.java           ← @Service + @Profile("loyalty") — optional bean
    │
    ├── order/
    │   ├── Order.java                    ← record + nested Line record + Status enum
    │   ├── OrderRepository.java          ← @Repository — in-memory storage + order ID generator
    │   ├── CheckoutService.java          ← @Service — validate → calculate → pay → notify
    │   └── RefundService.java            ← @Service — refunds through the original gateway
    │
    ├── ui/
    │   └── ConsoleMenu.java              ← @Component — interactive menu (run with the "menu" argument)
    │
    ├── AppConfig.java                    ← @Configuration + @ComponentScan + @PropertySource
    └── StoreApp.java                     ← Main

src/main/resources/
└── app.properties                        ← Tax rate, shipping fees, SMS host
```

---

## Topics Covered

| Topic | Where in the Code |
|---|---|
| `@ComponentScan` (default package) | In `AppConfig` — scans `com.shop.hub` and its sub-packages, so `InfraConfig` in `com.shop.hub.config` is found without `@Import` |
| `@PropertySource` | In `AppConfig` — loads `app.properties` from the classpath into the `Environment` |
| `PropertySourcesPlaceholderConfigurer` | In `AppConfig` — declared as a `static` `@Bean` so it is registered before the other beans and resolves the `${...}` placeholders |
| `@Value` | In `TaxCalculator` — `${shop.tax-rate}` is injected into the constructor and converted to `int` |
| `@Value` with defaults | In `ShippingService` (`${shipping.default-fee:50}`) and `InfraConfig` (`${sms.host:localhost}`) — the value after `:` is used when the property is missing |
| Type conversion | In `ShippingService` — `shipping.free-cities=Cairo,Giza` is converted to a `String[]` automatically |
| `Environment` | In `ShippingService.feeFor()` — builds the key `shipping.city-fee.<city>` at runtime and reads it with `env.getProperty(key, Integer.class, defaultFee)` |
| Environment variables | In `PaymobCardGateway` — `${paymob.api-key}` is resolved from the `PAYMOB_API_KEY` environment variable, so the secret is never stored in `app.properties` (and it is masked in the output) |
| Fail-fast configuration | `paymob.api-key` has no default — running with `prod` without `PAYMOB_API_KEY` stops at startup with `Could not resolve placeholder 'paymob.api-key'` |
| `@Profile` | `dev` / `prod` on the card gateways and notifiers, `loyalty` on `LoyaltyService` — the active profiles decide which beans exist |
| Same bean name in different profiles | `FakeCardGateway` and `PaymobCardGateway` are both named `"card"` — only one exists at a time, so the rest of the code just asks for `"card"` |
| `Map` injection | In `PaymentService` — `Map<String, PaymentGateway>` keyed by bean name (`cod`, `fawry`, `card`), so choosing a payment method is a map lookup |
| `List` injection | In `NotificationService` — `List<NotificationChannel>` holds every active channel, and `broadcast()` sends the message to all of them |
| Optional dependency | In `CheckoutService.setLoyaltyService()` — setter with `@Autowired(required = false)`; it stays `null` when the `loyalty` profile is off |
| Constructor Injection | In every other bean — no `@Autowired` needed because each has a single constructor |
| `@Bean` for a third-party class | In `InfraConfig` — `SmsClient` lives in `com.shop.external` and has no Spring annotations, so it is created with `@Bean` |
| `initMethod` / `destroyMethod` | In `InfraConfig` — `@Bean(initMethod = "connect", destroyMethod = "disconnect")` adds lifecycle callbacks without touching `SmsClient` |
| `@PostConstruct` | In `CatalogSeeder.seed()` — loads 5 products as soon as the context starts |
| try-with-resources | In Main — the context closes automatically, which calls `SmsClient.disconnect()` |
| `@Service` / `@Repository` / `@Component` | `CheckoutService`, `RefundService`, `PaymentService`, `ProductRepository`, `OrderRepository`, the gateways, the notifiers, `ConsoleMenu` |
| Dependency Chain | `ConsoleMenu` → `CheckoutService` → `PaymentService` → `PaymentGateway` beans, and `NotificationService` → `SmsNotifier` → `SmsClient` |
| Not every class is a bean | `Cart` — created with `new`; in `ConsoleMenu` it is a local variable, not a field, so the singleton keeps no per-user state |
| Records as POJOs | `Product`, `Order`, and `Order.Line` — plain data, not beans |
| Compact constructor | In `Order` — `lines = List.copyOf(lines)` makes the order's lines immutable |
| Immutable updates | `Order.withStatus()` returns a new `Order`, and `OrderRepository.save()` replaces the old one by ID |
| Defensive copies | `Cart.items()`, `ProductRepository.findAll()`, and `OrderRepository.findAll()` return copies, so callers can't change the internal data |
| Validate before side effects | In `CheckoutService.placeOrder()` — an empty cart, an unsupported payment method, or an unknown SKU is rejected before an order ID is generated or money is charged, so order IDs stay sequential |
| Error Handling | In Main (`attempt()`) and `ConsoleMenu.run()` — `IllegalArgumentException` / `IllegalStateException` print `REJECTED` without stopping the program; `NumberFormatException` is caught first because it is a subclass of `IllegalArgumentException` |
| Two run modes | In `StoreApp.main()` — the `menu` argument starts `ConsoleMenu`, otherwise a scripted scenario of 6 orders and 3 refunds runs |

---

## Profiles

| Profile | `card` Gateway | Notification Channels | Loyalty Points |
|---|---|---|---|
| *(none)* | none — card payments are rejected | SMS | — |
| `dev` | `FakeCardGateway` | Console + SMS | — |
| `prod` | `PaymobCardGateway` (needs `PAYMOB_API_KEY`) | Email + SMS | — |
| `loyalty` | — | — | +1 point per 100 EGP |

`cod` and `fawry` are available in every profile. Profiles can be combined, e.g. `dev,loyalty`.

---

## Pricing Rules

- **Tax:** 14% of the subtotal (`shop.tax-rate`)
- **Free shipping:** subtotal of 5,000 EGP or more, or delivery to Cairo / Giza
- **City fee:** Alexandria 70 EGP, Aswan 120 EGP (`shipping.city-fee.<city>`)
- **Default fee:** 50 EGP for any other city

---

## How to Run

The active profile is chosen with the `spring.profiles.active` system property. First, compile the project from inside its folder:

```bash
cd project-05-Order-Payment-Hub
mvn compile
```

> **PowerShell users:** wrap every `-D...` argument in quotes (e.g. `"-Dspring.profiles.active=dev,loyalty"`), otherwise PowerShell splits it at the `.` or `,`.

### 1. No profile

```bash
mvn exec:java -Dexec.mainClass=com.shop.hub.StoreApp
```

What you will see:
- Only `cod` and `fawry` exist, so the first order is `REJECTED: unsupported payment method: card`
- Notifications are sent through `[SMS]` only

### 2. `dev` profile

```bash
mvn exec:java -Dexec.mainClass=com.shop.hub.StoreApp -Dspring.profiles.active=dev
```

What you will see:
- Card payments go through `[FAKE CARD]`
- Notifications are sent through `[CONSOLE]` and `[SMS]`

### 3. `prod` profile

**Step 1 —** Set the Paymob API key as an environment variable:

```bash
# bash
export PAYMOB_API_KEY=your-key
```
```powershell
# PowerShell
$env:PAYMOB_API_KEY = "your-key"
```

**Step 2 —** Run with the `prod` profile:

```bash
mvn exec:java -Dexec.mainClass=com.shop.hub.StoreApp -Dspring.profiles.active=prod
```

What you will see:
- Card payments go through `[PAYMOB]`, with the key masked (e.g. `key ****1234`)
- Notifications are sent through `[EMAIL]` and `[SMS]`
- If you skip Step 1, the app stops at startup with `Could not resolve placeholder 'paymob.api-key'`

### 4. `loyalty` profile (combined with `dev` or `prod`)

```bash
mvn exec:java -Dexec.mainClass=com.shop.hub.StoreApp -Dspring.profiles.active=dev,loyalty
```

What you will see:
- Everything from `dev`, plus a `[LOYALTY] +N points` line after each order (1 point per 100 EGP)

### 5. Interactive menu

Add `-Dexec.args=menu` to any of the commands above, for example:

```bash
mvn exec:java -Dexec.mainClass=com.shop.hub.StoreApp -Dexec.args=menu -Dspring.profiles.active=dev
```

### Running from IntelliJ IDEA

1. Open **Run → Edit Configurations…** and select `StoreApp`
2. In **VM options**, add the profile: `-Dspring.profiles.active=dev` (or `prod`, `dev,loyalty`, …)
   - If the field is hidden, enable it from **Modify options → Add VM options**
3. For `prod`, add `PAYMOB_API_KEY=your-key` in **Environment variables**
4. For the interactive menu, put `menu` in **Program arguments**
5. Run `StoreApp`

---
