import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
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
        
        // Create investment account "INV200" with balance $5000
        customer.addInvestmentAccount("INV200");
        investmentAccount = (InvestmentAccount) customer.findAccountById("INV200");
        investmentAccount.setBalance(5000.0);
        
        // Buy 10 shares "AAPL" at $100; (The new balance is $3900)
        assertTrue(investmentAccount.buyStock("AAPL", 10, 100.0));
        assertEquals(3900.0, investmentAccount.getBalance(), 0.001);
        
        // Buy 20 shares "MSFT" at $50; (The new balance is $2800)
        assertTrue(investmentAccount.buyStock("MSFT", 20, 50.0));
        assertEquals(2800.0, investmentAccount.getBalance(), 0.001);
        
        // Action: Calculate total value
        double totalValue = investmentAccount.calculateValue();
        
        // Expected Output: $5000
        // Calculation: 
        // - AAPL: 10 shares * ($100 * 1.1) = 10 * 110 = 1100
        // - MSFT: 20 shares * ($50 * 1.1) = 20 * 55 = 1100  
        // - Balance: 2800
        // - Total: 1100 + 1100 + 2800 = 5000
        assertEquals(5000.0, totalValue, 0.001);
    }
    
    @Test
    public void testCase2_EmptyAccount() {
        // SetUp: Customer "Peter" (address "0816 Center St") holds a investment account "INV201"
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        
        // New investment account "INV201" balance $0, no trades
        customer.addInvestmentAccount("INV201");
        investmentAccount = (InvestmentAccount) customer.findAccountById("INV201");
        investmentAccount.setBalance(0.0);
        
        // Action: Calculate total value
        double totalValue = investmentAccount.calculateValue();
        
        // Expected Output: 0
        assertEquals(0.0, totalValue, 0.001);
    }
    
    @Test
    public void testCase3_CashOnlyNoStocks() {
        // SetUp: Customer 'Alice' (address "0811 Center St") holds a investment account "INV202"
        customer.setName("Alice");
        customer.setAddress("0811 Center St");
        
        // Investment account "INV202" balance $1000, no trades
        customer.addInvestmentAccount("INV202");
        investmentAccount = (InvestmentAccount) customer.findAccountById("INV202");
        investmentAccount.setBalance(1000.0);
        
        // Action: Calculate total value
        double totalValue = investmentAccount.calculateValue();
        
        // Expected Output: $1000
        assertEquals(1000.0, totalValue, 0.001);
    }
    
    @Test
    public void testCase4_RepeatedPurchasesOfSameStock() {
        // SetUp: Customer 'Bide' (address "0810 Main St") holds a investment account "INV203"
        customer.setName("Bide");
        customer.setAddress("0810 Main St");
        
        // Investment account "INV203" balance $1000
        customer.addInvestmentAccount("INV203");
        investmentAccount = (InvestmentAccount) customer.findAccountById("INV203");
        investmentAccount.setBalance(1000.0);
        
        // Buy 5 shares "AAPL" at $100. (The new balance is $450)
        assertTrue(investmentAccount.buyStock("AAPL", 5, 100.0));
        assertEquals(450.0, investmentAccount.getBalance(), 0.001);
        
        // Buy 1 shares "AAPL" at $120. (The new balance is $318)
        assertTrue(investmentAccount.buyStock("AAPL", 1, 120.0));
        assertEquals(318.0, investmentAccount.getBalance(), 0.001);
        
        // Action: Calculate total value
        double totalValue = investmentAccount.calculateValue();
        
        // Expected Output: $1000
        // Calculation:
        // - First AAPL purchase: 5 shares * ($100 * 1.1) = 5 * 110 = 550
        // - Second AAPL purchase: 1 share * ($120 * 1.1) = 1 * 132 = 132
        // - Balance: 318
        // - Total: 550 + 132 + 318 = 1000
        assertEquals(1000.0, totalValue, 0.001);
    }
    
    @Test
    public void testCase5_PrecisionCheckWithFractionalShare() {
        // SetUp: Customer 'Carli' (address "0820 Main St") holds a investment account "INV204"
        customer.setName("Carli");
        customer.setAddress("0820 Main St");
        
        // Investment account "INV204" balance $123.45
        customer.addInvestmentAccount("INV204");
        investmentAccount = (InvestmentAccount) customer.findAccountById("INV204");
        investmentAccount.setBalance(123.45);
        
        // Buy 10 shares of "MNO" at $10.10. (The new balance is $12.35)
        assertTrue(investmentAccount.buyStock("MNO", 10, 10.10));
        assertEquals(12.35, investmentAccount.getBalance(), 0.001);
        
        // Deposit $500 into "INV204". (The new balance is $512.35)
        assertTrue(investmentAccount.deposit(500.0));
        assertEquals(512.35, investmentAccount.getBalance(), 0.001);
        
        // Buy 5 shares of "MNO" at $10.10. (The new balance is $456.8)
        assertTrue(investmentAccount.buyStock("MNO", 5, 10.10));
        assertEquals(456.8, investmentAccount.getBalance(), 0.001);
        
        // Action: Calculate total value
        double totalValue = investmentAccount.calculateValue();
        
        // Expected Output: $623.45
        // Calculation:
        // - First MNO purchase: 10 shares * ($10.10 * 1.1) = 10 * 11.11 = 111.10
        // - Second MNO purchase: 5 shares * ($10.10 * 1.1) = 5 * 11.11 = 55.55
        // - Balance: 456.8
        // - Total: 111.10 + 55.55 + 456.8 = 623.45 (rounded to 2 decimal places)
        assertEquals(623.45, totalValue, 0.001);
    }
}