import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR4Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    /**
     * Test Case 1: No shift workers in the company
     * Input: A Company object with an empty list of ShiftWorkers
     * Expected Output: The total holiday premiums should be 0
     */
    @Test
    public void testCase1_noShiftWorkers() {
        // Setup: Create company with no shift workers
        List<Employee> employees = new ArrayList<>();
        
        // Add non-shift worker employees to verify they are ignored
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setCommissionPercentage(0.1);
        salesPerson.setAmountOfSales(1000);
        employees.add(salesPerson);
        
        Manager manager = new Manager();
        manager.setSalary(5000);
        employees.add(manager);
        
        company.setEmployees(employees);
        
        // Execute: Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify: Result should be 0 when no shift workers exist
        assertEquals(0.0, result, 0.001);
    }
    
    /**
     * Test Case 2: One shift worker in the company
     * Input: A Company object with a single ShiftWorker having a holidayPremium of 500.00
     * Expected Output: The total holiday premiums should be 500.00
     */
    @Test
    public void testCase2_singleShiftWorker() {
        // Setup: Create company with one shift worker
        List<Employee> employees = new ArrayList<>();
        
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setHolidayPremium(500.00);
        employees.add(shiftWorker);
        
        company.setEmployees(employees);
        
        // Execute: Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify: Result should be exactly 500.00
        assertEquals(500.00, result, 0.001);
    }
    
    /**
     * Test Case 3: Multiple shift workers with different premiums
     * Input: A Company object with three ShiftWorkers having holiday premiums of 200.00, 300.00, and 400.00 respectively
     * Expected Output: The total holiday premiums should be 900.00
     */
    @Test
    public void testCase3_multipleShiftWorkersDifferentPremiums() {
        // Setup: Create company with three shift workers
        List<Employee> employees = new ArrayList<>();
        
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setHolidayPremium(200.00);
        employees.add(worker1);
        
        ShiftWorker worker2 = new ShiftWorker();
        worker2.setHolidayPremium(300.00);
        employees.add(worker2);
        
        ShiftWorker worker3 = new ShiftWorker();
        worker3.setHolidayPremium(400.00);
        employees.add(worker3);
        
        company.setEmployees(employees);
        
        // Execute: Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify: Result should be sum of all premiums (200 + 300 + 400 = 900)
        assertEquals(900.00, result, 0.001);
    }
    
    /**
     * Test Case 4: Multiple shift workers with some zero premiums
     * Input: A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively
     * Expected Output: The total holiday premiums should be 400.00
     */
    @Test
    public void testCase4_multipleShiftWorkersSomeZeroPremiums() {
        // Setup: Create company with four shift workers, some with zero premiums
        List<Employee> employees = new ArrayList<>();
        
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setHolidayPremium(0.00);
        employees.add(worker1);
        
        ShiftWorker worker2 = new ShiftWorker();
        worker2.setHolidayPremium(250.00);
        employees.add(worker2);
        
        ShiftWorker worker3 = new ShiftWorker();
        worker3.setHolidayPremium(0.00);
        employees.add(worker3);
        
        ShiftWorker worker4 = new ShiftWorker();
        worker4.setHolidayPremium(150.00);
        employees.add(worker4);
        
        company.setEmployees(employees);
        
        // Execute: Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify: Result should be sum of non-zero premiums (0 + 250 + 0 + 150 = 400)
        assertEquals(400.00, result, 0.001);
    }
    
    /**
     * Test Case 5: Single shift worker with zero premium
     * Input: A Company object with a single ShiftWorker having a holidayPremium of 0
     * Expected Output: The total holiday premiums should be 0
     */
    @Test
    public void testCase5_singleShiftWorkerZeroPremium() {
        // Setup: Create company with one shift worker having zero premium
        List<Employee> employees = new ArrayList<>();
        
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setHolidayPremium(0.00);
        employees.add(shiftWorker);
        
        company.setEmployees(employees);
        
        // Execute: Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify: Result should be 0 for zero premium
        assertEquals(0.00, result, 0.001);
    }
}