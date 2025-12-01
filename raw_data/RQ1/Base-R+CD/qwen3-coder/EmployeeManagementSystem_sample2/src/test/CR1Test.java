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
    public void testCase1_singleWorker() {
        // Create a worker with weeklyWorkingHour = 40, hourlyRates = 20.00
        Worker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        worker.setHourlyRates(20.00);
        
        // Add worker to company
        List<Employee> employees = new ArrayList<>();
        employees.add(worker);
        company.setEmployees(employees);
        
        // Calculate total salary - should be 800.00 (40 * 20.00)
        double totalSalary = company.calculateTotalEmployeeSalary();
        assertEquals(800.00, totalSalary, 0.01);
    }
    
    @Test
    public void testCase2_singleSalesPerson() {
        // Create a sales person with salary = 3000.00, amountOfSales = 2000.00, commissionPercentage = 0.10
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(3000.00);
        salesPerson.setAmountOfSales(2000.00);
        salesPerson.setCommissionPercentage(0.10);
        
        // Add sales person to company
        List<Employee> employees = new ArrayList<>();
        employees.add(salesPerson);
        company.setEmployees(employees);
        
        // Calculate total salary - should be 3200.00 (3000.00 + 2000.00 * 0.10)
        double totalSalary = company.calculateTotalEmployeeSalary();
        assertEquals(3200.00, totalSalary, 0.01);
    }
    
    @Test
    public void testCase3_singleManager() {
        // Create a manager with salary = 5000.00
        Manager manager = new Manager();
        manager.setSalary(5000.00);
        
        // Add manager to company
        List<Employee> employees = new ArrayList<>();
        employees.add(manager);
        company.setEmployees(employees);
        
        // Calculate total salary - should be 5000.00
        double totalSalary = company.calculateTotalEmployeeSalary();
        assertEquals(5000.00, totalSalary, 0.01);
    }
    
    @Test
    public void testCase4_workerSalesPersonAndManager() {
        // Create a worker with weeklyWorkingHour = 35, hourlyRates = 22.00
        Worker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(35);
        worker.setHourlyRates(22.00);
        
        // Create a sales person with salary = 2800.00, amountOfSales = 1500.00, commissionPercentage = 0.15
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(2800.00);
        salesPerson.setAmountOfSales(1500.00);
        salesPerson.setCommissionPercentage(0.15);
        
        // Create a manager with salary = 4800.00
        Manager manager = new Manager();
        manager.setSalary(4800.00);
        
        // Add all employees to company
        List<Employee> employees = new ArrayList<>();
        employees.add(worker);
        employees.add(salesPerson);
        employees.add(manager);
        company.setEmployees(employees);
        
        // Calculate total salary - should be 8595.00
        // Worker: 35 * 22.00 = 770.00
        // SalesPerson: 2800.00 + (1500.00 * 0.15) = 2800.00 + 225.00 = 3025.00
        // Manager: 4800.00
        // Total: 770.00 + 3025.00 + 4800.00 = 8595.00
        double totalSalary = company.calculateTotalEmployeeSalary();
        assertEquals(8595.00, totalSalary, 0.01);
    }
    
    @Test
    public void testCase5_multipleWorkers() {
        // Create first worker with weeklyWorkingHour = 45, hourlyRates = 18.00
        Worker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(45);
        worker1.setHourlyRates(18.00);
        
        // Create second worker with weeklyWorkingHour = 38, hourlyRates = 21.00
        Worker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(38);
        worker2.setHourlyRates(21.00);
        
        // Create third shift worker with weeklyWorkingHour = 30, hourlyRates = 24.00, holidayPremium = 200.00
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setWeeklyWorkingHour(30);
        shiftWorker.setHourlyRates(24.00);
        shiftWorker.setHolidayPremium(200.00);
        
        // Add all workers to company
        List<Employee> employees = new ArrayList<>();
        employees.add(worker1);
        employees.add(worker2);
        employees.add(shiftWorker);
        company.setEmployees(employees);
        
        // Calculate total salary - should be 2528.00
        // First worker: 45 * 18.00 = 810.00
        // Second worker: 38 * 21.00 = 798.00
        // Shift worker: (30 * 24.00) + 200.00 = 720.00 + 200.00 = 920.00
        // Total: 810.00 + 798.00 + 920.00 = 2528.00
        double totalSalary = company.calculateTotalEmployeeSalary();
        assertEquals(2528.00, totalSalary, 0.01);
    }
}