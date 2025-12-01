// IMPORTANT: Do not include package declaration
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR5Test {
    
    @Test
    public void testCase1_singleManagerWithMultipleSubordinates() {
        // Create Manager M1
        Manager m1 = new Manager();
        
        // Create subordinates
        Worker w1 = new ShiftWorker();
        SalesPeople s1 = new SalesPeople();
        Manager m2 = new Manager();
        
        // Assign subordinates to M1
        List<Employee> subordinates = new ArrayList<>();
        subordinates.add(w1);
        subordinates.add(s1);
        subordinates.add(m2);
        m1.setSubordinates(subordinates);
        
        // Test the number of direct subordinates
        assertEquals(3, m1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase2_managerWithNoSubordinates() {
        // Create Manager M1 with no subordinates
        Manager m1 = new Manager();
        
        // Test the number of subordinates is 0
        assertEquals(0, m1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase3_multipleManagersWithDifferentNumberOfSubordinates() {
        // Create Manager A with 3 subordinates
        Manager managerA = new Manager();
        Worker sw1 = new ShiftWorker();
        Worker osw1 = new OffShiftWorker();
        SalesPeople sp1 = new SalesPeople();
        
        List<Employee> subordinatesA = new ArrayList<>();
        subordinatesA.add(sw1);
        subordinatesA.add(osw1);
        subordinatesA.add(sp1);
        managerA.setSubordinates(subordinatesA);
        
        // Create Manager B with 7 subordinates
        Manager managerB = new Manager();
        ShiftWorker sw2 = new ShiftWorker();
        ShiftWorker sw3 = new ShiftWorker();
        OffShiftWorker osw2 = new OffShiftWorker();
        OffShiftWorker osw3 = new OffShiftWorker();
        SalesPeople sp2 = new SalesPeople();
        SalesPeople sp3 = new SalesPeople();
        SalesPeople sp4 = new SalesPeople();
        
        List<Employee> subordinatesB = new ArrayList<>();
        subordinatesB.add(sw2);
        subordinatesB.add(sw3);
        subordinatesB.add(osw2);
        subordinatesB.add(osw3);
        subordinatesB.add(sp2);
        subordinatesB.add(sp3);
        subordinatesB.add(sp4);
        managerB.setSubordinates(subordinatesB);
        
        // Test the number of subordinates for each manager
        assertEquals(3, managerA.getDirectSubordinateEmployeesCount());
        assertEquals(7, managerB.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase4_managerWithSingleSubordinate() {
        // Create Manager M1
        Manager m1 = new Manager();
        
        // Create Worker W1 as subordinate
        Worker w1 = new ShiftWorker();
        
        // Assign W1 as subordinate to M1
        List<Employee> subordinates = new ArrayList<>();
        subordinates.add(w1);
        m1.setSubordinates(subordinates);
        
        // Test the number of subordinates is 1
        assertEquals(1, m1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase5_multipleManagersWithDifferentNumberOfSubordinatesIncludingNested() {
        // Create Worker W1
        Worker w1 = new ShiftWorker();
        
        // Create SalesPeople S1, S2, S3
        SalesPeople s1 = new SalesPeople();
        SalesPeople s2 = new SalesPeople();
        SalesPeople s3 = new SalesPeople();
        
        // Create Manager B with 1 subordinate
        Manager managerB = new Manager();
        Worker w2 = new ShiftWorker();
        List<Employee> subordinatesB = new ArrayList<>();
        subordinatesB.add(w2);
        managerB.setSubordinates(subordinatesB);
        
        // Create Manager A with 5 subordinates (including Manager B)
        Manager managerA = new Manager();
        List<Employee> subordinatesA = new ArrayList<>();
        subordinatesA.add(w1);
        subordinatesA.add(s1);
        subordinatesA.add(s2);
        subordinatesA.add(s3);
        subordinatesA.add(managerB);
        managerA.setSubordinates(subordinatesA);
        
        // Test the number of subordinates for each manager
        assertEquals(5, managerA.getDirectSubordinateEmployeesCount());
        assertEquals(1, managerB.getDirectSubordinateEmployeesCount());
    }
}