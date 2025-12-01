import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents a bank customer.
 */
 class Customer {

    private String name;
    private String address;
    private List<Account> accounts = new ArrayList<>();

    /** Unparameterized constructor */
    public Customer() {
    }

    /** Getter for name */
    public String getName() {
        return name;
    }

    /** Setter for name */
    public void setName(String name) {
        this.name = name;
    }

    /** Getter for address */
    public String getAddress() {
        return address;
    }

    /** Setter for address */
    public void setAddress(String address) {
        this.address = address;
    }

    /** Getter for accounts list (modifiable) */
    public List<Account> getAccounts() {
        return accounts;
    }

    /** Setter for accounts list */
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    /**
     * Adds a new account to the customer.
     *
     * @param account the account to add
     * @return true if the account was added, false if an account with the same id already exists
     */
    public boolean addAccount(Account account) {
        if (account == null) {
            return false;
        }
        // ensure unique account id
        if (getAccountById(account.getAccountId()).isPresent()) {
            return false;
        }
        accounts.add(account);
        return true;
    }

    /**
     * Removes an account identified by its id.
     * <p>
     * Removal is allowed only when the account balance is zero and,
     * for investment accounts, there are no stock transactions.
     *
     * @param accountId the id of the account to remove
     * @return true if the removal succeeded, false otherwise
     */
    public boolean removeAccount(String accountId) {
        Optional<Account> opt = getAccountById(accountId);
        if (!opt.isPresent()) {
            return false;
        }
        Account acc = opt.get();

        // balance must be zero
        if (Math.abs(acc.getBalance()) > 0.00001) {
            return false;
        }

        // additional check for investment accounts
        if (acc instanceof InvestmentAccount) {
            InvestmentAccount inv = (InvestmentAccount) acc;
            if (!inv.getTransactions().isEmpty()) {
                return false;
            }
        }

        return accounts.remove(acc);
    }

    /**
     * Finds an account belonging to this customer by its id.
     *
     * @param accountId the id to search for
     * @return an {@code Optional} containing the account if found, otherwise empty
     */
    public Optional<Account> getAccountById(String accountId) {
        return accounts.stream()
                .filter(a -> a.getAccountId().equals(accountId))
                .findFirst();
    }
}

/**
 * Abstract base class for all account types.
 */
public abstract class Account {

    private String accountId;
    private double balance;

    /** Unparameterized constructor */
    public Account() {
    }

    /** Getter for accountId */
    public String getAccountId() {
        return accountId;
    }

    /** Setter for accountId */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /** Getter for balance */
    public double getBalance() {
        return balance;
    }

    /** Setter for balance */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Deposits money into the account.
     * <p>
     * The amount must be positive and may not exceed $1,000,000.
     *
     * @param amount the amount to deposit
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
     * Helper method to round a double value to two decimal places.
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

    private double interestRate; // e.g., 0.03 for 3%

    /** Unparameterized constructor */
    public SavingsAccount() {
    }

    /** Getter for interestRate */
    public double getInterestRate() {
        return interestRate;
    }

    /** Setter for interestRate */
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * Calculates the daily interest and adds it to the balance.
     * <p>
     * Daily interest = balance * interestRate / 360.
     * The interest is rounded to two decimal places before being added.
     *
     * @return the amount of interest that was added
     */
    public double applyDailyInterest() {
        double currentBalance = getBalance();
        double dailyInterest = currentBalance * interestRate / 360.0;
        dailyInterest = roundTwoDecimals(dailyInterest);
        setBalance(roundTwoDecimals(currentBalance + dailyInterest));
        return dailyInterest;
    }
}

/**
 * Investment account used to buy stocks.
 */
 class InvestmentAccount extends Account {

    private List<StockTransaction> transactions = new ArrayList<>();

    /** Unparameterized constructor */
    public InvestmentAccount() {
    }

    /** Getter for transactions */
    public List<StockTransaction> getTransactions() {
        return transactions;
    }

    /** Setter for transactions */
    public void setTransactions(List<StockTransaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * Attempts to buy a stock and records the transaction.
     * <p>
     * The total cost = (quantity * price) + commission.
     * Commission is 10% of the stock cost (excluding commission itself).
     *
     * @param ticker   the stock ticker symbol
     * @param quantity the number of shares to buy (must be positive)
     * @param price    the purchase price per share (must be positive)
     * @return true if the transaction was saved and balance deducted, false otherwise
     */
    public boolean buyStock(String ticker, int quantity, double price) {
        if (ticker == null || ticker.isEmpty() || quantity <= 0 || price <= 0) {
            return false;
        }

        double stockCost = quantity * price;
        double commission = stockCost * 0.10; // 10%
        double totalDeduction = stockCost + commission;

        // Ensure sufficient balance
        if (getBalance() < totalDeduction) {
            return false;
        }

        // Deduct from balance
        setBalance(roundTwoDecimals(getBalance() - totalDeduction));

        // Record transaction
        StockTransaction tx = new StockTransaction();
        tx.setTicker(ticker);
        tx.setQuantity(quantity);
        tx.setPurchasePrice(price);
        transactions.add(tx);
        return true;
    }

    /**
     * Calculates the total market value of the investment account.
     * <p>
     * Market value of a stock = quantity * (purchasePrice * 1.1).
     *
     * @return the sum of account balance and total market value of all stocks,
     * rounded to two decimal places; returns 0 if there are no stock transactions
     */
    public double calculateTotalValue() {
        if (transactions.isEmpty()) {
            return 0.0;
        }

        double stocksValue = 0.0;
        for (StockTransaction tx : transactions) {
            double marketPricePerShare = tx.getPurchasePrice() * 1.1;
            stocksValue += tx.getQuantity() * marketPricePerShare;
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

    /** Getter for ticker */
    public String getTicker() {
        return ticker;
    }

    /** Setter for ticker */
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    /** Getter for quantity */
    public int getQuantity() {
        return quantity;
    }

    /** Setter for quantity */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /** Getter for purchasePrice */
    public double getPurchasePrice() {
        return purchasePrice;
    }

    /** Setter for purchasePrice */
    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
}