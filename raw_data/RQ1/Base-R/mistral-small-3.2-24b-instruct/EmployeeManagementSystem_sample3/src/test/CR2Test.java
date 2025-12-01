import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

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
        worker.setWeeklyWorkingHours(40);
        company.addEmployee(worker);
        
        // Calculate average working hours
        double averageHours = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Expected Output: The average working hours per week is 40 hours
        assertEquals(40.0, averageHours, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersInDeliveryDepartmentSameHours() {
        // Test Case 2: Multiple workers in delivery department with same hours
        // Input: Three workers in the Delivery department, each with a weeklyWorkingHour of 35 hours
        for (int i = 0; i < 3; i++) {
            Worker worker = new Worker();
            worker.setDepartment("Delivery");
            worker.setWeeklyWorkingHours(35);
            company.addEmployee(worker);
        }
        
        // Calculate average working hours
        double averageHours = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Expected Output: The average working hours per week is 35 hours
        assertEquals(35.0, averageHours, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersInDeliveryDepartmentDifferentHours() {
        // Test Case 3: Multiple workers in delivery department with different hours
        // Input: Two workers in the Delivery department. One has a weeklyWorkingHour of 20 hours and the other has 30 hours
        Worker worker1 = new Worker();
        worker1.setDepartment("Delivery");
        worker1.setWeeklyWorkingHours(20);
        company.addEmployee(worker1);
        
        Worker worker2 = new Worker();
        worker2.setDepartment("Delivery");
        worker2.setWeeklyWorkingHours(30);
        company.addEmployee(worker2);
        
        // Calculate average working hours
        double averageHours = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Expected Output: The average working hours per week is 25 hours
        assertEquals(25.0, averageHours, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentWithNoWorkers() {
        // Test Case 4: Delivery department with no workers
        // Input: There are zero workers in the Delivery department
        // Add employees from other departments to ensure the method correctly ignores them
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setDepartment("Sales");
        company.addEmployee(salesPerson);
        
        Manager manager = new Manager();
        manager.setDepartment("Management");
        company.addEmployee(manager);
        
        // Calculate average working hours
        double averageHours = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Expected Output: The average working hours per week is 0 hours (as there are no workers)
        assertEquals(0.0, averageHours, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() {
        // Test Case 5: Delivery department mixed with other departments
        // Input: There are 5 employees in total across all departments. 
        // Among them, 3 are in the Delivery department with weeklyWorkingHours of 25, 32, and 28 hours respectively
        
        // Add delivery department workers
        Worker worker1 = new Worker();
        worker1.setDepartment("Delivery");
        worker1.setWeeklyWorkingHours(25);
        company.addEmployee(worker1);
        
        Worker worker2 = new Worker();
        worker2.setDepartment("Delivery");
        worker2.setWeeklyWorkingHours(32);
        company.addEmployee(worker2);
        
        Worker worker3 = new Worker();
        worker3.setDepartment("Delivery");
        worker3.setWeeklyWorkingHours(28);
        company.addEmployee(worker3);
        
        // Add employees from other departments (should be ignored in calculation)
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setDepartment("Sales");
        company.addEmployee(salesPerson);
        
        Manager manager = new Manager();
        manager.setDepartment("Management");
        company.addEmployee(manager);
        
        // Calculate average working hours
        double averageHours = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Expected Output: The average working hours per week for workers in the Delivery department is 28.33 (rounded to two decimal places) hours
        assertEquals(28.33, averageHours, 0.01); // Using delta of 0.01 for rounding to two decimal places
    }
}