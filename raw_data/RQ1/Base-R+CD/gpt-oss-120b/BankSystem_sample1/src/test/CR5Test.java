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
        // Setup: Customer "Poe" with investment account "INV200"
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        investmentAccount.setId("INV200");
        investmentAccount.setBalance(5000.0);
        
        // Buy 10 shares "AAPL" at $100
        assertTrue(investmentAccount.buyStock("AAPL", 10, 100.0));
        assertEquals(3900.0, investmentAccount.getBalance(), 0.001);
        
        // Buy 20 shares "MSFT" at $50
        assertTrue(investmentAccount.buyStock("MSFT", 20, 50.0));
        assertEquals(2800.0, investmentAccount.getBalance(), 0.001);
        
        // Add account to customer
        customer.getAccounts().add(investmentAccount);
        
        // Calculate total value
        double totalValue = investmentAccount.calculateValue();
        
        // Expected: $5000 (10*100*1.1 + 20*50*1.1 + 2800 balance)
        // AAPL value: 10 * 100 * 1.1 = 1100
        // MSFT value: 20 * 50 * 1.1 = 1100
        // Cash balance: 2800
        // Total: 1100 + 1100 + 2800 = 5000
        assertEquals(5000.0, totalValue, 0.001);
    }

    @Test
    public void testCase2_emptyAccount() {
        // Setup: Customer "Peter" with new investment account "INV201"
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        investmentAccount.setId("INV201");
        investmentAccount.setBalance(0.0);
        investmentAccount.setTransactions(new ArrayList<>());
        
        // Add account to customer
        customer.getAccounts().add(investmentAccount);
        
        // Calculate total value
        double totalValue = investmentAccount.calculateValue();
        
        // Expected: 0
        assertEquals(0.0, totalValue, 0.001);
    }

    @Test
    public void testCase3_cashOnlyNoStocks() {
        // Setup: Customer 'Alice' with investment account "INV202"
        customer.setName("Alice");
        customer.setAddress("0811 Center St");
        investmentAccount.setId("INV202");
        investmentAccount.setBalance(1000.0);
        investmentAccount.setTransactions(new ArrayList<>());
        
        // Add account to customer
        customer.getAccounts().add(investmentAccount);
        
        // Calculate total value
        double totalValue = investmentAccount.calculateValue();
        
        // Expected: $1000
        assertEquals(1000.0, totalValue, 0.001);
    }

    @Test
    public void testCase4_repeatedPurchasesOfSameStock() {
        // Setup: Customer 'Bide' with investment account "INV203"
        customer.setName("Bide");
        customer.setAddress("0810 Main St");
        investmentAccount.setId("INV203");
        investmentAccount.setBalance(1000.0);
        
        // Buy 5 shares "AAPL" at $100
        assertTrue(investmentAccount.buyStock("AAPL", 5, 100.0));
        assertEquals(450.0, investmentAccount.getBalance(), 0.001);
        
        // Buy 1 shares "AAPL" at $120
        assertTrue(investmentAccount.buyStock("AAPL", 1, 120.0));
        assertEquals(318.0, investmentAccount.getBalance(), 0.001);
        
        // Add account to customer
        customer.getAccounts().add(investmentAccount);
        
        // Calculate total value
        double totalValue = investmentAccount.calculateValue();
        
        // Expected: $1000
        // First purchase: 5 * 100 * 1.1 = 550
        // Second purchase: 1 * 120 * 1.1 = 132
        // Cash balance: 318
        // Total: 550 + 132 + 318 = 1000
        assertEquals(1000.0, totalValue, 0.001);
    }

    @Test
    public void testCase5_precisionCheckWithFractionalShare() {
        // Setup: Customer 'Carli' with investment account "INV204"
        customer.setName("Carli");
        customer.setAddress("0820 Main St");
        investmentAccount.setId("INV204");
        investmentAccount.setBalance(123.45);
        
        // Buy 10 shares of "MNO" at $10.10
        assertTrue(investmentAccount.buyStock("MNO", 10, 10.10));
        assertEquals(12.35, investmentAccount.getBalance(), 0.001);
        
        // Deposit $500 into "INV204"
        assertTrue(investmentAccount.deposit(500.0));
        assertEquals(512.35, investmentAccount.getBalance(), 0.001);
        
        // Buy 5 shares of "MNO" at $10.10
        assertTrue(investmentAccount.buyStock("MNO", 5, 10.10));
        assertEquals(456.8, investmentAccount.getBalance(), 0.001);
        
        // Add account to customer
        customer.getAccounts().add(investmentAccount);
        
        // Calculate total value
        double totalValue = investmentAccount.calculateValue();
        
        // Expected: $623.45
        // First purchase: 10 * 10.10 * 1.1 = 111.10
        // Second purchase: 5 * 10.10 * 1.1 = 55.55
        // Cash balance: 456.80
        // Total: 111.10 + 55.55 + 456.80 = 623.45
        assertEquals(623.45, totalValue, 0.001);
    }
}