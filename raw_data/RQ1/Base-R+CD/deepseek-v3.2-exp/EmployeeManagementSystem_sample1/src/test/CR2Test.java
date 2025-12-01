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
        // Setup: Create delivery department with one worker having 40 hours
        Department deliveryDept = new Department();
        deliveryDept.setType(DepartmentType.DELIVERY);
        
        OffShiftWorker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        worker.setHourlyRates(15.0);
        worker.setName("Worker1");
        worker.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
        worker.setSocialInsuranceNumber("123-45-6789");
        worker.setDepartment("Delivery");
        
        List<Employee> deliveryEmployees = new ArrayList<>();
        deliveryEmployees.add(worker);
        deliveryDept.setEmployees(deliveryEmployees);
        
        List<Department> departments = new ArrayList<>();
        departments.add(deliveryDept);
        company.setDepartments(departments);
        
        // Execute: Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify: Expected average is 40 hours
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersSameHoursInDeliveryDepartment() throws Exception {
        // Setup: Create delivery department with three workers each having 35 hours
        Department deliveryDept = new Department();
        deliveryDept.setType(DepartmentType.DELIVERY);
        
        List<Employee> deliveryEmployees = new ArrayList<>();
        
        for (int i = 1; i <= 3; i++) {
            OffShiftWorker worker = new OffShiftWorker();
            worker.setWeeklyWorkingHour(35);
            worker.setHourlyRates(15.0);
            worker.setName("Worker" + i);
            worker.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
            worker.setSocialInsuranceNumber("123-45-678" + i);
            worker.setDepartment("Delivery");
            deliveryEmployees.add(worker);
        }
        
        deliveryDept.setEmployees(deliveryEmployees);
        
        List<Department> departments = new ArrayList<>();
        departments.add(deliveryDept);
        company.setDepartments(departments);
        
        // Execute: Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify: Expected average is 35 hours
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersDifferentHoursInDeliveryDepartment() throws Exception {
        // Setup: Create delivery department with two workers having different hours
        Department deliveryDept = new Department();
        deliveryDept.setType(DepartmentType.DELIVERY);
        
        List<Employee> deliveryEmployees = new ArrayList<>();
        
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(20);
        worker1.setHourlyRates(15.0);
        worker1.setName("Worker1");
        worker1.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
        worker1.setSocialInsuranceNumber("123-45-6789");
        worker1.setDepartment("Delivery");
        deliveryEmployees.add(worker1);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(30);
        worker2.setHourlyRates(15.0);
        worker2.setName("Worker2");
        worker2.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
        worker2.setSocialInsuranceNumber("123-45-6790");
        worker2.setDepartment("Delivery");
        deliveryEmployees.add(worker2);
        
        deliveryDept.setEmployees(deliveryEmployees);
        
        List<Department> departments = new ArrayList<>();
        departments.add(deliveryDept);
        company.setDepartments(departments);
        
        // Execute: Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify: Expected average is 25 hours ((20 + 30) / 2)
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentWithNoWorkers() {
        // Setup: Create delivery department with no workers
        Department deliveryDept = new Department();
        deliveryDept.setType(DepartmentType.DELIVERY);
        deliveryDept.setEmployees(new ArrayList<Employee>());
        
        List<Department> departments = new ArrayList<>();
        departments.add(deliveryDept);
        company.setDepartments(departments);
        
        // Execute: Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify: Expected average is 0 hours (no workers)
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() throws Exception {
        // Setup: Create multiple departments including delivery department with 3 workers
        List<Department> departments = new ArrayList<>();
        
        // Create production department (should be ignored in calculation)
        Department productionDept = new Department();
        productionDept.setType(DepartmentType.PRODUCTION);
        List<Employee> productionEmployees = new ArrayList<>();
        
        OffShiftWorker prodWorker = new OffShiftWorker();
        prodWorker.setWeeklyWorkingHour(40); // This should be ignored
        prodWorker.setHourlyRates(15.0);
        prodWorker.setName("ProdWorker");
        prodWorker.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
        prodWorker.setSocialInsuranceNumber("123-45-6789");
        prodWorker.setDepartment("Production");
        productionEmployees.add(prodWorker);
        
        productionDept.setEmployees(productionEmployees);
        departments.add(productionDept);
        
        // Create control department (should be ignored in calculation)
        Department controlDept = new Department();
        controlDept.setType(DepartmentType.CONTROL);
        List<Employee> controlEmployees = new ArrayList<>();
        
        OffShiftWorker controlWorker = new OffShiftWorker();
        controlWorker.setWeeklyWorkingHour(38); // This should be ignored
        controlWorker.setHourlyRates(15.0);
        controlWorker.setName("ControlWorker");
        controlWorker.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
        controlWorker.setSocialInsuranceNumber("123-45-6790");
        controlWorker.setDepartment("Control");
        controlEmployees.add(controlWorker);
        
        controlDept.setEmployees(controlEmployees);
        departments.add(controlDept);
        
        // Create delivery department with 3 workers (this is what we're testing)
        Department deliveryDept = new Department();
        deliveryDept.setType(DepartmentType.DELIVERY);
        List<Employee> deliveryEmployees = new ArrayList<>();
        
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(25);
        worker1.setHourlyRates(15.0);
        worker1.setName("DeliveryWorker1");
        worker1.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
        worker1.setSocialInsuranceNumber("123-45-6791");
        worker1.setDepartment("Delivery");
        deliveryEmployees.add(worker1);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(32);
        worker2.setHourlyRates(15.0);
        worker2.setName("DeliveryWorker2");
        worker2.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
        worker2.setSocialInsuranceNumber("123-45-6792");
        worker2.setDepartment("Delivery");
        deliveryEmployees.add(worker2);
        
        OffShiftWorker worker3 = new OffShiftWorker();
        worker3.setWeeklyWorkingHour(28);
        worker3.setHourlyRates(15.0);
        worker3.setName("DeliveryWorker3");
        worker3.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
        worker3.setSocialInsuranceNumber("123-45-6793");
        worker3.setDepartment("Delivery");
        deliveryEmployees.add(worker3);
        
        deliveryDept.setEmployees(deliveryEmployees);
        departments.add(deliveryDept);
        
        company.setDepartments(departments);
        
        // Execute: Calculate average working hours
        double result = company.findAverageWorkingHoursInDeliveryDepartment();
        
        // Verify: Expected average is 28.33 hours ((25 + 32 + 28) / 3 = 28.333...)
        assertEquals(28.33, result, 0.01); // Allow small delta for floating point precision
    }
}