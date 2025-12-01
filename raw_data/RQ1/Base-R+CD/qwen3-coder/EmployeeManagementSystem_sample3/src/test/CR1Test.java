import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CR1Test {
    
    private Company company;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        company = new Company();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_calculateTotalSalaryForCompanyWithSingleWorker() {
        // Create a worker with specified details
        Worker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        worker.setHourlyRates(20.00);
        
        // Add worker to company
        List<Employee> employees = new ArrayList<>();
        employees.add(worker);
        company.setEmployees(employees);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Assert the expected result
        assertEquals(800.00, totalSalary, 0.01);
    }
    
    @Test
    public void testCase2_calculateTotalSalaryForCompanyWithSingleSalesPerson() {
        // Create a salesperson with specified details
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(3000.00);
        salesPerson.setAmountOfSales(2000.00);
        salesPerson.setCommissionPercentage(0.10);
        
        // Add salesperson to company
        List<Employee> employees = new ArrayList<>();
        employees.add(salesPerson);
        company.setEmployees(employees);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Assert the expected result
        assertEquals(3200.00, totalSalary, 0.01);
    }
    
    @Test
    public void testCase3_calculateTotalSalaryForCompanyWithSingleManager() {
        // Create a manager with specified details
        Manager manager = new Manager();
        manager.setSalary(5000.00);
        
        // Add manager to company
        List<Employee> employees = new ArrayList<>();
        employees.add(manager);
        company.setEmployees(employees);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Assert the expected result
        assertEquals(5000.00, totalSalary, 0.01);
    }
    
    @Test
    public void testCase4_calculateTotalSalaryForCompanyWithWorkerSalesPersonAndManager() {
        // Create a worker with specified details
        Worker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(35);
        worker.setHourlyRates(22.00);
        
        // Create a salesperson with specified details
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(2800.00);
        salesPerson.setAmountOfSales(1500.00);
        salesPerson.setCommissionPercentage(0.15);
        
        // Create a manager with specified details
        Manager manager = new Manager();
        manager.setSalary(4800.00);
        
        // Add all employees to company
        List<Employee> employees = new ArrayList<>();
        employees.add(worker);
        employees.add(salesPerson);
        employees.add(manager);
        company.setEmployees(employees);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Assert the expected result
        assertEquals(8595.00, totalSalary, 0.01);
    }
    
    @Test
    public void testCase5_calculateTotalSalaryForCompanyWithMultipleWorkers() {
        // Create first worker with specified details
        Worker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(45);
        worker1.setHourlyRates(18.00);
        
        // Create second worker with specified details
        Worker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(38);
        worker2.setHourlyRates(21.00);
        
        // Create shift worker with specified details
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
        
        // Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Worker1 salary: 45 * 18.00 = 810.00
        // Worker2 salary: 38 * 21.00 = 798.00
        // ShiftWorker salary: 30 * 24.00 + 200.00 = 920.00
        // Total: 810.00 + 798.00 + 920.00 = 2528.00
        
        // Assert the expected result
        assertEquals(2528.00, totalSalary, 0.01);
    }
}