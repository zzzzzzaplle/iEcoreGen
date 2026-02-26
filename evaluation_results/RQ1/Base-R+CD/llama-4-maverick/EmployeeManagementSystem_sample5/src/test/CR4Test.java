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
        // Test Case 1: "No shift workers in the company"
        // Input: "A Company object with an empty list of ShiftWorkers"
        // Expected Output: "The total holiday premiums should be 0"
        
        // Create employees list without any ShiftWorker instances
        List<Employee> employees = new ArrayList<>();
        
        // Add some non-ShiftWorker employees to ensure the test is valid
        Manager manager = new Manager();
        manager.setSalary(50000);
        employees.add(manager);
        
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setAmountOfSales(10000);
        salesPerson.setCommissionPercentage(0.1);
        salesPerson.setSalary(30000);
        employees.add(salesPerson);
        
        OffShiftWorker offShiftWorker = new OffShiftWorker();
        offShiftWorker.setWeeklyWorkingHour(40);
        offShiftWorker.setHourlyRates(25);
        employees.add(offShiftWorker);
        
        company.setEmployees(employees);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify the result is 0 since there are no ShiftWorkers
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_oneShiftWorkerInCompany() {
        // Test Case 2: "One shift worker in the company"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 500.00"
        // Expected Output: "The total holiday premiums should be 500.00"
        
        List<Employee> employees = new ArrayList<>();
        
        // Create and configure a ShiftWorker with holiday premium of 500.00
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setHolidayPremium(500.00);
        employees.add(shiftWorker);
        
        company.setEmployees(employees);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify the result is 500.00
        assertEquals(500.00, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleShiftWorkersWithDifferentPremiums() {
        // Test Case 3: "Multiple shift workers with different premiums"
        // Input: "A Company object with three ShiftWorkers having holiday premiums of 200.00, 300.00, and 400.00 respectively"
        // Expected Output: "The total holiday premiums should be 900.00"
        
        List<Employee> employees = new ArrayList<>();
        
        // Create and configure first ShiftWorker with holiday premium of 200.00
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setHolidayPremium(200.00);
        employees.add(shiftWorker1);
        
        // Create and configure second ShiftWorker with holiday premium of 300.00
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setHolidayPremium(300.00);
        employees.add(shiftWorker2);
        
        // Create and configure third ShiftWorker with holiday premium of 400.00
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setHolidayPremium(400.00);
        employees.add(shiftWorker3);
        
        company.setEmployees(employees);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify the result is 900.00 (200 + 300 + 400)
        assertEquals(900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_multipleShiftWorkersWithSomeZeroPremiums() {
        // Test Case 4: "Multiple shift workers with some zero premiums"
        // Input: "A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively"
        // Expected Output: "The total holiday premiums should be 400.00"
        
        List<Employee> employees = new ArrayList<>();
        
        // Create and configure first ShiftWorker with holiday premium of 0
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setHolidayPremium(0);
        employees.add(shiftWorker1);
        
        // Create and configure second ShiftWorker with holiday premium of 250.00
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setHolidayPremium(250.00);
        employees.add(shiftWorker2);
        
        // Create and configure third ShiftWorker with holiday premium of 0
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setHolidayPremium(0);
        employees.add(shiftWorker3);
        
        // Create and configure fourth ShiftWorker with holiday premium of 150.00
        ShiftWorker shiftWorker4 = new ShiftWorker();
        shiftWorker4.setHolidayPremium(150.00);
        employees.add(shiftWorker4);
        
        company.setEmployees(employees);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify the result is 400.00 (0 + 250 + 0 + 150)
        assertEquals(400.00, result, 0.001);
    }
    
    @Test
    public void testCase5_singleShiftWorkerWithZeroPremium() {
        // Test Case 5: "Single shift worker with zero premium"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 0"
        // Expected Output: "The total holiday premiums should be 0"
        
        List<Employee> employees = new ArrayList<>();
        
        // Create and configure a ShiftWorker with holiday premium of 0
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setHolidayPremium(0);
        employees.add(shiftWorker);
        
        company.setEmployees(employees);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify the result is 0
        assertEquals(0.0, result, 0.001);
    }
}