![Logo](devbookheader.png)

# Dude, where's my backend?

Hello all, Zark Muckerberg was all prepared for the launch of his new developer only social network - DevBook.

All was going swimmingly well until release day when it became apparent that the backend system has vanished...
Investigations are ongoing... however the launch cannot wait until then, thankfully we have a Crack team of super  
developers on standby ready to save the day.

Instructions are provided below and if you have any questions please feel free to reach out and clarify.

Thankfully some things were salvaged so not all hope is lost, we still have the following:

- React frontend
- Resources Folder of goodies, in there we have:
  - Security implementation classes for our back end
  - Raw Entities
  - List of dependencies needed for POM
- Detailed explanation of our API contracts

Please use this README carefully it has all the instructions you will need!!

## Tech Stack & Prerequisites

**Client:** React, Redux, Custom CSS, Axios

**Server:** Spring Boot, Java 17

**Prerequisites:** Git, IntelliJ, Java 17, Npm 6.14 or greater, Maven configured for proxy, npm config configured for proxy

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

### Prioritisation & Planning

If we are to split the overall requirements up using the [MoSCoW](https://en.wikipedia.org/wiki/MoSCoW_method) principle, we can break it down as follows:

![Logo](MoSCoW.png)

Please plan your workloads accordingly and aim to get the MVP out the door, continually assess progress and look to where your time is best spent, sometimes
it's better to cut your losses and move on in the grand scheme of things.

Overall we will need 3 controllers - 
* PostController 
* ProfileController
* User Controller

As the front end is already built we will be constrained when it comes to the naming conventions for our endpoints so
to that end I have a FULL list of all the required endpoints and their controllers below:

### User Controller

This controller will be responsible for registering new users onto the system and it will also
provide a UserDto when its called with a security token allowing the caller to work out what user it is
dealing with based on JWT token alone. This controller has a total of 2 endpoints.

#### Get User (Authenticated)

```http
  GET /api/v1/users
```

This endpoint should return a UserDto, the email will be known once the user is authenticated.
Email can be found (once auth has been done) using - SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()

#### Register User

```http
  POST /api/v1/users
```

This endpoint is used to register a new user and it requires no authentication, it expects a valid UserDto to be
passed in so that it can validate and save it away, we then log in the user and return Status 200 and a Response body with {"token" : "tokenvalue"}
This JWT token will provide the backbone for the entire front end and allow the logged in user to access protected endpoints.

### Profile Controller

This controller will be responsible for everything profile related, it will allow CRUD operations on profiles
and allow the front end to return a profile for the logged in User.
On top of this it will provide some more functionality in the form of adding and removing both Education
and Experience objects from a Profile. Please note User and Profile are not the same thing. A User can exist BEFORE
they have a profile. This controller has a total of 9 endpoints.

#### Get AllProfiles

```http
  GET /api/v1/profile
```

This endpoint should return all profiles as a List of ProfileDto
It requires no authentication and no input params/request body, just a simple get request to return all profiles in the DB

#### Get ProfileById

```http
  GET /api/v1/profile/user/{userId}
```

This endpoint is similar to the one above but instead of returning a list of ProfileDtos it will return just one Profile Dto
This profile will be the one that has the embedded user Id that was passed through.
It will accept a path variable of Long for user Id and pass back a ProfileDto

#### Get MyProfile (Authenticated)

```http
  GET /api/v1/profile/me
```

This endpoint will return a ProfileDto and it will derive this based on the logged in user, similar to the GetUser endpoint
we need to use - SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString() to get the users email.
This end point takes in no params or request body object and it returns a ProfileDto.

#### Create Or Update Profile (Authenticated)

```http
  POST /api/v1/profile
```

This endpoint will receive a partial ProfileDto object and then we are expected to see if a profile already exists for the logged
in user. If a profile already exists then please update it with the new changes, if it doesnt exist then you will need to build the full
Profile and add in the fields passed from the caller then save the profile.
This end point takes in a partial ProfileDto and will return a complete ProfileDto (Updated or created)
Please note this endpoint has a few 'Gotchas' for example check how the 'Skills' are sent and think about how you map them to the Entities
in the DB. Also keep in mind that the front end will expect these back in the same way that it sends them!

#### Delete MyProfile (Authenticated)

```http
  DELETE /api/v1/profile
```

This endpoint will be responsible for deleting a User, their profile and Posts.
Although it is in the profile controller we do expect this endpoint to fully delete a User, Profile and Posts associated with this user.
The endpoint expects no input and doesnt need to return anything except a 200-Status-OK once deleted.

#### Add Experience (Authenticated)

```http
  PUT /api/v1/profile/experience
```

This endpoint will accept a ExperienceDto as part of the Request body and it should update the profile of the logged in user
and finally it should return the full ProfileDto once it has finished

#### Delete Experience (Authenticated)

```http
  DELETE /api/v1/profile/experience/{experienceId}
```

This endpoint will accept a param being passed in 'experienceId' it should then go and remove the Experience object from the
profile of the logged in user and finally return the full ProfileDto when its finished

#### Add Education (Authenticated)

```http
  PUT /api/v1/profile/education
```

This endpoint will accept a EducationDto as part of the Request body and it should update the profile of the logged in user
and finally it should return the full ProfileDto once it has finished

#### Delete Education (Authenticated)

```http
  DELETE /api/v1/profile/experience/{educationId}
```

This endpoint will accept a param being passed in 'educationId' it should then go and remove the Education object from the
profile of the logged in user and finally return the full ProfileDto when its finished

### Post Controller

This controller is responsible for everything Post related and forms the main functionality of the Social Network
application, it allows people to make posts and delete them, also it allows for people 'Liking' and 'Commenting'
on the posts as well. This controller has a total of 8 endpoints

#### Get Posts (Authenticated)

```http
  GET /api/v1/posts
```

This endpoint will get all posts and return a List of PostDtos, it requires no input.

#### Get Post (Authenticated)

```http
  GET /api/v1/posts/{postId}
```

This endpoint will get one post and return a PostDtos, It has an input of postId which is used to locate the post it
needs to return

#### Create Post (Authenticated)

```http
  POST /api/v1/posts
```

This endpoint will create a new post, it will accept an incoming string for the post like {"text":"some text here"}
The endpoint should then build up a new Post object populating everything that is needed and then finally return the
PostDto object once it has been saved

#### Delete Post (Authenticated)

```http
  DELETE /api/v1/posts/{postId}
```

This endpoint will delete a post, it accepts a variable postId which will be the Id of the post we should delete, we
should then delete the post and return {"msg":"Post Removed"} alongside status 200-OK

#### Like Post (Authenticated)

```http
  PUT /api/v1/posts/like/{postId}
```

This endpoint will register a 'Like' under a post it should check if the user has already liked this post, if they
havent already then it should register a Like object for the user and then return a List of LikeDtos from the Post

#### UnLike Post (Authenticated)

```http
  PUT /api/v1/posts/unlike/{postId}
```

This endpoint is similar to above except it will 'unlike' a Post, of course the user has to have a like in there already
in order to remove it, once we remove the Like we should return a List of LikeDtos to reflect the new Likes.

#### Add comment to post (Authenticated)

```http
  POST /api/v1/posts/comment/{postId}
```

This endpoint will allow us to add a comment to a Post, it accepts a postId param and in RequestBody it will send {"text": "some comment"}
We should then find the relevant Post and add our comment to it finally we should return a List of CommentDto

#### Delete comment from post (Authenticated)

```http
  DELETE /api/v1/posts/comment/{postId}/{commentId}
```

This endpoint will allow us to delete a comment from a Post, it will send TWO params postId and commentId, this will
allow us to locate the Post and finally delete the comment matching commentId. We should return a List of CommentDtos
to reflect the new comments

## Reflection

What did you learn while building this project? 

What challenges did you face and how did you overcome them?

What would you do differently next time if faced with a similar challenge?

## Authors

- [@jhanna60](https://github.com/jhanna60)

- [@AlessandroArosio](https://github.com/AlessandroArosio)
