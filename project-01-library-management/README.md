# Library Management System — Spring Core Project 1

## Project Structure
```
src/main/java/com/libraryProject/
│
├── beans/
│   ├── Book.java              ← POJO — data only
│   └── Magazine.java          ← POJO — data only
│
├── repository/
│   ├── BookRepository.java    ← @Repository Bean
│   └── MagazineRepository.java← @Repository Bean
│
├── service/
│   └── LibraryService.java    ← @Service Bean — business logic
│
├── config/
│   ├── BookConfig.java        ← @Configuration — @Bean definitions for books
│   ├── MagazineConfig.java    ← @Configuration — @Bean definitions for magazines
│   └── LibraryConfig.java     ← @Configuration + @Import + @ComponentScan
│
└── LibraryApp.java            ← Main
```

---

## Topics Covered

| Topic | Where in the Code |
|---|---|
| `NoUniqueBeanDefinitionException` | Thrown if you call `getBean(Book.class)` without `@Primary` |
| Custom Bean Names | `@Bean(name = "cleanCodeBook")` in `BookConfig` |
| `@Primary` | On `cleanCodeBook` — returned when no bean name is specified |
| `@Import` | In `LibraryConfig` — pulls in `BookConfig` and `MagazineConfig` |
| `@Component` / `@Service` / `@Repository` | In `LibraryService`, `BookRepository`, `MagazineRepository` |
| Constructor Injection | In the `LibraryService` constructor |
| Singleton Proof | In Main — `s1 == s2` → `true` |

---
