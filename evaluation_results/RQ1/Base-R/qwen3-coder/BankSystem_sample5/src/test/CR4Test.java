import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.math.BigDecimal;

public class CR4Test {
    private InvestmentAccount investmentAccount;
    
    @Before
    public void setUp() {
        investmentAccount = new InvestmentAccount();
    }
    
    @Test
    public void testCase1_SufficientBalancePurchase() {
        // SetUp: Investment account "INV100" balance $10,000
        investmentAccount.setAccountId("INV100");
        investmentAccount.setBalance(new BigDecimal("10000"));
        
        // Action: Buy 100 shares of "ABC" at $50 each
        boolean result = investmentAccount.buyStocks("ABC", 100, new BigDecimal("50"));
        
        // Expected Output: True
        assertTrue("Purchase should succeed with sufficient balance", result);
        
        // Verify transaction was recorded
        assertEquals(1, investmentAccount.getStockTransactions().size());
        assertEquals("ABC", investmentAccount.getStockTransactions().get(0).getTicker());
        assertEquals(100, investmentAccount.getStockTransactions().get(0).getQuantity());
        assertEquals(new BigDecimal("50"), investmentAccount.getStockTransactions().get(0).getPrice());
        
        // Verify balance was updated correctly (10000 - (100*50 + 500 commission) = 4500)
        assertEquals(new BigDecimal("4500"), investmentAccount.getBalance());
    }
    
    @Test
    public void testCase2_InsufficientFunds() {
        // SetUp: Investment account "INV101" balance $5,000
        investmentAccount.setAccountId("INV101");
        investmentAccount.setBalance(new BigDecimal("5000"));
        
        // Action: Attempt to buy 200 shares of "XYZ" at $30 each
        boolean result = investmentAccount.buyStocks("XYZ", 200, new BigDecimal("30"));
        
        // Expected Output: False
        assertFalse("Purchase should fail with insufficient funds", result);
        
        // Verify no transaction was recorded
        assertEquals(0, investmentAccount.getStockTransactions().size());
        
        // Verify balance remains unchanged
        assertEquals(new BigDecimal("5000"), investmentAccount.getBalance());
    }
    
    @Test
    public void testCase3_ExactFundsPurchase() {
        // SetUp: Investment account "INV102" balance $5,500
        investmentAccount.setAccountId("INV102");
        investmentAccount.setBalance(new BigDecimal("5500"));
        
        // Action: Buy 100 shares of "DEF" at $50 each
        boolean result = investmentAccount.buyStocks("DEF", 100, new BigDecimal("50"));
        
        // Expected Output: True
        assertTrue("Purchase should succeed with exact funds", result);
        
        // Verify transaction was recorded
        assertEquals(1, investmentAccount.getStockTransactions().size());
        assertEquals("DEF", investmentAccount.getStockTransactions().get(0).getTicker());
        
        // Verify balance was updated to zero
        assertEquals(BigDecimal.ZERO, investmentAccount.getBalance());
    }
    
    @Test
    public void testCase4_LowValueSingleShare() {
        // SetUp: Investment account "INV103" balance $100
        investmentAccount.setAccountId("INV103");
        investmentAccount.setBalance(new BigDecimal("100"));
        
        // Action: Buy 1 share of "GHI" at $80
        boolean result = investmentAccount.buyStocks("GHI", 1, new BigDecimal("80"));
        
        // Expected Output: True
        assertTrue("Purchase should succeed with low-value single share", result);
        
        // Verify transaction was recorded
        assertEquals(1, investmentAccount.getStockTransactions().size());
        assertEquals("GHI", investmentAccount.getStockTransactions().get(0).getTicker());
        assertEquals(1, investmentAccount.getStockTransactions().get(0).getQuantity());
        
        // Verify balance was updated correctly (100 - (80 + 8 commission) = 12)
        assertEquals(new BigDecimal("12"), investmentAccount.getBalance());
    }
    
    @Test
    public void testCase5_SecondPurchaseFailsAfterBalanceDrops() {
        // SetUp: Investment account "INV104" balance $4,000
        investmentAccount.setAccountId("INV104");
        investmentAccount.setBalance(new BigDecimal("4000"));
        
        // First purchase: 50 shares of "JKL" at $40
        boolean firstPurchase = investmentAccount.buyStocks("JKL", 50, new BigDecimal("40"));
        assertTrue("First purchase should succeed", firstPurchase);
        
        // Verify first purchase details
        assertEquals(1, investmentAccount.getStockTransactions().size());
        assertEquals(new BigDecimal("1800"), investmentAccount.getBalance()); // 4000 - (2000 + 200)
        
        // Action: Attempt second identical purchase requiring $2,200
        boolean secondPurchase = investmentAccount.buyStocks("JKL", 50, new BigDecimal("40"));
        
        // Expected Output: False
        assertFalse("Second purchase should fail due to insufficient balance", secondPurchase);
        
        // Verify only one transaction exists (no new transaction added)
        assertEquals(1, investmentAccount.getStockTransactions().size());
        
        // Verify balance remains unchanged from first purchase
        assertEquals(new BigDecimal("1800"), investmentAccount.getBalance());
    }
}