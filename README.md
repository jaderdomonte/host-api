[![Java CI with Maven](https://github.com/jaderdomonte/booking-api/actions/workflows/maven.yml/badge.svg?branch=main&event=push)](https://github.com/jaderdomonte/booking-api/actions/workflows/maven.yml)

# Hostfully - Test Review - Backend
## Booking RESTful Java API
Booking API is a simple RESTful API for managing bookings and blockings in a property. 
It provides endpoints for creating, reading, updating, deleting, cancel and rebook bookings, as well
as provide endpoints for creating, reading, updating and deleting Blockings.


## Instalation
Follow these steps to get started:

1. Clone this repo (git clone https://github.com/jaderdomonte/host-api.git)

2. Get into the project folder that has the pom.xml file.

3. Execute the commands below:

    - mvn package
    - java -jar target/host-api-0.0.1-SNAPSHOT.jar

Or skip steps 2 and 3 and import the project into your IDE and run it.

4. Access: http://localhost:4000/swagger-ui/index.html#/

## Tecnology
- Java 17
- Maven
- SpringBoot
- Spring Data JPA
- Spring Validation
- Spring Web
- H2 database
- Lombok
- Junit
- Mockito

## Architecture description
A object-oriented multilayered architecture mixed with some DDD concepts as well.

Layers:
   - domain: business logic class of the application.
   - db: handler all the interactions with the database.
   - exceptions: handler all the exceptions launched by the application.
   - usecases: implements the use cases of the application.
   - web: handler all the interactions with web requisitions.

## Features
Bookings
   - Create Booking.
   - Get a Booking by id.
   - Get Bookings by filter.
   - Update Booking.
   - Delete Booking.
   - Cancel Booking.
   - Activate Booking.

Blockings
   - Create Blocking.
   - Get Blockings by filter.
   - Update Blocking.
   - Delete Blocking.

Obs: Assuming that Check in must start after **2pm** and CheckOut must end before **11am**. This rule will be valid for Bookings and Blockings. Therefore, it is possible to have the following scenarios for the same property:
1. A Blocking Check in **2024-06-12** and Check out **2024-06-21**
2. A Booking Check in **2024-06-21** and Check out **2024-06-25**
3. A Booking Check in **2024-06-25** and Check out **2024-06-28**
4. A Blocking Check in **2024-06-28** and Check out **2024-07-05**

## Documentation
Access http://localhost:4000/swagger-ui/index.html#/ to test all features and see full documentation.

## Author
- Jader Santos
- email: jaderdomonte@gmail.com
- Linkedin: www.linkedin.com/in/jaderdomonte

