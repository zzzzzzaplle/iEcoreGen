import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR5Test {
    
    private SimpleDateFormat dateFormat;
    private Date testDate;
    
    @Before
    public void setUp() throws ParseException {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        testDate = dateFormat.parse("1990-01-01 00:00:00");
    }
    
    @Test
    public void testCase1_SingleManagerWithMultipleSubordinates() throws ParseException {
        // Create Manager M1
        Manager manager = new Manager("IT", "John Manager", testDate, "123-45-6789", 75000.0, "Senior Manager");
        
        // Create subordinate employees
        Worker worker = new OffShiftWorker("IT", "Worker1", testDate, "111-11-1111", 40, 25.0);
        SalesPeople salesPerson = new SalesPeople("Sales", "SalesPerson1", testDate, "222-22-2222", 50000.0, 100000.0, 5.0);
        Manager subordinateManager = new Manager("IT", "Sub Manager", testDate, "333-33-3333", 60000.0, "Junior Manager");
        
        // Assign subordinates to manager
        manager.addSubordinate(worker);
        manager.addSubordinate(salesPerson);
        manager.addSubordinate(subordinateManager);
        
        // Verify the number of direct subordinates
        assertEquals("The number of direct subordinates for Manager M1 is 3.", 3, manager.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase2_ManagerWithNoSubordinates() throws ParseException {
        // Create Manager M1 with no subordinates
        Manager manager = new Manager("HR", "Jane Manager", testDate, "444-44-4444", 80000.0, "HR Manager");
        
        // Verify the number of direct subordinates is 0
        assertEquals("The number of subordinates for the manager is 0.", 0, manager.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase3_MultipleManagersWithDifferentNumberOfSubordinates() throws ParseException {
        // Create Manager A
        Manager managerA = new Manager("Production", "Manager A", testDate, "555-55-5555", 90000.0, "Production Manager");
        
        // Create Manager B
        Manager managerB = new Manager("Sales", "Manager B", testDate, "666-66-6666", 85000.0, "Sales Manager");
        
        // Create subordinates for Manager A (1 ShiftWorker, 1 OffShiftWorker, 1 SalesPeople)
        ShiftWorker shiftWorkerA1 = new ShiftWorker("Production", "ShiftWorker1", testDate, "777-77-7777", 40, 30.0, 100.0);
        OffShiftWorker offShiftWorkerA1 = new OffShiftWorker("Production", "OffShiftWorker1", testDate, "888-88-8888", 35, 28.0);
        SalesPeople salesPersonA1 = new SalesPeople("Sales", "SalesPersonA1", testDate, "999-99-9999", 45000.0, 80000.0, 4.0);
        
        managerA.addSubordinate(shiftWorkerA1);
        managerA.addSubordinate(offShiftWorkerA1);
        managerA.addSubordinate(salesPersonA1);
        
        // Create subordinates for Manager B (2 ShiftWorker, 2 OffShiftWorker, 3 SalesPeople)
        ShiftWorker shiftWorkerB1 = new ShiftWorker("Production", "ShiftWorkerB1", testDate, "101-10-1010", 40, 32.0, 120.0);
        ShiftWorker shiftWorkerB2 = new ShiftWorker("Production", "ShiftWorkerB2", testDate, "111-11-1110", 38, 31.0, 110.0);
        OffShiftWorker offShiftWorkerB1 = new OffShiftWorker("Production", "OffShiftWorkerB1", testDate, "121-12-1212", 36, 29.0);
        OffShiftWorker offShiftWorkerB2 = new OffShiftWorker("Production", "OffShiftWorkerB2", testDate, "131-13-1313", 37, 27.0);
        SalesPeople salesPersonB1 = new SalesPeople("Sales", "SalesPersonB1", testDate, "141-14-1414", 48000.0, 90000.0, 4.5);
        SalesPeople salesPersonB2 = new SalesPeople("Sales", "SalesPersonB2", testDate, "151-15-1515", 47000.0, 85000.0, 4.2);
        SalesPeople salesPersonB3 = new SalesPeople("Sales", "SalesPersonB3", testDate, "161-16-1616", 46000.0, 82000.0, 4.0);
        
        managerB.addSubordinate(shiftWorkerB1);
        managerB.addSubordinate(shiftWorkerB2);
        managerB.addSubordinate(offShiftWorkerB1);
        managerB.addSubordinate(offShiftWorkerB2);
        managerB.addSubordinate(salesPersonB1);
        managerB.addSubordinate(salesPersonB2);
        managerB.addSubordinate(salesPersonB3);
        
        // Verify the number of subordinates for each manager
        assertEquals("The number of subordinates for Manager A is 3.", 3, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("The number of subordinates for Manager B is 7.", 7, managerB.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase4_ManagerWithSingleSubordinate() throws ParseException {
        // Create Manager M1
        Manager manager = new Manager("Finance", "Finance Manager", testDate, "171-17-1717", 95000.0, "Finance Director");
        
        // Create Worker W1
        Worker worker = new ShiftWorker("Finance", "Worker W1", testDate, "181-18-1818", 40, 35.0, 150.0);
        
        // Assign Worker W1 as subordinate
        manager.addSubordinate(worker);
        
        // Verify the number of subordinates
        assertEquals("The number of subordinates for the manager is 1.", 1, manager.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase5_MultipleManagersWithNestedSubordinates() throws ParseException {
        // Create Manager A
        Manager managerA = new Manager("Operations", "Manager A", testDate, "191-19-1919", 100000.0, "Operations Director");
        
        // Create Manager B
        Manager managerB = new Manager("Operations", "Manager B", testDate, "202-20-2020", 80000.0, "Operations Manager");
        
        // Create Worker W1
        Worker workerW1 = new OffShiftWorker("Operations", "Worker W1", testDate, "212-21-2121", 40, 30.0);
        
        // Create SalesPerson S1, S2, S3
        SalesPeople salesPersonS1 = new SalesPeople("Sales", "SalesPerson S1", testDate, "222-22-2220", 50000.0, 100000.0, 5.0);
        SalesPeople salesPersonS2 = new SalesPeople("Sales", "SalesPerson S2", testDate, "232-23-2323", 52000.0, 110000.0, 5.2);
        SalesPeople salesPersonS3 = new SalesPeople("Sales", "SalesPerson S3", testDate, "242-24-2424", 48000.0, 95000.0, 4.8);
        
        // Assign subordinates to Manager B
        managerB.addSubordinate(workerW1);
        
        // Assign subordinates to Manager A (Worker W1, SalesPerson S1, S2, S3, Manager B)
        managerA.addSubordinate(workerW1);
        managerA.addSubordinate(salesPersonS1);
        managerA.addSubordinate(salesPersonS2);
        managerA.addSubordinate(salesPersonS3);
        managerA.addSubordinate(managerB);
        
        // Verify the number of subordinates for each manager
        assertEquals("The number of subordinates for Manager A is 5.", 5, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("The number of subordinates for Manager B is 1.", 1, managerB.getDirectSubordinateEmployeesCount());
    }
}