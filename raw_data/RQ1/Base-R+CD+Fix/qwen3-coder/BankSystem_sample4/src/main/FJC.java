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
     * Constructs a new Customer with default values.
     */
    public Customer() {
        this.accounts = new ArrayList<>();
    }

    /**
     * Gets the customer's name.
     *
     * @return the name of the customer
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the customer's name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the customer's address.
     *
     * @return the address of the customer
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the customer's address.
     *
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the list of accounts for this customer.
     *
     * @return the list of accounts
     */
    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * Sets the list of accounts for this customer.
     *
     * @param accounts the accounts to set
     */
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    /**
     * Adds a savings account to the customer.
     *
     * @param id the account ID
     * @param interestRate the interest rate for the savings account
     * @return true if the account was added successfully, false otherwise
     */
    public boolean addSavingAccount(String id, double interestRate) {
        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                return false; // Account ID already exists
            }
        }
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId(id);
        savingAccount.setInterestRate(interestRate);
        accounts.add(savingAccount);
        return true;
    }

    /**
     * Adds an investment account to the customer.
     *
     * @param id the account ID
     * @return true if the account was added successfully, false otherwise
     */
    public boolean addInvestmentAccount(String id) {
        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                return false; // Account ID already exists
            }
        }
        InvestmentAccount investmentAccount = new InvestmentAccount();
        investmentAccount.setId(id);
        accounts.add(investmentAccount);
        return true;
    }

    /**
     * Removes an investment account from the customer.
     *
     * @param id the account ID to remove
     * @return true if the account was removed successfully, false otherwise
     */
    public boolean removeInvestmentAccount(String id) {
        Account accountToRemove = findAccountById(id);
        if (accountToRemove instanceof InvestmentAccount) {
            InvestmentAccount investmentAccount = (InvestmentAccount) accountToRemove;
            if (investmentAccount.getBalance() == 0 && investmentAccount.getTransactions().isEmpty()) {
                return accounts.remove(investmentAccount);
            }
        }
        return false;
    }

    /**
     * Removes a savings account from the customer.
     *
     * @param id the account ID to remove
     * @return true if the account was removed successfully, false otherwise
     */
    public boolean removeSavingAccount(String id) {
        Account accountToRemove = findAccountById(id);
        if (accountToRemove instanceof SavingAccount) {
            SavingAccount savingAccount = (SavingAccount) accountToRemove;
            if (savingAccount.getBalance() == 0) {
                return accounts.remove(savingAccount);
            }
        }
        return false;
    }

    /**
     * Finds an account by its ID.
     *
     * @param id the account ID to search for
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
 * Abstract class representing a bank account.
 */
abstract class Account {
    protected String id;
    protected double balance;

    /**
     * Constructs a new Account with default values.
     */
    public Account() {
        this.balance = 0.0;
    }

    /**
     * Gets the account ID.
     *
     * @return the account ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the account ID.
     *
     * @param id the ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the account balance.
     *
     * @return the balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Sets the account balance.
     *
     * @param balance the balance to set
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Deposits money into the account.
     *
     * @param amount the amount to deposit
     * @return true if the deposit was successful, false otherwise
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
 * Represents a savings account that earns interest.
 */
class SavingAccount extends Account {
    private double interestRate;

    /**
     * Constructs a new SavingAccount with default values.
     */
    public SavingAccount() {
        super();
    }

    /**
     * Gets the interest rate for this savings account.
     *
     * @return the interest rate
     */
    public double getInterestRate() {
        return interestRate;
    }

    /**
     * Sets the interest rate for this savings account.
     *
     * @param interestRate the interest rate to set
     */
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * Calculates the daily interest for this savings account.
     * Daily interest = balance * interest rate / 360
     *
     * @return the calculated daily interest rounded to 2 decimal places
     */
    public double calculateDailyInterest() {
        double dailyInterest = balance * interestRate / 360;
        return Math.round(dailyInterest * 100.0) / 100.0;
    }
}

/**
 * Represents an investment account for buying stocks.
 */
class InvestmentAccount extends Account {
    private List<StockTransaction> transactions;

    /**
     * Constructs a new InvestmentAccount with default values.
     */
    public InvestmentAccount() {
        super();
        this.transactions = new ArrayList<>();
    }

    /**
     * Gets the list of stock transactions for this account.
     *
     * @return the list of transactions
     */
    public List<StockTransaction> getTransactions() {
        return transactions;
    }

    /**
     * Sets the list of stock transactions for this account.
     *
     * @param transactions the transactions to set
     */
    public void setTransactions(List<StockTransaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * Buys stocks and records the transaction.
     *
     * @param stockSymbol the stock symbol to buy
     * @param quantity the number of shares to buy
     * @param price the price per share
     * @return true if the purchase was successful, false otherwise
     */
    public boolean buyStock(String stockSymbol, int quantity, double price) {
        double stockCost = quantity * price;
        double commission = stockCost * 0.10;
        double totalCost = stockCost + commission;

        if (balance < totalCost) {
            return false; // Insufficient funds
        }

        // Record the transaction
        StockTransaction transaction = new StockTransaction();
        transaction.setStock(stockSymbol);
        transaction.setQuantity(quantity);
        transaction.setPrice(price);
        transaction.setCommission(commission);
        transactions.add(transaction);

        // Deduct the total cost from the account balance
        balance -= totalCost;
        return true;
    }

    /**
     * Calculates the total value of the investment account.
     * This includes the account balance and the value of all stocks.
     * Stock value is calculated as quantity * (purchase price * 1.1)
     *
     * @return the total value of the investment account rounded to 2 decimal places
     */
    public double calculateValue() {
        double stockValue = 0.0;
        for (StockTransaction transaction : transactions) {
            double currentValue = transaction.getQuantity() * (transaction.getPrice() * 1.1);
            stockValue += currentValue;
        }
        double totalValue = balance + stockValue;
        return Math.round(totalValue * 100.0) / 100.0;
    }
}

/**
 * Represents a stock transaction record.
 */
class StockTransaction {
    private String stock;
    private int quantity;
    private double price;
    private double commission;

    /**
     * Constructs a new StockTransaction with default values.
     */
    public StockTransaction() {
    }

    /**
     * Gets the stock symbol.
     *
     * @return the stock symbol
     */
    public String getStock() {
        return stock;
    }

    /**
     * Sets the stock symbol.
     *
     * @param stock the stock symbol to set
     */
    public void setStock(String stock) {
        this.stock = stock;
    }

    /**
     * Gets the quantity of stocks purchased.
     *
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of stocks purchased.
     *
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the price per stock.
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price per stock.
     *
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the commission paid for this transaction.
     *
     * @return the commission
     */
    public double getCommission() {
        return commission;
    }

    /**
     * Sets the commission paid for this transaction.
     *
     * @param commission the commission to set
     */
    public void setCommission(double commission) {
        this.commission = commission;
    }
}