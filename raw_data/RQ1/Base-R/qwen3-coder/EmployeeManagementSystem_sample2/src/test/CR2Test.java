import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR2Test {
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_singleWorkerInDeliveryDepartment() {
        // Input: One worker in the Delivery department with a weeklyWorkingHour of 40 hours
        Worker worker = new Worker();
        worker.setDepartment("delivery");
        worker.setWeeklyWorkingHours(40.0);
        company.addEmployee(worker);
        
        // Expected Output: The average working hours per week is 40 hours
        double result = company.findAverageWorkingHoursInDelivery();
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersSameHours() {
        // Input: Three workers in the Delivery department, each with a weeklyWorkingHour of 35 hours
        Worker worker1 = new Worker();
        worker1.setDepartment("delivery");
        worker1.setWeeklyWorkingHours(35.0);
        
        Worker worker2 = new Worker();
        worker2.setDepartment("delivery");
        worker2.setWeeklyWorkingHours(35.0);
        
        Worker worker3 = new Worker();
        worker3.setDepartment("delivery");
        worker3.setWeeklyWorkingHours(35.0);
        
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        company.addEmployee(worker3);
        
        // Expected Output: The average working hours per week is 35 hours
        double result = company.findAverageWorkingHoursInDelivery();
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersDifferentHours() {
        // Input: Two workers in the Delivery department. One has a weeklyWorkingHour of 20 hours and the other has 30 hours
        Worker worker1 = new Worker();
        worker1.setDepartment("delivery");
        worker1.setWeeklyWorkingHours(20.0);
        
        Worker worker2 = new Worker();
        worker2.setDepartment("delivery");
        worker2.setWeeklyWorkingHours(30.0);
        
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        
        // Expected Output: The average working hours per week is 25 hours
        double result = company.findAverageWorkingHoursInDelivery();
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_noWorkersInDeliveryDepartment() {
        // Input: There are zero workers in the Delivery department
        // No workers added to delivery department
        
        // Expected Output: The average working hours per week is 0 hours (as there are no workers)
        double result = company.findAverageWorkingHoursInDelivery();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_deliveryMixedWithOtherDepartments() {
        // Input: There are 5 employees in total across all departments. Among them, 3 are in the Delivery department with weeklyWorkingHours of 25, 32, and 28 hours respectively
        
        // Delivery department workers
        Worker deliveryWorker1 = new Worker();
        deliveryWorker1.setDepartment("delivery");
        deliveryWorker1.setWeeklyWorkingHours(25.0);
        
        Worker deliveryWorker2 = new Worker();
        deliveryWorker2.setDepartment("delivery");
        deliveryWorker2.setWeeklyWorkingHours(32.0);
        
        Worker deliveryWorker3 = new Worker();
        deliveryWorker3.setDepartment("delivery");
        deliveryWorker3.setWeeklyWorkingHours(28.0);
        
        // Other department employees
        Worker otherWorker = new Worker();
        otherWorker.setDepartment("production");
        otherWorker.setWeeklyWorkingHours(40.0);
        
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setDepartment("sales");
        
        company.addEmployee(deliveryWorker1);
        company.addEmployee(deliveryWorker2);
        company.addEmployee(deliveryWorker3);
        company.addEmployee(otherWorker);
        company.addEmployee(salesPerson);
        
        // Expected Output: The average working hours per week for workers in the Delivery department is 28.33 (rounded to two decimal places) hours
        double result = company.findAverageWorkingHoursInDelivery();
        assertEquals(28.33, result, 0.01);
    }
}