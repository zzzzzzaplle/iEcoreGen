import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;

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
        
        // Add some non-ShiftWorker employees to ensure the method correctly ignores them
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(3000.00);
        salesPerson.setAmountOfSales(10000.00);
        salesPerson.setCommissionPercentage(0.1);
        company.addEmployee(salesPerson);
        
        OffShiftWorker offShiftWorker = new OffShiftWorker();
        offShiftWorker.setWeeklyWorkingHour(40);
        offShiftWorker.setHourlyRates(25.00);
        company.addEmployee(offShiftWorker);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify the result is 0 since there are no shift workers
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_oneShiftWorkerInCompany() {
        // Test Case 2: One shift worker in the company
        // Input: A Company object with a single ShiftWorker having a holidayPremium of 500.00
        // Expected Output: The total holiday premiums should be 500.00
        
        // Create and configure a shift worker with holiday premium
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setWeeklyWorkingHour(40);
        shiftWorker.setHourlyRates(20.00);
        shiftWorker.setHolidayPremium(500.00);
        company.addEmployee(shiftWorker);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify the result matches the expected premium
        assertEquals(500.00, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleShiftWorkersWithDifferentPremiums() {
        // Test Case 3: Multiple shift workers with different premiums
        // Input: A Company object with three ShiftWorkers having holiday premiums of 200.00, 300.00, and 400.00 respectively
        // Expected Output: The total holiday premiums should be 900.00
        
        // Create and configure first shift worker
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setWeeklyWorkingHour(35);
        shiftWorker1.setHourlyRates(18.00);
        shiftWorker1.setHolidayPremium(200.00);
        company.addEmployee(shiftWorker1);
        
        // Create and configure second shift worker
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setWeeklyWorkingHour(40);
        shiftWorker2.setHourlyRates(22.00);
        shiftWorker2.setHolidayPremium(300.00);
        company.addEmployee(shiftWorker2);
        
        // Create and configure third shift worker
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setWeeklyWorkingHour(38);
        shiftWorker3.setHourlyRates(20.00);
        shiftWorker3.setHolidayPremium(400.00);
        company.addEmployee(shiftWorker3);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify the result matches the sum of all premiums
        assertEquals(900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_multipleShiftWorkersWithSomeZeroPremiums() {
        // Test Case 4: Multiple shift workers with some zero premiums
        // Input: A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively
        // Expected Output: The total holiday premiums should be 400.00
        
        // Create and configure first shift worker with zero premium
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setWeeklyWorkingHour(35);
        shiftWorker1.setHourlyRates(18.00);
        shiftWorker1.setHolidayPremium(0.00);
        company.addEmployee(shiftWorker1);
        
        // Create and configure second shift worker with 250.00 premium
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setWeeklyWorkingHour(40);
        shiftWorker2.setHourlyRates(22.00);
        shiftWorker2.setHolidayPremium(250.00);
        company.addEmployee(shiftWorker2);
        
        // Create and configure third shift worker with zero premium
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setWeeklyWorkingHour(38);
        shiftWorker3.setHourlyRates(20.00);
        shiftWorker3.setHolidayPremium(0.00);
        company.addEmployee(shiftWorker3);
        
        // Create and configure fourth shift worker with 150.00 premium
        ShiftWorker shiftWorker4 = new ShiftWorker();
        shiftWorker4.setWeeklyWorkingHour(42);
        shiftWorker4.setHourlyRates(25.00);
        shiftWorker4.setHolidayPremium(150.00);
        company.addEmployee(shiftWorker4);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify the result matches the sum of non-zero premiums
        assertEquals(400.00, result, 0.001);
    }
    
    @Test
    public void testCase5_singleShiftWorkerWithZeroPremium() {
        // Test Case 5: Single shift worker with zero premium
        // Input: A Company object with a single ShiftWorker having a holidayPremium of 0
        // Expected Output: The total holiday premiums should be 0
        
        // Create and configure a shift worker with zero holiday premium
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setWeeklyWorkingHour(40);
        shiftWorker.setHourlyRates(20.00);
        shiftWorker.setHolidayPremium(0.00);
        company.addEmployee(shiftWorker);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify the result is 0 for zero premium
        assertEquals(0.0, result, 0.001);
    }
}