import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR5Test {
    
    @Test
    public void testCase1_SingleManagerWithMultipleSubordinates() {
        // Setup: Create Manager M1 and assign Worker W1, SalesPerson S1, and Manager M2 as direct subordinates
        Manager m1 = new Manager();
        m1.setName("M1");
        
        Worker w1 = new ShiftWorker(); // Using ShiftWorker as concrete implementation
        w1.setName("W1");
        
        SalesPeople s1 = new SalesPeople();
        s1.setName("S1");
        
        Manager m2 = new Manager();
        m2.setName("M2");
        
        // Add subordinates to M1
        List<Employee> subordinates = new ArrayList<>();
        subordinates.add(w1);
        subordinates.add(s1);
        subordinates.add(m2);
        m1.setSubordinates(subordinates);
        
        // Verify the number of direct subordinates for Manager M1 is 3
        assertEquals("The number of direct subordinates for Manager M1 is 3.", 3, m1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase2_ManagerWithNoSubordinates() {
        // Setup: Create Manager M1 with no subordinates
        Manager m1 = new Manager();
        m1.setName("M1");
        
        // Verify the number of subordinates for the manager is 0
        assertEquals("The number of subordinates for the manager is 0.", 0, m1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase3_MultipleManagersWithDifferentNumberOfSubordinates() {
        // Setup: Create Manager A with 3 subordinates and Manager B with 7 subordinates
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        
        // Create subordinates for Manager A (1 ShiftWorker, 1 OffShiftWorker, 1 SalesPeople)
        List<Employee> subordinatesA = new ArrayList<>();
        subordinatesA.add(new ShiftWorker());
        subordinatesA.add(new OffShiftWorker());
        subordinatesA.add(new SalesPeople());
        managerA.setSubordinates(subordinatesA);
        
        // Create subordinates for Manager B (2 ShiftWorker, 2 OffShiftWorker, 3 SalesPeople)
        List<Employee> subordinatesB = new ArrayList<>();
        subordinatesB.add(new ShiftWorker());
        subordinatesB.add(new ShiftWorker());
        subordinatesB.add(new OffShiftWorker());
        subordinatesB.add(new OffShiftWorker());
        subordinatesB.add(new SalesPeople());
        subordinatesB.add(new SalesPeople());
        subordinatesB.add(new SalesPeople());
        managerB.setSubordinates(subordinatesB);
        
        // Verify the number of subordinates for Manager A is 3 and Manager B is 7
        assertEquals("The number of subordinates for Manager A is 3.", 3, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("The number of subordinates for Manager B is 7.", 7, managerB.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase4_ManagerWithSingleSubordinate() {
        // Setup: Create Manager M1 and assign Worker W1 as subordinate
        Manager m1 = new Manager();
        m1.setName("M1");
        
        Worker w1 = new ShiftWorker(); // Using ShiftWorker as concrete implementation
        w1.setName("W1");
        
        // Add subordinate to M1
        List<Employee> subordinates = new ArrayList<>();
        subordinates.add(w1);
        m1.setSubordinates(subordinates);
        
        // Verify the number of subordinates for the manager is 1
        assertEquals("The number of subordinates for the manager is 1.", 1, m1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase5_MultipleManagersWithNestedSubordinates() {
        // Setup: Manager A has 5 subordinates, Manager B has 1 subordinate
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        
        Worker w1 = new ShiftWorker(); // Using ShiftWorker as concrete implementation
        w1.setName("W1");
        
        SalesPeople s1 = new SalesPeople();
        s1.setName("S1");
        
        SalesPeople s2 = new SalesPeople();
        s2.setName("S2");
        
        SalesPeople s3 = new SalesPeople();
        s3.setName("S3");
        
        // Set up Manager B's subordinates (1 Worker W1)
        List<Employee> subordinatesB = new ArrayList<>();
        subordinatesB.add(w1);
        managerB.setSubordinates(subordinatesB);
        
        // Set up Manager A's subordinates (Worker W1, SalesPerson S1, S2, S3, Manager B)
        List<Employee> subordinatesA = new ArrayList<>();
        subordinatesA.add(w1);
        subordinatesA.add(s1);
        subordinatesA.add(s2);
        subordinatesA.add(s3);
        subordinatesA.add(managerB);
        managerA.setSubordinates(subordinatesA);
        
        // Verify the number of subordinates for Manager A is 5 and Manager B is 1
        assertEquals("The number of subordinates for Manager A is 5.", 5, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("The number of subordinates for Manager B is 1.", 1, managerB.getDirectSubordinateEmployeesCount());
    }
}