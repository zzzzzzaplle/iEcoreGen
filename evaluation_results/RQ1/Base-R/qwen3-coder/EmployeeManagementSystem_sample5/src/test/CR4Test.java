import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

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
        
        // Add employees that are NOT shift workers to ensure the method handles non-ShiftWorker types correctly
        Worker regularWorker = new Worker();
        regularWorker.setDepartment("delivery");
        regularWorker.setWeeklyWorkingHours(40.0);
        regularWorker.setHourlyRates(15.0);
        company.addEmployee(regularWorker);
        
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setDepartment("sales");
        salesPerson.setSalary(3000.0);
        salesPerson.setAmountOfSales(10000.0);
        salesPerson.setCommissionPercentage(5.0);
        company.addEmployee(salesPerson);
        
        double result = company.calculateTotalHolidayPremiums();
        assertEquals("Total holiday premiums should be 0 when there are no shift workers", 
                     0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_oneShiftWorkerInCompany() {
        // Test Case 2: One shift worker in the company
        // Input: A Company object with a single ShiftWorker having a holidayPremium of 500.00
        // Expected Output: The total holiday premiums should be 500.00
        
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setDepartment("delivery");
        shiftWorker.setWeeklyWorkingHours(40.0);
        shiftWorker.setHourlyRates(15.0);
        shiftWorker.setHolidayPremium(500.00);
        company.addEmployee(shiftWorker);
        
        double result = company.calculateTotalHolidayPremiums();
        assertEquals("Total holiday premiums should be 500.00 for a single shift worker", 
                     500.00, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleShiftWorkersWithDifferentPremiums() {
        // Test Case 3: Multiple shift workers with different premiums
        // Input: A Company object with three ShiftWorkers having holiday premiums of 200.00, 300.00, and 400.00 respectively
        // Expected Output: The total holiday premiums should be 900.00
        
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setDepartment("delivery");
        worker1.setWeeklyWorkingHours(35.0);
        worker1.setHourlyRates(12.0);
        worker1.setHolidayPremium(200.00);
        company.addEmployee(worker1);
        
        ShiftWorker worker2 = new ShiftWorker();
        worker2.setDepartment("delivery");
        worker2.setWeeklyWorkingHours(40.0);
        worker2.setHourlyRates(15.0);
        worker2.setHolidayPremium(300.00);
        company.addEmployee(worker2);
        
        ShiftWorker worker3 = new ShiftWorker();
        worker3.setDepartment("delivery");
        worker3.setWeeklyWorkingHours(38.0);
        worker3.setHourlyRates(14.0);
        worker3.setHolidayPremium(400.00);
        company.addEmployee(worker3);
        
        double result = company.calculateTotalHolidayPremiums();
        assertEquals("Total holiday premiums should be 900.00 for three shift workers with premiums 200, 300, and 400", 
                     900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_multipleShiftWorkersWithSomeZeroPremiums() {
        // Test Case 4: Multiple shift workers with some zero premiums
        // Input: A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively
        // Expected Output: The total holiday premiums should be 400.00
        
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setDepartment("delivery");
        worker1.setWeeklyWorkingHours(35.0);
        worker1.setHourlyRates(12.0);
        worker1.setHolidayPremium(0.0);
        company.addEmployee(worker1);
        
        ShiftWorker worker2 = new ShiftWorker();
        worker2.setDepartment("delivery");
        worker2.setWeeklyWorkingHours(40.0);
        worker2.setHourlyRates(15.0);
        worker2.setHolidayPremium(250.00);
        company.addEmployee(worker2);
        
        ShiftWorker worker3 = new ShiftWorker();
        worker3.setDepartment("delivery");
        worker3.setWeeklyWorkingHours(38.0);
        worker3.setHourlyRates(14.0);
        worker3.setHolidayPremium(0.0);
        company.addEmployee(worker3);
        
        ShiftWorker worker4 = new ShiftWorker();
        worker4.setDepartment("delivery");
        worker4.setWeeklyWorkingHours(42.0);
        worker4.setHourlyRates(16.0);
        worker4.setHolidayPremium(150.00);
        company.addEmployee(worker4);
        
        double result = company.calculateTotalHolidayPremiums();
        assertEquals("Total holiday premiums should be 400.00 for four shift workers with premiums 0, 250, 0, and 150", 
                     400.00, result, 0.001);
    }
    
    @Test
    public void testCase5_singleShiftWorkerWithZeroPremium() {
        // Test Case 5: Single shift worker with zero premium
        // Input: A Company object with a single ShiftWorker having a holidayPremium of 0
        // Expected Output: The total holiday premiums should be 0
        
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setDepartment("delivery");
        shiftWorker.setWeeklyWorkingHours(40.0);
        shiftWorker.setHourlyRates(15.0);
        shiftWorker.setHolidayPremium(0.0);
        company.addEmployee(shiftWorker);
        
        double result = company.calculateTotalHolidayPremiums();
        assertEquals("Total holiday premiums should be 0 for a single shift worker with zero premium", 
                     0.0, result, 0.001);
    }
}