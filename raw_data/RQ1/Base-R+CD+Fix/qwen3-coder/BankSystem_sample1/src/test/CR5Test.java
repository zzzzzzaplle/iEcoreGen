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
        // SetUp: Customer "Poe" (address "0814 Center St") holds a investment account "INV200"
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        customer.addInvestmentAccount("INV200");
        investmentAccount = (InvestmentAccount) customer.findAccountById("INV200");
        
        // Investment account "INV200" balance $5000
        investmentAccount.setBalance(5000);
        
        // Buy 10 shares "AAPL" at $100; (The new balance is $3900)
        investmentAccount.buyStock("AAPL", 10, 100);
        
        // Buy 20 shares "MSFT" at $50; (The new balance is $2800)
        investmentAccount.buyStock("MSFT", 20, 50);
        
        // Action: Calculate total value
        double result = investmentAccount.calculateValue();
        
        // Expected Output: $5000
        assertEquals(5000.0, result, 0.001);
    }
    
    @Test
    public void testCase2_EmptyAccount() {
        // SetUp: Customer "Peter" (address "0816 Center St") holds a investment account "INV201"
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        customer.addInvestmentAccount("INV201");
        investmentAccount = (InvestmentAccount) customer.findAccountById("INV201");
        
        // New investment account "INV201" balance $0, no trades
        investmentAccount.setBalance(0);
        
        // Action: Calculate total value
        double result = investmentAccount.calculateValue();
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_CashOnlyNoStocks() {
        // SetUp: Customer 'Alice' (address "0811 Center St") holds a investment account "INV202"
        customer.setName("Alice");
        customer.setAddress("0811 Center St");
        customer.addInvestmentAccount("INV202");
        investmentAccount = (InvestmentAccount) customer.findAccountById("INV202");
        
        // Investment account "INV202" balance $1000, no trades
        investmentAccount.setBalance(1000);
        
        // Action: Calculate total value
        double result = investmentAccount.calculateValue();
        
        // Expected Output: $1000
        assertEquals(1000.0, result, 0.001);
    }
    
    @Test
    public void testCase4_RepeatedPurchasesOfSameStock() {
        // SetUp: Customer 'Bide' (address "0810 Main St") holds a investment account "INV203"
        customer.setName("Bide");
        customer.setAddress("0810 Main St");
        customer.addInvestmentAccount("INV203");
        investmentAccount = (InvestmentAccount) customer.findAccountById("INV203");
        
        // Investment account "INV203" balance $1000
        investmentAccount.setBalance(1000);
        
        // Buy 5 shares "AAPL" at $100. (The new balance is $450)
        investmentAccount.buyStock("AAPL", 5, 100);
        
        // Buy 1 shares "AAPL" at $120. (The new balance is $318)
        investmentAccount.buyStock("AAPL", 1, 120);
        
        // Action: Calculate total value
        double result = investmentAccount.calculateValue();
        
        // Expected Output: $1000
        assertEquals(1000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_PrecisionCheckWithFractionalShare() {
        // SetUp: Customer 'Carli' (address "0820 Main St") holds a investment account "INV204"
        customer.setName("Carli");
        customer.setAddress("0820 Main St");
        customer.addInvestmentAccount("INV204");
        investmentAccount = (InvestmentAccount) customer.findAccountById("INV204");
        
        // Investment account "INV204" balance $123.45
        investmentAccount.setBalance(123.45);
        
        // Buy 10 shares of "MNO" at $10.10. (The new balance is $12.35)
        investmentAccount.buyStock("MNO", 10, 10.10);
        
        // Deposit $500 into "INV204". (The new balance is $512.35)
        investmentAccount.deposit(500);
        
        // Buy 5 shares of "MNO" at $10.10. (The new balance is $456.8)
        investmentAccount.buyStock("MNO", 5, 10.10);
        
        // Action: Calculate total value
        double result = investmentAccount.calculateValue();
        
        // Expected Output: $623.45
        assertEquals(623.45, result, 0.001);
    }
}