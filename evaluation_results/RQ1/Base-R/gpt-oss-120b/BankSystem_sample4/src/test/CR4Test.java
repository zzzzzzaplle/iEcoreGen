import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.math.BigDecimal;

public class CR4Test {
    
    private Customer customer;
    
    @Before
    public void setUp() {
        customer = new Customer("Poe", "0814 Center St");
    }
    
    @Test
    public void testCase1_SufficientBalancePurchase() {
        // SetUp: Investment account "INV100" balance $10,000
        InvestmentAccount account = new InvestmentAccount("INV100");
        account.setBalance(new BigDecimal("10000"));
        customer.addAccount(account);
        
        // Action: Buy 100 shares of "ABC" at $50 each
        boolean result = account.buyStocks("ABC", 100, new BigDecimal("50"));
        
        // Expected Output: True
        assertTrue("Purchase should succeed with sufficient balance", result);
        
        // Verify balance was updated correctly: $10,000 - ($5,000 + $500) = $4,500
        assertEquals(new BigDecimal("4500.00"), account.getBalance());
        
        // Verify transaction was recorded
        assertEquals(1, account.getTransactions().size());
        StockTransaction txn = account.getTransactions().get(0);
        assertEquals("ABC", txn.getTicker());
        assertEquals(100, txn.getQuantity());
        assertEquals(new BigDecimal("50"), txn.getPurchasePrice());
    }
    
    @Test
    public void testCase2_InsufficientFunds() {
        // SetUp: Investment account "INV101" balance $5,000
        InvestmentAccount account = new InvestmentAccount("INV101");
        account.setBalance(new BigDecimal("5000"));
        customer.addAccount(account);
        
        // Action: Attempt to buy 200 shares of "XYZ" at $30 each
        boolean result = account.buyStocks("XYZ", 200, new BigDecimal("30"));
        
        // Expected Output: False
        assertFalse("Purchase should fail with insufficient funds", result);
        
        // Verify balance remains unchanged
        assertEquals(new BigDecimal("5000.00"), account.getBalance());
        
        // Verify no transaction was recorded
        assertTrue("No transaction should be recorded for failed purchase", 
                   account.getTransactions().isEmpty());
    }
    
    @Test
    public void testCase3_ExactFundsPurchase() {
        // SetUp: Investment account "INV102" balance $5,500
        InvestmentAccount account = new InvestmentAccount("INV102");
        account.setBalance(new BigDecimal("5500"));
        customer.addAccount(account);
        
        // Action: Buy 100 shares of "DEF" at $50 each
        boolean result = account.buyStocks("DEF", 100, new BigDecimal("50"));
        
        // Expected Output: True
        assertTrue("Purchase should succeed with exact funds", result);
        
        // Verify balance was updated to zero: $5,500 - ($5,000 + $500) = $0
        assertEquals(BigDecimal.ZERO.setScale(2), account.getBalance());
        
        // Verify transaction was recorded
        assertEquals(1, account.getTransactions().size());
        StockTransaction txn = account.getTransactions().get(0);
        assertEquals("DEF", txn.getTicker());
        assertEquals(100, txn.getQuantity());
        assertEquals(new BigDecimal("50"), txn.getPurchasePrice());
    }
    
    @Test
    public void testCase4_LowValueSingleShare() {
        // SetUp: Investment account "INV103" balance $100
        InvestmentAccount account = new InvestmentAccount("INV103");
        account.setBalance(new BigDecimal("100"));
        customer.addAccount(account);
        
        // Action: Buy 1 share of "GHI" at $80
        boolean result = account.buyStocks("GHI", 1, new BigDecimal("80"));
        
        // Expected Output: True
        assertTrue("Purchase should succeed for single share", result);
        
        // Verify balance was updated correctly: $100 - ($80 + $8) = $12
        assertEquals(new BigDecimal("12.00"), account.getBalance());
        
        // Verify transaction was recorded
        assertEquals(1, account.getTransactions().size());
        StockTransaction txn = account.getTransactions().get(0);
        assertEquals("GHI", txn.getTicker());
        assertEquals(1, txn.getQuantity());
        assertEquals(new BigDecimal("80"), txn.getPurchasePrice());
    }
    
    @Test
    public void testCase5_SecondPurchaseFailsAfterBalanceDrops() {
        // SetUp: Investment account "INV104" balance $4,000
        InvestmentAccount account = new InvestmentAccount("INV104");
        account.setBalance(new BigDecimal("4000"));
        customer.addAccount(account);
        
        // First purchase: 50 shares of "JKL" at $40
        boolean firstPurchase = account.buyStocks("JKL", 50, new BigDecimal("40"));
        assertTrue("First purchase should succeed", firstPurchase);
        
        // Verify remaining balance after first purchase: $4,000 - ($2,000 + $200) = $1,800
        assertEquals(new BigDecimal("1800.00"), account.getBalance());
        
        // Action: Attempt second identical purchase requiring $2,200
        boolean secondPurchase = account.buyStocks("JKL", 50, new BigDecimal("40"));
        
        // Expected Output: False
        assertFalse("Second purchase should fail due to insufficient funds", secondPurchase);
        
        // Verify balance remains unchanged after failed second purchase
        assertEquals(new BigDecimal("1800.00"), account.getBalance());
        
        // Verify only one transaction was recorded (the successful first purchase)
        assertEquals(1, account.getTransactions().size());
    }
}