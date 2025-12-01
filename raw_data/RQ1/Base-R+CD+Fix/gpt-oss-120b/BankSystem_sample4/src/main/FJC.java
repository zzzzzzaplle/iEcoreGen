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

    /** Unparameterized constructor. */
    public Customer() {
        this.accounts = new ArrayList<>();
    }

    /** Parameterized constructor for convenience. */
    public Customer(String name, String address) {
        this.name = name;
        this.address = address;
        this.accounts = new ArrayList<>();
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
     * Adds a new saving account to the customer.
     *
     * @param id            unique identifier of the account
     * @param interestRate  interest rate for the saving account (e.g., 0.05 for 5%)
     * @return true if the account was added; false if an account with the same id already exists
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
     * @param id unique identifier of the account
     * @return true if the account was added; false if an account with the same id already exists
     */
    public boolean addInvestmentAccount(String id) {
        if (findAccountById(id) != null) {
            return false; // duplicate id
        }
        InvestmentAccount account = new InvestmentAccount();
        account.setId(id);
        account.setBalance(0.0);
        accounts.add(account);
        return true;
    }

    /**
     * Removes a saving account if it has zero balance and no stock transactions.
     *
     * @param id identifier of the saving account to remove
     * @return true if removal succeeded; false otherwise
     */
    public boolean removeSavingAccount(String id) {
        Account acc = findAccountById(id);
        if (acc instanceof SavingAccount) {
            if (acc.getBalance() == 0.0) {
                accounts.remove(acc);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes an investment account if it has zero balance and no stock transactions.
     *
     * @param id identifier of the investment account to remove
     * @return true if removal succeeded; false otherwise
     */
    public boolean removeInvestmentAccount(String id) {
        Account acc = findAccountById(id);
        if (acc instanceof InvestmentAccount) {
            InvestmentAccount invAcc = (InvestmentAccount) acc;
            if (invAcc.getBalance() == 0.0 && invAcc.getTransactions().isEmpty()) {
                accounts.remove(acc);
                return true;
            }
        }
        return false;
    }

    /**
     * Finds an account belonging to this customer by its unique id.
     *
     * @param id the account id to search for
     * @return the matching {@link Account} or {@code null} if not found
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

    private String id;
    private double balance;

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
     * Deposits an amount into the account.
     *
     * @param amount amount to deposit; must be positive and not exceed $1,000,000
     * @return true if deposit succeeded; false otherwise
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
     * Calculates the daily interest, updates the balance, and returns the interest amount.
     * Daily interest = balance * interestRate / 360, rounded to two decimal places.
     *
     * @return the daily interest that was added to the balance
     */
    public double calculateDailyInterest() {
        double dailyInterest = getBalance() * interestRate / 360.0;
        BigDecimal bd = new BigDecimal(dailyInterest).setScale(2, RoundingMode.HALF_UP);
        double roundedInterest = bd.doubleValue();
        setBalance(getBalance() + roundedInterest);
        return roundedInterest;
    }
}

/**
 * Investment account that can buy stocks and keep transaction history.
 */
class InvestmentAccount extends Account {

    private List<StockTransaction> transactions;

    /** Unparameterized constructor. */
    public InvestmentAccount() {
        this.transactions = new ArrayList<>();
    }

    /* ---------- Getters and Setters ---------- */

    public List<StockTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<StockTransaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * Purchases a stock, records the transaction and deducts cost + commission from the balance.
     * Commission is 10% of the stock cost.
     *
     * @param stockSymbol ticker symbol of the stock
     * @param quantity   number of shares to buy (must be positive)
     * @param price      purchase price per share (must be positive)
     * @return true if the purchase succeeded and transaction was recorded; false otherwise
     */
    public boolean buyStock(String stockSymbol, int quantity, double price) {
        if (quantity <= 0 || price <= 0) {
            return false;
        }
        double stockCost = quantity * price;
        double commission = stockCost * 0.10; // 10% commission
        double totalCost = stockCost + commission;

        if (getBalance() < totalCost) {
            return false; // insufficient funds
        }

        // Deduct the total cost
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
     * Calculates the total value of the investment account, which includes the cash balance
     * and the current market value of all owned stocks. The market price of each stock is
     * assumed to be 1.1 times its purchase price.
     *
     * @return the total account value rounded to two decimal places; returns 0 if there are
     *         no stock transactions
     */
    public double calculateValue() {
        if (transactions.isEmpty()) {
            return 0.0;
        }
        double stockValue = 0.0;
        for (StockTransaction tx : transactions) {
            double marketPricePerShare = tx.getPrice() * 1.1;
            stockValue += tx.getQuantity() * marketPricePerShare;
        }
        double total = getBalance() + stockValue;
        BigDecimal bd = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}

/**
 * Represents a stock purchase transaction.
 */
class StockTransaction {

    private String stock;      // ticker symbol
    private int quantity;
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