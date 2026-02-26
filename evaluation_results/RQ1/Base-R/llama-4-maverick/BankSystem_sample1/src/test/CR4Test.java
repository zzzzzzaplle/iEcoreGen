import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    private InvestmentAccount investmentAccount;
    
    @Before
    public void setUp() {
        // Reset investment account before each test
        investmentAccount = new InvestmentAccount();
    }
    
    @Test
    public void testCase1_SufficientBalancePurchase() {
        // SetUp: Investment account "INV100" balance $10,000
        investmentAccount.setId("INV100");
        investmentAccount.setBalance(10000.0);
        
        // Action: Buy 100 shares of "ABC" at $50 each (cost $5,000, commission $500)
        boolean result = investmentAccount.buyStocks("ABC", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Purchase should succeed with sufficient balance", result);
        
        // Verify balance was updated correctly (10000 - 5000 - 500 = 4500)
        assertEquals("Balance should be updated correctly", 4500.0, investmentAccount.getBalance(), 0.001);
        
        // Verify transaction was recorded
        assertEquals("Transaction should be recorded", 1, investmentAccount.getStockTransactions().size());
        StockTransaction transaction = investmentAccount.getStockTransactions().get(0);
        assertEquals("Ticker should match", "ABC", transaction.getTicker());
        assertEquals("Quantity should match", 100, transaction.getQuantity());
        assertEquals("Price should match", 50.0, transaction.getPrice(), 0.001);
    }
    
    @Test
    public void testCase2_InsufficientFunds() {
        // SetUp: Investment account "INV101" balance $5,000
        investmentAccount.setId("INV101");
        investmentAccount.setBalance(5000.0);
        
        // Action: Attempt to buy 200 shares of "XYZ" at $30 each (commission $600, total needed $6,600)
        boolean result = investmentAccount.buyStocks("XYZ", 200, 30.0);
        
        // Expected Output: False
        assertFalse("Purchase should fail with insufficient funds", result);
        
        // Verify balance remains unchanged
        assertEquals("Balance should remain unchanged", 5000.0, investmentAccount.getBalance(), 0.001);
        
        // Verify no transaction was recorded
        assertTrue("No transaction should be recorded", investmentAccount.getStockTransactions().isEmpty());
    }
    
    @Test
    public void testCase3_ExactFundsPurchase() {
        // SetUp: Investment account "INV102" balance $5,500
        investmentAccount.setId("INV102");
        investmentAccount.setBalance(5500.0);
        
        // Action: Buy 100 shares of "DEF" at $50 each (commission $500, total needed $5,500 exactly)
        boolean result = investmentAccount.buyStocks("DEF", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Purchase should succeed with exact funds", result);
        
        // Verify balance was updated correctly (5500 - 5000 - 500 = 0)
        assertEquals("Balance should be zero after exact purchase", 0.0, investmentAccount.getBalance(), 0.001);
        
        // Verify transaction was recorded
        assertEquals("Transaction should be recorded", 1, investmentAccount.getStockTransactions().size());
        StockTransaction transaction = investmentAccount.getStockTransactions().get(0);
        assertEquals("Ticker should match", "DEF", transaction.getTicker());
        assertEquals("Quantity should match", 100, transaction.getQuantity());
        assertEquals("Price should match", 50.0, transaction.getPrice(), 0.001);
    }
    
    @Test
    public void testCase4_LowValueSingleShare() {
        // SetUp: Investment account "INV103" balance $100
        investmentAccount.setId("INV103");
        investmentAccount.setBalance(100.0);
        
        // Action: Buy 1 share of "GHI" at $80 (commission $8, total $88)
        boolean result = investmentAccount.buyStocks("GHI", 1, 80.0);
        
        // Expected Output: True
        assertTrue("Purchase should succeed for low-value single share", result);
        
        // Verify balance was updated correctly (100 - 80 - 8 = 12)
        assertEquals("Balance should be updated correctly", 12.0, investmentAccount.getBalance(), 0.001);
        
        // Verify transaction was recorded
        assertEquals("Transaction should be recorded", 1, investmentAccount.getStockTransactions().size());
        StockTransaction transaction = investmentAccount.getStockTransactions().get(0);
        assertEquals("Ticker should match", "GHI", transaction.getTicker());
        assertEquals("Quantity should match", 1, transaction.getQuantity());
        assertEquals("Price should match", 80.0, transaction.getPrice(), 0.001);
    }
    
    @Test
    public void testCase5_SecondPurchaseFailsAfterBalanceDrops() {
        // SetUp: Investment account "INV104" balance $4,000
        investmentAccount.setId("INV104");
        investmentAccount.setBalance(4000.0);
        
        // First purchase: 50 shares of "JKL" at $40 (cost $2,000, commission $200, remaining balance $1,800)
        boolean firstPurchase = investmentAccount.buyStocks("JKL", 50, 40.0);
        assertTrue("First purchase should succeed", firstPurchase);
        assertEquals("Balance after first purchase should be 1800", 1800.0, investmentAccount.getBalance(), 0.001);
        
        // Action: Attempt a second identical purchase requiring $2,200
        boolean secondPurchase = investmentAccount.buyStocks("JKL", 50, 40.0);
        
        // Expected Output: False
        assertFalse("Second purchase should fail after balance drops", secondPurchase);
        
        // Verify balance remains unchanged after failed second purchase
        assertEquals("Balance should remain 1800 after failed purchase", 1800.0, investmentAccount.getBalance(), 0.001);
        
        // Verify only one transaction was recorded (the successful first purchase)
        assertEquals("Only one transaction should be recorded", 1, investmentAccount.getStockTransactions().size());
    }
}