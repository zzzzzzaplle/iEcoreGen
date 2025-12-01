import java.util.ArrayList;
import java.util.List;

/**
 * Represents a bank customer.
 */
 class Customer {

    /** Customer's full name. */
    private String name;

    /** Customer's residential address. */
    private String address;

    /** List of accounts owned by the customer. */
    private List<Account> accounts;

    /**
     * No‑argument constructor.
     * Initializes the accounts list.
     */
    public Customer() {
        this.accounts = new ArrayList<>();
    }

    /* ==================== Getters and Setters ==================== */

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
     * @return a mutable list of accounts
     */
    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * Sets the list of accounts owned by the customer.
     *
     * @param accounts the list of accounts to set
     */
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    /* ==================== Business Operations ==================== */

    /**
     * Adds a new savings account to the customer.
     *
     * @param id           the unique identifier for the new account
     * @param interestRate the interest rate for the savings account (e.g., 0.05 for 5%)
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
        account.setBalance(0.0);
        accounts.add(account);
        return true;
    }

    /**
     * Adds a new investment account to the customer.
     *
     * @param id the unique identifier for the new account
     * @return {@code true} if the account was added successfully; {@code false}
     *         if an account with the same id already exists
     */
    public boolean addInvestmentAccount(String id) {
        if (findAccountById(id) != null) {
            return false; // duplicate id
        }
        InvestmentAccount account = new InvestmentAccount();
        account.setId(id);
        account.setBalance(0.0);
        account.setTransactions(new ArrayList<>());
        accounts.add(account);
        return true;
    }

    /**
     * Removes a savings account from the customer.
     *
     * @param id the identifier of the savings account to remove
     * @return {@code true} if the account was removed; {@code false} if the
     *         account does not exist, is not a savings account, or has a non‑zero
     *         balance
     */
    public boolean removeSavingAccount(String id) {
        Account acc = findAccountById(id);
        if (acc instanceof SavingAccount) {
            if (Math.abs(acc.getBalance()) < 0.000001) { // balance == 0
                accounts.remove(acc);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes an investment account from the customer.
     *
     * @param id the identifier of the investment account to remove
     * @return {@code true} if the account was removed; {@code false} if the
     *         account does not exist, is not an investment account, has a non‑zero
     *         balance, or contains any stock transactions
     */
    public boolean removeInvestmentAccount(String id) {
        Account acc = findAccountById(id);
        if (acc instanceof InvestmentAccount) {
            InvestmentAccount invAcc = (InvestmentAccount) acc;
            if (Math.abs(invAcc.getBalance()) < 0.000001 && invAcc.getTransactions().isEmpty()) {
                accounts.remove(invAcc);
                return true;
            }
        }
        return false;
    }

    /**
     * Finds an account owned by the customer using its unique identifier.
     *
     * @param id the identifier of the account to find
     * @return the {@link Account} with the matching id, or {@code null} if not found
     */
    public Account findAccountById(String id) {
        for (Account acc : accounts) {
            if (acc.getId() != null && acc.getId().equals(id)) {
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

    /** Unique identifier for the account. */
    private String id;

    /** Current balance of the account. */
    private double balance;

    /**
     * No‑argument constructor.
     */
    public Account() {
        // default values (null id, 0 balance)
    }

    /* ==================== Getters and Setters ==================== */

    /**
     * Returns the account identifier.
     *
     * @return the account id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the account identifier.
     *
     * @param id the id to set for the account
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the current balance of the account.
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

    /* ==================== Business Operations ==================== */

    /**
     * Deposits a positive amount into the account.
     * <p>
     * The amount must be greater than zero and cannot exceed the maximum single
     * deposit limit of $1,000,000.
     *
     * @param amount the amount to deposit
     * @return {@code true} if the deposit succeeded; {@code false} otherwise
     */
    public boolean deposit(double amount) {
        if (amount <= 0.0 || amount > 1_000_000.0) {
            return false;
        }
        this.balance += amount;
        return true;
    }
}

/**
 * Savings account that accrues daily interest.
 */
class SavingAccount extends Account {

    /** Annual interest rate expressed as a decimal (e.g., 0.05 for 5%). */
    private double interestRate;

    /**
     * No‑argument constructor.
     */
    public SavingAccount() {
        // nothing to initialise beyond the super class
    }

    /* ==================== Getters and Setters ==================== */

    /**
     * Returns the interest rate for this savings account.
     *
     * @return the interest rate as a decimal
     */
    public double getInterestRate() {
        return interestRate;
    }

    /**
     * Sets the interest rate for this savings account.
     *
     * @param interestRate the interest rate to set (e.g., 0.04 for 4%)
     */
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    /* ==================== Business Operations ==================== */

    /**
     * Calculates the daily interest for the account and adds it to the balance.
     * <p>
     * Daily interest is computed as:
     * <pre>
     *   interest = balance × interestRate ÷ 360
     * </pre>
     * The result is rounded to two decimal places before being added to the
     * balance.
     *
     * @return the amount of interest added to the balance (rounded to two decimals)
     */
    public double calculateDailyInterest() {
        double rawInterest = getBalance() * interestRate / 360.0;
        double roundedInterest = roundToTwoDecimals(rawInterest);
        setBalance(getBalance() + roundedInterest);
        return roundedInterest;
    }

    /**
     * Utility method to round a double value to two decimal places using
     * half‑up rounding.
     *
     * @param value the value to round
     * @return the rounded value
     */
    private double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}

/**
 * Investment account used to buy stocks.
 */
class InvestmentAccount extends Account {

    /** List of stock purchase transactions performed on this account. */
    private List<StockTransaction> transactions;

    /**
     * No‑argument constructor.
     */
    public InvestmentAccount() {
        this.transactions = new ArrayList<>();
    }

    /* ==================== Getters and Setters ==================== */

    /**
     * Returns the list of stock transactions.
     *
     * @return mutable list of {@link StockTransaction}
     */
    public List<StockTransaction> getTransactions() {
        return transactions;
    }

    /**
     * Sets the list of stock transactions.
     *
     * @param transactions the list to set
     */
    public void setTransactions(List<StockTransaction> transactions) {
        this.transactions = transactions;
    }

    /* ==================== Business Operations ==================== */

    /**
     * Purchases a stock and records the transaction.
     * <p>
     * The total cost of the purchase is:
     * <pre>
     *   stockCost = quantity × price
     *   commission = 10% of stockCost
     *   total = stockCost + commission
     * </pre>
     * The method checks that the account balance is sufficient to cover the total
     * cost. If so, the balance is reduced by the total amount and a new
     * {@link StockTransaction} is added to the transaction list.
     *
     * @param stockSymbol the ticker symbol of the stock to buy
     * @param quantity    the number of shares to purchase (must be positive)
     * @param price       the price per share (must be positive)
     * @return {@code true} if the purchase succeeded and the transaction was recorded;
     *         {@code false} otherwise (e.g., insufficient funds or invalid parameters)
     */
    public boolean buyStock(String stockSymbol, int quantity, double price) {
        if (stockSymbol == null || stockSymbol.isEmpty() || quantity <= 0 || price <= 0.0) {
            return false;
        }

        double stockCost = quantity * price;
        double commission = stockCost * 0.10; // 10% commission
        double totalCost = stockCost + commission;

        if (getBalance() < totalCost) {
            return false; // insufficient funds
        }

        // Deduct total cost from balance
        setBalance(getBalance() - totalCost);

        // Record the transaction
        StockTransaction tx = new StockTransaction();
        tx.setStock(stockSymbol);
        tx.setQuantity(quantity);
        tx.setPrice(price);
        tx.setCommission(commission);
        transactions.add(tx);

        return true;
    }

    /**
     * Calculates the total value of the investment account, which includes the
     * current cash balance and the market value of all owned stocks.
     * <p>
     * The market value of each stock is computed as:
     * <pre>
     *   marketValue = quantity × purchasePrice × 1.1
     * </pre>
     * (i.e., a 10% increase over the purchase price). The sum of all market
     * values is added to the cash balance. The final value is rounded to two
     * decimal places. If the account has no stock transactions, the method
     * returns {@code 0.0}.
     *
     * @return the total value of the investment account (rounded to two decimals),
     *         or {@code 0.0} if there are no stock transactions
     */
    public double calculateValue() {
        if (transactions.isEmpty()) {
            return 0.0;
        }

        double totalStockValue = 0.0;
        for (StockTransaction tx : transactions) {
            double marketValue = tx.getQuantity() * tx.getPrice() * 1.1;
            totalStockValue += marketValue;
        }

        double totalValue = getBalance() + totalStockValue;
        return roundToTwoDecimals(totalValue);
    }

    /**
     * Utility method to round a double value to two decimal places using
     * half‑up rounding.
     *
     * @param value the value to round
     * @return the rounded value
     */
    private double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}

/**
 * Represents a single stock purchase transaction.
 */
class StockTransaction {

    /** Ticker symbol of the stock. */
    private String stock;

    /** Number of shares purchased. */
    private int quantity;

    /** Purchase price per share. */
    private double price;

    /** Commission paid for this transaction (10% of stock cost). */
    private double commission;

    /**
     * No‑argument constructor.
     */
    public StockTransaction() {
        // default values
    }

    /* ==================== Getters and Setters ==================== */

    /**
     * Returns the stock ticker symbol.
     *
     * @return the stock symbol
     */
    public String getStock() {
        return stock;
    }

    /**
     * Sets the stock ticker symbol.
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
     * @param quantity the number of shares to set
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
     * @param price the price per share to set
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
     * @param commission the commission amount to set
     */
    public void setCommission(double commission) {
        this.commission = commission;
    }
}