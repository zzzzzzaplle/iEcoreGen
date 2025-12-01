package edu.employee.employee3.test;

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
        Worker workerW1 = factory.createShiftWorker(); // Using ShiftWorker as concrete Worker implementation
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
        
        // Create 2 ShiftWorkers
        ShiftWorker shiftWorker2 = factory.createShiftWorker();
        shiftWorker2.setName("SW2");
        ShiftWorker shiftWorker3 = factory.createShiftWorker();
        shiftWorker3.setName("SW3");
        
        // Create 2 OffShiftWorkers
        OffShiftWorker offShiftWorker2 = factory.createOffShiftWorker();
        offShiftWorker2.setName("OSW2");
        OffShiftWorker offShiftWorker3 = factory.createOffShiftWorker();
        offShiftWorker3.setName("OSW3");
        
        // Create 3 SalesPeople
        SalesPeople salesPeople2 = factory.createSalesPeople();
        salesPeople2.setName("SP2");
        SalesPeople salesPeople3 = factory.createSalesPeople();
        salesPeople3.setName("SP3");
        SalesPeople salesPeople4 = factory.createSalesPeople();
        salesPeople4.setName("SP4");
        
        // Assign all subordinates to Manager B
        managerB.getSubordinates().add(shiftWorker2);
        managerB.getSubordinates().add(shiftWorker3);
        managerB.getSubordinates().add(offShiftWorker2);
        managerB.getSubordinates().add(offShiftWorker3);
        managerB.getSubordinates().add(salesPeople2);
        managerB.getSubordinates().add(salesPeople3);
        managerB.getSubordinates().add(salesPeople4);
        
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
        
        // Create Worker W1 as subordinate
        Worker workerW1 = factory.createShiftWorker(); // Using ShiftWorker as concrete Worker implementation
        workerW1.setName("W1");
        
        // Assign Worker W1 as subordinate to Manager M1
        managerM1.getSubordinates().add(workerW1);
        
        // Verify the number of subordinates for the manager is 1
        assertEquals("The number of subordinates for the manager should be 1", 
                     1, managerM1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase5_MultipleManagersWithNestedSubordinates() {
        // Create Manager A
        Manager managerA = factory.createManager();
        managerA.setName("Manager A");
        
        // Create subordinates for Manager A: 1 Worker, 3 SalesPerson and 1 Manager B
        Worker workerW1 = factory.createShiftWorker();
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
        
        // Create subordinate for Manager B: Worker W1 (same worker, different reference)
        Worker workerW2 = factory.createShiftWorker();
        workerW2.setName("W2");
        managerB.getSubordinates().add(workerW2);
        
        // Verify the number of subordinates for Manager A is 5 and Manager B is 1
        assertEquals("The number of subordinates for Manager A should be 5", 
                     5, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("The number of subordinates for Manager B should be 1", 
                     1, managerB.getDirectSubordinateEmployeesCount());
    }
}