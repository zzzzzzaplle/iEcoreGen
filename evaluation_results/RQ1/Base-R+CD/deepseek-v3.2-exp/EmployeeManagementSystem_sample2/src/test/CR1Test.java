import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
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
    public void testCase1_CalculateTotalSalaryForCompanyWithSingleWorker() throws Exception {
        // Create a worker
        OffShiftWorker worker = new OffShiftWorker();
        worker.setName("Worker1");
        worker.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
        worker.setSocialInsuranceNumber("123456789");
        worker.setDepartment("Production");
        worker.setWeeklyWorkingHour(40);
        worker.setHourlyRates(20.00);
        
        // Add worker to company
        company.addEmployee(worker);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Verify the result
        assertEquals(800.00, totalSalary, 0.001);
    }
    
    @Test
    public void testCase2_CalculateTotalSalaryForCompanyWithSingleSalesPerson() throws Exception {
        // Create a salesperson
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setName("SalesPerson1");
        salesPerson.setBirthDate(dateFormat.parse("1985-05-15 00:00:00"));
        salesPerson.setSocialInsuranceNumber("987654321");
        salesPerson.setDepartment("Sales");
        salesPerson.setSalary(3000.00);
        salesPerson.setAmountOfSales(2000.00);
        salesPerson.setCommissionPercentage(0.10);
        
        // Add salesperson to company
        company.addEmployee(salesPerson);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Verify the result
        assertEquals(3200.00, totalSalary, 0.001);
    }
    
    @Test
    public void testCase3_CalculateTotalSalaryForCompanyWithSingleManager() throws Exception {
        // Create a manager
        Manager manager = new Manager();
        manager.setName("Manager1");
        manager.setBirthDate(dateFormat.parse("1975-03-20 00:00:00"));
        manager.setSocialInsuranceNumber("555555555");
        manager.setDepartment("Management");
        manager.setSalary(5000.00);
        manager.setPosition("Senior Manager");
        
        // Add manager to company
        company.addEmployee(manager);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Verify the result
        assertEquals(5000.00, totalSalary, 0.001);
    }
    
    @Test
    public void testCase4_CalculateTotalSalaryForCompanyWithWorkerSalesPersonAndManager() throws Exception {
        // Create a worker
        OffShiftWorker worker = new OffShiftWorker();
        worker.setName("Worker1");
        worker.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
        worker.setSocialInsuranceNumber("111111111");
        worker.setDepartment("Production");
        worker.setWeeklyWorkingHour(35);
        worker.setHourlyRates(22.00);
        
        // Create a salesperson
        SalesPeople salesPerson = new SalesPeople();
        salesPerson.setName("SalesPerson1");
        salesPerson.setBirthDate(dateFormat.parse("1985-05-15 00:00:00"));
        salesPerson.setSocialInsuranceNumber("222222222");
        salesPerson.setDepartment("Sales");
        salesPerson.setSalary(2800.00);
        salesPerson.setAmountOfSales(1500.00);
        salesPerson.setCommissionPercentage(0.15);
        
        // Create a manager
        Manager manager = new Manager();
        manager.setName("Manager1");
        manager.setBirthDate(dateFormat.parse("1975-03-20 00:00:00"));
        manager.setSocialInsuranceNumber("333333333");
        manager.setDepartment("Management");
        manager.setSalary(4800.00);
        manager.setPosition("Department Manager");
        
        // Add all employees to company
        company.addEmployee(worker);
        company.addEmployee(salesPerson);
        company.addEmployee(manager);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Verify the result
        assertEquals(8595.00, totalSalary, 0.001);
    }
    
    @Test
    public void testCase5_CalculateTotalSalaryForCompanyWithMultipleWorkers() throws Exception {
        // Create first worker (off-shift)
        OffShiftWorker worker1 = new OffShiftWorker();
        worker1.setName("Worker1");
        worker1.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
        worker1.setSocialInsuranceNumber("111111111");
        worker1.setDepartment("Production");
        worker1.setWeeklyWorkingHour(45);
        worker1.setHourlyRates(18.00);
        
        // Create second worker (off-shift)
        OffShiftWorker worker2 = new OffShiftWorker();
        worker2.setName("Worker2");
        worker2.setBirthDate(dateFormat.parse("1992-03-15 00:00:00"));
        worker2.setSocialInsuranceNumber("222222222");
        worker2.setDepartment("Production");
        worker2.setWeeklyWorkingHour(38);
        worker2.setHourlyRates(21.00);
        
        // Create third worker (shift worker)
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setName("ShiftWorker1");
        shiftWorker.setBirthDate(dateFormat.parse("1988-07-20 00:00:00"));
        shiftWorker.setSocialInsuranceNumber("333333333");
        shiftWorker.setDepartment("Production");
        shiftWorker.setWeeklyWorkingHour(30);
        shiftWorker.setHourlyRates(24.00);
        shiftWorker.setHolidayPremium(200.00);
        
        // Add all workers to company
        company.addEmployee(worker1);
        company.addEmployee(worker2);
        company.addEmployee(shiftWorker);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Verify the result
        assertEquals(2528.00, totalSalary, 0.001);
    }
}