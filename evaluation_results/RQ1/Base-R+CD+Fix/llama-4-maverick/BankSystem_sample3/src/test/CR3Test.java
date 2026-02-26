import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    @Test
    public void testCase1_StandardBalanceAndRate() {
        // Set up customer and savings account
        Customer customer = new Customer();
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        
        // Add savings account with specified parameters
        customer.addSavingAccount("SAV007", 3.65);
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV007");
        account.setBalance(10000.00);
        
        // Trigger daily interest calculation
        double dailyInterest = account.calculateDailyInterest();
        
        // Verify the calculated daily interest
        assertEquals(1.01, dailyInterest, 0.001);
        // Verify the updated balance
        assertEquals(10001.01, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_ZeroBalance() {
        // Set up customer and savings account
        Customer customer = new Customer();
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        
        // Add savings account with zero balance
        customer.addSavingAccount("SAV008", 5.0);
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV008");
        account.setBalance(0.00);
        
        // Trigger daily interest calculation
        double dailyInterest = account.calculateDailyInterest();
        
        // Verify the calculated daily interest
        assertEquals(0.00, dailyInterest, 0.001);
        // Verify the balance remains zero
        assertEquals(0.00, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_VerySmallBalance() {
        // Set up customer and savings account
        Customer customer = new Customer();
        customer.setName("Paul");
        customer.setAddress("0815 Center St");
        
        // Add savings account with very small balance
        customer.addSavingAccount("SAV009", 2.0);
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV009");
        account.setBalance(0.01);
        
        // Trigger daily interest calculation
        double dailyInterest = account.calculateDailyInterest();
        
        // Verify the calculated daily interest (should round down to 0.00)
        assertEquals(0.00, dailyInterest, 0.001);
        // Verify the balance remains the same
        assertEquals(0.01, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_LargeBalance() {
        // Set up customer and savings account
        Customer customer = new Customer();
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        
        // Add savings account with large balance
        customer.addSavingAccount("SAV010", 2.0);
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV010");
        account.setBalance(1000000.00);
        
        // Trigger daily interest calculation
        double dailyInterest = account.calculateDailyInterest();
        
        // Verify the calculated daily interest
        assertEquals(55.56, dailyInterest, 0.001);
        // Verify the updated balance
        assertEquals(1000055.56, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_HighPrecisionRate() {
        // Set up customer and savings account
        Customer customer = new Customer();
        customer.setName("Beli");
        customer.setAddress("0819 Center St");
        
        // Add savings account with high precision rate
        customer.addSavingAccount("SAV011", 2.75);
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV011");
        account.setBalance(1234.56);
        
        // Trigger daily interest calculation
        double dailyInterest = account.calculateDailyInterest();
        
        // Verify the calculated daily interest
        assertEquals(0.09, dailyInterest, 0.001);
        // Verify the updated balance
        assertEquals(1234.65, account.getBalance(), 0.001);
    }
}