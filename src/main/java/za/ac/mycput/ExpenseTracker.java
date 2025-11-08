package za.ac.mycput;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import  java.util.Date;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.InputMismatchException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ExpenseTracker {
    private static List<Expense> expense = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static final String FILE_NAME = "expenses.csv";
    public static void main(String[] args) {
        System.out.println("DEBUG: Saving file in this folder: " + new java.io.File(".").getAbsolutePath());

        loadExpensesFromFile();

        while (true) {
            printMenu();
            int choice = getIntegerInput();

            switch (choice) {
                case 1:
                    addExpense();
                    break;
                case 2:
                    updateExpense();
                    break;
                case 3:
                    deleteExpense();
                    break;
                case 4:
                    viewAllExpense();
                    break;
                case 5:
                    viewTotalSummary();
                    break;
                case 6:
                    viewMonthlySummary();
                    break;
                case 7:
                    System.out.println("Bye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Wrong choice!Please try again(1 - 7)");
            }
            System.out.println();
        }
    }

    private static void printMenu(){
        System.out.println("1. Add Expense");
        System.out.println("2. Update Expense");
        System.out.println("3. Delete Expense");
        System.out.println("4. View All Expense");
        System.out.println("5. View Monthly Expense");
        System.out.println("6. View Total Expense");
        System.out.println("7. Exit");
    }
    private static void addExpense(){
        System.out.print("Enter amount: ");
        double amount = getDoubleInput();
        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        Expense newExpense = new Expense(amount, description);
        expense.add(newExpense);

        saveExpensesToFile();

        System.out.println("Expense added successfully(ID: " + newExpense.getId() + ")");
    }

    private static void updateExpense(){
        if(expense.isEmpty()){
            System.out.println("Expense is empty!");
            return;
        }
        viewAllExpense();
        System.out.println("Enter ID of the expense you want to update: ");
        int id = getIntegerInput();

        Expense  updatedExpense = findExpenseById(id);

        if (updatedExpense == null){
            System.out.println("Expense not found!");
        }else {
            System.out.println("Enter new description (current: " + updatedExpense.getDescription() + "): ");
            String newDescription = scanner.nextLine();

            System.out.print("Enter new amount (current: " + updatedExpense.getAmount() + "): ");
            System.out.print("Enter amount: ");
            double newAmount = getDoubleInput();
            updatedExpense.setDescription(newDescription);
            updatedExpense.setAmount(newAmount);

            System.out.println("Expense updated successfully");
        }
    }

    private static void deleteExpense(){
        if(expense.isEmpty()){
            System.out.println("Expense is empty!");
            return;
        }
        viewAllExpense();
        System.out.println("Enter ID of the expense you want to delete: ");
        int id = getIntegerInput();

        Expense expenseToDelete = findExpenseById(id);

        if(expenseToDelete == null){
            System.out.println("Expense not found!");
        }else {
            expense.remove(expenseToDelete);
            saveExpensesToFile();
            System.out.println("Expense deleted successfully");
        }
    }

    private static void viewAllExpense(){
        if(expense.isEmpty()){
            System.out.println("Expense is empty!");
        }else {
            System.out.println("All expenses");
            for (Expense expense : expense) {
                System.out.println(expense);
            }
            System.out.println();
        }
    }
    private static void viewTotalSummary() {
        double total = 0;
        for (Expense exp : expense) {
            total += exp.getAmount();
        }

        System.out.println(" Total Expense Summary ");
        System.out.println("Total number of expenses: " + expense.size());
        System.out.printf("Total amount spent: " + total);
    }
    public static void viewMonthlySummary(){
        System.out.println("Enter month (1 for jan 12 for Dec): ");
        int month = getIntegerInput();

        if(month < 1 || month > 12){
            System.out.println("Invalid month");
            return;
        }
        int currentYear = LocalDate.now().getYear();
        double monthlyTotal = 0;

        System.out.println("Expenses for month " + month  + "/" + currentYear);
        boolean found = false;
        for (Expense expense : expense) {
            if (expense.getDate().getYear() == currentYear && expense.getDate().getMonthValue() == month) {
                System.out.println(expense);
                monthlyTotal += expense.getAmount();
                found = true;
            }
        }
        if(!found){
            System.out.println("No expenses found");
        }else {
            System.out.println("Total for month " + month +":" + monthlyTotal);
        }
    }

    private static Expense findExpenseById(int id) {
        for (Expense exp : expense) {
            if (exp.getId() == id) {
                return exp;
            }
        }
        return null;
    }

    private static void saveExpensesToFile() {
        System.out.println("DEBUG: Attempting to save to file: " + FILE_NAME);
        try (FileWriter fw = new FileWriter(FILE_NAME);
             PrintWriter pw = new PrintWriter(fw)) {


            for (Expense exp : expense) {
                pw.println(exp.toCsvString());
            }
            System.out.println("DEBUG: Successfully saved expenses.");

        } catch (IOException e) {
            System.out.println("Error: Could not save expenses to file: " + e.getMessage());
        }
    }


    private static void loadExpensesFromFile() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return;
        }

        int maxId = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 4) {
                    try {
                        int id = Integer.parseInt(values[0]);
                        LocalDate date = LocalDate.parse(values[1]);
                        String description = values[2];
                        double amount = Double.parseDouble(values[3]);

                        Expense loadedExpense = new Expense(id, date, description, amount);
                        expense.add(loadedExpense);

                        if (id > maxId) {
                            maxId = id;
                        }

                    } catch (NumberFormatException | DateTimeParseException e) {
                        e.printStackTrace();

                    }
                } else {
                    System.out.println("DEkipping.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Expense.setNextId(maxId + 1);
    }


    private static double getDoubleInput() {
        while (true) {
            try {
                double amount = scanner.nextDouble();
                scanner.nextLine();
                if (amount < 0) {
                    System.out.println("Amount cannot be negative. Please re-enter:");
                } else {
                    return amount;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number:");
                scanner.nextLine();
            }
        }
    }


    private static int getIntegerInput() {
        while (true) {
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                return choice;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number:");
                scanner.nextLine();
            }
        }
    }
}

