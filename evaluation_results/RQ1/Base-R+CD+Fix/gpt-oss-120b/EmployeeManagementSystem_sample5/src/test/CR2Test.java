import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public void testCase1_singleWorkerInDeliveryDepartment() {
        // Input: One worker in the Delivery department with a weeklyWorkingHour of 40 hours
        OffShiftWorker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        worker.setHourlyRates(15.0);
        
        List<Employee> deliveryEmployees = new ArrayList<>();
        deliveryEmployees.add(worker);
        deliveryDepartment.setEmployees(deliveryEmployees);
        
        List<Department> departments = new ArrayList<>();
        departments.add(deliveryDepartment);
        company.setDepartments(departments);
        
        // Expected Output: The average working hours per week is 40 hours
        double result = company.calculateAverageWorkerWorkingHoursInDelivery();
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersSameHours() {
        // Input: Three workers in the Delivery department, each with a weeklyWorkingHour of 35 hours
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(35);
        worker1.setHourlyRates(15.0);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(35);
        worker2.setHourlyRates(15.0);
        
        OffShiftWorker worker3 = new OffShiftWorker();
        worker3.setWeeklyWorkingHour(35);
        worker3.setHourlyRates(15.0);
        
        List<Employee> deliveryEmployees = new ArrayList<>();
        deliveryEmployees.add(worker1);
        deliveryEmployees.add(worker2);
        deliveryEmployees.add(worker3);
        deliveryDepartment.setEmployees(deliveryEmployees);
        
        List<Department> departments = new ArrayList<>();
        departments.add(deliveryDepartment);
        company.setDepartments(departments);
        
        // Expected Output: The average working hours per week is 35 hours
        double result = company.calculateAverageWorkerWorkingHoursInDelivery();
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersDifferentHours() {
        // Input: Two workers in the Delivery department. One has a weeklyWorkingHour of 20 hours and the other has 30 hours
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(20);
        worker1.setHourlyRates(15.0);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(30);
        worker2.setHourlyRates(15.0);
        
        List<Employee> deliveryEmployees = new ArrayList<>();
        deliveryEmployees.add(worker1);
        deliveryEmployees.add(worker2);
        deliveryDepartment.setEmployees(deliveryEmployees);
        
        List<Department> departments = new ArrayList<>();
        departments.add(deliveryDepartment);
        company.setDepartments(departments);
        
        // Expected Output: The average working hours per week is 25 hours
        double result = company.calculateAverageWorkerWorkingHoursInDelivery();
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentWithNoWorkers() {
        // Input: There are zero workers in the Delivery department
        // Add non-worker employees to delivery department
        Manager manager = new Manager();
        manager.setSalary(50000);
        
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(30000);
        salesPerson.setAmountOfSales(10000);
        salesPerson.setCommissionPercentage(0.1);
        
        List<Employee> deliveryEmployees = new ArrayList<>();
        deliveryEmployees.add(manager);
        deliveryEmployees.add(salesPerson);
        deliveryDepartment.setEmployees(deliveryEmployees);
        
        List<Department> departments = new ArrayList<>();
        departments.add(deliveryDepartment);
        company.setDepartments(departments);
        
        // Expected Output: The average working hours per week is 0 hours (as there are no workers)
        double result = company.calculateAverageWorkerWorkingHoursInDelivery();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_deliveryDepartmentMixedWithOtherDepartments() {
        // Input: There are 5 employees in total across all departments. 
        // Among them, 3 are in the Delivery department with weeklyWorkingHours of 25, 32, and 28 hours respectively
        
        // Delivery department workers
        OffShiftWorker deliveryWorker1 = new OffShiftWorker();
        deliveryWorker1.setWeeklyWorkingHour(25);
        deliveryWorker1.setHourlyRates(15.0);
        
        OffShiftWorker deliveryWorker2 = new OffShiftWorker();
        deliveryWorker2.setWeeklyWorkingHour(32);
        deliveryWorker2.setHourlyRates(15.0);
        
        OffShiftWorker deliveryWorker3 = new OffShiftWorker();
        deliveryWorker3.setWeeklyWorkingHour(28);
        deliveryWorker3.setHourlyRates(15.0);
        
        List<Employee> deliveryEmployees = new ArrayList<>();
        deliveryEmployees.add(deliveryWorker1);
        deliveryEmployees.add(deliveryWorker2);
        deliveryEmployees.add(deliveryWorker3);
        deliveryDepartment.setEmployees(deliveryEmployees);
        
        // Other department employees (non-workers)
        Manager manager = new Manager();
        manager.setSalary(50000);
        
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(30000);
        salesPerson.setAmountOfSales(10000);
        salesPerson.setCommissionPercentage(0.1);
        
        List<Employee> otherEmployees = new ArrayList<>();
        otherEmployees.add(manager);
        otherEmployees.add(salesPerson);
        otherDepartment.setEmployees(otherEmployees);
        
        List<Department> departments = new ArrayList<>();
        departments.add(deliveryDepartment);
        departments.add(otherDepartment);
        company.setDepartments(departments);
        
        // Expected Output: The average working hours per week for workers in the Delivery department is 28.33 (rounded to two decimal places) hours
        double result = company.calculateAverageWorkerWorkingHoursInDelivery();
        assertEquals(28.33, result, 0.01); // Using delta of 0.01 for rounding to two decimal places
    }
}