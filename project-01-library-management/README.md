# Library Management System — Spring Core Project

## Project Structure
```
src/main/java/com/example/library/
│
├── beans/
│   ├── Book.java              ← POJO — data فقط
│   └── Magazine.java          ← POJO — data فقط
│
├── repository/
│   ├── BookRepository.java    ← @Repository Bean
│   └── MagazineRepository.java← @Repository Bean
│
├── service/
│   └── LibraryService.java    ← @Service Bean — business logic
│
├── config/
│   ├── BookConfig.java        ← @Configuration — @Bean للكتب
│   ├── MagazineConfig.java    ← @Configuration — @Bean للمجلات
│   └── LibraryConfig.java     ← @Configuration + @Import + @ComponentScan
│
└── LibraryApp.java            ← Main
```

---

## Topics Covered

| Topic | فين في الكود |
|---|---|
| `NoUniqueBeanDefinitionException` | لو جربت `getBean(Book.class)` من غير `@Primary` |
| Custom Bean Names | `@Bean(name = "cleanCodeBook")` في `BookConfig` |
| `@Primary` | على `cleanCodeBook` — هو اللي بيتجاب لو مش حددت |
| `@Import` | في `LibraryConfig` بيجيب `BookConfig` و`MagazineConfig` |
| `@Component` / `@Service` / `@Repository` | في `LibraryService`, `BookRepository`, `MagazineRepository` |
| Constructor Injection | في `LibraryService` constructor |
| Singleton Proof | في الـ Main — `service1 == service2` → `true` |

---
