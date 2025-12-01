import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

public class CR1Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_CalculateTotalSalaryForCompanyWithSingleWorker() throws Exception {
        // Setup: Create company with 1 Worker
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date birthDate = sdf.parse("1990-01-01 00:00:00");
        
        Worker worker = new OffShiftWorker("PRODUCTION", "John Doe", birthDate, 
                                         "123-45-6789", 40, 20.00);
        
        company.getEmployees().add(worker);
        
        // Execute: Calculate total salary
        double actualTotalSalary = company.calculateTotalEmployeeSalary();
        
        // Verify: Expected total salary is 800.00 (40 * 20.00)
        assertEquals(800.00, actualTotalSalary, 0.001);
    }
    
    @Test
    public void testCase2_CalculateTotalSalaryForCompanyWithSingleSalesPerson() throws Exception {
        // Setup: Create company with 1 SalesPerson
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date birthDate = sdf.parse("1990-01-01 00:00:00");
        
        SalesPeople salesPerson = new SalesPeople("SALES", "Jane Smith", birthDate, 
                                                "987-65-4321", 3000.00, 2000.00, 0.10);
        
        company.getEmployees().add(salesPerson);
        
        // Execute: Calculate total salary
        double actualTotalSalary = company.calculateTotalEmployeeSalary();
        
        // Verify: Expected total salary is 3200.00 (3000.00 + 2000.00 * 0.10)
        assertEquals(3200.00, actualTotalSalary, 0.001);
    }
    
    @Test
    public void testCase3_CalculateTotalSalaryForCompanyWithSingleManager() throws Exception {
        // Setup: Create company with 1 Manager
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date birthDate = sdf.parse("1990-01-01 00:00:00");
        
        Manager manager = new Manager("MANAGEMENT", "Bob Johnson", birthDate, 
                                    "555-12-3456", 5000.00, "Senior Manager");
        
        company.getEmployees().add(manager);
        
        // Execute: Calculate total salary
        double actualTotalSalary = company.calculateTotalEmployeeSalary();
        
        // Verify: Expected total salary is 5000.00
        assertEquals(5000.00, actualTotalSalary, 0.001);
    }
    
    @Test
    public void testCase4_CalculateTotalSalaryForCompanyWithWorkerSalesPersonAndManager() throws Exception {
        // Setup: Create company with 1 Worker, 1 SalesPerson and 1 Manager
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date birthDate = sdf.parse("1990-01-01 00:00:00");
        
        // Worker: 35 hours * 22.00 = 770.00
        Worker worker = new OffShiftWorker("PRODUCTION", "Worker 1", birthDate, 
                                         "111-11-1111", 35, 22.00);
        
        // SalesPerson: 2800.00 + (1500.00 * 0.15) = 2800.00 + 225.00 = 3025.00
        SalesPeople salesPerson = new SalesPeople("SALES", "Sales 1", birthDate, 
                                                "222-22-2222", 2800.00, 1500.00, 0.15);
        
        // Manager: 4800.00
        Manager manager = new Manager("MANAGEMENT", "Manager 1", birthDate, 
                                    "333-33-3333", 4800.00, "Manager");
        
        company.getEmployees().add(worker);
        company.getEmployees().add(salesPerson);
        company.getEmployees().add(manager);
        
        // Execute: Calculate total salary
        double actualTotalSalary = company.calculateTotalEmployeeSalary();
        
        // Verify: Expected total salary is 770.00 + 3025.00 + 4800.00 = 8595.00
        assertEquals(8595.00, actualTotalSalary, 0.001);
    }
    
    @Test
    public void testCase5_CalculateTotalSalaryForCompanyWithMultipleWorkers() throws Exception {
        // Setup: Create company with 3 Workers (1 shift worker)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date birthDate = sdf.parse("1990-01-01 00:00:00");
        
        // First Worker: 45 hours * 18.00 = 810.00
        Worker worker1 = new OffShiftWorker("PRODUCTION", "Worker 1", birthDate, 
                                          "111-11-1111", 45, 18.00);
        
        // Second Worker: 38 hours * 21.00 = 798.00
        Worker worker2 = new OffShiftWorker("PRODUCTION", "Worker 2", birthDate, 
                                          "222-22-2222", 38, 21.00);
        
        // Shift Worker: (30 hours * 24.00) + 200.00 holiday premium = 720.00 + 200.00 = 920.00
        ShiftWorker shiftWorker = new ShiftWorker("PRODUCTION", "Shift Worker", birthDate, 
                                                "333-33-3333", 30, 24.00, 200.00);
        
        company.getEmployees().add(worker1);
        company.getEmployees().add(worker2);
        company.getEmployees().add(shiftWorker);
        
        // Execute: Calculate total salary
        double actualTotalSalary = company.calculateTotalEmployeeSalary();
        
        // Verify: Expected total salary is 810.00 + 798.00 + 920.00 = 2528.00
        assertEquals(2528.00, actualTotalSalary, 0.001);
    }
}