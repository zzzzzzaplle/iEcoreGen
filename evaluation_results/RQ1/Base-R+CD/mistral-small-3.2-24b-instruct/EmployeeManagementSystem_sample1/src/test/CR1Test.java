import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.ArrayList;

public class CR1Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_calculateTotalSalaryForCompanyWithSingleWorker() {
        // Create a worker
        Worker worker = new Worker() {};
        worker.setWeeklyWorkingHour(40);
        worker.setHourlyRates(20.00);
        
        // Add worker to company
        company.getEmployees().add(worker);
        
        // Calculate total salary
        double result = company.calculateTotalEmployeeSalary();
        
        // Verify result
        assertEquals(800.00, result, 0.001);
    }
    
    @Test
    public void testCase2_calculateTotalSalaryForCompanyWithSingleSalesPerson() {
        // Create a sales person
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(3000.00);
        salesPerson.setAmountOfSales(2000.00);
        salesPerson.setCommissionPercentage(0.10);
        
        // Add sales person to company
        company.getEmployees().add(salesPerson);
        
        // Calculate total salary
        double result = company.calculateTotalEmployeeSalary();
        
        // Verify result
        assertEquals(3200.00, result, 0.001);
    }
    
    @Test
    public void testCase3_calculateTotalSalaryForCompanyWithSingleManager() {
        // Create a manager
        Manager manager = new Manager();
        manager.setSalary(5000.00);
        
        // Add manager to company
        company.getEmployees().add(manager);
        
        // Calculate total salary
        double result = company.calculateTotalEmployeeSalary();
        
        // Verify result
        assertEquals(5000.00, result, 0.001);
    }
    
    @Test
    public void testCase4_calculateTotalSalaryForCompanyWithWorkerSalesPersonAndManager() {
        // Create a worker
        Worker worker = new Worker() {};
        worker.setWeeklyWorkingHour(35);
        worker.setHourlyRates(22.00);
        
        // Create a sales person
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(2800.00);
        salesPerson.setAmountOfSales(1500.00);
        salesPerson.setCommissionPercentage(0.15);
        
        // Create a manager
        Manager manager = new Manager();
        manager.setSalary(4800.00);
        
        // Add all employees to company
        company.getEmployees().add(worker);
        company.getEmployees().add(salesPerson);
        company.getEmployees().add(manager);
        
        // Calculate total salary
        double result = company.calculateTotalEmployeeSalary();
        
        // Verify result
        assertEquals(8595.00, result, 0.001); // 770.00 + 3025.00 + 4800.00
    }
    
    @Test
    public void testCase5_calculateTotalSalaryForCompanyWithMultipleWorkers() {
        // Create first worker
        Worker worker1 = new Worker() {};
        worker1.setWeeklyWorkingHour(45);
        worker1.setHourlyRates(18.00);
        
        // Create second worker
        Worker worker2 = new Worker() {};
        worker2.setWeeklyWorkingHour(38);
        worker2.setHourlyRates(21.00);
        
        // Create shift worker
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setWeeklyWorkingHour(30);
        shiftWorker.setHourlyRates(24.00);
        shiftWorker.setHolidayPremium(200.00);
        
        // Add all workers to company
        company.getEmployees().add(worker1);
        company.getEmployees().add(worker2);
        company.getEmployees().add(shiftWorker);
        
        // Calculate total salary
        double result = company.calculateTotalEmployeeSalary();
        
        // Verify result
        assertEquals(2528.00, result, 0.001); // 810.00 + 798.00 + 720.00 + 200.00
    }
}