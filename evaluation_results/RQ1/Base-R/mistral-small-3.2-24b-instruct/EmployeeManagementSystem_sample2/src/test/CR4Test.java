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
        // Test Case 1: "No shift workers in the company"
        // Input: "A Company object with an empty list of ShiftWorkers"
        // Expected Output: "The total holiday premiums should be 0"
        
        // Add some non-shift workers to ensure the company is not empty
        NonShiftWorker nonShiftWorker = new NonShiftWorker();
        nonShiftWorker.setName("John Doe");
        nonShiftWorker.setDepartment("IT");
        company.addEmployee(nonShiftWorker);
        
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setName("Jane Smith");
        salesPerson.setDepartment("Sales");
        company.addEmployee(salesPerson);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify the result is 0 since there are no shift workers
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_oneShiftWorkerInCompany() {
        // Test Case 2: "One shift worker in the company"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 500.00"
        // Expected Output: "The total holiday premiums should be 500.00"
        
        // Create and configure a shift worker
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setName("Shift Worker 1");
        shiftWorker.setDepartment("Delivery");
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
        
        // Create and configure three shift workers with different premiums
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setName("Shift Worker 1");
        shiftWorker1.setDepartment("Delivery");
        shiftWorker1.setHolidayPremium(200.00);
        company.addEmployee(shiftWorker1);
        
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setName("Shift Worker 2");
        shiftWorker2.setDepartment("Delivery");
        shiftWorker2.setHolidayPremium(300.00);
        company.addEmployee(shiftWorker2);
        
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setName("Shift Worker 3");
        shiftWorker3.setDepartment("Delivery");
        shiftWorker3.setHolidayPremium(400.00);
        company.addEmployee(shiftWorker3);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify the sum of all premiums
        assertEquals(900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_multipleShiftWorkersWithSomeZeroPremiums() {
        // Test Case 4: "Multiple shift workers with some zero premiums"
        // Input: "A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively"
        // Expected Output: "The total holiday premiums should be 400.00"
        
        // Create and configure four shift workers with some zero premiums
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setName("Shift Worker 1");
        shiftWorker1.setDepartment("Delivery");
        shiftWorker1.setHolidayPremium(0.00);
        company.addEmployee(shiftWorker1);
        
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setName("Shift Worker 2");
        shiftWorker2.setDepartment("Delivery");
        shiftWorker2.setHolidayPremium(250.00);
        company.addEmployee(shiftWorker2);
        
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setName("Shift Worker 3");
        shiftWorker3.setDepartment("Delivery");
        shiftWorker3.setHolidayPremium(0.00);
        company.addEmployee(shiftWorker3);
        
        ShiftWorker shiftWorker4 = new ShiftWorker();
        shiftWorker4.setName("Shift Worker 4");
        shiftWorker4.setDepartment("Delivery");
        shiftWorker4.setHolidayPremium(150.00);
        company.addEmployee(shiftWorker4);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify the sum of non-zero premiums
        assertEquals(400.00, result, 0.001);
    }
    
    @Test
    public void testCase5_singleShiftWorkerWithZeroPremium() {
        // Test Case 5: "Single shift worker with zero premium"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 0"
        // Expected Output: "The total holiday premiums should be 0"
        
        // Create and configure a shift worker with zero premium
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setName("Shift Worker");
        shiftWorker.setDepartment("Delivery");
        shiftWorker.setHolidayPremium(0.00);
        company.addEmployee(shiftWorker);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify the result is 0
        assertEquals(0.0, result, 0.001);
    }
}