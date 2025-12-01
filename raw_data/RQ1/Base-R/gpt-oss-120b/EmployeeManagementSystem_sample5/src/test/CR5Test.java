import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.*;

public class CR5Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_SingleManagerWithMultipleSubordinates() {
        // Create Manager M1
        Manager m1 = new Manager();
        m1.setName("Manager M1");
        
        // Create subordinates: Worker W1, SalesPerson S1, and Manager M2
        NonShiftWorker w1 = new NonShiftWorker();
        w1.setName("Worker W1");
        
        SalesPerson s1 = new SalesPerson();
        s1.setName("SalesPerson S1");
        
        Manager m2 = new Manager();
        m2.setName("Manager M2");
        
        // Assign subordinates to M1
        m1.addSubordinate(w1);
        m1.addSubordinate(s1);
        m1.addSubordinate(m2);
        
        // Add manager to company
        company.addEmployee(m1);
        
        // Get number of direct subordinates per manager
        Map<Manager, Integer> result = company.getNumberOfDirectSubordinatesPerManager();
        
        // Verify Manager M1 has 3 subordinates
        assertEquals("The number of direct subordinates for Manager M1 is 3.", 
                     Integer.valueOf(3), result.get(m1));
    }
    
    @Test
    public void testCase2_ManagerWithNoSubordinates() {
        // Create Manager M1 with no subordinates
        Manager m1 = new Manager();
        m1.setName("Manager M1");
        
        // Add manager to company
        company.addEmployee(m1);
        
        // Get number of direct subordinates per manager
        Map<Manager, Integer> result = company.getNumberOfDirectSubordinatesPerManager();
        
        // Verify Manager M1 has 0 subordinates
        assertEquals("The number of subordinates for the manager is 0.", 
                     Integer.valueOf(0), result.get(m1));
    }
    
    @Test
    public void testCase3_MultipleManagersWithDifferentNumberOfSubordinates() {
        // Create Manager A with 3 subordinates
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        
        ShiftWorker sw1 = new ShiftWorker();
        sw1.setName("ShiftWorker 1");
        sw1.setDepartment(Department.DELIVERY);
        
        NonShiftWorker nsw1 = new NonShiftWorker();
        nsw1.setName("NonShiftWorker 1");
        
        SalesPerson sp1 = new SalesPerson();
        sp1.setName("SalesPerson 1");
        
        managerA.addSubordinate(sw1);
        managerA.addSubordinate(nsw1);
        managerA.addSubordinate(sp1);
        
        // Create Manager B with 7 subordinates
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        
        // Create 2 ShiftWorkers
        ShiftWorker sw2 = new ShiftWorker();
        sw2.setName("ShiftWorker 2");
        sw2.setDepartment(Department.DELIVERY);
        
        ShiftWorker sw3 = new ShiftWorker();
        sw3.setName("ShiftWorker 3");
        sw3.setDepartment(Department.DELIVERY);
        
        // Create 2 NonShiftWorkers
        NonShiftWorker nsw2 = new NonShiftWorker();
        nsw2.setName("NonShiftWorker 2");
        
        NonShiftWorker nsw3 = new NonShiftWorker();
        nsw3.setName("NonShiftWorker 3");
        
        // Create 3 SalesPeople
        SalesPerson sp2 = new SalesPerson();
        sp2.setName("SalesPerson 2");
        
        SalesPerson sp3 = new SalesPerson();
        sp3.setName("SalesPerson 3");
        
        SalesPerson sp4 = new SalesPerson();
        sp4.setName("SalesPerson 4");
        
        // Assign all subordinates to Manager B
        managerB.addSubordinate(sw2);
        managerB.addSubordinate(sw3);
        managerB.addSubordinate(nsw2);
        managerB.addSubordinate(nsw3);
        managerB.addSubordinate(sp2);
        managerB.addSubordinate(sp3);
        managerB.addSubordinate(sp4);
        
        // Add managers to company
        company.addEmployee(managerA);
        company.addEmployee(managerB);
        
        // Get number of direct subordinates per manager
        Map<Manager, Integer> result = company.getNumberOfDirectSubordinatesPerManager();
        
        // Verify Manager A has 3 subordinates and Manager B has 7 subordinates
        assertEquals("The number of subordinates for Manager A is 3.", 
                     Integer.valueOf(3), result.get(managerA));
        assertEquals("The number of subordinates for Manager B is 7.", 
                     Integer.valueOf(7), result.get(managerB));
    }
    
    @Test
    public void testCase4_ManagerWithSingleSubordinate() {
        // Create Manager M1
        Manager m1 = new Manager();
        m1.setName("Manager M1");
        
        // Create Worker W1
        NonShiftWorker w1 = new NonShiftWorker();
        w1.setName("Worker W1");
        
        // Assign Worker W1 as subordinate to Manager M1
        m1.addSubordinate(w1);
        
        // Add manager to company
        company.addEmployee(m1);
        
        // Get number of direct subordinates per manager
        Map<Manager, Integer> result = company.getNumberOfDirectSubordinatesPerManager();
        
        // Verify Manager M1 has 1 subordinate
        assertEquals("The number of subordinates for the manager is 1.", 
                     Integer.valueOf(1), result.get(m1));
    }
    
    @Test
    public void testCase5_MultipleManagersWithNestedSubordinates() {
        // Create Manager B
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        
        // Create Worker W1
        NonShiftWorker w1 = new NonShiftWorker();
        w1.setName("Worker W1");
        
        // Assign Worker W1 as subordinate to Manager B
        managerB.addSubordinate(w1);
        
        // Create Manager A
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        
        // Create 1 Worker
        NonShiftWorker w2 = new NonShiftWorker();
        w2.setName("Worker W2");
        
        // Create 3 SalesPersons
        SalesPerson s1 = new SalesPerson();
        s1.setName("SalesPerson S1");
        
        SalesPerson s2 = new SalesPerson();
        s2.setName("SalesPerson S2");
        
        SalesPerson s3 = new SalesPerson();
        s3.setName("SalesPerson S3");
        
        // Assign all subordinates to Manager A: 1 Worker, 3 SalesPersons, and Manager B
        managerA.addSubordinate(w2);
        managerA.addSubordinate(s1);
        managerA.addSubordinate(s2);
        managerA.addSubordinate(s3);
        managerA.addSubordinate(managerB);
        
        // Add managers to company
        company.addEmployee(managerA);
        company.addEmployee(managerB);
        
        // Get number of direct subordinates per manager
        Map<Manager, Integer> result = company.getNumberOfDirectSubordinatesPerManager();
        
        // Verify Manager A has 5 subordinates and Manager B has 1 subordinate
        assertEquals("The number of subordinates for Manager A is 5.", 
                     Integer.valueOf(5), result.get(managerA));
        assertEquals("The number of subordinates for Manager B is 1.", 
                     Integer.valueOf(1), result.get(managerB));
    }
}