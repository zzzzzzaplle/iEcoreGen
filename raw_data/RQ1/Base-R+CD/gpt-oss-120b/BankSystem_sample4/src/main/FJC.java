import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents a bank customer. A customer has a name, an address and a collection of accounts.
 */
 class Customer {

    private String name;
    private String address;
    private List<Account> accounts;

    /**
     * Creates a new {@code Customer} with empty account list.
     */
    public Customer() {
        this.accounts = new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    /**
     * Returns the customer's name.
     *
     * @return the name of the customer
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the customer's name.
     *
     * @param name the name to set for the customer
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the customer's address.
     *
     * @return the address of the customer
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the customer's address.
     *
     * @param address the address to set for the customer
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns the list of accounts owned by the customer.
     *
     * @return a mutable list of {@code Account}s
     */
    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * Sets the list of accounts owned by the customer.
     *
     * @param accounts the list of accounts to associate with the customer
     */
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    // -------------------------------------------------------------------------
    // Business Operations
    // -------------------------------------------------------------------------

    /**
     * Adds a new savings account to the customer.
     *
     * @param id           the unique identifier for the new account
     * @param interestRate the interest rate for the savings account (e.g. 0.05 for 5%)
     * @return {@code true} if the account was added successfully; {@code false} if an
     *         account with the same id already exists
     */
    public boolean addSavingAccount(String id, double interestRate) {
        if (findAccountById(id) != null) {
            return false; // duplicate id
        }
        SavingAccount account = new SavingAccount();
        account.setId(id);
        account.setInterestRate(interestRate);
        accounts.add(account);
        return true;
    }

    /**
     * Adds a new investment account to the customer.
     *
     * @param id the unique identifier for the new account
     * @return {@code true} if the account was added successfully; {@code false} if an
     *         account with the same id already exists
     */
    public boolean addInvestmentAccount(String id) {
        if (findAccountById(id) != null) {
            return false; // duplicate id
        }
        InvestmentAccount account = new InvestmentAccount();
        account.setId(id);
        accounts.add(account);
        return true;
    }

    /**
     * Removes a savings account from the customer.
     *
     * @param id the identifier of the account to be removed
     * @return {@code true} if the account existed, had a zero balance and was removed;
     *         {@code false} otherwise
     */
    public boolean removeSavingAccount(String id) {
        Account acc = findAccountById(id);
        if (acc instanceof SavingAccount) {
            if (Math.abs(acc.getBalance()) < 0.0001) { // balance is zero (allowing floating‑point tolerance)
                accounts.remove(acc);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes an investment account from the customer.
     *
     * @param id the identifier of the account to be removed
     * @return {@code true} if the account existed, had a zero balance, contained no
     *         stock transactions and was removed; {@code false} otherwise
     */
    public boolean removeInvestmentAccount(String id) {
        Account acc = findAccountById(id);
        if (acc instanceof InvestmentAccount) {
            InvestmentAccount invAcc = (InvestmentAccount) acc;
            if (Math.abs(invAcc.getBalance()) < 0.0001 && invAcc.getTransactions().isEmpty()) {
                accounts.remove(invAcc);
                return true;
            }
        }
        return false;
    }

    /**
     * Finds an account belonging to the customer by its identifier.
     *
     * @param id the identifier of the account to locate
     * @return the {@code Account} with the given id, or {@code null} if none exists
     */
    public Account findAccountById(String id) {
        for (Account a : accounts) {
            if (a.getId() != null && a.getId().equals(id)) {
                return a;
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
     * Creates a new {@code Account} with a zero balance.
     */
    public Account() {
        this.balance = 0.0;
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    /**
     * Returns the unique identifier of the account.
     *
     * @return the account id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the account.
     *
     * @param id the id to assign to the account
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the current balance of the account.
     *
     * @return the balance as a {@code double}
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
        this.balance = roundTwoDecimals(balance);
    }

    // -------------------------------------------------------------------------
    // Core Operations
    // -------------------------------------------------------------------------

    /**
     * Deposits a positive amount into the account, respecting the maximum single
     * deposit limit of $1,000,000.
     *
     * @param amount the amount to deposit; must be greater than 0 and not exceed
     *               1,000,000
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
     * Utility method to round a {@code double} to two decimal places using
     * {@link RoundingMode#HALF_UP}.
     *
     * @param value the value to round
     * @return the rounded value
     */
    protected double roundTwoDecimals(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}

/**
 * Savings account that accrues daily interest.
 */
class SavingAccount extends Account {

    private double interestRate; // e.g. 0.05 for 5%

    /**
     * Creates a new {@code SavingAccount} with zero interest rate.
     */
    public SavingAccount() {
        super();
        this.interestRate = 0.0;
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    /**
     * Returns the interest rate applied to this savings account.
     *
     * @return the interest rate as a decimal (e.g. 0.04 for 4%)
     */
    public double getInterestRate() {
        return interestRate;
    }

    /**
     * Sets the interest rate for this savings account.
     *
     * @param interestRate the interest rate to set (as a decimal)
     */
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    // -------------------------------------------------------------------------
    // Business Operations
    // -------------------------------------------------------------------------

    /**
     * Calculates the daily interest based on the current balance and the interest
     * rate, adds it to the balance and returns the interest amount.
     *
     * <p>Daily interest = {@code balance * interestRate / 360}. The interest and the
     * updated balance are rounded to two decimal places.</p>
     *
     * @return the interest amount that was added to the balance
     */
    public double calculateDailyInterest() {
        double currentBalance = getBalance();
        double rawInterest = currentBalance * interestRate / 360.0;
        double interest = roundTwoDecimals(rawInterest);
        setBalance(currentBalance + interest);
        return interest;
    }
}

/**
 * Investment account that can hold stock transactions.
 */
class InvestmentAccount extends Account {

    private List<StockTransaction> transactions;

    /**
     * Creates a new {@code InvestmentAccount} with an empty list of transactions.
     */
    public InvestmentAccount() {
        super();
        this.transactions = new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    /**
     * Returns the list of stock transactions performed on this account.
     *
     * @return a mutable list of {@code StockTransaction}s
     */
    public List<StockTransaction> getTransactions() {
        return transactions;
    }

    /**
     * Sets the list of stock transactions for this account.
     *
     * @param transactions the list to associate with the account
     */
    public void setTransactions(List<StockTransaction> transactions) {
        this.transactions = transactions;
    }

    // -------------------------------------------------------------------------
    // Business Operations
    // -------------------------------------------------------------------------

    /**
     * Purchases a stock and records the transaction.
     *
     * <p>The total cost of the purchase is {@code quantity * price} plus a commission
     * of 10% of that cost. The method checks that the account balance is sufficient
     * to cover the total cost before performing the purchase.</p>
     *
     * @param stockSymbol the ticker symbol of the stock to buy
     * @param quantity    the number of shares to purchase (must be positive)
     * @param price       the price per share (must be positive)
     * @return {@code true} if the purchase succeeded and the transaction was recorded;
     *         {@code false} otherwise
     */
    public boolean buyStock(String stockSymbol, int quantity, double price) {
        if (stockSymbol == null || stockSymbol.isEmpty() || quantity <= 0 || price <= 0) {
            return false;
        }
        double cost = quantity * price;
        double commission = roundTwoDecimals(cost * 0.10); // 10% commission
        double total = roundTwoDecimals(cost + commission);

        if (getBalance() < total) {
            return false; // insufficient funds
        }

        // Deduct total cost from balance
        setBalance(getBalance() - total);

        // Record transaction
        StockTransaction tx = new StockTransaction();
        tx.setStock(stockSymbol);
        tx.setQuantity(quantity);
        tx.setPrice(price);
        tx.setCommission(commission);
        transactions.add(tx);
        return true;
    }

    /**
     * Calculates the total value of the investment account, which consists of the
     * current cash balance plus the market value of all owned stocks.
     *
     * <p>The market price of each stock is assumed to be 1.1 times its purchase
     * price. The result is rounded to two decimal places. If there are no stock
     * transactions, the method returns {@code 0.0}.</p>
     *
     * @return the total value of the investment account, or {@code 0.0} if no
     *         stock transactions exist
     */
    public double calculateValue() {
        if (transactions.isEmpty()) {
            return 0.0;
        }
        double stockValue = 0.0;
        for (StockTransaction tx : transactions) {
            double marketPrice = tx.getPrice() * 1.1; // 10% increase over purchase price
            stockValue += tx.getQuantity() * marketPrice;
        }
        double total = getBalance() + stockValue;
        return roundTwoDecimals(total);
    }
}

/**
 * Represents a single stock purchase transaction.
 */
class StockTransaction {

    private String stock;      // ticker symbol
    private int quantity;
    private double price;      // purchase price per share
    private double commission; // commission paid for this transaction

    /**
     * Creates a new {@code StockTransaction} with default values.
     */
    public StockTransaction() {
        // default constructor
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    /**
     * Returns the ticker symbol of the stock.
     *
     * @return the stock symbol
     */
    public String getStock() {
        return stock;
    }

    /**
     * Sets the ticker symbol of the stock.
     *
     * @param stock the stock symbol to set
     */
    public void setStock(String stock) {
        this.stock = stock;
    }

    /**
     * Returns the quantity of shares purchased.
     *
     * @return the number of shares
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of shares purchased.
     *
     * @param quantity the number of shares
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Returns the purchase price per share.
     *
     * @return the price per share
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the purchase price per share.
     *
     * @param price the price per share
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returns the commission paid for this transaction.
     *
     * @return the commission amount
     */
    public double getCommission() {
        return commission;
    }

    /**
     * Sets the commission paid for this transaction.
     *
     * @param commission the commission amount
     */
    public void setCommission(double commission) {
        this.commission = commission;
    }
}