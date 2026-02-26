import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.math.BigDecimal;

public class CR2Test {
    
    private Customer customer;
    
    @Before
    public void setUp() {
        customer = new Customer();
    }
    
    @Test
    public void testCase1_DepositPositiveAmountToSavings() {
        // SetUp: Customer "Doli" (address "123 Main St") holds savings account "SAV004", balance $1000, interest rate 3.65%
        Customer doli = new Customer("Doli", "123 Main St");
        SavingsAccount savAccount = new SavingsAccount("SAV004", new BigDecimal("0.0365"));
        savAccount.setBalance(new BigDecimal("1000"));
        doli.addAccount(savAccount);
        
        // Action: Deposit $500 into "SAV004"
        boolean result = doli.deposit("SAV004", new BigDecimal("500"));
        
        // Expected Output: 1500 (new balance)
        assertTrue("Deposit should succeed", result);
        assertEquals("Balance should be 1500", 
                     new BigDecimal("1500"), 
                     savAccount.getBalance());
    }
    
    @Test
    public void testCase2_DepositZeroAmount() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds savings account "SAV005", balance $200, interest rate 3.65%
        Customer alie = new Customer("Alie", "0812 Center St");
        SavingsAccount savAccount = new SavingsAccount("SAV005", new BigDecimal("0.0365"));
        savAccount.setBalance(new BigDecimal("200"));
        alie.addAccount(savAccount);
        
        // Action: Attempt to deposit $0
        boolean result = alie.deposit("SAV005", new BigDecimal("0"));
        
        // Expected Output: False, the balance of 'SAV005' is still $200
        assertFalse("Deposit of zero should fail", result);
        assertEquals("Balance should remain 200", 
                     new BigDecimal("200"), 
                     savAccount.getBalance());
    }
    
    @Test
    public void testCase3_DepositNegativeAmount() {
        // SetUp: Investment account "INV001" holds balance $300
        Customer customer = new Customer();
        InvestmentAccount invAccount = new InvestmentAccount("INV001");
        invAccount.setBalance(new BigDecimal("300"));
        customer.addAccount(invAccount);
        
        // Action: Attempt to deposit -$100
        boolean result = customer.deposit("INV001", new BigDecimal("-100"));
        
        // Expected Output: False
        assertFalse("Deposit of negative amount should fail", result);
    }
    
    @Test
    public void testCase4_DepositExactlyAtSingleDepositLimit() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds an investment account "INV002" holds balance $0
        Customer alie = new Customer("Alie", "0812 Center St");
        InvestmentAccount invAccount = new InvestmentAccount("INV002");
        invAccount.setBalance(BigDecimal.ZERO);
        alie.addAccount(invAccount);
        
        // Action: Deposit $1,000,000
        boolean result = alie.deposit("INV002", new BigDecimal("1000000"));
        
        // Expected Output: 1,000,000
        assertTrue("Deposit at limit should succeed", result);
        assertEquals("Balance should be 1000000", 
                     new BigDecimal("1000000"), 
                     invAccount.getBalance());
    }
    
    @Test
    public void testCase5_DepositExceedsLimitByOneDollar() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds a savings account "SAV006" holds balance $50, interest rate 3.65%
        Customer alie = new Customer("Alie", "0812 Center St");
        SavingsAccount savAccount = new SavingsAccount("SAV006", new BigDecimal("0.0365"));
        savAccount.setBalance(new BigDecimal("50"));
        alie.addAccount(savAccount);
        
        // Action: Attempt to deposit $1,000,001
        boolean result = alie.deposit("SAV006", new BigDecimal("1000001"));
        
        // Expected Output: False
        assertFalse("Deposit exceeding limit should fail", result);
    }
}