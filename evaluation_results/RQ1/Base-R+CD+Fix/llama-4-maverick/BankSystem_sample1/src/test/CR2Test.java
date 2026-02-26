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
        
        savingsAccount = new SavingAccount();
        savingsAccount.setId("SAV004");
        savingsAccount.setBalance(1000.0);
        savingsAccount.setInterestRate(3.65);
        
        investmentAccount = new InvestmentAccount();
        investmentAccount.setId("INV001");
        investmentAccount.setBalance(300.0);
        
        List<Account> accounts = new ArrayList<>();
        accounts.add(savingsAccount);
        accounts.add(investmentAccount);
        customer.setAccounts(accounts);
    }
    
    @Test
    public void testCase1_depositPositiveAmountToSavings() {
        // SetUp: Customer "Doli" (address "123 Main St") holds savings account "SAV004", balance $1 000, interest rate 3.65 %.
        // This is done in setUp method
        
        // Action: Deposit $500 into "SAV004".
        boolean result = savingsAccount.deposit(500.0);
        
        // Expected Output: 1 500 // new balance
        assertTrue(result);
        assertEquals(1500.0, savingsAccount.getBalance(), 0.01);
    }
    
    @Test
    public void testCase2_depositZeroAmount() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds savings account "SAV005", balance $200, interest rate 3.65 %.
        Customer alie = new Customer();
        alie.setName("Alie");
        alie.setAddress("0812 Center St");
        
        SavingAccount sav005 = new SavingAccount();
        sav005.setId("SAV005");
        sav005.setBalance(200.0);
        sav005.setInterestRate(3.65);
        
        List<Account> accounts = new ArrayList<>();
        accounts.add(sav005);
        alie.setAccounts(accounts);
        
        // Action: Attempt to deposit $0.
        boolean result = sav005.deposit(0.0);
        
        // Expected Output: False, the balance of 'SAV005' is still $200.
        assertFalse(result);
        assertEquals(200.0, sav005.getBalance(), 0.01);
    }
    
    @Test
    public void testCase3_depositNegativeAmount() {
        // SetUp: Investment account "INV001" holds balance $300.
        // This is done in setUp method
        
        // Action: Attempt to deposit –$100.
        boolean result = investmentAccount.deposit(-100.0);
        
        // Expected Output: False
        assertFalse(result);
    }
    
    @Test
    public void testCase4_depositExactlyAtSingleDepositLimit() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds an investment account "INV002" holds balance $0.
        Customer alie = new Customer();
        alie.setName("Alie");
        alie.setAddress("0812 Center St");
        
        InvestmentAccount inv002 = new InvestmentAccount();
        inv002.setId("INV002");
        inv002.setBalance(0.0);
        
        List<Account> accounts = new ArrayList<>();
        accounts.add(inv002);
        alie.setAccounts(accounts);
        
        // Action: Deposit $1 000 000.
        boolean result = inv002.deposit(1000000.0);
        
        // Expected Output: 1 000 000
        assertTrue(result);
        assertEquals(1000000.0, inv002.getBalance(), 0.01);
    }
    
    @Test
    public void testCase5_depositExceedsLimitByOneDollar() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds a savings account "SAV006" holds balance $50, interest rate 3.65 %.
        Customer alie = new Customer();
        alie.setName("Alie");
        alie.setAddress("0812 Center St");
        
        SavingAccount sav006 = new SavingAccount();
        sav006.setId("SAV006");
        sav006.setBalance(50.0);
        sav006.setInterestRate(3.65);
        
        List<Account> accounts = new ArrayList<>();
        accounts.add(sav006);
        alie.setAccounts(accounts);
        
        // Action: Attempt to deposit $1 000 001.
        boolean result = sav006.deposit(1000001.0);
        
        // Expected Output: False
        assertFalse(result);
    }
}