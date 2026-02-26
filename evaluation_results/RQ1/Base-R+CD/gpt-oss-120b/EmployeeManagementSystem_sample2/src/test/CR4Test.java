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
        // Test Case 1: "No shift workers in the company"
        // Input: "A Company object with an empty list of ShiftWorkers"
        // Expected Output: "The total holiday premiums should be 0"
        
        // Add some non-ShiftWorker employees to ensure the method works correctly
        Manager manager = new Manager();
        manager.setSalary(5000.00);
        company.addEmployee(manager);
        
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(3000.00);
        salesPerson.setAmountOfSales(10000.00);
        salesPerson.setCommissionPercentage(0.05);
        company.addEmployee(salesPerson);
        
        OffShiftWorker offShiftWorker = new OffShiftWorker();
        offShiftWorker.setWeeklyWorkingHour(40);
        offShiftWorker.setHourlyRates(25.00);
        company.addEmployee(offShiftWorker);
        
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase2_oneShiftWorkerInCompany() {
        // Test Case 2: "One shift worker in the company"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 500.00"
        // Expected Output: "The total holiday premiums should be 500.00"
        
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setHolidayPremium(500.00);
        company.addEmployee(shiftWorker);
        
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals(500.00, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleShiftWorkersWithDifferentPremiums() {
        // Test Case 3: "Multiple shift workers with different premiums"
        // Input: "A Company object with three ShiftWorkers having holiday premiums of 200.00, 300.00, and 400.00 respectively"
        // Expected Output: "The total holiday premiums should be 900.00"
        
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setHolidayPremium(200.00);
        company.addEmployee(shiftWorker1);
        
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setHolidayPremium(300.00);
        company.addEmployee(shiftWorker2);
        
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setHolidayPremium(400.00);
        company.addEmployee(shiftWorker3);
        
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals(900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_multipleShiftWorkersWithSomeZeroPremiums() {
        // Test Case 4: "Multiple shift workers with some zero premiums"
        // Input: "A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively"
        // Expected Output: "The total holiday premiums should be 400.00"
        
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setHolidayPremium(0.00);
        company.addEmployee(shiftWorker1);
        
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setHolidayPremium(250.00);
        company.addEmployee(shiftWorker2);
        
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setHolidayPremium(0.00);
        company.addEmployee(shiftWorker3);
        
        ShiftWorker shiftWorker4 = new ShiftWorker();
        shiftWorker4.setHolidayPremium(150.00);
        company.addEmployee(shiftWorker4);
        
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals(400.00, result, 0.001);
    }
    
    @Test
    public void testCase5_singleShiftWorkerWithZeroPremium() {
        // Test Case 5: "Single shift worker with zero premium"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 0"
        // Expected Output: "The total holiday premiums should be 0"
        
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setHolidayPremium(0.00);
        company.addEmployee(shiftWorker);
        
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals(0.00, result, 0.001);
    }
}