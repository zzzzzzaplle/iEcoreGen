import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    private Customer customer;
    private BankSystem bankSystem;
    
    @Before
    public void setUp() {
        bankSystem = new BankSystem();
        customer = new Customer();
    }
    
    @Test
    public void testCase1_addNewSavingsAccountSuccessfully() {
        // SetUp: Customer "John Doe", address "123 Main St", currently holds no accounts
        customer.setName("John Doe");
        customer.setAddress("123 Main St");
        
        // Action: Add a savings account with ID "SAV001", interest rate 2%
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountId("SAV001");
        savingsAccount.setInterestRate(new BigDecimal("0.02"));
        
        boolean result = customer.addAccount(savingsAccount);
        
        // Expected Output: True
        assertTrue("Should successfully add new savings account", result);
        assertEquals("Customer should have exactly 1 account", 1, customer.getAccounts().size());
        assertEquals("Account ID should be SAV001", "SAV001", customer.getAccounts().get(0).getAccountId());
    }
    
    @Test
    public void testCase2_addAccountWithDuplicateID() {
        // SetUp: Customer "Mia" (address "123 Main St") already holds an investment account "INV001"
        customer.setName("Mia");
        customer.setAddress("123 Main St");
        
        InvestmentAccount existingAccount = new InvestmentAccount();
        existingAccount.setAccountId("INV001");
        customer.addAccount(existingAccount);
        
        // Action: Attempt to add another investment account using the same ID "INV001"
        InvestmentAccount duplicateAccount = new InvestmentAccount();
        duplicateAccount.setAccountId("INV001");
        
        boolean result = customer.addAccount(duplicateAccount);
        
        // Expected Output: False, Customer still holds exactly one account "INV001"
        assertFalse("Should not add account with duplicate ID", result);
        assertEquals("Customer should still have exactly 1 account", 1, customer.getAccounts().size());
        assertEquals("Account ID should still be INV001", "INV001", customer.getAccounts().get(0).getAccountId());
    }
    
    @Test
    public void testCase3_removeExistingZeroBalanceAccount() {
        // SetUp: Customer "Doli" (address "123 Main St") holds savings account "SAV002", balance $0, interest rate 3.65%
        customer.setName("Doli");
        customer.setAddress("123 Main St");
        
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountId("SAV002");
        savingsAccount.setBalance(BigDecimal.ZERO);
        savingsAccount.setInterestRate(new BigDecimal("0.0365"));
        customer.addAccount(savingsAccount);
        
        // Action: Remove account "SAV002"
        boolean result = customer.removeAccount(savingsAccount);
        
        // Expected Output: True, Customer now has no account "SAV002"
        assertTrue("Should successfully remove zero-balance account", result);
        assertEquals("Customer should have no accounts after removal", 0, customer.getAccounts().size());
    }
    
    @Test
    public void testCase4_attemptToRemoveAccountThatStillHasBalance() {
        // SetUp: Customer "Liam" (address "123 Main St") holds savings account "SAV003", balance $250, interest rate 3.65%
        customer.setName("Liam");
        customer.setAddress("123 Main St");
        
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountId("SAV003");
        savingsAccount.setBalance(new BigDecimal("250"));
        savingsAccount.setInterestRate(new BigDecimal("0.0365"));
        customer.addAccount(savingsAccount);
        
        // Action: Request deletion of account "SAV003"
        boolean result = customer.removeAccount(savingsAccount);
        
        // Expected Output: False, Customer still holds account "SAV003" with balance $250
        assertFalse("Should not remove account with balance", result);
        assertEquals("Customer should still have 1 account", 1, customer.getAccounts().size());
        assertEquals("Account ID should still be SAV003", "SAV003", customer.getAccounts().get(0).getAccountId());
        assertEquals("Balance should remain $250", new BigDecimal("250"), customer.getAccounts().get(0).getBalance());
    }
    
    @Test
    public void testCase5_removeInvestmentAccount() {
        // SetUp: Customer "Aliza" (address "123 Main St") currently has 2 investment accounts: 
        // "INV001" with no stock transaction, "INV002" (balance $100) with 1 stock transaction (10 shares of stock "ABS" at price $5)
        customer.setName("Aliza");
        customer.setAddress("123 Main St");
        
        // Create INV001 - no stock transactions
        InvestmentAccount inv001 = new InvestmentAccount();
        inv001.setAccountId("INV001");
        inv001.setBalance(BigDecimal.ZERO);
        
        // Create INV002 - with stock transaction
        InvestmentAccount inv002 = new InvestmentAccount();
        inv002.setAccountId("INV002");
        inv002.setBalance(new BigDecimal("100"));
        
        // Add stock transaction to INV002
        StockTransaction transaction = new StockTransaction("ABS", 10, new BigDecimal("5"));
        List<StockTransaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        inv002.getStockTransactions().put("ABS", transactions);
        
        customer.addAccount(inv001);
        customer.addAccount(inv002);
        
        // Action: Attempt to remove account "INV001", "INV002"
        boolean removeInv001Result = customer.removeAccount(inv001);
        boolean removeInv002Result = customer.removeAccount(inv002);
        
        // Expected Output: remove "INV001" : True, remove "INV002" : False
        assertTrue("Should successfully remove INV001 (no balance, no transactions)", removeInv001Result);
        assertFalse("Should not remove INV002 (has stock transactions)", removeInv002Result);
        
        // Verify final state
        assertEquals("Customer should have 1 account remaining", 1, customer.getAccounts().size());
        assertEquals("Remaining account should be INV002", "INV002", customer.getAccounts().get(0).getAccountId());
    }
}