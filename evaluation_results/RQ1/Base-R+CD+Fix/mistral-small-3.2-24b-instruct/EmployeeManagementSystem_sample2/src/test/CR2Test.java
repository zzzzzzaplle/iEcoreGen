import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    public void testCase1_singleWorkerInDeliveryDepartment() throws Exception {
        // Create a single worker in the Delivery department with 40 hours
        Department deliveryDept = new Department(DepartmentType.DELIVERY);
        OffShiftWorker worker = new OffShiftWorker("Delivery", "Worker1", 
            dateFormat.parse("1990-01-01 00:00:00"), "123-45-6789", 40, 25.0);
        
        deliveryDept.getEmployees().add(worker);
        company.getDepartments().add(deliveryDept);
        
        // Calculate average working hours for delivery department
        double averageHours = deliveryDept.calculateAverageWorkerWorkingHours();
        
        // Verify the result is 40 hours
        assertEquals(40.0, averageHours, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersSameHours() throws Exception {
        // Create three workers in Delivery department, each with 35 hours
        Department deliveryDept = new Department(DepartmentType.DELIVERY);
        
        OffShiftWorker worker1 = new OffShiftWorker("Delivery", "Worker1", 
            dateFormat.parse("1990-01-01 00:00:00"), "123-45-6789", 35, 25.0);
        OffShiftWorker worker2 = new OffShiftWorker("Delivery", "Worker2", 
            dateFormat.parse("1991-02-02 00:00:00"), "123-45-6790", 35, 25.0);
        OffShiftWorker worker3 = new OffShiftWorker("Delivery", "Worker3", 
            dateFormat.parse("1992-03-03 00:00:00"), "123-45-6791", 35, 25.0);
        
        deliveryDept.getEmployees().add(worker1);
        deliveryDept.getEmployees().add(worker2);
        deliveryDept.getEmployees().add(worker3);
        company.getDepartments().add(deliveryDept);
        
        // Calculate average working hours for delivery department
        double averageHours = deliveryDept.calculateAverageWorkerWorkingHours();
        
        // Verify the result is 35 hours
        assertEquals(35.0, averageHours, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersDifferentHours() throws Exception {
        // Create two workers in Delivery department with different hours
        Department deliveryDept = new Department(DepartmentType.DELIVERY);
        
        OffShiftWorker worker1 = new OffShiftWorker("Delivery", "Worker1", 
            dateFormat.parse("1990-01-01 00:00:00"), "123-45-6789", 20, 25.0);
        OffShiftWorker worker2 = new OffShiftWorker("Delivery", "Worker2", 
            dateFormat.parse("1991-02-02 00:00:00"), "123-45-6790", 30, 25.0);
        
        deliveryDept.getEmployees().add(worker1);
        deliveryDept.getEmployees().add(worker2);
        company.getDepartments().add(deliveryDept);
        
        // Calculate average working hours for delivery department
        double averageHours = deliveryDept.calculateAverageWorkerWorkingHours();
        
        // Verify the result is 25 hours (average of 20 and 30)
        assertEquals(25.0, averageHours, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentNoWorkers() throws Exception {
        // Create a Delivery department with no workers
        Department deliveryDept = new Department(DepartmentType.DELIVERY);
        company.getDepartments().add(deliveryDept);
        
        // Calculate average working hours for delivery department
        double averageHours = deliveryDept.calculateAverageWorkerWorkingHours();
        
        // Verify the result is 0 hours (no workers)
        assertEquals(0.0, averageHours, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() throws Exception {
        // Create multiple departments with workers
        Department deliveryDept = new Department(DepartmentType.DELIVERY);
        Department productionDept = new Department(DepartmentType.PRODUCTION);
        
        // Add 3 workers to Delivery department with different hours
        OffShiftWorker worker1 = new OffShiftWorker("Delivery", "Worker1", 
            dateFormat.parse("1990-01-01 00:00:00"), "123-45-6789", 25, 25.0);
        OffShiftWorker worker2 = new OffShiftWorker("Delivery", "Worker2", 
            dateFormat.parse("1991-02-02 00:00:00"), "123-45-6790", 32, 25.0);
        OffShiftWorker worker3 = new OffShiftWorker("Delivery", "Worker3", 
            dateFormat.parse("1992-03-03 00:00:00"), "123-45-6791", 28, 25.0);
        
        // Add 2 workers to Production department (should not be counted)
        OffShiftWorker worker4 = new OffShiftWorker("Production", "Worker4", 
            dateFormat.parse("1993-04-04 00:00:00"), "123-45-6792", 40, 25.0);
        OffShiftWorker worker5 = new OffShiftWorker("Production", "Worker5", 
            dateFormat.parse("1994-05-05 00:00:00"), "123-45-6793", 38, 25.0);
        
        deliveryDept.getEmployees().add(worker1);
        deliveryDept.getEmployees().add(worker2);
        deliveryDept.getEmployees().add(worker3);
        productionDept.getEmployees().add(worker4);
        productionDept.getEmployees().add(worker5);
        
        company.getDepartments().add(deliveryDept);
        company.getDepartments().add(productionDept);
        
        // Calculate average working hours specifically for delivery department
        double averageHours = deliveryDept.calculateAverageWorkerWorkingHours();
        
        // Verify the result is 28.33 hours (average of 25, 32, 28)
        assertEquals(28.33, averageHours, 0.01);
    }
}