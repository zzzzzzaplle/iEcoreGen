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
        assertTrue("Account should be successfully added", customer.getAccounts().contains(savingsAccount));
        assertEquals("Customer should have exactly one account", 1, customer.getAccounts().size());
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
        InvestmentAccount duplicateAccount = new InvestmentAccount();
        duplicateAccount.setAccountId("INV001");
        customer.addAccount(duplicateAccount);
        
        // Expected Output: False - Customer still holds exactly one account "INV001"
        assertEquals("Customer should still have exactly one account", 1, customer.getAccounts().size());
        assertTrue("Customer should still hold the original account", customer.getAccounts().contains(existingAccount));
        assertFalse("Customer should not have the duplicate account", customer.getAccounts().contains(duplicateAccount));
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
        
        // Expected Output: True - Customer now has no account "SAV002"
        assertTrue("Remove operation should be successful", result);
        assertEquals("Customer should have no accounts after removal", 0, customer.getAccounts().size());
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
        
        // Expected Output: False - Customer still holds account "SAV003" with balance $250
        assertFalse("Remove operation should fail when account has balance", result);
        assertEquals("Customer should still have one account", 1, customer.getAccounts().size());
        assertEquals("Account balance should remain unchanged", 250.0, customer.getAccounts().get(0).getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_RemoveInvestmentAccount() {
        // SetUp: Customer "Aliza" (address "123 Main St") currently has 2 investment accounts:
        // "INV001" with no stock transaction, "INV002" (balance $100) with 1 stock transaction (10 shares of stock "ABS" at price $5)
        customer.setName("Aliza");
        customer.setAddress("123 Main St");
        
        // Create INV001 - no stock transactions, zero balance
        InvestmentAccount inv001 = new InvestmentAccount();
        inv001.setAccountId("INV001");
        inv001.setBalance(0.0);
        customer.addAccount(inv001);
        
        // Create INV002 - has stock transaction, balance $100
        InvestmentAccount inv002 = new InvestmentAccount();
        inv002.setAccountId("INV002");
        inv002.setBalance(100.0);
        
        // Add stock transaction to INV002
        StockTransaction transaction = new StockTransaction();
        transaction.setTicker("ABS");
        transaction.setQuantity(10);
        transaction.setPrice(5.0);
        transaction.setCommission(5.0); // 10% commission on $50 stock cost
        
        List<StockTransaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        inv002.setStockTransactions(transactions);
        
        customer.addAccount(inv002);
        
        // Action: Attempt to remove account "INV001", "INV002"
        boolean removeInv001Result = customer.removeAccount("INV001");
        boolean removeInv002Result = customer.removeAccount("INV002");
        
        // Expected Output: remove "INV001" : True, remove "INV002" : False
        assertTrue("INV001 should be successfully removed", removeInv001Result);
        assertFalse("INV002 should not be removed due to stock transactions", removeInv002Result);
        assertEquals("Customer should have one account remaining", 1, customer.getAccounts().size());
        assertEquals("Remaining account should be INV002", "INV002", customer.getAccounts().get(0).getAccountId());
    }
}