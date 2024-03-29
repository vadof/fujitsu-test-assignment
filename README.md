## Technologies
- Spring Boot 3.2.4
- Java 17
- H2 Database

## Description

- In addition to the table where weather data is stored,
the station table and the regional_base_fee table have also been added.
They were added to make the system more flexible.
- When you launch the application, the weather data is immediately updated, 
and then it is updated according to a schedule.
</br>

In addition to the main task added:
- Endpoint to get all available cities for delivery and the vehicles that are available in them.
- Endpoint to update RBF price.


### Endpoint documentation [OpenAPI](http://localhost:8080/swagger-ui/index.html#/)<br/>
### Database Console:
- [URL](http://localhost:8080/h2-console)
- Driver Class: org.h2.Driver
- JDBC URL: jdbc:h2:mem:delivery
- User Name: sa
- Password: none

#### NB! To open the documentation and database console the application must be running