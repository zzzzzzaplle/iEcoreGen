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
        // Setup: Create a delivery department with one worker having 40 hours
        DeliveryDepartment deliveryDept = new DeliveryDepartment();
        Worker worker = new Worker();
        worker.setWeeklyWorkingHours(40.0);
        deliveryDept.getEmployees().add(worker);
        
        company.getDepartments().add(deliveryDept);
        
        // Execute: Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify: Average should be exactly 40 hours
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersSameHoursInDeliveryDepartment() {
        // Setup: Create a delivery department with three workers, each with 35 hours
        DeliveryDepartment deliveryDept = new DeliveryDepartment();
        
        Worker worker1 = new Worker();
        worker1.setWeeklyWorkingHours(35.0);
        deliveryDept.getEmployees().add(worker1);
        
        Worker worker2 = new Worker();
        worker2.setWeeklyWorkingHours(35.0);
        deliveryDept.getEmployees().add(worker2);
        
        Worker worker3 = new Worker();
        worker3.setWeeklyWorkingHours(35.0);
        deliveryDept.getEmployees().add(worker3);
        
        company.getDepartments().add(deliveryDept);
        
        // Execute: Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify: Average should be exactly 35 hours
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersDifferentHoursInDeliveryDepartment() {
        // Setup: Create a delivery department with two workers having different hours
        DeliveryDepartment deliveryDept = new DeliveryDepartment();
        
        Worker worker1 = new Worker();
        worker1.setWeeklyWorkingHours(20.0);
        deliveryDept.getEmployees().add(worker1);
        
        Worker worker2 = new Worker();
        worker2.setWeeklyWorkingHours(30.0);
        deliveryDept.getEmployees().add(worker2);
        
        company.getDepartments().add(deliveryDept);
        
        // Execute: Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify: Average should be 25 hours (20 + 30) / 2 = 25
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentWithNoWorkers() {
        // Setup: Create an empty delivery department
        DeliveryDepartment deliveryDept = new DeliveryDepartment();
        company.getDepartments().add(deliveryDept);
        
        // Execute: Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify: Average should be 0 when there are no workers
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() {
        // Setup: Create multiple departments including delivery department with 3 workers
        DeliveryDepartment deliveryDept = new DeliveryDepartment();
        
        // Add 3 workers to delivery department with different hours
        Worker worker1 = new Worker();
        worker1.setWeeklyWorkingHours(25.0);
        deliveryDept.getEmployees().add(worker1);
        
        Worker worker2 = new Worker();
        worker2.setWeeklyWorkingHours(32.0);
        deliveryDept.getEmployees().add(worker2);
        
        Worker worker3 = new Worker();
        worker3.setWeeklyWorkingHours(28.0);
        deliveryDept.getEmployees().add(worker3);
        
        // Add other departments with workers (should not affect the calculation)
        ProductionDepartment productionDept = new ProductionDepartment();
        Worker prodWorker = new Worker();
        prodWorker.setWeeklyWorkingHours(40.0);
        productionDept.getEmployees().add(prodWorker);
        
        ControlDepartment controlDept = new ControlDepartment();
        Worker controlWorker = new Worker();
        controlWorker.setWeeklyWorkingHours(38.0);
        controlDept.getEmployees().add(controlWorker);
        
        company.getDepartments().add(deliveryDept);
        company.getDepartments().add(productionDept);
        company.getDepartments().add(controlDept);
        
        // Execute: Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify: Average should be (25 + 32 + 28) / 3 = 28.33 (rounded to two decimal places)
        assertEquals(28.33, result, 0.01);
    }
}