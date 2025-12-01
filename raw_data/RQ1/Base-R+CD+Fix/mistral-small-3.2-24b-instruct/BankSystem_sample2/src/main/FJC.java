import java.util.ArrayList;
import java.util.List;

class Customer {
    private String name;
    private String address;
    private List<Account> accounts;

    public Customer() {
        this.accounts = new ArrayList<>();
    }

    /**
     * Adds a savings account to the customer's accounts.
     *
     * @param id The unique identifier for the account.
     * @param interestRate The interest rate for the savings account.
     * @return true if the account was added successfully, false otherwise.
     */
    public boolean addSavingAccount(String id, double interestRate) {
        if (findAccountById(id) != null) {
            return false;
        }
        SavingAccount account = new SavingAccount();
        account.setId(id);
        account.setInterestRate(interestRate);
        accounts.add(account);
        return true;
    }

    /**
     * Adds an investment account to the customer's accounts.
     *
     * @param id The unique identifier for the account.
     * @return true if the account was added successfully, false otherwise.
     */
    public boolean addInvestmentAccount(String id) {
        if (findAccountById(id) != null) {
            return false;
        }
        InvestmentAccount account = new InvestmentAccount();
        account.setId(id);
        accounts.add(account);
        return true;
    }

    /**
     * Removes an investment account from the customer's accounts.
     *
     * @param id The unique identifier for the account.
     * @return true if the account was removed successfully, false otherwise.
     */
    public boolean removeInvestmentAccount(String id) {
        Account account = findAccountById(id);
        if (account == null || !(account instanceof InvestmentAccount) || ((InvestmentAccount) account).getTransactions().size() > 0 || account.getBalance() > 0) {
            return false;
        }
        accounts.remove(account);
        return true;
    }

    /**
     * Removes a savings account from the customer's accounts.
     *
     * @param id The unique identifier for the account.
     * @return true if the account was removed successfully, false otherwise.
     */
    public boolean removeSavingAccount(String id) {
        Account account = findAccountById(id);
        if (account == null || !(account instanceof SavingAccount) || account.getBalance() > 0) {
            return false;
        }
        accounts.remove(account);
        return true;
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

    public Account() {
    }

    /**
     * Deposits money into the account.
     *
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

    public SavingAccount() {
    }

    /**
     * Calculates the daily interest for the savings account.
     *
     * @return The calculated daily interest.
     */
    public double calculateDailyInterest() {
        double dailyInterest = getBalance() * interestRate / 360;
        return Math.round(dailyInterest * 100.0) / 100.0;
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
    private List<StockTransaction> transactions;

    public InvestmentAccount() {
        this.transactions = new ArrayList<>();
    }

    /**
     * Buys stocks and updates the investment account balance.
     *
     * @param stockSymbol The symbol of the stock.
     * @param quantity The quantity of the stock.
     * @param price The price of the stock.
     * @return true if the transaction was saved successfully, false otherwise.
     */
    public boolean buyStock(String stockSymbol, int quantity, double price) {
        double cost = quantity * price;
        double commission = cost * 0.1;
        if (getBalance() < cost + commission) {
            return false;
        }
        StockTransaction transaction = new StockTransaction();
        transaction.setStock(stockSymbol);
        transaction.setQuantity(quantity);
        transaction.setPrice(price);
        transaction.setCommission(commission);
        transactions.add(transaction);
        setBalance(getBalance() - cost - commission);
        return true;
    }

    /**
     * Calculates the value of the investment account.
     *
     * @return The calculated value of the investment account.
     */
    public double calculateValue() {
        double totalValue = getBalance();
        for (StockTransaction transaction : transactions) {
            double stockValue = transaction.getQuantity() * transaction.getPrice() * 1.1;
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

    public StockTransaction() {
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