import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR5Test {
    
    private Manager managerA;
    private Manager managerB;
    private Worker workerW1;
    private SalesPeople salesPersonS1;
    private Manager managerM2;
    private ShiftWorker shiftWorker1;
    private OffShiftWorker offShiftWorker1;
    private SalesPeople salesPerson1;
    private ShiftWorker shiftWorker2;
    private OffShiftWorker offShiftWorker2;
    private SalesPeople salesPerson2;
    private SalesPeople salesPerson3;
    
    @Before
    public void setUp() throws Exception {
        // Initialize date formatter for creating birth dates
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // Create base employees that will be used in tests
        workerW1 = new OffShiftWorker("PRODUCTION", "Worker W1", sdf.parse("1990-01-01 00:00:00"), "123-45-6789", 40, 25.0);
        salesPersonS1 = new SalesPeople("DELIVERY", "Sales Person S1", sdf.parse("1985-05-15 00:00:00"), "987-65-4321", 3000, 50000, 5.0);
        managerM2 = new Manager("CONTROL", "Manager M2", sdf.parse("1980-12-31 00:00:00"), "555-55-5555", 80000, "Assistant Manager");
        
        // Additional employees for multiple manager tests
        shiftWorker1 = new ShiftWorker("PRODUCTION", "Shift Worker 1", sdf.parse("1992-03-15 00:00:00"), "111-11-1111", 35, 30.0, 200.0);
        offShiftWorker1 = new OffShiftWorker("PRODUCTION", "Off Shift Worker 1", sdf.parse("1991-07-20 00:00:00"), "222-22-2222", 40, 25.0);
        salesPerson1 = new SalesPeople("DELIVERY", "Sales Person 1", sdf.parse("1988-09-10 00:00:00"), "333-33-3333", 3500, 60000, 4.5);
        
        shiftWorker2 = new ShiftWorker("PRODUCTION", "Shift Worker 2", sdf.parse("1993-04-25 00:00:00"), "444-44-4444", 38, 28.0, 180.0);
        offShiftWorker2 = new OffShiftWorker("PRODUCTION", "Off Shift Worker 2", sdf.parse("1994-11-30 00:00:00"), "555-55-5555", 42, 26.0);
        salesPerson2 = new SalesPeople("DELIVERY", "Sales Person 2", sdf.parse("1987-02-14 00:00:00"), "666-66-6666", 3200, 55000, 5.5);
        salesPerson3 = new SalesPeople("DELIVERY", "Sales Person 3", sdf.parse("1989-06-18 00:00:00"), "777-77-7777", 3400, 58000, 4.8);
    }
    
    @Test
    public void testCase1_SingleManagerWithMultipleSubordinates() throws Exception {
        // Setup: Create Manager M1 and assign 3 direct subordinates
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Manager managerM1 = new Manager("CONTROL", "Manager M1", sdf.parse("1975-03-20 00:00:00"), "111-11-1111", 90000, "Senior Manager");
        
        // Assign Worker W1, SalesPerson S1, and Manager M2 as direct subordinates to M1
        managerM1.getSubordinates().add(workerW1);
        managerM1.getSubordinates().add(salesPersonS1);
        managerM1.getSubordinates().add(managerM2);
        
        // Expected Output: The number of direct subordinates for Manager M1 is 3
        assertEquals("The number of direct subordinates for Manager M1 should be 3", 3, managerM1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase2_ManagerWithNoSubordinates() throws Exception {
        // Setup: Create Manager M1 with no subordinates
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Manager managerM1 = new Manager("CONTROL", "Manager M1", sdf.parse("1975-03-20 00:00:00"), "111-11-1111", 90000, "Senior Manager");
        
        // Do not assign any subordinates (subordinates list remains empty)
        
        // Expected Output: The number of subordinates for the manager is 0
        assertEquals("The number of subordinates for the manager should be 0", 0, managerM1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase3_MultipleManagersWithDifferentNumberOfSubordinates() throws Exception {
        // Setup: Create Manager A and Manager B with different numbers of subordinates
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        managerA = new Manager("PRODUCTION", "Manager A", sdf.parse("1978-08-15 00:00:00"), "888-88-8888", 85000, "Production Manager");
        managerB = new Manager("DELIVERY", "Manager B", sdf.parse("1982-12-25 00:00:00"), "999-99-9999", 78000, "Delivery Manager");
        
        // Assign 3 subordinates to Manager A: 1 ShiftWorker, 1 OffShiftWorker, 1 SalesPeople
        managerA.getSubordinates().add(shiftWorker1);
        managerA.getSubordinates().add(offShiftWorker1);
        managerA.getSubordinates().add(salesPerson1);
        
        // Assign 7 subordinates to Manager B: 2 ShiftWorker, 2 OffShiftWorker, 3 SalesPeople
        managerB.getSubordinates().add(shiftWorker1);
        managerB.getSubordinates().add(shiftWorker2);
        managerB.getSubordinates().add(offShiftWorker1);
        managerB.getSubordinates().add(offShiftWorker2);
        managerB.getSubordinates().add(salesPerson1);
        managerB.getSubordinates().add(salesPerson2);
        managerB.getSubordinates().add(salesPerson3);
        
        // Expected Output: The number of subordinates for Manager A is 3. The number of subordinates for Manager B is 7.
        assertEquals("The number of subordinates for Manager A should be 3", 3, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("The number of subordinates for Manager B should be 7", 7, managerB.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase4_ManagerWithSingleSubordinate() throws Exception {
        // Setup: Create Manager M1 with 1 direct subordinate employee: 1 Worker
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Manager managerM1 = new Manager("CONTROL", "Manager M1", sdf.parse("1975-03-20 00:00:00"), "111-11-1111", 90000, "Senior Manager");
        
        // Assign Worker W1 as subordinate
        managerM1.getSubordinates().add(workerW1);
        
        // Expected Output: The number of subordinates for the manager is 1
        assertEquals("The number of subordinates for the manager should be 1", 1, managerM1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase5_MultipleManagersWithNestedSubordinates() throws Exception {
        // Setup: Create Manager A and Manager B with nested structure
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        managerA = new Manager("CONTROL", "Manager A", sdf.parse("1978-08-15 00:00:00"), "888-88-8888", 95000, "Senior Manager");
        managerB = new Manager("PRODUCTION", "Manager B", sdf.parse("1982-12-25 00:00:00"), "999-99-9999", 82000, "Production Manager");
        
        // Create additional sales people for Manager A
        SalesPeople salesPersonS2 = new SalesPeople("DELIVERY", "Sales Person S2", sdf.parse("1986-04-12 00:00:00"), "222-22-2222", 3200, 45000, 4.0);
        SalesPeople salesPersonS3 = new SalesPeople("DELIVERY", "Sales Person S3", sdf.parse("1984-07-23 00:00:00"), "333-33-3333", 3400, 52000, 5.0);
        
        // Assign 5 subordinates to Manager A: 1 Worker, 3 SalesPerson, and 1 Manager B
        managerA.getSubordinates().add(workerW1);
        managerA.getSubordinates().add(salesPersonS1);
        managerA.getSubordinates().add(salesPersonS2);
        managerA.getSubordinates().add(salesPersonS3);
        managerA.getSubordinates().add(managerB);
        
        // Create another worker for Manager B
        Worker workerW2 = new OffShiftWorker("PRODUCTION", "Worker W2", sdf.parse("1991-02-28 00:00:00"), "444-44-4444", 45, 22.0);
        
        // Assign 1 subordinate to Manager B: Worker W1
        managerB.getSubordinates().add(workerW1);
        
        // Expected Output: The number of subordinates for Manager A is 5. The number of subordinates for Manager B is 1.
        assertEquals("The number of subordinates for Manager A should be 5", 5, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("The number of subordinates for Manager B should be 1", 1, managerB.getDirectSubordinateEmployeesCount());
    }
}