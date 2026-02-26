package edu.bank.bank3.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.bank.BankFactory;
import edu.bank.Customer;
import edu.bank.InvestmentAccount;
import edu.bank.StockTransaction;

public class CR5Test {
    
    private BankFactory factory;
    
    @Before
    public void setUp() {
        factory = BankFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_multipleStockPositions() {
        // SetUp:
        // - Customer "Poe" (address "0814 Center St") holds a investment account "INV200"
        Customer customer = factory.createCustomer();
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        
        // - Investment account "INV200" balance $5000.
        InvestmentAccount account = factory.createInvestmentAccount();
        account.setId("INV200");
        account.setBalance(5000);
        account.setCustomer(customer);
        customer.getAccounts().add(account);
        
        // - Buy 10 shares "AAPL" at $100; (The new balance is $3900)
        assertTrue(account.buyStock("AAPL", 10, 100));
        assertEquals(3900.0, account.getBalance(), 0.01);
        
        // - Buy 20 shares "MSFT" at $50; (The new balance is $2800)
        assertTrue(account.buyStock("MSFT", 20, 50));
        assertEquals(2800.0, account.getBalance(), 0.01);
        
        // Action:
        // - Calculate total value.
        double totalValue = account.calculateValue();
        
        // Expected Output: $5 000
        assertEquals(5000.0, totalValue, 0.01);
    }
    
    @Test
    public void testCase2_emptyAccount() {
        // SetUp:
        // - Customer "Peter" (address "0816 Center St") holds a investment account "INV201"
        Customer customer = factory.createCustomer();
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        
        // - New investment account "INV201" balance $0, no trades.
        InvestmentAccount account = factory.createInvestmentAccount();
        account.setId("INV201");
        account.setBalance(0);
        account.setCustomer(customer);
        customer.getAccounts().add(account);
        
        // Action:
        // - Calculate total value.
        double totalValue = account.calculateValue();
        
        // Expected Output: 0
        assertEquals(0.0, totalValue, 0.01);
    }
    
    @Test
    public void testCase3_cashOnlyNoStocks() {
        // SetUp:
        // - Customer 'Alice' (address "0811 Center St") holds a investment account "INV202"
        Customer customer = factory.createCustomer();
        customer.setName("Alice");
        customer.setAddress("0811 Center St");
        
        // - Investment account "INV202" balance $1 000, no trades.
        InvestmentAccount account = factory.createInvestmentAccount();
        account.setId("INV202");
        account.setBalance(1000);
        account.setCustomer(customer);
        customer.getAccounts().add(account);
        
        // Action:
        // - Calculate total value.
        double totalValue = account.calculateValue();
        
        // Expected Output: $1 000
        assertEquals(1000.0, totalValue, 0.01);
    }
    
    @Test
    public void testCase4_repeatedPurchasesOfSameStock() {
        // SetUp:
        // - Customer 'Bide' (address "0810 Main St") holds a investment account "INV203"
        Customer customer = factory.createCustomer();
        customer.setName("Bide");
        customer.setAddress("0810 Main St");
        
        // - Investment account "INV203" balance $1 000.
        InvestmentAccount account = factory.createInvestmentAccount();
        account.setId("INV203");
        account.setBalance(1000);
        account.setCustomer(customer);
        customer.getAccounts().add(account);
        
        // - Buy 5 shares "AAPL" at $100. (The new balance is $450)
        assertTrue(account.buyStock("AAPL", 5, 100));
        assertEquals(450.0, account.getBalance(), 0.01);
        
        // - Buy 1 shares "AAPL" at $120. (The new balance is $318)
        assertTrue(account.buyStock("AAPL", 1, 120));
        assertEquals(318.0, account.getBalance(), 0.01);
        
        // Action:
        // - Calculate total value.
        double totalValue = account.calculateValue();
        
        // Expected Output: $ 1 000
        assertEquals(1000.0, totalValue, 0.01);
    }
    
    @Test
    public void testCase5_precisionCheckWithFractionalShare() {
        // SetUp:
        // - Customer 'Carli' (address "0820 Main St") holds a investment account "INV204"
        Customer customer = factory.createCustomer();
        customer.setName("Carli");
        customer.setAddress("0820 Main St");
        
        // - Investment account "INV204" balance $123.45.
        InvestmentAccount account = factory.createInvestmentAccount();
        account.setId("INV204");
        account.setBalance(123.45);
        account.setCustomer(customer);
        customer.getAccounts().add(account);
        
        // - Buy 10 shares of "MNO" at $10.10. (The new balance is $12.35)
        assertTrue(account.buyStock("MNO", 10, 10.10));
        assertEquals(12.35, account.getBalance(), 0.01);
        
        // - Deposit $500 into "INV204". (The new balance is $512.35)
        assertTrue(account.deposit(500));
        assertEquals(512.35, account.getBalance(), 0.01);
        
        // - Buy 5 shares of "MNO" at $10.10. (The new balance is $456.8)
        assertTrue(account.buyStock("MNO", 5, 10.10));
        assertEquals(456.8, account.getBalance(), 0.01);
        
        // Action:
        // - Calculate total value.
        double totalValue = account.calculateValue();
        
        // Expected Output: $623.45
        assertEquals(623.45, totalValue, 0.01);
    }
}