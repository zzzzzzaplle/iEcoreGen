import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    private Customer customer;
    private SavingAccount savingAccount;
    
    @Before
    public void setUp() {
        customer = new Customer();
    }
    
    @Test
    public void testCase1_standardBalanceAndRate() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds a savings account "SAV007"
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        
        // "SAV007" balance $10 000, interest rate 3.65 %
        customer.addSavingAccount("SAV007", 0.0365);
        savingAccount = (SavingAccount) customer.findAccountById("SAV007");
        savingAccount.setBalance(10000.0);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = savingAccount.calculateDailyInterest();
        
        // Expected Output: 1.01 - interest added, new balance $10 001.01
        assertEquals(1.01, dailyInterest, 0.001);
    }
    
    @Test
    public void testCase2_zeroBalance() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a savings account "SAV008"
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        
        // Savings account "SAV008" balance $0, interest rate 5 %
        customer.addSavingAccount("SAV008", 0.05);
        savingAccount = (SavingAccount) customer.findAccountById("SAV008");
        savingAccount.setBalance(0.0);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = savingAccount.calculateDailyInterest();
        
        // Expected Output: 0.00
        assertEquals(0.00, dailyInterest, 0.001);
    }
    
    @Test
    public void testCase3_verySmallBalance() {
        // SetUp: Customer "Paul" (address "0815 Center St") holds a savings account "SAV009"
        customer.setName("Paul");
        customer.setAddress("0815 Center St");
        
        // Savings account "SAV009" balance $0.01, interest rate 2 %
        customer.addSavingAccount("SAV009", 0.02);
        savingAccount = (SavingAccount) customer.findAccountById("SAV009");
        savingAccount.setBalance(0.01);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = savingAccount.calculateDailyInterest();
        
        // Expected Output: 0.00 - rounds down
        assertEquals(0.00, dailyInterest, 0.001);
    }
    
    @Test
    public void testCase4_largeBalance() {
        // SetUp: Customer "Peter" (address "0816 Center St") holds a savings account "SAV010"
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        
        // Savings account "SAV010" balance $1 000 000, interest rate 2 %
        customer.addSavingAccount("SAV010", 0.02);
        savingAccount = (SavingAccount) customer.findAccountById("SAV010");
        savingAccount.setBalance(1000000.0);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = savingAccount.calculateDailyInterest();
        
        // Expected Output: 55.56
        assertEquals(55.56, dailyInterest, 0.001);
    }
    
    @Test
    public void testCase5_highPrecisionRate() {
        // SetUp: Customer "Beli" (address "0819 Center St") holds a savings account "SAV011"
        customer.setName("Beli");
        customer.setAddress("0819 Center St");
        
        // Savings account "SAV011" balance $1 234.56, interest rate 2.75 %
        customer.addSavingAccount("SAV011", 0.0275);
        savingAccount = (SavingAccount) customer.findAccountById("SAV011");
        savingAccount.setBalance(1234.56);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = savingAccount.calculateDailyInterest();
        
        // Expected Output: 0.09
        assertEquals(0.09, dailyInterest, 0.001);
    }
}