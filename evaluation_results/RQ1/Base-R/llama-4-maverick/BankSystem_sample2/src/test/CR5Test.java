import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    private InvestmentAccount investmentAccount;
    
    @Test
    public void testCase1_MultipleStockPositions() {
        // SetUp: Customer "Poe" holds investment account "INV200" with balance $5000
        investmentAccount = new InvestmentAccount();
        investmentAccount.setId("INV200");
        investmentAccount.setBalance(5000.0);
        
        // Buy 10 shares "AAPL" at $100 (new balance: $5000 - (10*100 + 10% commission) = $5000 - $1100 = $3900)
        investmentAccount.buyStock("AAPL", 10, 100.0);
        
        // Buy 20 shares "MSFT" at $50 (new balance: $3900 - (20*50 + 10% commission) = $3900 - $1100 = $2800)
        investmentAccount.buyStock("MSFT", 20, 50.0);
        
        // Action: Calculate total value
        double actualValue = investmentAccount.calculateAccountValue();
        
        // Expected Output: $5000 (balance $2800 + AAPL: 10*100*1.1=$1100 + MSFT: 20*50*1.1=$1100 = $5000)
        assertEquals(5000.0, actualValue, 0.001);
    }
    
    @Test
    public void testCase2_EmptyAccount() {
        // SetUp: Customer "Peter" holds investment account "INV201" with balance $0, no trades
        investmentAccount = new InvestmentAccount();
        investmentAccount.setId("INV201");
        investmentAccount.setBalance(0.0);
        
        // Action: Calculate total value
        double actualValue = investmentAccount.calculateAccountValue();
        
        // Expected Output: 0
        assertEquals(0.0, actualValue, 0.001);
    }
    
    @Test
    public void testCase3_CashOnlyNoStocks() {
        // SetUp: Customer 'Alice' holds investment account "INV202" with balance $1000, no trades
        investmentAccount = new InvestmentAccount();
        investmentAccount.setId("INV202");
        investmentAccount.setBalance(1000.0);
        
        // Action: Calculate total value
        double actualValue = investmentAccount.calculateAccountValue();
        
        // Expected Output: $1000
        assertEquals(1000.0, actualValue, 0.001);
    }
    
    @Test
    public void testCase4_RepeatedPurchasesOfSameStock() {
        // SetUp: Customer 'Bide' holds investment account "INV203" with balance $1000
        investmentAccount = new InvestmentAccount();
        investmentAccount.setId("INV203");
        investmentAccount.setBalance(1000.0);
        
        // Buy 5 shares "AAPL" at $100 (new balance: $1000 - (5*100 + 10% commission) = $1000 - $550 = $450)
        investmentAccount.buyStock("AAPL", 5, 100.0);
        
        // Buy 1 share "AAPL" at $120 (new balance: $450 - (1*120 + 10% commission) = $450 - $132 = $318)
        investmentAccount.buyStock("AAPL", 1, 120.0);
        
        // Action: Calculate total value
        double actualValue = investmentAccount.calculateAccountValue();
        
        // Expected Output: $1000 (balance $318 + AAPL1: 5*100*1.1=$550 + AAPL2: 1*120*1.1=$132 = $1000)
        assertEquals(1000.0, actualValue, 0.001);
    }
    
    @Test
    public void testCase5_PrecisionCheckWithFractionalShare() {
        // SetUp: Customer 'Carli' holds investment account "INV204" with balance $123.45
        investmentAccount = new InvestmentAccount();
        investmentAccount.setId("INV204");
        investmentAccount.setBalance(123.45);
        
        // Buy 10 shares of "MNO" at $10.10 (new balance: $123.45 - (10*10.10 + 10% commission) = $123.45 - $111.10 = $12.35)
        investmentAccount.buyStock("MNO", 10, 10.10);
        
        // Deposit $500 into "INV204" (new balance: $12.35 + $500 = $512.35)
        investmentAccount.deposit(500.0);
        
        // Buy 5 shares of "MNO" at $10.10 (new balance: $512.35 - (5*10.10 + 10% commission) = $512.35 - $55.55 = $456.80)
        investmentAccount.buyStock("MNO", 5, 10.10);
        
        // Action: Calculate total value
        double actualValue = investmentAccount.calculateAccountValue();
        
        // Expected Output: $623.45 (balance $456.80 + MNO1: 10*10.10*1.1=$111.10 + MNO2: 5*10.10*1.1=$55.55 = $623.45)
        assertEquals(623.45, actualValue, 0.001);
    }
}