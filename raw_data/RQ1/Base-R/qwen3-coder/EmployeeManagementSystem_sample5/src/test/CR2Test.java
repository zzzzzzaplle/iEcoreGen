import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize a new Company before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_singleWorkerInDeliveryDepartment() {
        // Test Case 1: Single worker in delivery department
        // Input: One worker in the Delivery department with a weeklyWorkingHour of 40 hours
        Worker worker = new Worker();
        worker.setDepartment("Delivery");
        worker.setWeeklyWorkingHours(40.0);
        company.addEmployee(worker);
        
        // Expected Output: The average working hours per week is 40 hours
        double expectedAverage = 40.0;
        double actualAverage = company.findAverageWorkingHoursInDelivery();
        
        assertEquals("Average should be 40.0 for single worker with 40 hours", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersSameHours() {
        // Test Case 2: Multiple workers in delivery department with same hours
        // Input: Three workers in the Delivery department, each with a weeklyWorkingHour of 35 hours
        for (int i = 0; i < 3; i++) {
            Worker worker = new Worker();
            worker.setDepartment("Delivery");
            worker.setWeeklyWorkingHours(35.0);
            company.addEmployee(worker);
        }
        
        // Expected Output: The average working hours per week is 35 hours
        double expectedAverage = 35.0;
        double actualAverage = company.findAverageWorkingHoursInDelivery();
        
        assertEquals("Average should be 35.0 for three workers with 35 hours each", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersDifferentHours() {
        // Test Case 3: Multiple workers in delivery department with different hours
        // Input: Two workers in the Delivery department. One has a weeklyWorkingHour of 20 hours and the other has 30 hours
        Worker worker1 = new Worker();
        worker1.setDepartment("Delivery");
        worker1.setWeeklyWorkingHours(20.0);
        company.addEmployee(worker1);
        
        Worker worker2 = new Worker();
        worker2.setDepartment("Delivery");
        worker2.setWeeklyWorkingHours(30.0);
        company.addEmployee(worker2);
        
        // Expected Output: The average working hours per week is 25 hours
        double expectedAverage = 25.0;
        double actualAverage = company.findAverageWorkingHoursInDelivery();
        
        assertEquals("Average should be 25.0 for workers with 20 and 30 hours", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentNoWorkers() {
        // Test Case 4: Delivery department with no workers
        // Input: There are zero workers in the Delivery department
        // (No workers added to company, only setup creates empty company)
        
        // Expected Output: The average working hours per week is 0 hours (as there are no workers)
        double expectedAverage = 0.0;
        double actualAverage = company.findAverageWorkingHoursInDelivery();
        
        assertEquals("Average should be 0.0 when no workers in delivery department", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_mixedDepartments() {
        // Test Case 5: Delivery department mixed with other departments
        // Input: There are 5 employees in total across all departments. 
        // Among them, 3 are in the Delivery department with weeklyWorkingHours of 25, 32, and 28 hours respectively.
        
        // Add 3 workers to Delivery department
        Worker deliveryWorker1 = new Worker();
        deliveryWorker1.setDepartment("Delivery");
        deliveryWorker1.setWeeklyWorkingHours(25.0);
        company.addEmployee(deliveryWorker1);
        
        Worker deliveryWorker2 = new Worker();
        deliveryWorker2.setDepartment("Delivery");
        deliveryWorker2.setWeeklyWorkingHours(32.0);
        company.addEmployee(deliveryWorker2);
        
        Worker deliveryWorker3 = new Worker();
        deliveryWorker3.setDepartment("Delivery");
        deliveryWorker3.setWeeklyWorkingHours(28.0);
        company.addEmployee(deliveryWorker3);
        
        // Add 2 employees from other departments (should not be counted in average)
        Worker otherDeptWorker = new Worker();
        otherDeptWorker.setDepartment("Sales");
        otherDeptWorker.setWeeklyWorkingHours(40.0);
        company.addEmployee(otherDeptWorker);
        
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setDepartment("Marketing");
        company.addEmployee(salesPerson);
        
        // Expected Output: The average working hours per week for workers in the Delivery department is 28.33 (rounded to two decimal places) hours
        double expectedAverage = 28.33; // (25 + 32 + 28) / 3 = 28.333...
        double actualAverage = company.findAverageWorkingHoursInDelivery();
        
        assertEquals("Average should be 28.33 for delivery workers with 25, 32, and 28 hours", 
                     expectedAverage, actualAverage, 0.01); // Using delta of 0.01 for rounding
    }
}