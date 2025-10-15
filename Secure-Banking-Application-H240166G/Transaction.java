public class Transaction {
    private String accountNumber;
    private String type;
    private double amount;

    public Transaction(String accountNumber, String type, double amount) {
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return accountNumber + "," + type + "," + amount;
    }
}