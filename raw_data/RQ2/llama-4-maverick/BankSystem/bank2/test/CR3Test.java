package edu.bank.bank2.test;

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
    public void testCase1_standardBalanceAndRate() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds a savings account "SAV007"
        // "SAV007" balance $10 000, interest rate 3.65 %.
        Customer customer = factory.createCustomer();
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        
        SavingAccount savingAccount = factory.createSavingAccount();
        savingAccount.setId("SAV007");
        savingAccount.setBalance(10000.0);
        savingAccount.setInterestRate(0.0365); // 3.65%
        
        customer.getAccounts().add(savingAccount);
        
        // Action: Trigger daily-interest calculation.
        double dailyInterest = savingAccount.calculateDailyInterest();
        
        // Expected Output: 1.01
        assertEquals(1.01, dailyInterest, 0.01);
    }
    
    @Test
    public void testCase2_zeroBalance() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a savings account "SAV008"
        // Savings account "SAV008" balance $0, interest rate 5 %.
        Customer customer = factory.createCustomer();
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        
        SavingAccount savingAccount = factory.createSavingAccount();
        savingAccount.setId("SAV008");
        savingAccount.setBalance(0.0);
        savingAccount.setInterestRate(0.05); // 5%
        
        customer.getAccounts().add(savingAccount);
        
        // Action: Trigger daily-interest calculation.
        double dailyInterest = savingAccount.calculateDailyInterest();
        
        // Expected Output: 0.00
        assertEquals(0.00, dailyInterest, 0.01);
    }
    
    @Test
    public void testCase3_verySmallBalance() {
        // SetUp: Customer "Paul" (address "0815 Center St") holds a savings account "SAV009"
        // Savings account "SAV009" balance $0.01, interest rate 2 %.
        Customer customer = factory.createCustomer();
        customer.setName("Paul");
        customer.setAddress("0815 Center St");
        
        SavingAccount savingAccount = factory.createSavingAccount();
        savingAccount.setId("SAV009");
        savingAccount.setBalance(0.01);
        savingAccount.setInterestRate(0.02); // 2%
        
        customer.getAccounts().add(savingAccount);
        
        // Action: Trigger daily-interest calculation.
        double dailyInterest = savingAccount.calculateDailyInterest();
        
        // Expected Output: 0.00
        assertEquals(0.00, dailyInterest, 0.01);
    }
    
    @Test
    public void testCase4_largeBalance() {
        // SetUp: Customer "Peter" (address "0816 Center St") holds a savings account "SAV010"
        // Savings account "SAV010" balance $1 000 000, interest rate 2 %.
        Customer customer = factory.createCustomer();
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        
        SavingAccount savingAccount = factory.createSavingAccount();
        savingAccount.setId("SAV010");
        savingAccount.setBalance(1000000.0);
        savingAccount.setInterestRate(0.02); // 2%
        
        customer.getAccounts().add(savingAccount);
        
        // Action: Trigger daily-interest calculation.
        double dailyInterest = savingAccount.calculateDailyInterest();
        
        // Expected Output: 55.56
        assertEquals(55.56, dailyInterest, 0.01);
    }
    
    @Test
    public void testCase5_highPrecisionRate() {
        // SetUp: Customer "Beli" (address "0819 Center St") holds a savings account "SAV011"
        // Savings account "SAV011" balance $1 234.56, interest rate 2.75 %.
        Customer customer = factory.createCustomer();
        customer.setName("Beli");
        customer.setAddress("0819 Center St");
        
        SavingAccount savingAccount = factory.createSavingAccount();
        savingAccount.setId("SAV011");
        savingAccount.setBalance(1234.56);
        savingAccount.setInterestRate(0.0275); // 2.75%
        
        customer.getAccounts().add(savingAccount);
        
        // Action: Trigger daily-interest calculation.
        double dailyInterest = savingAccount.calculateDailyInterest();
        
        // Expected Output: 0.09
        assertEquals(0.09, dailyInterest, 0.01);
    }
}