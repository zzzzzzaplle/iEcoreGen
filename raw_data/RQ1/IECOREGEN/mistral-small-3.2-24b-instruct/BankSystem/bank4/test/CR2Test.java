package edu.bank.bank4.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.bank.BankFactory;
import edu.bank.Customer;
import edu.bank.SavingAccount;
import edu.bank.InvestmentAccount;

public class CR2Test {
    
    private BankFactory factory;
    private Customer customerDoli;
    private Customer customerAlie;
    
    @Before
    public void setUp() {
        factory = BankFactory.eINSTANCE;
        
        // Create customer Doli
        customerDoli = factory.createCustomer();
        customerDoli.setName("Doli");
        customerDoli.setAddress("123 Main St");
        
        // Create customer Alie
        customerAlie = factory.createCustomer();
        customerAlie.setName("Alie");
        customerAlie.setAddress("0812 Center St");
    }
    
    @Test
    public void testCase1_DepositPositiveAmountToSavings() {
        // SetUp: Customer "Doli" holds savings account "SAV004", balance $1000, interest rate 3.65%
        boolean accountCreated = customerDoli.addSavingAccount("SAV004", 0.0365);
        assertTrue("Savings account should be created successfully", accountCreated);
        
        SavingAccount savAccount = (SavingAccount) customerDoli.findAccountById("SAV004");
        assertNotNull("Savings account should be found", savAccount);
        savAccount.setBalance(1000.0);
        
        // Action: Deposit $500 into "SAV004"
        boolean depositResult = savAccount.deposit(500.0);
        
        // Expected Output: 1500 (new balance)
        assertTrue("Deposit should be successful", depositResult);
        assertEquals(1500.0, savAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_DepositZeroAmount() {
        // SetUp: Customer "Alie" holds savings account "SAV005", balance $200, interest rate 3.65%
        boolean accountCreated = customerAlie.addSavingAccount("SAV005", 0.0365);
        assertTrue("Savings account should be created successfully", accountCreated);
        
        SavingAccount savAccount = (SavingAccount) customerAlie.findAccountById("SAV005");
        assertNotNull("Savings account should be found", savAccount);
        savAccount.setBalance(200.0);
        double initialBalance = savAccount.getBalance();
        
        // Action: Attempt to deposit $0
        boolean depositResult = savAccount.deposit(0.0);
        
        // Expected Output: False, the balance of 'SAV005' is still $200
        assertFalse("Deposit of zero amount should fail", depositResult);
        assertEquals("Balance should remain unchanged", initialBalance, savAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_DepositNegativeAmount() {
        // SetUp: Investment account "INV001" holds balance $300
        boolean accountCreated = customerAlie.addInvestmentAccount("INV001");
        assertTrue("Investment account should be created successfully", accountCreated);
        
        InvestmentAccount invAccount = (InvestmentAccount) customerAlie.findAccountById("INV001");
        assertNotNull("Investment account should be found", invAccount);
        invAccount.setBalance(300.0);
        
        // Action: Attempt to deposit -$100
        boolean depositResult = invAccount.deposit(-100.0);
        
        // Expected Output: False
        assertFalse("Deposit of negative amount should fail", depositResult);
    }
    
    @Test
    public void testCase4_DepositExactlyAtSingleDepositLimit() {
        // SetUp: Customer "Alie" holds an investment account "INV002" with balance $0
        boolean accountCreated = customerAlie.addInvestmentAccount("INV002");
        assertTrue("Investment account should be created successfully", accountCreated);
        
        InvestmentAccount invAccount = (InvestmentAccount) customerAlie.findAccountById("INV002");
        assertNotNull("Investment account should be found", invAccount);
        invAccount.setBalance(0.0);
        
        // Action: Deposit $1,000,000
        boolean depositResult = invAccount.deposit(1000000.0);
        
        // Expected Output: 1,000,000
        assertTrue("Deposit at limit should be successful", depositResult);
        assertEquals(1000000.0, invAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_DepositExceedsLimitByOneDollar() {
        // SetUp: Customer "Alie" holds a savings account "SAV006" with balance $50, interest rate 3.65%
        boolean accountCreated = customerAlie.addSavingAccount("SAV006", 0.0365);
        assertTrue("Savings account should be created successfully", accountCreated);
        
        SavingAccount savAccount = (SavingAccount) customerAlie.findAccountById("SAV006");
        assertNotNull("Savings account should be found", savAccount);
        savAccount.setBalance(50.0);
        double initialBalance = savAccount.getBalance();
        
        // Action: Attempt to deposit $1,000,001
        boolean depositResult = savAccount.deposit(1000001.0);
        
        // Expected Output: False
        assertFalse("Deposit exceeding limit should fail", depositResult);
        assertEquals("Balance should remain unchanged", initialBalance, savAccount.getBalance(), 0.001);
    }
}