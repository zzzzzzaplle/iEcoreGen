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
    }
    
    @Test
    public void testCase1_addNewSavingsAccountSuccessfully() {
        // SetUp: Customer "John Doe", address "123 Main St", currently holds no accounts
        customer.setName("John Doe");
        customer.setAddress("123 Main St");
        
        // Action: Add a savings account with ID "SAV001", interest rate 2%
        SavingsAccount savingsAccount = new SavingsAccount("SAV001", 0.0, 2.0);
        customer.addAccount(savingsAccount);
        
        // Expected Output: True (Account added successfully)
        assertEquals(1, customer.getAccounts().size());
        assertEquals("SAV001", customer.getAccounts().get(0).getId());
    }
    
    @Test
    public void testCase2_addAccountWithDuplicateID() {
        // SetUp: Customer "Mia" (address "123 Main St") already holds an investment account "INV001"
        customer.setName("Mia");
        customer.setAddress("123 Main St");
        InvestmentAccount existingAccount = new InvestmentAccount("INV001", 1000.0);
        customer.addAccount(existingAccount);
        
        // Action: Attempt to add another investment account using the same ID "INV001"
        InvestmentAccount duplicateAccount = new InvestmentAccount("INV001", 500.0);
        customer.addAccount(duplicateAccount);
        
        // Expected Output: False - Customer still holds exactly one account "INV001"
        assertEquals(1, customer.getAccounts().size());
        assertEquals("INV001", customer.getAccounts().get(0).getId());
        assertEquals(1000.0, customer.getAccounts().get(0).getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_removeExistingZeroBalanceAccount() {
        // SetUp: Customer "Doli" (address "123 Main St") holds savings account "SAV002", balance $0, interest rate 3.65%
        customer.setName("Doli");
        customer.setAddress("123 Main St");
        SavingsAccount savingsAccount = new SavingsAccount("SAV002", 0.0, 3.65);
        customer.addAccount(savingsAccount);
        
        // Action: Remove account "SAV002"
        boolean result = customer.removeAccount("SAV002");
        
        // Expected Output: True - Customer now has no account "SAV002"
        assertTrue(result);
        assertEquals(0, customer.getAccounts().size());
    }
    
    @Test
    public void testCase4_attemptToRemoveAccountThatStillHasBalance() {
        // SetUp: Customer "Liam" (address "123 Main St") holds savings account "SAV003", balance $250, interest rate 3.65%
        customer.setName("Liam");
        customer.setAddress("123 Main St");
        SavingsAccount savingsAccount = new SavingsAccount("SAV003", 250.0, 3.65);
        customer.addAccount(savingsAccount);
        
        // Action: Request deletion of account "SAV003"
        boolean result = customer.removeAccount("SAV003");
        
        // Expected Output: False - Customer still holds account "SAV003" with balance $250
        assertFalse(result);
        assertEquals(1, customer.getAccounts().size());
        assertEquals("SAV003", customer.getAccounts().get(0).getId());
        assertEquals(250.0, customer.getAccounts().get(0).getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_removeInvestmentAccount() {
        // SetUp: Customer "Aliza" (address "123 Main St") currently has 2 investment accounts: 
        // "INV001" with no stock transaction, "INV002" (balance $100) with 1 stock transaction
        customer.setName("Aliza");
        customer.setAddress("123 Main St");
        
        InvestmentAccount inv001 = new InvestmentAccount("INV001", 0.0);
        InvestmentAccount inv002 = new InvestmentAccount("INV002", 100.0);
        
        // Add stock transaction to INV002
        inv002.buyStocks("ABS", 10, 5.0);
        
        customer.addAccount(inv001);
        customer.addAccount(inv002);
        
        // Action: Attempt to remove account "INV001"
        boolean resultInv001 = customer.removeAccount("INV001");
        
        // Action: Attempt to remove account "INV002"
        boolean resultInv002 = customer.removeAccount("INV002");
        
        // Expected Output: remove "INV001" : True, remove "INV002" : False
        assertTrue(resultInv001);
        assertFalse(resultInv002);
        
        // Verify INV001 was removed but INV002 remains
        assertEquals(1, customer.getAccounts().size());
        assertEquals("INV002", customer.getAccounts().get(0).getId());
    }
}