import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR2Test {
    
    private Company company;
    private Department deliveryDepartment;
    private Department productionDepartment;
    private Department controlDepartment;
    
    @Before
    public void setUp() {
        // Initialize company and departments before each test
        company = new Company();
        deliveryDepartment = new Department();
        productionDepartment = new Department();
        controlDepartment = new Department();
        
        deliveryDepartment.setType(DepartmentType.DELIVERY);
        productionDepartment.setType(DepartmentType.PRODUCTION);
        controlDepartment.setType(DepartmentType.CONTROL);
        
        List<Department> departments = new ArrayList<>();
        departments.add(deliveryDepartment);
        departments.add(productionDepartment);
        departments.add(controlDepartment);
        company.setDepartments(departments);
    }
    
    @Test
    public void testCase1_singleWorkerInDeliveryDepartment() {
        // Test Case 1: Single worker in delivery department with 40 hours
        List<Employee> employees = new ArrayList<>();
        
        // Create a worker for delivery department
        Worker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        worker.setDepartment("Delivery");
        employees.add(worker);
        
        deliveryDepartment.setEmployees(employees);
        
        // Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify the result matches expected output
        assertEquals(40.0, result, 0.01);
    }
    
    @Test
    public void testCase2_multipleWorkersSameHours() {
        // Test Case 2: Three workers in delivery department, each with 35 hours
        List<Employee> employees = new ArrayList<>();
        
        // Create three workers with same working hours
        Worker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(35);
        worker1.setDepartment("Delivery");
        employees.add(worker1);
        
        Worker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(35);
        worker2.setDepartment("Delivery");
        employees.add(worker2);
        
        Worker worker3 = new OffShiftWorker();
        worker3.setWeeklyWorkingHour(35);
        worker3.setDepartment("Delivery");
        employees.add(worker3);
        
        deliveryDepartment.setEmployees(employees);
        
        // Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify the result matches expected output
        assertEquals(35.0, result, 0.01);
    }
    
    @Test
    public void testCase3_multipleWorkersDifferentHours() {
        // Test Case 3: Two workers in delivery department with different hours (20 and 30)
        List<Employee> employees = new ArrayList<>();
        
        // Create two workers with different working hours
        Worker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(20);
        worker1.setDepartment("Delivery");
        employees.add(worker1);
        
        Worker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(30);
        worker2.setDepartment("Delivery");
        employees.add(worker2);
        
        deliveryDepartment.setEmployees(employees);
        
        // Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify the result matches expected output (average of 20 and 30 is 25)
        assertEquals(25.0, result, 0.01);
    }
    
    @Test
    public void testCase4_deliveryDepartmentNoWorkers() {
        // Test Case 4: Delivery department with no workers
        List<Employee> employees = new ArrayList<>();
        deliveryDepartment.setEmployees(employees);
        
        // Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify the result is 0 when there are no workers
        assertEquals(0.0, result, 0.01);
    }
    
    @Test
    public void testCase5_mixedDepartmentsWithDeliveryWorkers() {
        // Test Case 5: Mixed departments with delivery department workers (25, 32, 28 hours)
        List<Employee> deliveryEmployees = new ArrayList<>();
        
        // Create three workers for delivery department
        Worker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(25);
        worker1.setDepartment("Delivery");
        deliveryEmployees.add(worker1);
        
        Worker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(32);
        worker2.setDepartment("Delivery");
        deliveryEmployees.add(worker2);
        
        Worker worker3 = new OffShiftWorker();
        worker3.setWeeklyWorkingHour(28);
        worker3.setDepartment("Delivery");
        deliveryEmployees.add(worker3);
        
        deliveryDepartment.setEmployees(deliveryEmployees);
        
        // Add some non-worker employees and workers from other departments
        List<Employee> productionEmployees = new ArrayList<>();
        Manager manager = new Manager();
        manager.setDepartment("Production");
        productionEmployees.add(manager);
        
        Worker productionWorker = new OffShiftWorker();
        productionWorker.setWeeklyWorkingHour(40);
        productionWorker.setDepartment("Production");
        productionEmployees.add(productionWorker);
        
        productionDepartment.setEmployees(productionEmployees);
        
        // Calculate average working hours for delivery department only
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify the result matches expected output (average of 25, 32, 28 is 28.33)
        assertEquals(28.33, result, 0.01);
    }
}