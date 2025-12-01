import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Company company;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalPrintStream;
    
    @Before
    public void setUp() {
        company = new Company();
        // Redirect System.out to capture console output
        outputStream = new ByteArrayOutputStream();
        originalPrintStream = System.out;
        System.setOut(new PrintStream(outputStream));
    }
    
    @Test
    public void testCase1_SingleManagerWithMultipleSubordinates() {
        // Create Manager M1
        Manager m1 = new Manager();
        m1.setName("M1");
        
        // Create subordinates: Worker W1, SalesPerson S1, and Manager M2
        Worker w1 = new Worker();
        w1.setName("W1");
        
        SalesPerson s1 = new SalesPerson();
        s1.setName("S1");
        
        Manager m2 = new Manager();
        m2.setName("M2");
        
        // Assign subordinates to M1
        List<Employee> subordinates = new ArrayList<>();
        subordinates.add(w1);
        subordinates.add(s1);
        subordinates.add(m2);
        m1.setSubordinates(subordinates);
        
        // Add manager to company
        company.getEmployees().add(m1);
        
        // Execute the method
        company.printManagerSubordinateCounts();
        
        // Verify the output
        String expectedOutput = "Manager M1 has 3 direct subordinates." + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }
    
    @Test
    public void testCase2_ManagerWithNoSubordinates() {
        // Create Manager M1 with no subordinates
        Manager m1 = new Manager();
        m1.setName("M1");
        
        // Add manager to company
        company.getEmployees().add(m1);
        
        // Execute the method
        company.printManagerSubordinateCounts();
        
        // Verify the output
        String expectedOutput = "Manager M1 has 0 direct subordinates." + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }
    
    @Test
    public void testCase3_MultipleManagersWithDifferentNumberOfSubordinates() {
        // Create Manager A with 3 subordinates
        Manager managerA = new Manager();
        managerA.setName("A");
        
        ShiftWorker sw1 = new ShiftWorker();
        sw1.setName("SW1");
        NonShiftWorker nsw1 = new NonShiftWorker();
        nsw1.setName("NSW1");
        SalesPerson sp1 = new SalesPerson();
        sp1.setName("SP1");
        
        List<Employee> subordinatesA = new ArrayList<>();
        subordinatesA.add(sw1);
        subordinatesA.add(nsw1);
        subordinatesA.add(sp1);
        managerA.setSubordinates(subordinatesA);
        
        // Create Manager B with 7 subordinates
        Manager managerB = new Manager();
        managerB.setName("B");
        
        List<Employee> subordinatesB = new ArrayList<>();
        subordinatesB.add(new ShiftWorker());
        subordinatesB.add(new ShiftWorker());
        subordinatesB.add(new NonShiftWorker());
        subordinatesB.add(new NonShiftWorker());
        subordinatesB.add(new SalesPerson());
        subordinatesB.add(new SalesPerson());
        subordinatesB.add(new SalesPerson());
        managerB.setSubordinates(subordinatesB);
        
        // Add managers to company
        company.getEmployees().add(managerA);
        company.getEmployees().add(managerB);
        
        // Execute the method
        company.printManagerSubordinateCounts();
        
        // Verify the output
        String expectedOutput = "Manager A has 3 direct subordinates." + System.lineSeparator() +
                              "Manager B has 7 direct subordinates." + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }
    
    @Test
    public void testCase4_ManagerWithSingleSubordinate() {
        // Create Manager M1
        Manager m1 = new Manager();
        m1.setName("M1");
        
        // Create Worker W1 and assign as subordinate
        Worker w1 = new Worker();
        w1.setName("W1");
        
        List<Employee> subordinates = new ArrayList<>();
        subordinates.add(w1);
        m1.setSubordinates(subordinates);
        
        // Add manager to company
        company.getEmployees().add(m1);
        
        // Execute the method
        company.printManagerSubordinateCounts();
        
        // Verify the output
        String expectedOutput = "Manager M1 has 1 direct subordinates." + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }
    
    @Test
    public void testCase5_MultipleManagersWithNestedSubordinates() {
        // Create Manager B with 1 subordinate
        Manager managerB = new Manager();
        managerB.setName("B");
        
        Worker w1 = new Worker();
        w1.setName("W1");
        
        List<Employee> subordinatesB = new ArrayList<>();
        subordinatesB.add(w1);
        managerB.setSubordinates(subordinatesB);
        
        // Create Manager A with 5 subordinates including Manager B
        Manager managerA = new Manager();
        managerA.setName("A");
        
        Worker worker1 = new Worker();
        worker1.setName("Worker1");
        SalesPerson sp1 = new SalesPerson();
        sp1.setName("SP1");
        SalesPerson sp2 = new SalesPerson();
        sp2.setName("SP2");
        SalesPerson sp3 = new SalesPerson();
        sp3.setName("SP3");
        
        List<Employee> subordinatesA = new ArrayList<>();
        subordinatesA.add(worker1);
        subordinatesA.add(sp1);
        subordinatesA.add(sp2);
        subordinatesA.add(sp3);
        subordinatesA.add(managerB);
        managerA.setSubordinates(subordinatesA);
        
        // Add managers to company
        company.getEmployees().add(managerA);
        company.getEmployees().add(managerB);
        
        // Execute the method
        company.printManagerSubordinateCounts();
        
        // Verify the output
        String expectedOutput = "Manager A has 5 direct subordinates." + System.lineSeparator() +
                              "Manager B has 1 direct subordinates." + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }
}