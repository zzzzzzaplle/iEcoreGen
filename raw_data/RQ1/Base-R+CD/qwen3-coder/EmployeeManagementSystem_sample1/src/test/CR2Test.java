import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR2Test {
    
    private Company company;
    private Department deliveryDepartment;
    
    @Before
    public void setUp() {
        company = new Company();
        deliveryDepartment = new Department();
        deliveryDepartment.setType(DepartmentType.DELIVERY);
        company.getDepartments().add(deliveryDepartment);
    }
    
    @Test
    public void testCase1_singleWorkerInDeliveryDepartment() {
        // Create a worker with 40 weekly working hours
        OffShiftWorker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        
        // Add worker to delivery department
        deliveryDepartment.getEmployees().add(worker);
        
        // Calculate average working hours
        double average = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Expected: 40 hours
        assertEquals(40.0, average, 0.01);
    }
    
    @Test
    public void testCase2_multipleWorkersInDeliveryDepartmentWithSameHours() {
        // Create three workers with 35 weekly working hours each
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(35);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(35);
        
        OffShiftWorker worker3 = new OffShiftWorker();
        worker3.setWeeklyWorkingHour(35);
        
        // Add workers to delivery department
        deliveryDepartment.getEmployees().add(worker1);
        deliveryDepartment.getEmployees().add(worker2);
        deliveryDepartment.getEmployees().add(worker3);
        
        // Calculate average working hours
        double average = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Expected: 35 hours
        assertEquals(35.0, average, 0.01);
    }
    
    @Test
    public void testCase3_multipleWorkersInDeliveryDepartmentWithDifferentHours() {
        // Create two workers with different weekly working hours
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(20);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(30);
        
        // Add workers to delivery department
        deliveryDepartment.getEmployees().add(worker1);
        deliveryDepartment.getEmployees().add(worker2);
        
        // Calculate average working hours
        double average = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Expected: 25 hours (average of 20 and 30)
        assertEquals(25.0, average, 0.01);
    }
    
    @Test
    public void testCase4_deliveryDepartmentWithNoWorkers() {
        // No workers added to delivery department
        
        // Calculate average working hours
        double average = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Expected: 0 hours (as there are no workers)
        assertEquals(0.0, average, 0.01);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() {
        // Create delivery department workers
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(25);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(32);
        
        OffShiftWorker worker3 = new OffShiftWorker();
        worker3.setWeeklyWorkingHour(28);
        
        // Add delivery workers to delivery department
        deliveryDepartment.getEmployees().add(worker1);
        deliveryDepartment.getEmployees().add(worker2);
        deliveryDepartment.getEmployees().add(worker3);
        
        // Create workers for other departments (should not affect result)
        Department productionDepartment = new Department();
        productionDepartment.setType(DepartmentType.PRODUCTION);
        
        OffShiftWorker prodWorker = new OffShiftWorker();
        prodWorker.setWeeklyWorkingHour(40);
        productionDepartment.getEmployees().add(prodWorker);
        
        // Add production department to company
        company.getDepartments().add(productionDepartment);
        
        // Calculate average working hours for delivery department
        double average = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Expected: 28.33 hours (average of 25, 32, and 28)
        assertEquals(28.33, average, 0.01);
    }
}