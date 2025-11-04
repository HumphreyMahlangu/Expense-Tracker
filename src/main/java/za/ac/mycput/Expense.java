package za.ac.mycput;
import java.time.LocalDate;


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
