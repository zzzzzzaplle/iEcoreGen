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
        
        // Create a department (Delivery department as specified in the requirement)
        Department deliveryDept = new Department();
        deliveryDept.setName("Delivery");
        
        // Add some non-shift workers to ensure the department has employees but no shift workers
        NonShiftWorker worker1 = new NonShiftWorker();
        worker1.setWeeklyWorkingHours(40);
        worker1.setHourlyRates(25.0);
        deliveryDept.addEmployee(worker1);
        
        Salesperson salesperson = new Salesperson();
        salesperson.setSalary(3000);
        salesperson.setAmountOfSales(10000);
        salesperson.setCommissionPercentage(0.1);
        deliveryDept.addEmployee(salesperson);
        
        company.addDepartment(deliveryDept);
        company.addEmployee(worker1);
        company.addEmployee(salesperson);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify the result is 0 since there are no shift workers
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_oneShiftWorkerInCompany() {
        // Test Case 2: One shift worker in the company
        // Input: A Company object with a single ShiftWorker having a holidayPremium of 500.00
        // Expected Output: The total holiday premiums should be 500.00
        
        // Create a department
        Department deliveryDept = new Department();
        deliveryDept.setName("Delivery");
        
        // Create and configure a shift worker with holiday premium of 500.00
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setWeeklyWorkingHours(40);
        shiftWorker.setHourlyRates(20.0);
        shiftWorker.setHolidayPremiums(500.00);
        
        deliveryDept.addEmployee(shiftWorker);
        company.addDepartment(deliveryDept);
        company.addEmployee(shiftWorker);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify the result is 500.00
        assertEquals(500.00, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleShiftWorkersWithDifferentPremiums() {
        // Test Case 3: Multiple shift workers with different premiums
        // Input: A Company object with three ShiftWorkers having holiday premiums of 200.00, 300.00, and 400.00 respectively
        // Expected Output: The total holiday premiums should be 900.00
        
        // Create a department
        Department deliveryDept = new Department();
        deliveryDept.setName("Delivery");
        
        // Create and configure three shift workers with different holiday premiums
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setWeeklyWorkingHours(35);
        worker1.setHourlyRates(18.0);
        worker1.setHolidayPremiums(200.00);
        
        ShiftWorker worker2 = new ShiftWorker();
        worker2.setWeeklyWorkingHours(40);
        worker2.setHourlyRates(22.0);
        worker2.setHolidayPremiums(300.00);
        
        ShiftWorker worker3 = new ShiftWorker();
        worker3.setWeeklyWorkingHours(38);
        worker3.setHourlyRates(25.0);
        worker3.setHolidayPremiums(400.00);
        
        // Add workers to department and company
        deliveryDept.addEmployee(worker1);
        deliveryDept.addEmployee(worker2);
        deliveryDept.addEmployee(worker3);
        
        company.addDepartment(deliveryDept);
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        company.addEmployee(worker3);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify the result is 900.00 (200 + 300 + 400)
        assertEquals(900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_multipleShiftWorkersWithSomeZeroPremiums() {
        // Test Case 4: Multiple shift workers with some zero premiums
        // Input: A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively
        // Expected Output: The total holiday premiums should be 400.00
        
        // Create a department
        Department deliveryDept = new Department();
        deliveryDept.setName("Delivery");
        
        // Create and configure four shift workers with some zero premiums
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setWeeklyWorkingHours(35);
        worker1.setHourlyRates(18.0);
        worker1.setHolidayPremiums(0);
        
        ShiftWorker worker2 = new ShiftWorker();
        worker2.setWeeklyWorkingHours(40);
        worker2.setHourlyRates(22.0);
        worker2.setHolidayPremiums(250.00);
        
        ShiftWorker worker3 = new ShiftWorker();
        worker3.setWeeklyWorkingHours(38);
        worker3.setHourlyRates(25.0);
        worker3.setHolidayPremiums(0);
        
        ShiftWorker worker4 = new ShiftWorker();
        worker4.setWeeklyWorkingHours(42);
        worker4.setHourlyRates(20.0);
        worker4.setHolidayPremiums(150.00);
        
        // Add workers to department and company
        deliveryDept.addEmployee(worker1);
        deliveryDept.addEmployee(worker2);
        deliveryDept.addEmployee(worker3);
        deliveryDept.addEmployee(worker4);
        
        company.addDepartment(deliveryDept);
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        company.addEmployee(worker3);
        company.addEmployee(worker4);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify the result is 400.00 (0 + 250 + 0 + 150)
        assertEquals(400.00, result, 0.001);
    }
    
    @Test
    public void testCase5_singleShiftWorkerWithZeroPremium() {
        // Test Case 5: Single shift worker with zero premium
        // Input: A Company object with a single ShiftWorker having a holidayPremium of 0
        // Expected Output: The total holiday premiums should be 0
        
        // Create a department
        Department deliveryDept = new Department();
        deliveryDept.setName("Delivery");
        
        // Create and configure a shift worker with zero holiday premium
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setWeeklyWorkingHours(40);
        shiftWorker.setHourlyRates(20.0);
        shiftWorker.setHolidayPremiums(0);
        
        deliveryDept.addEmployee(shiftWorker);
        company.addDepartment(deliveryDept);
        company.addEmployee(shiftWorker);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify the result is 0
        assertEquals(0.0, result, 0.001);
    }
}