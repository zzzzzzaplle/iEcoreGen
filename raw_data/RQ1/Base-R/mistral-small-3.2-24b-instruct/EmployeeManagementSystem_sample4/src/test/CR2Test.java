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
        // Input: One worker in the Delivery department with a weeklyWorkingHour of 40 hours
        Worker worker = new Worker("Delivery", "John Doe", LocalDate.of(1990, 1, 1), "123-45-6789", 40.0, 25.0, false);
        company.addEmployee(worker);
        
        // Expected Output: The average working hours per week is 40 hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersInDeliveryDepartmentWithSameHours() {
        // Input: Three workers in the Delivery department, each with a weeklyWorkingHour of 35 hours
        Worker worker1 = new Worker("Delivery", "Worker 1", LocalDate.of(1985, 5, 10), "111-11-1111", 35.0, 20.0, true);
        Worker worker2 = new Worker("Delivery", "Worker 2", LocalDate.of(1988, 8, 15), "222-22-2222", 35.0, 22.0, false);
        Worker worker3 = new Worker("Delivery", "Worker 3", LocalDate.of(1992, 3, 25), "333-33-3333", 35.0, 24.0, true);
        
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        company.addEmployee(worker3);
        
        // Expected Output: The average working hours per week is 35 hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersInDeliveryDepartmentWithDifferentHours() {
        // Input: Two workers in the Delivery department. One has a weeklyWorkingHour of 20 hours and the other has 30 hours
        Worker worker1 = new Worker("Delivery", "Worker A", LocalDate.of(1980, 12, 5), "444-44-4444", 20.0, 18.0, false);
        Worker worker2 = new Worker("Delivery", "Worker B", LocalDate.of(1975, 7, 20), "555-55-5555", 30.0, 26.0, true);
        
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        
        // Expected Output: The average working hours per week is 25 hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentWithNoWorkers() {
        // Input: There are zero workers in the Delivery department
        // Add some employees from other departments
        SalesPerson salesPerson = new SalesPerson("Sales", "Sales Person", LocalDate.of(1982, 4, 12), "666-66-6666", 3000.0, 50000.0, 0.1);
        Manager manager = new Manager("Management", "Manager", LocalDate.of(1978, 9, 30), "777-77-7777", 8000.0);
        Worker nonDeliveryWorker = new Worker("Production", "Production Worker", LocalDate.of(1991, 6, 18), "888-88-8888", 38.0, 21.5, false);
        
        company.addEmployee(salesPerson);
        company.addEmployee(manager);
        company.addEmployee(nonDeliveryWorker);
        
        // Expected Output: The average working hours per week is 0 hours (as there are no workers)
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() {
        // Input: There are 5 employees in total across all departments. Among them, 3 are in the Delivery department with weeklyWorkingHours of 25, 32, and 28 hours respectively
        Worker deliveryWorker1 = new Worker("Delivery", "Delivery Worker 1", LocalDate.of(1987, 2, 14), "999-99-9999", 25.0, 23.0, true);
        Worker deliveryWorker2 = new Worker("Delivery", "Delivery Worker 2", LocalDate.of(1993, 11, 8), "101-10-1010", 32.0, 25.5, false);
        Worker deliveryWorker3 = new Worker("Delivery", "Delivery Worker 3", LocalDate.of(1989, 7, 3), "111-11-1112", 28.0, 27.0, true);
        
        SalesPerson salesPerson = new SalesPerson("Marketing", "Marketing Person", LocalDate.of(1984, 4, 22), "121-12-1212", 3500.0, 75000.0, 0.08);
        Manager manager = new Manager("HR", "HR Manager", LocalDate.of(1976, 12, 10), "131-13-1313", 7500.0);
        
        company.addEmployee(deliveryWorker1);
        company.addEmployee(deliveryWorker2);
        company.addEmployee(deliveryWorker3);
        company.addEmployee(salesPerson);
        company.addEmployee(manager);
        
        // Expected Output: The average working hours per week for workers in the Delivery department is 28.33 (rounded to two decimal places) hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        assertEquals(28.33, result, 0.01); // Using delta of 0.01 for rounding to two decimal places
    }
}