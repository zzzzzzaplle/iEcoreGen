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
        // SetUp: Customer "Poe" with address "0814 Center St"
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        
        // Create investment account "INV200" with balance $5000
        customer.addInvestmentAccount("INV200");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV200");
        account.deposit(5000);
        
        // Buy 10 shares "AAPL" at $100 (new balance: $3900)
        account.buyStock("AAPL", 10, 100);
        
        // Buy 20 shares "MSFT" at $50 (new balance: $2800)
        account.buyStock("MSFT", 20, 50);
        
        // Action: Calculate total value
        double actualValue = account.calculateValue();
        
        // Expected Output: $5000 (rounded to 2 decimal places)
        assertEquals(5000.0, actualValue, 0.001);
    }
    
    @Test
    public void testCase2_EmptyAccount() {
        // SetUp: Customer "Peter" with address "0816 Center St"
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        
        // Create investment account "INV201" with balance $0, no trades
        customer.addInvestmentAccount("INV201");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV201");
        
        // Action: Calculate total value
        double actualValue = account.calculateValue();
        
        // Expected Output: 0
        assertEquals(0.0, actualValue, 0.001);
    }
    
    @Test
    public void testCase3_CashOnlyNoStocks() {
        // SetUp: Customer "Alice" with address "0811 Center St"
        customer.setName("Alice");
        customer.setAddress("0811 Center St");
        
        // Create investment account "INV202" with balance $1000, no trades
        customer.addInvestmentAccount("INV202");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV202");
        account.deposit(1000);
        
        // Action: Calculate total value
        double actualValue = account.calculateValue();
        
        // Expected Output: $1000
        assertEquals(1000.0, actualValue, 0.001);
    }
    
    @Test
    public void testCase4_RepeatedPurchasesSameStock() {
        // SetUp: Customer "Bide" with address "0810 Main St"
        customer.setName("Bide");
        customer.setAddress("0810 Main St");
        
        // Create investment account "INV203" with balance $1000
        customer.addInvestmentAccount("INV203");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV203");
        account.deposit(1000);
        
        // Buy 5 shares "AAPL" at $100 (new balance: $450)
        account.buyStock("AAPL", 5, 100);
        
        // Buy 1 share "AAPL" at $120 (new balance: $318)
        account.buyStock("AAPL", 1, 120);
        
        // Action: Calculate total value
        double actualValue = account.calculateValue();
        
        // Expected Output: $1000
        assertEquals(1000.0, actualValue, 0.001);
    }
    
    @Test
    public void testCase5_PrecisionCheckWithFractionalShare() {
        // SetUp: Customer "Carli" with address "0820 Main St"
        customer.setName("Carli");
        customer.setAddress("0820 Main St");
        
        // Create investment account "INV204" with balance $123.45
        customer.addInvestmentAccount("INV204");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV204");
        account.deposit(123.45);
        
        // Buy 10 shares of "MNO" at $10.10 (new balance: $12.35)
        account.buyStock("MNO", 10, 10.10);
        
        // Deposit $500 into "INV204" (new balance: $512.35)
        account.deposit(500);
        
        // Buy 5 shares of "MNO" at $10.10 (new balance: $456.80)
        account.buyStock("MNO", 5, 10.10);
        
        // Action: Calculate total value
        double actualValue = account.calculateValue();
        
        // Expected Output: $623.45
        assertEquals(623.45, actualValue, 0.001);
    }
}