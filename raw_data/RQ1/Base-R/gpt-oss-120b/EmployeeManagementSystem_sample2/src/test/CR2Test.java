import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR2Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Reset company before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_singleWorkerInDeliveryDepartment() {
        // Test Case 1: Single worker in delivery department
        // Input: One worker in the Delivery department with a weeklyWorkingHour of 40 hours
        ShiftWorker worker = new ShiftWorker();
        worker.setDepartment(Department.DELIVERY);
        worker.setWeeklyWorkingHours(40.0);
        company.addEmployee(worker);
        
        // Expected Output: The average working hours per week is 40 hours
        double result = company.averageWeeklyHoursInDelivery();
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersInDeliveryDepartmentSameHours() {
        // Test Case 2: Multiple workers in delivery department with same hours
        // Input: Three workers in the Delivery department, each with a weeklyWorkingHour of 35 hours
        for (int i = 0; i < 3; i++) {
            ShiftWorker worker = new ShiftWorker();
            worker.setDepartment(Department.DELIVERY);
            worker.setWeeklyWorkingHours(35.0);
            company.addEmployee(worker);
        }
        
        // Expected Output: The average working hours per week is 35 hours
        double result = company.averageWeeklyHoursInDelivery();
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersInDeliveryDepartmentDifferentHours() {
        // Test Case 3: Multiple workers in delivery department with different hours
        // Input: Two workers in the Delivery department. One has a weeklyWorkingHour of 20 hours and the other has 30 hours
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setDepartment(Department.DELIVERY);
        worker1.setWeeklyWorkingHours(20.0);
        company.addEmployee(worker1);
        
        ShiftWorker worker2 = new ShiftWorker();
        worker2.setDepartment(Department.DELIVERY);
        worker2.setWeeklyWorkingHours(30.0);
        company.addEmployee(worker2);
        
        // Expected Output: The average working hours per week is 25 hours
        double result = company.averageWeeklyHoursInDelivery();
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentWithNoWorkers() {
        // Test Case 4: Delivery department with no workers
        // Input: There are zero workers in the Delivery department
        // Add employees from other departments to ensure they are ignored
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setDepartment(Department.PRODUCTION);
        company.addEmployee(salesPerson);
        
        Manager manager = new Manager();
        manager.setDepartment(Department.CONTROL);
        company.addEmployee(manager);
        
        // Expected Output: The average working hours per week is 0 hours (as there are no workers)
        double result = company.averageWeeklyHoursInDelivery();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() {
        // Test Case 5: Delivery department mixed with other departments
        // Input: There are 5 employees in total across all departments. 
        // Among them, 3 are in the Delivery department with weeklyWorkingHours of 25, 32, and 28 hours respectively
        
        // Add 3 workers to Delivery department
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setDepartment(Department.DELIVERY);
        worker1.setWeeklyWorkingHours(25.0);
        company.addEmployee(worker1);
        
        ShiftWorker worker2 = new ShiftWorker();
        worker2.setDepartment(Department.DELIVERY);
        worker2.setWeeklyWorkingHours(32.0);
        company.addEmployee(worker2);
        
        ShiftWorker worker3 = new ShiftWorker();
        worker3.setDepartment(Department.DELIVERY);
        worker3.setWeeklyWorkingHours(28.0);
        company.addEmployee(worker3);
        
        // Add 2 employees from other departments (should be ignored in calculation)
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setDepartment(Department.PRODUCTION);
        company.addEmployee(salesPerson);
        
        Manager manager = new Manager();
        manager.setDepartment(Department.CONTROL);
        company.addEmployee(manager);
        
        // Expected Output: The average working hours per week for workers in the Delivery department is 28.33 (rounded to two decimal places) hours
        double result = company.averageWeeklyHoursInDelivery();
        assertEquals(28.33, result, 0.01); // Using delta of 0.01 for rounding to two decimal places
    }
}