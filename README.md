# Social Media Desktop App

Hi! This is README file that will help you navigate through the application and help you understand the program.

# Description
This is ASU Connect. It is a social media app that will connect all friends together to share posts and comment on your friends posts and interact with new people. The program gives you the ability to add your friends, so their posts appear to you. You can like and comment any post. Edit your profile page as you want: upload a profile image, bio and friends you have. Post your thoughts, hobbies, opinion to all the people make your voice heard in the world. 

# Badges
![Application logo](https://github.com/mykhallaf/social-media-project/blob/main/SocialMediaApp/gui/Images/Asu%20connect.jpg)

# Installation

All application Files can be found on [GitHub](https://github.com/mykhallaf/social-media-project/tree/main) website you will find README file and a folder called [SocialMediaApp](https://github.com/mykhallaf/social-media-project/tree/main/SocialMediaApp). Open the folder then download [gui](https://github.com/mykhallaf/social-media-project/tree/main/SocialMediaApp/gui) folder all the needed application files are in that folder. The application is constructed mainly in **Java**, **JavaFx** is used for GUI and **sql** is used to store data into database.
### Requirements

 -   **Java Compiler:** Latest JVM and SDK
-   **Java IDE:** IntelliJ is preferred  
-   **Memory:** 4 GB RAM  
-   **Storage:** 500 MB available space

# Usage
 
 # Support
If there is any program, you faced or you want to make a feedback contact any of these emails ahmedhazem200411@gmail.com - mykalaaf@gmail.com

# Roadmap
The development team is working on: (Next Update)

 - Multi-threading
 - Client-based Server

# Contributing

This program is open for contribution, but we only accept it from university student, juniors' and seniors' programmers.
Thanks for your help and support!

# Authors and acknowledgment

We want to give thanks to all member of the development team:

 - Mohamed Khalaaf          | 22P0xxx
 - Mariam Gamgoum           | 22P0172
 - Mariam Ramy              | 22P0219
 - Basmala Khaled           | 22P0xxx
 - Ahmed Hazem                 | 22P0226
 - Mohamed Ahmed AbdelHamid | 22P0287

# Project status
**Finished!**

## UML diagrams

You can render UML diagrams using [Mermaid](https://mermaidjs.github.io/). For example, this will produce a sequence diagram about the Login procedure:

```mermaid
sequenceDiagram
User ->> UI: Enter Email & Password
UI ->> Backend: Send Email & password for verification
Backend ->> Database: Retreive Data
Database ->> Backend: Send Data
Backend ->> UI: Compare Data & give feedback
UI ->> User: Welcome to ASU Connet
