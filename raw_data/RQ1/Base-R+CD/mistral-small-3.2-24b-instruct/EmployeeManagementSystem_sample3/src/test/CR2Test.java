import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR2Test {
    private Department deliveryDepartment;
    
    @Before
    public void setUp() {
        deliveryDepartment = new Department();
        deliveryDepartment.setType(DepartmentType.DELIVERY);
    }
    
    @Test
    public void testCase1_singleWorkerInDeliveryDepartment() {
        // Setup: Create one worker in Delivery department with 40 hours
        OffShiftWorker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        deliveryDepartment.addEmployee(worker);
        
        // Execute: Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: Average should be exactly 40 hours
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersSameHours() {
        // Setup: Create three workers in Delivery department, each with 35 hours
        for (int i = 0; i < 3; i++) {
            OffShiftWorker worker = new OffShiftWorker();
            worker.setWeeklyWorkingHour(35);
            deliveryDepartment.addEmployee(worker);
        }
        
        // Execute: Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: Average should be exactly 35 hours
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersDifferentHours() {
        // Setup: Create two workers with different hours (20 and 30)
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(20);
        deliveryDepartment.addEmployee(worker1);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(30);
        deliveryDepartment.addEmployee(worker2);
        
        // Execute: Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: Average should be 25 hours ((20+30)/2)
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentNoWorkers() {
        // Setup: Department has no workers (empty)
        
        // Execute: Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: Should return 0 when no workers
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() {
        // Setup: Create 3 workers in Delivery department with hours 25, 32, and 28
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(25);
        deliveryDepartment.addEmployee(worker1);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(32);
        deliveryDepartment.addEmployee(worker2);
        
        OffShiftWorker worker3 = new OffShiftWorker();
        worker3.setWeeklyWorkingHour(28);
        deliveryDepartment.addEmployee(worker3);
        
        // Note: Other departments are not added to this department instance
        // as per the method implementation, it only considers employees in this department
        
        // Execute: Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: Average should be 28.33 hours ((25+32+28)/3 = 85/3 = 28.333...)
        assertEquals(28.33, result, 0.01); // Using delta of 0.01 for rounding to two decimals
    }
}