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
        Worker shiftWorker1 = new ShiftWorker();
        Worker offShiftWorker1 = new OffShiftWorker();
        SalesPeople salesPeople1 = new SalesPeople();
        
        List<Employee> subordinatesA = new ArrayList<>();
        subordinatesA.add(shiftWorker1);
        subordinatesA.add(offShiftWorker1);
        subordinatesA.add(salesPeople1);
        managerA.setSubordinates(subordinatesA);
        
        // Create Manager B with 7 subordinates
        Manager managerB = new Manager();
        Worker shiftWorker2 = new ShiftWorker();
        Worker shiftWorker3 = new ShiftWorker();
        Worker offShiftWorker2 = new OffShiftWorker();
        Worker offShiftWorker3 = new OffShiftWorker();
        SalesPeople salesPeople2 = new SalesPeople();
        SalesPeople salesPeople3 = new SalesPeople();
        SalesPeople salesPeople4 = new SalesPeople();
        
        List<Employee> subordinatesB = new ArrayList<>();
        subordinatesB.add(shiftWorker2);
        subordinatesB.add(shiftWorker3);
        subordinatesB.add(offShiftWorker2);
        subordinatesB.add(offShiftWorker3);
        subordinatesB.add(salesPeople2);
        subordinatesB.add(salesPeople3);
        subordinatesB.add(salesPeople4);
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
        // Create Manager B with 1 subordinate
        Manager managerB = new Manager();
        Worker w1 = new ShiftWorker();
        
        List<Employee> subordinatesB = new ArrayList<>();
        subordinatesB.add(w1);
        managerB.setSubordinates(subordinatesB);
        
        // Create Manager A with 5 subordinates including Manager B
        Manager managerA = new Manager();
        Worker worker1 = new ShiftWorker();
        SalesPeople salesPerson1 = new SalesPeople();
        SalesPeople salesPerson2 = new SalesPeople();
        SalesPeople salesPerson3 = new SalesPeople();
        
        List<Employee> subordinatesA = new ArrayList<>();
        subordinatesA.add(worker1);
        subordinatesA.add(salesPerson1);
        subordinatesA.add(salesPerson2);
        subordinatesA.add(salesPerson3);
        subordinatesA.add(managerB);
        managerA.setSubordinates(subordinatesA);
        
        // Test the number of subordinates for each manager
        assertEquals(5, managerA.getDirectSubordinateEmployeesCount());
        assertEquals(1, managerB.getDirectSubordinateEmployeesCount());
    }
}