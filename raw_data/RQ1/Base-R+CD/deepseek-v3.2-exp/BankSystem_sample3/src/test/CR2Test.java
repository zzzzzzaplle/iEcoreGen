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
        customer.findAccountById("SAV004").setBalance(1000.0);
        
        // Action: Deposit $500 into "SAV004"
        boolean depositResult = customer.findAccountById("SAV004").deposit(500.0);
        
        // Expected Output: 1500 (new balance)
        assertTrue("Deposit should be successful", depositResult);
        assertEquals("Balance should be 1500 after deposit", 1500.0, 
                    customer.findAccountById("SAV004").getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_DepositZeroAmount() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds savings account "SAV005", balance $200, interest rate 3.65%
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        customer.addSavingAccount("SAV005", 3.65);
        customer.findAccountById("SAV005").setBalance(200.0);
        double initialBalance = customer.findAccountById("SAV005").getBalance();
        
        // Action: Attempt to deposit $0
        boolean depositResult = customer.findAccountById("SAV005").deposit(0.0);
        
        // Expected Output: False, the balance of 'SAV005' is still $200
        assertFalse("Deposit of zero amount should fail", depositResult);
        assertEquals("Balance should remain unchanged at 200", 200.0, 
                    customer.findAccountById("SAV005").getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_DepositNegativeAmount() {
        // SetUp: Investment account "INV001" holds balance $300
        customer.addInvestmentAccount("INV001");
        customer.findAccountById("INV001").setBalance(300.0);
        double initialBalance = customer.findAccountById("INV001").getBalance();
        
        // Action: Attempt to deposit -$100
        boolean depositResult = customer.findAccountById("INV001").deposit(-100.0);
        
        // Expected Output: False
        assertFalse("Deposit of negative amount should fail", depositResult);
        assertEquals("Balance should remain unchanged at 300", 300.0, 
                    customer.findAccountById("INV001").getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_DepositExactlyAtSingleDepositLimit() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds an investment account "INV002" holds balance $0
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        customer.addInvestmentAccount("INV002");
        customer.findAccountById("INV002").setBalance(0.0);
        
        // Action: Deposit $1,000,000
        boolean depositResult = customer.findAccountById("INV002").deposit(1000000.0);
        
        // Expected Output: 1,000,000
        assertTrue("Deposit at maximum limit should be successful", depositResult);
        assertEquals("Balance should be 1000000 after deposit", 1000000.0, 
                    customer.findAccountById("INV002").getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_DepositExceedsLimitByOneDollar() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds a savings account "SAV006" holds balance $50, interest rate 3.65%
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        customer.addSavingAccount("SAV006", 3.65);
        customer.findAccountById("SAV006").setBalance(50.0);
        double initialBalance = customer.findAccountById("SAV006").getBalance();
        
        // Action: Attempt to deposit $1,000,001
        boolean depositResult = customer.findAccountById("SAV006").deposit(1000001.0);
        
        // Expected Output: False
        assertFalse("Deposit exceeding maximum limit should fail", depositResult);
        assertEquals("Balance should remain unchanged at 50", 50.0, 
                    customer.findAccountById("SAV006").getBalance(), 0.001);
    }
}