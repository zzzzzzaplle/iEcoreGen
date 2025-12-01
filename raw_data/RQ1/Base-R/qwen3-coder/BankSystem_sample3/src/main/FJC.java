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
     * Adds an account to the customer's account list.
     *
     * @param account The account to be added
     */
    public void addAccount(Account account) {
        accounts.add(account);
    }

    /**
     * Removes an account from the customer's account list based on account ID.
     * The account can only be removed if it has zero balance and no stock transactions.
     *
     * @param accountId The ID of the account to be removed
     * @return true if the account is successfully removed, false otherwise
     */
    public boolean removeAccount(String accountId) {
        for (Account account : accounts) {
            if (account.getAccountId().equals(accountId)) {
                if (account.getBalance() == 0) {
                    if (account instanceof SavingsAccount) {
                        accounts.remove(account);
                        return true;
                    } else if (account instanceof InvestmentAccount) {
                        InvestmentAccount investmentAccount = (InvestmentAccount) account;
                        if (investmentAccount.getStockTransactions().isEmpty()) {
                            accounts.remove(account);
                            return true;
                        }
                    }
                }
                return false;
            }
        }
        return false;
    }
}

abstract class Account {
    protected String accountId;
    protected double balance;

    public Account() {
        this.balance = 0.0;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Deposits money into the account.
     * The amount must be positive and cannot exceed $1,000,000.
     *
     * @param amount The amount to deposit
     * @return true if the deposit is successful, false otherwise
     */
    public boolean deposit(double amount) {
        if (amount <= 0 || amount > 1000000) {
            return false;
        }
        balance += amount;
        return true;
    }
}

class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount() {
        super();
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * Calculates the daily interest for the savings account and updates the balance.
     * Daily interest = balance * interest rate / 360, rounded to two decimal places.
     *
     * @return The daily interest amount
     */
    public double calculateDailyInterest() {
        double dailyInterest = balance * interestRate / 360;
        dailyInterest = Math.round(dailyInterest * 100.0) / 100.0;
        balance += dailyInterest;
        return dailyInterest;
    }
}

class InvestmentAccount extends Account {
    private List<StockTransaction> stockTransactions;
    private static final double COMMISSION_RATE = 0.10;

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
     * Checks if the balance is sufficient to cover the stock cost and commission.
     * Stock cost = number of stocks * price.
     * Commission = 10% of the stock cost.
     *
     * @param ticker The stock ticker symbol
     * @param quantity The number of stocks to buy
     * @param price The price per stock
     * @return true if the transaction is successful, false otherwise
     */
    public boolean buyStocks(String ticker, int quantity, double price) {
        double stockCost = quantity * price;
        double commission = stockCost * COMMISSION_RATE;
        double totalCost = stockCost + commission;

        if (balance < totalCost) {
            return false;
        }

        balance -= totalCost;
        StockTransaction transaction = new StockTransaction();
        transaction.setTicker(ticker);
        transaction.setQuantity(quantity);
        transaction.setPrice(price);
        transaction.setCommission(commission);
        stockTransactions.add(transaction);
        return true;
    }

    /**
     * Calculates the total value of the investment account.
     * Includes the account balance and the total value of stocks.
     * Stock value = quantity * current market price (1.1 times purchase price).
     * Returns 0 if there are no stock transactions.
     *
     * @return The total value of the investment account, rounded to two decimal places
     */
    public double calculateInvestmentValue() {
        if (stockTransactions.isEmpty()) {
            return 0.0;
        }

        double totalStockValue = 0.0;
        for (StockTransaction transaction : stockTransactions) {
            double currentMarketPrice = transaction.getPrice() * 1.1;
            totalStockValue += transaction.getQuantity() * currentMarketPrice;
        }

        double totalValue = balance + totalStockValue;
        return Math.round(totalValue * 100.0) / 100.0;
    }
}

class StockTransaction {
    private String ticker;
    private int quantity;
    private double price;
    private double commission;

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