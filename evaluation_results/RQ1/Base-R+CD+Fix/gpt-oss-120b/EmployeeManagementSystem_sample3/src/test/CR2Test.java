import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_singleWorkerInDeliveryDepartment() {
        // Setup: One worker in the Delivery department with a weeklyWorkingHour of 40 hours
        OffShiftWorker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        worker.setDepartment(DepartmentType.DELIVERY);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(worker);
        company.setEmployees(employees);
        
        // Execute: Calculate average working hours for delivery department
        double result = company.calculateAverageWorkingHoursInDelivery();
        
        // Verify: The average working hours per week is 40 hours
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersInDeliveryDepartmentWithSameHours() {
        // Setup: Three workers in the Delivery department, each with a weeklyWorkingHour of 35 hours
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(35);
        worker1.setDepartment(DepartmentType.DELIVERY);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(35);
        worker2.setDepartment(DepartmentType.DELIVERY);
        
        OffShiftWorker worker3 = new OffShiftWorker();
        worker3.setWeeklyWorkingHour(35);
        worker3.setDepartment(DepartmentType.DELIVERY);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(worker1);
        employees.add(worker2);
        employees.add(worker3);
        company.setEmployees(employees);
        
        // Execute: Calculate average working hours for delivery department
        double result = company.calculateAverageWorkingHoursInDelivery();
        
        // Verify: The average working hours per week is 35 hours
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersInDeliveryDepartmentWithDifferentHours() {
        // Setup: Two workers in the Delivery department with weeklyWorkingHours of 20 and 30 hours
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(20);
        worker1.setDepartment(DepartmentType.DELIVERY);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(30);
        worker2.setDepartment(DepartmentType.DELIVERY);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(worker1);
        employees.add(worker2);
        company.setEmployees(employees);
        
        // Execute: Calculate average working hours for delivery department
        double result = company.calculateAverageWorkingHoursInDelivery();
        
        // Verify: The average working hours per week is 25 hours
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentWithNoWorkers() {
        // Setup: Zero workers in the Delivery department
        // Add an employee that is not a worker and not in delivery department
        Manager manager = new Manager();
        manager.setDepartment(DepartmentType.PRODUCTION);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(manager);
        company.setEmployees(employees);
        
        // Execute: Calculate average working hours for delivery department
        double result = company.calculateAverageWorkingHoursInDelivery();
        
        // Verify: The average working hours per week is 0 hours
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() {
        // Setup: 5 employees total, 3 in Delivery department with hours 25, 32, and 28
        OffShiftWorker deliveryWorker1 = new OffShiftWorker();
        deliveryWorker1.setWeeklyWorkingHour(25);
        deliveryWorker1.setDepartment(DepartmentType.DELIVERY);
        
        OffShiftWorker deliveryWorker2 = new OffShiftWorker();
        deliveryWorker2.setWeeklyWorkingHour(32);
        deliveryWorker2.setDepartment(DepartmentType.DELIVERY);
        
        OffShiftWorker deliveryWorker3 = new OffShiftWorker();
        deliveryWorker3.setWeeklyWorkingHour(28);
        deliveryWorker3.setDepartment(DepartmentType.DELIVERY);
        
        // Add employees from other departments
        Manager productionManager = new Manager();
        productionManager.setDepartment(DepartmentType.PRODUCTION);
        
        SalesPeople controlSales = new SalesPeople();
        controlSales.setDepartment(DepartmentType.CONTROL);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(deliveryWorker1);
        employees.add(deliveryWorker2);
        employees.add(deliveryWorker3);
        employees.add(productionManager);
        employees.add(controlSales);
        company.setEmployees(employees);
        
        // Execute: Calculate average working hours for delivery department
        double result = company.calculateAverageWorkingHoursInDelivery();
        
        // Verify: The average working hours per week is 28.33 (rounded to two decimal places)
        assertEquals(28.33, result, 0.01);
    }
}