# Patient Registration Web #

This project I'm building a simple user interface for managing Patient records. 

The UI will interact with the API I have built in the first part.

## Requirements

### create a patient
A user can create a patient with the following data.

Required:
- First Name
- Last Name
- Patient ID
- Date of Birth
- Gender (M, F or O)

Optional:
- Middle Name

The screen should show the user any validation errors that occur during submission to the API.

If the record is successfully saved the user should be navigated to the “View Patient” screen and be shown the Patient they have just created.

### View a patient

On this screen, a user should be able to see the details of the Patient record they have selected.

### Edit a patient

On this screen, a user should be able to edit the details of the Patient they have selected to edit. 

If there are any validation errors these should be shown to the user.

Any of the other possible errors that could occur for this API should also be shown to the user. 

Upon successfully saving the update, the user should be navigated to the “View Patient” screen.

### View list of patients

This is the default screen of the app.

- On this screen, the patients will be listed.

- The default sort will be by PatientID descending.

- The user should be able to enter a search term into a search box and use this to filter the patients.

- The user should be able to select a page number to filter the patients.

- The user should be able to limit a number of patients to show.

- The user should be able to select one of the available columns to sort by and also the direction of the sort.

- The user should be able to select if they want to see soft-deleted results. By default, the soft-deleted results shouldn’t be shown.

## What did I apply?

The UI have be built with ReactJS, React Router, PrimeReact, Bootstrap, Axios.

## Install software

To start the backend on your machine you need to have the following software installed:

* NodeJS

## How to start?

At root folder, run command:

> npm install

> npm run start

Check web is running:

> http://localhost:3000/