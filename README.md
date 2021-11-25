![Logo](devbookheader.png)

# Dude, where's my backend?

Hello all, Zark Muckerberg was all prepared for the launch of his new developer only social network - DevBook.

All was going swimmingly well until release day when it became apparent that the backend system has vanished...
Investigations are ongoing... however the launch cannot wait until then, thankfully we have a Crack team of super  
developers on standby ready to save the day.

Instructions are provided below and if you have any questions please feel free to reach out and clarify.

Thankfully some things were salvaged so not all hope is lost, we still have the following:

- React frontend
- Security implementation classes for our back end
- Detailed explanation of our API contracts

Without further ado lets get started...

## Tech Stack & Prerequisites

**Client:** React, Redux, Custom CSS, Axios

**Server:** Spring Boot, Java 11 / 17 (Your choice)

**Prerequisites:** Git, IntelliJ, Java 11/17, Npm 6.14 or greater, you may also need to modify your npm config
file to get npm working with our proxy - insert instrcutions here-

## Running the Frontend Locally

Clone the project

```bash
  git clone https://github.com/jhanna60/DevBook-FrontEnd.git
```

Go to the project directory

```bash
  cd DevBook-FrontEnd
```

Install dependencies

```bash
  npm install
```

Start the server

```bash
  npm run start
```

## API Documentation

Overall we will need 3 controllers - PostController, ProfileController and User Controller

As the front end is already built we will be constrained when it comes to naming conventions for our endpoints so
to that end I have a list of all the required endpoints and their controller below:

### User Controller

This controller will be responsible for registering new users onto the system and it will also
provide a UserDto when its called with a security token allowing the caller to work out what user it is
dealing with based on JWT token alone. It provides 2 endpoints in total:

#### Get User (Private)

```http
  GET /api/v1/users
```

This endpoint should return a UserDto, it is a private endpoint so the email will be known.
Email can be found (once auth has been done) using - SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()

#### Register User (Public)

```http
  POST /api/v1/users
```

This endpoint is used to register a new user and it is public, it expects a valid UserDto to be
passed in and in return it will return Status 200 and a Response body with {"token" : "tokenvalue"}

### Profile Controller

This controller will be responsible for everything profile related, it will allow CRUD operations on profiles
and allow the front end to return a profile for the logged in User.
On top of this it will provide some more functionality in the form of adding and removing both Education
and Experience objects from a Profile. Please note User and Profile are not the same thing. A User can exist BEFORE
they have a profile.

## Authors

- [@jhanna60](https://github.com/jhanna60)

- [@AlessandroArosio](https://github.com/AlessandroArosio)
