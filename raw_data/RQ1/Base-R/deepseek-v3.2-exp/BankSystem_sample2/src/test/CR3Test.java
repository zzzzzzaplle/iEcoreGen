import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    @Test
    public void testCase1_StandardBalanceAndRate() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds a savings account "SAV007"
        // "SAV007" balance $10 000, interest rate 3.65 %
        Customer customer = new Customer();
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        customer.addAccount("SAV007");
        
        SavingsAccount account = (SavingsAccount) customer.getAccounts().get(0);
        account.setBalance(10000.0);
        account.setInterestRate(3.65);
        
        // Action: Trigger daily-interest calculation
        account.calculateDailyInterest();
        
        // Expected Output: 1.01 (interest added, new balance $10 001.01)
        assertEquals(10001.01, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_ZeroBalance() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a savings account "SAV008"
        // Savings account "SAV008" balance $0, interest rate 5 %
        Customer customer = new Customer();
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        customer.addAccount("SAV008");
        
        SavingsAccount account = (SavingsAccount) customer.getAccounts().get(0);
        account.setBalance(0.0);
        account.setInterestRate(5.0);
        
        // Action: Trigger daily-interest calculation
        account.calculateDailyInterest();
        
        // Expected Output: 0.00
        assertEquals(0.00, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_VerySmallBalance() {
        // SetUp: Customer "Paul" (address "0815 Center St") holds a savings account "SAV009"
        // Savings account "SAV009" balance $0.01, interest rate 2 %
        Customer customer = new Customer();
        customer.setName("Paul");
        customer.setAddress("0815 Center St");
        customer.addAccount("SAV009");
        
        SavingsAccount account = (SavingsAccount) customer.getAccounts().get(0);
        account.setBalance(0.01);
        account.setInterestRate(2.0);
        
        // Action: Trigger daily-interest calculation
        account.calculateDailyInterest();
        
        // Expected Output: 0.00 (rounds down)
        assertEquals(0.01, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_LargeBalance() {
        // SetUp: Customer "Peter" (address "0816 Center St") holds a savings account "SAV010"
        // Savings account "SAV010" balance $1 000 000, interest rate 2 %
        Customer customer = new Customer();
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        customer.addAccount("SAV010");
        
        SavingsAccount account = (SavingsAccount) customer.getAccounts().get(0);
        account.setBalance(1000000.0);
        account.setInterestRate(2.0);
        
        // Action: Trigger daily-interest calculation
        account.calculateDailyInterest();
        
        // Expected Output: 55.56
        assertEquals(1000055.56, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_HighPrecisionRate() {
        // SetUp: Customer "Beli" (address "0819 Center St") holds a savings account "SAV011"
        // Savings account "SAV011" balance $1 234.56, interest rate 2.75 %
        Customer customer = new Customer();
        customer.setName("Beli");
        customer.setAddress("0819 Center St");
        customer.addAccount("SAV011");
        
        SavingsAccount account = (SavingsAccount) customer.getAccounts().get(0);
        account.setBalance(1234.56);
        account.setInterestRate(2.75);
        
        // Action: Trigger daily-interest calculation
        account.calculateDailyInterest();
        
        // Expected Output: 0.09
        assertEquals(1234.65, account.getBalance(), 0.001);
    }
}