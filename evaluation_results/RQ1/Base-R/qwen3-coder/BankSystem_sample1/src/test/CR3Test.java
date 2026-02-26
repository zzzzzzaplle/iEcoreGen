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
        
        SavingsAccount account = new SavingsAccount();
        account.setAccountId("SAV007");
        account.setBalance(10000.0);
        account.setInterestRate(0.0365); // 3.65%
        
        customer.addAccount(account);
        
        // Action: Trigger daily-interest calculation
        account.calculateAndAddDailyInterest();
        
        // Expected Output: 1.01 (interest added, new balance $10,001.01)
        assertEquals(10001.01, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_ZeroBalance() {
        // SetUp: Customer "Poe" with savings account "SAV008"
        Customer customer = new Customer();
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        
        SavingsAccount account = new SavingsAccount();
        account.setAccountId("SAV008");
        account.setBalance(0.0);
        account.setInterestRate(0.05); // 5%
        
        customer.addAccount(account);
        
        // Action: Trigger daily-interest calculation
        account.calculateAndAddDailyInterest();
        
        // Expected Output: 0.00
        assertEquals(0.00, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_VerySmallBalance() {
        // SetUp: Customer "Paul" with savings account "SAV009"
        Customer customer = new Customer();
        customer.setName("Paul");
        customer.setAddress("0815 Center St");
        
        SavingsAccount account = new SavingsAccount();
        account.setAccountId("SAV009");
        account.setBalance(0.01);
        account.setInterestRate(0.02); // 2%
        
        customer.addAccount(account);
        
        // Action: Trigger daily-interest calculation
        account.calculateAndAddDailyInterest();
        
        // Expected Output: 0.00 (rounds down)
        assertEquals(0.01, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_LargeBalance() {
        // SetUp: Customer "Peter" with savings account "SAV010"
        Customer customer = new Customer();
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        
        SavingsAccount account = new SavingsAccount();
        account.setAccountId("SAV010");
        account.setBalance(1000000.0);
        account.setInterestRate(0.02); // 2%
        
        customer.addAccount(account);
        
        // Action: Trigger daily-interest calculation
        account.calculateAndAddDailyInterest();
        
        // Expected Output: 55.56
        assertEquals(1000055.56, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_HighPrecisionRate() {
        // SetUp: Customer "Beli" with savings account "SAV011"
        Customer customer = new Customer();
        customer.setName("Beli");
        customer.setAddress("0819 Center St");
        
        SavingsAccount account = new SavingsAccount();
        account.setAccountId("SAV011");
        account.setBalance(1234.56);
        account.setInterestRate(0.0275); // 2.75%
        
        customer.addAccount(account);
        
        // Action: Trigger daily-interest calculation
        account.calculateAndAddDailyInterest();
        
        // Expected Output: 0.09
        assertEquals(1234.65, account.getBalance(), 0.001);
    }
}