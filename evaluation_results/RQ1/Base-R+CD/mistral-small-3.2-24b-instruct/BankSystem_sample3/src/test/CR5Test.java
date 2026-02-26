import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    private Customer customer;
    
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
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV200");
        account.setBalance(5000);
        
        // Buy 10 shares "AAPL" at $100 (cost: 1000 + 100 commission = 1100, new balance: 3900)
        assertTrue(account.buyStock("AAPL", 10, 100));
        assertEquals(3900, account.getBalance(), 0.001);
        
        // Buy 20 shares "MSFT" at $50 (cost: 1000 + 100 commission = 1100, new balance: 2800)
        assertTrue(account.buyStock("MSFT", 20, 50));
        assertEquals(2800, account.getBalance(), 0.001);
        
        // Action: Calculate total value
        double result = account.calculateValue();
        
        // Expected Output: $5000
        // Calculation: balance (2800) + AAPL value (10 * 100 * 1.1 = 1100) + MSFT value (20 * 50 * 1.1 = 1100)
        assertEquals(5000, result, 0.001);
    }
    
    @Test
    public void testCase2_EmptyAccount() {
        // SetUp: Customer "Peter" (address "0816 Center St") holds a investment account "INV201"
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        
        // New investment account "INV201" balance $0, no trades
        customer.addInvestmentAccount("INV201");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV201");
        account.setBalance(0);
        
        // Action: Calculate total value
        double result = account.calculateValue();
        
        // Expected Output: 0
        assertEquals(0, result, 0.001);
    }
    
    @Test
    public void testCase3_CashOnlyNoStocks() {
        // SetUp: Customer 'Alice' (address "0811 Center St") holds a investment account "INV202"
        customer.setName("Alice");
        customer.setAddress("0811 Center St");
        
        // Investment account "INV202" balance $1000, no trades
        customer.addInvestmentAccount("INV202");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV202");
        account.setBalance(1000);
        
        // Action: Calculate total value
        double result = account.calculateValue();
        
        // Expected Output: $1000
        assertEquals(1000, result, 0.001);
    }
    
    @Test
    public void testCase4_RepeatedPurchasesSameStock() {
        // SetUp: Customer 'Bide' (address "0810 Main St") holds a investment account "INV203"
        customer.setName("Bide");
        customer.setAddress("0810 Main St");
        
        // Investment account "INV203" balance $1000
        customer.addInvestmentAccount("INV203");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV203");
        account.setBalance(1000);
        
        // Buy 5 shares "AAPL" at $100 (cost: 500 + 50 commission = 550, new balance: 450)
        assertTrue(account.buyStock("AAPL", 5, 100));
        assertEquals(450, account.getBalance(), 0.001);
        
        // Buy 1 share "AAPL" at $120 (cost: 120 + 12 commission = 132, new balance: 318)
        assertTrue(account.buyStock("AAPL", 1, 120));
        assertEquals(318, account.getBalance(), 0.001);
        
        // Action: Calculate total value
        double result = account.calculateValue();
        
        // Expected Output: $1000
        // Calculation: balance (318) + AAPL value (6 * 110 = 660) + AAPL value at 1.1x purchase price
        // First purchase: 5 shares * 100 * 1.1 = 550
        // Second purchase: 1 share * 120 * 1.1 = 132
        // Total stock value: 550 + 132 = 682
        // Total account value: 318 + 682 = 1000
        assertEquals(1000, result, 0.001);
    }
    
    @Test
    public void testCase5_PrecisionCheckWithFractionalShare() {
        // SetUp: Customer 'Carli' (address "0820 Main St") holds a investment account "INV204"
        customer.setName("Carli");
        customer.setAddress("0820 Main St");
        
        // Investment account "INV204" balance $123.45
        customer.addInvestmentAccount("INV204");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV204");
        account.setBalance(123.45);
        
        // Buy 10 shares of "MNO" at $10.10 (cost: 101 + 10.10 commission = 111.10, new balance: 12.35)
        assertTrue(account.buyStock("MNO", 10, 10.10));
        assertEquals(12.35, account.getBalance(), 0.001);
        
        // Deposit $500 into "INV204" (new balance: 512.35)
        assertTrue(account.deposit(500));
        assertEquals(512.35, account.getBalance(), 0.001);
        
        // Buy 5 shares of "MNO" at $10.10 (cost: 50.50 + 5.05 commission = 55.55, new balance: 456.80)
        assertTrue(account.buyStock("MNO", 5, 10.10));
        assertEquals(456.80, account.getBalance(), 0.001);
        
        // Action: Calculate total value
        double result = account.calculateValue();
        
        // Expected Output: $623.45
        // Calculation: balance (456.80) + 
        // First purchase: 10 shares * 10.10 * 1.1 = 111.10
        // Second purchase: 5 shares * 10.10 * 1.1 = 55.55
        // Total stock value: 111.10 + 55.55 = 166.65
        // Total account value: 456.80 + 166.65 = 623.45
        assertEquals(623.45, result, 0.001);
    }
}