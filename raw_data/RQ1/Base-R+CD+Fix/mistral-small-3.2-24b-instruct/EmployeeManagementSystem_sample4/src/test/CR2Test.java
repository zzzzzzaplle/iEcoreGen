import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Department deliveryDepartment;
    private Department productionDepartment;
    
    @Before
    public void setUp() {
        // Initialize departments before each test
        deliveryDepartment = new Department();
        deliveryDepartment.setType(DepartmentType.DELIVERY);
        deliveryDepartment.setEmployees(new ArrayList<Employee>());
        
        productionDepartment = new Department();
        productionDepartment.setType(DepartmentType.PRODUCTION);
        productionDepartment.setEmployees(new ArrayList<Employee>());
    }
    
    @Test
    public void testCase1_singleWorkerInDeliveryDepartment() {
        // Test Case 1: Single worker in delivery department
        // Input: One worker in the Delivery department with a weeklyWorkingHour of 40 hours
        ShiftWorker worker = new ShiftWorker();
        worker.setWeeklyWorkingHour(40);
        
        deliveryDepartment.getEmployees().add(worker);
        
        // Expected Output: The average working hours per week is 40 hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersSameHours() {
        // Test Case 2: Multiple workers in delivery department with same hours
        // Input: Three workers in the Delivery department, each with a weeklyWorkingHour of 35 hours
        for (int i = 0; i < 3; i++) {
            OffShiftWorker worker = new OffShiftWorker();
            worker.setWeeklyWorkingHour(35);
            deliveryDepartment.getEmployees().add(worker);
        }
        
        // Expected Output: The average working hours per week is 35 hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersDifferentHours() {
        // Test Case 3: Multiple workers in delivery department with different hours
        // Input: Two workers in the Delivery department. One has a weeklyWorkingHour of 20 hours and the other has 30 hours
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setWeeklyWorkingHour(20);
        deliveryDepartment.getEmployees().add(worker1);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(30);
        deliveryDepartment.getEmployees().add(worker2);
        
        // Expected Output: The average working hours per week is 25 hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentWithNoWorkers() {
        // Test Case 4: Delivery department with no workers
        // Input: There are zero workers in the Delivery department
        // (department already has empty employee list from setup)
        
        // Expected Output: The average working hours per week is 0 hours (as there are no workers)
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() {
        // Test Case 5: Delivery department mixed with other departments
        // Input: There are 5 employees in total across all departments. 
        // Among them, 3 are in the Delivery department with weeklyWorkingHours of 25, 32, and 28 hours respectively.
        
        // Add workers to delivery department
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setWeeklyWorkingHour(25);
        deliveryDepartment.getEmployees().add(worker1);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(32);
        deliveryDepartment.getEmployees().add(worker2);
        
        ShiftWorker worker3 = new ShiftWorker();
        worker3.setWeeklyWorkingHour(28);
        deliveryDepartment.getEmployees().add(worker3);
        
        // Add non-worker employees to production department (should not affect the calculation)
        Manager manager = new Manager();
        SalesPeople salesPerson = new SalesPeople();
        productionDepartment.getEmployees().add(manager);
        productionDepartment.getEmployees().add(salesPerson);
        
        // Expected Output: The average working hours per week for workers in the Delivery department is 28.33 (rounded to two decimal places) hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        assertEquals(28.33, result, 0.01); // Using delta of 0.01 for rounding to two decimal places
    }
}