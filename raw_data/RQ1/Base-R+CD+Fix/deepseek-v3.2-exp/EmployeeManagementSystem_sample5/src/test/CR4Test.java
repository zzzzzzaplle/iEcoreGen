import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Company company;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        company = new Company();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_NoShiftWorkersInCompany() {
        // Test Case 1: No shift workers in the company
        // Input: A Company object with an empty list of ShiftWorkers
        // Expected Output: The total holiday premiums should be 0
        
        // Create some non-shift worker employees
        Manager manager = new Manager();
        manager.setName("John Manager");
        manager.setSalary(5000.00);
        
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setName("Jane Sales");
        salesPerson.setSalary(3000.00);
        salesPerson.setAmountOfSales(10000.00);
        salesPerson.setCommissionPercentage(0.1);
        
        OffShiftWorker offShiftWorker = new OffShiftWorker();
        offShiftWorker.setName("Bob OffShift");
        offShiftWorker.setWeeklyWorkingHour(40);
        offShiftWorker.setHourlyRates(25.00);
        
        // Add employees to company
        company.addEmployee(manager);
        company.addEmployee(salesPerson);
        company.addEmployee(offShiftWorker);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify result is 0 since there are no shift workers
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_OneShiftWorkerInCompany() {
        // Test Case 2: One shift worker in the company
        // Input: A Company object with a single ShiftWorker having a holidayPremium of 500.00
        // Expected Output: The total holiday premiums should be 500.00
        
        // Create a shift worker with holiday premium
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setName("Alice Shift");
        shiftWorker.setWeeklyWorkingHour(35);
        shiftWorker.setHourlyRates(20.00);
        shiftWorker.setHolidayPremium(500.00);
        
        // Add shift worker to company
        company.addEmployee(shiftWorker);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify result equals the single shift worker's holiday premium
        assertEquals(500.00, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleShiftWorkersWithDifferentPremiums() {
        // Test Case 3: Multiple shift workers with different premiums
        // Input: A Company object with three ShiftWorkers having holiday premiums of 200.00, 300.00, and 400.00 respectively
        // Expected Output: The total holiday premiums should be 900.00
        
        // Create first shift worker
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setName("Worker 1");
        shiftWorker1.setWeeklyWorkingHour(30);
        shiftWorker1.setHourlyRates(18.00);
        shiftWorker1.setHolidayPremium(200.00);
        
        // Create second shift worker
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setName("Worker 2");
        shiftWorker2.setWeeklyWorkingHour(40);
        shiftWorker2.setHourlyRates(22.00);
        shiftWorker2.setHolidayPremium(300.00);
        
        // Create third shift worker
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setName("Worker 3");
        shiftWorker3.setWeeklyWorkingHour(35);
        shiftWorker3.setHourlyRates(20.00);
        shiftWorker3.setHolidayPremium(400.00);
        
        // Add all shift workers to company
        company.addEmployee(shiftWorker1);
        company.addEmployee(shiftWorker2);
        company.addEmployee(shiftWorker3);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify result equals sum of all holiday premiums (200 + 300 + 400 = 900)
        assertEquals(900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_MultipleShiftWorkersWithSomeZeroPremiums() {
        // Test Case 4: Multiple shift workers with some zero premiums
        // Input: A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively
        // Expected Output: The total holiday premiums should be 400.00
        
        // Create shift workers with zero and non-zero premiums
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setName("Worker A");
        shiftWorker1.setWeeklyWorkingHour(25);
        shiftWorker1.setHourlyRates(15.00);
        shiftWorker1.setHolidayPremium(0.00);
        
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setName("Worker B");
        shiftWorker2.setWeeklyWorkingHour(40);
        shiftWorker2.setHourlyRates(20.00);
        shiftWorker2.setHolidayPremium(250.00);
        
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setName("Worker C");
        shiftWorker3.setWeeklyWorkingHour(35);
        shiftWorker3.setHourlyRates(18.00);
        shiftWorker3.setHolidayPremium(0.00);
        
        ShiftWorker shiftWorker4 = new ShiftWorker();
        shiftWorker4.setName("Worker D");
        shiftWorker4.setWeeklyWorkingHour(30);
        shiftWorker4.setHourlyRates(16.00);
        shiftWorker4.setHolidayPremium(150.00);
        
        // Add all shift workers to company
        company.addEmployee(shiftWorker1);
        company.addEmployee(shiftWorker2);
        company.addEmployee(shiftWorker3);
        company.addEmployee(shiftWorker4);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify result equals sum of non-zero holiday premiums (0 + 250 + 0 + 150 = 400)
        assertEquals(400.00, result, 0.001);
    }
    
    @Test
    public void testCase5_SingleShiftWorkerWithZeroPremium() {
        // Test Case 5: Single shift worker with zero premium
        // Input: A Company object with a single ShiftWorker having a holidayPremium of 0
        // Expected Output: The total holiday premiums should be 0
        
        // Create shift worker with zero holiday premium
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setName("Zero Premium Worker");
        shiftWorker.setWeeklyWorkingHour(40);
        shiftWorker.setHourlyRates(25.00);
        shiftWorker.setHolidayPremium(0.00);
        
        // Add shift worker to company
        company.addEmployee(shiftWorker);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify result is 0 since the single shift worker has zero holiday premium
        assertEquals(0.0, result, 0.001);
    }
}