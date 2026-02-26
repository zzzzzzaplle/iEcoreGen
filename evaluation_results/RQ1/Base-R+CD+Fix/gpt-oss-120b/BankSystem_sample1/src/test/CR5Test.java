import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    private Customer customer;
    
    @Before
    public void setUp() {
        // Reset customer before each test
        customer = null;
    }
    
    @Test
    public void testCase1_MultipleStockPositions() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a investment account "INV200"
        customer = new Customer("Poe", "0814 Center St");
        
        // Investment account "INV200" balance $5000
        customer.addInvestmentAccount("INV200");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV200");
        account.setBalance(5000.0);
        
        // Buy 10 shares "AAPL" at $100; (The new balance is $3900)
        assertTrue(account.buyStock("AAPL", 10, 100.0));
        assertEquals(3900.0, account.getBalance(), 0.001);
        
        // Buy 20 shares "MSFT" at $50; (The new balance is $2800)
        assertTrue(account.buyStock("MSFT", 20, 50.0));
        assertEquals(2800.0, account.getBalance(), 0.001);
        
        // Action: Calculate total value
        double result = account.calculateValue();
        
        // Expected Output: $5000
        assertEquals(5000.0, result, 0.001);
    }
    
    @Test
    public void testCase2_EmptyAccount() {
        // SetUp: Customer "Peter" (address "0816 Center St") holds a investment account "INV201"
        customer = new Customer("Peter", "0816 Center St");
        
        // New investment account "INV201" balance $0, no trades
        customer.addInvestmentAccount("INV201");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV201");
        account.setBalance(0.0);
        
        // Action: Calculate total value
        double result = account.calculateValue();
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_CashOnlyNoStocks() {
        // SetUp: Customer 'Alice' (address "0811 Center St") holds a investment account "INV202"
        customer = new Customer("Alice", "0811 Center St");
        
        // Investment account "INV202" balance $1000, no trades
        customer.addInvestmentAccount("INV202");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV202");
        account.setBalance(1000.0);
        
        // Action: Calculate total value
        double result = account.calculateValue();
        
        // Expected Output: $1000
        assertEquals(1000.0, result, 0.001);
    }
    
    @Test
    public void testCase4_RepeatedPurchasesOfSameStock() {
        // SetUp: Customer 'Bide' (address "0810 Main St") holds a investment account "INV203"
        customer = new Customer("Bide", "0810 Main St");
        
        // Investment account "INV203" balance $1000
        customer.addInvestmentAccount("INV203");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV203");
        account.setBalance(1000.0);
        
        // Buy 5 shares "AAPL" at $100. (The new balance is $450)
        assertTrue(account.buyStock("AAPL", 5, 100.0));
        assertEquals(450.0, account.getBalance(), 0.001);
        
        // Buy 1 shares "AAPL" at $120. (The new balance is $318)
        assertTrue(account.buyStock("AAPL", 1, 120.0));
        assertEquals(318.0, account.getBalance(), 0.001);
        
        // Action: Calculate total value
        double result = account.calculateValue();
        
        // Expected Output: $1000
        assertEquals(1000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_PrecisionCheckWithFractionalShare() {
        // SetUp: Customer 'Carli' (address "0820 Main St") holds a investment account "INV204"
        customer = new Customer("Carli", "0820 Main St");
        
        // Investment account "INV204" balance $123.45
        customer.addInvestmentAccount("INV204");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV204");
        account.setBalance(123.45);
        
        // Buy 10 shares of "MNO" at $10.10. (The new balance is $12.35)
        assertTrue(account.buyStock("MNO", 10, 10.10));
        assertEquals(12.35, account.getBalance(), 0.001);
        
        // Deposit $500 into "INV204". (The new balance is $512.35)
        assertTrue(account.deposit(500.0));
        assertEquals(512.35, account.getBalance(), 0.001);
        
        // Buy 5 shares of "MNO" at $10.10. (The new balance is $456.8)
        assertTrue(account.buyStock("MNO", 5, 10.10));
        assertEquals(456.8, account.getBalance(), 0.001);
        
        // Action: Calculate total value
        double result = account.calculateValue();
        
        // Expected Output: $623.45
        assertEquals(623.45, result, 0.001);
    }
}