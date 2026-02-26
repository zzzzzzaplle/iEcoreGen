import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    @Test
    public void testCase1_MultipleStockPositions() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a investment account "INV200"
        Customer customer = new Customer("Poe", "0814 Center St");
        customer.addInvestmentAccount("INV200");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV200");
        
        // Investment account "INV200" balance $5000.
        account.setBalance(5000.0);
        
        // Buy 10 shares "AAPL" at $100; (The new balance is $3900)
        boolean result1 = account.buyStock("AAPL", 10, 100.0);
        assertTrue(result1);
        assertEquals(3900.0, account.getBalance(), 0.000001);
        
        // Buy 20 shares "MSFT" at $50; (The new balance is $2800)
        boolean result2 = account.buyStock("MSFT", 20, 50.0);
        assertTrue(result2);
        assertEquals(2800.0, account.getBalance(), 0.000001);
        
        // Action: Calculate total value.
        double totalValue = account.calculateValue();
        
        // Expected Output: $5 000
        assertEquals(5000.0, totalValue, 0.001);
    }
    
    @Test
    public void testCase2_EmptyAccount() {
        // SetUp: Customer "Peter" (address "0816 Center St") holds a investment account "INV201"
        Customer customer = new Customer("Peter", "0816 Center St");
        customer.addInvestmentAccount("INV201");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV201");
        
        // New investment account "INV201" balance $0, no trades.
        account.setBalance(0.0);
        
        // Action: Calculate total value.
        double totalValue = account.calculateValue();
        
        // Expected Output: 0
        assertEquals(0.0, totalValue, 0.001);
    }
    
    @Test
    public void testCase3_CashOnlyNoStocks() {
        // SetUp: Customer 'Alice' (address "0811 Center St") holds a investment account "INV202"
        Customer customer = new Customer("Alice", "0811 Center St");
        customer.addInvestmentAccount("INV202");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV202");
        
        // Investment account "INV202" balance $1 000, no trades.
        account.setBalance(1000.0);
        
        // Action: Calculate total value.
        double totalValue = account.calculateValue();
        
        // Expected Output: $1 000
        assertEquals(1000.0, totalValue, 0.001);
    }
    
    @Test
    public void testCase4_RepeatedPurchasesOfSameStock() {
        // SetUp: Customer 'Bide' (address "0810 Main St") holds a investment account "INV203"
        Customer customer = new Customer("Bide", "0810 Main St");
        customer.addInvestmentAccount("INV203");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV203");
        
        // Investment account "INV203" balance $1 000.
        account.setBalance(1000.0);
        
        // Buy 5 shares "AAPL" at $100. (The new balance is $450)
        boolean result1 = account.buyStock("AAPL", 5, 100.0);
        assertTrue(result1);
        assertEquals(450.0, account.getBalance(), 0.000001);
        
        // Buy 1 shares "AAPL" at $120. (The new balance is $318)
        boolean result2 = account.buyStock("AAPL", 1, 120.0);
        assertTrue(result2);
        assertEquals(318.0, account.getBalance(), 0.000001);
        
        // Action: Calculate total value.
        double totalValue = account.calculateValue();
        
        // Expected Output: $ 1 000
        assertEquals(1000.0, totalValue, 0.001);
    }
    
    @Test
    public void testCase5_PrecisionCheckWithFractionalShare() {
        // SetUp: Customer 'Carli' (address "0820 Main St") holds a investment account "INV204"
        Customer customer = new Customer("Carli", "0820 Main St");
        customer.addInvestmentAccount("INV204");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV204");
        
        // Investment account "INV204" balance $123.45.
        account.setBalance(123.45);
        
        // Buy 10 shares of "MNO" at $10.10. (The new balance is $12.35)
        boolean result1 = account.buyStock("MNO", 10, 10.10);
        assertTrue(result1);
        assertEquals(12.35, account.getBalance(), 0.000001);
        
        // Deposit $500 into "INV204". (The new balance is $512.35)
        boolean depositResult = account.deposit(500.0);
        assertTrue(depositResult);
        assertEquals(512.35, account.getBalance(), 0.000001);
        
        // Buy 5 shares of "MNO" at $10.10. (The new balance is $456.8)
        boolean result2 = account.buyStock("MNO", 5, 10.10);
        assertTrue(result2);
        assertEquals(456.80, account.getBalance(), 0.000001);
        
        // Action: Calculate total value.
        double totalValue = account.calculateValue();
        
        // Expected Output: $623.45
        assertEquals(623.45, totalValue, 0.001);
    }
}