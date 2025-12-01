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
        // SetUp: Customer "Alie" (address "0812 Center St") holds a savings account "SAV007"
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        
        // SetUp: "SAV007" balance $10 000, interest rate 3.65 %
        boolean accountAdded = customer.addSavingAccount("SAV007", 0.0365);
        assertTrue("Savings account should be added successfully", accountAdded);
        
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV007");
        account.setBalance(10000.0);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = account.calculateDailyInterest();
        
        // Expected Output: 1.01
        assertEquals(1.01, dailyInterest, 0.001);
        
        // Verify new balance after interest is added
        account.setBalance(account.getBalance() + dailyInterest);
        assertEquals(10001.01, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_ZeroBalance() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a savings account "SAV008"
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        
        // SetUp: Savings account "SAV008" balance $0, interest rate 5 %
        boolean accountAdded = customer.addSavingAccount("SAV008", 0.05);
        assertTrue("Savings account should be added successfully", accountAdded);
        
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV008");
        account.setBalance(0.0);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = account.calculateDailyInterest();
        
        // Expected Output: 0.00
        assertEquals(0.00, dailyInterest, 0.001);
    }
    
    @Test
    public void testCase3_VerySmallBalance() {
        // SetUp: Customer "Paul" (address "0815 Center St") holds a savings account "SAV009"
        customer.setName("Paul");
        customer.setAddress("0815 Center St");
        
        // SetUp: Savings account "SAV009" balance $0.01, interest rate 2 %
        boolean accountAdded = customer.addSavingAccount("SAV009", 0.02);
        assertTrue("Savings account should be added successfully", accountAdded);
        
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV009");
        account.setBalance(0.01);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = account.calculateDailyInterest();
        
        // Expected Output: 0.00 (rounds down)
        assertEquals(0.00, dailyInterest, 0.001);
    }
    
    @Test
    public void testCase4_LargeBalance() {
        // SetUp: Customer "Peter" (address "0816 Center St") holds a savings account "SAV010"
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        
        // SetUp: Savings account "SAV010" balance $1 000 000, interest rate 2 %
        boolean accountAdded = customer.addSavingAccount("SAV010", 0.02);
        assertTrue("Savings account should be added successfully", accountAdded);
        
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV010");
        account.setBalance(1000000.0);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = account.calculateDailyInterest();
        
        // Expected Output: 55.56
        assertEquals(55.56, dailyInterest, 0.001);
    }
    
    @Test
    public void testCase5_HighPrecisionRate() {
        // SetUp: Customer "Beli" (address "0819 Center St") holds a savings account "SAV011"
        customer.setName("Beli");
        customer.setAddress("0819 Center St");
        
        // SetUp: Savings account "SAV011" balance $1 234.56, interest rate 2.75 %
        boolean accountAdded = customer.addSavingAccount("SAV011", 0.0275);
        assertTrue("Savings account should be added successfully", accountAdded);
        
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV011");
        account.setBalance(1234.56);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = account.calculateDailyInterest();
        
        // Expected Output: 0.09
        assertEquals(0.09, dailyInterest, 0.001);
    }
}