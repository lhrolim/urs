# urs

## General ##

Implementing an stub api in java 11 using spring-boot with the following requirements:

* Design and implement an API for short URL creation
* Implement forwarding of short URLs to the original ones
* There should be some form of persistent storage
* The application should be distributed as one or more Docker images

## Implementation ##

* The Short Urls were generated using a 36^length combination algorithm (uncapital letters and numbers), where length can be adjusted at the application.properties file (`urs.shorturlLength`)
* The statistics are updated asynchronously to the database. The idea is to keep the performance high and allow eventual modularization. 
  Any hits to a given short-url are first stored into Redis. These are only synced to the database on Quartz Job whose interval can be configured at (`urs.statisticsUpdateIntervalInMinutes`)
  
## API ##

* creation:  
 [Post]http://localhost:8080/urs/api/v1/url
* redirect:
 [Get] http://localhost:8080/urs/{shorturl}
* statistics:
 [Get] http://localhost:8080/urs/api/v1/statistics/{shorturl}

## Running 

After cloning the project:

`mvn clean package dockerfile:build && docker-compose build && docker-compose up`

The server should be accessible localhost:8080


## Database ##

Used postgres as the database.
* The standard properties are  dbname:urs, user:ursuser, password:urspassword
* Schema is automatically migrated using liquibase

## Customizing ##

Several aspects can be modified at the application.properties file


## TODOS ##

* Create unit tests
* Document the API, using swagger
* Extract an extra micro-service for the statistics part