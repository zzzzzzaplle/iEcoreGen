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
    }
    
    @Test
    public void testCase1_sufficientBalancePurchase() {
        // SetUp: Customer "Poe" holds an investment account "INV100" with $10,000 balance
        investmentAccount = new InvestmentAccount();
        investmentAccount.setId("INV100");
        investmentAccount.setBalance(10000.0);
        customer.getAccounts().add(investmentAccount);
        
        // Action: Buy 100 shares of "ABC" at $50 each (cost $5,000, commission $500)
        boolean result = investmentAccount.buyStock("ABC", 100, 50.0);
        
        // Expected Output: True
        assertTrue(result);
        
        // Verify balance was updated correctly: 10000 - 5000 - 500 = 4500
        assertEquals(4500.0, investmentAccount.getBalance(), 0.01);
        
        // Verify transaction was recorded
        assertEquals(1, investmentAccount.getTransactions().size());
        StockTransaction transaction = investmentAccount.getTransactions().get(0);
        assertEquals("ABC", transaction.getStock());
        assertEquals(100, transaction.getQuantity());
        assertEquals(50.0, transaction.getPrice(), 0.01);
        assertEquals(500.0, transaction.getCommission(), 0.01);
    }
    
    @Test
    public void testCase2_insufficientFunds() {
        // SetUp: Investment account "INV101" balance $5,000
        investmentAccount = new InvestmentAccount();
        investmentAccount.setId("INV101");
        investmentAccount.setBalance(5000.0);
        customer.getAccounts().add(investmentAccount);
        
        // Action: Attempt to buy 200 shares of "XYZ" at $30 each (commission $600, total needed $6,600)
        boolean result = investmentAccount.buyStock("XYZ", 200, 30.0);
        
        // Expected Output: False
        assertFalse(result);
        
        // Verify balance remains unchanged
        assertEquals(5000.0, investmentAccount.getBalance(), 0.01);
        
        // Verify no transaction was recorded
        assertEquals(0, investmentAccount.getTransactions().size());
    }
    
    @Test
    public void testCase3_exactFundsPurchase() {
        // SetUp: Customer "Poe" holds an investment account "INV102" with $5,500 balance
        investmentAccount = new InvestmentAccount();
        investmentAccount.setId("INV102");
        investmentAccount.setBalance(5500.0);
        customer.getAccounts().add(investmentAccount);
        
        // Action: Buy 100 shares of "DEF" at $50 each (commission $500, total needed $5,500 exactly)
        boolean result = investmentAccount.buyStock("DEF", 100, 50.0);
        
        // Expected Output: True
        assertTrue(result);
        
        // Verify balance was updated to zero
        assertEquals(0.0, investmentAccount.getBalance(), 0.01);
        
        // Verify transaction was recorded
        assertEquals(1, investmentAccount.getTransactions().size());
        StockTransaction transaction = investmentAccount.getTransactions().get(0);
        assertEquals("DEF", transaction.getStock());
        assertEquals(100, transaction.getQuantity());
        assertEquals(50.0, transaction.getPrice(), 0.01);
        assertEquals(500.0, transaction.getCommission(), 0.01);
    }
    
    @Test
    public void testCase4_lowValueSingleShare() {
        // SetUp: Customer "Poe" holds an investment account "INV103" with $100 balance
        investmentAccount = new InvestmentAccount();
        investmentAccount.setId("INV103");
        investmentAccount.setBalance(100.0);
        customer.getAccounts().add(investmentAccount);
        
        // Action: Buy 1 share of "GHI" at $80 (commission $8, total $88)
        boolean result = investmentAccount.buyStock("GHI", 1, 80.0);
        
        // Expected Output: True
        assertTrue(result);
        
        // Verify balance was updated correctly: 100 - 80 - 8 = 12
        assertEquals(12.0, investmentAccount.getBalance(), 0.01);
        
        // Verify transaction was recorded
        assertEquals(1, investmentAccount.getTransactions().size());
        StockTransaction transaction = investmentAccount.getTransactions().get(0);
        assertEquals("GHI", transaction.getStock());
        assertEquals(1, transaction.getQuantity());
        assertEquals(80.0, transaction.getPrice(), 0.01);
        assertEquals(8.0, transaction.getCommission(), 0.01);
    }
    
    @Test
    public void testCase5_secondPurchaseFailsAfterBalanceDrops() {
        // SetUp: Customer "Poe" holds an investment account "INV104" with $4,000 balance
        investmentAccount = new InvestmentAccount();
        investmentAccount.setId("INV104");
        investmentAccount.setBalance(4000.0);
        customer.getAccounts().add(investmentAccount);
        
        // First purchase: 50 shares of "JKL" at $40 (cost $2,000, commission $200, remaining balance $1,800)
        boolean firstPurchaseResult = investmentAccount.buyStock("JKL", 50, 40.0);
        assertTrue(firstPurchaseResult);
        assertEquals(1800.0, investmentAccount.getBalance(), 0.01);
        assertEquals(1, investmentAccount.getTransactions().size());
        
        // Action: Attempt a second identical purchase requiring $2,200
        boolean secondPurchaseResult = investmentAccount.buyStock("JKL", 50, 40.0);
        
        // Expected Output: False
        assertFalse(secondPurchaseResult);
        
        // Verify balance remains at $1,800
        assertEquals(1800.0, investmentAccount.getBalance(), 0.01);
        
        // Verify only the first transaction is recorded
        assertEquals(1, investmentAccount.getTransactions().size());
    }
}