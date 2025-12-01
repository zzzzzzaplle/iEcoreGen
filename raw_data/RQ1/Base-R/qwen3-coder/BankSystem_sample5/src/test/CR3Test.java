import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.math.BigDecimal;

public class CR3Test {
    
    @Test
    public void testCase1_StandardBalanceAndRate() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds a savings account "SAV007"
        // "SAV007" balance $10 000, interest rate 3.65 %
        Customer customer = new Customer();
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountId("SAV007");
        savingsAccount.setBalance(new BigDecimal("10000.00"));
        savingsAccount.setInterestRate(new BigDecimal("0.0365"));
        
        customer.addAccount(savingsAccount);
        
        // Action: Trigger daily-interest calculation
        savingsAccount.calculateDailyInterest();
        
        // Expected Output: 1.01 (interest added, new balance $10 001.01)
        BigDecimal expectedBalance = new BigDecimal("10001.01");
        assertEquals(expectedBalance, savingsAccount.getBalance());
    }
    
    @Test
    public void testCase2_ZeroBalance() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a savings account "SAV008"
        // Savings account "SAV008" balance $0, interest rate 5 %
        Customer customer = new Customer();
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountId("SAV008");
        savingsAccount.setBalance(BigDecimal.ZERO);
        savingsAccount.setInterestRate(new BigDecimal("0.05"));
        
        customer.addAccount(savingsAccount);
        
        // Action: Trigger daily-interest calculation
        savingsAccount.calculateDailyInterest();
        
        // Expected Output: 0.00
        BigDecimal expectedBalance = BigDecimal.ZERO.setScale(2);
        assertEquals(expectedBalance, savingsAccount.getBalance());
    }
    
    @Test
    public void testCase3_VerySmallBalance() {
        // SetUp: Customer "Paul" (address "0815 Center St") holds a savings account "SAV009"
        // Savings account "SAV009" balance $0.01, interest rate 2 %
        Customer customer = new Customer();
        customer.setName("Paul");
        customer.setAddress("0815 Center St");
        
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountId("SAV009");
        savingsAccount.setBalance(new BigDecimal("0.01"));
        savingsAccount.setInterestRate(new BigDecimal("0.02"));
        
        customer.addAccount(savingsAccount);
        
        // Action: Trigger daily-interest calculation
        savingsAccount.calculateDailyInterest();
        
        // Expected Output: 0.00 (rounds down)
        BigDecimal expectedBalance = new BigDecimal("0.01");
        assertEquals(expectedBalance, savingsAccount.getBalance());
    }
    
    @Test
    public void testCase4_LargeBalance() {
        // SetUp: Customer "Peter" (address "0816 Center St") holds a savings account "SAV010"
        // Savings account "SAV010" balance $1 000 000, interest rate 2 %
        Customer customer = new Customer();
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountId("SAV010");
        savingsAccount.setBalance(new BigDecimal("1000000.00"));
        savingsAccount.setInterestRate(new BigDecimal("0.02"));
        
        customer.addAccount(savingsAccount);
        
        // Action: Trigger daily-interest calculation
        savingsAccount.calculateDailyInterest();
        
        // Expected Output: 55.56
        BigDecimal expectedBalance = new BigDecimal("1000055.56");
        assertEquals(expectedBalance, savingsAccount.getBalance());
    }
    
    @Test
    public void testCase5_HighPrecisionRate() {
        // SetUp: Customer "Beli" (address "0819 Center St") holds a savings account "SAV011"
        // Savings account "SAV011" balance $1 234.56, interest rate 2.75 %
        Customer customer = new Customer();
        customer.setName("Beli");
        customer.setAddress("0819 Center St");
        
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountId("SAV011");
        savingsAccount.setBalance(new BigDecimal("1234.56"));
        savingsAccount.setInterestRate(new BigDecimal("0.0275"));
        
        customer.addAccount(savingsAccount);
        
        // Action: Trigger daily-interest calculation
        savingsAccount.calculateDailyInterest();
        
        // Expected Output: 0.09
        BigDecimal expectedBalance = new BigDecimal("1234.65");
        assertEquals(expectedBalance, savingsAccount.getBalance());
    }
}