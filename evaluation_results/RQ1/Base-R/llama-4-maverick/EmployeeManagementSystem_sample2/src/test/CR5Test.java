import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    @Test
    public void testCase1_SingleManagerWithMultipleSubordinates() {
        // Test Case 1: "Single manager with multiple subordinates"
        // Create Manager M1
        Manager m1 = new Manager();
        
        // Create subordinate employees: 1 Worker, 1 SalesPerson and 1 Manager
        Worker w1 = new NonShiftWorker();
        Salesperson s1 = new Salesperson();
        Manager m2 = new Manager();
        
        // Assign subordinates to M1
        m1.addSubordinate(w1);
        m1.addSubordinate(s1);
        m1.addSubordinate(m2);
        
        // Verify the number of direct subordinates for Manager M1 is 3
        assertEquals("The number of direct subordinates for Manager M1 is 3.", 3, m1.getNumberOfSubordinates());
    }
    
    @Test
    public void testCase2_ManagerWithNoSubordinates() {
        // Test Case 2: "Manager with no subordinates"
        // Create Manager M1
        Manager m1 = new Manager();
        
        // Do not assign any subordinates
        // Verify the number of subordinates for the manager is 0
        assertEquals("The number of subordinates for the manager is 0.", 0, m1.getNumberOfSubordinates());
    }
    
    @Test
    public void testCase3_MultipleManagersWithDifferentNumberOfSubordinates() {
        // Test Case 3: "Multiple managers with different number of subordinates"
        // Create Manager A and assign 3 subordinates (1 ShiftWorker, 1 OffShiftWorker, 1 SalesPeople)
        Manager managerA = new Manager();
        ShiftWorker sw1 = new ShiftWorker();
        NonShiftWorker nsw1 = new NonShiftWorker();
        Salesperson sp1 = new Salesperson();
        
        managerA.addSubordinate(sw1);
        managerA.addSubordinate(nsw1);
        managerA.addSubordinate(sp1);
        
        // Create Manager B and assign 7 subordinates (2 ShiftWorker, 2 OffShiftWorker, 3 SalesPeople)
        Manager managerB = new Manager();
        ShiftWorker sw2 = new ShiftWorker();
        ShiftWorker sw3 = new ShiftWorker();
        NonShiftWorker nsw2 = new NonShiftWorker();
        NonShiftWorker nsw3 = new NonShiftWorker();
        Salesperson sp2 = new Salesperson();
        Salesperson sp3 = new Salesperson();
        Salesperson sp4 = new Salesperson();
        
        managerB.addSubordinate(sw2);
        managerB.addSubordinate(sw3);
        managerB.addSubordinate(nsw2);
        managerB.addSubordinate(nsw3);
        managerB.addSubordinate(sp2);
        managerB.addSubordinate(sp3);
        managerB.addSubordinate(sp4);
        
        // Verify the number of subordinates for Manager A is 3 and Manager B is 7
        assertEquals("The number of subordinates for Manager A is 3.", 3, managerA.getNumberOfSubordinates());
        assertEquals("The number of subordinates for Manager B is 7.", 7, managerB.getNumberOfSubordinates());
    }
    
    @Test
    public void testCase4_ManagerWithSingleSubordinate() {
        // Test Case 4: "Manager with a single subordinate"
        // Create Manager M1
        Manager m1 = new Manager();
        
        // Assign Worker W1 as subordinate
        Worker w1 = new NonShiftWorker();
        m1.addSubordinate(w1);
        
        // Verify the number of subordinates for the manager is 1
        assertEquals("The number of subordinates for the manager is 1.", 1, m1.getNumberOfSubordinates());
    }
    
    @Test
    public void testCase5_MultipleManagersWithNestedSubordinates() {
        // Test Case 5: "Multiple managers with different number of subordinates"
        // Create Manager A and assign worker W1, SalesPerson (S1, S2, S3), Manager B as direct subordinates
        Manager managerA = new Manager();
        Worker w1 = new NonShiftWorker();
        Salesperson s1 = new Salesperson();
        Salesperson s2 = new Salesperson();
        Salesperson s3 = new Salesperson();
        Manager managerB = new Manager();
        
        managerA.addSubordinate(w1);
        managerA.addSubordinate(s1);
        managerA.addSubordinate(s2);
        managerA.addSubordinate(s3);
        managerA.addSubordinate(managerB);
        
        // Create Manager B and assign 1 subordinate employee: Worker W1
        Worker w2 = new NonShiftWorker();
        managerB.addSubordinate(w2);
        
        // Verify the number of subordinates for Manager A is 5 and Manager B is 1
        assertEquals("The number of subordinates for Manager A is 5.", 5, managerA.getNumberOfSubordinates());
        assertEquals("The number of subordinates for Manager B is 1.", 1, managerB.getNumberOfSubordinates());
    }
}