import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR2Test {
    private Department deliveryDepartment;
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
        deliveryDepartment = new Department();
        deliveryDepartment.setType(DepartmentType.DELIVERY);
    }
    
    @Test
    public void testCase1_singleWorkerInDeliveryDepartment() {
        // Setup: One worker in the Delivery department with a weeklyWorkingHour of 40 hours
        Worker worker = new ShiftWorker();
        worker.setWeeklyWorkingHour(40);
        
        deliveryDepartment.getEmployees().add(worker);
        company.getDepartments().add(deliveryDepartment);
        
        // Execute: Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: The average working hours per week is 40 hours
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersInDeliveryDepartmentWithSameHours() {
        // Setup: Three workers in the Delivery department, each with a weeklyWorkingHour of 35 hours
        Worker worker1 = new ShiftWorker();
        worker1.setWeeklyWorkingHour(35);
        
        Worker worker2 = new ShiftWorker();
        worker2.setWeeklyWorkingHour(35);
        
        Worker worker3 = new ShiftWorker();
        worker3.setWeeklyWorkingHour(35);
        
        deliveryDepartment.getEmployees().add(worker1);
        deliveryDepartment.getEmployees().add(worker2);
        deliveryDepartment.getEmployees().add(worker3);
        company.getDepartments().add(deliveryDepartment);
        
        // Execute: Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: The average working hours per week is 35 hours
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersInDeliveryDepartmentWithDifferentHours() {
        // Setup: Two workers in the Delivery department. One has a weeklyWorkingHour of 20 hours and the other has 30 hours
        Worker worker1 = new ShiftWorker();
        worker1.setWeeklyWorkingHour(20);
        
        Worker worker2 = new ShiftWorker();
        worker2.setWeeklyWorkingHour(30);
        
        deliveryDepartment.getEmployees().add(worker1);
        deliveryDepartment.getEmployees().add(worker2);
        company.getDepartments().add(deliveryDepartment);
        
        // Execute: Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: The average working hours per week is 25 hours
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentWithNoWorkers() {
        // Setup: There are zero workers in the Delivery department
        // Only non-worker employees or empty department
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setName("Sales Person");
        deliveryDepartment.getEmployees().add(salesPerson);
        company.getDepartments().add(deliveryDepartment);
        
        // Execute: Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: The average working hours per week is 0 hours (as there are no workers)
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() {
        // Setup: There are 5 employees in total across all departments. 
        // Among them, 3 are in the Delivery department with weeklyWorkingHours of 25, 32, and 28 hours respectively
        
        // Create delivery department with 3 workers
        Worker worker1 = new ShiftWorker();
        worker1.setWeeklyWorkingHour(25);
        
        Worker worker2 = new ShiftWorker();
        worker2.setWeeklyWorkingHour(32);
        
        Worker worker3 = new ShiftWorker();
        worker3.setWeeklyWorkingHour(28);
        
        deliveryDepartment.getEmployees().add(worker1);
        deliveryDepartment.getEmployees().add(worker2);
        deliveryDepartment.getEmployees().add(worker3);
        
        // Create other departments with non-worker employees
        Department productionDepartment = new Department();
        productionDepartment.setType(DepartmentType.PRODUCTION);
        
        Manager manager = new Manager();
        manager.setName("Production Manager");
        productionDepartment.getEmployees().add(manager);
        
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setName("Sales Person");
        productionDepartment.getEmployees().add(salesPerson);
        
        company.getDepartments().add(deliveryDepartment);
        company.getDepartments().add(productionDepartment);
        
        // Execute: Calculate average working hours for delivery department
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: The average working hours per week for workers in the Delivery department is 28.33 (rounded to two decimal places) hours
        assertEquals(28.33, result, 0.01);
    }
}