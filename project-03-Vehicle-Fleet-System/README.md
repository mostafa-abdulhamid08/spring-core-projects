## Project Structure
```
src/main/java/com/example/fleet/
│
├── model/
│   ├── Vehicle.java              ← Interface — data contract
│   ├── ElectricVehicle.java      ← POJO implements Vehicle
│   └── PetrolVehicle.java        ← POJO implements Vehicle
│
├── repository/
│   └── FleetRepository.java      ← @Repository Bean
│
├── service/
│   ├── FleetLogger.java          ← @Component + @PostConstruct + @PreDestroy
│   └── FleetService.java         ← @Service + Constructor Injection + @PostConstruct
│
├── config/
│   ├── FleetBeanRegistrar.java   ← BeanRegistrar — dynamic registration
│   └── FleetConfig.java          ← @Configuration + @ComponentScan + @Import
│
└── FleetApp.java                 ← Main
```

---

## Topics Covered

| Topic | فين في الكود |
|---|---|
| Constructor Injection | `FleetService` — يستقبل `FleetRepository` و`FleetLogger` |
| Autowiring | Spring يحقن تلقائياً من غير `new` في أي حتة |
| DI | `FleetService` مش عارف إزاي اتعمل الـ dependencies |
| `@Service` / `@Repository` / `@Component` | كل الـ stereotype annotations |
| `@PostConstruct` | في `FleetLogger` و`FleetService` |
| `@PreDestroy` | في `FleetLogger` |
| `BeanRegistrar` | في `FleetBeanRegistrar` — يسجل Electric أو Petrol حسب الـ property |
| `@Import` | في `FleetConfig` |
| Singleton Proof | في الـ Main |
| `@ComponentScan` | في `FleetConfig` |
---