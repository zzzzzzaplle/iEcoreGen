import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.math.BigDecimal;

public class CR3Test {
    
    private SavingsAccount savingsAccount;
    
    @Before
    public void setUp() {
        // Reset savings account before each test
        savingsAccount = new SavingsAccount();
    }
    
    @Test
    public void testCase1_StandardBalanceAndRate() {
        // SetUp: Customer "Alie" holds savings account "SAV007" with balance $10,000, interest rate 3.65%
        savingsAccount.setAccountId("SAV007");
        savingsAccount.setBalance(new BigDecimal("10000.00"));
        savingsAccount.setInterestRate(new BigDecimal("0.0365")); // 3.65%
        
        // Action: Trigger daily-interest calculation
        savingsAccount.calculateDailyInterest();
        
        // Expected Output: 1.01 (interest added, new balance $10,001.01)
        BigDecimal expectedBalance = new BigDecimal("10001.01");
        assertEquals("Balance after daily interest should be 10001.01", 
                     expectedBalance, savingsAccount.getBalance());
    }
    
    @Test
    public void testCase2_ZeroBalance() {
        // SetUp: Customer "Poe" holds savings account "SAV008" with balance $0, interest rate 5%
        savingsAccount.setAccountId("SAV008");
        savingsAccount.setBalance(BigDecimal.ZERO);
        savingsAccount.setInterestRate(new BigDecimal("0.05")); // 5%
        
        // Action: Trigger daily-interest calculation
        savingsAccount.calculateDailyInterest();
        
        // Expected Output: 0.00
        assertEquals("Balance with zero principal should remain zero", 
                     BigDecimal.ZERO, savingsAccount.getBalance());
    }
    
    @Test
    public void testCase3_VerySmallBalance() {
        // SetUp: Customer "Paul" holds savings account "SAV009" with balance $0.01, interest rate 2%
        savingsAccount.setAccountId("SAV009");
        savingsAccount.setBalance(new BigDecimal("0.01"));
        savingsAccount.setInterestRate(new BigDecimal("0.02")); // 2%
        
        // Action: Trigger daily-interest calculation
        savingsAccount.calculateDailyInterest();
        
        // Expected Output: 0.00 (rounds down)
        BigDecimal expectedBalance = new BigDecimal("0.01"); // Should remain 0.01 due to rounding
        assertEquals("Very small balance should round down to original amount", 
                     expectedBalance, savingsAccount.getBalance());
    }
    
    @Test
    public void testCase4_LargeBalance() {
        // SetUp: Customer "Peter" holds savings account "SAV010" with balance $1,000,000, interest rate 2%
        savingsAccount.setAccountId("SAV010");
        savingsAccount.setBalance(new BigDecimal("1000000.00"));
        savingsAccount.setInterestRate(new BigDecimal("0.02")); // 2%
        
        // Action: Trigger daily-interest calculation
        savingsAccount.calculateDailyInterest();
        
        // Expected Output: 55.56 (interest added)
        BigDecimal expectedBalance = new BigDecimal("1000055.56");
        assertEquals("Large balance should calculate correct daily interest", 
                     expectedBalance, savingsAccount.getBalance());
    }
    
    @Test
    public void testCase5_HighPrecisionRate() {
        // SetUp: Customer "Beli" holds savings account "SAV011" with balance $1,234.56, interest rate 2.75%
        savingsAccount.setAccountId("SAV011");
        savingsAccount.setBalance(new BigDecimal("1234.56"));
        savingsAccount.setInterestRate(new BigDecimal("0.0275")); // 2.75%
        
        // Action: Trigger daily-interest calculation
        savingsAccount.calculateDailyInterest();
        
        // Expected Output: 0.09 (interest added)
        BigDecimal expectedBalance = new BigDecimal("1234.65");
        assertEquals("High precision rate should calculate correct daily interest", 
                     expectedBalance, savingsAccount.getBalance());
    }
}