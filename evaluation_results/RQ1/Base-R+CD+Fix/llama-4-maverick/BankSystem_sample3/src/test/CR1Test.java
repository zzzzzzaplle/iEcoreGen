import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

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
        assertNotNull(customer.findAccountById("SAV001"));
        assertEquals(1, customer.getAccounts().size());
    }
    
    @Test
    public void testCase2_addAccountWithDuplicateID() {
        // SetUp: Customer "Mia" (address "123 Main St") already holds an investment account "INV001"
        customer.setName("Mia");
        customer.setAddress("123 Main St");
        customer.addInvestmentAccount("INV001");
        
        // Action: Attempt to add another investment account using the same ID "INV001"
        boolean result = customer.addInvestmentAccount("INV001");
        
        // Expected Output: False and Customer still holds exactly one account "INV001"
        assertFalse(result);
        assertEquals(1, customer.getAccounts().size());
        assertNotNull(customer.findAccountById("INV001"));
    }
    
    @Test
    public void testCase3_removeExistingZeroBalanceAccount() {
        // SetUp: Customer "Doli" (address "123 Main St") holds savings account "SAV002", balance $0, interest rate 3.65%
        customer.setName("Doli");
        customer.setAddress("123 Main St");
        customer.addSavingAccount("SAV002", 3.65);
        // Ensure balance is 0 (default value)
        
        // Action: Remove account "SAV002"
        boolean result = customer.removeSavingAccount("SAV002");
        
        // Expected Output: True and Customer now has no account "SAV002"
        assertTrue(result);
        assertNull(customer.findAccountById("SAV002"));
        assertEquals(0, customer.getAccounts().size());
    }
    
    @Test
    public void testCase4_attemptToRemoveAccountThatStillHasBalance() {
        // SetUp: Customer "Liam" (address "123 Main St") holds savings account "SAV003", balance $250, interest rate 3.65%
        customer.setName("Liam");
        customer.setAddress("123 Main St");
        customer.addSavingAccount("SAV003", 3.65);
        // Set balance to $250
        customer.findAccountById("SAV003").setBalance(250.0);
        
        // Action: Request deletion of account "SAV003"
        boolean result = customer.removeSavingAccount("SAV003");
        
        // Expected Output: False and Customer still holds account "SAV003" with balance $250
        assertFalse(result);
        assertNotNull(customer.findAccountById("SAV003"));
        assertEquals(250.0, customer.findAccountById("SAV003").getBalance(), 0.001);
        assertEquals(1, customer.getAccounts().size());
    }
    
    @Test
    public void testCase5_removeInvestmentAccount() {
        // SetUp: Customer "Aliza" (address "123 Main St") currently has 2 investment accounts:
        // "INV001" with no stock transaction, "INV002" (balance $100) with 1 stock transaction
        customer.setName("Aliza");
        customer.setAddress("123 Main St");
        
        // Create INV001 account (no transactions, zero balance by default)
        customer.addInvestmentAccount("INV001");
        
        // Create INV002 account with balance $100 and 1 stock transaction
        customer.addInvestmentAccount("INV002");
        InvestmentAccount inv2 = (InvestmentAccount) customer.findAccountById("INV002");
        inv2.setBalance(100.0);
        inv2.buyStock("ABS", 10, 5.0); // This adds a stock transaction
        
        // Action: Attempt to remove account "INV001", "INV002"
        boolean result1 = customer.removeInvestmentAccount("INV001");
        boolean result2 = customer.removeInvestmentAccount("INV002");
        
        // Expected Output: remove "INV001" : True, remove "INV002" : False
        assertTrue(result1);
        assertFalse(result2);
        
        // Verify INV001 is removed
        assertNull(customer.findAccountById("INV001"));
        
        // Verify INV002 still exists
        assertNotNull(customer.findAccountById("INV002"));
        assertEquals(1, customer.getAccounts().size());
    }
}