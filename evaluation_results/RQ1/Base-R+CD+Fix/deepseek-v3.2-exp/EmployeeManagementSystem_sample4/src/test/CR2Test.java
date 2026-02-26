import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR2Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_singleWorkerInDeliveryDepartment() {
        // Test Case 1: Single worker in delivery department
        // Input: One worker in the Delivery department with a weeklyWorkingHour of 40 hours
        // Expected Output: The average working hours per week is 40 hours
        
        // Create delivery department
        Department deliveryDept = new Department();
        deliveryDept.setType(DepartmentType.DELIVERY);
        
        // Create a worker with 40 hours per week
        OffShiftWorker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        
        // Add worker to delivery department
        deliveryDept.getEmployees().add(worker);
        
        // Add delivery department to company
        company.getDepartments().add(deliveryDept);
        
        // Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify the result
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersSameHours() {
        // Test Case 2: Multiple workers in delivery department with same hours
        // Input: Three workers in the Delivery department, each with a weeklyWorkingHour of 35 hours
        // Expected Output: The average working hours per week is 35 hours
        
        // Create delivery department
        Department deliveryDept = new Department();
        deliveryDept.setType(DepartmentType.DELIVERY);
        
        // Create three workers with 35 hours per week each
        for (int i = 0; i < 3; i++) {
            OffShiftWorker worker = new OffShiftWorker();
            worker.setWeeklyWorkingHour(35);
            deliveryDept.getEmployees().add(worker);
        }
        
        // Add delivery department to company
        company.getDepartments().add(deliveryDept);
        
        // Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify the result
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersDifferentHours() {
        // Test Case 3: Multiple workers in delivery department with different hours
        // Input: Two workers in the Delivery department. One has a weeklyWorkingHour of 20 hours and the other has 30 hours
        // Expected Output: The average working hours per week is 25 hours
        
        // Create delivery department
        Department deliveryDept = new Department();
        deliveryDept.setType(DepartmentType.DELIVERY);
        
        // Create first worker with 20 hours
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(20);
        deliveryDept.getEmployees().add(worker1);
        
        // Create second worker with 30 hours
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(30);
        deliveryDept.getEmployees().add(worker2);
        
        // Add delivery department to company
        company.getDepartments().add(deliveryDept);
        
        // Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify the result (20 + 30) / 2 = 25
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentNoWorkers() {
        // Test Case 4: Delivery department with no workers
        // Input: There are zero workers in the Delivery department
        // Expected Output: The average working hours per week is 0 hours (as there are no workers)
        
        // Create delivery department with no workers
        Department deliveryDept = new Department();
        deliveryDept.setType(DepartmentType.DELIVERY);
        
        // Add delivery department to company
        company.getDepartments().add(deliveryDept);
        
        // Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify the result should be 0 when no workers
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_mixedDepartmentsWithDeliveryWorkers() {
        // Test Case 5: Delivery department mixed with other departments
        // Input: There are 5 employees in total across all departments. Among them, 3 are in the Delivery department with weeklyWorkingHours of 25, 32, and 28 hours respectively
        // Expected Output: The average working hours per week for workers in the Delivery department is 28.33 (rounded to two decimal places) hours
        
        // Create delivery department
        Department deliveryDept = new Department();
        deliveryDept.setType(DepartmentType.DELIVERY);
        
        // Create delivery department workers with different hours
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(25);
        deliveryDept.getEmployees().add(worker1);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(32);
        deliveryDept.getEmployees().add(worker2);
        
        OffShiftWorker worker3 = new OffShiftWorker();
        worker3.setWeeklyWorkingHour(28);
        deliveryDept.getEmployees().add(worker3);
        
        // Create other departments with other types of employees
        Department productionDept = new Department();
        productionDept.setType(DepartmentType.PRODUCTION);
        
        // Add a manager to production department (not a worker)
        Manager manager = new Manager();
        productionDept.getEmployees().add(manager);
        
        Department controlDept = new Department();
        controlDept.setType(DepartmentType.CONTROL);
        
        // Add a salesperson to control department (not a worker)
        SalesPeople salesPerson = new SalesPeople();
        controlDept.getEmployees().add(salesPerson);
        
        // Add all departments to company
        company.getDepartments().add(deliveryDept);
        company.getDepartments().add(productionDept);
        company.getDepartments().add(controlDept);
        
        // Calculate average working hours (25 + 32 + 28) / 3 = 28.333...
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify the result rounded to two decimal places
        assertEquals(28.33, result, 0.01);
    }
}