import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;

public class CR2Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize company before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_singleWorkerInDeliveryDepartment() {
        // Test Case 1: Single worker in delivery department
        // Input: One worker in the Delivery department with a weeklyWorkingHour of 40 hours
        Worker worker = new Worker();
        worker.setDepartment("delivery");
        worker.setWeeklyWorkingHours(40);
        company.addEmployee(worker);
        
        // Expected Output: The average working hours per week is 40 hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersInDeliveryDepartmentWithSameHours() {
        // Test Case 2: Multiple workers in delivery department with same hours
        // Input: Three workers in the Delivery department, each with a weeklyWorkingHour of 35 hours
        for (int i = 0; i < 3; i++) {
            Worker worker = new Worker();
            worker.setDepartment("delivery");
            worker.setWeeklyWorkingHours(35);
            company.addEmployee(worker);
        }
        
        // Expected Output: The average working hours per week is 35 hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersInDeliveryDepartmentWithDifferentHours() {
        // Test Case 3: Multiple workers in delivery department with different hours
        // Input: Two workers in the Delivery department. One has a weeklyWorkingHour of 20 hours and the other has 30 hours
        Worker worker1 = new Worker();
        worker1.setDepartment("delivery");
        worker1.setWeeklyWorkingHours(20);
        company.addEmployee(worker1);
        
        Worker worker2 = new Worker();
        worker2.setDepartment("delivery");
        worker2.setWeeklyWorkingHours(30);
        company.addEmployee(worker2);
        
        // Expected Output: The average working hours per week is 25 hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentWithNoWorkers() {
        // Test Case 4: Delivery department with no workers
        // Input: There are zero workers in the Delivery department
        // No workers added to company
        
        // Expected Output: The average working hours per week is 0 hours (as there are no workers)
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() {
        // Test Case 5: Delivery department mixed with other departments
        // Input: There are 5 employees in total across all departments. 
        // Among them, 3 are in the Delivery department with weeklyWorkingHours of 25, 32, and 28 hours respectively
        
        // Add 3 workers to delivery department
        Worker worker1 = new Worker();
        worker1.setDepartment("delivery");
        worker1.setWeeklyWorkingHours(25);
        company.addEmployee(worker1);
        
        Worker worker2 = new Worker();
        worker2.setDepartment("delivery");
        worker2.setWeeklyWorkingHours(32);
        company.addEmployee(worker2);
        
        Worker worker3 = new Worker();
        worker3.setDepartment("delivery");
        worker3.setWeeklyWorkingHours(28);
        company.addEmployee(worker3);
        
        // Add 2 employees from other departments (should not be counted)
        Worker otherDeptWorker = new Worker();
        otherDeptWorker.setDepartment("marketing");
        otherDeptWorker.setWeeklyWorkingHours(40);
        company.addEmployee(otherDeptWorker);
        
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setDepartment("sales");
        company.addEmployee(salesPerson);
        
        // Expected Output: The average working hours per week for workers in the Delivery department is 28.33 (rounded to two decimal places) hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        assertEquals(28.33, result, 0.01); // Allow small delta for floating point precision
    }
}