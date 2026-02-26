import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    private Company company;
    
    @Before
    public void setUp() {
        // Reset company before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_noShiftWorkersInCompany() {
        // Test Case 1: No shift workers in the company
        // Input: A Company object with an empty list of ShiftWorkers
        // Expected Output: The total holiday premiums should be 0
        
        // Company already has no employees after setup
        double result = company.calculateTotalHolidayPremiums();
        assertEquals("Total holiday premiums should be 0 when there are no shift workers", 
                     0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_oneShiftWorkerWithPremium() {
        // Test Case 2: One shift worker in the company
        // Input: A Company object with a single ShiftWorker having a holidayPremium of 500.00
        // Expected Output: The total holiday premiums should be 500.00
        
        Worker shiftWorker = new Worker();
        shiftWorker.setShiftWorker(true);
        shiftWorker.setHolidayPremium(500.00);
        company.addEmployee(shiftWorker);
        
        double result = company.calculateTotalHolidayPremiums();
        assertEquals("Total holiday premiums should be 500.00 for one shift worker", 
                     500.00, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleShiftWorkersWithDifferentPremiums() {
        // Test Case 3: Multiple shift workers with different premiums
        // Input: A Company object with three ShiftWorkers having holiday premiums of 200.00, 300.00, and 400.00 respectively
        // Expected Output: The total holiday premiums should be 900.00
        
        Worker worker1 = new Worker();
        worker1.setShiftWorker(true);
        worker1.setHolidayPremium(200.00);
        company.addEmployee(worker1);
        
        Worker worker2 = new Worker();
        worker2.setShiftWorker(true);
        worker2.setHolidayPremium(300.00);
        company.addEmployee(worker2);
        
        Worker worker3 = new Worker();
        worker3.setShiftWorker(true);
        worker3.setHolidayPremium(400.00);
        company.addEmployee(worker3);
        
        double result = company.calculateTotalHolidayPremiums();
        assertEquals("Total holiday premiums should be 900.00 for three shift workers", 
                     900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_multipleShiftWorkersWithSomeZeroPremiums() {
        // Test Case 4: Multiple shift workers with some zero premiums
        // Input: A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively
        // Expected Output: The total holiday premiums should be 400.00
        
        Worker worker1 = new Worker();
        worker1.setShiftWorker(true);
        worker1.setHolidayPremium(0.00);
        company.addEmployee(worker1);
        
        Worker worker2 = new Worker();
        worker2.setShiftWorker(true);
        worker2.setHolidayPremium(250.00);
        company.addEmployee(worker2);
        
        Worker worker3 = new Worker();
        worker3.setShiftWorker(true);
        worker3.setHolidayPremium(0.00);
        company.addEmployee(worker3);
        
        Worker worker4 = new Worker();
        worker4.setShiftWorker(true);
        worker4.setHolidayPremium(150.00);
        company.addEmployee(worker4);
        
        double result = company.calculateTotalHolidayPremiums();
        assertEquals("Total holiday premiums should be 400.00 for four shift workers with some zero premiums", 
                     400.00, result, 0.001);
    }
    
    @Test
    public void testCase5_singleShiftWorkerWithZeroPremium() {
        // Test Case 5: Single shift worker with zero premium
        // Input: A Company object with a single ShiftWorker having a holidayPremium of 0
        // Expected Output: The total holiday premiums should be 0
        
        Worker shiftWorker = new Worker();
        shiftWorker.setShiftWorker(true);
        shiftWorker.setHolidayPremium(0.00);
        company.addEmployee(shiftWorker);
        
        double result = company.calculateTotalHolidayPremiums();
        assertEquals("Total holiday premiums should be 0 for a shift worker with zero premium", 
                     0.0, result, 0.001);
    }
}