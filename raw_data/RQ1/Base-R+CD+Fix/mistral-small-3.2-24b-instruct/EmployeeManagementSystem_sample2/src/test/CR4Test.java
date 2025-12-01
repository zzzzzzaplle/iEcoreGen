import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    public void testCase1_noShiftWorkersInCompany() throws Exception {
        // Test Case 1: "No shift workers in the company"
        // Input: "A Company object with an empty list of ShiftWorkers"
        // Expected Output: "The total holiday premiums should be 0"
        
        // Create employees without any ShiftWorkers
        Date birthDate = dateFormat.parse("1990-01-01 00:00:00");
        
        // Add a Manager
        Manager manager = new Manager("PRODUCTION", "John Manager", birthDate, "123-45-6789", 50000.0, "Senior Manager");
        company.getEmployees().add(manager);
        
        // Add a SalesPerson
        SalesPeople salesPerson = new SalesPeople("CONTROL", "Jane Sales", birthDate, "987-65-4321", 40000.0, 100000.0, 5.0);
        company.getEmployees().add(salesPerson);
        
        // Add an OffShiftWorker (not a ShiftWorker)
        OffShiftWorker offShiftWorker = new OffShiftWorker("DELIVERY", "Bob Worker", birthDate, "555-55-5555", 40, 25.0);
        company.getEmployees().add(offShiftWorker);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify the result is 0 since there are no ShiftWorkers
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_oneShiftWorkerInCompany() throws Exception {
        // Test Case 2: "One shift worker in the company"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 500.00"
        // Expected Output: "The total holiday premiums should be 500.00"
        
        // Create and add a single ShiftWorker with holiday premium of 500.00
        Date birthDate = dateFormat.parse("1985-05-15 00:00:00");
        ShiftWorker shiftWorker = new ShiftWorker("DELIVERY", "Alice Shift", birthDate, "111-11-1111", 40, 20.0, 500.0);
        company.getEmployees().add(shiftWorker);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify the result is 500.00
        assertEquals(500.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleShiftWorkersWithDifferentPremiums() throws Exception {
        // Test Case 3: "Multiple shift workers with different premiums"
        // Input: "A Company object with three ShiftWorkers having holiday premiums of 200.00, 300.00, and 400.00 respectively"
        // Expected Output: "The total holiday premiums should be 900.00"
        
        Date birthDate = dateFormat.parse("1988-03-20 00:00:00");
        
        // Create and add three ShiftWorkers with different holiday premiums
        ShiftWorker shiftWorker1 = new ShiftWorker("DELIVERY", "Worker One", birthDate, "222-22-2222", 35, 18.0, 200.0);
        ShiftWorker shiftWorker2 = new ShiftWorker("DELIVERY", "Worker Two", birthDate, "333-33-3333", 40, 22.0, 300.0);
        ShiftWorker shiftWorker3 = new ShiftWorker("DELIVERY", "Worker Three", birthDate, "444-44-4444", 38, 20.0, 400.0);
        
        company.getEmployees().add(shiftWorker1);
        company.getEmployees().add(shiftWorker2);
        company.getEmployees().add(shiftWorker3);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify the result is 900.00 (200 + 300 + 400)
        assertEquals(900.0, result, 0.001);
    }
    
    @Test
    public void testCase4_multipleShiftWorkersWithSomeZeroPremiums() throws Exception {
        // Test Case 4: "Multiple shift workers with some zero premiums"
        // Input: "A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively"
        // Expected Output: "The total holiday premiums should be 400.00"
        
        Date birthDate = dateFormat.parse("1992-07-10 00:00:00");
        
        // Create and add four ShiftWorkers with some zero premiums
        ShiftWorker shiftWorker1 = new ShiftWorker("DELIVERY", "Worker A", birthDate, "555-55-5555", 40, 25.0, 0.0);
        ShiftWorker shiftWorker2 = new ShiftWorker("DELIVERY", "Worker B", birthDate, "666-66-6666", 42, 26.0, 250.0);
        ShiftWorker shiftWorker3 = new ShiftWorker("DELIVERY", "Worker C", birthDate, "777-77-7777", 38, 24.0, 0.0);
        ShiftWorker shiftWorker4 = new ShiftWorker("DELIVERY", "Worker D", birthDate, "888-88-8888", 36, 23.0, 150.0);
        
        company.getEmployees().add(shiftWorker1);
        company.getEmployees().add(shiftWorker2);
        company.getEmployees().add(shiftWorker3);
        company.getEmployees().add(shiftWorker4);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify the result is 400.00 (0 + 250 + 0 + 150)
        assertEquals(400.0, result, 0.001);
    }
    
    @Test
    public void testCase5_singleShiftWorkerWithZeroPremium() throws Exception {
        // Test Case 5: "Single shift worker with zero premium"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 0"
        // Expected Output: "The total holiday premiums should be 0"
        
        Date birthDate = dateFormat.parse("1995-12-25 00:00:00");
        
        // Create and add a single ShiftWorker with zero holiday premium
        ShiftWorker shiftWorker = new ShiftWorker("DELIVERY", "Zero Premium Worker", birthDate, "999-99-9999", 40, 30.0, 0.0);
        company.getEmployees().add(shiftWorker);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify the result is 0.0
        assertEquals(0.0, result, 0.001);
    }
}