# DbApplication backend
Useful links/credit:
- [Markdown cheatsheet](https://www.markdownguide.org/cheat-sheet/)
- [Stackedit.io](https://stackedit.io/app#)

## Notes

- to use MongoDbReference, start spring with profile: `gradlew bootRun --args='--spring.profiles.active=mongoref'`
- to use MongoDbEmbedded, start spring with profile: `gradlew bootRun --args='--spring.profiles.active=mongoemb'`
- to use PostgreSql Traditional, start spring with profile: `gradlew bootRun --args='--spring.profiles.active=postgretrad'`
- to use PostgreSql Reference JSON, start spring with profile: `gradlew bootRun --args='--spring.profiles.active=postgreref'`
- to use PostgreSql Embedded JSON, start spring with profile: `gradlew bootRun --args='--spring.profiles.active=postgreemb'`

## Endpoints for all controllers
- GET `http://localhost:8080` <-- validate current running version of backend

### Countries:
- GET `http://localhost:8080/countries` <-- get all countries
- GET `http://localhost:8080/countries/{countryCode}` <-- get country by code
- POST `http://localhost:8080/countries` <-- add a new country

### Occupations:
- GET `http://localhost:8080/occupations` <-- get all occupations
- GET `http://localhost:8080/occupations/{occupationCode}` <-- get occupation by code
- POST `http://localhost:8080/occupations` <-- add a new occupation

### Employee status types:
- GET `http://localhost:8080/employeeStatusTypes` <-- get all employee status types
- GET `http://localhost:8080/employeeStatusTypes/{employeeStatusTypeCode}` <-- get employee status type by code
- POST `http://localhost:8080/employeeStatusTypes` <-- add a new employee status type

### Persons:
- GET `http://localhost:8080/persons` <-- get all persons
- GET `http://localhost:8080/persons/{objectID}` <-- get person by id
- POST `http://localhost:8080/persons` <-- add a new person
- PUT `http://localhost:8080/persons` <-- update person

### Employees:
- GET `http://localhost:8080/employees` <-- get all employees
- GET `http://localhost:8080/employees/{personId}` <-- get employee by person_id
- POST `http://localhost:8080/employees` <-- add a new employee
- PUT `http://localhost:8080/employees` <-- update employee
- DELETE `http://localhost:8080/employees/{personId}` <-- delete employee by person_id

### Employments:
- GET `http://localhost:8080/employments/occupationCode={occupationCode}` <-- get all employments for occupation_code
- GET `http://localhost:8080/employments/personId={personId}` <-- get all employments for employee by person_id
- POST `http://localhost:8080/employments` <-- add a new employment for employee
- PUT `http://localhost:8080/employments` <-- update employment for employee
- PUT `http://localhost:8080/employments/endEmployments` <-- end all employments for employee


