import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    private Customer customer;
    private SavingAccount savingsAccount;
    private InvestmentAccount investmentAccount;
    
    @Before
    public void setUp() {
        customer = new Customer();
    }
    
    @Test
    public void testCase1_DepositPositiveAmountToSavings() {
        // SetUp: Customer "Doli" (address "123 Main St") holds savings account "SAV004", balance $1000, interest rate 3.65%
        customer.setName("Doli");
        customer.setAddress("123 Main St");
        boolean accountCreated = customer.addSavingAccount("SAV004", 3.65);
        assertTrue("Savings account should be created successfully", accountCreated);
        
        savingsAccount = (SavingAccount) customer.findAccountById("SAV004");
        savingsAccount.setBalance(1000.0);
        
        // Action: Deposit $500 into "SAV004"
        boolean depositResult = savingsAccount.deposit(500.0);
        
        // Expected Output: 1500 (new balance)
        assertTrue("Deposit should be successful", depositResult);
        assertEquals(1500.0, savingsAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_DepositZeroAmount() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds savings account "SAV005", balance $200, interest rate 3.65%
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        boolean accountCreated = customer.addSavingAccount("SAV005", 3.65);
        assertTrue("Savings account should be created successfully", accountCreated);
        
        savingsAccount = (SavingAccount) customer.findAccountById("SAV005");
        savingsAccount.setBalance(200.0);
        double initialBalance = savingsAccount.getBalance();
        
        // Action: Attempt to deposit $0
        boolean depositResult = savingsAccount.deposit(0.0);
        
        // Expected Output: False, the balance of 'SAV005' is still $200
        assertFalse("Deposit of zero amount should fail", depositResult);
        assertEquals("Balance should remain unchanged", 200.0, savingsAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_DepositNegativeAmount() {
        // SetUp: Investment account "INV001" holds balance $300
        customer.setName("Test Customer");
        customer.setAddress("Test Address");
        boolean accountCreated = customer.addInvestmentAccount("INV001");
        assertTrue("Investment account should be created successfully", accountCreated);
        
        investmentAccount = (InvestmentAccount) customer.findAccountById("INV001");
        investmentAccount.setBalance(300.0);
        double initialBalance = investmentAccount.getBalance();
        
        // Action: Attempt to deposit -$100
        boolean depositResult = investmentAccount.deposit(-100.0);
        
        // Expected Output: False
        assertFalse("Deposit of negative amount should fail", depositResult);
        assertEquals("Balance should remain unchanged", 300.0, investmentAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_DepositExactlyAtSingleDepositLimit() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds an investment account "INV002" holds balance $0
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        boolean accountCreated = customer.addInvestmentAccount("INV002");
        assertTrue("Investment account should be created successfully", accountCreated);
        
        investmentAccount = (InvestmentAccount) customer.findAccountById("INV002");
        investmentAccount.setBalance(0.0);
        
        // Action: Deposit $1,000,000
        boolean depositResult = investmentAccount.deposit(1000000.0);
        
        // Expected Output: 1,000,000
        assertTrue("Deposit at maximum limit should be successful", depositResult);
        assertEquals(1000000.0, investmentAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_DepositExceedsLimitByOneDollar() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds a savings account "SAV006" holds balance $50, interest rate 3.65%
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        boolean accountCreated = customer.addSavingAccount("SAV006", 3.65);
        assertTrue("Savings account should be created successfully", accountCreated);
        
        savingsAccount = (SavingAccount) customer.findAccountById("SAV006");
        savingsAccount.setBalance(50.0);
        double initialBalance = savingsAccount.getBalance();
        
        // Action: Attempt to deposit $1,000,001
        boolean depositResult = savingsAccount.deposit(1000001.0);
        
        // Expected Output: False
        assertFalse("Deposit exceeding limit should fail", depositResult);
        assertEquals("Balance should remain unchanged", 50.0, savingsAccount.getBalance(), 0.001);
    }
}