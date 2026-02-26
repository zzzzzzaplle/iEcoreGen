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
     * Adds an account to the customer's account list
     * @param account The account to be added
     * @return true if account was added successfully, false otherwise
     */
    public boolean addAccount(Account account) {
        if (account != null && !hasAccount(account.getAccountId())) {
            accounts.add(account);
            return true;
        }
        return false;
    }
    
    /**
     * Removes an account from the customer's account list if eligible for removal
     * @param accountId The ID of the account to be removed
     * @return true if account was removed successfully, false otherwise
     */
    public boolean removeAccount(String accountId) {
        Account account = findAccount(accountId);
        if (account != null && account.canBeRemoved()) {
            accounts.remove(account);
            return true;
        }
        return false;
    }
    
    /**
     * Checks if customer already has an account with the given ID
     * @param accountId The account ID to check
     * @return true if account exists, false otherwise
     */
    private boolean hasAccount(String accountId) {
        return findAccount(accountId) != null;
    }
    
    /**
     * Finds an account by its ID
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
    
    /**
     * Checks if the account can be removed
     * @return true if account has zero balance and no stock transactions, false otherwise
     */
    public abstract boolean canBeRemoved();
    
    /**
     * Deposits money into the account
     * @param amount The amount to deposit
     * @return true if deposit was successful, false otherwise
     */
    public abstract boolean deposit(double amount);
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
    
    public double getInterestRate() {
        return interestRate;
    }
    
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
    
    /**
     * Checks if savings account can be removed
     * @return true if balance is zero, false otherwise
     */
    @Override
    public boolean canBeRemoved() {
        return getBalance() == 0;
    }
    
    /**
     * Deposits money into the savings account
     * @param amount The amount to deposit
     * @return true if deposit was successful, false otherwise
     */
    @Override
    public boolean deposit(double amount) {
        if (amount > 0 && amount <= 1000000) {
            setBalance(getBalance() + amount);
            return true;
        }
        return false;
    }
    
    /**
     * Calculates and applies daily interest to the account balance
     * Daily interest = balance * interest rate / 360
     */
    public void calculateDailyInterest() {
        double dailyInterest = getBalance() * interestRate / 360;
        dailyInterest = Math.round(dailyInterest * 100.0) / 100.0; // Keep two decimal places
        setBalance(getBalance() + dailyInterest);
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
    
    public List<StockTransaction> getStockTransactions() {
        return stockTransactions;
    }
    
    public void setStockTransactions(List<StockTransaction> stockTransactions) {
        this.stockTransactions = stockTransactions;
    }
    
    /**
     * Checks if investment account can be removed
     * @return true if balance is zero and there are no stock transactions, false otherwise
     */
    @Override
    public boolean canBeRemoved() {
        return getBalance() == 0 && stockTransactions.isEmpty();
    }
    
    /**
     * Deposits money into the investment account
     * @param amount The amount to deposit
     * @return true if deposit was successful, false otherwise
     */
    @Override
    public boolean deposit(double amount) {
        if (amount > 0 && amount <= 1000000) {
            setBalance(getBalance() + amount);
            return true;
        }
        return false;
    }
    
    /**
     * Buys stocks and updates the investment account balance
     * @param ticker The stock ticker symbol
     * @param quantity The number of stocks to buy
     * @param price The price per stock
     * @return true if transaction was recorded successfully, false otherwise
     */
    public boolean buyStock(String ticker, int quantity, double price) {
        if (quantity <= 0 || price <= 0) {
            return false;
        }
        
        double stockCost = quantity * price;
        double commission = stockCost * 0.1; // 10% commission
        double totalCost = stockCost + commission;
        
        if (getBalance() >= totalCost) {
            setBalance(getBalance() - totalCost);
            
            StockTransaction transaction = new StockTransaction();
            transaction.setTicker(ticker);
            transaction.setQuantity(quantity);
            transaction.setPurchasePrice(price);
            transaction.setCommission(commission);
            
            stockTransactions.add(transaction);
            return true;
        }
        
        return false;
    }
    
    /**
     * Calculates the total value of the investment account
     * @return The total value including balance and stock value (kept to two decimal places)
     */
    public double calculateTotalValue() {
        double stockValue = 0;
        
        for (StockTransaction transaction : stockTransactions) {
            double currentStockPrice = transaction.getPurchasePrice() * 1.1; // 1.1 times purchase price
            stockValue += transaction.getQuantity() * currentStockPrice;
        }
        
        double totalValue = getBalance() + stockValue;
        return Math.round(totalValue * 100.0) / 100.0; // Keep two decimal places
    }
    
    /**
     * Gets all stock transactions grouped by ticker
     * @return Map of ticker to list of transactions
     */
    public Map<String, List<StockTransaction>> getStockTransactionsByTicker() {
        Map<String, List<StockTransaction>> transactionsByTicker = new HashMap<>();
        
        for (StockTransaction transaction : stockTransactions) {
            String ticker = transaction.getTicker();
            transactionsByTicker.putIfAbsent(ticker, new ArrayList<>());
            transactionsByTicker.get(ticker).add(transaction);
        }
        
        return transactionsByTicker;
    }
}

/**
 * Represents a stock transaction record
 */
class StockTransaction {
    private String ticker;
    private int quantity;
    private double purchasePrice;
    private double commission;
    
    /**
     * Default constructor
     */
    public StockTransaction() {
    }
    
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
    
    public double getCommission() {
        return commission;
    }
    
    public void setCommission(double commission) {
        this.commission = commission;
    }
}

/**
 * Main bank system class that manages customers and accounts
 */
class BankSystem {
    private List<Customer> customers;
    
    /**
     * Default constructor
     */
    public BankSystem() {
        this.customers = new ArrayList<>();
    }
    
    public List<Customer> getCustomers() {
        return customers;
    }
    
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
    
    /**
     * Adds a customer to the bank system
     * @param customer The customer to be added
     * @return true if customer was added successfully, false otherwise
     */
    public boolean addCustomer(Customer customer) {
        if (customer != null) {
            customers.add(customer);
            return true;
        }
        return false;
    }
    
    /**
     * Finds a customer by name and address
     * @param name The customer name
     * @param address The customer address
     * @return The customer if found, null otherwise
     */
    public Customer findCustomer(String name, String address) {
        for (Customer customer : customers) {
            if (customer.getName().equals(name) && customer.getAddress().equals(address)) {
                return customer;
            }
        }
        return null;
    }
}