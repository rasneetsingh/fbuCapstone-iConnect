# fbuCapstone-iConnect


# Name of App: iConnect

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)

## Overview
### Description
A social networking app for both prospective and current international students to search for students from the country or university they are interested in and chat with each other, create a resourceful post and also create a group chat to to discuss topics in common. 

### App Evaluation
- **Category:** Social networking.
- **Mobile:** It is mobile. 
- **Story:** Allows user to share post, connect with each other and start conversation.
- **Market:** Students
- **Habit:** Can be used everyday.

## Product Spec
### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* Sign up 
* LogIn 
  *Google and Facebook LogIn Features
* Edit the profile
* Create a Post
* Like/Comment/Share Post
* Message
*Create a Group Chat, Add Participant and start messaging. 
* View someone's university (Google map Api)
* Search User by school/country/major.
*Search Feature in Post
*Notificaton to see who liked and commented on post
*Dark Mode


**Optional Nice-to-have Stories**
*Notificaton to see who liked and commented on post
*Create a group, add participants and start conversation
*Push Notification


### 2. Screen Archetypes

* Get Started
* Register - User can sign up or login
   * Prompted to home screen
  
* Home Fragment - Shows the post
    *Detail view of post
   
* Add Post Activity
   * Allows user to compose the post
* Profile Screen
   * User Profile and their info and MAP
 *Notification Screen
  *See who liked and commented on your post.
 *Group Chat
  *Create a group and add participants and start messaging


### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Home Screen
* Compose Screen
* Profile
 



 
## Wireframes

![wireframe](https://user-images.githubusercontent.com/67130269/174226708-8cadde81-0dc9-47f6-b0ad-132c5ff67098.jpg)



## Schema 
### Models

#### User

   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the user |
   | Username      | String   | username for sign|
   | Password      | String   | the users password |
   | uni name        | String   | the university name |
  
   
   
   #### Posts

   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the post |
   |   description        | String   | the post description |
   | User          | Pointer   | the user who posted |
   | createdAt     | DateTime | date the post is created|
   
### Networking
#### List of network requests by screen

#### [OPTIONAL:] Existing API Endpoints
##### GoogleMap API 


   
    


