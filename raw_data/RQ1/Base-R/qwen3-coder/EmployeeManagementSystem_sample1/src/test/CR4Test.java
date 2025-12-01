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
        
        // Create and add employees that are NOT shift workers
        Worker regularWorker = new Worker();
        regularWorker.setWeeklyWorkingHours(40);
        regularWorker.setHourlyRate(20);
        regularWorker.setDepartment("delivery");
        company.getEmployees().add(regularWorker);
        
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setFixedSalary(3000);
        salesPerson.setAmountOfSales(10000);
        salesPerson.setCommissionPercentage(5);
        salesPerson.setDepartment("sales");
        company.getEmployees().add(salesPerson);
        
        Manager manager = new Manager();
        manager.setFixedSalary(5000);
        manager.setPosition("Senior Manager");
        manager.setDepartment("management");
        company.getEmployees().add(manager);
        
        // Calculate total holiday premiums - should be 0 since no shift workers
        double result = company.calculateTotalHolidayPremiums();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_oneShiftWorkerInCompany() {
        // Test Case 2: One shift worker in the company
        // Input: A Company object with a single ShiftWorker having a holidayPremium of 500.00
        // Expected Output: The total holiday premiums should be 500.00
        
        // Create and add a shift worker with holiday premium
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setWeeklyWorkingHours(40);
        shiftWorker.setHourlyRate(25);
        shiftWorker.setHolidayPremium(500.00);
        shiftWorker.setDepartment("production");
        company.getEmployees().add(shiftWorker);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        assertEquals(500.00, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleShiftWorkersWithDifferentPremiums() {
        // Test Case 3: Multiple shift workers with different premiums
        // Input: A Company object with three ShiftWorkers having holiday premiums of 200.00, 300.00, and 400.00 respectively
        // Expected Output: The total holiday premiums should be 900.00
        
        // Create and add first shift worker
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setWeeklyWorkingHours(35);
        shiftWorker1.setHourlyRate(22);
        shiftWorker1.setHolidayPremium(200.00);
        shiftWorker1.setDepartment("delivery");
        company.getEmployees().add(shiftWorker1);
        
        // Create and add second shift worker
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setWeeklyWorkingHours(40);
        shiftWorker2.setHourlyRate(24);
        shiftWorker2.setHolidayPremium(300.00);
        shiftWorker2.setDepartment("production");
        company.getEmployees().add(shiftWorker2);
        
        // Create and add third shift worker
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setWeeklyWorkingHours(38);
        shiftWorker3.setHourlyRate(26);
        shiftWorker3.setHolidayPremium(400.00);
        shiftWorker3.setDepartment("maintenance");
        company.getEmployees().add(shiftWorker3);
        
        // Calculate total holiday premiums (200 + 300 + 400 = 900)
        double result = company.calculateTotalHolidayPremiums();
        assertEquals(900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_multipleShiftWorkersWithSomeZeroPremiums() {
        // Test Case 4: Multiple shift workers with some zero premiums
        // Input: A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively
        // Expected Output: The total holiday premiums should be 400.00
        
        // Create and add first shift worker with zero premium
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setWeeklyWorkingHours(35);
        shiftWorker1.setHourlyRate(20);
        shiftWorker1.setHolidayPremium(0);
        shiftWorker1.setDepartment("delivery");
        company.getEmployees().add(shiftWorker1);
        
        // Create and add second shift worker with 250 premium
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setWeeklyWorkingHours(40);
        shiftWorker2.setHourlyRate(22);
        shiftWorker2.setHolidayPremium(250.00);
        shiftWorker2.setDepartment("production");
        company.getEmployees().add(shiftWorker2);
        
        // Create and add third shift worker with zero premium
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setWeeklyWorkingHours(38);
        shiftWorker3.setHourlyRate(24);
        shiftWorker3.setHolidayPremium(0);
        shiftWorker3.setDepartment("maintenance");
        company.getEmployees().add(shiftWorker3);
        
        // Create and add fourth shift worker with 150 premium
        ShiftWorker shiftWorker4 = new ShiftWorker();
        shiftWorker4.setWeeklyWorkingHours(42);
        shiftWorker4.setHourlyRate(26);
        shiftWorker4.setHolidayPremium(150.00);
        shiftWorker4.setDepartment("logistics");
        company.getEmployees().add(shiftWorker4);
        
        // Calculate total holiday premiums (0 + 250 + 0 + 150 = 400)
        double result = company.calculateTotalHolidayPremiums();
        assertEquals(400.00, result, 0.001);
    }
    
    @Test
    public void testCase5_singleShiftWorkerWithZeroPremium() {
        // Test Case 5: Single shift worker with zero premium
        // Input: A Company object with a single ShiftWorker having a holidayPremium of 0
        // Expected Output: The total holiday premiums should be 0
        
        // Create and add a shift worker with zero holiday premium
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setWeeklyWorkingHours(40);
        shiftWorker.setHourlyRate(25);
        shiftWorker.setHolidayPremium(0);
        shiftWorker.setDepartment("delivery");
        company.getEmployees().add(shiftWorker);
        
        // Calculate total holiday premiums - should be 0
        double result = company.calculateTotalHolidayPremiums();
        assertEquals(0.0, result, 0.001);
    }
}