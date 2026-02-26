import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

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
        
        // Add employees that are not shift workers to ensure the company is not empty
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setAmountOfSales(1000);
        salesPerson.setCommissionPercentage(0.1);
        salesPerson.setSalary(2000);
        company.addEmployee(salesPerson);
        
        OffShiftWorker offShiftWorker = new OffShiftWorker();
        offShiftWorker.setWeeklyWorkingHour(40);
        offShiftWorker.setHourlyRates(15);
        company.addEmployee(offShiftWorker);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify result is 0 when there are no shift workers
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_oneShiftWorkerInCompany() {
        // Test Case 2: "One shift worker in the company"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 500.00"
        // Expected Output: "The total holiday premiums should be 500.00"
        
        // Create and configure a shift worker with holiday premium of 500.00
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setHolidayPremium(500.00);
        shiftWorker.setWeeklyWorkingHour(40);
        shiftWorker.setHourlyRates(20);
        company.addEmployee(shiftWorker);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify result matches expected premium
        assertEquals(500.00, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleShiftWorkersWithDifferentPremiums() {
        // Test Case 3: "Multiple shift workers with different premiums"
        // Input: "A Company object with three ShiftWorkers having holiday premiums of 200.00, 300.00, and 400.00 respectively"
        // Expected Output: "The total holiday premiums should be 900.00"
        
        // Create first shift worker with premium 200.00
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setHolidayPremium(200.00);
        shiftWorker1.setWeeklyWorkingHour(35);
        shiftWorker1.setHourlyRates(18);
        company.addEmployee(shiftWorker1);
        
        // Create second shift worker with premium 300.00
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setHolidayPremium(300.00);
        shiftWorker2.setWeeklyWorkingHour(40);
        shiftWorker2.setHourlyRates(22);
        company.addEmployee(shiftWorker2);
        
        // Create third shift worker with premium 400.00
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setHolidayPremium(400.00);
        shiftWorker3.setWeeklyWorkingHour(38);
        shiftWorker3.setHourlyRates(25);
        company.addEmployee(shiftWorker3);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify sum of premiums (200 + 300 + 400 = 900)
        assertEquals(900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_multipleShiftWorkersWithSomeZeroPremiums() {
        // Test Case 4: "Multiple shift workers with some zero premiums"
        // Input: "A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively"
        // Expected Output: "The total holiday premiums should be 400.00"
        
        // Create first shift worker with premium 0
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setHolidayPremium(0);
        shiftWorker1.setWeeklyWorkingHour(35);
        shiftWorker1.setHourlyRates(18);
        company.addEmployee(shiftWorker1);
        
        // Create second shift worker with premium 250.00
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setHolidayPremium(250.00);
        shiftWorker2.setWeeklyWorkingHour(40);
        shiftWorker2.setHourlyRates(22);
        company.addEmployee(shiftWorker2);
        
        // Create third shift worker with premium 0
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setHolidayPremium(0);
        shiftWorker3.setWeeklyWorkingHour(38);
        shiftWorker3.setHourlyRates(25);
        company.addEmployee(shiftWorker3);
        
        // Create fourth shift worker with premium 150.00
        ShiftWorker shiftWorker4 = new ShiftWorker();
        shiftWorker4.setHolidayPremium(150.00);
        shiftWorker4.setWeeklyWorkingHour(42);
        shiftWorker4.setHourlyRates(20);
        company.addEmployee(shiftWorker4);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify sum of non-zero premiums (0 + 250 + 0 + 150 = 400)
        assertEquals(400.00, result, 0.001);
    }
    
    @Test
    public void testCase5_singleShiftWorkerWithZeroPremium() {
        // Test Case 5: "Single shift worker with zero premium"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 0"
        // Expected Output: "The total holiday premiums should be 0"
        
        // Create shift worker with zero premium
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setHolidayPremium(0);
        shiftWorker.setWeeklyWorkingHour(40);
        shiftWorker.setHourlyRates(20);
        company.addEmployee(shiftWorker);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify result is 0 for zero premium
        assertEquals(0.0, result, 0.001);
    }
}