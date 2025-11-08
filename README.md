# Java Command-Line Expense Tracker

This is a simple, command-line (CLI) application built with Java that allows users to track their daily expenses. It provides basic CRUD (Create, Read, Update, Delete) functionality and saves all data to a local `expenses.csv` file, so your data is persistent between sessions.

This project was built to practice core Java concepts, including object-oriented programming (OOP), file I/O, error handling, and collections.

---

##  Features

* **Add Expense:** Add a new expense with a description and amount.
* **Update Expense:** Modify the description and amount of an existing expense using its unique ID.
* **Delete Expense:** Remove an expense from the tracker using its ID.
* **View All Expenses:** See a complete list of all saved expenses, showing ID, date, description, and amount.
* **View Total Summary:** Get a summary of the total number of expenses and the total amount spent.
* **View Monthly Summary:** View a list and total for all expenses recorded in a specific month of the current year.
* **Data Persistence:** All expenses are automatically saved to `expenses.csv` on add, update, or delete, and are reloaded when the app starts.

---

## Tech Stack

* **Java**: The core programming language.
* **Maven**: Used for project management and dependencies (if any).

---

##  How to Run

### Prerequisites

* Java Development Kit (JDK) 11 or newer.
* Apache Maven (to build the project).

### Running the Application

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/HumphreyMahlangu/Expense-Tracker.git](https://github.com/HumphreyMahlangu/Expense-Tracker.git)
    cd Expense-Tracker
    ```

2.  **Compile the project with Maven:**
    ```bash
    mvn clean compile
    ```

3.  **Run the application:**
    (Note: Replace `za.ac.mycput.ExpenseTracker` with your package name and main class if it's different.)
    ```bash
    mvn exec:java -Dexec.mainClass="za.ac.mycput.ExpenseTracker"
    ```

---

##  File Structure

* `src/main/java/za/ac/mycput/`: Contains the `Expense.java` (model) and `ExpenseTracker.java` (main application) files.
* `pom.xml`: The Maven project configuration file.
* `expenses.csv`: This file is **auto-generated** in the project's root directory when you add your first expense. It is used to store all expense data.