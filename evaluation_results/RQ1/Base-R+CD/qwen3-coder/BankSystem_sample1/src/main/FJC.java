import java.util.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents a customer in the bank system.
 */
class Customer {
    private String name;
    private String address;
    private List<Account> accounts;

    /**
     * Constructs a new Customer with empty accounts list.
     */
    public Customer() {
        this.accounts = new ArrayList<>();
    }

    /**
     * Gets the customer's name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the customer's name.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the customer's address.
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the customer's address.
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the list of accounts.
     * @return the accounts
     */
    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * Sets the list of accounts.
     * @param accounts the accounts to set
     */
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    /**
     * Adds a new savings account for the customer.
     * @param id the account ID
     * @param interestRate the interest rate for the savings account
     * @return true if the account was added successfully, false otherwise
     */
    public boolean addSavingAccount(String id, double interestRate) {
        // Check if account ID already exists
        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                return false;
            }
        }
        SavingAccount account = new SavingAccount();
        account.setId(id);
        account.setInterestRate(interestRate);
        accounts.add(account);
        return true;
    }

    /**
     * Adds a new investment account for the customer.
     * @param id the account ID
     * @return true if the account was added successfully, false otherwise
     */
    public boolean addInvestmentAccount(String id) {
        // Check if account ID already exists
        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                return false;
            }
        }
        InvestmentAccount account = new InvestmentAccount();
        account.setId(id);
        accounts.add(account);
        return true;
    }

    /**
     * Removes an investment account by ID.
     * @param id the account ID to remove
     * @return true if the account was removed successfully, false otherwise
     */
    public boolean removeInvestmentAccount(String id) {
        Account account = findAccountById(id);
        if (account instanceof InvestmentAccount) {
            InvestmentAccount investmentAccount = (InvestmentAccount) account;
            // Check if account has balance or transactions
            if (investmentAccount.getBalance() != 0 || !investmentAccount.getTransactions().isEmpty()) {
                return false;
            }
            accounts.remove(account);
            return true;
        }
        return false;
    }

    /**
     * Removes a savings account by ID.
     * @param id the account ID to remove
     * @return true if the account was removed successfully, false otherwise
     */
    public boolean removeSavingAccount(String id) {
        Account account = findAccountById(id);
        if (account instanceof SavingAccount) {
            SavingAccount savingAccount = (SavingAccount) account;
            // Check if account has balance
            if (savingAccount.getBalance() != 0) {
                return false;
            }
            accounts.remove(account);
            return true;
        }
        return false;
    }

    /**
     * Finds an account by its ID.
     * @param id the account ID to search for
     * @return the account if found, null otherwise
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
 * Abstract class representing a bank account.
 */
abstract class Account {
    protected String id;
    protected double balance;

    /**
     * Constructs a new Account with zero balance.
     */
    public Account() {
        this.balance = 0.0;
    }

    /**
     * Gets the account ID.
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the account ID.
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the account balance.
     * @return the balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Sets the account balance.
     * @param balance the balance to set
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
        // Check that the amount must be positive and cannot exceed the maximum single-deposit limit of $1000000
        if (amount <= 0 || amount > 1000000) {
            return false;
        }
        balance += amount;
        return true;
    }
}

/**
 * Represents a savings account with interest rate.
 */
class SavingAccount extends Account {
    private double interestRate;

    /**
     * Constructs a new SavingAccount.
     */
    public SavingAccount() {
        super();
    }

    /**
     * Gets the interest rate.
     * @return the interestRate
     */
    public double getInterestRate() {
        return interestRate;
    }

    /**
     * Sets the interest rate.
     * @param interestRate the interestRate to set
     */
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * Calculates the daily interest and updates the balance.
     * Daily interest = balance * interest rate / 360.
     * The daily interest keeps two decimal places.
     * @return the calculated daily interest
     */
    public double calculateDailyInterest() {
        double dailyInterest = balance * interestRate / 360;
        // Keep two decimal places
        dailyInterest = Math.round(dailyInterest * 100.0) / 100.0;
        balance += dailyInterest;
        return dailyInterest;
    }
}

/**
 * Represents an investment account for buying stocks.
 */
class InvestmentAccount extends Account {
    private List<StockTransaction> transactions;

    /**
     * Constructs a new InvestmentAccount with empty transactions list.
     */
    public InvestmentAccount() {
        super();
        this.transactions = new ArrayList<>();
    }

    /**
     * Gets the list of stock transactions.
     * @return the transactions
     */
    public List<StockTransaction> getTransactions() {
        return transactions;
    }

    /**
     * Sets the list of stock transactions.
     * @param transactions the transactions to set
     */
    public void setTransactions(List<StockTransaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * Buys stocks and updates the investment account balance.
     * Before buying stocks, ensures that the current balance is sufficient to cover
     * the stock cost and the bank's commission (10% of the cost).
     * Stock cost = number of stocks * price.
     * @param stockSymbol the stock symbol
     * @param quantity the number of stocks to buy
     * @param price the price per stock
     * @return true if the transaction was successful, false otherwise
     */
    public boolean buyStock(String stockSymbol, int quantity, double price) {
        double stockCost = quantity * price;
        double commission = stockCost * 0.1; // 10% commission
        double totalCost = stockCost + commission;

        // Check if balance is sufficient
        if (balance < totalCost) {
            return false;
        }

        // Deduct the total cost from balance
        balance -= totalCost;

        // Create and add the transaction record
        StockTransaction transaction = new StockTransaction();
        transaction.setStock(stockSymbol);
        transaction.setQuantity(quantity);
        transaction.setPrice(price);
        transaction.setCommission(commission);
        transactions.add(transaction);

        return true;
    }

    /**
     * Calculates the total value of the investment account, including the account balance
     * and the total value of stocks.
     * The value of each stock is the number of stocks multiplied by the current stock market
     * price (1.1 times its purchase price).
     * @return the total value of the investment account, or 0 if there are no transactions
     */
    public double calculateValue() {
        if (transactions.isEmpty()) {
            return 0.0;
        }

        double totalStockValue = 0.0;
        for (StockTransaction transaction : transactions) {
            // Current market price is 1.1 times the purchase price
            double currentPrice = transaction.getPrice() * 1.1;
            totalStockValue += transaction.getQuantity() * currentPrice;
        }

        // Round to two decimal places
        BigDecimal totalValue = new BigDecimal(balance + totalStockValue);
        totalValue = totalValue.setScale(2, RoundingMode.HALF_UP);
        
        return totalValue.doubleValue();
    }
}

/**
 * Represents a stock transaction record.
 */
class StockTransaction {
    private String stock;
    private int quantity;
    private double price;
    private double commission;

    /**
     * Constructs a new StockTransaction.
     */
    public StockTransaction() {}

    /**
     * Gets the stock symbol.
     * @return the stock
     */
    public String getStock() {
        return stock;
    }

    /**
     * Sets the stock symbol.
     * @param stock the stock to set
     */
    public void setStock(String stock) {
        this.stock = stock;
    }

    /**
     * Gets the quantity of stocks.
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of stocks.
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the price per stock.
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price per stock.
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the commission amount.
     * @return the commission
     */
    public double getCommission() {
        return commission;
    }

    /**
     * Sets the commission amount.
     * @param commission the commission to set
     */
    public void setCommission(double commission) {
        this.commission = commission;
    }
}