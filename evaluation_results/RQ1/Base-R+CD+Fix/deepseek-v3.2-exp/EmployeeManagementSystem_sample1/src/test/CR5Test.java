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
        
        // Create subordinate employees: Worker, SalesPerson, and Manager
        Worker w1 = new ShiftWorker();
        w1.setName("W1");
        
        SalesPeople s1 = new SalesPeople();
        s1.setName("S1");
        
        Manager m2 = new Manager();
        m2.setName("M2");
        
        // Assign subordinates to M1
        m1.addSubordinate(w1);
        m1.addSubordinate(s1);
        m1.addSubordinate(m2);
        
        // Verify the number of direct subordinates for Manager M1 is 3
        assertEquals("The number of direct subordinates for Manager M1 is 3.", 3, m1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase2_managerWithNoSubordinates() {
        // Create Manager M1 with no subordinates
        Manager m1 = new Manager();
        m1.setName("M1");
        
        // Verify the number of subordinates for the manager is 0
        assertEquals("The number of subordinates for the manager is 0.", 0, m1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase3_multipleManagersWithDifferentNumberOfSubordinates() {
        // Create Manager A with 3 subordinates
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        
        ShiftWorker sw1 = new ShiftWorker();
        sw1.setName("SW1");
        OffShiftWorker ow1 = new OffShiftWorker();
        ow1.setName("OW1");
        SalesPeople sp1 = new SalesPeople();
        sp1.setName("SP1");
        
        managerA.addSubordinate(sw1);
        managerA.addSubordinate(ow1);
        managerA.addSubordinate(sp1);
        
        // Create Manager B with 7 subordinates
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        
        // Create 2 ShiftWorkers
        ShiftWorker sw2 = new ShiftWorker();
        sw2.setName("SW2");
        ShiftWorker sw3 = new ShiftWorker();
        sw3.setName("SW3");
        
        // Create 2 OffShiftWorkers
        OffShiftWorker ow2 = new OffShiftWorker();
        ow2.setName("OW2");
        OffShiftWorker ow3 = new OffShiftWorker();
        ow3.setName("OW3");
        
        // Create 3 SalesPeople
        SalesPeople sp2 = new SalesPeople();
        sp2.setName("SP2");
        SalesPeople sp3 = new SalesPeople();
        sp3.setName("SP3");
        SalesPeople sp4 = new SalesPeople();
        sp4.setName("SP4");
        
        managerB.addSubordinate(sw2);
        managerB.addSubordinate(sw3);
        managerB.addSubordinate(ow2);
        managerB.addSubordinate(ow3);
        managerB.addSubordinate(sp2);
        managerB.addSubordinate(sp3);
        managerB.addSubordinate(sp4);
        
        // Verify the number of subordinates for each manager
        assertEquals("The number of subordinates for Manager A is 3.", 3, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("The number of subordinates for Manager B is 7.", 7, managerB.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase4_managerWithSingleSubordinate() {
        // Create Manager M1
        Manager m1 = new Manager();
        m1.setName("M1");
        
        // Create Worker W1
        Worker w1 = new ShiftWorker();
        w1.setName("W1");
        
        // Assign W1 as subordinate to M1
        m1.addSubordinate(w1);
        
        // Verify the number of subordinates for the manager is 1
        assertEquals("The number of subordinates for the manager is 1.", 1, m1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase5_nestedManagerSubordinates() {
        // Create Manager B and assign 1 subordinate employee: Worker W1
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        
        Worker w1 = new ShiftWorker();
        w1.setName("W1");
        
        managerB.addSubordinate(w1);
        
        // Create Manager A and assign 5 subordinates: 1 Worker, 3 SalesPerson, and 1 Manager B
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        
        Worker w2 = new ShiftWorker();
        w2.setName("W2");
        
        SalesPeople s1 = new SalesPeople();
        s1.setName("S1");
        SalesPeople s2 = new SalesPeople();
        s2.setName("S2");
        SalesPeople s3 = new SalesPeople();
        s3.setName("S3");
        
        managerA.addSubordinate(w2);
        managerA.addSubordinate(s1);
        managerA.addSubordinate(s2);
        managerA.addSubordinate(s3);
        managerA.addSubordinate(managerB);
        
        // Verify the number of subordinates for each manager
        assertEquals("The number of subordinates for Manager A is 5.", 5, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("The number of subordinates for Manager B is 1.", 1, managerB.getDirectSubordinateEmployeesCount());
    }
}