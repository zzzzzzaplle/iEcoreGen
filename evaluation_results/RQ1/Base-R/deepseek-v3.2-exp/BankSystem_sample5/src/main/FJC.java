import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * Adds an account to the customer
     * @param accountId the unique account ID
     * @param accountType the type of account ("SAVINGS" or "INVESTMENT")
     * @return true if account was added successfully, false otherwise
     */
    public boolean addAccount(String accountId, String accountType) {
        if (getAccountById(accountId) != null) {
            return false; // Account ID already exists
        }
        
        Account account;
        if ("SAVINGS".equalsIgnoreCase(accountType)) {
            account = new SavingsAccount(accountId);
        } else if ("INVESTMENT".equalsIgnoreCase(accountType)) {
            account = new InvestmentAccount(accountId);
        } else {
            return false; // Invalid account type
        }
        
        accounts.add(account);
        return true;
    }

    /**
     * Removes an account from the customer
     * @param accountId the unique account ID to remove
     * @return true if account was removed successfully, false otherwise
     */
    public boolean removeAccount(String accountId) {
        Account account = getAccountById(accountId);
        if (account == null) {
            return false; // Account not found
        }
        
        // Check if account has zero balance and no stock transactions
        if (account.getBalance() == 0) {
            if (account instanceof InvestmentAccount) {
                InvestmentAccount investmentAccount = (InvestmentAccount) account;
                if (investmentAccount.getStockTransactions().isEmpty()) {
                    accounts.remove(account);
                    return true;
                }
            } else {
                accounts.remove(account);
                return true;
            }
        }
        
        return false;
    }

    /**
     * Gets an account by its ID
     * @param accountId the account ID to search for
     * @return the account if found, null otherwise
     */
    public Account getAccountById(String accountId) {
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
 * Abstract base class for all bank accounts
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
     * Constructor with account ID
     * @param accountId the unique account identifier
     */
    public Account(String accountId) {
        this.accountId = accountId;
        this.balance = 0.0;
    }

    /**
     * Deposits money into the account
     * @param amount the amount to deposit
     * @return true if deposit was successful, false otherwise
     */
    public boolean deposit(double amount) {
        if (amount <= 0 || amount > 1000000) {
            return false; // Invalid amount
        }
        
        this.balance += amount;
        return true;
    }

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
        super();
    }

    /**
     * Constructor with account ID
     * @param accountId the unique account identifier
     */
    public SavingsAccount(String accountId) {
        super(accountId);
    }

    /**
     * Calculates and applies daily interest to the account balance
     * Daily interest = balance * interest rate / 360
     * The result is rounded to two decimal places
     */
    public void calculateDailyInterest() {
        double dailyInterest = getBalance() * interestRate / 360;
        dailyInterest = Math.round(dailyInterest * 100.0) / 100.0; // Keep two decimal places
        setBalance(getBalance() + dailyInterest);
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
    private double price;
    private double commission;

    /**
     * Default constructor
     */
    public StockTransaction() {
    }

    /**
     * Constructor with transaction details
     * @param ticker the stock ticker symbol
     * @param quantity the number of stocks purchased
     * @param price the purchase price per stock
     * @param commission the commission paid for the transaction
     */
    public StockTransaction(String ticker, int quantity, double price, double commission) {
        this.ticker = ticker;
        this.quantity = quantity;
        this.price = price;
        this.commission = commission;
    }

    /**
     * Calculates the total cost of this stock transaction including commission
     * @return the total cost of the transaction
     */
    public double getTotalCost() {
        return (quantity * price) + commission;
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

/**
 * Represents an investment account for buying stocks
 */
class InvestmentAccount extends Account {
    private List<StockTransaction> stockTransactions;

    /**
     * Default constructor
     */
    public InvestmentAccount() {
        super();
        this.stockTransactions = new ArrayList<>();
    }

    /**
     * Constructor with account ID
     * @param accountId the unique account identifier
     */
    public InvestmentAccount(String accountId) {
        super(accountId);
        this.stockTransactions = new ArrayList<>();
    }

    /**
     * Buys stocks and updates the investment account balance
     * The system saves the transaction records. There is no need to combine multiple purchases of the same stock.
     * Before buying stocks, it is necessary to ensure that the current balance is sufficient to cover 
     * the stock cost and the bank's commission (10% of stock cost).
     * @param ticker the stock ticker symbol
     * @param quantity the number of stocks to buy
     * @param price the price per stock
     * @return true if the transaction was saved successfully, false otherwise
     */
    public boolean buyStock(String ticker, int quantity, double price) {
        if (quantity <= 0 || price <= 0) {
            return false; // Invalid quantity or price
        }
        
        double stockCost = quantity * price;
        double commission = stockCost * 0.10; // 10% commission
        double totalCost = stockCost + commission;
        
        // Check if balance is sufficient
        if (getBalance() < totalCost) {
            return false; // Insufficient funds
        }
        
        // Create and save transaction record
        StockTransaction transaction = new StockTransaction(ticker, quantity, price, commission);
        stockTransactions.add(transaction);
        
        // Update account balance
        setBalance(getBalance() - totalCost);
        
        return true;
    }

    /**
     * Calculates the total value of the investment account, including the account balance 
     * and the total value of stocks. The value of each stock is the number of stocks multiplied 
     * by the current stock market price (1.1 times its purchase price).
     * @return the total account value rounded to two decimal places, or 0 if there are no stock transactions
     */
    public double calculateAccountValue() {
        if (stockTransactions.isEmpty()) {
            return 0.0;
        }
        
        double stockValue = 0.0;
        
        // Calculate total stock value
        for (StockTransaction transaction : stockTransactions) {
            double currentStockPrice = transaction.getPrice() * 1.1; // 1.1 times purchase price
            stockValue += transaction.getQuantity() * currentStockPrice;
        }
        
        double totalValue = getBalance() + stockValue;
        return Math.round(totalValue * 100.0) / 100.0; // Keep two decimal places
    }

    /**
     * Gets the total quantity of a specific stock owned
     * @param ticker the stock ticker symbol
     * @return the total quantity of the specified stock owned
     */
    public int getStockQuantity(String ticker) {
        int totalQuantity = 0;
        for (StockTransaction transaction : stockTransactions) {
            if (transaction.getTicker().equals(ticker)) {
                totalQuantity += transaction.getQuantity();
            }
        }
        return totalQuantity;
    }

    // Getters and setters
    public List<StockTransaction> getStockTransactions() {
        return stockTransactions;
    }

    public void setStockTransactions(List<StockTransaction> stockTransactions) {
        this.stockTransactions = stockTransactions;
    }
}

/**
 * Represents the bank system that manages customers and their accounts
 */
class BankSystem {
    private List<Customer> customers;

    /**
     * Default constructor
     */
    public BankSystem() {
        this.customers = new ArrayList<>();
    }

    /**
     * Finds a customer by name and address
     * @param name the customer's name
     * @param address the customer's address
     * @return the customer if found, null otherwise
     */
    public Customer findCustomer(String name, String address) {
        for (Customer customer : customers) {
            if (customer.getName().equals(name) && customer.getAddress().equals(address)) {
                return customer;
            }
        }
        return null;
    }

    /**
     * Adds a new customer to the bank system
     * @param name the customer's name
     * @param address the customer's address
     * @return true if customer was added successfully, false if customer already exists
     */
    public boolean addCustomer(String name, String address) {
        if (findCustomer(name, address) != null) {
            return false; // Customer already exists
        }
        
        Customer customer = new Customer();
        customer.setName(name);
        customer.setAddress(address);
        customers.add(customer);
        return true;
    }

    /**
     * Processes daily interest for all savings accounts at 23:59:59
     */
    public void processDailyInterest() {
        for (Customer customer : customers) {
            for (Account account : customer.getAccounts()) {
                if (account instanceof SavingsAccount) {
                    SavingsAccount savingsAccount = (SavingsAccount) account;
                    savingsAccount.calculateDailyInterest();
                }
            }
        }
    }

    // Getters and setters
    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}