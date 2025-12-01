import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    @Test
    public void testCase1_SingleManagerWithMultipleSubordinates() {
        // Test Case 1: "Single manager with multiple subordinates"
        // Setup: Create Manager M1, assign Worker W1, SalesPerson S1, and Manager M2 as direct subordinates
        Manager m1 = new Manager();
        Worker w1 = new Worker();
        SalesPerson s1 = new SalesPerson();
        Manager m2 = new Manager();
        
        m1.addSubordinate(w1);
        m1.addSubordinate(s1);
        m1.addSubordinate(m2);
        
        // Expected Output: The number of direct subordinates for Manager M1 is 3
        assertEquals("The number of direct subordinates for Manager M1 should be 3", 3, m1.getNumberOfSubordinates());
    }
    
    @Test
    public void testCase2_ManagerWithNoSubordinates() {
        // Test Case 2: "Manager with no subordinates"
        // Setup: Create Manager M1, do not assign any subordinates
        Manager m1 = new Manager();
        
        // Expected Output: The number of subordinates for the manager is 0
        assertEquals("The number of subordinates for the manager should be 0", 0, m1.getNumberOfSubordinates());
    }
    
    @Test
    public void testCase3_MultipleManagersWithDifferentNumberOfSubordinates() {
        // Test Case 3: "Multiple managers with different number of subordinates"
        // Setup: Create Manager A with 3 subordinates, Manager B with 7 subordinates
        Manager managerA = new Manager();
        Manager managerB = new Manager();
        
        // Manager A: 1 ShiftWorker, 1 OffShiftWorker, 1 SalesPerson
        ShiftWorker sw1 = new ShiftWorker();
        OffShiftWorker ow1 = new OffShiftWorker();
        SalesPerson sp1 = new SalesPerson();
        
        managerA.addSubordinate(sw1);
        managerA.addSubordinate(ow1);
        managerA.addSubordinate(sp1);
        
        // Manager B: 2 ShiftWorker, 2 OffShiftWorker, 3 SalesPerson
        ShiftWorker sw2 = new ShiftWorker();
        ShiftWorker sw3 = new ShiftWorker();
        OffShiftWorker ow2 = new OffShiftWorker();
        OffShiftWorker ow3 = new OffShiftWorker();
        SalesPerson sp2 = new SalesPerson();
        SalesPerson sp3 = new SalesPerson();
        SalesPerson sp4 = new SalesPerson();
        
        managerB.addSubordinate(sw2);
        managerB.addSubordinate(sw3);
        managerB.addSubordinate(ow2);
        managerB.addSubordinate(ow3);
        managerB.addSubordinate(sp2);
        managerB.addSubordinate(sp3);
        managerB.addSubordinate(sp4);
        
        // Expected Output: The number of subordinates for Manager A is 3. The number of subordinates for Manager B is 7
        assertEquals("The number of subordinates for Manager A should be 3", 3, managerA.getNumberOfSubordinates());
        assertEquals("The number of subordinates for Manager B should be 7", 7, managerB.getNumberOfSubordinates());
    }
    
    @Test
    public void testCase4_ManagerWithSingleSubordinate() {
        // Test Case 4: "Manager with a single subordinate"
        // Setup: Create Manager M1, assign Worker W1 as subordinate
        Manager m1 = new Manager();
        Worker w1 = new Worker();
        
        m1.addSubordinate(w1);
        
        // Expected Output: The number of subordinates for the manager is 1
        assertEquals("The number of subordinates for the manager should be 1", 1, m1.getNumberOfSubordinates());
    }
    
    @Test
    public void testCase5_MultipleManagersWithNestedSubordinates() {
        // Test Case 5: "Multiple managers with different number of subordinates"
        // Setup: Manager A has 5 subordinates, Manager B has 1 subordinate
        Manager managerA = new Manager();
        Manager managerB = new Manager();
        Worker w1 = new Worker();
        SalesPerson s1 = new SalesPerson();
        SalesPerson s2 = new SalesPerson();
        SalesPerson s3 = new SalesPerson();
        Worker w2 = new Worker();
        
        // Manager A: 1 Worker, 3 SalesPerson, 1 Manager B
        managerA.addSubordinate(w1);
        managerA.addSubordinate(s1);
        managerA.addSubordinate(s2);
        managerA.addSubordinate(s3);
        managerA.addSubordinate(managerB);
        
        // Manager B: 1 Worker W1
        managerB.addSubordinate(w2);
        
        // Expected Output: The number of subordinates for Manager A is 5. The number of subordinates for Manager B is 1
        assertEquals("The number of subordinates for Manager A should be 5", 5, managerA.getNumberOfSubordinates());
        assertEquals("The number of subordinates for Manager B should be 1", 1, managerB.getNumberOfSubordinates());
    }
}