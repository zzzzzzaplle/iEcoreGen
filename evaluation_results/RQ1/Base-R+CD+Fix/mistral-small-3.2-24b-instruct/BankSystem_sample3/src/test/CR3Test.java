import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    @Test
    public void testCase1_StandardBalanceAndRate() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds a savings account "SAV007"
        Customer customer = new Customer();
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        customer.addSavingAccount("SAV007", 3.65);
        
        // SetUp: "SAV007" balance $10 000, interest rate 3.65 %
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV007");
        account.deposit(10000.0);
        
        // Action: Trigger daily-interest calculation
        double result = account.calculateDailyInterest();
        
        // Expected Output: 1.01
        assertEquals(1.01, result, 0.001);
        assertEquals(10001.01, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_ZeroBalance() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a savings account "SAV008"
        Customer customer = new Customer();
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        customer.addSavingAccount("SAV008", 5.0);
        
        // SetUp: Savings account "SAV008" balance $0, interest rate 5 %
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV008");
        // Balance is already 0 by default
        
        // Action: Trigger daily-interest calculation
        double result = account.calculateDailyInterest();
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
        assertEquals(0.00, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_VerySmallBalance() {
        // SetUp: Customer "Paul" (address "0815 Center St") holds a savings account "SAV009"
        Customer customer = new Customer();
        customer.setName("Paul");
        customer.setAddress("0815 Center St");
        customer.addSavingAccount("SAV009", 2.0);
        
        // SetUp: Savings account "SAV009" balance $0.01, interest rate 2 %
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV009");
        account.deposit(0.01);
        
        // Action: Trigger daily-interest calculation
        double result = account.calculateDailyInterest();
        
        // Expected Output: 0.00 (rounds down)
        assertEquals(0.00, result, 0.001);
        assertEquals(0.01, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_LargeBalance() {
        // SetUp: Customer "Peter" (address "0816 Center St") holds a savings account "SAV010"
        Customer customer = new Customer();
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        customer.addSavingAccount("SAV010", 2.0);
        
        // SetUp: Savings account "SAV010" balance $1 000 000, interest rate 2 %
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV010");
        account.deposit(1000000.0);
        
        // Action: Trigger daily-interest calculation
        double result = account.calculateDailyInterest();
        
        // Expected Output: 55.56
        assertEquals(55.56, result, 0.001);
        assertEquals(1000055.56, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_HighPrecisionRate() {
        // SetUp: Customer "Beli" (address "0819 Center St") holds a savings account "SAV011"
        Customer customer = new Customer();
        customer.setName("Beli");
        customer.setAddress("0819 Center St");
        customer.addSavingAccount("SAV011", 2.75);
        
        // SetUp: Savings account "SAV011" balance $1 234.56, interest rate 2.75 %
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV011");
        account.deposit(1234.56);
        
        // Action: Trigger daily-interest calculation
        double result = account.calculateDailyInterest();
        
        // Expected Output: 0.09
        assertEquals(0.09, result, 0.001);
        assertEquals(1234.65, account.getBalance(), 0.001);
    }
}