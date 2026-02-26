import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents a bank customer who can own multiple accounts.
 */
 class Customer {

    private String name;
    private String address;
    private List<Account> accounts;

    /** Default constructor required by the specification. */
    public Customer() {
        this.accounts = new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

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

    // -------------------------------------------------------------------------
    // Business methods
    // -------------------------------------------------------------------------

    /**
     * Adds a new saving account with the given id and interest rate.
     *
     * @param id           the unique identifier for the account
     * @param interestRate the interest rate (e.g., 0.05 for 5%)
     * @return {@code true} if the account was added, {@code false} if an account
     *         with the same id already exists
     */
    public boolean addSavingAccount(String id, double interestRate) {
        if (findAccountById(id) != null) {
            return false; // id already used
        }
        SavingAccount sa = new SavingAccount();
        sa.setId(id);
        sa.setInterestRate(interestRate);
        sa.setBalance(0.0);
        accounts.add(sa);
        return true;
    }

    /**
     * Adds a new investment account with the given id.
     *
     * @param id the unique identifier for the account
     * @return {@code true} if the account was added, {@code false} if an account
     *         with the same id already exists
     */
    public boolean addInvestmentAccount(String id) {
        if (findAccountById(id) != null) {
            return false;
        }
        InvestmentAccount ia = new InvestmentAccount();
        ia.setId(id);
        ia.setBalance(0.0);
        ia.setTransactions(new ArrayList<>());
        accounts.add(ia);
        return true;
    }

    /**
     * Removes a saving account identified by {@code id}. The account can be
     * removed only when its balance is zero.
     *
     * @param id the identifier of the account to remove
     * @return {@code true} if removal succeeded, {@code false} otherwise
     */
    public boolean removeSavingAccount(String id) {
        Account acc = findAccountById(id);
        if (acc instanceof SavingAccount) {
            if (Math.abs(acc.getBalance()) < 0.000001) {
                accounts.remove(acc);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes an investment account identified by {@code id}. The account can be
     * removed only when its balance is zero **and** it has no stock transactions.
     *
     * @param id the identifier of the account to remove
     * @return {@code true} if removal succeeded, {@code false} otherwise
     */
    public boolean removeInvestmentAccount(String id) {
        Account acc = findAccountById(id);
        if (acc instanceof InvestmentAccount) {
            InvestmentAccount ia = (InvestmentAccount) acc;
            if (Math.abs(ia.getBalance()) < 0.000001 && ia.getTransactions().isEmpty()) {
                accounts.remove(acc);
                return true;
            }
        }
        return false;
    }

    /**
     * Searches the customer's accounts for an account with the given id.
     *
     * @param id the account identifier
     * @return the {@link Account} if found, otherwise {@code null}
     */
    public Account findAccountById(String id) {
        for (Account a : accounts) {
            if (Objects.equals(a.getId(), id)) {
                return a;
            }
        }
        return null;
    }
}

/**
 * Abstract base class for all bank accounts.
 */
abstract class Account {

    private String id;
    private double balance;

    /** Default constructor required by the specification. */
    public Account() {
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the current balance of the account.
     *
     * @return the balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Sets the account balance.
     *
     * @param balance the new balance value
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    // -------------------------------------------------------------------------
    // Business methods
    // -------------------------------------------------------------------------

    /**
     * Deposits a positive amount into the account, respecting the maximum
     * single‑deposit limit of $1,000,000.
     *
     * @param amount the amount to deposit
     * @return {@code true} if the deposit succeeded, {@code false} otherwise
     */
    public boolean deposit(double amount) {
        if (amount <= 0 || amount > 1_000_000) {
            return false;
        }
        this.balance = roundTwoDecimals(this.balance + amount);
        return true;
    }

    /**
     * Utility method to round a double to two decimal places using
     * {@link BigDecimal}.
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
class SavingAccount extends Account {

    private double interestRate; // e.g., 0.03 for 3%

    /** Default constructor required by the specification. */
    public SavingAccount() {
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    // -------------------------------------------------------------------------
    // Business methods
    // -------------------------------------------------------------------------

    /**
     * Calculates the daily interest using the formula:
     * <pre>
     *   dailyInterest = balance * interestRate / 360
     * </pre>
     * The interest is rounded to two decimal places and added to the account
     * balance (as would happen at 23:59:59).
     *
     * @return the calculated daily interest amount
     */
    public double calculateDailyInterest() {
        double dailyInterest = getBalance() * interestRate / 360.0;
        dailyInterest = roundTwoDecimals(dailyInterest);
        // update balance as if at the end of the day
        setBalance(roundTwoDecimals(getBalance() + dailyInterest));
        return dailyInterest;
    }
}

/**
 * Investment account used for buying stocks.
 */
class InvestmentAccount extends Account {

    private List<StockTransaction> transactions;

    /** Default constructor required by the specification. */
    public InvestmentAccount() {
        this.transactions = new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    public List<StockTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<StockTransaction> transactions) {
        this.transactions = transactions;
    }

    // -------------------------------------------------------------------------
    // Business methods
    // -------------------------------------------------------------------------

    /**
     * Purchases a stock, creating a transaction record and deducting the total
     * cost (stock cost + commission) from the account balance.
     *
     * @param stockSymbol the ticker symbol of the stock
     * @param quantity    number of shares to buy (must be positive)
     * @param price       price per share (must be positive)
     * @return {@code true} if the purchase succeeded and the transaction was
     *         recorded, {@code false} otherwise (e.g., insufficient balance)
     */
    public boolean buyStock(String stockSymbol, int quantity, double price) {
        if (quantity <= 0 || price <= 0) {
            return false;
        }

        double stockCost = quantity * price;
        double commission = roundTwoDecimals(stockCost * 0.10); // 10% commission
        double totalCost = roundTwoDecimals(stockCost + commission);

        if (totalCost > getBalance()) {
            return false; // insufficient funds
        }

        // deduct total cost from balance
        setBalance(roundTwoDecimals(getBalance() - totalCost));

        // record transaction
        StockTransaction tx = new StockTransaction();
        tx.setStock(stockSymbol);
        tx.setQuantity(quantity);
        tx.setPrice(price);
        tx.setCommission(commission);
        transactions.add(tx);
        return true;
    }

    /**
     * Calculates the total value of the investment account, which consists of
     * the current cash balance plus the market value of all owned stocks.
     * <p>
     * The market price of a stock is defined as 1.1 × its purchase price.
     *
     * @return the total account value rounded to two decimal places, or {@code 0}
     *         if no stock transactions exist
     */
    public double calculateValue() {
        if (transactions.isEmpty()) {
            return 0.0;
        }

        double stocksValue = 0.0;
        for (StockTransaction tx : transactions) {
            double marketPricePerShare = roundTwoDecimals(tx.getPrice() * 1.1);
            stocksValue += tx.getQuantity() * marketPricePerShare;
        }

        double total = roundTwoDecimals(getBalance() + stocksValue);
        return total;
    }
}

/**
 * Represents a single stock purchase transaction.
 */
class StockTransaction {

    private String stock;
    private int quantity;
    private double price;
    private double commission;

    /** Default constructor required by the specification. */
    public StockTransaction() {
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

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