import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    @Test
    public void testCase1_MultipleStockPositions() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a investment account "INV200"
        Customer customer = new Customer();
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        
        // Create investment account "INV200" with balance $5000
        InvestmentAccount account = new InvestmentAccount();
        account.setId("INV200");
        account.setBalance(5000.0);
        
        // Add account to customer
        customer.getAccounts().add(account);
        
        // Buy 10 shares "AAPL" at $100 (The new balance is $3900)
        boolean buyResult1 = account.buyStock("AAPL", 10, 100.0);
        assertTrue("First stock purchase should succeed", buyResult1);
        assertEquals("Balance after first purchase should be $3900", 3900.0, account.getBalance(), 0.001);
        
        // Buy 20 shares "MSFT" at $50 (The new balance is $2800)
        boolean buyResult2 = account.buyStock("MSFT", 20, 50.0);
        assertTrue("Second stock purchase should succeed", buyResult2);
        assertEquals("Balance after second purchase should be $2800", 2800.0, account.getBalance(), 0.001);
        
        // Action: Calculate total value
        double totalValue = account.calculateValue();
        
        // Expected Output: $5000
        // Calculation: 
        // - AAPL: 10 shares × ($100 × 1.1) = $1100
        // - MSFT: 20 shares × ($50 × 1.1) = $1100  
        // - Balance: $2800
        // - Total: $1100 + $1100 + $2800 = $5000
        assertEquals("Total value should be $5000", 5000.0, totalValue, 0.001);
    }
    
    @Test
    public void testCase2_EmptyAccount() {
        // SetUp: Customer "Peter" (address "0816 Center St") holds a investment account "INV201"
        Customer customer = new Customer();
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        
        // Create investment account "INV201" with balance $0, no trades
        InvestmentAccount account = new InvestmentAccount();
        account.setId("INV201");
        account.setBalance(0.0);
        
        // Add account to customer
        customer.getAccounts().add(account);
        
        // Action: Calculate total value
        double totalValue = account.calculateValue();
        
        // Expected Output: 0
        assertEquals("Total value of empty account should be 0", 0.0, totalValue, 0.001);
    }
    
    @Test
    public void testCase3_CashOnlyNoStocks() {
        // SetUp: Customer 'Alice' (address "0811 Center St") holds a investment account "INV202"
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setAddress("0811 Center St");
        
        // Create investment account "INV202" with balance $1000, no trades
        InvestmentAccount account = new InvestmentAccount();
        account.setId("INV202");
        account.setBalance(1000.0);
        
        // Add account to customer
        customer.getAccounts().add(account);
        
        // Action: Calculate total value
        double totalValue = account.calculateValue();
        
        // Expected Output: $1000
        assertEquals("Total value of cash-only account should be $1000", 1000.0, totalValue, 0.001);
    }
    
    @Test
    public void testCase4_RepeatedPurchasesSameStock() {
        // SetUp: Customer 'Bide' (address "0810 Main St") holds a investment account "INV203"
        Customer customer = new Customer();
        customer.setName("Bide");
        customer.setAddress("0810 Main St");
        
        // Create investment account "INV203" with balance $1000
        InvestmentAccount account = new InvestmentAccount();
        account.setId("INV203");
        account.setBalance(1000.0);
        
        // Add account to customer
        customer.getAccounts().add(account);
        
        // Buy 5 shares "AAPL" at $100 (The new balance is $450)
        boolean buyResult1 = account.buyStock("AAPL", 5, 100.0);
        assertTrue("First AAPL purchase should succeed", buyResult1);
        assertEquals("Balance after first purchase should be $450", 450.0, account.getBalance(), 0.001);
        
        // Buy 1 share "AAPL" at $120 (The new balance is $318)
        boolean buyResult2 = account.buyStock("AAPL", 1, 120.0);
        assertTrue("Second AAPL purchase should succeed", buyResult2);
        assertEquals("Balance after second purchase should be $318", 318.0, account.getBalance(), 0.001);
        
        // Action: Calculate total value
        double totalValue = account.calculateValue();
        
        // Expected Output: $1000
        // Calculation:
        // - First AAPL purchase: 5 shares × ($100 × 1.1) = $550
        // - Second AAPL purchase: 1 share × ($120 × 1.1) = $132
        // - Balance: $318
        // - Total: $550 + $132 + $318 = $1000
        assertEquals("Total value should be $1000", 1000.0, totalValue, 0.001);
    }
    
    @Test
    public void testCase5_PrecisionCheckWithFractionalShare() {
        // SetUp: Customer 'Carli' (address "0820 Main St") holds a investment account "INV204"
        Customer customer = new Customer();
        customer.setName("Carli");
        customer.setAddress("0820 Main St");
        
        // Create investment account "INV204" with balance $123.45
        InvestmentAccount account = new InvestmentAccount();
        account.setId("INV204");
        account.setBalance(123.45);
        
        // Add account to customer
        customer.getAccounts().add(account);
        
        // Buy 10 shares of "MNO" at $10.10 (The new balance is $12.35)
        boolean buyResult1 = account.buyStock("MNO", 10, 10.10);
        assertTrue("First MNO purchase should succeed", buyResult1);
        assertEquals("Balance after first purchase should be $12.35", 12.35, account.getBalance(), 0.001);
        
        // Deposit $500 into "INV204" (The new balance is $512.35)
        boolean depositResult = account.deposit(500.0);
        assertTrue("Deposit should succeed", depositResult);
        assertEquals("Balance after deposit should be $512.35", 512.35, account.getBalance(), 0.001);
        
        // Buy 5 shares of "MNO" at $10.10 (The new balance is $456.80)
        boolean buyResult2 = account.buyStock("MNO", 5, 10.10);
        assertTrue("Second MNO purchase should succeed", buyResult2);
        assertEquals("Balance after second purchase should be $456.80", 456.80, account.getBalance(), 0.001);
        
        // Action: Calculate total value
        double totalValue = account.calculateValue();
        
        // Expected Output: $623.45
        // Calculation:
        // - First MNO purchase: 10 shares × ($10.10 × 1.1) = $111.10
        // - Second MNO purchase: 5 shares × ($10.10 × 1.1) = $55.55
        // - Balance: $456.80
        // - Total: $111.10 + $55.55 + $456.80 = $623.45
        assertEquals("Total value should be $623.45", 623.45, totalValue, 0.001);
    }
}