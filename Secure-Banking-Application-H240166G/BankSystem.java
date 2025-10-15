import java.io.*;
import java.security.MessageDigest;
import java.util.*;

public class BankSystem {
    private Map<String, User> users = new HashMap<>();
    private Map<String, Account> accounts = new HashMap<>();
    private List<Transaction> transactions = new ArrayList<>();

    public BankSystem() {
        loadUsers();
        loadAccounts();
        loadTransactions();
    }

    public boolean login(String username, String password) {
        User user = users.get(username);
        if (user == null) return false;
        return user.getPasswordHash().equals(hash(password));
    }

    public boolean register(String username, String password) {
        if (users.containsKey(username)) return false;
        User user = new User(username, hash(password));
        users.put(username, user);
        saveUsers();
        return true;
    }

    public Account createAccount(String username) {
        String accNum = UUID.randomUUID().toString();
        Account account = new Account(accNum, username, 0.0);
        accounts.put(accNum, account);
        saveAccounts();
        return account;
    }

    public boolean deposit(String accNum, double amount) {
        Account account = accounts.get(accNum);
        if (account == null || amount <= 0) return false;
        account.setBalance(account.getBalance() + amount);
        transactions.add(new Transaction(accNum, "Deposit", amount));
        saveAccounts();
        saveTransactions();
        return true;
    }

    public boolean withdraw(String accNum, double amount) {
        Account account = accounts.get(accNum);
        if (account == null || amount <= 0 || account.getBalance() < amount) return false;
        account.setBalance(account.getBalance() - amount);
        transactions.add(new Transaction(accNum, "Withdraw", amount));
        saveAccounts();
        saveTransactions();
        return true;
    }

    private String hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }

    private void loadUsers() {
        try (BufferedReader br = new BufferedReader(new FileReader("data/users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                users.put(parts[0], new User(parts[0], parts[1]));
            }
        } catch (IOException ignored) {}
    }

    private void saveUsers() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/users.txt"))) {
            for (User u : users.values()) {
                bw.write(u.getUsername() + "," + u.getPasswordHash());
                bw.newLine();
            }
        } catch (IOException ignored) {}
    }

    private void loadAccounts() {
        try (BufferedReader br = new BufferedReader(new FileReader("data/accounts.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String accNum = parts[0];
                String owner = parts[1];
                double balance = Double.parseDouble(parts[2]);
                accounts.put(accNum, new Account(accNum, owner, balance));
            }
        } catch (IOException ignored) {}
    }

    private void saveAccounts() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/accounts.txt"))) {
            for (Account a : accounts.values()) {
                bw.write(a.getAccountNumber() + "," + a.getOwner() + "," + a.getBalance());
                bw.newLine();
            }
        } catch (IOException ignored) {}
    }

    private void loadTransactions() {
        try (BufferedReader br = new BufferedReader(new FileReader("data/transactions.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                transactions.add(new Transaction(parts[0], parts[1], Double.parseDouble(parts[2])));
            }
        } catch (IOException ignored) {}
    }

    private void saveTransactions() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/transactions.txt"))) {
            for (Transaction t : transactions) {
                bw.write(t.toString());
                bw.newLine();
            }
        } catch (IOException ignored) {}
    }
}