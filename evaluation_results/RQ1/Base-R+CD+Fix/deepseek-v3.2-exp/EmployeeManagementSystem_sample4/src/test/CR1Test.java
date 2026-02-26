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
        // Input: A Company with 1 Worker. Worker details: weeklyWorkingHour = 40, hourlyRates = 20.00
        OffShiftWorker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        worker.setHourlyRates(20.00);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(worker);
        company.setEmployees(employees);
        
        // Expected Output: The total salary of all employees in the company is 800.00 (40.00 * 20.00)
        double expectedTotalSalary = 800.00;
        double actualTotalSalary = company.calculateTotalEmployeeSalary();
        
        assertEquals("Total salary should be 800.00 for single worker", 
                     expectedTotalSalary, actualTotalSalary, 0.001);
    }
    
    @Test
    public void testCase2_CalculateTotalSalaryForCompanyWithSingleSalesPerson() {
        // Input: A Company with 1 SalesPerson. SalesPerson details: salary = 3000.00, amountOfSales = 2000.00, commissionPercentage = 0.10
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(3000.00);
        salesPerson.setAmountOfSales(2000.00);
        salesPerson.setCommissionPercentage(0.10);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(salesPerson);
        company.setEmployees(employees);
        
        // Expected Output: The total salary of all employees in the company is 3200.00
        double expectedTotalSalary = 3200.00;
        double actualTotalSalary = company.calculateTotalEmployeeSalary();
        
        assertEquals("Total salary should be 3200.00 for single sales person", 
                     expectedTotalSalary, actualTotalSalary, 0.001);
    }
    
    @Test
    public void testCase3_CalculateTotalSalaryForCompanyWithSingleManager() {
        // Input: A Company with 1 Manager. Manager details: salary = 5000.00
        Manager manager = new Manager();
        manager.setSalary(5000.00);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(manager);
        company.setEmployees(employees);
        
        // Expected Output: The total salary of all employees in the company is 5000.00
        double expectedTotalSalary = 5000.00;
        double actualTotalSalary = company.calculateTotalEmployeeSalary();
        
        assertEquals("Total salary should be 5000.00 for single manager", 
                     expectedTotalSalary, actualTotalSalary, 0.001);
    }
    
    @Test
    public void testCase4_CalculateTotalSalaryForCompanyWithWorkerSalesPersonAndManager() {
        // Input: A Company with 1 Worker, 1 SalesPerson and 1 Manager
        // Worker details: weeklyWorkingHour = 35, hourlyRates = 22.00
        OffShiftWorker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(35);
        worker.setHourlyRates(22.00);
        
        // SalesPerson details: salary = 2800.00, amountOfSales = 1500.00, commissionPercentage = 0.15
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(2800.00);
        salesPerson.setAmountOfSales(1500.00);
        salesPerson.setCommissionPercentage(0.15);
        
        // Manager details: salary = 4800.00
        Manager manager = new Manager();
        manager.setSalary(4800.00);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(worker);
        employees.add(salesPerson);
        employees.add(manager);
        company.setEmployees(employees);
        
        // Expected Output: The total salary of all employees in the company is 8595.00
        double expectedTotalSalary = 8595.00;
        double actualTotalSalary = company.calculateTotalEmployeeSalary();
        
        assertEquals("Total salary should be 8595.00 for worker, sales person and manager", 
                     expectedTotalSalary, actualTotalSalary, 0.001);
    }
    
    @Test
    public void testCase5_CalculateTotalSalaryForCompanyWithMultipleWorkers() {
        // Input: A Company with 2 Workers, 1 shift worker
        // First Worker details: weeklyWorkingHour = 45, hourlyRates = 18.00
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(45);
        worker1.setHourlyRates(18.00);
        
        // Second Worker details: weeklyWorkingHour = 38, hourlyRates = 21.00
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(38);
        worker2.setHourlyRates(21.00);
        
        // Third shift worker details: weeklyWorkingHour = 30, hourlyRates = 24.00, holiday premiums = 200.00
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setWeeklyWorkingHour(30);
        shiftWorker.setHourlyRates(24.00);
        shiftWorker.setHolidayPremium(200.00);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(worker1);
        employees.add(worker2);
        employees.add(shiftWorker);
        company.setEmployees(employees);
        
        // Expected Output: The total salary of all employees in the company is 2528.00
        double expectedTotalSalary = 2528.00;
        double actualTotalSalary = company.calculateTotalEmployeeSalary();
        
        assertEquals("Total salary should be 2528.00 for multiple workers including shift worker", 
                     expectedTotalSalary, actualTotalSalary, 0.001);
    }
}