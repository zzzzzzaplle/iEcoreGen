import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.math.BigDecimal;

public class CR3Test {
    
    @Test
    public void testCase1_StandardBalanceAndRate() {
        // SetUp: Customer "Alie" with savings account "SAV007"
        Customer customer = new Customer();
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        
        SavingsAccount account = new SavingsAccount();
        account.setAccountId("SAV007");
        account.setBalance(new BigDecimal("10000"));
        account.setInterestRate(new BigDecimal("0.0365"));
        
        customer.addAccount(account);
        
        // Action: Trigger daily-interest calculation
        BigDecimal dailyInterest = account.calculateDailyInterest();
        
        // Expected Output: 1.01
        assertEquals(new BigDecimal("1.01"), dailyInterest);
        assertEquals(new BigDecimal("10001.01"), account.getBalance());
    }
    
    @Test
    public void testCase2_ZeroBalance() {
        // SetUp: Customer "Poe" with savings account "SAV008"
        Customer customer = new Customer();
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        
        SavingsAccount account = new SavingsAccount();
        account.setAccountId("SAV008");
        account.setBalance(BigDecimal.ZERO);
        account.setInterestRate(new BigDecimal("0.05"));
        
        customer.addAccount(account);
        
        // Action: Trigger daily-interest calculation
        BigDecimal dailyInterest = account.calculateDailyInterest();
        
        // Expected Output: 0.00
        assertEquals(BigDecimal.ZERO, dailyInterest);
        assertEquals(BigDecimal.ZERO, account.getBalance());
    }
    
    @Test
    public void testCase3_VerySmallBalance() {
        // SetUp: Customer "Paul" with savings account "SAV009"
        Customer customer = new Customer();
        customer.setName("Paul");
        customer.setAddress("0815 Center St");
        
        SavingsAccount account = new SavingsAccount();
        account.setAccountId("SAV009");
        account.setBalance(new BigDecimal("0.01"));
        account.setInterestRate(new BigDecimal("0.02"));
        
        customer.addAccount(account);
        
        // Action: Trigger daily-interest calculation
        BigDecimal dailyInterest = account.calculateDailyInterest();
        
        // Expected Output: 0.00 (rounds down)
        assertEquals(BigDecimal.ZERO, dailyInterest);
        assertEquals(new BigDecimal("0.01"), account.getBalance());
    }
    
    @Test
    public void testCase4_LargeBalance() {
        // SetUp: Customer "Peter" with savings account "SAV010"
        Customer customer = new Customer();
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        
        SavingsAccount account = new SavingsAccount();
        account.setAccountId("SAV010");
        account.setBalance(new BigDecimal("1000000"));
        account.setInterestRate(new BigDecimal("0.02"));
        
        customer.addAccount(account);
        
        // Action: Trigger daily-interest calculation
        BigDecimal dailyInterest = account.calculateDailyInterest();
        
        // Expected Output: 55.56
        assertEquals(new BigDecimal("55.56"), dailyInterest);
        assertEquals(new BigDecimal("1000055.56"), account.getBalance());
    }
    
    @Test
    public void testCase5_HighPrecisionRate() {
        // SetUp: Customer "Beli" with savings account "SAV011"
        Customer customer = new Customer();
        customer.setName("Beli");
        customer.setAddress("0819 Center St");
        
        SavingsAccount account = new SavingsAccount();
        account.setAccountId("SAV011");
        account.setBalance(new BigDecimal("1234.56"));
        account.setInterestRate(new BigDecimal("0.0275"));
        
        customer.addAccount(account);
        
        // Action: Trigger daily-interest calculation
        BigDecimal dailyInterest = account.calculateDailyInterest();
        
        // Expected Output: 0.09
        assertEquals(new BigDecimal("0.09"), dailyInterest);
        assertEquals(new BigDecimal("1234.65"), account.getBalance());
    }
}