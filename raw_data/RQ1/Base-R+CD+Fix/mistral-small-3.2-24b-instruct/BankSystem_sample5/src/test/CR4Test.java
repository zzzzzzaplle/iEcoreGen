import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    private Customer customer;
    
    @Before
    public void setUp() {
        // Create a new customer before each test
        customer = new Customer();
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
    }
    
    @Test
    public void testCase1_SufficientBalancePurchase() {
        // SetUp: Investment account "INV100" balance $10,000
        customer.addInvestmentAccount("INV100");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV100");
        account.setBalance(10000);
        
        // Action: Buy 100 shares of "ABC" at $50 each (cost $5,000, commission $500)
        boolean result = account.buyStock("ABC", 100, 50);
        
        // Expected Output: True
        assertTrue("Should successfully purchase with sufficient balance", result);
        
        // Verify balance was updated correctly (10000 - (100*50 + 100*50*0.1) = 10000 - 5500 = 4500)
        assertEquals(4500, account.getBalance(), 0.001);
        
        // Verify transaction was recorded
        assertEquals(1, account.getTransactions().size());
        StockTransaction transaction = account.getTransactions().get(0);
        assertEquals("ABC", transaction.getStock());
        assertEquals(100, transaction.getQuantity());
        assertEquals(50, transaction.getPrice(), 0.001);
        assertEquals(500, transaction.getCommission(), 0.001);
    }
    
    @Test
    public void testCase2_InsufficientFunds() {
        // SetUp: Investment account "INV101" balance $5,000
        customer.addInvestmentAccount("INV101");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV101");
        account.setBalance(5000);
        
        // Action: Attempt to buy 200 shares of "XYZ" at $30 each (commission $600, total needed $6,600)
        boolean result = account.buyStock("XYZ", 200, 30);
        
        // Expected Output: False
        assertFalse("Should fail purchase with insufficient funds", result);
        
        // Verify balance remains unchanged
        assertEquals(5000, account.getBalance(), 0.001);
        
        // Verify no transaction was recorded
        assertEquals(0, account.getTransactions().size());
    }
    
    @Test
    public void testCase3_ExactFundsPurchase() {
        // SetUp: Investment account "INV102" balance $5,500
        customer.addInvestmentAccount("INV102");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV102");
        account.setBalance(5500);
        
        // Action: Buy 100 shares of "DEF" at $50 each (commission $500, total needed $5,500 exactly)
        boolean result = account.buyStock("DEF", 100, 50);
        
        // Expected Output: True
        assertTrue("Should successfully purchase with exact funds", result);
        
        // Verify balance was updated to zero
        assertEquals(0, account.getBalance(), 0.001);
        
        // Verify transaction was recorded
        assertEquals(1, account.getTransactions().size());
        StockTransaction transaction = account.getTransactions().get(0);
        assertEquals("DEF", transaction.getStock());
        assertEquals(100, transaction.getQuantity());
        assertEquals(50, transaction.getPrice(), 0.001);
        assertEquals(500, transaction.getCommission(), 0.001);
    }
    
    @Test
    public void testCase4_LowValueSingleShare() {
        // SetUp: Investment account "INV103" balance $100
        customer.addInvestmentAccount("INV103");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV103");
        account.setBalance(100);
        
        // Action: Buy 1 share of "GHI" at $80 (commission $8, total $88)
        boolean result = account.buyStock("GHI", 1, 80);
        
        // Expected Output: True
        assertTrue("Should successfully purchase single low-value share", result);
        
        // Verify balance was updated correctly (100 - (1*80 + 1*80*0.1) = 100 - 88 = 12)
        assertEquals(12, account.getBalance(), 0.001);
        
        // Verify transaction was recorded
        assertEquals(1, account.getTransactions().size());
        StockTransaction transaction = account.getTransactions().get(0);
        assertEquals("GHI", transaction.getStock());
        assertEquals(1, transaction.getQuantity());
        assertEquals(80, transaction.getPrice(), 0.001);
        assertEquals(8, transaction.getCommission(), 0.001);
    }
    
    @Test
    public void testCase5_SecondPurchaseFailsAfterBalanceDrops() {
        // SetUp: Investment account "INV104" balance $4,000
        customer.addInvestmentAccount("INV104");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV104");
        account.setBalance(4000);
        
        // First purchase: 50 shares of "JKL" at $40 (cost $2,000, commission $200, remaining balance $1,800)
        boolean firstPurchase = account.buyStock("JKL", 50, 40);
        assertTrue("First purchase should succeed", firstPurchase);
        assertEquals(1800, account.getBalance(), 0.001);
        
        // Action: Attempt second identical purchase requiring $2,200
        boolean secondPurchase = account.buyStock("JKL", 50, 40);
        
        // Expected Output: False
        assertFalse("Second purchase should fail due to insufficient balance", secondPurchase);
        
        // Verify balance remains unchanged from first purchase
        assertEquals(1800, account.getBalance(), 0.001);
        
        // Verify only one transaction was recorded (the first one)
        assertEquals(1, account.getTransactions().size());
    }
}