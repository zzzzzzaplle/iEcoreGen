import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    private Customer customer;
    private SavingsAccount savingsAccount;
    
    @Test
    public void testCase1_standardBalanceAndRate() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds a savings account "SAV007"
        customer = new Customer("Alie", "0812 Center St");
        savingsAccount = new SavingsAccount("SAV007", 10000.0, 3.65);
        customer.addAccount(savingsAccount);
        
        // Action: Trigger daily-interest calculation
        savingsAccount.calculateDailyInterest();
        
        // Expected Output: 1.01 interest added, new balance $10 001.01
        assertEquals(10001.01, savingsAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_zeroBalance() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a savings account "SAV008"
        customer = new Customer("Poe", "0814 Center St");
        savingsAccount = new SavingsAccount("SAV008", 0.0, 5.0);
        customer.addAccount(savingsAccount);
        
        // Action: Trigger daily-interest calculation
        savingsAccount.calculateDailyInterest();
        
        // Expected Output: 0.00
        assertEquals(0.00, savingsAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_verySmallBalance() {
        // SetUp: Customer "Paul" (address "0815 Center St") holds a savings account "SAV009"
        customer = new Customer("Paul", "0815 Center St");
        savingsAccount = new SavingsAccount("SAV009", 0.01, 2.0);
        customer.addAccount(savingsAccount);
        
        // Action: Trigger daily-interest calculation
        savingsAccount.calculateDailyInterest();
        
        // Expected Output: 0.00 (rounds down)
        assertEquals(0.01, savingsAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_largeBalance() {
        // SetUp: Customer "Peter" (address "0816 Center St") holds a savings account "SAV010"
        customer = new Customer("Peter", "0816 Center St");
        savingsAccount = new SavingsAccount("SAV010", 1000000.0, 2.0);
        customer.addAccount(savingsAccount);
        
        // Action: Trigger daily-interest calculation
        savingsAccount.calculateDailyInterest();
        
        // Expected Output: 55.56
        assertEquals(1000055.56, savingsAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_highPrecisionRate() {
        // SetUp: Customer "Beli" (address "0819 Center St") holds a savings account "SAV011"
        customer = new Customer("Beli", "0819 Center St");
        savingsAccount = new SavingsAccount("SAV011", 1234.56, 2.75);
        customer.addAccount(savingsAccount);
        
        // Action: Trigger daily-interest calculation
        savingsAccount.calculateDailyInterest();
        
        // Expected Output: 0.09
        assertEquals(1234.65, savingsAccount.getBalance(), 0.001);
    }
}