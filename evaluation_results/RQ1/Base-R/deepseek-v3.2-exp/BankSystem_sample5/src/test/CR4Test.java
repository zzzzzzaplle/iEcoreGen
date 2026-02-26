import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    private BankSystem bankSystem;
    private Customer customer;
    
    @Before
    public void setUp() {
        // Initialize bank system and create customer for testing
        bankSystem = new BankSystem();
        bankSystem.addCustomer("Poe", "0814 Center St");
        customer = bankSystem.findCustomer("Poe", "0814 Center St");
    }
    
    @Test
    public void testCase1_SufficientBalancePurchase() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a investment account "INV100"
        // Investment account "INV100" balance $10 000
        customer.addAccount("INV100", "INVESTMENT");
        InvestmentAccount account = (InvestmentAccount) customer.getAccountById("INV100");
        account.setBalance(10000.0);
        
        // Action: Buy 100 shares of "ABC" at $50 each (cost $5 000, commission $500)
        boolean result = account.buyStock("ABC", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Sufficient balance purchase should succeed", result);
        
        // Verify transaction was recorded
        assertEquals("Should have one stock transaction", 1, account.getStockTransactions().size());
        
        // Verify balance was updated correctly (10000 - 5000 - 500 = 4500)
        assertEquals("Balance should be updated correctly", 4500.0, account.getBalance(), 0.001);
        
        // Verify stock quantity
        assertEquals("Should have 100 shares of ABC", 100, account.getStockQuantity("ABC"));
    }
    
    @Test
    public void testCase2_InsufficientFunds() {
        // SetUp: Investment account "INV101" balance $5 000
        customer.addAccount("INV101", "INVESTMENT");
        InvestmentAccount account = (InvestmentAccount) customer.getAccountById("INV101");
        account.setBalance(5000.0);
        
        // Action: Attempt to buy 200 shares of "XYZ" at $30 each (commission $600, total needed $6 600)
        boolean result = account.buyStock("XYZ", 200, 30.0);
        
        // Expected Output: False
        assertFalse("Insufficient funds purchase should fail", result);
        
        // Verify no transaction was recorded
        assertEquals("Should have no stock transactions", 0, account.getStockTransactions().size());
        
        // Verify balance remains unchanged
        assertEquals("Balance should remain unchanged", 5000.0, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_ExactFundsPurchase() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a investment account "INV102"
        // Investment account "INV102" balance $5 500
        customer.addAccount("INV102", "INVESTMENT");
        InvestmentAccount account = (InvestmentAccount) customer.getAccountById("INV102");
        account.setBalance(5500.0);
        
        // Action: Buy 100 shares of "DEF" at $50 each (commission $500, total needed $5 500 exactly)
        boolean result = account.buyStock("DEF", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Exact funds purchase should succeed", result);
        
        // Verify transaction was recorded
        assertEquals("Should have one stock transaction", 1, account.getStockTransactions().size());
        
        // Verify balance was updated correctly (5500 - 5000 - 500 = 0)
        assertEquals("Balance should be zero after exact funds purchase", 0.0, account.getBalance(), 0.001);
        
        // Verify stock quantity
        assertEquals("Should have 100 shares of DEF", 100, account.getStockQuantity("DEF"));
    }
    
    @Test
    public void testCase4_LowValueSingleShare() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a investment account "INV103"
        // Investment account "INV103" balance $100
        customer.addAccount("INV103", "INVESTMENT");
        InvestmentAccount account = (InvestmentAccount) customer.getAccountById("INV103");
        account.setBalance(100.0);
        
        // Action: Buy 1 share of "GHI" at $80 (commission $8, total $88)
        boolean result = account.buyStock("GHI", 1, 80.0);
        
        // Expected Output: True
        assertTrue("Low-value single share purchase should succeed", result);
        
        // Verify transaction was recorded
        assertEquals("Should have one stock transaction", 1, account.getStockTransactions().size());
        
        // Verify balance was updated correctly (100 - 80 - 8 = 12)
        assertEquals("Balance should be updated correctly", 12.0, account.getBalance(), 0.001);
        
        // Verify stock quantity
        assertEquals("Should have 1 share of GHI", 1, account.getStockQuantity("GHI"));
    }
    
    @Test
    public void testCase5_SecondPurchaseFailsAfterBalanceDrops() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a investment account "INV104"
        // Investment account "INV104" balance $4 000
        customer.addAccount("INV104", "INVESTMENT");
        InvestmentAccount account = (InvestmentAccount) customer.getAccountById("INV104");
        account.setBalance(4000.0);
        
        // First purchase already completed: 50 shares of "JKL" at $40 (cost $2 000, commission $200, remaining balance $1 800)
        boolean firstPurchase = account.buyStock("JKL", 50, 40.0);
        assertTrue("First purchase should succeed", firstPurchase);
        assertEquals("Balance after first purchase should be 1800", 1800.0, account.getBalance(), 0.001);
        
        // Action: Attempt a second identical purchase requiring $2 200
        boolean secondPurchase = account.buyStock("JKL", 50, 40.0);
        
        // Expected Output: False
        assertFalse("Second purchase should fail due to insufficient funds", secondPurchase);
        
        // Verify only one transaction was recorded
        assertEquals("Should have only one stock transaction", 1, account.getStockTransactions().size());
        
        // Verify balance remains at 1800
        assertEquals("Balance should remain at 1800", 1800.0, account.getBalance(), 0.001);
        
        // Verify stock quantity remains at 50
        assertEquals("Should have 50 shares of JKL", 50, account.getStockQuantity("JKL"));
    }
}