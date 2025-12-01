import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class BankSystem {
    private List<Customer> customers;

    public BankSystem() {
        this.customers = new ArrayList<>();
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public boolean addCustomer(Customer customer) {
        return customers.add(customer);
    }

    public boolean removeCustomer(Customer customer) {
        return customers.remove(customer);
    }
}

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

    public boolean addAccount(Account account) {
        return accounts.add(account);
    }

    public boolean removeAccount(Account account) {
        return accounts.remove(account);
    }
}

abstract class Account {
    private String accountId;
    private BigDecimal balance;

    public Account() {
        this.balance = BigDecimal.ZERO;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public abstract boolean deposit(BigDecimal amount);
}

class SavingsAccount extends Account {
    private BigDecimal interestRate;

    public SavingsAccount() {
        super();
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public boolean deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0 || amount.compareTo(new BigDecimal("1000000")) > 0) {
            return false;
        }
        this.setBalance(this.getBalance().add(amount));
        return true;
    }

    public void calculateDailyInterest() {
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        if (time.format(formatter).equals("23:59:59")) {
            BigDecimal dailyInterest = this.getBalance().multiply(this.getInterestRate()).divide(new BigDecimal("360"), 2, RoundingMode.HALF_UP);
            this.setBalance(this.getBalance().add(dailyInterest));
        }
    }
}

class InvestmentAccount extends Account {
    private Map<String, List<StockTransaction>> stockTransactions;

    public InvestmentAccount() {
        super();
        this.stockTransactions = new HashMap<>();
    }

    public Map<String, List<StockTransaction>> getStockTransactions() {
        return stockTransactions;
    }

    public void setStockTransactions(Map<String, List<StockTransaction>> stockTransactions) {
        this.stockTransactions = stockTransactions;
    }

    @Override
    public boolean deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0 || amount.compareTo(new BigDecimal("1000000")) > 0) {
            return false;
        }
        this.setBalance(this.getBalance().add(amount));
        return true;
    }

    public boolean buyStocks(String ticker, int quantity, BigDecimal price) {
        BigDecimal cost = new BigDecimal(quantity).multiply(price);
        BigDecimal commission = cost.multiply(new BigDecimal("0.10"));
        BigDecimal totalCost = cost.add(commission);

        if (this.getBalance().compareTo(totalCost) < 0) {
            return false;
        }

        this.setBalance(this.getBalance().subtract(totalCost));

        StockTransaction transaction = new StockTransaction(ticker, quantity, price);
        this.stockTransactions.computeIfAbsent(ticker, k -> new ArrayList<>()).add(transaction);
        return true;
    }

    public BigDecimal calculateAccountValue() {
        if (this.stockTransactions.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal totalValue = this.getBalance();
        for (Map.Entry<String, List<StockTransaction>> entry : this.stockTransactions.entrySet()) {
            for (StockTransaction transaction : entry.getValue()) {
                BigDecimal currentPrice = transaction.getPrice().multiply(new BigDecimal("1.1"));
                BigDecimal stockValue = new BigDecimal(transaction.getQuantity()).multiply(currentPrice);
                totalValue = totalValue.add(stockValue);
            }
        }
        return totalValue.setScale(2, RoundingMode.HALF_UP);
    }
}

class StockTransaction {
    private String ticker;
    private int quantity;
    private BigDecimal price;

    public StockTransaction(String ticker, int quantity, BigDecimal price) {
        this.ticker = ticker;
        this.quantity = quantity;
        this.price = price;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}