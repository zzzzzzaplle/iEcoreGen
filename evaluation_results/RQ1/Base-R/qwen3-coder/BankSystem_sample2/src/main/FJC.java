import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

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
     * Adds an account to the customer's account list
     * @param account The account to be added
     */
    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    /**
     * Removes an account from the customer's account list by account ID
     * @param accountId The ID of the account to be removed
     * @return true if the operation is successful, false otherwise
     */
    public boolean removeAccount(String accountId) {
        Account accountToRemove = null;
        for (Account account : accounts) {
            if (account.getAccountId().equals(accountId)) {
                // Check if account has balance or stock transactions
                if (account.getBalance().compareTo(BigDecimal.ZERO) != 0) {
                    return false;
                }
                
                if (account instanceof InvestmentAccount) {
                    InvestmentAccount investmentAccount = (InvestmentAccount) account;
                    if (!investmentAccount.getStockTransactions().isEmpty()) {
                        return false;
                    }
                }
                
                accountToRemove = account;
                break;
            }
        }
        
        if (accountToRemove != null) {
            accounts.remove(accountToRemove);
            return true;
        }
        
        return false;
    }
}

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
     * Deposits money into the account
     * @param amount The amount to deposit
     * @return true if the operation is successful, false otherwise
     */
    public boolean deposit(BigDecimal amount) {
        // Check that the amount must be positive and cannot exceed the maximum single-deposit limit of $1,000,000
        if (amount.compareTo(BigDecimal.ZERO) <= 0 || amount.compareTo(new BigDecimal("1000000")) > 0) {
            return false;
        }
        
        this.balance = this.balance.add(amount);
        return true;
    }
}

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
     * Calculates the daily interest of the savings account and updates the balance
     * Daily interest = balance * interest rate / 360
     * @return the daily interest with two decimal places
     */
    public BigDecimal calculateDailyInterest() {
        if (interestRate == null || balance.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        
        // Daily interest = balance * interest rate / 360
        BigDecimal dailyInterest = balance.multiply(interestRate).divide(new BigDecimal("360"), 10, RoundingMode.HALF_UP);
        dailyInterest = dailyInterest.setScale(2, RoundingMode.HALF_UP);
        
        // Update the balance with daily interest
        this.balance = this.balance.add(dailyInterest);
        
        return dailyInterest;
    }
}

class InvestmentAccount extends Account {
    private List<StockTransaction> stockTransactions;
    private static final BigDecimal COMMISSION_RATE = new BigDecimal("0.10"); // 10% commission

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
     * Buys stocks and updates the investment account balance
     * @param ticker The stock ticker symbol
     * @param quantity The number of stocks to buy
     * @param price The price per stock
     * @return true if the transaction is successful, false otherwise
     */
    public boolean buyStocks(String ticker, int quantity, BigDecimal price) {
        // Calculate stock cost
        BigDecimal stockCost = price.multiply(new BigDecimal(quantity));
        
        // Calculate commission (10% of stock cost)
        BigDecimal commission = stockCost.multiply(COMMISSION_RATE);
        
        // Total cost = stock cost + commission
        BigDecimal totalCost = stockCost.add(commission);
        
        // Check if balance is sufficient
        if (this.balance.compareTo(totalCost) < 0) {
            return false;
        }
        
        // Deduct total cost from balance
        this.balance = this.balance.subtract(totalCost);
        
        // Save transaction record
        StockTransaction transaction = new StockTransaction();
        transaction.setTicker(ticker);
        transaction.setQuantity(quantity);
        transaction.setPurchasePrice(price);
        transaction.setCommission(commission);
        this.stockTransactions.add(transaction);
        
        return true;
    }

    /**
     * Calculates the total value of the investment account including balance and stock values
     * The value of each stock is the number of stocks multiplied by the current stock market price (1.1 times its purchase price)
     * @return the total value with two decimal places, or 0 if there are no stock transactions
     */
    public BigDecimal calculateInvestmentValue() {
        BigDecimal totalValue = new BigDecimal(this.balance.toString()); // Start with account balance
        
        // If there are no stock transactions, return just the balance
        if (this.stockTransactions.isEmpty()) {
            return totalValue.setScale(2, RoundingMode.HALF_UP);
        }
        
        // Current market price is 1.1 times purchase price
        BigDecimal marketPriceMultiplier = new BigDecimal("1.1");
        
        // Calculate total value of all stocks
        for (StockTransaction transaction : this.stockTransactions) {
            BigDecimal purchasePrice = transaction.getPurchasePrice();
            BigDecimal currentPrice = purchasePrice.multiply(marketPriceMultiplier);
            BigDecimal stockValue = currentPrice.multiply(new BigDecimal(transaction.getQuantity()));
            totalValue = totalValue.add(stockValue);
        }
        
        return totalValue.setScale(2, RoundingMode.HALF_UP);
    }
}

class StockTransaction {
    private String ticker;
    private int quantity;
    private BigDecimal purchasePrice;
    private BigDecimal commission;

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