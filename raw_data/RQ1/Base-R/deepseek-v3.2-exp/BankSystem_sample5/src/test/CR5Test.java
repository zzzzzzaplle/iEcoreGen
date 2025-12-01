import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    private BankSystem bankSystem;
    
    @Before
    public void setUp() {
        bankSystem = new BankSystem();
    }
    
    @Test
    public void testCase1_MultipleStockPositions() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a investment account "INV200"
        bankSystem.addCustomer("Poe", "0814 Center St");
        Customer customer = bankSystem.findCustomer("Poe", "0814 Center St");
        customer.addAccount("INV200", "INVESTMENT");
        InvestmentAccount account = (InvestmentAccount) customer.getAccountById("INV200");
        
        // SetUp: Investment account "INV200" balance $5000
        account.setBalance(5000.0);
        
        // SetUp: Buy 10 shares "AAPL" at $100; (The new balance is $3900)
        account.buyStock("AAPL", 10, 100.0);
        
        // SetUp: Buy 20 shares "MSFT" at $50; (The new balance is $2800)
        account.buyStock("MSFT", 20, 50.0);
        
        // Action: Calculate total value
        double result = account.calculateAccountValue();
        
        // Expected Output: $5 000  
        // Calculation: 
        // AAPL: 10 shares * $100 * 1.1 = $1,100
        // MSFT: 20 shares * $50 * 1.1 = $1,100
        // Stock value total: $2,200
        // Account balance: $2,800
        // Total: $5,000
        assertEquals(5000.0, result, 0.001);
    }
    
    @Test
    public void testCase2_EmptyAccount() {
        // SetUp: Customer "Peter" (address "0816 Center St") holds a investment account "INV201"
        bankSystem.addCustomer("Peter", "0816 Center St");
        Customer customer = bankSystem.findCustomer("Peter", "0816 Center St");
        customer.addAccount("INV201", "INVESTMENT");
        InvestmentAccount account = (InvestmentAccount) customer.getAccountById("INV201");
        
        // SetUp: New investment account "INV201" balance $0, no trades
        account.setBalance(0.0);
        
        // Action: Calculate total value
        double result = account.calculateAccountValue();
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_CashOnlyNoStocks() {
        // SetUp: Customer 'Alice' (address "0811 Center St") holds a investment account "INV202"
        bankSystem.addCustomer("Alice", "0811 Center St");
        Customer customer = bankSystem.findCustomer("Alice", "0811 Center St");
        customer.addAccount("INV202", "INVESTMENT");
        InvestmentAccount account = (InvestmentAccount) customer.getAccountById("INV202");
        
        // SetUp: Investment account "INV202" balance $1 000, no trades
        account.setBalance(1000.0);
        
        // Action: Calculate total value
        double result = account.calculateAccountValue();
        
        // Expected Output: $1 000
        assertEquals(1000.0, result, 0.001);
    }
    
    @Test
    public void testCase4_RepeatedPurchasesOfSameStock() {
        // SetUp: Customer 'Bide' (address "0810 Main St") holds a investment account "INV203"
        bankSystem.addCustomer("Bide", "0810 Main St");
        Customer customer = bankSystem.findCustomer("Bide", "0810 Main St");
        customer.addAccount("INV203", "INVESTMENT");
        InvestmentAccount account = (InvestmentAccount) customer.getAccountById("INV203");
        
        // SetUp: Investment account "INV203" balance $1 000
        account.setBalance(1000.0);
        
        // SetUp: Buy 5 shares "AAPL" at $100. (The new balance is $450)
        account.buyStock("AAPL", 5, 100.0);
        
        // SetUp: Buy 1 shares "AAPL" at $120. (The new balance is $318)
        account.buyStock("AAPL", 1, 120.0);
        
        // Action: Calculate total value
        double result = account.calculateAccountValue();
        
        // Expected Output: $ 1 000
        // Calculation:
        // Transaction 1: 5 shares * $100 = $500, commission $50, total $550
        // Transaction 2: 1 share * $120 = $120, commission $12, total $132
        // Total spent on stocks: $682
        // Current balance: $318
        // AAPL total shares: 6 shares
        // Current AAPL price: average price? Wait, specification says "1.1 times its purchase price" per transaction
        // Transaction 1 value: 5 shares * $100 * 1.1 = $550
        // Transaction 2 value: 1 share * $120 * 1.1 = $132
        // Total stock value: $682
        // Account total: $318 + $682 = $1,000
        assertEquals(1000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_PrecisionCheckWithFractionalShare() {
        // SetUp: Customer 'Carli' (address "0820 Main St") holds a investment account "INV204"
        bankSystem.addCustomer("Carli", "0820 Main St");
        Customer customer = bankSystem.findCustomer("Carli", "0820 Main St");
        customer.addAccount("INV204", "INVESTMENT");
        InvestmentAccount account = (InvestmentAccount) customer.getAccountById("INV204");
        
        // SetUp: Investment account "INV204" balance $123.45
        account.setBalance(123.45);
        
        // SetUp: Buy 10 shares of "MNO" at $10.10. (The new balance is $12.35)
        account.buyStock("MNO", 10, 10.10);
        
        // SetUp: Deposit $500 into "INV204". (The new balance is $512.35)
        account.deposit(500.0);
        
        // SetUp: Buy 5 shares of "MNO" at $10.10. (The new balance is $456.8)
        account.buyStock("MNO", 5, 10.10);
        
        // Action: Calculate total value
        double result = account.calculateAccountValue();
        
        // Expected Output: $623.45
        // Calculation:
        // Transaction 1: 10 shares * $10.10 = $101, commission $10.10, total $111.10
        // Balance after transaction 1: $123.45 - $111.10 = $12.35
        // Deposit: $500, new balance: $512.35
        // Transaction 2: 5 shares * $10.10 = $50.50, commission $5.05, total $55.55
        // Balance after transaction 2: $512.35 - $55.55 = $456.80
        // Stock value calculation:
        // Transaction 1: 10 shares * $10.10 * 1.1 = $111.10
        // Transaction 2: 5 shares * $10.10 * 1.1 = $55.55
        // Total stock value: $166.65
        // Account total: $456.80 + $166.65 = $623.45
        assertEquals(623.45, result, 0.001);
    }
}