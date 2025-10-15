import java.util.Scanner;

public class Main {
    public static class ConsoleColors {
        public static final String RESET = "\u001B[0m";
        public static final String RED = "\u001B[31m";
        public static final String GREEN = "\u001B[32m";
        public static final String YELLOW = "\u001B[33m";
        public static final String BLUE = "\u001B[34m";
        public static final String PURPLE = "\u001B[35m";
        public static final String CYAN = "\u001B[36m";
        public static final String WHITE = "\u001B[37m";
    }

    public static void main(String[] args) {
        BankSystem bank = new BankSystem();
        Scanner sc = new Scanner(System.in);

        System.out.println(ConsoleColors.CYAN + "╔══════════════════════════════════════╗");
        System.out.println("║      Welcome to SecureBankApp!      ║");
        System.out.println("╚══════════════════════════════════════╝" + ConsoleColors.RESET);

        String username = "";
        String password = "";
        boolean loggedIn = false;

        while (!loggedIn) {
            System.out.print(ConsoleColors.YELLOW + "\nLogin or Register (L/R): " + ConsoleColors.RESET);
            String choice = sc.nextLine().toUpperCase();

            if (choice.equals("R")) {
                System.out.print(ConsoleColors.BLUE + "👤 Enter username: " + ConsoleColors.RESET);
                username = sc.nextLine();
                System.out.print(ConsoleColors.GREEN + "🔒 Enter password: " + ConsoleColors.RESET);
                password = sc.nextLine();

                if (bank.register(username, password)) {
                    System.out.println(ConsoleColors.GREEN + "✅ Registration successful!" + ConsoleColors.RESET);
                } else {
                    System.out.println(ConsoleColors.RED + "❌ Registration failed. Try again." + ConsoleColors.RESET);
                    continue;
                }
            }

            System.out.print(ConsoleColors.BLUE + "\n👤 Enter username: " + ConsoleColors.RESET);
            username = sc.nextLine();
            System.out.print(ConsoleColors.GREEN + "🔒 Enter password: " + ConsoleColors.RESET);
            password = sc.nextLine();

            if (bank.login(username, password)) {
                System.out.println(ConsoleColors.GREEN + "✅ Login successful!" + ConsoleColors.RESET);
                loggedIn = true;
            } else {
                System.out.println(ConsoleColors.RED + "❌ Invalid credentials." + ConsoleColors.RESET);
                System.out.print(ConsoleColors.YELLOW + "Would you like to Register (R) or Exit (E)? " + ConsoleColors.RESET);
                String nextStep = sc.nextLine().toUpperCase();
                if (nextStep.equals("E")) {
                    System.out.println(ConsoleColors.CYAN + "👋 Goodbye!" + ConsoleColors.RESET);
                    sc.close();
                    return;
                }
            }
        }

        Account acc = bank.createAccount(username);
        System.out.println(ConsoleColors.PURPLE + "\n🎉 New account created! Account Number: " + acc.getAccountNumber() + ConsoleColors.RESET);

        while (true) {
            System.out.println(ConsoleColors.YELLOW + "\n📋 Menu:");
            System.out.println("1️⃣ View Balance");
            System.out.println("2️⃣ Deposit");
            System.out.println("3️⃣ Withdraw");
            System.out.println("4️⃣ Exit" + ConsoleColors.RESET);
            System.out.print(ConsoleColors.BLUE + "Choose an option: " + ConsoleColors.RESET);
            String opt = sc.nextLine();

            switch (opt) {
                case "1":
                    System.out.println(ConsoleColors.CYAN + "💰 Current Balance: $" + acc.getBalance() + ConsoleColors.RESET);
                    break;
                case "2":
                    System.out.print(ConsoleColors.YELLOW + "💵 Enter amount to deposit: " + ConsoleColors.RESET);
                    double dep = Double.parseDouble(sc.nextLine());
                    bank.deposit(acc.getAccountNumber(), dep);
                    System.out.println(ConsoleColors.GREEN + "✅ Deposited successfully!" + ConsoleColors.RESET);
                    break;
                case "3":
                    System.out.print(ConsoleColors.YELLOW + "💸 Enter amount to withdraw: " + ConsoleColors.RESET);
                    double wd = Double.parseDouble(sc.nextLine());
                    if (bank.withdraw(acc.getAccountNumber(), wd)) {
                        System.out.println(ConsoleColors.GREEN + "✅ Withdrawal successful!" + ConsoleColors.RESET);
                    } else {
                        System.out.println(ConsoleColors.RED + "❌ Insufficient balance." + ConsoleColors.RESET);
                    }
                    break;
                case "4":
                    System.out.println(ConsoleColors.CYAN + "👋 Thank you for using SecureBankApp. Goodbye!" + ConsoleColors.RESET);
                    sc.close();
                    return;
                default:
                    System.out.println(ConsoleColors.RED + "❌ Invalid option. Please try again." + ConsoleColors.RESET);
            }
        }
    }
}