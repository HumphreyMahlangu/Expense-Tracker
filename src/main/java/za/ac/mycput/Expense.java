package za.ac.mycput;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;


public class Expense {
    private static int nextID = 1;
    private  double amount;
    private String description;
    private LocalDate date;
    private int id;

    public Expense(double amount, String description) {
        this.amount = amount;
        this.description = description;
        this.date = LocalDate.now();
        this.id = nextID++;
    }

    public Expense(int id, LocalDate date, String description, double amount) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.amount = amount;
    }

    public static void setNextId(int id) {
        nextID = id;
    }

    public String toCsvString() {
        return id + "," + date + "," + description + "," + amount;
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return  "Expense [amount=" + amount + ", description=" + description + ", date=" + date + ", id=" + id + "]";
    }
}
