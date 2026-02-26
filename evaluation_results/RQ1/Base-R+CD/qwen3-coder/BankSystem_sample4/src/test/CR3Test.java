import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Customer customer;
    private SavingAccount savingAccount;
    
    @Before
    public void setUp() {
        customer = new Customer();
        savingAccount = new SavingAccount();
        customer.getAccounts().add(savingAccount);
    }
    
    @Test
    public void testCase1_standardBalanceAndRate() {
        // SetUp: Customer "Alie" with savings account "SAV007"
        // Balance $10,000, interest rate 3.65%
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        savingAccount.setId("SAV007");
        savingAccount.setBalance(10000.0);
        savingAccount.setInterestRate(0.0365);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = savingAccount.calculateDailyInterest();
        
        // Expected Output: 1.01
        assertEquals(1.01, dailyInterest, 0.001);
    }
    
    @Test
    public void testCase2_zeroBalance() {
        // SetUp: Customer "Poe" with savings account "SAV008"
        // Balance $0, interest rate 5%
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        savingAccount.setId("SAV008");
        savingAccount.setBalance(0.0);
        savingAccount.setInterestRate(0.05);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = savingAccount.calculateDailyInterest();
        
        // Expected Output: 0.00
        assertEquals(0.00, dailyInterest, 0.001);
    }
    
    @Test
    public void testCase3_verySmallBalance() {
        // SetUp: Customer "Paul" with savings account "SAV009"
        // Balance $0.01, interest rate 2%
        customer.setName("Paul");
        customer.setAddress("0815 Center St");
        savingAccount.setId("SAV009");
        savingAccount.setBalance(0.01);
        savingAccount.setInterestRate(0.02);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = savingAccount.calculateDailyInterest();
        
        // Expected Output: 0.00 (rounds down)
        assertEquals(0.00, dailyInterest, 0.001);
    }
    
    @Test
    public void testCase4_largeBalance() {
        // SetUp: Customer "Peter" with savings account "SAV010"
        // Balance $1,000,000, interest rate 2%
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        savingAccount.setId("SAV010");
        savingAccount.setBalance(1000000.0);
        savingAccount.setInterestRate(0.02);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = savingAccount.calculateDailyInterest();
        
        // Expected Output: 55.56
        assertEquals(55.56, dailyInterest, 0.001);
    }
    
    @Test
    public void testCase5_highPrecisionRate() {
        // SetUp: Customer "Beli" with savings account "SAV011"
        // Balance $1,234.56, interest rate 2.75%
        customer.setName("Beli");
        customer.setAddress("0819 Center St");
        savingAccount.setId("SAV011");
        savingAccount.setBalance(1234.56);
        savingAccount.setInterestRate(0.0275);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = savingAccount.calculateDailyInterest();
        
        // Expected Output: 0.09
        assertEquals(0.09, dailyInterest, 0.001);
    }
}