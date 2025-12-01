import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR2Test {
    
    private Company company;
    private Department deliveryDepartment;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        // Initialize objects before each test
        company = new Company();
        deliveryDepartment = new Department(DepartmentType.DELIVERY);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_singleWorkerInDeliveryDepartment() throws Exception {
        // Test Case 1: Single worker in delivery department
        // Input: One worker in the Delivery department with a weeklyWorkingHour of 40 hours
        Date birthDate = dateFormat.parse("1990-01-01 00:00:00");
        
        // Create a single worker in delivery department
        OffShiftWorker worker = new OffShiftWorker(
            "DELIVERY", 
            "John Doe", 
            birthDate, 
            "123-45-6789", 
            40, 
            25.0
        );
        
        // Add worker to company
        List<Employee> employees = new ArrayList<>();
        employees.add(worker);
        company.setEmployees(employees);
        
        // Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours(company);
        
        // Expected Output: The average working hours per week is 40 hours
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersSameHours() throws Exception {
        // Test Case 2: Multiple workers in delivery department with same hours
        // Input: Three workers in the Delivery department, each with a weeklyWorkingHour of 35 hours
        Date birthDate = dateFormat.parse("1990-01-01 00:00:00");
        
        // Create three workers with same hours in delivery department
        List<Employee> employees = new ArrayList<>();
        employees.add(new OffShiftWorker("DELIVERY", "Worker1", birthDate, "111-11-1111", 35, 20.0));
        employees.add(new OffShiftWorker("DELIVERY", "Worker2", birthDate, "222-22-2222", 35, 20.0));
        employees.add(new OffShiftWorker("DELIVERY", "Worker3", birthDate, "333-33-3333", 35, 20.0));
        
        company.setEmployees(employees);
        
        // Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours(company);
        
        // Expected Output: The average working hours per week is 35 hours
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersDifferentHours() throws Exception {
        // Test Case 3: Multiple workers in delivery department with different hours
        // Input: Two workers in the Delivery department. One has a weeklyWorkingHour of 20 hours and the other has 30 hours
        Date birthDate = dateFormat.parse("1990-01-01 00:00:00");
        
        // Create two workers with different hours in delivery department
        List<Employee> employees = new ArrayList<>();
        employees.add(new OffShiftWorker("DELIVERY", "Worker1", birthDate, "111-11-1111", 20, 15.0));
        employees.add(new OffShiftWorker("DELIVERY", "Worker2", birthDate, "222-22-2222", 30, 15.0));
        
        company.setEmployees(employees);
        
        // Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours(company);
        
        // Expected Output: The average working hours per week is 25 hours
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentWithNoWorkers() throws Exception {
        // Test Case 4: Delivery department with no workers
        // Input: There are zero workers in the Delivery department
        Date birthDate = dateFormat.parse("1990-01-01 00:00:00");
        
        // Create employees but none in delivery department
        List<Employee> employees = new ArrayList<>();
        employees.add(new OffShiftWorker("PRODUCTION", "Worker1", birthDate, "111-11-1111", 40, 20.0));
        employees.add(new Manager("CONTROL", "Manager1", birthDate, "222-22-2222", 50000, "Senior Manager"));
        
        company.setEmployees(employees);
        
        // Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours(company);
        
        // Expected Output: The average working hours per week is 0 hours (as there are no workers)
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_mixedDepartmentsWithDeliveryWorkers() throws Exception {
        // Test Case 5: Delivery department mixed with other departments
        // Input: There are 5 employees in total across all departments. 
        // Among them, 3 are in the Delivery department with weeklyWorkingHours of 25, 32, and 28 hours respectively
        Date birthDate = dateFormat.parse("1990-01-01 00:00:00");
        
        // Create mixed departments with 3 workers in delivery department
        List<Employee> employees = new ArrayList<>();
        
        // Delivery department workers
        employees.add(new OffShiftWorker("DELIVERY", "DeliveryWorker1", birthDate, "111-11-1111", 25, 18.0));
        employees.add(new OffShiftWorker("DELIVERY", "DeliveryWorker2", birthDate, "222-22-2222", 32, 18.0));
        employees.add(new OffShiftWorker("DELIVERY", "DeliveryWorker3", birthDate, "333-33-3333", 28, 18.0));
        
        // Other department employees
        employees.add(new OffShiftWorker("PRODUCTION", "ProdWorker", birthDate, "444-44-4444", 40, 20.0));
        employees.add(new Manager("CONTROL", "Manager", birthDate, "555-55-5555", 60000, "Director"));
        
        company.setEmployees(employees);
        
        // Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours(company);
        
        // Expected Output: The average working hours per week for workers in the Delivery department is 28.33 (rounded to two decimal places) hours
        // Calculation: (25 + 32 + 28) / 3 = 85 / 3 = 28.333...
        assertEquals(28.33, result, 0.01); // Using delta of 0.01 for two decimal places precision
    }
}