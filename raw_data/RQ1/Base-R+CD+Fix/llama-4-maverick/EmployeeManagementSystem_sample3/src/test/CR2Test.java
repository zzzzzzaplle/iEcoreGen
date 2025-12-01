import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR2Test {
    private Company company;
    private Department deliveryDepartment;
    
    @Before
    public void setUp() {
        company = new Company();
        deliveryDepartment = new Department();
        deliveryDepartment.setType(DepartmentType.DELIVERY);
        company.setDepartments(new ArrayList<>());
        company.getDepartments().add(deliveryDepartment);
    }
    
    @Test
    public void testCase1_singleWorkerInDeliveryDepartment() {
        // Setup: One worker in Delivery department with 40 hours
        Worker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        deliveryDepartment.setEmployees(new ArrayList<>());
        deliveryDepartment.getEmployees().add(worker);
        
        // Execute
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersSameHours() {
        // Setup: Three workers in Delivery department with 35 hours each
        List<Employee> workers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Worker worker = new OffShiftWorker();
            worker.setWeeklyWorkingHour(35);
            workers.add(worker);
        }
        deliveryDepartment.setEmployees(workers);
        
        // Execute
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersDifferentHours() {
        // Setup: Two workers in Delivery department with 20 and 30 hours
        Worker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(20);
        Worker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(30);
        
        List<Employee> workers = new ArrayList<>();
        workers.add(worker1);
        workers.add(worker2);
        deliveryDepartment.setEmployees(workers);
        
        // Execute
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentNoWorkers() {
        // Setup: Empty delivery department (no workers)
        deliveryDepartment.setEmployees(new ArrayList<>());
        
        // Execute
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOthers() {
        // Setup: 3 workers in Delivery department with 25, 32, and 28 hours
        // Also add some non-worker employees to test filtering
        Worker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(25);
        Worker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(32);
        Worker worker3 = new OffShiftWorker();
        worker3.setWeeklyWorkingHour(28);
        
        // Add non-worker employees to ensure they are filtered out
        Manager manager = new Manager();
        SalesPeople salesPerson = new SalesPeople();
        
        List<Employee> employees = new ArrayList<>();
        employees.add(worker1);
        employees.add(worker2);
        employees.add(worker3);
        employees.add(manager);
        employees.add(salesPerson);
        
        deliveryDepartment.setEmployees(employees);
        
        // Execute
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: (25 + 32 + 28) / 3 = 28.33
        assertEquals(28.33, result, 0.01);
    }
}