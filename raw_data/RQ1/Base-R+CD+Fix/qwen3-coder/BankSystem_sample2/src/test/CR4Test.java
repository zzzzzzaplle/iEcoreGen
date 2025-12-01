import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

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
        customer.addInvestmentAccount("INV100");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV100");
        account.setBalance(10000.0);
        
        // Action: Buy 100 shares of "ABC" at $50 each
        boolean result = account.buyStock("ABC", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Purchase should succeed with sufficient balance", result);
        
        // Verify balance was updated correctly (10000 - (100*50 + 500 commission) = 4500)
        assertEquals(4500.0, account.getBalance(), 0.001);
        
        // Verify transaction was recorded
        List<StockTransaction> transactions = account.getTransactions();
        assertEquals(1, transactions.size());
        StockTransaction transaction = transactions.get(0);
        assertEquals("ABC", transaction.getStock());
        assertEquals(100, transaction.getQuantity());
        assertEquals(50.0, transaction.getPrice(), 0.001);
        assertEquals(500.0, transaction.getCommission(), 0.001);
    }
    
    @Test
    public void testCase2_InsufficientFunds() {
        // SetUp: Investment account "INV101" balance $5,000
        customer.addInvestmentAccount("INV101");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV101");
        account.setBalance(5000.0);
        
        // Action: Attempt to buy 200 shares of "XYZ" at $30 each
        boolean result = account.buyStock("XYZ", 200, 30.0);
        
        // Expected Output: False
        assertFalse("Purchase should fail with insufficient funds", result);
        
        // Verify balance remains unchanged
        assertEquals(5000.0, account.getBalance(), 0.001);
        
        // Verify no transaction was recorded
        assertTrue("No transactions should be recorded for failed purchase", 
                   account.getTransactions().isEmpty());
    }
    
    @Test
    public void testCase3_ExactFundsPurchase() {
        // SetUp: Investment account "INV102" balance $5,500
        customer.addInvestmentAccount("INV102");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV102");
        account.setBalance(5500.0);
        
        // Action: Buy 100 shares of "DEF" at $50 each
        boolean result = account.buyStock("DEF", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Purchase should succeed with exact funds", result);
        
        // Verify balance was updated to zero
        assertEquals(0.0, account.getBalance(), 0.001);
        
        // Verify transaction was recorded
        List<StockTransaction> transactions = account.getTransactions();
        assertEquals(1, transactions.size());
        StockTransaction transaction = transactions.get(0);
        assertEquals("DEF", transaction.getStock());
        assertEquals(100, transaction.getQuantity());
        assertEquals(50.0, transaction.getPrice(), 0.001);
        assertEquals(500.0, transaction.getCommission(), 0.001);
    }
    
    @Test
    public void testCase4_LowValueSingleShare() {
        // SetUp: Investment account "INV103" balance $100
        customer.addInvestmentAccount("INV103");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV103");
        account.setBalance(100.0);
        
        // Action: Buy 1 share of "GHI" at $80
        boolean result = account.buyStock("GHI", 1, 80.0);
        
        // Expected Output: True
        assertTrue("Purchase should succeed for low-value single share", result);
        
        // Verify balance was updated correctly (100 - (80 + 8 commission) = 12)
        assertEquals(12.0, account.getBalance(), 0.001);
        
        // Verify transaction was recorded
        List<StockTransaction> transactions = account.getTransactions();
        assertEquals(1, transactions.size());
        StockTransaction transaction = transactions.get(0);
        assertEquals("GHI", transaction.getStock());
        assertEquals(1, transaction.getQuantity());
        assertEquals(80.0, transaction.getPrice(), 0.001);
        assertEquals(8.0, transaction.getCommission(), 0.001);
    }
    
    @Test
    public void testCase5_SecondPurchaseFailsAfterBalanceDrops() {
        // SetUp: Investment account "INV104" balance $4,000
        customer.addInvestmentAccount("INV104");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV104");
        account.setBalance(4000.0);
        
        // First purchase: 50 shares of "JKL" at $40
        boolean firstPurchase = account.buyStock("JKL", 50, 40.0);
        assertTrue("First purchase should succeed", firstPurchase);
        
        // Verify first purchase details
        assertEquals(1800.0, account.getBalance(), 0.001); // 4000 - (2000 + 200)
        assertEquals(1, account.getTransactions().size());
        
        // Action: Attempt second identical purchase requiring $2,200
        boolean secondPurchase = account.buyStock("JKL", 50, 40.0);
        
        // Expected Output: False
        assertFalse("Second purchase should fail due to insufficient balance", secondPurchase);
        
        // Verify balance remains unchanged from first purchase
        assertEquals(1800.0, account.getBalance(), 0.001);
        
        // Verify only one transaction exists (the first one)
        assertEquals("Only one transaction should exist", 1, account.getTransactions().size());
    }
}