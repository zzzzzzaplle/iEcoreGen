import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Represents a customer in the bank system.
 */
class Customer {
    private String name;
    private String address;
    private List<Account> accounts;

    public Customer() {
        this.accounts = new ArrayList<>();
    }

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
     * Adds an account to the customer's account list.
     *
     * @param account the account to add
     */
    public void addAccount(Account account) {
        accounts.add(account);
    }

    /**
     * Removes an account from the customer's account list by account ID.
     * The account can only be removed if it has zero balance and no stock transactions.
     *
     * @param accountId the ID of the account to remove
     * @return true if the account was successfully removed, false otherwise
     */
    public boolean removeAccount(String accountId) {
        Account accountToRemove = null;
        for (Account account : accounts) {
            if (account.getAccountId().equals(accountId)) {
                accountToRemove = account;
                break;
            }
        }

        if (accountToRemove != null) {
            if (accountToRemove.getBalance().compareTo(BigDecimal.ZERO) == 0) {
                if (accountToRemove instanceof InvestmentAccount) {
                    InvestmentAccount investmentAccount = (InvestmentAccount) accountToRemove;
                    if (investmentAccount.getStockTransactions().isEmpty()) {
                        accounts.remove(accountToRemove);
                        return true;
                    }
                } else {
                    accounts.remove(accountToRemove);
                    return true;
                }
            }
        }
        return false;
    }
}

/**
 * Represents a bank account.
 */
abstract class Account {
    protected String accountId;
    protected BigDecimal balance;

    public Account() {
        this.balance = BigDecimal.ZERO;
    }

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

    /**
     * Deposits money into the account.
     * The amount must be positive and cannot exceed $1,000,000.
     *
     * @param amount the amount to deposit
     * @return true if the deposit was successful, false otherwise
     */
    public boolean deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        if (amount.compareTo(new BigDecimal("1000000")) > 0) {
            return false;
        }
        balance = balance.add(amount);
        return true;
    }
}

/**
 * Represents a savings account with an interest rate.
 */
class SavingsAccount extends Account {
    private BigDecimal interestRate;

    public SavingsAccount() {
        super();
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * Calculates the daily interest for the savings account and adds it to the balance.
     * Daily interest = balance * interest rate / 360, rounded to 2 decimal places.
     */
    public void calculateDailyInterest() {
        BigDecimal dailyInterest = balance.multiply(interestRate).divide(new BigDecimal("360"), 2, RoundingMode.HALF_UP);
        balance = balance.add(dailyInterest);
    }
}

/**
 * Represents a stock transaction.
 */
class StockTransaction {
    private String ticker;
    private int quantity;
    private BigDecimal price;

    public StockTransaction() {
    }

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

/**
 * Represents an investment account for buying stocks.
 */
class InvestmentAccount extends Account {
    private List<StockTransaction> stockTransactions;
    private static final BigDecimal COMMISSION_RATE = new BigDecimal("0.10");

    public InvestmentAccount() {
        super();
        this.stockTransactions = new ArrayList<>();
    }

    public List<StockTransaction> getStockTransactions() {
        return stockTransactions;
    }

    public void setStockTransactions(List<StockTransaction> stockTransactions) {
        this.stockTransactions = stockTransactions;
    }

    /**
     * Buys stocks and updates the investment account balance.
     * The system saves the transaction records.
     * Before buying stocks, it is necessary to ensure that the current balance
     * is sufficient to cover the stock cost and the bank's commission.
     *
     * @param ticker   the stock ticker
     * @param quantity the number of stocks to buy
     * @param price    the price per stock
     * @return true if the transaction was successful, false otherwise
     */
    public boolean buyStocks(String ticker, int quantity, BigDecimal price) {
        BigDecimal stockCost = new BigDecimal(quantity).multiply(price);
        BigDecimal commission = stockCost.multiply(COMMISSION_RATE);
        BigDecimal totalCost = stockCost.add(commission);

        if (balance.compareTo(totalCost) < 0) {
            return false;
        }

        StockTransaction transaction = new StockTransaction();
        transaction.setTicker(ticker);
        transaction.setQuantity(quantity);
        transaction.setPrice(price);

        stockTransactions.add(transaction);
        balance = balance.subtract(totalCost);
        return true;
    }

    /**
     * Calculates the total value of the investment account, including the account balance
     * and the total value of stocks.
     * The value of each stock is the number of stocks multiplied by the current stock market price
     * (1.1 times its purchase price).
     *
     * @return the total value of the investment account, rounded to 2 decimal places
     */
    public BigDecimal calculateInvestmentValue() {
        BigDecimal totalStockValue = BigDecimal.ZERO;

        for (StockTransaction transaction : stockTransactions) {
            BigDecimal currentPrice = transaction.getPrice().multiply(new BigDecimal("1.1"));
            BigDecimal stockValue = new BigDecimal(transaction.getQuantity()).multiply(currentPrice);
            totalStockValue = totalStockValue.add(stockValue);
        }

        BigDecimal totalValue = balance.add(totalStockValue);
        return totalValue.setScale(2, RoundingMode.HALF_UP);
    }
}