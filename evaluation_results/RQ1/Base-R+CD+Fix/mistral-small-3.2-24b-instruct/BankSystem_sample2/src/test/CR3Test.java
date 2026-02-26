import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    private Customer customer;
    
    @Before
    public void setUp() {
        customer = new Customer();
    }
    
    @Test
    public void testCase1_standardBalanceAndRate() {
        // SetUp: Customer "Alie" with savings account "SAV007"
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        
        // Create savings account with balance $10,000 and interest rate 3.65%
        SavingAccount account = new SavingAccount();
        account.setId("SAV007");
        account.setBalance(10000.0);
        account.setInterestRate(3.65);
        customer.getAccounts().add(account);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = account.calculateDailyInterest();
        
        // Expected Output: 1.01
        assertEquals(1.01, dailyInterest, 0.001);
        
        // Verify new balance after interest addition
        account.setBalance(account.getBalance() + dailyInterest);
        assertEquals(10001.01, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_zeroBalance() {
        // SetUp: Customer "Poe" with savings account "SAV008"
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        
        // Create savings account with balance $0 and interest rate 5%
        SavingAccount account = new SavingAccount();
        account.setId("SAV008");
        account.setBalance(0.0);
        account.setInterestRate(5.0);
        customer.getAccounts().add(account);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = account.calculateDailyInterest();
        
        // Expected Output: 0.00
        assertEquals(0.00, dailyInterest, 0.001);
        
        // Verify balance remains unchanged
        assertEquals(0.0, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_verySmallBalance() {
        // SetUp: Customer "Paul" with savings account "SAV009"
        customer.setName("Paul");
        customer.setAddress("0815 Center St");
        
        // Create savings account with balance $0.01 and interest rate 2%
        SavingAccount account = new SavingAccount();
        account.setId("SAV009");
        account.setBalance(0.01);
        account.setInterestRate(2.0);
        customer.getAccounts().add(account);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = account.calculateDailyInterest();
        
        // Expected Output: 0.00 (rounds down)
        assertEquals(0.00, dailyInterest, 0.001);
        
        // Verify balance remains unchanged
        assertEquals(0.01, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_largeBalance() {
        // SetUp: Customer "Peter" with savings account "SAV010"
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        
        // Create savings account with balance $1,000,000 and interest rate 2%
        SavingAccount account = new SavingAccount();
        account.setId("SAV010");
        account.setBalance(1000000.0);
        account.setInterestRate(2.0);
        customer.getAccounts().add(account);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = account.calculateDailyInterest();
        
        // Expected Output: 55.56
        assertEquals(55.56, dailyInterest, 0.001);
        
        // Verify new balance after interest addition
        account.setBalance(account.getBalance() + dailyInterest);
        assertEquals(1000055.56, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_highPrecisionRate() {
        // SetUp: Customer "Beli" with savings account "SAV011"
        customer.setName("Beli");
        customer.setAddress("0819 Center St");
        
        // Create savings account with balance $1,234.56 and interest rate 2.75%
        SavingAccount account = new SavingAccount();
        account.setId("SAV011");
        account.setBalance(1234.56);
        account.setInterestRate(2.75);
        customer.getAccounts().add(account);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = account.calculateDailyInterest();
        
        // Expected Output: 0.09
        assertEquals(0.09, dailyInterest, 0.001);
        
        // Verify new balance after interest addition
        account.setBalance(account.getBalance() + dailyInterest);
        assertEquals(1234.65, account.getBalance(), 0.001);
    }
}