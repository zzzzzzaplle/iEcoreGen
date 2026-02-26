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
     * Constructs a Customer object.
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
     * @param id the account id
     * @param interestRate the interest rate of the savings account
     * @return true if the account is added successfully, false otherwise
     */
    public boolean addSavingAccount(String id, double interestRate) {
        if (findAccountById(id) != null) {
            return false;
        }
        SavingAccount account = new SavingAccount();
        account.setId(id);
        account.setInterestRate(interestRate);
        return accounts.add(account);
    }

    /**
     * Adds an investment account to the customer's accounts.
     * @param id the account id
     * @return true if the account is added successfully, false otherwise
     */
    public boolean addInvestmentAccount(String id) {
        if (findAccountById(id) != null) {
            return false;
        }
        InvestmentAccount account = new InvestmentAccount();
        account.setId(id);
        return accounts.add(account);
    }

    /**
     * Removes an investment account from the customer's accounts.
     * @param id the account id
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
     * @param id the account id
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
     * @param id the account id
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
    private double balance;

    /**
     * Constructs an Account object.
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
     * @param amount the amount to deposit
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

/**
 * Represents a savings account.
 */
class SavingAccount extends Account {
    private double interestRate;

    /**
     * Constructs a SavingAccount object.
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
        double dailyInterest = getBalance() * interestRate / 360;
        return Math.round(dailyInterest * 100.0) / 100.0;
    }
}

/**
 * Represents an investment account.
 */
class InvestmentAccount extends Account {
    private List<StockTransaction> transactions;

    /**
     * Constructs an InvestmentAccount object.
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
     * @param stockSymbol the stock symbol
     * @param quantity the quantity of stocks to buy
     * @param price the price of the stocks
     * @return true if the stock transaction is successful, false otherwise
     */
    public boolean buyStock(String stockSymbol, int quantity, double price) {
        double cost = quantity * price;
        double commission = cost * 0.1;
        if (getBalance() < cost + commission) {
            return false;
        }
        setBalance(getBalance() - cost - commission);
        StockTransaction transaction = new StockTransaction();
        transaction.setStock(stockSymbol);
        transaction.setQuantity(quantity);
        transaction.setPrice(price);
        transaction.setCommission(commission);
        return transactions.add(transaction);
    }

    /**
     * Calculates the value of the investment account.
     * @return the value of the investment account
     */
    public double calculateValue() {
        double totalValue = getBalance();
        for (StockTransaction transaction : transactions) {
            totalValue += transaction.getQuantity() * transaction.getPrice() * 1.1;
        }
        return Math.round(totalValue * 100.0) / 100.0;
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
     * Constructs a StockTransaction object.
     */
    public StockTransaction() {}

    /**
     * Gets the stock symbol.
     * @return the stock
     */
    public String getStock() {
        return stock;
    }

    /**
     * Sets the stock symbol.
     * @param stock the stock to set
     */
    public void setStock(String stock) {
        this.stock = stock;
    }

    /**
     * Gets the quantity of stocks.
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of stocks.
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the price of the stocks.
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the stocks.
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the commission of the stock transaction.
     * @return the commission
     */
    public double getCommission() {
        return commission;
    }

    /**
     * Sets the commission of the stock transaction.
     * @param commission the commission to set
     */
    public void setCommission(double commission) {
        this.commission = commission;
    }
}