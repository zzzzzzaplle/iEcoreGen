import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR2Test {
    
    private Department deliveryDepartment;
    private Department productionDepartment;
    private Department controlDepartment;
    
    @Before
    public void setUp() {
        deliveryDepartment = new Department();
        deliveryDepartment.setType(DepartmentType.DELIVERY);
        
        productionDepartment = new Department();
        productionDepartment.setType(DepartmentType.PRODUCTION);
        
        controlDepartment = new Department();
        controlDepartment.setType(DepartmentType.CONTROL);
    }
    
    @Test
    public void testCase1_singleWorkerInDeliveryDepartment() {
        // Create one worker with 40 hours
        ShiftWorker worker = new ShiftWorker();
        worker.setWeeklyWorkingHour(40);
        
        // Add worker to delivery department
        List<Employee> employees = new ArrayList<>();
        employees.add(worker);
        deliveryDepartment.setEmployees(employees);
        
        // Calculate average working hours
        double average = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Expected: 40 hours
        assertEquals(40.0, average, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersInDeliveryDepartmentWithSameHours() {
        // Create three workers with 35 hours each
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setWeeklyWorkingHour(35);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(35);
        
        ShiftWorker worker3 = new ShiftWorker();
        worker3.setWeeklyWorkingHour(35);
        
        // Add workers to delivery department
        List<Employee> employees = new ArrayList<>();
        employees.add(worker1);
        employees.add(worker2);
        employees.add(worker3);
        deliveryDepartment.setEmployees(employees);
        
        // Calculate average working hours
        double average = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Expected: 35 hours
        assertEquals(35.0, average, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersInDeliveryDepartmentWithDifferentHours() {
        // Create two workers with 20 and 30 hours
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setWeeklyWorkingHour(20);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(30);
        
        // Add workers to delivery department
        List<Employee> employees = new ArrayList<>();
        employees.add(worker1);
        employees.add(worker2);
        deliveryDepartment.setEmployees(employees);
        
        // Calculate average working hours
        double average = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Expected: 25 hours ( (20 + 30) / 2 )
        assertEquals(25.0, average, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentWithNoWorkers() {
        // Create empty employee list
        List<Employee> employees = new ArrayList<>();
        deliveryDepartment.setEmployees(employees);
        
        // Calculate average working hours
        double average = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Expected: 0 hours (no workers)
        assertEquals(0.0, average, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() {
        // Create 3 workers for delivery department with 25, 32, and 28 hours
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setWeeklyWorkingHour(25);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(32);
        
        ShiftWorker worker3 = new ShiftWorker();
        worker3.setWeeklyWorkingHour(28);
        
        // Add workers to delivery department
        List<Employee> deliveryEmployees = new ArrayList<>();
        deliveryEmployees.add(worker1);
        deliveryEmployees.add(worker2);
        deliveryEmployees.add(worker3);
        deliveryDepartment.setEmployees(deliveryEmployees);
        
        // Calculate average working hours
        double average = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Expected: 28.33 hours ( (25 + 32 + 28) / 3 = 85 / 3 = 28.333... )
        assertEquals(28.33, average, 0.01);
    }
}