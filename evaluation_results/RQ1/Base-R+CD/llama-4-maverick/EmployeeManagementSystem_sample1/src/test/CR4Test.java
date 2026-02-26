import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_noShiftWorkersInCompany() {
        // Test Case 1: "No shift workers in the company"
        // Input: "A Company object with an empty list of ShiftWorkers"
        // Expected Output: "The total holiday premiums should be 0"
        
        // Setup: Create company with employees that are not shift workers
        List<Employee> employees = new ArrayList<>();
        
        // Add a Manager
        Manager manager = new Manager();
        manager.setSalary(50000);
        employees.add(manager);
        
        // Add a SalesPerson
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(30000);
        salesPerson.setAmountOfSales(100000);
        salesPerson.setCommissionPercentage(0.1);
        employees.add(salesPerson);
        
        // Add an OffShiftWorker
        OffShiftWorker offShiftWorker = new OffShiftWorker();
        offShiftWorker.setWeeklyWorkingHour(40);
        offShiftWorker.setHourlyRates(20);
        employees.add(offShiftWorker);
        
        company.setEmployees(employees);
        
        // Execute and Verify
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals("Total holiday premiums should be 0 when no shift workers exist", 0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_oneShiftWorkerInCompany() {
        // Test Case 2: "One shift worker in the company"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 500.00"
        // Expected Output: "The total holiday premiums should be 500.00"
        
        // Setup: Create company with one shift worker
        List<Employee> employees = new ArrayList<>();
        
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setWeeklyWorkingHour(40);
        shiftWorker.setHourlyRates(25);
        shiftWorker.setHolidayPremium(500.00);
        employees.add(shiftWorker);
        
        company.setEmployees(employees);
        
        // Execute and Verify
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals("Total holiday premiums should be 500.00 for one shift worker", 500.00, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleShiftWorkersWithDifferentPremiums() {
        // Test Case 3: "Multiple shift workers with different premiums"
        // Input: "A Company object with three ShiftWorkers having holiday premiums of 200.00, 300.00, and 400.00 respectively"
        // Expected Output: "The total holiday premiums should be 900.00"
        
        // Setup: Create company with three shift workers with different premiums
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
        
        // Execute and Verify
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals("Total holiday premiums should be 900.00 for three shift workers", 900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_multipleShiftWorkersWithSomeZeroPremiums() {
        // Test Case 4: "Multiple shift workers with some zero premiums"
        // Input: "A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively"
        // Expected Output: "The total holiday premiums should be 400.00"
        
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
        
        // Execute and Verify
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals("Total holiday premiums should be 400.00 for mixed premium values", 400.00, result, 0.001);
    }
    
    @Test
    public void testCase5_singleShiftWorkerWithZeroPremium() {
        // Test Case 5: "Single shift worker with zero premium"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 0"
        // Expected Output: "The total holiday premiums should be 0"
        
        // Setup: Create company with one shift worker with zero premium
        List<Employee> employees = new ArrayList<>();
        
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setWeeklyWorkingHour(35);
        shiftWorker.setHourlyRates(18);
        shiftWorker.setHolidayPremium(0.00);
        employees.add(shiftWorker);
        
        company.setEmployees(employees);
        
        // Execute and Verify
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals("Total holiday premiums should be 0 for shift worker with zero premium", 0.00, result, 0.001);
    }
}