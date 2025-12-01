import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;

class Customer {
    private String name;
    private String address;
    private List<Account> accounts;

    public Customer() {
        this.accounts = new ArrayList<>();
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
     * Adds a new savings account to the customer's accounts.
     * 
     * @param id The unique identifier for the account
     * @param interestRate The interest rate for the savings account
     * @return true if the account was successfully added, false otherwise
     */
    public boolean addSavingAccount(String id, double interestRate) {
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
     * Adds a new investment account to the customer's accounts.
     * 
     * @param id The unique identifier for the account
     * @return true if the account was successfully added, false otherwise
     */
    public boolean addInvestmentAccount(String id) {
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
     * Removes an investment account from the customer's accounts.
     * The account can only be removed if it has zero balance and no transactions.
     * 
     * @param id The unique identifier for the account to remove
     * @return true if the account was successfully removed, false otherwise
     */
    public boolean removeInvestmentAccount(String id) {
        Account account = findAccountById(id);
        if (account instanceof InvestmentAccount) {
            InvestmentAccount investmentAccount = (InvestmentAccount) account;
            if (investmentAccount.getBalance() == 0 && 
                (investmentAccount.getTransactions() == null || investmentAccount.getTransactions().isEmpty())) {
                return accounts.remove(account);
            }
        }
        return false;
    }

    /**
     * Removes a savings account from the customer's accounts.
     * The account can only be removed if it has zero balance.
     * 
     * @param id The unique identifier for the account to remove
     * @return true if the account was successfully removed, false otherwise
     */
    public boolean removeSavingAccount(String id) {
        Account account = findAccountById(id);
        if (account instanceof SavingAccount) {
            SavingAccount savingAccount = (SavingAccount) account;
            if (savingAccount.getBalance() == 0) {
                return accounts.remove(account);
            }
        }
        return false;
    }

    /**
     * Finds an account by its unique identifier.
     * 
     * @param id The unique identifier for the account
     * @return The account if found, null otherwise
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

abstract class Account {
    protected String id;
    protected double balance;

    public Account() {
        this.balance = 0.0;
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
     * Deposits money into the account.
     * The amount must be positive and cannot exceed $1,000,000.
     * 
     * @param amount The amount to deposit
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

class SavingAccount extends Account {
    private double interestRate;

    public SavingAccount() {
        super();
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * Calculates the daily interest for the savings account.
     * Daily interest = balance * interest rate / 360.
     * The result is rounded to two decimal places.
     * 
     * @return The daily interest amount
     */
    public double calculateDailyInterest() {
        double dailyInterest = balance * interestRate / 360;
        BigDecimal bd = new BigDecimal(dailyInterest).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}

class InvestmentAccount extends Account {
    private List<StockTransaction> transactions;

    public InvestmentAccount() {
        super();
        this.transactions = new ArrayList<>();
    }

    public List<StockTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<StockTransaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * Buys stocks and records the transaction.
     * Checks if the account has sufficient balance to cover both the stock cost and commission.
     * Commission is 10% of the stock cost.
     * 
     * @param stockSymbol The symbol of the stock to buy
     * @param quantity The number of stocks to buy
     * @param price The price per stock
     * @return true if the transaction was successful, false otherwise
     */
    public boolean buyStock(String stockSymbol, int quantity, double price) {
        if (quantity <= 0 || price <= 0) {
            return false;
        }

        double stockCost = quantity * price;
        double commission = stockCost * 0.1;
        double totalCost = stockCost + commission;

        if (balance < totalCost) {
            return false;
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
     * This includes the account balance and the total value of all stocks.
     * Stock value is calculated as quantity * (purchase price * 1.1).
     * The result is rounded to two decimal places.
     * 
     * @return The total value of the investment account, or 0 if there are no transactions
     */
    public double calculateValue() {
        if (transactions.isEmpty()) {
            return 0.0;
        }

        double stockValue = 0.0;
        for (StockTransaction transaction : transactions) {
            double currentValue = transaction.getQuantity() * (transaction.getPrice() * 1.1);
            stockValue += currentValue;
        }

        BigDecimal stockValueBD = new BigDecimal(stockValue).setScale(2, RoundingMode.HALF_UP);
        BigDecimal balanceBD = new BigDecimal(balance).setScale(2, RoundingMode.HALF_UP);
        
        return stockValueBD.doubleValue() + balanceBD.doubleValue();
    }
}

class StockTransaction {
    private String stock;
    private int quantity;
    private double price;
    private double commission;

    public StockTransaction() {}

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