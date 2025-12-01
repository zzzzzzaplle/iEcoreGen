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
    public void testCase1_MultipleStockPositions() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a investment account "INV200"
        Customer customer = factory.createCustomer();
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        
        // Create investment account "INV200" with balance $5000
        InvestmentAccount account = factory.createInvestmentAccount();
        account.setId("INV200");
        account.setBalance(5000.0);
        account.setCustomer(customer);
        customer.getAccounts().add(account);
        
        // Buy 10 shares "AAPL" at $100; (The new balance is $3900)
        // Stock cost: 10 * 100 = 1000, Commission: 1000 * 0.1 = 100, Total: 1100
        account.buyStock("AAPL", 10, 100.0);
        
        // Buy 20 shares "MSFT" at $50; (The new balance is $2800)
        // Stock cost: 20 * 50 = 1000, Commission: 1000 * 0.1 = 100, Total: 1100
        account.buyStock("MSFT", 20, 50.0);
        
        // Action: Calculate total value
        double actualValue = account.calculateValue();
        
        // Expected Output: $5000
        // AAPL value: 10 * 100 * 1.1 = 1100
        // MSFT value: 20 * 50 * 1.1 = 1100
        // Balance: 2800
        // Total: 1100 + 1100 + 2800 = 5000
        assertEquals(5000.0, actualValue, 0.001);
    }
    
    @Test
    public void testCase2_EmptyAccount() {
        // SetUp: Customer "Peter" (address "0816 Center St") holds a investment account "INV201"
        Customer customer = factory.createCustomer();
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        
        // New investment account "INV201" balance $0, no trades
        InvestmentAccount account = factory.createInvestmentAccount();
        account.setId("INV201");
        account.setBalance(0.0);
        account.setCustomer(customer);
        customer.getAccounts().add(account);
        
        // Action: Calculate total value
        double actualValue = account.calculateValue();
        
        // Expected Output: 0
        assertEquals(0.0, actualValue, 0.001);
    }
    
    @Test
    public void testCase3_CashOnlyNoStocks() {
        // SetUp: Customer 'Alice' (address "0811 Center St") holds a investment account "INV202"
        Customer customer = factory.createCustomer();
        customer.setName("Alice");
        customer.setAddress("0811 Center St");
        
        // Investment account "INV202" balance $1000, no trades
        InvestmentAccount account = factory.createInvestmentAccount();
        account.setId("INV202");
        account.setBalance(1000.0);
        account.setCustomer(customer);
        customer.getAccounts().add(account);
        
        // Action: Calculate total value
        double actualValue = account.calculateValue();
        
        // Expected Output: $1000
        assertEquals(1000.0, actualValue, 0.001);
    }
    
    @Test
    public void testCase4_RepeatedPurchasesOfSameStock() {
        // SetUp: Customer 'Bide' (address "0810 Main St") holds a investment account "INV203"
        Customer customer = factory.createCustomer();
        customer.setName("Bide");
        customer.setAddress("0810 Main St");
        
        // Investment account "INV203" balance $1000
        InvestmentAccount account = factory.createInvestmentAccount();
        account.setId("INV203");
        account.setBalance(1000.0);
        account.setCustomer(customer);
        customer.getAccounts().add(account);
        
        // Buy 5 shares "AAPL" at $100. (The new balance is $450)
        // Stock cost: 5 * 100 = 500, Commission: 500 * 0.1 = 50, Total: 550
        account.buyStock("AAPL", 5, 100.0);
        
        // Buy 1 shares "AAPL" at $120. (The new balance is $318)
        // Stock cost: 1 * 120 = 120, Commission: 120 * 0.1 = 12, Total: 132
        account.buyStock("AAPL", 1, 120.0);
        
        // Action: Calculate total value
        double actualValue = account.calculateValue();
        
        // Expected Output: $1000
        // First AAPL purchase: 5 * 100 * 1.1 = 550
        // Second AAPL purchase: 1 * 120 * 1.1 = 132
        // Balance: 318
        // Total: 550 + 132 + 318 = 1000
        assertEquals(1000.0, actualValue, 0.001);
    }
    
    @Test
    public void testCase5_PrecisionCheckWithFractionalShare() {
        // SetUp: Customer 'Carli' (address "0820 Main St") holds a investment account "INV204"
        Customer customer = factory.createCustomer();
        customer.setName("Carli");
        customer.setAddress("0820 Main St");
        
        // Investment account "INV204" balance $123.45
        InvestmentAccount account = factory.createInvestmentAccount();
        account.setId("INV204");
        account.setBalance(123.45);
        account.setCustomer(customer);
        customer.getAccounts().add(account);
        
        // Buy 10 shares of "MNO" at $10.10. (The new balance is $12.35)
        // Stock cost: 10 * 10.10 = 101.0, Commission: 101.0 * 0.1 = 10.1, Total: 111.1
        account.buyStock("MNO", 10, 10.10);
        
        // Deposit $500 into "INV204". (The new balance is $512.35)
        account.deposit(500.0);
        
        // Buy 5 shares of "MNO" at $10.10. (The new balance is $456.8)
        // Stock cost: 5 * 10.10 = 50.5, Commission: 50.5 * 0.1 = 5.05, Total: 55.55
        account.buyStock("MNO", 5, 10.10);
        
        // Action: Calculate total value
        double actualValue = account.calculateValue();
        
        // Expected Output: $623.45
        // First MNO purchase: 10 * 10.10 * 1.1 = 111.1
        // Second MNO purchase: 5 * 10.10 * 1.1 = 55.55
        // Balance: 456.8
        // Total: 111.1 + 55.55 + 456.8 = 623.45
        assertEquals(623.45, actualValue, 0.001);
    }
}