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
        // Setup: Create a worker with specified details
        OffShiftWorker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        worker.setHourlyRates(20.00);
        
        // Add worker to company
        List<Employee> employees = new ArrayList<>();
        employees.add(worker);
        company.setEmployees(employees);
        
        // Execute: Calculate total salary
        double result = company.calculateTotalEmployeeSalary();
        
        // Verify: Expected result is 800.00
        assertEquals("Total salary for single worker should be 800.00", 800.00, result, 0.001);
    }
    
    @Test
    public void testCase2_CalculateTotalSalaryForCompanyWithSingleSalesPerson() {
        // Setup: Create a sales person with specified details
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(3000.00);
        salesPerson.setAmountOfSales(2000.00);
        salesPerson.setCommissionPercentage(0.10);
        
        // Add sales person to company
        List<Employee> employees = new ArrayList<>();
        employees.add(salesPerson);
        company.setEmployees(employees);
        
        // Execute: Calculate total salary
        double result = company.calculateTotalEmployeeSalary();
        
        // Verify: Expected result is 3200.00 (3000.00 + 2000.00 * 0.10)
        assertEquals("Total salary for single sales person should be 3200.00", 3200.00, result, 0.001);
    }
    
    @Test
    public void testCase3_CalculateTotalSalaryForCompanyWithSingleManager() {
        // Setup: Create a manager with specified details
        Manager manager = new Manager();
        manager.setSalary(5000.00);
        
        // Add manager to company
        List<Employee> employees = new ArrayList<>();
        employees.add(manager);
        company.setEmployees(employees);
        
        // Execute: Calculate total salary
        double result = company.calculateTotalEmployeeSalary();
        
        // Verify: Expected result is 5000.00
        assertEquals("Total salary for single manager should be 5000.00", 5000.00, result, 0.001);
    }
    
    @Test
    public void testCase4_CalculateTotalSalaryForCompanyWithWorkerSalesPersonAndManager() {
        // Setup: Create a worker, sales person, and manager with specified details
        OffShiftWorker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(35);
        worker.setHourlyRates(22.00);
        
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(2800.00);
        salesPerson.setAmountOfSales(1500.00);
        salesPerson.setCommissionPercentage(0.15);
        
        Manager manager = new Manager();
        manager.setSalary(4800.00);
        
        // Add all employees to company
        List<Employee> employees = new ArrayList<>();
        employees.add(worker);
        employees.add(salesPerson);
        employees.add(manager);
        company.setEmployees(employees);
        
        // Execute: Calculate total salary
        double result = company.calculateTotalEmployeeSalary();
        
        // Verify: Expected result is 8595.00 (35*22 + 2800+1500*0.15 + 4800)
        assertEquals("Total salary for mixed employee types should be 8595.00", 8595.00, result, 0.001);
    }
    
    @Test
    public void testCase5_CalculateTotalSalaryForCompanyWithMultipleWorkers() {
        // Setup: Create two regular workers and one shift worker with holiday premium
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(45);
        worker1.setHourlyRates(18.00);
        
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(38);
        worker2.setHourlyRates(21.00);
        
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
        
        // Execute: Calculate total salary
        double result = company.calculateTotalEmployeeSalary();
        
        // Verify: Expected result is 2528.00 (45*18 + 38*21 + 30*24 + 200)
        assertEquals("Total salary for multiple workers should be 2528.00", 2528.00, result, 0.001);
    }
}