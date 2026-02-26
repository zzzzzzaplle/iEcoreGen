package edu.employee.employee5.test;

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
        // Initialize the factory using Ecore factory pattern
        factory = EmployeeFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_SingleManagerWithMultipleSubordinates() {
        // Test Case 1: "Single manager with multiple subordinates"
        // Input: "A manager with 3 direct subordinate employees:1 Worker, 1 SalesPerson and 1 Manager."
        
        // Setup: Create Manager M1
        Manager m1 = factory.createManager();
        m1.setName("Manager M1");
        
        // Create subordinate employees
        Worker w1 = factory.createShiftWorker(); // Using ShiftWorker as concrete Worker implementation
        w1.setName("Worker W1");
        
        SalesPeople s1 = factory.createSalesPeople();
        s1.setName("SalesPerson S1");
        
        Manager m2 = factory.createManager();
        m2.setName("Manager M2");
        
        // Assign subordinates to M1
        m1.getSubordinates().add(w1);
        m1.getSubordinates().add(s1);
        m1.getSubordinates().add(m2);
        
        // Expected Output: "The number of direct subordinates for Manager M1 is 3."
        assertEquals("Manager M1 should have 3 direct subordinates", 3, m1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase2_ManagerWithNoSubordinates() {
        // Test Case 2: "Manager with no subordinates"
        // Input: "A manager with no direct subordinate employees."
        
        // Setup: Create Manager M1 with no subordinates
        Manager m1 = factory.createManager();
        m1.setName("Manager M1");
        
        // Expected Output: "The number of subordinates for the manager is 0."
        assertEquals("Manager with no subordinates should return 0", 0, m1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase3_MultipleManagersWithDifferentNumberOfSubordinates() {
        // Test Case 3: "Multiple managers with different number of subordinates"
        // Input: "Manager A has 3 subordinate employees, Manager B has 7 subordinate employees."
        
        // Setup: Create Manager A with 3 subordinates
        Manager managerA = factory.createManager();
        managerA.setName("Manager A");
        
        ShiftWorker sw1 = factory.createShiftWorker();
        sw1.setName("ShiftWorker 1");
        OffShiftWorker osw1 = factory.createOffShiftWorker();
        osw1.setName("OffShiftWorker 1");
        SalesPeople sp1 = factory.createSalesPeople();
        sp1.setName("SalesPeople 1");
        
        managerA.getSubordinates().add(sw1);
        managerA.getSubordinates().add(osw1);
        managerA.getSubordinates().add(sp1);
        
        // Setup: Create Manager B with 7 subordinates
        Manager managerB = factory.createManager();
        managerB.setName("Manager B");
        
        // Add 2 ShiftWorkers
        for (int i = 1; i <= 2; i++) {
            ShiftWorker sw = factory.createShiftWorker();
            sw.setName("ShiftWorker B" + i);
            managerB.getSubordinates().add(sw);
        }
        
        // Add 2 OffShiftWorkers
        for (int i = 1; i <= 2; i++) {
            OffShiftWorker osw = factory.createOffShiftWorker();
            osw.setName("OffShiftWorker B" + i);
            managerB.getSubordinates().add(osw);
        }
        
        // Add 3 SalesPeople
        for (int i = 1; i <= 3; i++) {
            SalesPeople sp = factory.createSalesPeople();
            sp.setName("SalesPeople B" + i);
            managerB.getSubordinates().add(sp);
        }
        
        // Expected Output: "The number of subordinates for Manager A is 3. The number of subordinates for Manager B is 7."
        assertEquals("Manager A should have 3 subordinates", 3, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("Manager B should have 7 subordinates", 7, managerB.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase4_ManagerWithSingleSubordinate() {
        // Test Case 4: "Manager with a single subordinate"
        // Input: "A manager with 1 direct subordinate employee:1 Worker."
        
        // Setup: Create Manager M1
        Manager m1 = factory.createManager();
        m1.setName("Manager M1");
        
        // Assign Worker W1 as subordinate
        Worker w1 = factory.createShiftWorker(); // Using ShiftWorker as concrete Worker implementation
        w1.setName("Worker W1");
        m1.getSubordinates().add(w1);
        
        // Expected Output: "The number of subordinates for the manager is 1."
        assertEquals("Manager with single subordinate should return 1", 1, m1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase5_MultipleManagersWithNestedSubordinates() {
        // Test Case 5: "Multiple managers with different number of subordinates"
        // Input: "Manager A has 5 subordinate employees: 1 Worker, 3 SalesPerson and 1 Manager B. Manager B has 1 subordinate employee: Worker W1."
        
        // Setup: Create Manager B first
        Manager managerB = factory.createManager();
        managerB.setName("Manager B");
        
        // Manager B has 1 subordinate: Worker W1
        Worker w1 = factory.createShiftWorker();
        w1.setName("Worker W1");
        managerB.getSubordinates().add(w1);
        
        // Setup: Create Manager A
        Manager managerA = factory.createManager();
        managerA.setName("Manager A");
        
        // Manager A has 1 Worker
        Worker w2 = factory.createShiftWorker();
        w2.setName("Worker W2");
        managerA.getSubordinates().add(w2);
        
        // Manager A has 3 SalesPeople
        for (int i = 1; i <= 3; i++) {
            SalesPeople sp = factory.createSalesPeople();
            sp.setName("SalesPerson S" + i);
            managerA.getSubordinates().add(sp);
        }
        
        // Manager A has Manager B as subordinate
        managerA.getSubordinates().add(managerB);
        
        // Expected Output: "The number of subordinates for Manager A is 5. The number of subordinates for Manager B is 1."
        assertEquals("Manager A should have 5 subordinates", 5, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("Manager B should have 1 subordinate", 1, managerB.getDirectSubordinateEmployeesCount());
    }
}