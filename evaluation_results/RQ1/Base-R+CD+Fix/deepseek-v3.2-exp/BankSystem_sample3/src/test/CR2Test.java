import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    private Customer customer;
    private SavingAccount savingAccount;
    private InvestmentAccount investmentAccount;
    
    @Before
    public void setUp() {
        customer = new Customer();
    }
    
    @Test
    public void testCase1_depositPositiveAmountToSavings() {
        // SetUp: Customer "Doli" (address "123 Main St") holds savings account "SAV004", balance $1000, interest rate 3.65%
        customer.setName("Doli");
        customer.setAddress("123 Main St");
        customer.addSavingAccount("SAV004", 3.65);
        
        savingAccount = (SavingAccount) customer.findAccountById("SAV004");
        savingAccount.setBalance(1000);
        
        // Action: Deposit $500 into "SAV004"
        boolean result = savingAccount.deposit(500);
        
        // Expected Output: 1500 (new balance)
        assertTrue("Deposit should be successful", result);
        assertEquals(1500, savingAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_depositZeroAmount() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds savings account "SAV005", balance $200, interest rate 3.65%
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        customer.addSavingAccount("SAV005", 3.65);
        
        savingAccount = (SavingAccount) customer.findAccountById("SAV005");
        savingAccount.setBalance(200);
        
        // Action: Attempt to deposit $0
        boolean result = savingAccount.deposit(0);
        
        // Expected Output: False, the balance of 'SAV005' is still $200
        assertFalse("Deposit of zero amount should fail", result);
        assertEquals(200, savingAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_depositNegativeAmount() {
        // SetUp: Investment account "INV001" holds balance $300
        customer.addInvestmentAccount("INV001");
        investmentAccount = (InvestmentAccount) customer.findAccountById("INV001");
        investmentAccount.setBalance(300);
        
        // Action: Attempt to deposit -$100
        boolean result = investmentAccount.deposit(-100);
        
        // Expected Output: False
        assertFalse("Deposit of negative amount should fail", result);
        assertEquals(300, investmentAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_depositExactlyAtSingleDepositLimit() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds an investment account "INV002" holds balance $0
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        customer.addInvestmentAccount("INV002");
        
        investmentAccount = (InvestmentAccount) customer.findAccountById("INV002");
        investmentAccount.setBalance(0);
        
        // Action: Deposit $1,000,000
        boolean result = investmentAccount.deposit(1000000);
        
        // Expected Output: 1,000,000
        assertTrue("Deposit at limit should be successful", result);
        assertEquals(1000000, investmentAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_depositExceedsLimitByOneDollar() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds a savings account "SAV006" holds balance $50, interest rate 3.65%
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        customer.addSavingAccount("SAV006", 3.65);
        
        savingAccount = (SavingAccount) customer.findAccountById("SAV006");
        savingAccount.setBalance(50);
        
        // Action: Attempt to deposit $1,000,001
        boolean result = savingAccount.deposit(1000001);
        
        // Expected Output: False
        assertFalse("Deposit exceeding limit should fail", result);
        assertEquals(50, savingAccount.getBalance(), 0.001);
    }
}