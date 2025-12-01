import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    private Customer customer;
    private SavingsAccount savingsAccount;
    private InvestmentAccount investmentAccount;
    
    @Before
    public void setUp() {
        // Reset objects before each test
        customer = null;
        savingsAccount = null;
        investmentAccount = null;
    }
    
    @Test
    public void testCase1_DepositPositiveAmountToSavings() {
        // SetUp: Customer "Doli" (address "123 Main St") holds savings account "SAV004", balance $1000, interest rate 3.65%
        customer = new Customer("Doli", "123 Main St");
        savingsAccount = new SavingsAccount("SAV004", 1000.0, 3.65);
        customer.addAccount(savingsAccount);
        
        // Action: Deposit $500 into "SAV004"
        boolean result = savingsAccount.deposit(500.0);
        
        // Expected Output: 1500 (new balance)
        assertTrue("Deposit should be successful", result);
        assertEquals(1500.0, savingsAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_DepositZeroAmount() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds savings account "SAV005", balance $200, interest rate 3.65%
        customer = new Customer("Alie", "0812 Center St");
        savingsAccount = new SavingsAccount("SAV005", 200.0, 3.65);
        customer.addAccount(savingsAccount);
        
        // Action: Attempt to deposit $0
        boolean result = savingsAccount.deposit(0.0);
        
        // Expected Output: False, the balance of 'SAV005' is still $200
        assertFalse("Deposit of zero amount should fail", result);
        assertEquals(200.0, savingsAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_DepositNegativeAmount() {
        // SetUp: Investment account "INV001" holds balance $300
        investmentAccount = new InvestmentAccount("INV001", 300.0);
        
        // Action: Attempt to deposit -$100
        boolean result = investmentAccount.deposit(-100.0);
        
        // Expected Output: False
        assertFalse("Deposit of negative amount should fail", result);
        assertEquals(300.0, investmentAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_DepositExactlyAtSingleDepositLimit() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds an investment account "INV002" holds balance $0
        customer = new Customer("Alie", "0812 Center St");
        investmentAccount = new InvestmentAccount("INV002", 0.0);
        customer.addAccount(investmentAccount);
        
        // Action: Deposit $1,000,000
        boolean result = investmentAccount.deposit(1000000.0);
        
        // Expected Output: 1,000,000
        assertTrue("Deposit at limit should be successful", result);
        assertEquals(1000000.0, investmentAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_DepositExceedsLimitByOneDollar() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds a savings account "SAV006" holds balance $50, interest rate 3.65%
        customer = new Customer("Alie", "0812 Center St");
        savingsAccount = new SavingsAccount("SAV006", 50.0, 3.65);
        customer.addAccount(savingsAccount);
        
        // Action: Attempt to deposit $1,000,001
        boolean result = savingsAccount.deposit(1000001.0);
        
        // Expected Output: False
        assertFalse("Deposit exceeding limit should fail", result);
        assertEquals(50.0, savingsAccount.getBalance(), 0.001);
    }
}