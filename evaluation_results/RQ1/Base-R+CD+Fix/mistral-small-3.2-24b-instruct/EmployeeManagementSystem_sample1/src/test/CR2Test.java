import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Department deliveryDepartment;
    
    @Before
    public void setUp() {
        // Create a new delivery department before each test
        deliveryDepartment = new Department();
        deliveryDepartment.setType(DepartmentType.DELIVERY);
        deliveryDepartment.setEmployees(new ArrayList<>());
    }
    
    @Test
    public void testCase1_singleWorkerInDeliveryDepartment() {
        // Input: One worker in the Delivery department with a weeklyWorkingHour of 40 hours
        OffShiftWorker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        
        deliveryDepartment.getEmployees().add(worker);
        
        // Expected Output: The average working hours per week is 40 hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersInDeliveryDepartmentWithSameHours() {
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
    public void testCase3_multipleWorkersInDeliveryDepartmentWithDifferentHours() {
        // Input: Two workers in the Delivery department. One has a weeklyWorkingHour of 20 hours and the other has 30 hours
        OffShiftWorker worker1 = new OffShiftWorker();
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
        // Input: There are zero workers in the Delivery department
        // No workers added to the department
        
        // Expected Output: The average working hours per week is 0 hours (as there are no workers)
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() {
        // Input: There are 5 employees in total across all departments. 
        // Among them, 3 are in the Delivery department with weeklyWorkingHours of 25, 32, and 28 hours respectively.
        
        // Add 3 workers to delivery department
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(25);
        deliveryDepartment.getEmployees().add(worker1);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(32);
        deliveryDepartment.getEmployees().add(worker2);
        
        OffShiftWorker worker3 = new OffShiftWorker();
        worker3.setWeeklyWorkingHour(28);
        deliveryDepartment.getEmployees().add(worker3);
        
        // Expected Output: The average working hours per week for workers in the Delivery department is 28.33 (rounded to two decimal places) hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        assertEquals(28.33, result, 0.01); // Using delta of 0.01 for rounding to two decimal places
    }
}