import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a customer in the banking system
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
     * @param name the customer's name
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
     * @param address the customer's address
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
     * @param accounts the list of accounts
     */
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    /**
     * Adds an account to the customer
     * @param accountId the unique account ID
     * @return true if the operation is successful, false otherwise
     */
    public boolean addAccount(String accountId) {
        if (accountId == null || accountId.trim().isEmpty()) {
            return false;
        }
        
        // Check if account ID already exists
        for (Account account : accounts) {
            if (account.getId().equals(accountId)) {
                return false;
            }
        }
        
        // Create a default savings account
        SavingsAccount newAccount = new SavingsAccount();
        newAccount.setId(accountId);
        newAccount.setBalance(0.0);
        newAccount.setInterestRate(0.0);
        
        accounts.add(newAccount);
        return true;
    }

    /**
     * Removes an account from the customer
     * @param accountId the unique account ID
     * @return true if the operation is successful, false otherwise
     */
    public boolean removeAccount(String accountId) {
        if (accountId == null || accountId.trim().isEmpty()) {
            return false;
        }
        
        for (Account account : accounts) {
            if (account.getId().equals(accountId)) {
                // Check if account has zero balance
                if (account.getBalance() != 0.0) {
                    return false;
                }
                
                // For investment accounts, check if there are stock transactions
                if (account instanceof InvestmentAccount) {
                    InvestmentAccount investmentAccount = (InvestmentAccount) account;
                    if (!investmentAccount.getStockTransactions().isEmpty()) {
                        return false;
                    }
                }
                
                accounts.remove(account);
                return true;
            }
        }
        
        return false;
    }

    /**
     * Deposits money into an account
     * @param accountId the account ID
     * @param amount the amount to deposit
     * @return true if the operation is successful, false otherwise
     */
    public boolean deposit(String accountId, double amount) {
        if (amount <= 0 || amount > 1000000) {
            return false;
        }
        
        for (Account account : accounts) {
            if (account.getId().equals(accountId)) {
                account.setBalance(account.getBalance() + amount);
                return true;
            }
        }
        
        return false;
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
     * @param id the account ID
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
     * @param balance the account balance
     */
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
     * Gets the interest rate
     * @return the interest rate
     */
    public double getInterestRate() {
        return interestRate;
    }

    /**
     * Sets the interest rate
     * @param interestRate the interest rate
     */
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * Calculates the daily interest and updates the balance
     * Daily interest = balance * interest rate / 360
     * The daily interest is rounded to two decimal places
     */
    public void calculateDailyInterest() {
        double dailyInterest = getBalance() * interestRate / 360;
        dailyInterest = Math.round(dailyInterest * 100.0) / 100.0;
        setBalance(getBalance() + dailyInterest);
    }
}

/**
 * Represents a stock transaction
 */
class StockTransaction {
    private String ticker;
    private int quantity;
    private double price;
    private double commission;

    /**
     * Default constructor
     */
    public StockTransaction() {
    }

    /**
     * Gets the stock ticker
     * @return the stock ticker
     */
    public String getTicker() {
        return ticker;
    }

    /**
     * Sets the stock ticker
     * @param ticker the stock ticker
     */
    public void setTicker(String ticker) {
        this.ticker = ticker;
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
     * @param quantity the quantity of stocks
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
     * @param price the price per stock
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the commission for the transaction
     * @return the commission for the transaction
     */
    public double getCommission() {
        return commission;
    }

    /**
     * Sets the commission for the transaction
     * @param commission the commission for the transaction
     */
    public void setCommission(double commission) {
        this.commission = commission;
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
     * Gets the list of stock transactions
     * @return the list of stock transactions
     */
    public List<StockTransaction> getStockTransactions() {
        return stockTransactions;
    }

    /**
     * Sets the list of stock transactions
     * @param stockTransactions the list of stock transactions
     */
    public void setStockTransactions(List<StockTransaction> stockTransactions) {
        this.stockTransactions = stockTransactions;
    }

    /**
     * Buys stocks and updates the investment account balance
     * The system saves the transaction records
     * Before buying stocks, it is necessary to ensure that the current balance is sufficient 
     * to cover the stock cost and the bank's commission
     * @param ticker the stock ticker
     * @param quantity the number of stocks to buy
     * @param price the price per stock
     * @return true if the transaction records are saved successfully, false otherwise
     */
    public boolean buyStocks(String ticker, int quantity, double price) {
        if (ticker == null || ticker.trim().isEmpty() || quantity <= 0 || price <= 0) {
            return false;
        }
        
        double stockCost = quantity * price;
        double commission = stockCost * 0.10; // 10% commission
        double totalCost = stockCost + commission;
        
        // Check if balance is sufficient
        if (getBalance() < totalCost) {
            return false;
        }
        
        // Create and save the transaction
        StockTransaction transaction = new StockTransaction();
        transaction.setTicker(ticker);
        transaction.setQuantity(quantity);
        transaction.setPrice(price);
        transaction.setCommission(commission);
        
        stockTransactions.add(transaction);
        
        // Update the balance
        setBalance(getBalance() - totalCost);
        
        return true;
    }

    /**
     * Calculates the value of the investment account, including the account balance 
     * and the total value of stocks
     * The value of each stock is the number of stocks multiplied by the current stock 
     * market price (1.1 times its purchase price)
     * @return the total value of the investment account, rounded to two decimal places, 
     * or 0 if there are no stock transactions
     */
    public double calculateInvestmentValue() {
        if (stockTransactions.isEmpty()) {
            return 0.0;
        }
        
        double stockValue = 0.0;
        
        for (StockTransaction transaction : stockTransactions) {
            double currentPrice = transaction.getPrice() * 1.1; // 1.1 times purchase price
            stockValue += transaction.getQuantity() * currentPrice;
        }
        
        double totalValue = getBalance() + stockValue;
        totalValue = Math.round(totalValue * 100.0) / 100.0;
        
        return totalValue;
    }
}