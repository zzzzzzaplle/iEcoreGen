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
        customer.setName("Test Customer");
        customer.setAddress("123 Main St");
    }
    
    @Test
    public void testCase1_addNewSavingsAccountSuccessfully() {
        // SetUp: Customer "John Doe", address "123 Main St", currently holds no accounts.
        Customer john = new Customer();
        john.setName("John Doe");
        john.setAddress("123 Main St");
        
        // Action: Add a savings account with ID "SAV001", interest rate 2%.
        boolean result = john.addSavingAccount("SAV001", 0.02);
        
        // Expected Output: True
        assertTrue(result);
        assertEquals(1, john.getAccounts().size());
        assertNotNull(john.findAccountById("SAV001"));
    }
    
    @Test
    public void testCase2_addAccountWithDuplicateID() {
        // SetUp: Customer "Mia" (address "123 Main St") already holds an investment account "INV001".
        Customer mia = new Customer();
        mia.setName("Mia");
        mia.setAddress("123 Main St");
        mia.addInvestmentAccount("INV001");
        
        // Action: Attempt to add another investment account using the same ID "INV001".
        boolean result = mia.addInvestmentAccount("INV001");
        
        // Expected Output: False
        assertFalse(result);
        // Customer still holds exactly one account "INV001".
        assertEquals(1, mia.getAccounts().size());
        assertNotNull(mia.findAccountById("INV001"));
    }
    
    @Test
    public void testCase3_removeExistingZeroBalanceAccount() {
        // SetUp: Customer "Doli" (address "123 Main St") holds savings account "SAV002", balance $0, interest rate 3.65%.
        Customer doli = new Customer();
        doli.setName("Doli");
        doli.setAddress("123 Main St");
        doli.addSavingAccount("SAV002", 0.0365);
        
        // Action: Remove account "SAV002".
        boolean result = doli.removeSavingAccount("SAV002");
        
        // Expected Output: True
        assertTrue(result);
        // Customer now has no account "SAV002".
        assertNull(doli.findAccountById("SAV002"));
        assertEquals(0, doli.getAccounts().size());
    }
    
    @Test
    public void testCase4_attemptToRemoveAccountThatStillHasBalance() {
        // SetUp: Customer "Liam" (address "123 Main St") holds savings account "SAV003", balance $250, interest rate 3.65%.
        Customer liam = new Customer();
        liam.setName("Liam");
        liam.setAddress("123 Main St");
        liam.addSavingAccount("SAV003", 0.0365);
        Account account = liam.findAccountById("SAV003");
        account.setBalance(250.0);
        
        // Action: Request deletion of account "SAV003".
        boolean result = liam.removeSavingAccount("SAV003");
        
        // Expected Output: False
        assertFalse(result);
        // Customer still holds account "SAV003" with balance $250.
        assertNotNull(liam.findAccountById("SAV003"));
        assertEquals(250.0, liam.findAccountById("SAV003").getBalance(), 0.001);
        assertEquals(1, liam.getAccounts().size());
    }
    
    @Test
    public void testCase5_removeAnInvestmentAccount() {
        // SetUp: Customer "Aliza" (address "123 Main St") currently has 2 investment accounts:
        // "INV001" with no stock transaction, "INV002" (balance $100) with 1 stock transaction
        // (10 shares of stock "ABS" at price $5).
        Customer aliza = new Customer();
        aliza.setName("Aliza");
        aliza.setAddress("123 Main St");
        
        aliza.addInvestmentAccount("INV001");
        aliza.addInvestmentAccount("INV002");
        
        // Set up INV002 with balance and transaction
        InvestmentAccount inv002 = (InvestmentAccount) aliza.findAccountById("INV002");
        inv002.setBalance(100.0);
        inv002.buyStock("ABS", 10, 5.0);
        
        // Action: Attempt to remove account "INV001", "INV002"
        boolean result1 = aliza.removeInvestmentAccount("INV001");
        boolean result2 = aliza.removeInvestmentAccount("INV002");
        
        // Expected Output:
        // remove "INV001" : True
        assertTrue(result1);
        // remove "INV002" : False
        assertFalse(result2);
    }
}