import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    @Test
    public void testCase1_MultipleStockPositions() {
        // SetUp: Customer "Poe" with investment account "INV200" balance $5000
        Customer customer = new Customer("Poe", "0814 Center St");
        InvestmentAccount account = new InvestmentAccount("INV200", 5000.0);
        customer.addAccount(account);
        
        // Buy 10 shares "AAPL" at $100 (new balance: $3900)
        account.buyStocks("AAPL", 10, 100.0);
        // Buy 20 shares "MSFT" at $50 (new balance: $2800)
        account.buyStocks("MSFT", 20, 50.0);
        
        // Action: Calculate total value
        double result = account.calculateAccountValue();
        
        // Expected Output: $5000 (balance $2800 + AAPL: 10*100*1.1=1100 + MSFT: 20*50*1.1=1100)
        assertEquals(5000.0, result, 0.001);
    }
    
    @Test
    public void testCase2_EmptyAccount() {
        // SetUp: Customer "Peter" with investment account "INV201" balance $0, no trades
        Customer customer = new Customer("Peter", "0816 Center St");
        InvestmentAccount account = new InvestmentAccount("INV201", 0.0);
        customer.addAccount(account);
        
        // Action: Calculate total value
        double result = account.calculateAccountValue();
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_CashOnlyNoStocks() {
        // SetUp: Customer 'Alice' with investment account "INV202" balance $1000, no trades
        Customer customer = new Customer("Alice", "0811 Center St");
        InvestmentAccount account = new InvestmentAccount("INV202", 1000.0);
        customer.addAccount(account);
        
        // Action: Calculate total value
        double result = account.calculateAccountValue();
        
        // Expected Output: $1000
        assertEquals(1000.0, result, 0.001);
    }
    
    @Test
    public void testCase4_RepeatedPurchasesSameStock() {
        // SetUp: Customer 'Bide' with investment account "INV203" balance $1000
        Customer customer = new Customer("Bide", "0810 Main St");
        InvestmentAccount account = new InvestmentAccount("INV203", 1000.0);
        customer.addAccount(account);
        
        // Buy 5 shares "AAPL" at $100 (new balance: $450)
        account.buyStocks("AAPL", 5, 100.0);
        // Buy 1 shares "AAPL" at $120 (new balance: $318)
        account.buyStocks("AAPL", 1, 120.0);
        
        // Action: Calculate total value
        double result = account.calculateAccountValue();
        
        // Expected Output: $1000 (balance $318 + AAPL: (5*100*1.1=550) + (1*120*1.1=132) = 682, total 1000)
        assertEquals(1000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_PrecisionCheckWithFractionalShare() {
        // SetUp: Customer 'Carli' with investment account "INV204" balance $123.45
        Customer customer = new Customer("Carli", "0820 Main St");
        InvestmentAccount account = new InvestmentAccount("INV204", 123.45);
        customer.addAccount(account);
        
        // Buy 10 shares of "MNO" at $10.10 (new balance: $12.35)
        account.buyStocks("MNO", 10, 10.10);
        // Deposit $500 into "INV204" (new balance: $512.35)
        account.deposit(500.0);
        // Buy 5 shares of "MNO" at $10.10 (new balance: $456.8)
        account.buyStocks("MNO", 5, 10.10);
        
        // Action: Calculate total value
        double result = account.calculateAccountValue();
        
        // Expected Output: $623.45 (balance $456.8 + MNO: (10*10.10*1.1=111.1) + (5*10.10*1.1=55.55) = 166.65, total 623.45)
        assertEquals(623.45, result, 0.001);
    }
}