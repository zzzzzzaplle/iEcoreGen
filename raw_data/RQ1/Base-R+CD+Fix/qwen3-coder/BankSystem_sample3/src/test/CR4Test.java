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
    public void testCase1_SufficientBalancePurchase() {
        // SetUp: Customer "Poe" holds investment account "INV100" with $10,000 balance
        investmentAccount = new InvestmentAccount();
        investmentAccount.setId("INV100");
        investmentAccount.setBalance(10000.0);
        
        List<Account> accounts = new ArrayList<>();
        accounts.add(investmentAccount);
        customer.setAccounts(accounts);
        
        // Action: Buy 100 shares of "ABC" at $50 each
        boolean result = investmentAccount.buyStock("ABC", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Should successfully purchase stocks with sufficient balance", result);
        
        // Verify balance was updated correctly (10000 - (100*50 + 100*50*0.1) = 10000 - 5500 = 4500)
        assertEquals(4500.0, investmentAccount.getBalance(), 0.001);
        
        // Verify transaction was recorded
        assertEquals(1, investmentAccount.getTransactions().size());
        StockTransaction transaction = investmentAccount.getTransactions().get(0);
        assertEquals("ABC", transaction.getStock());
        assertEquals(100, transaction.getQuantity());
        assertEquals(50.0, transaction.getPrice(), 0.001);
        assertEquals(500.0, transaction.getCommission(), 0.001);
    }
    
    @Test
    public void testCase2_InsufficientFunds() {
        // SetUp: Investment account "INV101" with $5,000 balance
        investmentAccount = new InvestmentAccount();
        investmentAccount.setId("INV101");
        investmentAccount.setBalance(5000.0);
        
        List<Account> accounts = new ArrayList<>();
        accounts.add(investmentAccount);
        customer.setAccounts(accounts);
        
        // Action: Attempt to buy 200 shares of "XYZ" at $30 each
        boolean result = investmentAccount.buyStock("XYZ", 200, 30.0);
        
        // Expected Output: False
        assertFalse("Should fail purchase due to insufficient funds", result);
        
        // Verify balance remains unchanged
        assertEquals(5000.0, investmentAccount.getBalance(), 0.001);
        
        // Verify no transaction was recorded
        assertEquals(0, investmentAccount.getTransactions().size());
    }
    
    @Test
    public void testCase3_ExactFundsPurchase() {
        // SetUp: Customer "Poe" holds investment account "INV102" with $5,500 balance
        investmentAccount = new InvestmentAccount();
        investmentAccount.setId("INV102");
        investmentAccount.setBalance(5500.0);
        
        List<Account> accounts = new ArrayList<>();
        accounts.add(investmentAccount);
        customer.setAccounts(accounts);
        
        // Action: Buy 100 shares of "DEF" at $50 each
        boolean result = investmentAccount.buyStock("DEF", 100, 50.0);
        
        // Expected Output: True
        assertTrue("Should successfully purchase with exact funds", result);
        
        // Verify balance was updated to zero (5500 - (100*50 + 100*50*0.1) = 5500 - 5500 = 0)
        assertEquals(0.0, investmentAccount.getBalance(), 0.001);
        
        // Verify transaction was recorded
        assertEquals(1, investmentAccount.getTransactions().size());
        StockTransaction transaction = investmentAccount.getTransactions().get(0);
        assertEquals("DEF", transaction.getStock());
        assertEquals(100, transaction.getQuantity());
        assertEquals(50.0, transaction.getPrice(), 0.001);
        assertEquals(500.0, transaction.getCommission(), 0.001);
    }
    
    @Test
    public void testCase4_LowValueSingleShare() {
        // SetUp: Customer "Poe" holds investment account "INV103" with $100 balance
        investmentAccount = new InvestmentAccount();
        investmentAccount.setId("INV103");
        investmentAccount.setBalance(100.0);
        
        List<Account> accounts = new ArrayList<>();
        accounts.add(investmentAccount);
        customer.setAccounts(accounts);
        
        // Action: Buy 1 share of "GHI" at $80
        boolean result = investmentAccount.buyStock("GHI", 1, 80.0);
        
        // Expected Output: True
        assertTrue("Should successfully purchase single low-value share", result);
        
        // Verify balance was updated correctly (100 - (1*80 + 1*80*0.1) = 100 - 88 = 12)
        assertEquals(12.0, investmentAccount.getBalance(), 0.001);
        
        // Verify transaction was recorded
        assertEquals(1, investmentAccount.getTransactions().size());
        StockTransaction transaction = investmentAccount.getTransactions().get(0);
        assertEquals("GHI", transaction.getStock());
        assertEquals(1, transaction.getQuantity());
        assertEquals(80.0, transaction.getPrice(), 0.001);
        assertEquals(8.0, transaction.getCommission(), 0.001);
    }
    
    @Test
    public void testCase5_SecondPurchaseFailsAfterBalanceDrops() {
        // SetUp: Customer "Poe" holds investment account "INV104" with $4,000 balance
        investmentAccount = new InvestmentAccount();
        investmentAccount.setId("INV104");
        investmentAccount.setBalance(4000.0);
        
        List<Account> accounts = new ArrayList<>();
        accounts.add(investmentAccount);
        customer.setAccounts(accounts);
        
        // First purchase: 50 shares of "JKL" at $40
        boolean firstPurchase = investmentAccount.buyStock("JKL", 50, 40.0);
        assertTrue("First purchase should succeed", firstPurchase);
        
        // Verify balance after first purchase (4000 - (50*40 + 50*40*0.1) = 4000 - 2200 = 1800)
        assertEquals(1800.0, investmentAccount.getBalance(), 0.001);
        
        // Action: Attempt second identical purchase requiring $2,200
        boolean secondPurchase = investmentAccount.buyStock("JKL", 50, 40.0);
        
        // Expected Output: False
        assertFalse("Second purchase should fail due to insufficient balance", secondPurchase);
        
        // Verify balance remains unchanged after failed purchase
        assertEquals(1800.0, investmentAccount.getBalance(), 0.001);
        
        // Verify only one transaction was recorded (the successful first one)
        assertEquals(1, investmentAccount.getTransactions().size());
    }
}