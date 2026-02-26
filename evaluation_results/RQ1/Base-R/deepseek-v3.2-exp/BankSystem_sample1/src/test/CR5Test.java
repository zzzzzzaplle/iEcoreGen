import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    private Customer customer;
    private InvestmentAccount investmentAccount;
    
    @Before
    public void setUp() {
        customer = new Customer();
        investmentAccount = new InvestmentAccount();
    }
    
    @Test
    public void testCase1_MultipleStockPositions() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a investment account "INV200"
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        investmentAccount.setAccountId("INV200");
        
        // Investment account "INV200" balance $5000
        investmentAccount.setBalance(5000.0);
        
        // Buy 10 shares "AAPL" at $100; (The new balance is $3900)
        assertTrue(investmentAccount.buyStocks("AAPL", 10, 100.0));
        assertEquals(3900.0, investmentAccount.getBalance(), 0.001);
        
        // Buy 20 shares "MSFT" at $50; (The new balance is $2800)
        assertTrue(investmentAccount.buyStocks("MSFT", 20, 50.0));
        assertEquals(2800.0, investmentAccount.getBalance(), 0.001);
        
        // Action: Calculate total value
        double totalValue = investmentAccount.calculateTotalValue();
        
        // Expected Output: $5000 (balance + stock value: 2800 + (10*100*1.1 + 20*50*1.1) = 2800 + 3300 = 6100)
        // Note: The expected output in specification appears incorrect based on the calculation logic
        // According to the method logic: stock value = purchase price * 1.1 * quantity
        // AAPL: 10 * 100 * 1.1 = 1100, MSFT: 20 * 50 * 1.1 = 1100, total stock value = 2200
        // Total value = balance (2800) + stock value (2200) = 5000
        assertEquals(5000.0, totalValue, 0.001);
    }
    
    @Test
    public void testCase2_EmptyAccount() {
        // SetUp: Customer "Peter" (address "0816 Center St") holds a investment account "INV201"
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        investmentAccount.setAccountId("INV201");
        
        // New investment account "INV201" balance $0, no trades
        investmentAccount.setBalance(0.0);
        
        // Action: Calculate total value
        double totalValue = investmentAccount.calculateTotalValue();
        
        // Expected Output: 0
        assertEquals(0.0, totalValue, 0.001);
    }
    
    @Test
    public void testCase3_CashOnlyNoStocks() {
        // SetUp: Customer 'Alice' (address "0811 Center St") holds a investment account "INV202"
        customer.setName("Alice");
        customer.setAddress("0811 Center St");
        investmentAccount.setAccountId("INV202");
        
        // Investment account "INV202" balance $1000, no trades
        investmentAccount.setBalance(1000.0);
        
        // Action: Calculate total value
        double totalValue = investmentAccount.calculateTotalValue();
        
        // Expected Output: $1000
        assertEquals(1000.0, totalValue, 0.001);
    }
    
    @Test
    public void testCase4_RepeatedPurchasesOfSameStock() {
        // SetUp: Customer 'Bide' (address "0810 Main St") holds a investment account "INV203"
        customer.setName("Bide");
        customer.setAddress("0810 Main St");
        investmentAccount.setAccountId("INV203");
        
        // Investment account "INV203" balance $1000
        investmentAccount.setBalance(1000.0);
        
        // Buy 5 shares "AAPL" at $100. (The new balance is $450)
        assertTrue(investmentAccount.buyStocks("AAPL", 5, 100.0));
        assertEquals(450.0, investmentAccount.getBalance(), 0.001);
        
        // Buy 1 shares "AAPL" at $120. (The new balance is $318)
        assertTrue(investmentAccount.buyStocks("AAPL", 1, 120.0));
        assertEquals(318.0, investmentAccount.getBalance(), 0.001);
        
        // Action: Calculate total value
        double totalValue = investmentAccount.calculateTotalValue();
        
        // Expected Output: $1000 (balance + stock value: 318 + (5*100*1.1 + 1*120*1.1) = 318 + 682 = 1000)
        assertEquals(1000.0, totalValue, 0.001);
    }
    
    @Test
    public void testCase5_PrecisionCheckWithFractionalShare() {
        // SetUp: Customer 'Carli' (address "0820 Main St") holds a investment account "INV204"
        customer.setName("Carli");
        customer.setAddress("0820 Main St");
        investmentAccount.setAccountId("INV204");
        
        // Investment account "INV204" balance $123.45
        investmentAccount.setBalance(123.45);
        
        // Buy 10 shares of "MNO" at $10.10. (The new balance is $12.35)
        assertTrue(investmentAccount.buyStocks("MNO", 10, 10.10));
        assertEquals(12.35, investmentAccount.getBalance(), 0.001);
        
        // Deposit $500 into "INV204". (The new balance is $512.35)
        assertTrue(investmentAccount.deposit(500.0));
        assertEquals(512.35, investmentAccount.getBalance(), 0.001);
        
        // Buy 5 shares of "MNO" at $10.10. (The new balance is $456.8)
        assertTrue(investmentAccount.buyStocks("MNO", 5, 10.10));
        assertEquals(456.8, investmentAccount.getBalance(), 0.001);
        
        // Action: Calculate total value
        double totalValue = investmentAccount.calculateTotalValue();
        
        // Expected Output: $623.45 (balance + stock value: 456.8 + (10*10.10*1.1 + 5*10.10*1.1) = 456.8 + 166.65 = 623.45)
        assertEquals(623.45, totalValue, 0.001);
    }
}