import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Company company;
    private Department deliveryDepartment;
    private Department otherDepartment;
    
    @Before
    public void setUp() {
        // Initialize company and departments before each test
        company = new Company();
        deliveryDepartment = new Department();
        deliveryDepartment.setName("Delivery");
        otherDepartment = new Department();
        otherDepartment.setName("Sales");
    }
    
    @Test
    public void testCase1_singleWorkerInDeliveryDepartment() {
        // Test Case 1: Single worker in delivery department with 40 hours
        Worker worker = new Worker();
        worker.setName("John Doe");
        worker.setWeeklyWorkingHours(40.0);
        
        deliveryDepartment.addEmployee(worker);
        company.addDepartment(deliveryDepartment);
        
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersSameHours() {
        // Test Case 2: Three workers in delivery department, each with 35 hours
        Worker worker1 = new Worker();
        worker1.setName("Worker 1");
        worker1.setWeeklyWorkingHours(35.0);
        
        Worker worker2 = new Worker();
        worker2.setName("Worker 2");
        worker2.setWeeklyWorkingHours(35.0);
        
        Worker worker3 = new Worker();
        worker3.setName("Worker 3");
        worker3.setWeeklyWorkingHours(35.0);
        
        deliveryDepartment.addEmployee(worker1);
        deliveryDepartment.addEmployee(worker2);
        deliveryDepartment.addEmployee(worker3);
        company.addDepartment(deliveryDepartment);
        
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersDifferentHours() {
        // Test Case 3: Two workers in delivery department with 20 and 30 hours
        Worker worker1 = new Worker();
        worker1.setName("Worker 1");
        worker1.setWeeklyWorkingHours(20.0);
        
        Worker worker2 = new Worker();
        worker2.setName("Worker 2");
        worker2.setWeeklyWorkingHours(30.0);
        
        deliveryDepartment.addEmployee(worker1);
        deliveryDepartment.addEmployee(worker2);
        company.addDepartment(deliveryDepartment);
        
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentNoWorkers() {
        // Test Case 4: Delivery department with no workers
        deliveryDepartment.setEmployees(new ArrayList<>());
        company.addDepartment(deliveryDepartment);
        
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_mixedDepartmentsWithDeliveryDepartmentWorkers() {
        // Test Case 5: Mixed departments with 3 workers in delivery department (25, 32, 28 hours)
        Worker deliveryWorker1 = new Worker();
        deliveryWorker1.setName("Delivery Worker 1");
        deliveryWorker1.setWeeklyWorkingHours(25.0);
        
        Worker deliveryWorker2 = new Worker();
        deliveryWorker2.setName("Delivery Worker 2");
        deliveryWorker2.setWeeklyWorkingHours(32.0);
        
        Worker deliveryWorker3 = new Worker();
        deliveryWorker3.setName("Delivery Worker 3");
        deliveryWorker3.setWeeklyWorkingHours(28.0);
        
        // Add non-worker employees to other department
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setName("Sales Person");
        salesPerson.setFixedSalary(50000.0);
        
        Manager manager = new Manager();
        manager.setName("Manager");
        manager.setFixedSalary(80000.0);
        
        deliveryDepartment.addEmployee(deliveryWorker1);
        deliveryDepartment.addEmployee(deliveryWorker2);
        deliveryDepartment.addEmployee(deliveryWorker3);
        otherDepartment.addEmployee(salesPerson);
        otherDepartment.addEmployee(manager);
        
        company.addDepartment(deliveryDepartment);
        company.addDepartment(otherDepartment);
        
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        assertEquals(28.33, result, 0.01); // Allow small delta for floating point precision
    }
}