import java.util.ArrayList;
import java.util.List;

import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The Customer class represents a customer in the bank system.
 * It contains the customer's name, address, and a list of accounts.
 */
class Customer {
    private String name;
    private String address;
    private List<Account> accounts;

    /**
     * Constructs a new Customer with empty name, address, and an empty list of accounts.
     */
    public Customer() {
        this.name = "";
        this.address = "";
        this.accounts = new ArrayList<>();
    }

    /**
     * Adds a savings account to the customer's list of accounts.
     *
     * @param id The ID of the savings account.
     * @param interestRate The interest rate of the savings account.
     * @return true if the account was added successfully, false otherwise.
     */
    public boolean addSavingAccount(String id, double interestRate) {
        if (findAccountById(id) == null) {
            SavingAccount savingAccount = new SavingAccount();
            savingAccount.setId(id);
            savingAccount.setInterestRate(interestRate);
            accounts.add(savingAccount);
            return true;
        }
        return false;
    }

    /**
     * Adds an investment account to the customer's list of accounts.
     *
     * @param id The ID of the investment account.
     * @return true if the account was added successfully, false otherwise.
     */
    public boolean addInvestmentAccount(String id) {
        if (findAccountById(id) == null) {
            InvestmentAccount investmentAccount = new InvestmentAccount();
            investmentAccount.setId(id);
            accounts.add(investmentAccount);
            return true;
        }
        return false;
    }

    /**
     * Removes an investment account from the customer's list of accounts.
     *
     * @param id The ID of the investment account.
     * @return true if the account was removed successfully, false otherwise.
     */
    public boolean removeInvestmentAccount(String id) {
        InvestmentAccount investmentAccount = (InvestmentAccount) findAccountById(id);
        if (investmentAccount != null && investmentAccount.getBalance() == 0 && investmentAccount.getTransactions().isEmpty()) {
            accounts.remove(investmentAccount);
            return true;
        }
        return false;
    }

    /**
     * Removes a savings account from the customer's list of accounts.
     *
     * @param id The ID of the savings account.
     * @return true if the account was removed successfully, false otherwise.
     */
    public boolean removeSavingAccount(String id) {
        SavingAccount savingAccount = (SavingAccount) findAccountById(id);
        if (savingAccount != null && savingAccount.getBalance() == 0) {
            accounts.remove(savingAccount);
            return true;
        }
        return false;
    }

    /**
     * Finds an account by its ID.
     *
     * @param id The ID of the account.
     * @return The Account object if found, null otherwise.
     */
    public Account findAccountById(String id) {
        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                return account;
            }
        }
        return null;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}

/**
 * The abstract Account class represents an account in the bank system.
 * It contains the account ID and balance.
 */
abstract class Account {
    private String id;
    private double balance;

    /**
     * Constructs a new Account with empty ID and balance 0.
     */
    public Account() {
        this.id = "";
        this.balance = 0;
    }

    /**
     * Deposits money into the account.
     *
     * @param amount The amount of money to deposit.
     * @return true if the deposit was successful, false otherwise.
     */
    public boolean deposit(double amount) {
        if (amount > 0 && amount <= 1000000) {
            balance += amount;
            return true;
        }
        return false;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

/**
 * The SavingAccount class represents a savings account in the bank system.
 * It extends the Account class and contains the interest rate.
 */
class SavingAccount extends Account {
    private double interestRate;

    /**
     * Constructs a new SavingAccount with empty ID, balance 0, and interest rate 0.
     */
    public SavingAccount() {
        super();
        this.interestRate = 0;
    }

    /**
     * Calculates the daily interest of the savings account.
     *
     * @return The daily interest.
     */
    public double calculateDailyInterest() {
        double dailyInterest = getBalance() * interestRate / 360;
        return Math.round(dailyInterest * 100.0) / 100.0;
    }

    // Update the balance with daily interest at 23:59:59
    public void updateDailyInterest() {
        LocalTime now = LocalTime.now();
        if (now.equals(LocalTime.of(23, 59, 59))) {
            double dailyInterest = calculateDailyInterest();
            setBalance(getBalance() + dailyInterest);
        }
    }

    // Getters and setters
    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
}

/**
 * The InvestmentAccount class represents an investment account in the bank system.
 * It extends the Account class and contains a list of stock transactions.
 */
class InvestmentAccount extends Account {
    private List<StockTransaction> transactions;

    /**
     * Constructs a new InvestmentAccount with empty ID, balance 0, and an empty list of transactions.
     */
    public InvestmentAccount() {
        super();
        this.transactions = new ArrayList<>();
    }

    /**
     * Buys stocks and updates the investment account balance.
     *
     * @param stockSymbol The symbol of the stock.
     * @param quantity The quantity of the stock.
     * @param price The price of the stock.
     * @return true if the transaction was saved successfully, false otherwise.
     */
    public boolean buyStock(String stockSymbol, int quantity, double price) {
        double cost = quantity * price;
        double commission = cost * 0.1;
        double total = cost + commission;
        if (getBalance() >= total) {
            StockTransaction transaction = new StockTransaction();
            transaction.setStock(stockSymbol);
            transaction.setQuantity(quantity);
            transaction.setPrice(price);
            transaction.setCommission(commission);
            transactions.add(transaction);
            setBalance(getBalance() - total);
            return true;
        }
        return false;
    }

    /**
     * Calculates the value of the investment account, including the account balance and the total value of stocks.
     *
     * @return The value of the investment account.
     */
    public double calculateValue() {
        double totalStockValue = 0;
        for (StockTransaction transaction : transactions) {
            double stockValue = transaction.getQuantity() * transaction.getPrice() * 1.1;
            totalStockValue += stockValue;
        }
        double value = getBalance() + totalStockValue;
        return Math.round(value * 100.0) / 100.0;
    }

    // Getters and setters
    public List<StockTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<StockTransaction> transactions) {
        this.transactions = transactions;
    }
}

/**
 * The StockTransaction class represents a stock transaction in the investment account.
 * It contains the stock symbol, quantity, price, and commission.
 */
class StockTransaction {
    private String stock;
    private int quantity;
    private double price;
    private double commission;

    /**
     * Constructs a new StockTransaction with empty stock, quantity 0, price 0, and commission 0.
     */
    public StockTransaction() {
        this.stock = "";
        this.quantity = 0;
        this.price = 0;
        this.commission = 0;
    }

    // Getters and setters
    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }
}