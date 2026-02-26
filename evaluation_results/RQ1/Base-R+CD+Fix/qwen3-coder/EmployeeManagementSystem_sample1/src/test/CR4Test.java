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
        // Test case 1: No shift workers in the company
        // Input: A Company object with an empty list of ShiftWorkers
        // Expected Output: The total holiday premiums should be 0
        
        // Add employees that are not shift workers to ensure only shift workers are considered
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setAmountOfSales(10000);
        salesPerson.setCommissionPercentage(0.1);
        salesPerson.setSalary(2000);
        
        Manager manager = new Manager();
        manager.setSalary(5000);
        
        OffShiftWorker offShiftWorker = new OffShiftWorker();
        offShiftWorker.setWeeklyWorkingHour(40);
        offShiftWorker.setHourlyRates(25);
        
        // Add employees to company
        List<Employee> employees = new ArrayList<>();
        employees.add(salesPerson);
        employees.add(manager);
        employees.add(offShiftWorker);
        company.setEmployees(employees);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify the result is 0 since there are no shift workers
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_oneShiftWorkerInCompany() {
        // Test case 2: One shift worker in the company
        // Input: A Company object with a single ShiftWorker having a holidayPremium of 500.00
        // Expected Output: The total holiday premiums should be 500.00
        
        // Create a shift worker with holiday premium of 500.00
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setHolidayPremium(500.00);
        
        // Add shift worker to company
        List<Employee> employees = new ArrayList<>();
        employees.add(shiftWorker);
        company.setEmployees(employees);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify the result is 500.00
        assertEquals(500.00, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleShiftWorkersWithDifferentPremiums() {
        // Test case 3: Multiple shift workers with different premiums
        // Input: A Company object with three ShiftWorkers having holiday premiums of 200.00, 300.00, and 400.00 respectively
        // Expected Output: The total holiday premiums should be 900.00
        
        // Create three shift workers with different holiday premiums
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setHolidayPremium(200.00);
        
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setHolidayPremium(300.00);
        
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setHolidayPremium(400.00);
        
        // Add shift workers to company
        List<Employee> employees = new ArrayList<>();
        employees.add(shiftWorker1);
        employees.add(shiftWorker2);
        employees.add(shiftWorker3);
        company.setEmployees(employees);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify the result is 900.00 (200 + 300 + 400)
        assertEquals(900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_multipleShiftWorkersWithSomeZeroPremiums() {
        // Test case 4: Multiple shift workers with some zero premiums
        // Input: A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively
        // Expected Output: The total holiday premiums should be 400.00
        
        // Create four shift workers with some zero premiums
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setHolidayPremium(0);
        
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setHolidayPremium(250.00);
        
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setHolidayPremium(0);
        
        ShiftWorker shiftWorker4 = new ShiftWorker();
        shiftWorker4.setHolidayPremium(150.00);
        
        // Add shift workers to company
        List<Employee> employees = new ArrayList<>();
        employees.add(shiftWorker1);
        employees.add(shiftWorker2);
        employees.add(shiftWorker3);
        employees.add(shiftWorker4);
        company.setEmployees(employees);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify the result is 400.00 (0 + 250 + 0 + 150)
        assertEquals(400.00, result, 0.001);
    }
    
    @Test
    public void testCase5_singleShiftWorkerWithZeroPremium() {
        // Test case 5: Single shift worker with zero premium
        // Input: A Company object with a single ShiftWorker having a holidayPremium of 0
        // Expected Output: The total holiday premiums should be 0
        
        // Create a shift worker with zero holiday premium
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setHolidayPremium(0);
        
        // Add shift worker to company
        List<Employee> employees = new ArrayList<>();
        employees.add(shiftWorker);
        company.setEmployees(employees);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify the result is 0
        assertEquals(0.0, result, 0.001);
    }
}