package edu.bank.bank4.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.bank.BankFactory;
import edu.bank.Customer;
import edu.bank.SavingAccount;

public class CR3Test {
    
    private BankFactory factory;
    
    @Before
    public void setUp() {
        factory = BankFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_StandardBalanceAndRate() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds a savings account "SAV007"
        Customer customer = factory.createCustomer();
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        
        // Create savings account with balance $10,000 and interest rate 3.65%
        SavingAccount account = factory.createSavingAccount();
        account.setId("SAV007");
        account.setBalance(10000.0);
        account.setInterestRate(0.0365); // 3.65%
        account.setCustomer(customer);
        customer.getAccounts().add(account);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = account.calculateDailyInterest();
        
        // Expected Output: 1.01 (interest added, new balance $10,001.01)
        assertEquals(1.01, dailyInterest, 0.01);
        assertEquals(10001.01, account.getBalance(), 0.01);
    }
    
    @Test
    public void testCase2_ZeroBalance() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a savings account "SAV008"
        Customer customer = factory.createCustomer();
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        
        // Create savings account with balance $0 and interest rate 5%
        SavingAccount account = factory.createSavingAccount();
        account.setId("SAV008");
        account.setBalance(0.0);
        account.setInterestRate(0.05); // 5%
        account.setCustomer(customer);
        customer.getAccounts().add(account);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = account.calculateDailyInterest();
        
        // Expected Output: 0.00
        assertEquals(0.00, dailyInterest, 0.001);
        assertEquals(0.00, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_VerySmallBalance() {
        // SetUp: Customer "Paul" (address "0815 Center St") holds a savings account "SAV009"
        Customer customer = factory.createCustomer();
        customer.setName("Paul");
        customer.setAddress("0815 Center St");
        
        // Create savings account with balance $0.01 and interest rate 2%
        SavingAccount account = factory.createSavingAccount();
        account.setId("SAV009");
        account.setBalance(0.01);
        account.setInterestRate(0.02); // 2%
        account.setCustomer(customer);
        customer.getAccounts().add(account);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = account.calculateDailyInterest();
        
        // Expected Output: 0.00 (rounds down)
        assertEquals(0.00, dailyInterest, 0.001);
        assertEquals(0.01, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_LargeBalance() {
        // SetUp: Customer "Peter" (address "0816 Center St") holds a savings account "SAV010"
        Customer customer = factory.createCustomer();
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        
        // Create savings account with balance $1,000,000 and interest rate 2%
        SavingAccount account = factory.createSavingAccount();
        account.setId("SAV010");
        account.setBalance(1000000.0);
        account.setInterestRate(0.02); // 2%
        account.setCustomer(customer);
        customer.getAccounts().add(account);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = account.calculateDailyInterest();
        
        // Expected Output: 55.56
        assertEquals(55.56, dailyInterest, 0.01);
        assertEquals(1000055.56, account.getBalance(), 0.01);
    }
    
    @Test
    public void testCase5_HighPrecisionRate() {
        // SetUp: Customer "Beli" (address "0819 Center St") holds a savings account "SAV011"
        Customer customer = factory.createCustomer();
        customer.setName("Beli");
        customer.setAddress("0819 Center St");
        
        // Create savings account with balance $1,234.56 and interest rate 2.75%
        SavingAccount account = factory.createSavingAccount();
        account.setId("SAV011");
        account.setBalance(1234.56);
        account.setInterestRate(0.0275); // 2.75%
        account.setCustomer(customer);
        customer.getAccounts().add(account);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = account.calculateDailyInterest();
        
        // Expected Output: 0.09
        assertEquals(0.09, dailyInterest, 0.01);
        assertEquals(1234.65, account.getBalance(), 0.01);
    }
}