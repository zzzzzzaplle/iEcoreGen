import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    private Customer customer;
    private SavingAccount savingAccount;
    
    @Before
    public void setUp() {
        customer = new Customer();
        savingAccount = new SavingAccount();
        customer.getAccounts().add(savingAccount);
    }
    
    @Test
    public void testCase1_standardBalanceAndRate() {
        // Setup: Customer "Alie" with savings account "SAV007"
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        savingAccount.setId("SAV007");
        savingAccount.setBalance(10000.0);
        savingAccount.setInterestRate(0.0365); // 3.65%
        
        // Action: Trigger daily-interest calculation
        double interestAdded = savingAccount.calculateDailyInterest();
        
        // Expected Output: 1.01
        assertEquals(1.01, interestAdded, 0.001);
        assertEquals(10001.01, savingAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_zeroBalance() {
        // Setup: Customer "Poe" with savings account "SAV008"
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        savingAccount.setId("SAV008");
        savingAccount.setBalance(0.0);
        savingAccount.setInterestRate(0.05); // 5%
        
        // Action: Trigger daily-interest calculation
        double interestAdded = savingAccount.calculateDailyInterest();
        
        // Expected Output: 0.00
        assertEquals(0.00, interestAdded, 0.001);
        assertEquals(0.0, savingAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_verySmallBalance() {
        // Setup: Customer "Paul" with savings account "SAV009"
        customer.setName("Paul");
        customer.setAddress("0815 Center St");
        savingAccount.setId("SAV009");
        savingAccount.setBalance(0.01);
        savingAccount.setInterestRate(0.02); // 2%
        
        // Action: Trigger daily-interest calculation
        double interestAdded = savingAccount.calculateDailyInterest();
        
        // Expected Output: 0.00 (rounds down)
        assertEquals(0.00, interestAdded, 0.001);
        assertEquals(0.01, savingAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_largeBalance() {
        // Setup: Customer "Peter" with savings account "SAV010"
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        savingAccount.setId("SAV010");
        savingAccount.setBalance(1000000.0);
        savingAccount.setInterestRate(0.02); // 2%
        
        // Action: Trigger daily-interest calculation
        double interestAdded = savingAccount.calculateDailyInterest();
        
        // Expected Output: 55.56
        assertEquals(55.56, interestAdded, 0.001);
        assertEquals(1000055.56, savingAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_highPrecisionRate() {
        // Setup: Customer "Beli" with savings account "SAV011"
        customer.setName("Beli");
        customer.setAddress("0819 Center St");
        savingAccount.setId("SAV011");
        savingAccount.setBalance(1234.56);
        savingAccount.setInterestRate(0.0275); // 2.75%
        
        // Action: Trigger daily-interest calculation
        double interestAdded = savingAccount.calculateDailyInterest();
        
        // Expected Output: 0.09
        assertEquals(0.09, interestAdded, 0.001);
        assertEquals(1234.65, savingAccount.getBalance(), 0.001);
    }
}