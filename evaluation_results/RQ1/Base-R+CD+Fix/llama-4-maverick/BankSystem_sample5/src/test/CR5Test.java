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
        // SetUp: Customer "Poe" holds investment account "INV200"
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        investmentAccount.setId("INV200");
        
        // Investment account "INV200" balance $5000
        investmentAccount.setBalance(5000);
        
        // Buy 10 shares "AAPL" at $100 (10% commission = $100)
        assertTrue(investmentAccount.buyStock("AAPL", 10, 100));
        // Balance should be $5000 - $1000 - $100 = $3900
        
        // Buy 20 shares "MSFT" at $50 (10% commission = $100)
        assertTrue(investmentAccount.buyStock("MSFT", 20, 50));
        // Balance should be $3900 - $1000 - $100 = $2800
        
        customer.getAccounts().add(investmentAccount);
        
        // Action: Calculate total value
        double totalValue = investmentAccount.calculateValue();
        
        // Expected Output: $5000
        // Calculation: Balance ($2800) + AAPL value (10 * $100 * 1.1) + MSFT value (20 * $50 * 1.1)
        // = $2800 + $1100 + $1100 = $5000
        assertEquals(5000.0, totalValue, 0.01);
    }
    
    @Test
    public void testCase2_emptyAccount() {
        // SetUp: Customer "Peter" holds investment account "INV201"
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        investmentAccount.setId("INV201");
        
        // New investment account "INV201" balance $0, no trades
        investmentAccount.setBalance(0);
        
        customer.getAccounts().add(investmentAccount);
        
        // Action: Calculate total value
        double totalValue = investmentAccount.calculateValue();
        
        // Expected Output: 0
        assertEquals(0.0, totalValue, 0.01);
    }
    
    @Test
    public void testCase3_cashOnlyNoStocks() {
        // SetUp: Customer 'Alice' holds investment account "INV202"
        customer.setName("Alice");
        customer.setAddress("0811 Center St");
        investmentAccount.setId("INV202");
        
        // Investment account "INV202" balance $1000, no trades
        investmentAccount.setBalance(1000);
        
        customer.getAccounts().add(investmentAccount);
        
        // Action: Calculate total value
        double totalValue = investmentAccount.calculateValue();
        
        // Expected Output: $1000
        assertEquals(1000.0, totalValue, 0.01);
    }
    
    @Test
    public void testCase4_repeatedPurchasesOfSameStock() {
        // SetUp: Customer 'Bide' holds investment account "INV203"
        customer.setName("Bide");
        customer.setAddress("0810 Main St");
        investmentAccount.setId("INV203");
        
        // Investment account "INV203" balance $1000
        investmentAccount.setBalance(1000);
        
        // Buy 5 shares "AAPL" at $100 (10% commission = $50)
        assertTrue(investmentAccount.buyStock("AAPL", 5, 100));
        // Balance should be $1000 - $500 - $50 = $450
        
        // Buy 1 shares "AAPL" at $120 (10% commission = $12)
        assertTrue(investmentAccount.buyStock("AAPL", 1, 120));
        // Balance should be $450 - $120 - $12 = $318
        
        customer.getAccounts().add(investmentAccount);
        
        // Action: Calculate total value
        double totalValue = investmentAccount.calculateValue();
        
        // Expected Output: $1000
        // Calculation: Balance ($318) + AAPL value ((5 * $100 * 1.1) + (1 * $120 * 1.1))
        // = $318 + $550 + $132 = $1000
        assertEquals(1000.0, totalValue, 0.01);
    }
    
    @Test
    public void testCase5_precisionCheckWithFractionalShare() {
        // SetUp: Customer 'Carli' holds investment account "INV204"
        customer.setName("Carli");
        customer.setAddress("0820 Main St");
        investmentAccount.setId("INV204");
        
        // Investment account "INV204" balance $123.45
        investmentAccount.setBalance(123.45);
        
        // Buy 10 shares of "MNO" at $10.10 (10% commission = $10.10)
        assertTrue(investmentAccount.buyStock("MNO", 10, 10.10));
        // Balance should be $123.45 - $101 - $10.10 = $12.35
        
        // Deposit $500 into "INV204"
        assertTrue(investmentAccount.deposit(500));
        // Balance should be $12.35 + $500 = $512.35
        
        // Buy 5 shares of "MNO" at $10.10 (10% commission = $5.05)
        assertTrue(investmentAccount.buyStock("MNO", 5, 10.10));
        // Balance should be $512.35 - $50.50 - $5.05 = $456.80
        
        customer.getAccounts().add(investmentAccount);
        
        // Action: Calculate total value
        double totalValue = investmentAccount.calculateValue();
        
        // Expected Output: $623.45
        // Calculation: Balance ($456.80) + MNO value ((10 * $10.10 * 1.1) + (5 * $10.10 * 1.1))
        // = $456.80 + $111.10 + $55.55 = $623.45
        assertEquals(623.45, totalValue, 0.01);
    }
}