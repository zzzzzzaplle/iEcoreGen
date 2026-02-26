import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR4Test {
    private Company company;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        company = new Company();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_noShiftWorkersInCompany() throws ParseException {
        // Test Case 1: "No shift workers in the company"
        // Input: "A Company object with an empty list of ShiftWorkers"
        // Expected Output: "The total holiday premiums should be 0"
        
        // Create employees that are NOT shift workers
        Date birthDate = dateFormat.parse("1990-01-01 00:00:00");
        
        // Add a Manager
        Manager manager = new Manager("IT", "John Manager", birthDate, "123-45-6789", 50000.0, "Senior Manager");
        company.addEmployee(manager);
        
        // Add a SalesPerson
        SalesPeople salesPerson = new SalesPeople("Sales", "Jane Sales", birthDate, "987-65-4321", 30000.0, 100000.0, 5.0);
        company.addEmployee(salesPerson);
        
        // Add an OffShiftWorker
        OffShiftWorker offShiftWorker = new OffShiftWorker("Delivery", "Bob OffShift", birthDate, "456-78-9012", 40, 25.0);
        company.addEmployee(offShiftWorker);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify the result is 0 since there are no shift workers
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_oneShiftWorkerInCompany() throws ParseException {
        // Test Case 2: "One shift worker in the company"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 500.00"
        // Expected Output: "The total holiday premiums should be 500.00"
        
        Date birthDate = dateFormat.parse("1985-05-15 00:00:00");
        
        // Add a single ShiftWorker with holidayPremium of 500.00
        ShiftWorker shiftWorker = new ShiftWorker("Production", "Alice Shift", birthDate, "111-22-3333", 40, 30.0, 500.0);
        company.addEmployee(shiftWorker);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify the result is 500.00
        assertEquals(500.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleShiftWorkersWithDifferentPremiums() throws ParseException {
        // Test Case 3: "Multiple shift workers with different premiums"
        // Input: "A Company object with three ShiftWorkers having holiday premiums of 200.00, 300.00, and 400.00 respectively"
        // Expected Output: "The total holiday premiums should be 900.00"
        
        Date birthDate = dateFormat.parse("1988-03-20 00:00:00");
        
        // Add three ShiftWorkers with different holiday premiums
        ShiftWorker shiftWorker1 = new ShiftWorker("Control", "Worker1", birthDate, "222-33-4444", 35, 28.0, 200.0);
        ShiftWorker shiftWorker2 = new ShiftWorker("Production", "Worker2", birthDate, "333-44-5555", 40, 32.0, 300.0);
        ShiftWorker shiftWorker3 = new ShiftWorker("Delivery", "Worker3", birthDate, "444-55-6666", 38, 30.0, 400.0);
        
        company.addEmployee(shiftWorker1);
        company.addEmployee(shiftWorker2);
        company.addEmployee(shiftWorker3);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify the result is 900.00 (200 + 300 + 400)
        assertEquals(900.0, result, 0.001);
    }
    
    @Test
    public void testCase4_multipleShiftWorkersWithSomeZeroPremiums() throws ParseException {
        // Test Case 4: "Multiple shift workers with some zero premiums"
        // Input: "A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively"
        // Expected Output: "The total holiday premiums should be 400.00"
        
        Date birthDate = dateFormat.parse("1992-07-10 00:00:00");
        
        // Add four ShiftWorkers with some zero premiums
        ShiftWorker shiftWorker1 = new ShiftWorker("Production", "WorkerA", birthDate, "555-66-7777", 40, 25.0, 0.0);
        ShiftWorker shiftWorker2 = new ShiftWorker("Control", "WorkerB", birthDate, "666-77-8888", 35, 28.0, 250.0);
        ShiftWorker shiftWorker3 = new ShiftWorker("Delivery", "WorkerC", birthDate, "777-88-9999", 42, 30.0, 0.0);
        ShiftWorker shiftWorker4 = new ShiftWorker("Production", "WorkerD", birthDate, "888-99-0000", 38, 26.0, 150.0);
        
        company.addEmployee(shiftWorker1);
        company.addEmployee(shiftWorker2);
        company.addEmployee(shiftWorker3);
        company.addEmployee(shiftWorker4);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify the result is 400.00 (0 + 250 + 0 + 150)
        assertEquals(400.0, result, 0.001);
    }
    
    @Test
    public void testCase5_singleShiftWorkerWithZeroPremium() throws ParseException {
        // Test Case 5: "Single shift worker with zero premium"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 0"
        // Expected Output: "The total holiday premiums should be 0"
        
        Date birthDate = dateFormat.parse("1995-12-25 00:00:00");
        
        // Add a single ShiftWorker with zero holiday premium
        ShiftWorker shiftWorker = new ShiftWorker("Delivery", "Zero Premium Worker", birthDate, "999-00-1111", 40, 20.0, 0.0);
        company.addEmployee(shiftWorker);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalShiftWorkerHolidayPremiums();
        
        // Verify the result is 0
        assertEquals(0.0, result, 0.001);
    }
}