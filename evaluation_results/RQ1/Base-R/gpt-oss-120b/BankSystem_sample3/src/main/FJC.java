import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Represents a customer of the bank.
 * A customer has a name, an address and a list of accounts.
 */
 class Customer {

    private String name;
    private String address;
    private List<Account> accounts = new ArrayList<>();

    /** No‑argument constructor required by the task */
    public Customer() {
    }

    /** Getter / Setter methods */
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
     * Adds an account to the customer.
     *
     * @param account the account to be added (must not be {@code null})
     */
    public void addAccount(Account account) {
        if (account != null) {
            accounts.add(account);
        }
    }

    /**
     * Removes an account identified by {@code accountId}.
     * <p>
     * The account can be removed only when:
     * <ul>
     *   <li>its balance is zero, and</li>
     *   <li>for an {@link InvestmentAccount} it has no stock transactions.</li>
     * </ul>
     *
     * @param accountId the unique identifier of the account to be removed
     * @return {@code true} if the account was removed, {@code false} otherwise
     */
    public boolean removeAccount(String accountId) {
        Iterator<Account> it = accounts.iterator();
        while (it.hasNext()) {
            Account acc = it.next();
            if (Objects.equals(acc.getId(), accountId)) {
                boolean canRemove = acc.getBalance().compareTo(BigDecimal.ZERO) == 0;
                if (acc instanceof InvestmentAccount) {
                    canRemove = canRemove && ((InvestmentAccount) acc).getTransactions().isEmpty();
                }
                if (canRemove) {
                    it.remove();
                    return true;
                }
                return false;
            }
        }
        return false; // not found
    }

    /**
     * Deposits money into the account identified by {@code accountId}.
     * The amount must be positive and not exceed $1,000,000.
     *
     * @param accountId the unique identifier of the target account
     * @param amount    the amount to deposit (must be > 0 and ≤ 1,000,000)
     * @return {@code true} if the deposit succeeded, {@code false} otherwise
     */
    public boolean deposit(String accountId, double amount) {
        if (amount <= 0 || amount > 1_000_000) {
            return false;
        }
        for (Account acc : accounts) {
            if (Objects.equals(acc.getId(), accountId)) {
                BigDecimal newBalance = acc.getBalance()
                        .add(BigDecimal.valueOf(amount))
                        .setScale(2, RoundingMode.HALF_UP);
                acc.setBalance(newBalance);
                return true;
            }
        }
        return false; // account not found
    }

    /**
     * Retrieves an account of a given id.
     *
     * @param accountId the id to look for
     * @return the {@link Account} instance or {@code null} if not found
     */
    public Account getAccountById(String accountId) {
        for (Account acc : accounts) {
            if (Objects.equals(acc.getId(), accountId)) {
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

    private String id;                       // unique account identifier
    private BigDecimal balance = BigDecimal.ZERO; // always stored with 2 decimal places

    /** No‑argument constructor required by the task */
    public Account() {
    }

    /** Getter / Setter methods */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance.setScale(2, RoundingMode.HALF_UP);
    }

    public void setBalance(BigDecimal balance) {
        if (balance != null) {
            this.balance = balance.setScale(2, RoundingMode.HALF_UP);
        }
    }
}

/**
 * Savings account that earns interest.
 */
class SavingsAccount extends Account {

    private double interestRate; // e.g., 0.05 for 5%

    /** No‑argument constructor required by the task */
    public SavingsAccount() {
    }

    /** Getter / Setter methods */
    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * Calculates the daily interest and adds it to the balance.
     * Daily interest = balance × interestRate / 360.
     * The result is rounded to two decimal places.
     *
     * @return the amount of interest that was added
     */
    public BigDecimal applyDailyInterest() {
        BigDecimal dailyInterest = getBalance()
                .multiply(BigDecimal.valueOf(interestRate))
                .divide(BigDecimal.valueOf(360), 10, RoundingMode.HALF_UP) // intermediate precision
                .setScale(2, RoundingMode.HALF_UP);
        setBalance(getBalance().add(dailyInterest));
        return dailyInterest;
    }
}

/**
 * Investment account used to buy stocks.
 */
class InvestmentAccount extends Account {

    private List<StockTransaction> transactions = new ArrayList<>();

    /** No‑argument constructor required by the task */
    public InvestmentAccount() {
    }

    /** Getter / Setter methods */
    public List<StockTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<StockTransaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * Attempts to buy a stock.
     * <p>
     * The method checks that the account balance can cover the stock cost
     * plus a 10% commission. If sufficient, the balance is reduced,
     * a new {@link StockTransaction} is stored and {@code true} is returned.
     *
     * @param ticker   the stock ticker symbol
     * @param quantity number of shares to buy (must be positive)
     * @param price    purchase price per share (must be positive)
     * @return {@code true} if the transaction was recorded, {@code false} otherwise
     */
    public boolean buyStock(String ticker, int quantity, double price) {
        if (ticker == null || ticker.isEmpty() || quantity <= 0 || price <= 0) {
            return false;
        }
        BigDecimal stockCost = BigDecimal.valueOf(quantity).multiply(BigDecimal.valueOf(price));
        BigDecimal commission = stockCost.multiply(BigDecimal.valueOf(0.10)); // 10%
        BigDecimal totalDeduction = stockCost.add(commission).setScale(2, RoundingMode.HALF_UP);

        if (getBalance().compareTo(totalDeduction) < 0) {
            return false; // insufficient funds
        }

        // Update balance
        setBalance(getBalance().subtract(totalDeduction));

        // Record transaction
        StockTransaction tx = new StockTransaction();
        tx.setTicker(ticker);
        tx.setQuantity(quantity);
        tx.setPurchasePrice(price);
        transactions.add(tx);
        return true;
    }

    /**
     * Calculates the total value of the investment account.
     * Value = current balance + Σ (quantity × purchasePrice × 1.1)
     * (the market price is assumed to be 1.1 times the purchase price).
     * The result is rounded to two decimal places.
     *
     * @return total account value; {@code 0} if there are no stock transactions
     */
    public BigDecimal calculateTotalValue() {
        if (transactions.isEmpty()) {
            return BigDecimal.ZERO.setScale(2);
        }
        BigDecimal stockValue = BigDecimal.ZERO;
        for (StockTransaction tx : transactions) {
            BigDecimal curPrice = BigDecimal.valueOf(tx.getPurchasePrice())
                    .multiply(BigDecimal.valueOf(1.1));
            BigDecimal value = curPrice.multiply(BigDecimal.valueOf(tx.getQuantity()));
            stockValue = stockValue.add(value);
        }
        BigDecimal total = getBalance().add(stockValue).setScale(2, RoundingMode.HALF_UP);
        return total;
    }
}

/**
 * Represents a single stock purchase transaction.
 */
class StockTransaction {

    private String ticker;
    private int quantity;
    private double purchasePrice; // price per share at purchase time

    /** No‑argument constructor required by the task */
    public StockTransaction() {
    }

    /** Getter / Setter methods */
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

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
}