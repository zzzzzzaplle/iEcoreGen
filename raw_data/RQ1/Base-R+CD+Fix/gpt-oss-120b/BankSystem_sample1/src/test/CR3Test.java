import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    private Customer customer;
    
    @Before
    public void setUp() {
        // This method runs before each test
        customer = new Customer();
    }
    
    @Test
    public void testCase1_standardBalanceAndRate() {
        // SetUp: Customer "Alie" with address "0812 Center St" holds savings account "SAV007"
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        
        // Create savings account with balance $10,000 and interest rate 3.65%
        customer.addSavingAccount("SAV007", 0.0365);
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV007");
        account.setBalance(10000.0);
        
        // Action: Trigger daily-interest calculation
        double interest = account.calculateDailyInterest();
        
        // Expected Output: 1.01
        assertEquals(1.01, interest, 0.001);
        assertEquals(10001.01, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_zeroBalance() {
        // SetUp: Customer "Poe" with address "0814 Center St" holds savings account "SAV008"
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        
        // Create savings account with balance $0 and interest rate 5%
        customer.addSavingAccount("SAV008", 0.05);
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV008");
        account.setBalance(0.0);
        
        // Action: Trigger daily-interest calculation
        double interest = account.calculateDailyInterest();
        
        // Expected Output: 0.00
        assertEquals(0.00, interest, 0.001);
        assertEquals(0.00, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_verySmallBalance() {
        // SetUp: Customer "Paul" with address "0815 Center St" holds savings account "SAV009"
        customer.setName("Paul");
        customer.setAddress("0815 Center St");
        
        // Create savings account with balance $0.01 and interest rate 2%
        customer.addSavingAccount("SAV009", 0.02);
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV009");
        account.setBalance(0.01);
        
        // Action: Trigger daily-interest calculation
        double interest = account.calculateDailyInterest();
        
        // Expected Output: 0.00 (rounds down)
        assertEquals(0.00, interest, 0.001);
        assertEquals(0.01, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_largeBalance() {
        // SetUp: Customer "Peter" with address "0816 Center St" holds savings account "SAV010"
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        
        // Create savings account with balance $1,000,000 and interest rate 2%
        customer.addSavingAccount("SAV010", 0.02);
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV010");
        account.setBalance(1000000.0);
        
        // Action: Trigger daily-interest calculation
        double interest = account.calculateDailyInterest();
        
        // Expected Output: 55.56
        assertEquals(55.56, interest, 0.001);
        assertEquals(1000055.56, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_highPrecisionRate() {
        // SetUp: Customer "Beli" with address "0819 Center St" holds savings account "SAV011"
        customer.setName("Beli");
        customer.setAddress("0819 Center St");
        
        // Create savings account with balance $1,234.56 and interest rate 2.75%
        customer.addSavingAccount("SAV011", 0.0275);
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV011");
        account.setBalance(1234.56);
        
        // Action: Trigger daily-interest calculation
        double interest = account.calculateDailyInterest();
        
        // Expected Output: 0.09
        assertEquals(0.09, interest, 0.001);
        assertEquals(1234.65, account.getBalance(), 0.001);
    }
}