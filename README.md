# Salaries Extraction Project

## Description

This is a small project designed to extract salary information from a SQLite database in the form of a file. The project is built using Java, leveraging the Spring Boot framework to manage application logic, and SQLite as the database.

## Technologies

The project uses the following technologies:
- **Java**: The main programming language used for implementing business logic.
- **Spring Boot**: A framework that facilitates rapid development of web applications and dependency management.
- **SQLite**: A lightweight database used for storing and retrieving salary information.

## Features

- Fetch employee salary data from a SQLite database.
- Store salary data in the database in the form of a file.
- Process and extract data in a user-friendly format.

## API Endpoints
Here are the five most important API endpoints available in this project:

1. **Get All Salaries**
  - Endpoint: ```/api/salaries/all```
  - Method: GET

2. **Get Salaries By Year Range**
  - Endpoint: ```/api/salaries/year```
  - Method: GET
  - Parameters:
    1. minYear (optional): Minimum year for filtering salaries.
    2. maxYear (optional): Maximum year for filtering salaries.
    3. limit (default: 10): Maximum number of records to return
     
3. **Get Salaries By ID**
 - Endpoint: ```/api/salaries/id/{id}```
 - Method: GET
 - Parameters:
   1. id (required): The ID of the salary record.

4. **Search Salaries By Job Title**
 - Endpoint: ```/api/salaries/search```
 - Method: GET
 - Parameters:
   1. keyword (required): The keyword to search in job titles.
   2. limit (default: 10): Maximum number of records to return

5. **Get Average Salary by Year**
   - Endpoint: ```/api/salaries/avg-year```
   - Method: GET

## How to Run the Project

To run the project locally, follow these steps:
1. **Clone the repository**:
   ```git clone https://github.com/YourUsername/repository_name.git```
2. **Navigate to the project directory**:
   ```cd repository_name```
3. **Build the project using Maven (if you are using Maven)**:
   ```./mvw clean install```
4. **Run the application**:
   ```./mvw spring-boot:run```


