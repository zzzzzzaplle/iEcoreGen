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
        
        // Add account to customer
        List<Account> accounts = new java.util.ArrayList<>();
        accounts.add(investmentAccount);
        customer.setAccounts(accounts);
        
        // Action: Buy 100 shares of "ABC" at $50 each (cost $5,000, commission $500)
        boolean result = investmentAccount.buyStock("ABC", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Should successfully buy stocks with sufficient balance", result);
        
        // Verify transaction record was saved
        assertEquals("Should have exactly 1 transaction", 1, investmentAccount.getTransactions().size());
        
        // Verify balance was updated correctly (10000 - (5000 + 500) = 4500)
        assertEquals("Balance should be updated correctly", 4500.0, investmentAccount.getBalance(), 0.001);
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
        assertFalse("Should fail to buy stocks with insufficient funds", result);
        
        // Verify no transaction record was saved
        assertTrue("Should have no transactions", investmentAccount.getTransactions().isEmpty());
        
        // Verify balance remains unchanged
        assertEquals("Balance should remain unchanged", 5000.0, investmentAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_ExactFundsPurchase() {
        // SetUp: Investment account "INV102" balance $5,500
        investmentAccount = new InvestmentAccount();
        investmentAccount.setId("INV102");
        investmentAccount.setBalance(5500.0);
        
        // Add account to customer
        List<Account> accounts = new java.util.ArrayList<>();
        accounts.add(investmentAccount);
        customer.setAccounts(accounts);
        
        // Action: Buy 100 shares of "DEF" at $50 each (commission $500, total needed $5,500 exactly)
        boolean result = investmentAccount.buyStock("DEF", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Should successfully buy stocks with exact funds", result);
        
        // Verify transaction record was saved
        assertEquals("Should have exactly 1 transaction", 1, investmentAccount.getTransactions().size());
        
        // Verify balance was updated correctly (5500 - 5500 = 0)
        assertEquals("Balance should be zero after exact purchase", 0.0, investmentAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_LowValueSingleShare() {
        // SetUp: Investment account "INV103" balance $100
        investmentAccount = new InvestmentAccount();
        investmentAccount.setId("INV103");
        investmentAccount.setBalance(100.0);
        
        // Add account to customer
        List<Account> accounts = new java.util.ArrayList<>();
        accounts.add(investmentAccount);
        customer.setAccounts(accounts);
        
        // Action: Buy 1 share of "GHI" at $80 (commission $8, total $88)
        boolean result = investmentAccount.buyStock("GHI", 1, 80.0);
        
        // Expected Output: True
        assertTrue("Should successfully buy single share with sufficient balance", result);
        
        // Verify transaction record was saved
        assertEquals("Should have exactly 1 transaction", 1, investmentAccount.getTransactions().size());
        
        // Verify balance was updated correctly (100 - 88 = 12)
        assertEquals("Balance should be updated correctly", 12.0, investmentAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_SecondPurchaseFailsAfterBalanceDrops() {
        // SetUp: Investment account "INV104" balance $4,000
        investmentAccount = new InvestmentAccount();
        investmentAccount.setId("INV104");
        investmentAccount.setBalance(4000.0);
        
        // Add account to customer
        List<Account> accounts = new java.util.ArrayList<>();
        accounts.add(investmentAccount);
        customer.setAccounts(accounts);
        
        // First purchase: 50 shares of "JKL" at $40 (cost $2,000, commission $200)
        boolean firstPurchase = investmentAccount.buyStock("JKL", 50, 40.0);
        assertTrue("First purchase should succeed", firstPurchase);
        
        // Verify first purchase details
        assertEquals("Remaining balance after first purchase should be $1,800", 1800.0, investmentAccount.getBalance(), 0.001);
        assertEquals("Should have 1 transaction after first purchase", 1, investmentAccount.getTransactions().size());
        
        // Action: Attempt second identical purchase requiring $2,200
        boolean secondPurchase = investmentAccount.buyStock("JKL", 50, 40.0);
        
        // Expected Output: False
        assertFalse("Second purchase should fail due to insufficient balance", secondPurchase);
        
        // Verify no additional transaction record was saved
        assertEquals("Should still have only 1 transaction", 1, investmentAccount.getTransactions().size());
        
        // Verify balance remains unchanged after failed second purchase
        assertEquals("Balance should remain at $1,800", 1800.0, investmentAccount.getBalance(), 0.001);
    }
}