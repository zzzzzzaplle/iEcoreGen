import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.math.BigDecimal;

public class CR3Test {
    
    private Customer customer;
    private SavingsAccount savingsAccount;
    
    @Before
    public void setUp() {
        customer = new Customer();
        savingsAccount = new SavingsAccount();
    }
    
    @Test
    public void testCase1_StandardBalanceAndRate() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds a savings account "SAV007"
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        
        // "SAV007" balance $10 000, interest rate 3.65 %
        savingsAccount.setId("SAV007");
        savingsAccount.setBalance(new BigDecimal("10000.00"));
        savingsAccount.setInterestRate(0.0365);
        customer.addAccount(savingsAccount);
        
        // Action: Trigger daily-interest calculation
        BigDecimal interestAdded = savingsAccount.applyDailyInterest();
        
        // Expected Output: 1.01 - interest added, new balance $10 001.01
        assertEquals(new BigDecimal("1.01"), interestAdded);
        assertEquals(new BigDecimal("10001.01"), savingsAccount.getBalance());
    }
    
    @Test
    public void testCase2_ZeroBalance() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a savings account "SAV008"
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        
        // Savings account "SAV008" balance $0, interest rate 5 %
        savingsAccount.setId("SAV008");
        savingsAccount.setBalance(BigDecimal.ZERO);
        savingsAccount.setInterestRate(0.05);
        customer.addAccount(savingsAccount);
        
        // Action: Trigger daily-interest calculation
        BigDecimal interestAdded = savingsAccount.applyDailyInterest();
        
        // Expected Output: 0.00
        assertEquals(new BigDecimal("0.00"), interestAdded);
        assertEquals(BigDecimal.ZERO.setScale(2), savingsAccount.getBalance());
    }
    
    @Test
    public void testCase3_VerySmallBalance() {
        // SetUp: Customer "Paul" (address "0815 Center St") holds a savings account "SAV009"
        customer.setName("Paul");
        customer.setAddress("0815 Center St");
        
        // Savings account "SAV009" balance $0.01, interest rate 2 %
        savingsAccount.setId("SAV009");
        savingsAccount.setBalance(new BigDecimal("0.01"));
        savingsAccount.setInterestRate(0.02);
        customer.addAccount(savingsAccount);
        
        // Action: Trigger daily-interest calculation
        BigDecimal interestAdded = savingsAccount.applyDailyInterest();
        
        // Expected Output: 0.00 - rounds down
        assertEquals(new BigDecimal("0.00"), interestAdded);
        assertEquals(new BigDecimal("0.01"), savingsAccount.getBalance());
    }
    
    @Test
    public void testCase4_LargeBalance() {
        // SetUp: Customer "Peter" (address "0816 Center St") holds a savings account "SAV010"
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        
        // Savings account "SAV010" balance $1 000 000, interest rate 2 %
        savingsAccount.setId("SAV010");
        savingsAccount.setBalance(new BigDecimal("1000000.00"));
        savingsAccount.setInterestRate(0.02);
        customer.addAccount(savingsAccount);
        
        // Action: Trigger daily-interest calculation
        BigDecimal interestAdded = savingsAccount.applyDailyInterest();
        
        // Expected Output: 55.56
        assertEquals(new BigDecimal("55.56"), interestAdded);
        assertEquals(new BigDecimal("1000055.56"), savingsAccount.getBalance());
    }
    
    @Test
    public void testCase5_HighPrecisionRate() {
        // SetUp: Customer "Beli" (address "0819 Center St") holds a savings account "SAV011"
        customer.setName("Beli");
        customer.setAddress("0819 Center St");
        
        // Savings account "SAV011" balance $1 234.56, interest rate 2.75 %
        savingsAccount.setId("SAV011");
        savingsAccount.setBalance(new BigDecimal("1234.56"));
        savingsAccount.setInterestRate(0.0275);
        customer.addAccount(savingsAccount);
        
        // Action: Trigger daily-interest calculation
        BigDecimal interestAdded = savingsAccount.applyDailyInterest();
        
        // Expected Output: 0.09
        assertEquals(new BigDecimal("0.09"), interestAdded);
        assertEquals(new BigDecimal("1234.65"), savingsAccount.getBalance());
    }
}