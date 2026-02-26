import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

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
        // SetUp: Customer "Poe" holds investment account "INV100" with $10,000 balance
        InvestmentAccount account = new InvestmentAccount();
        account.setId("INV100");
        account.setBalance(10000.0);
        customer.addAccount(account);
        
        // Action: Buy 100 shares of "ABC" at $50 each (cost $5,000, commission $500)
        boolean result = account.buyStock("ABC", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Should successfully purchase stocks with sufficient balance", result);
        
        // Verify balance was updated correctly (10000 - 5000 - 500 = 4500)
        assertEquals(4500.0, account.getBalance(), 0.001);
        
        // Verify transaction was recorded
        assertEquals(1, account.getStockTransactions().size());
        StockTransaction transaction = account.getStockTransactions().get(0);
        assertEquals("ABC", transaction.getTicker());
        assertEquals(100, transaction.getQuantity());
        assertEquals(50.0, transaction.getPrice(), 0.001);
    }
    
    @Test
    public void testCase2_InsufficientFunds() {
        // SetUp: Investment account "INV101" with $5,000 balance
        InvestmentAccount account = new InvestmentAccount();
        account.setId("INV101");
        account.setBalance(5000.0);
        
        // Action: Attempt to buy 200 shares of "XYZ" at $30 each (commission $600, total needed $6,600)
        boolean result = account.buyStock("XYZ", 200, 30.0);
        
        // Expected Output: False
        assertFalse("Should fail purchase due to insufficient funds", result);
        
        // Verify balance remains unchanged
        assertEquals(5000.0, account.getBalance(), 0.001);
        
        // Verify no transaction was recorded
        assertTrue(account.getStockTransactions().isEmpty());
    }
    
    @Test
    public void testCase3_ExactFundsPurchase() {
        // SetUp: Customer "Poe" holds investment account "INV102" with $5,500 balance
        InvestmentAccount account = new InvestmentAccount();
        account.setId("INV102");
        account.setBalance(5500.0);
        customer.addAccount(account);
        
        // Action: Buy 100 shares of "DEF" at $50 each (commission $500, total needed $5,500 exactly)
        boolean result = account.buyStock("DEF", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Should successfully purchase stocks with exact funds", result);
        
        // Verify balance was updated to zero
        assertEquals(0.0, account.getBalance(), 0.001);
        
        // Verify transaction was recorded
        assertEquals(1, account.getStockTransactions().size());
        StockTransaction transaction = account.getStockTransactions().get(0);
        assertEquals("DEF", transaction.getTicker());
        assertEquals(100, transaction.getQuantity());
        assertEquals(50.0, transaction.getPrice(), 0.001);
    }
    
    @Test
    public void testCase4_LowValueSingleShare() {
        // SetUp: Customer "Poe" holds investment account "INV103" with $100 balance
        InvestmentAccount account = new InvestmentAccount();
        account.setId("INV103");
        account.setBalance(100.0);
        customer.addAccount(account);
        
        // Action: Buy 1 share of "GHI" at $80 (commission $8, total $88)
        boolean result = account.buyStock("GHI", 1, 80.0);
        
        // Expected Output: True
        assertTrue("Should successfully purchase single share with low value", result);
        
        // Verify balance was updated correctly (100 - 80 - 8 = 12)
        assertEquals(12.0, account.getBalance(), 0.001);
        
        // Verify transaction was recorded
        assertEquals(1, account.getStockTransactions().size());
        StockTransaction transaction = account.getStockTransactions().get(0);
        assertEquals("GHI", transaction.getTicker());
        assertEquals(1, transaction.getQuantity());
        assertEquals(80.0, transaction.getPrice(), 0.001);
    }
    
    @Test
    public void testCase5_SecondPurchaseFailsAfterBalanceDrops() {
        // SetUp: Customer "Poe" holds investment account "INV104" with $4,000 balance
        InvestmentAccount account = new InvestmentAccount();
        account.setId("INV104");
        account.setBalance(4000.0);
        customer.addAccount(account);
        
        // First purchase: 50 shares of "JKL" at $40 (cost $2,000, commission $200, remaining balance $1,800)
        boolean firstPurchase = account.buyStock("JKL", 50, 40.0);
        assertTrue("First purchase should succeed", firstPurchase);
        assertEquals(1800.0, account.getBalance(), 0.001);
        
        // Action: Attempt second identical purchase requiring $2,200
        boolean secondPurchase = account.buyStock("JKL", 50, 40.0);
        
        // Expected Output: False
        assertFalse("Second purchase should fail due to insufficient funds after first purchase", secondPurchase);
        
        // Verify balance remains unchanged from after first purchase
        assertEquals(1800.0, account.getBalance(), 0.001);
        
        // Verify only one transaction was recorded (the successful first purchase)
        assertEquals(1, account.getStockTransactions().size());
    }
}