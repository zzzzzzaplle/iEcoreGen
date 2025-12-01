import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Department deliveryDepartment;
    private Department productionDepartment;
    private List<Employee> allEmployees;
    private Company company;
    
    @Before
    public void setUp() {
        // Create departments
        deliveryDepartment = new Department();
        deliveryDepartment.setType(DepartmentType.DELIVERY);
        
        productionDepartment = new Department();
        productionDepartment.setType(DepartmentType.PRODUCTION);
        
        // Create company and set up departments
        company = new Company();
        List<Department> departments = new ArrayList<>();
        departments.add(deliveryDepartment);
        departments.add(productionDepartment);
        company.setDepartments(departments);
    }
    
    @Test
    public void testCase1_singleWorkerInDeliveryDepartment() {
        // Test Case 1: Single worker in delivery department
        // Input: One worker in the Delivery department with a weeklyWorkingHour of 40 hours
        Worker worker = new ShiftWorker();
        worker.setWeeklyWorkingHour(40);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(worker);
        deliveryDepartment.setEmployees(employees);
        
        // Expected Output: The average working hours per week is 40 hours
        double expected = 40.0;
        double actual = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        assertEquals(expected, actual, 0.01);
    }
    
    @Test
    public void testCase2_multipleWorkersInDeliveryDepartmentWithSameHours() {
        // Test Case 2: Multiple workers in delivery department with same hours
        // Input: Three workers in the Delivery department, each with a weeklyWorkingHour of 35 hours
        Worker worker1 = new ShiftWorker();
        worker1.setWeeklyWorkingHour(35);
        
        Worker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(35);
        
        Worker worker3 = new ShiftWorker();
        worker3.setWeeklyWorkingHour(35);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(worker1);
        employees.add(worker2);
        employees.add(worker3);
        deliveryDepartment.setEmployees(employees);
        
        // Expected Output: The average working hours per week is 35 hours
        double expected = 35.0;
        double actual = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        assertEquals(expected, actual, 0.01);
    }
    
    @Test
    public void testCase3_multipleWorkersInDeliveryDepartmentWithDifferentHours() {
        // Test Case 3: Multiple workers in delivery department with different hours
        // Input: Two workers in the Delivery department. One has a weeklyWorkingHour of 20 hours and the other has 30 hours
        Worker worker1 = new ShiftWorker();
        worker1.setWeeklyWorkingHour(20);
        
        Worker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(30);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(worker1);
        employees.add(worker2);
        deliveryDepartment.setEmployees(employees);
        
        // Expected Output: The average working hours per week is 25 hours
        double expected = 25.0;
        double actual = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        assertEquals(expected, actual, 0.01);
    }
    
    @Test
    public void testCase4_deliveryDepartmentWithNoWorkers() {
        // Test Case 4: Delivery department with no workers
        // Input: There are zero workers in the Delivery department
        List<Employee> employees = new ArrayList<>();
        deliveryDepartment.setEmployees(employees);
        
        // Expected Output: The average working hours per week is 0 hours (as there are no workers)
        double expected = 0.0;
        double actual = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        assertEquals(expected, actual, 0.01);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() {
        // Test Case 5: Delivery department mixed with other departments
        // Input: There are 5 employees in total across all departments. Among them, 3 are in the Delivery department with weeklyWorkingHours of 25, 32, and 28 hours respectively
        Worker worker1 = new ShiftWorker();
        worker1.setWeeklyWorkingHour(25);
        
        Worker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(32);
        
        Worker worker3 = new ShiftWorker();
        worker3.setWeeklyWorkingHour(28);
        
        List<Employee> deliveryEmployees = new ArrayList<>();
        deliveryEmployees.add(worker1);
        deliveryEmployees.add(worker2);
        deliveryEmployees.add(worker3);
        deliveryDepartment.setEmployees(deliveryEmployees);
        
        // Add some employees to production department to make total 5
        Manager manager = new Manager();
        manager.setWeeklyWorkingHour(40); // This shouldn't affect calculation as it's not a worker
        
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setWeeklyWorkingHour(30); // This shouldn't affect calculation as it's not a worker
        
        List<Employee> productionEmployees = new ArrayList<>();
        productionEmployees.add(manager);
        productionEmployees.add(salesPerson);
        productionDepartment.setEmployees(productionEmployees);
        
        // Expected Output: The average working hours per week for workers in the Delivery department is 28.33 hours
        double expected = 28.33;
        double actual = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        assertEquals(expected, actual, 0.01);
    }
}