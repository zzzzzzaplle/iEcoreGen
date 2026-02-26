import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    private Customer customer;
    
    @Before
    public void setUp() {
        customer = new Customer();
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
    }
    
    @Test
    public void testCase1_SufficientBalancePurchase() {
        // Setup
        customer.addInvestmentAccount("INV100");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV100");
        account.deposit(10000);
        
        // Action: Buy 100 shares of "ABC" at $50 each
        boolean result = account.buyStock("ABC", 100, 50);
        
        // Verify transaction was successful
        assertTrue("Should successfully buy stocks with sufficient balance", result);
        // Verify balance was updated correctly (10000 - (5000 + 500) = 4500)
        assertEquals("Balance should be reduced by total cost + commission", 4500.0, account.getBalance(), 0.001);
        // Verify transaction record was saved
        assertEquals("Should have one transaction record", 1, account.getTransactions().size());
        StockTransaction transaction = account.getTransactions().get(0);
        assertEquals("Stock symbol should match", "ABC", transaction.getStock());
        assertEquals("Quantity should match", 100, transaction.getQuantity());
        assertEquals("Price should match", 50.0, transaction.getPrice(), 0.001);
        assertEquals("Commission should be 10% of 5000", 500.0, transaction.getCommission(), 0.001);
    }
    
    @Test
    public void testCase2_InsufficientFunds() {
        // Setup
        customer.addInvestmentAccount("INV101");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV101");
        account.deposit(5000);
        
        // Action: Attempt to buy 200 shares of "XYZ" at $30 each
        boolean result = account.buyStock("XYZ", 200, 30);
        
        // Verify transaction failed due to insufficient funds
        assertFalse("Should fail to buy stocks with insufficient balance", result);
        // Verify balance remains unchanged
        assertEquals("Balance should remain 5000", 5000.0, account.getBalance(), 0.001);
        // Verify no transaction record was created
        assertTrue("Should have no transaction records", account.getTransactions().isEmpty());
    }
    
    @Test
    public void testCase3_ExactFundsPurchase() {
        // Setup
        customer.addInvestmentAccount("INV102");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV102");
        account.deposit(5500);
        
        // Action: Buy 100 shares of "DEF" at $50 each
        boolean result = account.buyStock("DEF", 100, 50);
        
        // Verify transaction was successful with exact funds
        assertTrue("Should successfully buy stocks with exact balance", result);
        // Verify balance was reduced to zero
        assertEquals("Balance should be zero after exact purchase", 0.0, account.getBalance(), 0.001);
        // Verify transaction record was saved
        assertEquals("Should have one transaction record", 1, account.getTransactions().size());
        StockTransaction transaction = account.getTransactions().get(0);
        assertEquals("Stock symbol should match", "DEF", transaction.getStock());
        assertEquals("Commission should be 10% of 5000", 500.0, transaction.getCommission(), 0.001);
    }
    
    @Test
    public void testCase4_LowValueSingleShare() {
        // Setup
        customer.addInvestmentAccount("INV103");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV103");
        account.deposit(100);
        
        // Action: Buy 1 share of "GHI" at $80
        boolean result = account.buyStock("GHI", 1, 80);
        
        // Verify transaction was successful
        assertTrue("Should successfully buy single share with sufficient balance", result);
        // Verify balance was updated correctly (100 - (80 + 8) = 12)
        assertEquals("Balance should be 12 after purchase", 12.0, account.getBalance(), 0.001);
        // Verify transaction record was saved
        assertEquals("Should have one transaction record", 1, account.getTransactions().size());
        StockTransaction transaction = account.getTransactions().get(0);
        assertEquals("Stock symbol should match", "GHI", transaction.getStock());
        assertEquals("Commission should be 10% of 80", 8.0, transaction.getCommission(), 0.001);
    }
    
    @Test
    public void testCase5_SecondPurchaseFailsAfterBalanceDrops() {
        // Setup
        customer.addInvestmentAccount("INV104");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV104");
        account.deposit(4000);
        
        // First purchase: 50 shares of "JKL" at $40
        boolean firstResult = account.buyStock("JKL", 50, 40);
        assertTrue("First purchase should succeed", firstResult);
        assertEquals("Balance after first purchase should be 1800", 1800.0, account.getBalance(), 0.001);
        
        // Action: Attempt second identical purchase requiring $2200
        boolean secondResult = account.buyStock("JKL", 50, 40);
        
        // Verify second transaction fails due to insufficient funds
        assertFalse("Second purchase should fail with insufficient balance", secondResult);
        // Verify balance remains unchanged from first purchase
        assertEquals("Balance should remain 1800", 1800.0, account.getBalance(), 0.001);
        // Verify only one transaction record exists
        assertEquals("Should have only one transaction record", 1, account.getTransactions().size());
    }
}