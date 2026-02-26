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
     * Gets the customer's name
     * @return the customer's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the customer's name
     * @param name the customer's name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the customer's address
     * @return the customer's address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the customer's address
     * @param address the customer's address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the list of accounts
     * @return the list of accounts
     */
    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * Sets the list of accounts
     * @param accounts the list of accounts to set
     */
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
            return false; // Account ID already exists
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
            return false; // Account ID already exists
        }
        InvestmentAccount account = new InvestmentAccount();
        account.setId(id);
        accounts.add(account);
        return true;
    }

    /**
     * Removes an investment account
     * @param id the unique account ID
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
     * Removes a savings account
     * @param id the unique account ID
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

    /**
     * Gets the account ID
     * @return the account ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the account ID
     * @param id the account ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the account balance
     * @return the account balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Sets the account balance
     * @param balance the account balance to set
     */
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
 * Represents a savings account that earns interest
 */
class SavingAccount extends Account {
    private double interestRate;

    /**
     * Default constructor
     */
    public SavingAccount() {
    }

    /**
     * Gets the interest rate
     * @return the interest rate
     */
    public double getInterestRate() {
        return interestRate;
    }

    /**
     * Sets the interest rate
     * @param interestRate the interest rate to set
     */
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * Calculates the daily interest for the savings account
     * Daily interest = balance * interest rate / 360
     * @return the daily interest amount rounded to two decimal places
     */
    public double calculateDailyInterest() {
        double dailyInterest = getBalance() * interestRate / 360;
        return Math.round(dailyInterest * 100.0) / 100.0;
    }

    /**
     * Updates the balance with the daily interest at 23:59:59
     */
    public void updateDailyInterest() {
        double dailyInterest = calculateDailyInterest();
        setBalance(getBalance() + dailyInterest);
    }
}

/**
 * Represents an investment account used for buying stocks
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
     * Gets the list of stock transactions
     * @return the list of stock transactions
     */
    public List<StockTransaction> getTransactions() {
        return transactions;
    }

    /**
     * Sets the list of stock transactions
     * @param transactions the list of stock transactions to set
     */
    public void setTransactions(List<StockTransaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * Buys stocks and updates the investment account balance
     * @param stockSymbol the stock symbol/ticker
     * @param quantity the number of stocks to buy
     * @param price the price per stock
     * @return true if the transaction was successful, false otherwise
     */
    public boolean buyStock(String stockSymbol, int quantity, double price) {
        double stockCost = quantity * price;
        double commission = stockCost * 0.10; // 10% commission
        double totalCost = stockCost + commission;

        if (getBalance() < totalCost) {
            return false; // Insufficient balance
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
     * Includes account balance and total stock value (current market price is 1.1 times purchase price)
     * @return the total account value rounded to two decimal places, or 0 if no stock transactions exist
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

    /**
     * Gets the stock symbol
     * @return the stock symbol
     */
    public String getStock() {
        return stock;
    }

    /**
     * Sets the stock symbol
     * @param stock the stock symbol to set
     */
    public void setStock(String stock) {
        this.stock = stock;
    }

    /**
     * Gets the quantity of stocks
     * @return the quantity of stocks
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of stocks
     * @param quantity the quantity of stocks to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the price per stock
     * @return the price per stock
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price per stock
     * @param price the price per stock to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the commission paid for the transaction
     * @return the commission amount
     */
    public double getCommission() {
        return commission;
    }

    /**
     * Sets the commission paid for the transaction
     * @param commission the commission amount to set
     */
    public void setCommission(double commission) {
        this.commission = commission;
    }
}