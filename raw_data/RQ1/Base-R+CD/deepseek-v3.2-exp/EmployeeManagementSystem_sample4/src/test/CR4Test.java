import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class CR4Test {
    private Company company;
    private Department deliveryDepartment;
    private Department productionDepartment;
    
    @Before
    public void setUp() {
        company = new Company();
        deliveryDepartment = new Department();
        deliveryDepartment.setType(DepartmentType.DELIVERY);
        productionDepartment = new Department();
        productionDepartment.setType(DepartmentType.PRODUCTION);
    }
    
    @Test
    public void testCase1_noShiftWorkersInCompany() {
        // Test Case 1: "No shift workers in the company"
        // Input: "A Company object with an empty list of ShiftWorkers"
        // Expected Output: "The total holiday premiums should be 0"
        
        // Add departments to company
        company.addDepartment(deliveryDepartment);
        company.addDepartment(productionDepartment);
        
        // Add some non-shift worker employees to delivery department
        OffShiftWorker offShiftWorker = new OffShiftWorker();
        offShiftWorker.setWeeklyWorkingHour(40);
        offShiftWorker.setHourlyRates(25.0);
        deliveryDepartment.addEmployee(offShiftWorker);
        
        // Add non-shift worker employees to production department
        OffShiftWorker productionWorker = new OffShiftWorker();
        productionWorker.setWeeklyWorkingHour(35);
        productionWorker.setHourlyRates(20.0);
        productionDepartment.addEmployee(productionWorker);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify result is 0 since there are no shift workers
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_oneShiftWorkerInCompany() {
        // Test Case 2: "One shift worker in the company"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 500.00"
        // Expected Output: "The total holiday premiums should be 500.00"
        
        // Add delivery department to company
        company.addDepartment(deliveryDepartment);
        
        // Create and configure a shift worker with holiday premium of 500.00
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setWeeklyWorkingHour(40);
        shiftWorker.setHourlyRates(30.0);
        shiftWorker.setHolidayPremium(500.00);
        deliveryDepartment.addEmployee(shiftWorker);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify result is 500.00
        assertEquals(500.00, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleShiftWorkersWithDifferentPremiums() {
        // Test Case 3: "Multiple shift workers with different premiums"
        // Input: "A Company object with three ShiftWorkers having holiday premiums of 200.00, 300.00, and 400.00 respectively"
        // Expected Output: "The total holiday premiums should be 900.00"
        
        // Add delivery department to company
        company.addDepartment(deliveryDepartment);
        
        // Create and configure three shift workers with different premiums
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setWeeklyWorkingHour(35);
        shiftWorker1.setHourlyRates(25.0);
        shiftWorker1.setHolidayPremium(200.00);
        deliveryDepartment.addEmployee(shiftWorker1);
        
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setWeeklyWorkingHour(40);
        shiftWorker2.setHourlyRates(30.0);
        shiftWorker2.setHolidayPremium(300.00);
        deliveryDepartment.addEmployee(shiftWorker2);
        
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setWeeklyWorkingHour(45);
        shiftWorker3.setHourlyRates(35.0);
        shiftWorker3.setHolidayPremium(400.00);
        deliveryDepartment.addEmployee(shiftWorker3);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify result is 900.00 (200 + 300 + 400)
        assertEquals(900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_multipleShiftWorkersWithSomeZeroPremiums() {
        // Test Case 4: "Multiple shift workers with some zero premiums"
        // Input: "A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively"
        // Expected Output: "The total holiday premiums should be 400.00"
        
        // Add delivery department to company
        company.addDepartment(deliveryDepartment);
        
        // Create and configure four shift workers with some zero premiums
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setWeeklyWorkingHour(35);
        shiftWorker1.setHourlyRates(25.0);
        shiftWorker1.setHolidayPremium(0);
        deliveryDepartment.addEmployee(shiftWorker1);
        
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setWeeklyWorkingHour(40);
        shiftWorker2.setHourlyRates(30.0);
        shiftWorker2.setHolidayPremium(250.00);
        deliveryDepartment.addEmployee(shiftWorker2);
        
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setWeeklyWorkingHour(45);
        shiftWorker3.setHourlyRates(35.0);
        shiftWorker3.setHolidayPremium(0);
        deliveryDepartment.addEmployee(shiftWorker3);
        
        ShiftWorker shiftWorker4 = new ShiftWorker();
        shiftWorker4.setWeeklyWorkingHour(50);
        shiftWorker4.setHourlyRates(40.0);
        shiftWorker4.setHolidayPremium(150.00);
        deliveryDepartment.addEmployee(shiftWorker4);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify result is 400.00 (0 + 250 + 0 + 150)
        assertEquals(400.00, result, 0.001);
    }
    
    @Test
    public void testCase5_singleShiftWorkerWithZeroPremium() {
        // Test Case 5: "Single shift worker with zero premium"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 0"
        // Expected Output: "The total holiday premiums should be 0"
        
        // Add delivery department to company
        company.addDepartment(deliveryDepartment);
        
        // Create and configure a shift worker with zero holiday premium
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setWeeklyWorkingHour(40);
        shiftWorker.setHourlyRates(30.0);
        shiftWorker.setHolidayPremium(0);
        deliveryDepartment.addEmployee(shiftWorker);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify result is 0
        assertEquals(0.0, result, 0.001);
    }
}