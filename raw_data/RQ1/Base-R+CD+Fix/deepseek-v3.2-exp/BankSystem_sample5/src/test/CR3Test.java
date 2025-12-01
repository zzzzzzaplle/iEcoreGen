import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    private Customer customer;
    private SavingAccount savingsAccount;
    
    @Before
    public void setUp() {
        customer = new Customer();
    }
    
    @Test
    public void testCase1_StandardBalanceAndRate() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds a savings account "SAV007"
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        
        // "SAV007" balance $10 000, interest rate 3.65 %
        savingsAccount = new SavingAccount();
        savingsAccount.setId("SAV007");
        savingsAccount.setBalance(10000.0);
        savingsAccount.setInterestRate(3.65);
        customer.getAccounts().add(savingsAccount);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = savingsAccount.calculateDailyInterest();
        
        // Expected Output: 1.01
        assertEquals(1.01, dailyInterest, 0.001);
        
        // Verify new balance is $10 001.01
        savingsAccount.updateDailyInterest();
        assertEquals(10001.01, savingsAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_ZeroBalance() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a savings account "SAV008"
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        
        // Savings account "SAV008" balance $0, interest rate 5 %
        savingsAccount = new SavingAccount();
        savingsAccount.setId("SAV008");
        savingsAccount.setBalance(0.0);
        savingsAccount.setInterestRate(5.0);
        customer.getAccounts().add(savingsAccount);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = savingsAccount.calculateDailyInterest();
        
        // Expected Output: 0.00
        assertEquals(0.00, dailyInterest, 0.001);
        
        // Verify balance remains $0.00 after update
        savingsAccount.updateDailyInterest();
        assertEquals(0.00, savingsAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_VerySmallBalance() {
        // SetUp: Customer "Paul" (address "0815 Center St") holds a savings account "SAV009"
        customer.setName("Paul");
        customer.setAddress("0815 Center St");
        
        // Savings account "SAV009" balance $0.01, interest rate 2 %
        savingsAccount = new SavingAccount();
        savingsAccount.setId("SAV009");
        savingsAccount.setBalance(0.01);
        savingsAccount.setInterestRate(2.0);
        customer.getAccounts().add(savingsAccount);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = savingsAccount.calculateDailyInterest();
        
        // Expected Output: 0.00 (rounds down)
        assertEquals(0.00, dailyInterest, 0.001);
        
        // Verify balance remains $0.01 after update
        savingsAccount.updateDailyInterest();
        assertEquals(0.01, savingsAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_LargeBalance() {
        // SetUp: Customer "Peter" (address "0816 Center St") holds a savings account "SAV010"
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        
        // Savings account "SAV010" balance $1 000 000, interest rate 2 %
        savingsAccount = new SavingAccount();
        savingsAccount.setId("SAV010");
        savingsAccount.setBalance(1000000.0);
        savingsAccount.setInterestRate(2.0);
        customer.getAccounts().add(savingsAccount);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = savingsAccount.calculateDailyInterest();
        
        // Expected Output: 55.56
        assertEquals(55.56, dailyInterest, 0.001);
        
        // Verify new balance is $1,000,055.56
        savingsAccount.updateDailyInterest();
        assertEquals(1000055.56, savingsAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_HighPrecisionRate() {
        // SetUp: Customer "Beli" (address "0819 Center St") holds a savings account "SAV011"
        customer.setName("Beli");
        customer.setAddress("0819 Center St");
        
        // Savings account "SAV011" balance $1 234.56, interest rate 2.75 %
        savingsAccount = new SavingAccount();
        savingsAccount.setId("SAV011");
        savingsAccount.setBalance(1234.56);
        savingsAccount.setInterestRate(2.75);
        customer.getAccounts().add(savingsAccount);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = savingsAccount.calculateDailyInterest();
        
        // Expected Output: 0.09
        assertEquals(0.09, dailyInterest, 0.001);
        
        // Verify new balance is $1,234.65
        savingsAccount.updateDailyInterest();
        assertEquals(1234.65, savingsAccount.getBalance(), 0.001);
    }
}