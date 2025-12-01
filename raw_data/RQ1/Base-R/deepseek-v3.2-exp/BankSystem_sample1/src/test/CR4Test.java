import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    private InvestmentAccount investmentAccount;
    private Customer customer;
    
    @Before
    public void setUp() {
        customer = new Customer();
        investmentAccount = new InvestmentAccount();
    }
    
    @Test
    public void testCase1_SufficientBalancePurchase() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a investment account "INV100"
        // Investment account "INV100" balance $10 000.
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        investmentAccount.setAccountId("INV100");
        investmentAccount.setBalance(10000.0);
        customer.addAccount(investmentAccount);
        
        // Action: Buy 100 shares of "ABC" at $50 each (cost $5 000, commission $500)
        boolean result = investmentAccount.buyStocks("ABC", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Purchase should succeed with sufficient balance", result);
        
        // Verify transaction record was saved
        assertEquals("Should have one transaction record", 1, investmentAccount.getStockTransactions().size());
        assertEquals("Stock ticker should be ABC", "ABC", investmentAccount.getStockTransactions().get(0).getTicker());
        assertEquals("Quantity should be 100", 100, investmentAccount.getStockTransactions().get(0).getQuantity());
        assertEquals("Purchase price should be 50.0", 50.0, investmentAccount.getStockTransactions().get(0).getPurchasePrice(), 0.001);
        
        // Verify balance was updated correctly (10000 - (100*50 + 500) = 4500)
        assertEquals("Balance should be 4500 after purchase", 4500.0, investmentAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_InsufficientFunds() {
        // SetUp: Investment account "INV101" balance $5 000.
        investmentAccount.setAccountId("INV101");
        investmentAccount.setBalance(5000.0);
        
        // Action: Attempt to buy 200 shares of "XYZ" at $30 each (commission $600, total needed $6 600)
        boolean result = investmentAccount.buyStocks("XYZ", 200, 30.0);
        
        // Expected Output: False
        assertFalse("Purchase should fail with insufficient funds", result);
        
        // Verify no transaction record was saved
        assertTrue("Should have no transaction records", investmentAccount.getStockTransactions().isEmpty());
        
        // Verify balance remains unchanged
        assertEquals("Balance should remain 5000.0", 5000.0, investmentAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_ExactFundsPurchase() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a investment account "INV102"
        // Investment account "INV102" balance $5 500.
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        investmentAccount.setAccountId("INV102");
        investmentAccount.setBalance(5500.0);
        customer.addAccount(investmentAccount);
        
        // Action: Buy 100 shares of "DEF" at $50 each (commission $500, total needed $5 500 exactly)
        boolean result = investmentAccount.buyStocks("DEF", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Purchase should succeed with exact funds", result);
        
        // Verify transaction record was saved
        assertEquals("Should have one transaction record", 1, investmentAccount.getStockTransactions().size());
        assertEquals("Stock ticker should be DEF", "DEF", investmentAccount.getStockTransactions().get(0).getTicker());
        
        // Verify balance was updated correctly (5500 - 5500 = 0)
        assertEquals("Balance should be 0 after exact funds purchase", 0.0, investmentAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_LowValueSingleShare() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a investment account "INV103"
        // Investment account "INV103" balance $100.
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        investmentAccount.setAccountId("INV103");
        investmentAccount.setBalance(100.0);
        customer.addAccount(investmentAccount);
        
        // Action: Buy 1 share of "GHI" at $80 (commission $8, total $88)
        boolean result = investmentAccount.buyStocks("GHI", 1, 80.0);
        
        // Expected Output: True
        assertTrue("Purchase should succeed for low-value single share", result);
        
        // Verify transaction record was saved
        assertEquals("Should have one transaction record", 1, investmentAccount.getStockTransactions().size());
        assertEquals("Quantity should be 1", 1, investmentAccount.getStockTransactions().get(0).getQuantity());
        
        // Verify balance was updated correctly (100 - 88 = 12)
        assertEquals("Balance should be 12 after purchase", 12.0, investmentAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_SecondPurchaseFailsAfterBalanceDrops() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a investment account "INV104"
        // Investment account "INV104" balance $4 000.
        // First purchase already completed: 50 shares of "JKL" at $40 (cost $2 000, commission $200, remaining balance $1 800)
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        investmentAccount.setAccountId("INV104");
        investmentAccount.setBalance(4000.0);
        customer.addAccount(investmentAccount);
        
        // Execute first purchase
        boolean firstPurchase = investmentAccount.buyStocks("JKL", 50, 40.0);
        assertTrue("First purchase should succeed", firstPurchase);
        
        // Action: Attempt a second identical purchase requiring $2 200
        boolean secondPurchase = investmentAccount.buyStocks("JKL", 50, 40.0);
        
        // Expected Output: False
        assertFalse("Second purchase should fail due to insufficient funds", secondPurchase);
        
        // Verify only one transaction record exists (the first one)
        assertEquals("Should have only one transaction record", 1, investmentAccount.getStockTransactions().size());
        
        // Verify balance remains at 1800 (after first purchase)
        assertEquals("Balance should remain 1800 after failed second purchase", 1800.0, investmentAccount.getBalance(), 0.001);
    }
}