import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR4Test {
    
    private InvestmentAccount testAccount;
    private Customer testCustomer;
    
    @Before
    public void setUp() {
        testAccount = new InvestmentAccount();
        testCustomer = new Customer();
    }
    
    @Test
    public void testCase1_SufficientBalancePurchase() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds investment account "INV100" with balance $10,000
        testCustomer.setName("Poe");
        testCustomer.setAddress("0814 Center St");
        testAccount.setId("INV100");
        testAccount.setBalance(10000.0);
        testCustomer.addAccount(testAccount);
        
        // Action: Buy 100 shares of "ABC" at $50 each (cost $5,000, commission $500)
        boolean result = testAccount.buyStocks("ABC", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Should successfully purchase stocks with sufficient balance", result);
        
        // Verify balance was updated correctly (10000 - (100*50 + 500) = 4500)
        assertEquals(4500.0, testAccount.getBalance(), 0.001);
        
        // Verify transaction record was saved
        List<StockTransaction> transactions = testAccount.getStockTransactions();
        assertEquals(1, transactions.size());
        StockTransaction transaction = transactions.get(0);
        assertEquals("ABC", transaction.getTicker());
        assertEquals(100, transaction.getQuantity());
        assertEquals(50.0, transaction.getPrice(), 0.001);
    }
    
    @Test
    public void testCase2_InsufficientFunds() {
        // SetUp: Investment account "INV101" balance $5,000
        testAccount.setId("INV101");
        testAccount.setBalance(5000.0);
        
        // Action: Attempt to buy 200 shares of "XYZ" at $30 each (commission $600, total needed $6,600)
        boolean result = testAccount.buyStocks("XYZ", 200, 30.0);
        
        // Expected Output: False
        assertFalse("Should fail purchase due to insufficient funds", result);
        
        // Verify balance remains unchanged
        assertEquals(5000.0, testAccount.getBalance(), 0.001);
        
        // Verify no transaction record was saved
        assertTrue(testAccount.getStockTransactions().isEmpty());
    }
    
    @Test
    public void testCase3_ExactFundsPurchase() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds investment account "INV102" with balance $5,500
        testCustomer.setName("Poe");
        testCustomer.setAddress("0814 Center St");
        testAccount.setId("INV102");
        testAccount.setBalance(5500.0);
        testCustomer.addAccount(testAccount);
        
        // Action: Buy 100 shares of "DEF" at $50 each (commission $500, total needed $5,500 exactly)
        boolean result = testAccount.buyStocks("DEF", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Should successfully purchase stocks with exact funds", result);
        
        // Verify balance was updated to zero
        assertEquals(0.0, testAccount.getBalance(), 0.001);
        
        // Verify transaction record was saved
        List<StockTransaction> transactions = testAccount.getStockTransactions();
        assertEquals(1, transactions.size());
        StockTransaction transaction = transactions.get(0);
        assertEquals("DEF", transaction.getTicker());
        assertEquals(100, transaction.getQuantity());
        assertEquals(50.0, transaction.getPrice(), 0.001);
    }
    
    @Test
    public void testCase4_LowValueSingleShare() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds investment account "INV103" with balance $100
        testCustomer.setName("Poe");
        testCustomer.setAddress("0814 Center St");
        testAccount.setId("INV103");
        testAccount.setBalance(100.0);
        testCustomer.addAccount(testAccount);
        
        // Action: Buy 1 share of "GHI" at $80 (commission $8, total $88)
        boolean result = testAccount.buyStocks("GHI", 1, 80.0);
        
        // Expected Output: True
        assertTrue("Should successfully purchase single share with sufficient funds", result);
        
        // Verify balance was updated correctly (100 - (1*80 + 8) = 12)
        assertEquals(12.0, testAccount.getBalance(), 0.001);
        
        // Verify transaction record was saved
        List<StockTransaction> transactions = testAccount.getStockTransactions();
        assertEquals(1, transactions.size());
        StockTransaction transaction = transactions.get(0);
        assertEquals("GHI", transaction.getTicker());
        assertEquals(1, transaction.getQuantity());
        assertEquals(80.0, transaction.getPrice(), 0.001);
    }
    
    @Test
    public void testCase5_SecondPurchaseFailsAfterBalanceDrops() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds investment account "INV104" with balance $4,000
        testCustomer.setName("Poe");
        testCustomer.setAddress("0814 Center St");
        testAccount.setId("INV104");
        testAccount.setBalance(4000.0);
        testCustomer.addAccount(testAccount);
        
        // First purchase: 50 shares of "JKL" at $40 (cost $2,000, commission $200, remaining balance $1,800)
        boolean firstPurchase = testAccount.buyStocks("JKL", 50, 40.0);
        assertTrue("First purchase should succeed", firstPurchase);
        assertEquals(1800.0, testAccount.getBalance(), 0.001);
        
        // Action: Attempt second identical purchase requiring $2,200
        boolean secondPurchase = testAccount.buyStocks("JKL", 50, 40.0);
        
        // Expected Output: False
        assertFalse("Second purchase should fail due to insufficient balance", secondPurchase);
        
        // Verify balance remains unchanged after failed second purchase
        assertEquals(1800.0, testAccount.getBalance(), 0.001);
        
        // Verify only one transaction record exists
        List<StockTransaction> transactions = testAccount.getStockTransactions();
        assertEquals(1, transactions.size());
    }
}