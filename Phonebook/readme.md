To run application, please execute the following command in Maven:

1) To use SQL storage: mvn spring-boot:run -Dspring.profiles.active=sql
2) To use file storage: mvn spring-boot:run -Dspring.profiles.active=file

Script to Create MySQL Database Phonebook:

CREATE SCHEMA `Phonebook`;

All required tables and files will create automatically
