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
    private List<Account> accounts = new ArrayList<>();

    /** Unparameterized constructor. */
    public Customer() {
    }

    /** Parameterized constructor for convenience. */
    public Customer(String name, String address) {
        this.name = name;
        this.address = address;
    }

    /* ---------- Getters and Setters ---------- */

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

    /* ---------- Business Methods ---------- */

    /**
     * Adds a new savings account for this customer.
     *
     * @param id           unique identifier for the account
     * @param interestRate interest rate for the savings account (e.g., 0.05 for 5%)
     * @return {@code true} if the account was added, {@code false} if an account with the same id already exists
     */
    public boolean addSavingAccount(String id, double interestRate) {
        if (findAccountById(id) != null) {
            return false; // id already used
        }
        SavingAccount acc = new SavingAccount();
        acc.setId(id);
        acc.setInterestRate(interestRate);
        accounts.add(acc);
        return true;
    }

    /**
     * Adds a new investment account for this customer.
     *
     * @param id unique identifier for the account
     * @return {@code true} if the account was added, {@code false} if an account with the same id already exists
     */
    public boolean addInvestmentAccount(String id) {
        if (findAccountById(id) != null) {
            return false; // id already used
        }
        InvestmentAccount acc = new InvestmentAccount();
        acc.setId(id);
        accounts.add(acc);
        return true;
    }

    /**
     * Removes a savings account if its balance is zero.
     *
     * @param id account identifier
     * @return {@code true} if the account was removed, {@code false} otherwise
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
     * Removes an investment account if its balance is zero and it has no stock transactions.
     *
     * @param id account identifier
     * @return {@code true} if the account was removed, {@code false} otherwise
     */
    public boolean removeInvestmentAccount(String id) {
        Account acc = findAccountById(id);
        if (acc instanceof InvestmentAccount) {
            InvestmentAccount inv = (InvestmentAccount) acc;
            if (Math.abs(inv.getBalance()) < 0.000001 && inv.getTransactions().isEmpty()) {
                accounts.remove(acc);
                return true;
            }
        }
        return false;
    }

    /**
     * Finds an account belonging to this customer by its id.
     *
     * @param id the account identifier
     * @return the {@link Account} if found, otherwise {@code null}
     */
    public Account findAccountById(String id) {
        for (Account a : accounts) {
            if (a.getId().equals(id)) {
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

    protected String id;
    protected double balance;

    /** Unparameterized constructor. */
    public Account() {
    }

    /* ---------- Getters and Setters ---------- */

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
     * Deposits money into the account.
     *
     * @param amount amount to deposit; must be positive and not exceed $1,000,000
     * @return {@code true} if the deposit succeeded, {@code false} otherwise
     */
    public boolean deposit(double amount) {
        if (amount <= 0 || amount > 1_000_000) {
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

    private double interestRate; // e.g., 0.05 for 5%

    /** Unparameterized constructor. */
    public SavingAccount() {
    }

    /* ---------- Getters and Setters ---------- */

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
     * The returned value is rounded to two decimal places.
     *
     * @return the interest added to the account (rounded to 2 decimal places)
     */
    public double calculateDailyInterest() {
        double rawInterest = this.balance * this.interestRate / 360.0;
        BigDecimal bd = BigDecimal.valueOf(rawInterest).setScale(2, RoundingMode.HALF_UP);
        double interest = bd.doubleValue();
        this.balance += interest;
        return interest;
    }
}

/**
 * Investment account used to buy stocks.
 */
class InvestmentAccount extends Account {

    private List<StockTransaction> transactions = new ArrayList<>();

    /** Unparameterized constructor. */
    public InvestmentAccount() {
    }

    /* ---------- Getters and Setters ---------- */

    public List<StockTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<StockTransaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * Attempts to buy a stock, creating a new {@link StockTransaction} if.
     *
     * @param stockSymbol ticker symbol of the stock
     * @param quantity    number of shares to buy (must be positive)
     * @param price       price per share (must be positive)
     * @return {@code true} if the transaction was recorded and balance updated, {@code false} otherwise
     */
    public boolean buyStock(String stockSymbol, int quantity, double price) {
        if (stockSymbol == null || stockSymbol.isEmpty() || quantity <= 0 || price <= 0) {
            return false;
        }

        double stockCost = quantity * price;
        double commission = stockCost * 0.10; // 10% commission
        double totalCost = stockCost + commission;

        if (this.balance < totalCost) {
            return false; // insufficient funds
        }

        this.balance -= totalCost;

        StockTransaction tx = new StockTransaction();
        tx.setStock(stockSymbol);
        tx.setQuantity(quantity);
        tx.setPrice(price);
        tx.setCommission(commission);
        transactions.add(tx);
        return true;
    }

    /**
     * Calculates the total value of the investment account, which includes the current cash balance
     * plus the market value of all owned stocks. The market price of each stock is assumed to be
     * 1.1 times its purchase price.
     *
     * If there are no stock transactions, the method returns {@code 0.0}.
     * The returned value is rounded to two decimal places.
     *
     * @return the total value of the investment account (rounded to 2 decimal places) or 0 if no stocks
     */
    public double calculateValue() {
        if (transactions.isEmpty()) {
            return 0.0;
        }

        double totalStockValue = 0.0;
        for (StockTransaction tx : transactions) {
            double marketPrice = tx.getPrice() * 1.1; // 10% increase over purchase price
            totalStockValue += tx.getQuantity() * marketPrice;
        }

        double totalValue = this.balance + totalStockValue;
        BigDecimal bd = BigDecimal.valueOf(totalValue).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}

/**
 * Represents a stock purchase transaction.
 */
class StockTransaction {

    private String stock;      // ticker symbol
    private int quantity;      // number of shares
    private double price;      // purchase price per share
    private double commission; // commission paid for this transaction

    /** Unparameterized constructor. */
    public StockTransaction() {
    }

    /* ---------- Getters and Setters ---------- */

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