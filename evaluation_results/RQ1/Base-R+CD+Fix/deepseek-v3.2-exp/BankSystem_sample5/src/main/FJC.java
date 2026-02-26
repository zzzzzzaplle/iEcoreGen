import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer in the bank system.
 * Each customer has a name, address, and a list of accounts.
 */
class Customer {
    private String name;
    private String address;
    private List<Account> accounts;

    /**
     * Default constructor for Customer.
     * Initializes the accounts list as an empty ArrayList.
     */
    public Customer() {
        this.accounts = new ArrayList<>();
    }

    /**
     * Gets the name of the customer.
     * @return the name of the customer
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
     * @return the address of the customer
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
     * @return the list of accounts
     */
    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * Sets the list of accounts for the customer.
     * @param accounts the list of accounts to set
     */
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    /**
     * Adds a new savings account with the specified ID and interest rate.
     * Checks if an account with the same ID already exists.
     * @param id the unique ID for the savings account
     * @param interestRate the interest rate for the savings account
     * @return true if the account was added successfully, false otherwise
     */
    public boolean addSavingAccount(String id, double interestRate) {
        if (findAccountById(id) != null) {
            return false;
        }
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId(id);
        savingAccount.setInterestRate(interestRate);
        accounts.add(savingAccount);
        return true;
    }

    /**
     * Adds a new investment account with the specified ID.
     * Checks if an account with the same ID already exists.
     * @param id the unique ID for the investment account
     * @return true if the account was added successfully, false otherwise
     */
    public boolean addInvestmentAccount(String id) {
        if (findAccountById(id) != null) {
            return false;
        }
        InvestmentAccount investmentAccount = new InvestmentAccount();
        investmentAccount.setId(id);
        accounts.add(investmentAccount);
        return true;
    }

    /**
     * Removes an investment account with the specified ID.
     * Checks if the account exists and has no balance or stock transactions.
     * @param id the ID of the investment account to remove
     * @return true if the account was removed successfully, false otherwise
     */
    public boolean removeInvestmentAccount(String id) {
        Account account = findAccountById(id);
        if (account instanceof InvestmentAccount) {
            InvestmentAccount investmentAccount = (InvestmentAccount) account;
            if (investmentAccount.getBalance() == 0 && investmentAccount.getTransactions().isEmpty()) {
                accounts.remove(account);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a savings account with the specified ID.
     * Checks if the account exists and has no balance.
     * @param id the ID of the savings account to remove
     * @return true if the account was removed successfully, false otherwise
     */
    public boolean removeSavingAccount(String id) {
        Account account = findAccountById(id);
        if (account instanceof SavingAccount) {
            if (account.getBalance() == 0) {
                accounts.remove(account);
                return true;
            }
        }
        return false;
    }

    /**
     * Finds an account by its ID.
     * @param id the ID of the account to find
     * @return the account with the specified ID, or null if not found
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
 * Each account has a unique ID and a balance.
 */
abstract class Account {
    private String id;
    private double balance;

    /**
     * Default constructor for Account.
     */
    public Account() {
    }

    /**
     * Gets the ID of the account.
     * @return the ID of the account
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the account.
     * @param id the ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the balance of the account.
     * @return the balance of the account
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
     * Deposits a specified amount into the account.
     * Validates that the amount is positive and does not exceed the maximum deposit limit.
     * @param amount the amount to deposit
     * @return true if the deposit was successful, false otherwise
     * @throws IllegalArgumentException if the amount is invalid
     */
    public boolean deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        if (amount > 1000000) {
            return false;
        }
        this.balance += amount;
        return true;
    }
}

/**
 * Represents a savings account that offers an interest rate.
 * Extends the Account class.
 */
class SavingAccount extends Account {
    private double interestRate;

    /**
     * Default constructor for SavingAccount.
     */
    public SavingAccount() {
    }

    /**
     * Gets the interest rate of the savings account.
     * @return the interest rate
     */
    public double getInterestRate() {
        return interestRate;
    }

    /**
     * Sets the interest rate of the savings account.
     * @param interestRate the interest rate to set
     */
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * Calculates the daily interest for the savings account.
     * Daily interest is computed as balance * interest rate / 360.
     * The result is rounded to two decimal places.
     * @return the daily interest amount
     */
    public double calculateDailyInterest() {
        double interest = getBalance() * interestRate / 360;
        return Math.round(interest * 100.0) / 100.0;
    }

    /**
     * Updates the balance by adding the daily interest.
     * This method should be called at 23:59:59 each day.
     */
    public void updateDailyInterest() {
        double dailyInterest = calculateDailyInterest();
        setBalance(getBalance() + dailyInterest);
    }
}

/**
 * Represents an investment account used to buy stocks.
 * Extends the Account class and maintains a list of stock transactions.
 */
class InvestmentAccount extends Account {
    private List<StockTransaction> transactions;

    /**
     * Default constructor for InvestmentAccount.
     * Initializes the transactions list as an empty ArrayList.
     */
    public InvestmentAccount() {
        this.transactions = new ArrayList<>();
    }

    /**
     * Gets the list of stock transactions for the investment account.
     * @return the list of stock transactions
     */
    public List<StockTransaction> getTransactions() {
        return transactions;
    }

    /**
     * Sets the list of stock transactions for the investment account.
     * @param transactions the list of stock transactions to set
     */
    public void setTransactions(List<StockTransaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * Buys a specified quantity of a stock at a given price.
     * Checks if the account balance is sufficient to cover the stock cost and commission.
     * Commission is 10% of the stock cost.
     * @param stockSymbol the symbol of the stock to buy
     * @param quantity the quantity of stocks to buy
     * @param price the price per stock
     * @return true if the transaction was successful, false otherwise
     */
    public boolean buyStock(String stockSymbol, int quantity, double price) {
        double stockCost = quantity * price;
        double commission = stockCost * 0.10;
        double totalCost = stockCost + commission;

        if (getBalance() < totalCost) {
            return false;
        }

        StockTransaction transaction = new StockTransaction();
        transaction.setStock(stockSymbol);
        transaction.setQuantity(quantity);
        transaction.setPrice(price);
        transaction.setCommission(commission);
        transactions.add(transaction);

        setBalance(getBalance() - totalCost);
        return true;
    }

    /**
     * Calculates the total value of the investment account.
     * The value includes the account balance and the total value of all stocks.
     * Stock value is computed as quantity * current price (1.1 times purchase price).
     * The result is rounded to two decimal places.
     * @return the total value of the investment account, or 0 if there are no stock transactions
     */
    public double calculateValue() {
        if (transactions.isEmpty()) {
            return 0;
        }

        double stockValue = 0;
        for (StockTransaction transaction : transactions) {
            double currentPrice = transaction.getPrice() * 1.1;
            stockValue += transaction.getQuantity() * currentPrice;
        }

        double totalValue = getBalance() + stockValue;
        return Math.round(totalValue * 100.0) / 100.0;
    }
}

/**
 * Represents a stock transaction in an investment account.
 * Each transaction records the stock symbol, quantity, price, and commission.
 */
class StockTransaction {
    private String stock;
    private int quantity;
    private double price;
    private double commission;

    /**
     * Default constructor for StockTransaction.
     */
    public StockTransaction() {
    }

    /**
     * Gets the stock symbol for the transaction.
     * @return the stock symbol
     */
    public String getStock() {
        return stock;
    }

    /**
     * Sets the stock symbol for the transaction.
     * @param stock the stock symbol to set
     */
    public void setStock(String stock) {
        this.stock = stock;
    }

    /**
     * Gets the quantity of stocks in the transaction.
     * @return the quantity of stocks
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of stocks for the transaction.
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the price per stock in the transaction.
     * @return the price per stock
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price per stock for the transaction.
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the commission for the transaction.
     * @return the commission amount
     */
    public double getCommission() {
        return commission;
    }

    /**
     * Sets the commission for the transaction.
     * @param commission the commission to set
     */
    public void setCommission(double commission) {
        this.commission = commission;
    }
}