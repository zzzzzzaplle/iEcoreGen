import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Customer customer;
    
    @Before
    public void setUp() {
        customer = new Customer();
        customer.setAddress("123 Main St");
    }
    
    @Test
    public void testCase1_addNewSavingsAccountSuccessfully() {
        // SetUp: Customer "John Doe", address "123 Main St", currently holds no accounts
        customer.setName("John Doe");
        
        // Action: Add a savings account with ID "SAV001", interest rate 2%
        boolean result = customer.addSavingAccount("SAV001", 0.02);
        
        // Expected Output: True
        assertTrue(result);
        assertEquals(1, customer.getAccounts().size());
        Account account = customer.findAccountById("SAV001");
        assertNotNull(account);
        assertTrue(account instanceof SavingAccount);
        assertEquals(0.02, ((SavingAccount) account).getInterestRate(), 0.001);
    }
    
    @Test
    public void testCase2_addAccountWithDuplicateID() {
        // SetUp: Customer "Mia" already holds an investment account "INV001"
        customer.setName("Mia");
        customer.addInvestmentAccount("INV001");
        
        // Action: Attempt to add another investment account using the same ID "INV001"
        boolean result = customer.addInvestmentAccount("INV001");
        
        // Expected Output: False, Customer still holds exactly one account "INV001"
        assertFalse(result);
        assertEquals(1, customer.getAccounts().size());
        Account account = customer.findAccountById("INV001");
        assertNotNull(account);
    }
    
    @Test
    public void testCase3_removeExistingZeroBalanceAccount() {
        // SetUp: Customer "Doli" holds savings account "SAV002", balance $0, interest rate 3.65%
        customer.setName("Doli");
        customer.addSavingAccount("SAV002", 0.0365);
        
        // Action: Remove account "SAV002"
        boolean result = customer.removeSavingAccount("SAV002");
        
        // Expected Output: True, Customer now has no account "SAV002"
        assertTrue(result);
        assertEquals(0, customer.getAccounts().size());
        Account account = customer.findAccountById("SAV002");
        assertNull(account);
    }
    
    @Test
    public void testCase4_attemptToRemoveAccountThatStillHasBalance() {
        // SetUp: Customer "Liam" holds savings account "SAV003", balance $250, interest rate 3.65%
        customer.setName("Liam");
        customer.addSavingAccount("SAV003", 0.0365);
        Account account = customer.findAccountById("SAV003");
        account.setBalance(250.0);
        
        // Action: Request deletion of account "SAV003"
        boolean result = customer.removeSavingAccount("SAV003");
        
        // Expected Output: False, Customer still holds account "SAV003" with balance $250
        assertFalse(result);
        assertEquals(1, customer.getAccounts().size());
        Account remainingAccount = customer.findAccountById("SAV003");
        assertNotNull(remainingAccount);
        assertEquals(250.0, remainingAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_removeAnInvestmentAccount() {
        // SetUp: Customer "Aliza" has 2 investment accounts
        customer.setName("Aliza");
        
        // INV001 with no stock transaction
        customer.addInvestmentAccount("INV001");
        
        // INV002 (balance $100) with 1 stock transaction (10 shares of stock "ABS" at price $5)
        customer.addInvestmentAccount("INV002");
        InvestmentAccount inv002 = (InvestmentAccount) customer.findAccountById("INV002");
        inv002.setBalance(100.0);
        inv002.buyStock("ABS", 10, 5.0);
        
        // Action: Attempt to remove account "INV001", "INV002"
        boolean result1 = customer.removeInvestmentAccount("INV001");
        boolean result2 = customer.removeInvestmentAccount("INV002");
        
        // Expected Output: remove "INV001": True, remove "INV002": False
        assertTrue(result1);
        assertFalse(result2);
        
        // Verify INV001 was removed
        Account account1 = customer.findAccountById("INV001");
        assertNull(account1);
        
        // Verify INV002 still exists
        Account account2 = customer.findAccountById("INV002");
        assertNotNull(account2);
    }
}