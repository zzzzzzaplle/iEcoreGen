import java.util.ArrayList;
import java.util.List;

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

    /**
     * Adds an account to the customer's list of accounts.
     * @param account The account to be added.
     * @return true if the account was added successfully, false otherwise.
     */
    public boolean addAccount(Account account) {
        if (account == null) {
            return false;
        }
        if (accounts.stream().anyMatch(a -> a.getId().equals(account.getId()))) {
            return false;
        }
        return accounts.add(account);
    }

    /**
     * Removes an account from the customer's list of accounts.
     * @param accountId The ID of the account to be removed.
     * @return true if the account was removed successfully, false otherwise.
     */
    public boolean removeAccount(String accountId) {
        return accounts.removeIf(account -> account.getId().equals(accountId) && account.getBalance() == 0 && account.getStockTransactions().isEmpty());
    }
}

abstract class Account {
    private String id;
    private double balance;

    public Account() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Deposits money into the account.
     * @param amount The amount to deposit.
     * @return true if the deposit was successful, false otherwise.
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
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * Calculates the daily interest and updates the account balance.
     */
    public void calculateDailyInterest() {
        double dailyInterest = (getBalance() * interestRate) / 360;
        double roundedInterest = Math.round(dailyInterest * 100.0) / 100.0;
        setBalance(getBalance() + roundedInterest);
    }
}

class InvestmentAccount extends Account {
    private List<StockTransaction> stockTransactions;

    public InvestmentAccount() {
        this.stockTransactions = new ArrayList<>();
    }

    public List<StockTransaction> getStockTransactions() {
        return stockTransactions;
    }

    /**
     * Buys stocks and updates the investment account balance.
     * @param ticker The stock ticker.
     * @param quantity The quantity of stocks to buy.
     * @param price The price per stock.
     * @return true if the transaction was saved successfully, false otherwise.
     */
    public boolean buyStocks(String ticker, int quantity, double price) {
        double totalCost = quantity * price;
        double commission = totalCost * 0.1;
        double totalAmount = totalCost + commission;

        if (getBalance() < totalAmount) {
            return false;
        }

        setBalance(getBalance() - totalAmount);
        StockTransaction transaction = new StockTransaction(ticker, quantity, price);
        return stockTransactions.add(transaction);
    }

    /**
     * Calculates the value of the investment account, including the account balance and the total value of stocks.
     * @return The total value of the investment account, or 0 if there are no stock transactions.
     */
    public double calculateAccountValue() {
        if (stockTransactions.isEmpty()) {
            return 0;
        }

        double totalStockValue = stockTransactions.stream()
                .mapToDouble(transaction -> transaction.getQuantity() * (transaction.getPrice() * 1.1))
                .sum();

        double totalValue = getBalance() + totalStockValue;
        return Math.round(totalValue * 100.0) / 100.0;
    }
}

class StockTransaction {
    private String ticker;
    private int quantity;
    private double price;

    public StockTransaction() {
    }

    public StockTransaction(String ticker, int quantity, double price) {
        this.ticker = ticker;
        this.quantity = quantity;
        this.price = price;
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
}