﻿# 'ATION

Vacation budgeting website app with functionality to get tickets and accommodation based on input budget and dates per country and city.
Includes showcase of current weather.

Main stack: Java, CSS, Html, Bootstrap 4, SpringBoot, Thymeleaf, SQL, Hibernate

## How to run

Few important steps needed to successfully run the website app

1) To run the project locally (minimum), a `application.properties` file is necessary. Destination for the aforementioned 
file is `src/main/resources/application.properties`. The content of the `application.properties` is present in the `properties_stuff.txt` file.
Very important NOTE is to have an `api.key` in the `application.properties`

2) A SQL (or some similar) database must contain currently predefined information. The file `sql_table_creation.txt` contains
the information needed to be in the database table. Hence, the database name and the table name must be as is. 

3) Download the project and run it in your preferred IDE. Must have a database already created with the table present.

4) The starting api link is `localhost:80/countries`

## Dependencies

All necessary dependencies are in the `pom.xml` file.
