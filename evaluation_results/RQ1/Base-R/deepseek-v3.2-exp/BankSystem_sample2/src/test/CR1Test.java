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
        boolean result = customer.addAccount("SAV001");
        
        // Expected Output: True
        assertTrue("Adding account SAV001 should return true", result);
        
        // Verify the account was added with correct properties
        assertEquals("Customer should have exactly 1 account", 1, customer.getAccounts().size());
        Account account = customer.getAccounts().get(0);
        assertEquals("Account ID should be SAV001", "SAV001", account.getId());
        assertEquals("Account balance should be 0.0", 0.0, account.getBalance(), 0.001);
        assertTrue("Account should be a SavingsAccount", account instanceof SavingsAccount);
    }
    
    @Test
    public void testCase2_AddAccountWithDuplicateID() {
        // SetUp: Customer "Mia" (address "123 Main St") already holds an investment account "INV001"
        customer.setName("Mia");
        customer.setAddress("123 Main St");
        
        // Create and add initial investment account
        InvestmentAccount existingAccount = new InvestmentAccount();
        existingAccount.setId("INV001");
        existingAccount.setBalance(100.0);
        List<Account> accounts = new ArrayList<>();
        accounts.add(existingAccount);
        customer.setAccounts(accounts);
        
        // Action: Attempt to add another investment account using the same ID "INV001"
        boolean result = customer.addAccount("INV001");
        
        // Expected Output: False
        assertFalse("Adding duplicate account ID should return false", result);
        
        // Expected Output: Customer still holds exactly one account "INV001"
        assertEquals("Customer should still have exactly 1 account", 1, customer.getAccounts().size());
        assertEquals("Account ID should remain INV001", "INV001", customer.getAccounts().get(0).getId());
    }
    
    @Test
    public void testCase3_RemoveExistingZeroBalanceAccount() {
        // SetUp: Customer "Doli" (address "123 Main St") holds savings account "SAV002", balance $0, interest rate 3.65%
        customer.setName("Doli");
        customer.setAddress("123 Main St");
        
        SavingsAccount account = new SavingsAccount();
        account.setId("SAV002");
        account.setBalance(0.0);
        account.setInterestRate(3.65);
        
        List<Account> accounts = new ArrayList<>();
        accounts.add(account);
        customer.setAccounts(accounts);
        
        // Action: Remove account "SAV002"
        boolean result = customer.removeAccount("SAV002");
        
        // Expected Output: True
        assertTrue("Removing zero-balance account should return true", result);
        
        // Expected Output: Customer now has no account "SAV002"
        assertTrue("Customer should have no accounts after removal", customer.getAccounts().isEmpty());
    }
    
    @Test
    public void testCase4_AttemptToRemoveAccountThatStillHasBalance() {
        // SetUp: Customer "Liam" (address "123 Main St") holds savings account "SAV003", balance $250, interest rate 3.65%
        customer.setName("Liam");
        customer.setAddress("123 Main St");
        
        SavingsAccount account = new SavingsAccount();
        account.setId("SAV003");
        account.setBalance(250.0);
        account.setInterestRate(3.65);
        
        List<Account> accounts = new ArrayList<>();
        accounts.add(account);
        customer.setAccounts(accounts);
        
        // Action: Request deletion of account "SAV003"
        boolean result = customer.removeAccount("SAV003");
        
        // Expected Output: False
        assertFalse("Removing account with balance should return false", result);
        
        // Expected Output: Customer still holds account "SAV003" with balance $250
        assertEquals("Customer should still have exactly 1 account", 1, customer.getAccounts().size());
        Account remainingAccount = customer.getAccounts().get(0);
        assertEquals("Account ID should remain SAV003", "SAV003", remainingAccount.getId());
        assertEquals("Account balance should remain 250.0", 250.0, remainingAccount.getBalance(), 0.001);
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
        
        // Create INV002 - with stock transaction, $100 balance
        InvestmentAccount inv002 = new InvestmentAccount();
        inv002.setId("INV002");
        inv002.setBalance(100.0);
        
        // Add stock transaction to INV002
        StockTransaction transaction = new StockTransaction();
        transaction.setTicker("ABS");
        transaction.setQuantity(10);
        transaction.setPrice(5.0);
        transaction.setCommission(5.0);
        List<StockTransaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        inv002.setStockTransactions(transactions);
        
        List<Account> accounts = new ArrayList<>();
        accounts.add(inv001);
        accounts.add(inv002);
        customer.setAccounts(accounts);
        
        // Action: Attempt to remove account "INV001"
        boolean resultInv001 = customer.removeAccount("INV001");
        
        // Expected Output: remove "INV001" : True
        assertTrue("Removing INV001 with no balance and no transactions should return true", resultInv001);
        
        // Action: Attempt to remove account "INV002"
        boolean resultInv002 = customer.removeAccount("INV002");
        
        // Expected Output: remove "INV002" : False
        assertFalse("Removing INV002 with stock transactions should return false", resultInv002);
        
        // Verify final state: INV001 removed, INV002 remains
        assertEquals("Customer should have exactly 1 account remaining", 1, customer.getAccounts().size());
        assertEquals("Remaining account should be INV002", "INV002", customer.getAccounts().get(0).getId());
    }
}