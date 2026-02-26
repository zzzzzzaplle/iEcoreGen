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
    
    @Before
    public void setUp() {
        factory = BankFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_depositPositiveAmountToSavings() {
        // SetUp: Customer "Doli" (address "123 Main St") holds savings account "SAV004", balance $1 000, interest rate 3.65 %.
        Customer customer = factory.createCustomer();
        customer.setName("Doli");
        customer.setAddress("123 Main St");
        
        SavingAccount savingsAccount = factory.createSavingAccount();
        savingsAccount.setId("SAV004");
        savingsAccount.setBalance(1000.0);
        savingsAccount.setInterestRate(3.65);
        
        customer.getAccounts().add(savingsAccount);
        
        // Action: Deposit $500 into "SAV004".
        boolean result = savingsAccount.deposit(500.0);
        
        // Expected Output: 1 500      // new balance
        assertTrue(result);
        assertEquals(1500.0, savingsAccount.getBalance(), 0.01);
    }
    
    @Test
    public void testCase2_depositZeroAmount() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds savings account "SAV005", balance $200, interest rate 3.65 %.
        Customer customer = factory.createCustomer();
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        
        SavingAccount savingsAccount = factory.createSavingAccount();
        savingsAccount.setId("SAV005");
        savingsAccount.setBalance(200.0);
        savingsAccount.setInterestRate(3.65);
        
        customer.getAccounts().add(savingsAccount);
        
        // Action: Attempt to deposit $0.
        boolean result = savingsAccount.deposit(0.0);
        
        // Expected Output: False, the balance of 'SAV005' is still $200.
        assertFalse(result);
        assertEquals(200.0, savingsAccount.getBalance(), 0.01);
    }
    
    @Test
    public void testCase3_depositNegativeAmount() {
        // SetUp: Investment account "INV001" holds balance $300.
        InvestmentAccount investmentAccount = factory.createInvestmentAccount();
        investmentAccount.setId("INV001");
        investmentAccount.setBalance(300.0);
        
        // Action: Attempt to deposit –$100.
        boolean result = investmentAccount.deposit(-100.0);
        
        // Expected Output: False
        assertFalse(result);
    }
    
    @Test
    public void testCase4_depositExactlyAtSingleDepositLimit() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds an investment account "INV002" holds balance $0.
        Customer customer = factory.createCustomer();
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        
        InvestmentAccount investmentAccount = factory.createInvestmentAccount();
        investmentAccount.setId("INV002");
        investmentAccount.setBalance(0.0);
        
        customer.getAccounts().add(investmentAccount);
        
        // Action: Deposit $1 000 000.
        boolean result = investmentAccount.deposit(1000000.0);
        
        // Expected Output: 1 000 000
        assertTrue(result);
        assertEquals(1000000.0, investmentAccount.getBalance(), 0.01);
    }
    
    @Test
    public void testCase5_depositExceedsLimitByOneDollar() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds a savings account "SAV006" holds balance $50, interest rate 3.65 %.
        Customer customer = factory.createCustomer();
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        
        SavingAccount savingsAccount = factory.createSavingAccount();
        savingsAccount.setId("SAV006");
        savingsAccount.setBalance(50.0);
        savingsAccount.setInterestRate(3.65);
        
        customer.getAccounts().add(savingsAccount);
        
        // Action: Attempt to deposit $1 000 001.
        boolean result = savingsAccount.deposit(1000001.0);
        
        // Expected Output: False
        assertFalse(result);
    }
}