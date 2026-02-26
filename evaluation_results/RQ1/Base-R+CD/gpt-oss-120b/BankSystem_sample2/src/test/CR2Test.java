import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    private Customer customerDoli;
    private Customer customerAlie;
    private SavingAccount savingsAccount;
    private InvestmentAccount investmentAccount;
    
    @Before
    public void setUp() {
        // Initialize customers and accounts before each test
        customerDoli = new Customer("Doli", "123 Main St");
        customerAlie = new Customer("Alie", "0812 Center St");
    }
    
    @Test
    public void testCase1_depositPositiveAmountToSavings() {
        // SetUp: Customer "Doli" holds savings account "SAV004", balance $1000, interest rate 3.65%
        customerDoli.addSavingAccount("SAV004", 0.0365);
        Account account = customerDoli.findAccountById("SAV004");
        account.deposit(1000.0); // Set initial balance to $1000
        
        // Action: Deposit $500 into "SAV004"
        boolean result = account.deposit(500.0);
        
        // Expected Output: true, new balance should be $1500
        assertTrue(result);
        assertEquals(1500.0, account.getBalance(), 0.01);
    }
    
    @Test
    public void testCase2_depositZeroAmount() {
        // SetUp: Customer "Alie" holds savings account "SAV005", balance $200, interest rate 3.65%
        customerAlie.addSavingAccount("SAV005", 0.0365);
        Account account = customerAlie.findAccountById("SAV005");
        account.deposit(200.0); // Set initial balance to $200
        
        // Action: Attempt to deposit $0
        boolean result = account.deposit(0.0);
        
        // Expected Output: False, the balance of 'SAV005' is still $200
        assertFalse(result);
        assertEquals(200.0, account.getBalance(), 0.01);
    }
    
    @Test
    public void testCase3_depositNegativeAmount() {
        // SetUp: Investment account "INV001" holds balance $300
        customerAlie.addInvestmentAccount("INV001");
        Account account = customerAlie.findAccountById("INV001");
        account.deposit(300.0); // Set initial balance to $300
        
        // Action: Attempt to deposit -$100
        boolean result = account.deposit(-100.0);
        
        // Expected Output: False
        assertFalse(result);
    }
    
    @Test
    public void testCase4_depositExactlyAtSingleDepositLimit() {
        // SetUp: Customer "Alie" holds an investment account "INV002" with balance $0
        customerAlie.addInvestmentAccount("INV002");
        Account account = customerAlie.findAccountById("INV002");
        // Initial balance is $0
        
        // Action: Deposit $1,000,000
        boolean result = account.deposit(1000000.0);
        
        // Expected Output: true, new balance should be $1,000,000
        assertTrue(result);
        assertEquals(1000000.0, account.getBalance(), 0.01);
    }
    
    @Test
    public void testCase5_depositExceedsLimitByOneDollar() {
        // SetUp: Customer "Alie" holds a savings account "SAV006" with balance $50, interest rate 3.65%
        customerAlie.addSavingAccount("SAV006", 0.0365);
        Account account = customerAlie.findAccountById("SAV006");
        account.deposit(50.0); // Set initial balance to $50
        
        // Action: Attempt to deposit $1,000,001
        boolean result = account.deposit(1000001.0);
        
        // Expected Output: False
        assertFalse(result);
    }
}