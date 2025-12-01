import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer in the bank system.
 */
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
     * Gets the name of the customer.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the customer.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the address of the customer.
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the customer.
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the list of accounts associated with the customer.
     * @return the accounts
     */
    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * Sets the list of accounts associated with the customer.
     * @param accounts the accounts to set
     */
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    /**
     * Adds a savings account to the customer's accounts.
     * @param id the unique id of the account
     * @param interestRate the interest rate of the savings account
     * @return true if the account is added successfully, false otherwise
     */
    public boolean addSavingAccount(String id, double interestRate) {
        SavingAccount account = new SavingAccount();
        account.setId(id);
        account.setInterestRate(interestRate);
        return accounts.add(account);
    }

    /**
     * Adds an investment account to the customer's accounts.
     * @param id the unique id of the account
     * @return true if the account is added successfully, false otherwise
     */
    public boolean addInvestmentAccount(String id) {
        InvestmentAccount account = new InvestmentAccount();
        account.setId(id);
        return accounts.add(account);
    }

    /**
     * Removes an investment account from the customer's accounts.
     * @param id the unique id of the account to be removed
     * @return true if the account is removed successfully, false otherwise
     */
    public boolean removeInvestmentAccount(String id) {
        Account account = findAccountById(id);
        if (account instanceof InvestmentAccount && account.getBalance() == 0 && ((InvestmentAccount) account).getTransactions().isEmpty()) {
            return accounts.remove(account);
        }
        return false;
    }

    /**
     * Removes a savings account from the customer's accounts.
     * @param id the unique id of the account to be removed
     * @return true if the account is removed successfully, false otherwise
     */
    public boolean removeSavingAccount(String id) {
        Account account = findAccountById(id);
        if (account instanceof SavingAccount && account.getBalance() == 0) {
            return accounts.remove(account);
        }
        return false;
    }

    /**
     * Finds an account by its id.
     * @param id the id of the account to be found
     * @return the account if found, null otherwise
     */
    public Account findAccountById(String id) {
        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                return account;
            }
        }
        return null;
    }
}

/**
 * Represents a bank account.
 */
abstract class Account {
    private String id;
    protected double balance;

    /**
     * Default constructor for Account.
     */
    public Account() {}

    /**
     * Gets the id of the account.
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id of the account.
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the balance of the account.
     * @return the balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Sets the balance of the account.
     * @param balance the balance to set
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Deposits money into the account.
     * @param amount the amount to be deposited
     * @return true if the deposit is successful, false otherwise
     */
    public boolean deposit(double amount) {
        if (amount > 0 && amount <= 1000000) {
            balance += amount;
            return true;
        }
        return false;
    }
}

/**
 * Represents a savings account.
 */
class SavingAccount extends Account {
    private double interestRate;

    /**
     * Default constructor for SavingAccount.
     */
    public SavingAccount() {}

    /**
     * Gets the interest rate of the savings account.
     * @return the interestRate
     */
    public double getInterestRate() {
        return interestRate;
    }

    /**
     * Sets the interest rate of the savings account.
     * @param interestRate the interestRate to set
     */
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * Calculates the daily interest of the savings account.
     * @return the daily interest
     */
    public double calculateDailyInterest() {
        double dailyInterest = balance * interestRate / 360;
        dailyInterest = Math.round(dailyInterest * 100.0) / 100.0; // Keep two decimal places
        balance += dailyInterest;
        return dailyInterest;
    }
}

/**
 * Represents an investment account.
 */
class InvestmentAccount extends Account {
    private List<StockTransaction> transactions;

    /**
     * Default constructor for InvestmentAccount.
     */
    public InvestmentAccount() {
        this.transactions = new ArrayList<>();
    }

    /**
     * Gets the list of stock transactions associated with the investment account.
     * @return the transactions
     */
    public List<StockTransaction> getTransactions() {
        return transactions;
    }

    /**
     * Sets the list of stock transactions associated with the investment account.
     * @param transactions the transactions to set
     */
    public void setTransactions(List<StockTransaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * Buys stocks and updates the investment account balance.
     * @param stockSymbol the symbol of the stock to be bought
     * @param quantity the quantity of the stock to be bought
     * @param price the price of the stock to be bought
     * @return true if the stock is bought successfully, false otherwise
     */
    public boolean buyStock(String stockSymbol, int quantity, double price) {
        double cost = quantity * price;
        double commission = cost * 0.1;
        if (balance >= cost + commission) {
            balance -= cost + commission;
            StockTransaction transaction = new StockTransaction();
            transaction.setStock(stockSymbol);
            transaction.setQuantity(quantity);
            transaction.setPrice(price);
            transaction.setCommission(commission);
            transactions.add(transaction);
            return true;
        }
        return false;
    }

    /**
     * Calculates the value of the investment account.
     * @return the value of the investment account
     */
    public double calculateValue() {
        double totalValue = balance;
        for (StockTransaction transaction : transactions) {
            totalValue += transaction.getQuantity() * transaction.getPrice() * 1.1;
        }
        totalValue = Math.round(totalValue * 100.0) / 100.0; // Keep two decimal places
        return transactions.isEmpty() ? 0 : totalValue;
    }
}

/**
 * Represents a stock transaction.
 */
class StockTransaction {
    private String stock;
    private int quantity;
    private double price;
    private double commission;

    /**
     * Default constructor for StockTransaction.
     */
    public StockTransaction() {}

    /**
     * Gets the stock symbol of the transaction.
     * @return the stock
     */
    public String getStock() {
        return stock;
    }

    /**
     * Sets the stock symbol of the transaction.
     * @param stock the stock to set
     */
    public void setStock(String stock) {
        this.stock = stock;
    }

    /**
     * Gets the quantity of the stock in the transaction.
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the stock in the transaction.
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the price of the stock in the transaction.
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the stock in the transaction.
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the commission of the transaction.
     * @return the commission
     */
    public double getCommission() {
        return commission;
    }

    /**
     * Sets the commission of the transaction.
     * @param commission the commission to set
     */
    public void setCommission(double commission) {
        this.commission = commission;
    }
}