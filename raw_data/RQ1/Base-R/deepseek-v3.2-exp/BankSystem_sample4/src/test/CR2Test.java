import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.math.BigDecimal;

public class CR2Test {
    
    private Customer customer;
    private SavingsAccount savingsAccount;
    private InvestmentAccount investmentAccount;
    
    @Before
    public void setUp() {
        customer = new Customer();
    }
    
    @Test
    public void testCase1_DepositPositiveAmountToSavings() {
        // SetUp: Customer "Doli" (address "123 Main St") holds savings account "SAV004", balance $1000, interest rate 3.65%
        customer.setName("Doli");
        customer.setAddress("123 Main St");
        savingsAccount = new SavingsAccount();
        savingsAccount.setAccountId("SAV004");
        savingsAccount.setBalance(new BigDecimal("1000"));
        savingsAccount.setInterestRate(new BigDecimal("0.0365"));
        customer.addAccount(savingsAccount);
        
        // Action: Deposit $500 into "SAV004"
        boolean result = savingsAccount.deposit(new BigDecimal("500"));
        
        // Expected Output: 1500 (new balance)
        assertTrue("Deposit should succeed", result);
        assertEquals("Balance should be 1500", new BigDecimal("1500"), savingsAccount.getBalance());
    }
    
    @Test
    public void testCase2_DepositZeroAmount() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds savings account "SAV005", balance $200, interest rate 3.65%
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        savingsAccount = new SavingsAccount();
        savingsAccount.setAccountId("SAV005");
        savingsAccount.setBalance(new BigDecimal("200"));
        savingsAccount.setInterestRate(new BigDecimal("0.0365"));
        customer.addAccount(savingsAccount);
        
        // Action: Attempt to deposit $0
        boolean result = savingsAccount.deposit(new BigDecimal("0"));
        
        // Expected Output: False, the balance of 'SAV005' is still $200
        assertFalse("Deposit of zero should fail", result);
        assertEquals("Balance should remain 200", new BigDecimal("200"), savingsAccount.getBalance());
    }
    
    @Test
    public void testCase3_DepositNegativeAmount() {
        // SetUp: Investment account "INV001" holds balance $300
        investmentAccount = new InvestmentAccount();
        investmentAccount.setAccountId("INV001");
        investmentAccount.setBalance(new BigDecimal("300"));
        
        // Action: Attempt to deposit -$100
        boolean result = investmentAccount.deposit(new BigDecimal("-100"));
        
        // Expected Output: False
        assertFalse("Deposit of negative amount should fail", result);
        assertEquals("Balance should remain 300", new BigDecimal("300"), investmentAccount.getBalance());
    }
    
    @Test
    public void testCase4_DepositExactlyAtSingleDepositLimit() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds an investment account "INV002" holds balance $0
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        investmentAccount = new InvestmentAccount();
        investmentAccount.setAccountId("INV002");
        investmentAccount.setBalance(BigDecimal.ZERO);
        customer.addAccount(investmentAccount);
        
        // Action: Deposit $1,000,000
        boolean result = investmentAccount.deposit(new BigDecimal("1000000"));
        
        // Expected Output: 1,000,000
        assertTrue("Deposit at limit should succeed", result);
        assertEquals("Balance should be 1000000", new BigDecimal("1000000"), investmentAccount.getBalance());
    }
    
    @Test
    public void testCase5_DepositExceedsLimitByOneDollar() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds a savings account "SAV006" holds balance $50, interest rate 3.65%
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        savingsAccount = new SavingsAccount();
        savingsAccount.setAccountId("SAV006");
        savingsAccount.setBalance(new BigDecimal("50"));
        savingsAccount.setInterestRate(new BigDecimal("0.0365"));
        customer.addAccount(savingsAccount);
        
        // Action: Attempt to deposit $1,000,001
        boolean result = savingsAccount.deposit(new BigDecimal("1000001"));
        
        // Expected Output: False
        assertFalse("Deposit exceeding limit should fail", result);
        assertEquals("Balance should remain 50", new BigDecimal("50"), savingsAccount.getBalance());
    }
}