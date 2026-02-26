import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

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
        account.setBalance(10000);
        customer.getAccounts().add(account);
        
        // Action: Buy 100 shares of "ABC" at $50 each (cost $5,000, commission $500)
        boolean result = account.buyStock("ABC", 100, 50);
        
        // Expected Output: True
        assertTrue(result);
        
        // Verify balance was updated correctly: 10000 - 5000 - 500 = 4500
        assertEquals(4500, account.getBalance(), 0.01);
        
        // Verify transaction was recorded
        assertEquals(1, account.getTransactions().size());
        StockTransaction transaction = account.getTransactions().get(0);
        assertEquals("ABC", transaction.getStock());
        assertEquals(100, transaction.getQuantity());
        assertEquals(50, transaction.getPrice(), 0.01);
        assertEquals(500, transaction.getCommission(), 0.01);
    }
    
    @Test
    public void testCase2_insufficientFunds() {
        // SetUp: Investment account "INV101" balance $5,000
        account = new InvestmentAccount();
        account.setId("INV101");
        account.setBalance(5000);
        
        // Action: Attempt to buy 200 shares of "XYZ" at $30 each (commission $600, total needed $6,600)
        boolean result = account.buyStock("XYZ", 200, 30);
        
        // Expected Output: False
        assertFalse(result);
        
        // Verify balance remains unchanged
        assertEquals(5000, account.getBalance(), 0.01);
        
        // Verify no transaction was recorded
        assertEquals(0, account.getTransactions().size());
    }
    
    @Test
    public void testCase3_exactFundsPurchase() {
        // SetUp: Customer "Poe" holds an investment account "INV102" with $5,500 balance
        account = new InvestmentAccount();
        account.setId("INV102");
        account.setBalance(5500);
        customer.getAccounts().add(account);
        
        // Action: Buy 100 shares of "DEF" at $50 each (commission $500, total needed $5,500 exactly)
        boolean result = account.buyStock("DEF", 100, 50);
        
        // Expected Output: True
        assertTrue(result);
        
        // Verify balance was updated to zero: 5500 - 5000 - 500 = 0
        assertEquals(0, account.getBalance(), 0.01);
        
        // Verify transaction was recorded
        assertEquals(1, account.getTransactions().size());
        StockTransaction transaction = account.getTransactions().get(0);
        assertEquals("DEF", transaction.getStock());
        assertEquals(100, transaction.getQuantity());
        assertEquals(50, transaction.getPrice(), 0.01);
        assertEquals(500, transaction.getCommission(), 0.01);
    }
    
    @Test
    public void testCase4_lowValueSingleShare() {
        // SetUp: Customer "Poe" holds an investment account "INV103" with $100 balance
        account = new InvestmentAccount();
        account.setId("INV103");
        account.setBalance(100);
        customer.getAccounts().add(account);
        
        // Action: Buy 1 share of "GHI" at $80 (commission $8, total $88)
        boolean result = account.buyStock("GHI", 1, 80);
        
        // Expected Output: True
        assertTrue(result);
        
        // Verify balance was updated correctly: 100 - 80 - 8 = 12
        assertEquals(12, account.getBalance(), 0.01);
        
        // Verify transaction was recorded
        assertEquals(1, account.getTransactions().size());
        StockTransaction transaction = account.getTransactions().get(0);
        assertEquals("GHI", transaction.getStock());
        assertEquals(1, transaction.getQuantity());
        assertEquals(80, transaction.getPrice(), 0.01);
        assertEquals(8, transaction.getCommission(), 0.01);
    }
    
    @Test
    public void testCase5_secondPurchaseFailsAfterBalanceDrops() {
        // SetUp: Customer "Poe" holds an investment account "INV104" with $4,000 balance
        account = new InvestmentAccount();
        account.setId("INV104");
        account.setBalance(4000);
        customer.getAccounts().add(account);
        
        // First purchase: 50 shares of "JKL" at $40 (cost $2,000, commission $200)
        boolean firstPurchaseResult = account.buyStock("JKL", 50, 40);
        assertTrue(firstPurchaseResult);
        
        // Verify balance after first purchase: 4000 - 2000 - 200 = 1800
        assertEquals(1800, account.getBalance(), 0.01);
        
        // Action: Attempt a second identical purchase requiring $2,200 (cost $2,000 + commission $200)
        boolean secondPurchaseResult = account.buyStock("JKL", 50, 40);
        
        // Expected Output: False
        assertFalse(secondPurchaseResult);
        
        // Verify balance remains at 1800
        assertEquals(1800, account.getBalance(), 0.01);
        
        // Verify only one transaction was recorded
        assertEquals(1, account.getTransactions().size());
    }
}