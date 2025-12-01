import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents a bank customer. A customer has a name, an address and a list of accounts.
 */
 class Customer {

    private String name;
    private String address;
    private List<Account> accounts = new ArrayList<>();

    /** Unparameterized constructor */
    public Customer() {
    }

    /** Parameterized constructor for convenience */
    public Customer(String name, String address) {
        this.name = name;
        this.address = address;
    }

    /* -------------------- getters & setters -------------------- */

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
     * Adds an account to this customer.
     *
     * @param account the account to add
     */
    public void addAccount(Account account) {
        if (account != null) {
            accounts.add(account);
        }
    }

    /**
     * Removes an account identified by {@code accountId}.
     * <p>
     * The account can only be removed when:
     * <ul>
     *   <li>its balance is zero, and</li>
     *   <li>for investment accounts, there are no stock transactions.</li>
     * </ul>
     *
     * @param accountId the unique identifier of the account to remove
     * @return {@code true} if the account was successfully removed; {@code false} otherwise
     */
    public boolean removeAccount(String accountId) {
        if (accountId == null) {
            return false;
        }
        Iterator<Account> it = accounts.iterator();
        while (it.hasNext()) {
            Account acc = it.next();
            if (accountId.equals(acc.getId())) {
                if (acc.getBalance() != 0) {
                    return false; // balance not zero
                }
                if (acc instanceof InvestmentAccount) {
                    InvestmentAccount inv = (InvestmentAccount) acc;
                    if (!inv.getTransactions().isEmpty()) {
                        return false; // has stock transactions
                    }
                }
                // All conditions satisfied – remove
                it.remove();
                return true;
            }
        }
        return false; // not found
    }
}

/**
 * Abstract base class for all account types.
 */
public abstract class Account {

    private String id;
    private double balance;

    /** Unparameterized constructor */
    public Account() {
    }

    /** Parameterized constructor for convenience */
    public Account(String id, double balance) {
        this.id = id;
        this.balance = balance;
    }

    /* -------------------- getters & setters -------------------- */

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
     * Deposits the specified {@code amount} into the account.
     * <p>
     * The amount must be positive and may not exceed the maximum single‑deposit limit
     * of $1,000,000.
     *
     * @param amount the amount to deposit
     * @return {@code true} if the deposit succeeded; {@code false} otherwise
     */
    public boolean deposit(double amount) {
        if (amount <= 0 || amount > 1_000_000) {
            return false;
        }
        this.balance = roundTwoDecimals(this.balance + amount);
        return true;
    }

    /**
     * Helper method to round a double value to two decimal places using {@link BigDecimal}.
     *
     * @param value the value to round
     * @return the rounded value
     */
    protected double roundTwoDecimals(double value) {
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}

/**
 * Savings account that accrues daily interest.
 */
 class SavingsAccount extends Account {

    private double interestRate; // expressed as a decimal, e.g., 0.05 for 5%

    /** Unparameterized constructor */
    public SavingsAccount() {
    }

    /** Parameterized constructor for convenience */
    public SavingsAccount(String id, double balance, double interestRate) {
        super(id, balance);
        this.interestRate = interestRate;
    }

    /* -------------------- getters & setters -------------------- */

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * Calculates the daily interest for this savings account and adds it to the balance.
     * <p>
     * Daily interest = {@code balance * interestRate / 360}. The result is rounded to two
     * decimal places before being added to the balance. In a real system this method would
     * be scheduled to run at 23:59:59 each day; here it is invoked manually.
     */
    public void calculateAndApplyDailyInterest() {
        double dailyInterest = (getBalance() * interestRate) / 360.0;
        dailyInterest = roundTwoDecimals(dailyInterest);
        setBalance(roundTwoDecimals(getBalance() + dailyInterest));
    }
}

/**
 * Investment account that can purchase stocks.
 */
 class InvestmentAccount extends Account {

    private List<StockTransaction> transactions = new ArrayList<>();

    /** Unparameterized constructor */
    public InvestmentAccount() {
    }

    /** Parameterized constructor for convenience */
    public InvestmentAccount(String id, double balance) {
        super(id, balance);
    }

    /* -------------------- getters & setters -------------------- */

    public List<StockTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<StockTransaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * Purchases a stock and records the transaction.
     * <p>
     * The cost of the purchase is {@code quantity * price}. The bank charges a commission
     * of 10% of the cost. The account balance must be sufficient to cover {@code cost +
     * commission}. If the purchase succeeds, the balance is decreased by the total amount
     * and a {@link StockTransaction} is stored.
     *
     * @param ticker   the stock ticker symbol
     * @param quantity the number of shares to buy (must be positive)
     * @param price    the purchase price per share (must be positive)
     * @return {@code true} if the transaction was recorded successfully; {@code false}
     *         otherwise (e.g., insufficient funds or invalid parameters)
     */
    public boolean buyStock(String ticker, int quantity, double price) {
        if (ticker == null || ticker.isEmpty() || quantity <= 0 || price <= 0) {
            return false;
        }
        double cost = quantity * price;
        double commission = cost * 0.10; // 10% commission
        double totalExpense = cost + commission;

        if (totalExpense > getBalance()) {
            return false; // insufficient balance
        }

        // Deduct total expense
        setBalance(roundTwoDecimals(getBalance() - totalExpense));

        // Record transaction
        StockTransaction tx = new StockTransaction();
        tx.setTicker(ticker);
        tx.setQuantity(quantity);
        tx.setPurchasePrice(price);
        transactions.add(tx);
        return true;
    }

    /**
     * Calculates the total current value of the investment account.
     * <p>
     * The value consists of the cash balance plus the market value of all owned stocks.
     * The market price of each stock is defined as {@code 1.1 * purchasePrice}.
     * The result is rounded to two decimal places. If there are no stock transactions,
     * the method returns {@code 0.0}.
     *
     * @return the total value of the investment account, rounded to two decimal places
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

    private String ticker;
    private int quantity;
    private double purchasePrice; // price per share at purchase time

    /** Unparameterized constructor */
    public StockTransaction() {
    }

    /** Parameterized constructor for convenience */
    public StockTransaction(String ticker, int quantity, double purchasePrice) {
        this.ticker = ticker;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
    }

    /* -------------------- getters & setters -------------------- */

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
}