import java.util.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents a customer in the banking system.
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
     * Adds a new savings account for the customer.
     *
     * @param id the unique identifier for the account
     * @param interestRate the interest rate for the savings account
     * @return true if the account was added successfully, false otherwise
     */
    public boolean addSavingAccount(String id, double interestRate) {
        // Check if account with same ID already exists
        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                return false;
            }
        }
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId(id);
        savingAccount.setInterestRate(interestRate);
        accounts.add(savingAccount);
        return true;
    }

    /**
     * Adds a new investment account for the customer.
     *
     * @param id the unique identifier for the account
     * @return true if the account was added successfully, false otherwise
     */
    public boolean addInvestmentAccount(String id) {
        // Check if account with same ID already exists
        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                return false;
            }
        }
        InvestmentAccount investmentAccount = new InvestmentAccount();
        investmentAccount.setId(id);
        accounts.add(investmentAccount);
        return true;
    }

    /**
     * Removes an investment account by its ID.
     *
     * @param id the unique identifier of the account to remove
     * @return true if the account was removed successfully, false otherwise
     */
    public boolean removeInvestmentAccount(String id) {
        Account accountToRemove = null;
        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                if (account instanceof InvestmentAccount) {
                    InvestmentAccount investmentAccount = (InvestmentAccount) account;
                    // Check if account has balance or transactions
                    if (investmentAccount.getBalance() == 0 && 
                        (investmentAccount.getTransactions() == null || investmentAccount.getTransactions().isEmpty())) {
                        accountToRemove = account;
                        break;
                    }
                }
                return false; // Account exists but conditions not met
            }
        }
        if (accountToRemove != null) {
            accounts.remove(accountToRemove);
            return true;
        }
        return false;
    }

    /**
     * Removes a savings account by its ID.
     *
     * @param id the unique identifier of the account to remove
     * @return true if the account was removed successfully, false otherwise
     */
    public boolean removeSavingAccount(String id) {
        Account accountToRemove = null;
        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                if (account instanceof SavingAccount) {
                    SavingAccount savingAccount = (SavingAccount) account;
                    // Check if account has balance
                    if (savingAccount.getBalance() == 0) {
                        accountToRemove = account;
                        break;
                    }
                }
                return false; // Account exists but conditions not met
            }
        }
        if (accountToRemove != null) {
            accounts.remove(accountToRemove);
            return true;
        }
        return false;
    }

    /**
     * Finds an account by its ID.
     *
     * @param id the unique identifier of the account
     * @return the Account object if found, null otherwise
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
 * Abstract base class for all account types.
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
     * Deposits money into the account.
     *
     * @param amount the amount to deposit
     * @return true if the deposit was successful, false otherwise
     */
    public boolean deposit(double amount) {
        if (amount <= 0 || amount > 1000000) {
            return false;
        }
        this.balance += amount;
        return true;
    }
}

/**
 * Represents a savings account that earns interest.
 */
class SavingAccount extends Account {
    private double interestRate;

    /**
     * Constructs a new SavingsAccount with zero balance and zero interest rate.
     */
    public SavingAccount() {
        super();
    }

    // Getters and setters
    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * Calculates the daily interest and adds it to the account balance.
     *
     * @return the calculated daily interest amount
     */
    public double calculateDailyInterest() {
        double dailyInterest = balance * interestRate / 360;
        // Keep two decimal places
        BigDecimal bd = new BigDecimal(dailyInterest).setScale(2, RoundingMode.HALF_UP);
        double roundedInterest = bd.doubleValue();
        this.balance += roundedInterest;
        return roundedInterest;
    }
}

/**
 * Represents an investment account for buying stocks.
 */
class InvestmentAccount extends Account {
    private List<StockTransaction> transactions;

    /**
     * Constructs a new InvestmentAccount with zero balance and empty transactions list.
     */
    public InvestmentAccount() {
        super();
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
     * Buys stocks and records the transaction.
     *
     * @param stockSymbol the symbol of the stock to buy
     * @param quantity the number of stocks to buy
     * @param price the price per stock
     * @return true if the purchase was successful, false otherwise
     */
    public boolean buyStock(String stockSymbol, int quantity, double price) {
        if (quantity <= 0 || price <= 0) {
            return false;
        }

        double stockCost = quantity * price;
        double commission = stockCost * 0.1; // 10% commission
        double totalCost = stockCost + commission;

        if (this.balance < totalCost) {
            return false;
        }

        // Deduct total cost from balance
        this.balance -= totalCost;

        // Record the transaction
        StockTransaction transaction = new StockTransaction();
        transaction.setStock(stockSymbol);
        transaction.setQuantity(quantity);
        transaction.setPrice(price);
        transaction.setCommission(commission);
        transactions.add(transaction);

        return true;
    }

    /**
     * Calculates the total value of the investment account including balance and stocks.
     *
     * @return the total value of the investment account with two decimal places
     */
    public double calculateValue() {
        double stockValue = 0.0;

        // Calculate value of all stocks (current market price is 1.1 times purchase price)
        for (StockTransaction transaction : transactions) {
            double currentPrice = transaction.getPrice() * 1.1;
            stockValue += transaction.getQuantity() * currentPrice;
        }

        // Total value is account balance plus stock value
        double totalValue = this.balance + stockValue;

        // Round to two decimal places
        BigDecimal bd = new BigDecimal(totalValue).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
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
     * Constructs a new StockTransaction with default values.
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