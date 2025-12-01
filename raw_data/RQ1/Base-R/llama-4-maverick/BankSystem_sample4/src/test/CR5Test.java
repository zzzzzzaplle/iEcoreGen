import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    private InvestmentAccount investmentAccount;
    
    @Before
    public void setUp() {
        investmentAccount = new InvestmentAccount();
    }
    
    @Test
    public void testCase1_MultipleStockPositions() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a investment account "INV200"
        // Investment account "INV200" balance $5000
        investmentAccount.setId("INV200");
        investmentAccount.setBalance(5000.0);
        
        // Buy 10 shares "AAPL" at $100; (The new balance is $3900)
        boolean buyResult1 = investmentAccount.buyStocks("AAPL", 10, 100.0);
        assertTrue("First stock purchase should succeed", buyResult1);
        assertEquals(3900.0, investmentAccount.getBalance(), 0.001);
        
        // Buy 20 shares "MSFT" at $50; (The new balance is $2800)
        boolean buyResult2 = investmentAccount.buyStocks("MSFT", 20, 50.0);
        assertTrue("Second stock purchase should succeed", buyResult2);
        assertEquals(2800.0, investmentAccount.getBalance(), 0.001);
        
        // Action: Calculate total value
        double totalValue = investmentAccount.calculateAccountValue();
        
        // Expected Output: $5 000
        assertEquals(5000.0, totalValue, 0.001);
    }
    
    @Test
    public void testCase2_EmptyAccount() {
        // SetUp: Customer "Peter" (address "0816 Center St") holds a investment account "INV201"
        // New investment account "INV201" balance $0, no trades
        investmentAccount.setId("INV201");
        investmentAccount.setBalance(0.0);
        
        // Action: Calculate total value
        double totalValue = investmentAccount.calculateAccountValue();
        
        // Expected Output: 0
        assertEquals(0.0, totalValue, 0.001);
    }
    
    @Test
    public void testCase3_CashOnlyNoStocks() {
        // SetUp: Customer 'Alice' (address "0811 Center St") holds a investment account "INV202"
        // Investment account "INV202" balance $1 000, no trades
        investmentAccount.setId("INV202");
        investmentAccount.setBalance(1000.0);
        
        // Action: Calculate total value
        double totalValue = investmentAccount.calculateAccountValue();
        
        // Expected Output: $1 000
        assertEquals(1000.0, totalValue, 0.001);
    }
    
    @Test
    public void testCase4_RepeatedPurchasesOfSameStock() {
        // SetUp: Customer 'Bide' (address "0810 Main St") holds a investment account "INV203"
        // Investment account "INV203" balance $1 000
        investmentAccount.setId("INV203");
        investmentAccount.setBalance(1000.0);
        
        // Buy 5 shares "AAPL" at $100. (The new balance is $450)
        boolean buyResult1 = investmentAccount.buyStocks("AAPL", 5, 100.0);
        assertTrue("First AAPL purchase should succeed", buyResult1);
        assertEquals(450.0, investmentAccount.getBalance(), 0.001);
        
        // Buy 1 shares "AAPL" at $120. (The new balance is $318)
        boolean buyResult2 = investmentAccount.buyStocks("AAPL", 1, 120.0);
        assertTrue("Second AAPL purchase should succeed", buyResult2);
        assertEquals(318.0, investmentAccount.getBalance(), 0.001);
        
        // Action: Calculate total value
        double totalValue = investmentAccount.calculateAccountValue();
        
        // Expected Output: $1 000
        assertEquals(1000.0, totalValue, 0.001);
    }
    
    @Test
    public void testCase5_PrecisionCheckWithFractionalShare() {
        // SetUp: Customer 'Carli' (address "0820 Main St") holds a investment account "INV204"
        // Investment account "INV204" balance $123.45
        investmentAccount.setId("INV204");
        investmentAccount.setBalance(123.45);
        
        // Buy 10 shares of "MNO" at $10.10. (The new balance is $12.35)
        boolean buyResult1 = investmentAccount.buyStocks("MNO", 10, 10.10);
        assertTrue("First MNO purchase should succeed", buyResult1);
        assertEquals(12.35, investmentAccount.getBalance(), 0.001);
        
        // Deposit $500 into "INV204". (The new balance is $512.35)
        boolean depositResult = investmentAccount.deposit(500.0);
        assertTrue("Deposit should succeed", depositResult);
        assertEquals(512.35, investmentAccount.getBalance(), 0.001);
        
        // Buy 5 shares of "MNO" at $10.10. (The new balance is $456.8)
        boolean buyResult2 = investmentAccount.buyStocks("MNO", 5, 10.10);
        assertTrue("Second MNO purchase should succeed", buyResult2);
        assertEquals(456.8, investmentAccount.getBalance(), 0.001);
        
        // Action: Calculate total value
        double totalValue = investmentAccount.calculateAccountValue();
        
        // Expected Output: $623.45
        assertEquals(623.45, totalValue, 0.001);
    }
}