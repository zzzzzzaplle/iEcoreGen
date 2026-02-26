import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Customer {
    private String name;
    private String address;
    private List<Account> accounts;

    public Customer() {
        this.accounts = new ArrayList<>();
    }

    public Customer(String name, String address) {
        this();
        this.name = name;
        this.address = address;
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
     * Adds an account to the customer's list of accounts.
     * @param account The account to add.
     */
    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    /**
     * Removes an account from the customer's list of accounts.
     * @param accountId The ID of the account to remove.
     * @return true if the account was successfully removed, false otherwise.
     */
    public boolean removeAccount(String accountId) {
        Account accountToRemove = null;
        for (Account account : accounts) {
            if (account.getId().equals(accountId)) {
                accountToRemove = account;
                break;
            }
        }

        if (accountToRemove != null && accountToRemove.getBalance().compareTo(BigDecimal.ZERO) == 0 && accountToRemove.getStockTransactions().isEmpty()) {
            accounts.remove(accountToRemove);
            return true;
        }
        return false;
    }
}

abstract class Account {
    private String id;
    private BigDecimal balance;

    public Account() {
        this.balance = BigDecimal.ZERO;
    }

    public Account(String id) {
        this();
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * Deposits money into the account.
     * @param amount The amount to deposit.
     * @return true if the deposit was successful, false otherwise.
     */
    public boolean deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0 || amount.compareTo(new BigDecimal("1000000")) > 0) {
            return false;
        }
        this.balance = this.balance.add(amount);
        return true;
    }

    public abstract void updateDailyInterest();

    public abstract BigDecimal calculateValue();
}

class SavingsAccount extends Account {
    private BigDecimal interestRate;

    public SavingsAccount() {
        super();
    }

    public SavingsAccount(String id, BigDecimal interestRate) {
        super(id);
        this.interestRate = interestRate;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public void updateDailyInterest() {
        LocalTime now = LocalTime.now();
        if (now.getHour() == 23 && now.getMinute() == 59 && now.getSecond() == 59) {
            BigDecimal dailyInterest = getBalance().multiply(interestRate).divide(new BigDecimal("360"), 2, RoundingMode.HALF_UP);
            setBalance(getBalance().add(dailyInterest));
        }
    }

    @Override
    public BigDecimal calculateValue() {
        return getBalance();
    }
}

class InvestmentAccount extends Account {
    private Map<String, StockTransaction> stockTransactions;

    public InvestmentAccount() {
        super();
        this.stockTransactions = new HashMap<>();
    }

    public InvestmentAccount(String id) {
        this();
        setId(id);
    }

    public Map<String, StockTransaction> getStockTransactions() {
        return stockTransactions;
    }

    public void setStockTransactions(Map<String, StockTransaction> stockTransactions) {
        this.stockTransactions = stockTransactions;
    }

    /**
     * Buys stocks and updates the investment account balance.
     * @param ticker The stock ticker.
     * @param quantity The number of stocks to buy.
     * @param price The price per stock.
     * @return true if the transaction was successful, false otherwise.
     */
    public boolean buyStocks(String ticker, int quantity, BigDecimal price) {
        BigDecimal cost = new BigDecimal(quantity).multiply(price);
        BigDecimal commission = cost.multiply(new BigDecimal("0.1"));
        BigDecimal totalCost = cost.add(commission);

        if (getBalance().compareTo(totalCost) < 0) {
            return false;
        }

        setBalance(getBalance().subtract(totalCost));

        StockTransaction transaction = stockTransactions.get(ticker);
        if (transaction == null) {
            transaction = new StockTransaction(ticker, quantity, price);
            stockTransactions.put(ticker, transaction);
        } else {
            transaction.addQuantity(quantity, price);
        }

        return true;
    }

    @Override
    public void updateDailyInterest() {
        // No daily interest for investment accounts
    }

    @Override
    public BigDecimal calculateValue() {
        BigDecimal totalValue = BigDecimal.ZERO;
        for (StockTransaction transaction : stockTransactions.values()) {
            BigDecimal currentPrice = transaction.getPrice().multiply(new BigDecimal("1.1"));
            totalValue = totalValue.add(currentPrice.multiply(new BigDecimal(transaction.getTotalQuantity())));
        }
        return totalValue.add(getBalance()).setScale(2, RoundingMode.HALF_UP);
    }
}

class StockTransaction {
    private String ticker;
    private int totalQuantity;
    private BigDecimal price;

    public StockTransaction() {
    }

    public StockTransaction(String ticker, int quantity, BigDecimal price) {
        this.ticker = ticker;
        this.totalQuantity = quantity;
        this.price = price;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Adds quantity to the total quantity of the stock transaction.
     * @param quantity The quantity to add.
     * @param price The price per stock.
     */
    public void addQuantity(int quantity, BigDecimal price) {
        this.totalQuantity += quantity;
        // Update the average price
        BigDecimal totalCost = this.price.multiply(new BigDecimal(this.totalQuantity - quantity));
        totalCost = totalCost.add(price.multiply(new BigDecimal(quantity)));
        this.price = totalCost.divide(new BigDecimal(this.totalQuantity), 2, RoundingMode.HALF_UP);
    }
}