package edu.employee.employee3.test;

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
    
    @Before
    public void setUp() {
        // Initialize the factory using Ecore factory pattern
        factory = EmployeeFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_CalculateTotalSalaryForCompanyWithSingleWorker() {
        // Create company using factory pattern
        Company company = factory.createCompany();
        
        // Create OffShiftWorker using factory pattern
        OffShiftWorker worker = factory.createOffShiftWorker();
        worker.setWeeklyWorkingHour(40);
        worker.setHourlyRates(20.00);
        
        // Add worker to company
        company.getEmployees().add(worker);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Verify result
        assertEquals(800.00, totalSalary, 0.01);
    }
    
    @Test
    public void testCase2_CalculateTotalSalaryForCompanyWithSingleSalesPerson() {
        // Create company using factory pattern
        Company company = factory.createCompany();
        
        // Create SalesPeople using factory pattern
        SalesPeople salesPerson = factory.createSalesPeople();
        salesPerson.setSalary(3000.00);
        salesPerson.setAmountOfSales(2000.00);
        salesPerson.setCommissionPercentage(0.10);
        
        // Add sales person to company
        company.getEmployees().add(salesPerson);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Verify result
        assertEquals(3200.00, totalSalary, 0.01);
    }
    
    @Test
    public void testCase3_CalculateTotalSalaryForCompanyWithSingleManager() {
        // Create company using factory pattern
        Company company = factory.createCompany();
        
        // Create Manager using factory pattern
        Manager manager = factory.createManager();
        manager.setSalary(5000.00);
        
        // Add manager to company
        company.getEmployees().add(manager);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Verify result
        assertEquals(5000.00, totalSalary, 0.01);
    }
    
    @Test
    public void testCase4_CalculateTotalSalaryForCompanyWithWorkerSalesPersonAndManager() {
        // Create company using factory pattern
        Company company = factory.createCompany();
        
        // Create Worker using factory pattern
        OffShiftWorker worker = factory.createOffShiftWorker();
        worker.setWeeklyWorkingHour(35);
        worker.setHourlyRates(22.00);
        
        // Create SalesPeople using factory pattern
        SalesPeople salesPerson = factory.createSalesPeople();
        salesPerson.setSalary(2800.00);
        salesPerson.setAmountOfSales(1500.00);
        salesPerson.setCommissionPercentage(0.15);
        
        // Create Manager using factory pattern
        Manager manager = factory.createManager();
        manager.setSalary(4800.00);
        
        // Add all employees to company
        company.getEmployees().add(worker);
        company.getEmployees().add(salesPerson);
        company.getEmployees().add(manager);
        
        // Calculate total salary
        double totalSalary = company.calculateTotalEmployeeSalary();
        
        // Verify result
        assertEquals(8595.00, totalSalary, 0.01);
    }
    
    @Test
    public void testCase5_CalculateTotalSalaryForCompanyWithMultipleWorkers() {
        // Create company using factory pattern
        Company company = factory.createCompany();
        
        // Create first OffShiftWorker using factory pattern
        OffShiftWorker worker1 = factory.createOffShiftWorker();
        worker1.setWeeklyWorkingHour(45);
        worker1.setHourlyRates(18.00);
        
        // Create second OffShiftWorker using factory pattern
        OffShiftWorker worker2 = factory.createOffShiftWorker();
        worker2.setWeeklyWorkingHour(38);
        worker2.setHourlyRates(21.00);
        
        // Create ShiftWorker using factory pattern
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
        
        // Verify result
        assertEquals(2528.00, totalSalary, 0.01);
    }
}