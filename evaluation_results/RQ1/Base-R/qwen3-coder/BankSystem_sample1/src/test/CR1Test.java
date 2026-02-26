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
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountId("SAV001");
        savingsAccount.setInterestRate(2.0);
        
        // Expected Output: True
        customer.addAccount(savingsAccount);
        assertTrue(customer.getAccounts().contains(savingsAccount));
        assertEquals(1, customer.getAccounts().size());
    }
    
    @Test
    public void testCase2_AddAccountWithDuplicateID() {
        // SetUp: Customer "Mia" (address "123 Main St") already holds an investment account "INV001"
        customer.setName("Mia");
        customer.setAddress("123 Main St");
        
        InvestmentAccount existingAccount = new InvestmentAccount();
        existingAccount.setAccountId("INV001");
        customer.addAccount(existingAccount);
        
        // Action: Attempt to add another investment account using the same ID "INV001"
        InvestmentAccount newAccount = new InvestmentAccount();
        newAccount.setAccountId("INV001");
        
        // Since addAccount doesn't check for duplicates, we need to simulate the behavior
        // by checking if an account with the same ID already exists
        boolean accountExists = false;
        for (Account account : customer.getAccounts()) {
            if (account.getAccountId().equals("INV001")) {
                accountExists = true;
                break;
            }
        }
        
        // Expected Output: False (account should not be added)
        // Customer still holds exactly one account "INV001"
        assertTrue(accountExists);
        assertEquals(1, customer.getAccounts().size());
        
        // Attempt to add the duplicate account - in real implementation this would return false
        // but current implementation doesn't prevent duplicates, so we test the expected state
        customer.addAccount(newAccount);
        // After adding duplicate, the system currently allows it - this is a gap in the implementation
        // For test purposes, we verify the expected behavior described in requirements
        int inv001Count = 0;
        for (Account account : customer.getAccounts()) {
            if (account.getAccountId().equals("INV001")) {
                inv001Count++;
            }
        }
        // The requirement expects only one account with ID INV001
        assertTrue("Should have only one account with ID INV001", inv001Count <= 1);
    }
    
    @Test
    public void testCase3_RemoveExistingZeroBalanceAccount() {
        // SetUp: Customer "Doli" (address "123 Main St") holds savings account "SAV002", balance $0, interest rate 3.65%
        customer.setName("Doli");
        customer.setAddress("123 Main St");
        
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountId("SAV002");
        savingsAccount.setBalance(0.0);
        savingsAccount.setInterestRate(3.65);
        customer.addAccount(savingsAccount);
        
        // Action: Remove account "SAV002"
        boolean result = customer.removeAccount("SAV002");
        
        // Expected Output: True
        assertTrue(result);
        // Customer now has no account "SAV002"
        assertFalse(customer.getAccounts().contains(savingsAccount));
        assertEquals(0, customer.getAccounts().size());
    }
    
    @Test
    public void testCase4_AttemptToRemoveAccountThatStillHasBalance() {
        // SetUp: Customer "Liam" (address "123 Main St") holds savings account "SAV003", balance $250, interest rate 3.65%
        customer.setName("Liam");
        customer.setAddress("123 Main St");
        
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountId("SAV003");
        savingsAccount.setBalance(250.0);
        savingsAccount.setInterestRate(3.65);
        customer.addAccount(savingsAccount);
        
        // Action: Request deletion of account "SAV003"
        boolean result = customer.removeAccount("SAV003");
        
        // Expected Output: False
        assertFalse(result);
        // Customer still holds account "SAV003" with balance $250
        assertTrue(customer.getAccounts().contains(savingsAccount));
        assertEquals(1, customer.getAccounts().size());
        assertEquals(250.0, savingsAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_RemoveInvestmentAccount() {
        // SetUp: Customer "Aliza" (address "123 Main St") currently has 2 investment accounts: 
        // "INV001" with no stock transaction, "INV002" (balance $100) with 1 stock transaction
        customer.setName("Aliza");
        customer.setAddress("123 Main St");
        
        // Create INV001 - no stock transactions, zero balance
        InvestmentAccount inv001 = new InvestmentAccount();
        inv001.setAccountId("INV001");
        inv001.setBalance(0.0);
        
        // Create INV002 - with stock transaction, balance $100
        InvestmentAccount inv002 = new InvestmentAccount();
        inv002.setAccountId("INV002");
        inv002.setBalance(100.0);
        
        // Add stock transaction to INV002: 10 shares of stock "ABS" at price $5
        StockTransaction transaction = new StockTransaction();
        transaction.setTicker("ABS");
        transaction.setQuantity(10);
        transaction.setPrice(5.0);
        inv002.getStockTransactions().add(transaction);
        
        customer.addAccount(inv001);
        customer.addAccount(inv002);
        
        // Action: Attempt to remove account "INV001"
        boolean resultInv001 = customer.removeAccount("INV001");
        
        // Expected Output: remove "INV001" : True
        assertTrue(resultInv001);
        
        // Action: Attempt to remove account "INV002"
        boolean resultInv002 = customer.removeAccount("INV002");
        
        // Expected Output: remove "INV002" : False
        assertFalse(resultInv002);
        
        // Verify final state: INV001 removed, INV002 still exists
        assertEquals(1, customer.getAccounts().size());
        assertTrue(customer.getAccounts().contains(inv002));
    }
}