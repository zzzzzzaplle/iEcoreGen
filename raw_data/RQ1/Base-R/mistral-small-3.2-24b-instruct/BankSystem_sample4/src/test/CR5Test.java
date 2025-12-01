import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private BankSystem bankSystem;
    
    @Before
    public void setUp() {
        bankSystem = new BankSystem();
    }
    
    @Test
    public void testCase1_MultipleStockPositions() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a investment account "INV200"
        Customer customer = new Customer();
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        
        InvestmentAccount account = new InvestmentAccount();
        account.setAccountId("INV200");
        account.setBalance(new BigDecimal("5000"));
        
        // Buy 10 shares "AAPL" at $100; (The new balance is $3900)
        BigDecimal aaplPrice = new BigDecimal("100");
        boolean buyResult1 = account.buyStocks("AAPL", 10, aaplPrice);
        assertTrue("AAPL purchase should succeed", buyResult1);
        assertEquals("Balance after AAPL purchase", new BigDecimal("3900"), account.getBalance());
        
        // Buy 20 shares "MSFT" at $50; (The new balance is $2800)
        BigDecimal msftPrice = new BigDecimal("50");
        boolean buyResult2 = account.buyStocks("MSFT", 20, msftPrice);
        assertTrue("MSFT purchase should succeed", buyResult2);
        assertEquals("Balance after MSFT purchase", new BigDecimal("2800"), account.getBalance());
        
        // Action: Calculate total value
        BigDecimal totalValue = account.calculateAccountValue();
        
        // Expected Output: $5000 (2800 balance + (10*100*1.1) + (20*50*1.1) = 2800 + 1100 + 1100 = 5000)
        assertEquals("Total account value for multiple stock positions", 
                    new BigDecimal("5000.00"), totalValue);
    }
    
    @Test
    public void testCase2_EmptyAccount() {
        // SetUp: Customer "Peter" (address "0816 Center St") holds a investment account "INV201"
        Customer customer = new Customer();
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        
        InvestmentAccount account = new InvestmentAccount();
        account.setAccountId("INV201");
        account.setBalance(BigDecimal.ZERO); // New investment account balance $0, no trades
        
        // Action: Calculate total value
        BigDecimal totalValue = account.calculateAccountValue();
        
        // Expected Output: 0
        assertEquals("Total account value for empty account", 
                    BigDecimal.ZERO.setScale(2), totalValue);
    }
    
    @Test
    public void testCase3_CashOnlyNoStocks() {
        // SetUp: Customer 'Alice' (address "0811 Center St") holds a investment account "INV202"
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setAddress("0811 Center St");
        
        InvestmentAccount account = new InvestmentAccount();
        account.setAccountId("INV202");
        account.setBalance(new BigDecimal("1000")); // Balance $1000, no trades
        
        // Action: Calculate total value
        BigDecimal totalValue = account.calculateAccountValue();
        
        // Expected Output: $1000
        assertEquals("Total account value for cash-only account", 
                    new BigDecimal("1000.00"), totalValue);
    }
    
    @Test
    public void testCase4_RepeatedPurchasesOfSameStock() {
        // SetUp: Customer 'Bide' (address "0810 Main St") holds a investment account "INV203"
        Customer customer = new Customer();
        customer.setName("Bide");
        customer.setAddress("0810 Main St");
        
        InvestmentAccount account = new InvestmentAccount();
        account.setAccountId("INV203");
        account.setBalance(new BigDecimal("1000"));
        
        // Buy 5 shares "AAPL" at $100. (The new balance is $450)
        BigDecimal aaplPrice1 = new BigDecimal("100");
        boolean buyResult1 = account.buyStocks("AAPL", 5, aaplPrice1);
        assertTrue("First AAPL purchase should succeed", buyResult1);
        assertEquals("Balance after first AAPL purchase", new BigDecimal("450"), account.getBalance());
        
        // Buy 1 shares "AAPL" at $120. (The new balance is $318)
        BigDecimal aaplPrice2 = new BigDecimal("120");
        boolean buyResult2 = account.buyStocks("AAPL", 1, aaplPrice2);
        assertTrue("Second AAPL purchase should succeed", buyResult2);
        assertEquals("Balance after second AAPL purchase", new BigDecimal("318"), account.getBalance());
        
        // Action: Calculate total value
        BigDecimal totalValue = account.calculateAccountValue();
        
        // Expected Output: $1000 (318 balance + (6*110) + (6*132) = 318 + 660 + 132 = 1110? Wait, let's recalculate:
        // Total AAPL shares: 5 + 1 = 6 shares
        // Current price for all shares: 6 * (average price * 1.1) but spec says 1.1 times purchase price per transaction
        // Actually, each transaction is calculated separately:
        // Transaction 1: 5 shares at $100 → current value: 5 * (100 * 1.1) = 5 * 110 = 550
        // Transaction 2: 1 share at $120 → current value: 1 * (120 * 1.1) = 1 * 132 = 132
        // Total stock value: 550 + 132 = 682
        // Account value: 318 (balance) + 682 (stocks) = 1000
        assertEquals("Total account value for repeated purchases of same stock", 
                    new BigDecimal("1000.00"), totalValue);
    }
    
    @Test
    public void testCase5_PrecisionCheckWithFractionalShare() {
        // SetUp: Customer 'Carli' (address "0820 Main St") holds a investment account "INV204"
        Customer customer = new Customer();
        customer.setName("Carli");
        customer.setAddress("0820 Main St");
        
        InvestmentAccount account = new InvestmentAccount();
        account.setAccountId("INV204");
        account.setBalance(new BigDecimal("123.45"));
        
        // Buy 10 shares of "MNO" at $10.10. (The new balance is $12.35)
        BigDecimal mnoPrice1 = new BigDecimal("10.10");
        boolean buyResult1 = account.buyStocks("MNO", 10, mnoPrice1);
        assertTrue("First MNO purchase should succeed", buyResult1);
        
        // Calculate expected balance after first purchase:
        // Cost: 10 * 10.10 = 101.00
        // Commission: 101.00 * 0.10 = 10.10
        // Total cost: 101.00 + 10.10 = 111.10
        // New balance: 123.45 - 111.10 = 12.35
        assertEquals("Balance after first MNO purchase", 
                    new BigDecimal("12.35"), account.getBalance());
        
        // Deposit $500 into "INV204". (The new balance is $512.35)
        boolean depositResult = account.deposit(new BigDecimal("500"));
        assertTrue("Deposit should succeed", depositResult);
        assertEquals("Balance after deposit", new BigDecimal("512.35"), account.getBalance());
        
        // Buy 5 shares of "MNO" at $10.10. (The new balance is $456.8)
        BigDecimal mnoPrice2 = new BigDecimal("10.10");
        boolean buyResult2 = account.buyStocks("MNO", 5, mnoPrice2);
        assertTrue("Second MNO purchase should succeed", buyResult2);
        
        // Calculate expected balance after second purchase:
        // Cost: 5 * 10.10 = 50.50
        // Commission: 50.50 * 0.10 = 5.05
        // Total cost: 50.50 + 5.05 = 55.55
        // New balance: 512.35 - 55.55 = 456.80
        assertEquals("Balance after second MNO purchase", 
                    new BigDecimal("456.80"), account.getBalance());
        
        // Action: Calculate total value
        BigDecimal totalValue = account.calculateAccountValue();
        
        // Expected Output: $623.45
        // Calculation:
        // Transaction 1: 10 shares at $10.10 → current value: 10 * (10.10 * 1.1) = 10 * 11.11 = 111.10
        // Transaction 2: 5 shares at $10.10 → current value: 5 * (10.10 * 1.1) = 5 * 11.11 = 55.55
        // Total stock value: 111.10 + 55.55 = 166.65
        // Account value: 456.80 (balance) + 166.65 (stocks) = 623.45
        assertEquals("Total account value with precision check", 
                    new BigDecimal("623.45"), totalValue);
    }
}