## 💼 Job Application System Project 🌐📄🏢🧑‍💼👩‍💻

📝 About Project The Job Application System is a RESTful API built with Spring Boot to efficiently manage Jobs, Job Seekers, Companies, and Job Applications. It provides full CRUD operations for all entities and follows the Model-View-Controller (MVC) architectural pattern.
The system is designed to simplify the process of job postings and applications. Companies can publish jobs, and job seekers can apply to them. It uses robust entity relationships and is structured to be clean, modular, and scalable. 
Security is integrated using **Spring Security with JWT Authentication**, enabling secure login and role-based access control (RBAC).

## 🚀 Features

### 🧑‍💼 Job Management
- Jobs can only be created after a company is registered.
- If a company is deleted, all its associated jobs are also removed (cascade delete).
- Job seekers can browse available jobs and view detailed information.


### 🏢 Company Management
- Companies can create, update, and delete their profiles.
- Each company can manage a list of multiple job postings.


### 👤 Job Seeker Management
- Job seekers can register, update their profiles, and apply for jobs.
- They can also view their application history and current statuses.


### 📄 Job Application Handling
- Job seekers can apply to any job.
- Companies can review and manage incoming applications.
- Applications display linked job, job seeker, and company information.


### 🔁 Entity Relationships
- Many-to-One:
- Job Application → Job
- Job Application → Job Seeker

- One-to-Many:
- Company → Jobs

### 🔐 Authentication & Authorization
- Secured endpoints using JWT Tokens.
- Supports Role-Based Access Control (RBAC) between Admin, User, and Manager roles.


### 🧪 Validation & Exception Handling
- Uses Spring Validation (javax.validation) for request validation.
- Includes global exception handling for consistent error messages.


### 📦 Database Migrations
- Uses Spring Boot’s automatic migration from application properties.
- Schema generation and updates handled via JPA/Hibernate configuration.

### 🐳 Docker Support
- Dockerfile: Defines how to build the Spring Boot application image.
- docker-compose.yml: Orchestrates running the Spring Boot app together with a PostgreSQL database container.

### 🧪 Testing & CI/CD

Unit Tests:

- Comprehensive JUnit tests have been implemented for controllers and services.
- CompanyControllerImplUnitTest ensures that endpoints return expected responses and handle edge cases correctly.
- Tests are located in src/test/java/com/AsadBabayev/....

CI/CD Integration:

- GitHub Actions workflow (.github/workflows/maven.yml) is set up to automatically:
- Build the project with Maven.
- Run all unit tests on every push or pull request to main.
- Cache Maven dependencies for faster builds.
- This ensures code quality and prevents broken changes from being merged.


### 🛠️ Technologies Used

- Java + Spring Boot
- Spring Data JPA + Hibernate
- Spring Security + JWT Authentication 
- PostgreSQL (or MySQL – configurable)
- Lombok for reducing boilerplate
- Maven for build automation
- MapStruct for DTO mapping
- Spring Validation API
- JUnit (unit tests)
- GitHub Actions (CI/CD)
- Docker & Docker Compose


## 🔧 Planned Improvements
### 🧠 Add caching mechanisms to boost performance
### 🐞 Fix minor gaps in job deletion, application logic, and error handling
### 🧹 Refactor and optimize code for better readability and maintainability
### ⚛️ Add frontend (React) in a short time


## 🐛 Found a Bug?
### If you find any bugs or would like to contribute:

- 📧 Contact: asad_babayev@outlook.com
- 📌 Submit an Issue via the GitHub Issues tab
- 🔄 Contribute a Pull Request (PR): Please reference related issues and follow the existing project structure
