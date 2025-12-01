import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.math.BigDecimal;

public class CR5Test {
    
    private InvestmentAccount investmentAccount;
    
    @Test
    public void testCase1_MultipleStockPositions() {
        // SetUp: Customer "Poe" holds investment account "INV200" with balance $5000
        investmentAccount = new InvestmentAccount("INV200");
        investmentAccount.setBalance(new BigDecimal("5000"));
        
        // Buy 10 shares "AAPL" at $100 (cost: 10*100=1000, commission: 1000*0.1=100, total: 1100)
        boolean buy1 = investmentAccount.buyStocks("AAPL", 10, new BigDecimal("100"));
        assertTrue("First stock purchase should succeed", buy1);
        assertEquals("Balance after first purchase", new BigDecimal("3900"), investmentAccount.getBalance());
        
        // Buy 20 shares "MSFT" at $50 (cost: 20*50=1000, commission: 1000*0.1=100, total: 1100)
        boolean buy2 = investmentAccount.buyStocks("MSFT", 20, new BigDecimal("50"));
        assertTrue("Second stock purchase should succeed", buy2);
        assertEquals("Balance after second purchase", new BigDecimal("2800"), investmentAccount.getBalance());
        
        // Action: Calculate total value
        BigDecimal totalValue = investmentAccount.calculateInvestmentValue();
        
        // Expected Output: $5000
        // Calculation: 
        // AAPL value: 10 shares * ($100 * 1.1) = 10 * $110 = $1100
        // MSFT value: 20 shares * ($50 * 1.1) = 20 * $55 = $1100
        // Stock total: $1100 + $1100 = $2200
        // Balance: $2800
        // Total: $2800 + $2200 = $5000
        assertEquals("Total investment value with multiple stocks", 
                    new BigDecimal("5000.00"), totalValue);
    }
    
    @Test
    public void testCase2_EmptyAccount() {
        // SetUp: Customer "Peter" holds investment account "INV201" with balance $0, no trades
        investmentAccount = new InvestmentAccount("INV201");
        investmentAccount.setBalance(BigDecimal.ZERO);
        
        // Action: Calculate total value
        BigDecimal totalValue = investmentAccount.calculateInvestmentValue();
        
        // Expected Output: 0
        assertEquals("Total investment value for empty account", 
                    new BigDecimal("0.00"), totalValue);
    }
    
    @Test
    public void testCase3_CashOnlyNoStocks() {
        // SetUp: Customer 'Alice' holds investment account "INV202" with balance $1000, no trades
        investmentAccount = new InvestmentAccount("INV202");
        investmentAccount.setBalance(new BigDecimal("1000"));
        
        // Action: Calculate total value
        BigDecimal totalValue = investmentAccount.calculateInvestmentValue();
        
        // Expected Output: $1000 (balance only, no stocks)
        assertEquals("Total investment value with cash only", 
                    new BigDecimal("1000.00"), totalValue);
    }
    
    @Test
    public void testCase4_RepeatedPurchasesSameStock() {
        // SetUp: Customer 'Bide' holds investment account "INV203" with balance $1000
        investmentAccount = new InvestmentAccount("INV203");
        investmentAccount.setBalance(new BigDecimal("1000"));
        
        // Buy 5 shares "AAPL" at $100 (cost: 5*100=500, commission: 500*0.1=50, total: 550)
        boolean buy1 = investmentAccount.buyStocks("AAPL", 5, new BigDecimal("100"));
        assertTrue("First AAPL purchase should succeed", buy1);
        assertEquals("Balance after first purchase", new BigDecimal("450"), investmentAccount.getBalance());
        
        // Buy 1 share "AAPL" at $120 (cost: 1*120=120, commission: 120*0.1=12, total: 132)
        boolean buy2 = investmentAccount.buyStocks("AAPL", 1, new BigDecimal("120"));
        assertTrue("Second AAPL purchase should succeed", buy2);
        assertEquals("Balance after second purchase", new BigDecimal("318"), investmentAccount.getBalance());
        
        // Action: Calculate total value
        BigDecimal totalValue = investmentAccount.calculateInvestmentValue();
        
        // Expected Output: $1000
        // Calculation:
        // First AAPL position: 5 shares * ($100 * 1.1) = 5 * $110 = $550
        // Second AAPL position: 1 share * ($120 * 1.1) = 1 * $132 = $132
        // Stock total: $550 + $132 = $682
        // Balance: $318
        // Total: $318 + $682 = $1000
        assertEquals("Total investment value with repeated purchases of same stock", 
                    new BigDecimal("1000.00"), totalValue);
    }
    
    @Test
    public void testCase5_PrecisionCheckWithFractionalShare() {
        // SetUp: Customer 'Carli' holds investment account "INV204" with balance $123.45
        investmentAccount = new InvestmentAccount("INV204");
        investmentAccount.setBalance(new BigDecimal("123.45"));
        
        // Buy 10 shares of "MNO" at $10.10 (cost: 10*10.10=101.00, commission: 101.00*0.1=10.10, total: 111.10)
        boolean buy1 = investmentAccount.buyStocks("MNO", 10, new BigDecimal("10.10"));
        assertTrue("First MNO purchase should succeed", buy1);
        assertEquals("Balance after first purchase", new BigDecimal("12.35"), investmentAccount.getBalance());
        
        // Deposit $500 into account
        investmentAccount.deposit(new BigDecimal("500"));
        assertEquals("Balance after deposit", new BigDecimal("512.35"), investmentAccount.getBalance());
        
        // Buy 5 shares of "MNO" at $10.10 (cost: 5*10.10=50.50, commission: 50.50*0.1=5.05, total: 55.55)
        boolean buy2 = investmentAccount.buyStocks("MNO", 5, new BigDecimal("10.10"));
        assertTrue("Second MNO purchase should succeed", buy2);
        assertEquals("Balance after second purchase", new BigDecimal("456.80"), investmentAccount.getBalance());
        
        // Action: Calculate total value
        BigDecimal totalValue = investmentAccount.calculateInvestmentValue();
        
        // Expected Output: $623.45
        // Calculation:
        // First MNO position: 10 shares * ($10.10 * 1.1) = 10 * $11.11 = $111.10
        // Second MNO position: 5 shares * ($10.10 * 1.1) = 5 * $11.11 = $55.55
        // Stock total: $111.10 + $55.55 = $166.65
        // Balance: $456.80
        // Total: $456.80 + $166.65 = $623.45
        assertEquals("Total investment value with precision check", 
                    new BigDecimal("623.45"), totalValue);
    }
}