import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR1Test {
    
    private Company company;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        company = new Company();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CalculateTotalSalaryForCompanyWithSingleWorker() throws Exception {
        // Input: A Company with 1 Worker. Worker details: weeklyWorkingHour = 40, hourlyRates = 20.00
        Date birthDate = dateFormat.parse("1990-01-01 00:00:00");
        OffShiftWorker worker = new OffShiftWorker("PRODUCTION", "John Doe", birthDate, "123-45-6789", 40, 20.00);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(worker);
        company.setEmployees(employees);
        
        // Expected Output: The total salary of all employees in the company is 800.00 (40.00 * 20.00)
        double expectedTotalSalary = 800.00;
        double actualTotalSalary = company.calculateTotalEmployeeSalary();
        
        assertEquals("Total salary should be 800.00 for single worker", expectedTotalSalary, actualTotalSalary, 0.001);
    }
    
    @Test
    public void testCase2_CalculateTotalSalaryForCompanyWithSingleSalesPerson() throws Exception {
        // Input: A Company with 1 SalesPerson. SalesPerson details: salary = 3000.00, amountOfSales = 2000.00, commissionPercentage = 0.10
        Date birthDate = dateFormat.parse("1990-01-01 00:00:00");
        SalesPeople salesPerson = new SalesPeople("DELIVERY", "Jane Smith", birthDate, "987-65-4321", 3000.00, 2000.00, 0.10);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(salesPerson);
        company.setEmployees(employees);
        
        // Expected Output: The total salary of all employees in the company is 3200.00
        double expectedTotalSalary = 3200.00;
        double actualTotalSalary = company.calculateTotalEmployeeSalary();
        
        assertEquals("Total salary should be 3200.00 for single sales person", expectedTotalSalary, actualTotalSalary, 0.001);
    }
    
    @Test
    public void testCase3_CalculateTotalSalaryForCompanyWithSingleManager() throws Exception {
        // Input: A Company with 1 Manager. Manager details: salary = 5000.00
        Date birthDate = dateFormat.parse("1990-01-01 00:00:00");
        Manager manager = new Manager("CONTROL", "Bob Johnson", birthDate, "456-78-9012", 5000.00, "Senior Manager");
        
        List<Employee> employees = new ArrayList<>();
        employees.add(manager);
        company.setEmployees(employees);
        
        // Expected Output: The total salary of all employees in the company is 5000.00
        double expectedTotalSalary = 5000.00;
        double actualTotalSalary = company.calculateTotalEmployeeSalary();
        
        assertEquals("Total salary should be 5000.00 for single manager", expectedTotalSalary, actualTotalSalary, 0.001);
    }
    
    @Test
    public void testCase4_CalculateTotalSalaryForCompanyWithWorkerSalesPersonAndManager() throws Exception {
        // Input: A Company with 1 Worker, 1 SalesPerson and 1 Manager. 
        // Worker details: weeklyWorkingHour = 35, hourlyRates = 22.00. 
        // SalesPerson details: salary = 2800.00, amountOfSales = 1500.00, commissionPercentage = 0.15. 
        // Manager details: salary = 4800.00
        
        Date birthDate = dateFormat.parse("1990-01-01 00:00:00");
        
        OffShiftWorker worker = new OffShiftWorker("PRODUCTION", "Worker1", birthDate, "111-11-1111", 35, 22.00);
        SalesPeople salesPerson = new SalesPeople("DELIVERY", "SalesPerson1", birthDate, "222-22-2222", 2800.00, 1500.00, 0.15);
        Manager manager = new Manager("CONTROL", "Manager1", birthDate, "333-33-3333", 4800.00, "Manager");
        
        List<Employee> employees = new ArrayList<>();
        employees.add(worker);
        employees.add(salesPerson);
        employees.add(manager);
        company.setEmployees(employees);
        
        // Expected Output: The total salary of all employees in the company is 8595.00
        double expectedTotalSalary = 8595.00;
        double actualTotalSalary = company.calculateTotalEmployeeSalary();
        
        assertEquals("Total salary should be 8595.00 for mixed employee types", expectedTotalSalary, actualTotalSalary, 0.001);
    }
    
    @Test
    public void testCase5_CalculateTotalSalaryForCompanyWithMultipleWorkers() throws Exception {
        // Input: A Company with 2 Workers, 1 shift worker. 
        // First Worker details: weeklyWorkingHour = 45, hourlyRates = 18.00. 
        // Second Worker details: weeklyWorkingHour = 38, hourlyRates = 21.00. 
        // Third shift worker details: weeklyWorkingHour = 30, hourlyRates = 24.00, holiday premiums = 200.00
        
        Date birthDate = dateFormat.parse("1990-01-01 00:00:00");
        
        OffShiftWorker worker1 = new OffShiftWorker("PRODUCTION", "Worker1", birthDate, "111-11-1111", 45, 18.00);
        OffShiftWorker worker2 = new OffShiftWorker("PRODUCTION", "Worker2", birthDate, "222-22-2222", 38, 21.00);
        ShiftWorker shiftWorker = new ShiftWorker("PRODUCTION", "ShiftWorker1", birthDate, "333-33-3333", 30, 24.00, 200.00);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(worker1);
        employees.add(worker2);
        employees.add(shiftWorker);
        company.setEmployees(employees);
        
        // Expected Output: The total salary of all employees in the company is 2528.00
        double expectedTotalSalary = 2528.00;
        double actualTotalSalary = company.calculateTotalEmployeeSalary();
        
        assertEquals("Total salary should be 2528.00 for multiple workers including shift worker", expectedTotalSalary, actualTotalSalary, 0.001);
    }
}