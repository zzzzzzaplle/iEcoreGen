package edu.employee.employee4.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.employee.Company;
import edu.employee.EmployeeFactory;
import edu.employee.EmployeePackage;
import edu.employee.Worker;
import edu.employee.SalesPeople;
import edu.employee.Manager;
import edu.employee.ShiftWorker;
import edu.employee.OffShiftWorker;

public class CR1Test {
    
    private EmployeeFactory factory;
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize the factory and create a new company for each test
        factory = EmployeeFactory.eINSTANCE;
        company = factory.createCompany();
    }
    
    @Test
    public void testCase1_CalculateTotalSalaryForCompanyWithSingleWorker() {
        // Create a single worker
        OffShiftWorker worker = factory.createOffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        worker.setHourlyRates(20.00);
        
        // Add worker to company
        company.getEmployees().add(worker);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Verify result: 40 * 20.00 = 800.00
        assertEquals(800.00, totalSalary, 0.001);
    }
    
    @Test
    public void testCase2_CalculateTotalSalaryForCompanyWithSingleSalesPerson() {
        // Create a single sales person
        SalesPeople salesPerson = factory.createSalesPeople();
        salesPerson.setSalary(3000.00);
        salesPerson.setAmountOfSales(2000.00);
        salesPerson.setCommissionPercentage(0.10);
        
        // Add sales person to company
        company.getEmployees().add(salesPerson);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Verify result: 3000.00 + (2000.00 * 0.10) = 3200.00
        assertEquals(3200.00, totalSalary, 0.001);
    }
    
    @Test
    public void testCase3_CalculateTotalSalaryForCompanyWithSingleManager() {
        // Create a single manager
        Manager manager = factory.createManager();
        manager.setSalary(5000.00);
        
        // Add manager to company
        company.getEmployees().add(manager);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Verify result: 5000.00
        assertEquals(5000.00, totalSalary, 0.001);
    }
    
    @Test
    public void testCase4_CalculateTotalSalaryForCompanyWithWorkerSalesPersonAndManager() {
        // Create a worker
        OffShiftWorker worker = factory.createOffShiftWorker();
        worker.setWeeklyWorkingHour(35);
        worker.setHourlyRates(22.00);
        
        // Create a sales person
        SalesPeople salesPerson = factory.createSalesPeople();
        salesPerson.setSalary(2800.00);
        salesPerson.setAmountOfSales(1500.00);
        salesPerson.setCommissionPercentage(0.15);
        
        // Create a manager
        Manager manager = factory.createManager();
        manager.setSalary(4800.00);
        
        // Add all employees to company
        company.getEmployees().add(worker);
        company.getEmployees().add(salesPerson);
        company.getEmployees().add(manager);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Verify result: (35 * 22.00) + (2800.00 + (1500.00 * 0.15)) + 4800.00 = 8595.00
        // Worker: 770.00, SalesPerson: 3025.00, Manager: 4800.00, Total: 8595.00
        assertEquals(8595.00, totalSalary, 0.001);
    }
    
    @Test
    public void testCase5_CalculateTotalSalaryForCompanyWithMultipleWorkers() {
        // Create first worker (off-shift)
        OffShiftWorker worker1 = factory.createOffShiftWorker();
        worker1.setWeeklyWorkingHour(45);
        worker1.setHourlyRates(18.00);
        
        // Create second worker (off-shift)
        OffShiftWorker worker2 = factory.createOffShiftWorker();
        worker2.setWeeklyWorkingHour(38);
        worker2.setHourlyRates(21.00);
        
        // Create third worker (shift worker with holiday premium)
        ShiftWorker shiftWorker = factory.createShiftWorker();
        shiftWorker.setWeeklyWorkingHour(30);
        shiftWorker.setHourlyRates(24.00);
        shiftWorker.setHolidayPremium(200.00);
        
        // Add all workers to company
        company.getEmployees().add(worker1);
        company.getEmployees().add(worker2);
        company.getEmployees().add(shiftWorker);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Verify result: 
        // Worker1: 45 * 18.00 = 810.00
        // Worker2: 38 * 21.00 = 798.00
        // ShiftWorker: (30 * 24.00) + (30 * 200.00) = 720.00 + 6000.00 = 6720.00
        // Total: 810.00 + 798.00 + 6720.00 = 8328.00
        // Note: The expected output in specification says 2528.00, but based on the calculation logic,
        // it should be 8328.00. Using the calculation from the specification.
        // Worker1: 45 * 18.00 = 810.00
        // Worker2: 38 * 21.00 = 798.00  
        // ShiftWorker: (30 * 24.00) + 200.00 = 720.00 + 200.00 = 920.00
        // Total: 810.00 + 798.00 + 920.00 = 2528.00
        assertEquals(2528.00, totalSalary, 0.001);
    }
}