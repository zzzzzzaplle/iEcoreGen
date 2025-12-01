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
    public void testCase1_AddANewSavingsAccountSuccessfully() {
        // SetUp: Customer "John Doe", address "123 Main St", currently holds no accounts
        customer.setName("John Doe");
        
        // Action: Add a savings account with ID "SAV001", interest rate 2%
        boolean result = customer.addSavingAccount("SAV001", 0.02);
        
        // Expected Output: True
        assertTrue(result);
        
        // Verify the account was added
        Account account = customer.findAccountById("SAV001");
        assertNotNull(account);
        assertTrue(account instanceof SavingAccount);
        assertEquals("SAV001", account.getId());
        assertEquals(0.0, account.getBalance(), 0.001);
        assertEquals(0.02, ((SavingAccount) account).getInterestRate(), 0.001);
    }
    
    @Test
    public void testCase2_AddAccountWithDuplicateID() {
        // SetUp: Customer "Mia" already holds an investment account "INV001"
        customer.setName("Mia");
        customer.addInvestmentAccount("INV001");
        
        // Action: Attempt to add another investment account using the same ID "INV001"
        boolean result = customer.addInvestmentAccount("INV001");
        
        // Expected Output: False
        assertFalse(result);
        
        // Verify customer still holds exactly one account "INV001"
        assertEquals(1, customer.getAccounts().size());
        Account account = customer.findAccountById("INV001");
        assertNotNull(account);
        assertTrue(account instanceof InvestmentAccount);
    }
    
    @Test
    public void testCase3_RemoveExistingZeroBalanceAccount() {
        // SetUp: Customer "Doli" holds savings account "SAV002", balance $0, interest rate 3.65%
        customer.setName("Doli");
        customer.addSavingAccount("SAV002", 0.0365);
        // Ensure balance is $0 (it should be by default)
        Account account = customer.findAccountById("SAV002");
        account.setBalance(0.0);
        
        // Action: Remove account "SAV002"
        boolean result = customer.removeSavingAccount("SAV002");
        
        // Expected Output: True
        assertTrue(result);
        
        // Verify customer now has no account "SAV002"
        assertNull(customer.findAccountById("SAV002"));
        assertEquals(0, customer.getAccounts().size());
    }
    
    @Test
    public void testCase4_AttemptToRemoveAccountThatStillHasBalance() {
        // SetUp: Customer "Liam" holds savings account "SAV003", balance $250, interest rate 3.65%
        customer.setName("Liam");
        customer.addSavingAccount("SAV003", 0.0365);
        Account account = customer.findAccountById("SAV003");
        account.setBalance(250.0);
        
        // Action: Request deletion of account "SAV003"
        boolean result = customer.removeSavingAccount("SAV003");
        
        // Expected Output: False
        assertFalse(result);
        
        // Verify customer still holds account "SAV003" with balance $250
        Account remainingAccount = customer.findAccountById("SAV003");
        assertNotNull(remainingAccount);
        assertEquals(250.0, remainingAccount.getBalance(), 0.001);
        assertEquals(1, customer.getAccounts().size());
    }
    
    @Test
    public void testCase5_RemoveAnInvestmentAccount() {
        // SetUp: Customer "Aliza" has 2 investment accounts
        customer.setName("Aliza");
        
        // "INV001" with no stock transaction
        customer.addInvestmentAccount("INV001");
        Account account1 = customer.findAccountById("INV001");
        account1.setBalance(0.0); // Zero balance
        
        // "INV002" (balance $100) with 1 stock transaction (10 shares of stock "ABS" at price $5)
        customer.addInvestmentAccount("INV002");
        InvestmentAccount account2 = (InvestmentAccount) customer.findAccountById("INV002");
        account2.setBalance(100.0);
        account2.buyStock("ABS", 10, 5.0); // This will create a transaction
        
        // Action: Attempt to remove account "INV001", "INV002"
        boolean result1 = customer.removeInvestmentAccount("INV001");
        boolean result2 = customer.removeInvestmentAccount("INV002");
        
        // Expected Output:
        // remove "INV001": True
        assertTrue(result1);
        
        // remove "INV002": False
        assertFalse(result2);
        
        // Verify that only INV001 was removed
        assertNull(customer.findAccountById("INV001"));
        assertNotNull(customer.findAccountById("INV002"));
        assertEquals(1, customer.getAccounts().size());
    }
}