import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    private Customer customer;
    private SavingsAccount savingsAccount;
    
    @Before
    public void setUp() {
        customer = new Customer();
        savingsAccount = new SavingsAccount();
    }
    
    @Test
    public void testCase1_StandardBalanceAndRate() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds a savings account "SAV007"
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        savingsAccount.setAccountId("SAV007");
        savingsAccount.setBalance(10000.0);
        savingsAccount.setInterestRate(3.65);
        customer.addAccount(savingsAccount);
        
        // Action: Trigger daily-interest calculation
        savingsAccount.calculateDailyInterest();
        
        // Expected Output: 1.01 interest added, new balance $10 001.01
        assertEquals(10001.01, savingsAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_ZeroBalance() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a savings account "SAV008"
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        savingsAccount.setAccountId("SAV008");
        savingsAccount.setBalance(0.0);
        savingsAccount.setInterestRate(5.0);
        customer.addAccount(savingsAccount);
        
        // Action: Trigger daily-interest calculation
        savingsAccount.calculateDailyInterest();
        
        // Expected Output: 0.00
        assertEquals(0.00, savingsAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_VerySmallBalance() {
        // SetUp: Customer "Paul" (address "0815 Center St") holds a savings account "SAV009"
        customer.setName("Paul");
        customer.setAddress("0815 Center St");
        savingsAccount.setAccountId("SAV009");
        savingsAccount.setBalance(0.01);
        savingsAccount.setInterestRate(2.0);
        customer.addAccount(savingsAccount);
        
        // Action: Trigger daily-interest calculation
        savingsAccount.calculateDailyInterest();
        
        // Expected Output: 0.00 (rounds down)
        assertEquals(0.01, savingsAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_LargeBalance() {
        // SetUp: Customer "Peter" (address "0816 Center St") holds a savings account "SAV010"
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        savingsAccount.setAccountId("SAV010");
        savingsAccount.setBalance(1000000.0);
        savingsAccount.setInterestRate(2.0);
        customer.addAccount(savingsAccount);
        
        // Action: Trigger daily-interest calculation
        savingsAccount.calculateDailyInterest();
        
        // Expected Output: 55.56
        assertEquals(1000055.56, savingsAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_HighPrecisionRate() {
        // SetUp: Customer "Beli" (address "0819 Center St") holds a savings account "SAV011"
        customer.setName("Beli");
        customer.setAddress("0819 Center St");
        savingsAccount.setAccountId("SAV011");
        savingsAccount.setBalance(1234.56);
        savingsAccount.setInterestRate(2.75);
        customer.addAccount(savingsAccount);
        
        // Action: Trigger daily-interest calculation
        savingsAccount.calculateDailyInterest();
        
        // Expected Output: 0.09
        assertEquals(1234.65, savingsAccount.getBalance(), 0.001);
    }
}