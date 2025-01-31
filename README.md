# Java Enterprise Webshop

## Overview
The **Java Enterprise Webshop** is a full-stack web application designed to simulate an online shopping platform. It follows a robust **three-tier architecture** comprising a **presentation layer**, **business logic layer**, and **data access layer**. The application is built using **Java EE** and leverages **JSP, Servlets, and JDBC** for seamless interaction between the user interface and the underlying database.

## Features
- **User Authentication & Authorization**
  - Customers can register, log in, and manage their accounts.
  - Role-based access control for **customers, administrators, and warehouse staff**.
- **Shopping Cart Functionality**
  - Users can add, view, and remove items from their cart.
  - Session management ensures a smooth shopping experience.
- **Product Inventory Management**
  - Real-time stock management to ensure availability.
  - Admins can add, edit, and delete products and categories.
- **Order Processing & Transaction Management**
  - Secure and reliable order placement with transaction handling.
  - Warehouse staff can process and pack orders.
- **MVC Design Pattern**
  - Follows the **Model-View-Controller (MVC)** architecture for maintainability and scalability.

## Technologies Used
- **Backend:** Java EE (JSP, Servlets, JDBC)
- **Frontend:** HTML, CSS, JavaScript, JSP
- **Database:** MySQL (or another RDBMS with JDBC support)
- **Application Server:** Apache Tomcat
- **Version Control:** Git & GitHub

## Installation & Setup
### Prerequisites
Ensure you have the following installed:
- **Java Development Kit (JDK) 11+**
- **Apache Tomcat (v9+)**
- **MySQL (or compatible database)**
- **Maven (for dependency management)**
- **Git (for version control)**

### Steps to Run the Application
1. **Clone the repository:**
   ```sh
   git clone https://github.com/yourusername/java-enterprise-webshop.git
   cd java-enterprise-webshop
   ```
2. **Configure the database:**
   - Create a new MySQL database.
   - Update `database.properties` with the correct credentials.
3. **Build and deploy the application:**
   ```sh
   mvn clean package
   ```
   - Deploy the generated `.war` file in Tomcat's `webapps/` directory.
4. **Start the Tomcat server:**
   ```sh
   startup.sh (Linux/macOS)
   startup.bat (Windows)
   ```
5. **Access the application:**
   - Open `http://localhost:8080/java-enterprise-webshop/` in a web browser.



---
**Note:** Ensure the `database.properties` file is properly configured before running the application.

