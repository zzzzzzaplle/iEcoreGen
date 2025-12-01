import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Customer customer;
    private SavingAccount savingAccount;
    
    @Before
    public void setUp() {
        customer = new Customer();
        customer.setAccounts(new ArrayList<>());
    }
    
    @Test
    public void testCase1_standardBalanceAndRate() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds a savings account "SAV007"
        // "SAV007" balance $10 000, interest rate 3.65 %.
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        
        assertTrue(customer.addSavingAccount("SAV007", 0.0365));
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV007");
        account.setBalance(10000.00);
        
        // Action: Trigger daily-interest calculation.
        double interest = account.calculateDailyInterest();
        
        // Expected Output: 1.01
        assertEquals(1.01, interest, 0.001);
        assertEquals(10001.01, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_zeroBalance() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a savings account "SAV008"
        // Savings account "SAV008" balance $0, interest rate 5 %.
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        
        assertTrue(customer.addSavingAccount("SAV008", 0.05));
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV008");
        account.setBalance(0.00);
        
        // Action: Trigger daily-interest calculation.
        double interest = account.calculateDailyInterest();
        
        // Expected Output: 0.00
        assertEquals(0.00, interest, 0.001);
        assertEquals(0.00, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_verySmallBalance() {
        // SetUp: Customer "Paul" (address "0815 Center St") holds a savings account "SAV009"
        // Savings account "SAV009" balance $0.01, interest rate 2 %.
        customer.setName("Paul");
        customer.setAddress("0815 Center St");
        
        assertTrue(customer.addSavingAccount("SAV009", 0.02));
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV009");
        account.setBalance(0.01);
        
        // Action: Trigger daily-interest calculation.
        double interest = account.calculateDailyInterest();
        
        // Expected Output: 0.00
        assertEquals(0.00, interest, 0.001);
        assertEquals(0.01, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_largeBalance() {
        // SetUp: Customer "Peter" (address "0816 Center St") holds a savings account "SAV010"
        // Savings account "SAV010" balance $1 000 000, interest rate 2 %.
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        
        assertTrue(customer.addSavingAccount("SAV010", 0.02));
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV010");
        account.setBalance(1000000.00);
        
        // Action: Trigger daily-interest calculation.
        double interest = account.calculateDailyInterest();
        
        // Expected Output: 55.56
        assertEquals(55.56, interest, 0.001);
        assertEquals(1000055.56, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_highPrecisionRate() {
        // SetUp: Customer "Beli" (address "0819 Center St") holds a savings account "SAV011"
        // Savings account "SAV011" balance $1 234.56, interest rate 2.75 %.
        customer.setName("Beli");
        customer.setAddress("0819 Center St");
        
        assertTrue(customer.addSavingAccount("SAV011", 0.0275));
        SavingAccount account = (SavingAccount) customer.findAccountById("SAV011");
        account.setBalance(1234.56);
        
        // Action: Trigger daily-interest calculation.
        double interest = account.calculateDailyInterest();
        
        // Expected Output: 0.09
        assertEquals(0.09, interest, 0.001);
        assertEquals(1234.65, account.getBalance(), 0.001);
    }
}