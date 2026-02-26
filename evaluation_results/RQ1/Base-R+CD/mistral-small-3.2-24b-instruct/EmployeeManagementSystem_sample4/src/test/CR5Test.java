import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class CR5Test {
    private Company company;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        company = new Company();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_SingleManagerWithMultipleSubordinates() throws Exception {
        // Setup: Create Manager M1
        Manager m1 = new Manager("PRODUCTION", "M1", dateFormat.parse("1980-01-01 00:00:00"), "SIN001", 50000.0, "Senior Manager");
        
        // Create subordinates: 1 Worker, 1 SalesPerson and 1 Manager
        OffShiftWorker w1 = new OffShiftWorker("PRODUCTION", "W1", dateFormat.parse("1990-01-01 00:00:00"), "SIN002", 40, 20.0);
        SalesPeople s1 = new SalesPeople("PRODUCTION", "S1", dateFormat.parse("1985-01-01 00:00:00"), "SIN003", 30000.0, 100000.0, 0.1);
        Manager m2 = new Manager("PRODUCTION", "M2", dateFormat.parse("1975-01-01 00:00:00"), "SIN004", 45000.0, "Junior Manager");
        
        // Add all employees to company
        List<Employee> employees = new ArrayList<>();
        employees.add(m1);
        employees.add(w1);
        employees.add(s1);
        employees.add(m2);
        company.setEmployees(employees);
        
        // Expected Output: The number of direct subordinates for Manager M1 is 3
        assertEquals("Manager M1 should have 3 direct subordinates", 3, m1.getDirectSubordinateEmployeesCount(company));
    }
    
    @Test
    public void testCase2_ManagerWithNoSubordinates() throws Exception {
        // Setup: Create Manager M1 with no subordinates
        Manager m1 = new Manager("CONTROL", "M1", dateFormat.parse("1980-01-01 00:00:00"), "SIN001", 50000.0, "Senior Manager");
        
        // Add only manager to company
        List<Employee> employees = new ArrayList<>();
        employees.add(m1);
        company.setEmployees(employees);
        
        // Expected Output: The number of subordinates for the manager is 0
        assertEquals("Manager M1 should have 0 direct subordinates", 0, m1.getDirectSubordinateEmployeesCount(company));
    }
    
    @Test
    public void testCase3_MultipleManagersWithDifferentNumberOfSubordinates() throws Exception {
        // Setup: Create Manager A with 3 subordinates
        Manager managerA = new Manager("DELIVERY", "ManagerA", dateFormat.parse("1970-01-01 00:00:00"), "SIN001", 60000.0, "Director");
        
        // Create subordinates for Manager A: 1 ShiftWorker, 1 OffShiftWorker, 1 SalesPeople
        ShiftWorker sw1 = new ShiftWorker("DELIVERY", "SW1", dateFormat.parse("1985-01-01 00:00:00"), "SIN002", 35, 25.0, 500.0);
        OffShiftWorker osw1 = new OffShiftWorker("DELIVERY", "OSW1", dateFormat.parse("1990-01-01 00:00:00"), "SIN003", 40, 22.0);
        SalesPeople sp1 = new SalesPeople("DELIVERY", "SP1", dateFormat.parse("1988-01-01 00:00:00"), "SIN004", 35000.0, 120000.0, 0.08);
        
        // Setup: Create Manager B with 7 subordinates
        Manager managerB = new Manager("PRODUCTION", "ManagerB", dateFormat.parse("1975-01-01 00:00:00"), "SIN005", 55000.0, "Supervisor");
        
        // Create subordinates for Manager B: 2 ShiftWorker, 2 OffShiftWorker, 3 SalesPeople
        ShiftWorker sw2 = new ShiftWorker("PRODUCTION", "SW2", dateFormat.parse("1986-01-01 00:00:00"), "SIN006", 38, 24.0, 450.0);
        ShiftWorker sw3 = new ShiftWorker("PRODUCTION", "SW3", dateFormat.parse("1987-01-01 00:00:00"), "SIN007", 37, 23.0, 400.0);
        OffShiftWorker osw2 = new OffShiftWorker("PRODUCTION", "OSW2", dateFormat.parse("1991-01-01 00:00:00"), "SIN008", 39, 21.0);
        OffShiftWorker osw3 = new OffShiftWorker("PRODUCTION", "OSW3", dateFormat.parse("1992-01-01 00:00:00"), "SIN009", 41, 20.0);
        SalesPeople sp2 = new SalesPeople("PRODUCTION", "SP2", dateFormat.parse("1989-01-01 00:00:00"), "SIN010", 32000.0, 90000.0, 0.09);
        SalesPeople sp3 = new SalesPeople("PRODUCTION", "SP3", dateFormat.parse("1990-01-01 00:00:00"), "SIN011", 33000.0, 95000.0, 0.1);
        SalesPeople sp4 = new SalesPeople("PRODUCTION", "SP4", dateFormat.parse("1991-01-01 00:00:00"), "SIN012", 34000.0, 100000.0, 0.11);
        
        // Add all employees to company
        List<Employee> employees = new ArrayList<>();
        employees.add(managerA);
        employees.add(sw1);
        employees.add(osw1);
        employees.add(sp1);
        employees.add(managerB);
        employees.add(sw2);
        employees.add(sw3);
        employees.add(osw2);
        employees.add(osw3);
        employees.add(sp2);
        employees.add(sp3);
        employees.add(sp4);
        company.setEmployees(employees);
        
        // Expected Output: The number of subordinates for Manager A is 3. The number of subordinates for Manager B is 7
        assertEquals("Manager A should have 3 direct subordinates", 3, managerA.getDirectSubordinateEmployeesCount(company));
        assertEquals("Manager B should have 7 direct subordinates", 7, managerB.getDirectSubordinateEmployeesCount(company));
    }
    
    @Test
    public void testCase4_ManagerWithSingleSubordinate() throws Exception {
        // Setup: Create Manager M1
        Manager m1 = new Manager("CONTROL", "M1", dateFormat.parse("1980-01-01 00:00:00"), "SIN001", 50000.0, "Senior Manager");
        
        // Create subordinate: 1 Worker
        OffShiftWorker w1 = new OffShiftWorker("CONTROL", "W1", dateFormat.parse("1990-01-01 00:00:00"), "SIN002", 40, 20.0);
        
        // Add employees to company
        List<Employee> employees = new ArrayList<>();
        employees.add(m1);
        employees.add(w1);
        company.setEmployees(employees);
        
        // Expected Output: The number of subordinates for the manager is 1
        assertEquals("Manager M1 should have 1 direct subordinate", 1, m1.getDirectSubordinateEmployeesCount(company));
    }
    
    @Test
    public void testCase5_MultipleManagersWithNestedSubordinates() throws Exception {
        // Setup: Create Manager A
        Manager managerA = new Manager("DELIVERY", "ManagerA", dateFormat.parse("1970-01-01 00:00:00"), "SIN001", 60000.0, "Director");
        
        // Create subordinates for Manager A: 1 Worker, 3 SalesPerson, 1 Manager B
        OffShiftWorker w1 = new OffShiftWorker("DELIVERY", "W1", dateFormat.parse("1990-01-01 00:00:00"), "SIN002", 40, 20.0);
        SalesPeople s1 = new SalesPeople("DELIVERY", "S1", dateFormat.parse("1985-01-01 00:00:00"), "SIN003", 30000.0, 100000.0, 0.1);
        SalesPeople s2 = new SalesPeople("DELIVERY", "S2", dateFormat.parse("1986-01-01 00:00:00"), "SIN004", 31000.0, 110000.0, 0.1);
        SalesPeople s3 = new SalesPeople("DELIVERY", "S3", dateFormat.parse("1987-01-01 00:00:00"), "SIN005", 32000.0, 120000.0, 0.1);
        Manager managerB = new Manager("DELIVERY", "ManagerB", dateFormat.parse("1975-01-01 00:00:00"), "SIN006", 45000.0, "Supervisor");
        
        // Setup: Create subordinate for Manager B: 1 Worker
        OffShiftWorker w2 = new OffShiftWorker("DELIVERY", "W2", dateFormat.parse("1991-01-01 00:00:00"), "SIN007", 35, 18.0);
        
        // Add all employees to company
        List<Employee> employees = new ArrayList<>();
        employees.add(managerA);
        employees.add(w1);
        employees.add(s1);
        employees.add(s2);
        employees.add(s3);
        employees.add(managerB);
        employees.add(w2);
        company.setEmployees(employees);
        
        // Expected Output: The number of subordinates for Manager A is 5. The number of subordinates for Manager B is 1
        assertEquals("Manager A should have 5 direct subordinates", 5, managerA.getDirectSubordinateEmployeesCount(company));
        assertEquals("Manager B should have 1 direct subordinate", 1, managerB.getDirectSubordinateEmployeesCount(company));
    }
}