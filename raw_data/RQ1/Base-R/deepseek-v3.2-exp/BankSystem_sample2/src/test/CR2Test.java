import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    private Customer customer;
    
    @Before
    public void setUp() {
        customer = new Customer();
    }
    
    @Test
    public void testCase1_DepositPositiveAmountToSavings() {
        // SetUp: Customer "Doli" (address "123 Main St") holds savings account "SAV004", balance $1000, interest rate 3.65%
        customer.setName("Doli");
        customer.setAddress("123 Main St");
        customer.addAccount("SAV004");
        
        // Set initial balance and interest rate for the savings account
        customer.getAccounts().get(0).setBalance(1000.0);
        if (customer.getAccounts().get(0) instanceof SavingsAccount) {
            SavingsAccount savingsAccount = (SavingsAccount) customer.getAccounts().get(0);
            savingsAccount.setInterestRate(3.65);
        }
        
        // Action: Deposit $500 into "SAV004"
        boolean result = customer.deposit("SAV004", 500.0);
        
        // Expected Output: 1500 (new balance)
        assertTrue("Deposit should be successful", result);
        assertEquals(1500.0, customer.getAccounts().get(0).getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_DepositZeroAmount() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds savings account "SAV005", balance $200, interest rate 3.65%
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        customer.addAccount("SAV005");
        
        // Set initial balance and interest rate for the savings account
        customer.getAccounts().get(0).setBalance(200.0);
        if (customer.getAccounts().get(0) instanceof SavingsAccount) {
            SavingsAccount savingsAccount = (SavingsAccount) customer.getAccounts().get(0);
            savingsAccount.setInterestRate(3.65);
        }
        
        double initialBalance = customer.getAccounts().get(0).getBalance();
        
        // Action: Attempt to deposit $0
        boolean result = customer.deposit("SAV005", 0.0);
        
        // Expected Output: False, the balance of 'SAV005' is still $200
        assertFalse("Deposit of zero amount should fail", result);
        assertEquals(200.0, customer.getAccounts().get(0).getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_DepositNegativeAmount() {
        // SetUp: Investment account "INV001" holds balance $300
        InvestmentAccount investmentAccount = new InvestmentAccount();
        investmentAccount.setId("INV001");
        investmentAccount.setBalance(300.0);
        
        // Add the investment account to the customer
        customer.setAccounts(new java.util.ArrayList<Account>());
        customer.getAccounts().add(investmentAccount);
        
        // Action: Attempt to deposit -$100
        boolean result = customer.deposit("INV001", -100.0);
        
        // Expected Output: False
        assertFalse("Deposit of negative amount should fail", result);
        assertEquals(300.0, customer.getAccounts().get(0).getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_DepositExactlyAtSingleDepositLimit() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds an investment account "INV002" holds balance $0
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        
        InvestmentAccount investmentAccount = new InvestmentAccount();
        investmentAccount.setId("INV002");
        investmentAccount.setBalance(0.0);
        
        customer.setAccounts(new java.util.ArrayList<Account>());
        customer.getAccounts().add(investmentAccount);
        
        // Action: Deposit $1,000,000
        boolean result = customer.deposit("INV002", 1000000.0);
        
        // Expected Output: 1,000,000
        assertTrue("Deposit at limit should be successful", result);
        assertEquals(1000000.0, customer.getAccounts().get(0).getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_DepositExceedsLimitByOneDollar() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds a savings account "SAV006" holds balance $50, interest rate 3.65%
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        customer.addAccount("SAV006");
        
        // Set initial balance and interest rate for the savings account
        customer.getAccounts().get(0).setBalance(50.0);
        if (customer.getAccounts().get(0) instanceof SavingsAccount) {
            SavingsAccount savingsAccount = (SavingsAccount) customer.getAccounts().get(0);
            savingsAccount.setInterestRate(3.65);
        }
        
        double initialBalance = customer.getAccounts().get(0).getBalance();
        
        // Action: Attempt to deposit $1,000,001
        boolean result = customer.deposit("SAV006", 1000001.0);
        
        // Expected Output: False
        assertFalse("Deposit exceeding limit should fail", result);
        assertEquals(50.0, customer.getAccounts().get(0).getBalance(), 0.001);
    }
}