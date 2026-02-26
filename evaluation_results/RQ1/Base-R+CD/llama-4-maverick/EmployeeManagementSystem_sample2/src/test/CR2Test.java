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
        List<Department> departments = new ArrayList<>();
        departments.add(deliveryDepartment);
        company.setDepartments(departments);
    }
    
    @Test
    public void testCase1_singleWorkerInDeliveryDepartment() {
        // Setup: One worker in the Delivery department with a weeklyWorkingHour of 40 hours
        List<Employee> employees = new ArrayList<>();
        OffShiftWorker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        employees.add(worker);
        deliveryDepartment.setEmployees(employees);
        
        // Execute: Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: The average working hours per week is 40 hours
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersSameHours() {
        // Setup: Three workers in the Delivery department, each with a weeklyWorkingHour of 35 hours
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            OffShiftWorker worker = new OffShiftWorker();
            worker.setWeeklyWorkingHour(35);
            employees.add(worker);
        }
        deliveryDepartment.setEmployees(employees);
        
        // Execute: Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: The average working hours per week is 35 hours
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersDifferentHours() {
        // Setup: Two workers in the Delivery department with weeklyWorkingHours of 20 and 30 hours
        List<Employee> employees = new ArrayList<>();
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(20);
        employees.add(worker1);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(30);
        employees.add(worker2);
        
        deliveryDepartment.setEmployees(employees);
        
        // Execute: Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: The average working hours per week is 25 hours
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentWithNoWorkers() {
        // Setup: Zero workers in the Delivery department (empty employees list)
        List<Employee> employees = new ArrayList<>();
        deliveryDepartment.setEmployees(employees);
        
        // Execute: Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: The average working hours per week is 0 hours
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() {
        // Setup: 3 workers in Delivery department with weeklyWorkingHours of 25, 32, and 28 hours
        List<Employee> employees = new ArrayList<>();
        
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(25);
        employees.add(worker1);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(32);
        employees.add(worker2);
        
        OffShiftWorker worker3 = new OffShiftWorker();
        worker3.setWeeklyWorkingHour(28);
        employees.add(worker3);
        
        deliveryDepartment.setEmployees(employees);
        
        // Execute: Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: The average working hours per week is 28.33 (rounded to two decimal places)
        assertEquals(28.33, result, 0.01);
    }
}