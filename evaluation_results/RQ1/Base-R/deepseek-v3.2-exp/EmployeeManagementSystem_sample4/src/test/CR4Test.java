import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Reset company before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_noShiftWorkersInCompany() {
        // Test Case 1: "No shift workers in the company"
        // Input: "A Company object with an empty list of ShiftWorkers"
        // Expected Output: "The total holiday premiums should be 0"
        
        // Create a delivery department but with no shift workers
        DeliveryDepartment deliveryDept = new DeliveryDepartment();
        
        // Add some non-shift workers to delivery department
        NonShiftWorker nonShiftWorker1 = new NonShiftWorker();
        nonShiftWorker1.setWeeklyWorkingHours(40);
        nonShiftWorker1.setHourlyRate(25.0);
        nonShiftWorker1.setHolidayPremiums(100.0); // This should NOT be counted
        
        deliveryDept.getEmployees().add(nonShiftWorker1);
        company.getDepartments().add(deliveryDept);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify result is 0 since there are no shift workers
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_oneShiftWorkerInCompany() {
        // Test Case 2: "One shift worker in the company"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 500.00"
        // Expected Output: "The total holiday premiums should be 500.00"
        
        // Create delivery department
        DeliveryDepartment deliveryDept = new DeliveryDepartment();
        
        // Create and configure a shift worker with holiday premium of 500.00
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setName("John Doe");
        shiftWorker.setWeeklyWorkingHours(40);
        shiftWorker.setHourlyRate(20.0);
        shiftWorker.setHolidayPremiums(500.00);
        
        deliveryDept.getEmployees().add(shiftWorker);
        company.getDepartments().add(deliveryDept);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify result is 500.00
        assertEquals(500.00, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleShiftWorkersWithDifferentPremiums() {
        // Test Case 3: "Multiple shift workers with different premiums"
        // Input: "A Company object with three ShiftWorkers having holiday premiums of 200.00, 300.00, and 400.00 respectively"
        // Expected Output: "The total holiday premiums should be 900.00"
        
        // Create delivery department
        DeliveryDepartment deliveryDept = new DeliveryDepartment();
        
        // Create and configure three shift workers with different holiday premiums
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setName("Worker 1");
        shiftWorker1.setWeeklyWorkingHours(35);
        shiftWorker1.setHourlyRate(18.0);
        shiftWorker1.setHolidayPremiums(200.00);
        
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setName("Worker 2");
        shiftWorker2.setWeeklyWorkingHours(40);
        shiftWorker2.setHourlyRate(22.0);
        shiftWorker2.setHolidayPremiums(300.00);
        
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setName("Worker 3");
        shiftWorker3.setWeeklyWorkingHours(38);
        shiftWorker3.setHourlyRate(20.0);
        shiftWorker3.setHolidayPremiums(400.00);
        
        // Add all shift workers to delivery department
        deliveryDept.getEmployees().add(shiftWorker1);
        deliveryDept.getEmployees().add(shiftWorker2);
        deliveryDept.getEmployees().add(shiftWorker3);
        company.getDepartments().add(deliveryDept);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify result is 900.00 (200 + 300 + 400)
        assertEquals(900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_multipleShiftWorkersWithSomeZeroPremiums() {
        // Test Case 4: "Multiple shift workers with some zero premiums"
        // Input: "A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively"
        // Expected Output: "The total holiday premiums should be 400.00"
        
        // Create delivery department
        DeliveryDepartment deliveryDept = new DeliveryDepartment();
        
        // Create and configure four shift workers with specified holiday premiums
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setName("Worker 1");
        shiftWorker1.setWeeklyWorkingHours(35);
        shiftWorker1.setHourlyRate(18.0);
        shiftWorker1.setHolidayPremiums(0.00);
        
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setName("Worker 2");
        shiftWorker2.setWeeklyWorkingHours(40);
        shiftWorker2.setHourlyRate(22.0);
        shiftWorker2.setHolidayPremiums(250.00);
        
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setName("Worker 3");
        shiftWorker3.setWeeklyWorkingHours(38);
        shiftWorker3.setHourlyRate(20.0);
        shiftWorker3.setHolidayPremiums(0.00);
        
        ShiftWorker shiftWorker4 = new ShiftWorker();
        shiftWorker4.setName("Worker 4");
        shiftWorker4.setWeeklyWorkingHours(42);
        shiftWorker4.setHourlyRate(25.0);
        shiftWorker4.setHolidayPremiums(150.00);
        
        // Add all shift workers to delivery department
        deliveryDept.getEmployees().add(shiftWorker1);
        deliveryDept.getEmployees().add(shiftWorker2);
        deliveryDept.getEmployees().add(shiftWorker3);
        deliveryDept.getEmployees().add(shiftWorker4);
        company.getDepartments().add(deliveryDept);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify result is 400.00 (0 + 250 + 0 + 150)
        assertEquals(400.00, result, 0.001);
    }
    
    @Test
    public void testCase5_singleShiftWorkerWithZeroPremium() {
        // Test Case 5: "Single shift worker with zero premium"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 0"
        // Expected Output: "The total holiday premiums should be 0"
        
        // Create delivery department
        DeliveryDepartment deliveryDept = new DeliveryDepartment();
        
        // Create and configure a shift worker with zero holiday premium
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setName("John Doe");
        shiftWorker.setWeeklyWorkingHours(40);
        shiftWorker.setHourlyRate(20.0);
        shiftWorker.setHolidayPremiums(0.00);
        
        deliveryDept.getEmployees().add(shiftWorker);
        company.getDepartments().add(deliveryDept);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify result is 0.00
        assertEquals(0.00, result, 0.001);
    }
}