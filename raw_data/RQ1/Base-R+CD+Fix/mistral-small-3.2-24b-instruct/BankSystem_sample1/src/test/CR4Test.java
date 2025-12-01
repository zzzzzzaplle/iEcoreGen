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
        // SetUp: Customer "Poe" holds investment account "INV100" with $10,000 balance
        investmentAccount = new InvestmentAccount();
        investmentAccount.setId("INV100");
        investmentAccount.setBalance(10000.0);
        customer.getAccounts().add(investmentAccount);
        
        // Action: Buy 100 shares of "ABC" at $50 each (cost $5,000, commission $500)
        boolean result = investmentAccount.buyStock("ABC", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Purchase should succeed with sufficient balance", result);
        
        // Verify transaction was saved
        assertEquals("Should have one transaction", 1, investmentAccount.getTransactions().size());
        assertEquals("Balance should be reduced by total amount", 4500.0, investmentAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_InsufficientFunds() {
        // SetUp: Investment account "INV101" with $5,000 balance
        investmentAccount = new InvestmentAccount();
        investmentAccount.setId("INV101");
        investmentAccount.setBalance(5000.0);
        
        // Action: Attempt to buy 200 shares of "XYZ" at $30 each (total needed $6,600)
        try {
            investmentAccount.buyStock("XYZ", 200, 30.0);
            fail("Should have thrown IllegalArgumentException for insufficient funds");
        } catch (IllegalArgumentException e) {
            // Expected behavior - insufficient funds
            assertEquals("Insufficient balance to cover the stock cost and commission.", e.getMessage());
        }
        
        // Verify no transaction was saved and balance remains unchanged
        assertEquals("Should have no transactions", 0, investmentAccount.getTransactions().size());
        assertEquals("Balance should remain unchanged", 5000.0, investmentAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_ExactFundsPurchase() {
        // SetUp: Customer "Poe" holds investment account "INV102" with $5,500 balance
        investmentAccount = new InvestmentAccount();
        investmentAccount.setId("INV102");
        investmentAccount.setBalance(5500.0);
        customer.getAccounts().add(investmentAccount);
        
        // Action: Buy 100 shares of "DEF" at $50 each (total needed $5,500 exactly)
        boolean result = investmentAccount.buyStock("DEF", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Purchase should succeed with exact funds", result);
        
        // Verify transaction was saved and balance is zero
        assertEquals("Should have one transaction", 1, investmentAccount.getTransactions().size());
        assertEquals("Balance should be zero after exact purchase", 0.0, investmentAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_LowValueSingleShare() {
        // SetUp: Customer "Poe" holds investment account "INV103" with $100 balance
        investmentAccount = new InvestmentAccount();
        investmentAccount.setId("INV103");
        investmentAccount.setBalance(100.0);
        customer.getAccounts().add(investmentAccount);
        
        // Action: Buy 1 share of "GHI" at $80 (commission $8, total $88)
        boolean result = investmentAccount.buyStock("GHI", 1, 80.0);
        
        // Expected Output: True
        assertTrue("Purchase should succeed for low-value single share", result);
        
        // Verify transaction was saved and balance updated
        assertEquals("Should have one transaction", 1, investmentAccount.getTransactions().size());
        assertEquals("Balance should be reduced by total amount", 12.0, investmentAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_SecondPurchaseFailsAfterBalanceDrops() {
        // SetUp: Customer "Poe" holds investment account "INV104" with $4,000 balance
        investmentAccount = new InvestmentAccount();
        investmentAccount.setId("INV104");
        investmentAccount.setBalance(4000.0);
        customer.getAccounts().add(investmentAccount);
        
        // First purchase: 50 shares of "JKL" at $40 (cost $2,000, commission $200)
        boolean firstPurchase = investmentAccount.buyStock("JKL", 50, 40.0);
        assertTrue("First purchase should succeed", firstPurchase);
        assertEquals("Remaining balance should be $1,800", 1800.0, investmentAccount.getBalance(), 0.001);
        
        // Action: Attempt second identical purchase requiring $2,200
        try {
            investmentAccount.buyStock("JKL", 50, 40.0);
            fail("Should have thrown IllegalArgumentException for insufficient funds on second purchase");
        } catch (IllegalArgumentException e) {
            // Expected behavior - insufficient funds for second purchase
            assertEquals("Insufficient balance to cover the stock cost and commission.", e.getMessage());
        }
        
        // Verify only one transaction exists and balance remains at $1,800
        assertEquals("Should have only one transaction", 1, investmentAccount.getTransactions().size());
        assertEquals("Balance should remain at $1,800", 1800.0, investmentAccount.getBalance(), 0.001);
    }
}