import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    @Test
    public void testCase1_SingleManagerWithMultipleSubordinates() {
        // Setup: Create Manager M1 and assign Worker W1, SalesPerson S1, and Manager M2 as direct subordinates
        Manager managerM1 = new Manager();
        Worker workerW1 = new OffShiftWorker();
        SalesPeople salesPersonS1 = new SalesPeople();
        Manager managerM2 = new Manager();
        
        List<Employee> subordinates = new ArrayList<>();
        subordinates.add(workerW1);
        subordinates.add(salesPersonS1);
        subordinates.add(managerM2);
        managerM1.setSubordinates(subordinates);
        
        // Expected Output: The number of direct subordinates for Manager M1 is 3
        assertEquals("The number of direct subordinates for Manager M1 should be 3", 
                     3, managerM1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase2_ManagerWithNoSubordinates() {
        // Setup: Create Manager M1 with no subordinates
        Manager managerM1 = new Manager();
        
        // Expected Output: The number of subordinates for the manager is 0
        assertEquals("The number of subordinates for the manager should be 0", 
                     0, managerM1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase3_MultipleManagersWithDifferentNumberOfSubordinates() {
        // Setup: Create Manager A with 3 subordinates and Manager B with 7 subordinates
        Manager managerA = new Manager();
        Manager managerB = new Manager();
        
        // Manager A subordinates: 1 ShiftWorker, 1 OffShiftWorker, 1 SalesPeople
        List<Employee> subordinatesA = new ArrayList<>();
        subordinatesA.add(new ShiftWorker());
        subordinatesA.add(new OffShiftWorker());
        subordinatesA.add(new SalesPeople());
        managerA.setSubordinates(subordinatesA);
        
        // Manager B subordinates: 2 ShiftWorker, 2 OffShiftWorker, 3 SalesPeople
        List<Employee> subordinatesB = new ArrayList<>();
        subordinatesB.add(new ShiftWorker());
        subordinatesB.add(new ShiftWorker());
        subordinatesB.add(new OffShiftWorker());
        subordinatesB.add(new OffShiftWorker());
        subordinatesB.add(new SalesPeople());
        subordinatesB.add(new SalesPeople());
        subordinatesB.add(new SalesPeople());
        managerB.setSubordinates(subordinatesB);
        
        // Expected Output: The number of subordinates for Manager A is 3. The number of subordinates for Manager B is 7
        assertEquals("The number of subordinates for Manager A should be 3", 
                     3, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("The number of subordinates for Manager B should be 7", 
                     7, managerB.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase4_ManagerWithSingleSubordinate() {
        // Setup: Create Manager M1 and assign Worker W1 as subordinate
        Manager managerM1 = new Manager();
        Worker workerW1 = new OffShiftWorker();
        
        List<Employee> subordinates = new ArrayList<>();
        subordinates.add(workerW1);
        managerM1.setSubordinates(subordinates);
        
        // Expected Output: The number of subordinates for the manager is 1
        assertEquals("The number of subordinates for the manager should be 1", 
                     1, managerM1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase5_MultipleManagersWithNestedSubordinates() {
        // Setup: Create Manager A with 5 subordinates and Manager B with 1 subordinate
        Manager managerA = new Manager();
        Manager managerB = new Manager();
        Worker workerW1 = new OffShiftWorker();
        SalesPeople salesPersonS1 = new SalesPeople();
        SalesPeople salesPersonS2 = new SalesPeople();
        SalesPeople salesPersonS3 = new SalesPeople();
        
        // Manager B has 1 subordinate employee: Worker W1
        List<Employee> subordinatesB = new ArrayList<>();
        subordinatesB.add(workerW1);
        managerB.setSubordinates(subordinatesB);
        
        // Manager A has 5 subordinates: 1 Worker, 3 SalesPerson and 1 Manager B
        List<Employee> subordinatesA = new ArrayList<>();
        subordinatesA.add(workerW1);
        subordinatesA.add(salesPersonS1);
        subordinatesA.add(salesPersonS2);
        subordinatesA.add(salesPersonS3);
        subordinatesA.add(managerB);
        managerA.setSubordinates(subordinatesA);
        
        // Expected Output: The number of subordinates for Manager A is 5. The number of subordinates for Manager B is 1
        assertEquals("The number of subordinates for Manager A should be 5", 
                     5, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("The number of subordinates for Manager B should be 1", 
                     1, managerB.getDirectSubordinateEmployeesCount());
    }
}