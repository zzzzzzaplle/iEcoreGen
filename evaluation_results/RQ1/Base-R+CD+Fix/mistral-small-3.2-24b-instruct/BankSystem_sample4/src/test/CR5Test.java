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
        
        // Create investment account with $5000 balance
        InvestmentAccount account = new InvestmentAccount();
        account.setId("INV200");
        account.setBalance(5000.0);
        customer.getAccounts().add(account);
        
        // Buy 10 shares "AAPL" at $100 (balance becomes $3900)
        boolean result1 = account.buyStock("AAPL", 10, 100.0);
        assertTrue("AAPL purchase should succeed", result1);
        assertEquals(3900.0, account.getBalance(), 0.001);
        
        // Buy 20 shares "MSFT" at $50 (balance becomes $2800)
        boolean result2 = account.buyStock("MSFT", 20, 50.0);
        assertTrue("MSFT purchase should succeed", result2);
        assertEquals(2800.0, account.getBalance(), 0.001);
        
        // Action: Calculate total value
        double calculatedValue = account.calculateValue();
        
        // Expected Output: $5000 (2800 balance + (10*100*1.1) + (20*50*1.1) = 2800 + 1100 + 1100 = 5000)
        assertEquals(5000.0, calculatedValue, 0.001);
    }
    
    @Test
    public void testCase2_EmptyAccount() {
        // SetUp: Customer "Peter" with investment account "INV201"
        Customer customer = new Customer();
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        
        // Create investment account with $0 balance, no trades
        InvestmentAccount account = new InvestmentAccount();
        account.setId("INV201");
        account.setBalance(0.0);
        customer.getAccounts().add(account);
        
        // Action: Calculate total value
        double calculatedValue = account.calculateValue();
        
        // Expected Output: 0
        assertEquals(0.0, calculatedValue, 0.001);
    }
    
    @Test
    public void testCase3_CashOnlyNoStocks() {
        // SetUp: Customer 'Alice' with investment account "INV202"
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setAddress("0811 Center St");
        
        // Create investment account with $1000 balance, no trades
        InvestmentAccount account = new InvestmentAccount();
        account.setId("INV202");
        account.setBalance(1000.0);
        customer.getAccounts().add(account);
        
        // Action: Calculate total value
        double calculatedValue = account.calculateValue();
        
        // Expected Output: $1000 (balance only, no stocks)
        assertEquals(1000.0, calculatedValue, 0.001);
    }
    
    @Test
    public void testCase4_RepeatedPurchasesSameStock() {
        // SetUp: Customer 'Bide' with investment account "INV203"
        Customer customer = new Customer();
        customer.setName("Bide");
        customer.setAddress("0810 Main St");
        
        // Create investment account with $1000 balance
        InvestmentAccount account = new InvestmentAccount();
        account.setId("INV203");
        account.setBalance(1000.0);
        customer.getAccounts().add(account);
        
        // Buy 5 shares "AAPL" at $100 (balance becomes $450)
        boolean result1 = account.buyStock("AAPL", 5, 100.0);
        assertTrue("First AAPL purchase should succeed", result1);
        assertEquals(450.0, account.getBalance(), 0.001);
        
        // Buy 1 share "AAPL" at $120 (balance becomes $318)
        boolean result2 = account.buyStock("AAPL", 1, 120.0);
        assertTrue("Second AAPL purchase should succeed", result2);
        assertEquals(318.0, account.getBalance(), 0.001);
        
        // Action: Calculate total value
        double calculatedValue = account.calculateValue();
        
        // Expected Output: $1000 (318 balance + ((5+1)*average_price*1.1) = 318 + 682 = 1000)
        // Note: Each transaction is calculated separately: (5*100*1.1) + (1*120*1.1) = 550 + 132 = 682
        assertEquals(1000.0, calculatedValue, 0.001);
    }
    
    @Test
    public void testCase5_PrecisionCheckWithFractionalShare() {
        // SetUp: Customer 'Carli' with investment account "INV204"
        Customer customer = new Customer();
        customer.setName("Carli");
        customer.setAddress("0820 Main St");
        
        // Create investment account with $123.45 balance
        InvestmentAccount account = new InvestmentAccount();
        account.setId("INV204");
        account.setBalance(123.45);
        customer.getAccounts().add(account);
        
        // Buy 10 shares of "MNO" at $10.10 (balance becomes $12.35)
        boolean result1 = account.buyStock("MNO", 10, 10.10);
        assertTrue("First MNO purchase should succeed", result1);
        assertEquals(12.35, account.getBalance(), 0.001);
        
        // Deposit $500 into "INV204" (balance becomes $512.35)
        boolean depositResult = account.deposit(500.0);
        assertTrue("Deposit should succeed", depositResult);
        assertEquals(512.35, account.getBalance(), 0.001);
        
        // Buy 5 shares of "MNO" at $10.10 (balance becomes $456.8)
        boolean result2 = account.buyStock("MNO", 5, 10.10);
        assertTrue("Second MNO purchase should succeed", result2);
        assertEquals(456.8, account.getBalance(), 0.001);
        
        // Action: Calculate total value
        double calculatedValue = account.calculateValue();
        
        // Expected Output: $623.45 (456.8 balance + (10*10.10*1.1) + (5*10.10*1.1) = 456.8 + 111.1 + 55.55 = 623.45)
        assertEquals(623.45, calculatedValue, 0.001);
    }
}