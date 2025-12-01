import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents a bank customer with personal data and a collection of accounts.
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

    /* ---------- Getters & Setters ---------- */
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
     * Adds a new SavingsAccount to the customer.
     *
     * @param id           unique identifier for the account
     * @param interestRate interest rate for the savings account (e.g., 0.05 for 5%)
     * @return true if the account was added, false if an account with the same id already exists
     */
    public boolean addSavingAccount(String id, double interestRate) {
        if (findAccountById(id) != null) {
            return false; // duplicate id
        }
        SavingAccount acc = new SavingAccount();
        acc.setId(id);
        acc.setInterestRate(interestRate);
        accounts.add(acc);
        return true;
    }

    /**
     * Adds a new InvestmentAccount to the customer.
     *
     * @param id unique identifier for the account
     * @return true if the account was added, false if an account with the same id already exists
     */
    public boolean addInvestmentAccount(String id) {
        if (findAccountById(id) != null) {
            return false; // duplicate id
        }
        InvestmentAccount acc = new InvestmentAccount();
        acc.setId(id);
        accounts.add(acc);
        return true;
    }

    /**
     * Removes a SavingsAccount if it exists and its balance is zero.
     *
     * @param id identifier of the account to remove
     * @return true if the account was removed, false otherwise
     */
    public boolean removeSavingAccount(String id) {
        Account acc = findAccountById(id);
        if (acc instanceof SavingAccount && acc.getBalance() == 0.0) {
            return accounts.remove(acc);
        }
        return false;
    }

    /**
     * Removes an InvestmentAccount if it exists, its balance is zero and it has no stock transactions.
     *
     * @param id identifier of the account to remove
     * @return true if the account was removed, false otherwise
     */
    public boolean removeInvestmentAccount(String id) {
        Account acc = findAccountById(id);
        if (acc instanceof InvestmentAccount) {
            InvestmentAccount inv = (InvestmentAccount) acc;
            if (inv.getBalance() == 0.0 && inv.getTransactions().isEmpty()) {
                return accounts.remove(inv);
            }
        }
        return false;
    }

    /**
     * Retrieves an account belonging to this customer by its unique id.
     *
     * @param id the account identifier
     * @return the Account if found, otherwise null
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
 * Abstract base class for all account types.
 */
abstract class Account {

    private String id;
    private double balance;

    /** Unparameterized constructor */
    protected Account() {
    }

    /* ---------- Getters & Setters ---------- */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the current balance of the account.
     *
     * @return balance amount
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Sets the current balance of the account.
     *
     * @param balance new balance amount
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Deposits a positive amount into the account, respecting a maximum single‑deposit limit.
     *
     * @param amount amount to deposit; must be > 0 and ≤ 1,000,000
     * @return true if the deposit succeeded, false otherwise
     */
    public boolean deposit(double amount) {
        if (amount <= 0 || amount > 1_000_000) {
            return false;
        }
        this.balance = roundTwoDecimals(this.balance + amount);
        return true;
    }

    /**
     * Helper method to round a double to two decimal places using HALF_UP rounding.
     *
     * @param value the value to round
     * @return rounded value
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

    /** Unparameterized constructor */
    public SavingAccount() {
    }

    /* ---------- Getters & Setters ---------- */
    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * Calculates the daily interest based on the current balance and interest rate,
     * adds the interest to the balance, and returns the interest amount.
     *
     * Daily interest = balance * interestRate / 360
     *
     * @return the interest amount added to the account (rounded to two decimals)
     */
    public double calculateDailyInterest() {
        double balance = getBalance();
        double dailyInterest = balance * interestRate / 360.0;
        dailyInterest = roundTwoDecimals(dailyInterest);
        setBalance(roundTwoDecimals(balance + dailyInterest));
        return dailyInterest;
    }
}

/**
 * Investment account used for buying stocks.
 */
class InvestmentAccount extends Account {

    private List<StockTransaction> transactions = new ArrayList<>();

    /** Unparameterized constructor */
    public InvestmentAccount() {
    }

    /* ---------- Getters & Setters ---------- */
    public List<StockTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<StockTransaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * Purchases a stock, records the transaction and deducts the total cost (stock price + commission)
     * from the account balance. The commission is 10% of the stock cost.
     *
     * @param stockSymbol ticker symbol of the stock
     * @param quantity    number of shares to buy; must be > 0
     * @param price       purchase price per share; must be > 0
     * @return true if the purchase succeeded and the transaction was recorded, false otherwise
     */
    public boolean buyStock(String stockSymbol, int quantity, double price) {
        if (quantity <= 0 || price <= 0) {
            return false;
        }

        double stockCost = quantity * price;
        double commission = roundTwoDecimals(stockCost * 0.10);
        double totalDeduction = roundTwoDecimals(stockCost + commission);

        if (getBalance() < totalDeduction) {
            return false; // insufficient funds
        }

        // update balance
        setBalance(roundTwoDecimals(getBalance() - totalDeduction));

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
     * Calculates the total value of the investment account, which includes the cash balance
     * and the market value of all owned stocks. The market price of each stock is assumed
     * to be 1.1 times its purchase price.
     *
     * @return total account value rounded to two decimals; returns 0 if no stock transactions exist
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

    /** Unparameterized constructor */
    public StockTransaction() {
    }

    /* ---------- Getters & Setters ---------- */
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