import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

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
        customer.addInvestmentAccount("INV100");
        investmentAccount = (InvestmentAccount) customer.findAccountById("INV100");
        investmentAccount.setBalance(10000.0);
        
        // Action: Buy 100 shares of "ABC" at $50 each (cost $5,000, commission $500)
        boolean result = investmentAccount.buyStock("ABC", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Should return true for sufficient balance purchase", result);
        assertEquals("Balance should be reduced by total cost and commission", 
                     10000.0 - (100 * 50.0) - (100 * 50.0 * 0.1), investmentAccount.getBalance(), 0.001);
        assertEquals("Transaction should be recorded", 1, investmentAccount.getTransactions().size());
    }
    
    @Test
    public void testCase2_InsufficientFunds() {
        // SetUp: Investment account "INV101" balance $5,000
        customer.addInvestmentAccount("INV101");
        investmentAccount = (InvestmentAccount) customer.findAccountById("INV101");
        investmentAccount.setBalance(5000.0);
        
        // Action: Attempt to buy 200 shares of "XYZ" at $30 each (commission $600, total needed $6,600)
        boolean result = investmentAccount.buyStock("XYZ", 200, 30.0);
        
        // Expected Output: False
        assertFalse("Should return false for insufficient funds", result);
        assertEquals("Balance should remain unchanged", 5000.0, investmentAccount.getBalance(), 0.001);
        assertTrue("No transaction should be recorded", investmentAccount.getTransactions().isEmpty());
    }
    
    @Test
    public void testCase3_ExactFundsPurchase() {
        // SetUp: Investment account "INV102" balance $5,500
        customer.addInvestmentAccount("INV102");
        investmentAccount = (InvestmentAccount) customer.findAccountById("INV102");
        investmentAccount.setBalance(5500.0);
        
        // Action: Buy 100 shares of "DEF" at $50 each (commission $500, total needed $5,500 exactly)
        boolean result = investmentAccount.buyStock("DEF", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Should return true for exact funds purchase", result);
        assertEquals("Balance should be exactly zero", 0.0, investmentAccount.getBalance(), 0.001);
        assertEquals("Transaction should be recorded", 1, investmentAccount.getTransactions().size());
    }
    
    @Test
    public void testCase4_LowValueSingleShare() {
        // SetUp: Investment account "INV103" balance $100
        customer.addInvestmentAccount("INV103");
        investmentAccount = (InvestmentAccount) customer.findAccountById("INV103");
        investmentAccount.setBalance(100.0);
        
        // Action: Buy 1 share of "GHI" at $80 (commission $8, total $88)
        boolean result = investmentAccount.buyStock("GHI", 1, 80.0);
        
        // Expected Output: True
        assertTrue("Should return true for low-value single share purchase", result);
        assertEquals("Balance should be reduced by total cost and commission", 
                     100.0 - (1 * 80.0) - (1 * 80.0 * 0.1), investmentAccount.getBalance(), 0.001);
        assertEquals("Transaction should be recorded", 1, investmentAccount.getTransactions().size());
    }
    
    @Test
    public void testCase5_SecondPurchaseFailsAfterBalanceDrops() {
        // SetUp: Investment account "INV104" balance $4,000
        customer.addInvestmentAccount("INV104");
        investmentAccount = (InvestmentAccount) customer.findAccountById("INV104");
        investmentAccount.setBalance(4000.0);
        
        // First purchase: 50 shares of "JKL" at $40 (cost $2,000, commission $200, remaining balance $1,800)
        boolean firstPurchase = investmentAccount.buyStock("JKL", 50, 40.0);
        assertTrue("First purchase should succeed", firstPurchase);
        assertEquals("Balance after first purchase should be $1,800", 1800.0, investmentAccount.getBalance(), 0.001);
        
        // Action: Attempt a second identical purchase requiring $2,200
        boolean secondPurchase = investmentAccount.buyStock("JKL", 50, 40.0);
        
        // Expected Output: False
        assertFalse("Should return false for second purchase with insufficient funds", secondPurchase);
        assertEquals("Balance should remain unchanged after failed purchase", 1800.0, investmentAccount.getBalance(), 0.001);
        assertEquals("Only one transaction should be recorded", 1, investmentAccount.getTransactions().size());
    }
}