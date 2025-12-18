# 🐾 Petstore API Test Automation Framework

A professional API test automation framework for the [Swagger Petstore](https://petstore.swagger.io/), built with Java. This project demonstrates a modern testing approach using **OpenAPI Generator**, **Service Object Pattern**, **Fluent Assertions**, and **Allure Reporting**.

## 🚀 Technology Stack

* **Java 17** - Programming language.
* **Maven** - Dependency management and build lifecycle.
* **OpenAPI Generator** (Maven Plugin) - Automatically generates the API client (OkHttp + Gson) from the Swagger spec.
* **JUnit 5** - Testing framework.
* **AssertJ** - Fluent assertions library.
* **Allure Framework** - Comprehensive test reporting.
* **AspectJ** - AOP agent used to track test steps (`@Step`) for reports.
* **Lombok** - Reduces boilerplate code (loggers, getters/setters).

---

## 📂 Project Architecture

The project follows a layered architecture to separate HTTP technical details from business test logic:

1.  **Generated Client (`src/gen/java`)**:
    * Code generated automatically by the `openapi-generator-maven-plugin`.
    * Contains DTO models (`Pet`, `Category`) and the technical HTTP client (`PetApi`).
    * *Note: This code should not be modified manually.*

2.  **Service Object (`PetStoreService`)**:
    * A wrapper layer over the generated client.
    * Uses `...WithHttpInfo` methods to control HTTP status codes (e.g., expecting 200 OK or 404 Not Found).
    * Defines report steps using the `@Step` annotation.
    * Examples: `addPet_200(Pet pet)`, `getPetById_404(Long id)`.

3.  **Custom Asserters (`PetAsserter`)**:
    * Custom assertion class extending AssertJ's `AbstractAssert`.
    * Enables readable, fluent data verification:
        ```java
        assertThat(pet)
            .toHaveName("Doggy")
            .toHaveStatus(AVAILABLE);
        ```

4.  **Tests (`PetStoreTest`)**:
    * Clean test scenarios.
    * No raw HTTP code or `try-catch` blocks.
    * Focuses on the business flow: *Prepare Data -> Execute Action via Service -> Verify via Asserter*.

---

## ⚙️ Prerequisites & Configuration

### 1. Prerequisites
* JDK 17 or higher.
* Maven 3.8 or higher.

### 2. Allure Configuration
For reports to be generated correctly, the project requires a properties file to specify the output directory.

**File:** `src/test/resources/allure.properties`
```properties
allure.results.directory=target/allure-results
```

---

## ▶️ Running Tests

### Step 1: Build and Test
Run the following command to clean the project, generate OpenAPI sources, and execute tests:

```bash
mvn clean test
```

**What happens under the hood?**
1.  The OpenAPI plugin downloads the Swagger spec and generates code in `src/gen/java/main`.
2.  The `maven-dependency-plugin` copies the `aspectjweaver.jar` into the `target` folder.
3.  The `maven-surefire-plugin` runs tests with the `-javaagent` flag, allowing Allure to capture `@Step` annotations.

### Step 2: Generate Report
After the tests finish (regardless of pass/fail status), generate the HTML report with:

```bash
mvn allure:serve
```

This command will start a temporary local web server and automatically open the report in your default browser.

---

## 📦 Directory Structure

```text
.
├── pom.xml                        # Maven configuration
├── src
│   ├── gen/java/main              # Auto-generated API client (Do not edit)
│   └── test
│       ├── java
│       │   └── com/example
│       │       ├── api            # (Generated) API Client classes
│       │       ├── model          # (Generated) DTO models
│       │       ├── asserters      # Custom Fluent Asserters
│       │       ├── service        # Service Objects (@Step definitions)
│       │       └── PetStoreTest.java  # Main Test Class
│       └── resources
│           └── allure.properties  # Allure configuration
└── target
    └── allure-results             # Raw test result files (JSON)
```
