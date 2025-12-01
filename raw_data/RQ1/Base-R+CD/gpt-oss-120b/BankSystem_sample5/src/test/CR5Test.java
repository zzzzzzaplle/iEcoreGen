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
        // SetUp:
        // - Customer "Poe" (address "0814 Center St") holds a investment account "INV200"
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        investmentAccount.setId("INV200");
        investmentAccount.setBalance(5000.0);
        customer.getAccounts().add(investmentAccount);

        // - Buy 10 shares "AAPL" at $100; (The new balance is $3900)
        assertTrue(investmentAccount.buyStock("AAPL", 10, 100.0));

        // - Buy 20 shares "MSFT" at $50; (The new balance is $2800)
        assertTrue(investmentAccount.buyStock("MSFT", 20, 50.0));

        // Action:
        // - Calculate total value.
        double totalValue = investmentAccount.calculateValue();

        // Expected Output: $5 000
        assertEquals(5000.0, totalValue, 0.01);
    }

    @Test
    public void testCase2_emptyAccount() {
        // SetUp:
        // - Customer "Peter" (address "0816 Center St") holds a investment account "INV201"
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        investmentAccount.setId("INV201");
        investmentAccount.setBalance(0.0);
        customer.getAccounts().add(investmentAccount);

        // - New investment account "INV201" balance $0, no trades.

        // Action:
        // - Calculate total value.
        double totalValue = investmentAccount.calculateValue();

        // Expected Output: 0
        assertEquals(0.0, totalValue, 0.01);
    }

    @Test
    public void testCase3_cashOnlyNoStocks() {
        // SetUp:
        // - Customer 'Alice' (address "0811 Center St") holds a investment account "INV202"
        customer.setName("Alice");
        customer.setAddress("0811 Center St");
        investmentAccount.setId("INV202");
        investmentAccount.setBalance(1000.0);
        customer.getAccounts().add(investmentAccount);

        // - Investment account "INV202" balance $1 000, no trades.

        // Action:
        // - Calculate total value.
        double totalValue = investmentAccount.calculateValue();

        // Expected Output: $1 000
        assertEquals(1000.0, totalValue, 0.01);
    }

    @Test
    public void testCase4_repeatedPurchasesOfSameStock() {
        // SetUp:
        // - Customer 'Bide' (address "0810 Main St") holds a investment account "INV203"
        customer.setName("Bide");
        customer.setAddress("0810 Main St");
        investmentAccount.setId("INV203");
        investmentAccount.setBalance(1000.0);
        customer.getAccounts().add(investmentAccount);

        // - Buy 5 shares "AAPL" at $100. (The new balance is $450)
        assertTrue(investmentAccount.buyStock("AAPL", 5, 100.0));

        // - Buy 1 shares "AAPL" at $120. (The new balance is $318)
        assertTrue(investmentAccount.buyStock("AAPL", 1, 120.0));

        // Action:
        // - Calculate total value.
        double totalValue = investmentAccount.calculateValue();

        // Expected Output: $ 1 000
        assertEquals(1000.0, totalValue, 0.01);
    }

    @Test
    public void testCase5_precisionCheckWithFractionalShare() {
        // SetUp:
        // - Customer 'Carli' (address "0820 Main St") holds a investment account "INV204"
        customer.setName("Carli");
        customer.setAddress("0820 Main St");
        investmentAccount.setId("INV204");
        investmentAccount.setBalance(123.45);
        customer.getAccounts().add(investmentAccount);

        // - Buy 10 shares of "MNO" at $10.10. (The new balance is $12.35)
        assertTrue(investmentAccount.buyStock("MNO", 10, 10.10));

        // - Deposit $500 into "INV204". (The new balance is $512.35)
        assertTrue(investmentAccount.deposit(500.0));

        // - Buy 5 shares of "MNO" at $10.10. (The new balance is $456.8)
        assertTrue(investmentAccount.buyStock("MNO", 5, 10.10));

        // Action:
        // - Calculate total value.
        double totalValue = investmentAccount.calculateValue();

        // Expected Output: $623.45
        assertEquals(623.45, totalValue, 0.01);
    }
}