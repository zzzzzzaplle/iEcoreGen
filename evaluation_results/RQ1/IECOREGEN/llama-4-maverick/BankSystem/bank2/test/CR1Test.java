package edu.bank.bank2.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.bank.BankFactory;
import edu.bank.Customer;
import edu.bank.Account;
import edu.bank.SavingAccount;
import edu.bank.InvestmentAccount;
import edu.bank.StockTransaction;

public class CR1Test {
    
    private BankFactory factory;
    
    @Before
    public void setUp() {
        factory = BankFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_addNewSavingsAccountSuccessfully() {
        // SetUp: Customer "John Doe", address "123 Main St", currently holds no accounts
        Customer customer = factory.createCustomer();
        customer.setName("John Doe");
        customer.setAddress("123 Main St");
        
        // Action: Add a savings account with ID "SAV001", interest rate 2%
        boolean result = customer.addSavingAccount("SAV001", 0.02);
        
        // Expected Output: True
        assertTrue(result);
        
        // Verify the account was added
        assertEquals(1, customer.getAccounts().size());
        Account account = customer.findAccountById("SAV001");
        assertNotNull(account);
        assertTrue(account instanceof SavingAccount);
        assertEquals("SAV001", account.getId());
        assertEquals(0.02, ((SavingAccount) account).getInterestRate(), 0.001);
    }
    
    @Test
    public void testCase2_addAccountWithDuplicateID() {
        // SetUp: Customer "Mia" already holds an investment account "INV001"
        Customer customer = factory.createCustomer();
        customer.setName("Mia");
        customer.setAddress("123 Main St");
        
        // Create initial investment account
        InvestmentAccount initialAccount = factory.createInvestmentAccount();
        initialAccount.setId("INV001");
        customer.getAccounts().add(initialAccount);
        
        // Action: Attempt to add another investment account using the same ID "INV001"
        boolean result = customer.addInvestmentAccount("INV001");
        
        // Expected Output: False
        assertFalse(result);
        
        // Customer still holds exactly one account "INV001"
        assertEquals(1, customer.getAccounts().size());
        Account account = customer.findAccountById("INV001");
        assertNotNull(account);
        assertEquals("INV001", account.getId());
    }
    
    @Test
    public void testCase3_removeExistingZeroBalanceAccount() {
        // SetUp: Customer "Doli" holds savings account "SAV002", balance $0, interest rate 3.65%
        Customer customer = factory.createCustomer();
        customer.setName("Doli");
        customer.setAddress("123 Main St");
        
        SavingAccount savingAccount = factory.createSavingAccount();
        savingAccount.setId("SAV002");
        savingAccount.setBalance(0.0);
        savingAccount.setInterestRate(0.0365);
        customer.getAccounts().add(savingAccount);
        
        // Action: Remove account "SAV002"
        boolean result = customer.removeSavingAccount("SAV002");
        
        // Expected Output: True
        assertTrue(result);
        
        // Customer now has no account "SAV002"
        assertEquals(0, customer.getAccounts().size());
        Account account = customer.findAccountById("SAV002");
        assertNull(account);
    }
    
    @Test
    public void testCase4_attemptToRemoveAccountThatStillHasBalance() {
        // SetUp: Customer "Liam" holds savings account "SAV003", balance $250, interest rate 3.65%
        Customer customer = factory.createCustomer();
        customer.setName("Liam");
        customer.setAddress("123 Main St");
        
        SavingAccount savingAccount = factory.createSavingAccount();
        savingAccount.setId("SAV003");
        savingAccount.setBalance(250.0);
        savingAccount.setInterestRate(0.0365);
        customer.getAccounts().add(savingAccount);
        
        // Action: Request deletion of account "SAV003"
        boolean result = customer.removeSavingAccount("SAV003");
        
        // Expected Output: False
        assertFalse(result);
        
        // Customer still holds account "SAV003" with balance $250
        assertEquals(1, customer.getAccounts().size());
        Account account = customer.findAccountById("SAV003");
        assertNotNull(account);
        assertEquals(250.0, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_removeAnInvestmentAccount() {
        // SetUp: Customer "Aliza" currently has 2 investment accounts
        Customer customer = factory.createCustomer();
        customer.setName("Aliza");
        customer.setAddress("123 Main St");
        
        // "INV001" with no stock transaction, zero balance
        InvestmentAccount account1 = factory.createInvestmentAccount();
        account1.setId("INV001");
        account1.setBalance(0.0);
        customer.getAccounts().add(account1);
        
        // "INV002" (balance $100) with 1 stock transaction
        InvestmentAccount account2 = factory.createInvestmentAccount();
        account2.setId("INV002");
        account2.setBalance(100.0);
        customer.getAccounts().add(account2);
        
        // Add a stock transaction to INV002
        StockTransaction transaction = factory.createStockTransaction();
        transaction.setStock("ABS");
        transaction.setQuantity(10);
        transaction.setPrice(5.0);
        transaction.setCommission(5.0); // 10% of (10*5) = 5
        transaction.setAccount(account2);
        account2.getTransactions().add(transaction);
        
        // Action: Attempt to remove account "INV001", "INV002"
        boolean result1 = customer.removeInvestmentAccount("INV001");
        boolean result2 = customer.removeInvestmentAccount("INV002");
        
        // Expected Output:
        // remove "INV001": True
        assertTrue(result1);
        
        // remove "INV002": False
        assertFalse(result2);
        
        // Verify final state
        assertEquals(1, customer.getAccounts().size());
        assertNotNull(customer.findAccountById("INV002"));
        assertNull(customer.findAccountById("INV001"));
    }
}