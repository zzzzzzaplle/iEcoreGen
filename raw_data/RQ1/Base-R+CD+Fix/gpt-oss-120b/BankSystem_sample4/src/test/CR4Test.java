import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    private Customer customer;
    
    @Before
    public void setUp() {
        customer = new Customer("Poe", "0814 Center St");
    }
    
    @Test
    public void testCase1_SufficientBalancePurchase() {
        // SetUp: Customer "Poe" holds investment account "INV100" with $10,000 balance
        customer.addInvestmentAccount("INV100");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV100");
        account.setBalance(10000.0);
        
        // Action: Buy 100 shares of "ABC" at $50 each (cost $5,000, commission $500)
        boolean result = account.buyStock("ABC", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Purchase should succeed with sufficient balance", result);
        assertEquals("Balance should be reduced by $5,500", 4500.0, account.getBalance(), 0.001);
        assertEquals("Should have one transaction recorded", 1, account.getTransactions().size());
    }
    
    @Test
    public void testCase2_InsufficientFunds() {
        // SetUp: Investment account "INV101" with $5,000 balance
        customer.addInvestmentAccount("INV101");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV101");
        account.setBalance(5000.0);
        
        // Action: Attempt to buy 200 shares of "XYZ" at $30 each (total needed $6,600)
        boolean result = account.buyStock("XYZ", 200, 30.0);
        
        // Expected Output: False
        assertFalse("Purchase should fail with insufficient funds", result);
        assertEquals("Balance should remain unchanged", 5000.0, account.getBalance(), 0.001);
        assertTrue("No transactions should be recorded", account.getTransactions().isEmpty());
    }
    
    @Test
    public void testCase3_ExactFundsPurchase() {
        // SetUp: Customer "Poe" holds investment account "INV102" with $5,500 balance
        customer.addInvestmentAccount("INV102");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV102");
        account.setBalance(5500.0);
        
        // Action: Buy 100 shares of "DEF" at $50 each (total needed $5,500 exactly)
        boolean result = account.buyStock("DEF", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Purchase should succeed with exact funds", result);
        assertEquals("Balance should be reduced to zero", 0.0, account.getBalance(), 0.001);
        assertEquals("Should have one transaction recorded", 1, account.getTransactions().size());
    }
    
    @Test
    public void testCase4_LowValueSingleShare() {
        // SetUp: Customer "Poe" holds investment account "INV103" with $100 balance
        customer.addInvestmentAccount("INV103");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV103");
        account.setBalance(100.0);
        
        // Action: Buy 1 share of "GHI" at $80 (commission $8, total $88)
        boolean result = account.buyStock("GHI", 1, 80.0);
        
        // Expected Output: True
        assertTrue("Purchase should succeed for single share", result);
        assertEquals("Balance should be reduced by $88", 12.0, account.getBalance(), 0.001);
        assertEquals("Should have one transaction recorded", 1, account.getTransactions().size());
    }
    
    @Test
    public void testCase5_SecondPurchaseFailsAfterBalanceDrops() {
        // SetUp: Customer "Poe" holds investment account "INV104" with $4,000 balance
        customer.addInvestmentAccount("INV104");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV104");
        account.setBalance(4000.0);
        
        // First purchase: 50 shares of "JKL" at $40 (cost $2,000, commission $200)
        boolean firstPurchase = account.buyStock("JKL", 50, 40.0);
        assertTrue("First purchase should succeed", firstPurchase);
        assertEquals("Balance after first purchase should be $1,800", 1800.0, account.getBalance(), 0.001);
        
        // Action: Attempt second identical purchase requiring $2,200
        boolean secondPurchase = account.buyStock("JKL", 50, 40.0);
        
        // Expected Output: False
        assertFalse("Second purchase should fail due to insufficient funds", secondPurchase);
        assertEquals("Balance should remain at $1,800", 1800.0, account.getBalance(), 0.001);
        assertEquals("Should have only one transaction recorded", 1, account.getTransactions().size());
    }
}