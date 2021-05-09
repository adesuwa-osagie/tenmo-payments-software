# Tenmo Payments Software

## Table of Contents

  - [Introduction](#introduction)
    - [Future Tasks](#future-tasks)
  - [Architecture](#architecture)
  - [How To Run The Project](#how-to-run-the-project)
    - [Database Scripts](#database-scripts)
    - [Java Setup](#java-setup)

## Introduction
This is the second capstone project for Tech Elevator, simulating a Venmo app. It allows the user to register, login, and transfer TE bucks (ie. imaginary money to other registered users.

### Future Tasks
- [ ] Allow users to request TE bucks
- [ ] Allow users to view their pending requests 
- [ ] Unit testing 

For this project, a RESTful API was programmed using Spring Boot framework and PostgreSQL.

## Architecture
This is a Java application.
* Back End:
  * Java
  * PostgreSQL
* Testing
  * Postman
  * Integration Testing

## How To Run The P(roject

### Database Scripts

The database scripts for the database can be found under **java/*database***. Run *schema.sql*, then *user.sql*.


### Java Setup 

1. Download the project.

2. Open the project in IntelliJ IDEA,  or another computer software that reads and writes Java.
   
3. Under **java/*tenmo-server*/src/main/resources, select filed called *application.properties*.
   * Go line 2: *spring.datasource.url= ...*
   * Change the connection to <u>the connection of the database loaded with the scripts *schema.sql* and *user.sql*.</u>

4. Under the **java/*tenmo-server*/src/main/java/com/techelevator/tenmo** folder, there is a runnable class called *TenmoApplication*.
   
5. Run *TenmoApplication* and **keep is running** when running the the app in order to use the app.
   
   **Note:** This class must be running when using the app.

6. To find the class that runs the app, go under the **java/*tenmo-client*/src/main/java/com/techelevator/tenmo** folder, then select the runnable class called *App*.
   
7. Run *App*.

8. The app should display the following options:

        1) View your current balance
        2) Send TE bucks
        3) View your past transfers
        4) Request TE bucks
        5) View your pending requests
        6) Login as different user
        7) Exit


