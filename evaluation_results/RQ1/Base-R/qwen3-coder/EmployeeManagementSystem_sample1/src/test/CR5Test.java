import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    @Test
    public void testCase1_SingleManagerWithMultipleSubordinates() {
        // Test Case 1: "Single manager with multiple subordinates"
        // Setup: Create Manager M1. Assign Worker W1, SalesPerson S1, and Manager M2 as direct subordinates to M1.
        
        // Create Manager M1
        Manager m1 = new Manager();
        m1.setName("Manager M1");
        
        // Create Worker W1
        Worker w1 = new Worker();
        w1.setName("Worker W1");
        
        // Create SalesPerson S1
        SalesPerson s1 = new SalesPerson();
        s1.setName("SalesPerson S1");
        
        // Create Manager M2
        Manager m2 = new Manager();
        m2.setName("Manager M2");
        
        // Assign subordinates to M1
        m1.addSubordinate(w1);
        m1.addSubordinate(s1);
        m1.addSubordinate(m2);
        
        // Expected Output: "The number of direct subordinates for Manager M1 is 3."
        assertEquals("The number of direct subordinates for Manager M1 is 3.", 3, m1.getNumberOfSubordinates());
    }
    
    @Test
    public void testCase2_ManagerWithNoSubordinates() {
        // Test Case 2: "Manager with no subordinates"
        // Setup: Create Manager M1. Do not assign any subordinates.
        
        // Create Manager M1
        Manager m1 = new Manager();
        m1.setName("Manager M1");
        
        // Expected Output: "The number of subordinates for the manager is 0."
        assertEquals("The number of subordinates for the manager is 0.", 0, m1.getNumberOfSubordinates());
    }
    
    @Test
    public void testCase3_MultipleManagersWithDifferentNumberOfSubordinates() {
        // Test Case 3: "Multiple managers with different number of subordinates"
        // Setup: Create Manager A and assign 3 subordinates (1 ShiftWorker, 1 OffShiftWorker, 1 SalesPeople).
        //        Create Manager B and assign 7 subordinates (2 ShiftWorker, 2 OffShiftWorker, 3 SalesPeople).
        
        // Create Manager A
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        
        // Create subordinates for Manager A
        ShiftWorker sw1 = new ShiftWorker();
        sw1.setName("ShiftWorker SW1");
        
        OffShiftWorker osw1 = new OffShiftWorker();
        osw1.setName("OffShiftWorker OSW1");
        
        SalesPerson sp1 = new SalesPerson();
        sp1.setName("SalesPerson SP1");
        
        // Assign subordinates to Manager A
        managerA.addSubordinate(sw1);
        managerA.addSubordinate(osw1);
        managerA.addSubordinate(sp1);
        
        // Create Manager B
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        
        // Create subordinates for Manager B (2 ShiftWorker, 2 OffShiftWorker, 3 SalesPeople)
        ShiftWorker sw2 = new ShiftWorker();
        sw2.setName("ShiftWorker SW2");
        ShiftWorker sw3 = new ShiftWorker();
        sw3.setName("ShiftWorker SW3");
        
        OffShiftWorker osw2 = new OffShiftWorker();
        osw2.setName("OffShiftWorker OSW2");
        OffShiftWorker osw3 = new OffShiftWorker();
        osw3.setName("OffShiftWorker OSW3");
        
        SalesPerson sp2 = new SalesPerson();
        sp2.setName("SalesPerson SP2");
        SalesPerson sp3 = new SalesPerson();
        sp3.setName("SalesPerson SP3");
        SalesPerson sp4 = new SalesPerson();
        sp4.setName("SalesPerson SP4");
        
        // Assign subordinates to Manager B
        managerB.addSubordinate(sw2);
        managerB.addSubordinate(sw3);
        managerB.addSubordinate(osw2);
        managerB.addSubordinate(osw3);
        managerB.addSubordinate(sp2);
        managerB.addSubordinate(sp3);
        managerB.addSubordinate(sp4);
        
        // Expected Output: "The number of subordinates for Manager A is 3. The number of subordinates for Manager B is 7."
        assertEquals("The number of subordinates for Manager A is 3.", 3, managerA.getNumberOfSubordinates());
        assertEquals("The number of subordinates for Manager B is 7.", 7, managerB.getNumberOfSubordinates());
    }
    
    @Test
    public void testCase4_ManagerWithSingleSubordinate() {
        // Test Case 4: "Manager with a single subordinate"
        // Setup: Create Manager M1. Assign Worker W1 as subordinate.
        
        // Create Manager M1
        Manager m1 = new Manager();
        m1.setName("Manager M1");
        
        // Create Worker W1
        Worker w1 = new Worker();
        w1.setName("Worker W1");
        
        // Assign subordinate to M1
        m1.addSubordinate(w1);
        
        // Expected Output: "The number of subordinates for the manager is 1."
        assertEquals("The number of subordinates for the manager is 1.", 1, m1.getNumberOfSubordinates());
    }
    
    @Test
    public void testCase5_MultipleManagersWithHierarchicalSubordinates() {
        // Test Case 5: "Multiple managers with different number of subordinates"
        // Setup: Create Manager A and assign worker W1, SalesPerson (S1, S2, S3), Manager B as direct subordinates.
        //        Create Manager B and assign 1 subordinate employee: Worker W1.
        
        // Create Manager A
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        
        // Create subordinates for Manager A
        Worker w1 = new Worker();
        w1.setName("Worker W1");
        
        SalesPerson s1 = new SalesPerson();
        s1.setName("SalesPerson S1");
        SalesPerson s2 = new SalesPerson();
        s2.setName("SalesPerson S2");
        SalesPerson s3 = new SalesPerson();
        s3.setName("SalesPerson S3");
        
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        
        // Assign subordinates to Manager A
        managerA.addSubordinate(w1);
        managerA.addSubordinate(s1);
        managerA.addSubordinate(s2);
        managerA.addSubordinate(s3);
        managerA.addSubordinate(managerB);
        
        // Create subordinate for Manager B
        Worker w2 = new Worker();
        w2.setName("Worker W2");
        managerB.addSubordinate(w2);
        
        // Expected Output: "The number of subordinates for Manager A is 5. The number of subordinates for Manager B is 1."
        assertEquals("The number of subordinates for Manager A is 5.", 5, managerA.getNumberOfSubordinates());
        assertEquals("The number of subordinates for Manager B is 1.", 1, managerB.getNumberOfSubordinates());
    }
}