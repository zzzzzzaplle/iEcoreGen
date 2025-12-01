import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    private Customer customer;
    private InvestmentAccount account;
    
    @Before
    public void setUp() {
        customer = new Customer();
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
    }
    
    @Test
    public void testCase1_SufficientBalancePurchase() {
        // SetUp: Customer "Poe" holds investment account "INV100" with $10,000 balance
        customer.addInvestmentAccount("INV100");
        account = (InvestmentAccount) customer.findAccountById("INV100");
        account.setBalance(10000.0);
        
        // Action: Buy 100 shares of "ABC" at $50 each
        boolean result = account.buyStock("ABC", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Purchase should succeed with sufficient balance", result);
        
        // Verify transaction was recorded
        assertEquals(1, account.getTransactions().size());
        assertEquals("ABC", account.getTransactions().get(0).getStock());
        
        // Verify balance was updated correctly (10000 - (100*50) - (5000*0.1) = 4500)
        assertEquals(4500.0, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_InsufficientFunds() {
        // SetUp: Investment account "INV101" with $5,000 balance
        customer.addInvestmentAccount("INV101");
        account = (InvestmentAccount) customer.findAccountById("INV101");
        account.setBalance(5000.0);
        
        // Action: Attempt to buy 200 shares of "XYZ" at $30 each (needs $6,600)
        boolean result = account.buyStock("XYZ", 200, 30.0);
        
        // Expected Output: False
        assertFalse("Purchase should fail with insufficient funds", result);
        
        // Verify no transaction was recorded
        assertEquals(0, account.getTransactions().size());
        
        // Verify balance remains unchanged
        assertEquals(5000.0, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_ExactFundsPurchase() {
        // SetUp: Customer "Poe" holds investment account "INV102" with $5,500 balance
        customer.addInvestmentAccount("INV102");
        account = (InvestmentAccount) customer.findAccountById("INV102");
        account.setBalance(5500.0);
        
        // Action: Buy 100 shares of "DEF" at $50 each (exactly $5,500 needed)
        boolean result = account.buyStock("DEF", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Purchase should succeed with exact funds", result);
        
        // Verify transaction was recorded
        assertEquals(1, account.getTransactions().size());
        assertEquals("DEF", account.getTransactions().get(0).getStock());
        
        // Verify balance was updated to zero
        assertEquals(0.0, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_LowValueSingleShare() {
        // SetUp: Customer "Poe" holds investment account "INV103" with $100 balance
        customer.addInvestmentAccount("INV103");
        account = (InvestmentAccount) customer.findAccountById("INV103");
        account.setBalance(100.0);
        
        // Action: Buy 1 share of "GHI" at $80
        boolean result = account.buyStock("GHI", 1, 80.0);
        
        // Expected Output: True
        assertTrue("Purchase should succeed for low-value single share", result);
        
        // Verify transaction was recorded
        assertEquals(1, account.getTransactions().size());
        assertEquals("GHI", account.getTransactions().get(0).getStock());
        
        // Verify balance was updated correctly (100 - 80 - 8 = 12)
        assertEquals(12.0, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_SecondPurchaseFailsAfterBalanceDrops() {
        // SetUp: Customer "Poe" holds investment account "INV104" with $4,000 balance
        customer.addInvestmentAccount("INV104");
        account = (InvestmentAccount) customer.findAccountById("INV104");
        account.setBalance(4000.0);
        
        // First purchase: 50 shares of "JKL" at $40
        boolean firstPurchase = account.buyStock("JKL", 50, 40.0);
        assertTrue("First purchase should succeed", firstPurchase);
        
        // Verify first purchase details
        assertEquals(1800.0, account.getBalance(), 0.001); // 4000 - 2000 - 200 = 1800
        assertEquals(1, account.getTransactions().size());
        
        // Action: Attempt second identical purchase requiring $2,200
        boolean secondPurchase = account.buyStock("JKL", 50, 40.0);
        
        // Expected Output: False
        assertFalse("Second purchase should fail due to insufficient balance", secondPurchase);
        
        // Verify no additional transaction was recorded
        assertEquals(1, account.getTransactions().size());
        
        // Verify balance remains unchanged after failed purchase
        assertEquals(1800.0, account.getBalance(), 0.001);
    }
}