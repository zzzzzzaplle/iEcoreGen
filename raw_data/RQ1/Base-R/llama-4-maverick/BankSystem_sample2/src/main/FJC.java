import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a customer in the bank system.
 */
class Customer {
    private String name;
    private String address;
    private Map<String, Account> accounts;

    /**
     * Constructs a new Customer object.
     */
    public Customer() {
        this.accounts = new HashMap<>();
    }

    /**
     * Gets the customer's name.
     * @return the customer's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the customer's name.
     * @param name the customer's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the customer's address.
     * @return the customer's address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the customer's address.
     * @param address the customer's address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the customer's accounts.
     * @return the customer's accounts
     */
    public Map<String, Account> getAccounts() {
        return accounts;
    }

    /**
     * Adds an account to the customer.
     * @param account the account to add
     */
    public void addAccount(Account account) {
        this.accounts.put(account.getId(), account);
    }

    /**
     * Removes an account from the customer.
     * @param accountId the ID of the account to remove
     * @return true if the account was removed successfully, false otherwise
     */
    public boolean removeAccount(String accountId) {
        Account account = accounts.get(accountId);
        if (account != null && account.getBalance() == 0 && (account instanceof InvestmentAccount ? ((InvestmentAccount) account).getStockTransactions().isEmpty() : true)) {
            accounts.remove(accountId);
            return true;
        }
        return false;
    }
}

/**
 * Represents a generic account in the bank system.
 */
abstract class Account {
    private String id;
    private double balance;

    /**
     * Constructs a new Account object.
     */
    public Account() {}

    /**
     * Gets the account's ID.
     * @return the account's ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the account's ID.
     * @param id the account's ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the account's balance.
     * @return the account's balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Sets the account's balance.
     * @param balance the account's balance
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Deposits money into the account.
     * @param amount the amount to deposit
     * @return true if the deposit was successful, false otherwise
     */
    public boolean deposit(double amount) {
        if (amount > 0 && amount <= 1000000) {
            this.balance += amount;
            return true;
        }
        return false;
    }
}

/**
 * Represents a savings account in the bank system.
 */
class SavingsAccount extends Account {
    private double interestRate;

    /**
     * Constructs a new SavingsAccount object.
     */
    public SavingsAccount() {}

    /**
     * Gets the interest rate of the savings account.
     * @return the interest rate
     */
    public double getInterestRate() {
        return interestRate;
    }

    /**
     * Sets the interest rate of the savings account.
     * @param interestRate the interest rate
     */
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * Calculates the daily interest of the savings account and updates the balance.
     */
    public void calculateDailyInterest() {
        double dailyInterest = getBalance() * interestRate / 360;
        dailyInterest = Math.round(dailyInterest * 100.0) / 100.0;
        setBalance(getBalance() + dailyInterest);
    }
}

/**
 * Represents a stock transaction in an investment account.
 */
class StockTransaction {
    private String ticker;
    private int quantity;
    private double price;

    /**
     * Constructs a new StockTransaction object.
     */
    public StockTransaction() {}

    /**
     * Gets the ticker symbol of the stock.
     * @return the ticker symbol
     */
    public String getTicker() {
        return ticker;
    }

    /**
     * Sets the ticker symbol of the stock.
     * @param ticker the ticker symbol
     */
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    /**
     * Gets the quantity of the stock.
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the stock.
     * @param quantity the quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the price of the stock.
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the stock.
     * @param price the price
     */
    public void setPrice(double price) {
        this.price = price;
    }
}

/**
 * Represents an investment account in the bank system.
 */
class InvestmentAccount extends Account {
    private List<StockTransaction> stockTransactions;

    /**
     * Constructs a new InvestmentAccount object.
     */
    public InvestmentAccount() {
        this.stockTransactions = new ArrayList<>();
    }

    /**
     * Gets the stock transactions in the investment account.
     * @return the stock transactions
     */
    public List<StockTransaction> getStockTransactions() {
        return stockTransactions;
    }

    /**
     * Buys stocks and updates the investment account balance.
     * @param ticker the ticker symbol of the stock
     * @param quantity the quantity of the stock
     * @param price the price of the stock
     * @return true if the stock was bought successfully, false otherwise
     */
    public boolean buyStock(String ticker, int quantity, double price) {
        double stockCost = quantity * price;
        double commission = stockCost * 0.1;
        if (getBalance() >= stockCost + commission) {
            StockTransaction transaction = new StockTransaction();
            transaction.setTicker(ticker);
            transaction.setQuantity(quantity);
            transaction.setPrice(price);
            stockTransactions.add(transaction);
            setBalance(getBalance() - stockCost - commission);
            return true;
        }
        return false;
    }

    /**
     * Calculates the value of the investment account, including the account balance and the total value of stocks.
     * @return the value of the investment account
     */
    public double calculateAccountValue() {
        double totalStockValue = 0;
        for (StockTransaction transaction : stockTransactions) {
            totalStockValue += transaction.getQuantity() * transaction.getPrice() * 1.1;
        }
        totalStockValue = Math.round(totalStockValue * 100.0) / 100.0;
        return totalStockValue + getBalance();
    }
}