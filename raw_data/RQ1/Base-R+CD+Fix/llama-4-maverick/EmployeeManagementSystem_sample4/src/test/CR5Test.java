import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR5Test {
    
    @Test
    public void testCase1_SingleManagerWithMultipleSubordinates() {
        // Create Manager M1
        Manager m1 = new Manager();
        m1.setName("M1");
        
        // Create subordinates: Worker W1, SalesPerson S1, and Manager M2
        Worker w1 = new ShiftWorker();
        w1.setName("W1");
        
        SalesPeople s1 = new SalesPeople();
        s1.setName("S1");
        
        Manager m2 = new Manager();
        m2.setName("M2");
        
        // Assign subordinates to M1
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
        // Create Manager M1
        Manager m1 = new Manager();
        m1.setName("M1");
        
        // Do not assign any subordinates (default empty list)
        
        // Verify the number of subordinates for the manager is 0
        assertEquals("The number of subordinates for the manager is 0.", 0, m1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase3_MultipleManagersWithDifferentNumberOfSubordinates() {
        // Create Manager A and assign 3 subordinates
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
        
        // Create Manager B and assign 7 subordinates
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
        
        // Verify the number of subordinates for Manager A is 3
        assertEquals("The number of subordinates for Manager A is 3.", 3, managerA.getDirectSubordinateEmployeesCount());
        
        // Verify the number of subordinates for Manager B is 7
        assertEquals("The number of subordinates for Manager B is 7.", 7, managerB.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase4_ManagerWithSingleSubordinate() {
        // Create Manager M1
        Manager m1 = new Manager();
        m1.setName("M1");
        
        // Create Worker W1
        Worker w1 = new ShiftWorker();
        w1.setName("W1");
        
        // Assign W1 as subordinate to M1
        List<Employee> subordinates = new ArrayList<>();
        subordinates.add(w1);
        m1.setSubordinates(subordinates);
        
        // Verify the number of subordinates for the manager is 1
        assertEquals("The number of subordinates for the manager is 1.", 1, m1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase5_MultipleManagersWithDifferentNumberOfSubordinatesComplex() {
        // Create Manager A and Manager B
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        
        // Create Worker W1 and SalesPerson S1, S2, S3
        Worker w1 = new ShiftWorker();
        w1.setName("W1");
        
        SalesPeople s1 = new SalesPeople();
        s1.setName("S1");
        
        SalesPeople s2 = new SalesPeople();
        s2.setName("S2");
        
        SalesPeople s3 = new SalesPeople();
        s3.setName("S3");
        
        // Create Worker W2 for Manager B
        Worker w2 = new ShiftWorker();
        w2.setName("W2");
        
        // Assign subordinates to Manager A: Worker W1, SalesPerson S1, S2, S3, Manager B
        List<Employee> subordinatesA = new ArrayList<>();
        subordinatesA.add(w1);
        subordinatesA.add(s1);
        subordinatesA.add(s2);
        subordinatesA.add(s3);
        subordinatesA.add(managerB);
        managerA.setSubordinates(subordinatesA);
        
        // Assign subordinate to Manager B: Worker W2
        List<Employee> subordinatesB = new ArrayList<>();
        subordinatesB.add(w2);
        managerB.setSubordinates(subordinatesB);
        
        // Verify the number of subordinates for Manager A is 5
        assertEquals("The number of subordinates for Manager A is 5.", 5, managerA.getDirectSubordinateEmployeesCount());
        
        // Verify the number of subordinates for Manager B is 1
        assertEquals("The number of subordinates for Manager B is 1.", 1, managerB.getDirectSubordinateEmployeesCount());
    }
}