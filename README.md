# **Task Management System**
*RESTful API to facilitate creation and assignment of tasks to users*

![Java](https://img.shields.io/badge/Java-24-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.5-green)
![MySql](https://img.shields.io/badge/MySQL-15-green)
![Docker](https://img.shields.io/badge/Docker-✓-lightblue)


---
### **Get API Documentation Here: https://documenter.getpostman.com/view/45398599/2sB2qfBKRE**

## **Features**
- **User Creation**
  -View user assigned tasks
  -Perform CRUD operations on User


- **Task Creation**
  - Add task and assign a user to task
  - Perform CRUD operations on task


### **1. Build Docker Image**
```bash
docker compose build
```

### **2. Run Containers**
```bash
docker compose up
```

### **3. Build & Run containers**
```bash
docker compose up --build
```

### **🔒 Environment Variables**
| Key        | Purpose           |  
|------------|-------------------|  
| `DATABASE` | Database name     |  
| `USER`     | Database user     |  
| `PASSWORD` | Database password |  
