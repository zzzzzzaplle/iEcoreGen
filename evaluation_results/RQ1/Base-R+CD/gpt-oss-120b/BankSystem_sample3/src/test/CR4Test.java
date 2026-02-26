import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    private Customer customer;
    private InvestmentAccount account;
    
    @Before
    public void setUp() {
        customer = new Customer();
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
    }
    
    @Test
    public void testCase1_sufficientBalancePurchase() {
        // SetUp: Customer "Poe" holds an investment account "INV100" with $10,000 balance
        account = new InvestmentAccount();
        account.setId("INV100");
        account.setBalance(10000.0);
        customer.getAccounts().add(account);
        
        // Action: Buy 100 shares of "ABC" at $50 each (cost $5,000, commission $500)
        boolean result = account.buyStock("ABC", 100, 50.0);
        
        // Expected Output: True
        assertTrue(result);
    }
    
    @Test
    public void testCase2_insufficientFunds() {
        // SetUp: Investment account "INV101" balance $5,000
        account = new InvestmentAccount();
        account.setId("INV101");
        account.setBalance(5000.0);
        customer.getAccounts().add(account);
        
        // Action: Attempt to buy 200 shares of "XYZ" at $30 each (commission $600, total needed $6,600)
        boolean result = account.buyStock("XYZ", 200, 30.0);
        
        // Expected Output: False
        assertFalse(result);
    }
    
    @Test
    public void testCase3_exactFundsPurchase() {
        // SetUp: Customer "Poe" holds an investment account "INV102" with $5,500 balance
        account = new InvestmentAccount();
        account.setId("INV102");
        account.setBalance(5500.0);
        customer.getAccounts().add(account);
        
        // Action: Buy 100 shares of "DEF" at $50 each (commission $500, total needed $5,500 exactly)
        boolean result = account.buyStock("DEF", 100, 50.0);
        
        // Expected Output: True
        assertTrue(result);
    }
    
    @Test
    public void testCase4_lowValueSingleShare() {
        // SetUp: Customer "Poe" holds an investment account "INV103" with $100 balance
        account = new InvestmentAccount();
        account.setId("INV103");
        account.setBalance(100.0);
        customer.getAccounts().add(account);
        
        // Action: Buy 1 share of "GHI" at $80 (commission $8, total $88)
        boolean result = account.buyStock("GHI", 1, 80.0);
        
        // Expected Output: True
        assertTrue(result);
    }
    
    @Test
    public void testCase5_secondPurchaseFailsAfterBalanceDrops() {
        // SetUp: Customer "Poe" holds an investment account "INV104" with $4,000 balance
        account = new InvestmentAccount();
        account.setId("INV104");
        account.setBalance(4000.0);
        customer.getAccounts().add(account);
        
        // First purchase: 50 shares of "JKL" at $40 (cost $2,000, commission $200, remaining balance $1,800)
        boolean firstPurchase = account.buyStock("JKL", 50, 40.0);
        assertTrue(firstPurchase);
        
        // Action: Attempt a second identical purchase requiring $2,200
        boolean result = account.buyStock("JKL", 50, 40.0);
        
        // Expected Output: False
        assertFalse(result);
    }
}