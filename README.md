[![Codacy Badge](https://app.codacy.com/project/badge/Grade/f9d41228810e49478c4ed525b3a9b77d)](https://www.codacy.com/gh/ticherti/_vote-for-lunch/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=ticherti/_vote-for-lunch&amp;utm_campaign=Badge_Grade)
[Project Vote For Restaurant](https://github.com/ticherti/_vote-for-lunch)
===============================

#### Voting system for deciding where to have lunch

 - Spring Boot  
 - Spring Data JPA  
 - Jackson  
 - Lombok  
 - H2 Database  
 - JUnit 5  
 - Swagger/ OpenAPI 3.0
-----------------------------------------------------
The task is:

Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend.

Build a voting system for deciding where to have lunch.

 - 2 types of users: admin and regular user  
 - Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
 - Menu changes each day (admins do the updates)  
 - Users can vote on which restaurant they want to have lunch at  
 - Only one vote counted per user  
 - If user votes again the same day:  
     - If it is before 11:00 we assume that he changed his mind.      
     - If it is after 11:00 then it is too late, vote can't be changed  
 - Each restaurant provides a new menu each day.
-------------------------------------------------------------
 - Stack: [JDK 17](http://jdk.java.net/17/), Spring Boot 2.5, Lombok, H2, Caffeine Cache, Swagger/OpenAPI 3.0, Mapstruct
 - Run: `mvn spring-boot:run` in root directory.
-----------------------------------------------------
[REST API documentation](http://localhost:8080/swagger-ui.html)  
Credentials:
```
User:  user@yandex.ru / password
Admin: admin@gmail.com / admin
```