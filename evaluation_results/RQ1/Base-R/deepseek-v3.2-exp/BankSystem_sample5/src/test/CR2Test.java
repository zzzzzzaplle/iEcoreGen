import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    private BankSystem bankSystem;
    private Customer customerDoli;
    private Customer customerAlie;
    
    @Before
    public void setUp() {
        // Initialize bank system and test customers
        bankSystem = new BankSystem();
        
        // Set up customer Doli with savings account
        bankSystem.addCustomer("Doli", "123 Main St");
        customerDoli = bankSystem.findCustomer("Doli", "123 Main St");
        customerDoli.addAccount("SAV004", "SAVINGS");
        SavingsAccount sav004 = (SavingsAccount) customerDoli.getAccountById("SAV004");
        sav004.setBalance(1000.0);
        sav004.setInterestRate(0.0365); // 3.65%
        
        // Set up customer Alie with multiple accounts
        bankSystem.addCustomer("Alie", "0812 Center St");
        customerAlie = bankSystem.findCustomer("Alie", "0812 Center St");
        
        // Savings account for Alie
        customerAlie.addAccount("SAV005", "SAVINGS");
        SavingsAccount sav005 = (SavingsAccount) customerAlie.getAccountById("SAV005");
        sav005.setBalance(200.0);
        sav005.setInterestRate(0.0365); // 3.65%
        
        // Investment account for Alie
        customerAlie.addAccount("INV002", "INVESTMENT");
        InvestmentAccount inv002 = (InvestmentAccount) customerAlie.getAccountById("INV002");
        inv002.setBalance(0.0);
        
        // Additional savings account for Alie
        customerAlie.addAccount("SAV006", "SAVINGS");
        SavingsAccount sav006 = (SavingsAccount) customerAlie.getAccountById("SAV006");
        sav006.setBalance(50.0);
        sav006.setInterestRate(0.0365); // 3.65%
        
        // Independent investment account for test case 3
        // Note: This account is not associated with any customer as per test specification
    }
    
    @Test
    public void testCase1_DepositPositiveAmountToSavings() {
        // Test Case 1: Deposit positive amount to savings
        // SetUp: Customer "Doli" (address "123 Main St") holds savings account "SAV004", balance $1 000, interest rate 3.65 %
        
        // Get the savings account
        Account sav004 = customerDoli.getAccountById("SAV004");
        assertNotNull("SAV004 account should exist", sav004);
        assertEquals(1000.0, sav004.getBalance(), 0.001);
        
        // Action: Deposit $500 into "SAV004"
        boolean depositResult = sav004.deposit(500.0);
        
        // Verify deposit was successful
        assertTrue("Deposit of $500 should be successful", depositResult);
        
        // Expected Output: 1 500 (new balance)
        assertEquals(1500.0, sav004.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_DepositZeroAmount() {
        // Test Case 2: Deposit zero amount
        // SetUp: Customer "Alie" (address "0812 Center St") holds savings account "SAV005", balance $200, interest rate 3.65 %
        
        // Get the savings account
        Account sav005 = customerAlie.getAccountById("SAV005");
        assertNotNull("SAV005 account should exist", sav005);
        assertEquals(200.0, sav005.getBalance(), 0.001);
        
        // Action: Attempt to deposit $0
        boolean depositResult = sav005.deposit(0.0);
        
        // Verify deposit failed
        assertFalse("Deposit of $0 should fail", depositResult);
        
        // Expected Output: False, the balance of 'SAV005' is still $200
        assertEquals(200.0, sav005.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_DepositNegativeAmount() {
        // Test Case 3: Deposit negative amount
        // SetUp: Investment account "INV001" holds balance $300
        
        // Create independent investment account as specified
        InvestmentAccount inv001 = new InvestmentAccount("INV001");
        inv001.setBalance(300.0);
        
        // Action: Attempt to deposit –$100
        boolean depositResult = inv001.deposit(-100.0);
        
        // Expected Output: False
        assertFalse("Deposit of negative amount should fail", depositResult);
        
        // Verify balance remains unchanged
        assertEquals(300.0, inv001.getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_DepositExactlyAtSingleDepositLimit() {
        // Test Case 4: Deposit exactly at single-deposit limit
        // SetUp: Customer "Alie" (address "0812 Center St") holds an investment account "INV002" holds balance $0
        
        // Get the investment account
        Account inv002 = customerAlie.getAccountById("INV002");
        assertNotNull("INV002 account should exist", inv002);
        assertEquals(0.0, inv002.getBalance(), 0.001);
        
        // Action: Deposit $1 000 000
        boolean depositResult = inv002.deposit(1000000.0);
        
        // Verify deposit was successful
        assertTrue("Deposit of $1,000,000 should be successful", depositResult);
        
        // Expected Output: 1 000 000
        assertEquals(1000000.0, inv002.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_DepositExceedsLimitByOneDollar() {
        // Test Case 5: Deposit exceeds limit by one dollar
        // SetUp: Customer "Alie" (address "0812 Center St") holds a savings account "SAV006" holds balance $50, interest rate 3.65 %
        
        // Get the savings account
        Account sav006 = customerAlie.getAccountById("SAV006");
        assertNotNull("SAV006 account should exist", sav006);
        assertEquals(50.0, sav006.getBalance(), 0.001);
        
        // Action: Attempt to deposit $1 000 001
        boolean depositResult = sav006.deposit(1000001.0);
        
        // Verify deposit failed
        assertFalse("Deposit exceeding limit by $1 should fail", depositResult);
        
        // Expected Output: False
        assertEquals(50.0, sav006.getBalance(), 0.001);
    }
}