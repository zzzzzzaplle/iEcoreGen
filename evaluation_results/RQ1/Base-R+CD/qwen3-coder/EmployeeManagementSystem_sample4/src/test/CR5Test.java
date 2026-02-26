import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR5Test {
    
    private Manager managerA;
    private Manager managerB;
    private Worker worker1;
    private Worker worker2;
    private Worker worker3;
    private SalesPeople salesPerson1;
    private SalesPeople salesPerson2;
    private SalesPeople salesPerson3;
    private ShiftWorker shiftWorker1;
    private ShiftWorker shiftWorker2;
    private OffShiftWorker offShiftWorker1;
    private OffShiftWorker offShiftWorker2;
    
    @Before
    public void setUp() {
        // Initialize all employee objects
        managerA = new Manager();
        managerB = new Manager();
        worker1 = new ShiftWorker();
        worker2 = new OffShiftWorker();
        worker3 = new OffShiftWorker();
        salesPerson1 = new SalesPeople();
        salesPerson2 = new SalesPeople();
        salesPerson3 = new SalesPeople();
        shiftWorker1 = new ShiftWorker();
        shiftWorker2 = new ShiftWorker();
        offShiftWorker1 = new OffShiftWorker();
        offShiftWorker2 = new OffShiftWorker();
    }
    
    @Test
    public void testCase1_singleManagerWithMultipleSubordinates() {
        // Create Manager M1
        Manager m1 = new Manager();
        
        // Create subordinates: 1 Worker, 1 SalesPerson, 1 Manager
        Worker w1 = new ShiftWorker();
        SalesPeople s1 = new SalesPeople();
        Manager m2 = new Manager();
        
        // Assign subordinates to M1
        List<Employee> subordinates = new ArrayList<>();
        subordinates.add(w1);
        subordinates.add(s1);
        subordinates.add(m2);
        m1.setSubordinates(subordinates);
        
        // Test that the number of direct subordinates is 3
        assertEquals(3, m1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase2_managerWithNoSubordinates() {
        // Create Manager M1 with no subordinates
        Manager m1 = new Manager();
        // By default, subordinates list is empty
        
        // Test that the number of subordinates is 0
        assertEquals(0, m1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase3_multipleManagersWithDifferentNumberOfSubordinates() {
        // Create Manager A with 3 subordinates
        List<Employee> subordinatesA = new ArrayList<>();
        subordinatesA.add(shiftWorker1);
        subordinatesA.add(offShiftWorker1);
        subordinatesA.add(salesPerson1);
        managerA.setSubordinates(subordinatesA);
        
        // Create Manager B with 7 subordinates
        List<Employee> subordinatesB = new ArrayList<>();
        subordinatesB.add(shiftWorker1);
        subordinatesB.add(shiftWorker2);
        subordinatesB.add(offShiftWorker1);
        subordinatesB.add(offShiftWorker2);
        subordinatesB.add(salesPerson1);
        subordinatesB.add(salesPerson2);
        subordinatesB.add(salesPerson3);
        managerB.setSubordinates(subordinatesB);
        
        // Test that Manager A has 3 subordinates
        assertEquals(3, managerA.getDirectSubordinateEmployeesCount());
        
        // Test that Manager B has 7 subordinates
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
        
        // Test that the number of subordinates is 1
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
        
        // Create Manager B and assign 1 subordinate: Worker W1
        List<Employee> subordinatesB = new ArrayList<>();
        subordinatesB.add(w1);
        managerB.setSubordinates(subordinatesB);
        
        // Create Manager A and assign worker W1, SalesPeople (S1, S2, S3), Manager B as direct subordinates
        List<Employee> subordinatesA = new ArrayList<>();
        subordinatesA.add(w1);
        subordinatesA.add(s1);
        subordinatesA.add(s2);
        subordinatesA.add(s3);
        subordinatesA.add(managerB);
        managerA.setSubordinates(subordinatesA);
        
        // Test that Manager A has 5 direct subordinates
        assertEquals(5, managerA.getDirectSubordinateEmployeesCount());
        
        // Test that Manager B has 1 direct subordinate
        assertEquals(1, managerB.getDirectSubordinateEmployeesCount());
    }
}