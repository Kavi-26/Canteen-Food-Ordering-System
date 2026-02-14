# College Canteen Food Ordering System

## Overview
A mobile application for a college canteen that allows students to order food, verify with OTP, and staff to manage orders.

## Tech Stack
-   **Frontend**: Android (Java)
-   **Backend**: Java Spring Boot
-   **Database**: MySQL

## Prerequisites
-   **Java JDK 17+**: Required for both Backend and Android build.
-   **MySQL Server**: Must be installed and running.
-   **Android Studio**: Required to run the mobile app.
-   **IntelliJ IDEA (Recommended)**: To run the Spring Boot backend easily (includes Maven).

## Setup Instructions

### 1. Database Setup
1.  Open your terminal or command prompt.
2.  Login to MySQL:
    ```bash
    mysql -u root -p
    # Enter password: kavi2002
    ```
3.  Enter your password (`kavi2002`).
4.  Create the database:
    ```sql
    CREATE DATABASE college_canteen;
    EXIT;
    ```
5.  *Note*: The backend will automatically create tables and seed initial data when it starts.

### 2. Backend Setup
1.  Open the `CollegeCanteen/backend` folder in **IntelliJ IDEA** (or Eclipse).
    -   *If using CLI and you have Maven installed*: Run `mvn spring-boot:run` in the `backend` folder.
2.  Open `src/main/resources/application.properties`.
3.  Update the MySQL username and password if different from `root` / (empty).
4.  In IntelliJ, find `CollegeCanteenBackendApplication.java`, right-click, and select **Run 'CollegeCanteenBackendApplication'**.
5.  The server should start on `http://localhost:8080`.

### 3. Android App Setup
1.  Open **Android Studio**.
2.  Select **Open** and choose the `CollegeCanteen` folder.
3.  Let Gradle sync complete.
4.  Open `app/src/main/java/com/collegecanteen/network/RetrofitClient.java`.
5.  Check `BASE_URL`:
    -   Use `http://10.0.2.2:8080/` if testing on Android Emulator.
    -   Use your PC's IP address (e.g., `http://192.168.1.5:8080/`) if testing on a physical device.
6.  Connect an Android device or start an Emulator.
7.  Click the Green **Run** arrow (Shift+F10).

## Features
-   **User**: Register, Login, Browse Menu, Add to Cart, Place Order (with OTP), Verify OTP.
-   **Staff**: Login (Email: `staff@college.edu`, Pass: `admin`), View Orders, Update Status (Preparing/Ready/Completed).

## Demo Credentials
-   **Student**: Register a new user in the app.
-   **Staff**: `staff@college.edu` / `admin`
