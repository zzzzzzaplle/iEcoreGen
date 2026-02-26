import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Customer customer;
    private InvestmentAccount investmentAccount;
    private BankSystem bankSystem;
    
    @Before
    public void setUp() {
        // Initialize bank system and customer before each test
        bankSystem = new BankSystem();
        customer = new Customer();
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        bankSystem.addCustomer(customer);
    }
    
    @Test
    public void testCase1_SufficientBalancePurchase() {
        // SetUp: Investment account "INV100" balance $10,000
        investmentAccount = new InvestmentAccount();
        investmentAccount.setAccountId("INV100");
        investmentAccount.setBalance(10000.0);
        customer.addAccount(investmentAccount);
        
        // Action: Buy 100 shares of "ABC" at $50 each
        boolean result = investmentAccount.buyStock("ABC", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Purchase should succeed with sufficient balance", result);
        
        // Verify balance was updated correctly: $10,000 - ($5,000 + $500) = $4,500
        assertEquals(4500.0, investmentAccount.getBalance(), 0.001);
        
        // Verify transaction was recorded
        assertEquals(1, investmentAccount.getStockTransactions().size());
        StockTransaction transaction = investmentAccount.getStockTransactions().get(0);
        assertEquals("ABC", transaction.getTicker());
        assertEquals(100, transaction.getQuantity());
        assertEquals(50.0, transaction.getPurchasePrice(), 0.001);
        assertEquals(500.0, transaction.getCommission(), 0.001);
    }
    
    @Test
    public void testCase2_InsufficientFunds() {
        // SetUp: Investment account "INV101" balance $5,000
        investmentAccount = new InvestmentAccount();
        investmentAccount.setAccountId("INV101");
        investmentAccount.setBalance(5000.0);
        
        // Action: Attempt to buy 200 shares of "XYZ" at $30 each
        boolean result = investmentAccount.buyStock("XYZ", 200, 30.0);
        
        // Expected Output: False
        assertFalse("Purchase should fail with insufficient funds", result);
        
        // Verify balance remains unchanged
        assertEquals(5000.0, investmentAccount.getBalance(), 0.001);
        
        // Verify no transaction was recorded
        assertTrue(investmentAccount.getStockTransactions().isEmpty());
    }
    
    @Test
    public void testCase3_ExactFundsPurchase() {
        // SetUp: Investment account "INV102" balance $5,500
        investmentAccount = new InvestmentAccount();
        investmentAccount.setAccountId("INV102");
        investmentAccount.setBalance(5500.0);
        customer.addAccount(investmentAccount);
        
        // Action: Buy 100 shares of "DEF" at $50 each
        boolean result = investmentAccount.buyStock("DEF", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Purchase should succeed with exact funds", result);
        
        // Verify balance was updated correctly: $5,500 - ($5,000 + $500) = $0
        assertEquals(0.0, investmentAccount.getBalance(), 0.001);
        
        // Verify transaction was recorded
        assertEquals(1, investmentAccount.getStockTransactions().size());
        StockTransaction transaction = investmentAccount.getStockTransactions().get(0);
        assertEquals("DEF", transaction.getTicker());
        assertEquals(100, transaction.getQuantity());
        assertEquals(50.0, transaction.getPurchasePrice(), 0.001);
        assertEquals(500.0, transaction.getCommission(), 0.001);
    }
    
    @Test
    public void testCase4_LowValueSingleShare() {
        // SetUp: Investment account "INV103" balance $100
        investmentAccount = new InvestmentAccount();
        investmentAccount.setAccountId("INV103");
        investmentAccount.setBalance(100.0);
        customer.addAccount(investmentAccount);
        
        // Action: Buy 1 share of "GHI" at $80
        boolean result = investmentAccount.buyStock("GHI", 1, 80.0);
        
        // Expected Output: True
        assertTrue("Purchase should succeed for low-value single share", result);
        
        // Verify balance was updated correctly: $100 - ($80 + $8) = $12
        assertEquals(12.0, investmentAccount.getBalance(), 0.001);
        
        // Verify transaction was recorded
        assertEquals(1, investmentAccount.getStockTransactions().size());
        StockTransaction transaction = investmentAccount.getStockTransactions().get(0);
        assertEquals("GHI", transaction.getTicker());
        assertEquals(1, transaction.getQuantity());
        assertEquals(80.0, transaction.getPurchasePrice(), 0.001);
        assertEquals(8.0, transaction.getCommission(), 0.001);
    }
    
    @Test
    public void testCase5_SecondPurchaseFailsAfterBalanceDrops() {
        // SetUp: Investment account "INV104" balance $4,000
        investmentAccount = new InvestmentAccount();
        investmentAccount.setAccountId("INV104");
        investmentAccount.setBalance(4000.0);
        customer.addAccount(investmentAccount);
        
        // First purchase: 50 shares of "JKL" at $40
        boolean firstPurchase = investmentAccount.buyStock("JKL", 50, 40.0);
        assertTrue("First purchase should succeed", firstPurchase);
        
        // Verify balance after first purchase: $4,000 - ($2,000 + $200) = $1,800
        assertEquals(1800.0, investmentAccount.getBalance(), 0.001);
        
        // Action: Attempt second identical purchase requiring $2,200
        boolean secondPurchase = investmentAccount.buyStock("JKL", 50, 40.0);
        
        // Expected Output: False
        assertFalse("Second purchase should fail due to insufficient funds", secondPurchase);
        
        // Verify balance remains unchanged after failed purchase
        assertEquals(1800.0, investmentAccount.getBalance(), 0.001);
        
        // Verify only one transaction was recorded (the successful first purchase)
        assertEquals(1, investmentAccount.getStockTransactions().size());
    }
}