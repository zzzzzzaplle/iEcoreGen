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
        savingsAccount.setAccountId("SAV001");
        savingsAccount.setInterestRate(new BigDecimal("0.02"));
        
        boolean result = customer.addAccount(savingsAccount);
        
        // Expected Output: True
        assertTrue("Should successfully add new savings account", result);
        assertEquals("Customer should have exactly 1 account", 1, customer.getAccounts().size());
        assertEquals("Added account should be SAV001", "SAV001", customer.getAccounts().get(0).getAccountId());
    }
    
    @Test
    public void testCase2_addAccountWithDuplicateID() {
        // SetUp: Customer "Mia" (address "123 Main St") already holds an investment account "INV001"
        customer.setName("Mia");
        customer.setAddress("123 Main St");
        
        InvestmentAccount existingAccount = new InvestmentAccount();
        existingAccount.setAccountId("INV001");
        customer.addAccount(existingAccount);
        
        int initialAccountCount = customer.getAccounts().size();
        
        // Action: Attempt to add another investment account using the same ID "INV001"
        InvestmentAccount duplicateAccount = new InvestmentAccount();
        duplicateAccount.setAccountId("INV001");
        
        boolean result = customer.addAccount(duplicateAccount);
        
        // Expected Output: False
        assertFalse("Should not add account with duplicate ID", result);
        // Customer still holds exactly one account "INV001"
        assertEquals("Customer should still have exactly one account", initialAccountCount, customer.getAccounts().size());
        assertEquals("Account should still be INV001", "INV001", customer.getAccounts().get(0).getAccountId());
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
        
        int initialAccountCount = customer.getAccounts().size();
        
        // Action: Remove account "SAV002"
        boolean result = customer.removeAccount("SAV002");
        
        // Expected Output: True
        assertTrue("Should successfully remove zero-balance account", result);
        // Customer now has no account "SAV002"
        assertEquals("Customer should have one less account", initialAccountCount - 1, customer.getAccounts().size());
        assertTrue("SAV002 account should be removed", customer.getAccounts().isEmpty());
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
        
        int initialAccountCount = customer.getAccounts().size();
        
        // Action: Request deletion of account "SAV003"
        boolean result = customer.removeAccount("SAV003");
        
        // Expected Output: False
        assertFalse("Should not remove account with non-zero balance", result);
        // Customer still holds account "SAV003" with balance $250
        assertEquals("Customer should still have the same number of accounts", initialAccountCount, customer.getAccounts().size());
        assertEquals("Account should still be SAV003", "SAV003", customer.getAccounts().get(0).getAccountId());
        assertEquals("Balance should still be $250", new BigDecimal("250"), customer.getAccounts().get(0).getBalance());
    }
    
    @Test
    public void testCase5_removeInvestmentAccount() {
        // SetUp: Customer "Aliza" (address "123 Main St") currently has 2 investment accounts: 
        // "INV001" with no stock transaction, "INV002" (balance $100) with 1 stock transaction (10 shares of stock "ABS" at price $5)
        customer.setName("Aliza");
        customer.setAddress("123 Main St");
        
        // Create INV001 account with no stock transactions
        InvestmentAccount inv001 = new InvestmentAccount();
        inv001.setAccountId("INV001");
        inv001.setBalance(BigDecimal.ZERO);
        customer.addAccount(inv001);
        
        // Create INV002 account with stock transaction
        InvestmentAccount inv002 = new InvestmentAccount();
        inv002.setAccountId("INV002");
        inv002.setBalance(new BigDecimal("100"));
        
        // Add stock transaction to INV002
        StockTransaction stockTransaction = new StockTransaction();
        stockTransaction.setTicker("ABS");
        stockTransaction.setQuantity(10);
        stockTransaction.setPurchasePrice(new BigDecimal("5"));
        stockTransaction.setCommission(new BigDecimal("5")); // 10% commission on $50 stock cost = $5
        
        List<StockTransaction> transactions = new ArrayList<>();
        transactions.add(stockTransaction);
        inv002.setStockTransactions(transactions);
        
        customer.addAccount(inv002);
        
        int initialAccountCount = customer.getAccounts().size();
        
        // Action: Attempt to remove account "INV001"
        boolean removeInv001Result = customer.removeAccount("INV001");
        
        // Expected Output: remove "INV001" : True
        assertTrue("Should successfully remove INV001 with zero balance and no stock transactions", removeInv001Result);
        assertEquals("Customer should have one less account after removing INV001", initialAccountCount - 1, customer.getAccounts().size());
        
        // Action: Attempt to remove account "INV002"
        boolean removeInv002Result = customer.removeAccount("INV002");
        
        // Expected Output: remove "INV002" : False
        assertFalse("Should not remove INV002 with stock transactions", removeInv002Result);
        assertEquals("Customer should still have INV002 account", 1, customer.getAccounts().size());
        assertEquals("Remaining account should be INV002", "INV002", customer.getAccounts().get(0).getAccountId());
    }
}