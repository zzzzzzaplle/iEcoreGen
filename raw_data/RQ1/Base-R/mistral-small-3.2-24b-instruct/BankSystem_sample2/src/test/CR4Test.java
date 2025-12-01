import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR4Test {
    
    private InvestmentAccount testAccount;
    private Customer testCustomer;
    
    @Before
    public void setUp() {
        // Common setup that runs before each test
        testCustomer = new Customer("Poe", "0814 Center St");
    }
    
    @Test
    public void testCase1_SufficientBalancePurchase() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds an investment account "INV100"
        // Investment account "INV100" balance $10,000
        testAccount = new InvestmentAccount("INV100", 10000.0);
        testCustomer.addAccount(testAccount);
        
        // Action: Buy 100 shares of "ABC" at $50 each (cost $5,000, commission $500)
        boolean result = testAccount.buyStocks("ABC", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Should successfully complete purchase with sufficient balance", result);
        
        // Verify transaction was recorded
        List<StockTransaction> transactions = testAccount.getStockTransactions();
        assertEquals("Should have one transaction record", 1, transactions.size());
        
        // Verify balance was updated correctly (10000 - (100*50 + 500) = 10000 - 5500 = 4500)
        assertEquals(4500.0, testAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_InsufficientFunds() {
        // SetUp: Investment account "INV101" balance $5,000
        testAccount = new InvestmentAccount("INV101", 5000.0);
        testCustomer.addAccount(testAccount);
        
        // Action: Attempt to buy 200 shares of "XYZ" at $30 each (commission $600, total needed $6,600)
        boolean result = testAccount.buyStocks("XYZ", 200, 30.0);
        
        // Expected Output: False
        assertFalse("Should fail purchase due to insufficient funds", result);
        
        // Verify no transaction was recorded
        List<StockTransaction> transactions = testAccount.getStockTransactions();
        assertEquals("Should have no transaction records", 0, transactions.size());
        
        // Verify balance remains unchanged
        assertEquals(5000.0, testAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_ExactFundsPurchase() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds an investment account "INV102"
        // Investment account "INV102" balance $5,500
        testAccount = new InvestmentAccount("INV102", 5500.0);
        testCustomer.addAccount(testAccount);
        
        // Action: Buy 100 shares of "DEF" at $50 each (commission $500, total needed $5,500 exactly)
        boolean result = testAccount.buyStocks("DEF", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Should successfully complete purchase with exact funds", result);
        
        // Verify transaction was recorded
        List<StockTransaction> transactions = testAccount.getStockTransactions();
        assertEquals("Should have one transaction record", 1, transactions.size());
        
        // Verify balance was updated correctly (5500 - 5500 = 0)
        assertEquals(0.0, testAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_LowValueSingleShare() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds an investment account "INV103"
        // Investment account "INV103" balance $100
        testAccount = new InvestmentAccount("INV103", 100.0);
        testCustomer.addAccount(testAccount);
        
        // Action: Buy 1 share of "GHI" at $80 (commission $8, total $88)
        boolean result = testAccount.buyStocks("GHI", 1, 80.0);
        
        // Expected Output: True
        assertTrue("Should successfully complete single share purchase", result);
        
        // Verify transaction was recorded
        List<StockTransaction> transactions = testAccount.getStockTransactions();
        assertEquals("Should have one transaction record", 1, transactions.size());
        
        // Verify balance was updated correctly (100 - (80 + 8) = 12)
        assertEquals(12.0, testAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_SecondPurchaseFailsAfterBalanceDrops() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds an investment account "INV104"
        // Investment account "INV104" balance $4,000
        testAccount = new InvestmentAccount("INV104", 4000.0);
        testCustomer.addAccount(testAccount);
        
        // First purchase: 50 shares of "JKL" at $40 (cost $2,000, commission $200, remaining balance $1,800)
        boolean firstPurchase = testAccount.buyStocks("JKL", 50, 40.0);
        assertTrue("First purchase should succeed", firstPurchase);
        
        // Verify first purchase details
        assertEquals("First purchase should leave $1,800 balance", 1800.0, testAccount.getBalance(), 0.001);
        assertEquals("Should have one transaction after first purchase", 1, testAccount.getStockTransactions().size());
        
        // Action: Attempt a second identical purchase requiring $2,200
        boolean secondPurchase = testAccount.buyStocks("JKL", 50, 40.0);
        
        // Expected Output: False
        assertFalse("Second purchase should fail due to insufficient balance", secondPurchase);
        
        // Verify still only one transaction (second purchase failed)
        assertEquals("Should still have only one transaction", 1, testAccount.getStockTransactions().size());
        
        // Verify balance unchanged after failed second purchase
        assertEquals("Balance should remain $1,800 after failed second purchase", 1800.0, testAccount.getBalance(), 0.001);
    }
}