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
        customer.setName("Test Customer");
        customer.setAddress("Test Address");
        customer.setAccounts(new ArrayList<Account>());
    }
    
    @Test
    public void testCase1_depositPositiveAmountToSavings() {
        // SetUp: Customer "Doli" holds savings account "SAV004", balance $1000, interest rate 3.65%
        SavingAccount account = new SavingAccount();
        account.setId("SAV004");
        account.setBalance(1000.0);
        account.setInterestRate(3.65);
        customer.getAccounts().add(account);
        
        // Action: Deposit $500 into "SAV004"
        boolean result = account.deposit(500.0);
        
        // Expected Output: 1500 (new balance)
        assertTrue(result);
        assertEquals(1500.0, account.getBalance(), 0.01);
    }
    
    @Test
    public void testCase2_depositZeroAmount() {
        // SetUp: Customer "Alie" holds savings account "SAV005", balance $200, interest rate 3.65%
        SavingAccount account = new SavingAccount();
        account.setId("SAV005");
        account.setBalance(200.0);
        account.setInterestRate(3.65);
        customer.getAccounts().add(account);
        
        // Action: Attempt to deposit $0
        boolean result = account.deposit(0.0);
        
        // Expected Output: False, the balance of 'SAV005' is still $200
        assertFalse(result);
        assertEquals(200.0, account.getBalance(), 0.01);
    }
    
    @Test
    public void testCase3_depositNegativeAmount() {
        // SetUp: Investment account "INV001" holds balance $300
        InvestmentAccount account = new InvestmentAccount();
        account.setId("INV001");
        account.setBalance(300.0);
        customer.getAccounts().add(account);
        
        // Action: Attempt to deposit -$100
        boolean result = account.deposit(-100.0);
        
        // Expected Output: False
        assertFalse(result);
    }
    
    @Test
    public void testCase4_depositExactlyAtSingleDepositLimit() {
        // SetUp: Customer "Alie" holds an investment account "INV002" with balance $0
        InvestmentAccount account = new InvestmentAccount();
        account.setId("INV002");
        account.setBalance(0.0);
        customer.getAccounts().add(account);
        
        // Action: Deposit $1000000
        boolean result = account.deposit(1000000.0);
        
        // Expected Output: 1000000 (new balance)
        assertTrue(result);
        assertEquals(1000000.0, account.getBalance(), 0.01);
    }
    
    @Test
    public void testCase5_depositExceedsLimitByOneDollar() {
        // SetUp: Customer "Alie" holds a savings account "SAV006" with balance $50, interest rate 3.65%
        SavingAccount account = new SavingAccount();
        account.setId("SAV006");
        account.setBalance(50.0);
        account.setInterestRate(3.65);
        customer.getAccounts().add(account);
        
        // Action: Attempt to deposit $1000001
        boolean result = account.deposit(1000001.0);
        
        // Expected Output: False
        assertFalse(result);
        assertEquals(50.0, account.getBalance(), 0.01);
    }
}