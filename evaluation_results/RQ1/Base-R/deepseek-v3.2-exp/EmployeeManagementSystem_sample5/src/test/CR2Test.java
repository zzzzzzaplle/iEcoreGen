import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR2Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_SingleWorkerInDeliveryDepartment() {
        // Test Case 1: Single worker in delivery department
        // Input: One worker in the Delivery department with a weeklyWorkingHour of 40 hours
        // Expected Output: The average working hours per week is 40 hours
        
        // Create delivery department
        Department deliveryDept = new Department();
        deliveryDept.setName("Delivery");
        
        // Create worker and set properties
        Worker worker = new Worker();
        worker.setWeeklyWorkingHours(40.0);
        worker.setDepartment(deliveryDept);
        
        // Add worker to company
        company.addEmployee(worker);
        
        // Calculate and verify average
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_MultipleWorkersSameHours() {
        // Test Case 2: Multiple workers in delivery department with same hours
        // Input: Three workers in the Delivery department, each with a weeklyWorkingHour of 35 hours
        // Expected Output: The average working hours per week is 35 hours
        
        // Create delivery department
        Department deliveryDept = new Department();
        deliveryDept.setName("Delivery");
        
        // Create three workers with same hours
        Worker worker1 = new Worker();
        worker1.setWeeklyWorkingHours(35.0);
        worker1.setDepartment(deliveryDept);
        
        Worker worker2 = new Worker();
        worker2.setWeeklyWorkingHours(35.0);
        worker2.setDepartment(deliveryDept);
        
        Worker worker3 = new Worker();
        worker3.setWeeklyWorkingHours(35.0);
        worker3.setDepartment(deliveryDept);
        
        // Add workers to company
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        company.addEmployee(worker3);
        
        // Calculate and verify average
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleWorkersDifferentHours() {
        // Test Case 3: Multiple workers in delivery department with different hours
        // Input: Two workers in the Delivery department. One has weeklyWorkingHour of 20 hours and the other has 30 hours
        // Expected Output: The average working hours per week is 25 hours
        
        // Create delivery department
        Department deliveryDept = new Department();
        deliveryDept.setName("Delivery");
        
        // Create two workers with different hours
        Worker worker1 = new Worker();
        worker1.setWeeklyWorkingHours(20.0);
        worker1.setDepartment(deliveryDept);
        
        Worker worker2 = new Worker();
        worker2.setWeeklyWorkingHours(30.0);
        worker2.setDepartment(deliveryDept);
        
        // Add workers to company
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        
        // Calculate and verify average
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_DeliveryDepartmentWithNoWorkers() {
        // Test Case 4: Delivery department with no workers
        // Input: There are zero workers in the Delivery department
        // Expected Output: The average working hours per week is 0 hours (as there are no workers)
        
        // Create delivery department but no workers
        Department deliveryDept = new Department();
        deliveryDept.setName("Delivery");
        
        // Add some non-worker employees to delivery department
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setDepartment(deliveryDept);
        company.addEmployee(salesPerson);
        
        Manager manager = new Manager();
        manager.setDepartment(deliveryDept);
        company.addEmployee(manager);
        
        // Calculate and verify average (should be 0 since no workers)
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_MixedDepartmentsWithDeliveryWorkers() {
        // Test Case 5: Delivery department mixed with other departments
        // Input: There are 5 employees in total across all departments. Among them, 3 are in the Delivery department 
        // with weeklyWorkingHours of 25, 32, and 28 hours respectively
        // Expected Output: The average working hours per week for workers in the Delivery department is 28.33 hours
        
        // Create departments
        Department deliveryDept = new Department();
        deliveryDept.setName("Delivery");
        
        Department salesDept = new Department();
        salesDept.setName("Sales");
        
        Department hrDept = new Department();
        hrDept.setName("HR");
        
        // Create delivery department workers
        Worker deliveryWorker1 = new Worker();
        deliveryWorker1.setWeeklyWorkingHours(25.0);
        deliveryWorker1.setDepartment(deliveryDept);
        
        Worker deliveryWorker2 = new Worker();
        deliveryWorker2.setWeeklyWorkingHours(32.0);
        deliveryWorker2.setDepartment(deliveryDept);
        
        Worker deliveryWorker3 = new Worker();
        deliveryWorker3.setWeeklyWorkingHours(28.0);
        deliveryWorker3.setDepartment(deliveryDept);
        
        // Create non-delivery employees
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setDepartment(salesDept);
        
        Manager hrManager = new Manager();
        hrManager.setDepartment(hrDept);
        
        // Add all employees to company
        company.addEmployee(deliveryWorker1);
        company.addEmployee(deliveryWorker2);
        company.addEmployee(deliveryWorker3);
        company.addEmployee(salesPerson);
        company.addEmployee(hrManager);
        
        // Calculate and verify average (should only consider delivery department workers)
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        assertEquals(28.33, result, 0.01); // Allow small delta for floating point precision
    }
}