import java.util.ArrayList;
import java.util.List;

class Customer {
    private String name;
    private String address;
    private List<Account> accounts;

    public Customer() {
        accounts = new ArrayList<>();
    }

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

    /**
     * Adds a new savings account to the customer's accounts
     * @param id the unique account ID
     * @param interestRate the interest rate for the savings account
     * @return true if the account was successfully added, false if an account with the same ID already exists
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
     * Adds a new investment account to the customer's accounts
     * @param id the unique account ID
     * @return true if the account was successfully added, false if an account with the same ID already exists
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
     * Removes a savings account from the customer's accounts
     * @param id the account ID to remove
     * @return true if the account was successfully removed, false if the account was not found or cannot be removed
     */
    public boolean removeSavingAccount(String id) {
        Account account = findAccountById(id);
        if (account instanceof SavingAccount && canRemoveAccount(account)) {
            accounts.remove(account);
            return true;
        }
        return false;
    }

    /**
     * Removes an investment account from the customer's accounts
     * @param id the account ID to remove
     * @return true if the account was successfully removed, false if the account was not found or cannot be removed
     */
    public boolean removeInvestmentAccount(String id) {
        Account account = findAccountById(id);
        if (account instanceof InvestmentAccount && canRemoveAccount(account)) {
            accounts.remove(account);
            return true;
        }
        return false;
    }

    /**
     * Finds an account by its ID
     * @param id the account ID to search for
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

    /**
     * Checks if an account can be removed (has zero balance and no stock transactions)
     * @param account the account to check
     * @return true if the account can be removed, false otherwise
     */
    private boolean canRemoveAccount(Account account) {
        if (account.getBalance() != 0) {
            return false;
        }
        if (account instanceof InvestmentAccount) {
            InvestmentAccount investmentAccount = (InvestmentAccount) account;
            return investmentAccount.getTransactions().isEmpty();
        }
        return true;
    }
}

abstract class Account {
    private String id;
    private double balance;

    public Account() {
    }

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

    /**
     * Deposits money into the account
     * @param amount the amount to deposit
     * @return true if the deposit was successful, false if the amount is invalid
     */
    public boolean deposit(double amount) {
        if (amount <= 0 || amount > 1000000) {
            return false;
        }
        balance += amount;
        return true;
    }
}

class SavingAccount extends Account {
    private double interestRate;

    public SavingAccount() {
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * Calculates the daily interest for the savings account
     * @return the calculated daily interest rounded to two decimal places
     */
    public double calculateDailyInterest() {
        double interest = getBalance() * interestRate / 360;
        return Math.round(interest * 100.0) / 100.0;
    }

    /**
     * Updates the account balance by adding the daily interest
     * This method should be called at 23:59:59 daily
     */
    public void updateDailyInterest() {
        double dailyInterest = calculateDailyInterest();
        setBalance(getBalance() + dailyInterest);
    }
}

class InvestmentAccount extends Account {
    private List<StockTransaction> transactions;

    public InvestmentAccount() {
        transactions = new ArrayList<>();
    }

    public List<StockTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<StockTransaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * Buys stocks and updates the investment account balance
     * @param stockSymbol the stock symbol/ticker
     * @param quantity the number of stocks to buy
     * @param price the price per stock
     * @return true if the transaction was successful, false if insufficient balance
     */
    public boolean buyStock(String stockSymbol, int quantity, double price) {
        double stockCost = quantity * price;
        double commission = stockCost * 0.1;
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
     * Calculates the total value of the investment account
     * @return the total value including balance and stock value, rounded to two decimal places
     */
    public double calculateValue() {
        if (transactions.isEmpty()) {
            return 0.0;
        }

        double stockValue = 0.0;
        for (StockTransaction transaction : transactions) {
            double currentPrice = transaction.getPrice() * 1.1;
            stockValue += transaction.getQuantity() * currentPrice;
        }

        double totalValue = getBalance() + stockValue;
        return Math.round(totalValue * 100.0) / 100.0;
    }
}

class StockTransaction {
    private String stock;
    private int quantity;
    private double price;
    private double commission;

    public StockTransaction() {
    }

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