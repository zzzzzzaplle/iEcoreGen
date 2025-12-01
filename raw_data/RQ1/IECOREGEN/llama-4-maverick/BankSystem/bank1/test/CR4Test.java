package edu.bank.bank1.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.bank.BankFactory;
import edu.bank.Customer;
import edu.bank.InvestmentAccount;

public class CR4Test {
    
    private Customer customer;
    private InvestmentAccount account;
    
    @Before
    public void setUp() {
        // Create customer "Poe" with address "0814 Center St"
        customer = BankFactory.eINSTANCE.createCustomer();
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
    }
    
    @Test
    public void testCase1_sufficientBalancePurchase() {
        // SetUp: Customer "Poe" holds investment account "INV100" with balance $10,000
        account = BankFactory.eINSTANCE.createInvestmentAccount();
        account.setId("INV100");
        account.setBalance(10000.0);
        account.setCustomer(customer);
        customer.getAccounts().add(account);
        
        // Action: Buy 100 shares of "ABC" at $50 each (cost $5,000, commission $500)
        boolean result = account.buyStock("ABC", 100, 50.0);
        
        // Expected Output: True
        assertTrue(result);
        
        // Verify balance was updated correctly: 10000 - 5000 - 500 = 4500
        assertEquals(4500.0, account.getBalance(), 0.01);
        
        // Verify transaction was recorded
        assertEquals(1, account.getTransactions().size());
        assertEquals("ABC", account.getTransactions().get(0).getStock());
        assertEquals(100, account.getTransactions().get(0).getQuantity());
        assertEquals(50.0, account.getTransactions().get(0).getPrice(), 0.01);
        assertEquals(500.0, account.getTransactions().get(0).getCommission(), 0.01);
    }
    
    @Test
    public void testCase2_insufficientFunds() {
        // SetUp: Investment account "INV101" balance $5,000
        account = BankFactory.eINSTANCE.createInvestmentAccount();
        account.setId("INV101");
        account.setBalance(5000.0);
        account.setCustomer(customer);
        customer.getAccounts().add(account);
        
        // Action: Attempt to buy 200 shares of "XYZ" at $30 each (cost $6,000, commission $600, total needed $6,600)
        boolean result = account.buyStock("XYZ", 200, 30.0);
        
        // Expected Output: False
        assertFalse(result);
        
        // Verify balance remains unchanged
        assertEquals(5000.0, account.getBalance(), 0.01);
        
        // Verify no transaction was recorded
        assertEquals(0, account.getTransactions().size());
    }
    
    @Test
    public void testCase3_exactFundsPurchase() {
        // SetUp: Customer "Poe" holds investment account "INV102" with balance $5,500
        account = BankFactory.eINSTANCE.createInvestmentAccount();
        account.setId("INV102");
        account.setBalance(5500.0);
        account.setCustomer(customer);
        customer.getAccounts().add(account);
        
        // Action: Buy 100 shares of "DEF" at $50 each (cost $5,000, commission $500, total needed $5,500 exactly)
        boolean result = account.buyStock("DEF", 100, 50.0);
        
        // Expected Output: True
        assertTrue(result);
        
        // Verify balance was updated to zero
        assertEquals(0.0, account.getBalance(), 0.01);
        
        // Verify transaction was recorded
        assertEquals(1, account.getTransactions().size());
        assertEquals("DEF", account.getTransactions().get(0).getStock());
        assertEquals(100, account.getTransactions().get(0).getQuantity());
        assertEquals(50.0, account.getTransactions().get(0).getPrice(), 0.01);
        assertEquals(500.0, account.getTransactions().get(0).getCommission(), 0.01);
    }
    
    @Test
    public void testCase4_lowValueSingleShare() {
        // SetUp: Customer "Poe" holds investment account "INV103" with balance $100
        account = BankFactory.eINSTANCE.createInvestmentAccount();
        account.setId("INV103");
        account.setBalance(100.0);
        account.setCustomer(customer);
        customer.getAccounts().add(account);
        
        // Action: Buy 1 share of "GHI" at $80 (cost $80, commission $8, total $88)
        boolean result = account.buyStock("GHI", 1, 80.0);
        
        // Expected Output: True
        assertTrue(result);
        
        // Verify balance was updated correctly: 100 - 80 - 8 = 12
        assertEquals(12.0, account.getBalance(), 0.01);
        
        // Verify transaction was recorded
        assertEquals(1, account.getTransactions().size());
        assertEquals("GHI", account.getTransactions().get(0).getStock());
        assertEquals(1, account.getTransactions().get(0).getQuantity());
        assertEquals(80.0, account.getTransactions().get(0).getPrice(), 0.01);
        assertEquals(8.0, account.getTransactions().get(0).getCommission(), 0.01);
    }
    
    @Test
    public void testCase5_secondPurchaseFailsAfterBalanceDrops() {
        // SetUp: Customer "Poe" holds investment account "INV104" with balance $4,000
        account = BankFactory.eINSTANCE.createInvestmentAccount();
        account.setId("INV104");
        account.setBalance(4000.0);
        account.setCustomer(customer);
        customer.getAccounts().add(account);
        
        // First purchase: 50 shares of "JKL" at $40 (cost $2,000, commission $200, remaining balance $1,800)
        boolean firstResult = account.buyStock("JKL", 50, 40.0);
        assertTrue(firstResult);
        assertEquals(1800.0, account.getBalance(), 0.01);
        assertEquals(1, account.getTransactions().size());
        
        // Action: Attempt a second identical purchase requiring $2,200
        boolean secondResult = account.buyStock("JKL", 50, 40.0);
        
        // Expected Output: False
        assertFalse(secondResult);
        
        // Verify balance remains at $1,800
        assertEquals(1800.0, account.getBalance(), 0.01);
        
        // Verify only one transaction exists
        assertEquals(1, account.getTransactions().size());
    }
}