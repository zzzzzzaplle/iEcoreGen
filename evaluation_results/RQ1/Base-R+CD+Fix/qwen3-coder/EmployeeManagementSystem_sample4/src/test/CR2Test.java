import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

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
    }
    
    @Test
    public void testCase1_SingleWorkerInDeliveryDepartment() {
        // Test Case 1: Single worker in delivery department
        // Input: One worker in the Delivery department with a weeklyWorkingHour of 40 hours
        // Expected Output: The average working hours per week is 40 hours
        
        // Create a worker and set weekly working hours to 40
        OffShiftWorker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        worker.setDepartment("Delivery");
        
        // Add worker to delivery department
        List<Employee> deliveryEmployees = new ArrayList<>();
        deliveryEmployees.add(worker);
        deliveryDepartment.setEmployees(deliveryEmployees);
        
        // Add department to company
        List<Department> departments = new ArrayList<>();
        departments.add(deliveryDepartment);
        company.setDepartments(departments);
        
        // Find delivery department and calculate average
        double averageHours = 0;
        for (Department dept : company.getDepartments()) {
            if (dept.getType() == DepartmentType.DELIVERY) {
                averageHours = dept.calculateAverageWorkerWorkingHours();
                break;
            }
        }
        
        // Verify the result
        assertEquals(40.0, averageHours, 0.001);
    }
    
    @Test
    public void testCase2_MultipleWorkersSameHoursInDeliveryDepartment() {
        // Test Case 2: Multiple workers in delivery department with same hours
        // Input: Three workers in the Delivery department, each with a weeklyWorkingHour of 35 hours
        // Expected Output: The average working hours per week is 35 hours
        
        // Create three workers with same working hours
        List<Employee> deliveryEmployees = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            OffShiftWorker worker = new OffShiftWorker();
            worker.setWeeklyWorkingHour(35);
            worker.setDepartment("Delivery");
            deliveryEmployees.add(worker);
        }
        
        deliveryDepartment.setEmployees(deliveryEmployees);
        
        // Add department to company
        List<Department> departments = new ArrayList<>();
        departments.add(deliveryDepartment);
        company.setDepartments(departments);
        
        // Find delivery department and calculate average
        double averageHours = 0;
        for (Department dept : company.getDepartments()) {
            if (dept.getType() == DepartmentType.DELIVERY) {
                averageHours = dept.calculateAverageWorkerWorkingHours();
                break;
            }
        }
        
        // Verify the result
        assertEquals(35.0, averageHours, 0.001);
    }
    
    @Test
    public void testCase3_MultipleWorkersDifferentHoursInDeliveryDepartment() {
        // Test Case 3: Multiple workers in delivery department with different hours
        // Input: Two workers in the Delivery department. One has a weeklyWorkingHour of 20 hours and the other has 30 hours
        // Expected Output: The average working hours per week is 25 hours
        
        // Create two workers with different working hours
        List<Employee> deliveryEmployees = new ArrayList<>();
        
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(20);
        worker1.setDepartment("Delivery");
        deliveryEmployees.add(worker1);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(30);
        worker2.setDepartment("Delivery");
        deliveryEmployees.add(worker2);
        
        deliveryDepartment.setEmployees(deliveryEmployees);
        
        // Add department to company
        List<Department> departments = new ArrayList<>();
        departments.add(deliveryDepartment);
        company.setDepartments(departments);
        
        // Find delivery department and calculate average
        double averageHours = 0;
        for (Department dept : company.getDepartments()) {
            if (dept.getType() == DepartmentType.DELIVERY) {
                averageHours = dept.calculateAverageWorkerWorkingHours();
                break;
            }
        }
        
        // Verify the result
        assertEquals(25.0, averageHours, 0.001);
    }
    
    @Test
    public void testCase4_DeliveryDepartmentWithNoWorkers() {
        // Test Case 4: Delivery department with no workers
        // Input: There are zero workers in the Delivery department
        // Expected Output: The average working hours per week is 0 hours (as there are no workers)
        
        // Create delivery department with no workers
        List<Employee> deliveryEmployees = new ArrayList<>(); // Empty list
        deliveryDepartment.setEmployees(deliveryEmployees);
        
        // Add department to company
        List<Department> departments = new ArrayList<>();
        departments.add(deliveryDepartment);
        company.setDepartments(departments);
        
        // Find delivery department and calculate average
        double averageHours = 0;
        for (Department dept : company.getDepartments()) {
            if (dept.getType() == DepartmentType.DELIVERY) {
                averageHours = dept.calculateAverageWorkerWorkingHours();
                break;
            }
        }
        
        // Verify the result
        assertEquals(0.0, averageHours, 0.001);
    }
    
    @Test
    public void testCase5_DeliveryDepartmentMixedWithOtherDepartments() {
        // Test Case 5: Delivery department mixed with other departments
        // Input: There are 5 employees in total across all departments. Among them, 3 are in the Delivery department with weeklyWorkingHours of 25, 32, and 28 hours respectively
        // Expected Output: The average working hours per week for workers in the Delivery department is 28.33 (rounded to two decimal places) hours
        
        // Create delivery department workers
        List<Employee> deliveryEmployees = new ArrayList<>();
        
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(25);
        worker1.setDepartment("Delivery");
        deliveryEmployees.add(worker1);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(32);
        worker2.setDepartment("Delivery");
        deliveryEmployees.add(worker2);
        
        OffShiftWorker worker3 = new OffShiftWorker();
        worker3.setWeeklyWorkingHour(28);
        worker3.setDepartment("Delivery");
        deliveryEmployees.add(worker3);
        
        deliveryDepartment.setEmployees(deliveryEmployees);
        
        // Create other department with non-worker employees (to make total 5 employees)
        List<Employee> otherEmployees = new ArrayList<>();
        
        Manager manager = new Manager();
        manager.setDepartment("Production");
        otherEmployees.add(manager);
        
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setDepartment("Production");
        otherEmployees.add(salesPerson);
        
        otherDepartment.setEmployees(otherEmployees);
        
        // Add both departments to company
        List<Department> departments = new ArrayList<>();
        departments.add(deliveryDepartment);
        departments.add(otherDepartment);
        company.setDepartments(departments);
        
        // Find delivery department and calculate average
        double averageHours = 0;
        for (Department dept : company.getDepartments()) {
            if (dept.getType() == DepartmentType.DELIVERY) {
                averageHours = dept.calculateAverageWorkerWorkingHours();
                break;
            }
        }
        
        // Verify the result (25 + 32 + 28 = 85 / 3 = 28.333... rounded to 28.33)
        assertEquals(28.33, averageHours, 0.01);
    }
}