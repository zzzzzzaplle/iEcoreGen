import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR4Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_noShiftWorkersInCompany() {
        // Test Case 1: No shift workers in the company
        // Input: A Company object with an empty list of ShiftWorkers
        // Expected Output: The total holiday premiums should be 0
        
        // Add a non-shift worker to ensure company has employees but no shift workers
        Worker nonShiftWorker = new Worker("IT", "John Doe", LocalDate.of(1990, 1, 1), 
                                         "123-456-789", 40, 25.0, false);
        company.addEmployee(nonShiftWorker);
        
        double result = company.calculateTotalHolidayPremiums();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_oneShiftWorkerInCompany() {
        // Test Case 2: One shift worker in the company
        // Input: A Company object with a single ShiftWorker having a holidayPremium of 500.00
        // Expected Output: The total holiday premiums should be 500.00
        
        // Create a shift worker with weekly hours and hourly rate that will result in 500.00 premium
        // premium = weeklyWorkingHours * hourlyRate * 0.1 = 500.00
        // Let's use: 40 hours/week * 125.0/hour * 0.1 = 500.00
        Worker shiftWorker = new Worker("Delivery", "Jane Smith", LocalDate.of(1985, 5, 15), 
                                      "987-654-321", 40, 125.0, true);
        company.addEmployee(shiftWorker);
        
        double result = company.calculateTotalHolidayPremiums();
        assertEquals(500.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleShiftWorkersWithDifferentPremiums() {
        // Test Case 3: Multiple shift workers with different premiums
        // Input: A Company object with three ShiftWorkers having holiday premiums of 200.00, 300.00, and 400.00 respectively
        // Expected Output: The total holiday premiums should be 900.00
        
        // Worker 1: premium = 40 * 50.0 * 0.1 = 200.00
        Worker worker1 = new Worker("Delivery", "Worker One", LocalDate.of(1990, 1, 1), 
                                  "111-111-111", 40, 50.0, true);
        
        // Worker 2: premium = 30 * 100.0 * 0.1 = 300.00
        Worker worker2 = new Worker("Delivery", "Worker Two", LocalDate.of(1991, 2, 2), 
                                  "222-222-222", 30, 100.0, true);
        
        // Worker 3: premium = 20 * 200.0 * 0.1 = 400.00
        Worker worker3 = new Worker("Delivery", "Worker Three", LocalDate.of(1992, 3, 3), 
                                  "333-333-333", 20, 200.0, true);
        
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        company.addEmployee(worker3);
        
        double result = company.calculateTotalHolidayPremiums();
        assertEquals(900.0, result, 0.001);
    }
    
    @Test
    public void testCase4_multipleShiftWorkersWithSomeZeroPremiums() {
        // Test Case 4: Multiple shift workers with some zero premiums
        // Input: A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively
        // Expected Output: The total holiday premiums should be 400.00
        
        // Worker 1: premium = 0 (zero weekly hours)
        Worker worker1 = new Worker("Delivery", "Worker One", LocalDate.of(1990, 1, 1), 
                                  "111-111-111", 0, 100.0, true);
        
        // Worker 2: premium = 25 * 100.0 * 0.1 = 250.00
        Worker worker2 = new Worker("Delivery", "Worker Two", LocalDate.of(1991, 2, 2), 
                                  "222-222-222", 25, 100.0, true);
        
        // Worker 3: premium = 0 (zero hourly rate)
        Worker worker3 = new Worker("Delivery", "Worker Three", LocalDate.of(1992, 3, 3), 
                                  "333-333-333", 40, 0.0, true);
        
        // Worker 4: premium = 30 * 50.0 * 0.1 = 150.00
        Worker worker4 = new Worker("Delivery", "Worker Four", LocalDate.of(1993, 4, 4), 
                                  "444-444-444", 30, 50.0, true);
        
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        company.addEmployee(worker3);
        company.addEmployee(worker4);
        
        double result = company.calculateTotalHolidayPremiums();
        assertEquals(400.0, result, 0.001);
    }
    
    @Test
    public void testCase5_singleShiftWorkerWithZeroPremium() {
        // Test Case 5: Single shift worker with zero premium
        // Input: A Company object with a single ShiftWorker having a holidayPremium of 0
        // Expected Output: The total holiday premiums should be 0
        
        // Create a shift worker with zero premium (zero weekly hours)
        Worker shiftWorker = new Worker("Delivery", "Zero Premium Worker", LocalDate.of(1990, 1, 1), 
                                      "123-456-789", 0, 100.0, true);
        company.addEmployee(shiftWorker);
        
        double result = company.calculateTotalHolidayPremiums();
        assertEquals(0.0, result, 0.001);
    }
}