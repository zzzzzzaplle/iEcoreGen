import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.List;

public class CR5Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_singleManagerWithMultipleSubordinates() {
        // Create Manager M1
        Manager m1 = new Manager("IT", "John Manager", LocalDate.of(1980, 5, 15), "123-45-6789", "Senior Manager");
        
        // Create subordinates: Worker W1, SalesPerson S1, and Manager M2
        Worker w1 = new Worker("IT", "Alice Worker", LocalDate.of(1990, 3, 20), "987-65-4321", 40, 25.0, true);
        Salesperson s1 = new Salesperson("Sales", "Bob Sales", LocalDate.of(1985, 8, 10), "456-78-9012", 3000.0, 50000.0, 0.1);
        Manager m2 = new Manager("HR", "Carol Manager", LocalDate.of(1975, 12, 5), "789-01-2345", "HR Manager");
        
        // Assign subordinates to M1
        m1.addSubordinate(w1);
        m1.addSubordinate(s1);
        m1.addSubordinate(m2);
        
        // Add manager to company
        company.addEmployee(m1);
        
        // Get number of direct subordinates for each manager
        List<Integer> subordinatesCount = company.getNumberOfDirectSubordinatesForEachManager();
        
        // Verify the result
        assertEquals("Should have exactly one manager in the company", 1, subordinatesCount.size());
        assertEquals("Manager M1 should have 3 direct subordinates", Integer.valueOf(3), subordinatesCount.get(0));
    }
    
    @Test
    public void testCase2_managerWithNoSubordinates() {
        // Create Manager M1 with no subordinates
        Manager m1 = new Manager("Finance", "David Manager", LocalDate.of(1978, 7, 25), "234-56-7890", "Finance Director");
        
        // Add manager to company
        company.addEmployee(m1);
        
        // Get number of direct subordinates for each manager
        List<Integer> subordinatesCount = company.getNumberOfDirectSubordinatesForEachManager();
        
        // Verify the result
        assertEquals("Should have exactly one manager in the company", 1, subordinatesCount.size());
        assertEquals("Manager M1 should have 0 direct subordinates", Integer.valueOf(0), subordinatesCount.get(0));
    }
    
    @Test
    public void testCase3_multipleManagersWithDifferentNumberOfSubordinates() {
        // Create Manager A
        Manager managerA = new Manager("Operations", "Manager A", LocalDate.of(1970, 1, 1), "111-11-1111", "Operations Manager");
        
        // Create subordinates for Manager A: 1 ShiftWorker, 1 OffShiftWorker, 1 SalesPerson
        Worker shiftWorker = new Worker("Operations", "Shift Worker", LocalDate.of(1985, 2, 15), "222-22-2222", 35, 20.0, true);
        Worker offShiftWorker = new Worker("Operations", "OffShift Worker", LocalDate.of(1990, 6, 20), "333-33-3333", 40, 18.0, false);
        Salesperson salesPerson = new Salesperson("Sales", "Sales Person", LocalDate.of(1988, 9, 10), "444-44-4444", 2500.0, 30000.0, 0.08);
        
        // Assign subordinates to Manager A
        managerA.addSubordinate(shiftWorker);
        managerA.addSubordinate(offShiftWorker);
        managerA.addSubordinate(salesPerson);
        
        // Create Manager B
        Manager managerB = new Manager("Marketing", "Manager B", LocalDate.of(1975, 3, 15), "555-55-5555", "Marketing Manager");
        
        // Create subordinates for Manager B: 2 ShiftWorker, 2 OffShiftWorker, 3 SalesPeople
        Worker shiftWorker1 = new Worker("Marketing", "SW1", LocalDate.of(1992, 4, 5), "666-66-6666", 38, 22.0, true);
        Worker shiftWorker2 = new Worker("Marketing", "SW2", LocalDate.of(1991, 7, 12), "777-77-7777", 36, 21.0, true);
        Worker offShiftWorker1 = new Worker("Marketing", "OSW1", LocalDate.of(1989, 11, 20), "888-88-8888", 40, 19.0, false);
        Worker offShiftWorker2 = new Worker("Marketing", "OSW2", LocalDate.of(1993, 2, 28), "999-99-9999", 42, 17.0, false);
        Salesperson salesPerson1 = new Salesperson("Marketing", "SP1", LocalDate.of(1987, 5, 15), "101-10-1010", 2800.0, 40000.0, 0.09);
        Salesperson salesPerson2 = new Salesperson("Marketing", "SP2", LocalDate.of(1986, 8, 22), "202-20-2020", 2700.0, 35000.0, 0.07);
        Salesperson salesPerson3 = new Salesperson("Marketing", "SP3", LocalDate.of(1990, 12, 10), "303-30-3030", 2600.0, 32000.0, 0.06);
        
        // Assign subordinates to Manager B
        managerB.addSubordinate(shiftWorker1);
        managerB.addSubordinate(shiftWorker2);
        managerB.addSubordinate(offShiftWorker1);
        managerB.addSubordinate(offShiftWorker2);
        managerB.addSubordinate(salesPerson1);
        managerB.addSubordinate(salesPerson2);
        managerB.addSubordinate(salesPerson3);
        
        // Add both managers to company
        company.addEmployee(managerA);
        company.addEmployee(managerB);
        
        // Get number of direct subordinates for each manager
        List<Integer> subordinatesCount = company.getNumberOfDirectSubordinatesForEachManager();
        
        // Verify the results
        assertEquals("Should have exactly two managers in the company", 2, subordinatesCount.size());
        assertTrue("List should contain count 3 for Manager A", subordinatesCount.contains(Integer.valueOf(3)));
        assertTrue("List should contain count 7 for Manager B", subordinatesCount.contains(Integer.valueOf(7)));
    }
    
    @Test
    public void testCase4_managerWithSingleSubordinate() {
        // Create Manager M1
        Manager m1 = new Manager("Logistics", "Emily Manager", LocalDate.of(1982, 9, 18), "404-40-4040", "Logistics Manager");
        
        // Create subordinate Worker W1
        Worker w1 = new Worker("Logistics", "Frank Worker", LocalDate.of(1995, 4, 22), "505-50-5050", 40, 16.0, false);
        
        // Assign subordinate to M1
        m1.addSubordinate(w1);
        
        // Add manager to company
        company.addEmployee(m1);
        
        // Get number of direct subordinates for each manager
        List<Integer> subordinatesCount = company.getNumberOfDirectSubordinatesForEachManager();
        
        // Verify the result
        assertEquals("Should have exactly one manager in the company", 1, subordinatesCount.size());
        assertEquals("Manager M1 should have 1 direct subordinate", Integer.valueOf(1), subordinatesCount.get(0));
    }
    
    @Test
    public void testCase5_multipleManagersWithNestedSubordinates() {
        // Create Manager B first
        Manager managerB = new Manager("Production", "Manager B", LocalDate.of(1973, 6, 30), "606-60-6060", "Production Manager");
        
        // Create subordinate Worker W1 for Manager B
        Worker w1 = new Worker("Production", "Worker W1", LocalDate.of(1994, 3, 14), "707-70-7070", 45, 15.0, true);
        
        // Assign subordinate to Manager B
        managerB.addSubordinate(w1);
        
        // Create Manager A
        Manager managerA = new Manager("Executive", "Manager A", LocalDate.of(1968, 11, 8), "808-80-8080", "Executive Director");
        
        // Create subordinates for Manager A: Worker W1, SalesPerson S1, S2, S3, and Manager B
        Worker workerForA = new Worker("Executive", "Worker for A", LocalDate.of(1991, 5, 19), "909-90-9090", 35, 24.0, false);
        Salesperson s1 = new Salesperson("Sales", "Sales S1", LocalDate.of(1984, 7, 23), "010-01-0101", 3200.0, 45000.0, 0.1);
        Salesperson s2 = new Salesperson("Sales", "Sales S2", LocalDate.of(1986, 2, 17), "020-20-2020", 3100.0, 42000.0, 0.09);
        Salesperson s3 = new Salesperson("Sales", "Sales S3", LocalDate.of(1989, 10, 5), "030-30-3030", 3000.0, 38000.0, 0.08);
        
        // Assign subordinates to Manager A
        managerA.addSubordinate(workerForA);
        managerA.addSubordinate(s1);
        managerA.addSubordinate(s2);
        managerA.addSubordinate(s3);
        managerA.addSubordinate(managerB);
        
        // Add both managers to company
        company.addEmployee(managerA);
        company.addEmployee(managerB);
        
        // Get number of direct subordinates for each manager
        List<Integer> subordinatesCount = company.getNumberOfDirectSubordinatesForEachManager();
        
        // Verify the results
        assertEquals("Should have exactly two managers in the company", 2, subordinatesCount.size());
        assertTrue("List should contain count 5 for Manager A", subordinatesCount.contains(Integer.valueOf(5)));
        assertTrue("List should contain count 1 for Manager B", subordinatesCount.contains(Integer.valueOf(1)));
    }
}