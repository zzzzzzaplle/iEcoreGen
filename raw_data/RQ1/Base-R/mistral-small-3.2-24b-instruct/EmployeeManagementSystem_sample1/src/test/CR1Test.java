import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
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
        Worker worker = new Worker("IT", "John Doe", LocalDate.of(1990, 1, 1), "123-45-6789", 40, 20.00, false);
        company.addEmployee(worker);
        
        // Expected Output: The total salary of all employees in the company is 800.00 (40.00 * 20.00)
        double expectedSalary = 800.00;
        double actualSalary = company.calculateTotalSalary();
        
        assertEquals("Total salary should be 800.00 for single worker", expectedSalary, actualSalary, 0.001);
    }
    
    @Test
    public void testCase2_CalculateTotalSalaryForCompanyWithSingleSalesPerson() {
        // Input: A Company with 1 SalesPerson. SalesPerson details: salary = 3000.00, amountOfSales = 2000.00, commissionPercentage = 0.10
        Salesperson salesperson = new Salesperson("Sales", "Jane Smith", LocalDate.of(1985, 5, 15), "987-65-4321", 3000.00, 2000.00, 0.10);
        company.addEmployee(salesperson);
        
        // Expected Output: The total salary of all employees in the company is 3200.00
        double expectedSalary = 3200.00;
        double actualSalary = company.calculateTotalSalary();
        
        assertEquals("Total salary should be 3200.00 for single salesperson", expectedSalary, actualSalary, 0.001);
    }
    
    @Test
    public void testCase3_CalculateTotalSalaryForCompanyWithSingleManager() {
        // Input: A Company with 1 Manager. Manager details: salary = 5000.00
        Manager manager = new Manager("Management", "Bob Johnson", LocalDate.of(1975, 8, 20), "456-78-9012", "Senior Manager");
        company.addEmployee(manager);
        
        // Expected Output: The total salary of all employees in the company is 5000.00
        double expectedSalary = 5000.00;
        double actualSalary = company.calculateTotalSalary();
        
        assertEquals("Total salary should be 5000.00 for single manager", expectedSalary, actualSalary, 0.001);
    }
    
    @Test
    public void testCase4_CalculateTotalSalaryForCompanyWithWorkerSalesPersonAndManager() {
        // Input: A Company with 1 Worker, 1 SalesPerson and 1 Manager
        Worker worker = new Worker("IT", "John Doe", LocalDate.of(1990, 1, 1), "123-45-6789", 35, 22.00, false);
        Salesperson salesperson = new Salesperson("Sales", "Jane Smith", LocalDate.of(1985, 5, 15), "987-65-4321", 2800.00, 1500.00, 0.15);
        Manager manager = new Manager("Management", "Bob Johnson", LocalDate.of(1975, 8, 20), "456-78-9012", "Senior Manager");
        
        company.addEmployee(worker);
        company.addEmployee(salesperson);
        company.addEmployee(manager);
        
        // Expected Output: The total salary of all employees in the company is 8595.00
        double expectedSalary = 8595.00;
        double actualSalary = company.calculateTotalSalary();
        
        assertEquals("Total salary should be 8595.00 for mixed employee types", expectedSalary, actualSalary, 0.001);
    }
    
    @Test
    public void testCase5_CalculateTotalSalaryForCompanyWithMultipleWorkers() {
        // Input: A Company with 2 Workers, 1 shift worker
        Worker worker1 = new Worker("IT", "John Doe", LocalDate.of(1990, 1, 1), "123-45-6789", 45, 18.00, false);
        Worker worker2 = new Worker("HR", "Alice Brown", LocalDate.of(1988, 3, 12), "234-56-7890", 38, 21.00, false);
        Worker shiftWorker = new Worker("Delivery", "Charlie Wilson", LocalDate.of(1992, 7, 8), "345-67-8901", 30, 24.00, true);
        
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        company.addEmployee(shiftWorker);
        
        // Expected Output: The total salary of all employees in the company is 2528.00
        double expectedSalary = 2528.00;
        double actualSalary = company.calculateTotalSalary();
        
        assertEquals("Total salary should be 2528.00 for multiple workers including shift worker", expectedSalary, actualSalary, 0.001);
    }
}