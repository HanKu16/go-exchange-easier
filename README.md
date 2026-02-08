# Go Exchange Easier 🚀

![Java](https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=gitea&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat-square&logo=spring-boot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=flat-square&logo=postgresql&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?style=flat-square&logo=redis&logoColor=white)
![Amazon S3](https://img.shields.io/badge/Amazon%20S3-569A31?style=flat-square&logo=data:image/svg%2Bxml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAyNCAyNCIgZmlsbD0id2hpdGUiPjxwYXRoIGQ9Ik0xOS4zNSAxMC4wNEMxOC42NyA2LjU5IDE1LjY0IDQgMTIgNCA5LjExIDQgNi42IDUuNjQgNS4zNSA4LjA0IDIuMzQgOC4zNiAwIDEwLjkxIDAgMTRjMCAzLjMxIDIuNjkgNiA2IDZoMTNjMi43NiAwIDUtMi4yNCA1LTUgMC0yLjY0LTIuMDUtNC43OC00LjY1LTQuOTZ6Ii8+PC9zdmc+)
![TypeScript](https://img.shields.io/badge/TypeScript-3178C6?style=flat-square&logo=typescript&logoColor=white)
![React](https://img.shields.io/badge/React-20232A?style=flat-square&logo=react&logoColor=61DAFB)
![MUI](https://img.shields.io/badge/MUI-007FFF?style=flat-square&logo=mui&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=docker&logoColor=white)



## 📖 About The Project

The main goal of this platform is to **centralize information** about student exchanges, acting as a dedicated alternative to fragmented social media groups. Instead of scrolling through endless posts to find relevant advice, prospective students can instantly connect with students who have already returned.

The application allows users to browse detailed **user and university profiles** and precisely filter experienced students by **country, city, university, field of study, and specific exchange period** (e.g., 2023–2025). This ensures that users gain first-hand insights from people who were actually there. The **university review system** empowers users to gather authentic opinions and choose the ideal exchange destination for their needs. Additionally, a **real-time chat feature** is currently being implemented to further facilitate direct peer-to-peer communication.



## ✨ Key Features

* **🔍 Search & Filtering**
    Allows users to find students and universities based on various criteria.

* **👤 User Profiles**
    Users can customize their profiles by adding a bio, country of origin and home university.

* **🏫 University Profiles**
    Displays basic information, allows users to read and add reviews, and provides a direct link to the official university website.

* **🤝 Social Features**
    Includes the ability to follow other users to stay updated with their activity.

* **☁️ Media & Storage**
    Image storage handles by **S3** and data caching via **Redis**.

* **💬 Chat System (In Progress)**
     A direct messaging system is currently being developed to allow instant peer-to-peer communication without leaving the platform.



## 🛠️ Tech Stack

### ⚙️ Backend
* **Java 23**
* **Spring Boot 3**
* **Spring Security**

### 🎨 Frontend
* **TypeScript**
* **React**
* **Material UI**

### 💾 Database & Storage
* **PostgreSQL**
* **Redis**
* **Amazon S3**

### 🐳 DevOps
* **Docker**
* **Docker Compose**



## ⚙️ Prerequisites

Before running the project, make sure you have the following installed:

* [Docker](https://docs.docker.com/get-docker/) (Engine & Compose)
* [Git](https://git-scm.com/downloads)



## 🚀 Installing & Running

### 1. Clone the repository
```bash
git clone https://github.com/HanKu16/go-exchange-easier
```

### 2. Navigate to the project directory
```bash
cd go-exchange-easier
```

### 3. Create a .env file based on the example provided
The default settings in .env.example are pre-configured to work out-of-the-box with the Docker environment. No manual changes are required for local development.
```bash
cp .env.example .env
```

### 4. Run the following command to build the images and start all services
```bash
docker-compose up -d --build
```

### 5. **Access the application**
With the containers running, the environment is fully initialized. The database comes **pre-seeded with sample data**, so no manual setup is required to test the application.

> **⚠️ Important:** **Authentication is required** to access the application. Apart from the **Login** and **Register** pages, all other views and features are protected. You must log in to explore the content.

* **Frontend:** [http://localhost:5173](http://localhost:5173)
    * **Login:** [http://localhost:5173/login](http://localhost:5173/login)
     * **Register:** [http://localhost:5173/register](http://localhost:5173/register)
     * **Example University Profile:** [http://localhost:5173/universities/168](http://localhost:5173/universities/168)
     * **Example User Profile:** [http://localhost:5173/users/2531](http://localhost:5173/users/2531)
     * **Search Page:** [http://localhost:5173/search](http://localhost:5173/search)

* **Backend API:** [http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)
* **Object Storage (MinIO):** [http://localhost:9001](http://localhost:9001)

#### 🔐 Demo Credentials
Use these credentials to log in as a pre-existing user without registration:
* **Email:** `DemoUser` 
* **Password:** `DemoUserPassword`

### 6. Stop the application
To stop all running containers and remove the created networks, run:
```bash
docker-compose down
```

## 📄 License & Copyright

© 2026 Jakub Pietrzykowski. All rights reserved.

This project is intended for **educational and portfolio purposes only**.

You are permitted to:
* View the code.
* Clone and run the project locally for evaluation or recruitment purposes.

You are **NOT** permitted to:
* Use this code for commercial purposes.
* Modify, distribute, or sub-license the code without prior written permission from the author.