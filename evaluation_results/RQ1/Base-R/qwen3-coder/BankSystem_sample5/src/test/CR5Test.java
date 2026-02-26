import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Customer customer;
    private InvestmentAccount investmentAccount;
    
    @Before
    public void setUp() {
        customer = new Customer();
    }
    
    @Test
    public void testCase1_MultipleStockPositions() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a investment account "INV200"
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        investmentAccount = new InvestmentAccount();
        investmentAccount.setAccountId("INV200");
        investmentAccount.setBalance(new BigDecimal("5000"));
        customer.addAccount(investmentAccount);
        
        // Buy 10 shares "AAPL" at $100; (The new balance is $3900)
        investmentAccount.buyStocks("AAPL", 10, new BigDecimal("100"));
        
        // Buy 20 shares "MSFT" at $50; (The new balance is $2800)
        investmentAccount.buyStocks("MSFT", 20, new BigDecimal("50"));
        
        // Action: Calculate total value
        BigDecimal result = investmentAccount.calculateInvestmentValue();
        
        // Expected Output: $5000
        assertEquals(new BigDecimal("5000.00"), result);
    }
    
    @Test
    public void testCase2_EmptyAccount() {
        // SetUp: Customer "Peter" (address "0816 Center St") holds a investment account "INV201"
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        investmentAccount = new InvestmentAccount();
        investmentAccount.setAccountId("INV201");
        investmentAccount.setBalance(BigDecimal.ZERO);
        customer.addAccount(investmentAccount);
        
        // Action: Calculate total value
        BigDecimal result = investmentAccount.calculateInvestmentValue();
        
        // Expected Output: 0
        assertEquals(BigDecimal.ZERO.setScale(2), result);
    }
    
    @Test
    public void testCase3_CashOnlyNoStocks() {
        // SetUp: Customer 'Alice' (address "0811 Center St") holds a investment account "INV202"
        customer.setName("Alice");
        customer.setAddress("0811 Center St");
        investmentAccount = new InvestmentAccount();
        investmentAccount.setAccountId("INV202");
        investmentAccount.setBalance(new BigDecimal("1000"));
        customer.addAccount(investmentAccount);
        
        // Action: Calculate total value
        BigDecimal result = investmentAccount.calculateInvestmentValue();
        
        // Expected Output: $1000
        assertEquals(new BigDecimal("1000.00"), result);
    }
    
    @Test
    public void testCase4_RepeatedPurchasesOfSameStock() {
        // SetUp: Customer 'Bide' (address "0810 Main St") holds a investment account "INV203"
        customer.setName("Bide");
        customer.setAddress("0810 Main St");
        investmentAccount = new InvestmentAccount();
        investmentAccount.setAccountId("INV203");
        investmentAccount.setBalance(new BigDecimal("1000"));
        customer.addAccount(investmentAccount);
        
        // Buy 5 shares "AAPL" at $100. (The new balance is $450)
        investmentAccount.buyStocks("AAPL", 5, new BigDecimal("100"));
        
        // Buy 1 shares "AAPL" at $120. (The new balance is $318)
        investmentAccount.buyStocks("AAPL", 1, new BigDecimal("120"));
        
        // Action: Calculate total value
        BigDecimal result = investmentAccount.calculateInvestmentValue();
        
        // Expected Output: $1000
        assertEquals(new BigDecimal("1000.00"), result);
    }
    
    @Test
    public void testCase5_PrecisionCheckWithFractionalShare() {
        // SetUp: Customer 'Carli' (address "0820 Main St") holds a investment account "INV204"
        customer.setName("Carli");
        customer.setAddress("0820 Main St");
        investmentAccount = new InvestmentAccount();
        investmentAccount.setAccountId("INV204");
        investmentAccount.setBalance(new BigDecimal("123.45"));
        customer.addAccount(investmentAccount);
        
        // Buy 10 shares of "MNO" at $10.10. (The new balance is $12.35)
        investmentAccount.buyStocks("MNO", 10, new BigDecimal("10.10"));
        
        // Deposit $500 into "INV204". (The new balance is $512.35)
        investmentAccount.deposit(new BigDecimal("500"));
        
        // Buy 5 shares of "MNO" at $10.10. (The new balance is $456.80)
        investmentAccount.buyStocks("MNO", 5, new BigDecimal("10.10"));
        
        // Action: Calculate total value
        BigDecimal result = investmentAccount.calculateInvestmentValue();
        
        // Expected Output: $623.45
        assertEquals(new BigDecimal("623.45"), result);
    }
}