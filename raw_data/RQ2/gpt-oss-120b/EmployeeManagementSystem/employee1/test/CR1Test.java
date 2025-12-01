package edu.employee.employee1.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.employee.*;
import java.util.Date;

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
        // Create a worker and add to company
        Worker worker = factory.createOffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        worker.setHourlyRates(20.00);
        worker.setName("Worker1");
        worker.setBirthDate(new Date());
        worker.setSocialInsuranceNumber("123456789");
        
        // Add worker to company
        company.getEmployees().add(worker);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Expected: 40 * 20.00 = 800.00
        assertEquals(800.00, totalSalary, 0.01);
    }
    
    @Test
    public void testCase2_CalculateTotalSalaryForCompanyWithSingleSalesPerson() {
        // Create a sales person and add to company
        SalesPeople salesPerson = factory.createSalesPeople();
        salesPerson.setSalary(3000.00);
        salesPerson.setAmountOfSales(2000.00);
        salesPerson.setCommissionPercentage(0.10);
        salesPerson.setName("SalesPerson1");
        salesPerson.setBirthDate(new Date());
        salesPerson.setSocialInsuranceNumber("987654321");
        
        // Add sales person to company
        company.getEmployees().add(salesPerson);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Expected: 3000.00 + (2000.00 * 0.10) = 3200.00
        assertEquals(3200.00, totalSalary, 0.01);
    }
    
    @Test
    public void testCase3_CalculateTotalSalaryForCompanyWithSingleManager() {
        // Create a manager and add to company
        Manager manager = factory.createManager();
        manager.setSalary(5000.00);
        manager.setName("Manager1");
        manager.setBirthDate(new Date());
        manager.setSocialInsuranceNumber("555555555");
        
        // Add manager to company
        company.getEmployees().add(manager);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Expected: 5000.00
        assertEquals(5000.00, totalSalary, 0.01);
    }
    
    @Test
    public void testCase4_CalculateTotalSalaryForCompanyWithWorkerSalesPersonAndManager() {
        // Create worker
        Worker worker = factory.createOffShiftWorker();
        worker.setWeeklyWorkingHour(35);
        worker.setHourlyRates(22.00);
        worker.setName("Worker1");
        worker.setBirthDate(new Date());
        worker.setSocialInsuranceNumber("111111111");
        
        // Create sales person
        SalesPeople salesPerson = factory.createSalesPeople();
        salesPerson.setSalary(2800.00);
        salesPerson.setAmountOfSales(1500.00);
        salesPerson.setCommissionPercentage(0.15);
        salesPerson.setName("SalesPerson1");
        salesPerson.setBirthDate(new Date());
        salesPerson.setSocialInsuranceNumber("222222222");
        
        // Create manager
        Manager manager = factory.createManager();
        manager.setSalary(4800.00);
        manager.setName("Manager1");
        manager.setBirthDate(new Date());
        manager.setSocialInsuranceNumber("333333333");
        
        // Add all employees to company
        company.getEmployees().add(worker);
        company.getEmployees().add(salesPerson);
        company.getEmployees().add(manager);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Expected: (35 * 22.00) + (2800.00 + 1500.00 * 0.15) + 4800.00 = 8595.00
        // Worker: 770.00, SalesPerson: 3025.00, Manager: 4800.00
        assertEquals(8595.00, totalSalary, 0.01);
    }
    
    @Test
    public void testCase5_CalculateTotalSalaryForCompanyWithMultipleWorkers() {
        // Create first worker (off-shift)
        Worker worker1 = factory.createOffShiftWorker();
        worker1.setWeeklyWorkingHour(45);
        worker1.setHourlyRates(18.00);
        worker1.setName("Worker1");
        worker1.setBirthDate(new Date());
        worker1.setSocialInsuranceNumber("444444444");
        
        // Create second worker (off-shift)
        Worker worker2 = factory.createOffShiftWorker();
        worker2.setWeeklyWorkingHour(38);
        worker2.setHourlyRates(21.00);
        worker2.setName("Worker2");
        worker2.setBirthDate(new Date());
        worker2.setSocialInsuranceNumber("555555555");
        
        // Create third worker (shift worker with holiday premium)
        ShiftWorker shiftWorker = factory.createShiftWorker();
        shiftWorker.setWeeklyWorkingHour(30);
        shiftWorker.setHourlyRates(24.00);
        shiftWorker.setHolidayPremium(200.00);
        shiftWorker.setName("ShiftWorker1");
        shiftWorker.setBirthDate(new Date());
        shiftWorker.setSocialInsuranceNumber("666666666");
        
        // Add all workers to company
        company.getEmployees().add(worker1);
        company.getEmployees().add(worker2);
        company.getEmployees().add(shiftWorker);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Expected: (45 * 18.00) + (38 * 21.00) + (30 * 24.00 + 200.00) = 2528.00
        // Worker1: 810.00, Worker2: 798.00, ShiftWorker: 920.00
        assertEquals(2528.00, totalSalary, 0.01);
    }
}