import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR4Test {
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_NoShiftWorkersInCompany() {
        // Test Case 1: "No shift workers in the company"
        // Input: "A Company object with an empty list of ShiftWorkers"
        // Expected Output: "The total holiday premiums should be 0"
        
        // Add some non-shift worker employees to ensure the method handles them correctly
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setName("Sales Person");
        company.getEmployees().add(salesPerson);
        
        Manager manager = new Manager();
        manager.setName("Manager");
        company.getEmployees().add(manager);
        
        OffShiftWorker offShiftWorker = new OffShiftWorker();
        offShiftWorker.setName("Off-Shift Worker");
        company.getEmployees().add(offShiftWorker);
        
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_OneShiftWorkerInCompany() {
        // Test Case 2: "One shift worker in the company"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 500.00"
        // Expected Output: "The total holiday premiums should be 500.00"
        
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setName("Shift Worker");
        shiftWorker.setHolidayPremium(500.00);
        company.getEmployees().add(shiftWorker);
        
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals(500.00, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleShiftWorkersWithDifferentPremiums() {
        // Test Case 3: "Multiple shift workers with different premiums"
        // Input: "A Company object with three ShiftWorkers having holiday premiums of 200.00, 300.00, and 400.00 respectively"
        // Expected Output: "The total holiday premiums should be 900.00"
        
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setName("Shift Worker 1");
        shiftWorker1.setHolidayPremium(200.00);
        
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setName("Shift Worker 2");
        shiftWorker2.setHolidayPremium(300.00);
        
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setName("Shift Worker 3");
        shiftWorker3.setHolidayPremium(400.00);
        
        company.getEmployees().add(shiftWorker1);
        company.getEmployees().add(shiftWorker2);
        company.getEmployees().add(shiftWorker3);
        
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals(900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_MultipleShiftWorkersWithSomeZeroPremiums() {
        // Test Case 4: "Multiple shift workers with some zero premiums"
        // Input: "A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively"
        // Expected Output: "The total holiday premiums should be 400.00"
        
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setName("Shift Worker 1");
        shiftWorker1.setHolidayPremium(0);
        
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setName("Shift Worker 2");
        shiftWorker2.setHolidayPremium(250.00);
        
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setName("Shift Worker 3");
        shiftWorker3.setHolidayPremium(0);
        
        ShiftWorker shiftWorker4 = new ShiftWorker();
        shiftWorker4.setName("Shift Worker 4");
        shiftWorker4.setHolidayPremium(150.00);
        
        company.getEmployees().add(shiftWorker1);
        company.getEmployees().add(shiftWorker2);
        company.getEmployees().add(shiftWorker3);
        company.getEmployees().add(shiftWorker4);
        
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals(400.00, result, 0.001);
    }
    
    @Test
    public void testCase5_SingleShiftWorkerWithZeroPremium() {
        // Test Case 5: "Single shift worker with zero premium"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 0"
        // Expected Output: "The total holiday premiums should be 0"
        
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setName("Shift Worker");
        shiftWorker.setHolidayPremium(0);
        company.getEmployees().add(shiftWorker);
        
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals(0.0, result, 0.001);
    }
}