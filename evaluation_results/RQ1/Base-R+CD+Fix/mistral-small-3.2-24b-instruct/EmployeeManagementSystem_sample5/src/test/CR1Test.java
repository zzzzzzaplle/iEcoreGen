import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR1Test {
    
    private Company company;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        company = new Company();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CalculateTotalSalaryForCompanyWithSingleWorker() throws ParseException {
        // Setup: Create a company with 1 Worker
        Worker worker = new OffShiftWorker("Production", "John Doe", 
                                         dateFormat.parse("1980-05-15 00:00:00"), 
                                         "123-45-6789", 40, 20.00);
        company.addEmployee(worker);
        
        // Execute: Calculate total salary
        double actualTotalSalary = company.calculateTotalEmployeeSalary();
        
        // Verify: The total salary should be 800.00 (40.00 * 20.00)
        assertEquals(800.00, actualTotalSalary, 0.001);
    }
    
    @Test
    public void testCase2_CalculateTotalSalaryForCompanyWithSingleSalesPerson() throws ParseException {
        // Setup: Create a company with 1 SalesPerson
        SalesPeople salesPerson = new SalesPeople("Sales", "Jane Smith", 
                                                dateFormat.parse("1985-08-20 00:00:00"), 
                                                "987-65-4321", 3000.00, 2000.00, 0.10);
        company.addEmployee(salesPerson);
        
        // Execute: Calculate total salary
        double actualTotalSalary = company.calculateTotalEmployeeSalary();
        
        // Verify: The total salary should be 3200.00 (3000 + 2000 * 0.10 = 3000 + 200 = 3200)
        assertEquals(3200.00, actualTotalSalary, 0.001);
    }
    
    @Test
    public void testCase3_CalculateTotalSalaryForCompanyWithSingleManager() throws ParseException {
        // Setup: Create a company with 1 Manager
        Manager manager = new Manager("Management", "Bob Johnson", 
                                    dateFormat.parse("1975-12-10 00:00:00"), 
                                    "456-78-9012", 5000.00, "Senior Manager");
        company.addEmployee(manager);
        
        // Execute: Calculate total salary
        double actualTotalSalary = company.calculateTotalEmployeeSalary();
        
        // Verify: The total salary should be 5000.00
        assertEquals(5000.00, actualTotalSalary, 0.001);
    }
    
    @Test
    public void testCase4_CalculateTotalSalaryForCompanyWithWorkerSalesPersonAndManager() throws ParseException {
        // Setup: Create a company with 1 Worker, 1 SalesPerson and 1 Manager
        Worker worker = new OffShiftWorker("Production", "Worker1", 
                                         dateFormat.parse("1982-03-25 00:00:00"), 
                                         "111-11-1111", 35, 22.00);
        SalesPeople salesPerson = new SalesPeople("Sales", "SalesPerson1", 
                                                dateFormat.parse("1988-07-15 00:00:00"), 
                                                "222-22-2222", 2800.00, 1500.00, 0.15);
        Manager manager = new Manager("Management", "Manager1", 
                                    dateFormat.parse("1978-11-05 00:00:00"), 
                                    "333-33-3333", 4800.00, "Manager");
        
        company.addEmployee(worker);
        company.addEmployee(salesPerson);
        company.addEmployee(manager);
        
        // Execute: Calculate total salary
        double actualTotalSalary = company.calculateTotalEmployeeSalary();
        
        // Verify: The total salary should be 8595.00
        // Worker: 35 * 22.00 = 770.00
        // SalesPerson: 2800 + (1500 * 0.15) = 2800 + 225 = 3025.00
        // Manager: 4800.00
        // Total: 770 + 3025 + 4800 = 8595.00
        assertEquals(8595.00, actualTotalSalary, 0.001);
    }
    
    @Test
    public void testCase5_CalculateTotalSalaryForCompanyWithMultipleWorkers() throws ParseException {
        // Setup: Create a company with 3 Workers (2 regular workers, 1 shift worker)
        Worker worker1 = new OffShiftWorker("Production", "Worker1", 
                                          dateFormat.parse("1983-04-18 00:00:00"), 
                                          "444-44-4444", 45, 18.00);
        Worker worker2 = new OffShiftWorker("Production", "Worker2", 
                                          dateFormat.parse("1986-09-12 00:00:00"), 
                                          "555-55-5555", 38, 21.00);
        ShiftWorker shiftWorker = new ShiftWorker("Production", "ShiftWorker1", 
                                               dateFormat.parse("1981-06-30 00:00:00"), 
                                               "666-66-6666", 30, 24.00, 200.00);
        
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        company.addEmployee(shiftWorker);
        
        // Execute: Calculate total salary
        double actualTotalSalary = company.calculateTotalEmployeeSalary();
        
        // Verify: The total salary should be 2528.00
        // Worker1: 45 * 18.00 = 810.00
        // Worker2: 38 * 21.00 = 798.00
        // ShiftWorker: (30 * 24.00) + 200.00 = 720 + 200 = 920.00
        // Total: 810 + 798 + 920 = 2528.00
        assertEquals(2528.00, actualTotalSalary, 0.001);
    }
}