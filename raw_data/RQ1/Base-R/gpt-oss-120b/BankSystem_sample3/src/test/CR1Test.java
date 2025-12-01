import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.math.BigDecimal;
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
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setId("SAV001");
        savingsAccount.setInterestRate(0.02);
        
        // Expected Output: True
        customer.addAccount(savingsAccount);
        assertEquals("Customer should have 1 account after adding", 1, customer.getAccounts().size());
        assertEquals("Account ID should match", "SAV001", customer.getAccounts().get(0).getId());
        assertTrue("Account should be SavingsAccount", customer.getAccounts().get(0) instanceof SavingsAccount);
    }
    
    @Test
    public void testCase2_addAccountWithDuplicateID() {
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
        
        // Expected Output: False (method always returns void, so we check customer state)
        // Customer still holds exactly one account "INV001"
        assertEquals("Customer should still have exactly 1 account", 1, customer.getAccounts().size());
        assertEquals("Account ID should still be INV001", "INV001", customer.getAccounts().get(0).getId());
    }
    
    @Test
    public void testCase3_removeExistingZeroBalanceAccount() {
        // SetUp: Customer "Doli" (address "123 Main St") holds savings account "SAV002", balance $0, interest rate 3.65%
        customer.setName("Doli");
        customer.setAddress("123 Main St");
        
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setId("SAV002");
        savingsAccount.setBalance(BigDecimal.ZERO);
        savingsAccount.setInterestRate(0.0365);
        customer.addAccount(savingsAccount);
        
        // Action: Remove account "SAV002"
        boolean result = customer.removeAccount("SAV002");
        
        // Expected Output: True
        assertTrue("Remove operation should return true", result);
        // Customer now has no account "SAV002"
        assertEquals("Customer should have no accounts after removal", 0, customer.getAccounts().size());
        assertNull("Account should not be found after removal", customer.getAccountById("SAV002"));
    }
    
    @Test
    public void testCase4_attemptToRemoveAccountThatStillHasBalance() {
        // SetUp: Customer "Liam" (address "123 Main St") holds savings account "SAV003", balance $250, interest rate 3.65%
        customer.setName("Liam");
        customer.setAddress("123 Main St");
        
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setId("SAV003");
        savingsAccount.setBalance(new BigDecimal("250.00"));
        savingsAccount.setInterestRate(0.0365);
        customer.addAccount(savingsAccount);
        
        // Action: Request deletion of account "SAV003"
        boolean result = customer.removeAccount("SAV003");
        
        // Expected Output: False
        assertFalse("Remove operation should return false", result);
        // Customer still holds account "SAV003" with balance $250
        assertEquals("Customer should still have 1 account", 1, customer.getAccounts().size());
        Account remainingAccount = customer.getAccountById("SAV003");
        assertNotNull("Account should still exist", remainingAccount);
        assertEquals("Balance should remain $250", new BigDecimal("250.00"), remainingAccount.getBalance());
    }
    
    @Test
    public void testCase5_removeInvestmentAccount() {
        // SetUp: Customer "Aliza" (address "123 Main St") currently has 2 investment accounts: 
        // "INV001" with no stock transaction, "INV002" (balance $100) with 1 stock transaction
        customer.setName("Aliza");
        customer.setAddress("123 Main St");
        
        // Create INV001 - no stock transactions, zero balance
        InvestmentAccount inv001 = new InvestmentAccount();
        inv001.setId("INV001");
        inv001.setBalance(BigDecimal.ZERO);
        customer.addAccount(inv001);
        
        // Create INV002 - with stock transaction, balance $100
        InvestmentAccount inv002 = new InvestmentAccount();
        inv002.setId("INV002");
        inv002.setBalance(new BigDecimal("100.00"));
        
        // Add stock transaction to INV002
        StockTransaction transaction = new StockTransaction();
        transaction.setTicker("ABS");
        transaction.setQuantity(10);
        transaction.setPurchasePrice(5.0);
        List<StockTransaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        inv002.setTransactions(transactions);
        
        customer.addAccount(inv002);
        
        // Action: Attempt to remove account "INV001"
        boolean resultInv001 = customer.removeAccount("INV001");
        
        // Expected Output: remove "INV001" : True
        assertTrue("Removing INV001 should return true", resultInv001);
        
        // Action: Attempt to remove account "INV002"
        boolean resultInv002 = customer.removeAccount("INV002");
        
        // Expected Output: remove "INV002" : False
        assertFalse("Removing INV002 should return false", resultInv002);
        
        // Verify final state
        assertEquals("Customer should have 1 account remaining", 1, customer.getAccounts().size());
        assertNull("INV001 should not be found", customer.getAccountById("INV001"));
        assertNotNull("INV002 should still exist", customer.getAccountById("INV002"));
    }
}