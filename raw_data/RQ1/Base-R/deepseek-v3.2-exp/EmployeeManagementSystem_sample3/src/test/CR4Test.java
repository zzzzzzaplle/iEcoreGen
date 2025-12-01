import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize a fresh Company object before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_noShiftWorkersInCompany() {
        // Test Case 1: No shift workers in the company
        // Input: A Company object with an empty list of ShiftWorkers
        // Expected Output: The total holiday premiums should be 0
        
        // Setup: Add non-shift workers and other employee types to ensure only shift workers are considered
        NonShiftWorker nonShiftWorker = new NonShiftWorker();
        nonShiftWorker.setDepartment("delivery");
        nonShiftWorker.setHolidayPremium(100.00);
        company.addEmployee(nonShiftWorker);
        
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setDepartment("sales");
        company.addEmployee(salesPerson);
        
        Manager manager = new Manager();
        manager.setDepartment("management");
        company.addEmployee(manager);
        
        // Execute: Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify: Should return 0 since there are no shift workers in delivery department
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_oneShiftWorkerInCompany() {
        // Test Case 2: One shift worker in the company
        // Input: A Company object with a single ShiftWorker having a holidayPremium of 500.00
        // Expected Output: The total holiday premiums should be 500.00
        
        // Setup: Create and add a shift worker with holiday premium
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setDepartment("delivery");
        shiftWorker.setHolidayPremium(500.00);
        company.addEmployee(shiftWorker);
        
        // Execute: Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify: Should return the premium amount of the single shift worker
        assertEquals(500.00, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleShiftWorkersWithDifferentPremiums() {
        // Test Case 3: Multiple shift workers with different premiums
        // Input: A Company object with three ShiftWorkers having holiday premiums of 200.00, 300.00, and 400.00 respectively
        // Expected Output: The total holiday premiums should be 900.00
        
        // Setup: Create and add three shift workers with different holiday premiums
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setDepartment("delivery");
        worker1.setHolidayPremium(200.00);
        company.addEmployee(worker1);
        
        ShiftWorker worker2 = new ShiftWorker();
        worker2.setDepartment("delivery");
        worker2.setHolidayPremium(300.00);
        company.addEmployee(worker2);
        
        ShiftWorker worker3 = new ShiftWorker();
        worker3.setDepartment("delivery");
        worker3.setHolidayPremium(400.00);
        company.addEmployee(worker3);
        
        // Execute: Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify: Should return the sum of all premiums (200 + 300 + 400 = 900)
        assertEquals(900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_multipleShiftWorkersWithSomeZeroPremiums() {
        // Test Case 4: Multiple shift workers with some zero premiums
        // Input: A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively
        // Expected Output: The total holiday premiums should be 400.00
        
        // Setup: Create and add four shift workers with mixed zero and non-zero premiums
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setDepartment("delivery");
        worker1.setHolidayPremium(0.00);
        company.addEmployee(worker1);
        
        ShiftWorker worker2 = new ShiftWorker();
        worker2.setDepartment("delivery");
        worker2.setHolidayPremium(250.00);
        company.addEmployee(worker2);
        
        ShiftWorker worker3 = new ShiftWorker();
        worker3.setDepartment("delivery");
        worker3.setHolidayPremium(0.00);
        company.addEmployee(worker3);
        
        ShiftWorker worker4 = new ShiftWorker();
        worker4.setDepartment("delivery");
        worker4.setHolidayPremium(150.00);
        company.addEmployee(worker4);
        
        // Execute: Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify: Should return the sum of non-zero premiums (0 + 250 + 0 + 150 = 400)
        assertEquals(400.00, result, 0.001);
    }
    
    @Test
    public void testCase5_singleShiftWorkerWithZeroPremium() {
        // Test Case 5: Single shift worker with zero premium
        // Input: A Company object with a single ShiftWorker having a holidayPremium of 0
        // Expected Output: The total holiday premiums should be 0
        
        // Setup: Create and add a shift worker with zero holiday premium
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setDepartment("delivery");
        shiftWorker.setHolidayPremium(0.00);
        company.addEmployee(shiftWorker);
        
        // Execute: Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify: Should return 0 since the premium is zero
        assertEquals(0.0, result, 0.001);
    }
}