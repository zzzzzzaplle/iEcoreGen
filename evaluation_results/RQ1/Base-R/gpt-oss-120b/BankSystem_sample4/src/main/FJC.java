import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a bank customer which can own multiple accounts.
 */
 class Customer {

    private String name;
    private String address;
    private Map<String, Account> accounts = new HashMap<>();

    /** Unparameterized constructor. */
    public Customer() {}

    /** Unparameterized constructor with name and address. */
    public Customer(String name, String address) {
        this.name = name;
        this.address = address;
    }

    // ---- Getters and Setters ----

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public Map<String, Account> getAccounts() { return accounts; }

    public void setAccounts(Map<String, Account> accounts) { this.accounts = accounts; }

    // ---- Functional Methods ----

    /**
     * Adds a new account to the customer.
     *
     * @param account the account to add
     * @return true if the account was added successfully, false if an account with the same ID already exists
     */
    public boolean addAccount(Account account) {
        if (account == null || accounts.containsKey(account.getId())) {
            return false;
        }
        accounts.put(account.getId(), account);
        return true;
    }

    /**
     * Removes an account from the customer by its ID.
     * The account can only be removed if its balance is zero and, for an investment account,
     * it has no stock transactions.
     *
     * @param accountId the ID of the account to remove
     * @return true if the account was removed, false otherwise
     */
    public boolean removeAccount(String accountId) {
        Account account = accounts.get(accountId);
        if (account == null) {
            return false;
        }
        if (account.getBalance().compareTo(BigDecimal.ZERO) != 0) {
            return false;
        }
        if (account instanceof InvestmentAccount) {
            InvestmentAccount inv = (InvestmentAccount) account;
            if (!inv.getTransactions().isEmpty()) {
                return false;
            }
        }
        accounts.remove(accountId);
        return true;
    }

    /**
     * Deposits money into the specified account.
     *
     * @param accountId the ID of the account to deposit into
     * @param amount    the amount to deposit
     * @return true if the deposit was successful, false otherwise
     */
    public boolean deposit(String accountId, BigDecimal amount) {
        Account account = accounts.get(accountId);
        if (account == null) {
            return false;
        }
        return account.deposit(amount);
    }
}

/**
 * Abstract base class for bank accounts.
 */
abstract class Account {

    private String id;
    private BigDecimal balance = BigDecimal.ZERO;

    /** Unparameterized constructor. */
    public Account() {}

    /** Unparameterized constructor with ID. */
    public Account(String id) {
        this.id = id;
    }

    // ---- Getters and Setters ----

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public BigDecimal getBalance() { return balance; }

    public void setBalance(BigDecimal balance) { this.balance = balance; }

    // ---- Functional Methods ----

    /**
     * Deposits money into this account.
     *
     * @param amount the amount to deposit
     * @return true if the deposit succeeded, false otherwise
     */
    public boolean deposit(BigDecimal amount) {
        if (amount == null ||
            amount.compareTo(BigDecimal.ZERO) <= 0 ||
            amount.compareTo(new BigDecimal("1000000")) > 0) {
            return false;
        }
        balance = balance.add(amount);
        return true;
    }
}

/**
 * Savings account which accrues daily interest.
 */
class SavingsAccount extends Account {

    private BigDecimal interestRate; // e.g., 0.05 for 5%

    /** Unparameterized constructor. */
    public SavingsAccount() {}

    /** Unparameterized constructor with ID and interest rate. */
    public SavingsAccount(String id, BigDecimal interestRate) {
        super(id);
        this.interestRate = interestRate;
    }

    // ---- Getters and Setters ----

    public BigDecimal getInterestRate() { return interestRate; }

    public void setInterestRate(BigDecimal interestRate) { this.interestRate = interestRate; }

    // ---- Functional Methods ----

    /**
     * Calculates the daily interest for this savings account and adds it to the balance.
     * The interest is calculated as: balance * interestRate / 360,
     * rounded to two decimal places using HALF_UP rounding mode.
     * This method should be invoked at 23:59:59 of each day.
     *
     * @return the interest amount that was added to the balance
     */
    public BigDecimal calculateDailyInterest() {
        BigDecimal balance = getBalance();
        BigDecimal interest = balance.multiply(interestRate)
                .divide(new BigDecimal("360"), 10, RoundingMode.HALF_UP);
        interest = interest.setScale(2, RoundingMode.HALF_UP);
        setBalance(balance.add(interest));
        return interest;
    }
}

/**
 * Representation of a stock transaction.
 */
class StockTransaction {

    private String ticker;
    private int quantity;
    private BigDecimal purchasePrice;
    private LocalDateTime purchaseDate;

    /** Unparameterized constructor. */
    public StockTransaction() {}

    /** Unparameterized constructor with details. */
    public StockTransaction(String ticker, int quantity, BigDecimal purchasePrice) {
        this.ticker = ticker;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.purchaseDate = LocalDateTime.now();
    }

    // ---- Getters and Setters ----

    public String getTicker() { return ticker; }

    public void setTicker(String ticker) { this.ticker = ticker; }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public BigDecimal getPurchasePrice() { return purchasePrice; }

    public void setPurchasePrice(BigDecimal purchasePrice) { this.purchasePrice = purchasePrice; }

    public LocalDateTime getPurchaseDate() { return purchaseDate; }

    public void setPurchaseDate(LocalDateTime purchaseDate) { this.purchaseDate = purchaseDate; }
}

/**
 * Investment account used to buy stocks.
 */
class InvestmentAccount extends Account {

    private static final BigDecimal COMMISSION_RATE = new BigDecimal("0.10"); // 10%
    private List<StockTransaction> transactions = new ArrayList<>();

    /** Unparameterized constructor. */
    public InvestmentAccount() {}

    /** Unparameterized constructor with ID. */
    public InvestmentAccount(String id) {
        super(id);
    }

    // ---- Getters and Setters ----

    public List<StockTransaction> getTransactions() { return transactions; }

    public void setTransactions(List<StockTransaction> transactions) { this.transactions = transactions; }

    // ---- Functional Methods ----

    /**
     * Buys a specified quantity of a stock at a given price, deducts the cost plus commission
     * from the account balance, and records the transaction.
     *
     * @param ticker        the stock ticker symbol
     * @param quantity      the number of shares to buy
     * @param purchasePrice the price per share at which the stock is bought
     * @return true if the transaction was successful, false otherwise
     */
    public boolean buyStocks(String ticker, int quantity, BigDecimal purchasePrice) {
        if (ticker == null || ticker.isEmpty() || quantity <= 0 || purchasePrice == null
                || purchasePrice.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        BigDecimal cost = purchasePrice.multiply(new BigDecimal(quantity));
        BigDecimal commission = cost.multiply(COMMISSION_RATE);
        BigDecimal totalCost = cost.add(commission);

        if (getBalance().compareTo(totalCost) < 0) {
            return false; // insufficient funds
        }

        // Deduct total cost
        setBalance(getBalance().subtract(totalCost));

        // Record transaction
        StockTransaction txn = new StockTransaction(ticker, quantity, purchasePrice);
        transactions.add(txn);
        return true;
    }

    /**
     * Calculates the total value of this investment account which consists of the current
     * account balance plus the market value of all held stocks.
     * The market value of each stock is considered as 1.1 times its purchase price.
     *
     * @return the total value rounded to two decimal places, or BigDecimal.ZERO if no transactions exist
     */
    public BigDecimal calculateInvestmentValue() {
        if (transactions.isEmpty()) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }

        BigDecimal stockValue = BigDecimal.ZERO;
        for (StockTransaction txn : transactions) {
            BigDecimal currentPrice = txn.getPurchasePrice().multiply(new BigDecimal("1.1"));
            BigDecimal value = currentPrice.multiply(new BigDecimal(txn.getQuantity()));
            stockValue = stockValue.add(value);
        }

        BigDecimal total = getBalance().add(stockValue);
        return total.setScale(2, RoundingMode.HALF_UP);
    }
}