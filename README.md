
# ING Mortage issual project

## Frameworks Used

Java 17, Spring Boot 3.1, Maven 3.8.
For removing bolier plate code --- Lombok.
API Documentation - Open API 3.0.(but classes arent generated using maven plugin). Sample file have been placed in the project to express the developer's
desire on API design first approach
Database-Embedded DB - H2.
Testing - Junit jupiter, Mockito, MockMvc Embedded DB - H2.
No checkstyles files used

## Code setup and run
1. Extract the zip file
2. Navigate to project root folder and execute mvn spring-boot:run -Dspring-boot.run.profiles=prod (ensure maven is present on the machine)
3. Application uses port 8080
4. Kindly provide Trace-Id(random number) and Token (random number) in the headers. For now, this information is used for logging
## Packaging structure

Considering microservice architecture functional package is taken into consideration but commonly used classes are packaged as layers

## Production ready considerations:

1. Spring boot application runs with active profiles hence properties per environment is placed and the prod profile is configured to point the H2 database. In the real time scenario, different databases can be used based on the environment.
2. Spring security is enabled with different credentials for prod with different roles. Roles can also be configured in tables and can be made run time rather than hardcoding
3. Actuator and prometheus endpoints are made available to capture health and metrics which is accessible for user with role SUPERUSER.

   http://localhost:8080/actuator :
   With actuators operational informations like health, metrics, info, dump can be collected

   http://localhost:8080/actuator/auditevents
   Audit events are collected

   Metrics sent by actuators can be intercepted by prometheus server for event monitoring and alerting
4. If application is hosted on cloud (Azure for instance), then ApplicationInsights can be condigured with Instrumentation key for monitoring and logAnalytics
5. Azure keyvault can be used to store and retrive secrets and certificates
6. Additional validation like TLS/MA  and validating user token against AD can be done 
