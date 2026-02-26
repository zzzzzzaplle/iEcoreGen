import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Company company;
    private Department deliveryDepartment;
    private Department productionDepartment;
    
    @Before
    public void setUp() {
        company = new Company();
        deliveryDepartment = new Department();
        deliveryDepartment.setType(DepartmentType.DELIVERY);
        
        productionDepartment = new Department();
        productionDepartment.setType(DepartmentType.PRODUCTION);
        
        List<Department> departments = new ArrayList<>();
        departments.add(deliveryDepartment);
        departments.add(productionDepartment);
        company.setDepartments(departments);
    }
    
    @Test
    public void testCase1_singleWorkerInDeliveryDepartment() {
        // Setup: One worker in the Delivery department with a weeklyWorkingHour of 40 hours
        List<Employee> deliveryEmployees = new ArrayList<>();
        OffShiftWorker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        deliveryEmployees.add(worker);
        deliveryDepartment.setEmployees(deliveryEmployees);
        
        // Execute: Calculate average working hours
        double result = company.calculateAverageWorkingHoursInDeliveryDepartment();
        
        // Verify: The average working hours per week is 40 hours
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersSameHoursInDeliveryDepartment() {
        // Setup: Three workers in the Delivery department, each with a weeklyWorkingHour of 35 hours
        List<Employee> deliveryEmployees = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            OffShiftWorker worker = new OffShiftWorker();
            worker.setWeeklyWorkingHour(35);
            deliveryEmployees.add(worker);
        }
        deliveryDepartment.setEmployees(deliveryEmployees);
        
        // Execute: Calculate average working hours
        double result = company.calculateAverageWorkingHoursInDeliveryDepartment();
        
        // Verify: The average working hours per week is 35 hours
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersDifferentHoursInDeliveryDepartment() {
        // Setup: Two workers in the Delivery department. One has 20 hours and the other has 30 hours
        List<Employee> deliveryEmployees = new ArrayList<>();
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(20);
        deliveryEmployees.add(worker1);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(30);
        deliveryEmployees.add(worker2);
        
        deliveryDepartment.setEmployees(deliveryEmployees);
        
        // Execute: Calculate average working hours
        double result = company.calculateAverageWorkingHoursInDeliveryDepartment();
        
        // Verify: The average working hours per week is 25 hours
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentWithNoWorkers() {
        // Setup: Zero workers in the Delivery department (empty employee list)
        List<Employee> deliveryEmployees = new ArrayList<>();
        deliveryDepartment.setEmployees(deliveryEmployees);
        
        // Execute: Calculate average working hours
        double result = company.calculateAverageWorkingHoursInDeliveryDepartment();
        
        // Verify: The average working hours per week is 0 hours
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() {
        // Setup: 5 employees across all departments, 3 in Delivery with hours 25, 32, and 28
        List<Employee> deliveryEmployees = new ArrayList<>();
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(25);
        deliveryEmployees.add(worker1);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(32);
        deliveryEmployees.add(worker2);
        
        OffShiftWorker worker3 = new OffShiftWorker();
        worker3.setWeeklyWorkingHour(28);
        deliveryEmployees.add(worker3);
        deliveryDepartment.setEmployees(deliveryEmployees);
        
        // Add non-worker employees to other departments to ensure they don't affect the calculation
        List<Employee> productionEmployees = new ArrayList<>();
        Manager manager = new Manager();
        productionEmployees.add(manager);
        
        SalesPeople salesPerson = new SalesPeople();
        productionEmployees.add(salesPerson);
        productionDepartment.setEmployees(productionEmployees);
        
        // Execute: Calculate average working hours
        double result = company.calculateAverageWorkingHoursInDeliveryDepartment();
        
        // Verify: The average working hours per week is 28.33 hours (rounded to two decimal places)
        assertEquals(28.33, result, 0.01);
    }
}