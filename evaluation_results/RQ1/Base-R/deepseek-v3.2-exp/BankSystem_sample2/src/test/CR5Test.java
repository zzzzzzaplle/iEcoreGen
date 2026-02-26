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
        
        InvestmentAccount invAccount = new InvestmentAccount();
        invAccount.setId("INV200");
        invAccount.setBalance(5000.0);
        
        // Set the investment account to customer
        customer.getAccounts().add(invAccount);
        
        // Buy 10 shares "AAPL" at $100; (The new balance is $3900)
        boolean buyResult1 = invAccount.buyStocks("AAPL", 10, 100.0);
        assertTrue("First stock purchase should succeed", buyResult1);
        assertEquals("Balance after first purchase should be 3900", 3900.0, invAccount.getBalance(), 0.001);
        
        // Buy 20 shares "MSFT" at $50; (The new balance is $2800)
        boolean buyResult2 = invAccount.buyStocks("MSFT", 20, 50.0);
        assertTrue("Second stock purchase should succeed", buyResult2);
        assertEquals("Balance after second purchase should be 2800", 2800.0, invAccount.getBalance(), 0.001);
        
        // Action: Calculate total value
        double totalValue = invAccount.calculateInvestmentValue();
        
        // Expected Output: $5000
        assertEquals("Total investment value should be 5000", 5000.0, totalValue, 0.001);
    }
    
    @Test
    public void testCase2_EmptyAccount() {
        // SetUp: Customer "Peter" (address "0816 Center St") holds a investment account "INV201"
        Customer customer = new Customer();
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        
        InvestmentAccount invAccount = new InvestmentAccount();
        invAccount.setId("INV201");
        invAccount.setBalance(0.0);
        // No trades (empty stockTransactions list)
        
        // Set the investment account to customer
        customer.getAccounts().add(invAccount);
        
        // Action: Calculate total value
        double totalValue = invAccount.calculateInvestmentValue();
        
        // Expected Output: 0
        assertEquals("Total investment value should be 0", 0.0, totalValue, 0.001);
    }
    
    @Test
    public void testCase3_CashOnlyNoStocks() {
        // SetUp: Customer 'Alice' (address "0811 Center St") holds a investment account "INV202"
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setAddress("0811 Center St");
        
        InvestmentAccount invAccount = new InvestmentAccount();
        invAccount.setId("INV202");
        invAccount.setBalance(1000.0);
        // No trades (empty stockTransactions list)
        
        // Set the investment account to customer
        customer.getAccounts().add(invAccount);
        
        // Action: Calculate total value
        double totalValue = invAccount.calculateInvestmentValue();
        
        // Expected Output: $1000
        assertEquals("Total investment value should be 1000", 1000.0, totalValue, 0.001);
    }
    
    @Test
    public void testCase4_RepeatedPurchasesOfSameStock() {
        // SetUp: Customer 'Bide' (address "0810 Main St") holds a investment account "INV203"
        Customer customer = new Customer();
        customer.setName("Bide");
        customer.setAddress("0810 Main St");
        
        InvestmentAccount invAccount = new InvestmentAccount();
        invAccount.setId("INV203");
        invAccount.setBalance(1000.0);
        
        // Set the investment account to customer
        customer.getAccounts().add(invAccount);
        
        // Buy 5 shares "AAPL" at $100. (The new balance is $450)
        boolean buyResult1 = invAccount.buyStocks("AAPL", 5, 100.0);
        assertTrue("First AAPL purchase should succeed", buyResult1);
        assertEquals("Balance after first purchase should be 450", 450.0, invAccount.getBalance(), 0.001);
        
        // Buy 1 shares "AAPL" at $120. (The new balance is $318)
        boolean buyResult2 = invAccount.buyStocks("AAPL", 1, 120.0);
        assertTrue("Second AAPL purchase should succeed", buyResult2);
        assertEquals("Balance after second purchase should be 318", 318.0, invAccount.getBalance(), 0.001);
        
        // Action: Calculate total value
        double totalValue = invAccount.calculateInvestmentValue();
        
        // Expected Output: $1000
        assertEquals("Total investment value should be 1000", 1000.0, totalValue, 0.001);
    }
    
    @Test
    public void testCase5_PrecisionCheckWithFractionalShare() {
        // SetUp: Customer 'Carli' (address "0820 Main St") holds a investment account "INV204"
        Customer customer = new Customer();
        customer.setName("Carli");
        customer.setAddress("0820 Main St");
        
        InvestmentAccount invAccount = new InvestmentAccount();
        invAccount.setId("INV204");
        invAccount.setBalance(123.45);
        
        // Set the investment account to customer
        customer.getAccounts().add(invAccount);
        
        // Buy 10 shares of "MNO" at $10.10. (The new balance is $12.35)
        boolean buyResult1 = invAccount.buyStocks("MNO", 10, 10.10);
        assertTrue("First MNO purchase should succeed", buyResult1);
        assertEquals("Balance after first purchase should be 12.35", 12.35, invAccount.getBalance(), 0.001);
        
        // Deposit $500 into "INV204". (The new balance is $512.35)
        boolean depositResult = customer.deposit("INV204", 500.0);
        assertTrue("Deposit should succeed", depositResult);
        assertEquals("Balance after deposit should be 512.35", 512.35, invAccount.getBalance(), 0.001);
        
        // Buy 5 shares of "MNO" at $10.10. (The new balance is $456.8)
        boolean buyResult2 = invAccount.buyStocks("MNO", 5, 10.10);
        assertTrue("Second MNO purchase should succeed", buyResult2);
        assertEquals("Balance after second purchase should be 456.8", 456.8, invAccount.getBalance(), 0.001);
        
        // Action: Calculate total value
        double totalValue = invAccount.calculateInvestmentValue();
        
        // Expected Output: $623.45
        assertEquals("Total investment value should be 623.45", 623.45, totalValue, 0.001);
    }
}