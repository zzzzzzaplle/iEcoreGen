import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Represents a customer in the bank system.
 */
class Customer {
    private String name;
    private String address;
    private List<Account> accounts;

    /**
     * Default constructor for Customer.
     */
    public Customer() {
        this.accounts = new ArrayList<>();
    }

    // Getters and Setters
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
     * Adds an account to the customer's account list.
     *
     * @param account the account to add
     */
    public void addAccount(Account account) {
        accounts.add(account);
    }

    /**
     * Removes an account from the customer's account list by account ID.
     * An account can only be removed if it has zero balance and no stock transactions (for investment accounts).
     *
     * @param accountId the ID of the account to remove
     * @return true if the account was successfully removed, false otherwise
     */
    public boolean removeAccount(String accountId) {
        Account accountToRemove = null;
        for (Account account : accounts) {
            if (account.getAccountId().equals(accountId)) {
                // Check if account balance is zero
                if (account.getBalance() != 0) {
                    return false;
                }

                // For investment accounts, check if there are no stock transactions
                if (account instanceof InvestmentAccount) {
                    InvestmentAccount investmentAccount = (InvestmentAccount) account;
                    if (investmentAccount.getStockTransactions() != null && !investmentAccount.getStockTransactions().isEmpty()) {
                        return false;
                    }
                }

                accountToRemove = account;
                break;
            }
        }

        if (accountToRemove != null) {
            accounts.remove(accountToRemove);
            return true;
        }

        return false;
    }
}

/**
 * Represents a bank account.
 */
abstract class Account {
    protected String accountId;
    protected double balance;

    /**
     * Default constructor for Account.
     */
    public Account() {
    }

    // Getters and Setters
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
     * Deposits money into the account.
     * The amount must be positive and cannot exceed $1,000,000.
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
 * Represents a savings account with an interest rate.
 */
class SavingsAccount extends Account {
    private double interestRate;

    /**
     * Default constructor for SavingsAccount.
     */
    public SavingsAccount() {
        super();
    }

    // Getters and Setters
    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * Calculates the daily interest and adds it to the account balance.
     * Daily interest = balance * interest rate / 360.
     * The result is rounded to two decimal places.
     */
    public void calculateAndAddDailyInterest() {
        double dailyInterest = balance * interestRate / 360;
        dailyInterest = Math.round(dailyInterest * 100.0) / 100.0;
        balance += dailyInterest;
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
     * Default constructor for StockTransaction.
     */
    public StockTransaction() {
    }

    // Getters and Setters
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
}

/**
 * Represents an investment account used for buying stocks.
 */
class InvestmentAccount extends Account {
    private List<StockTransaction> stockTransactions;
    private static final double COMMISSION_RATE = 0.10; // 10% commission

    /**
     * Default constructor for InvestmentAccount.
     */
    public InvestmentAccount() {
        super();
        this.stockTransactions = new ArrayList<>();
    }

    // Getters and Setters
    public List<StockTransaction> getStockTransactions() {
        return stockTransactions;
    }

    public void setStockTransactions(List<StockTransaction> stockTransactions) {
        this.stockTransactions = stockTransactions;
    }

    /**
     * Buys stocks and updates the investment account balance.
     * The system saves the transaction records.
     * Before buying stocks, it is necessary to ensure that the current balance
     * is sufficient to cover the stock cost and the bank's commission.
     * Stock cost = number of stocks * price.
     * Commission = 10% of the stock cost.
     *
     * @param ticker   the stock ticker symbol
     * @param quantity the number of stocks to buy
     * @param price    the price per stock
     * @return true if the transaction was successful, false otherwise
     */
    public boolean buyStocks(String ticker, int quantity, double price) {
        if (quantity <= 0 || price <= 0) {
            return false;
        }

        double stockCost = quantity * price;
        double commission = stockCost * COMMISSION_RATE;
        double totalCost = stockCost + commission;

        if (balance < totalCost) {
            return false;
        }

        // Create and save the transaction record
        StockTransaction transaction = new StockTransaction();
        transaction.setTicker(ticker);
        transaction.setQuantity(quantity);
        transaction.setPrice(price);

        stockTransactions.add(transaction);
        balance -= totalCost;

        return true;
    }

    /**
     * Calculates the total value of the investment account, including the account balance
     * and the total value of stocks.
     * The value of each stock is the number of stocks multiplied by the current stock market price
     * (1.1 times its purchase price).
     * Returns 0 if there is no stock transaction in the investment account.
     * The value is rounded to two decimal places.
     *
     * @return the total value of the investment account
     */
    public double calculateTotalValue() {
        if (stockTransactions.isEmpty()) {
            return 0.0;
        }

        double stockValue = 0.0;
        for (StockTransaction transaction : stockTransactions) {
            double currentPrice = transaction.getPrice() * 1.1; // 1.1 times purchase price
            stockValue += transaction.getQuantity() * currentPrice;
        }

        // Round to two decimal places
        stockValue = Math.round(stockValue * 100.0) / 100.0;
        
        double totalValue = balance + stockValue;
        return Math.round(totalValue * 100.0) / 100.0;
    }
}