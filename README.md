
# Clinic Management System

A **Clinic Management System** built to streamline clinic operations, including patient registration, appointment scheduling and doctor management. The project focuses on creating an efficient and user-friendly platform for healthcare providers and patients.

---

## 🚀 Features

- **Patient Management**: Register new patients, view details, and update information.
- **Appointment Scheduling**: Book, reschedule, and cancel appointments with doctors.
- **Doctor Management**: Add and manage doctors, specializations, and availability.
- **Authentication & Authorization**: Secure login for admin, doctors, and patients.
- **Reports & Analytics**: View daily, weekly, and monthly appointments for clinic operations.

---

## 🛠️ Tech Stack

- **Backend**: Spring Boot (Java)
- **Database**: MySQL (with JPA/Hibernate)
- **Tools**: IntelliJ IDEA, Postman, Git & GitHub

---

## 📂 Project Structure

```
clinic-management/
│── src/main/java/com/clinic/main   # Java source files
│   ├── config/                     # App Configurations
│   ├── controllers/                # REST controllers
│   ├── customeException/           # Custome Exception
│   ├── entity/                     # Entities (Patient, Doctor, Appointment)
│   ├── entityMapper/               # Entities Mapper(PatientMapper, DoctorMapper, AppointmentMapper)
│   ├── repository/                 # JPA repositories
│   ├── service/                    # Business logic
│── src/main/resources/             
│   ├── application.properties      # Database & app configs
│── pom.xml                         # Maven dependencies
```

---

## ⚙️ Setup & Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/KaranBuradkar/Clinic-Management.git
   cd Clinic-Management
   ```

2. Configure the database in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/clinic_management_db
   spring.datasource.username=yourusername //root
   spring.datasource.password=yourpassword
   spring.jpa.hibernate.ddl-auto=update
   ```

3. Run the project:
   ```bash
   mvn spring-boot:run
   ```

4. Access the application at:
   ```
   http://localhost:8080
   ```

---

## 🤝 Contribution

Contributions are welcome!
1. Fork the repository
2. Create a feature branch (`git checkout -b feature-name`)
3. Commit your changes (`git commit -m "Added feature"`)
4. Push to your branch (`git push origin feature-name`)
5. Create a Pull Request

---

## 📜 License

This project is licensed under the 
[MIT License](LICENSE).

---

## 👨‍💻 Author

- **Karan Buradkar**  
  [LinkedIn](https://linkedin.com/in/karan-buradkar-70361a258) | [GitHub](https://github.com/KaranBuradkar)