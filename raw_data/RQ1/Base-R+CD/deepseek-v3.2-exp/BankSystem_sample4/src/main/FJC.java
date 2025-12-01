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

    /**
     * Adds a savings account for the customer
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
        accounts.add(account);
        return true;
    }

    /**
     * Adds an investment account for the customer
     * @param id the unique account ID
     * @return true if the account was added successfully, false otherwise
     */
    public boolean addInvestmentAccount(String id) {
        if (findAccountById(id) != null) {
            return false; // Account ID must be unique
        }
        InvestmentAccount account = new InvestmentAccount();
        account.setId(id);
        accounts.add(account);
        return true;
    }

    /**
     * Removes a savings account
     * @param id the account ID to remove
     * @return true if the account was removed successfully, false otherwise
     */
    public boolean removeSavingAccount(String id) {
        Account account = findAccountById(id);
        if (account instanceof SavingAccount && canRemoveAccount(account)) {
            return accounts.remove(account);
        }
        return false;
    }

    /**
     * Removes an investment account
     * @param id the account ID to remove
     * @return true if the account was removed successfully, false otherwise
     */
    public boolean removeInvestmentAccount(String id) {
        Account account = findAccountById(id);
        if (account instanceof InvestmentAccount && canRemoveAccount(account)) {
            return accounts.remove(account);
        }
        return false;
    }

    /**
     * Finds an account by its ID
     * @param id the account ID to search for
     * @return the account with the specified ID, or null if not found
     */
    public Account findAccountById(String id) {
        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                return account;
            }
        }
        return null;
    }

    /**
     * Checks if an account can be removed (balance is 0 and no stock transactions for investment accounts)
     * @param account the account to check
     * @return true if the account can be removed, false otherwise
     */
    private boolean canRemoveAccount(Account account) {
        if (account.getBalance() != 0) {
            return false;
        }
        
        if (account instanceof InvestmentAccount) {
            InvestmentAccount investmentAccount = (InvestmentAccount) account;
            return investmentAccount.getTransactions().isEmpty();
        }
        
        return true;
    }
}

/**
 * Abstract class representing a bank account
 */
abstract class Account {
    private String id;
    private double balance;

    /**
     * Default constructor
     */
    public Account() {
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

    /**
     * Deposits money into the account
     * @param amount the amount to deposit
     * @return true if the deposit was successful, false otherwise
     */
    public boolean deposit(double amount) {
        if (amount <= 0 || amount > 1000000) {
            return false;
        }
        balance += amount;
        return true;
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

    // Getters and setters
    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * Calculates the daily interest for the savings account
     * Daily interest = balance * interest rate / 360
     * @return the daily interest amount rounded to two decimal places
     */
    public double calculateDailyInterest() {
        double interest = getBalance() * interestRate / 360;
        return Math.round(interest * 100.0) / 100.0;
    }

    /**
     * Updates the balance with the daily interest at 23:59:59
     */
    public void applyDailyInterest() {
        double interest = calculateDailyInterest();
        setBalance(getBalance() + interest);
    }
}

/**
 * Represents an investment account for buying stocks
 */
class InvestmentAccount extends Account {
    private List<StockTransaction> transactions;

    /**
     * Default constructor
     */
    public InvestmentAccount() {
        this.transactions = new ArrayList<>();
    }

    // Getters and setters
    public List<StockTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<StockTransaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * Buys stocks and updates the account balance
     * @param stockSymbol the stock symbol/ticker
     * @param quantity the number of stocks to buy
     * @param price the price per stock
     * @return true if the transaction was successful, false otherwise
     */
    public boolean buyStock(String stockSymbol, int quantity, double price) {
        if (quantity <= 0 || price <= 0) {
            return false;
        }

        double stockCost = quantity * price;
        double commission = stockCost * 0.10; // 10% commission
        double totalCost = stockCost + commission;

        if (getBalance() < totalCost) {
            return false; // Insufficient funds
        }

        // Create and save transaction record
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
     * Value = account balance + total stock value (current market price is 1.1 times purchase price)
     * @return the total account value rounded to two decimal places, or 0 if no stock transactions
     */
    public double calculateValue() {
        if (transactions.isEmpty()) {
            return 0.0;
        }

        double stockValue = 0.0;
        for (StockTransaction transaction : transactions) {
            double currentPrice = transaction.getPrice() * 1.1; // Current market price
            stockValue += transaction.getQuantity() * currentPrice;
        }

        double totalValue = getBalance() + stockValue;
        return Math.round(totalValue * 100.0) / 100.0;
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