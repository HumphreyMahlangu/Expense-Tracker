package za.ac.mycput;
import java.time.LocalDate;
import  java.util.Date;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.InputMismatchException;

public class ExpenseTracker {
    private static List<Expense> expense = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

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


    private static double getDoubleInput() {
        while (true) {
            try {
                double amount = scanner.nextDouble();
                scanner.nextLine(); // This "consumes" the leftover newline character
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

