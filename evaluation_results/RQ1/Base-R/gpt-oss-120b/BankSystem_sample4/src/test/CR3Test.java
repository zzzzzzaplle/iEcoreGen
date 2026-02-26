import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CR3Test {
    
    @Test
    public void testCase1_StandardBalanceAndRate() {
        // SetUp: Customer "Alie" with savings account "SAV007"
        // Balance $10,000, interest rate 3.65%
        Customer customer = new Customer("Alie", "0812 Center St");
        SavingsAccount account = new SavingsAccount("SAV007", new BigDecimal("0.0365"));
        account.setBalance(new BigDecimal("10000.00"));
        customer.addAccount(account);
        
        // Action: Trigger daily-interest calculation
        BigDecimal interest = account.calculateDailyInterest();
        
        // Expected Output: 1.01
        assertEquals(new BigDecimal("1.01"), interest);
        assertEquals(new BigDecimal("10001.01"), account.getBalance());
    }
    
    @Test
    public void testCase2_ZeroBalance() {
        // SetUp: Customer "Poe" with savings account "SAV008"
        // Balance $0, interest rate 5%
        Customer customer = new Customer("Poe", "0814 Center St");
        SavingsAccount account = new SavingsAccount("SAV008", new BigDecimal("0.05"));
        account.setBalance(BigDecimal.ZERO);
        customer.addAccount(account);
        
        // Action: Trigger daily-interest calculation
        BigDecimal interest = account.calculateDailyInterest();
        
        // Expected Output: 0.00
        assertEquals(new BigDecimal("0.00"), interest);
        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), account.getBalance());
    }
    
    @Test
    public void testCase3_VerySmallBalance() {
        // SetUp: Customer "Paul" with savings account "SAV009"
        // Balance $0.01, interest rate 2%
        Customer customer = new Customer("Paul", "0815 Center St");
        SavingsAccount account = new SavingsAccount("SAV009", new BigDecimal("0.02"));
        account.setBalance(new BigDecimal("0.01"));
        customer.addAccount(account);
        
        // Action: Trigger daily-interest calculation
        BigDecimal interest = account.calculateDailyInterest();
        
        // Expected Output: 0.00 (rounds down)
        assertEquals(new BigDecimal("0.00"), interest);
        assertEquals(new BigDecimal("0.01"), account.getBalance());
    }
    
    @Test
    public void testCase4_LargeBalance() {
        // SetUp: Customer "Peter" with savings account "SAV010"
        // Balance $1,000,000, interest rate 2%
        Customer customer = new Customer("Peter", "0816 Center St");
        SavingsAccount account = new SavingsAccount("SAV010", new BigDecimal("0.02"));
        account.setBalance(new BigDecimal("1000000.00"));
        customer.addAccount(account);
        
        // Action: Trigger daily-interest calculation
        BigDecimal interest = account.calculateDailyInterest();
        
        // Expected Output: 55.56
        assertEquals(new BigDecimal("55.56"), interest);
        assertEquals(new BigDecimal("1000055.56"), account.getBalance());
    }
    
    @Test
    public void testCase5_HighPrecisionRate() {
        // SetUp: Customer "Beli" with savings account "SAV011"
        // Balance $1,234.56, interest rate 2.75%
        Customer customer = new Customer("Beli", "0819 Center St");
        SavingsAccount account = new SavingsAccount("SAV011", new BigDecimal("0.0275"));
        account.setBalance(new BigDecimal("1234.56"));
        customer.addAccount(account);
        
        // Action: Trigger daily-interest calculation
        BigDecimal interest = account.calculateDailyInterest();
        
        // Expected Output: 0.09
        assertEquals(new BigDecimal("0.09"), interest);
        assertEquals(new BigDecimal("1234.65"), account.getBalance());
    }
}