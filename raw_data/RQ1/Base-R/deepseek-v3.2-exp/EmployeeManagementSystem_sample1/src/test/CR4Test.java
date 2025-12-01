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
        // Test Case 1: No shift workers in the company
        // Input: A Company object with an empty list of ShiftWorkers
        // Expected Output: The total holiday premiums should be 0
        
        // Add non-ShiftWorker employees to ensure the method handles them correctly
        Worker regularWorker = new Worker();
        regularWorker.setDepartment("delivery");
        regularWorker.setWeeklyWorkingHours(40);
        regularWorker.setHourlyRate(20);
        company.addEmployee(regularWorker);
        
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setDepartment("sales");
        salesPerson.setFixedSalary(3000);
        salesPerson.setAmountOfSales(10000);
        salesPerson.setCommissionPercentage(0.1);
        company.addEmployee(salesPerson);
        
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
        
        // Create a shift worker with delivery department and holiday premium
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setDepartment("delivery");
        shiftWorker.setWeeklyWorkingHours(40);
        shiftWorker.setHourlyRate(25);
        shiftWorker.setHolidayPremium(500.00);
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
        
        // Create first shift worker
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setDepartment("delivery");
        shiftWorker1.setWeeklyWorkingHours(35);
        shiftWorker1.setHourlyRate(22);
        shiftWorker1.setHolidayPremium(200.00);
        company.addEmployee(shiftWorker1);
        
        // Create second shift worker
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setDepartment("delivery");
        shiftWorker2.setWeeklyWorkingHours(40);
        shiftWorker2.setHourlyRate(20);
        shiftWorker2.setHolidayPremium(300.00);
        company.addEmployee(shiftWorker2);
        
        // Create third shift worker
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setDepartment("delivery");
        shiftWorker3.setWeeklyWorkingHours(38);
        shiftWorker3.setHourlyRate(18);
        shiftWorker3.setHolidayPremium(400.00);
        company.addEmployee(shiftWorker3);
        
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
        
        // Create first shift worker with zero premium
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setDepartment("delivery");
        shiftWorker1.setWeeklyWorkingHours(35);
        shiftWorker1.setHourlyRate(22);
        shiftWorker1.setHolidayPremium(0);
        company.addEmployee(shiftWorker1);
        
        // Create second shift worker with 250.00 premium
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setDepartment("delivery");
        shiftWorker2.setWeeklyWorkingHours(40);
        shiftWorker2.setHourlyRate(20);
        shiftWorker2.setHolidayPremium(250.00);
        company.addEmployee(shiftWorker2);
        
        // Create third shift worker with zero premium
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setDepartment("delivery");
        shiftWorker3.setWeeklyWorkingHours(38);
        shiftWorker3.setHourlyRate(18);
        shiftWorker3.setHolidayPremium(0);
        company.addEmployee(shiftWorker3);
        
        // Create fourth shift worker with 150.00 premium
        ShiftWorker shiftWorker4 = new ShiftWorker();
        shiftWorker4.setDepartment("delivery");
        shiftWorker4.setWeeklyWorkingHours(42);
        shiftWorker4.setHourlyRate(19);
        shiftWorker4.setHolidayPremium(150.00);
        company.addEmployee(shiftWorker4);
        
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
        
        // Create a shift worker with zero holiday premium
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setDepartment("delivery");
        shiftWorker.setWeeklyWorkingHours(40);
        shiftWorker.setHourlyRate(25);
        shiftWorker.setHolidayPremium(0);
        company.addEmployee(shiftWorker);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify the result is 0
        assertEquals(0.0, result, 0.001);
    }
}