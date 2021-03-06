## Spring Boot Todo-List
Kata to practice Spring Boot.

### Notes
* ๐ See API Docs, run and go to  [Swagger UI](http://localhost:8080/swagger-ui/index.html?configUrl=/api-docs/swagger-config).
* ๐ H2 Database used with Spring Data and JPA Repositories.
* ๐ Spring Security with BasicAuth.
* ๐งช Unit and integration testing.
* ๐ Coverage reports with JaCoCo.
* ๐ App Monitor with AOP.

#### Install dependencies
```bash
mvn install
```
### Docker
##### Build image
```bash
docker build -t rsginer/spring-boot-todo-list .
```
##### Run container
```bash
docker container run -d -it -p 8080:8080 --name todolist rsginer/spring-boot-todo-list
```
- Go to http://localhost:8080/swagger-ui/index.html?configUrl=/api-docs/swagger-config
### Run
```bash 
mvn spring-boot:run
```
- Go to http://localhost:8080/swagger-ui/index.html?configUrl=/api-docs/swagger-config

### Test
```bash
mvn test
```
#### Coverage report
```bash
mvn verify
```
- Open html report with your browser `target/site/jacoco/index.html`

