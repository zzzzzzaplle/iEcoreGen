import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a bank customer.
 * <p>
 * A customer has a name, an address and a collection of accounts (savings or investment).
 * </p>
 */
 class Customer {

    private String name;
    private String address;
    private List<Account> accounts;

    /** Default constructor. Initializes the accounts list. */
    public Customer() {
        this.accounts = new ArrayList<>();
    }

    /** @return the customer's name */
    public String getName() {
        return name;
    }

    /** @param name the customer's name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the customer's address */
    public String getAddress() {
        return address;
    }

    /** @param address the customer's address to set */
    public void setAddress(String address) {
        this.address = address;
    }

    /** @return the list of accounts owned by the customer */
    public List<Account> getAccounts() {
        return accounts;
    }

    /** @param accounts the list of accounts to set */
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    /**
     * Adds a new savings account for this customer.
     *
     * @param id           the unique identifier for the new account
     * @param interestRate the interest rate (e.g., 0.03 for 3%)
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
     * Adds a new investment account for this customer.
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
        account.setBalance(0.0);
        accounts.add(account);
        return true;
    }

    /**
     * Removes a savings account identified by {@code id}.
     * <p>
     * The account can be removed only if its balance is zero.
     * </p>
     *
     * @param id the identifier of the savings account to remove
     * @return {@code true} if the account was removed; {@code false} otherwise
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
     * Removes an investment account identified by {@code id}.
     * <p>
     * The account can be removed only if its balance is zero and it has no stock
     * transactions.
     * </p>
     *
     * @param id the identifier of the investment account to remove
     * @return {@code true} if the account was removed; {@code false} otherwise
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
     * Finds an account belonging to this customer by its identifier.
     *
     * @param id the identifier of the account to find
     * @return the {@link Account} if found; {@code null} otherwise
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

    private String id;
    private double balance;

    /** Default constructor. */
    protected Account() {
    }

    /** @return the account identifier */
    public String getId() {
        return id;
    }

    /** @param id the account identifier to set */
    public void setId(String id) {
        this.id = id;
    }

    /** @return the current balance of the account */
    public double getBalance() {
        return balance;
    }

    /** @param balance the balance to set */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Deposits a positive amount into the account.
     * <p>
     * The amount must be greater than zero and cannot exceed $1,000,000 per
     * transaction.
     * </p>
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
 * Savings account that accrues daily interest.
 */
class SavingAccount extends Account {

    private double interestRate; // e.g., 0.03 for 3%

    /** Default constructor. */
    public SavingAccount() {
        super();
    }

    /** @return the interest rate for this savings account */
    public double getInterestRate() {
        return interestRate;
    }

    /** @param interestRate the interest rate to set */
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * Calculates the daily interest based on the current balance and interest rate,
     * updates the balance, and returns the interest amount.
     * <p>
     * Daily interest = balance * interestRate / 360.
     * The result is rounded to two decimal places using {@link RoundingMode#HALF_UP}.
     * </p>
     *
     * @return the interest amount that was added to the balance
     */
    public double calculateDailyInterest() {
        double rawInterest = getBalance() * interestRate / 360.0;
        double roundedInterest = roundToTwoDecimals(rawInterest);
        setBalance(getBalance() + roundedInterest);
        return roundedInterest;
    }

    /** Helper method to round a double to two decimal places. */
    private double roundToTwoDecimals(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}

/**
 * Investment account used to buy stocks.
 */
class InvestmentAccount extends Account {

    private List<StockTransaction> transactions;

    /** Default constructor. Initializes the transaction list. */
    public InvestmentAccount() {
        super();
        this.transactions = new ArrayList<>();
    }

    /** @return the list of stock transactions for this account */
    public List<StockTransaction> getTransactions() {
        return transactions;
    }

    /** @param transactions the transaction list to set */
    public void setTransactions(List<StockTransaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * Attempts to buy a stock and records the transaction.
     * <p>
     * The total cost includes a 10% commission on the stock cost.
     * The operation succeeds only if the account balance is sufficient to cover
     * the total cost.
     * </p>
     *
     * @param stockSymbol the ticker symbol of the stock
     * @param quantity    the number of shares to purchase (must be positive)
     * @param price       the price per share (must be positive)
     * @return {@code true} if the purchase succeeded and the transaction was saved;
     *         {@code false} otherwise
     */
    public boolean buyStock(String stockSymbol, int quantity, double price) {
        if (stockSymbol == null || stockSymbol.isEmpty()) {
            return false;
        }
        if (quantity <= 0 || price <= 0) {
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
        StockTransaction tx = new StockTransaction();
        tx.setStock(stockSymbol);
        tx.setQuantity(quantity);
        tx.setPrice(price);
        tx.setCommission(commission);
        transactions.add(tx);
        return true;
    }

    /**
     * Calculates the total value of the investment account.
     * <p>
     * The value includes the current account balance plus the market value of all
     * owned stocks. The market price of each stock is assumed to be 1.1 times its
     * purchase price.
     * </p>
     * <p>
     * If there are no stock transactions, the method returns {@code 0.0} (as per
     * the functional requirement), ignoring the cash balance.
     * </p>
     *
     * @return the total value of the investment account rounded to two decimal places
     */
    public double calculateValue() {
        if (transactions.isEmpty()) {
            return 0.0;
        }

        double total = getBalance();
        for (StockTransaction tx : transactions) {
            double marketPrice = tx.getPrice() * 1.1; // 10% increase over purchase price
            double stockValue = tx.getQuantity() * marketPrice;
            total += stockValue;
        }
        return roundToTwoDecimals(total);
    }

    /** Helper method to round a double to two decimal places. */
    private double roundToTwoDecimals(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
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

    /** Default constructor. */
    public StockTransaction() {
    }

    /** @return the ticker symbol of the stock */
    public String getStock() {
        return stock;
    }

    /** @param stock the ticker symbol to set */
    public void setStock(String stock) {
        this.stock = stock;
    }

    /** @return the number of shares purchased */
    public int getQuantity() {
        return quantity;
    }

    /** @param quantity the quantity to set */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /** @return the purchase price per share */
    public double getPrice() {
        return price;
    }

    /** @param price the purchase price per share to set */
    public void setPrice(double price) {
        this.price = price;
    }

    /** @return the commission paid for this transaction */
    public double getCommission() {
        return commission;
    }

    /** @param commission the commission to set */
    public void setCommission(double commission) {
        this.commission = commission;
    }
}