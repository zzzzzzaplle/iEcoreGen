import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR4Test {
    
    private Customer customer;
    private InvestmentAccount investmentAccount;
    
    @Before
    public void setUp() {
        customer = new Customer();
        investmentAccount = new InvestmentAccount();
    }
    
    @Test
    public void testCase1_SufficientBalancePurchase() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a investment account "INV100"
        // Investment account "INV100" balance $10 000
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        investmentAccount.setId("INV100");
        investmentAccount.setBalance(10000.0);
        customer.getAccounts().add(investmentAccount);
        
        // Action: Buy 100 shares of "ABC" at $50 each (cost $5 000, commission $500)
        boolean result = investmentAccount.buyStock("ABC", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Should successfully purchase stocks with sufficient balance", result);
        
        // Verify transaction record was saved
        List<StockTransaction> transactions = investmentAccount.getTransactions();
        assertEquals("Should have one transaction record", 1, transactions.size());
        
        StockTransaction transaction = transactions.get(0);
        assertEquals("Stock symbol should match", "ABC", transaction.getStock());
        assertEquals("Quantity should match", 100, transaction.getQuantity());
        assertEquals("Price should match", 50.0, transaction.getPrice(), 0.001);
        assertEquals("Commission should be 10% of stock cost", 500.0, transaction.getCommission(), 0.001);
        
        // Verify balance was updated correctly (10000 - (5000 + 500) = 4500)
        assertEquals("Balance should be updated correctly", 4500.0, investmentAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_InsufficientFunds() {
        // SetUp: Investment account "INV101" balance $5 000
        investmentAccount.setId("INV101");
        investmentAccount.setBalance(5000.0);
        
        // Action: Attempt to buy 200 shares of "XYZ" at $30 each (commission $600, total needed $6 600)
        boolean result = investmentAccount.buyStock("XYZ", 200, 30.0);
        
        // Expected Output: False
        assertFalse("Should fail purchase due to insufficient funds", result);
        
        // Verify no transaction record was saved
        assertTrue("Should have no transaction records", investmentAccount.getTransactions().isEmpty());
        
        // Verify balance remains unchanged
        assertEquals("Balance should remain unchanged", 5000.0, investmentAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_ExactFundsPurchase() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a investment account "INV102"
        // Investment account "INV102" balance $5 500
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        investmentAccount.setId("INV102");
        investmentAccount.setBalance(5500.0);
        customer.getAccounts().add(investmentAccount);
        
        // Action: Buy 100 shares of "DEF" at $50 each (commission $500, total needed $5 500 exactly)
        boolean result = investmentAccount.buyStock("DEF", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Should successfully purchase stocks with exact funds", result);
        
        // Verify transaction record was saved
        List<StockTransaction> transactions = investmentAccount.getTransactions();
        assertEquals("Should have one transaction record", 1, transactions.size());
        
        StockTransaction transaction = transactions.get(0);
        assertEquals("Stock symbol should match", "DEF", transaction.getStock());
        
        // Verify balance was updated correctly (5500 - 5500 = 0)
        assertEquals("Balance should be zero after exact funds purchase", 0.0, investmentAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_LowValueSingleShare() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a investment account "INV103"
        // Investment account "INV103" balance $100
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        investmentAccount.setId("INV103");
        investmentAccount.setBalance(100.0);
        customer.getAccounts().add(investmentAccount);
        
        // Action: Buy 1 share of "GHI" at $80 (commission $8, total $88)
        boolean result = investmentAccount.buyStock("GHI", 1, 80.0);
        
        // Expected Output: True
        assertTrue("Should successfully purchase single low-value share", result);
        
        // Verify transaction record was saved
        List<StockTransaction> transactions = investmentAccount.getTransactions();
        assertEquals("Should have one transaction record", 1, transactions.size());
        
        StockTransaction transaction = transactions.get(0);
        assertEquals("Stock symbol should match", "GHI", transaction.getStock());
        assertEquals("Quantity should be 1", 1, transaction.getQuantity());
        
        // Verify balance was updated correctly (100 - 88 = 12)
        assertEquals("Balance should be updated correctly", 12.0, investmentAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_SecondPurchaseFailsAfterBalanceDrops() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a investment account "INV104"
        // Investment account "INV104" balance $4 000
        // First purchase already completed: 50 shares of "JKL" at $40 (cost $2 000, commission $200, remaining balance $1 800)
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        investmentAccount.setId("INV104");
        investmentAccount.setBalance(4000.0);
        customer.getAccounts().add(investmentAccount);
        
        // Execute first purchase
        boolean firstPurchase = investmentAccount.buyStock("JKL", 50, 40.0);
        assertTrue("First purchase should succeed", firstPurchase);
        
        // Verify first purchase details
        assertEquals("Balance after first purchase should be 1800", 1800.0, investmentAccount.getBalance(), 0.001);
        assertEquals("Should have one transaction after first purchase", 1, investmentAccount.getTransactions().size());
        
        // Action: Attempt a second identical purchase requiring $2 200
        boolean secondPurchase = investmentAccount.buyStock("JKL", 50, 40.0);
        
        // Expected Output: False
        assertFalse("Second purchase should fail due to insufficient funds", secondPurchase);
        
        // Verify only one transaction record exists (the first one)
        assertEquals("Should still have only one transaction record", 1, investmentAccount.getTransactions().size());
        
        // Verify balance remains at 1800 (unchanged after failed second purchase)
        assertEquals("Balance should remain at 1800", 1800.0, investmentAccount.getBalance(), 0.001);
    }
}