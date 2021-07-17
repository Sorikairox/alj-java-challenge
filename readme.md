### How to use this spring-boot project

- Install packages with `mvn package`
- Run `mvn spring-boot:run` for starting the application (or use your IDE)

Application (with the embedded H2 database) is ready to be used ! You can access the url below for testing it :

- Swagger UI : http://localhost:8080/swagger-ui.html
- H2 UI : http://localhost:8080/h2-console

> Don't forget to set the `JDBC URL` value as `jdbc:h2:mem:testdb` for H2 UI.



### Instructions

- download the zip file of this project
- create a repository in your own github named 'java-challenge'
- clone your repository in a folder on your machine
- extract the zip file in this folder
- commit and push

- Enhance the code in any ways you can see, you are free! Some possibilities:
  - Add tests
  - Change syntax
  - Protect controller end points
  - Add caching logic for database calls
  - Improve doc and comments
  - Fix any bug you might find
- Edit readme.md and add any comments. It can be about what you did, what you would have done if you had more time, etc.
- Send us the link of your repository.

#### Restrictions
- use java 8


#### What we will look for
- Readability of your code
- Documentation
- Comments in your code 
- Appropriate usage of spring boot
- Appropriate usage of packages
- Is the application running as expected
- No performance issues


### What did I do ?

- Read how to unit test Spring Boot app.
- I wrote unit test for EmployeeController, and renamed/fixed things.
- I wrote unit test for EmployeeServiceImpl.
- I wrote e2e test for all API endpoints


### My experience in Java

It was my first time using Java. Yes, you read that right, no missing words. I'm used to Javascript/Typescript.<br>However, I strongly believe that the language I use is irrelevant, as long as it is Object-Oriented.

Why ?

Simply because 2 years ago I started a journey to become a Software Craftsman, all the things I learn and practice along the way apply to every OOP languages. <br>I might not know every method of the `ArrayList` class, but I am trying my best to write clean code as defined by [Robert C Martin](https://www.amazon.com/Clean-Code-Handbook-Software-Craftsmanship/dp/0132350882).