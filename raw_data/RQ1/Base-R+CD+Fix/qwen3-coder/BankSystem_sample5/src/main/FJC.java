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
     * Gets the name of the customer.
     *
     * @return the name of the customer
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the customer.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the address of the customer.
     *
     * @return the address of the customer
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the customer.
     *
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the list of accounts for the customer.
     *
     * @return the list of accounts
     */
    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * Sets the list of accounts for the customer.
     *
     * @param accounts the accounts to set
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
        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                return false; // Account with this ID already exists
            }
        }
        SavingAccount account = new SavingAccount();
        account.setId(id);
        account.setInterestRate(interestRate);
        accounts.add(account);
        return true;
    }

    /**
     * Adds a new investment account for the customer.
     *
     * @param id the unique identifier for the account
     * @return true if the account was successfully added, false otherwise
     */
    public boolean addInvestmentAccount(String id) {
        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                return false; // Account with this ID already exists
            }
        }
        InvestmentAccount account = new InvestmentAccount();
        account.setId(id);
        accounts.add(account);
        return true;
    }

    /**
     * Removes an investment account from the customer's accounts.
     *
     * @param id the unique identifier of the account to remove
     * @return true if the account was successfully removed, false otherwise
     */
    public boolean removeInvestmentAccount(String id) {
        Account accountToRemove = null;
        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                if (!(account instanceof InvestmentAccount)) {
                    return false; // Not an investment account
                }
                accountToRemove = account;
                break;
            }
        }

        if (accountToRemove == null) {
            return false; // Account not found
        }

        InvestmentAccount investmentAccount = (InvestmentAccount) accountToRemove;
        if (investmentAccount.getBalance() != 0 || !investmentAccount.getTransactions().isEmpty()) {
            return false; // Account has balance or transactions
        }

        return accounts.remove(accountToRemove);
    }

    /**
     * Removes a savings account from the customer's accounts.
     *
     * @param id the unique identifier of the account to remove
     * @return true if the account was successfully removed, false otherwise
     */
    public boolean removeSavingAccount(String id) {
        Account accountToRemove = null;
        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                if (!(account instanceof SavingAccount)) {
                    return false; // Not a savings account
                }
                accountToRemove = account;
                break;
            }
        }

        if (accountToRemove == null) {
            return false; // Account not found
        }

        SavingAccount savingAccount = (SavingAccount) accountToRemove;
        if (savingAccount.getBalance() != 0) {
            return false; // Account has balance
        }

        return accounts.remove(accountToRemove);
    }

    /**
     * Finds an account by its unique identifier.
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
 * Abstract class representing a bank account.
 */
abstract class Account {
    protected String id;
    protected double balance;

    /**
     * Constructs a new Account with default values.
     */
    public Account() {
    }

    /**
     * Gets the unique identifier of the account.
     *
     * @return the account ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the account.
     *
     * @param id the account ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the current balance of the account.
     *
     * @return the account balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Sets the balance of the account.
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
 * Represents a savings account which offers interest.
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
     * Gets the interest rate for the savings account.
     *
     * @return the interest rate
     */
    public double getInterestRate() {
        return interestRate;
    }

    /**
     * Sets the interest rate for the savings account.
     *
     * @param interestRate the interest rate to set
     */
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * Calculates the daily interest and updates the account balance.
     * Daily interest = balance * interest rate / 360.
     * The result is rounded to two decimal places.
     *
     * @return the calculated daily interest
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
 * Represents an investment account used for buying stocks.
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
     * Before buying, ensures that the current balance is sufficient to cover
     * the stock cost and the bank's commission (10% of the stock cost).
     * Stock cost = number of stocks * price.
     *
     * @param stockSymbol the symbol of the stock to buy
     * @param quantity    the number of stocks to buy
     * @param price       the price per stock
     * @return true if the transaction was successful, false otherwise
     */
    public boolean buyStock(String stockSymbol, int quantity, double price) {
        if (quantity <= 0 || price <= 0) {
            return false;
        }

        double stockCost = quantity * price;
        double commission = stockCost * 0.1; // 10% commission
        double totalCost = stockCost + commission;

        if (balance < totalCost) {
            return false; // Insufficient funds
        }

        StockTransaction transaction = new StockTransaction();
        transaction.setStock(stockSymbol);
        transaction.setQuantity(quantity);
        transaction.setPrice(price);
        transaction.setCommission(commission);

        transactions.add(transaction);
        balance -= totalCost;
        return true;
    }

    /**
     * Calculates the total value of the investment account.
     * This includes the account balance and the total value of stocks.
     * The value of each stock is calculated as quantity * (purchase price * 1.1).
     * The result is rounded to two decimal places.
     *
     * @return the total value of the investment account, or 0 if there are no transactions
     */
    public double calculateValue() {
        double stockValue = 0.0;

        for (StockTransaction transaction : transactions) {
            double currentValue = transaction.getQuantity() * (transaction.getPrice() * 1.1);
            stockValue += currentValue;
        }

        // Round to two decimal places
        stockValue = Math.round(stockValue * 100.0) / 100.0;
        
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
     * Gets the quantity of stocks in the transaction.
     *
     * @return the quantity of stocks
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of stocks in the transaction.
     *
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the price per stock in the transaction.
     *
     * @return the price per stock
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price per stock in the transaction.
     *
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the commission for the transaction.
     *
     * @return the commission
     */
    public double getCommission() {
        return commission;
    }

    /**
     * Sets the commission for the transaction.
     *
     * @param commission the commission to set
     */
    public void setCommission(double commission) {
        this.commission = commission;
    }
}