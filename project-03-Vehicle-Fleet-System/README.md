# Vehicle Fleet System — Spring Core Project 3

## Project Structure
```
src/main/java/com/vehicleFleetSystem/
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

| Topic | Where in the Code |
|---|---|
| Constructor Injection | `FleetService` — receives `FleetRepository` and `FleetLogger` |
| Autowiring | Spring injects dependencies automatically — no `new` anywhere |
| DI | `FleetService` doesn't know how its dependencies were created |
| `@Service` / `@Repository` / `@Component` | All the stereotype annotations |
| `@PostConstruct` | In `FleetLogger` and `FleetService` |
| `@PreDestroy` | In `FleetLogger` |
| `BeanRegistrar` | In `FleetBeanRegistrar` — registers Electric or Petrol vehicles based on the `fleet.type` property |
| `@Import` | In `FleetConfig` |
| Singleton Proof | In Main |
| `@ComponentScan` | In `FleetConfig` |

---
