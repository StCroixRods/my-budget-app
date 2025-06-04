# Budget Tracker Application (READ ME MADE BY AI FOR CLEAR WRITING)

## Description

A web application built using Java and Spring Boot that allows users to track personal expenses. Users can add, view, edit, and delete their expenses, categorize them, and view a summary page with various financial metrics and a visual breakdown of spending by category.

## Features

* **Expense Management (CRUD):**
    * Add new expenses with description, amount, date, and category.
    * View a paginated and sortable list of all recorded expenses.
    * Edit existing expenses.
    * Delete expenses.
* **Expense Categorization:**
    * Expenses are categorized using a predefined list (e.g., Food, Utilities, Transportation).
    * Category selection via a dropdown menu on forms.
* **Summary Dashboard:**
    * Displays key financial metrics, including:
        * Total spending for the current month.
        * Total spending of all time.
        * Number of expenses recorded this month.
        * Total spending from the previous month.
        * Average daily spending for the current month.
        * Percentage change in spending compared to the last month (with color coding for increase/decrease).
    * Interactive pie chart showing the breakdown of expenses by category for the current month.
* **User Interface & Experience:**
    * Responsive web design using Bootstrap 5.
    * Consistent navigation bar and footer across all pages (using Thymeleaf fragments).
    * Styled tables for displaying expense lists.
    * User-friendly forms for adding and editing expenses.
    * Sortable columns (Date, Amount) on the main expense list.
    * Server-side form validation with clear error messages displayed to the user.
* **Data Persistence:**
    * Expense data is stored persistently using an H2 file-based database.

## Technologies Used

* **Backend:**
    * Java 17
    * Spring Boot 3.x
        * Spring MVC (for web controllers)
        * Spring Data JPA (for database interaction)
        * Spring Validation (for form validation)
        * Spring Boot DevTools
    * H2 Database (file-based)
* **Frontend:**
    * Thymeleaf (server-side template engine)
    * HTML5
    * CSS3
    * Bootstrap 5
    * JavaScript (for Chart.js integration)
* **Charting:**
    * Chart.js
* **Build Tool:**
    * Apache Maven
* **Version Control:**
    * Git & GitHub

## Setup and How to Run Locally

1.  **Prerequisites:**
    * Java JDK 17 or higher installed.
    * Apache Maven installed.
    * Git installed.
2.  **Clone the Repository:**
    ```bash
    git clone [https://github.com/StCroixRods/my-budget-app.git](https://github.com/StCroixRods/my-budget-app.git)
    ```
    *(Replace with your actual GitHub repository URL)*
3.  **Navigate to the Project Directory:**
    ```bash
    cd my-budget-app
    ```
4.  **Run the Application:**
    * Using Maven:
        ```bash
        mvn spring-boot:run
        ```
    * Alternatively, import the project as a Maven project into your IDE (like IntelliJ IDEA) and run the `BudgetTrackerApplication` main class.
5.  **Access the Application:**
    * Open your web browser and go to: `http://localhost:8080`
    * (The default port is 8080. If you've configured a different one, use that.)
6.  **Database:**
    * The application uses an H2 file-based database. The database file (`budgettracker.mv.db` or similar) will be created in a `/data` directory within the project's root folder upon first run and data entry.
    * The H2 console can be accessed at `http://localhost:8080/h2-console` (JDBC URL should be `jdbc:h2:file:./data/budgettracker`, username `sa`, no password).

## Screenshots

* Example: Main Expense List Page
* Example: Add Expense Form with Validation
* Example: Summary Page with Metrics and Pie Chart

## Future Enhancements (Optional)

* User authentication and authorization.
* More detailed reporting and filtering options.
* Ability to define custom budget goals per category.
* Switch to a production-grade database like PostgreSQL or MySQL.
* Unit and integration tests.
