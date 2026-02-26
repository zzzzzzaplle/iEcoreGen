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
        // Setup: One worker in the Delivery department with a weeklyWorkingHour of 40 hours
        ShiftWorker worker = new ShiftWorker();
        worker.setWeeklyWorkingHour(40);
        deliveryDepartment.getEmployees().add(worker);
        
        // Execute: Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: The average working hours per week is 40 hours
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersSameHours() {
        // Setup: Three workers in the Delivery department, each with a weeklyWorkingHour of 35 hours
        for (int i = 0; i < 3; i++) {
            OffShiftWorker worker = new OffShiftWorker();
            worker.setWeeklyWorkingHour(35);
            deliveryDepartment.getEmployees().add(worker);
        }
        
        // Execute: Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: The average working hours per week is 35 hours
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersDifferentHours() {
        // Setup: Two workers in the Delivery department with different hours
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(20);
        deliveryDepartment.getEmployees().add(worker1);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(30);
        deliveryDepartment.getEmployees().add(worker2);
        
        // Execute: Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: The average working hours per week is 25 hours
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_noWorkersInDeliveryDepartment() {
        // Setup: Zero workers in the Delivery department
        // (deliveryDepartment already has no workers from setup)
        
        // Execute: Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: The average working hours per week is 0 hours
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() {
        // Setup: 5 employees across departments, 3 in Delivery with hours 25, 32, and 28
        DeliveryDepartment deliveryDept = new DeliveryDepartment();
        deliveryDept.setType(DepartmentType.DELIVERY);
        
        // Add 3 workers to delivery department
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(25);
        deliveryDept.getEmployees().add(worker1);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(32);
        deliveryDept.getEmployees().add(worker2);
        
        OffShiftWorker worker3 = new OffShiftWorker();
        worker3.setWeeklyWorkingHour(28);
        deliveryDept.getEmployees().add(worker3);
        
        // Add 2 non-worker employees to delivery department (should not affect calculation)
        Manager manager = new Manager();
        deliveryDept.getEmployees().add(manager);
        
        SalesPeople salesPerson = new SalesPeople();
        deliveryDept.getEmployees().add(salesPerson);
        
        // Execute: Calculate average working hours for delivery department
        double result = deliveryDept.calculateAverageWorkerWorkingHours();
        
        // Verify: The average working hours per week is 28.33 (rounded to two decimal places)
        assertEquals(28.33, result, 0.01);
    }
    
    // Helper class to access protected method for testing
    private static class DeliveryDepartment extends Department {
        @Override
        public double calculateAverageWorkerWorkingHours() {
            return super.calculateAverageWorkerWorkingHours();
        }
    }
}