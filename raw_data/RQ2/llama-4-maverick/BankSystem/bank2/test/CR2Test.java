package edu.bank.bank2.test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import edu.bank.BankFactory;
import edu.bank.Customer;
import edu.bank.SavingAccount;
import edu.bank.InvestmentAccount;

public class CR2Test {

    private BankFactory factory;

    @Before
    public void setUp() {
        factory = BankFactory.eINSTANCE;
    }

    @Test
    public void testCase1_depositPositiveAmountToSavings() {
        // SetUp: Customer "Doli" with savings account "SAV004", balance $1000, interest rate 3.65%
        Customer customer = factory.createCustomer();
        customer.setName("Doli");
        customer.setAddress("123 Main St");

        SavingAccount savingAccount = factory.createSavingAccount();
        savingAccount.setId("SAV004");
        savingAccount.setBalance(1000.0);
        savingAccount.setInterestRate(3.65);

        customer.getAccounts().add(savingAccount);

        // Action: Deposit $500 into "SAV004"
        boolean result = savingAccount.deposit(500.0);

        // Expected Output: new balance should be 1500, operation should succeed
        assertTrue(result);
        assertEquals(1500.0, savingAccount.getBalance(), 0.001);
    }

    @Test
    public void testCase2_depositZeroAmount() {
        // SetUp: Customer "Alie" with savings account "SAV005", balance $200, interest rate 3.65%
        Customer customer = factory.createCustomer();
        customer.setName("Alie");
        customer.setAddress("0812 Center St");

        SavingAccount savingAccount = factory.createSavingAccount();
        savingAccount.setId("SAV005");
        savingAccount.setBalance(200.0);
        savingAccount.setInterestRate(3.65);

        customer.getAccounts().add(savingAccount);

        // Action: Attempt to deposit $0
        boolean result = savingAccount.deposit(0.0);

        // Expected Output: False, the balance of 'SAV005' is still $200
        assertFalse(result);
        assertEquals(200.0, savingAccount.getBalance(), 0.001);
    }

    @Test
    public void testCase3_depositNegativeAmount() {
        // SetUp: Investment account "INV001" with balance $300
        InvestmentAccount investmentAccount = factory.createInvestmentAccount();
        investmentAccount.setId("INV001");
        investmentAccount.setBalance(300.0);

        // Action: Attempt to deposit -$100
        boolean result = investmentAccount.deposit(-100.0);

        // Expected Output: False
        assertFalse(result);
    }

    @Test
    public void testCase4_depositExactlyAtSingleDepositLimit() {
        // SetUp: Customer "Alie" with investment account "INV002" with balance $0
        Customer customer = factory.createCustomer();
        customer.setName("Alie");
        customer.setAddress("0812 Center St");

        InvestmentAccount investmentAccount = factory.createInvestmentAccount();
        investmentAccount.setId("INV002");
        investmentAccount.setBalance(0.0);

        customer.getAccounts().add(investmentAccount);

        // Action: Deposit $1,000,000
        boolean result = investmentAccount.deposit(1000000.0);

        // Expected Output: balance should be 1,000,000
        assertTrue(result);
        assertEquals(1000000.0, investmentAccount.getBalance(), 0.001);
    }

    @Test
    public void testCase5_depositExceedsLimitByOneDollar() {
        // SetUp: Customer "Alie" with savings account "SAV006", balance $50, interest rate 3.65%
        Customer customer = factory.createCustomer();
        customer.setName("Alie");
        customer.setAddress("0812 Center St");

        SavingAccount savingAccount = factory.createSavingAccount();
        savingAccount.setId("SAV006");
        savingAccount.setBalance(50.0);
        savingAccount.setInterestRate(3.65);

        customer.getAccounts().add(savingAccount);

        // Action: Attempt to deposit $1,000,001
        boolean result = savingAccount.deposit(1000001.0);

        // Expected Output: False
        assertFalse(result);
    }
}