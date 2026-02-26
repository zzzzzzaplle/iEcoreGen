import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR2Test {
    
    private Company company;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        company = new Company();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_SingleWorkerInDeliveryDepartment() throws Exception {
        // Setup: Create delivery department with one worker having 40 hours
        Department deliveryDept = new Department(DepartmentType.DELIVERY);
        Date birthDate = dateFormat.parse("1990-01-01 00:00:00");
        
        OffShiftWorker worker = new OffShiftWorker("Delivery", "John Doe", birthDate, 
                                                 "123-45-6789", 40, 25.0);
        deliveryDept.addEmployee(worker);
        company.addDepartment(deliveryDept);
        
        // Execute: Calculate average working hours
        double result = deliveryDept.calculateAverageWorkerWorkingHours();
        
        // Verify: Average should be 40 hours
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_MultipleWorkersInDeliveryDepartmentWithSameHours() throws Exception {
        // Setup: Create delivery department with three workers, each with 35 hours
        Department deliveryDept = new Department(DepartmentType.DELIVERY);
        Date birthDate = dateFormat.parse("1990-01-01 00:00:00");
        
        // Add three workers with same working hours
        deliveryDept.addEmployee(new OffShiftWorker("Delivery", "Worker1", birthDate, 
                                                   "111-11-1111", 35, 20.0));
        deliveryDept.addEmployee(new OffShiftWorker("Delivery", "Worker2", birthDate, 
                                                   "222-22-2222", 35, 20.0));
        deliveryDept.addEmployee(new OffShiftWorker("Delivery", "Worker3", birthDate, 
                                                   "333-33-3333", 35, 20.0));
        company.addDepartment(deliveryDept);
        
        // Execute: Calculate average working hours
        double result = deliveryDept.calculateAverageWorkerWorkingHours();
        
        // Verify: Average should be 35 hours
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleWorkersInDeliveryDepartmentWithDifferentHours() throws Exception {
        // Setup: Create delivery department with two workers having different hours
        Department deliveryDept = new Department(DepartmentType.DELIVERY);
        Date birthDate = dateFormat.parse("1990-01-01 00:00:00");
        
        // Add workers with different working hours
        deliveryDept.addEmployee(new OffShiftWorker("Delivery", "Worker1", birthDate, 
                                                   "111-11-1111", 20, 15.0));
        deliveryDept.addEmployee(new OffShiftWorker("Delivery", "Worker2", birthDate, 
                                                   "222-22-2222", 30, 15.0));
        company.addDepartment(deliveryDept);
        
        // Execute: Calculate average working hours
        double result = deliveryDept.calculateAverageWorkerWorkingHours();
        
        // Verify: Average should be 25 hours (20 + 30) / 2 = 25
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_DeliveryDepartmentWithNoWorkers() {
        // Setup: Create delivery department with no workers
        Department deliveryDept = new Department(DepartmentType.DELIVERY);
        company.addDepartment(deliveryDept);
        
        // Execute: Calculate average working hours
        double result = deliveryDept.calculateAverageWorkerWorkingHours();
        
        // Verify: Should return 0 when there are no workers
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_DeliveryDepartmentMixedWithOtherDepartments() throws Exception {
        // Setup: Create multiple departments including delivery department
        Date birthDate = dateFormat.parse("1990-01-01 00:00:00");
        
        // Create delivery department with 3 workers having different hours
        Department deliveryDept = new Department(DepartmentType.DELIVERY);
        deliveryDept.addEmployee(new OffShiftWorker("Delivery", "Worker1", birthDate, 
                                                   "111-11-1111", 25, 18.0));
        deliveryDept.addEmployee(new OffShiftWorker("Delivery", "Worker2", birthDate, 
                                                   "222-22-2222", 32, 18.0));
        deliveryDept.addEmployee(new OffShiftWorker("Delivery", "Worker3", birthDate, 
                                                   "333-33-3333", 28, 18.0));
        company.addDepartment(deliveryDept);
        
        // Create other departments with different types of employees (should not affect the calculation)
        Department productionDept = new Department(DepartmentType.PRODUCTION);
        productionDept.addEmployee(new OffShiftWorker("Production", "ProdWorker", birthDate, 
                                                     "444-44-4444", 40, 22.0));
        company.addDepartment(productionDept);
        
        Department controlDept = new Department(DepartmentType.CONTROL);
        controlDept.addEmployee(new Manager("Control", "Manager", birthDate, 
                                           "555-55-5555", 50000, "Senior Manager"));
        company.addDepartment(controlDept);
        
        // Execute: Calculate average working hours for delivery department only
        double result = deliveryDept.calculateAverageWorkerWorkingHours();
        
        // Verify: Average should be (25 + 32 + 28) / 3 = 28.33
        assertEquals(28.33, result, 0.01); // Using delta of 0.01 for rounding to two decimal places
    }
}