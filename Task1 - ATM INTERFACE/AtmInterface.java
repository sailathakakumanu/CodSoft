import java.util.*;

class BankAccount {
    private double balance;

    BankAccount(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public boolean deposit(double amount) {
        if (amount >= 0) {
            balance += amount;
            return true;
        }
        return false;
    }
}

class ATM {
    private BankAccount account;
    private Scanner scanner;

    public ATM(BankAccount account, Scanner scanner) {
        this.account = account;
        this.scanner = scanner;
    }

    public void withdraw(double amount) {
        if (account.withdraw(amount)) {
            System.out.println("Withdrawal successful. Amount withdrawn: Rs." + amount);
            System.out.println("Current Balance: Rs." + account.getBalance());
        } else {
            System.out.println("Withdrawal failed. Insufficient balance.");
        }
    }

    public void deposit(double amount) {
        if (account.deposit(amount)) {
            System.out.println("Deposit successful. Amount deposited: Rs." + amount);
            System.out.println("Current Balance: Rs." + account.getBalance());
        } else {
            System.out.println("Deposit failed. Invalid deposit amount.");
        }
    }

    public void checkBalance() {
        System.out.println("Your transaction was succcessful.");
        System.out.println("Available Balance: Rs." + account.getBalance());
    }

    public void run() {
        double amount;

        while (true) {

            System.out.println("Dear Customer, Please Select Transaction: ");
            System.out.println("1. Cash Withdrawal \n2. Deposit \n3. Balance Enquiry \n4. Exit");
            System.out.println("Enter your choice: ");
            String choiceInput = scanner.nextLine();
            if (!choiceInput.matches("\\d+")) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            int choice = Integer.parseInt(choiceInput);
            
            switch (choice) {
                case 1:
                    System.out.print("Please enter amount: ");
                    amount = scanner.nextDouble();
                    withdraw(amount);
                    break;
                case 2:
                    System.out.print("Please enter amount: ");
                    amount = scanner.nextDouble();
                    deposit(amount);
                    break;

                case 3:
                    checkBalance();
                    break;
                case 4:
                    System.out.println("Thank you for banking with us.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid Choice. Please try again.");

            }
        }
    }}

class AtmInterface {
    public static void main(String[] args) {
    	
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter your initial Balance:");
        double initialBalance = scanner.nextDouble();
        scanner.nextLine();      
        BankAccount user = new BankAccount(initialBalance);
        ATM atm = new ATM(user,scanner);
        atm.run();
        
}
}