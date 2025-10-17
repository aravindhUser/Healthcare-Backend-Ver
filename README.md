# Healthcare Appointment Booking System

This monorepo contains all microservices for the healthcare system.

## Microservices
- `doctor-service`: Manages doctor profiles and availability
- `patient-service`: Handles patient registration and history
- `appointment-service`: Manages appointment booking
- `auth-service`: Authentication and JWT
- `api-gateway`: Centralized routing
- `service-discovery`: Eureka server

## How to Run
Each service is a Spring Boot project. Navigate to the folder and run:

mvn spring-boot:run
