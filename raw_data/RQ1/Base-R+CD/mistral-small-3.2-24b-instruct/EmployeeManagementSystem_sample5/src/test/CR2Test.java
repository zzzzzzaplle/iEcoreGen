import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR2Test {
    
    private Department deliveryDepartment;
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize fresh objects before each test
        deliveryDepartment = new Department();
        deliveryDepartment.setType(DepartmentType.DELIVERY);
        company = new Company();
    }
    
    @Test
    public void testCase1_singleWorkerInDeliveryDepartment() {
        // Test Case 1: Single worker in delivery department
        // Input: One worker in the Delivery department with a weeklyWorkingHour of 40 hours
        // Expected Output: The average working hours per week is 40 hours
        
        // Create and configure a single worker
        OffShiftWorker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        worker.setDepartment("Delivery");
        
        // Add worker to department and department to company
        deliveryDepartment.addEmployee(worker);
        company.addDepartment(deliveryDepartment);
        
        // Calculate and assert the average
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersSameHours() {
        // Test Case 2: Multiple workers in delivery department with same hours
        // Input: Three workers in the Delivery department, each with a weeklyWorkingHour of 35 hours
        // Expected Output: The average working hours per week is 35 hours
        
        // Create and configure three workers with same hours
        for (int i = 0; i < 3; i++) {
            OffShiftWorker worker = new OffShiftWorker();
            worker.setWeeklyWorkingHour(35);
            worker.setDepartment("Delivery");
            deliveryDepartment.addEmployee(worker);
        }
        
        company.addDepartment(deliveryDepartment);
        
        // Calculate and assert the average
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersDifferentHours() {
        // Test Case 3: Multiple workers in delivery department with different hours
        // Input: Two workers in the Delivery department. One has a weeklyWorkingHour of 20 hours and the other has 30 hours
        // Expected Output: The average working hours per week is 25 hours
        
        // Create first worker with 20 hours
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(20);
        worker1.setDepartment("Delivery");
        deliveryDepartment.addEmployee(worker1);
        
        // Create second worker with 30 hours
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(30);
        worker2.setDepartment("Delivery");
        deliveryDepartment.addEmployee(worker2);
        
        company.addDepartment(deliveryDepartment);
        
        // Calculate and assert the average (20 + 30) / 2 = 25
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentNoWorkers() {
        // Test Case 4: Delivery department with no workers
        // Input: There are zero workers in the Delivery department
        // Expected Output: The average working hours per week is 0 hours (as there are no workers)
        
        // Add department with no workers to company
        company.addDepartment(deliveryDepartment);
        
        // Calculate and assert the average should be 0
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() {
        // Test Case 5: Delivery department mixed with other departments
        // Input: There are 5 employees in total across all departments. Among them, 3 are in the 
        // Delivery department with weeklyWorkingHours of 25, 32, and 28 hours respectively
        // Expected Output: The average working hours per week for workers in the Delivery department is 28.33 (rounded to two decimal places) hours
        
        // Create and configure delivery department workers
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(25);
        worker1.setDepartment("Delivery");
        deliveryDepartment.addEmployee(worker1);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(32);
        worker2.setDepartment("Delivery");
        deliveryDepartment.addEmployee(worker2);
        
        OffShiftWorker worker3 = new OffShiftWorker();
        worker3.setWeeklyWorkingHour(28);
        worker3.setDepartment("Delivery");
        deliveryDepartment.addEmployee(worker3);
        
        // Create other departments with employees (these should not affect the calculation)
        Department productionDepartment = new Department();
        productionDepartment.setType(DepartmentType.PRODUCTION);
        
        Manager manager = new Manager();
        manager.setDepartment("Production");
        productionDepartment.addEmployee(manager);
        
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setDepartment("Production");
        productionDepartment.addEmployee(salesPerson);
        
        // Add all departments to company
        company.addDepartment(deliveryDepartment);
        company.addDepartment(productionDepartment);
        
        // Calculate and assert the average (25 + 32 + 28) / 3 = 28.333...
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        assertEquals(28.33, result, 0.01); // Allowing 0.01 delta for rounding
    }
}