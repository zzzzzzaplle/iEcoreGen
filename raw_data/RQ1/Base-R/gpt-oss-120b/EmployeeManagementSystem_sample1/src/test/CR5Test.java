import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
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
        
        // Create subordinates: Worker, SalesPerson, and Manager
        Worker w1 = new Worker();
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
        
        // Get subordinate counts
        Map<Manager, Integer> subordinateCounts = company.getSubordinateCounts();
        
        // Verify the number of direct subordinates for Manager M1 is 3
        assertEquals(1, subordinateCounts.size());
        assertEquals(Integer.valueOf(3), subordinateCounts.get(m1));
    }
    
    @Test
    public void testCase2_managerWithNoSubordinates() {
        // Create Manager M1 with no subordinates
        Manager m1 = new Manager();
        m1.setName("M1");
        
        // Add manager to company
        company.addEmployee(m1);
        
        // Get subordinate counts
        Map<Manager, Integer> subordinateCounts = company.getSubordinateCounts();
        
        // Verify the number of subordinates for the manager is 0
        assertEquals(1, subordinateCounts.size());
        assertEquals(Integer.valueOf(0), subordinateCounts.get(m1));
    }
    
    @Test
    public void testCase3_multipleManagersWithDifferentNumberOfSubordinates() {
        // Create Manager A
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        
        // Create subordinates for Manager A: ShiftWorker, OffShiftWorker, SalesPerson
        ShiftWorker sw1 = new ShiftWorker();
        sw1.setName("SW1");
        
        OffShiftWorker osw1 = new OffShiftWorker();
        osw1.setName("OSW1");
        
        SalesPerson sp1 = new SalesPerson();
        sp1.setName("SP1");
        
        // Assign 3 subordinates to Manager A
        managerA.addSubordinate(sw1);
        managerA.addSubordinate(osw1);
        managerA.addSubordinate(sp1);
        
        // Create Manager B
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        
        // Create 7 subordinates for Manager B: 2 ShiftWorker, 2 OffShiftWorker, 3 SalesPerson
        for (int i = 1; i <= 2; i++) {
            ShiftWorker sw = new ShiftWorker();
            sw.setName("SW" + i);
            managerB.addSubordinate(sw);
        }
        
        for (int i = 1; i <= 2; i++) {
            OffShiftWorker osw = new OffShiftWorker();
            osw.setName("OSW" + i);
            managerB.addSubordinate(osw);
        }
        
        for (int i = 1; i <= 3; i++) {
            SalesPerson sp = new SalesPerson();
            sp.setName("SP" + i);
            managerB.addSubordinate(sp);
        }
        
        // Add both managers to company
        company.addEmployee(managerA);
        company.addEmployee(managerB);
        
        // Get subordinate counts
        Map<Manager, Integer> subordinateCounts = company.getSubordinateCounts();
        
        // Verify the number of subordinates for Manager A is 3 and Manager B is 7
        assertEquals(2, subordinateCounts.size());
        assertEquals(Integer.valueOf(3), subordinateCounts.get(managerA));
        assertEquals(Integer.valueOf(7), subordinateCounts.get(managerB));
    }
    
    @Test
    public void testCase4_managerWithSingleSubordinate() {
        // Create Manager M1
        Manager m1 = new Manager();
        m1.setName("M1");
        
        // Create Worker W1 as subordinate
        Worker w1 = new Worker();
        w1.setName("W1");
        
        // Assign W1 as subordinate to M1
        m1.addSubordinate(w1);
        
        // Add manager to company
        company.addEmployee(m1);
        
        // Get subordinate counts
        Map<Manager, Integer> subordinateCounts = company.getSubordinateCounts();
        
        // Verify the number of subordinates for the manager is 1
        assertEquals(1, subordinateCounts.size());
        assertEquals(Integer.valueOf(1), subordinateCounts.get(m1));
    }
    
    @Test
    public void testCase5_multipleManagersWithNestedSubordinates() {
        // Create Manager A
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        
        // Create Worker W1
        Worker w1 = new Worker();
        w1.setName("W1");
        
        // Create SalesPerson S1, S2, S3
        SalesPerson s1 = new SalesPerson();
        s1.setName("S1");
        
        SalesPerson s2 = new SalesPerson();
        s2.setName("S2");
        
        SalesPerson s3 = new SalesPerson();
        s3.setName("S3");
        
        // Create Manager B
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        
        // Assign subordinates to Manager A: Worker W1, SalesPerson S1, S2, S3, Manager B
        managerA.addSubordinate(w1);
        managerA.addSubordinate(s1);
        managerA.addSubordinate(s2);
        managerA.addSubordinate(s3);
        managerA.addSubordinate(managerB);
        
        // Create Worker W1 for Manager B
        Worker w1ForB = new Worker();
        w1ForB.setName("W1ForB");
        
        // Assign Worker W1 as subordinate to Manager B
        managerB.addSubordinate(w1ForB);
        
        // Add both managers to company
        company.addEmployee(managerA);
        company.addEmployee(managerB);
        
        // Get subordinate counts
        Map<Manager, Integer> subordinateCounts = company.getSubordinateCounts();
        
        // Verify the number of subordinates for Manager A is 5 and Manager B is 1
        assertEquals(2, subordinateCounts.size());
        assertEquals(Integer.valueOf(5), subordinateCounts.get(managerA));
        assertEquals(Integer.valueOf(1), subordinateCounts.get(managerB));
    }
}