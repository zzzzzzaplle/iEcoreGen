import java.util.ArrayList;
import java.util.List;

class Customer {
    private String name;
    private String address;
    private List<Account> accounts;

    /**
     * Default constructor for Customer.
     */
    public Customer() {
        this.accounts = new ArrayList<>();
    }

    /**
     * Constructor for Customer with name and address.
     * @param name The name of the customer.
     * @param address The address of the customer.
     */
    public Customer(String name, String address) {
        this();
        this.name = name;
        this.address = address;
    }

    /**
     * Adds an account to the customer's list of accounts.
     * @param account The account to be added.
     */
    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    /**
     * Removes an account from the customer's list of accounts.
     * @param accountId The ID of the account to be removed.
     * @return true if the account was successfully removed, false otherwise.
     */
    public boolean removeAccount(String accountId) {
        for (Account account : accounts) {
            if (account.getId().equals(accountId)) {
                if (account instanceof SavingsAccount) {
                    if (((SavingsAccount) account).getBalance() == 0) {
                        accounts.remove(account);
                        return true;
                    }
                } else if (account instanceof InvestmentAccount) {
                    if (((InvestmentAccount) account).getBalance() == 0 && ((InvestmentAccount) account).getStockTransactions().isEmpty()) {
                        accounts.remove(account);
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    // Getters and Setters
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

abstract class Account {
    private String id;
    private double balance;

    /**
     * Default constructor for Account.
     */
    public Account() {
    }

    /**
     * Constructor for Account with ID and balance.
     * @param id The ID of the account.
     * @param balance The initial balance of the account.
     */
    public Account(String id, double balance) {
        this.id = id;
        this.balance = balance;
    }

    /**
     * Deposits money into the account.
     * @param amount The amount to deposit.
     * @return true if the deposit was successful, false otherwise.
     */
    public boolean deposit(double amount) {
        if (amount > 0 && amount <= 1000000) {
            this.balance += amount;
            return true;
        }
        return false;
    }

    // Getters and Setters
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
}

class SavingsAccount extends Account {
    private double interestRate;

    /**
     * Default constructor for SavingsAccount.
     */
    public SavingsAccount() {
    }

    /**
     * Constructor for SavingsAccount with ID, balance, and interest rate.
     * @param id The ID of the account.
     * @param balance The initial balance of the account.
     * @param interestRate The interest rate of the account.
     */
    public SavingsAccount(String id, double balance, double interestRate) {
        super(id, balance);
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

    // Getters and Setters
    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
}

class InvestmentAccount extends Account {
    private List<StockTransaction> stockTransactions;

    /**
     * Default constructor for InvestmentAccount.
     */
    public InvestmentAccount() {
        this.stockTransactions = new ArrayList<>();
    }

    /**
     * Constructor for InvestmentAccount with ID and balance.
     * @param id The ID of the account.
     * @param balance The initial balance of the account.
     */
    public InvestmentAccount(String id, double balance) {
        this();
        super.setId(id);
        super.setBalance(balance);
    }

    /**
     * Buys stocks and updates the investment account balance.
     * @param ticker The ticker symbol of the stock.
     * @param quantity The quantity of stocks to buy.
     * @param price The price per stock.
     * @return true if the transaction was successful, false otherwise.
     */
    public boolean buyStocks(String ticker, int quantity, double price) {
        double stockCost = quantity * price;
        double commission = stockCost * 0.1;
        double totalCost = stockCost + commission;

        if (getBalance() >= totalCost) {
            StockTransaction transaction = new StockTransaction(ticker, quantity, price);
            stockTransactions.add(transaction);
            setBalance(getBalance() - totalCost);
            return true;
        }
        return false;
    }

    /**
     * Calculates the value of the investment account.
     * @return The total value of the account, including balance and stocks.
     */
    public double calculateAccountValue() {
        double totalValue = getBalance();
        for (StockTransaction transaction : stockTransactions) {
            double stockValue = transaction.getQuantity() * (transaction.getPrice() * 1.1);
            double roundedValue = Math.round(stockValue * 100.0) / 100.0;
            totalValue += roundedValue;
        }
        return totalValue;
    }

    // Getters and Setters
    public List<StockTransaction> getStockTransactions() {
        return stockTransactions;
    }

    public void setStockTransactions(List<StockTransaction> stockTransactions) {
        this.stockTransactions = stockTransactions;
    }
}

class StockTransaction {
    private String ticker;
    private int quantity;
    private double price;

    /**
     * Default constructor for StockTransaction.
     */
    public StockTransaction() {
    }

    /**
     * Constructor for StockTransaction with ticker, quantity, and price.
     * @param ticker The ticker symbol of the stock.
     * @param quantity The quantity of stocks.
     * @param price The price per stock.
     */
    public StockTransaction(String ticker, int quantity, double price) {
        this.ticker = ticker;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and Setters
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