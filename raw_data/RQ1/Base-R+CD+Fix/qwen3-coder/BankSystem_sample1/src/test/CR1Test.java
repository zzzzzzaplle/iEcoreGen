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
    public void testCase1_AddNewSavingsAccountSuccessfully() {
        // SetUp: Customer "John Doe", address "123 Main St", currently holds no accounts
        customer.setName("John Doe");
        customer.setAddress("123 Main St");
        
        // Action: Add a savings account with ID "SAV001", interest rate 2%
        boolean result = customer.addSavingAccount("SAV001", 2.0);
        
        // Expected Output: True
        assertTrue("Should successfully add new savings account", result);
        assertEquals("Customer should have exactly 1 account", 1, customer.getAccounts().size());
        assertNotNull("Account should exist", customer.findAccountById("SAV001"));
        assertTrue("Account should be a SavingAccount", customer.findAccountById("SAV001") instanceof SavingAccount);
    }
    
    @Test
    public void testCase2_AddAccountWithDuplicateID() {
        // SetUp: Customer "Mia" (address "123 Main St") already holds an investment account "INV001"
        customer.setName("Mia");
        customer.setAddress("123 Main St");
        customer.addInvestmentAccount("INV001");
        
        // Verify initial setup
        assertEquals("Customer should have exactly 1 account initially", 1, customer.getAccounts().size());
        assertNotNull("INV001 account should exist", customer.findAccountById("INV001"));
        
        // Action: Attempt to add another investment account using the same ID "INV001"
        boolean result = customer.addInvestmentAccount("INV001");
        
        // Expected Output: False and Customer still holds exactly one account "INV001"
        assertFalse("Should not add account with duplicate ID", result);
        assertEquals("Customer should still have exactly 1 account", 1, customer.getAccounts().size());
        assertNotNull("INV001 account should still exist", customer.findAccountById("INV001"));
    }
    
    @Test
    public void testCase3_RemoveExistingZeroBalanceAccount() {
        // SetUp: Customer "Doli" (address "123 Main St") holds savings account "SAV002", balance $0, interest rate 3.65%
        customer.setName("Doli");
        customer.setAddress("123 Main St");
        customer.addSavingAccount("SAV002", 3.65);
        
        // Verify initial setup
        assertEquals("Customer should have exactly 1 account initially", 1, customer.getAccounts().size());
        assertNotNull("SAV002 account should exist", customer.findAccountById("SAV002"));
        assertEquals("SAV002 account should have zero balance", 0.0, customer.findAccountById("SAV002").getBalance(), 0.001);
        
        // Action: Remove account "SAV002"
        boolean result = customer.removeSavingAccount("SAV002");
        
        // Expected Output: True and Customer now has no account "SAV002"
        assertTrue("Should successfully remove zero-balance savings account", result);
        assertEquals("Customer should have no accounts after removal", 0, customer.getAccounts().size());
        assertNull("SAV002 account should no longer exist", customer.findAccountById("SAV002"));
    }
    
    @Test
    public void testCase4_AttemptToRemoveAccountThatStillHasBalance() {
        // SetUp: Customer "Liam" (address "123 Main St") holds savings account "SAV003", balance $250, interest rate 3.65%
        customer.setName("Liam");
        customer.setAddress("123 Main St");
        customer.addSavingAccount("SAV003", 3.65);
        
        // Deposit $250 to create non-zero balance
        Account account = customer.findAccountById("SAV003");
        account.deposit(250.0);
        
        // Verify initial setup
        assertEquals("Customer should have exactly 1 account initially", 1, customer.getAccounts().size());
        assertNotNull("SAV003 account should exist", account);
        assertEquals("SAV003 account should have $250 balance", 250.0, account.getBalance(), 0.001);
        
        // Action: Request deletion of account "SAV003"
        boolean result = customer.removeSavingAccount("SAV003");
        
        // Expected Output: False and Customer still holds account "SAV003" with balance $250
        assertFalse("Should not remove account with non-zero balance", result);
        assertEquals("Customer should still have exactly 1 account", 1, customer.getAccounts().size());
        assertNotNull("SAV003 account should still exist", customer.findAccountById("SAV003"));
        assertEquals("SAV003 account should still have $250 balance", 250.0, customer.findAccountById("SAV003").getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_RemoveInvestmentAccount() {
        // SetUp: Customer "Aliza" (address "123 Main St") currently has 2 investment accounts: 
        // "INV001" with no stock transaction, "INV002" (balance $100) with 1 stock transaction
        customer.setName("Aliza");
        customer.setAddress("123 Main St");
        customer.addInvestmentAccount("INV001");
        customer.addInvestmentAccount("INV002");
        
        // Deposit $100 to INV002 and add stock transaction
        InvestmentAccount inv002 = (InvestmentAccount) customer.findAccountById("INV002");
        inv002.deposit(100.0);
        inv002.buyStock("ABS", 10, 5.0);
        
        // Verify initial setup
        assertEquals("Customer should have exactly 2 accounts initially", 2, customer.getAccounts().size());
        assertNotNull("INV001 account should exist", customer.findAccountById("INV001"));
        assertNotNull("INV002 account should exist", customer.findAccountById("INV002"));
        
        // Action: Attempt to remove account "INV001" and "INV002"
        boolean resultINV001 = customer.removeInvestmentAccount("INV001");
        boolean resultINV002 = customer.removeInvestmentAccount("INV002");
        
        // Expected Output: remove "INV001" : True, remove "INV002" : False
        assertTrue("Should successfully remove investment account with zero balance and no transactions", resultINV001);
        assertFalse("Should not remove investment account with balance and transactions", resultINV002);
        
        // Verify final state
        assertEquals("Customer should have exactly 1 account after removal", 1, customer.getAccounts().size());
        assertNull("INV001 account should no longer exist", customer.findAccountById("INV001"));
        assertNotNull("INV002 account should still exist", customer.findAccountById("INV002"));
    }
}