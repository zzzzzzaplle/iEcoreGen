import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    private Customer customer;
    
    @Before
    public void setUp() {
        customer = new Customer();
    }
    
    @Test
    public void testCase1_MultipleStockPositions() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a investment account "INV200"
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        
        // Create investment account "INV200" with balance $5000
        customer.addInvestmentAccount("INV200");
        InvestmentAccount inv200 = (InvestmentAccount) customer.findAccountById("INV200");
        inv200.setBalance(5000.0);
        
        // Buy 10 shares "AAPL" at $100 (The new balance is $3900)
        boolean buy1 = inv200.buyStock("AAPL", 10, 100.0);
        assertTrue("First stock purchase should succeed", buy1);
        assertEquals("Balance after first purchase", 3900.0, inv200.getBalance(), 0.001);
        
        // Buy 20 shares "MSFT" at $50 (The new balance is $2800)
        boolean buy2 = inv200.buyStock("MSFT", 20, 50.0);
        assertTrue("Second stock purchase should succeed", buy2);
        assertEquals("Balance after second purchase", 2800.0, inv200.getBalance(), 0.001);
        
        // Action: Calculate total value
        double calculatedValue = inv200.calculateValue();
        
        // Expected Output: $5000
        assertEquals("Total value should be $5000", 5000.0, calculatedValue, 0.001);
    }
    
    @Test
    public void testCase2_EmptyAccount() {
        // SetUp: Customer "Peter" (address "0816 Center St") holds a investment account "INV201"
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        
        // New investment account "INV201" balance $0, no trades
        customer.addInvestmentAccount("INV201");
        InvestmentAccount inv201 = (InvestmentAccount) customer.findAccountById("INV201");
        inv201.setBalance(0.0);
        
        // Action: Calculate total value
        double calculatedValue = inv201.calculateValue();
        
        // Expected Output: 0
        assertEquals("Total value should be 0", 0.0, calculatedValue, 0.001);
    }
    
    @Test
    public void testCase3_CashOnlyNoStocks() {
        // SetUp: Customer 'Alice' (address "0811 Center St") holds a investment account "INV202"
        customer.setName("Alice");
        customer.setAddress("0811 Center St");
        
        // Investment account "INV202" balance $1000, no trades
        customer.addInvestmentAccount("INV202");
        InvestmentAccount inv202 = (InvestmentAccount) customer.findAccountById("INV202");
        inv202.setBalance(1000.0);
        
        // Action: Calculate total value
        double calculatedValue = inv202.calculateValue();
        
        // Expected Output: $1000
        assertEquals("Total value should be $1000", 1000.0, calculatedValue, 0.001);
    }
    
    @Test
    public void testCase4_RepeatedPurchasesOfSameStock() {
        // SetUp: Customer 'Bide' (address "0810 Main St") holds a investment account "INV203"
        customer.setName("Bide");
        customer.setAddress("0810 Main St");
        
        // Investment account "INV203" balance $1000
        customer.addInvestmentAccount("INV203");
        InvestmentAccount inv203 = (InvestmentAccount) customer.findAccountById("INV203");
        inv203.setBalance(1000.0);
        
        // Buy 5 shares "AAPL" at $100 (The new balance is $450)
        boolean buy1 = inv203.buyStock("AAPL", 5, 100.0);
        assertTrue("First AAPL purchase should succeed", buy1);
        assertEquals("Balance after first purchase", 450.0, inv203.getBalance(), 0.001);
        
        // Buy 1 share "AAPL" at $120 (The new balance is $318)
        boolean buy2 = inv203.buyStock("AAPL", 1, 120.0);
        assertTrue("Second AAPL purchase should succeed", buy2);
        assertEquals("Balance after second purchase", 318.0, inv203.getBalance(), 0.001);
        
        // Action: Calculate total value
        double calculatedValue = inv203.calculateValue();
        
        // Expected Output: $1000
        assertEquals("Total value should be $1000", 1000.0, calculatedValue, 0.001);
    }
    
    @Test
    public void testCase5_PrecisionCheckWithFractionalShare() {
        // SetUp: Customer 'Carli' (address "0820 Main St") holds a investment account "INV204"
        customer.setName("Carli");
        customer.setAddress("0820 Main St");
        
        // Investment account "INV204" balance $123.45
        customer.addInvestmentAccount("INV204");
        InvestmentAccount inv204 = (InvestmentAccount) customer.findAccountById("INV204");
        inv204.setBalance(123.45);
        
        // Buy 10 shares of "MNO" at $10.10 (The new balance is $12.35)
        boolean buy1 = inv204.buyStock("MNO", 10, 10.10);
        assertTrue("First MNO purchase should succeed", buy1);
        assertEquals("Balance after first purchase", 12.35, inv204.getBalance(), 0.001);
        
        // Deposit $500 into "INV204" (The new balance is $512.35)
        boolean deposit = inv204.deposit(500.0);
        assertTrue("Deposit should succeed", deposit);
        assertEquals("Balance after deposit", 512.35, inv204.getBalance(), 0.001);
        
        // Buy 5 shares of "MNO" at $10.10 (The new balance is $456.8)
        boolean buy2 = inv204.buyStock("MNO", 5, 10.10);
        assertTrue("Second MNO purchase should succeed", buy2);
        assertEquals("Balance after second purchase", 456.8, inv204.getBalance(), 0.001);
        
        // Action: Calculate total value
        double calculatedValue = inv204.calculateValue();
        
        // Expected Output: $623.45
        assertEquals("Total value should be $623.45", 623.45, calculatedValue, 0.001);
    }
}