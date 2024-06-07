RESTAssured API uses Maven Project Dependencies defined on the POM file.
selenium-remote-driver (org.seleniumhq.selenium)
junit
poi
json-schema-validator
io.rest-assured
testng
rest-assured
Maven
Designed based on the Serenity Framework
Java
IntelliJ IDE
Selenium
Cucumber

Instructions to Run the Tests

Install Maven:
Ensure you have Maven installed on your system. You can download it from Maven's official website.

Project Structure:
Organize your project structure as follows:

my-restassured-tests/
├── pom.xml
└── src/
    └── test/
        └── java/
            └── CocktailDBTests.java
			
Run the Tests:
Open a terminal, navigate to your project directory, and execute the following command:

>> mvn test

NON-FUNCTIONAL TEST
Performance Testing:
Test the response time of the API under various loads.
Suggested Framework: Apache JMeter or Gatling.

Security Testing:
Test for common security vulnerabilities like SQL injection, XSS, and API authentication mechanisms.
Suggested Framework: OWASP ZAP or Burp Suite.

Assumptions Made
The CocktailDB API is publicly accessible without authentication.
The responses follow the documented structure consistently.
The environment is set up with Maven and Java installed.
This setup and the provided tests allows one to test the CocktailDB API according to the provided requirements.
