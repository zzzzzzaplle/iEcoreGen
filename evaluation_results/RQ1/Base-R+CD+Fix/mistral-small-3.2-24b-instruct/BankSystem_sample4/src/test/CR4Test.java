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
        // SetUp: Customer "Poe" holds investment account "INV100" with balance $10,000
        investmentAccount = new InvestmentAccount();
        investmentAccount.setId("INV100");
        investmentAccount.setBalance(10000.0);
        
        // Add account to customer
        customer.getAccounts().add(investmentAccount);
        
        // Action: Buy 100 shares of "ABC" at $50 each (cost $5,000, commission $500)
        boolean result = investmentAccount.buyStock("ABC", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Should successfully complete purchase with sufficient balance", result);
        
        // Verify transaction was saved
        List<StockTransaction> transactions = investmentAccount.getTransactions();
        assertEquals("Should have one transaction", 1, transactions.size());
        
        StockTransaction transaction = transactions.get(0);
        assertEquals("Stock symbol should match", "ABC", transaction.getStock());
        assertEquals("Quantity should be 100", 100, transaction.getQuantity());
        assertEquals("Price should be $50", 50.0, transaction.getPrice(), 0.001);
        assertEquals("Commission should be $500", 500.0, transaction.getCommission(), 0.001);
        
        // Verify balance was updated correctly
        assertEquals("Balance should be reduced by total amount", 4500.0, investmentAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_InsufficientFunds() {
        // SetUp: Investment account "INV101" balance $5,000
        investmentAccount = new InvestmentAccount();
        investmentAccount.setId("INV101");
        investmentAccount.setBalance(5000.0);
        
        // Action: Attempt to buy 200 shares of "XYZ" at $30 each (commission $600, total needed $6,600)
        boolean result = investmentAccount.buyStock("XYZ", 200, 30.0);
        
        // Expected Output: False
        assertFalse("Should fail purchase due to insufficient funds", result);
        
        // Verify no transaction was saved
        assertEquals("Should have no transactions", 0, investmentAccount.getTransactions().size());
        
        // Verify balance remains unchanged
        assertEquals("Balance should remain $5,000", 5000.0, investmentAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_ExactFundsPurchase() {
        // SetUp: Customer "Poe" holds investment account "INV102" with balance $5,500
        investmentAccount = new InvestmentAccount();
        investmentAccount.setId("INV102");
        investmentAccount.setBalance(5500.0);
        
        // Add account to customer
        customer.getAccounts().add(investmentAccount);
        
        // Action: Buy 100 shares of "DEF" at $50 each (commission $500, total needed $5,500 exactly)
        boolean result = investmentAccount.buyStock("DEF", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Should successfully complete purchase with exact funds", result);
        
        // Verify transaction was saved
        List<StockTransaction> transactions = investmentAccount.getTransactions();
        assertEquals("Should have one transaction", 1, transactions.size());
        
        StockTransaction transaction = transactions.get(0);
        assertEquals("Stock symbol should match", "DEF", transaction.getStock());
        assertEquals("Quantity should be 100", 100, transaction.getQuantity());
        assertEquals("Price should be $50", 50.0, transaction.getPrice(), 0.001);
        assertEquals("Commission should be $500", 500.0, transaction.getCommission(), 0.001);
        
        // Verify balance was updated correctly (should be 0)
        assertEquals("Balance should be 0 after exact purchase", 0.0, investmentAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_LowValueSingleShare() {
        // SetUp: Customer "Poe" holds investment account "INV103" with balance $100
        investmentAccount = new InvestmentAccount();
        investmentAccount.setId("INV103");
        investmentAccount.setBalance(100.0);
        
        // Add account to customer
        customer.getAccounts().add(investmentAccount);
        
        // Action: Buy 1 share of "GHI" at $80 (commission $8, total $88)
        boolean result = investmentAccount.buyStock("GHI", 1, 80.0);
        
        // Expected Output: True
        assertTrue("Should successfully complete low-value single share purchase", result);
        
        // Verify transaction was saved
        List<StockTransaction> transactions = investmentAccount.getTransactions();
        assertEquals("Should have one transaction", 1, transactions.size());
        
        StockTransaction transaction = transactions.get(0);
        assertEquals("Stock symbol should match", "GHI", transaction.getStock());
        assertEquals("Quantity should be 1", 1, transaction.getQuantity());
        assertEquals("Price should be $80", 80.0, transaction.getPrice(), 0.001);
        assertEquals("Commission should be $8", 8.0, transaction.getCommission(), 0.001);
        
        // Verify balance was updated correctly
        assertEquals("Balance should be $12 after purchase", 12.0, investmentAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_SecondPurchaseFailsAfterBalanceDrops() {
        // SetUp: Customer "Poe" holds investment account "INV104" with balance $4,000
        investmentAccount = new InvestmentAccount();
        investmentAccount.setId("INV104");
        investmentAccount.setBalance(4000.0);
        
        // Add account to customer
        customer.getAccounts().add(investmentAccount);
        
        // First purchase: 50 shares of "JKL" at $40 (cost $2,000, commission $200, remaining balance $1,800)
        boolean firstPurchase = investmentAccount.buyStock("JKL", 50, 40.0);
        assertTrue("First purchase should succeed", firstPurchase);
        assertEquals("Balance after first purchase should be $1,800", 1800.0, investmentAccount.getBalance(), 0.001);
        
        // Action: Attempt second identical purchase requiring $2,200
        boolean secondPurchase = investmentAccount.buyStock("JKL", 50, 40.0);
        
        // Expected Output: False
        assertFalse("Second purchase should fail due to insufficient funds", secondPurchase);
        
        // Verify only one transaction exists (the first one)
        assertEquals("Should have only one transaction", 1, investmentAccount.getTransactions().size());
        
        // Verify balance remains unchanged after failed second purchase
        assertEquals("Balance should remain $1,800", 1800.0, investmentAccount.getBalance(), 0.001);
    }
}