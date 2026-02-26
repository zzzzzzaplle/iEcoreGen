import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR1Test {
    private Customer customer;
    
    @Before
    public void setUp() {
        customer = new Customer();
    }
    
    @Test
    public void testCase1_addNewSavingsAccountSuccessfully() {
        // SetUp: Customer "John Doe", address "123 Main St", currently holds no accounts
        customer.setName("John Doe");
        customer.setAddress("123 Main St");
        
        // Action: Add a savings account with ID "SAV001", interest rate 2%
        boolean result = customer.addSavingAccount("SAV001", 2.0);
        
        // Expected Output: True
        assertTrue(result);
        
        // Verify account was added
        Account account = customer.findAccountById("SAV001");
        assertNotNull(account);
        assertTrue(account instanceof SavingAccount);
        assertEquals(2.0, ((SavingAccount) account).getInterestRate(), 0.001);
    }
    
    @Test
    public void testCase2_addAccountWithDuplicateID() {
        // SetUp: Customer "Mia" (address "123 Main St") already holds an investment account "INV001"
        customer.setName("Mia");
        customer.setAddress("123 Main St");
        customer.addInvestmentAccount("INV001");
        
        // Verify initial state
        assertEquals(1, customer.getAccounts().size());
        assertNotNull(customer.findAccountById("INV001"));
        
        // Action: Attempt to add another investment account using the same ID "INV001"
        boolean result = customer.addInvestmentAccount("INV001");
        
        // Expected Output: False
        assertFalse(result);
        
        // Customer still holds exactly one account "INV001"
        assertEquals(1, customer.getAccounts().size());
        assertNotNull(customer.findAccountById("INV001"));
    }
    
    @Test
    public void testCase3_removeExistingZeroBalanceAccount() {
        // SetUp: Customer "Doli" (address "123 Main St") holds savings account "SAV002", balance $0, interest rate 3.65%
        customer.setName("Doli");
        customer.setAddress("123 Main St");
        customer.addSavingAccount("SAV002", 3.65);
        
        // Verify initial state
        Account account = customer.findAccountById("SAV002");
        assertNotNull(account);
        assertEquals(0.0, account.getBalance(), 0.001);
        
        // Action: Remove account "SAV002"
        boolean result = customer.removeSavingAccount("SAV002");
        
        // Expected Output: True
        assertTrue(result);
        
        // Customer now has no account "SAV002"
        assertNull(customer.findAccountById("SAV002"));
        assertEquals(0, customer.getAccounts().size());
    }
    
    @Test
    public void testCase4_attemptToRemoveAccountThatStillHasBalance() {
        // SetUp: Customer "Liam" (address "123 Main St") holds savings account "SAV003", balance $250, interest rate 3.65%
        customer.setName("Liam");
        customer.setAddress("123 Main St");
        customer.addSavingAccount("SAV003", 3.65);
        
        // Deposit $250 to create balance
        Account account = customer.findAccountById("SAV003");
        account.deposit(250.0);
        assertEquals(250.0, account.getBalance(), 0.001);
        
        // Action: Request deletion of account "SAV003"
        boolean result = customer.removeSavingAccount("SAV003");
        
        // Expected Output: False
        assertFalse(result);
        
        // Customer still holds account "SAV003" with balance $250
        Account remainingAccount = customer.findAccountById("SAV003");
        assertNotNull(remainingAccount);
        assertEquals(250.0, remainingAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_removeInvestmentAccount() {
        // SetUp: Customer "Aliza" (address "123 Main St") currently has 2 investment accounts:
        // "INV001" with no stock transaction, "INV002" (balance $100) with 1 stock transaction
        customer.setName("Aliza");
        customer.setAddress("123 Main St");
        customer.addInvestmentAccount("INV001");
        customer.addInvestmentAccount("INV002");
        
        // Add balance and stock transaction to INV002
        InvestmentAccount inv002 = (InvestmentAccount) customer.findAccountById("INV002");
        inv002.deposit(100.0);
        inv002.buyStock("ABS", 10, 5.0);
        
        // Verify initial state
        assertEquals(2, customer.getAccounts().size());
        assertEquals(0, ((InvestmentAccount) customer.findAccountById("INV001")).getTransactions().size());
        assertEquals(1, inv002.getTransactions().size());
        assertTrue(inv002.getBalance() > 0);
        
        // Action: Attempt to remove account "INV001"
        boolean result1 = customer.removeInvestmentAccount("INV001");
        
        // Expected Output: remove "INV001" : True
        assertTrue(result1);
        
        // Action: Attempt to remove account "INV002" 
        boolean result2 = customer.removeInvestmentAccount("INV002");
        
        // Expected Output: remove "INV002" : False
        assertFalse(result2);
        
        // Verify final state
        assertNull(customer.findAccountById("INV001"));
        assertNotNull(customer.findAccountById("INV002"));
        assertEquals(1, customer.getAccounts().size());
    }
}