import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Map;

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
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setId("SAV001");
        savingsAccount.setInterestRate(2.0);
        
        // Expected Output: True
        customer.addAccount(savingsAccount);
        Map<String, Account> accounts = customer.getAccounts();
        assertTrue(accounts.containsKey("SAV001"));
        assertEquals(1, accounts.size());
        Account retrievedAccount = accounts.get("SAV001");
        assertTrue(retrievedAccount instanceof SavingsAccount);
        assertEquals(2.0, ((SavingsAccount) retrievedAccount).getInterestRate(), 0.001);
    }
    
    @Test
    public void testCase2_AddAccountWithDuplicateID() {
        // SetUp: Customer "Mia" (address "123 Main St") already holds an investment account "INV001"
        customer.setName("Mia");
        customer.setAddress("123 Main St");
        
        InvestmentAccount existingAccount = new InvestmentAccount();
        existingAccount.setId("INV001");
        customer.addAccount(existingAccount);
        
        // Action: Attempt to add another investment account using the same ID "INV001"
        InvestmentAccount newAccount = new InvestmentAccount();
        newAccount.setId("INV001");
        customer.addAccount(newAccount);
        
        // Expected Output: False - Customer still holds exactly one account "INV001"
        Map<String, Account> accounts = customer.getAccounts();
        assertEquals(1, accounts.size());
        assertTrue(accounts.containsKey("INV001"));
        // The original account should still be there (not replaced by the new one with same ID)
        assertSame(existingAccount, accounts.get("INV001"));
    }
    
    @Test
    public void testCase3_RemoveExistingZeroBalanceAccount() {
        // SetUp: Customer "Doli" (address "123 Main St") holds savings account "SAV002", balance $0, interest rate 3.65%
        customer.setName("Doli");
        customer.setAddress("123 Main St");
        
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setId("SAV002");
        savingsAccount.setBalance(0.0);
        savingsAccount.setInterestRate(3.65);
        customer.addAccount(savingsAccount);
        
        // Action: Remove account "SAV002"
        boolean result = customer.removeAccount("SAV002");
        
        // Expected Output: True - Customer now has no account "SAV002"
        assertTrue(result);
        Map<String, Account> accounts = customer.getAccounts();
        assertFalse(accounts.containsKey("SAV002"));
        assertEquals(0, accounts.size());
    }
    
    @Test
    public void testCase4_AttemptToRemoveAccountThatStillHasBalance() {
        // SetUp: Customer "Liam" (address "123 Main St") holds savings account "SAV003", balance $250, interest rate 3.65%
        customer.setName("Liam");
        customer.setAddress("123 Main St");
        
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setId("SAV003");
        savingsAccount.setBalance(250.0);
        savingsAccount.setInterestRate(3.65);
        customer.addAccount(savingsAccount);
        
        // Action: Request deletion of account "SAV003"
        boolean result = customer.removeAccount("SAV003");
        
        // Expected Output: False - Customer still holds account "SAV003" with balance $250
        assertFalse(result);
        Map<String, Account> accounts = customer.getAccounts();
        assertTrue(accounts.containsKey("SAV003"));
        assertEquals(1, accounts.size());
        Account retrievedAccount = accounts.get("SAV003");
        assertEquals(250.0, retrievedAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_RemoveInvestmentAccount() {
        // SetUp: Customer "Aliza" (address "123 Main St") currently has 2 investment accounts: 
        // "INV001" with no stock transaction, "INV002" (balance $100) with 1 stock transaction
        customer.setName("Aliza");
        customer.setAddress("123 Main St");
        
        // Create INV001 - no stock transactions, zero balance
        InvestmentAccount inv001 = new InvestmentAccount();
        inv001.setId("INV001");
        inv001.setBalance(0.0);
        customer.addAccount(inv001);
        
        // Create INV002 - with stock transaction, balance $100
        InvestmentAccount inv002 = new InvestmentAccount();
        inv002.setId("INV002");
        inv002.setBalance(100.0);
        
        StockTransaction transaction = new StockTransaction();
        transaction.setTicker("ABS");
        transaction.setQuantity(10);
        transaction.setPrice(5.0);
        inv002.getStockTransactions().add(transaction);
        
        customer.addAccount(inv002);
        
        // Action: Attempt to remove account "INV001", "INV002"
        boolean resultInv001 = customer.removeAccount("INV001");
        boolean resultInv002 = customer.removeAccount("INV002");
        
        // Expected Output: remove "INV001" : True, remove "INV002" : False
        assertTrue(resultInv001);
        assertFalse(resultInv002);
        
        Map<String, Account> accounts = customer.getAccounts();
        assertFalse(accounts.containsKey("INV001")); // Should be removed
        assertTrue(accounts.containsKey("INV002")); // Should remain (has stock transactions)
        assertEquals(1, accounts.size());
    }
}