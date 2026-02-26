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
        // Test Case 1: "No shift workers in the company"
        // Input: "A Company object with an empty list of ShiftWorkers"
        // Expected Output: "The total holiday premiums should be 0"
        
        // Create a company with no shift workers
        // Add some non-shift workers to ensure they are ignored
        Worker nonShiftWorker = new Worker();
        nonShiftWorker.setShiftWorker(false);
        nonShiftWorker.setHolidayPremium(100.00);
        company.addEmployee(nonShiftWorker);
        
        SalesPerson salesPerson = new SalesPerson();
        company.addEmployee(salesPerson);
        
        Manager manager = new Manager();
        company.addEmployee(manager);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify the result is 0 when there are no shift workers
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_oneShiftWorkerInCompany() {
        // Test Case 2: "One shift worker in the company"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 500.00"
        // Expected Output: "The total holiday premiums should be 500.00"
        
        // Create and add one shift worker with holiday premium of 500.00
        Worker shiftWorker = new Worker();
        shiftWorker.setShiftWorker(true);
        shiftWorker.setHolidayPremium(500.00);
        company.addEmployee(shiftWorker);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify the result matches the expected premium
        assertEquals(500.00, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleShiftWorkersWithDifferentPremiums() {
        // Test Case 3: "Multiple shift workers with different premiums"
        // Input: "A Company object with three ShiftWorkers having holiday premiums of 200.00, 300.00, and 400.00 respectively"
        // Expected Output: "The total holiday premiums should be 900.00"
        
        // Create and add three shift workers with different holiday premiums
        Worker shiftWorker1 = new Worker();
        shiftWorker1.setShiftWorker(true);
        shiftWorker1.setHolidayPremium(200.00);
        company.addEmployee(shiftWorker1);
        
        Worker shiftWorker2 = new Worker();
        shiftWorker2.setShiftWorker(true);
        shiftWorker2.setHolidayPremium(300.00);
        company.addEmployee(shiftWorker2);
        
        Worker shiftWorker3 = new Worker();
        shiftWorker3.setShiftWorker(true);
        shiftWorker3.setHolidayPremium(400.00);
        company.addEmployee(shiftWorker3);
        
        // Add a non-shift worker to ensure it's ignored
        Worker nonShiftWorker = new Worker();
        nonShiftWorker.setShiftWorker(false);
        nonShiftWorker.setHolidayPremium(100.00);
        company.addEmployee(nonShiftWorker);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify the sum of all premiums (200 + 300 + 400 = 900)
        assertEquals(900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_multipleShiftWorkersWithSomeZeroPremiums() {
        // Test Case 4: "Multiple shift workers with some zero premiums"
        // Input: "A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively"
        // Expected Output: "The total holiday premiums should be 400.00"
        
        // Create and add four shift workers with some zero premiums
        Worker shiftWorker1 = new Worker();
        shiftWorker1.setShiftWorker(true);
        shiftWorker1.setHolidayPremium(0.00);
        company.addEmployee(shiftWorker1);
        
        Worker shiftWorker2 = new Worker();
        shiftWorker2.setShiftWorker(true);
        shiftWorker2.setHolidayPremium(250.00);
        company.addEmployee(shiftWorker2);
        
        Worker shiftWorker3 = new Worker();
        shiftWorker3.setShiftWorker(true);
        shiftWorker3.setHolidayPremium(0.00);
        company.addEmployee(shiftWorker3);
        
        Worker shiftWorker4 = new Worker();
        shiftWorker4.setShiftWorker(true);
        shiftWorker4.setHolidayPremium(150.00);
        company.addEmployee(shiftWorker4);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify the sum of non-zero premiums (250 + 150 = 400)
        assertEquals(400.00, result, 0.001);
    }
    
    @Test
    public void testCase5_singleShiftWorkerWithZeroPremium() {
        // Test Case 5: "Single shift worker with zero premium"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 0"
        // Expected Output: "The total holiday premiums should be 0"
        
        // Create and add one shift worker with zero holiday premium
        Worker shiftWorker = new Worker();
        shiftWorker.setShiftWorker(true);
        shiftWorker.setHolidayPremium(0.00);
        company.addEmployee(shiftWorker);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify the result is 0 for zero premium
        assertEquals(0.00, result, 0.001);
    }
}