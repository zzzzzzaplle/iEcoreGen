import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    @Test
    public void testCase1_DepositPositiveAmountToSavings() {
        // SetUp: Customer "Doli" (address "123 Main St") holds savings account "SAV004", balance $1 000, interest rate 3.65 %
        Customer customer = new Customer();
        customer.setName("Doli");
        customer.setAddress("123 Main St");
        
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountId("SAV004");
        savingsAccount.setBalance(1000.0);
        savingsAccount.setInterestRate(3.65);
        
        customer.addAccount(savingsAccount);
        
        // Action: Deposit $500 into "SAV004"
        boolean result = savingsAccount.deposit(500.0);
        
        // Expected Output: 1 500 (new balance)
        assertTrue("Deposit should succeed", result);
        assertEquals(1500.0, savingsAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_DepositZeroAmount() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds savings account "SAV005", balance $200, interest rate 3.65 %
        Customer customer = new Customer();
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountId("SAV005");
        savingsAccount.setBalance(200.0);
        savingsAccount.setInterestRate(3.65);
        
        customer.addAccount(savingsAccount);
        
        // Action: Attempt to deposit $0
        boolean result = savingsAccount.deposit(0.0);
        
        // Expected Output: False, the balance of 'SAV005' is still $200
        assertFalse("Zero deposit should fail", result);
        assertEquals(200.0, savingsAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_DepositNegativeAmount() {
        // SetUp: Investment account "INV001" holds balance $300
        InvestmentAccount investmentAccount = new InvestmentAccount();
        investmentAccount.setAccountId("INV001");
        investmentAccount.setBalance(300.0);
        
        // Action: Attempt to deposit –$100
        boolean result = investmentAccount.deposit(-100.0);
        
        // Expected Output: False
        assertFalse("Negative deposit should fail", result);
        assertEquals(300.0, investmentAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_DepositExactlyAtSingleDepositLimit() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds an investment account "INV002" holds balance $0
        Customer customer = new Customer();
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        
        InvestmentAccount investmentAccount = new InvestmentAccount();
        investmentAccount.setAccountId("INV002");
        investmentAccount.setBalance(0.0);
        
        customer.addAccount(investmentAccount);
        
        // Action: Deposit $1 000 000
        boolean result = investmentAccount.deposit(1000000.0);
        
        // Expected Output: 1 000 000
        assertTrue("Deposit at limit should succeed", result);
        assertEquals(1000000.0, investmentAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_DepositExceedsLimitByOneDollar() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds a savings account "SAV006" holds balance $50, interest rate 3.65 %
        Customer customer = new Customer();
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountId("SAV006");
        savingsAccount.setBalance(50.0);
        savingsAccount.setInterestRate(3.65);
        
        customer.addAccount(savingsAccount);
        
        // Action: Attempt to deposit $1 000 001
        boolean result = savingsAccount.deposit(1000001.0);
        
        // Expected Output: False
        assertFalse("Deposit exceeding limit should fail", result);
        assertEquals(50.0, savingsAccount.getBalance(), 0.001);
    }
}