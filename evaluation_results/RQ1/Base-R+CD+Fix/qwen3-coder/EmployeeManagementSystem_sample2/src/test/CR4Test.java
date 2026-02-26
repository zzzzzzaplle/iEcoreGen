import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

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
        
        // Create employees that are not shift workers
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setName("John Sales");
        salesPerson.setDepartment("Sales");
        
        Manager manager = new Manager();
        manager.setName("Jane Manager");
        manager.setDepartment("Management");
        
        OffShiftWorker offShiftWorker = new OffShiftWorker();
        offShiftWorker.setName("Bob OffShift");
        offShiftWorker.setDepartment("Production");
        
        // Add employees to company
        List<Employee> employees = new ArrayList<>();
        employees.add(salesPerson);
        employees.add(manager);
        employees.add(offShiftWorker);
        company.setEmployees(employees);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify result is 0 since there are no shift workers
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_oneShiftWorkerInCompany() {
        // Test Case 2: One shift worker in the company
        // Input: A Company object with a single ShiftWorker having a holidayPremium of 500.00
        // Expected Output: The total holiday premiums should be 500.00
        
        // Create a shift worker with holiday premium of 500.00
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setName("Alice Shift");
        shiftWorker.setDepartment("Delivery");
        shiftWorker.setHolidayPremium(500.00);
        
        // Add shift worker to company
        List<Employee> employees = new ArrayList<>();
        employees.add(shiftWorker);
        company.setEmployees(employees);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify result is 500.00
        assertEquals(500.00, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleShiftWorkersWithDifferentPremiums() {
        // Test Case 3: Multiple shift workers with different premiums
        // Input: A Company object with three ShiftWorkers having holiday premiums of 200.00, 300.00, and 400.00 respectively
        // Expected Output: The total holiday premiums should be 900.00
        
        // Create three shift workers with different holiday premiums
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setName("Worker1");
        shiftWorker1.setDepartment("Delivery");
        shiftWorker1.setHolidayPremium(200.00);
        
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setName("Worker2");
        shiftWorker2.setDepartment("Delivery");
        shiftWorker2.setHolidayPremium(300.00);
        
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setName("Worker3");
        shiftWorker3.setDepartment("Delivery");
        shiftWorker3.setHolidayPremium(400.00);
        
        // Add shift workers to company
        List<Employee> employees = new ArrayList<>();
        employees.add(shiftWorker1);
        employees.add(shiftWorker2);
        employees.add(shiftWorker3);
        company.setEmployees(employees);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify result is 900.00 (200 + 300 + 400)
        assertEquals(900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_multipleShiftWorkersWithSomeZeroPremiums() {
        // Test Case 4: Multiple shift workers with some zero premiums
        // Input: A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively
        // Expected Output: The total holiday premiums should be 400.00
        
        // Create four shift workers with some zero premiums
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setName("Worker1");
        shiftWorker1.setDepartment("Delivery");
        shiftWorker1.setHolidayPremium(0.00);
        
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setName("Worker2");
        shiftWorker2.setDepartment("Delivery");
        shiftWorker2.setHolidayPremium(250.00);
        
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setName("Worker3");
        shiftWorker3.setDepartment("Delivery");
        shiftWorker3.setHolidayPremium(0.00);
        
        ShiftWorker shiftWorker4 = new ShiftWorker();
        shiftWorker4.setName("Worker4");
        shiftWorker4.setDepartment("Delivery");
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
        
        // Verify result is 400.00 (0 + 250 + 0 + 150)
        assertEquals(400.00, result, 0.001);
    }
    
    @Test
    public void testCase5_singleShiftWorkerWithZeroPremium() {
        // Test Case 5: Single shift worker with zero premium
        // Input: A Company object with a single ShiftWorker having a holidayPremium of 0
        // Expected Output: The total holiday premiums should be 0
        
        // Create a shift worker with zero holiday premium
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setName("Zero Premium Worker");
        shiftWorker.setDepartment("Delivery");
        shiftWorker.setHolidayPremium(0.00);
        
        // Add shift worker to company
        List<Employee> employees = new ArrayList<>();
        employees.add(shiftWorker);
        company.setEmployees(employees);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify result is 0.00
        assertEquals(0.00, result, 0.001);
    }
}