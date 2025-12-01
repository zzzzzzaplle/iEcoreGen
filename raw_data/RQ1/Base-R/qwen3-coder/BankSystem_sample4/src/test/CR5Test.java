import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.math.BigDecimal;

public class CR5Test {
    
    @Test
    public void testCase1_MultipleStockPositions() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a investment account "INV200"
        Customer customer = new Customer();
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        
        InvestmentAccount account = new InvestmentAccount();
        account.setId("INV200");
        account.setBalance(new BigDecimal("5000"));
        
        // Buy 10 shares "AAPL" at $100; (The new balance is $3900)
        boolean result1 = account.buyStocks("AAPL", 10, new BigDecimal("100"));
        assertTrue(result1);
        assertEquals(new BigDecimal("3900.00"), account.getBalance());
        
        // Buy 20 shares "MSFT" at $50; (The new balance is $2800)
        boolean result2 = account.buyStocks("MSFT", 20, new BigDecimal("50"));
        assertTrue(result2);
        assertEquals(new BigDecimal("2800.00"), account.getBalance());
        
        // Action: Calculate total value
        BigDecimal totalValue = account.calculateTotalValue();
        
        // Expected Output: $5000
        assertEquals(new BigDecimal("5000.00"), totalValue);
    }
    
    @Test
    public void testCase2_EmptyAccount() {
        // SetUp: Customer "Peter" (address "0816 Center St") holds a investment account "INV201"
        Customer customer = new Customer();
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        
        InvestmentAccount account = new InvestmentAccount();
        account.setId("INV201");
        account.setBalance(BigDecimal.ZERO); // New investment account balance $0, no trades
        
        // Action: Calculate total value
        BigDecimal totalValue = account.calculateTotalValue();
        
        // Expected Output: 0
        assertEquals(BigDecimal.ZERO.setScale(2), totalValue);
    }
    
    @Test
    public void testCase3_CashOnlyNoStocks() {
        // SetUp: Customer 'Alice' (address "0811 Center St") holds a investment account "INV202"
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setAddress("0811 Center St");
        
        InvestmentAccount account = new InvestmentAccount();
        account.setId("INV202");
        account.setBalance(new BigDecimal("1000")); // Balance $1000, no trades
        
        // Action: Calculate total value
        BigDecimal totalValue = account.calculateTotalValue();
        
        // Expected Output: $1000
        assertEquals(new BigDecimal("1000.00"), totalValue);
    }
    
    @Test
    public void testCase4_RepeatedPurchasesOfSameStock() {
        // SetUp: Customer 'Bide' (address "0810 Main St") holds a investment account "INV203"
        Customer customer = new Customer();
        customer.setName("Bide");
        customer.setAddress("0810 Main St");
        
        InvestmentAccount account = new InvestmentAccount();
        account.setId("INV203");
        account.setBalance(new BigDecimal("1000"));
        
        // Buy 5 shares "AAPL" at $100. (The new balance is $450)
        boolean result1 = account.buyStocks("AAPL", 5, new BigDecimal("100"));
        assertTrue(result1);
        assertEquals(new BigDecimal("450.00"), account.getBalance());
        
        // Buy 1 shares "AAPL" at $120. (The new balance is $318)
        boolean result2 = account.buyStocks("AAPL", 1, new BigDecimal("120"));
        assertTrue(result2);
        assertEquals(new BigDecimal("318.00"), account.getBalance());
        
        // Action: Calculate total value
        BigDecimal totalValue = account.calculateTotalValue();
        
        // Expected Output: $1000
        assertEquals(new BigDecimal("1000.00"), totalValue);
    }
    
    @Test
    public void testCase5_PrecisionCheckWithFractionalShare() {
        // SetUp: Customer 'Carli' (address "0820 Main St") holds a investment account "INV204"
        Customer customer = new Customer();
        customer.setName("Carli");
        customer.setAddress("0820 Main St");
        
        InvestmentAccount account = new InvestmentAccount();
        account.setId("INV204");
        account.setBalance(new BigDecimal("123.45"));
        
        // Buy 10 shares of "MNO" at $10.10. (The new balance is $12.35)
        boolean result1 = account.buyStocks("MNO", 10, new BigDecimal("10.10"));
        assertTrue(result1);
        assertEquals(new BigDecimal("12.35"), account.getBalance());
        
        // Deposit $500 into "INV204". (The new balance is $512.35)
        boolean depositResult = account.deposit(new BigDecimal("500"));
        assertTrue(depositResult);
        assertEquals(new BigDecimal("512.35"), account.getBalance());
        
        // Buy 5 shares of "MNO" at $10.10. (The new balance is $456.8)
        boolean result2 = account.buyStocks("MNO", 5, new BigDecimal("10.10"));
        assertTrue(result2);
        assertEquals(new BigDecimal("456.80"), account.getBalance());
        
        // Action: Calculate total value
        BigDecimal totalValue = account.calculateTotalValue();
        
        // Expected Output: $623.45
        assertEquals(new BigDecimal("623.45"), totalValue);
    }
}