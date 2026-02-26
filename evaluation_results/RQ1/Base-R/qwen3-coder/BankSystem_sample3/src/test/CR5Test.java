import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    @Test
    public void testCase1_MultipleStockPositions() {
        // SetUp: Customer "Poe" with investment account "INV200"
        Customer customer = new Customer();
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        
        InvestmentAccount account = new InvestmentAccount();
        account.setAccountId("INV200");
        account.setBalance(5000.0);
        
        // Buy 10 shares "AAPL" at $100 (balance becomes $3900)
        boolean buy1 = account.buyStocks("AAPL", 10, 100.0);
        assertTrue("First stock purchase should succeed", buy1);
        assertEquals("Balance after first purchase", 3900.0, account.getBalance(), 0.001);
        
        // Buy 20 shares "MSFT" at $50 (balance becomes $2800)
        boolean buy2 = account.buyStocks("MSFT", 20, 50.0);
        assertTrue("Second stock purchase should succeed", buy2);
        assertEquals("Balance after second purchase", 2800.0, account.getBalance(), 0.001);
        
        // Action: Calculate total value
        double totalValue = account.calculateInvestmentValue();
        
        // Expected Output: $5000
        assertEquals("Total investment value with multiple stocks", 5000.0, totalValue, 0.001);
    }
    
    @Test
    public void testCase2_EmptyAccount() {
        // SetUp: Customer "Peter" with investment account "INV201"
        Customer customer = new Customer();
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        
        InvestmentAccount account = new InvestmentAccount();
        account.setAccountId("INV201");
        account.setBalance(0.0); // Empty account, no trades
        
        // Action: Calculate total value
        double totalValue = account.calculateInvestmentValue();
        
        // Expected Output: 0
        assertEquals("Total investment value for empty account", 0.0, totalValue, 0.001);
    }
    
    @Test
    public void testCase3_CashOnlyNoStocks() {
        // SetUp: Customer 'Alice' with investment account "INV202"
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setAddress("0811 Center St");
        
        InvestmentAccount account = new InvestmentAccount();
        account.setAccountId("INV202");
        account.setBalance(1000.0); // Cash only, no trades
        
        // Action: Calculate total value
        double totalValue = account.calculateInvestmentValue();
        
        // Expected Output: $1000
        assertEquals("Total investment value for cash-only account", 1000.0, totalValue, 0.001);
    }
    
    @Test
    public void testCase4_RepeatedPurchasesSameStock() {
        // SetUp: Customer 'Bide' with investment account "INV203"
        Customer customer = new Customer();
        customer.setName("Bide");
        customer.setAddress("0810 Main St");
        
        InvestmentAccount account = new InvestmentAccount();
        account.setAccountId("INV203");
        account.setBalance(1000.0);
        
        // Buy 5 shares "AAPL" at $100 (balance becomes $450)
        boolean buy1 = account.buyStocks("AAPL", 5, 100.0);
        assertTrue("First AAPL purchase should succeed", buy1);
        assertEquals("Balance after first purchase", 450.0, account.getBalance(), 0.001);
        
        // Buy 1 share "AAPL" at $120 (balance becomes $318)
        boolean buy2 = account.buyStocks("AAPL", 1, 120.0);
        assertTrue("Second AAPL purchase should succeed", buy2);
        assertEquals("Balance after second purchase", 318.0, account.getBalance(), 0.001);
        
        // Action: Calculate total value
        double totalValue = account.calculateInvestmentValue();
        
        // Expected Output: $1000
        assertEquals("Total investment value with repeated stock purchases", 1000.0, totalValue, 0.001);
    }
    
    @Test
    public void testCase5_PrecisionCheckWithFractionalShare() {
        // SetUp: Customer 'Carli' with investment account "INV204"
        Customer customer = new Customer();
        customer.setName("Carli");
        customer.setAddress("0820 Main St");
        
        InvestmentAccount account = new InvestmentAccount();
        account.setAccountId("INV204");
        account.setBalance(123.45);
        
        // Buy 10 shares of "MNO" at $10.10 (balance becomes $12.35)
        boolean buy1 = account.buyStocks("MNO", 10, 10.10);
        assertTrue("First MNO purchase should succeed", buy1);
        assertEquals("Balance after first purchase", 12.35, account.getBalance(), 0.001);
        
        // Deposit $500 into "INV204" (balance becomes $512.35)
        boolean deposit = account.deposit(500.0);
        assertTrue("Deposit should succeed", deposit);
        assertEquals("Balance after deposit", 512.35, account.getBalance(), 0.001);
        
        // Buy 5 shares of "MNO" at $10.10 (balance becomes $456.8)
        boolean buy2 = account.buyStocks("MNO", 5, 10.10);
        assertTrue("Second MNO purchase should succeed", buy2);
        assertEquals("Balance after second purchase", 456.8, account.getBalance(), 0.001);
        
        // Action: Calculate total value
        double totalValue = account.calculateInvestmentValue();
        
        // Expected Output: $623.45
        assertEquals("Total investment value with precision check", 623.45, totalValue, 0.001);
    }
}