// IMPORTANT: Do not include package declaration
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
        // Input: One worker in the Delivery department with a weeklyWorkingHour of 40 hours
        Worker worker = new ShiftWorker();
        worker.setWeeklyWorkingHour(40);
        deliveryDepartment.getEmployees().add(worker);
        
        // Expected Output: The average working hours per week is 40 hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        assertEquals(40.0, result, 0.01);
    }
    
    @Test
    public void testCase2_multipleWorkersInDeliveryDepartmentWithSameHours() {
        // Input: Three workers in the Delivery department, each with a weeklyWorkingHour of 35 hours
        for (int i = 0; i < 3; i++) {
            Worker worker = new ShiftWorker();
            worker.setWeeklyWorkingHour(35);
            deliveryDepartment.getEmployees().add(worker);
        }
        
        // Expected Output: The average working hours per week is 35 hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        assertEquals(35.0, result, 0.01);
    }
    
    @Test
    public void testCase3_multipleWorkersInDeliveryDepartmentWithDifferentHours() {
        // Input: Two workers in the Delivery department. One has a weeklyWorkingHour of 20 hours and the other has 30 hours
        Worker worker1 = new ShiftWorker();
        worker1.setWeeklyWorkingHour(20);
        deliveryDepartment.getEmployees().add(worker1);
        
        Worker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(30);
        deliveryDepartment.getEmployees().add(worker2);
        
        // Expected Output: The average working hours per week is 25 hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        assertEquals(25.0, result, 0.01);
    }
    
    @Test
    public void testCase4_deliveryDepartmentWithNoWorkers() {
        // Input: There are zero workers in the Delivery department
        // Expected Output: The average working hours per week is 0 hours (as there are no workers)
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        assertEquals(0.0, result, 0.01);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() {
        // Input: There are 5 employees in total across all departments. Among them, 3 are in the Delivery department 
        // with weeklyWorkingHours of 25, 32, and 28 hours respectively
        
        // Add workers to delivery department
        Worker worker1 = new ShiftWorker();
        worker1.setWeeklyWorkingHour(25);
        deliveryDepartment.getEmployees().add(worker1);
        
        Worker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(32);
        deliveryDepartment.getEmployees().add(worker2);
        
        Worker worker3 = new ShiftWorker();
        worker3.setWeeklyWorkingHour(28);
        deliveryDepartment.getEmployees().add(worker3);
        
        // Add other departments with employees
        Department productionDepartment = new Department();
        productionDepartment.setType(DepartmentType.PRODUCTION);
        Worker productionWorker = new ShiftWorker();
        productionWorker.setWeeklyWorkingHour(40);
        productionDepartment.getEmployees().add(productionWorker);
        company.getDepartments().add(productionDepartment);
        
        Department controlDepartment = new Department();
        controlDepartment.setType(DepartmentType.CONTROL);
        Manager controlManager = new Manager();
        controlManager.setSalary(50000);
        controlDepartment.getEmployees().add(controlManager);
        company.getDepartments().add(controlDepartment);
        
        // Expected Output: The average working hours per week for workers in the Delivery department is 28.33 hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        assertEquals(28.33, result, 0.01);
    }
}