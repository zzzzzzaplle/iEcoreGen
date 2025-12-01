import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize a new Company object before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_noShiftWorkersInCompany() {
        // Test Case 1: No shift workers in the company
        // Input: A Company object with an empty list of ShiftWorkers
        // Expected Output: The total holiday premiums should be 0
        
        // The company is initially empty, so no shift workers exist
        double result = company.calculateTotalHolidayPremiums();
        assertEquals("Total holiday premiums should be 0 when there are no shift workers", 
                     0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_oneShiftWorkerWithPremium() {
        // Test Case 2: One shift worker in the company
        // Input: A Company object with a single ShiftWorker having a holidayPremium of 500.00
        // Expected Output: The total holiday premiums should be 500.00
        
        // Create and configure a shift worker
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setHolidayPremiums(500.00);
        
        // Add the shift worker to the company
        company.addEmployee(shiftWorker);
        
        double result = company.calculateTotalHolidayPremiums();
        assertEquals("Total holiday premiums should be 500.00 for one shift worker with 500.00 premium", 
                     500.00, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleShiftWorkersWithDifferentPremiums() {
        // Test Case 3: Multiple shift workers with different premiums
        // Input: A Company object with three ShiftWorkers having holiday premiums of 200.00, 300.00, and 400.00 respectively
        // Expected Output: The total holiday premiums should be 900.00
        
        // Create and configure three shift workers with different premiums
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setHolidayPremiums(200.00);
        
        ShiftWorker worker2 = new ShiftWorker();
        worker2.setHolidayPremiums(300.00);
        
        ShiftWorker worker3 = new ShiftWorker();
        worker3.setHolidayPremiums(400.00);
        
        // Add all shift workers to the company
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        company.addEmployee(worker3);
        
        double result = company.calculateTotalHolidayPremiums();
        assertEquals("Total holiday premiums should be 900.00 for three shift workers with premiums 200, 300, and 400", 
                     900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_multipleShiftWorkersWithSomeZeroPremiums() {
        // Test Case 4: Multiple shift workers with some zero premiums
        // Input: A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively
        // Expected Output: The total holiday premiums should be 400.00
        
        // Create and configure four shift workers with mixed premiums (some zero)
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setHolidayPremiums(0.00);
        
        ShiftWorker worker2 = new ShiftWorker();
        worker2.setHolidayPremiums(250.00);
        
        ShiftWorker worker3 = new ShiftWorker();
        worker3.setHolidayPremiums(0.00);
        
        ShiftWorker worker4 = new ShiftWorker();
        worker4.setHolidayPremiums(150.00);
        
        // Add all shift workers to the company
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        company.addEmployee(worker3);
        company.addEmployee(worker4);
        
        double result = company.calculateTotalHolidayPremiums();
        assertEquals("Total holiday premiums should be 400.00 for four shift workers with premiums 0, 250, 0, and 150", 
                     400.00, result, 0.001);
    }
    
    @Test
    public void testCase5_singleShiftWorkerWithZeroPremium() {
        // Test Case 5: Single shift worker with zero premium
        // Input: A Company object with a single ShiftWorker having a holidayPremium of 0
        // Expected Output: The total holiday premiums should be 0
        
        // Create and configure a shift worker with zero premium
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setHolidayPremiums(0.00);
        
        // Add the shift worker to the company
        company.addEmployee(shiftWorker);
        
        double result = company.calculateTotalHolidayPremiums();
        assertEquals("Total holiday premiums should be 0 for one shift worker with 0 premium", 
                     0.0, result, 0.001);
    }
}