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
        // Reset company before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_CalculateTotalSalaryForCompanyWithSingleWorker() {
        // Setup: Create a company with 1 Worker
        company = new Company();
        List<Employee> employees = new ArrayList<>();
        
        // Create Worker with specified details
        Worker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        worker.setHourlyRates(20.00);
        
        employees.add(worker);
        company.setEmployees(employees);
        
        // Execute: Calculate total salary
        double result = company.calculateTotalEmployeeSalary();
        
        // Verify: Expected output is 800.00 (40.00 * 20.00)
        assertEquals(800.00, result, 0.001);
    }
    
    @Test
    public void testCase2_CalculateTotalSalaryForCompanyWithSingleSalesPerson() {
        // Setup: Create a company with 1 SalesPerson
        company = new Company();
        List<Employee> employees = new ArrayList<>();
        
        // Create SalesPerson with specified details
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(3000.00);
        salesPerson.setAmountOfSales(2000.00);
        salesPerson.setCommissionPercentage(0.10);
        
        employees.add(salesPerson);
        company.setEmployees(employees);
        
        // Execute: Calculate total salary
        double result = company.calculateTotalEmployeeSalary();
        
        // Verify: Expected output is 3200.00 (3000 + 2000*0.10)
        assertEquals(3200.00, result, 0.001);
    }
    
    @Test
    public void testCase3_CalculateTotalSalaryForCompanyWithSingleManager() {
        // Setup: Create a company with 1 Manager
        company = new Company();
        List<Employee> employees = new ArrayList<>();
        
        // Create Manager with specified details
        Manager manager = new Manager();
        manager.setSalary(5000.00);
        
        employees.add(manager);
        company.setEmployees(employees);
        
        // Execute: Calculate total salary
        double result = company.calculateTotalEmployeeSalary();
        
        // Verify: Expected output is 5000.00
        assertEquals(5000.00, result, 0.001);
    }
    
    @Test
    public void testCase4_CalculateTotalSalaryForCompanyWithWorkerSalesPersonAndManager() {
        // Setup: Create a company with 1 Worker, 1 SalesPerson and 1 Manager
        company = new Company();
        List<Employee> employees = new ArrayList<>();
        
        // Create Worker with specified details
        Worker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(35);
        worker.setHourlyRates(22.00);
        
        // Create SalesPerson with specified details
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(2800.00);
        salesPerson.setAmountOfSales(1500.00);
        salesPerson.setCommissionPercentage(0.15);
        
        // Create Manager with specified details
        Manager manager = new Manager();
        manager.setSalary(4800.00);
        
        employees.add(worker);
        employees.add(salesPerson);
        employees.add(manager);
        company.setEmployees(employees);
        
        // Execute: Calculate total salary
        double result = company.calculateTotalEmployeeSalary();
        
        // Verify: Expected output is 8595.00
        // Worker: 35 * 22.00 = 770.00
        // SalesPerson: 2800 + (1500 * 0.15) = 2800 + 225 = 3025.00
        // Manager: 4800.00
        // Total: 770 + 3025 + 4800 = 8595.00
        assertEquals(8595.00, result, 0.001);
    }
    
    @Test
    public void testCase5_CalculateTotalSalaryForCompanyWithMultipleWorkers() {
        // Setup: Create a company with 2 Workers and 1 shift worker
        company = new Company();
        List<Employee> employees = new ArrayList<>();
        
        // Create first Worker (OffShiftWorker)
        Worker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(45);
        worker1.setHourlyRates(18.00);
        
        // Create second Worker (OffShiftWorker)
        Worker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(38);
        worker2.setHourlyRates(21.00);
        
        // Create third Worker (ShiftWorker)
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setWeeklyWorkingHour(30);
        shiftWorker.setHourlyRates(24.00);
        shiftWorker.setHolidayPremium(200.00);
        
        employees.add(worker1);
        employees.add(worker2);
        employees.add(shiftWorker);
        company.setEmployees(employees);
        
        // Execute: Calculate total salary
        double result = company.calculateTotalEmployeeSalary();
        
        // Verify: Expected output is 2528.00
        // Worker1: 45 * 18.00 = 810.00
        // Worker2: 38 * 21.00 = 798.00
        // ShiftWorker: (30 * 24.00) + 200.00 = 720 + 200 = 920.00
        // Total: 810 + 798 + 920 = 2528.00
        assertEquals(2528.00, result, 0.001);
    }
}