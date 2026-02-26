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
        
        // SetUp: "SAV007" balance $10 000, interest rate 3.65 %
        savingAccount = new SavingAccount();
        savingAccount.setId("SAV007");
        savingAccount.setBalance(10000.0);
        savingAccount.setInterestRate(3.65);
        
        customer.getAccounts().add(savingAccount);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = savingAccount.calculateDailyInterest();
        
        // Expected Output: 1.01
        assertEquals(1.01, dailyInterest, 0.001);
        
        // Verify balance update
        boolean updateResult = savingAccount.updateDailyInterest();
        assertTrue(updateResult);
        assertEquals(10001.01, savingAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_zeroBalance() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a savings account "SAV008"
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        
        // SetUp: Savings account "SAV008" balance $0, interest rate 5 %
        savingAccount = new SavingAccount();
        savingAccount.setId("SAV008");
        savingAccount.setBalance(0.0);
        savingAccount.setInterestRate(5.0);
        
        customer.getAccounts().add(savingAccount);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = savingAccount.calculateDailyInterest();
        
        // Expected Output: 0.00
        assertEquals(0.00, dailyInterest, 0.001);
        
        // Verify balance remains zero after update
        boolean updateResult = savingAccount.updateDailyInterest();
        assertTrue(updateResult);
        assertEquals(0.00, savingAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_verySmallBalance() {
        // SetUp: Customer "Paul" (address "0815 Center St") holds a savings account "SAV009"
        customer.setName("Paul");
        customer.setAddress("0815 Center St");
        
        // SetUp: Savings account "SAV009" balance $0.01, interest rate 2 %
        savingAccount = new SavingAccount();
        savingAccount.setId("SAV009");
        savingAccount.setBalance(0.01);
        savingAccount.setInterestRate(2.0);
        
        customer.getAccounts().add(savingAccount);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = savingAccount.calculateDailyInterest();
        
        // Expected Output: 0.00 (rounds down)
        assertEquals(0.00, dailyInterest, 0.001);
        
        // Verify balance remains the same after update
        boolean updateResult = savingAccount.updateDailyInterest();
        assertTrue(updateResult);
        assertEquals(0.01, savingAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_largeBalance() {
        // SetUp: Customer "Peter" (address "0816 Center St") holds a savings account "SAV010"
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        
        // SetUp: Savings account "SAV010" balance $1 000 000, interest rate 2 %
        savingAccount = new SavingAccount();
        savingAccount.setId("SAV010");
        savingAccount.setBalance(1000000.0);
        savingAccount.setInterestRate(2.0);
        
        customer.getAccounts().add(savingAccount);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = savingAccount.calculateDailyInterest();
        
        // Expected Output: 55.56
        assertEquals(55.56, dailyInterest, 0.001);
        
        // Verify balance update
        boolean updateResult = savingAccount.updateDailyInterest();
        assertTrue(updateResult);
        assertEquals(1000055.56, savingAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_highPrecisionRate() {
        // SetUp: Customer "Beli" (address "0819 Center St") holds a savings account "SAV011"
        customer.setName("Beli");
        customer.setAddress("0819 Center St");
        
        // SetUp: Savings account "SAV011" balance $1 234.56, interest rate 2.75 %
        savingAccount = new SavingAccount();
        savingAccount.setId("SAV011");
        savingAccount.setBalance(1234.56);
        savingAccount.setInterestRate(2.75);
        
        customer.getAccounts().add(savingAccount);
        
        // Action: Trigger daily-interest calculation
        double dailyInterest = savingAccount.calculateDailyInterest();
        
        // Expected Output: 0.09
        assertEquals(0.09, dailyInterest, 0.001);
        
        // Verify balance update
        boolean updateResult = savingAccount.updateDailyInterest();
        assertTrue(updateResult);
        assertEquals(1234.65, savingAccount.getBalance(), 0.001);
    }
}