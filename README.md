# IITBOMBAY Courses API - Backend

A robust Spring Boot REST API for managing IITBOMBAY courses and course delivery instances. This backend provides comprehensive CRUD operations for courses with prerequisite relationships and course delivery scheduling.

## 🚀 Features

### Course Management
- **CRUD Operations**: Full Create, Read, Update, Delete operations for courses
- **Prerequisites System**: Many-to-many relationship between courses for prerequisite management
- **Validation**: Comprehensive input validation and business logic validation
- **Search & Filter**: Advanced search and filtering capabilities
- **Dependency Protection**: Prevents deletion of courses that are prerequisites for others

### Course Delivery Instances
- **Instance Management**: Create and manage course delivery instances for specific years/semesters
- **Temporal Organization**: Organize courses by academic year and semester
- **Instance Validation**: Ensures proper course instance creation and deletion

### API Features
- **RESTful Design**: Standard REST API endpoints
- **CORS Support**: Cross-origin resource sharing enabled
- **Error Handling**: Comprehensive error responses with appropriate HTTP status codes
- **Data Validation**: Input validation using Bean Validation annotations
- **Database Integration**: JPA/Hibernate with H2 in-memory database

## 🛠️ Technology Stack

- **Spring Boot 2.7.18**: Main framework
- **Spring Data JPA**: Data access layer
- **Hibernate**: ORM framework
- **H2 Database**: In-memory database for development
- **Maven**: Build tool and dependency management
- **Java 24+**: Programming language
- **Bean Validation**: Input validation framework

## 📋 Prerequisites

Before running this application, make sure you have:

- **Java 24** or higher
- **Maven 3.6+**
- **IDE** (IntelliJ IDEA, Eclipse, or VS Code recommended)

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd IITBOMBAY-Courses-API/backend
```

### 2. Set Environment Variables

**For Windows (PowerShell):**
```powershell
$env:JAVA_HOME = "C:\Program Files\Java\jdk-24"
$env:PATH += ";C:\path\to\apache-maven-3.9.10\bin"
```

**For Windows (Command Prompt):**
```cmd
set JAVA_HOME=C:\Program Files\Java\jdk-24
set PATH=%PATH%;C:\path\to\apache-maven-3.9.10\bin
```

**For Linux/Mac:**
```bash
export JAVA_HOME=/path/to/your/jdk-24
export PATH=$PATH:/path/to/apache-maven-3.9.10/bin
```

### 3. Build the Project

```bash
mvn clean install
```

### 4. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`.

### 5. Access the API

- **API Base URL**: `http://localhost:8080/api`
- **H2 Console**: `http://localhost:8080/h2-console` (for database inspection)
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: (leave empty)

## 📁 Project Structure

```
src/main/java/com/iitbombay/coursesapi/
├── controller/           # REST controllers
│   ├── CourseController.java
│   └── CourseDeliveryController.java
├── model/               # Entity classes
│   ├── Course.java
│   └── CourseDelivery.java
├── repository/          # Data access layer
│   ├── CourseRepository.java
│   └── CourseDeliveryRepository.java
├── service/             # Business logic layer
│   ├── CourseService.java
│   └── CourseDeliveryService.java
├── DataInitializer.java # Sample data initialization
└── CoursesApiApplication.java # Main application class

src/main/resources/
├── application.properties # Application configuration
└── data.sql              # Database initialization (if needed)
```

## 🔧 Configuration

### Application Properties

The main configuration is in `src/main/resources/application.properties`:

```properties
# Server Configuration
server.port=8080

# Database Configuration
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JPA Configuration
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# CORS Configuration
spring.web.cors.allowed-origins=*
spring.web.cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS
spring.web.cors.allowed-headers=*
```

## 📚 API Documentation

### Course Endpoints

#### Get All Courses
```http
GET /api/courses
```
**Response**: List of all courses with prerequisites
```json
[
  {
    "id": 1,
    "courseCode": "CS101",
    "courseName": "Introduction to Computer Science",
    "department": "Computer Science",
    "instructor": "Dr. John Doe",
    "credits": 3,
    "semester": "Fall",
    "description": "Basic computer science concepts",
    "maxStudents": 50,
    "enrolledStudents": 0,
    "courseType": "Core",
    "prerequisites": []
  }
]
```

#### Get Course by ID
```http
GET /api/courses/{id}
```
**Response**: Course details with prerequisites

#### Create Course
```http
POST /api/courses
Content-Type: application/json

{
  "courseCode": "CS101",
  "courseName": "Introduction to Computer Science",
  "department": "Computer Science",
  "instructor": "Dr. John Doe",
  "credits": 3,
  "semester": "Fall",
  "description": "Basic computer science concepts",
  "maxStudents": 50,
  "courseType": "Core",
  "prerequisiteIds": [1, 2]
}
```
**Response**: Created course with ID
**Status Codes**:
- `201 Created` - Course created successfully
- `400 Bad Request` - Invalid input or prerequisite validation failed

#### Delete Course
```http
DELETE /api/courses/{id}
```
**Response**: 
- `200 OK` - Course deleted successfully
- `409 Conflict` - Course cannot be deleted (is a prerequisite for others)

#### Search Courses by Name
```http
GET /api/courses/search/name?keyword=computer
```

#### Get Courses by Department
```http
GET /api/courses/department/{department}
```

#### Get Courses by Semester
```http
GET /api/courses/semester/{semester}
```

### Course Delivery Instance Endpoints

#### Create Instance
```http
POST /api/instances
Content-Type: application/json

{
  "year": 2024,
  "semester": 1,
  "course": { "id": 1 }
}
```
**Response**: Created instance with course details

#### Get Instances by Year and Semester
```http
GET /api/instances/{year}/{semester}
```
**Response**: List of course instances for the specified year and semester

#### Get Specific Instance
```http
GET /api/instances/{year}/{semester}/{courseId}
```
**Response**: Specific course instance details

#### Delete Instance
```http
DELETE /api/instances/{year}/{semester}/{courseId}
```
**Response**: `200 OK` - Instance deleted successfully

## 🗄️ Database Schema

### Course Entity
```sql
CREATE TABLE courses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    course_code VARCHAR(255) UNIQUE NOT NULL,
    course_name VARCHAR(255) NOT NULL,
    department VARCHAR(255) NOT NULL,
    instructor VARCHAR(255) NOT NULL,
    credits INTEGER NOT NULL,
    semester VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    max_students INTEGER,
    enrolled_students INTEGER DEFAULT 0,
    course_type VARCHAR(255)
);
```

### Prerequisites Relationship
```sql
CREATE TABLE course_prerequisites (
    course_id BIGINT,
    prerequisite_id BIGINT,
    PRIMARY KEY (course_id, prerequisite_id),
    FOREIGN KEY (course_id) REFERENCES courses(id),
    FOREIGN KEY (prerequisite_id) REFERENCES courses(id)
);
```

### Course Delivery Entity
```sql
CREATE TABLE course_deliveries (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    year INTEGER NOT NULL,
    semester INTEGER NOT NULL,
    course_id BIGINT NOT NULL,
    FOREIGN KEY (course_id) REFERENCES courses(id)
);
```

## 🔍 Sample Data

The application includes sample data initialization in `DataInitializer.java`:

- Sample courses from various departments (Computer Science, Mathematics, Physics, etc.)
- Prerequisite relationships between courses
- Course delivery instances for different years/semesters

## 🐛 Error Handling

### HTTP Status Codes

- `200 OK` - Successful operation
- `201 Created` - Resource created successfully
- `400 Bad Request` - Invalid input data or validation failed
- `404 Not Found` - Resource not found
- `409 Conflict` - Business rule violation (e.g., cannot delete prerequisite)
- `500 Internal Server Error` - Server error

### Error Response Format
```json
{
  "timestamp": "2024-01-01T12:00:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid prerequisite course IDs: 999",
  "path": "/api/courses"
}
```

## 🧪 Testing

### Run Tests
```bash
mvn test
```

### Integration Tests
```bash
mvn verify
```

## 🚀 Deployment

### Build JAR
```bash
mvn clean package
```

### Run JAR
```bash
java -jar target/courses-api-0.0.1-SNAPSHOT.jar
```

### Production Configuration

For production deployment, update `application.properties`:

```properties
# Production Database (e.g., PostgreSQL)
spring.datasource.url=jdbc:postgresql://localhost:5432/courses_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect

# Disable H2 Console
spring.h2.console.enabled=false

# Production CORS
spring.web.cors.allowed-origins=https://your-frontend-domain.com
```

## 🔒 Security Considerations

- Input validation on all endpoints
- SQL injection protection through JPA
- CORS configuration for frontend integration
- Business logic validation for data integrity
- Prerequisite validation to prevent circular dependencies

## 🐛 Troubleshooting

### Common Issues

1. **Maven not found**
   - Ensure Maven is installed and added to PATH
   - Verify JAVA_HOME is set correctly
   - Try running with full path: `C:\path\to\maven\bin\mvn.cmd`

2. **Port already in use**
   - Change port in `application.properties`: `server.port=8081`
   - Or kill the process using port 8080

3. **Database connection issues**
   - H2 database is in-memory, so data is lost on restart
   - Check H2 console at `http://localhost:8080/h2-console`

4. **CORS issues with frontend**
   - Ensure CORS is configured in `application.properties`
   - Check that frontend is running on the correct port

### Development Tips

- Use H2 console to inspect database during development
- Enable SQL logging in `application.properties`
- Check application logs for detailed error messages

## 📝 Development Guidelines

### Code Style
- Follow Java naming conventions
- Use meaningful variable and method names
- Add comments for complex business logic
- Implement proper exception handling

### API Design
- Use RESTful conventions
- Return appropriate HTTP status codes
- Provide meaningful error messages
- Use consistent response formats

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Ensure all tests pass
6. Submit a pull request

## 📄 License

This project is part of the IITBOMBAY Courses API system.

## 🔗 Related

- [Frontend Documentation](../frontend/README.md)
- [API Testing Guide](./docs/api-testing.md)
- [Database Migration Guide](./docs/migration.md)

## 🔄 Version Control & .gitignore

- **Do NOT commit `target/` or `.jar` files** (build artifacts) to GitHub.
- Use a `.gitignore` file to exclude:
  - `target/`
  - `*.jar`
  - `.env`, `local.properties`, or any file with secrets
  - IDE/system files (e.g., `.idea/`, `.vscode/`, `.DS_Store`)

Example `.gitignore`:
```
target/
*.jar
*.class
*.log
*.iml
.idea/
.vscode/
.DS_Store
.env
local.properties
``` 