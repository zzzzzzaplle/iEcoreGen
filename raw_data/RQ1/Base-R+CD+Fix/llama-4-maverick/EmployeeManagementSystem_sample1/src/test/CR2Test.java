import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR2Test {
    private Company company;
    private Department deliveryDepartment;
    private Department otherDepartment;
    
    @Before
    public void setUp() {
        company = new Company();
        deliveryDepartment = new Department();
        deliveryDepartment.setType(DepartmentType.DELIVERY);
        
        otherDepartment = new Department();
        otherDepartment.setType(DepartmentType.PRODUCTION);
        
        company.setDepartments(new ArrayList<Department>());
        company.getDepartments().add(deliveryDepartment);
        company.getDepartments().add(otherDepartment);
    }
    
    @Test
    public void testCase1_singleWorkerInDeliveryDepartment() {
        // Setup: One worker in the Delivery department with a weeklyWorkingHour of 40 hours
        deliveryDepartment.setEmployees(new ArrayList<Employee>());
        
        ShiftWorker worker = new ShiftWorker();
        worker.setWeeklyWorkingHour(40);
        deliveryDepartment.getEmployees().add(worker);
        
        // Execute: Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: The average working hours per week is 40 hours
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersSameHours() {
        // Setup: Three workers in the Delivery department, each with a weeklyWorkingHour of 35 hours
        deliveryDepartment.setEmployees(new ArrayList<Employee>());
        
        for (int i = 0; i < 3; i++) {
            OffShiftWorker worker = new OffShiftWorker();
            worker.setWeeklyWorkingHour(35);
            deliveryDepartment.getEmployees().add(worker);
        }
        
        // Execute: Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: The average working hours per week is 35 hours
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersDifferentHours() {
        // Setup: Two workers in the Delivery department. One has 20 hours and the other has 30 hours
        deliveryDepartment.setEmployees(new ArrayList<Employee>());
        
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setWeeklyWorkingHour(20);
        deliveryDepartment.getEmployees().add(worker1);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(30);
        deliveryDepartment.getEmployees().add(worker2);
        
        // Execute: Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: The average working hours per week is 25 hours
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_noWorkersInDeliveryDepartment() {
        // Setup: Zero workers in the Delivery department
        deliveryDepartment.setEmployees(new ArrayList<Employee>());
        
        // Add non-worker employees to ensure they don't affect the calculation
        SalesPeople salesPerson = new SalesPeople();
        Manager manager = new Manager();
        deliveryDepartment.getEmployees().add(salesPerson);
        deliveryDepartment.getEmployees().add(manager);
        
        // Execute: Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: The average working hours per week is 0 hours
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_mixedDepartmentsWithDeliveryWorkers() {
        // Setup: 5 employees across all departments, 3 workers in Delivery with hours 25, 32, and 28
        deliveryDepartment.setEmployees(new ArrayList<Employee>());
        otherDepartment.setEmployees(new ArrayList<Employee>());
        
        // Add workers to delivery department
        ShiftWorker worker1 = new ShiftWorker();
        worker1.setWeeklyWorkingHour(25);
        deliveryDepartment.getEmployees().add(worker1);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(32);
        deliveryDepartment.getEmployees().add(worker2);
        
        ShiftWorker worker3 = new ShiftWorker();
        worker3.setWeeklyWorkingHour(28);
        deliveryDepartment.getEmployees().add(worker3);
        
        // Add other employees to other departments (should not affect delivery department calculation)
        SalesPeople salesPerson = new SalesPeople();
        otherDepartment.getEmployees().add(salesPerson);
        
        Manager manager = new Manager();
        otherDepartment.getEmployees().add(manager);
        
        // Execute: Calculate average working hours for delivery department
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: The average working hours per week is 28.33 (rounded to two decimal places)
        assertEquals(28.33, result, 0.01);
    }
}