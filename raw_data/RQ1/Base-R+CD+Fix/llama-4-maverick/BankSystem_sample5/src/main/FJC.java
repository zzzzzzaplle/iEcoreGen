import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer in the banking system.
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
     * @param id the ID of the account
     * @param interestRate the interest rate of the savings account
     * @return true if the account is added successfully, false otherwise
     */
    public boolean addSavingAccount(String id, double interestRate) {
        if (findAccountById(id) != null) {
            return false; // Account with the given ID already exists
        }
        SavingAccount account = new SavingAccount();
        account.setId(id);
        account.setInterestRate(interestRate);
        return accounts.add(account);
    }

    /**
     * Adds an investment account to the customer's accounts.
     * @param id the ID of the account
     * @return true if the account is added successfully, false otherwise
     */
    public boolean addInvestmentAccount(String id) {
        if (findAccountById(id) != null) {
            return false; // Account with the given ID already exists
        }
        InvestmentAccount account = new InvestmentAccount();
        account.setId(id);
        return accounts.add(account);
    }

    /**
     * Removes an investment account from the customer's accounts.
     * @param id the ID of the account to remove
     * @return true if the account is removed successfully, false otherwise
     */
    public boolean removeInvestmentAccount(String id) {
        Account account = findAccountById(id);
        if (account == null || !(account instanceof InvestmentAccount)) {
            return false; // Account not found or not an investment account
        }
        InvestmentAccount investmentAccount = (InvestmentAccount) account;
        if (investmentAccount.getBalance() != 0 || !investmentAccount.getTransactions().isEmpty()) {
            return false; // Account has balance or transactions, cannot be removed
        }
        return accounts.remove(account);
    }

    /**
     * Removes a savings account from the customer's accounts.
     * @param id the ID of the account to remove
     * @return true if the account is removed successfully, false otherwise
     */
    public boolean removeSavingAccount(String id) {
        Account account = findAccountById(id);
        if (account == null || !(account instanceof SavingAccount)) {
            return false; // Account not found or not a savings account
        }
        SavingAccount savingAccount = (SavingAccount) account;
        if (savingAccount.getBalance() != 0) {
            return false; // Account has balance, cannot be removed
        }
        return accounts.remove(account);
    }

    /**
     * Finds an account by its ID among the customer's accounts.
     * @param id the ID of the account to find
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
 * Abstract base class for different types of accounts.
 */
abstract class Account {
    private String id;
    private double balance;

    /**
     * Default constructor for Account.
     */
    public Account() {}

    /**
     * Gets the ID of the account.
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the account.
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
     * Deposits a specified amount into the account.
     * @param amount the amount to deposit
     * @return true if the deposit is successful, false otherwise
     */
    public boolean deposit(double amount) {
        if (amount <= 0 || amount > 1000000) {
            return false; // Invalid deposit amount
        }
        balance += amount;
        return true;
    }
}

/**
 * Represents a savings account with an interest rate.
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
     * Calculates the daily interest for the savings account.
     * @return the daily interest
     */
    public double calculateDailyInterest() {
        double dailyInterest = getBalance() * interestRate / 360;
        dailyInterest = Math.round(dailyInterest * 100.0) / 100.0; // Round to 2 decimal places
        return dailyInterest;
    }

    /**
     * Updates the balance by adding the daily interest.
     */
    public void updateBalanceWithDailyInterest() {
        double dailyInterest = calculateDailyInterest();
        setBalance(getBalance() + dailyInterest);
    }
}

/**
 * Represents an investment account that can hold stock transactions.
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
     * Gets the list of stock transactions in the investment account.
     * @return the transactions
     */
    public List<StockTransaction> getTransactions() {
        return transactions;
    }

    /**
     * Sets the list of stock transactions in the investment account.
     * @param transactions the transactions to set
     */
    public void setTransactions(List<StockTransaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * Buys stocks by creating a new stock transaction.
     * @param stockSymbol the symbol of the stock to buy
     * @param quantity the quantity of stocks to buy
     * @param price the price per stock
     * @return true if the stock transaction is successful, false otherwise
     */
    public boolean buyStock(String stockSymbol, int quantity, double price) {
        double totalCost = quantity * price;
        double commission = totalCost * 0.1; // 10% commission
        if (getBalance() < totalCost + commission) {
            return false; // Insufficient balance
        }
        StockTransaction transaction = new StockTransaction();
        transaction.setStock(stockSymbol);
        transaction.setQuantity(quantity);
        transaction.setPrice(price);
        transaction.setCommission(commission);
        transactions.add(transaction);
        setBalance(getBalance() - totalCost - commission);
        return true;
    }

    /**
     * Calculates the total value of the investment account, including balance and stock value.
     * @return the total value
     */
    public double calculateValue() {
        double totalValue = getBalance();
        for (StockTransaction transaction : transactions) {
            totalValue += transaction.getQuantity() * transaction.getPrice() * 1.1; // Current market price is 1.1 times purchase price
        }
        totalValue = Math.round(totalValue * 100.0) / 100.0; // Round to 2 decimal places
        return totalValue;
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
     * Gets the quantity of stocks in the transaction.
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of stocks in the transaction.
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the price per stock in the transaction.
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price per stock in the transaction.
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