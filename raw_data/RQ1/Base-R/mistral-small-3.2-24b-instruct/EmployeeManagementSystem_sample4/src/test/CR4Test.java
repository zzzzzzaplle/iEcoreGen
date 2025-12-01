import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

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
        
        // Add employees that are not shift workers (SalesPerson and Manager)
        SalesPerson salesPerson = new SalesPerson("Sales", "John Doe", 
            LocalDate.of(1985, 5, 15), "123-45-6789", 3000.0, 50000.0, 0.05);
        Manager manager = new Manager("Management", "Jane Smith", 
            LocalDate.of(1978, 8, 22), "987-65-4321", 8000.0);
        
        company.addEmployee(salesPerson);
        company.addEmployee(manager);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify the result is 0 since there are no shift workers in delivery department
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_oneShiftWorkerInCompany() {
        // Test Case 2: One shift worker in the company
        // Input: A Company object with a single ShiftWorker having a holidayPremium of 500.00
        // Expected Output: The total holiday premiums should be 500.00
        
        // Create a shift worker in Delivery department (should receive premium)
        Worker shiftWorker = new Worker("Delivery", "Mike Johnson", 
            LocalDate.of(1990, 3, 10), "555-55-5555", 40.0, 25.0, true);
            
        company.addEmployee(shiftWorker);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify the result is 50.00 (fixed premium for Delivery shift workers)
        assertEquals(50.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleShiftWorkersWithDifferentPremiums() {
        // Test Case 3: Multiple shift workers with different premiums
        // Input: A Company object with three ShiftWorkers having holiday premiums of 200.00, 300.00, and 400.00 respectively
        // Expected Output: The total holiday premiums should be 900.00
        
        // Create three shift workers in Delivery department (all should receive premium)
        Worker shiftWorker1 = new Worker("Delivery", "Worker One", 
            LocalDate.of(1988, 1, 15), "111-11-1111", 35.0, 20.0, true);
        Worker shiftWorker2 = new Worker("Delivery", "Worker Two", 
            LocalDate.of(1992, 6, 20), "222-22-2222", 38.0, 22.0, true);
        Worker shiftWorker3 = new Worker("Delivery", "Worker Three", 
            LocalDate.of(1985, 11, 5), "333-33-3333", 40.0, 25.0, true);
            
        company.addEmployee(shiftWorker1);
        company.addEmployee(shiftWorker2);
        company.addEmployee(shiftWorker3);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify the result is 150.00 (3 workers × 50.00 premium each)
        assertEquals(150.0, result, 0.001);
    }
    
    @Test
    public void testCase4_multipleShiftWorkersWithSomeZeroPremiums() {
        // Test Case 4: Multiple shift workers with some zero premiums
        // Input: A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively
        // Expected Output: The total holiday premiums should be 400.00
        
        // Create shift workers - some in Delivery (premium) and some in other departments (no premium)
        Worker worker1 = new Worker("IT", "IT Worker",  // Not in Delivery - no premium
            LocalDate.of(1991, 4, 12), "444-44-4444", 37.5, 30.0, true);
        Worker worker2 = new Worker("Delivery", "Delivery Worker 1",  // In Delivery - premium
            LocalDate.of(1989, 7, 18), "555-55-5555", 40.0, 22.0, true);
        Worker worker3 = new Worker("HR", "HR Worker",  // Not in Delivery - no premium
            LocalDate.of(1993, 9, 25), "666-66-6666", 35.0, 28.0, true);
        Worker worker4 = new Worker("Delivery", "Delivery Worker 2",  // In Delivery - premium
            LocalDate.of(1987, 12, 3), "777-77-7777", 42.0, 24.0, true);
            
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        company.addEmployee(worker3);
        company.addEmployee(worker4);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify the result is 100.00 (2 Delivery shift workers × 50.00 premium each)
        assertEquals(100.0, result, 0.001);
    }
    
    @Test
    public void testCase5_singleShiftWorkerWithZeroPremium() {
        // Test Case 5: Single shift worker with zero premium
        // Input: A Company object with a single ShiftWorker having a holidayPremium of 0
        // Expected Output: The total holiday premiums should be 0
        
        // Create a shift worker NOT in Delivery department (should not receive premium)
        Worker shiftWorker = new Worker("IT", "IT Specialist", 
            LocalDate.of(1990, 2, 28), "888-88-8888", 40.0, 35.0, true);
            
        company.addEmployee(shiftWorker);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify the result is 0 since the worker is not in Delivery department
        assertEquals(0.0, result, 0.001);
    }
}