import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class CR2Test {

    private Customer customer;
    private SavingAccount savingAccount;
    private InvestmentAccount investmentAccount;

    @Before
    public void setUp() {
        customer = new Customer();
        savingAccount = new SavingAccount();
        investmentAccount = new InvestmentAccount();
    }

    @Test
    public void testCase1_depositPositiveAmountToSavings() {
        // SetUp: Customer "Doli" (address "123 Main St") holds savings account "SAV004", balance $1 000, interest rate 3.65 %.
        customer.setName("Doli");
        customer.setAddress("123 Main St");
        savingAccount.setId("SAV004");
        savingAccount.setBalance(1000.0);
        savingAccount.setInterestRate(0.0365);
        customer.getAccounts().add(savingAccount);

        // Action: Deposit $500 into "SAV004".
        boolean result = savingAccount.deposit(500.0);

        // Expected Output: 1 500      // new balance
        assertTrue(result);
        assertEquals(1500.0, savingAccount.getBalance(), 0.001);
    }

    @Test
    public void testCase2_depositZeroAmount() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds savings account "SAV005", balance $200, interest rate 3.65 %.
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        savingAccount.setId("SAV005");
        savingAccount.setBalance(200.0);
        savingAccount.setInterestRate(0.0365);
        customer.getAccounts().add(savingAccount);

        // Action: Attempt to deposit $0.
        boolean result = savingAccount.deposit(0.0);

        // Expected Output: False, the balance of 'SAV005' is still $200.
        assertFalse(result);
        assertEquals(200.0, savingAccount.getBalance(), 0.001);
    }

    @Test
    public void testCase3_depositNegativeAmount() {
        // SetUp: Investment account "INV001" holds balance $300.
        investmentAccount.setId("INV001");
        investmentAccount.setBalance(300.0);
        customer.getAccounts().add(investmentAccount);

        // Action: Attempt to deposit –$100.
        boolean result = investmentAccount.deposit(-100.0);

        // Expected Output: False
        assertFalse(result);
    }

    @Test
    public void testCase4_depositExactlyAtSingleDepositLimit() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds an investment account "INV002" holds balance $0.
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        investmentAccount.setId("INV002");
        investmentAccount.setBalance(0.0);
        customer.getAccounts().add(investmentAccount);

        // Action: Deposit $1 000 000.
        boolean result = investmentAccount.deposit(1000000.0);

        // Expected Output: 1 000 000
        assertTrue(result);
        assertEquals(1000000.0, investmentAccount.getBalance(), 0.001);
    }

    @Test
    public void testCase5_depositExceedsLimitByOneDollar() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds a savings account "SAV006" holds balance $50, interest rate 3.65 %.
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        savingAccount.setId("SAV006");
        savingAccount.setBalance(50.0);
        savingAccount.setInterestRate(0.0365);
        customer.getAccounts().add(savingAccount);

        // Action: Attempt to deposit $1 000 001.
        boolean result = savingAccount.deposit(1000001.0);

        // Expected Output: False
        assertFalse(result);
    }
}