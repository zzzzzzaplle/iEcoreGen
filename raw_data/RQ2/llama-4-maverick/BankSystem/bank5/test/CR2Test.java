package edu.bank.bank5.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.bank.BankFactory;
import edu.bank.Customer;
import edu.bank.SavingAccount;
import edu.bank.InvestmentAccount;

public class CR2Test {
    
    private Customer customerDoli;
    private Customer customerAlie;
    private SavingAccount savingsAccountSAV004;
    private SavingAccount savingsAccountSAV005;
    private SavingAccount savingsAccountSAV006;
    private InvestmentAccount investmentAccountINV001;
    private InvestmentAccount investmentAccountINV002;
    
    @Before
    public void setUp() {
        // Create customers
        customerDoli = BankFactory.eINSTANCE.createCustomer();
        customerDoli.setName("Doli");
        customerDoli.setAddress("123 Main St");
        
        customerAlie = BankFactory.eINSTANCE.createCustomer();
        customerAlie.setName("Alie");
        customerAlie.setAddress("0812 Center St");
        
        // Create savings account SAV004 for Doli
        savingsAccountSAV004 = BankFactory.eINSTANCE.createSavingAccount();
        savingsAccountSAV004.setId("SAV004");
        savingsAccountSAV004.setBalance(1000.0);
        savingsAccountSAV004.setInterestRate(3.65);
        savingsAccountSAV004.setCustomer(customerDoli);
        customerDoli.getAccounts().add(savingsAccountSAV004);
        
        // Create savings account SAV005 for Alie
        savingsAccountSAV005 = BankFactory.eINSTANCE.createSavingAccount();
        savingsAccountSAV005.setId("SAV005");
        savingsAccountSAV005.setBalance(200.0);
        savingsAccountSAV005.setInterestRate(3.65);
        savingsAccountSAV005.setCustomer(customerAlie);
        customerAlie.getAccounts().add(savingsAccountSAV005);
        
        // Create investment account INV001
        investmentAccountINV001 = BankFactory.eINSTANCE.createInvestmentAccount();
        investmentAccountINV001.setId("INV001");
        investmentAccountINV001.setBalance(300.0);
        investmentAccountINV001.setCustomer(customerAlie);
        customerAlie.getAccounts().add(investmentAccountINV001);
        
        // Create investment account INV002
        investmentAccountINV002 = BankFactory.eINSTANCE.createInvestmentAccount();
        investmentAccountINV002.setId("INV002");
        investmentAccountINV002.setBalance(0.0);
        investmentAccountINV002.setCustomer(customerAlie);
        customerAlie.getAccounts().add(investmentAccountINV002);
        
        // Create savings account SAV006
        savingsAccountSAV006 = BankFactory.eINSTANCE.createSavingAccount();
        savingsAccountSAV006.setId("SAV006");
        savingsAccountSAV006.setBalance(50.0);
        savingsAccountSAV006.setInterestRate(3.65);
        savingsAccountSAV006.setCustomer(customerAlie);
        customerAlie.getAccounts().add(savingsAccountSAV006);
    }
    
    @Test
    public void testCase1_depositPositiveAmountToSavings() {
        // Action: Deposit $500 into "SAV004"
        boolean result = savingsAccountSAV004.deposit(500.0);
        
        // Expected Output: true and new balance should be 1500
        assertTrue("Deposit should be successful", result);
        assertEquals("New balance should be 1500", 1500.0, savingsAccountSAV004.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_depositZeroAmount() {
        // Action: Attempt to deposit $0
        boolean result = savingsAccountSAV005.deposit(0.0);
        
        // Expected Output: False, the balance of 'SAV005' is still $200
        assertFalse("Deposit should fail for zero amount", result);
        assertEquals("Balance should remain 200", 200.0, savingsAccountSAV005.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_depositNegativeAmount() {
        // Action: Attempt to deposit -$100
        boolean result = investmentAccountINV001.deposit(-100.0);
        
        // Expected Output: False
        assertFalse("Deposit should fail for negative amount", result);
    }
    
    @Test
    public void testCase4_depositExactlyAtSingleDepositLimit() {
        // Action: Deposit $1,000,000
        boolean result = investmentAccountINV002.deposit(1000000.0);
        
        // Expected Output: true and new balance should be 1,000,000
        assertTrue("Deposit should be successful for exact limit", result);
        assertEquals("New balance should be 1000000", 1000000.0, investmentAccountINV002.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_depositExceedsLimitByOneDollar() {
        // Action: Attempt to deposit $1,000,001
        boolean result = savingsAccountSAV006.deposit(1000001.0);
        
        // Expected Output: False
        assertFalse("Deposit should fail for amount exceeding limit", result);
        assertEquals("Balance should remain 50", 50.0, savingsAccountSAV006.getBalance(), 0.001);
    }
}