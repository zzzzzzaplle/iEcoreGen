import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
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
        // Setup: Create delivery department with one worker
        Department deliveryDept = new Department();
        deliveryDept.setType(DepartmentType.DELIVERY);
        
        OffShiftWorker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        worker.setDepartment("DELIVERY");
        
        List<Employee> employees = new ArrayList<>();
        employees.add(worker);
        deliveryDept.setEmployees(employees);
        
        List<Department> departments = new ArrayList<>();
        departments.add(deliveryDept);
        company.setDepartments(departments);
        
        // Execute: Calculate average working hours
        double result = company.calculateAverageDeliveryDepartmentWorkingHours();
        
        // Verify: Average should be 40 hours
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersSameHours() {
        // Setup: Create delivery department with three workers, all 35 hours
        Department deliveryDept = new Department();
        deliveryDept.setType(DepartmentType.DELIVERY);
        
        List<Employee> employees = new ArrayList<>();
        
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(35);
        worker1.setDepartment("DELIVERY");
        employees.add(worker1);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(35);
        worker2.setDepartment("DELIVERY");
        employees.add(worker2);
        
        OffShiftWorker worker3 = new OffShiftWorker();
        worker3.setWeeklyWorkingHour(35);
        worker3.setDepartment("DELIVERY");
        employees.add(worker3);
        
        deliveryDept.setEmployees(employees);
        
        List<Department> departments = new ArrayList<>();
        departments.add(deliveryDept);
        company.setDepartments(departments);
        
        // Execute: Calculate average working hours
        double result = company.calculateAverageDeliveryDepartmentWorkingHours();
        
        // Verify: Average should be 35 hours
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersDifferentHours() {
        // Setup: Create delivery department with two workers (20 and 30 hours)
        Department deliveryDept = new Department();
        deliveryDept.setType(DepartmentType.DELIVERY);
        
        List<Employee> employees = new ArrayList<>();
        
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(20);
        worker1.setDepartment("DELIVERY");
        employees.add(worker1);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(30);
        worker2.setDepartment("DELIVERY");
        employees.add(worker2);
        
        deliveryDept.setEmployees(employees);
        
        List<Department> departments = new ArrayList<>();
        departments.add(deliveryDept);
        company.setDepartments(departments);
        
        // Execute: Calculate average working hours
        double result = company.calculateAverageDeliveryDepartmentWorkingHours();
        
        // Verify: Average should be 25 hours ((20 + 30) / 2)
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentWithNoWorkers() {
        // Setup: Create empty delivery department
        Department deliveryDept = new Department();
        deliveryDept.setType(DepartmentType.DELIVERY);
        deliveryDept.setEmployees(new ArrayList<>());
        
        List<Department> departments = new ArrayList<>();
        departments.add(deliveryDept);
        company.setDepartments(departments);
        
        // Execute: Calculate average working hours
        double result = company.calculateAverageDeliveryDepartmentWorkingHours();
        
        // Verify: Average should be 0 hours (no workers)
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() {
        // Setup: Create multiple departments with mixed employees
        Department deliveryDept = new Department();
        deliveryDept.setType(DepartmentType.DELIVERY);
        
        List<Employee> deliveryEmployees = new ArrayList<>();
        
        // Delivery department workers (25, 32, 28 hours)
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(25);
        worker1.setDepartment("DELIVERY");
        deliveryEmployees.add(worker1);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(32);
        worker2.setDepartment("DELIVERY");
        deliveryEmployees.add(worker2);
        
        OffShiftWorker worker3 = new OffShiftWorker();
        worker3.setWeeklyWorkingHour(28);
        worker3.setDepartment("DELIVERY");
        deliveryEmployees.add(worker3);
        
        deliveryDept.setEmployees(deliveryEmployees);
        
        // Create other departments
        Department productionDept = new Department();
        productionDept.setType(DepartmentType.PRODUCTION);
        
        List<Employee> productionEmployees = new ArrayList<>();
        
        OffShiftWorker prodWorker1 = new OffShiftWorker();
        prodWorker1.setWeeklyWorkingHour(40);
        prodWorker1.setDepartment("PRODUCTION");
        productionEmployees.add(prodWorker1);
        
        Manager manager = new Manager();
        manager.setDepartment("PRODUCTION");
        productionEmployees.add(manager);
        
        productionDept.setEmployees(productionEmployees);
        
        // Add all departments to company
        List<Department> departments = new ArrayList<>();
        departments.add(deliveryDept);
        departments.add(productionDept);
        company.setDepartments(departments);
        
        // Execute: Calculate average working hours for delivery department only
        double result = company.calculateAverageDeliveryDepartmentWorkingHours();
        
        // Verify: Average should be 28.33 hours ((25 + 32 + 28) / 3)
        assertEquals(28.33, result, 0.01);
    }
}