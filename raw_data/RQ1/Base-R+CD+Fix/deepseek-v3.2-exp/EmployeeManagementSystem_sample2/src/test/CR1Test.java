import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;

public class CR1Test {
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_CalculateTotalSalaryForCompanyWithSingleWorker() {
        // Create a worker and set up details
        OffShiftWorker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        worker.setHourlyRates(20.00);
        
        // Add worker to company
        company.addEmployee(worker);
        
        // Calculate total salary and verify expected result
        double totalSalary = company.calculateTotalEmployeeSalary();
        assertEquals(800.00, totalSalary, 0.01);
    }
    
    @Test
    public void testCase2_CalculateTotalSalaryForCompanyWithSingleSalesPerson() {
        // Create a sales person and set up details
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(3000.00);
        salesPerson.setAmountOfSales(2000.00);
        salesPerson.setCommissionPercentage(0.10);
        
        // Add sales person to company
        company.addEmployee(salesPerson);
        
        // Calculate total salary and verify expected result
        double totalSalary = company.calculateTotalEmployeeSalary();
        assertEquals(3200.00, totalSalary, 0.01);
    }
    
    @Test
    public void testCase3_CalculateTotalSalaryForCompanyWithSingleManager() {
        // Create a manager and set up details
        Manager manager = new Manager();
        manager.setSalary(5000.00);
        
        // Add manager to company
        company.addEmployee(manager);
        
        // Calculate total salary and verify expected result
        double totalSalary = company.calculateTotalEmployeeSalary();
        assertEquals(5000.00, totalSalary, 0.01);
    }
    
    @Test
    public void testCase4_CalculateTotalSalaryForCompanyWithWorkerSalesPersonAndManager() {
        // Create worker and set up details
        OffShiftWorker worker = new OffShiftWorker();
        worker.setWeeklyWorkingHour(35);
        worker.setHourlyRates(22.00);
        
        // Create sales person and set up details
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setSalary(2800.00);
        salesPerson.setAmountOfSales(1500.00);
        salesPerson.setCommissionPercentage(0.15);
        
        // Create manager and set up details
        Manager manager = new Manager();
        manager.setSalary(4800.00);
        
        // Add all employees to company
        company.addEmployee(worker);
        company.addEmployee(salesPerson);
        company.addEmployee(manager);
        
        // Calculate total salary and verify expected result
        double totalSalary = company.calculateTotalEmployeeSalary();
        assertEquals(8595.00, totalSalary, 0.01);
    }
    
    @Test
    public void testCase5_CalculateTotalSalaryForCompanyWithMultipleWorkers() {
        // Create first worker (off-shift) and set up details
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setWeeklyWorkingHour(45);
        worker1.setHourlyRates(18.00);
        
        // Create second worker (off-shift) and set up details
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setWeeklyWorkingHour(38);
        worker2.setHourlyRates(21.00);
        
        // Create third worker (shift worker) and set up details
        ShiftWorker worker3 = new ShiftWorker();
        worker3.setWeeklyWorkingHour(30);
        worker3.setHourlyRates(24.00);
        worker3.setHolidayPremium(200.00);
        
        // Add all workers to company
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        company.addEmployee(worker3);
        
        // Calculate total salary and verify expected result
        double totalSalary = company.calculateTotalEmployeeSalary();
        assertEquals(2528.00, totalSalary, 0.01);
    }
}