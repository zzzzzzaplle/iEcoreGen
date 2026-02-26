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
        boolean result = customer.addSavingAccount("SAV001", 0.02);
        
        // Expected Output: True
        assertTrue(result);
        
        // Verify the account was added
        Account account = customer.findAccountById("SAV001");
        assertNotNull(account);
        assertTrue(account instanceof SavingAccount);
        assertEquals("SAV001", account.getId());
        assertEquals(0.02, ((SavingAccount) account).getInterestRate(), 0.001);
    }
    
    @Test
    public void testCase2_addAccountWithDuplicateID() {
        // SetUp: Customer "Mia" already holds an investment account "INV001"
        customer.setName("Mia");
        customer.setAddress("123 Main St");
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
    public void testCase3_removeExistingZeroBalanceAccount() {
        // SetUp: Customer "Doli" holds savings account "SAV002", balance $0, interest rate 3.65%
        customer.setName("Doli");
        customer.setAddress("123 Main St");
        customer.addSavingAccount("SAV002", 0.0365);
        
        // Action: Remove account "SAV002"
        boolean result = customer.removeSavingAccount("SAV002");
        
        // Expected Output: True
        assertTrue(result);
        
        // Verify customer now has no account "SAV002"
        Account account = customer.findAccountById("SAV002");
        assertNull(account);
        assertEquals(0, customer.getAccounts().size());
    }
    
    @Test
    public void testCase4_attemptToRemoveAccountThatStillHasBalance() {
        // SetUp: Customer "Liam" holds savings account "SAV003", balance $250, interest rate 3.65%
        customer.setName("Liam");
        customer.setAddress("123 Main St");
        customer.addSavingAccount("SAV003", 0.0365);
        
        // Set balance to $250
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
    public void testCase5_removeAnInvestmentAccount() {
        // SetUp: Customer "Aliza" has 2 investment accounts
        customer.setName("Aliza");
        customer.setAddress("123 Main St");
        
        // Add INV001 with no stock transaction
        customer.addInvestmentAccount("INV001");
        
        // Add INV002 with $100 balance and 1 stock transaction
        customer.addInvestmentAccount("INV002");
        InvestmentAccount inv002 = (InvestmentAccount) customer.findAccountById("INV002");
        inv002.setBalance(100.0);
        
        // Add a stock transaction to INV002
        StockTransaction transaction = new StockTransaction();
        transaction.setStock("ABS");
        transaction.setQuantity(10);
        transaction.setPrice(5.0);
        transaction.setCommission(5.0); // 10% of (10*5) = 5
        inv002.getTransactions().add(transaction);
        
        // Action: Attempt to remove account "INV001", "INV002"
        boolean result1 = customer.removeInvestmentAccount("INV001");
        boolean result2 = customer.removeInvestmentAccount("INV002");
        
        // Expected Output: remove "INV001": True, remove "INV002": False
        assertTrue(result1);
        assertFalse(result2);
        
        // Verify INV001 is removed and INV002 still exists
        assertNull(customer.findAccountById("INV001"));
        assertNotNull(customer.findAccountById("INV002"));
        assertEquals(1, customer.getAccounts().size());
    }
}