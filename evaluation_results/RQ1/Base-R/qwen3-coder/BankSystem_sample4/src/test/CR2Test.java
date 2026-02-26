import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.math.BigDecimal;

public class CR2Test {
    private Customer customerDoli;
    private Customer customerAlie;
    private SavingsAccount savingsAccountSAV004;
    private SavingsAccount savingsAccountSAV005;
    private SavingsAccount savingsAccountSAV006;
    private InvestmentAccount investmentAccountINV001;
    private InvestmentAccount investmentAccountINV002;

    @Before
    public void setUp() {
        // Set up Customer Doli with savings account SAV004
        customerDoli = new Customer();
        customerDoli.setName("Doli");
        customerDoli.setAddress("123 Main St");
        savingsAccountSAV004 = new SavingsAccount();
        savingsAccountSAV004.setId("SAV004");
        savingsAccountSAV004.setBalance(new BigDecimal("1000"));
        savingsAccountSAV004.setInterestRate(new BigDecimal("0.0365"));
        customerDoli.addAccount(savingsAccountSAV004);

        // Set up Customer Alie with savings account SAV005
        customerAlie = new Customer();
        customerAlie.setName("Alie");
        customerAlie.setAddress("0812 Center St");
        savingsAccountSAV005 = new SavingsAccount();
        savingsAccountSAV005.setId("SAV005");
        savingsAccountSAV005.setBalance(new BigDecimal("200"));
        savingsAccountSAV005.setInterestRate(new BigDecimal("0.0365"));
        customerAlie.addAccount(savingsAccountSAV005);

        // Set up Customer Alie with savings account SAV006
        savingsAccountSAV006 = new SavingsAccount();
        savingsAccountSAV006.setId("SAV006");
        savingsAccountSAV006.setBalance(new BigDecimal("50"));
        savingsAccountSAV006.setInterestRate(new BigDecimal("0.0365"));
        customerAlie.addAccount(savingsAccountSAV006);

        // Set up investment account INV001
        investmentAccountINV001 = new InvestmentAccount();
        investmentAccountINV001.setId("INV001");
        investmentAccountINV001.setBalance(new BigDecimal("300"));

        // Set up Customer Alie with investment account INV002
        investmentAccountINV002 = new InvestmentAccount();
        investmentAccountINV002.setId("INV002");
        investmentAccountINV002.setBalance(BigDecimal.ZERO);
        customerAlie.addAccount(investmentAccountINV002);
    }

    @Test
    public void testCase1_DepositPositiveAmountToSavings() {
        // Test Case 1: Deposit positive amount to savings
        // Action: Deposit $500 into "SAV004"
        boolean depositResult = savingsAccountSAV004.deposit(new BigDecimal("500"));
        
        // Verify deposit was successful
        assertTrue("Deposit should succeed", depositResult);
        
        // Verify new balance is 1500
        BigDecimal expectedBalance = new BigDecimal("1500");
        assertEquals("Balance should be 1500 after deposit", 
                     expectedBalance, savingsAccountSAV004.getBalance());
    }

    @Test
    public void testCase2_DepositZeroAmount() {
        // Test Case 2: Deposit zero amount
        // Action: Attempt to deposit $0 into "SAV005"
        boolean depositResult = savingsAccountSAV005.deposit(BigDecimal.ZERO);
        
        // Verify deposit failed
        assertFalse("Deposit of zero amount should fail", depositResult);
        
        // Verify balance remains unchanged at $200
        BigDecimal expectedBalance = new BigDecimal("200");
        assertEquals("Balance should remain 200 after failed deposit", 
                     expectedBalance, savingsAccountSAV005.getBalance());
    }

    @Test
    public void testCase3_DepositNegativeAmount() {
        // Test Case 3: Deposit negative amount
        // Action: Attempt to deposit -$100 into "INV001"
        boolean depositResult = investmentAccountINV001.deposit(new BigDecimal("-100"));
        
        // Verify deposit failed
        assertFalse("Deposit of negative amount should fail", depositResult);
        
        // Verify balance remains unchanged at $300
        BigDecimal expectedBalance = new BigDecimal("300");
        assertEquals("Balance should remain 300 after failed deposit", 
                     expectedBalance, investmentAccountINV001.getBalance());
    }

    @Test
    public void testCase4_DepositExactlyAtSingleDepositLimit() {
        // Test Case 4: Deposit exactly at single-deposit limit
        // Action: Deposit $1,000,000 into "INV002"
        boolean depositResult = investmentAccountINV002.deposit(new BigDecimal("1000000"));
        
        // Verify deposit was successful
        assertTrue("Deposit at limit should succeed", depositResult);
        
        // Verify new balance is 1,000,000
        BigDecimal expectedBalance = new BigDecimal("1000000");
        assertEquals("Balance should be 1000000 after deposit", 
                     expectedBalance, investmentAccountINV002.getBalance());
    }

    @Test
    public void testCase5_DepositExceedsLimitByOneDollar() {
        // Test Case 5: Deposit exceeds limit by one dollar
        // Action: Attempt to deposit $1,000,001 into "SAV006"
        boolean depositResult = savingsAccountSAV006.deposit(new BigDecimal("1000001"));
        
        // Verify deposit failed
        assertFalse("Deposit exceeding limit should fail", depositResult);
        
        // Verify balance remains unchanged at $50
        BigDecimal expectedBalance = new BigDecimal("50");
        assertEquals("Balance should remain 50 after failed deposit", 
                     expectedBalance, savingsAccountSAV006.getBalance());
    }
}