import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_singleWorkerInDeliveryDepartment() {
        // Setup: Create Delivery department with one worker having 40 hours
        Department deliveryDept = new Department();
        deliveryDept.setName("Delivery");
        
        ShiftWorker worker = new ShiftWorker();
        worker.setWeeklyWorkingHours(40.0);
        deliveryDept.addEmployee(worker);
        
        company.addDepartment(deliveryDept);
        
        // Execute: Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify: Average should be 40 hours
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersSameHoursInDeliveryDepartment() {
        // Setup: Create Delivery department with three workers each having 35 hours
        Department deliveryDept = new Department();
        deliveryDept.setName("Delivery");
        
        for (int i = 0; i < 3; i++) {
            NonShiftWorker worker = new NonShiftWorker();
            worker.setWeeklyWorkingHours(35.0);
            deliveryDept.addEmployee(worker);
        }
        
        company.addDepartment(deliveryDept);
        
        // Execute: Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify: Average should be 35 hours
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersDifferentHoursInDeliveryDepartment() {
        // Setup: Create Delivery department with two workers having 20 and 30 hours
        Department deliveryDept = new Department();
        deliveryDept.setName("Delivery");
        
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setWeeklyWorkingHours(20.0);
        deliveryDept.addEmployee(worker1);
        
        NonShiftWorker worker2 = new NonShiftWorker();
        worker2.setWeeklyWorkingHours(30.0);
        deliveryDept.addEmployee(worker2);
        
        company.addDepartment(deliveryDept);
        
        // Execute: Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify: Average should be 25 hours (20 + 30) / 2
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentWithNoWorkers() {
        // Setup: Create Delivery department with no workers
        Department deliveryDept = new Department();
        deliveryDept.setName("Delivery");
        
        // Add a salesperson (non-worker) to verify only workers are counted
        Salesperson salesperson = new Salesperson();
        deliveryDept.addEmployee(salesperson);
        
        company.addDepartment(deliveryDept);
        
        // Execute: Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify: Average should be 0 when no workers exist
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() {
        // Setup: Create multiple departments with mixed employees
        Department deliveryDept = new Department();
        deliveryDept.setName("Delivery");
        
        // Add 3 workers to Delivery department with different hours
        double[] deliveryHours = {25.0, 32.0, 28.0};
        for (double hours : deliveryHours) {
            ShiftWorker worker = new ShiftWorker();
            worker.setWeeklyWorkingHours(hours);
            deliveryDept.addEmployee(worker);
        }
        
        // Create other departments with employees
        Department salesDept = new Department();
        salesDept.setName("Sales");
        Salesperson salesperson = new Salesperson();
        salesDept.addEmployee(salesperson);
        
        Department hrDept = new Department();
        hrDept.setName("HR");
        NonShiftWorker hrWorker = new NonShiftWorker();
        hrWorker.setWeeklyWorkingHours(40.0);
        hrDept.addEmployee(hrWorker);
        
        // Add all departments to company
        company.addDepartment(deliveryDept);
        company.addDepartment(salesDept);
        company.addDepartment(hrDept);
        
        // Execute: Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify: Average should be (25 + 32 + 28) / 3 = 28.33 hours
        assertEquals(28.33, result, 0.01);
    }
}