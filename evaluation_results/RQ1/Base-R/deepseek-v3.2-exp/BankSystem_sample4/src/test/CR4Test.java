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
        // Set up investment account with $10,000 balance
        investmentAccount = new InvestmentAccount();
        investmentAccount.setAccountId("INV100");
        investmentAccount.setBalance(new BigDecimal("10000"));
        customer.addAccount(investmentAccount);
        
        // Buy 100 shares of "ABC" at $50 each
        boolean result = investmentAccount.buyStocks("ABC", 100, new BigDecimal("50"));
        
        // Verify transaction was successful
        assertTrue("Should return true for sufficient balance purchase", result);
        // Verify balance was updated correctly: $10,000 - ($5,000 + $500) = $4,500
        assertEquals(new BigDecimal("4500"), investmentAccount.getBalance());
        // Verify transaction record was saved
        assertEquals(1, investmentAccount.getStockTransactions().size());
    }
    
    @Test
    public void testCase2_InsufficientFunds() {
        // Set up investment account with $5,000 balance
        investmentAccount = new InvestmentAccount();
        investmentAccount.setAccountId("INV101");
        investmentAccount.setBalance(new BigDecimal("5000"));
        
        // Attempt to buy 200 shares of "XYZ" at $30 each (requires $6,600)
        boolean result = investmentAccount.buyStocks("XYZ", 200, new BigDecimal("30"));
        
        // Verify transaction failed due to insufficient funds
        assertFalse("Should return false for insufficient funds", result);
        // Verify balance remains unchanged
        assertEquals(new BigDecimal("5000"), investmentAccount.getBalance());
        // Verify no transaction record was saved
        assertTrue(investmentAccount.getStockTransactions().isEmpty());
    }
    
    @Test
    public void testCase3_ExactFundsPurchase() {
        // Set up investment account with exactly $5,500 balance
        investmentAccount = new InvestmentAccount();
        investmentAccount.setAccountId("INV102");
        investmentAccount.setBalance(new BigDecimal("5500"));
        customer.addAccount(investmentAccount);
        
        // Buy 100 shares of "DEF" at $50 each (exactly $5,500 total)
        boolean result = investmentAccount.buyStocks("DEF", 100, new BigDecimal("50"));
        
        // Verify transaction was successful with exact funds
        assertTrue("Should return true for exact funds purchase", result);
        // Verify balance is now zero
        assertEquals(BigDecimal.ZERO, investmentAccount.getBalance());
        // Verify transaction record was saved
        assertEquals(1, investmentAccount.getStockTransactions().size());
    }
    
    @Test
    public void testCase4_LowValueSingleShare() {
        // Set up investment account with $100 balance
        investmentAccount = new InvestmentAccount();
        investmentAccount.setAccountId("INV103");
        investmentAccount.setBalance(new BigDecimal("100"));
        customer.addAccount(investmentAccount);
        
        // Buy 1 share of "GHI" at $80
        boolean result = investmentAccount.buyStocks("GHI", 1, new BigDecimal("80"));
        
        // Verify transaction was successful
        assertTrue("Should return true for low-value single share purchase", result);
        // Verify balance was updated: $100 - ($80 + $8) = $12
        assertEquals(new BigDecimal("12"), investmentAccount.getBalance());
        // Verify transaction record was saved
        assertEquals(1, investmentAccount.getStockTransactions().size());
    }
    
    @Test
    public void testCase5_SecondPurchaseFailsAfterBalanceDrops() {
        // Set up investment account with $4,000 balance
        investmentAccount = new InvestmentAccount();
        investmentAccount.setAccountId("INV104");
        investmentAccount.setBalance(new BigDecimal("4000"));
        customer.addAccount(investmentAccount);
        
        // First purchase: 50 shares of "JKL" at $40 each
        boolean firstPurchase = investmentAccount.buyStocks("JKL", 50, new BigDecimal("40"));
        assertTrue("First purchase should succeed", firstPurchase);
        
        // Second identical purchase requiring $2,200 (but only $1,800 remaining)
        boolean secondPurchase = investmentAccount.buyStocks("JKL", 50, new BigDecimal("40"));
        
        // Verify second purchase fails due to insufficient funds
        assertFalse("Should return false for second purchase with insufficient funds", secondPurchase);
        // Verify balance remains at $1,800 after first purchase
        assertEquals(new BigDecimal("1800"), investmentAccount.getBalance());
        // Verify only one transaction record exists (only first purchase succeeded)
        assertEquals(1, investmentAccount.getStockTransactions().size());
    }
}