import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Department deliveryDepartment;
    private Department otherDepartment;
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
        
        deliveryDepartment = new Department();
        deliveryDepartment.setType(DepartmentType.DELIVERY);
        
        otherDepartment = new Department();
        otherDepartment.setType(DepartmentType.PRODUCTION);
        
        List<Department> departments = new ArrayList<>();
        departments.add(deliveryDepartment);
        departments.add(otherDepartment);
        company.setDepartments(departments);
    }
    
    @Test
    public void testCase1_SingleWorkerInDeliveryDepartment() {
        // Setup: One worker in the Delivery department with weeklyWorkingHour of 40 hours
        ShiftWorker worker = new ShiftWorker();
        worker.setWeeklyWorkingHour(40);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(worker);
        deliveryDepartment.setEmployees(employees);
        
        // Execute: Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: The average working hours per week is 40 hours
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_MultipleWorkersSameHoursInDeliveryDepartment() {
        // Setup: Three workers in the Delivery department, each with weeklyWorkingHour of 35 hours
        Worker worker1 = new ShiftWorker();
        worker1.setWeeklyWorkingHour(35);
        
        Worker worker2 = new ShiftWorker();
        worker2.setWeeklyWorkingHour(35);
        
        Worker worker3 = new ShiftWorker();
        worker3.setWeeklyWorkingHour(35);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(worker1);
        employees.add(worker2);
        employees.add(worker3);
        deliveryDepartment.setEmployees(employees);
        
        // Execute: Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: The average working hours per week is 35 hours
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleWorkersDifferentHoursInDeliveryDepartment() {
        // Setup: Two workers in the Delivery department with different hours
        Worker worker1 = new ShiftWorker();
        worker1.setWeeklyWorkingHour(20);
        
        Worker worker2 = new ShiftWorker();
        worker2.setWeeklyWorkingHour(30);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(worker1);
        employees.add(worker2);
        deliveryDepartment.setEmployees(employees);
        
        // Execute: Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: The average working hours per week is 25 hours (20+30)/2 = 25
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_DeliveryDepartmentWithNoWorkers() {
        // Setup: Zero workers in the Delivery department (only non-worker employees)
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(50000);
        salesPerson.setAmountOfSales(100000);
        salesPerson.setCommissionPercentage(0.1);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(salesPerson);
        deliveryDepartment.setEmployees(employees);
        
        // Execute: Calculate average working hours
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: The average working hours per week is 0 hours (as there are no workers)
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_DeliveryDepartmentMixedWithOtherDepartments() {
        // Setup: 5 employees total, 3 workers in Delivery department with hours 25, 32, 28
        Worker worker1 = new ShiftWorker();
        worker1.setWeeklyWorkingHour(25);
        
        Worker worker2 = new ShiftWorker();
        worker2.setWeeklyWorkingHour(32);
        
        Worker worker3 = new ShiftWorker();
        worker3.setWeeklyWorkingHour(28);
        
        // Add non-worker to Delivery department (should not be counted)
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(50000);
        
        // Add to Delivery department
        List<Employee> deliveryEmployees = new ArrayList<>();
        deliveryEmployees.add(worker1);
        deliveryEmployees.add(worker2);
        deliveryEmployees.add(worker3);
        deliveryEmployees.add(salesPerson);
        deliveryDepartment.setEmployees(deliveryEmployees);
        
        // Add workers to other department (should not be counted)
        Worker otherWorker = new ShiftWorker();
        otherWorker.setWeeklyWorkingHour(40);
        List<Employee> otherEmployees = new ArrayList<>();
        otherEmployees.add(otherWorker);
        otherDepartment.setEmployees(otherEmployees);
        
        // Execute: Calculate average working hours for Delivery department
        double result = deliveryDepartment.calculateAverageWorkerWorkingHours();
        
        // Verify: The average working hours per week is 28.33 (25+32+28)/3 = 85/3 = 28.33
        assertEquals(28.33, result, 0.01);
    }
}