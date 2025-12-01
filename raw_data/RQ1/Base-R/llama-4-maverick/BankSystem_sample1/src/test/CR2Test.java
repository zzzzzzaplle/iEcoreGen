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
        // Set up test data before each test
        customerDoli = new Customer();
        customerDoli.setName("Doli");
        customerDoli.setAddress("123 Main St");
        
        customerAlie = new Customer();
        customerAlie.setName("Alie");
        customerAlie.setAddress("0812 Center St");
        
        // Savings Account SAV004 for customer Doli
        savingsAccountSAV004 = new SavingsAccount();
        savingsAccountSAV004.setId("SAV004");
        savingsAccountSAV004.setBalance(1000.0);
        savingsAccountSAV004.setInterestRate(3.65);
        customerDoli.addAccount(savingsAccountSAV004);
        
        // Savings Account SAV005 for customer Alie
        savingsAccountSAV005 = new SavingsAccount();
        savingsAccountSAV005.setId("SAV005");
        savingsAccountSAV005.setBalance(200.0);
        savingsAccountSAV005.setInterestRate(3.65);
        customerAlie.addAccount(savingsAccountSAV005);
        
        // Investment Account INV001
        investmentAccountINV001 = new InvestmentAccount();
        investmentAccountINV001.setId("INV001");
        investmentAccountINV001.setBalance(300.0);
        
        // Investment Account INV002 for customer Alie
        investmentAccountINV002 = new InvestmentAccount();
        investmentAccountINV002.setId("INV002");
        investmentAccountINV002.setBalance(0.0);
        customerAlie.addAccount(investmentAccountINV002);
        
        // Savings Account SAV006 for customer Alie
        savingsAccountSAV006 = new SavingsAccount();
        savingsAccountSAV006.setId("SAV006");
        savingsAccountSAV006.setBalance(50.0);
        savingsAccountSAV006.setInterestRate(3.65);
        customerAlie.addAccount(savingsAccountSAV006);
    }
    
    @Test
    public void testCase1_DepositPositiveAmountToSavings() {
        // Test Case 1: Deposit positive amount to savings
        // Setup: Customer "Doli" holds savings account "SAV004", balance $1000, interest rate 3.65%
        // Action: Deposit $500 into "SAV004"
        // Expected Output: 1500 (new balance)
        
        boolean depositResult = savingsAccountSAV004.deposit(500.0);
        assertTrue("Deposit should be successful", depositResult);
        assertEquals("Balance should be updated to 1500", 1500.0, savingsAccountSAV004.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_DepositZeroAmount() {
        // Test Case 2: Deposit zero amount
        // Setup: Customer "Alie" holds savings account "SAV005", balance $200, interest rate 3.65%
        // Action: Attempt to deposit $0
        // Expected Output: False, the balance of 'SAV005' is still $200
        
        boolean depositResult = savingsAccountSAV005.deposit(0.0);
        assertFalse("Deposit of zero amount should fail", depositResult);
        assertEquals("Balance should remain unchanged at 200", 200.0, savingsAccountSAV005.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_DepositNegativeAmount() {
        // Test Case 3: Deposit negative amount
        // Setup: Investment account "INV001" holds balance $300
        // Action: Attempt to deposit -$100
        // Expected Output: False
        
        boolean depositResult = investmentAccountINV001.deposit(-100.0);
        assertFalse("Deposit of negative amount should fail", depositResult);
        assertEquals("Balance should remain unchanged at 300", 300.0, investmentAccountINV001.getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_DepositExactlyAtSingleDepositLimit() {
        // Test Case 4: Deposit exactly at single-deposit limit
        // Setup: Customer "Alie" holds an investment account "INV002" with balance $0
        // Action: Deposit $1,000,000
        // Expected Output: 1,000,000
        
        boolean depositResult = investmentAccountINV002.deposit(1000000.0);
        assertTrue("Deposit at limit should be successful", depositResult);
        assertEquals("Balance should be updated to 1000000", 1000000.0, investmentAccountINV002.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_DepositExceedsLimitByOneDollar() {
        // Test Case 5: Deposit exceeds limit by one dollar
        // Setup: Customer "Alie" holds a savings account "SAV006" with balance $50, interest rate 3.65%
        // Action: Attempt to deposit $1,000,001
        // Expected Output: False
        
        boolean depositResult = savingsAccountSAV006.deposit(1000001.0);
        assertFalse("Deposit exceeding limit should fail", depositResult);
        assertEquals("Balance should remain unchanged at 50", 50.0, savingsAccountSAV006.getBalance(), 0.001);
    }
}