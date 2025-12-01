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
        m1.setSocialInsuranceNumber("M1_SIN");
        
        // Create subordinates: Worker W1, SalesPerson S1, and Manager M2
        Worker w1 = new Worker();
        w1.setSocialInsuranceNumber("W1_SIN");
        
        SalesPerson s1 = new SalesPerson();
        s1.setSocialInsuranceNumber("S1_SIN");
        
        Manager m2 = new Manager();
        m2.setSocialInsuranceNumber("M2_SIN");
        
        // Assign subordinates to M1
        m1.addSubordinate(w1);
        m1.addSubordinate(s1);
        m1.addSubordinate(m2);
        
        // Add manager to company
        company.addEmployee(m1);
        
        // Get subordinates count for each manager
        Map<String, Integer> result = company.getNumberOfSubordinatesPerManager();
        
        // Verify M1 has 3 subordinates
        assertEquals("The number of direct subordinates for Manager M1 is 3.", 3, result.get("M1_SIN").intValue());
    }
    
    @Test
    public void testCase2_managerWithNoSubordinates() {
        // Create Manager M1 with no subordinates
        Manager m1 = new Manager();
        m1.setSocialInsuranceNumber("M1_SIN");
        
        // Add manager to company
        company.addEmployee(m1);
        
        // Get subordinates count for each manager
        Map<String, Integer> result = company.getNumberOfSubordinatesPerManager();
        
        // Verify M1 has 0 subordinates
        assertEquals("The number of subordinates for the manager is 0.", 0, result.get("M1_SIN").intValue());
    }
    
    @Test
    public void testCase3_multipleManagersWithDifferentNumberOfSubordinates() {
        // Create Manager A
        Manager managerA = new Manager();
        managerA.setSocialInsuranceNumber("A_SIN");
        
        // Create Manager B
        Manager managerB = new Manager();
        managerB.setSocialInsuranceNumber("B_SIN");
        
        // Create subordinates for Manager A: 1 ShiftWorker, 1 OffShiftWorker, 1 SalesPerson
        ShiftWorker sw1 = new ShiftWorker();
        sw1.setSocialInsuranceNumber("SW1_SIN");
        
        OffShiftWorker osw1 = new OffShiftWorker();
        osw1.setSocialInsuranceNumber("OSW1_SIN");
        
        SalesPerson sp1 = new SalesPerson();
        sp1.setSocialInsuranceNumber("SP1_SIN");
        
        // Assign subordinates to Manager A
        managerA.addSubordinate(sw1);
        managerA.addSubordinate(osw1);
        managerA.addSubordinate(sp1);
        
        // Create subordinates for Manager B: 2 ShiftWorker, 2 OffShiftWorker, 3 SalesPerson
        ShiftWorker sw2 = new ShiftWorker();
        sw2.setSocialInsuranceNumber("SW2_SIN");
        ShiftWorker sw3 = new ShiftWorker();
        sw3.setSocialInsuranceNumber("SW3_SIN");
        
        OffShiftWorker osw2 = new OffShiftWorker();
        osw2.setSocialInsuranceNumber("OSW2_SIN");
        OffShiftWorker osw3 = new OffShiftWorker();
        osw3.setSocialInsuranceNumber("OSW3_SIN");
        
        SalesPerson sp2 = new SalesPerson();
        sp2.setSocialInsuranceNumber("SP2_SIN");
        SalesPerson sp3 = new SalesPerson();
        sp3.setSocialInsuranceNumber("SP3_SIN");
        SalesPerson sp4 = new SalesPerson();
        sp4.setSocialInsuranceNumber("SP4_SIN");
        
        // Assign subordinates to Manager B
        managerB.addSubordinate(sw2);
        managerB.addSubordinate(sw3);
        managerB.addSubordinate(osw2);
        managerB.addSubordinate(osw3);
        managerB.addSubordinate(sp2);
        managerB.addSubordinate(sp3);
        managerB.addSubordinate(sp4);
        
        // Add managers to company
        company.addEmployee(managerA);
        company.addEmployee(managerB);
        
        // Get subordinates count for each manager
        Map<String, Integer> result = company.getNumberOfSubordinatesPerManager();
        
        // Verify Manager A has 3 subordinates and Manager B has 7 subordinates
        assertEquals("The number of subordinates for Manager A is 3.", 3, result.get("A_SIN").intValue());
        assertEquals("The number of subordinates for Manager B is 7.", 7, result.get("B_SIN").intValue());
    }
    
    @Test
    public void testCase4_managerWithSingleSubordinate() {
        // Create Manager M1
        Manager m1 = new Manager();
        m1.setSocialInsuranceNumber("M1_SIN");
        
        // Create Worker W1
        Worker w1 = new Worker();
        w1.setSocialInsuranceNumber("W1_SIN");
        
        // Assign W1 as subordinate to M1
        m1.addSubordinate(w1);
        
        // Add manager to company
        company.addEmployee(m1);
        
        // Get subordinates count for each manager
        Map<String, Integer> result = company.getNumberOfSubordinatesPerManager();
        
        // Verify M1 has 1 subordinate
        assertEquals("The number of subordinates for the manager is 1.", 1, result.get("M1_SIN").intValue());
    }
    
    @Test
    public void testCase5_multipleManagersWithHierarchicalSubordinates() {
        // Create Manager A
        Manager managerA = new Manager();
        managerA.setSocialInsuranceNumber("A_SIN");
        
        // Create Manager B
        Manager managerB = new Manager();
        managerB.setSocialInsuranceNumber("B_SIN");
        
        // Create Worker W1
        Worker w1 = new Worker();
        w1.setSocialInsuranceNumber("W1_SIN");
        
        // Create SalesPerson S1, S2, S3
        SalesPerson s1 = new SalesPerson();
        s1.setSocialInsuranceNumber("S1_SIN");
        SalesPerson s2 = new SalesPerson();
        s2.setSocialInsuranceNumber("S2_SIN");
        SalesPerson s3 = new SalesPerson();
        s3.setSocialInsuranceNumber("S3_SIN");
        
        // Assign subordinates to Manager A: Worker W1, SalesPerson S1, S2, S3, and Manager B
        managerA.addSubordinate(w1);
        managerA.addSubordinate(s1);
        managerA.addSubordinate(s2);
        managerA.addSubordinate(s3);
        managerA.addSubordinate(managerB);
        
        // Create Worker W2 for Manager B
        Worker w2 = new Worker();
        w2.setSocialInsuranceNumber("W2_SIN");
        
        // Assign subordinate to Manager B: Worker W2
        managerB.addSubordinate(w2);
        
        // Add managers to company
        company.addEmployee(managerA);
        company.addEmployee(managerB);
        
        // Get subordinates count for each manager
        Map<String, Integer> result = company.getNumberOfSubordinatesPerManager();
        
        // Verify Manager A has 5 subordinates and Manager B has 1 subordinate
        assertEquals("The number of subordinates for Manager A is 5.", 5, result.get("A_SIN").intValue());
        assertEquals("The number of subordinates for Manager B is 1.", 1, result.get("B_SIN").intValue());
    }
}