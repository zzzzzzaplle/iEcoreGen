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
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
    }
    
    @Test
    public void testCase1_SufficientBalancePurchase() {
        // SetUp: Investment account "INV100" balance $10,000
        investmentAccount = new InvestmentAccount();
        investmentAccount.setId("INV100");
        investmentAccount.setBalance(10000.0);
        customer.getAccounts().add(investmentAccount);
        
        // Action: Buy 100 shares of "ABC" at $50 each (cost $5,000, commission $500)
        boolean result = investmentAccount.buyStock("ABC", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Should successfully purchase stocks with sufficient balance", result);
        
        // Verify transaction was saved
        List<StockTransaction> transactions = investmentAccount.getTransactions();
        assertEquals("Should have 1 transaction", 1, transactions.size());
        assertEquals("Stock symbol should be ABC", "ABC", transactions.get(0).getStock());
        assertEquals("Quantity should be 100", 100, transactions.get(0).getQuantity());
        assertEquals("Price should be 50.0", 50.0, transactions.get(0).getPrice(), 0.001);
        assertEquals("Commission should be 500", 500.0, transactions.get(0).getCommission(), 0.001);
        
        // Verify balance was updated correctly
        assertEquals("Balance should be reduced by total cost", 4500.0, investmentAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_InsufficientFunds() {
        // SetUp: Investment account "INV101" balance $5,000
        investmentAccount = new InvestmentAccount();
        investmentAccount.setId("INV101");
        investmentAccount.setBalance(5000.0);
        customer.getAccounts().add(investmentAccount);
        
        // Action: Attempt to buy 200 shares of "XYZ" at $30 each (commission $600, total needed $6,600)
        boolean result = investmentAccount.buyStock("XYZ", 200, 30.0);
        
        // Expected Output: False
        assertFalse("Should fail purchase due to insufficient funds", result);
        
        // Verify no transaction was saved
        List<StockTransaction> transactions = investmentAccount.getTransactions();
        assertTrue("Should have no transactions", transactions.isEmpty());
        
        // Verify balance remains unchanged
        assertEquals("Balance should remain at 5000.0", 5000.0, investmentAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_ExactFundsPurchase() {
        // SetUp: Investment account "INV102" balance $5,500
        investmentAccount = new InvestmentAccount();
        investmentAccount.setId("INV102");
        investmentAccount.setBalance(5500.0);
        customer.getAccounts().add(investmentAccount);
        
        // Action: Buy 100 shares of "DEF" at $50 each (commission $500, total needed $5,500 exactly)
        boolean result = investmentAccount.buyStock("DEF", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Should successfully purchase stocks with exact funds", result);
        
        // Verify transaction was saved
        List<StockTransaction> transactions = investmentAccount.getTransactions();
        assertEquals("Should have 1 transaction", 1, transactions.size());
        assertEquals("Stock symbol should be DEF", "DEF", transactions.get(0).getStock());
        
        // Verify balance was updated to zero
        assertEquals("Balance should be reduced to zero", 0.0, investmentAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_LowValueSingleShare() {
        // SetUp: Investment account "INV103" balance $100
        investmentAccount = new InvestmentAccount();
        investmentAccount.setId("INV103");
        investmentAccount.setBalance(100.0);
        customer.getAccounts().add(investmentAccount);
        
        // Action: Buy 1 share of "GHI" at $80 (commission $8, total $88)
        boolean result = investmentAccount.buyStock("GHI", 1, 80.0);
        
        // Expected Output: True
        assertTrue("Should successfully purchase single share with low value", result);
        
        // Verify transaction was saved
        List<StockTransaction> transactions = investmentAccount.getTransactions();
        assertEquals("Should have 1 transaction", 1, transactions.size());
        assertEquals("Quantity should be 1", 1, transactions.get(0).getQuantity());
        
        // Verify balance was updated correctly
        assertEquals("Balance should be reduced by total cost", 12.0, investmentAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_SecondPurchaseFailsAfterBalanceDrops() {
        // SetUp: Investment account "INV104" balance $4,000
        investmentAccount = new InvestmentAccount();
        investmentAccount.setId("INV104");
        investmentAccount.setBalance(4000.0);
        customer.getAccounts().add(investmentAccount);
        
        // First purchase: 50 shares of "JKL" at $40 (cost $2,000, commission $200, remaining balance $1,800)
        boolean firstPurchase = investmentAccount.buyStock("JKL", 50, 40.0);
        assertTrue("First purchase should succeed", firstPurchase);
        assertEquals("Balance after first purchase should be 1800.0", 1800.0, investmentAccount.getBalance(), 0.001);
        
        // Action: Attempt a second identical purchase requiring $2,200
        boolean secondPurchase = investmentAccount.buyStock("JKL", 50, 40.0);
        
        // Expected Output: False
        assertFalse("Second purchase should fail due to insufficient funds after first purchase", secondPurchase);
        
        // Verify only one transaction exists (the first one)
        List<StockTransaction> transactions = investmentAccount.getTransactions();
        assertEquals("Should have only 1 transaction", 1, transactions.size());
        
        // Verify balance remains unchanged after failed second purchase
        assertEquals("Balance should remain at 1800.0", 1800.0, investmentAccount.getBalance(), 0.001);
    }
}