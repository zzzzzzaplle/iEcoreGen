import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

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
        
        // Setup: Create company with no shift workers
        // Add some non-shift worker employees to ensure they don't affect the calculation
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date birthDate = sdf.parse("1990-01-01 00:00:00");
            
            Manager manager = new Manager("PRODUCTION", "John Manager", birthDate, 
                                        "123-45-6789", 50000.0, "Senior Manager");
            SalesPeople salesPerson = new SalesPeople("CONTROL", "Jane Sales", birthDate,
                                                    "987-65-4321", 30000.0, 100000.0, 0.1);
            OffShiftWorker offShiftWorker = new OffShiftWorker("DELIVERY", "Bob Worker", birthDate,
                                                            "456-78-9012", 40, 25.0);
            
            ArrayList<Employee> employees = new ArrayList<>();
            employees.add(manager);
            employees.add(salesPerson);
            employees.add(offShiftWorker);
            
            company.setEmployees(employees);
            
            // Execute: Calculate total holiday premiums
            double result = company.calculateTotalShiftWorkerHolidayPremiums();
            
            // Verify: Should return 0 since there are no shift workers
            assertEquals(0.0, result, 0.001);
        } catch (Exception e) {
            fail("Test setup failed: " + e.getMessage());
        }
    }
    
    @Test
    public void testCase2_oneShiftWorkerWithPremium500() {
        // Test Case 2: "One shift worker in the company"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 500.00"
        // Expected Output: "The total holiday premiums should be 500.00"
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date birthDate = sdf.parse("1985-05-15 00:00:00");
            
            ShiftWorker shiftWorker = new ShiftWorker("DELIVERY", "Alice Shift", birthDate,
                                                    "111-22-3333", 35, 20.0, 500.0);
            
            ArrayList<Employee> employees = new ArrayList<>();
            employees.add(shiftWorker);
            
            company.setEmployees(employees);
            
            // Execute: Calculate total holiday premiums
            double result = company.calculateTotalShiftWorkerHolidayPremiums();
            
            // Verify: Should return 500.00
            assertEquals(500.0, result, 0.001);
        } catch (Exception e) {
            fail("Test setup failed: " + e.getMessage());
        }
    }
    
    @Test
    public void testCase3_multipleShiftWorkersDifferentPremiums() {
        // Test Case 3: "Multiple shift workers with different premiums"
        // Input: "A Company object with three ShiftWorkers having holiday premiums of 200.00, 300.00, and 400.00 respectively"
        // Expected Output: "The total holiday premiums should be 900.00"
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date birthDate = sdf.parse("1988-03-20 00:00:00");
            
            ShiftWorker shiftWorker1 = new ShiftWorker("DELIVERY", "Worker1", birthDate,
                                                     "222-33-4444", 40, 18.0, 200.0);
            ShiftWorker shiftWorker2 = new ShiftWorker("DELIVERY", "Worker2", birthDate,
                                                     "333-44-5555", 38, 19.0, 300.0);
            ShiftWorker shiftWorker3 = new ShiftWorker("DELIVERY", "Worker3", birthDate,
                                                     "444-55-6666", 42, 17.0, 400.0);
            
            ArrayList<Employee> employees = new ArrayList<>();
            employees.add(shiftWorker1);
            employees.add(shiftWorker2);
            employees.add(shiftWorker3);
            
            company.setEmployees(employees);
            
            // Execute: Calculate total holiday premiums
            double result = company.calculateTotalShiftWorkerHolidayPremiums();
            
            // Verify: Should return 900.00 (200 + 300 + 400)
            assertEquals(900.0, result, 0.001);
        } catch (Exception e) {
            fail("Test setup failed: " + e.getMessage());
        }
    }
    
    @Test
    public void testCase4_multipleShiftWorkersSomeZeroPremiums() {
        // Test Case 4: "Multiple shift workers with some zero premiums"
        // Input: "A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively"
        // Expected Output: "The total holiday premiums should be 400.00"
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date birthDate = sdf.parse("1992-07-10 00:00:00");
            
            ShiftWorker shiftWorker1 = new ShiftWorker("DELIVERY", "WorkerA", birthDate,
                                                     "555-66-7777", 36, 21.0, 0.0);
            ShiftWorker shiftWorker2 = new ShiftWorker("DELIVERY", "WorkerB", birthDate,
                                                     "666-77-8888", 39, 22.0, 250.0);
            ShiftWorker shiftWorker3 = new ShiftWorker("DELIVERY", "WorkerC", birthDate,
                                                     "777-88-9999", 37, 23.0, 0.0);
            ShiftWorker shiftWorker4 = new ShiftWorker("DELIVERY", "WorkerD", birthDate,
                                                     "888-99-0000", 41, 24.0, 150.0);
            
            ArrayList<Employee> employees = new ArrayList<>();
            employees.add(shiftWorker1);
            employees.add(shiftWorker2);
            employees.add(shiftWorker3);
            employees.add(shiftWorker4);
            
            company.setEmployees(employees);
            
            // Execute: Calculate total holiday premiums
            double result = company.calculateTotalShiftWorkerHolidayPremiums();
            
            // Verify: Should return 400.00 (0 + 250 + 0 + 150)
            assertEquals(400.0, result, 0.001);
        } catch (Exception e) {
            fail("Test setup failed: " + e.getMessage());
        }
    }
    
    @Test
    public void testCase5_singleShiftWorkerWithZeroPremium() {
        // Test Case 5: "Single shift worker with zero premium"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 0"
        // Expected Output: "The total holiday premiums should be 0"
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date birthDate = sdf.parse("1995-12-25 00:00:00");
            
            ShiftWorker shiftWorker = new ShiftWorker("DELIVERY", "Zero Premium Worker", birthDate,
                                                    "999-00-1111", 35, 20.0, 0.0);
            
            ArrayList<Employee> employees = new ArrayList<>();
            employees.add(shiftWorker);
            
            company.setEmployees(employees);
            
            // Execute: Calculate total holiday premiums
            double result = company.calculateTotalShiftWorkerHolidayPremiums();
            
            // Verify: Should return 0.0
            assertEquals(0.0, result, 0.001);
        } catch (Exception e) {
            fail("Test setup failed: " + e.getMessage());
        }
    }
}