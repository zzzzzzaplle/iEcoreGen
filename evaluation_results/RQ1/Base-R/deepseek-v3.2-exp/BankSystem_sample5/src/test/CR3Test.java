import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    private BankSystem bankSystem;
    
    @Before
    public void setUp() {
        bankSystem = new BankSystem();
    }
    
    @Test
    public void testCase1_standardBalanceAndRate() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds a savings account "SAV007"
        // "SAV007" balance $10 000, interest rate 3.65 %
        bankSystem.addCustomer("Alie", "0812 Center St");
        Customer customer = bankSystem.findCustomer("Alie", "0812 Center St");
        customer.addAccount("SAV007", "SAVINGS");
        SavingsAccount savingsAccount = (SavingsAccount) customer.getAccountById("SAV007");
        savingsAccount.setBalance(10000.0);
        savingsAccount.setInterestRate(3.65);
        
        // Action: Trigger daily-interest calculation
        bankSystem.processDailyInterest();
        
        // Expected Output: 1.01 (interest added, new balance $10 001.01)
        assertEquals(10001.01, savingsAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_zeroBalance() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a savings account "SAV008"
        // Savings account "SAV008" balance $0, interest rate 5 %
        bankSystem.addCustomer("Poe", "0814 Center St");
        Customer customer = bankSystem.findCustomer("Poe", "0814 Center St");
        customer.addAccount("SAV008", "SAVINGS");
        SavingsAccount savingsAccount = (SavingsAccount) customer.getAccountById("SAV008");
        savingsAccount.setBalance(0.0);
        savingsAccount.setInterestRate(5.0);
        
        // Action: Trigger daily-interest calculation
        bankSystem.processDailyInterest();
        
        // Expected Output: 0.00
        assertEquals(0.00, savingsAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_verySmallBalance() {
        // SetUp: Customer "Paul" (address "0815 Center St") holds a savings account "SAV009"
        // Savings account "SAV009" balance $0.01, interest rate 2 %
        bankSystem.addCustomer("Paul", "0815 Center St");
        Customer customer = bankSystem.findCustomer("Paul", "0815 Center St");
        customer.addAccount("SAV009", "SAVINGS");
        SavingsAccount savingsAccount = (SavingsAccount) customer.getAccountById("SAV009");
        savingsAccount.setBalance(0.01);
        savingsAccount.setInterestRate(2.0);
        
        // Action: Trigger daily-interest calculation
        bankSystem.processDailyInterest();
        
        // Expected Output: 0.00 (rounds down)
        assertEquals(0.01, savingsAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_largeBalance() {
        // SetUp: Customer "Peter" (address "0816 Center St") holds a savings account "SAV010"
        // Savings account "SAV010" balance $1 000 000, interest rate 2 %
        bankSystem.addCustomer("Peter", "0816 Center St");
        Customer customer = bankSystem.findCustomer("Peter", "0816 Center St");
        customer.addAccount("SAV010", "SAVINGS");
        SavingsAccount savingsAccount = (SavingsAccount) customer.getAccountById("SAV010");
        savingsAccount.setBalance(1000000.0);
        savingsAccount.setInterestRate(2.0);
        
        // Action: Trigger daily-interest calculation
        bankSystem.processDailyInterest();
        
        // Expected Output: 55.56
        assertEquals(1000055.56, savingsAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_highPrecisionRate() {
        // SetUp: Customer "Beli" (address "0819 Center St") holds a savings account "SAV011"
        // Savings account "SAV011" balance $1 234.56, interest rate 2.75 %
        bankSystem.addCustomer("Beli", "0819 Center St");
        Customer customer = bankSystem.findCustomer("Beli", "0819 Center St");
        customer.addAccount("SAV011", "SAVINGS");
        SavingsAccount savingsAccount = (SavingsAccount) customer.getAccountById("SAV011");
        savingsAccount.setBalance(1234.56);
        savingsAccount.setInterestRate(2.75);
        
        // Action: Trigger daily-interest calculation
        bankSystem.processDailyInterest();
        
        // Expected Output: 0.09
        assertEquals(1234.65, savingsAccount.getBalance(), 0.001);
    }
}