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

#### Get AllProfiles (Public)

```http
  GET /api/v1/profile
```

This endpoint should return all profiles as a List of ProfileDto
It requires no authentication and no input params/request body, just a simple get request to return all profiles in the DB

#### Get ProfileById (Public)

```http
  GET /api/v1/profile/user/{userId}
```

This endpoint is similar to the one above but instead of returning a list of ProfileDtos it will return just one Profile Dto
This profile will be the one that has the embedded user Id that was passed through.
It will accept a path variable of Long for user Id and pass back a ProfileDto

#### Get MyProfile (Private)

```http
  GET /api/v1/profile/me
```

This endpoint will return a ProfileDto and it will derive this based on the logged in user, similar to the GetUser endpoint
we need to use - SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString() to get the users email.
This end point takes in no params or request body object and it returns a ProfileDto.

#### Create Or Update Profile (Private)

```http
  POST /api/v1/profile
```

This endpoint will receive a partial ProfileDto object and then we are expected to see if a profile already exists for the logged
in user. If a profile already exists then please update it with the new changes, if it doesnt exist then you will need to build the full
Profile and add in the fields passed from the caller then save the profile.
This end point takes in a partial ProfileDto and will return a complete ProfileDto (Updated or created)
Please note this endpoint has a few 'Gotchas' for example check how the 'Skills' are sent and think about how you map them to the Entities
in the DB. Also keep in mind that the front end will expect these back in the same way that it sends them!

#### Delete MyProfile (Private)

```http
  DELETE /api/v1/profile
```

This endpoint will be responsible for deleting a User, their profile and Posts.
Although it is in the profile controller we do expect this endpoint to fully delete a User, Profile and Posts associated with this user.
The endpoint expects no input and doesnt need to return anything except a 200-Status-OK once deleted.

#### Add Experience (Private)

```http
  PUT /api/v1/profile/experience
```

This endpoint will accept a ExperienceDto as part of the Request body and it should update the profile of the logged in user
and finally it should return the full ProfileDto once it has finished

#### Delete Experience (Private)

```http
  DELETE /api/v1/profile/experience/{experienceId}
```

This endpoint will accept a param being passed in 'experienceId' it should then go and remove the Experience object from the
profile of the logged in user and finally return the full ProfileDto when its finished

#### Add Education (Private)

```http
  PUT /api/v1/profile/education
```

This endpoint will accept a EducationDto as part of the Request body and it should update the profile of the logged in user
and finally it should return the full ProfileDto once it has finished

#### Delete Education (Private)

```http
  DELETE /api/v1/profile/experience/{educationId}
```

This endpoint will accept a param being passed in 'educationId' it should then go and remove the Education object from the
profile of the logged in user and finally return the full ProfileDto when its finished

## Authors

- [@jhanna60](https://github.com/jhanna60)

- [@AlessandroArosio](https://github.com/AlessandroArosio)
