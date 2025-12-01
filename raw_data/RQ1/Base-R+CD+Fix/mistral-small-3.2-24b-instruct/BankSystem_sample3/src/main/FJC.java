import java.util.ArrayList;
import java.util.List;

class Customer {
    private String name;
    private String address;
    private List<Account> accounts = new ArrayList<>();

    /**
     * Adds a savings account to the customer's list of accounts.
     *
     * @param id The unique identifier for the savings account.
     * @param interestRate The interest rate for the savings account.
     * @return true if the account was added successfully, false otherwise.
     */
    public boolean addSavingAccount(String id, double interestRate) {
        if (findAccountById(id) != null) {
            return false;
        }
        accounts.add(new SavingAccount(id, interestRate));
        return true;
    }

    /**
     * Adds an investment account to the customer's list of accounts.
     *
     * @param id The unique identifier for the investment account.
     * @return true if the account was added successfully, false otherwise.
     */
    public boolean addInvestmentAccount(String id) {
        if (findAccountById(id) != null) {
            return false;
        }
        accounts.add(new InvestmentAccount(id));
        return true;
    }

    /**
     * Removes an investment account from the customer's list of accounts.
     *
     * @param id The unique identifier for the investment account.
     * @return true if the account was removed successfully, false otherwise.
     */
    public boolean removeInvestmentAccount(String id) {
        Account account = findAccountById(id);
        if (account == null || !(account instanceof InvestmentAccount) || ((InvestmentAccount) account).getTransactions().size() > 0 || account.getBalance() > 0) {
            return false;
        }
        return accounts.remove(account);
    }

    /**
     * Removes a savings account from the customer's list of accounts.
     *
     * @param id The unique identifier for the savings account.
     * @return true if the account was removed successfully, false otherwise.
     */
    public boolean removeSavingAccount(String id) {
        Account account = findAccountById(id);
        if (account == null || !(account instanceof SavingAccount) || account.getBalance() > 0) {
            return false;
        }
        return accounts.remove(account);
    }

    /**
     * Finds an account by its unique identifier.
     *
     * @param id The unique identifier for the account.
     * @return The account if found, null otherwise.
     */
    public Account findAccountById(String id) {
        for (Account account : accounts) {
            if (account.getId().equals(id)) {
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

abstract class Account {
    private String id;
    private double balance;

    public Account(String id) {
        this.id = id;
        this.balance = 0;
    }

    /**
     * Deposits money into the account.
     *
     * @param amount The amount of money to deposit.
     * @return true if the deposit was successful, false otherwise.
     */
    public boolean deposit(double amount) {
        if (amount <= 0 || amount > 1000000) {
            return false;
        }
        balance += amount;
        return true;
    }

    // Getters and setters
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

class SavingAccount extends Account {
    private double interestRate;

    public SavingAccount(String id, double interestRate) {
        super(id);
        this.interestRate = interestRate;
    }

    /**
     * Calculates the daily interest for the savings account and updates the balance.
     *
     * @return The calculated daily interest.
     */
    public double calculateDailyInterest() {
        double dailyInterest = getBalance() * interestRate / 360;
        double roundedInterest = Math.round(dailyInterest * 100.0) / 100.0;
        setBalance(getBalance() + roundedInterest);
        return roundedInterest;
    }

    // Getters and setters
    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
}

class InvestmentAccount extends Account {
    private List<StockTransaction> transactions = new ArrayList<>();

    public InvestmentAccount(String id) {
        super(id);
    }

    /**
     * Buys stocks and updates the investment account balance.
     *
     * @param stockSymbol The symbol of the stock.
     * @param quantity The quantity of stocks to buy.
     * @param price The price per stock.
     * @return true if the transaction was saved successfully, false otherwise.
     */
    public boolean buyStock(String stockSymbol, int quantity, double price) {
        double stockCost = quantity * price;
        double commission = stockCost * 0.1;
        double totalCost = stockCost + commission;

        if (getBalance() < totalCost) {
            return false;
        }

        setBalance(getBalance() - totalCost);
        transactions.add(new StockTransaction(stockSymbol, quantity, price, commission));
        return true;
    }

    /**
     * Calculates the value of the investment account, including the account balance and the total value of stocks.
     *
     * @return The calculated value of the investment account.
     */
    public double calculateValue() {
        if (transactions.isEmpty()) {
            return 0;
        }

        double totalValue = getBalance();
        for (StockTransaction transaction : transactions) {
            double stockValue = transaction.getQuantity() * (transaction.getPrice() * 1.1);
            totalValue += stockValue;
        }

        return Math.round(totalValue * 100.0) / 100.0;
    }

    // Getters and setters
    public List<StockTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<StockTransaction> transactions) {
        this.transactions = transactions;
    }
}

class StockTransaction {
    private String stock;
    private int quantity;
    private double price;
    private double commission;

    public StockTransaction(String stock, int quantity, double price, double commission) {
        this.stock = stock;
        this.quantity = quantity;
        this.price = price;
        this.commission = commission;
    }

    // Getters and setters
    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
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