import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer in the bank system
 */
class Customer {
    private String name;
    private String address;
    private List<Account> accounts;

    /**
     * Default constructor
     */
    public Customer() {
        this.accounts = new ArrayList<>();
    }

    /**
     * Adds a new savings account for the customer
     * @param id the unique account ID
     * @param interestRate the interest rate for the savings account
     * @return true if the account was added successfully, false otherwise
     */
    public boolean addSavingAccount(String id, double interestRate) {
        if (findAccountById(id) != null) {
            return false; // Account ID must be unique
        }
        SavingAccount account = new SavingAccount();
        account.setId(id);
        account.setInterestRate(interestRate);
        return accounts.add(account);
    }

    /**
     * Adds a new investment account for the customer
     * @param id the unique account ID
     * @return true if the account was added successfully, false otherwise
     */
    public boolean addInvestmentAccount(String id) {
        if (findAccountById(id) != null) {
            return false; // Account ID must be unique
        }
        InvestmentAccount account = new InvestmentAccount();
        account.setId(id);
        return accounts.add(account);
    }

    /**
     * Removes an investment account if it has no balance or stock transactions
     * @param id the account ID to remove
     * @return true if the account was removed successfully, false otherwise
     */
    public boolean removeInvestmentAccount(String id) {
        Account account = findAccountById(id);
        if (account instanceof InvestmentAccount) {
            InvestmentAccount investmentAccount = (InvestmentAccount) account;
            if (investmentAccount.getBalance() == 0 && investmentAccount.getTransactions().isEmpty()) {
                return accounts.remove(account);
            }
        }
        return false;
    }

    /**
     * Removes a savings account if it has no balance
     * @param id the account ID to remove
     * @return true if the account was removed successfully, false otherwise
     */
    public boolean removeSavingAccount(String id) {
        Account account = findAccountById(id);
        if (account instanceof SavingAccount && account.getBalance() == 0) {
            return accounts.remove(account);
        }
        return false;
    }

    /**
     * Finds an account by its ID
     * @param id the account ID to search for
     * @return the account with the matching ID, or null if not found
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
 * Abstract base class for all account types
 */
abstract class Account {
    private String id;
    private double balance;

    /**
     * Default constructor
     */
    public Account() {
    }

    /**
     * Deposits money into the account
     * @param amount the amount to deposit (must be positive and <= 1000000)
     * @return true if the deposit was successful, false otherwise
     */
    public boolean deposit(double amount) {
        if (amount <= 0 || amount > 1000000) {
            return false;
        }
        balance += amount;
        return true;
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
 * Represents a savings account with interest rate
 */
class SavingAccount extends Account {
    private double interestRate;

    /**
     * Default constructor
     */
    public SavingAccount() {
    }

    /**
     * Calculates and returns the daily interest
     * Daily interest = balance * interest rate / 360
     * @return the daily interest amount rounded to two decimal places
     */
    public double calculateDailyInterest() {
        double interest = getBalance() * interestRate / 360;
        return Math.round(interest * 100.0) / 100.0;
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
 * Represents an investment account for stock transactions
 */
class InvestmentAccount extends Account {
    private List<StockTransaction> transactions;

    /**
     * Default constructor
     */
    public InvestmentAccount() {
        this.transactions = new ArrayList<>();
    }

    /**
     * Buys stocks and updates the account balance
     * @param stockSymbol the stock symbol/ticker
     * @param quantity the number of stocks to buy
     * @param price the price per stock
     * @return true if the transaction was successful, false otherwise
     */
    public boolean buyStock(String stockSymbol, int quantity, double price) {
        double stockCost = quantity * price;
        double commission = stockCost * 0.1; // 10% commission
        double totalCost = stockCost + commission;

        if (getBalance() < totalCost) {
            return false; // Insufficient funds
        }

        // Create and add transaction record
        StockTransaction transaction = new StockTransaction();
        transaction.setStock(stockSymbol);
        transaction.setQuantity(quantity);
        transaction.setPrice(price);
        transaction.setCommission(commission);
        transactions.add(transaction);

        // Update account balance
        setBalance(getBalance() - totalCost);
        return true;
    }

    /**
     * Calculates the total value of the investment account
     * Account value = balance + (sum of all stock values)
     * Stock value = quantity * (price * 1.1)
     * @return the total account value rounded to two decimal places, or 0 if no transactions
     */
    public double calculateValue() {
        if (transactions.isEmpty()) {
            return 0.0;
        }

        double stockValue = 0.0;
        for (StockTransaction transaction : transactions) {
            double currentStockPrice = transaction.getPrice() * 1.1;
            stockValue += transaction.getQuantity() * currentStockPrice;
        }

        double totalValue = getBalance() + stockValue;
        return Math.round(totalValue * 100.0) / 100.0;
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
 * Represents a stock transaction record
 */
class StockTransaction {
    private String stock;
    private int quantity;
    private double price;
    private double commission;

    /**
     * Default constructor
     */
    public StockTransaction() {
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