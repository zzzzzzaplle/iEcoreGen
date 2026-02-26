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
        // SetUp: Customer "Poe" (address "0814 Center St") holds a investment account "INV100"
        // Investment account "INV100" balance $10 000
        customer.addInvestmentAccount("INV100");
        investmentAccount = (InvestmentAccount) customer.findAccountById("INV100");
        investmentAccount.setBalance(10000.0);
        
        // Action: Buy 100 shares of "ABC" at $50 each (cost $5 000, commission $500)
        boolean result = investmentAccount.buyStock("ABC", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Should successfully buy stocks with sufficient balance", result);
        
        // Verify balance was updated correctly: $10,000 - ($5,000 + $500) = $4,500
        assertEquals(4500.0, investmentAccount.getBalance(), 0.001);
        
        // Verify transaction was recorded
        List<StockTransaction> transactions = investmentAccount.getTransactions();
        assertEquals(1, transactions.size());
        StockTransaction transaction = transactions.get(0);
        assertEquals("ABC", transaction.getStock());
        assertEquals(100, transaction.getQuantity());
        assertEquals(50.0, transaction.getPrice(), 0.001);
        assertEquals(500.0, transaction.getCommission(), 0.001);
    }
    
    @Test
    public void testCase2_InsufficientFunds() {
        // SetUp: Investment account "INV101" balance $5 000
        customer.addInvestmentAccount("INV101");
        investmentAccount = (InvestmentAccount) customer.findAccountById("INV101");
        investmentAccount.setBalance(5000.0);
        
        // Action: Attempt to buy 200 shares of "XYZ" at $30 each (commission $600, total needed $6 600)
        boolean result = investmentAccount.buyStock("XYZ", 200, 30.0);
        
        // Expected Output: False
        assertFalse("Should fail to buy stocks with insufficient balance", result);
        
        // Verify balance remains unchanged
        assertEquals(5000.0, investmentAccount.getBalance(), 0.001);
        
        // Verify no transaction was recorded
        assertEquals(0, investmentAccount.getTransactions().size());
    }
    
    @Test
    public void testCase3_ExactFundsPurchase() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a investment account "INV102"
        // Investment account "INV102" balance $5 500
        customer.addInvestmentAccount("INV102");
        investmentAccount = (InvestmentAccount) customer.findAccountById("INV102");
        investmentAccount.setBalance(5500.0);
        
        // Action: Buy 100 shares of "DEF" at $50 each (commission $500, total needed $5 500 exactly)
        boolean result = investmentAccount.buyStock("DEF", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Should successfully buy stocks with exact balance", result);
        
        // Verify balance was updated to zero
        assertEquals(0.0, investmentAccount.getBalance(), 0.001);
        
        // Verify transaction was recorded
        List<StockTransaction> transactions = investmentAccount.getTransactions();
        assertEquals(1, transactions.size());
        StockTransaction transaction = transactions.get(0);
        assertEquals("DEF", transaction.getStock());
        assertEquals(100, transaction.getQuantity());
        assertEquals(50.0, transaction.getPrice(), 0.001);
        assertEquals(500.0, transaction.getCommission(), 0.001);
    }
    
    @Test
    public void testCase4_LowValueSingleShare() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a investment account "INV103"
        // Investment account "INV103" balance $100
        customer.addInvestmentAccount("INV103");
        investmentAccount = (InvestmentAccount) customer.findAccountById("INV103");
        investmentAccount.setBalance(100.0);
        
        // Action: Buy 1 share of "GHI" at $80 (commission $8, total $88)
        boolean result = investmentAccount.buyStock("GHI", 1, 80.0);
        
        // Expected Output: True
        assertTrue("Should successfully buy single low-value share", result);
        
        // Verify balance was updated correctly: $100 - ($80 + $8) = $12
        assertEquals(12.0, investmentAccount.getBalance(), 0.001);
        
        // Verify transaction was recorded
        List<StockTransaction> transactions = investmentAccount.getTransactions();
        assertEquals(1, transactions.size());
        StockTransaction transaction = transactions.get(0);
        assertEquals("GHI", transaction.getStock());
        assertEquals(1, transaction.getQuantity());
        assertEquals(80.0, transaction.getPrice(), 0.001);
        assertEquals(8.0, transaction.getCommission(), 0.001);
    }
    
    @Test
    public void testCase5_SecondPurchaseFailsAfterBalanceDrops() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a investment account "INV104"
        // Investment account "INV104" balance $4 000
        customer.addInvestmentAccount("INV104");
        investmentAccount = (InvestmentAccount) customer.findAccountById("INV104");
        investmentAccount.setBalance(4000.0);
        
        // First purchase already completed: 50 shares of "JKL" at $40 (cost $2 000, commission $200, remaining balance $1 800)
        boolean firstPurchase = investmentAccount.buyStock("JKL", 50, 40.0);
        assertTrue("First purchase should succeed", firstPurchase);
        assertEquals(1800.0, investmentAccount.getBalance(), 0.001);
        
        // Action: Attempt a second identical purchase requiring $2 200
        boolean secondPurchase = investmentAccount.buyStock("JKL", 50, 40.0);
        
        // Expected Output: False
        assertFalse("Second purchase should fail due to insufficient balance", secondPurchase);
        
        // Verify balance remains unchanged at $1,800
        assertEquals(1800.0, investmentAccount.getBalance(), 0.001);
        
        // Verify only one transaction was recorded (the successful first purchase)
        assertEquals(1, investmentAccount.getTransactions().size());
    }
}