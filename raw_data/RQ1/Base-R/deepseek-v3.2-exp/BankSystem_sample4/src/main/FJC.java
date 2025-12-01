import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents a customer in the bank system
 */
class Customer {
    private String name;
    private String address;
    private List<Account> accounts;

    /**
     * Default constructor
     */
    public Customer() {
        this.accounts = new ArrayList<>();
    }

    /**
     * Adds an account to the customer's account list
     * @param account The account to be added
     * @return true if the account was added successfully, false otherwise
     */
    public boolean addAccount(Account account) {
        if (account != null && !hasAccount(account.getAccountId())) {
            return accounts.add(account);
        }
        return false;
    }

    /**
     * Removes an account from the customer's account list
     * @param accountId The ID of the account to be removed
     * @return true if the account was removed successfully, false otherwise
     */
    public boolean removeAccount(String accountId) {
        Account account = getAccount(accountId);
        if (account != null && account.canBeRemoved()) {
            return accounts.remove(account);
        }
        return false;
    }

    /**
     * Checks if the customer has an account with the specified ID
     * @param accountId The account ID to check
     * @return true if the account exists, false otherwise
     */
    private boolean hasAccount(String accountId) {
        return getAccount(accountId) != null;
    }

    /**
     * Retrieves an account by its ID
     * @param accountId The ID of the account to retrieve
     * @return The account with the specified ID, or null if not found
     */
    private Account getAccount(String accountId) {
        for (Account account : accounts) {
            if (account.getAccountId().equals(accountId)) {
                return account;
            }
        }
        return null;
    }

    // Getters and setters
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
}

/**
 * Abstract class representing a bank account
 */
abstract class Account {
    private String accountId;
    private BigDecimal balance;

    /**
     * Default constructor
     */
    public Account() {
        this.balance = BigDecimal.ZERO;
    }

    /**
     * Deposits money into the account
     * @param amount The amount to deposit
     * @return true if the deposit was successful, false otherwise
     */
    public boolean deposit(BigDecimal amount) {
        if (amount != null && amount.compareTo(BigDecimal.ZERO) > 0 && 
            amount.compareTo(new BigDecimal("1000000")) <= 0) {
            balance = balance.add(amount);
            return true;
        }
        return false;
    }

    /**
     * Checks if the account can be removed
     * @return true if the account has zero balance and no stock transactions, false otherwise
     */
    public abstract boolean canBeRemoved();

    // Getters and setters
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}

/**
 * Represents a savings account with interest rate
 */
class SavingsAccount extends Account {
    private BigDecimal interestRate;

    /**
     * Default constructor
     */
    public SavingsAccount() {
        super();
    }

    /**
     * Calculates and applies daily interest to the account balance
     * Daily interest = balance * interest rate / 360
     */
    public void calculateDailyInterest() {
        BigDecimal dailyInterest = getBalance().multiply(interestRate)
                .divide(new BigDecimal("360"), 2, RoundingMode.HALF_UP);
        setBalance(getBalance().add(dailyInterest));
    }

    /**
     * Checks if the savings account can be removed
     * @return true if the account has zero balance, false otherwise
     */
    @Override
    public boolean canBeRemoved() {
        return getBalance().compareTo(BigDecimal.ZERO) == 0;
    }

    // Getters and setters
    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }
}

/**
 * Represents a stock transaction record
 */
class StockTransaction {
    private String ticker;
    private int quantity;
    private BigDecimal purchasePrice;
    private BigDecimal commission;

    /**
     * Default constructor
     */
    public StockTransaction() {
    }

    /**
     * Calculates the total cost of this stock transaction
     * @return The total cost including commission
     */
    public BigDecimal getTotalCost() {
        BigDecimal stockCost = purchasePrice.multiply(new BigDecimal(quantity));
        return stockCost.add(commission);
    }

    /**
     * Calculates the current value of this stock holding
     * @return The current value (1.1 times purchase price * quantity)
     */
    public BigDecimal getCurrentValue() {
        BigDecimal currentPrice = purchasePrice.multiply(new BigDecimal("1.1"));
        return currentPrice.multiply(new BigDecimal(quantity))
                .setScale(2, RoundingMode.HALF_UP);
    }

    // Getters and setters
    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }
}

/**
 * Represents an investment account for buying stocks
 */
class InvestmentAccount extends Account {
    private List<StockTransaction> stockTransactions;

    /**
     * Default constructor
     */
    public InvestmentAccount() {
        super();
        this.stockTransactions = new ArrayList<>();
    }

    /**
     * Buys stocks and updates the account balance
     * @param ticker The stock ticker symbol
     * @param quantity The number of stocks to buy
     * @param price The price per stock
     * @return true if the transaction was successful, false otherwise
     */
    public boolean buyStocks(String ticker, int quantity, BigDecimal price) {
        if (ticker == null || ticker.trim().isEmpty() || quantity <= 0 || 
            price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        BigDecimal stockCost = price.multiply(new BigDecimal(quantity));
        BigDecimal commission = stockCost.multiply(new BigDecimal("0.10"));
        BigDecimal totalCost = stockCost.add(commission);

        if (getBalance().compareTo(totalCost) >= 0) {
            StockTransaction transaction = new StockTransaction();
            transaction.setTicker(ticker);
            transaction.setQuantity(quantity);
            transaction.setPurchasePrice(price);
            transaction.setCommission(commission);

            if (stockTransactions.add(transaction)) {
                setBalance(getBalance().subtract(totalCost));
                return true;
            }
        }
        return false;
    }

    /**
     * Calculates the total value of the investment account
     * @return The total value including balance and stock holdings
     */
    public BigDecimal calculateTotalValue() {
        BigDecimal stockValue = BigDecimal.ZERO;
        
        for (StockTransaction transaction : stockTransactions) {
            stockValue = stockValue.add(transaction.getCurrentValue());
        }

        BigDecimal totalValue = getBalance().add(stockValue);
        return totalValue.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Checks if the investment account can be removed
     * @return true if the account has zero balance and no stock transactions, false otherwise
     */
    @Override
    public boolean canBeRemoved() {
        return getBalance().compareTo(BigDecimal.ZERO) == 0 && stockTransactions.isEmpty();
    }

    // Getters and setters
    public List<StockTransaction> getStockTransactions() {
        return stockTransactions;
    }

    public void setStockTransactions(List<StockTransaction> stockTransactions) {
        this.stockTransactions = stockTransactions;
    }
}