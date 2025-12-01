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
        company = new Company();
        deliveryDepartment = new Department();
        deliveryDepartment.setType(DepartmentType.DELIVERY);
        company.setDepartments(new ArrayList<>());
        company.getDepartments().add(deliveryDepartment);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_singleWorkerInDeliveryDepartment() throws Exception {
        // Setup: Create one worker in Delivery department with 40 hours
        Worker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        worker.setDepartment("DELIVERY");
        worker.setName("John Doe");
        worker.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
        worker.setSocialInsuranceNumber("123-45-6789");
        
        deliveryDepartment.setEmployees(new ArrayList<>());
        deliveryDepartment.getEmployees().add(worker);
        
        // Execute: Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: Average should be 40 hours
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersSameHours() throws Exception {
        // Setup: Create three workers in Delivery department, each with 35 hours
        List<Employee> workers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Worker worker = new OffShiftWorker();
            worker.setWeeklyWorkingHour(35);
            worker.setDepartment("DELIVERY");
            worker.setName("Worker " + (i + 1));
            worker.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
            worker.setSocialInsuranceNumber("123-45-678" + i);
            workers.add(worker);
        }
        
        deliveryDepartment.setEmployees(workers);
        
        // Execute: Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: Average should be 35 hours
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersDifferentHours() throws Exception {
        // Setup: Create two workers with different hours (20 and 30)
        Worker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(20);
        worker1.setDepartment("DELIVERY");
        worker1.setName("Worker 1");
        worker1.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
        worker1.setSocialInsuranceNumber("123-45-6789");
        
        Worker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(30);
        worker2.setDepartment("DELIVERY");
        worker2.setName("Worker 2");
        worker2.setBirthDate(dateFormat.parse("1991-01-01 00:00:00"));
        worker2.setSocialInsuranceNumber("123-45-6790");
        
        List<Employee> workers = new ArrayList<>();
        workers.add(worker1);
        workers.add(worker2);
        
        deliveryDepartment.setEmployees(workers);
        
        // Execute: Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: Average should be 25 hours (20+30)/2
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_noWorkersInDeliveryDepartment() {
        // Setup: Delivery department with empty employees list
        deliveryDepartment.setEmployees(new ArrayList<>());
        
        // Execute: Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: Should return 0 when no workers
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_mixedDepartmentsWithDeliveryWorkers() throws Exception {
        // Setup: Create 5 employees total, 3 in Delivery with hours 25, 32, 28
        List<Employee> deliveryEmployees = new ArrayList<>();
        
        Worker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(25);
        worker1.setDepartment("DELIVERY");
        worker1.setName("Delivery Worker 1");
        worker1.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
        worker1.setSocialInsuranceNumber("123-45-6789");
        
        Worker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(32);
        worker2.setDepartment("DELIVERY");
        worker2.setName("Delivery Worker 2");
        worker2.setBirthDate(dateFormat.parse("1991-01-01 00:00:00"));
        worker2.setSocialInsuranceNumber("123-45-6790");
        
        Worker worker3 = new OffShiftWorker();
        worker3.setWeeklyWorkingHour(28);
        worker3.setDepartment("DELIVERY");
        worker3.setName("Delivery Worker 3");
        worker3.setBirthDate(dateFormat.parse("1992-01-01 00:00:00"));
        worker3.setSocialInsuranceNumber("123-45-6791");
        
        deliveryEmployees.add(worker1);
        deliveryEmployees.add(worker2);
        deliveryEmployees.add(worker3);
        
        deliveryDepartment.setEmployees(deliveryEmployees);
        
        // Execute: Calculate average working hours for delivery department
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: Average should be (25+32+28)/3 = 28.33 hours (rounded to two decimal places)
        assertEquals(28.33, result, 0.01);
    }
}