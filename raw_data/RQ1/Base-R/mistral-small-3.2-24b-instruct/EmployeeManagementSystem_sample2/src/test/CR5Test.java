import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.Map;

public class CR5Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_singleManagerWithMultipleSubordinates() {
        // Create Manager M1
        Manager m1 = new Manager();
        m1.setName("M1");
        
        // Create subordinate employees
        Worker w1 = new NonShiftWorker();
        w1.setName("W1");
        
        SalesPerson s1 = new SalesPerson();
        s1.setName("S1");
        
        Manager m2 = new Manager();
        m2.setName("M2");
        
        // Assign subordinates to M1
        m1.addSubordinate(w1);
        m1.addSubordinate(s1);
        m1.addSubordinate(m2);
        
        // Add manager to company
        company.addEmployee(m1);
        
        // Get subordinates count for each manager
        Map<String, Integer> subordinatesMap = company.getNumberOfSubordinatesForManagers();
        
        // Verify results
        assertEquals("Should have one manager in the map", 1, subordinatesMap.size());
        assertEquals("Manager M1 should have 3 subordinates", Integer.valueOf(3), subordinatesMap.get("M1"));
    }
    
    @Test
    public void testCase2_managerWithNoSubordinates() {
        // Create Manager M1 with no subordinates
        Manager m1 = new Manager();
        m1.setName("M1");
        
        // Add manager to company
        company.addEmployee(m1);
        
        // Get subordinates count for each manager
        Map<String, Integer> subordinatesMap = company.getNumberOfSubordinatesForManagers();
        
        // Verify results
        assertEquals("Should have one manager in the map", 1, subordinatesMap.size());
        assertEquals("Manager M1 should have 0 subordinates", Integer.valueOf(0), subordinatesMap.get("M1"));
    }
    
    @Test
    public void testCase3_multipleManagersWithDifferentNumberOfSubordinates() {
        // Create Manager A
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        
        // Create subordinates for Manager A
        ShiftWorker sw1 = new ShiftWorker();
        sw1.setName("SW1");
        
        NonShiftWorker nsw1 = new NonShiftWorker();
        nsw1.setName("NSW1");
        
        SalesPerson sp1 = new SalesPerson();
        sp1.setName("SP1");
        
        // Assign subordinates to Manager A
        managerA.addSubordinate(sw1);
        managerA.addSubordinate(nsw1);
        managerA.addSubordinate(sp1);
        
        // Create Manager B
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        
        // Create subordinates for Manager B
        ShiftWorker sw2 = new ShiftWorker();
        sw2.setName("SW2");
        ShiftWorker sw3 = new ShiftWorker();
        sw3.setName("SW3");
        
        NonShiftWorker nsw2 = new NonShiftWorker();
        nsw2.setName("NSW2");
        NonShiftWorker nsw3 = new NonShiftWorker();
        nsw3.setName("NSW3");
        
        SalesPerson sp2 = new SalesPerson();
        sp2.setName("SP2");
        SalesPerson sp3 = new SalesPerson();
        sp3.setName("SP3");
        SalesPerson sp4 = new SalesPerson();
        sp4.setName("SP4");
        
        // Assign subordinates to Manager B
        managerB.addSubordinate(sw2);
        managerB.addSubordinate(sw3);
        managerB.addSubordinate(nsw2);
        managerB.addSubordinate(nsw3);
        managerB.addSubordinate(sp2);
        managerB.addSubordinate(sp3);
        managerB.addSubordinate(sp4);
        
        // Add both managers to company
        company.addEmployee(managerA);
        company.addEmployee(managerB);
        
        // Get subordinates count for each manager
        Map<String, Integer> subordinatesMap = company.getNumberOfSubordinatesForManagers();
        
        // Verify results
        assertEquals("Should have two managers in the map", 2, subordinatesMap.size());
        assertEquals("Manager A should have 3 subordinates", Integer.valueOf(3), subordinatesMap.get("Manager A"));
        assertEquals("Manager B should have 7 subordinates", Integer.valueOf(7), subordinatesMap.get("Manager B"));
    }
    
    @Test
    public void testCase4_managerWithSingleSubordinate() {
        // Create Manager M1
        Manager m1 = new Manager();
        m1.setName("M1");
        
        // Create subordinate employee
        Worker w1 = new ShiftWorker();
        w1.setName("W1");
        
        // Assign subordinate to M1
        m1.addSubordinate(w1);
        
        // Add manager to company
        company.addEmployee(m1);
        
        // Get subordinates count for each manager
        Map<String, Integer> subordinatesMap = company.getNumberOfSubordinatesForManagers();
        
        // Verify results
        assertEquals("Should have one manager in the map", 1, subordinatesMap.size());
        assertEquals("Manager M1 should have 1 subordinate", Integer.valueOf(1), subordinatesMap.get("M1"));
    }
    
    @Test
    public void testCase5_multipleManagersWithNestedSubordinates() {
        // Create Manager B first
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        
        // Create subordinate for Manager B
        Worker w1 = new NonShiftWorker();
        w1.setName("W1");
        managerB.addSubordinate(w1);
        
        // Create Manager A
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        
        // Create subordinates for Manager A
        Worker w2 = new NonShiftWorker();
        w2.setName("W2");
        
        SalesPerson s1 = new SalesPerson();
        s1.setName("S1");
        SalesPerson s2 = new SalesPerson();
        s2.setName("S2");
        SalesPerson s3 = new SalesPerson();
        s3.setName("S3");
        
        // Assign subordinates to Manager A (including Manager B)
        managerA.addSubordinate(w2);
        managerA.addSubordinate(s1);
        managerA.addSubordinate(s2);
        managerA.addSubordinate(s3);
        managerA.addSubordinate(managerB);
        
        // Add both managers to company
        company.addEmployee(managerA);
        company.addEmployee(managerB);
        
        // Get subordinates count for each manager
        Map<String, Integer> subordinatesMap = company.getNumberOfSubordinatesForManagers();
        
        // Verify results
        assertEquals("Should have two managers in the map", 2, subordinatesMap.size());
        assertEquals("Manager A should have 5 subordinates", Integer.valueOf(5), subordinatesMap.get("Manager A"));
        assertEquals("Manager B should have 1 subordinate", Integer.valueOf(1), subordinatesMap.get("Manager B"));
    }
}