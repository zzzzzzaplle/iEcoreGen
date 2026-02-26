import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Customer customer;
    private SavingAccount savingAccount;
    private InvestmentAccount investmentAccount;
    
    @Before
    public void setUp() {
        customer = new Customer();
        customer.setName("Doli");
        customer.setAddress("123 Main St");
        
        savingAccount = new SavingAccount();
        savingAccount.setId("SAV004");
        savingAccount.setBalance(1000.0);
        savingAccount.setInterestRate(3.65);
        
        investmentAccount = new InvestmentAccount();
        investmentAccount.setId("INV001");
        investmentAccount.setBalance(300.0);
        
        customer.getAccounts().add(savingAccount);
        customer.getAccounts().add(investmentAccount);
    }
    
    @Test
    public void testCase1_depositPositiveAmountToSavings() {
        // Setup: Customer "Doli" with savings account "SAV004", balance $1000, interest rate 3.65%
        // This is done in setUp method
        
        // Action: Deposit $500 into "SAV004"
        boolean result = savingAccount.deposit(500.0);
        
        // Expected Output: 1500 (new balance)
        assertTrue(result);
        assertEquals(1500.0, savingAccount.getBalance(), 0.01);
    }
    
    @Test
    public void testCase2_depositZeroAmount() {
        // Setup: Customer "Alie" with savings account "SAV005", balance $200, interest rate 3.65%
        Customer alie = new Customer();
        alie.setName("Alie");
        alie.setAddress("0812 Center St");
        
        SavingAccount sav005 = new SavingAccount();
        sav005.setId("SAV005");
        sav005.setBalance(200.0);
        sav005.setInterestRate(3.65);
        
        alie.getAccounts().add(sav005);
        
        // Action: Attempt to deposit $0
        boolean result = sav005.deposit(0.0);
        
        // Expected Output: False, the balance of 'SAV005' is still $200
        assertFalse(result);
        assertEquals(200.0, sav005.getBalance(), 0.01);
    }
    
    @Test
    public void testCase3_depositNegativeAmount() {
        // Setup: Investment account "INV001" holds balance $300
        // This is done in setUp method
        
        // Action: Attempt to deposit -$100
        boolean result = investmentAccount.deposit(-100.0);
        
        // Expected Output: False
        assertFalse(result);
    }
    
    @Test
    public void testCase4_depositExactlyAtSingleDepositLimit() {
        // Setup: Customer "Alie" with investment account "INV002" holding balance $0
        Customer alie = new Customer();
        alie.setName("Alie");
        alie.setAddress("0812 Center St");
        
        InvestmentAccount inv002 = new InvestmentAccount();
        inv002.setId("INV002");
        inv002.setBalance(0.0);
        
        alie.getAccounts().add(inv002);
        
        // Action: Deposit $1,000,000
        boolean result = inv002.deposit(1000000.0);
        
        // Expected Output: 1,000,000 (new balance)
        assertTrue(result);
        assertEquals(1000000.0, inv002.getBalance(), 0.01);
    }
    
    @Test
    public void testCase5_depositExceedsLimitByOneDollar() {
        // Setup: Customer "Alie" with savings account "SAV006" holding balance $50, interest rate 3.65%
        Customer alie = new Customer();
        alie.setName("Alie");
        alie.setAddress("0812 Center St");
        
        SavingAccount sav006 = new SavingAccount();
        sav006.setId("SAV006");
        sav006.setBalance(50.0);
        sav006.setInterestRate(3.65);
        
        alie.getAccounts().add(sav006);
        
        // Action: Attempt to deposit $1,000,001
        boolean result = sav006.deposit(1000001.0);
        
        // Expected Output: False
        assertFalse(result);
    }
}