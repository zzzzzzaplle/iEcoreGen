import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_CalculateTotalSalaryForCompanyWithSingleWorker() {
        // Create a worker with specified details
        Worker worker = new Worker();
        worker.setWeeklyWorkingHours(40.0);
        worker.setHourlyRate(20.0);
        
        // Add worker to company
        company.getEmployees().add(worker);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalSalary();
        
        // Verify expected result: 40 * 20 = 800.00
        assertEquals(800.00, totalSalary, 0.01);
    }
    
    @Test
    public void testCase2_CalculateTotalSalaryForCompanyWithSingleSalesPerson() {
        // Create a sales person with specified details
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setFixedSalary(3000.00);
        salesPerson.setAmountOfSales(2000.00);
        salesPerson.setCommissionPercentage(0.10);
        
        // Add sales person to company
        company.getEmployees().add(salesPerson);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalSalary();
        
        // Verify expected result: 3000 + (2000 * 0.10) = 3200.00
        assertEquals(3200.00, totalSalary, 0.01);
    }
    
    @Test
    public void testCase3_CalculateTotalSalaryForCompanyWithSingleManager() {
        // Create a manager with specified details
        Manager manager = new Manager();
        manager.setFixedSalary(5000.00);
        
        // Add manager to company
        company.getEmployees().add(manager);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalSalary();
        
        // Verify expected result: 5000.00
        assertEquals(5000.00, totalSalary, 0.01);
    }
    
    @Test
    public void testCase4_CalculateTotalSalaryForCompanyWithWorkerSalesPersonAndManager() {
        // Create a worker with specified details
        Worker worker = new Worker();
        worker.setWeeklyWorkingHours(35.0);
        worker.setHourlyRate(22.0);
        
        // Create a sales person with specified details
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setFixedSalary(2800.00);
        salesPerson.setAmountOfSales(1500.00);
        salesPerson.setCommissionPercentage(0.15);
        
        // Create a manager with specified details
        Manager manager = new Manager();
        manager.setFixedSalary(4800.00);
        
        // Add all employees to company
        company.getEmployees().add(worker);
        company.getEmployees().add(salesPerson);
        company.getEmployees().add(manager);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalSalary();
        
        // Verify expected result: 
        // Worker: 35 * 22 = 770
        // SalesPerson: 2800 + (1500 * 0.15) = 3025
        // Manager: 4800
        // Total: 770 + 3025 + 4800 = 8595.00
        assertEquals(8595.00, totalSalary, 0.01);
    }
    
    @Test
    public void testCase5_CalculateTotalSalaryForCompanyWithMultipleWorkers() {
        // Create first worker with specified details
        Worker worker1 = new Worker();
        worker1.setWeeklyWorkingHours(45.0);
        worker1.setHourlyRate(18.0);
        
        // Create second worker with specified details
        Worker worker2 = new Worker();
        worker2.setWeeklyWorkingHours(38.0);
        worker2.setHourlyRate(21.0);
        
        // Create shift worker with specified details
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setWeeklyWorkingHours(30.0);
        shiftWorker.setHourlyRate(24.0);
        shiftWorker.setHolidayPremium(200.0);
        
        // Add all workers to company
        company.getEmployees().add(worker1);
        company.getEmployees().add(worker2);
        company.getEmployees().add(shiftWorker);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalSalary();
        
        // Verify expected result:
        // Worker1: 45 * 18 = 810
        // Worker2: 38 * 21 = 798
        // ShiftWorker: (30 * 24) + 200 = 720 + 200 = 920
        // Total: 810 + 798 + 920 = 2528.00
        assertEquals(2528.00, totalSalary, 0.01);
    }
}