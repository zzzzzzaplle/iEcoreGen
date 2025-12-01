import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.math.BigDecimal;

public class CR4Test {
    
    private Customer customer;
    
    @Before
    public void setUp() {
        customer = new Customer();
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
    }
    
    @Test
    public void testCase1_SufficientBalancePurchase() {
        // SetUp: Investment account "INV100" balance $10,000
        InvestmentAccount account = new InvestmentAccount();
        account.setId("INV100");
        account.setBalance(new BigDecimal("10000.00"));
        customer.addAccount(account);
        
        // Action: Buy 100 shares of "ABC" at $50 each
        boolean result = account.buyStock("ABC", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Purchase should succeed with sufficient balance", result);
        
        // Verify balance was updated correctly (10000 - (5000 + 500) = 4500)
        assertEquals(new BigDecimal("4500.00"), account.getBalance());
        
        // Verify transaction was recorded
        assertEquals(1, account.getTransactions().size());
        StockTransaction tx = account.getTransactions().get(0);
        assertEquals("ABC", tx.getTicker());
        assertEquals(100, tx.getQuantity());
        assertEquals(50.0, tx.getPurchasePrice(), 0.001);
    }
    
    @Test
    public void testCase2_InsufficientFunds() {
        // SetUp: Investment account "INV101" balance $5,000
        InvestmentAccount account = new InvestmentAccount();
        account.setId("INV101");
        account.setBalance(new BigDecimal("5000.00"));
        customer.addAccount(account);
        
        // Action: Attempt to buy 200 shares of "XYZ" at $30 each
        boolean result = account.buyStock("XYZ", 200, 30.0);
        
        // Expected Output: False
        assertFalse("Purchase should fail with insufficient funds", result);
        
        // Verify balance remains unchanged
        assertEquals(new BigDecimal("5000.00"), account.getBalance());
        
        // Verify no transaction was recorded
        assertTrue("No transactions should be recorded for failed purchase", 
                   account.getTransactions().isEmpty());
    }
    
    @Test
    public void testCase3_ExactFundsPurchase() {
        // SetUp: Investment account "INV102" balance $5,500
        InvestmentAccount account = new InvestmentAccount();
        account.setId("INV102");
        account.setBalance(new BigDecimal("5500.00"));
        customer.addAccount(account);
        
        // Action: Buy 100 shares of "DEF" at $50 each
        boolean result = account.buyStock("DEF", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Purchase should succeed with exact funds", result);
        
        // Verify balance was updated to zero (5500 - (5000 + 500) = 0)
        assertEquals(new BigDecimal("0.00"), account.getBalance());
        
        // Verify transaction was recorded
        assertEquals(1, account.getTransactions().size());
        StockTransaction tx = account.getTransactions().get(0);
        assertEquals("DEF", tx.getTicker());
        assertEquals(100, tx.getQuantity());
        assertEquals(50.0, tx.getPurchasePrice(), 0.001);
    }
    
    @Test
    public void testCase4_LowValueSingleShare() {
        // SetUp: Investment account "INV103" balance $100
        InvestmentAccount account = new InvestmentAccount();
        account.setId("INV103");
        account.setBalance(new BigDecimal("100.00"));
        customer.addAccount(account);
        
        // Action: Buy 1 share of "GHI" at $80
        boolean result = account.buyStock("GHI", 1, 80.0);
        
        // Expected Output: True
        assertTrue("Purchase should succeed for low-value single share", result);
        
        // Verify balance was updated correctly (100 - (80 + 8) = 12)
        assertEquals(new BigDecimal("12.00"), account.getBalance());
        
        // Verify transaction was recorded
        assertEquals(1, account.getTransactions().size());
        StockTransaction tx = account.getTransactions().get(0);
        assertEquals("GHI", tx.getTicker());
        assertEquals(1, tx.getQuantity());
        assertEquals(80.0, tx.getPurchasePrice(), 0.001);
    }
    
    @Test
    public void testCase5_SecondPurchaseFailsAfterBalanceDrops() {
        // SetUp: Investment account "INV104" balance $4,000
        InvestmentAccount account = new InvestmentAccount();
        account.setId("INV104");
        account.setBalance(new BigDecimal("4000.00"));
        customer.addAccount(account);
        
        // First purchase: 50 shares of "JKL" at $40
        boolean firstPurchase = account.buyStock("JKL", 50, 40.0);
        assertTrue("First purchase should succeed", firstPurchase);
        
        // Verify balance after first purchase (4000 - (2000 + 200) = 1800)
        assertEquals(new BigDecimal("1800.00"), account.getBalance());
        
        // Action: Attempt second identical purchase requiring $2,200
        boolean secondPurchase = account.buyStock("JKL", 50, 40.0);
        
        // Expected Output: False
        assertFalse("Second purchase should fail due to insufficient balance", secondPurchase);
        
        // Verify balance remains unchanged after failed second purchase
        assertEquals(new BigDecimal("1800.00"), account.getBalance());
        
        // Verify only one transaction was recorded (the successful first one)
        assertEquals(1, account.getTransactions().size());
    }
}