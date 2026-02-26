import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    @Test
    public void testCase1_StandardBalanceAndRate() {
        // SetUp: Customer "Alie" with savings account "SAV007"
        Customer customer = new Customer();
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        
        // Create savings account with balance $10,000 and interest rate 3.65%
        boolean accountAdded = customer.addSavingAccount("SAV007", 3.65);
        assertTrue("Account should be added successfully", accountAdded);
        
        SavingAccount savingAccount = (SavingAccount) customer.findAccountById("SAV007");
        savingAccount.setBalance(10000.0);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = savingAccount.calculateDailyInterest();
        
        // Expected Output: 1.01
        assertEquals("Daily interest should be 1.01", 1.01, dailyInterest, 0.001);
        assertEquals("New balance should be 10001.01", 10001.01, savingAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_ZeroBalance() {
        // SetUp: Customer "Poe" with savings account "SAV008"
        Customer customer = new Customer();
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        
        // Create savings account with balance $0 and interest rate 5%
        boolean accountAdded = customer.addSavingAccount("SAV008", 5.0);
        assertTrue("Account should be added successfully", accountAdded);
        
        SavingAccount savingAccount = (SavingAccount) customer.findAccountById("SAV008");
        savingAccount.setBalance(0.0);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = savingAccount.calculateDailyInterest();
        
        // Expected Output: 0.00
        assertEquals("Daily interest should be 0.00", 0.00, dailyInterest, 0.001);
        assertEquals("Balance should remain 0.00", 0.00, savingAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_VerySmallBalance() {
        // SetUp: Customer "Paul" with savings account "SAV009"
        Customer customer = new Customer();
        customer.setName("Paul");
        customer.setAddress("0815 Center St");
        
        // Create savings account with balance $0.01 and interest rate 2%
        boolean accountAdded = customer.addSavingAccount("SAV009", 2.0);
        assertTrue("Account should be added successfully", accountAdded);
        
        SavingAccount savingAccount = (SavingAccount) customer.findAccountById("SAV009");
        savingAccount.setBalance(0.01);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = savingAccount.calculateDailyInterest();
        
        // Expected Output: 0.00 (rounds down)
        assertEquals("Daily interest should be 0.00", 0.00, dailyInterest, 0.001);
        assertEquals("Balance should remain 0.01", 0.01, savingAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_LargeBalance() {
        // SetUp: Customer "Peter" with savings account "SAV010"
        Customer customer = new Customer();
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        
        // Create savings account with balance $1,000,000 and interest rate 2%
        boolean accountAdded = customer.addSavingAccount("SAV010", 2.0);
        assertTrue("Account should be added successfully", accountAdded);
        
        SavingAccount savingAccount = (SavingAccount) customer.findAccountById("SAV010");
        savingAccount.setBalance(1000000.0);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = savingAccount.calculateDailyInterest();
        
        // Expected Output: 55.56
        assertEquals("Daily interest should be 55.56", 55.56, dailyInterest, 0.001);
        assertEquals("New balance should be 1000055.56", 1000055.56, savingAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_HighPrecisionRate() {
        // SetUp: Customer "Beli" with savings account "SAV011"
        Customer customer = new Customer();
        customer.setName("Beli");
        customer.setAddress("0819 Center St");
        
        // Create savings account with balance $1,234.56 and interest rate 2.75%
        boolean accountAdded = customer.addSavingAccount("SAV011", 2.75);
        assertTrue("Account should be added successfully", accountAdded);
        
        SavingAccount savingAccount = (SavingAccount) customer.findAccountById("SAV011");
        savingAccount.setBalance(1234.56);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = savingAccount.calculateDailyInterest();
        
        // Expected Output: 0.09
        assertEquals("Daily interest should be 0.09", 0.09, dailyInterest, 0.001);
        assertEquals("New balance should be 1234.65", 1234.65, savingAccount.getBalance(), 0.001);
    }
}