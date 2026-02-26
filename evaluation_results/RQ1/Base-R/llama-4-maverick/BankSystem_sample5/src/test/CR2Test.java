import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    private Customer customerDoli;
    private Customer customerAlie;
    private SavingsAccount savingsAccountSAV004;
    private SavingsAccount savingsAccountSAV005;
    private SavingsAccount savingsAccountSAV006;
    private InvestmentAccount investmentAccountINV001;
    private InvestmentAccount investmentAccountINV002;
    
    @Before
    public void setUp() {
        // Set up customer Doli with savings account SAV004
        customerDoli = new Customer();
        customerDoli.setName("Doli");
        customerDoli.setAddress("123 Main St");
        savingsAccountSAV004 = new SavingsAccount();
        savingsAccountSAV004.setId("SAV004");
        savingsAccountSAV004.setBalance(1000.0);
        savingsAccountSAV004.setInterestRate(3.65);
        customerDoli.addAccount(savingsAccountSAV004);
        
        // Set up customer Alie with savings account SAV005
        customerAlie = new Customer();
        customerAlie.setName("Alie");
        customerAlie.setAddress("0812 Center St");
        savingsAccountSAV005 = new SavingsAccount();
        savingsAccountSAV005.setId("SAV005");
        savingsAccountSAV005.setBalance(200.0);
        savingsAccountSAV005.setInterestRate(3.65);
        customerAlie.addAccount(savingsAccountSAV005);
        
        // Set up investment account INV001
        investmentAccountINV001 = new InvestmentAccount();
        investmentAccountINV001.setId("INV001");
        investmentAccountINV001.setBalance(300.0);
        
        // Set up customer Alie with investment account INV002
        investmentAccountINV002 = new InvestmentAccount();
        investmentAccountINV002.setId("INV002");
        investmentAccountINV002.setBalance(0.0);
        customerAlie.addAccount(investmentAccountINV002);
        
        // Set up customer Alie with savings account SAV006
        savingsAccountSAV006 = new SavingsAccount();
        savingsAccountSAV006.setId("SAV006");
        savingsAccountSAV006.setBalance(50.0);
        savingsAccountSAV006.setInterestRate(3.65);
        customerAlie.addAccount(savingsAccountSAV006);
    }
    
    @Test
    public void testCase1_depositPositiveAmountToSavings() {
        // Test Case 1: Deposit positive amount to savings
        // Action: Deposit $500 into "SAV004"
        boolean result = savingsAccountSAV004.deposit(500.0);
        
        // Verify deposit was successful
        assertTrue("Deposit should succeed for positive amount within limit", result);
        
        // Verify new balance is correct
        assertEquals("New balance should be 1500", 1500.0, savingsAccountSAV004.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_depositZeroAmount() {
        // Test Case 2: Deposit zero amount
        double initialBalance = savingsAccountSAV005.getBalance();
        
        // Action: Attempt to deposit $0
        boolean result = savingsAccountSAV005.deposit(0.0);
        
        // Verify deposit failed
        assertFalse("Deposit should fail for zero amount", result);
        
        // Verify balance remains unchanged
        assertEquals("Balance should remain at 200", initialBalance, savingsAccountSAV005.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_depositNegativeAmount() {
        // Test Case 3: Deposit negative amount
        double initialBalance = investmentAccountINV001.getBalance();
        
        // Action: Attempt to deposit -$100
        boolean result = investmentAccountINV001.deposit(-100.0);
        
        // Verify deposit failed
        assertFalse("Deposit should fail for negative amount", result);
        
        // Verify balance remains unchanged
        assertEquals("Balance should remain at 300", initialBalance, investmentAccountINV001.getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_depositExactlyAtSingleDepositLimit() {
        // Test Case 4: Deposit exactly at single-deposit limit
        // Action: Deposit $1,000,000
        boolean result = investmentAccountINV002.deposit(1000000.0);
        
        // Verify deposit was successful
        assertTrue("Deposit should succeed for amount exactly at limit", result);
        
        // Verify new balance is correct
        assertEquals("New balance should be 1000000", 1000000.0, investmentAccountINV002.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_depositExceedsLimitByOneDollar() {
        // Test Case 5: Deposit exceeds limit by one dollar
        double initialBalance = savingsAccountSAV006.getBalance();
        
        // Action: Attempt to deposit $1,000,001
        boolean result = savingsAccountSAV006.deposit(1000001.0);
        
        // Verify deposit failed
        assertFalse("Deposit should fail for amount exceeding limit", result);
        
        // Verify balance remains unchanged
        assertEquals("Balance should remain at 50", initialBalance, savingsAccountSAV006.getBalance(), 0.001);
    }
}