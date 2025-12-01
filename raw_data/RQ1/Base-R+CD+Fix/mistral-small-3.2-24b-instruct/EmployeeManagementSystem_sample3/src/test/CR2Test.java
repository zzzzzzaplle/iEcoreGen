import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Company company;
    private Department deliveryDepartment;
    
    @Before
    public void setUp() {
        company = new Company();
        deliveryDepartment = new Department();
        deliveryDepartment.setType(DepartmentType.DELIVERY);
    }
    
    @Test
    public void testCase1_singleWorkerInDeliveryDepartment() {
        // Test Case 1: Single worker in delivery department
        // Input: One worker in the Delivery department with a weeklyWorkingHour of 40 hours
        // Expected Output: The average working hours per week is 40 hours
        
        // Create and configure a worker
        OffShiftWorker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        
        // Add worker to delivery department
        deliveryDepartment.getEmployees().add(worker);
        company.getDepartments().add(deliveryDepartment);
        
        // Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify the result
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersSameHours() {
        // Test Case 2: Multiple workers in delivery department with same hours
        // Input: Three workers in the Delivery department, each with a weeklyWorkingHour of 35 hours
        // Expected Output: The average working hours per week is 35 hours
        
        // Create and configure three workers with same hours
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
        company.getDepartments().add(deliveryDepartment);
        
        // Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify the result
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersDifferentHours() {
        // Test Case 3: Multiple workers in delivery department with different hours
        // Input: Two workers in the Delivery department. One has a weeklyWorkingHour of 20 hours and the other has 30 hours
        // Expected Output: The average working hours per week is 25 hours
        
        // Create and configure two workers with different hours
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(20);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(30);
        
        // Add workers to delivery department
        deliveryDepartment.getEmployees().add(worker1);
        deliveryDepartment.getEmployees().add(worker2);
        company.getDepartments().add(deliveryDepartment);
        
        // Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify the result
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentNoWorkers() {
        // Test Case 4: Delivery department with no workers
        // Input: There are zero workers in the Delivery department
        // Expected Output: The average working hours per week is 0 hours (as there are no workers)
        
        // Add delivery department with no workers
        company.getDepartments().add(deliveryDepartment);
        
        // Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify the result is 0 when no workers
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_mixedDepartmentsWithDeliveryDepartment() {
        // Test Case 5: Delivery department mixed with other departments
        // Input: There are 5 employees in total across all departments. Among them, 3 are in the Delivery 
        //        department with weeklyWorkingHours of 25, 32, and 28 hours respectively
        // Expected Output: The average working hours per week for workers in the Delivery department is 28.33 (rounded to two decimal places) hours
        
        // Create delivery department workers
        OffShiftWorker deliveryWorker1 = new OffShiftWorker();
        deliveryWorker1.setWeeklyWorkingHour(25);
        
        OffShiftWorker deliveryWorker2 = new OffShiftWorker();
        deliveryWorker2.setWeeklyWorkingHour(32);
        
        OffShiftWorker deliveryWorker3 = new OffShiftWorker();
        deliveryWorker3.setWeeklyWorkingHour(28);
        
        // Add workers to delivery department
        deliveryDepartment.getEmployees().add(deliveryWorker1);
        deliveryDepartment.getEmployees().add(deliveryWorker2);
        deliveryDepartment.getEmployees().add(deliveryWorker3);
        
        // Create other departments with non-worker employees
        Department productionDepartment = new Department();
        productionDepartment.setType(DepartmentType.PRODUCTION);
        
        // Add a manager to production department (non-worker)
        Manager productionManager = new Manager();
        productionDepartment.getEmployees().add(productionManager);
        
        Department controlDepartment = new Department();
        controlDepartment.setType(DepartmentType.CONTROL);
        
        // Add a salesperson to control department (non-worker)
        SalesPeople salesPerson = new SalesPeople();
        controlDepartment.getEmployees().add(salesPerson);
        
        // Add all departments to company
        company.getDepartments().add(deliveryDepartment);
        company.getDepartments().add(productionDepartment);
        company.getDepartments().add(controlDepartment);
        
        // Calculate average working hours for delivery department
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify the result (25 + 32 + 28) / 3 = 85 / 3 = 28.333...
        assertEquals(28.33, result, 0.01); // Using delta of 0.01 for rounding to two decimal places
    }
}