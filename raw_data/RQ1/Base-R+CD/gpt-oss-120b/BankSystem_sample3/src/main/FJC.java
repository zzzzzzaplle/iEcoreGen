import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a bank customer.
 */
 class Customer {

    private String name;
    private String address;
    private List<Account> accounts;

    /**
     * No‑argument constructor. Initializes the accounts list.
     */
    public Customer() {
        this.accounts = new ArrayList<>();
    }

    // -------------------- Getters and Setters --------------------

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

    // -------------------- Business Methods --------------------

    /**
     * Adds a new saving account for this customer.
     *
     * @param id           the unique identifier for the account
     * @param interestRate the interest rate for the saving account (e.g., 0.05 for 5%)
     * @return {@code true} if the account was added successfully; {@code false}
     *         if an account with the same id already exists
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
     * Adds a new investment account for this customer.
     *
     * @param id the unique identifier for the account
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
        accounts.add(account);
        return true;
    }

    /**
     * Removes a saving account identified by {@code id}. The account can be
     * removed only if its balance is zero.
     *
     * @param id the identifier of the saving account to remove
     * @return {@code true} if the account was removed; {@code false} otherwise
     */
    public boolean removeSavingAccount(String id) {
        Account acc = findAccountById(id);
        if (acc == null || !(acc instanceof SavingAccount)) {
            return false;
        }
        if (Math.abs(acc.getBalance()) > 0.000001) {
            return false; // non‑zero balance
        }
        return accounts.remove(acc);
    }

    /**
     * Removes an investment account identified by {@code id}. The account can be
     * removed only if its balance is zero **and** it has no stock transactions.
     *
     * @param id the identifier of the investment account to remove
     * @return {@code true} if the account was removed; {@code false} otherwise
     */
    public boolean removeInvestmentAccount(String id) {
        Account acc = findAccountById(id);
        if (acc == null || !(acc instanceof InvestmentAccount)) {
            return false;
        }
        if (Math.abs(acc.getBalance()) > 0.000001) {
            return false; // non‑zero balance
        }
        InvestmentAccount invAcc = (InvestmentAccount) acc;
        if (!invAcc.getTransactions().isEmpty()) {
            return false; // has stock transactions
        }
        return accounts.remove(acc);
    }

    /**
     * Finds an account belonging to this customer by its unique identifier.
     *
     * @param id the account identifier
     * @return the {@link Account} if found; {@code null} otherwise
     */
    public Account findAccountById(String id) {
        for (Account acc : accounts) {
            if (acc.getId().equals(id)) {
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
     * No‑argument constructor.
     */
    public Account() {
        // default constructor
    }

    // -------------------- Getters and Setters --------------------

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
     * Sets the balance of the account.
     *
     * @param balance the new balance
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    // -------------------- Business Method --------------------

    /**
     * Deposits a positive amount into the account. The amount must be greater
     * than zero and cannot exceed the maximum single‑deposit limit of
     * $1,000,000.
     *
     * @param amount the amount to deposit
     * @return {@code true} if the deposit succeeded; {@code false} otherwise
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
 * Represents a savings account that accrues daily interest.
 */
class SavingAccount extends Account {

    private double interestRate; // e.g., 0.05 for 5%

    /**
     * No‑argument constructor.
     */
    public SavingAccount() {
        super();
    }

    // -------------------- Getters and Setters --------------------

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    // -------------------- Business Method --------------------

    /**
     * Calculates the daily interest based on the current balance and the
     * account's interest rate, adds it to the balance, and returns the interest
     * amount. The interest is calculated as:
     * <pre>
     *   dailyInterest = balance * interestRate / 360
     * </pre>
     * The result is rounded to two decimal places using
     * {@link RoundingMode#HALF_UP}.
     *
     * @return the interest amount that was added to the balance
     */
    public double calculateDailyInterest() {
        BigDecimal bdBalance = BigDecimal.valueOf(getBalance());
        BigDecimal bdRate = BigDecimal.valueOf(interestRate);
        BigDecimal dailyInterest = bdBalance.multiply(bdRate)
                .divide(BigDecimal.valueOf(360), 10, RoundingMode.HALF_UP);
        dailyInterest = dailyInterest.setScale(2, RoundingMode.HALF_UP);
        // Update balance
        setBalance(getBalance() + dailyInterest.doubleValue());
        return dailyInterest.doubleValue();
    }
}

/**
 * Represents an investment account that can buy stocks.
 */
class InvestmentAccount extends Account {

    private List<StockTransaction> transactions;

    /**
     * No‑argument constructor. Initializes the transaction list.
     */
    public InvestmentAccount() {
        super();
        this.transactions = new ArrayList<>();
    }

    // -------------------- Getters and Setters --------------------

    public List<StockTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<StockTransaction> transactions) {
        this.transactions = transactions;
    }

    // -------------------- Business Methods --------------------

    /**
     * Purchases a stock and records the transaction. The total cost of the
     * purchase includes a 10% commission on the stock cost.
     *
     * @param stockSymbol the ticker symbol of the stock
     * @param quantity    the number of shares to buy (must be positive)
     * @param price       the price per share (must be positive)
     * @return {@code true} if the purchase succeeded and the transaction was
     *         recorded; {@code false} otherwise (e.g., insufficient funds)
     */
    public boolean buyStock(String stockSymbol, int quantity, double price) {
        if (stockSymbol == null || stockSymbol.isEmpty() || quantity <= 0 || price <= 0) {
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
     * Calculates the total value of the investment account, which includes the
     * current cash balance and the market value of all owned stocks. The market
     * price of each stock is assumed to be 1.1 times its purchase price.
     *
     * @return the total account value rounded to two decimal places, or {@code 0.0}
     *         if there are no stock transactions
     */
    public double calculateValue() {
        if (transactions.isEmpty()) {
            return 0.0;
        }
        BigDecimal totalStockValue = BigDecimal.ZERO;
        for (StockTransaction tx : transactions) {
            BigDecimal purchasePrice = BigDecimal.valueOf(tx.getPrice());
            BigDecimal marketPrice = purchasePrice.multiply(BigDecimal.valueOf(1.1));
            BigDecimal quantity = BigDecimal.valueOf(tx.getQuantity());
            totalStockValue = totalStockValue.add(marketPrice.multiply(quantity));
        }
        BigDecimal total = BigDecimal.valueOf(getBalance()).add(totalStockValue);
        total = total.setScale(2, RoundingMode.HALF_UP);
        return total.doubleValue();
    }
}

/**
 * Represents a single stock purchase transaction.
 */
class StockTransaction {

    private String stock;      // ticker symbol
    private int quantity;
    private double price;      // price per share at purchase
    private double commission; // commission paid for this transaction

    /**
     * No‑argument constructor.
     */
    public StockTransaction() {
        // default constructor
    }

    // -------------------- Getters and Setters --------------------

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