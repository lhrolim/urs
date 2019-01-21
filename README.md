# urs

## General ##

Implementing an stub api in java 11 using spring-boot with the following requirements:

* Design and implement an API for short URL creation
* Implement forwarding of short URLs to the original ones
* There should be some form of persistent storage
* The application should be distributed as one or more Docker images

## Running 

mvn clean package dockerfile:build && docker-compose build && docker-compose up


## Database ##

Used postgressql as the database.
* The standard properties are  dbname:urs, user:ursuser, password:urspassword
* Schema is automatically migrated using liquibase

## Customizing ##

Several aspects can be modified at the application.properties file


## TODOS ##

* Create unit tests
* Document the API