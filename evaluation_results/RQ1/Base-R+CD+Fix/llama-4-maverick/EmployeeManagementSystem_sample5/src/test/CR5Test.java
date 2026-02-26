import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;

public class CR5Test {
    
    @Test
    public void testCase1_singleManagerWithMultipleSubordinates() {
        // Create Manager M1
        Manager m1 = new Manager();
        m1.setName("M1");
        
        // Create subordinates: Worker W1, SalesPerson S1, and Manager M2
        Worker w1 = new OffShiftWorker();
        w1.setName("W1");
        
        SalesPeople s1 = new SalesPeople();
        s1.setName("S1");
        
        Manager m2 = new Manager();
        m2.setName("M2");
        
        // Assign subordinates to manager M1
        m1.getSubordinates().add(w1);
        m1.getSubordinates().add(s1);
        m1.getSubordinates().add(m2);
        
        // Verify the number of direct subordinates for Manager M1 is 3
        assertEquals("The number of direct subordinates for Manager M1 is 3", 
                     3, m1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase2_managerWithNoSubordinates() {
        // Create Manager M1
        Manager m1 = new Manager();
        m1.setName("M1");
        
        // Do not assign any subordinates
        // Verify the number of subordinates for the manager is 0
        assertEquals("The number of subordinates for the manager is 0", 
                     0, m1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase3_multipleManagersWithDifferentNumberOfSubordinates() {
        // Create Manager A and assign 3 subordinates
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        
        ShiftWorker sw1 = new ShiftWorker();
        sw1.setName("SW1");
        OffShiftWorker osw1 = new OffShiftWorker();
        osw1.setName("OSW1");
        SalesPeople sp1 = new SalesPeople();
        sp1.setName("SP1");
        
        managerA.getSubordinates().add(sw1);
        managerA.getSubordinates().add(osw1);
        managerA.getSubordinates().add(sp1);
        
        // Create Manager B and assign 7 subordinates
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        
        ShiftWorker sw2 = new ShiftWorker();
        sw2.setName("SW2");
        ShiftWorker sw3 = new ShiftWorker();
        sw3.setName("SW3");
        OffShiftWorker osw2 = new OffShiftWorker();
        osw2.setName("OSW2");
        OffShiftWorker osw3 = new OffShiftWorker();
        osw3.setName("OSW3");
        SalesPeople sp2 = new SalesPeople();
        sp2.setName("SP2");
        SalesPeople sp3 = new SalesPeople();
        sp3.setName("SP3");
        SalesPeople sp4 = new SalesPeople();
        sp4.setName("SP4");
        
        managerB.getSubordinates().add(sw2);
        managerB.getSubordinates().add(sw3);
        managerB.getSubordinates().add(osw2);
        managerB.getSubordinates().add(osw3);
        managerB.getSubordinates().add(sp2);
        managerB.getSubordinates().add(sp3);
        managerB.getSubordinates().add(sp4);
        
        // Verify the number of subordinates for each manager
        assertEquals("The number of subordinates for Manager A is 3", 
                     3, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("The number of subordinates for Manager B is 7", 
                     7, managerB.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase4_managerWithSingleSubordinate() {
        // Create Manager M1
        Manager m1 = new Manager();
        m1.setName("M1");
        
        // Create Worker W1
        Worker w1 = new OffShiftWorker();
        w1.setName("W1");
        
        // Assign Worker W1 as subordinate to Manager M1
        m1.getSubordinates().add(w1);
        
        // Verify the number of subordinates for the manager is 1
        assertEquals("The number of subordinates for the manager is 1", 
                     1, m1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase5_multipleManagersWithDifferentSubordinates() {
        // Create Manager A and Manager B
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        
        // Create subordinates for Manager A: Worker W1, SalesPerson S1, S2, S3
        Worker w1 = new OffShiftWorker();
        w1.setName("W1");
        
        SalesPeople s1 = new SalesPeople();
        s1.setName("S1");
        SalesPeople s2 = new SalesPeople();
        s2.setName("S2");
        SalesPeople s3 = new SalesPeople();
        s3.setName("S3");
        
        // Create subordinate for Manager B: Worker W2
        Worker w2 = new OffShiftWorker();
        w2.setName("W2");
        
        // Assign subordinates to Manager A
        managerA.getSubordinates().add(w1);
        managerA.getSubordinates().add(s1);
        managerA.getSubordinates().add(s2);
        managerA.getSubordinates().add(s3);
        managerA.getSubordinates().add(managerB); // Manager B is also a subordinate of Manager A
        
        // Assign subordinate to Manager B
        managerB.getSubordinates().add(w2);
        
        // Verify the number of subordinates for each manager
        assertEquals("The number of subordinates for Manager A is 5", 
                     5, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("The number of subordinates for Manager B is 1", 
                     1, managerB.getDirectSubordinateEmployeesCount());
    }
}