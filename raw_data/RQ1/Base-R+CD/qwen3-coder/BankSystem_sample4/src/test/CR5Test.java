import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR5Test {
    
    @Test
    public void testCase1_multipleStockPositions() {
        // SetUp:
        // - Customer "Poe" (address "0814 Center St") holds a investment account "INV200"
        Customer customer = new Customer();
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        
        // - Investment account "INV200" balance $5000.
        InvestmentAccount account = new InvestmentAccount();
        account.setId("INV200");
        account.setBalance(5000);
        customer.getAccounts().add(account);
        
        // - Buy 10 shares "AAPL" at $100; (The new balance is $3900)
        boolean result1 = account.buyStock("AAPL", 10, 100);
        assertTrue(result1);
        assertEquals(3900, account.getBalance(), 0.01);
        
        // - Buy 20 shares "MSFT" at $50; (The new balance is $2800)
        boolean result2 = account.buyStock("MSFT", 20, 50);
        assertTrue(result2);
        assertEquals(2800, account.getBalance(), 0.01);
        
        // Action:
        // - Calculate total value.
        double totalValue = account.calculateValue();
        
        // Expected Output: $5 000      
        assertEquals(5000, totalValue, 0.01);
    }
    
    @Test
    public void testCase2_emptyAccount() {
        // SetUp:
        // - Customer "Peter" (address "0816 Center St") holds a investment account "INV201"
        Customer customer = new Customer();
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        
        // - New investment account "INV201" balance $0, no trades.
        InvestmentAccount account = new InvestmentAccount();
        account.setId("INV201");
        account.setBalance(0);
        customer.getAccounts().add(account);
        
        // Action:
        // - Calculate total value.
        double totalValue = account.calculateValue();
        
        // Expected Output: 0
        assertEquals(0, totalValue, 0.01);
    }
    
    @Test
    public void testCase3_cashOnlyNoStocks() {
        // SetUp:
        // - Customer 'Alice' (address "0811 Center St") holds a investment account "INV202"
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setAddress("0811 Center St");
        
        // - Investment account "INV202" balance $1 000, no trades.
        InvestmentAccount account = new InvestmentAccount();
        account.setId("INV202");
        account.setBalance(1000);
        customer.getAccounts().add(account);
        
        // Action:
        // - Calculate total value.
        double totalValue = account.calculateValue();
        
        // Expected Output: $1 000
        assertEquals(1000, totalValue, 0.01);
    }
    
    @Test
    public void testCase4_repeatedPurchasesOfSameStock() {
        // SetUp:
        // - Customer 'Bide' (address "0810 Main St") holds a investment account "INV203"
        Customer customer = new Customer();
        customer.setName("Bide");
        customer.setAddress("0810 Main St");
        
        // - Investment account "INV203" balance $1 000.
        InvestmentAccount account = new InvestmentAccount();
        account.setId("INV203");
        account.setBalance(1000);
        customer.getAccounts().add(account);
        
        // - Buy 5 shares "AAPL" at $100. (The new balance is $450)
        boolean result1 = account.buyStock("AAPL", 5, 100);
        assertTrue(result1);
        assertEquals(450, account.getBalance(), 0.01);
        
        // - Buy 1 shares "AAPL" at $120. (The new balance is $318)
        boolean result2 = account.buyStock("AAPL", 1, 120);
        assertTrue(result2);
        assertEquals(318, account.getBalance(), 0.01);
        
        // Action:
        // - Calculate total value.
        double totalValue = account.calculateValue();
        
        // Expected Output: $ 1 000
        assertEquals(1000, totalValue, 0.01);
    }
    
    @Test
    public void testCase5_precisionCheckWithFractionalShare() {
        // SetUp:
        // - Customer 'Carli' (address "0820 Main St") holds a investment account "INV204"
        Customer customer = new Customer();
        customer.setName("Carli");
        customer.setAddress("0820 Main St");
        
        // - Investment account "INV204" balance $123.45.
        InvestmentAccount account = new InvestmentAccount();
        account.setId("INV204");
        account.setBalance(123.45);
        customer.getAccounts().add(account);
        
        // - Buy 10 shares of "MNO" at $10.10. (The new balance is $12.35)
        boolean result1 = account.buyStock("MNO", 10, 10.10);
        assertTrue(result1);
        assertEquals(12.35, account.getBalance(), 0.01);
        
        // - Deposit $500 into "INV204". (The new balance is $512.35)
        boolean result2 = account.deposit(500);
        assertTrue(result2);
        assertEquals(512.35, account.getBalance(), 0.01);
        
        // - Buy 5 shares of "MNO" at $10.10. (The new balance is $456.8)
        boolean result3 = account.buyStock("MNO", 5, 10.10);
        assertTrue(result3);
        assertEquals(456.8, account.getBalance(), 0.01);
        
        // Action:
        // - Calculate total value.
        double totalValue = account.calculateValue();
        
        // Expected Output: $623.45
        assertEquals(623.45, totalValue, 0.01);
    }
}