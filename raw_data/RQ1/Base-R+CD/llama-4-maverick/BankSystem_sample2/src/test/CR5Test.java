import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Customer customer;
    private InvestmentAccount investmentAccount;
    
    @Before
    public void setUp() {
        customer = new Customer();
        investmentAccount = new InvestmentAccount();
    }
    
    @Test
    public void testCase1_multipleStockPositions() {
        // SetUp: Customer "Poe" holds an investment account "INV200"
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        investmentAccount.setId("INV200");
        investmentAccount.setBalance(5000);
        
        // Buy 10 shares "AAPL" at $100 (cost: $1000, commission: $100, total: $1100)
        assertTrue(investmentAccount.buyStock("AAPL", 10, 100));
        assertEquals(3900, investmentAccount.getBalance(), 0.01);
        
        // Buy 20 shares "MSFT" at $50 (cost: $1000, commission: $100, total: $1100)
        assertTrue(investmentAccount.buyStock("MSFT", 20, 50));
        assertEquals(2800, investmentAccount.getBalance(), 0.01);
        
        // Add account to customer
        customer.getAccounts().add(investmentAccount);
        
        // Action: Calculate total value
        double totalValue = investmentAccount.calculateValue();
        
        // Expected Output: $5000
        assertEquals(5000, totalValue, 0.01);
    }
    
    @Test
    public void testCase2_emptyAccount() {
        // SetUp: Customer "Peter" holds a new investment account "INV201"
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        investmentAccount.setId("INV201");
        investmentAccount.setBalance(0);
        
        // No trades made
        
        // Add account to customer
        customer.getAccounts().add(investmentAccount);
        
        // Action: Calculate total value
        double totalValue = investmentAccount.calculateValue();
        
        // Expected Output: 0
        assertEquals(0, totalValue, 0.01);
    }
    
    @Test
    public void testCase3_cashOnlyNoStocks() {
        // SetUp: Customer "Alice" holds an investment account "INV202"
        customer.setName("Alice");
        customer.setAddress("0811 Center St");
        investmentAccount.setId("INV202");
        investmentAccount.setBalance(1000);
        
        // No trades made
        
        // Add account to customer
        customer.getAccounts().add(investmentAccount);
        
        // Action: Calculate total value
        double totalValue = investmentAccount.calculateValue();
        
        // Expected Output: $1000
        assertEquals(1000, totalValue, 0.01);
    }
    
    @Test
    public void testCase4_repeatedPurchasesOfSameStock() {
        // SetUp: Customer "Bide" holds an investment account "INV203"
        customer.setName("Bide");
        customer.setAddress("0810 Main St");
        investmentAccount.setId("INV203");
        investmentAccount.setBalance(1000);
        
        // Buy 5 shares "AAPL" at $100 (cost: $500, commission: $50, total: $550)
        assertTrue(investmentAccount.buyStock("AAPL", 5, 100));
        assertEquals(450, investmentAccount.getBalance(), 0.01);
        
        // Buy 1 shares "AAPL" at $120 (cost: $120, commission: $12, total: $132)
        assertTrue(investmentAccount.buyStock("AAPL", 1, 120));
        assertEquals(318, investmentAccount.getBalance(), 0.01);
        
        // Add account to customer
        customer.getAccounts().add(investmentAccount);
        
        // Action: Calculate total value
        double totalValue = investmentAccount.calculateValue();
        
        // Expected Output: $1000
        assertEquals(1000, totalValue, 0.01);
    }
    
    @Test
    public void testCase5_precisionCheckWithFractionalShare() {
        // SetUp: Customer "Carli" holds an investment account "INV204"
        customer.setName("Carli");
        customer.setAddress("0820 Main St");
        investmentAccount.setId("INV204");
        investmentAccount.setBalance(123.45);
        
        // Buy 10 shares of "MNO" at $10.10 (cost: $101, commission: $10.10, total: $111.10)
        assertTrue(investmentAccount.buyStock("MNO", 10, 10.10));
        assertEquals(12.35, investmentAccount.getBalance(), 0.01);
        
        // Deposit $500 into "INV204"
        assertTrue(investmentAccount.deposit(500));
        assertEquals(512.35, investmentAccount.getBalance(), 0.01);
        
        // Buy 5 shares of "MNO" at $10.10 (cost: $50.50, commission: $5.05, total: $55.55)
        assertTrue(investmentAccount.buyStock("MNO", 5, 10.10));
        assertEquals(456.8, investmentAccount.getBalance(), 0.01);
        
        // Add account to customer
        customer.getAccounts().add(investmentAccount);
        
        // Action: Calculate total value
        double totalValue = investmentAccount.calculateValue();
        
        // Expected Output: $623.45
        assertEquals(623.45, totalValue, 0.01);
    }
}