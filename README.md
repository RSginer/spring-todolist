## Spring Boot Todo-List
Kata to practice Spring Boot.

### Notes
* 📚 See API Docs, run and go to  [Swagger UI](http://localhost:8080/swagger-ui/index.html?configUrl=/api-docs/swagger-config).
* 📀 H2 Database used with Spring Data and JPA Repositories.
* 🔒 Spring Security with BasicAuth.
* 🧪 Unit and integration testing.
#### Install dependencies
```bash
mvn install
```

#### Run
```bash 
mvn spring-boot:run
```

#### Test
```bash
mvn test
```
- To run it with coverage html report in `target/site/jacoco`
```bash
mvn verify
```
