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
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        investmentAccount.setId("INV100");
        investmentAccount.setBalance(10000.0);
        customer.addAccount(investmentAccount);
        
        // Action: Buy 100 shares of "ABC" at $50 each (cost $5 000, commission $500)
        boolean result = investmentAccount.buyStock("ABC", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Purchase with sufficient balance should return true", result);
        assertEquals("Balance should be reduced by stock cost and commission", 4500.0, investmentAccount.getBalance(), 0.001);
        assertEquals("Should have one stock transaction", 1, investmentAccount.getStockTransactions().size());
        
        StockTransaction transaction = investmentAccount.getStockTransactions().get(0);
        assertEquals("Ticker should be ABC", "ABC", transaction.getTicker());
        assertEquals("Quantity should be 100", 100, transaction.getQuantity());
        assertEquals("Price should be 50.0", 50.0, transaction.getPrice(), 0.001);
    }
    
    @Test
    public void testCase2_InsufficientFunds() {
        // SetUp: Investment account "INV101" balance $5 000
        investmentAccount.setId("INV101");
        investmentAccount.setBalance(5000.0);
        
        // Action: Attempt to buy 200 shares of "XYZ" at $30 each (commission $600, total needed $6 600)
        boolean result = investmentAccount.buyStock("XYZ", 200, 30.0);
        
        // Expected Output: False
        assertFalse("Purchase with insufficient funds should return false", result);
        assertEquals("Balance should remain unchanged", 5000.0, investmentAccount.getBalance(), 0.001);
        assertTrue("No stock transactions should be added", investmentAccount.getStockTransactions().isEmpty());
    }
    
    @Test
    public void testCase3_ExactFundsPurchase() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a investment account "INV102"
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        investmentAccount.setId("INV102");
        investmentAccount.setBalance(5500.0);
        customer.addAccount(investmentAccount);
        
        // Action: Buy 100 shares of "DEF" at $50 each (commission $500, total needed $5 500 exactly)
        boolean result = investmentAccount.buyStock("DEF", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Purchase with exact funds should return true", result);
        assertEquals("Balance should be zero after exact purchase", 0.0, investmentAccount.getBalance(), 0.001);
        assertEquals("Should have one stock transaction", 1, investmentAccount.getStockTransactions().size());
        
        StockTransaction transaction = investmentAccount.getStockTransactions().get(0);
        assertEquals("Ticker should be DEF", "DEF", transaction.getTicker());
        assertEquals("Quantity should be 100", 100, transaction.getQuantity());
        assertEquals("Price should be 50.0", 50.0, transaction.getPrice(), 0.001);
    }
    
    @Test
    public void testCase4_LowValueSingleShare() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a investment account "INV103"
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        investmentAccount.setId("INV103");
        investmentAccount.setBalance(100.0);
        customer.addAccount(investmentAccount);
        
        // Action: Buy 1 share of "GHI" at $80 (commission $8, total $88)
        boolean result = investmentAccount.buyStock("GHI", 1, 80.0);
        
        // Expected Output: True
        assertTrue("Single share purchase with sufficient balance should return true", result);
        assertEquals("Balance should be reduced by stock cost and commission", 12.0, investmentAccount.getBalance(), 0.001);
        assertEquals("Should have one stock transaction", 1, investmentAccount.getStockTransactions().size());
        
        StockTransaction transaction = investmentAccount.getStockTransactions().get(0);
        assertEquals("Ticker should be GHI", "GHI", transaction.getTicker());
        assertEquals("Quantity should be 1", 1, transaction.getQuantity());
        assertEquals("Price should be 80.0", 80.0, transaction.getPrice(), 0.001);
    }
    
    @Test
    public void testCase5_SecondPurchaseFailsAfterBalanceDrops() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a investment account "INV104"
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        investmentAccount.setId("INV104");
        investmentAccount.setBalance(4000.0);
        customer.addAccount(investmentAccount);
        
        // First purchase: 50 shares of "JKL" at $40 (cost $2 000, commission $200, remaining balance $1 800)
        boolean firstPurchase = investmentAccount.buyStock("JKL", 50, 40.0);
        assertTrue("First purchase should succeed", firstPurchase);
        assertEquals("Balance after first purchase should be 1800.0", 1800.0, investmentAccount.getBalance(), 0.001);
        
        // Action: Attempt a second identical purchase requiring $2 200
        boolean secondPurchase = investmentAccount.buyStock("JKL", 50, 40.0);
        
        // Expected Output: False
        assertFalse("Second purchase with insufficient funds should return false", secondPurchase);
        assertEquals("Balance should remain unchanged after failed purchase", 1800.0, investmentAccount.getBalance(), 0.001);
        assertEquals("Should still have only one stock transaction", 1, investmentAccount.getStockTransactions().size());
    }
}