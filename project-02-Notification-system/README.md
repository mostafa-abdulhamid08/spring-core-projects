# Notification System — Spring Core Project 2

## Project Structure
```
src/main/java/com/notificationSystem/
│
├── services/
│   ├── NotificationService.java     ← Interface
│   ├── EmailNotification.java       ← @Component @Primary
│   ├── SMSNotification.java         ← @Component
│   ├── PushNotification.java        ← @Component
│   └── NotificationManager.java     ← @Component — business logic
│
├── beans/
│   └── AppLogger.java               ← @Component + @PostConstruct + @PreDestroy
│
├── config/
│   ├── NotificationConfig.java      ← @Configuration + @Bean
│   └── AppConfig.java               ← @Configuration + @ComponentScan + @Import
│
└── NotificationApp.java             ← Main
```

---

## Topics Covered

| Topic | Where in the Code |
|---|---|
| `@Component` | `EmailNotification`, `SMSNotification`, `PushNotification`, `AppLogger`, `NotificationManager` |
| `@Primary` | On `EmailNotification` — the default implementation |
| `@Qualifier` | In `NotificationManager` on the field injection |
| `@Bean` vs `@Component` | `AppStats` is registered with `@Bean` — simulates a class from an external library |
| `@PostConstruct` | In `AppLogger.init()` |
| `@PreDestroy` | In `AppLogger.destroy()` |
| Constructor Injection | In `NotificationManager` — receives a `Map` of all implementations |
| Setter Injection | In `NotificationManager.setAppLogger()` |
| Field Injection | In `NotificationManager` — for comparison only |
| Dynamic Selection | In `notifyByType()` — picks the channel at runtime |
| Singleton Proof | In Main |
| `@Import` | In `AppConfig` |

---
