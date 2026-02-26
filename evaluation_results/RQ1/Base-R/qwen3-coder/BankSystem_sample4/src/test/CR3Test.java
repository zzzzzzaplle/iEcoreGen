import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CR3Test {
    
    @Test
    public void testCase1_StandardBalanceAndRate() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds a savings account "SAV007"
        Customer customer = new Customer();
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        
        SavingsAccount account = new SavingsAccount();
        account.setId("SAV007");
        account.setBalance(new BigDecimal("10000"));
        account.setInterestRate(new BigDecimal("0.0365")); // 3.65%
        
        customer.addAccount(account);
        
        // Action: Trigger daily-interest calculation
        account.calculateAndAddDailyInterest();
        
        // Expected Output: 1.01 (interest added, new balance $10 001.01)
        BigDecimal expectedBalance = new BigDecimal("10001.01");
        assertEquals("Balance after daily interest should be 10001.01", 
                     expectedBalance, account.getBalance());
    }
    
    @Test
    public void testCase2_ZeroBalance() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a savings account "SAV008"
        Customer customer = new Customer();
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        
        SavingsAccount account = new SavingsAccount();
        account.setId("SAV008");
        account.setBalance(BigDecimal.ZERO);
        account.setInterestRate(new BigDecimal("0.05")); // 5%
        
        customer.addAccount(account);
        
        // Action: Trigger daily-interest calculation
        account.calculateAndAddDailyInterest();
        
        // Expected Output: 0.00
        BigDecimal expectedBalance = BigDecimal.ZERO.setScale(2);
        assertEquals("Balance with zero principal should remain zero", 
                     expectedBalance, account.getBalance());
    }
    
    @Test
    public void testCase3_VerySmallBalance() {
        // SetUp: Customer "Paul" (address "0815 Center St") holds a savings account "SAV009"
        Customer customer = new Customer();
        customer.setName("Paul");
        customer.setAddress("0815 Center St");
        
        SavingsAccount account = new SavingsAccount();
        account.setId("SAV009");
        account.setBalance(new BigDecimal("0.01"));
        account.setInterestRate(new BigDecimal("0.02")); // 2%
        
        customer.addAccount(account);
        
        // Action: Trigger daily-interest calculation
        account.calculateAndAddDailyInterest();
        
        // Expected Output: 0.00 (rounds down)
        BigDecimal expectedBalance = new BigDecimal("0.01");
        assertEquals("Very small balance should round down to 0.00 interest", 
                     expectedBalance, account.getBalance());
    }
    
    @Test
    public void testCase4_LargeBalance() {
        // SetUp: Customer "Peter" (address "0816 Center St") holds a savings account "SAV010"
        Customer customer = new Customer();
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        
        SavingsAccount account = new SavingsAccount();
        account.setId("SAV010");
        account.setBalance(new BigDecimal("1000000"));
        account.setInterestRate(new BigDecimal("0.02")); // 2%
        
        customer.addAccount(account);
        
        // Action: Trigger daily-interest calculation
        account.calculateAndAddDailyInterest();
        
        // Expected Output: 55.56
        BigDecimal expectedBalance = new BigDecimal("1000055.56");
        assertEquals("Large balance should calculate correct daily interest", 
                     expectedBalance, account.getBalance());
    }
    
    @Test
    public void testCase5_HighPrecisionRate() {
        // SetUp: Customer "Beli" (address "0819 Center St") holds a savings account "SAV011"
        Customer customer = new Customer();
        customer.setName("Beli");
        customer.setAddress("0819 Center St");
        
        SavingsAccount account = new SavingsAccount();
        account.setId("SAV011");
        account.setBalance(new BigDecimal("1234.56"));
        account.setInterestRate(new BigDecimal("0.0275")); // 2.75%
        
        customer.addAccount(account);
        
        // Action: Trigger daily-interest calculation
        account.calculateAndAddDailyInterest();
        
        // Expected Output: 0.09
        BigDecimal expectedBalance = new BigDecimal("1234.65");
        assertEquals("High precision rate should calculate correct daily interest", 
                     expectedBalance, account.getBalance());
    }
}