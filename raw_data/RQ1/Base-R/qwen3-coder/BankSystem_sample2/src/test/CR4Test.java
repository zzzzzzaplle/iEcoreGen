import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.math.BigDecimal;

public class CR4Test {
    private InvestmentAccount investmentAccount;
    private Customer customer;
    
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
        investmentAccount.setAccountId("INV100");
        investmentAccount.setBalance(new BigDecimal("10000"));
        customer.addAccount(investmentAccount);
        
        // Action: Buy 100 shares of "ABC" at $50 each
        boolean result = investmentAccount.buyStocks("ABC", 100, new BigDecimal("50"));
        
        // Expected Output: True
        assertTrue("Purchase should succeed with sufficient balance", result);
        
        // Verify balance was updated correctly (10000 - (5000 + 500) = 4500)
        assertEquals(new BigDecimal("4500"), investmentAccount.getBalance());
        
        // Verify transaction record was saved
        assertEquals(1, investmentAccount.getStockTransactions().size());
        StockTransaction transaction = investmentAccount.getStockTransactions().get(0);
        assertEquals("ABC", transaction.getTicker());
        assertEquals(100, transaction.getQuantity());
        assertEquals(new BigDecimal("50"), transaction.getPurchasePrice());
        assertEquals(new BigDecimal("500"), transaction.getCommission());
    }
    
    @Test
    public void testCase2_InsufficientFunds() {
        // SetUp: Investment account "INV101" balance $5,000
        investmentAccount = new InvestmentAccount();
        investmentAccount.setAccountId("INV101");
        investmentAccount.setBalance(new BigDecimal("5000"));
        
        // Action: Attempt to buy 200 shares of "XYZ" at $30 each
        boolean result = investmentAccount.buyStocks("XYZ", 200, new BigDecimal("30"));
        
        // Expected Output: False
        assertFalse("Purchase should fail with insufficient funds", result);
        
        // Verify balance remains unchanged
        assertEquals(new BigDecimal("5000"), investmentAccount.getBalance());
        
        // Verify no transaction record was saved
        assertTrue("No transaction should be recorded for failed purchase", 
                  investmentAccount.getStockTransactions().isEmpty());
    }
    
    @Test
    public void testCase3_ExactFundsPurchase() {
        // SetUp: Investment account "INV102" balance $5,500
        investmentAccount = new InvestmentAccount();
        investmentAccount.setAccountId("INV102");
        investmentAccount.setBalance(new BigDecimal("5500"));
        customer.addAccount(investmentAccount);
        
        // Action: Buy 100 shares of "DEF" at $50 each
        boolean result = investmentAccount.buyStocks("DEF", 100, new BigDecimal("50"));
        
        // Expected Output: True
        assertTrue("Purchase should succeed with exact funds", result);
        
        // Verify balance was updated correctly (5500 - (5000 + 500) = 0)
        assertEquals(BigDecimal.ZERO, investmentAccount.getBalance());
        
        // Verify transaction record was saved
        assertEquals(1, investmentAccount.getStockTransactions().size());
        StockTransaction transaction = investmentAccount.getStockTransactions().get(0);
        assertEquals("DEF", transaction.getTicker());
        assertEquals(100, transaction.getQuantity());
        assertEquals(new BigDecimal("50"), transaction.getPurchasePrice());
        assertEquals(new BigDecimal("500"), transaction.getCommission());
    }
    
    @Test
    public void testCase4_LowValueSingleShare() {
        // SetUp: Investment account "INV103" balance $100
        investmentAccount = new InvestmentAccount();
        investmentAccount.setAccountId("INV103");
        investmentAccount.setBalance(new BigDecimal("100"));
        customer.addAccount(investmentAccount);
        
        // Action: Buy 1 share of "GHI" at $80
        boolean result = investmentAccount.buyStocks("GHI", 1, new BigDecimal("80"));
        
        // Expected Output: True
        assertTrue("Purchase should succeed for low-value single share", result);
        
        // Verify balance was updated correctly (100 - (80 + 8) = 12)
        assertEquals(new BigDecimal("12"), investmentAccount.getBalance());
        
        // Verify transaction record was saved
        assertEquals(1, investmentAccount.getStockTransactions().size());
        StockTransaction transaction = investmentAccount.getStockTransactions().get(0);
        assertEquals("GHI", transaction.getTicker());
        assertEquals(1, transaction.getQuantity());
        assertEquals(new BigDecimal("80"), transaction.getPurchasePrice());
        assertEquals(new BigDecimal("8"), transaction.getCommission());
    }
    
    @Test
    public void testCase5_SecondPurchaseFailsAfterBalanceDrops() {
        // SetUp: Investment account "INV104" balance $4,000
        investmentAccount = new InvestmentAccount();
        investmentAccount.setAccountId("INV104");
        investmentAccount.setBalance(new BigDecimal("4000"));
        customer.addAccount(investmentAccount);
        
        // First purchase: 50 shares of "JKL" at $40
        boolean firstPurchase = investmentAccount.buyStocks("JKL", 50, new BigDecimal("40"));
        assertTrue("First purchase should succeed", firstPurchase);
        
        // Verify balance after first purchase (4000 - (2000 + 200) = 1800)
        assertEquals(new BigDecimal("1800"), investmentAccount.getBalance());
        
        // Action: Attempt second identical purchase requiring $2,200
        boolean secondPurchase = investmentAccount.buyStocks("JKL", 50, new BigDecimal("40"));
        
        // Expected Output: False
        assertFalse("Second purchase should fail due to insufficient balance", secondPurchase);
        
        // Verify balance remains unchanged after failed second purchase
        assertEquals(new BigDecimal("1800"), investmentAccount.getBalance());
        
        // Verify only one transaction record exists
        assertEquals(1, investmentAccount.getStockTransactions().size());
    }
}