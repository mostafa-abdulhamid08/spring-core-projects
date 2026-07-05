# Notification System — Spring Core Project 2

## Project Structure
```
src/main/java/com/example/notification/
│
├── service/
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

| Topic | فين في الكود |
|---|---|
| `@Component` | `EmailNotification`, `SMSNotification`, `PushNotification`, `AppLogger`, `NotificationManager` |
| `@Primary` | على `EmailNotification` — هي الـ default |
| `@Qualifier` | في `NotificationManager` على الـ Field Injection |
| `@Bean` vs `@Component` | `AppStats` بـ `@Bean` — بيمثل external library |
| `@PostConstruct` | في `AppLogger.init()` |
| `@PreDestroy` | في `AppLogger.destroy()` |
| Constructor Injection | في `NotificationManager` — يستقبل `Map` بكل الـ implementations |
| Setter Injection | في `NotificationManager.setLogger()` |
| Field Injection | في `NotificationManager` — للمقارنة فقط |
| Dynamic Selection | في `notifyByType()` — اختيار الـ channel في الـ runtime |
| Singleton Proof | في الـ Main |
| `@Import` | في `AppConfig` |

---