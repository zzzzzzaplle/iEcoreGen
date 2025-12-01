import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR1Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_calculateTotalSalaryForCompanyWithSingleWorker() {
        // Setup: Create a company with 1 Worker
        company.setEmployees(new ArrayList<Employee>());
        
        // Create worker with specified details
        OffShiftWorker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        worker.setHourlyRates(20.00);
        
        company.getEmployees().add(worker);
        
        // Execute: Calculate total salary
        double result = company.calculateTotalEmployeeSalary();
        
        // Verify: Check if result matches expected output
        assertEquals(800.00, result, 0.001);
    }
    
    @Test
    public void testCase2_calculateTotalSalaryForCompanyWithSingleSalesPerson() {
        // Setup: Create a company with 1 SalesPerson
        company.setEmployees(new ArrayList<Employee>());
        
        // Create salesperson with specified details
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(3000.00);
        salesPerson.setAmountOfSales(2000.00);
        salesPerson.setCommissionPercentage(0.10);
        
        company.getEmployees().add(salesPerson);
        
        // Execute: Calculate total salary
        double result = company.calculateTotalEmployeeSalary();
        
        // Verify: Check if result matches expected output
        assertEquals(3200.00, result, 0.001);
    }
    
    @Test
    public void testCase3_calculateTotalSalaryForCompanyWithSingleManager() {
        // Setup: Create a company with 1 Manager
        company.setEmployees(new ArrayList<Employee>());
        
        // Create manager with specified details
        Manager manager = new Manager();
        manager.setSalary(5000.00);
        
        company.getEmployees().add(manager);
        
        // Execute: Calculate total salary
        double result = company.calculateTotalEmployeeSalary();
        
        // Verify: Check if result matches expected output
        assertEquals(5000.00, result, 0.001);
    }
    
    @Test
    public void testCase4_calculateTotalSalaryForCompanyWithWorkerSalesPersonAndManager() {
        // Setup: Create a company with 1 Worker, 1 SalesPerson, and 1 Manager
        company.setEmployees(new ArrayList<Employee>());
        
        // Create worker with specified details
        OffShiftWorker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(35);
        worker.setHourlyRates(22.00);
        
        // Create salesperson with specified details
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(2800.00);
        salesPerson.setAmountOfSales(1500.00);
        salesPerson.setCommissionPercentage(0.15);
        
        // Create manager with specified details
        Manager manager = new Manager();
        manager.setSalary(4800.00);
        
        // Add all employees to company
        company.getEmployees().add(worker);
        company.getEmployees().add(salesPerson);
        company.getEmployees().add(manager);
        
        // Execute: Calculate total salary
        double result = company.calculateTotalEmployeeSalary();
        
        // Verify: Check if result matches expected output
        assertEquals(8595.00, result, 0.001);
    }
    
    @Test
    public void testCase5_calculateTotalSalaryForCompanyWithMultipleWorkers() {
        // Setup: Create a company with 3 Workers (2 regular, 1 shift worker)
        company.setEmployees(new ArrayList<Employee>());
        
        // Create first worker with specified details
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(45);
        worker1.setHourlyRates(18.00);
        
        // Create second worker with specified details
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(38);
        worker2.setHourlyRates(21.00);
        
        // Create shift worker with specified details
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setWeeklyWorkingHour(30);
        shiftWorker.setHourlyRates(24.00);
        shiftWorker.setHolidayPremium(200.00);
        
        // Add all workers to company
        company.getEmployees().add(worker1);
        company.getEmployees().add(worker2);
        company.getEmployees().add(shiftWorker);
        
        // Execute: Calculate total salary
        double result = company.calculateTotalEmployeeSalary();
        
        // Verify: Check if result matches expected output
        assertEquals(2528.00, result, 0.001);
    }
}