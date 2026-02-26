package edu.bank.bank2.test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import edu.bank.BankFactory;
import edu.bank.Customer;
import edu.bank.InvestmentAccount;
import edu.bank.StockTransaction;

public class CR4Test {

    private Customer customer;
    private InvestmentAccount account;

    @Before
    public void setUp() {
        // Create customer Poe with address "0814 Center St"
        customer = BankFactory.eINSTANCE.createCustomer();
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
    }

    @Test
    public void testCase1_sufficientBalancePurchase() {
        // SetUp:
        // - Customer "Poe" (address "0814 Center St") holds a investment account "INV100"
        // - Investment account "INV100" balance $10 000.
        account = BankFactory.eINSTANCE.createInvestmentAccount();
        account.setId("INV100");
        account.setBalance(10000.0);
        customer.getAccounts().add(account);

        // Action:
        // - Buy 100 shares of "ABC" at $50 each (cost $5 000, commission $500).
        boolean result = account.buyStock("ABC", 100, 50.0);

        // Expected Output: True
        assertTrue(result);
        
        // Verify balance is updated correctly: 10000 - (5000 + 500) = 4500
        assertEquals(4500.0, account.getBalance(), 0.01);
        
        // Verify transaction is recorded
        assertEquals(1, account.getTransactions().size());
        StockTransaction transaction = account.getTransactions().get(0);
        assertEquals("ABC", transaction.getStock());
        assertEquals(100, transaction.getQuantity());
        assertEquals(50.0, transaction.getPrice(), 0.01);
        assertEquals(500.0, transaction.getCommission(), 0.01);
    }

    @Test
    public void testCase2_insufficientFunds() {
        // SetUp:
        // - Investment account "INV101" balance $5 000.
        account = BankFactory.eINSTANCE.createInvestmentAccount();
        account.setId("INV101");
        account.setBalance(5000.0);
        customer.getAccounts().add(account);

        // Action:
        // - Attempt to buy 200 shares of "XYZ" at $30 each (commission $600, total needed $6 600).
        boolean result = account.buyStock("XYZ", 200, 30.0);

        // Expected Output: False
        assertFalse(result);
        
        // Verify balance remains unchanged
        assertEquals(5000.0, account.getBalance(), 0.01);
        
        // Verify no transaction is recorded
        assertEquals(0, account.getTransactions().size());
    }

    @Test
    public void testCase3_exactFundsPurchase() {
        // SetUp:
        // - Customer "Poe" (address "0814 Center St") holds a investment account "INV102"
        // - Investment account "INV102" balance $5 500.
        account = BankFactory.eINSTANCE.createInvestmentAccount();
        account.setId("INV102");
        account.setBalance(5500.0);
        customer.getAccounts().add(account);

        // Action:
        // - Buy 100 shares of "DEF" at $50 each (commission $500, total needed $5 500 exactly).
        boolean result = account.buyStock("DEF", 100, 50.0);

        // Expected Output: True
        assertTrue(result);
        
        // Verify balance is updated correctly: 5500 - (5000 + 500) = 0
        assertEquals(0.0, account.getBalance(), 0.01);
        
        // Verify transaction is recorded
        assertEquals(1, account.getTransactions().size());
        StockTransaction transaction = account.getTransactions().get(0);
        assertEquals("DEF", transaction.getStock());
        assertEquals(100, transaction.getQuantity());
        assertEquals(50.0, transaction.getPrice(), 0.01);
        assertEquals(500.0, transaction.getCommission(), 0.01);
    }

    @Test
    public void testCase4_lowValueSingleShare() {
        // SetUp:
        // - Customer "Poe" (address "0814 Center St") holds a investment account "INV103"
        // - Investment account "INV103" balance $100.
        account = BankFactory.eINSTANCE.createInvestmentAccount();
        account.setId("INV103");
        account.setBalance(100.0);
        customer.getAccounts().add(account);

        // Action:
        // - Buy 1 share of "GHI" at $80 (commission $8, total $88).
        boolean result = account.buyStock("GHI", 1, 80.0);

        // Expected Output: True
        assertTrue(result);
        
        // Verify balance is updated correctly: 100 - (80 + 8) = 12
        assertEquals(12.0, account.getBalance(), 0.01);
        
        // Verify transaction is recorded
        assertEquals(1, account.getTransactions().size());
        StockTransaction transaction = account.getTransactions().get(0);
        assertEquals("GHI", transaction.getStock());
        assertEquals(1, transaction.getQuantity());
        assertEquals(80.0, transaction.getPrice(), 0.01);
        assertEquals(8.0, transaction.getCommission(), 0.01);
    }

    @Test
    public void testCase5_secondPurchaseFailsAfterBalanceDrops() {
        // SetUp:
        // - Customer "Poe" (address "0814 Center St") holds a investment account "INV104"
        // - Investment account "INV104" balance $4 000.
        account = BankFactory.eINSTANCE.createInvestmentAccount();
        account.setId("INV104");
        account.setBalance(4000.0);
        customer.getAccounts().add(account);

        // - First purchase already completed: 50 shares of "JKL" at $40 (cost $2 000, commission $200, remaining balance $1 800).
        boolean firstPurchase = account.buyStock("JKL", 50, 40.0);
        assertTrue(firstPurchase);
        assertEquals(1800.0, account.getBalance(), 0.01);

        // Action:
        // - Attempt a second identical purchase requiring $2 200.
        boolean result = account.buyStock("JKL", 50, 40.0);

        // Expected Output: False
        assertFalse(result);
        
        // Verify balance remains at 1800
        assertEquals(1800.0, account.getBalance(), 0.01);
        
        // Verify only one transaction is recorded
        assertEquals(1, account.getTransactions().size());
    }
}