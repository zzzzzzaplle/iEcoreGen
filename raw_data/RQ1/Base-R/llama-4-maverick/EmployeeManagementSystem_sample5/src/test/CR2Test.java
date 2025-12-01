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
        // Test Case 1: Single worker in delivery department
        // Input: One worker in the Delivery department with a weeklyWorkingHour of 40 hours
        NonShiftWorker worker = new NonShiftWorker();
        worker.setDepartment("delivery");
        worker.setWeeklyWorkingHour(40.0);
        worker.setHourlyRates(15.0);
        company.addEmployee(worker);
        
        double result = company.averageWorkingHoursInDeliveryDepartment();
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersSameHours() {
        // Test Case 2: Multiple workers in delivery department with same hours
        // Input: Three workers in the Delivery department, each with a weeklyWorkingHour of 35 hours
        NonShiftWorker worker1 = new NonShiftWorker();
        worker1.setDepartment("delivery");
        worker1.setWeeklyWorkingHour(35.0);
        worker1.setHourlyRates(15.0);
        
        NonShiftWorker worker2 = new NonShiftWorker();
        worker2.setDepartment("delivery");
        worker2.setWeeklyWorkingHour(35.0);
        worker2.setHourlyRates(16.0);
        
        NonShiftWorker worker3 = new NonShiftWorker();
        worker3.setDepartment("delivery");
        worker3.setWeeklyWorkingHour(35.0);
        worker3.setHourlyRates(17.0);
        
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        company.addEmployee(worker3);
        
        double result = company.averageWorkingHoursInDeliveryDepartment();
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersDifferentHours() {
        // Test Case 3: Multiple workers in delivery department with different hours
        // Input: Two workers in the Delivery department. One has a weeklyWorkingHour of 20 hours and the other has 30 hours
        NonShiftWorker worker1 = new NonShiftWorker();
        worker1.setDepartment("delivery");
        worker1.setWeeklyWorkingHour(20.0);
        worker1.setHourlyRates(15.0);
        
        NonShiftWorker worker2 = new NonShiftWorker();
        worker2.setDepartment("delivery");
        worker2.setWeeklyWorkingHour(30.0);
        worker2.setHourlyRates(16.0);
        
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        
        double result = company.averageWorkingHoursInDeliveryDepartment();
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentNoWorkers() {
        // Test Case 4: Delivery department with no workers
        // Input: There are zero workers in the Delivery department
        // Add employees from other departments
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setDepartment("sales");
        salesPerson.setSalary(50000.0);
        
        Manager manager = new Manager();
        manager.setDepartment("management");
        manager.setSalary(80000.0);
        
        company.addEmployee(salesPerson);
        company.addEmployee(manager);
        
        double result = company.averageWorkingHoursInDeliveryDepartment();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_mixedDepartments() {
        // Test Case 5: Delivery department mixed with other departments
        // Input: There are 5 employees in total across all departments. Among them, 3 are in the Delivery department with weeklyWorkingHours of 25, 32, and 28 hours respectively
        
        // Delivery department workers
        NonShiftWorker deliveryWorker1 = new NonShiftWorker();
        deliveryWorker1.setDepartment("delivery");
        deliveryWorker1.setWeeklyWorkingHour(25.0);
        deliveryWorker1.setHourlyRates(15.0);
        
        NonShiftWorker deliveryWorker2 = new NonShiftWorker();
        deliveryWorker2.setDepartment("delivery");
        deliveryWorker2.setWeeklyWorkingHour(32.0);
        deliveryWorker2.setHourlyRates(16.0);
        
        NonShiftWorker deliveryWorker3 = new NonShiftWorker();
        deliveryWorker3.setDepartment("delivery");
        deliveryWorker3.setWeeklyWorkingHour(28.0);
        deliveryWorker3.setHourlyRates(17.0);
        
        // Other department employees
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setDepartment("sales");
        salesPerson.setSalary(50000.0);
        
        Manager manager = new Manager();
        manager.setDepartment("management");
        manager.setSalary(80000.0);
        
        company.addEmployee(deliveryWorker1);
        company.addEmployee(deliveryWorker2);
        company.addEmployee(deliveryWorker3);
        company.addEmployee(salesPerson);
        company.addEmployee(manager);
        
        double result = company.averageWorkingHoursInDeliveryDepartment();
        assertEquals(28.33, result, 0.01); // Rounded to two decimal places
    }
}