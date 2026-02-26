import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents a bank customer.
 */
 class Customer {
    private String name;
    private String address;
    private List<Account> accounts;

    /**
     * Constructs a new Customer with empty fields.
     */
    public Customer() {
        this.accounts = new ArrayList<>();
    }

    /**
     * Constructs a new Customer with the given name and address.
     *
     * @param name    the customer's name
     * @param address the customer's address
     */
    public Customer(String name, String address) {
        this.name = name;
        this.address = address;
        this.accounts = new ArrayList<>();
    }

    // ------------------------------------------------------------------------
    // Getters and setters
    // ------------------------------------------------------------------------

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

    // ------------------------------------------------------------------------
    // Account management methods
    // ------------------------------------------------------------------------

    /**
     * Adds a new savings account to this customer.
     *
     * @param id           the unique identifier for the account
     * @param interestRate the interest rate for the savings account (e.g., 0.05 for 5%)
     * @return {@code true} if the account was added successfully; {@code false} if an
     * account with the same id already exists or the id is {@code null}
     */
    public boolean addSavingAccount(String id, double interestRate) {
        if (id == null || findAccountById(id) != null) {
            return false;
        }
        SavingAccount account = new SavingAccount();
        account.setId(id);
        account.setInterestRate(interestRate);
        // Balance defaults to 0.0
        accounts.add(account);
        return true;
    }

    /**
     * Adds a new investment account to this customer.
     *
     * @param id the unique identifier for the account
     * @return {@code true} if the account was added successfully; {@code false} if an
     * account with the same id already exists or the id is {@code null}
     */
    public boolean addInvestmentAccount(String id) {
        if (id == null || findAccountById(id) != null) {
            return false;
        }
        InvestmentAccount account = new InvestmentAccount();
        account.setId(id);
        // Balance defaults to 0.0; transactions list is initialized in the constructor
        accounts.add(account);
        return true;
    }

    /**
     * Removes a savings account from this customer.
     *
     * @param id the identifier of the savings account to remove
     * @return {@code true} if the account was removed; {@code false} if the account does
     * not exist, is not a savings account, or has a non‑zero balance
     */
    public boolean removeSavingAccount(String id) {
        Account account = findAccountById(id);
        if (account instanceof SavingAccount) {
            if (account.getBalance() == 0.0) {
                accounts.remove(account);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes an investment account from this customer.
     *
     * @param id the identifier of the investment account to remove
     * @return {@code true} if the account was removed; {@code false} if the account does
     * not exist, is not an investment account, has a non‑zero balance, or has any stock
     * transactions
     */
    public boolean removeInvestmentAccount(String id) {
        Account account = findAccountById(id);
        if (account instanceof InvestmentAccount) {
            InvestmentAccount invAcc = (InvestmentAccount) account;
            if (invAcc.getBalance() == 0.0 && invAcc.getTransactions().isEmpty()) {
                accounts.remove(account);
                return true;
            }
        }
        return false;
    }

    /**
     * Finds an account belonging to this customer by its identifier.
     *
     * @param id the identifier of the account to find
     * @return the {@link Account} with the given id, or {@code null} if no such account exists
     */
    public Account findAccountById(String id) {
        if (id == null) {
            return null;
        }
        for (Account acc : accounts) {
            if (id.equals(acc.getId())) {
                return acc;
            }
        }
        return null;
    }
}

/**
 * Abstract base class for all account types.
 */
abstract class Account {
    private String id;
    private double balance;

    /**
     * Constructs an empty account.
     */
    public Account() {
        // balance defaults to 0.0
    }

    /**
     * Constructs an account with the given identifier.
     *
     * @param id the unique identifier for the account
     */
    public Account(String id) {
        this.id = id;
    }

    // ------------------------------------------------------------------------
    // Getters and setters
    // ------------------------------------------------------------------------

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    /**
     * Sets the account balance. This method is {@code protected} so that subclasses can
     * update the balance while external code must use {@link #deposit(double)}.
     *
     * @param balance the new balance
     */
    protected void setBalance(double balance) {
        this.balance = balance;
    }

    // ------------------------------------------------------------------------
    // Business operations
    // ------------------------------------------------------------------------

    /**
     * Deposits a positive amount into the account, respecting the maximum single‑deposit limit.
     *
     * @param amount the amount to deposit; must be greater than 0 and not exceed $1,000,000
     * @return {@code true} if the deposit succeeded; {@code false} otherwise
     */
    public boolean deposit(double amount) {
        if (amount <= 0.0 || amount > 1_000_000.0) {
            return false;
        }
        double newBalance = roundTwoDecimals(this.balance + amount);
        setBalance(newBalance);
        return true;
    }

    /**
     * Rounds a monetary value to two decimal places using {@link RoundingMode#HALF_UP}.
     *
     * @param value the value to round
     * @return the rounded value
     */
    protected static double roundTwoDecimals(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}

/**
 * Represents a savings account that accrues daily interest.
 */
class SavingAccount extends Account {
    private double interestRate; // e.g., 0.05 for 5%

    /**
     * Constructs an empty savings account.
     */
    public SavingAccount() {
        super();
    }

    /**
     * Constructs a savings account with the given identifier and interest rate.
     *
     * @param id           the unique identifier for the account
     * @param interestRate the interest rate for the savings account (as a decimal, e.g., 0.05 for 5%)
     */
    public SavingAccount(String id, double interestRate) {
        super(id);
        this.interestRate = interestRate;
    }

    // ------------------------------------------------------------------------
    // Getter and setter
    // ------------------------------------------------------------------------

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    // ------------------------------------------------------------------------
    // Business operations
    // ------------------------------------------------------------------------

    /**
     * Calculates the daily interest based on the current balance and interest rate,
     * adds it to the balance, and returns the interest amount.
     *
     * @return the interest amount added to the balance, rounded to two decimal places
     */
    public double calculateDailyInterest() {
        double interest = getBalance() * interestRate / 360.0;
        interest = roundTwoDecimals(interest);
        // Update balance
        setBalance(roundTwoDecimals(getBalance() + interest));
        return interest;
    }
}

/**
 * Represents an investment account that can buy stocks and track their value.
 */
class InvestmentAccount extends Account {
    private List<StockTransaction> transactions;

    /**
     * Constructs an empty investment account.
     */
    public InvestmentAccount() {
        super();
        this.transactions = new ArrayList<>();
    }

    /**
     * Constructs an investment account with the given identifier.
     *
     * @param id the unique identifier for the account
     */
    public InvestmentAccount(String id) {
        super(id);
        this.transactions = new ArrayList<>();
    }

    // ------------------------------------------------------------------------
    // Getter and setter
    // ------------------------------------------------------------------------

    public List<StockTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<StockTransaction> transactions) {
        this.transactions = transactions;
    }

    // ------------------------------------------------------------------------
    // Business operations
    // ------------------------------------------------------------------------

    /**
     * Attempts to buy a stock, deducting the purchase cost and commission from the account balance.
     *
     * @param stockSymbol the ticker symbol of the stock to buy
     * @param quantity    the number of shares to purchase; must be positive
     * @param price       the price per share; must be positive
     * @return {@code true} if the purchase succeeded and the transaction was recorded;
     * {@code false} if the balance is insufficient or parameters are invalid
     */
    public boolean buyStock(String stockSymbol, int quantity, double price) {
        if (stockSymbol == null || stockSymbol.isEmpty() || quantity <= 0 || price <= 0.0) {
            return false;
        }
        double cost = quantity * price;
        double commission = roundTwoDecimals(cost * 0.10); // 10% commission
        double totalDeduction = roundTwoDecimals(cost + commission);
        if (totalDeduction > getBalance()) {
            return false; // insufficient funds
        }
        // Deduct total amount from balance
        setBalance(roundTwoDecimals(getBalance() - totalDeduction));
        // Record transaction
        StockTransaction transaction = new StockTransaction();
        transaction.setStock(stockSymbol);
        transaction.setQuantity(quantity);
        transaction.setPrice(price);
        transaction.setCommission(commission);
        transactions.add(transaction);
        return true;
    }

    /**
     * Calculates the total value of the investment account, which includes the cash balance
     * and the market value of all owned stocks. The market price of each stock is assumed
     * to be 10% higher than its purchase price.
     *
     * @return the total account value rounded to two decimal places; if there are no
     * stock transactions, the value equals the cash balance
     */
    public double calculateValue() {
        double stockValue = 0.0;
        for (StockTransaction tx : transactions) {
            double marketPrice = tx.getPrice() * 1.1; // 10% increase over purchase price
            double value = tx.getQuantity() * marketPrice;
            stockValue += value;
        }
        stockValue = roundTwoDecimals(stockValue);
        double totalValue = roundTwoDecimals(getBalance() + stockValue);
        // If there are no stock transactions, totalValue equals the cash balance
        return totalValue;
    }
}

/**
 * Represents a record of a stock purchase transaction.
 */
class StockTransaction {
    private String stock;      // ticker symbol
    private int quantity;
    private double price;      // purchase price per share
    private double commission; // commission paid for this transaction

    /**
     * Constructs an empty stock transaction.
     */
    public StockTransaction() {
        // default constructor
    }

    // ------------------------------------------------------------------------
    // Getters and setters
    // ------------------------------------------------------------------------

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