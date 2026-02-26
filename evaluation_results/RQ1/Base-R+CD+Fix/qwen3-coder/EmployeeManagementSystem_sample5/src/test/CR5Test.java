import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR5Test {
    
    @Test
    public void testCase1_SingleManagerWithMultipleSubordinates() {
        // Test Case 1: "Single manager with multiple subordinates"
        // Setup: Create Manager M1 and assign Worker W1, SalesPerson S1, and Manager M2 as direct subordinates
        
        Manager m1 = new Manager();
        m1.setName("M1");
        
        Worker w1 = new OffShiftWorker();
        w1.setName("W1");
        
        SalesPeople s1 = new SalesPeople();
        s1.setName("S1");
        
        Manager m2 = new Manager();
        m2.setName("M2");
        
        List<Employee> subordinates = new ArrayList<>();
        subordinates.add(w1);
        subordinates.add(s1);
        subordinates.add(m2);
        m1.setSubordinates(subordinates);
        
        // Expected Output: "The number of direct subordinates for Manager M1 is 3."
        assertEquals("The number of direct subordinates for Manager M1 should be 3", 3, m1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase2_ManagerWithNoSubordinates() {
        // Test Case 2: "Manager with no subordinates"
        // Setup: Create Manager M1 without assigning any subordinates
        
        Manager m1 = new Manager();
        m1.setName("M1");
        
        // Expected Output: "The number of subordinates for the manager is 0."
        assertEquals("The number of subordinates for the manager should be 0", 0, m1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase3_MultipleManagersWithDifferentNumberOfSubordinates() {
        // Test Case 3: "Multiple managers with different number of subordinates"
        // Setup: Create Manager A with 3 subordinates (1 ShiftWorker, 1 OffShiftWorker, 1 SalesPeople)
        //        Create Manager B with 7 subordinates (2 ShiftWorker, 2 OffShiftWorker, 3 SalesPeople)
        
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        
        ShiftWorker sw1 = new ShiftWorker();
        sw1.setName("SW1");
        OffShiftWorker ow1 = new OffShiftWorker();
        ow1.setName("OW1");
        SalesPeople sp1 = new SalesPeople();
        sp1.setName("SP1");
        
        List<Employee> subordinatesA = new ArrayList<>();
        subordinatesA.add(sw1);
        subordinatesA.add(ow1);
        subordinatesA.add(sp1);
        managerA.setSubordinates(subordinatesA);
        
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        
        ShiftWorker sw2 = new ShiftWorker();
        sw2.setName("SW2");
        ShiftWorker sw3 = new ShiftWorker();
        sw3.setName("SW3");
        OffShiftWorker ow2 = new OffShiftWorker();
        ow2.setName("OW2");
        OffShiftWorker ow3 = new OffShiftWorker();
        ow3.setName("OW3");
        SalesPeople sp2 = new SalesPeople();
        sp2.setName("SP2");
        SalesPeople sp3 = new SalesPeople();
        sp3.setName("SP3");
        SalesPeople sp4 = new SalesPeople();
        sp4.setName("SP4");
        
        List<Employee> subordinatesB = new ArrayList<>();
        subordinatesB.add(sw2);
        subordinatesB.add(sw3);
        subordinatesB.add(ow2);
        subordinatesB.add(ow3);
        subordinatesB.add(sp2);
        subordinatesB.add(sp3);
        subordinatesB.add(sp4);
        managerB.setSubordinates(subordinatesB);
        
        // Expected Output: "The number of subordinates for Manager A is 3. The number of subordinates for Manager B is 7."
        assertEquals("The number of subordinates for Manager A should be 3", 3, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("The number of subordinates for Manager B should be 7", 7, managerB.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase4_ManagerWithSingleSubordinate() {
        // Test Case 4: "Manager with a single subordinate"
        // Setup: Create Manager M1 and assign Worker W1 as subordinate
        
        Manager m1 = new Manager();
        m1.setName("M1");
        
        Worker w1 = new OffShiftWorker();
        w1.setName("W1");
        
        List<Employee> subordinates = new ArrayList<>();
        subordinates.add(w1);
        m1.setSubordinates(subordinates);
        
        // Expected Output: "The number of subordinates for the manager is 1."
        assertEquals("The number of subordinates for the manager should be 1", 1, m1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase5_MultipleManagersWithDifferentNumberOfSubordinates() {
        // Test Case 5: "Multiple managers with different number of subordinates"
        // Setup: Create Manager A with 5 subordinates (1 Worker, 3 SalesPerson, and 1 Manager B)
        //        Create Manager B with 1 subordinate employee (Worker W1)
        
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        
        Worker w1 = new OffShiftWorker();
        w1.setName("W1");
        
        SalesPeople s1 = new SalesPeople();
        s1.setName("S1");
        SalesPeople s2 = new SalesPeople();
        s2.setName("S2");
        SalesPeople s3 = new SalesPeople();
        s3.setName("S3");
        
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        
        Worker w2 = new OffShiftWorker();
        w2.setName("W2");
        
        List<Employee> subordinatesB = new ArrayList<>();
        subordinatesB.add(w2);
        managerB.setSubordinates(subordinatesB);
        
        List<Employee> subordinatesA = new ArrayList<>();
        subordinatesA.add(w1);
        subordinatesA.add(s1);
        subordinatesA.add(s2);
        subordinatesA.add(s3);
        subordinatesA.add(managerB);
        managerA.setSubordinates(subordinatesA);
        
        // Expected Output: "The number of subordinates for Manager A is 5. The number of subordinates for Manager B is 1."
        assertEquals("The number of subordinates for Manager A should be 5", 5, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("The number of subordinates for Manager B should be 1", 1, managerB.getDirectSubordinateEmployeesCount());
    }
}