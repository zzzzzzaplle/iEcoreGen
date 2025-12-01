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
        account.setAccountId("INV200");
        account.setBalance(new BigDecimal("5000"));
        
        // Buy 10 shares "AAPL" at $100; (The new balance is $3900)
        boolean buyResult1 = account.buyStocks("AAPL", 10, new BigDecimal("100"));
        assertTrue("First stock purchase should succeed", buyResult1);
        assertEquals("Balance after first purchase should be 3900", 
                     new BigDecimal("3900"), account.getBalance());
        
        // Buy 20 shares "MSFT" at $50; (The new balance is $2800)
        boolean buyResult2 = account.buyStocks("MSFT", 20, new BigDecimal("50"));
        assertTrue("Second stock purchase should succeed", buyResult2);
        assertEquals("Balance after second purchase should be 2800", 
                     new BigDecimal("2800"), account.getBalance());
        
        // Action: Calculate total value
        BigDecimal totalValue = account.calculateInvestmentValue();
        
        // Expected Output: $5000
        assertEquals("Total investment value should be 5000", 
                     new BigDecimal("5000.00"), totalValue);
    }
    
    @Test
    public void testCase2_EmptyAccount() {
        // SetUp: Customer "Peter" (address "0816 Center St") holds a investment account "INV201"
        Customer customer = new Customer();
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        
        InvestmentAccount account = new InvestmentAccount();
        account.setAccountId("INV201");
        account.setBalance(BigDecimal.ZERO);
        // No stock transactions
        
        // Action: Calculate total value
        BigDecimal totalValue = account.calculateInvestmentValue();
        
        // Expected Output: 0
        assertEquals("Total investment value should be 0", 
                     new BigDecimal("0.00"), totalValue);
    }
    
    @Test
    public void testCase3_CashOnlyNoStocks() {
        // SetUp: Customer 'Alice' (address "0811 Center St") holds a investment account "INV202"
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setAddress("0811 Center St");
        
        InvestmentAccount account = new InvestmentAccount();
        account.setAccountId("INV202");
        account.setBalance(new BigDecimal("1000"));
        // No stock transactions
        
        // Action: Calculate total value
        BigDecimal totalValue = account.calculateInvestmentValue();
        
        // Expected Output: $1000
        assertEquals("Total investment value should be 1000", 
                     new BigDecimal("1000.00"), totalValue);
    }
    
    @Test
    public void testCase4_RepeatedPurchasesOfSameStock() {
        // SetUp: Customer 'Bide' (address "0810 Main St") holds a investment account "INV203"
        Customer customer = new Customer();
        customer.setName("Bide");
        customer.setAddress("0810 Main St");
        
        InvestmentAccount account = new InvestmentAccount();
        account.setAccountId("INV203");
        account.setBalance(new BigDecimal("1000"));
        
        // Buy 5 shares "AAPL" at $100. (The new balance is $450)
        boolean buyResult1 = account.buyStocks("AAPL", 5, new BigDecimal("100"));
        assertTrue("First stock purchase should succeed", buyResult1);
        assertEquals("Balance after first purchase should be 450", 
                     new BigDecimal("450"), account.getBalance());
        
        // Buy 1 shares "AAPL" at $120. (The new balance is $318)
        boolean buyResult2 = account.buyStocks("AAPL", 1, new BigDecimal("120"));
        assertTrue("Second stock purchase should succeed", buyResult2);
        assertEquals("Balance after second purchase should be 318", 
                     new BigDecimal("318"), account.getBalance());
        
        // Action: Calculate total value
        BigDecimal totalValue = account.calculateInvestmentValue();
        
        // Expected Output: $1000
        assertEquals("Total investment value should be 1000", 
                     new BigDecimal("1000.00"), totalValue);
    }
    
    @Test
    public void testCase5_PrecisionCheckWithFractionalShare() {
        // SetUp: Customer 'Carli' (address "0820 Main St") holds a investment account "INV204"
        Customer customer = new Customer();
        customer.setName("Carli");
        customer.setAddress("0820 Main St");
        
        InvestmentAccount account = new InvestmentAccount();
        account.setAccountId("INV204");
        account.setBalance(new BigDecimal("123.45"));
        
        // Buy 10 shares of "MNO" at $10.10. (The new balance is $12.35)
        boolean buyResult1 = account.buyStocks("MNO", 10, new BigDecimal("10.10"));
        assertTrue("First stock purchase should succeed", buyResult1);
        assertEquals("Balance after first purchase should be 12.35", 
                     new BigDecimal("12.35"), account.getBalance());
        
        // Deposit $500 into "INV204". (The new balance is $512.35)
        boolean depositResult = account.deposit(new BigDecimal("500"));
        assertTrue("Deposit should succeed", depositResult);
        assertEquals("Balance after deposit should be 512.35", 
                     new BigDecimal("512.35"), account.getBalance());
        
        // Buy 5 shares of "MNO" at $10.10. (The new balance is $456.80)
        boolean buyResult2 = account.buyStocks("MNO", 5, new BigDecimal("10.10"));
        assertTrue("Second stock purchase should succeed", buyResult2);
        assertEquals("Balance after second purchase should be 456.80", 
                     new BigDecimal("456.80"), account.getBalance());
        
        // Action: Calculate total value
        BigDecimal totalValue = account.calculateInvestmentValue();
        
        // Expected Output: $623.45
        assertEquals("Total investment value should be 623.45", 
                     new BigDecimal("623.45"), totalValue);
    }
}