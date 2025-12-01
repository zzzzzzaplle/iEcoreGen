// IMPORTANT: Do not include package declaration
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CR3Test {
    
    @Test
    public void testCase1_standardBalanceAndRate() {
        // SetUp: Customer "Alie" with savings account "SAV007"
        // Balance $10,000, interest rate 3.65%
        Customer customer = new Customer();
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        
        customer.addSavingAccount("SAV007", 0.0365);
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV007");
        account.setBalance(10000.00);
        
        // Action: Calculate daily interest
        double dailyInterest = account.calculateDailyInterest();
        
        // Expected Output: 1.01
        assertEquals(1.01, dailyInterest, 0.001);
        
        // Simulate adding interest to balance
        account.setBalance(account.getBalance() + dailyInterest);
        assertEquals(10001.01, account.getBalance(), 0.01);
    }
    
    @Test
    public void testCase2_zeroBalance() {
        // SetUp: Customer "Poe" with savings account "SAV008"
        // Balance $0, interest rate 5%
        Customer customer = new Customer();
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        
        customer.addSavingAccount("SAV008", 0.05);
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV008");
        account.setBalance(0.00);
        
        // Action: Calculate daily interest
        double dailyInterest = account.calculateDailyInterest();
        
        // Expected Output: 0.00
        assertEquals(0.00, dailyInterest, 0.001);
    }
    
    @Test
    public void testCase3_verySmallBalance() {
        // SetUp: Customer "Paul" with savings account "SAV009"
        // Balance $0.01, interest rate 2%
        Customer customer = new Customer();
        customer.setName("Paul");
        customer.setAddress("0815 Center St");
        
        customer.addSavingAccount("SAV009", 0.02);
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV009");
        account.setBalance(0.01);
        
        // Action: Calculate daily interest
        double dailyInterest = account.calculateDailyInterest();
        
        // Expected Output: 0.00 (rounds down)
        assertEquals(0.00, dailyInterest, 0.001);
    }
    
    @Test
    public void testCase4_largeBalance() {
        // SetUp: Customer "Peter" with savings account "SAV010"
        // Balance $1,000,000, interest rate 2%
        Customer customer = new Customer();
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        
        customer.addSavingAccount("SAV010", 0.02);
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV010");
        account.setBalance(1000000.00);
        
        // Action: Calculate daily interest
        double dailyInterest = account.calculateDailyInterest();
        
        // Expected Output: 55.56
        assertEquals(55.56, dailyInterest, 0.001);
    }
    
    @Test
    public void testCase5_highPrecisionRate() {
        // SetUp: Customer "Beli" with savings account "SAV011"
        // Balance $1,234.56, interest rate 2.75%
        Customer customer = new Customer();
        customer.setName("Beli");
        customer.setAddress("0819 Center St");
        
        customer.addSavingAccount("SAV011", 0.0275);
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV011");
        account.setBalance(1234.56);
        
        // Action: Calculate daily interest
        double dailyInterest = account.calculateDailyInterest();
        
        // Expected Output: 0.09
        assertEquals(0.09, dailyInterest, 0.001);
    }
}