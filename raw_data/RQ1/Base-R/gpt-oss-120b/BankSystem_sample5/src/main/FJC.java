import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

/**
 * Represents a bank customer.
 * A customer has a name, an address and a collection of accounts.
 */
 class Customer {

    /** Customer's full name */
    private String name;

    /** Customer's address */
    private String address;

    /** List of accounts owned by the customer */
    private List<Account> accounts;

    /** Unparameterized constructor required for testing */
    public Customer() {
        this.accounts = new ArrayList<>();
    }

    /** Constructor with basic fields */
    public Customer(String name, String address) {
        this.name = name;
        this.address = address;
        this.accounts = new ArrayList<>();
    }

    // -----------------------------------------------------------------------
    // Getters and Setters
    // -----------------------------------------------------------------------

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

    // -----------------------------------------------------------------------
    // Business methods
    // -----------------------------------------------------------------------

    /**
     * Adds a new account to the customer.
     *
     * @param account the account to be added; must not be {@code null}
     */
    public void addAccount(Account account) {
        if (account != null && !hasAccount(account.getId())) {
            accounts.add(account);
        }
    }

    /**
     * Removes an account identified by its unique id.
     * <p>
     * The account can be removed only when:
     * <ul>
     *   <li>its balance is zero, and</li>
     *   <li>it has no stock transactions (applicable only for investment accounts).</li>
     * </ul>
     *
     * @param accountId the unique identifier of the account to be removed
     * @return {@code true} if the account was successfully removed; {@code false}
     *         otherwise (account not found or constraints not satisfied)
     */
    public boolean removeAccount(String accountId) {
        Iterator<Account> iterator = accounts.iterator();
        while (iterator.hasNext()) {
            Account acc = iterator.next();
            if (acc.getId().equals(accountId)) {
                boolean canRemove = acc.getBalance() == 0.0;
                if (acc instanceof InvestmentAccount) {
                    canRemove = canRemove && ((InvestmentAccount) acc).getTransactions().isEmpty();
                }
                if (canRemove) {
                    iterator.remove();
                    return true;
                }
                return false;
            }
        }
        return false; // account not found
    }

    /**
     * Checks whether a customer already owns an account with the given id.
     *
     * @param accountId the id to be checked
     * @return {@code true} if an account with the supplied id exists; {@code false}
     *         otherwise
     */
    public boolean hasAccount(String accountId) {
        for (Account acc : accounts) {
            if (acc.getId().equals(accountId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves an account by its id.
     *
     * @param accountId the unique identifier of the desired account
     * @return the {@link Account} if found; {@code null} otherwise
     */
    public Account getAccountById(String accountId) {
        for (Account acc : accounts) {
            if (acc.getId().equals(accountId)) {
                return acc;
            }
        }
        return null;
    }
}

/**
 * Abstract base class for all account types.
 */
public abstract class Account {

    /** Unique identifier for the account */
    private String id;

    /** Current balance of the account */
    private double balance;

    /** Unparameterized constructor required for testing */
    public Account() {
        // default values
    }

    /** Constructor with id and initial balance */
    public Account(String id, double balance) {
        this.id = id;
        this.balance = balance;
    }

    // -----------------------------------------------------------------------
    // Getters and Setters
    // -----------------------------------------------------------------------

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

    // -----------------------------------------------------------------------
    // Business methods common to all accounts
    // -----------------------------------------------------------------------

    /**
     * Deposits money into the account.
     * <p>
     * The amount must be positive and must not exceed the maximum single
     * deposit limit of $1,000,000.
     *
     * @param amount the amount to deposit
     * @return {@code true} if the deposit succeeded; {@code false} otherwise
     */
    public boolean deposit(double amount) {
        if (amount <= 0.0 || amount > 1_000_000.0) {
            return false;
        }
        this.balance = roundTwoDecimals(this.balance + amount);
        return true;
    }

    /**
     * Helper method to round a double value to two decimal places.
     *
     * @param value the value to round
     * @return the rounded value
     */
    protected double roundTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}

/**
 * Savings account that earns daily interest.
 */
 class SavingsAccount extends Account {

    /** Interest rate expressed as a decimal (e.g., 0.05 for 5%) */
    private double interestRate;

    /** Unparameterized constructor required for testing */
    public SavingsAccount() {
        super();
    }

    /** Constructor with all fields */
    public SavingsAccount(String id, double balance, double interestRate) {
        super(id, balance);
        this.interestRate = interestRate;
    }

    // -----------------------------------------------------------------------
    // Getters and Setters
    // -----------------------------------------------------------------------

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    // -----------------------------------------------------------------------
    // Business methods
    // -----------------------------------------------------------------------

    /**
     * Calculates the daily interest for the current balance and adds it to the
     * account balance. The calculation follows:
     * <pre>
     *   dailyInterest = balance * interestRate / 360
     * </pre>
     * The interest is rounded to two decimal places before being added.
     *
     * @return the amount of interest that was added to the balance
     */
    public double applyDailyInterest() {
        double dailyInterest = (getBalance() * interestRate) / 360.0;
        dailyInterest = roundTwoDecimals(dailyInterest);
        setBalance(roundTwoDecimals(getBalance() + dailyInterest));
        return dailyInterest;
    }
}

/**
 * Investment account used to purchase stocks.
 */
 class InvestmentAccount extends Account {

    /** List of stock purchase transactions */
    private List<StockTransaction> transactions;

    /** Commission rate (10% of transaction cost) */
    private static final double COMMISSION_RATE = 0.10;

    /** Unparameterized constructor required for testing */
    public InvestmentAccount() {
        super();
        this.transactions = new ArrayList<>();
    }

    /** Constructor with id and initial balance */
    public InvestmentAccount(String id, double balance) {
        super(id, balance);
        this.transactions = new ArrayList<>();
    }

    // -----------------------------------------------------------------------
    // Getters and Setters
    // -----------------------------------------------------------------------

    public List<StockTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<StockTransaction> transactions) {
        this.transactions = transactions;
    }

    // -----------------------------------------------------------------------
    // Business methods
    // -----------------------------------------------------------------------

    /**
     * Attempts to buy a stock and records the transaction.
     * <p>
     * The total cost of the purchase is:
     * <pre>
     *   stockCost = quantity * pricePerUnit
     *   commission = stockCost * 0.10
     *   totalDeduction = stockCost + commission
     * </pre>
     * The method checks that the account balance can cover {@code totalDeduction}.
     * If sufficient, the balance is reduced and the transaction is stored.
     *
     * @param ticker   the stock ticker symbol
     * @param quantity number of shares to purchase (must be positive)
     * @param price    purchase price per share (must be positive)
     * @return {@code true} if the transaction was recorded successfully; {@code false}
     *         otherwise (insufficient funds or invalid parameters)
     */
    public boolean buyStock(String ticker, int quantity, double price) {
        if (ticker == null || ticker.isEmpty() || quantity <= 0 || price <= 0.0) {
            return false;
        }

        double stockCost = quantity * price;
        double commission = stockCost * COMMISSION_RATE;
        double totalDeduction = stockCost + commission;

        if (totalDeduction > getBalance()) {
            return false; // insufficient funds
        }

        // update balance
        setBalance(roundTwoDecimals(getBalance() - totalDeduction));

        // record transaction
        StockTransaction transaction = new StockTransaction(ticker, quantity, price, commission);
        transactions.add(transaction);
        return true;
    }

    /**
     * Calculates the total value of the investment account, comprising:
     * <ul>
     *   <li>Current cash balance.</li>
     *   <li>Total market value of all owned stocks (quantity * market price).</li>
     * </ul>
     * The market price for each stock is assumed to be 1.1 times its purchase price.
     *
     * @return the total account value rounded to two decimal places; returns
     *         {@code 0.0} if there are no stock transactions.
     */
    public double calculateTotalValue() {
        if (transactions.isEmpty()) {
            return 0.0;
        }

        double stocksValue = 0.0;
        for (StockTransaction tx : transactions) {
            double marketPrice = tx.getPurchasePrice() * 1.1;
            stocksValue += tx.getQuantity() * marketPrice;
        }

        double total = getBalance() + stocksValue;
        return roundTwoDecimals(total);
    }
}

/**
 * Represents a single stock purchase transaction.
 */
 class StockTransaction {

    /** Stock ticker symbol */
    private String ticker;

    /** Number of shares purchased */
    private int quantity;

    /** Purchase price per share */
    private double purchasePrice;

    /** Commission paid for this transaction */
    private double commission;

    /** Unparameterized constructor required for testing */
    public StockTransaction() {
    }

    /** Full constructor */
    public StockTransaction(String ticker, int quantity, double purchasePrice, double commission) {
        this.ticker = ticker;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.commission = commission;
    }

    // -----------------------------------------------------------------------
    // Getters and Setters
    // -----------------------------------------------------------------------

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