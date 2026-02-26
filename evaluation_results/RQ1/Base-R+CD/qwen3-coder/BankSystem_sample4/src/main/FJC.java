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
     * Constructs a new Customer with default values.
     */
    public Customer() {
        this.accounts = new ArrayList<>();
    }

    /**
     * Gets the customer's name.
     *
     * @return the name of the customer
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the customer's name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the customer's address.
     *
     * @return the address of the customer
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the customer's address.
     *
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the list of accounts for this customer.
     *
     * @return the list of accounts
     */
    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * Sets the list of accounts for this customer.
     *
     * @param accounts the accounts to set
     */
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
        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                return false; // Account ID already exists
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
        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                return false; // Account ID already exists
            }
        }
        InvestmentAccount investmentAccount = new InvestmentAccount();
        investmentAccount.setId(id);
        accounts.add(investmentAccount);
        return true;
    }

    /**
     * Removes an investment account if it has zero balance and no transactions.
     *
     * @param id the unique identifier of the account to remove
     * @return true if the account was removed successfully, false otherwise
     */
    public boolean removeInvestmentAccount(String id) {
        Account account = findAccountById(id);
        if (account instanceof InvestmentAccount) {
            InvestmentAccount investmentAccount = (InvestmentAccount) account;
            if (investmentAccount.getBalance() == 0 && 
                (investmentAccount.getTransactions() == null || investmentAccount.getTransactions().isEmpty())) {
                return accounts.remove(account);
            }
        }
        return false;
    }

    /**
     * Removes a savings account if it has zero balance.
     *
     * @param id the unique identifier of the account to remove
     * @return true if the account was removed successfully, false otherwise
     */
    public boolean removeSavingAccount(String id) {
        Account account = findAccountById(id);
        if (account instanceof SavingAccount) {
            if (account.getBalance() == 0) {
                return accounts.remove(account);
            }
        }
        return false;
    }

    /**
     * Finds an account by its unique identifier.
     *
     * @param id the unique identifier of the account
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
 * Abstract base class for all account types in the bank system.
 */
abstract class Account {
    protected String id;
    protected double balance;

    /**
     * Constructs a new Account with default values.
     */
    public Account() {
        this.balance = 0.0;
    }

    /**
     * Gets the account's unique identifier.
     *
     * @return the account ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the account's unique identifier.
     *
     * @param id the ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the current balance of the account.
     *
     * @return the account balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Sets the current balance of the account.
     *
     * @param balance the balance to set
     */
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
        balance += amount;
        return true;
    }
}

/**
 * Represents a savings account that earns interest.
 */
class SavingAccount extends Account {
    private double interestRate;

    /**
     * Constructs a new SavingAccount with default values.
     */
    public SavingAccount() {
        super();
    }

    /**
     * Gets the interest rate for this savings account.
     *
     * @return the interest rate
     */
    public double getInterestRate() {
        return interestRate;
    }

    /**
     * Sets the interest rate for this savings account.
     *
     * @param interestRate the interest rate to set
     */
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * Calculates the daily interest for this savings account.
     * Daily interest = balance * interest rate / 360.
     * The result is rounded to two decimal places.
     *
     * @return the calculated daily interest
     */
    public double calculateDailyInterest() {
        BigDecimal dailyInterest = BigDecimal.valueOf(balance * interestRate / 360);
        dailyInterest = dailyInterest.setScale(2, RoundingMode.HALF_UP);
        return dailyInterest.doubleValue();
    }
}

/**
 * Represents an investment account used for buying stocks.
 */
class InvestmentAccount extends Account {
    private List<StockTransaction> transactions;

    /**
     * Constructs a new InvestmentAccount with default values.
     */
    public InvestmentAccount() {
        super();
        this.transactions = new ArrayList<>();
    }

    /**
     * Gets the list of stock transactions for this account.
     *
     * @return the list of transactions
     */
    public List<StockTransaction> getTransactions() {
        return transactions;
    }

    /**
     * Sets the list of stock transactions for this account.
     *
     * @param transactions the transactions to set
     */
    public void setTransactions(List<StockTransaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * Buys stocks and records the transaction.
     * The system checks if the account has sufficient balance to cover
     * both the stock cost and the bank's commission (10% of stock cost).
     * Stock cost = number of stocks * price.
     *
     * @param stockSymbol the symbol of the stock to buy
     * @param quantity the number of stocks to buy
     * @param price the price per stock
     * @return true if the transaction was successful, false otherwise
     */
    public boolean buyStock(String stockSymbol, int quantity, double price) {
        double stockCost = quantity * price;
        double commission = stockCost * 0.1; // 10% commission
        double totalCost = stockCost + commission;

        if (balance < totalCost) {
            return false; // Insufficient balance
        }

        // Deduct the total cost from the account balance
        balance -= totalCost;

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
     * Calculates the total value of the investment account.
     * This includes the account balance and the total value of stocks.
     * The value of each stock is the number of stocks multiplied by
     * the current stock market price (1.1 times its purchase price).
     * The result is rounded to two decimal places.
     *
     * @return the total value of the investment account, or 0 if no transactions exist
     */
    public double calculateValue() {
        if (transactions.isEmpty()) {
            return 0.0;
        }

        double stockValue = 0.0;
        for (StockTransaction transaction : transactions) {
            // Current market price is 1.1 times the purchase price
            double currentPrice = transaction.getPrice() * 1.1;
            stockValue += transaction.getQuantity() * currentPrice;
        }

        BigDecimal totalValue = BigDecimal.valueOf(balance + stockValue);
        totalValue = totalValue.setScale(2, RoundingMode.HALF_UP);
        return totalValue.doubleValue();
    }
}

/**
 * Represents a stock transaction in an investment account.
 */
class StockTransaction {
    private String stock;
    private int quantity;
    private double price;
    private double commission;

    /**
     * Constructs a new StockTransaction with default values.
     */
    public StockTransaction() {}

    /**
     * Gets the stock symbol.
     *
     * @return the stock symbol
     */
    public String getStock() {
        return stock;
    }

    /**
     * Sets the stock symbol.
     *
     * @param stock the stock symbol to set
     */
    public void setStock(String stock) {
        this.stock = stock;
    }

    /**
     * Gets the quantity of stocks in this transaction.
     *
     * @return the quantity of stocks
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of stocks in this transaction.
     *
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the price per stock in this transaction.
     *
     * @return the price per stock
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price per stock in this transaction.
     *
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the commission applied to this transaction.
     *
     * @return the commission amount
     */
    public double getCommission() {
        return commission;
    }

    /**
     * Sets the commission applied to this transaction.
     *
     * @param commission the commission to set
     */
    public void setCommission(double commission) {
        this.commission = commission;
    }
}