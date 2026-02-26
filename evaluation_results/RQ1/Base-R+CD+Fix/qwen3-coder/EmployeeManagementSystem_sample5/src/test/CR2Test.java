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
        OffShiftWorker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        deliveryDepartment.getEmployees().add(worker);
        
        // Execute: Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: The average working hours per week is 40 hours
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersSameHoursInDeliveryDepartment() {
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
    public void testCase3_multipleWorkersDifferentHoursInDeliveryDepartment() {
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
    public void testCase4_deliveryDepartmentWithNoWorkers() {
        // Setup: Zero workers in the Delivery department (department is empty)
        
        // Execute: Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: The average working hours per week is 0 hours
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() {
        // Setup: 5 employees in total across all departments
        // 3 workers in Delivery department with different hours
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(25);
        deliveryDepartment.getEmployees().add(worker1);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(32);
        deliveryDepartment.getEmployees().add(worker2);
        
        OffShiftWorker worker3 = new OffShiftWorker();
        worker3.setWeeklyWorkingHour(28);
        deliveryDepartment.getEmployees().add(worker3);
        
        // Add other departments with employees (these should not affect the result)
        Department productionDepartment = new Department();
        productionDepartment.setType(DepartmentType.PRODUCTION);
        OffShiftWorker prodWorker = new OffShiftWorker();
        prodWorker.setWeeklyWorkingHour(40);
        productionDepartment.getEmployees().add(prodWorker);
        company.getDepartments().add(productionDepartment);
        
        Department controlDepartment = new Department();
        controlDepartment.setType(DepartmentType.CONTROL);
        Manager manager = new Manager();
        controlDepartment.getEmployees().add(manager);
        company.getDepartments().add(controlDepartment);
        
        // Execute: Calculate average working hours for Delivery department only
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: The average working hours per week for workers in the Delivery department is 28.33
        assertEquals(28.33, result, 0.01);
    }
}