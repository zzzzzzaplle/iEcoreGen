import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;

public class CR4Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize a new Company object before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_noShiftWorkersInCompany() {
        // Test Case 1: "No shift workers in the company"
        // Input: "A Company object with an empty list of ShiftWorkers"
        // Expected Output: "The total holiday premiums should be 0"
        
        // Add some non-ShiftWorker employees to ensure company has employees but no shift workers
        Worker worker = new Worker();
        worker.setName("Regular Worker");
        Department dept = new Department();
        dept.setName("Delivery");
        worker.setDepartment(dept);
        company.addEmployee(worker);
        
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setName("Sales Person");
        salesPerson.setDepartment(dept);
        company.addEmployee(salesPerson);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify the result is 0 since there are no shift workers in delivery department
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_oneShiftWorkerInCompany() {
        // Test Case 2: "One shift worker in the company"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 500.00"
        // Expected Output: "The total holiday premiums should be 500.00"
        
        // Create a shift worker with holiday premium and set to delivery department
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setName("Shift Worker 1");
        shiftWorker.setHolidayPremium(500.00);
        Department deliveryDept = new Department();
        deliveryDept.setName("Delivery");
        shiftWorker.setDepartment(deliveryDept);
        company.addEmployee(shiftWorker);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify the result equals the single shift worker's premium
        assertEquals(500.00, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleShiftWorkersWithDifferentPremiums() {
        // Test Case 3: "Multiple shift workers with different premiums"
        // Input: "A Company object with three ShiftWorkers having holiday premiums of 200.00, 300.00, and 400.00 respectively"
        // Expected Output: "The total holiday premiums should be 900.00"
        
        Department deliveryDept = new Department();
        deliveryDept.setName("Delivery");
        
        // Create first shift worker
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setName("Shift Worker 1");
        shiftWorker1.setHolidayPremium(200.00);
        shiftWorker1.setDepartment(deliveryDept);
        company.addEmployee(shiftWorker1);
        
        // Create second shift worker
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setName("Shift Worker 2");
        shiftWorker2.setHolidayPremium(300.00);
        shiftWorker2.setDepartment(deliveryDept);
        company.addEmployee(shiftWorker2);
        
        // Create third shift worker
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setName("Shift Worker 3");
        shiftWorker3.setHolidayPremium(400.00);
        shiftWorker3.setDepartment(deliveryDept);
        company.addEmployee(shiftWorker3);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify the result equals the sum of all premiums (200 + 300 + 400 = 900)
        assertEquals(900.00, result, 0.001);
    }
    
    @Test
    public void testCase4_multipleShiftWorkersWithSomeZeroPremiums() {
        // Test Case 4: "Multiple shift workers with some zero premiums"
        // Input: "A Company object with four ShiftWorkers having holiday premiums of 0, 250.00, 0, and 150.00 respectively"
        // Expected Output: "The total holiday premiums should be 400.00"
        
        Department deliveryDept = new Department();
        deliveryDept.setName("Delivery");
        
        // Create first shift worker with zero premium
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setName("Shift Worker 1");
        shiftWorker1.setHolidayPremium(0);
        shiftWorker1.setDepartment(deliveryDept);
        company.addEmployee(shiftWorker1);
        
        // Create second shift worker with 250 premium
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setName("Shift Worker 2");
        shiftWorker2.setHolidayPremium(250.00);
        shiftWorker2.setDepartment(deliveryDept);
        company.addEmployee(shiftWorker2);
        
        // Create third shift worker with zero premium
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setName("Shift Worker 3");
        shiftWorker3.setHolidayPremium(0);
        shiftWorker3.setDepartment(deliveryDept);
        company.addEmployee(shiftWorker3);
        
        // Create fourth shift worker with 150 premium
        ShiftWorker shiftWorker4 = new ShiftWorker();
        shiftWorker4.setName("Shift Worker 4");
        shiftWorker4.setHolidayPremium(150.00);
        shiftWorker4.setDepartment(deliveryDept);
        company.addEmployee(shiftWorker4);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify the result equals the sum of non-zero premiums (0 + 250 + 0 + 150 = 400)
        assertEquals(400.00, result, 0.001);
    }
    
    @Test
    public void testCase5_singleShiftWorkerWithZeroPremium() {
        // Test Case 5: "Single shift worker with zero premium"
        // Input: "A Company object with a single ShiftWorker having a holidayPremium of 0"
        // Expected Output: "The total holiday premiums should be 0"
        
        // Create a shift worker with zero holiday premium and set to delivery department
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setName("Shift Worker");
        shiftWorker.setHolidayPremium(0);
        Department deliveryDept = new Department();
        deliveryDept.setName("Delivery");
        shiftWorker.setDepartment(deliveryDept);
        company.addEmployee(shiftWorker);
        
        // Calculate total holiday premiums
        double result = company.calculateTotalHolidayPremiums();
        
        // Verify the result is 0 since the premium is zero
        assertEquals(0.0, result, 0.001);
    }
}