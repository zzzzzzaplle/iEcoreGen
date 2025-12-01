package edu.bank.bank2.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.bank.BankFactory;
import edu.bank.Customer;
import edu.bank.InvestmentAccount;

public class CR5Test {
    
    private BankFactory factory;
    
    @Before
    public void setUp() {
        factory = BankFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_multipleStockPositions() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a investment account "INV200"
        Customer customer = factory.createCustomer();
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        
        InvestmentAccount account = factory.createInvestmentAccount();
        account.setId("INV200");
        account.setBalance(5000); // Initial balance $5000
        
        // Buy 10 shares "AAPL" at $100; (The new balance is $3900)
        // Cost = 10 * 100 = 1000, Commission = 100, Total = 1100
        // New balance = 5000 - 1100 = 3900
        boolean result1 = account.buyStock("AAPL", 10, 100);
        assertTrue(result1);
        assertEquals(3900.0, account.getBalance(), 0.01);
        
        // Buy 20 shares "MSFT" at $50; (The new balance is $2800)
        // Cost = 20 * 50 = 1000, Commission = 100, Total = 1100
        // New balance = 3900 - 1100 = 2800
        boolean result2 = account.buyStock("MSFT", 20, 50);
        assertTrue(result2);
        assertEquals(2800.0, account.getBalance(), 0.01);
        
        // Action: Calculate total value
        // Account balance: $2800
        // AAPL value: 10 * 1.1 * 100 = 1100
        // MSFT value: 20 * 1.1 * 50 = 1100
        // Total value = 2800 + 1100 + 1100 = 5000
        double totalValue = account.calculateValue();
        
        // Expected Output: $5 000
        assertEquals(5000.0, totalValue, 0.01);
    }
    
    @Test
    public void testCase2_emptyAccount() {
        // SetUp: Customer "Peter" (address "0816 Center St") holds a investment account "INV201"
        Customer customer = factory.createCustomer();
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        
        InvestmentAccount account = factory.createInvestmentAccount();
        account.setId("INV201");
        account.setBalance(0); // New investment account "INV201" balance $0, no trades
        
        // Action: Calculate total value
        double totalValue = account.calculateValue();
        
        // Expected Output: 0
        assertEquals(0.0, totalValue, 0.01);
    }
    
    @Test
    public void testCase3_cashOnlyNoStocks() {
        // SetUp: Customer 'Alice' (address "0811 Center St") holds a investment account "INV202"
        Customer customer = factory.createCustomer();
        customer.setName("Alice");
        customer.setAddress("0811 Center St");
        
        InvestmentAccount account = factory.createInvestmentAccount();
        account.setId("INV202");
        account.setBalance(1000); // Investment account "INV202" balance $1 000, no trades
        
        // Action: Calculate total value
        double totalValue = account.calculateValue();
        
        // Expected Output: $1 000
        assertEquals(1000.0, totalValue, 0.01);
    }
    
    @Test
    public void testCase4_repeatedPurchasesSameStock() {
        // SetUp: Customer 'Bide' (address "0810 Main St") holds a investment account "INV203"
        Customer customer = factory.createCustomer();
        customer.setName("Bide");
        customer.setAddress("0810 Main St");
        
        InvestmentAccount account = factory.createInvestmentAccount();
        account.setId("INV203");
        account.setBalance(1000); // Investment account "INV203" balance $1 000
        
        // Buy 5 shares "AAPL" at $100. (The new balance is $450)
        // Cost = 5 * 100 = 500, Commission = 50, Total = 550
        // New balance = 1000 - 550 = 450
        boolean result1 = account.buyStock("AAPL", 5, 100);
        assertTrue(result1);
        assertEquals(450.0, account.getBalance(), 0.01);
        
        // Buy 1 shares "AAPL" at $120. (The new balance is $318)
        // Cost = 1 * 120 = 120, Commission = 12, Total = 132
        // New balance = 450 - 132 = 318
        boolean result2 = account.buyStock("AAPL", 1, 120);
        assertTrue(result2);
        assertEquals(318.0, account.getBalance(), 0.01);
        
        // Action: Calculate total value
        // Account balance: $318
        // First AAPL purchase value: 5 * 1.1 * 100 = 550
        // Second AAPL purchase value: 1 * 1.1 * 120 = 132
        // Total value = 318 + 550 + 132 = 1000
        double totalValue = account.calculateValue();
        
        // Expected Output: $ 1 000
        assertEquals(1000.0, totalValue, 0.01);
    }
    
    @Test
    public void testCase5_precisionCheckFractionalShare() {
        // SetUp: Customer 'Carli' (address "0820 Main St") holds a investment account "INV204"
        Customer customer = factory.createCustomer();
        customer.setName("Carli");
        customer.setAddress("0820 Main St");
        
        InvestmentAccount account = factory.createInvestmentAccount();
        account.setId("INV204");
        account.setBalance(123.45); // Investment account "INV204" balance $123.45
        
        // Buy 10 shares of "MNO" at $10.10. (The new balance is $12.35)
        // Cost = 10 * 10.10 = 101, Commission = 10.1, Total = 111.1
        // New balance = 123.45 - 111.1 = 12.35
        boolean result1 = account.buyStock("MNO", 10, 10.10);
        assertTrue(result1);
        assertEquals(12.35, account.getBalance(), 0.01);
        
        // Deposit $500 into "INV204". (The new balance is $512.35)
        boolean depositResult = account.deposit(500);
        assertTrue(depositResult);
        assertEquals(512.35, account.getBalance(), 0.01);
        
        // Buy 5 shares of "MNO" at $10.10. (The new balance is $456.8)
        // Cost = 5 * 10.10 = 50.5, Commission = 5.05, Total = 55.55
        // New balance = 512.35 - 55.55 = 456.8
        boolean result2 = account.buyStock("MNO", 5, 10.10);
        assertTrue(result2);
        assertEquals(456.8, account.getBalance(), 0.01);
        
        // Action: Calculate total value
        // Account balance: $456.8
        // First MNO purchase value: 10 * 1.1 * 10.10 = 111.1
        // Second MNO purchase value: 5 * 1.1 * 10.10 = 55.55
        // Total value = 456.8 + 111.1 + 55.55 = 623.45
        double totalValue = account.calculateValue();
        
        // Expected Output: $623.45
        assertEquals(623.45, totalValue, 0.01);
    }
}