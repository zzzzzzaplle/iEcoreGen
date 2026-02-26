import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    private Customer customerDoli;
    private Customer customerAlie;
    
    @Before
    public void setUp() {
        // Create customers
        customerDoli = new Customer();
        customerDoli.setName("Doli");
        customerDoli.setAddress("123 Main St");
        
        customerAlie = new Customer();
        customerAlie.setName("Alie");
        customerAlie.setAddress("0812 Center St");
    }
    
    @Test
    public void testCase1_DepositPositiveAmountToSavings() {
        // SetUp: Customer "Doli" holds savings account "SAV004", balance $1000, interest rate 3.65%
        customerDoli.addSavingAccount("SAV004", 3.65);
        SavingAccount savAccount = (SavingAccount) customerDoli.findAccountById("SAV004");
        savAccount.setBalance(1000.0);
        
        // Action: Deposit $500 into "SAV004"
        boolean depositResult = savAccount.deposit(500.0);
        
        // Verify deposit was successful
        assertTrue("Deposit should be successful", depositResult);
        
        // Expected Output: 1500 (new balance)
        assertEquals("Balance should be 1500 after deposit", 1500.0, savAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_DepositZeroAmount() {
        // SetUp: Customer "Alie" holds savings account "SAV005", balance $200, interest rate 3.65%
        customerAlie.addSavingAccount("SAV005", 3.65);
        SavingAccount savAccount = (SavingAccount) customerAlie.findAccountById("SAV005");
        savAccount.setBalance(200.0);
        double initialBalance = savAccount.getBalance();
        
        // Action: Attempt to deposit $0
        boolean depositResult = savAccount.deposit(0.0);
        
        // Verify deposit failed
        assertFalse("Deposit of zero amount should fail", depositResult);
        
        // Expected Output: False, the balance of 'SAV005' is still $200
        assertEquals("Balance should remain unchanged", initialBalance, savAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_DepositNegativeAmount() {
        // SetUp: Investment account "INV001" holds balance $300
        customerAlie.addInvestmentAccount("INV001");
        InvestmentAccount invAccount = (InvestmentAccount) customerAlie.findAccountById("INV001");
        invAccount.setBalance(300.0);
        double initialBalance = invAccount.getBalance();
        
        // Action: Attempt to deposit -$100
        boolean depositResult = invAccount.deposit(-100.0);
        
        // Verify deposit failed
        assertFalse("Deposit of negative amount should fail", depositResult);
        
        // Expected Output: False
        assertEquals("Balance should remain unchanged", initialBalance, invAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_DepositExactlyAtSingleDepositLimit() {
        // SetUp: Customer "Alie" holds an investment account "INV002" with balance $0
        customerAlie.addInvestmentAccount("INV002");
        InvestmentAccount invAccount = (InvestmentAccount) customerAlie.findAccountById("INV002");
        invAccount.setBalance(0.0);
        
        // Action: Deposit $1,000,000
        boolean depositResult = invAccount.deposit(1000000.0);
        
        // Verify deposit was successful
        assertTrue("Deposit at limit should be successful", depositResult);
        
        // Expected Output: 1,000,000
        assertEquals("Balance should be 1,000,000 after deposit", 1000000.0, invAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_DepositExceedsLimitByOneDollar() {
        // SetUp: Customer "Alie" holds a savings account "SAV006" with balance $50, interest rate 3.65%
        customerAlie.addSavingAccount("SAV006", 3.65);
        SavingAccount savAccount = (SavingAccount) customerAlie.findAccountById("SAV006");
        savAccount.setBalance(50.0);
        double initialBalance = savAccount.getBalance();
        
        // Action: Attempt to deposit $1,000,001
        boolean depositResult = savAccount.deposit(1000001.0);
        
        // Verify deposit failed
        assertFalse("Deposit exceeding limit should fail", depositResult);
        
        // Expected Output: False
        assertEquals("Balance should remain unchanged", initialBalance, savAccount.getBalance(), 0.001);
    }
}