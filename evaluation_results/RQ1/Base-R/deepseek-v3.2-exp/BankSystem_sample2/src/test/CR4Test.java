import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    private Customer customer;
    private InvestmentAccount investmentAccount;
    
    @Before
    public void setUp() {
        customer = new Customer();
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        investmentAccount = new InvestmentAccount();
    }
    
    @Test
    public void testCase1_SufficientBalancePurchase() {
        // SetUp: Investment account "INV100" balance $10,000
        investmentAccount.setId("INV100");
        investmentAccount.setBalance(10000.0);
        customer.getAccounts().add(investmentAccount);
        
        // Action: Buy 100 shares of "ABC" at $50 each (cost $5,000, commission $500)
        boolean result = investmentAccount.buyStocks("ABC", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Purchase should succeed with sufficient balance", result);
        
        // Verify balance was updated correctly: $10,000 - ($5,000 + $500) = $4,500
        assertEquals(4500.0, investmentAccount.getBalance(), 0.001);
        
        // Verify transaction was recorded
        assertEquals(1, investmentAccount.getStockTransactions().size());
        StockTransaction transaction = investmentAccount.getStockTransactions().get(0);
        assertEquals("ABC", transaction.getTicker());
        assertEquals(100, transaction.getQuantity());
        assertEquals(50.0, transaction.getPrice(), 0.001);
        assertEquals(500.0, transaction.getCommission(), 0.001);
    }
    
    @Test
    public void testCase2_InsufficientFunds() {
        // SetUp: Investment account "INV101" balance $5,000
        investmentAccount.setId("INV101");
        investmentAccount.setBalance(5000.0);
        customer.getAccounts().add(investmentAccount);
        
        // Action: Attempt to buy 200 shares of "XYZ" at $30 each (commission $600, total needed $6,600)
        boolean result = investmentAccount.buyStocks("XYZ", 200, 30.0);
        
        // Expected Output: False
        assertFalse("Purchase should fail with insufficient funds", result);
        
        // Verify balance remains unchanged
        assertEquals(5000.0, investmentAccount.getBalance(), 0.001);
        
        // Verify no transaction was recorded
        assertTrue(investmentAccount.getStockTransactions().isEmpty());
    }
    
    @Test
    public void testCase3_ExactFundsPurchase() {
        // SetUp: Investment account "