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
    public void testCase1_StandardBalanceAndRate() {
        // SetUp: Customer "Alie" with savings account "SAV007"
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        
        // Create savings account with balance $10,000 and interest rate 3.65%
        boolean accountAdded = customer.addSavingAccount("SAV007", 0.0365);
        assertTrue("Account should be added successfully", accountAdded);
        
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV007");
        assertNotNull("Account should be found", account);
        account.setBalance(10000.0);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = account.calculateDailyInterest();
        
        // Expected Output: 1.01
        assertEquals("Daily interest should be 1.01", 1.01, dailyInterest, 0.001);
        
        // Verify new balance after adding interest
        account.setBalance(account.getBalance() + dailyInterest);
        assertEquals("New balance should be 10001.01", 10001.01, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_ZeroBalance() {
        // SetUp: Customer "Poe" with savings account "SAV008"
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        
        // Create savings account with balance $0 and interest rate 5%
        boolean accountAdded = customer.addSavingAccount("SAV008", 0.05);
        assertTrue("Account should be added successfully", accountAdded);
        
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV008");
        assertNotNull("Account should be found", account);
        account.setBalance(0.0);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = account.calculateDailyInterest();
        
        // Expected Output: 0.00
        assertEquals("Daily interest should be 0.00", 0.00, dailyInterest, 0.001);
        
        // Verify balance remains unchanged
        assertEquals("Balance should remain 0.00", 0.00, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_VerySmallBalance() {
        // SetUp: Customer "Paul" with savings account "SAV009"
        customer.setName("Paul");
        customer.setAddress("0815 Center St");
        
        // Create savings account with balance $0.01 and interest rate 2%
        boolean accountAdded = customer.addSavingAccount("SAV009", 0.02);
        assertTrue("Account should be added successfully", accountAdded);
        
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV009");
        assertNotNull("Account should be found", account);
        account.setBalance(0.01);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = account.calculateDailyInterest();
        
        // Expected Output: 0.00 (rounds down)
        assertEquals("Daily interest should be 0.00", 0.00, dailyInterest, 0.001);
        
        // Verify balance remains unchanged
        assertEquals("Balance should remain 0.01", 0.01, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_LargeBalance() {
        // SetUp: Customer "Peter" with savings account "SAV010"
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        
        // Create savings account with balance $1,000,000 and interest rate 2%
        boolean accountAdded = customer.addSavingAccount("SAV010", 0.02);
        assertTrue("Account should be added successfully", accountAdded);
        
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV010");
        assertNotNull("Account should be found", account);
        account.setBalance(1000000.0);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = account.calculateDailyInterest();
        
        // Expected Output: 55.56
        assertEquals("Daily interest should be 55.56", 55.56, dailyInterest, 0.001);
        
        // Verify new balance after adding interest
        account.setBalance(account.getBalance() + dailyInterest);
        assertEquals("New balance should be 1000055.56", 1000055.56, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_HighPrecisionRate() {
        // SetUp: Customer "Beli" with savings account "SAV011"
        customer.setName("Beli");
        customer.setAddress("0819 Center St");
        
        // Create savings account with balance $1,234.56 and interest rate 2.75%
        boolean accountAdded = customer.addSavingAccount("SAV011", 0.0275);
        assertTrue("Account should be added successfully", accountAdded);
        
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV011");
        assertNotNull("Account should be found", account);
        account.setBalance(1234.56);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = account.calculateDailyInterest();
        
        // Expected Output: 0.09
        assertEquals("Daily interest should be 0.09", 0.09, dailyInterest, 0.001);
        
        // Verify new balance after adding interest
        account.setBalance(account.getBalance() + dailyInterest);
        assertEquals("New balance should be 1234.65", 1234.65, account.getBalance(), 0.001);
    }
}