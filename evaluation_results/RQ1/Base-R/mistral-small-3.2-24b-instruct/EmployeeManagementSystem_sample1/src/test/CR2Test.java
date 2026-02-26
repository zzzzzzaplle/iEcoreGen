import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
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
        
        // Create a worker in delivery department with 40 weekly working hours
        Worker worker = new Worker("Delivery", "John Doe", 
                                  LocalDate.of(1990, 1, 1), "123-45-6789",
                                  40, 25.0, false);
        company.addEmployee(worker);
        
        // Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify the result matches expected output
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersSameHours() {
        // Test Case 2: Multiple workers in delivery department with same hours
        // Input: Three workers in the Delivery department, each with a weeklyWorkingHour of 35 hours
        // Expected Output: The average working hours per week is 35 hours
        
        // Create three workers in delivery department with same weekly working hours
        Worker worker1 = new Worker("Delivery", "Worker One", 
                                   LocalDate.of(1985, 5, 15), "111-11-1111",
                                   35, 20.0, true);
        Worker worker2 = new Worker("Delivery", "Worker Two", 
                                   LocalDate.of(1988, 8, 22), "222-22-2222",
                                   35, 22.0, false);
        Worker worker3 = new Worker("Delivery", "Worker Three", 
                                   LocalDate.of(1992, 3, 10), "333-33-3333",
                                   35, 24.0, true);
        
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        company.addEmployee(worker3);
        
        // Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify the result matches expected output
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersDifferentHours() {
        // Test Case 3: Multiple workers in delivery department with different hours
        // Input: Two workers in the Delivery department. One has a weeklyWorkingHour of 20 hours and the other has 30 hours
        // Expected Output: The average working hours per week is 25 hours
        
        // Create two workers in delivery department with different weekly working hours
        Worker worker1 = new Worker("Delivery", "Worker A", 
                                   LocalDate.of(1990, 1, 1), "444-44-4444",
                                   20, 18.0, false);
        Worker worker2 = new Worker("Delivery", "Worker B", 
                                   LocalDate.of(1991, 2, 2), "555-55-5555",
                                   30, 19.0, true);
        
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        
        // Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify the result matches expected output (average of 20 and 30 is 25)
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_noWorkersInDeliveryDepartment() {
        // Test Case 4: Delivery department with no workers
        // Input: There are zero workers in the Delivery department
        // Expected Output: The average working hours per week is 0 hours (as there are no workers)
        
        // Add employees from other departments but no delivery workers
        Salesperson salesperson = new Salesperson("Sales", "Sales Person",
                                                 LocalDate.of(1985, 5, 15), "666-66-6666",
                                                 3000.0, 50000.0, 0.1);
        Manager manager = new Manager("Management", "Manager One",
                                    LocalDate.of(1975, 10, 20), "777-77-7777",
                                    "Senior Manager");
        
        company.addEmployee(salesperson);
        company.addEmployee(manager);
        
        // Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify the result is 0 when there are no delivery workers
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_mixedDepartments() {
        // Test Case 5: Delivery department mixed with other departments
        // Input: There are 5 employees in total across all departments. Among them, 3 are in the Delivery department with weeklyWorkingHours of 25, 32, and 28 hours respectively
        // Expected Output: The average working hours per week for workers in the Delivery department is 28.33 (rounded to two decimal places) hours
        
        // Create 3 delivery workers with specified hours
        Worker deliveryWorker1 = new Worker("Delivery", "Delivery Worker 1",
                                           LocalDate.of(1990, 1, 1), "888-88-8888",
                                           25, 20.0, true);
        Worker deliveryWorker2 = new Worker("Delivery", "Delivery Worker 2",
                                           LocalDate.of(1991, 2, 2), "999-99-9999",
                                           32, 21.0, false);
        Worker deliveryWorker3 = new Worker("Delivery", "Delivery Worker 3",
                                           LocalDate.of(1992, 3, 3), "000-00-0000",
                                           28, 22.0, true);
        
        // Create 2 employees from other departments
        Salesperson salesperson = new Salesperson("Sales", "Sales Employee",
                                                 LocalDate.of(1985, 5, 15), "111-11-1111",
                                                 2500.0, 40000.0, 0.08);
        Manager manager = new Manager("Management", "Department Manager",
                                    LocalDate.of(1978, 8, 18), "222-22-2222",
                                    "Team Lead");
        
        company.addEmployee(deliveryWorker1);
        company.addEmployee(deliveryWorker2);
        company.addEmployee(deliveryWorker3);
        company.addEmployee(salesperson);
        company.addEmployee(manager);
        
        // Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify the result matches expected output (average of 25, 32, 28 is 28.333...)
        assertEquals(28.33, result, 0.01); // Using delta of 0.01 for two decimal places precision
    }
}