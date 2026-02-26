import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR5Test {
    
    @Test
    public void testCase1_multipleStockPositions() {
        // SetUp: Customer "Poe" with investment account "INV200"
        Customer customer = new Customer();
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        
        // Add investment account with initial balance $5000
        customer.addInvestmentAccount("INV200");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV200");
        account.setBalance(5000.0);
        
        // Buy 10 shares "AAPL" at $100
        account.buyStock("AAPL", 10, 100.0); // Cost: 1000 + 100 commission = 1100, balance: 3900
        
        // Buy 20 shares "MSFT" at $50
        account.buyStock("MSFT", 20, 50.0); // Cost: 1000 + 100 commission = 1100, balance: 2800
        
        // Action: Calculate total value
        double totalValue = account.calculateValue();
        
        // Expected Output: $5000
        assertEquals(5000.0, totalValue, 0.01);
    }
    
    @Test
    public void testCase2_emptyAccount() {
        // SetUp: Customer "Peter" with new investment account "INV201"
        Customer customer = new Customer();
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        
        // Add investment account with $0 balance and no trades
        customer.addInvestmentAccount("INV201");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV201");
        account.setBalance(0.0);
        
        // Action: Calculate total value
        double totalValue = account.calculateValue();
        
        // Expected Output: 0
        assertEquals(0.0, totalValue, 0.01);
    }
    
    @Test
    public void testCase3_cashOnlyNoStocks() {
        // SetUp: Customer "Alice" with investment account "INV202"
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setAddress("0811 Center St");
        
        // Add investment account with $1000 balance and no trades
        customer.addInvestmentAccount("INV202");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV202");
        account.setBalance(1000.0);
        
        // Action: Calculate total value
        double totalValue = account.calculateValue();
        
        // Expected Output: $1000
        assertEquals(1000.0, totalValue, 0.01);
    }
    
    @Test
    public void testCase4_repeatedPurchasesOfSameStock() {
        // SetUp: Customer "Bide" with investment account "INV203"
        Customer customer = new Customer();
        customer.setName("Bide");
        customer.setAddress("0810 Main St");
        
        // Add investment account with $1000 balance
        customer.addInvestmentAccount("INV203");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV203");
        account.setBalance(1000.0);
        
        // Buy 5 shares "AAPL" at $100
        account.buyStock("AAPL", 5, 100.0); // Cost: 500 + 50 commission = 550, balance: 450
        
        // Buy 1 share "AAPL" at $120
        account.buyStock("AAPL", 1, 120.0); // Cost: 120 + 12 commission = 132, balance: 318
        
        // Action: Calculate total value
        double totalValue = account.calculateValue();
        
        // Expected Output: $1000
        assertEquals(1000.0, totalValue, 0.01);
    }
    
    @Test
    public void testCase5_precisionCheckWithFractionalShare() {
        // SetUp: Customer "Carli" with investment account "INV204"
        Customer customer = new Customer();
        customer.setName("Carli");
        customer.setAddress("0820 Main St");
        
        // Add investment account with $123.45 balance
        customer.addInvestmentAccount("INV204");
        InvestmentAccount account = (InvestmentAccount) customer.findAccountById("INV204");
        account.setBalance(123.45);
        
        // Buy 10 shares of "MNO" at $10.10
        account.buyStock("MNO", 10, 10.10); // Cost: 101 + 10.1 commission = 111.1, balance: 12.35
        
        // Deposit $500 into "INV204"
        account.deposit(500.0); // balance: 512.35
        
        // Buy 5 shares of "MNO" at $10.10
        account.buyStock("MNO", 5, 10.10); // Cost: 50.5 + 5.05 commission = 55.55, balance: 456.8
        
        // Action: Calculate total value
        double totalValue = account.calculateValue();
        
        // Expected Output: $623.45
        assertEquals(623.45, totalValue, 0.01);
    }
}