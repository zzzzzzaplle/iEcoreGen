import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;

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
        
        // Create and add employees that are NOT shift workers
        Manager manager = new Manager();
        manager.setName("John Manager");
        manager.setSalary(5000.00);
        company.addEmployee(manager);
        
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setName("Jane Sales");
        salesPerson.setSalary(2000.00);
        salesPerson.setAmountOfSales(10000.00);
        salesPerson.setCommissionPercentage(0.1);
        company.addEmployee(salesPerson);
        
        OffShiftWorker offShiftWorker = new OffShiftWorker();
        offShiftWorker.setName("Bob OffShift");
        offShiftWorker.setWeeklyWorkingHour(40);
        offShiftWorker.setHourlyRates(15.00);
        company.addEmployee(offShiftWorker);
        
        // Calculate total holiday premiums - should be 0 since no shift workers exist
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_oneShiftWorkerInCompany() {
        // Test Case 2: "One shift worker in the company"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 500.00"
        // Expected Output: "The total holiday premiums should be 500.00"
        
        // Create and add a shift worker with holiday premium of 500.00
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setName("Alice Shift");
        shiftWorker.setWeeklyWorkingHour(40);
        shiftWorker.setHourlyRates(20.00);
        shiftWorker.setHolidayPremium(500.00);
        company.addEmployee(shiftWorker);
        
        // Calculate total holiday premiums - should be exactly 500.00
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals(500.00, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleShiftWorkersWithDifferentPremiums() {
        // Test Case 3: "Multiple shift workers with different premiums"
        // Input: "A Company object with three ShiftWorkers having holiday premiums of 200.00, 300.00, and 400.00 respectively"
        // Expected Output: "The total holiday premiums should be 900.00"
        
        // Create and add first shift worker with holiday premium of 200.00
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setName("Worker 1");
        shiftWorker1.setWeeklyWorkingHour(35);
        shiftWorker1.setHourlyRates(18.00);
        shiftWorker1.setHolidayPremium(200.00);
        company.addEmployee(shiftWorker1);
        
        // Create and add second shift worker with holiday premium of 300.00
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setName("Worker 2");
        shiftWorker2.setWeeklyWorkingHour(40);
        shiftWorker2.setHourlyRates(22.00);
        shiftWorker2.setHolidayPremium(300.00);
        company.addEmployee(shiftWorker2);
        
        // Create and add third shift worker with holiday premium of 400.00
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setName("Worker 3");
        shiftWorker3.setWeeklyWorkingHour(38);
        shiftWorker3.setHourlyRates(20.00);
        shiftWorker3.setHolidayPremium(400.00);
        company.addEmployee(shiftWorker3);
        
        // Calculate total holiday premiums - should be 200 + 300 + 400 = 900.00
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals(900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_multipleShiftWorkersWithSomeZeroPremiums() {
        // Test Case 4: "Multiple shift workers with some zero premiums"
        // Input: "A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively"
        // Expected Output: "The total holiday premiums should be 400.00"
        
        // Create and add first shift worker with holiday premium of 0
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setName("Worker 1");
        shiftWorker1.setWeeklyWorkingHour(35);
        shiftWorker1.setHourlyRates(18.00);
        shiftWorker1.setHolidayPremium(0);
        company.addEmployee(shiftWorker1);
        
        // Create and add second shift worker with holiday premium of 250.00
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setName("Worker 2");
        shiftWorker2.setWeeklyWorkingHour(40);
        shiftWorker2.setHourlyRates(22.00);
        shiftWorker2.setHolidayPremium(250.00);
        company.addEmployee(shiftWorker2);
        
        // Create and add third shift worker with holiday premium of 0
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setName("Worker 3");
        shiftWorker3.setWeeklyWorkingHour(38);
        shiftWorker3.setHourlyRates(20.00);
        shiftWorker3.setHolidayPremium(0);
        company.addEmployee(shiftWorker3);
        
        // Create and add fourth shift worker with holiday premium of 150.00
        ShiftWorker shiftWorker4 = new ShiftWorker();
        shiftWorker4.setName("Worker 4");
        shiftWorker4.setWeeklyWorkingHour(42);
        shiftWorker4.setHourlyRates(25.00);
        shiftWorker4.setHolidayPremium(150.00);
        company.addEmployee(shiftWorker4);
        
        // Calculate total holiday premiums - should be 0 + 250 + 0 + 150 = 400.00
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals(400.00, result, 0.001);
    }
    
    @Test
    public void testCase5_singleShiftWorkerWithZeroPremium() {
        // Test Case 5: "Single shift worker with zero premium"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 0"
        // Expected Output: "The total holiday premiums should be 0"
        
        // Create and add a shift worker with holiday premium of 0
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setName("Zero Premium Worker");
        shiftWorker.setWeeklyWorkingHour(40);
        shiftWorker.setHourlyRates(20.00);
        shiftWorker.setHolidayPremium(0);
        company.addEmployee(shiftWorker);
        
        // Calculate total holiday premiums - should be 0
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals(0.0, result, 0.001);
    }
}