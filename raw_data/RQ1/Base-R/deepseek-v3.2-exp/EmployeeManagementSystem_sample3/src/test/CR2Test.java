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
        // Test Case 1: Single worker in delivery department
        // Input: One worker in the Delivery department with a weeklyWorkingHour of 40 hours
        // Expected Output: The average working hours per week is 40 hours
        
        // Create and configure a worker in delivery department
        Worker worker = new Worker();
        worker.setDepartment("Delivery");
        worker.setWeeklyWorkingHours(40.0);
        
        // Add worker to company
        company.addEmployee(worker);
        
        // Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify the result matches expected output
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersInDeliveryDepartmentSameHours() {
        // Test Case 2: Multiple workers in delivery department with same hours
        // Input: Three workers in the Delivery department, each with a weeklyWorkingHour of 35 hours
        // Expected Output: The average working hours per week is 35 hours
        
        // Create and configure three workers in delivery department
        for (int i = 0; i < 3; i++) {
            Worker worker = new Worker();
            worker.setDepartment("Delivery");
            worker.setWeeklyWorkingHours(35.0);
            company.addEmployee(worker);
        }
        
        // Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify the result matches expected output
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersInDeliveryDepartmentDifferentHours() {
        // Test Case 3: Multiple workers in delivery department with different hours
        // Input: Two workers in the Delivery department. One has a weeklyWorkingHour of 20 hours and the other has 30 hours
        // Expected Output: The average working hours per week is 25 hours
        
        // Create and configure first worker
        Worker worker1 = new Worker();
        worker1.setDepartment("Delivery");
        worker1.setWeeklyWorkingHours(20.0);
        company.addEmployee(worker1);
        
        // Create and configure second worker
        Worker worker2 = new Worker();
        worker2.setDepartment("Delivery");
        worker2.setWeeklyWorkingHours(30.0);
        company.addEmployee(worker2);
        
        // Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify the result matches expected output
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentWithNoWorkers() {
        // Test Case 4: Delivery department with no workers
        // Input: There are zero workers in the Delivery department
        // Expected Output: The average working hours per week is 0 hours (as there are no workers)
        
        // Add some non-worker employees to ensure company is not empty
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setDepartment("Sales");
        company.addEmployee(salesPerson);
        
        Manager manager = new Manager();
        manager.setDepartment("Management");
        company.addEmployee(manager);
        
        // Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify the result matches expected output (0.0)
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() {
        // Test Case 5: Delivery department mixed with other departments
        // Input: There are 5 employees in total across all departments. 
        // Among them, 3 are in the Delivery department with weeklyWorkingHours of 25, 32, and 28 hours respectively
        // Expected Output: The average working hours per week for workers in the Delivery department is 28.33 (rounded to two decimal places) hours
        
        // Create and configure workers in delivery department
        Worker worker1 = new Worker();
        worker1.setDepartment("Delivery");
        worker1.setWeeklyWorkingHours(25.0);
        company.addEmployee(worker1);
        
        Worker worker2 = new Worker();
        worker2.setDepartment("Delivery");
        worker2.setWeeklyWorkingHours(32.0);
        company.addEmployee(worker2);
        
        Worker worker3 = new Worker();
        worker3.setDepartment("Delivery");
        worker3.setWeeklyWorkingHours(28.0);
        company.addEmployee(worker3);
        
        // Add employees from other departments
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setDepartment("Sales");
        company.addEmployee(salesPerson);
        
        Manager manager = new Manager();
        manager.setDepartment("Management");
        company.addEmployee(manager);
        
        // Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify the result matches expected output (28.33)
        assertEquals(28.33, result, 0.01);
    }
}