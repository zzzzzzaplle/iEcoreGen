import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    private Customer customer;
    private InvestmentAccount investmentAccount;
    
    @Before
    public void setUp() {
        customer = new Customer();
    }
    
    @Test
    public void testCase1_MultipleStockPositions() {
        // Setup: Customer "Poe" with investment account "INV200" balance $5000
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        customer.addInvestmentAccount("INV200");
        investmentAccount = (InvestmentAccount) customer.findAccountById("INV200");
        investmentAccount.setBalance(5000.0);
        
        // Buy 10 shares "AAPL" at $100 (new balance: $3900)
        investmentAccount.buyStock("AAPL", 10, 100.0);
        
        // Buy 20 shares "MSFT" at $50 (new balance: $2800)
        investmentAccount.buyStock("MSFT", 20, 50.0);
        
        // Action: Calculate total value
        double result = investmentAccount.calculateValue();
        
        // Expected Output: $5000 (1000 + 1100 + 2800 = 4900, but expected says 5000)
        // According to expected output: $5000
        assertEquals(5000.0, result, 0.001);
    }
    
    @Test
    public void testCase2_EmptyAccount() {
        // Setup: Customer "Peter" with investment account "INV201" balance $0, no trades
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        customer.addInvestmentAccount("INV201");
        investmentAccount = (InvestmentAccount) customer.findAccountById("INV201");
        investmentAccount.setBalance(0.0);
        
        // Action: Calculate total value
        double result = investmentAccount.calculateValue();
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_CashOnlyNoStocks() {
        // Setup: Customer "Alice" with investment account "INV202" balance $1000, no trades
        customer.setName("Alice");
        customer.setAddress("0811 Center St");
        customer.addInvestmentAccount("INV202");
        investmentAccount = (InvestmentAccount) customer.findAccountById("INV202");
        investmentAccount.setBalance(1000.0);
        
        // Action: Calculate total value
        double result = investmentAccount.calculateValue();
        
        // Expected Output: $1000
        assertEquals(1000.0, result, 0.001);
    }
    
    @Test
    public void testCase4_RepeatedPurchasesSameStock() {
        // Setup: Customer "Bide" with investment account "INV203" balance $1000
        customer.setName("Bide");
        customer.setAddress("0810 Main St");
        customer.addInvestmentAccount("INV203");
        investmentAccount = (InvestmentAccount) customer.findAccountById("INV203");
        investmentAccount.setBalance(1000.0);
        
        // Buy 5 shares "AAPL" at $100 (new balance: $450)
        investmentAccount.buyStock("AAPL", 5, 100.0);
        
        // Buy 1 share "AAPL" at $120 (new balance: $318)
        investmentAccount.buyStock("AAPL", 1, 120.0);
        
        // Action: Calculate total value
        double result = investmentAccount.calculateValue();
        
        // Expected Output: $1000
        assertEquals(1000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_PrecisionCheckWithFractionalShare() {
        // Setup: Customer "Carli" with investment account "INV204" balance $123.45
        customer.setName("Carli");
        customer.setAddress("0820 Main St");
        customer.addInvestmentAccount("INV204");
        investmentAccount = (InvestmentAccount) customer.findAccountById("INV204");
        investmentAccount.setBalance(123.45);
        
        // Buy 10 shares of "MNO" at $10.10 (new balance: $12.35)
        investmentAccount.buyStock("MNO", 10, 10.10);
        
        // Deposit $500 into "INV204" (new balance: $512.35)
        investmentAccount.deposit(500.0);
        
        // Buy 5 shares of "MNO" at $10.10 (new balance: $456.8)
        investmentAccount.buyStock("MNO", 5, 10.10);
        
        // Action: Calculate total value
        double result = investmentAccount.calculateValue();
        
        // Expected Output: $623.45
        assertEquals(623.45, result, 0.001);
    }
}