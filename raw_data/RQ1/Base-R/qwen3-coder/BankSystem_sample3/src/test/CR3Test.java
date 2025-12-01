import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    @Test
    public void testCase1_StandardBalanceAndRate() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds a savings account "SAV007"
        Customer customer = new Customer();
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountId("SAV007");
        savingsAccount.setBalance(10000.0);
        savingsAccount.setInterestRate(3.65 / 100); // 3.65%
        
        customer.addAccount(savingsAccount);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = savingsAccount.calculateDailyInterest();
        
        // Expected Output: 1.01 - interest added, new balance $10 001.01
        assertEquals("Daily interest should be 1.01", 1.01, dailyInterest, 0.001);
        assertEquals("New balance should be 10001.01", 10001.01, savingsAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_ZeroBalance() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a savings account "SAV008"
        Customer customer = new Customer();
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountId("SAV008");
        savingsAccount.setBalance(0.0);
        savingsAccount.setInterestRate(5.0 / 100); // 5%
        
        customer.addAccount(savingsAccount);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = savingsAccount.calculateDailyInterest();
        
        // Expected Output: 0.00
        assertEquals("Daily interest should be 0.00", 0.00, dailyInterest, 0.001);
        assertEquals("Balance should remain 0.00", 0.00, savingsAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_VerySmallBalance() {
        // SetUp: Customer "Paul" (address "0815 Center St") holds a savings account "SAV009"
        Customer customer = new Customer();
        customer.setName("Paul");
        customer.setAddress("0815 Center St");
        
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountId("SAV009");
        savingsAccount.setBalance(0.01);
        savingsAccount.setInterestRate(2.0 / 100); // 2%
        
        customer.addAccount(savingsAccount);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = savingsAccount.calculateDailyInterest();
        
        // Expected Output: 0.00 - rounds down
        assertEquals("Daily interest should be 0.00", 0.00, dailyInterest, 0.001);
        assertEquals("Balance should remain 0.01", 0.01, savingsAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_LargeBalance() {
        // SetUp: Customer "Peter" (address "0816 Center St") holds a savings account "SAV010"
        Customer customer = new Customer();
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountId("SAV010");
        savingsAccount.setBalance(1000000.0);
        savingsAccount.setInterestRate(2.0 / 100); // 2%
        
        customer.addAccount(savingsAccount);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = savingsAccount.calculateDailyInterest();
        
        // Expected Output: 55.56
        assertEquals("Daily interest should be 55.56", 55.56, dailyInterest, 0.001);
        assertEquals("New balance should be 1000055.56", 1000055.56, savingsAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_HighPrecisionRate() {
        // SetUp: Customer "Beli" (address "0819 Center St") holds a savings account "SAV011"
        Customer customer = new Customer();
        customer.setName("Beli");
        customer.setAddress("0819 Center St");
        
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountId("SAV011");
        savingsAccount.setBalance(1234.56);
        savingsAccount.setInterestRate(2.75 / 100); // 2.75%
        
        customer.addAccount(savingsAccount);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = savingsAccount.calculateDailyInterest();
        
        // Expected Output: 0.09
        assertEquals("Daily interest should be 0.09", 0.09, dailyInterest, 0.001);
        assertEquals("New balance should be 1234.65", 1234.65, savingsAccount.getBalance(), 0.001);
    }
}