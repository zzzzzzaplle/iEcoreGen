import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    @Test
    public void testCase1_MultipleStockPositions() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a investment account "INV200"
        Customer customer = new Customer();
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        customer.addInvestmentAccount("INV200");
        
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV200");
        account.setBalance(5000);
        
        // Buy 10 shares "AAPL" at $100; (The new balance is $3900)
        account.buyStock("AAPL", 10, 100);
        
        // Buy 20 shares "MSFT" at $50; (The new balance is $2800)
        account.buyStock("MSFT", 20, 50);
        
        // Action: Calculate total value
        double result = account.calculateValue();
        
        // Expected Output: $5000
        assertEquals(5000.0, result, 0.001);
    }
    
    @Test
    public void testCase2_EmptyAccount() {
        // SetUp: Customer "Peter" (address "0816 Center St") holds a investment account "INV201"
        Customer customer = new Customer();
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        customer.addInvestmentAccount("INV201");
        
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV201");
        // New investment account "INV201" balance $0, no trades
        account.setBalance(0);
        
        // Action: Calculate total value
        double result = account.calculateValue();
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_CashOnlyNoStocks() {
        // SetUp: Customer 'Alice' (address "0811 Center St") holds a investment account "INV202"
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setAddress("0811 Center St");
        customer.addInvestmentAccount("INV202");
        
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV202");
        // Investment account "INV202" balance $1000, no trades
        account.setBalance(1000);
        
        // Action: Calculate total value
        double result = account.calculateValue();
        
        // Expected Output: $1000
        assertEquals(1000.0, result, 0.001);
    }
    
    @Test
    public void testCase4_RepeatedPurchasesOfSameStock() {
        // SetUp: Customer 'Bide' (address "0810 Main St") holds a investment account "INV203"
        Customer customer = new Customer();
        customer.setName("Bide");
        customer.setAddress("0810 Main St");
        customer.addInvestmentAccount("INV203");
        
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV203");
        account.setBalance(1000);
        
        // Buy 5 shares "AAPL" at $100. (The new balance is $450)
        account.buyStock("AAPL", 5, 100);
        
        // Buy 1 shares "AAPL" at $120. (The new balance is $318)
        account.buyStock("AAPL", 1, 120);
        
        // Action: Calculate total value
        double result = account.calculateValue();
        
        // Expected Output: $1000
        assertEquals(1000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_PrecisionCheckWithFractionalShare() {
        // SetUp: Customer 'Carli' (address "0820 Main St") holds a investment account "INV204"
        Customer customer = new Customer();
        customer.setName("Carli");
        customer.setAddress("0820 Main St");
        customer.addInvestmentAccount("INV204");
        
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV204");
        account.setBalance(123.45);
        
        // Buy 10 shares of "MNO" at $10.10. (The new balance is $12.35)
        account.buyStock("MNO", 10, 10.10);
        
        // Deposit $500 into "INV204". (The new balance is $512.35)
        account.deposit(500);
        
        // Buy 5 shares of "MNO" at $10.10. (The new balance is $456.8)
        account.buyStock("MNO", 5, 10.10);
        
        // Action: Calculate total value
        double result = account.calculateValue();
        
        // Expected Output: $623.45
        assertEquals(623.45, result, 0.001);
    }
}