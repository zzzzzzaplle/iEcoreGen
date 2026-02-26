import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    private Customer customer;
    
    @Before
    public void setUp() {
        customer = new Customer();
    }
    
    @Test
    public void testCase1_DepositPositiveAmountToSavings() {
        // SetUp: Customer "Doli" (address "123 Main St") holds savings account "SAV004", balance $1000, interest rate 3.65%
        customer.setName("Doli");
        customer.setAddress("123 Main St");
        customer.addSavingAccount("SAV004", 3.65);
        Account account = customer.findAccountById("SAV004");
        account.setBalance(1000);
        
        // Action: Deposit $500 into "SAV004"
        boolean result = account.deposit(500);
        
        // Expected Output: 1500 (new balance)
        assertTrue("Deposit should be successful", result);
        assertEquals(1500.0, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_DepositZeroAmount() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds savings account "SAV005", balance $200, interest rate 3.65%
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        customer.addSavingAccount("SAV005", 3.65);
        Account account = customer.findAccountById("SAV005");
        account.setBalance(200);
        double initialBalance = account.getBalance();
        
        // Action: Attempt to deposit $0
        boolean result = account.deposit(0);
        
        // Expected Output: False, the balance of 'SAV005' is still $200
        assertFalse("Deposit of zero amount should fail", result);
        assertEquals("Balance should remain unchanged", initialBalance, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_DepositNegativeAmount() {
        // SetUp: Investment account "INV001" holds balance $300
        customer.addInvestmentAccount("INV001");
        Account account = customer.findAccountById("INV001");
        account.setBalance(300);
        double initialBalance = account.getBalance();
        
        // Action: Attempt to deposit -$100
        boolean result = account.deposit(-100);
        
        // Expected Output: False
        assertFalse("Deposit of negative amount should fail", result);
        assertEquals("Balance should remain unchanged", initialBalance, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_DepositExactlyAtSingleDepositLimit() {
        // SetUp: Customer "Alie" (address