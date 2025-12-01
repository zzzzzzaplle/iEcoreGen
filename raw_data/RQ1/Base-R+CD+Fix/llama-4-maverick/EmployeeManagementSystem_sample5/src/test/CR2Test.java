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
    }
    
    @Test
    public void testCase1_singleWorkerInDeliveryDepartment() {
        // Setup: Create one worker in the Delivery department with weeklyWorkingHour of 40 hours
        OffShiftWorker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        
        List<Employee> deliveryEmployees = new ArrayList<>();
        deliveryEmployees.add(worker);
        deliveryDepartment.setEmployees(deliveryEmployees);
        
        List<Department> departments = new ArrayList<>();
        departments.add(deliveryDepartment);
        company.setDepartments(departments);
        
        // Execute: Calculate average working hours for delivery department
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: The average working hours per week is 40 hours
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersInDeliveryDepartmentWithSameHours() {
        // Setup: Create three workers in the Delivery department, each with weeklyWorkingHour of 35 hours
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(35);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(35);
        
        OffShiftWorker worker3 = new OffShiftWorker();
        worker3.setWeeklyWorkingHour(35);
        
        List<Employee> deliveryEmployees = new ArrayList<>();
        deliveryEmployees.add(worker1);
        deliveryEmployees.add(worker2);
        deliveryEmployees.add(worker3);
        deliveryDepartment.setEmployees(deliveryEmployees);
        
        List<Department> departments = new ArrayList<>();
        departments.add(deliveryDepartment);
        company.setDepartments(departments);
        
        // Execute: Calculate average working hours for delivery department
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: The average working hours per week is 35 hours
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersInDeliveryDepartmentWithDifferentHours() {
        // Setup: Create two workers in the Delivery department with different hours
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(20);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(30);
        
        List<Employee> deliveryEmployees = new ArrayList<>();
        deliveryEmployees.add(worker1);
        deliveryEmployees.add(worker2);
        deliveryDepartment.setEmployees(deliveryEmployees);
        
        List<Department> departments = new ArrayList<>();
        departments.add(deliveryDepartment);
        company.setDepartments(departments);
        
        // Execute: Calculate average working hours for delivery department
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: The average working hours per week is 25 hours
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentWithNoWorkers() {
        // Setup: Create delivery department with no workers
        List<Employee> deliveryEmployees = new ArrayList<>();
        deliveryDepartment.setEmployees(deliveryEmployees);
        
        List<Department> departments = new ArrayList<>();
        departments.add(deliveryDepartment);
        company.setDepartments(departments);
        
        // Execute: Calculate average working hours for delivery department
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: The average working hours per week is 0 hours
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() {
        // Setup: Create 5 employees across all departments with 3 in Delivery department
        // Delivery department workers
        OffShiftWorker deliveryWorker1 = new OffShiftWorker();
        deliveryWorker1.setWeeklyWorkingHour(25);
        
        OffShiftWorker deliveryWorker2 = new OffShiftWorker();
        deliveryWorker2.setWeeklyWorkingHour(32);
        
        OffShiftWorker deliveryWorker3 = new OffShiftWorker();
        deliveryWorker3.setWeeklyWorkingHour(28);
        
        List<Employee> deliveryEmployees = new ArrayList<>();
        deliveryEmployees.add(deliveryWorker1);
        deliveryEmployees.add(deliveryWorker2);
        deliveryEmployees.add(deliveryWorker3);
        deliveryDepartment.setEmployees(deliveryEmployees);
        
        // Production department worker
        OffShiftWorker productionWorker = new OffShiftWorker();
        productionWorker.setWeeklyWorkingHour(40);
        
        List<Employee> productionEmployees = new ArrayList<>();
        productionEmployees.add(productionWorker);
        productionDepartment.setEmployees(productionEmployees);
        
        // Control department worker (non-worker employee)
        SalesPeople salesPerson = new SalesPeople();
        List<Employee> controlEmployees = new ArrayList<>();
        controlEmployees.add(salesPerson);
        controlDepartment.setEmployees(controlEmployees);
        
        List<Department> departments = new ArrayList<>();
        departments.add(deliveryDepartment);
        departments.add(productionDepartment);
        departments.add(controlDepartment);
        company.setDepartments(departments);
        
        // Execute: Calculate average working hours for delivery department only
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: The average working hours per week for Delivery department is 28.33 hours
        assertEquals(28.33, result, 0.01);
    }
}