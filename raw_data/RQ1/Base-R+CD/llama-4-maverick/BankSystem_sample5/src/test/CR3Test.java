import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    private Customer customer;
    private SavingAccount savingsAccount;
    
    @Before
    public void setUp() {
        customer = new Customer();
        savingsAccount = new SavingAccount();
        customer.getAccounts().add(savingsAccount);
    }
    
    @Test
    public void testCase1_standardBalanceAndRate() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds a savings account "SAV007"
        // "SAV007" balance $10 000, interest rate 3.65 %.
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        savingsAccount.setId("SAV007");
        savingsAccount.setBalance(10000.0);
        savingsAccount.setInterestRate(0.0365); // 3.65%
        
        // Action: Trigger daily-interest calculation.
        double dailyInterest = savingsAccount.calculateDailyInterest();
        savingsAccount.updateBalanceWithDailyInterest();
        
        // Expected Output: 1.01
        assertEquals(1.01, dailyInterest, 0.001);
        assertEquals(10001.01, savingsAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_zeroBalance() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a savings account "SAV008"
        // Savings account "SAV008" balance $0, interest rate 5 %.
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        savingsAccount.setId("SAV008");
        savingsAccount.setBalance(0.0);
        savingsAccount.setInterestRate(0.05); // 5%
        
        // Action: Trigger daily-interest calculation.
        double dailyInterest = savingsAccount.calculateDailyInterest();
        savingsAccount.updateBalanceWithDailyInterest();
        
        // Expected Output: 0.00
        assertEquals(0.00, dailyInterest, 0.001);
        assertEquals(0.00, savingsAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_verySmallBalance() {
        // SetUp: Customer "Paul" (address "0815 Center St") holds a savings account "SAV009"
        // Savings account "SAV009" balance $0.01, interest rate 2 %.
        customer.setName("Paul");
        customer.setAddress("0815 Center St");
        savingsAccount.setId("SAV009");
        savingsAccount.setBalance(0.01);
        savingsAccount.setInterestRate(0.02); // 2%
        
        // Action: Trigger daily-interest calculation.
        double dailyInterest = savingsAccount.calculateDailyInterest();
        savingsAccount.updateBalanceWithDailyInterest();
        
        // Expected Output: 0.00
        assertEquals(0.00, dailyInterest, 0.001);
        assertEquals(0.01, savingsAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_largeBalance() {
        // SetUp: Customer "Peter" (address "0816 Center St") holds a savings account "SAV010"
        // Savings account "SAV010" balance $1 000 000, interest rate 2 %.
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        savingsAccount.setId("SAV010");
        savingsAccount.setBalance(1000000.0);
        savingsAccount.setInterestRate(0.02); // 2%
        
        // Action: Trigger daily-interest calculation.
        double dailyInterest = savingsAccount.calculateDailyInterest();
        savingsAccount.updateBalanceWithDailyInterest();
        
        // Expected Output: 55.56
        assertEquals(55.56, dailyInterest, 0.001);
        assertEquals(1000055.56, savingsAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_highPrecisionRate() {
        // SetUp: Customer "Beli" (address "0819 Center St") holds a savings account "SAV011"
        // Savings account "SAV011" balance $1 234.56, interest rate 2.75 %.
        customer.setName("Beli");
        customer.setAddress("0819 Center St");
        savingsAccount.setId("SAV011");
        savingsAccount.setBalance(1234.56);
        savingsAccount.setInterestRate(0.0275); // 2.75%
        
        // Action: Trigger daily-interest calculation.
        double dailyInterest = savingsAccount.calculateDailyInterest();
        savingsAccount.updateBalanceWithDailyInterest();
        
        // Expected Output: 0.09
        assertEquals(0.09, dailyInterest, 0.001);
        assertEquals(1234.65, savingsAccount.getBalance(), 0.001);
    }
}