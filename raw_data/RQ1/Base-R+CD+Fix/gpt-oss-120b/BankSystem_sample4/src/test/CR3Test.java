import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    private Customer customer;
    
    @Before
    public void setUp() {
        // Reset customer before each test
        customer = null;
    }
    
    @Test
    public void testCase1_StandardBalanceAndRate() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds a savings account "SAV007"
        customer = new Customer("Alie", "0812 Center St");
        customer.addSavingAccount("SAV007", 0.0365); // 3.65% interest rate
        
        // Set balance to $10,000
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV007");
        account.setBalance(10000.0);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = account.calculateDailyInterest();
        
        // Expected Output: 1.01
        assertEquals(1.01, dailyInterest, 0.001);
        
        // Verify new balance is $10,001.01
        assertEquals(10001.01, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_ZeroBalance() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a savings account "SAV008"
        customer = new Customer("Poe", "0814 Center St");
        customer.addSavingAccount("SAV008", 0.05); // 5% interest rate
        
        // Set balance to $0
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV008");
        account.setBalance(0.0);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = account.calculateDailyInterest();
        
        // Expected Output: 0.00
        assertEquals(0.00, dailyInterest, 0.001);
        
        // Verify balance remains $0.00
        assertEquals(0.00, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_VerySmallBalance() {
        // SetUp: Customer "Paul" (address "0815 Center St") holds a savings account "SAV009"
        customer = new Customer("Paul", "0815 Center St");
        customer.addSavingAccount("SAV009", 0.02); // 2% interest rate
        
        // Set balance to $0.01
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV009");
        account.setBalance(0.01);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = account.calculateDailyInterest();
        
        // Expected Output: 0.00 (rounds down)
        assertEquals(0.00, dailyInterest, 0.001);
        
        // Verify balance remains $0.01
        assertEquals(0.01, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_LargeBalance() {
        // SetUp: Customer "Peter" (address "0816 Center St") holds a savings account "SAV010"
        customer = new Customer("Peter", "0816 Center St");
        customer.addSavingAccount("SAV010", 0.02); // 2% interest rate
        
        // Set balance to $1,000,000
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV010");
        account.setBalance(1000000.0);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = account.calculateDailyInterest();
        
        // Expected Output: 55.56
        assertEquals(55.56, dailyInterest, 0.001);
        
        // Verify new balance is $1,000,055.56
        assertEquals(1000055.56, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_HighPrecisionRate() {
        // SetUp: Customer "Beli" (address "0819 Center St") holds a savings account "SAV011"
        customer = new Customer("Beli", "0819 Center St");
        customer.addSavingAccount("SAV011", 0.0275); // 2.75% interest rate
        
        // Set balance to $1,234.56
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV011");
        account.setBalance(1234.56);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = account.calculateDailyInterest();
        
        // Expected Output: 0.09
        assertEquals(0.09, dailyInterest, 0.001);
        
        // Verify new balance is $1,234.65
        assertEquals(1234.65, account.getBalance(), 0.001);
    }
}