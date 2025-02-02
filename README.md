# 💼 Portfolio API Development

This project is a **RESTful API** designed to manage user portfolio information. It allows users to perform CRUD operations on their portfolios, including projects, skills, experience, and education. The API is built using **Spring Boot**, and it ensures secure access with **JWT authentication**.

---

## 🌟 Features

- 👩‍💻 **Manage Projects**: Add, update, delete, and retrieve all projects.
- 🧑‍💼 **Manage Skills**: Add, update, delete, and retrieve all skills.
- 📚 **Manage Education**: Add, update, delete, and retrieve education details.
- 🏆 **Manage Experience**: Add, update, delete, and retrieve work experience.
- 🔐 **User Authentication**: JWT-based authentication for secure access.
- 🚀 **User Authorization**: Ensures users can only modify their own portfolio data.
- 📜 **Interactive API Documentation**: Swagger UI for easy access and testing.

---

## 🛠️ Technologies Used

- **Java**
- **Spring Boot**
- **JWT (JSON Web Tokens)**
- **Hibernate/JPA**
- **Swagger (Springdoc OpenAPI)**
- **H2 Database** (for development/testing)

---

## 📂 API Endpoints and Methods

### Project Endpoints

#### 1. **Add Project**
- **Endpoint:** `/addProject`
- **Method:** POST
- **Description:** Adds a new project to the portfolio.

#### 2. **Get All Projects**
- **Endpoint:** `/getAllProjects`
- **Method:** GET
- **Description:** Retrieves all projects from the portfolio.

#### 3. **Update Project**
- **Endpoint:** `/updateProject`
- **Method:** PUT
- **Description:** Updates an existing project's details.

#### 4. **Delete Project**
- **Endpoint:** `/deleteProject`
- **Method:** DELETE
- **Description:** Deletes a project by its ID.

---

### Skill Endpoints

#### 1. **Add Skill**
- **Endpoint:** `/addSkill`
- **Method:** POST
- **Description:** Adds a new skill to the portfolio.

#### 2. **Get All Skills**
- **Endpoint:** `/getAllSkills`
- **Method:** GET
- **Description:** Retrieves all skills from the portfolio.

#### 3. **Update Skill**
- **Endpoint:** `/updateSkill`
- **Method:** PUT
- **Description:** Updates an existing skill's details.

#### 4. **Delete Skill**
- **Endpoint:** `/deleteSkill`
- **Method:** DELETE
- **Description:** Deletes a skill by its ID.

---

### Education Endpoints

#### 1. **Add Education**
- **Endpoint:** `/addEducation`
- **Method:** POST
- **Description:** Adds education details to the portfolio.

#### 2. **Get All Education**
- **Endpoint:** `/getAllEducation`
- **Method:** GET
- **Description:** Retrieves all education details from the portfolio.

#### 3. **Update Education**
- **Endpoint:** `/updateEducation`
- **Method:** PUT
- **Description:** Updates an existing education detail.

#### 4. **Delete Education**
- **Endpoint:** `/deleteEducation`
- **Method:** DELETE
- **Description:** Deletes education details by its ID.

---

### Experience Endpoints

#### 1. **Add Experience**
- **Endpoint:** `/addExperience`
- **Method:** POST
- **Description:** Adds a work experience to the portfolio.

#### 2. **Get All Experience**
- **Endpoint:** `/getAllExperience`
- **Method:** GET
- **Description:** Retrieves all work experience details from the portfolio.

#### 3. **Update Experience**
- **Endpoint:** `/updateExperience`
- **Method:** PUT
- **Description:** Updates an existing work experience detail.

#### 4. **Delete Experience**
- **Endpoint:** `/deleteExperience`
- **Method:** DELETE
- **Description:** Deletes work experience details by its ID.

---

## 🚀 Installation and Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/hikmataliyev059/internIntelligence-portfolio-api.git
