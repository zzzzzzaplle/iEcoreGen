import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
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
        worker.setDepartment(Department.DELIVERY);
        worker.setWeeklyWorkingHours(40.0);
        worker.setHourlyRate(15.0);
        
        company.addEmployee(worker);
        
        double result = company.averageWorkingHoursInDelivery();
        assertEquals(40.0, result, 0.001);
    }
    
    @Test
    public void testCase2_multipleWorkersSameHours() {
        // Test Case 2: Multiple workers in delivery department with same hours
        // Input: Three workers in the Delivery department, each with a weeklyWorkingHour of 35 hours
        for (int i = 0; i < 3; i++) {
            NonShiftWorker worker = new NonShiftWorker();
            worker.setDepartment(Department.DELIVERY);
            worker.setWeeklyWorkingHours(35.0);
            worker.setHourlyRate(15.0);
            company.addEmployee(worker);
        }
        
        double result = company.averageWorkingHoursInDelivery();
        assertEquals(35.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleWorkersDifferentHours() {
        // Test Case 3: Multiple workers in delivery department with different hours
        // Input: Two workers in the Delivery department. One has a weeklyWorkingHour of 20 hours and the other has 30 hours
        NonShiftWorker worker1 = new NonShiftWorker();
        worker1.setDepartment(Department.DELIVERY);
        worker1.setWeeklyWorkingHours(20.0);
        worker1.setHourlyRate(15.0);
        
        NonShiftWorker worker2 = new NonShiftWorker();
        worker2.setDepartment(Department.DELIVERY);
        worker2.setWeeklyWorkingHours(30.0);
        worker2.setHourlyRate(15.0);
        
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        
        double result = company.averageWorkingHoursInDelivery();
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    public void testCase4_deliveryDepartmentNoWorkers() {
        // Test Case 4: Delivery department with no workers
        // Input: There are zero workers in the Delivery department
        // Add some employees in other departments to ensure they don't affect the calculation
        NonShiftWorker worker1 = new NonShiftWorker();
        worker1.setDepartment(Department.PRODUCTION);
        worker1.setWeeklyWorkingHours(40.0);
        worker1.setHourlyRate(15.0);
        
        NonShiftWorker worker2 = new NonShiftWorker();
        worker2.setDepartment(Department.CONTROL);
        worker2.setWeeklyWorkingHours(35.0);
        worker2.setHourlyRate(15.0);
        
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        
        double result = company.averageWorkingHoursInDelivery();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase5_mixedDepartmentsWithDeliveryWorkers() {
        // Test Case 5: Delivery department mixed with other departments
        // Input: There are 5 employees in total across all departments. 
        // Among them, 3 are in the Delivery department with weeklyWorkingHours of 25, 32, and 28 hours respectively
        
        // Delivery department workers
        NonShiftWorker deliveryWorker1 = new NonShiftWorker();
        deliveryWorker1.setDepartment(Department.DELIVERY);
        deliveryWorker1.setWeeklyWorkingHours(25.0);
        deliveryWorker1.setHourlyRate(15.0);
        
        NonShiftWorker deliveryWorker2 = new NonShiftWorker();
        deliveryWorker2.setDepartment(Department.DELIVERY);
        deliveryWorker2.setWeeklyWorkingHours(32.0);
        deliveryWorker2.setHourlyRate(15.0);
        
        NonShiftWorker deliveryWorker3 = new NonShiftWorker();
        deliveryWorker3.setDepartment(Department.DELIVERY);
        deliveryWorker3.setWeeklyWorkingHours(28.0);
        deliveryWorker3.setHourlyRate(15.0);
        
        // Other department employees
        NonShiftWorker productionWorker = new NonShiftWorker();
        productionWorker.setDepartment(Department.PRODUCTION);
        productionWorker.setWeeklyWorkingHours(40.0);
        productionWorker.setHourlyRate(15.0);
        
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setDepartment(Department.CONTROL);
        salesPerson.setSalaryBase(2000.0);
        salesPerson.setAmountOfSales(10000.0);
        salesPerson.setCommissionPercentage(0.05);
        
        company.addEmployee(deliveryWorker1);
        company.addEmployee(deliveryWorker2);
        company.addEmployee(deliveryWorker3);
        company.addEmployee(productionWorker);
        company.addEmployee(salesPerson);
        
        double result = company.averageWorkingHoursInDelivery();
        assertEquals(28.33, result, 0.01);
    }
}