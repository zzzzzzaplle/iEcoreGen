package edu.employee.employee2.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.employee.EmployeeFactory;
import edu.employee.EmployeePackage;
import edu.employee.Manager;
import edu.employee.Worker;
import edu.employee.SalesPeople;
import edu.employee.ShiftWorker;
import edu.employee.OffShiftWorker;

public class CR5Test {
    
    private EmployeeFactory factory;
    
    @Before
    public void setUp() {
        // Initialize the Ecore factory for creating objects
        factory = EmployeeFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_SingleManagerWithMultipleSubordinates() {
        // Create Manager M1
        Manager managerM1 = factory.createManager();
        managerM1.setName("M1");
        
        // Create subordinates: 1 Worker, 1 SalesPerson and 1 Manager
        Worker workerW1 = factory.createOffShiftWorker();
        workerW1.setName("W1");
        
        SalesPeople salesPersonS1 = factory.createSalesPeople();
        salesPersonS1.setName("S1");
        
        Manager managerM2 = factory.createManager();
        managerM2.setName("M2");
        
        // Assign subordinates to Manager M1
        managerM1.getSubordinates().add(workerW1);
        managerM1.getSubordinates().add(salesPersonS1);
        managerM1.getSubordinates().add(managerM2);
        
        // Verify the number of direct subordinates for Manager M1 is 3
        assertEquals("The number of direct subordinates for Manager M1 should be 3", 
                     3, managerM1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase2_ManagerWithNoSubordinates() {
        // Create Manager M1 with no subordinates
        Manager managerM1 = factory.createManager();
        managerM1.setName("M1");
        
        // Verify the number of subordinates for the manager is 0
        assertEquals("The number of subordinates for the manager should be 0", 
                     0, managerM1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase3_MultipleManagersWithDifferentNumberOfSubordinates() {
        // Create Manager A with 3 subordinates
        Manager managerA = factory.createManager();
        managerA.setName("Manager A");
        
        ShiftWorker shiftWorker1 = factory.createShiftWorker();
        shiftWorker1.setName("SW1");
        
        OffShiftWorker offShiftWorker1 = factory.createOffShiftWorker();
        offShiftWorker1.setName("OSW1");
        
        SalesPeople salesPeople1 = factory.createSalesPeople();
        salesPeople1.setName("SP1");
        
        managerA.getSubordinates().add(shiftWorker1);
        managerA.getSubordinates().add(offShiftWorker1);
        managerA.getSubordinates().add(salesPeople1);
        
        // Create Manager B with 7 subordinates
        Manager managerB = factory.createManager();
        managerB.setName("Manager B");
        
        // Add 2 ShiftWorkers
        for (int i = 1; i <= 2; i++) {
            ShiftWorker sw = factory.createShiftWorker();
            sw.setName("SW_B" + i);
            managerB.getSubordinates().add(sw);
        }
        
        // Add 2 OffShiftWorkers
        for (int i = 1; i <= 2; i++) {
            OffShiftWorker osw = factory.createOffShiftWorker();
            osw.setName("OSW_B" + i);
            managerB.getSubordinates().add(osw);
        }
        
        // Add 3 SalesPeople
        for (int i = 1; i <= 3; i++) {
            SalesPeople sp = factory.createSalesPeople();
            sp.setName("SP_B" + i);
            managerB.getSubordinates().add(sp);
        }
        
        // Verify the number of subordinates for Manager A is 3 and Manager B is 7
        assertEquals("The number of subordinates for Manager A should be 3", 
                     3, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("The number of subordinates for Manager B should be 7", 
                     7, managerB.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase4_ManagerWithSingleSubordinate() {
        // Create Manager M1
        Manager managerM1 = factory.createManager();
        managerM1.setName("M1");
        
        // Assign Worker W1 as subordinate
        Worker workerW1 = factory.createOffShiftWorker();
        workerW1.setName("W1");
        managerM1.getSubordinates().add(workerW1);
        
        // Verify the number of subordinates for the manager is 1
        assertEquals("The number of subordinates for the manager should be 1", 
                     1, managerM1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase5_MultipleManagersWithNestedSubordinates() {
        // Create Manager A with 5 subordinates
        Manager managerA = factory.createManager();
        managerA.setName("Manager A");
        
        // Create subordinates for Manager A: 1 Worker, 3 SalesPerson and 1 Manager B
        Worker workerW1 = factory.createOffShiftWorker();
        workerW1.setName("W1");
        
        SalesPeople salesPersonS1 = factory.createSalesPeople();
        salesPersonS1.setName("S1");
        
        SalesPeople salesPersonS2 = factory.createSalesPeople();
        salesPersonS2.setName("S2");
        
        SalesPeople salesPersonS3 = factory.createSalesPeople();
        salesPersonS3.setName("S3");
        
        Manager managerB = factory.createManager();
        managerB.setName("Manager B");
        
        // Assign all subordinates to Manager A
        managerA.getSubordinates().add(workerW1);
        managerA.getSubordinates().add(salesPersonS1);
        managerA.getSubordinates().add(salesPersonS2);
        managerA.getSubordinates().add(salesPersonS3);
        managerA.getSubordinates().add(managerB);
        
        // Create subordinate for Manager B: 1 Worker
        Worker workerW2 = factory.createOffShiftWorker();
        workerW2.setName("W2");
        managerB.getSubordinates().add(workerW2);
        
        // Verify the number of subordinates for Manager A is 5 and Manager B is 1
        assertEquals("The number of subordinates for Manager A should be 5", 
                     5, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("The number of subordinates for Manager B should be 1", 
                     1, managerB.getDirectSubordinateEmployeesCount());
    }
}