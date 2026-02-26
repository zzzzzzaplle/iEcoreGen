import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.math.BigDecimal;

public class CR5Test {
    
    @Test
    public void testCase1_MultipleStockPositions() {
        // SetUp: Customer "Poe" with investment account "INV200"
        Customer customer = new Customer();
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        
        InvestmentAccount invAccount = new InvestmentAccount();
        invAccount.setId("INV200");
        invAccount.setBalance(new BigDecimal("5000.00"));
        customer.addAccount(invAccount);
        
        // Buy 10 shares "AAPL" at $100 (new balance: $3900)
        boolean result1 = invAccount.buyStock("AAPL", 10, 100.0);
        assertTrue("First purchase should succeed", result1);
        assertEquals("Balance after first purchase", new BigDecimal("3900.00"), invAccount.getBalance());
        
        // Buy 20 shares "MSFT" at $50 (new balance: $2800)
        boolean result2 = invAccount.buyStock("MSFT", 20, 50.0);
        assertTrue("Second purchase should succeed", result2);
        assertEquals("Balance after second purchase", new BigDecimal("2800.00"), invAccount.getBalance());
        
        // Action: Calculate total value
        BigDecimal totalValue = invAccount.calculateTotalValue();
        
        // Expected Output: $5000
        assertEquals("Total value with multiple stock positions", new BigDecimal("5000.00"), totalValue);
    }
    
    @Test
    public void testCase2_EmptyAccount() {
        // SetUp: Customer "Peter" with investment account "INV201"
        Customer customer = new Customer();
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        
        InvestmentAccount invAccount = new InvestmentAccount();
        invAccount.setId("INV201");
        invAccount.setBalance(BigDecimal.ZERO);
        customer.addAccount(invAccount);
        
        // Action: Calculate total value
        BigDecimal totalValue = invAccount.calculateTotalValue();
        
        // Expected Output: 0
        assertEquals("Total value for empty account", BigDecimal.ZERO.setScale(2), totalValue);
    }
    
    @Test
    public void testCase3_CashOnlyNoStocks() {
        // SetUp: Customer "Alice" with investment account "INV202"
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setAddress("0811 Center St");
        
        InvestmentAccount invAccount = new InvestmentAccount();
        invAccount.setId("INV202");
        invAccount.setBalance(new BigDecimal("1000.00"));
        customer.addAccount(invAccount);
        
        // Action: Calculate total value
        BigDecimal totalValue = invAccount.calculateTotalValue();
        
        // Expected Output: $1000
        assertEquals("Total value for cash-only account", new BigDecimal("1000.00"), totalValue);
    }
    
    @Test
    public void testCase4_RepeatedPurchasesSameStock() {
        // SetUp: Customer "Bide" with investment account "INV203"
        Customer customer = new Customer();
        customer.setName("Bide");
        customer.setAddress("0810 Main St");
        
        InvestmentAccount invAccount = new InvestmentAccount();
        invAccount.setId("INV203");
        invAccount.setBalance(new BigDecimal("1000.00"));
        customer.addAccount(invAccount);
        
        // Buy 5 shares "AAPL" at $100 (new balance: $450)
        boolean result1 = invAccount.buyStock("AAPL", 5, 100.0);
        assertTrue("First purchase should succeed", result1);
        assertEquals("Balance after first purchase", new BigDecimal("450.00"), invAccount.getBalance());
        
        // Buy 1 share "AAPL" at $120 (new balance: $318)
        boolean result2 = invAccount.buyStock("AAPL", 1, 120.0);
        assertTrue("Second purchase should succeed", result2);
        assertEquals("Balance after second purchase", new BigDecimal("318.00"), invAccount.getBalance());
        
        // Action: Calculate total value
        BigDecimal totalValue = invAccount.calculateTotalValue();
        
        // Expected Output: $1000
        assertEquals("Total value with repeated purchases of same stock", new BigDecimal("1000.00"), totalValue);
    }
    
    @Test
    public void testCase5_PrecisionCheckWithFractionalShare() {
        // SetUp: Customer "Carli" with investment account "INV204"
        Customer customer = new Customer();
        customer.setName("Carli");
        customer.setAddress("0820 Main St");
        
        InvestmentAccount invAccount = new InvestmentAccount();
        invAccount.setId("INV204");
        invAccount.setBalance(new BigDecimal("123.45"));
        customer.addAccount(invAccount);
        
        // Buy 10 shares of "MNO" at $10.10 (new balance: $12.35)
        boolean result1 = invAccount.buyStock("MNO", 10, 10.10);
        assertTrue("First purchase should succeed", result1);
        assertEquals("Balance after first purchase", new BigDecimal("12.35"), invAccount.getBalance());
        
        // Deposit $500 into "INV204" (new balance: $512.35)
        boolean depositResult = customer.deposit("INV204", 500.0);
        assertTrue("Deposit should succeed", depositResult);
        assertEquals("Balance after deposit", new BigDecimal("512.35"), invAccount.getBalance());
        
        // Buy 5 shares of "MNO" at $10.10 (new balance: $456.80)
        boolean result2 = invAccount.buyStock("MNO", 5, 10.10);
        assertTrue("Second purchase should succeed", result2);
        assertEquals("Balance after second purchase", new BigDecimal("456.80"), invAccount.getBalance());
        
        // Action: Calculate total value
        BigDecimal totalValue = invAccount.calculateTotalValue();
        
        // Expected Output: $623.45
        assertEquals("Total value with precision check", new BigDecimal("623.45"), totalValue);
    }
}