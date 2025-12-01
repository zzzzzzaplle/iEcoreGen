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
        // Arrange: Create a company with 1 Worker
        Worker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        worker.setHourlyRates(20.00);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(worker);
        company.setEmployees(employees);
        
        // Act: Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Assert: The total salary should be 800.00 (40 * 20.00)
        assertEquals(800.00, totalSalary, 0.01);
    }
    
    @Test
    public void testCase2_calculateTotalSalaryForCompanyWithSingleSalesPerson() {
        // Arrange: Create a company with 1 SalesPerson
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(3000.00);
        salesPerson.setAmountOfSales(2000.00);
        salesPerson.setCommissionPercentage(0.10);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(salesPerson);
        company.setEmployees(employees);
        
        // Act: Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Assert: The total salary should be 3200.00 (3000.00 + 2000.00 * 0.10)
        assertEquals(3200.00, totalSalary, 0.01);
    }
    
    @Test
    public void testCase3_calculateTotalSalaryForCompanyWithSingleManager() {
        // Arrange: Create a company with 1 Manager
        Manager manager = new Manager();
        manager.setSalary(5000.00);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(manager);
        company.setEmployees(employees);
        
        // Act: Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Assert: The total salary should be 5000.00
        assertEquals(5000.00, totalSalary, 0.01);
    }
    
    @Test
    public void testCase4_calculateTotalSalaryForCompanyWithWorkerSalesPersonAndManager() {
        // Arrange: Create a company with 1 Worker, 1 SalesPerson and 1 Manager
        Worker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(35);
        worker.setHourlyRates(22.00);
        
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(2800.00);
        salesPerson.setAmountOfSales(1500.00);
        salesPerson.setCommissionPercentage(0.15);
        
        Manager manager = new Manager();
        manager.setSalary(4800.00);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(worker);
        employees.add(salesPerson);
        employees.add(manager);
        company.setEmployees(employees);
        
        // Act: Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Assert: The total salary should be 8595.00
        // Worker: 35 * 22.00 = 770.00
        // SalesPerson: 2800.00 + (1500.00 * 0.15) = 2800.00 + 225.00 = 3025.00
        // Manager: 4800.00
        // Total: 770.00 + 3025.00 + 4800.00 = 8595.00
        assertEquals(8595.00, totalSalary, 0.01);
    }
    
    @Test
    public void testCase5_calculateTotalSalaryForCompanyWithMultipleWorkers() {
        // Arrange: Create a company with 2 Workers, 1 shift worker
        Worker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(45);
        worker1.setHourlyRates(18.00);
        
        Worker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(38);
        worker2.setHourlyRates(21.00);
        
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setWeeklyWorkingHour(30);
        shiftWorker.setHourlyRates(24.00);
        shiftWorker.setHolidayPremium(200.00);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(worker1);
        employees.add(worker2);
        employees.add(shiftWorker);
        company.setEmployees(employees);
        
        // Act: Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Assert: The total salary should be 2528.00
        // First Worker: 45 * 18.00 = 810.00
        // Second Worker: 38 * 21.00 = 798.00
        // Shift Worker: (30 * 24.00) + 200.00 = 720.00 + 200.00 = 920.00
        // Total: 810.00 + 798.00 + 920.00 = 2528.00
        assertEquals(2528.00, totalSalary, 0.01);
    }
}