import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;

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
     * Adds an account to the customer's account list
     * @param account The account to add
     * @return true if account was added successfully, false otherwise
     */
    public boolean addAccount(Account account) {
        if (account != null && !hasAccount(account.getAccountId())) {
            return accounts.add(account);
        }
        return false;
    }

    /**
     * Removes an account from the customer's account list
     * @param accountId The ID of the account to remove
     * @return true if account was removed successfully, false otherwise
     */
    public boolean removeAccount(String accountId) {
        Account account = findAccount(accountId);
        if (account != null && account.canBeRemoved()) {
            return accounts.remove(account);
        }
        return false;
    }

    /**
     * Checks if customer has an account with the given ID
     * @param accountId The account ID to check
     * @return true if account exists, false otherwise
     */
    private boolean hasAccount(String accountId) {
        return findAccount(accountId) != null;
    }

    /**
     * Finds an account by ID
     * @param accountId The account ID to search for
     * @return The account if found, null otherwise
     */
    private Account findAccount(String accountId) {
        for (Account account : accounts) {
            if (account.getAccountId().equals(accountId)) {
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
 * Abstract class representing a bank account
 */
abstract class Account {
    private String accountId;
    private double balance;

    /**
     * Default constructor
     */
    public Account() {
    }

    /**
     * Deposits money into the account
     * @param amount The amount to deposit
     * @return true if deposit was successful, false otherwise
     */
    public boolean deposit(double amount) {
        if (amount > 0 && amount <= 1000000) {
            balance += amount;
            return true;
        }
        return false;
    }

    /**
     * Checks if the account can be removed
     * @return true if account has zero balance and no stock transactions, false otherwise
     */
    public abstract boolean canBeRemoved();

    // Getters and setters
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
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
class SavingsAccount extends Account {
    private double interestRate;

    /**
     * Default constructor
     */
    public SavingsAccount() {
    }

    /**
     * Calculates daily interest and updates the balance
     * Daily interest = balance * interest rate / 360
     */
    public void calculateDailyInterest() {
        double dailyInterest = getBalance() * interestRate / 360;
        dailyInterest = BigDecimal.valueOf(dailyInterest)
            .setScale(2, RoundingMode.HALF_UP)
            .doubleValue();
        setBalance(getBalance() + dailyInterest);
    }

    /**
     * Checks if savings account can be removed
     * @return true if balance is zero, false otherwise
     */
    @Override
    public boolean canBeRemoved() {
        return getBalance() == 0;
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
 * Represents a stock transaction record
 */
class StockTransaction {
    private String ticker;
    private int quantity;
    private double purchasePrice;

    /**
     * Default constructor
     */
    public StockTransaction() {
    }

    // Getters and setters
    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
}

/**
 * Represents an investment account for buying stocks
 */
class InvestmentAccount extends Account {
    private List<StockTransaction> stockTransactions;

    /**
     * Default constructor
     */
    public InvestmentAccount() {
        this.stockTransactions = new ArrayList<>();
    }

    /**
     * Buys stocks and updates the account balance
     * @param ticker The stock ticker symbol
     * @param quantity The number of stocks to buy
     * @param price The price per stock
     * @return true if transaction was successful, false otherwise
     */
    public boolean buyStocks(String ticker, int quantity, double price) {
        if (quantity <= 0 || price <= 0) {
            return false;
        }

        double stockCost = quantity * price;
        double commission = stockCost * 0.1;
        double totalCost = stockCost + commission;

        if (getBalance() >= totalCost) {
            // Create and save transaction record
            StockTransaction transaction = new StockTransaction();
            transaction.setTicker(ticker);
            transaction.setQuantity(quantity);
            transaction.setPurchasePrice(price);
            stockTransactions.add(transaction);

            // Update balance
            setBalance(getBalance() - totalCost);
            return true;
        }

        return false;
    }

    /**
     * Calculates the total value of the investment account
     * @return The total value including balance and stock value (rounded to 2 decimal places)
     */
    public double calculateTotalValue() {
        double stockValue = 0;

        for (StockTransaction transaction : stockTransactions) {
            double currentPrice = transaction.getPurchasePrice() * 1.1;
            stockValue += transaction.getQuantity() * currentPrice;
        }

        double totalValue = getBalance() + stockValue;
        return BigDecimal.valueOf(totalValue)
            .setScale(2, RoundingMode.HALF_UP)
            .doubleValue();
    }

    /**
     * Checks if investment account can be removed
     * @return true if balance is zero and no stock transactions exist, false otherwise
     */
    @Override
    public boolean canBeRemoved() {
        return getBalance() == 0 && stockTransactions.isEmpty();
    }

    // Getters and setters
    public List<StockTransaction> getStockTransactions() {
        return stockTransactions;
    }

    public void setStockTransactions(List<StockTransaction> stockTransactions) {
        this.stockTransactions = stockTransactions;
    }
}