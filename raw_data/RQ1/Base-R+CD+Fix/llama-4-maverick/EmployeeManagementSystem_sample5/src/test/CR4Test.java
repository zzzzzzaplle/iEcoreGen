import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
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
        
        // Create employees list without any ShiftWorkers
        List<Employee> employees = new ArrayList<>();
        
        // Add other types of employees but no ShiftWorkers
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setDepartment("DELIVERY");
        employees.add(salesPerson);
        
        Manager manager = new Manager();
        manager.setDepartment("PRODUCTION");
        employees.add(manager);
        
        OffShiftWorker offShiftWorker = new OffShiftWorker();
        offShiftWorker.setDepartment("CONTROL");
        employees.add(offShiftWorker);
        
        company.setEmployees(employees);
        
        // Verify total holiday premiums is 0
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_oneShiftWorkerInCompany() {
        // Test Case 2: "One shift worker in the company"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 500.00"
        // Expected Output: "The total holiday premiums should be 500.00"
        
        List<Employee> employees = new ArrayList<>();
        
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setHolidayPremium(500.00);
        shiftWorker.setDepartment("DELIVERY");
        employees.add(shiftWorker);
        
        company.setEmployees(employees);
        
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals(500.00, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleShiftWorkersWithDifferentPremiums() {
        // Test Case 3: "Multiple shift workers with different premiums"
        // Input: "A Company object with three ShiftWorkers having holiday premiums of 200.00, 300.00, and 400.00 respectively"
        // Expected Output: "The total holiday premiums should be 900.00"
        
        List<Employee> employees = new ArrayList<>();
        
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setHolidayPremium(200.00);
        shiftWorker1.setDepartment("DELIVERY");
        employees.add(shiftWorker1);
        
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setHolidayPremium(300.00);
        shiftWorker2.setDepartment("DELIVERY");
        employees.add(shiftWorker2);
        
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setHolidayPremium(400.00);
        shiftWorker3.setDepartment("DELIVERY");
        employees.add(shiftWorker3);
        
        company.setEmployees(employees);
        
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals(900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_multipleShiftWorkersWithSomeZeroPremiums() {
        // Test Case 4: "Multiple shift workers with some zero premiums"
        // Input: "A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively"
        // Expected Output: "The total holiday premiums should be 400.00"
        
        List<Employee> employees = new ArrayList<>();
        
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setHolidayPremium(0);
        shiftWorker1.setDepartment("DELIVERY");
        employees.add(shiftWorker1);
        
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setHolidayPremium(250.00);
        shiftWorker2.setDepartment("DELIVERY");
        employees.add(shiftWorker2);
        
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setHolidayPremium(0);
        shiftWorker3.setDepartment("DELIVERY");
        employees.add(shiftWorker3);
        
        ShiftWorker shiftWorker4 = new ShiftWorker();
        shiftWorker4.setHolidayPremium(150.00);
        shiftWorker4.setDepartment("DELIVERY");
        employees.add(shiftWorker4);
        
        company.setEmployees(employees);
        
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals(400.00, result, 0.001);
    }
    
    @Test
    public void testCase5_singleShiftWorkerWithZeroPremium() {
        // Test Case 5: "Single shift worker with zero premium"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 0"
        // Expected Output: "The total holiday premiums should be 0"
        
        List<Employee> employees = new ArrayList<>();
        
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setHolidayPremium(0);
        shiftWorker.setDepartment("DELIVERY");
        employees.add(shiftWorker);
        
        company.setEmployees(employees);
        
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        assertEquals(0.0, result, 0.001);
    }
}