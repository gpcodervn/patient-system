# Patient Registration API

This project I'm building a simple Patient registration API for managing Patient records.

## API Requirements

The API should accept `JSON` and also respond with JSON.

Primary keys in the application should be `version 4 UUID’s`.

The API should be built in Java with Spring Boot. You can use the packages within Spring Boot you’re most used to.
Target Java 11.

There are two references to PatientID below. The attribute in the Patient model, and PatientID as a URL parameter. The PatientID in the Patient model is a string, e.g. A001 or A002. The PatientID when referenced in the URL refers to the id attribute of the Patient model and should be a version 4 UUID.

### API create a Patient

URL: `POST /patients`

Required params - FirstName
- LastName
- PatientID
- dob (Date of Birth)
- gender (should be M, F or O)

Optional params - MiddleName

The API should return a 400 bad request if a patient with the same PatientID already exists.

The API should return a 422 validation error with details of the validation error if any of the params fail validation.

If a record in the database exists with a conflicting PatientID but it’s been soft-deleted. The record should be re-activated and the attributes updated to the values from the current request.

If the operation succeeds, the API should return 201 Created and the Patient record.

### API delete a Patient

URL: `DELETE /patients/:PatientID`

If the PatientID is not a valid UUID a 400 Bad Request error should be returned.

If the PatientID is a valid UUID but can’t be found in the DB then a 404 Not Found should be returned.

If the patient can be located, the patient should be soft deleted. The response should be 204 No Content.

### API get a Patient

URL: `GET /patients/:PatientID`

If the PatientID is not a valid UUID a 400 Bad Request error should be returned.

If the PatientID is a valid UUID but can’t be found in the DB then a 404 Not Found should be returned.

If the patient can be located in the DB, the record should be returned to the user with a 200 status. Patients that have been soft-deleted should still be able to be loaded by this API.

### API update a Patient

URL: `PUT /patients/:PatientID`

If the PatientID is not a valid UUID a 400 Bad Request error should be returned.

If the PatientID is a valid UUID but can’t be found in the DB then a 404 Not Found should be returned.

If the PatientID in the request body is different from the existing PatientID, and the new PatientID already exists in the DB the API should return a 400 Bad Request.

If the update is successful, the API should return the updated record and status 200.

### API get list Patients

URL: GET /patients

Optional query params

LastName

- Wildcard search, case insensitive
- For example, “Jo” would return results for “Jones”, “Joanne” FirstName
- Wildcard search, case insensitive
- For example, “be” would match “Ben”, “benjamin” gender
- Exact match (but still case insensitive)

PatientID

- Exact match (but still case insensitive)

dob

- Exact match

search

- If search is passed, an OR query should be done against the possible options listed above.

- Some known formats of search input that could be used to optimise queries are “LastName, FirstName”, “LastName,”, “DD/MM/YYYY”

withDeleted

- when this query param is passed, if it is true, the listing should also return Patient records that have been soft deleted.

Users should be able to sort the search results by - FirstName
- LastName
- PatientID
- gender
- dob

including sorting by multiple columns at one time.

The request should return a 200 status, and an array of Patient records, filtered and sorted based on the query params passed. The results shouldn’t include any soft-deleted Patients unless withDeleted=true has been provided.

Pagination

- limit: number of patients will be returned
- page: page number

## What did I apply?

* JDK 11
* Maven
* Lombok
* Spring Boot
* Spring Data
* Spring validation
* H2 database

## How to start?

At root folder, run command:

> mvn clean install

Start application by command:

> mvn spring-boot:run -Dspring-boot.run.profiles=dev

Check server is running:

> http://localhost:8090/api/v1/patients