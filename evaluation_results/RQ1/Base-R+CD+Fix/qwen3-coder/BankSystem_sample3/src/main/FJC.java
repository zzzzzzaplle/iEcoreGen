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
     * Gets the list of accounts associated with the customer.
     *
     * @return the list of accounts
     */
    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * Sets the list of accounts for the customer.
     *
     * @param accounts the list of accounts to set
     */
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    /**
     * Adds a new savings account for the customer.
     *
     * @param id           the unique identifier for the account
     * @param interestRate the interest rate for the savings account
     * @return true if the account was successfully added, false otherwise
     */
    public boolean addSavingAccount(String id, double interestRate) {
        // Check if account with the same ID already exists
        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                return false;
            }
        }
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId(id);
        savingAccount.setInterestRate(interestRate);
        accounts.add(savingAccount);
        return true;
    }

    /**
     * Adds a new investment account for the customer.
     *
     * @param id the unique identifier for the account
     * @return true if the account was successfully added, false otherwise
     */
    public boolean addInvestmentAccount(String id) {
        // Check if account with the same ID already exists
        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                return false;
            }
        }
        InvestmentAccount investmentAccount = new InvestmentAccount();
        investmentAccount.setId(id);
        accounts.add(investmentAccount);
        return true;
    }

    /**
     * Removes an investment account by its ID.
     *
     * @param id the unique identifier of the account to remove
     * @return true if the account was successfully removed, false otherwise
     */
    public boolean removeInvestmentAccount(String id) {
        Account accountToRemove = null;
        for (Account account : accounts) {
            if (account.getId().equals(id) && account instanceof InvestmentAccount) {
                accountToRemove = account;
                break;
            }
        }

        if (accountToRemove != null) {
            InvestmentAccount investmentAccount = (InvestmentAccount) accountToRemove;
            // Check if account has zero balance and no transactions
            if (investmentAccount.getBalance() == 0 && 
                (investmentAccount.getTransactions() == null || investmentAccount.getTransactions().isEmpty())) {
                accounts.remove(accountToRemove);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a savings account by its ID.
     *
     * @param id the unique identifier of the account to remove
     * @return true if the account was successfully removed, false otherwise
     */
    public boolean removeSavingAccount(String id) {
        Account accountToRemove = null;
        for (Account account : accounts) {
            if (account.getId().equals(id) && account instanceof SavingAccount) {
                accountToRemove = account;
                break;
            }
        }

        if (accountToRemove != null) {
            SavingAccount savingAccount = (SavingAccount) accountToRemove;
            // Check if account has zero balance
            if (savingAccount.getBalance() == 0) {
                accounts.remove(accountToRemove);
                return true;
            }
        }
        return false;
    }

    /**
     * Finds an account by its ID.
     *
     * @param id the unique identifier of the account
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
 * Abstract base class for all types of accounts.
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
     * @param id the account ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the account balance.
     *
     * @return the account balance
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
     * Calculates the daily interest for this savings account and updates the balance.
     * Daily interest = balance * interest rate / 360.
     *
     * @return the calculated daily interest rounded to two decimal places
     */
    public double calculateDailyInterest() {
        double dailyInterest = balance * interestRate / 360;
        // Round to two decimal places
        dailyInterest = Math.round(dailyInterest * 100.0) / 100.0;
        balance += dailyInterest;
        return dailyInterest;
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
     * @return the list of stock transactions
     */
    public List<StockTransaction> getTransactions() {
        return transactions;
    }

    /**
     * Sets the list of stock transactions for this account.
     *
     * @param transactions the list of transactions to set
     */
    public void setTransactions(List<StockTransaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * Buys stocks and records the transaction.
     *
     * @param stockSymbol the symbol of the stock to buy
     * @param quantity    the number of stocks to buy
     * @param price       the price per stock
     * @return true if the purchase was successful, false otherwise
     */
    public boolean buyStock(String stockSymbol, int quantity, double price) {
        double stockCost = quantity * price;
        double commission = stockCost * 0.1; // 10% commission
        double totalCost = stockCost + commission;

        // Check if balance is sufficient
        if (balance < totalCost) {
            return false;
        }

        // Deduct the total cost from balance
        balance -= totalCost;

        // Record the transaction
        StockTransaction transaction = new StockTransaction();
        transaction.setStock(stockSymbol);
        transaction.setQuantity(quantity);
        transaction.setPrice(price);
        transaction.setCommission(commission);
        transactions.add(transaction);

        return true;
    }

    /**
     * Calculates the total value of the investment account, including balance and stock values.
     * Stock value is calculated as quantity * (purchase price * 1.1).
     *
     * @return the total value of the investment account rounded to two decimal places
     */
    public double calculateValue() {
        double stockValue = 0.0;

        // Calculate the value of all stocks
        for (StockTransaction transaction : transactions) {
            double currentMarketPrice = transaction.getPrice() * 1.1;
            stockValue += transaction.getQuantity() * currentMarketPrice;
        }

        // Round to two decimal places
        stockValue = Math.round(stockValue * 100.0) / 100.0;

        // Return total value (balance + stock value)
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
     * Gets the quantity of stocks in this transaction.
     *
     * @return the quantity of stocks
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of stocks in this transaction.
     *
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the price per stock in this transaction.
     *
     * @return the price per stock
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price per stock in this transaction.
     *
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the commission charged for this transaction.
     *
     * @return the commission amount
     */
    public double getCommission() {
        return commission;
    }

    /**
     * Sets the commission charged for this transaction.
     *
     * @param commission the commission to set
     */
    public void setCommission(double commission) {
        this.commission = commission;
    }
}