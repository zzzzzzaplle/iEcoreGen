import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents a bank customer.
 */
 class Customer {

    private String name;
    private String address;
    private List<Account> accounts = new ArrayList<>();

    /** No‑argument constructor */
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

    /** Getter for accounts (modifiable list) */
    public List<Account> getAccounts() {
        return accounts;
    }

    /** Setter for accounts (replaces current list) */
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    /**
     * Adds a new saving account with the given id and interest rate.
     *
     * @param id            unique identifier for the account
     * @param interestRate  interest rate for the saving account (e.g., 0.05 for 5%)
     * @return true if the account was added, false if an account with the same id already exists
     */
    public boolean addSavingAccount(String id, double interestRate) {
        if (findAccountById(id) != null) {
            return false; // duplicate id
        }
        SavingAccount acct = new SavingAccount();
        acct.setId(id);
        acct.setBalance(0.0);
        acct.setInterestRate(interestRate);
        accounts.add(acct);
        return true;
    }

    /**
     * Adds a new investment account with the given id.
     *
     * @param id unique identifier for the account
     * @return true if the account was added, false if an account with the same id already exists
     */
    public boolean addInvestmentAccount(String id) {
        if (findAccountById(id) != null) {
            return false; // duplicate id
        }
        InvestmentAccount acct = new InvestmentAccount();
        acct.setId(id);
        acct.setBalance(0.0);
        accounts.add(acct);
        return true;
    }

    /**
     * Removes a saving account with the specified id.
     * The account can be removed only if its balance is zero.
     *
     * @param id account identifier
     * @return true if the account was removed, false otherwise
     */
    public boolean removeSavingAccount(String id) {
        Account acct = findAccountById(id);
        if (acct instanceof SavingAccount && acct.getBalance() == 0.0) {
            return accounts.remove(acct);
        }
        return false;
    }

    /**
     * Removes an investment account with the specified id.
     * The account can be removed only if its balance is zero and it has no stock transactions.
     *
     * @param id account identifier
     * @return true if the account was removed, false otherwise
     */
    public boolean removeInvestmentAccount(String id) {
        Account acct = findAccountById(id);
        if (acct instanceof InvestmentAccount) {
            InvestmentAccount invAcct = (InvestmentAccount) acct;
            if (invAcct.getBalance() == 0.0 && invAcct.getTransactions().isEmpty()) {
                return accounts.remove(acct);
            }
        }
        return false;
    }

    /**
     * Finds an account belonging to this customer by its id.
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

    /** No‑argument constructor */
    public Account() {
    }

    /** Getter for id */
    public String getId() {
        return id;
    }

    /** Setter for id */
    public void setId(String id) {
        this.id = id;
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
     * Deposits a positive amount into the account, respecting the maximum single deposit limit.
     *
     * @param amount amount to deposit; must be > 0 and ≤ 1,000,000
     * @return true if the deposit succeeded, false otherwise
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
 * Savings account with an interest rate.
 */
class SavingAccount extends Account {

    private double interestRate; // e.g., 0.05 for 5%

    /** No‑argument constructor */
    public SavingAccount() {
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
     * Calculates the daily interest, adds it to the balance and returns the interest amount.
     * Daily interest = balance * interestRate / 360, rounded to two decimal places.
     *
     * @return the interest amount added to the balance
     */
    public double calculateDailyInterest() {
        double rawInterest = getBalance() * interestRate / 360.0;
        BigDecimal bd = BigDecimal.valueOf(rawInterest).setScale(2, RoundingMode.HALF_UP);
        double interest = bd.doubleValue();
        setBalance(getBalance() + interest);
        return interest;
    }
}

/**
 * Investment account that can hold stock transactions.
 */
class InvestmentAccount extends Account {

    private List<StockTransaction> transactions = new ArrayList<>();

    /** No‑argument constructor */
    public InvestmentAccount() {
    }

    /** Getter for transactions */
    public List<StockTransaction> getTransactions() {
        return transactions;
    }

    /** Setter for transactions (replaces current list) */
    public void setTransactions(List<StockTransaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * Buys a stock, creates a transaction record and updates the account balance.
     * The system charges a commission of 10% of the stock cost.
     *
     * @param stockSymbol ticker symbol of the stock
     * @param quantity    number of shares to buy (must be > 0)
     * @param price       purchase price per share (must be > 0)
     * @return true if the purchase succeeded and the transaction was recorded,
     *         false if there are insufficient funds or invalid parameters
     */
    public boolean buyStock(String stockSymbol, int quantity, double price) {
        if (stockSymbol == null || stockSymbol.isEmpty() || quantity <= 0 || price <= 0) {
            return false;
        }
        double cost = quantity * price;
        double commission = cost * 0.10; // 10% commission
        double totalDebit = cost + commission;

        if (getBalance() < totalDebit) {
            return false; // insufficient funds
        }

        // Deduct from balance
        setBalance(getBalance() - totalDebit);

        // Record transaction
        StockTransaction txn = new StockTransaction();
        txn.setStock(stockSymbol);
        txn.setQuantity(quantity);
        txn.setPrice(price);
        txn.setCommission(commission);
        transactions.add(txn);
        return true;
    }

    /**
     * Calculates the total value of the investment account.
     * Value = current balance + sum of (quantity * purchase price * 1.1) for each transaction.
     * The market price is assumed to be 1.1 times the purchase price.
     *
     * @return the total value rounded to two decimal places; 0 if there are no transactions
     */
    public double calculateValue() {
        if (transactions.isEmpty()) {
            return 0.0;
        }
        double stockValue = 0.0;
        for (StockTransaction txn : transactions) {
            double marketPrice = txn.getPrice() * 1.1;
            stockValue += txn.getQuantity() * marketPrice;
        }
        double total = getBalance() + stockValue;
        BigDecimal bd = BigDecimal.valueOf(total).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
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

    /** No‑argument constructor */
    public StockTransaction() {
    }

    /** Getter for stock */
    public String getStock() {
        return stock;
    }

    /** Setter for stock */
    public void setStock(String stock) {
        this.stock = stock;
    }

    /** Getter for quantity */
    public int getQuantity() {
        return quantity;
    }

    /** Setter for quantity */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /** Getter for price */
    public double getPrice() {
        return price;
    }

    /** Setter for price */
    public void setPrice(double price) {
        this.price = price;
    }

    /** Getter for commission */
    public double getCommission() {
        return commission;
    }

    /** Setter for commission */
    public void setCommission(double commission) {
        this.commission = commission;
    }
}