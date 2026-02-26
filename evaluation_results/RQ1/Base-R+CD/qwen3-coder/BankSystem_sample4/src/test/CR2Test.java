import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Customer customer;
    private SavingAccount savingsAccount;
    private InvestmentAccount investmentAccount;
    
    @Before
    public void setUp() {
        customer = new Customer();
        customer.setName("Doli");
        customer.setAddress("123 Main St");
        customer.setAccounts(new ArrayList<Account>());
    }
    
    @Test
    public void testCase1_depositPositiveAmountToSavings() {
        // SetUp: Customer "Doli" holds savings account "SAV004", balance $1000, interest rate 3.65%
        savingsAccount = new SavingAccount();
        savingsAccount.setId("SAV004");
        savingsAccount.setBalance(1000.0);
        savingsAccount.setInterestRate(3.65);
        customer.getAccounts().add(savingsAccount);
        
        // Action: Deposit $500 into "SAV004"
        boolean result = savingsAccount.deposit(500.0);
        
        // Expected Output: 1500 (new balance)
        assertTrue(result);
        assertEquals(1500.0, savingsAccount.getBalance(), 0.01);
    }
    
    @Test
    public void testCase2_depositZeroAmount() {
        // SetUp: Customer "Alie" holds savings account "SAV005", balance $200, interest rate 3.65%
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        savingsAccount = new SavingAccount();
        savingsAccount.setId("SAV005");
        savingsAccount.setBalance(200.0);
        savingsAccount.setInterestRate(3.65);
        customer.getAccounts().add(savingsAccount);
        
        // Action: Attempt to deposit $0
        boolean result = savingsAccount.deposit(0.0);
        
        // Expected Output: False, the balance of 'SAV005' is still $200
        assertFalse(result);
        assertEquals(200.0, savingsAccount.getBalance(), 0.01);
    }
    
    @Test
    public void testCase3_depositNegativeAmount() {
        // SetUp: Investment account "INV001" holds balance $300
        investmentAccount = new InvestmentAccount();
        investmentAccount.setId("INV001");
        investmentAccount.setBalance(300.0);
        customer.getAccounts().add(investmentAccount);
        
        // Action: Attempt to deposit -$100
        boolean result = investmentAccount.deposit(-100.0);
        
        // Expected Output: False
        assertFalse(result);
    }
    
    @Test
    public void testCase4_depositExactlyAtSingleDepositLimit() {
        // SetUp: Customer "Alie" holds an investment account "INV002" with balance $0
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        investmentAccount = new InvestmentAccount();
        investmentAccount.setId("INV002");
        investmentAccount.setBalance(0.0);
        customer.getAccounts().add(investmentAccount);
        
        // Action: Deposit $1000000
        boolean result = investmentAccount.deposit(1000000.0);
        
        // Expected Output: 1000000 (new balance)
        assertTrue(result);
        assertEquals(1000000.0, investmentAccount.getBalance(), 0.01);
    }
    
    @Test
    public void testCase5_depositExceedsLimitByOneDollar() {
        // SetUp: Customer "Alie" holds a savings account "SAV006" with balance $50, interest rate 3.65%
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        savingsAccount = new SavingAccount();
        savingsAccount.setId("SAV006");
        savingsAccount.setBalance(50.0);
        savingsAccount.setInterestRate(3.65);
        customer.getAccounts().add(savingsAccount);
        
        // Action: Attempt to deposit $1000001
        boolean result = savingsAccount.deposit(1000001.0);
        
        // Expected Output: False
        assertFalse(result);
    }
}