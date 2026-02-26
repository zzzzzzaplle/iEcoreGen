import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

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
        
        // Add non-shift worker employees to ensure method ignores them
        Worker regularWorker = new Worker();
        regularWorker.setDepartment("delivery");
        regularWorker.setWeeklyWorkingHours(40.0);
        regularWorker.setHourlyRate(15.0);
        company.addEmployee(regularWorker);
        
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setDepartment("sales");
        salesPerson.setFixedSalary(3000.0);
        salesPerson.setAmountOfSales(10000.0);
        salesPerson.setCommissionPercentage(0.1);
        company.addEmployee(salesPerson);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify result is 0 since there are no shift workers
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_oneShiftWorkerInCompany() {
        // Test Case 2: One shift worker in the company
        // Input: A Company object with a single ShiftWorker having a holidayPremium of 500.00
        // Expected Output: The total holiday premiums should be 500.00
        
        // Create and configure a shift worker
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setDepartment("delivery");
        shiftWorker.setWeeklyWorkingHours(40.0);
        shiftWorker.setHourlyRate(15.0);
        shiftWorker.setHolidayPremium(500.00);
        company.addEmployee(shiftWorker);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify result matches the single shift worker's premium
        assertEquals(500.00, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleShiftWorkersWithDifferentPremiums() {
        // Test Case 3: Multiple shift workers with different premiums
        // Input: A Company object with three ShiftWorkers having holiday premiums of 200.00, 300.00, and 400.00 respectively
        // Expected Output: The total holiday premiums should be 900.00
        
        // Create and configure first shift worker
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setDepartment("delivery");
        shiftWorker1.setWeeklyWorkingHours(40.0);
        shiftWorker1.setHourlyRate(15.0);
        shiftWorker1.setHolidayPremium(200.00);
        company.addEmployee(shiftWorker1);
        
        // Create and configure second shift worker
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setDepartment("production");
        shiftWorker2.setWeeklyWorkingHours(35.0);
        shiftWorker2.setHourlyRate(18.0);
        shiftWorker2.setHolidayPremium(300.00);
        company.addEmployee(shiftWorker2);
        
        // Create and configure third shift worker
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setDepartment("delivery");
        shiftWorker3.setWeeklyWorkingHours(45.0);
        shiftWorker3.setHourlyRate(20.0);
        shiftWorker3.setHolidayPremium(400.00);
        company.addEmployee(shiftWorker3);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify result is the sum of all shift worker premiums
        assertEquals(900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_multipleShiftWorkersWithSomeZeroPremiums() {
        // Test Case 4: Multiple shift workers with some zero premiums
        // Input: A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively
        // Expected Output: The total holiday premiums should be 400.00
        
        // Create and configure first shift worker with zero premium
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setDepartment("delivery");
        shiftWorker1.setWeeklyWorkingHours(40.0);
        shiftWorker1.setHourlyRate(15.0);
        shiftWorker1.setHolidayPremium(0.0);
        company.addEmployee(shiftWorker1);
        
        // Create and configure second shift worker with 250.00 premium
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setDepartment("production");
        shiftWorker2.setWeeklyWorkingHours(35.0);
        shiftWorker2.setHourlyRate(18.0);
        shiftWorker2.setHolidayPremium(250.00);
        company.addEmployee(shiftWorker2);
        
        // Create and configure third shift worker with zero premium
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setDepartment("delivery");
        shiftWorker3.setWeeklyWorkingHours(45.0);
        shiftWorker3.setHourlyRate(20.0);
        shiftWorker3.setHolidayPremium(0.0);
        company.addEmployee(shiftWorker3);
        
        // Create and configure fourth shift worker with 150.00 premium
        ShiftWorker shiftWorker4 = new ShiftWorker();
        shiftWorker4.setDepartment("warehouse");
        shiftWorker4.setWeeklyWorkingHours(38.0);
        shiftWorker4.setHourlyRate(16.0);
        shiftWorker4.setHolidayPremium(150.00);
        company.addEmployee(shiftWorker4);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify result is the sum of non-zero premiums (250.00 + 150.00 = 400.00)
        assertEquals(400.00, result, 0.001);
    }
    
    @Test
    public void testCase5_singleShiftWorkerWithZeroPremium() {
        // Test Case 5: Single shift worker with zero premium
        // Input: A Company object with a single ShiftWorker having a holidayPremium of 0
        // Expected Output: The total holiday premiums should be 0
        
        // Create and configure a shift worker with zero premium
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setDepartment("delivery");
        shiftWorker.setWeeklyWorkingHours(40.0);
        shiftWorker.setHourlyRate(15.0);
        shiftWorker.setHolidayPremium(0.0);
        company.addEmployee(shiftWorker);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify result is 0 since the only shift worker has zero premium
        assertEquals(0.0, result, 0.001);
    }
}