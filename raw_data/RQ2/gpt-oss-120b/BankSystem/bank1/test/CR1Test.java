package edu.bank.bank1.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.bank.BankFactory;
import edu.bank.Customer;
import edu.bank.SavingAccount;
import edu.bank.InvestmentAccount;
import edu.bank.StockTransaction;

public class CR1Test {
    
    private BankFactory factory;
    private Customer customer;
    
    @Before
    public void setUp() {
        // Initialize the factory using Ecore factory pattern
        factory = BankFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_AddNewSavingsAccountSuccessfully() {
        // SetUp: Customer "John Doe", address "123 Main St", currently holds no accounts
        customer = factory.createCustomer();
        customer.setName("John Doe");
        customer.setAddress("123 Main St");
        
        // Action: Add a savings account with ID "SAV001", interest rate 2%
        boolean result = customer.addSavingAccount("SAV001", 0.02);
        
        // Expected Output: True
        assertTrue("Should successfully add savings account", result);
        assertNotNull("Account should be created", customer.findAccountById("SAV001"));
        assertEquals("Should have exactly one account", 1, customer.getAccounts().size());
    }
    
    @Test
    public void testCase2_AddAccountWithDuplicateID() {
        // SetUp: Customer "Mia" (address "123 Main St") already holds an investment account "INV001"
        customer = factory.createCustomer();
        customer.setName("Mia");
        customer.setAddress("123 Main St");
        customer.addInvestmentAccount("INV001");
        
        int initialAccountCount = customer.getAccounts().size();
        
        // Action: Attempt to add another investment account using the same ID "INV001"
        boolean result = customer.addInvestmentAccount("INV001");
        
        // Expected Output: False
        assertFalse("Should not allow duplicate account ID", result);
        // Customer still holds exactly one account "INV001"
        assertEquals("Account count should remain unchanged", initialAccountCount, customer.getAccounts().size());
        assertNotNull("Original account should still exist", customer.findAccountById("INV001"));
    }
    
    @Test
    public void testCase3_RemoveExistingZeroBalanceAccount() {
        // SetUp: Customer "Doli" (address "123 Main St") holds savings account "SAV002", balance $0, interest rate 3.65%
        customer = factory.createCustomer();
        customer.setName("Doli");
        customer.setAddress("123 Main St");
        customer.addSavingAccount("SAV002", 0.0365);
        
        // Verify initial setup
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV002");
        assertNotNull("Account should exist before removal", account);
        assertEquals("Account balance should be 0", 0.0, account.getBalance(), 0.001);
        
        // Action: Remove account "SAV002"
        boolean result = customer.removeSavingAccount("SAV002");
        
        // Expected Output: True
        assertTrue("Should successfully remove zero-balance account", result);
        // Customer now has no account "SAV002"
        assertNull("Account should no longer exist", customer.findAccountById("SAV002"));
        assertTrue("Customer should have no accounts", customer.getAccounts().isEmpty());
    }
    
    @Test
    public void testCase4_AttemptToRemoveAccountThatStillHasBalance() {
        // SetUp: Customer "Liam" (address "123 Main St") holds savings account "SAV003", balance $250, interest rate 3.65%
        customer = factory.createCustomer();
        customer.setName("Liam");
        customer.setAddress("123 Main St");
        customer.addSavingAccount("SAV003", 0.0365);
        
        // Set balance to $250
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV003");
        account.setBalance(250.0);
        
        // Verify initial setup
        assertEquals("Account balance should be 250", 250.0, account.getBalance(), 0.001);
        
        // Action: Request deletion of account "SAV003"
        boolean result = customer.removeSavingAccount("SAV003");
        
        // Expected Output: False
        assertFalse("Should not remove account with balance", result);
        // Customer still holds account "SAV003" with balance $250
        assertNotNull("Account should still exist", customer.findAccountById("SAV003"));
        SavingAccount remainingAccount = (SavingAccount) customer.findAccountById("SAV003");
        assertEquals("Balance should remain 250", 250.0, remainingAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_RemoveInvestmentAccount() {
        // SetUp: Customer "Aliza" (address "123 Main St") currently has 2 investment accounts: 
        // "INV001" with no stock transaction, "INV002" (balance $100) with 1 stock transaction
        customer = factory.createCustomer();
        customer.setName("Aliza");
        customer.setAddress("123 Main St");
        
        // Create first investment account with no transactions
        customer.addInvestmentAccount("INV001");
        
        // Create second investment account with balance and transaction
        customer.addInvestmentAccount("INV002");
        InvestmentAccount invAccount2 = (InvestmentAccount) customer.findAccountById("INV002");
        invAccount2.setBalance(100.0);
        
        // Add stock transaction to second account
        StockTransaction transaction = factory.createStockTransaction();
        transaction.setStock("ABS");
        transaction.setQuantity(10);
        transaction.setPrice(5.0);
        transaction.setCommission(5.0); // 10% of 50
        transaction.setAccount(invAccount2);
        invAccount2.getTransactions().add(transaction);
        
        // Verify initial setup
        assertEquals("Should have 2 accounts initially", 2, customer.getAccounts().size());
        
        // Action: Attempt to remove account "INV001"
        boolean result1 = customer.removeInvestmentAccount("INV001");
        
        // Expected Output: remove "INV001" : True
        assertTrue("Should remove investment account with no balance and no transactions", result1);
        assertNull("INV001 should no longer exist", customer.findAccountById("INV001"));
        
        // Action: Attempt to remove account "INV002"
        boolean result2 = customer.removeInvestmentAccount("INV002");
        
        // Expected Output: remove "INV002" : False
        assertFalse("Should not remove investment account with balance and transactions", result2);
        assertNotNull("INV002 should still exist", customer.findAccountById("INV002"));
        assertEquals("Should have 1 account remaining", 1, customer.getAccounts().size());
    }
}