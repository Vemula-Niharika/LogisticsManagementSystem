# 🚚 Logistics Management System

A full-stack Logistics Management System developed to manage shipment operations, including order placement, truck allocation, and driver assignment with role-based access control.

---

## 🔗 Technologies Used
- Java  
- Spring Boot  
- Spring Security  
- JWT Authentication  
- React.js  
- PostgreSQL  

---

## 👥 User Roles

### 👨‍💼 Admin
- Add and manage addresses (Loading / Unloading)
- Register trucks and drivers
- View all orders
- Assign trucks based on capacity
- Assign drivers based on availability
- Complete orders

### 👤 User
- Register and Login
- Place orders with cargo details
- Select loading and unloading addresses
- Track order status

---

## ⚙️ Key Features

- 🔐 JWT-based Authentication and Role-Based Access Control  
- 📦 Complete Order Workflow Management  
  (Placed → Truck Assigned → Driver Assigned → Completed)  
- 🚛 Truck assignment with capacity validation  
- 👨‍✈️ Driver assignment with availability tracking  
- 🔄 Automatic resource availability update after order completion  
- ❌ Prevention of duplicate data (addresses, drivers, trucks)  
- 🛡️ Input validation and Global Exception Handling  
- 📧 Email notifications for order updates  

---

## 🔢 Highlights

- Developed **10+ REST APIs** for orders, trucks, drivers, and addresses  
- Built a system supporting **multiple users with admin control**  
- Implemented **real-time resource allocation logic**  
- Integrated frontend with backend APIs  
- Tested APIs using **Postman (10+ endpoints)**  

---

## 📁 Project Structure

LogisticsManagementSystem  
├── backend (Spring Boot)  
├── frontend (React)  

---

## ▶️ Run the Project

### Backend
- Open in Eclipse  
- Configure PostgreSQL database  
- Run Spring Boot application  

### Frontend
- Open in VS Code  
- Run:
  npm install  
  npm start  

---

## 👨‍💻 Author
Your Niharika Vemula
