import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private BankSystem bankSystem;
    
    @Before
    public void setUp() {
        bankSystem = new BankSystem();
    }
    
    @Test
    public void testCase1_MultipleStockPositions() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a investment account "INV200"
        Customer customer = new Customer();
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        
        InvestmentAccount account = new InvestmentAccount();
        account.setAccountId("INV200");
        account.setBalance(5000.0);
        
        // Buy 10 shares "AAPL" at $100; (The new balance is $3900)
        boolean buy1 = account.buyStock("AAPL", 10, 100.0);
        assertTrue(buy1);
        assertEquals(3900.0, account.getBalance(), 0.001);
        
        // Buy 20 shares "MSFT" at $50; (The new balance is $2800)
        boolean buy2 = account.buyStock("MSFT", 20, 50.0);
        assertTrue(buy2);
        assertEquals(2800.0, account.getBalance(), 0.001);
        
        customer.addAccount(account);
        bankSystem.addCustomer(customer);
        
        // Action: Calculate total value
        double totalValue = account.calculateTotalValue();
        
        // Expected Output: $5000
        // Calculation: 
        // AAPL: 10 shares * ($100 * 1.1) = 10 * 110 = $1100
        // MSFT: 20 shares * ($50 * 1.1) = 20 * 55 = $1100
        // Balance: $2800
        // Total: 1100 + 1100 + 2800 = $5000
        assertEquals(5000.0, totalValue, 0.001);
    }
    
    @Test
    public void testCase2_EmptyAccount() {
        // SetUp: Customer "Peter" (address "0816 Center St") holds a investment account "INV201"
        Customer customer = new Customer();
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        
        InvestmentAccount account = new InvestmentAccount();
        account.setAccountId("INV201");
        account.setBalance(0.0); // New investment account balance $0, no trades
        
        customer.addAccount(account);
        bankSystem.addCustomer(customer);
        
        // Action: Calculate total value
        double totalValue = account.calculateTotalValue();
        
        // Expected Output: 0
        assertEquals(0.0, totalValue, 0.001);
    }
    
    @Test
    public void testCase3_CashOnlyNoStocks() {
        // SetUp: Customer 'Alice' (address "0811 Center St") holds a investment account "INV202"
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setAddress("0811 Center St");
        
        InvestmentAccount account = new InvestmentAccount();
        account.setAccountId("INV202");
        account.setBalance(1000.0); // Balance $1000, no trades
        
        customer.addAccount(account);
        bankSystem.addCustomer(customer);
        
        // Action: Calculate total value
        double totalValue = account.calculateTotalValue();
        
        // Expected Output: $1000
        assertEquals(1000.0, totalValue, 0.001);
    }
    
    @Test
    public void testCase4_RepeatedPurchasesOfSameStock() {
        // SetUp: Customer 'Bide' (address "0810 Main St") holds a investment account "INV203"
        Customer customer = new Customer();
        customer.setName("Bide");
        customer.setAddress("0810 Main St");
        
        InvestmentAccount account = new InvestmentAccount();
        account.setAccountId("INV203");
        account.setBalance(1000.0);
        
        // Buy 5 shares "AAPL" at $100. (The new balance is $450)
        boolean buy1 = account.buyStock("AAPL", 5, 100.0);
        assertTrue(buy1);
        assertEquals(450.0, account.getBalance(), 0.001);
        
        // Buy 1 shares "AAPL" at $120. (The new balance is $318)
        boolean buy2 = account.buyStock("AAPL", 1, 120.0);
        assertTrue(buy2);
        assertEquals(318.0, account.getBalance(), 0.001);
        
        customer.addAccount(account);
        bankSystem.addCustomer(customer);
        
        // Action: Calculate total value
        double totalValue = account.calculateTotalValue();
        
        // Expected Output: $1000
        // Calculation:
        // AAPL total shares: 5 + 1 = 6 shares
        // Current price: average purchase price is not used, each transaction is valued separately
        // First purchase: 5 shares * ($100 * 1.1) = 5 * 110 = $550
        // Second purchase: 1 share * ($120 * 1.1) = 1 * 132 = $132
        // Stock value: 550 + 132 = $682
        // Balance: $318
        // Total: 682 + 318 = $1000
        assertEquals(1000.0, totalValue, 0.001);
    }
    
    @Test
    public void testCase5_PrecisionCheckWithFractionalShare() {
        // SetUp: Customer 'Carli' (address "0820 Main St") holds a investment account "INV204"
        Customer customer = new Customer();
        customer.setName("Carli");
        customer.setAddress("0820 Main St");
        
        InvestmentAccount account = new InvestmentAccount();
        account.setAccountId("INV204");
        account.setBalance(123.45);
        
        // Buy 10 shares of "MNO" at $10.10. (The new balance is $12.35)
        boolean buy1 = account.buyStock("MNO", 10, 10.10);
        assertTrue(buy1);
        assertEquals(12.35, account.getBalance(), 0.001);
        
        // Deposit $500 into "INV204". (The new balance is $512.35)
        boolean deposit = account.deposit(500.0);
        assertTrue(deposit);
        assertEquals(512.35, account.getBalance(), 0.001);
        
        // Buy 5 shares of "MNO" at $10.10. (The new balance is $456.8)
        boolean buy2 = account.buyStock("MNO", 5, 10.10);
        assertTrue(buy2);
        assertEquals(456.8, account.getBalance(), 0.001);
        
        customer.addAccount(account);
        bankSystem.addCustomer(customer);
        
        // Action: Calculate total value
        double totalValue = account.calculateTotalValue();
        
        // Expected Output: $623.45
        // Calculation:
        // First purchase: 10 shares * ($10.10 * 1.1) = 10 * 11.11 = $111.10
        // Second purchase: 5 shares * ($10.10 * 1.1) = 5 * 11.11 = $55.55
        // Stock value: 111.10 + 55.55 = $166.65
        // Balance: $456.80
        // Total: 166.65 + 456.80 = $623.45
        assertEquals(623.45, totalValue, 0.001);
    }
}