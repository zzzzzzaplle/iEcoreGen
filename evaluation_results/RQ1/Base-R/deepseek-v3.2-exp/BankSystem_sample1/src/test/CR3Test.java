import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    @Test
    public void testCase1_standardBalanceAndRate() {
        // SetUp: Customer "Alie" with savings account "SAV007" - balance $10,000, interest rate 3.65%
        Customer customer = new Customer();
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountId("SAV007");
        savingsAccount.setBalance(10000.0);
        savingsAccount.setInterestRate(0.0365); // 3.65%
        
        customer.addAccount(savingsAccount);
        
        // Action: Trigger daily-interest calculation
        savingsAccount.calculateDailyInterest();
        
        // Expected Output: 1.01 (interest added, new balance $10,001.01)
        assertEquals(10001.01, savingsAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_zeroBalance() {
        // SetUp: Customer "Poe" with savings account "SAV008" - balance $0, interest rate 5%
        Customer customer = new Customer();
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountId("SAV008");
        savingsAccount.setBalance(0.0);
        savingsAccount.setInterestRate(0.05); // 5%
        
        customer.addAccount(savingsAccount);
        
        // Action: Trigger daily-interest calculation
        savingsAccount.calculateDailyInterest();
        
        // Expected Output: 0.00
        assertEquals(0.00, savingsAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_verySmallBalance() {
        // SetUp: Customer "Paul" with savings account "SAV009" - balance $0.01, interest rate 2%
        Customer customer = new Customer();
        customer.setName("Paul");
        customer.setAddress("0815 Center St");
        
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountId("SAV009");
        savingsAccount.setBalance(0.01);
        savingsAccount.setInterestRate(0.02); // 2%
        
        customer.addAccount(savingsAccount);
        
        // Action: Trigger daily-interest calculation
        savingsAccount.calculateDailyInterest();
        
        // Expected Output: 0.00 (rounds down)
        assertEquals(0.01, savingsAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_largeBalance() {
        // SetUp: Customer "Peter" with savings account "SAV010" - balance $1,000,000, interest rate 2%
        Customer customer = new Customer();
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountId("SAV010");
        savingsAccount.setBalance(1000000.0);
        savingsAccount.setInterestRate(0.02); // 2%
        
        customer.addAccount(savingsAccount);
        
        // Action: Trigger daily-interest calculation
        savingsAccount.calculateDailyInterest();
        
        // Expected Output: 55.56
        assertEquals(1000055.56, savingsAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_highPrecisionRate() {
        // SetUp: Customer "Beli" with savings account "SAV011" - balance $1,234.56, interest rate 2.75%
        Customer customer = new Customer();
        customer.setName("Beli");
        customer.setAddress("0819 Center St");
        
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountId("SAV011");
        savingsAccount.setBalance(1234.56);
        savingsAccount.setInterestRate(0.0275); // 2.75%
        
        customer.addAccount(savingsAccount);
        
        // Action: Trigger daily-interest calculation
        savingsAccount.calculateDailyInterest();
        
        // Expected Output: 0.09
        assertEquals(1234.65, savingsAccount.getBalance(), 0.001);
    }
}