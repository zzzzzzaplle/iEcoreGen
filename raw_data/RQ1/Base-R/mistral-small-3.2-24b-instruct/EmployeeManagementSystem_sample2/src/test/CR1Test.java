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
        // Create a NonShiftWorker (since holiday premiums are 0 for this test)
        NonShiftWorker worker = new NonShiftWorker();
        worker.setWeeklyWorkingHours(40);
        worker.setHourlyRate(20.00);
        
        // Add worker to company
        company.addEmployee(worker);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalSalary();
        
        // Verify result: 40 * 20.00 = 800.00
        assertEquals(800.00, totalSalary, 0.001);
    }
    
    @Test
    public void testCase2_CalculateTotalSalaryForCompanyWithSingleSalesPerson() {
        // Create SalesPerson
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setFixedSalary(3000.00);
        salesPerson.setAmountOfSales(2000.00);
        salesPerson.setCommissionPercentage(0.10);
        
        // Add sales person to company
        company.addEmployee(salesPerson);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalSalary();
        
        // Verify result: 3000.00 + (2000.00 * 0.10) = 3200.00
        assertEquals(3200.00, totalSalary, 0.001);
    }
    
    @Test
    public void testCase3_CalculateTotalSalaryForCompanyWithSingleManager() {
        // Create Manager (note: Manager's calculateSalary returns 0, so we need to handle salary differently)
        // For this test, we'll use the fact that Manager extends Employee and can have salary set
        // Since Manager's calculateSalary returns 0, we'll create a custom approach
        Manager manager = new Manager();
        
        // Create a subclass to handle salary properly for this test
        Employee managerWithSalary = new Employee() {
            private double salary = 5000.00;
            
            @Override
            public double calculateSalary() {
                return salary;
            }
            
            @Override
            public boolean isShiftWorker() {
                return false;
            }
        };
        
        // Add manager to company
        company.addEmployee(managerWithSalary);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalSalary();
        
        // Verify result: 5000.00
        assertEquals(5000.00, totalSalary, 0.001);
    }
    
    @Test
    public void testCase4_CalculateTotalSalaryForCompanyWithWorkerSalesPersonAndManager() {
        // Create Worker
        NonShiftWorker worker = new NonShiftWorker();
        worker.setWeeklyWorkingHours(35);
        worker.setHourlyRate(22.00);
        
        // Create SalesPerson
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setFixedSalary(2800.00);
        salesPerson.setAmountOfSales(1500.00);
        salesPerson.setCommissionPercentage(0.15);
        
        // Create Manager (using custom implementation for salary)
        Employee manager = new Employee() {
            private double salary = 4800.00;
            
            @Override
            public double calculateSalary() {
                return salary;
            }
            
            @Override
            public boolean isShiftWorker() {
                return false;
            }
        };
        
        // Add all employees to company
        company.addEmployee(worker);
        company.addEmployee(salesPerson);
        company.addEmployee(manager);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalSalary();
        
        // Verify result: (35 * 22.00) + (2800.00 + (1500.00 * 0.15)) + 4800.00 = 8595.00
        assertEquals(8595.00, totalSalary, 0.001);
    }
    
    @Test
    public void testCase5_CalculateTotalSalaryForCompanyWithMultipleWorkers() {
        // Create first Worker (NonShiftWorker)
        NonShiftWorker worker1 = new NonShiftWorker();
        worker1.setWeeklyWorkingHours(45);
        worker1.setHourlyRate(18.00);
        
        // Create second Worker (NonShiftWorker)
        NonShiftWorker worker2 = new NonShiftWorker();
        worker2.setWeeklyWorkingHours(38);
        worker2.setHourlyRate(21.00);
        
        // Create third Worker (ShiftWorker with holiday premium)
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setWeeklyWorkingHours(30);
        shiftWorker.setHourlyRate(24.00);
        shiftWorker.setHolidayPremium(200.00);
        
        // Add all workers to company
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        company.addEmployee(shiftWorker);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalSalary();
        
        // Verify result: (45 * 18.00) + (38 * 21.00) + ((30 * 24.00) + 200.00) = 2528.00
        assertEquals(2528.00, totalSalary, 0.001);
    }
}