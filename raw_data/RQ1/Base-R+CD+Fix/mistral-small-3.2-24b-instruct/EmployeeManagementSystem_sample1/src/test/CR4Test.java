import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;

public class CR4Test {
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize a new Company before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_noShiftWorkersInCompany() {
        // Test Case 1: No shift workers in the company
        // Input: A Company object with an empty list of ShiftWorkers
        // Expected Output: The total holiday premiums should be 0
        
        // Add non-shift worker employees to ensure they don't affect the calculation
        Manager manager = new Manager();
        manager.setSalary(50000);
        company.getEmployees().add(manager);
        
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(30000);
        salesPerson.setAmountOfSales(100000);
        salesPerson.setCommissionPercentage(0.1);
        company.getEmployees().add(salesPerson);
        
        OffShiftWorker offShiftWorker = new OffShiftWorker();
        offShiftWorker.setWeeklyWorkingHour(40);
        offShiftWorker.setHourlyRates(25);
        company.getEmployees().add(offShiftWorker);
        
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
        
        // Create and configure a shift worker
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setHolidayPremium(500.00);
        shiftWorker.setWeeklyWorkingHour(40);
        shiftWorker.setHourlyRates(20);
        
        // Add the shift worker to the company
        company.getEmployees().add(shiftWorker);
        
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
        
        // Create and configure three shift workers with different premiums
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setHolidayPremium(200.00);
        worker1.setWeeklyWorkingHour(35);
        worker1.setHourlyRates(18);
        
        ShiftWorker worker2 = new ShiftWorker();
        worker2.setHolidayPremium(300.00);
        worker2.setWeeklyWorkingHour(40);
        worker2.setHourlyRates(22);
        
        ShiftWorker worker3 = new ShiftWorker();
        worker3.setHolidayPremium(400.00);
        worker3.setWeeklyWorkingHour(38);
        worker3.setHourlyRates(20);
        
        // Add all shift workers to the company
        company.getEmployees().add(worker1);
        company.getEmployees().add(worker2);
        company.getEmployees().add(worker3);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify the result is the sum of all premiums (200 + 300 + 400 = 900)
        assertEquals(900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_multipleShiftWorkersWithSomeZeroPremiums() {
        // Test Case 4: Multiple shift workers with some zero premiums
        // Input: A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively
        // Expected Output: The total holiday premiums should be 400.00
        
        // Create and configure four shift workers with mixed premiums
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setHolidayPremium(0);
        worker1.setWeeklyWorkingHour(30);
        worker1.setHourlyRates(15);
        
        ShiftWorker worker2 = new ShiftWorker();
        worker2.setHolidayPremium(250.00);
        worker2.setWeeklyWorkingHour(40);
        worker2.setHourlyRates(20);
        
        ShiftWorker worker3 = new ShiftWorker();
        worker3.setHolidayPremium(0);
        worker3.setWeeklyWorkingHour(35);
        worker3.setHourlyRates(18);
        
        ShiftWorker worker4 = new ShiftWorker();
        worker4.setHolidayPremium(150.00);
        worker4.setWeeklyWorkingHour(42);
        worker4.setHourlyRates(25);
        
        // Add all shift workers to the company
        company.getEmployees().add(worker1);
        company.getEmployees().add(worker2);
        company.getEmployees().add(worker3);
        company.getEmployees().add(worker4);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify the result is the sum of non-zero premiums (0 + 250 + 0 + 150 = 400)
        assertEquals(400.00, result, 0.001);
    }
    
    @Test
    public void testCase5_singleShiftWorkerWithZeroPremium() {
        // Test Case 5: Single shift worker with zero premium
        // Input: A Company object with a single ShiftWorker having a holidayPremium of 0
        // Expected Output: The total holiday premiums should be 0
        
        // Create and configure a shift worker with zero premium
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setHolidayPremium(0);
        shiftWorker.setWeeklyWorkingHour(40);
        shiftWorker.setHourlyRates(20);
        
        // Add the shift worker to the company
        company.getEmployees().add(shiftWorker);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify the result is 0
        assertEquals(0.0, result, 0.001);
    }
}