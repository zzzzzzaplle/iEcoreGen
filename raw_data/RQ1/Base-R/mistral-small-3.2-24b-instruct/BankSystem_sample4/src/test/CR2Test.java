import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.math.BigDecimal;

public class CR2Test {
    
    private BankSystem bankSystem;
    private Customer customer1;
    private Customer customer2;
    private SavingsAccount savingsAccount1;
    private SavingsAccount savingsAccount2;
    private SavingsAccount savingsAccount3;
    private InvestmentAccount investmentAccount1;
    private InvestmentAccount investmentAccount2;
    
    @Before
    public void setUp() {
        bankSystem = new BankSystem();
        
        // Setup for Customer "Doli" with savings account "SAV004"
        customer1 = new Customer();
        customer1.setName("Doli");
        customer1.setAddress("123 Main St");
        savingsAccount1 = new SavingsAccount();
        savingsAccount1.setAccountId("SAV004");
        savingsAccount1.setBalance(new BigDecimal("1000"));
        savingsAccount1.setInterestRate(new BigDecimal("0.0365"));
        customer1.addAccount(savingsAccount1);
        bankSystem.addCustomer(customer1);
        
        // Setup for Customer "Alie" with savings account "SAV005"
        customer2 = new Customer();
        customer2.setName("Alie");
        customer2.setAddress("0812 Center St");
        savingsAccount2 = new SavingsAccount();
        savingsAccount2.setAccountId("SAV005");
        savingsAccount2.setBalance(new BigDecimal("200"));
        savingsAccount2.setInterestRate(new BigDecimal("0.0365"));
        customer2.addAccount(savingsAccount2);
        
        // Setup for Customer "Alie" with savings account "SAV006"
        savingsAccount3 = new SavingsAccount();
        savingsAccount3.setAccountId("SAV006");
        savingsAccount3.setBalance(new BigDecimal("50"));
        savingsAccount3.setInterestRate(new BigDecimal("0.0365"));
        customer2.addAccount(savingsAccount3);
        
        // Setup for investment account "INV001"
        investmentAccount1 = new InvestmentAccount();
        investmentAccount1.setAccountId("INV001");
        investmentAccount1.setBalance(new BigDecimal("300"));
        customer2.addAccount(investmentAccount1);
        
        // Setup for investment account "INV002"
        investmentAccount2 = new InvestmentAccount();
        investmentAccount2.setAccountId("INV002");
        investmentAccount2.setBalance(BigDecimal.ZERO);
        customer2.addAccount(investmentAccount2);
        
        bankSystem.addCustomer(customer2);
    }
    
    @Test
    public void testCase1_DepositPositiveAmountToSavings() {
        // Test Case 1: Deposit positive amount to savings
        // Action: Deposit $500 into "SAV004"
        boolean result = savingsAccount1.deposit(new BigDecimal("500"));
        
        // Verify deposit was successful
        assertTrue("Deposit should succeed", result);
        
        // Verify new balance is 1500
        assertEquals("Balance should be 1500", 
                     new BigDecimal("1500"), 
                     savingsAccount1.getBalance());
    }
    
    @Test
    public void testCase2_DepositZeroAmount() {
        // Test Case 2: Deposit zero amount
        // Action: Attempt to deposit $0 into "SAV005"
        boolean result = savingsAccount2.deposit(BigDecimal.ZERO);
        
        // Verify deposit failed
        assertFalse("Deposit of zero should fail", result);
        
        // Verify balance remains unchanged at 200
        assertEquals("Balance should remain 200", 
                     new BigDecimal("200"), 
                     savingsAccount2.getBalance());
    }
    
    @Test
    public void testCase3_DepositNegativeAmount() {
        // Test Case 3: Deposit negative amount
        // Action: Attempt to deposit -$100 into "INV001"
        boolean result = investmentAccount1.deposit(new BigDecimal("-100"));
        
        // Verify deposit failed
        assertFalse("Deposit of negative amount should fail", result);
        
        // Verify balance remains unchanged at 300
        assertEquals("Balance should remain 300", 
                     new BigDecimal("300"), 
                     investmentAccount1.getBalance());
    }
    
    @Test
    public void testCase4_DepositExactlyAtSingleDepositLimit() {
        // Test Case 4: Deposit exactly at single-deposit limit
        // Action: Deposit $1,000,000 into "INV002"
        boolean result = investmentAccount2.deposit(new BigDecimal("1000000"));
        
        // Verify deposit was successful
        assertTrue("Deposit at limit should succeed", result);
        
        // Verify new balance is 1,000,000
        assertEquals("Balance should be 1000000", 
                     new BigDecimal("1000000"), 
                     investmentAccount2.getBalance());
    }
    
    @Test
    public void testCase5_DepositExceedsLimitByOneDollar() {
        // Test Case 5: Deposit exceeds limit by one dollar
        // Action: Attempt to deposit $1,000,001 into "SAV006"
        boolean result = savingsAccount3.deposit(new BigDecimal("1000001"));
        
        // Verify deposit failed
        assertFalse("Deposit exceeding limit should fail", result);
        
        // Verify balance remains unchanged at 50
        assertEquals("Balance should remain 50", 
                     new BigDecimal("50"), 
                     savingsAccount3.getBalance());
    }
}