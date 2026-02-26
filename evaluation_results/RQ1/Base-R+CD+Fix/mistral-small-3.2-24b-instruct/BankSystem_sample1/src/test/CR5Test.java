import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    @Test
    public void testCase1_MultipleStockPositions() {
        // SetUp: Customer "Poe" with investment account "INV200"
        Customer customer = new Customer();
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        customer.addInvestmentAccount("INV200");
        
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV200");
        account.setBalance(5000.0);
        
        // Buy 10 shares "AAPL" at $100
        account.buyStock("AAPL", 10, 100.0);
        // Buy 20 shares "MSFT" at $50
        account.buyStock("MSFT", 20, 50.0);
        
        // Action: Calculate total value
        double result = account.calculateValue();
        
        // Expected Output: $5000
        assertEquals(5000.0, result, 0.001);
    }
    
    @Test
    public void testCase2_EmptyAccount() {
        // SetUp: Customer "Peter" with investment account "INV201"
        Customer customer = new Customer();
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        customer.addInvestmentAccount("INV201");
        
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV201");
        // Balance is $0 by default, no trades
        
        // Action: Calculate total value
        double result = account.calculateValue();
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_CashOnlyNoStocks() {
        // SetUp: Customer 'Alice' with investment account "INV202"
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setAddress("0811 Center St");
        customer.addInvestmentAccount("INV202");
        
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV202");
        account.setBalance(1000.0);
        // No trades
        
        // Action: Calculate total value
        double result = account.calculateValue();
        
        // Expected Output: $1000
        assertEquals(1000.0, result, 0.001);
    }
    
    @Test
    public void testCase4_RepeatedPurchasesOfSameStock() {
        // SetUp: Customer 'Bide' with investment account "INV203"
        Customer customer = new Customer();
        customer.setName("Bide");
        customer.setAddress("0810 Main St");
        customer.addInvestmentAccount("INV203");
        
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV203");
        account.setBalance(1000.0);
        
        // Buy 5 shares "AAPL" at $100
        account.buyStock("AAPL", 5, 100.0);
        // Buy 1 share "AAPL" at $120
        account.buyStock("AAPL", 1, 120.0);
        
        // Action: Calculate total value
        double result = account.calculateValue();
        
        // Expected Output: $1000
        assertEquals(1000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_PrecisionCheckWithFractionalShare() {
        // SetUp: Customer 'Carli' with investment account "INV204"
        Customer customer = new Customer();
        customer.setName("Carli");
        customer.setAddress("0820 Main St");
        customer.addInvestmentAccount("INV204");
        
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV204");
        account.setBalance(123.45);
        
        // Buy 10 shares of "MNO" at $10.10
        account.buyStock("MNO", 10, 10.10);
        // Deposit $500 into "INV204"
        account.deposit(500.0);
        // Buy 5 shares of "MNO" at $10.10
        account.buyStock("MNO", 5, 10.10);
        
        // Action: Calculate total value
        double result = account.calculateValue();
        
        // Expected Output: $623.45
        assertEquals(623.45, result, 0.001);
    }
}