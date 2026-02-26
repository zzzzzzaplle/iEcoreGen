import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
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
    public void testCase1_singleManagerWithMultipleSubordinates() throws Exception {
        // Create Manager M1
        Manager managerM1 = new Manager();
        managerM1.setName("M1");
        managerM1.setFixedSalary(5000.0);
        
        // Create subordinate employees
        Worker workerW1 = new Worker();
        workerW1.setName("W1");
        workerW1.setWeeklyWorkingHours(40.0);
        workerW1.setHourlyRate(25.0);
        
        SalesPerson salesPersonS1 = new SalesPerson();
        salesPersonS1.setName("S1");
        salesPersonS1.setFixedSalary(3000.0);
        salesPersonS1.setAmountOfSales(10000.0);
        salesPersonS1.setCommissionPercentage(0.1);
        
        Manager managerM2 = new Manager();
        managerM2.setName("M2");
        managerM2.setFixedSalary(4500.0);
        
        // Assign subordinates to Manager M1
        managerM1.addSubordinate(workerW1);
        managerM1.addSubordinate(salesPersonS1);
        managerM1.addSubordinate(managerM2);
        
        // Add manager to company
        company.addEmployee(managerM1);
        
        // Get subordinate counts
        List<String> results = company.getSubordinateCountsForManagers();
        
        // Verify results
        assertEquals(1, results.size());
        assertEquals("M1: 3 subordinates", results.get(0));
    }
    
    @Test
    public void testCase2_managerWithNoSubordinates() throws Exception {
        // Create Manager M1 with no subordinates
        Manager managerM1 = new Manager();
        managerM1.setName("M1");
        managerM1.setFixedSalary(5000.0);
        
        // Add manager to company
        company.addEmployee(managerM1);
        
        // Get subordinate counts
        List<String> results = company.getSubordinateCountsForManagers();
        
        // Verify results
        assertEquals(1, results.size());
        assertEquals("M1: 0 subordinates", results.get(0));
    }
    
    @Test
    public void testCase3_multipleManagersWithDifferentNumberOfSubordinates() throws Exception {
        // Create Manager A
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        managerA.setFixedSalary(6000.0);
        
        // Create subordinates for Manager A
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setName("SW1");
        shiftWorker1.setWeeklyWorkingHours(35.0);
        shiftWorker1.setHourlyRate(30.0);
        shiftWorker1.setHolidayPremium(200.0);
        
        NonShiftWorker nonShiftWorker1 = new NonShiftWorker();
        nonShiftWorker1.setName("NSW1");
        nonShiftWorker1.setWeeklyWorkingHours(40.0);
        nonShiftWorker1.setHourlyRate(25.0);
        nonShiftWorker1.setWeekendPermit(true);
        nonShiftWorker1.setOfficialHolidayPermit(false);
        
        SalesPerson salesPerson1 = new SalesPerson();
        salesPerson1.setName("SP1");
        salesPerson1.setFixedSalary(3500.0);
        salesPerson1.setAmountOfSales(15000.0);
        salesPerson1.setCommissionPercentage(0.08);
        
        // Assign 3 subordinates to Manager A
        managerA.addSubordinate(shiftWorker1);
        managerA.addSubordinate(nonShiftWorker1);
        managerA.addSubordinate(salesPerson1);
        
        // Create Manager B
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        managerB.setFixedSalary(5500.0);
        
        // Create subordinates for Manager B
        for (int i = 1; i <= 2; i++) {
            ShiftWorker sw = new ShiftWorker();
            sw.setName("SW" + (i + 1));
            sw.setWeeklyWorkingHours(38.0);
            sw.setHourlyRate(28.0);
            sw.setHolidayPremium(150.0);
            managerB.addSubordinate(sw);
        }
        
        for (int i = 1; i <= 2; i++) {
            NonShiftWorker nsw = new NonShiftWorker();
            nsw.setName("NSW" + (i + 1));
            nsw.setWeeklyWorkingHours(42.0);
            nsw.setHourlyRate(26.0);
            managerB.addSubordinate(nsw);
        }
        
        for (int i = 1; i <= 3; i++) {
            SalesPerson sp = new SalesPerson();
            sp.setName("SP" + (i + 1));
            sp.setFixedSalary(3200.0);
            sp.setAmountOfSales(12000.0);
            sp.setCommissionPercentage(0.07);
            managerB.addSubordinate(sp);
        }
        
        // Add managers to company
        company.addEmployee(managerA);
        company.addEmployee(managerB);
        
        // Get subordinate counts
        List<String> results = company.getSubordinateCountsForManagers();
        
        // Verify results - check both managers
        assertEquals(2, results.size());
        assertTrue(results.contains("Manager A: 3 subordinates"));
        assertTrue(results.contains("Manager B: 7 subordinates"));
    }
    
    @Test
    public void testCase4_managerWithSingleSubordinate() throws Exception {
        // Create Manager M1
        Manager managerM1 = new Manager();
        managerM1.setName("M1");
        managerM1.setFixedSalary(5000.0);
        
        // Create Worker W1
        Worker workerW1 = new Worker();
        workerW1.setName("W1");
        workerW1.setWeeklyWorkingHours(40.0);
        workerW1.setHourlyRate(25.0);
        
        // Assign Worker W1 as subordinate to Manager M1
        managerM1.addSubordinate(workerW1);
        
        // Add manager to company
        company.addEmployee(managerM1);
        
        // Get subordinate counts
        List<String> results = company.getSubordinateCountsForManagers();
        
        // Verify results
        assertEquals(1, results.size());
        assertEquals("M1: 1 subordinates", results.get(0));
    }
    
    @Test
    public void testCase5_multipleManagersWithNestedSubordinates() throws Exception {
        // Create Manager A
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        managerA.setFixedSalary(7000.0);
        
        // Create Worker W1
        Worker workerW1 = new Worker();
        workerW1.setName("W1");
        workerW1.setWeeklyWorkingHours(40.0);
        workerW1.setHourlyRate(25.0);
        
        // Create SalesPersons S1, S2, S3
        SalesPerson salesPerson1 = new SalesPerson();
        salesPerson1.setName("S1");
        salesPerson1.setFixedSalary(3000.0);
        salesPerson1.setAmountOfSales(10000.0);
        salesPerson1.setCommissionPercentage(0.1);
        
        SalesPerson salesPerson2 = new SalesPerson();
        salesPerson2.setName("S2");
        salesPerson2.setFixedSalary(3200.0);
        salesPerson2.setAmountOfSales(12000.0);
        salesPerson2.setCommissionPercentage(0.1);
        
        SalesPerson salesPerson3 = new SalesPerson();
        salesPerson3.setName("S3");
        salesPerson3.setFixedSalary(3100.0);
        salesPerson3.setAmountOfSales(11000.0);
        salesPerson3.setCommissionPercentage(0.1);
        
        // Create Manager B
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        managerB.setFixedSalary(5500.0);
        
        // Create Worker W1 for Manager B
        Worker workerForB = new Worker();
        workerForB.setName("W1");
        workerForB.setWeeklyWorkingHours(35.0);
        workerForB.setHourlyRate(28.0);
        
        // Assign subordinate to Manager B
        managerB.addSubordinate(workerForB);
        
        // Assign all subordinates to Manager A
        managerA.addSubordinate(workerW1);
        managerA.addSubordinate(salesPerson1);
        managerA.addSubordinate(salesPerson2);
        managerA.addSubordinate(salesPerson3);
        managerA.addSubordinate(managerB);
        
        // Add managers to company
        company.addEmployee(managerA);
        company.addEmployee(managerB);
        
        // Get subordinate counts
        List<String> results = company.getSubordinateCountsForManagers();
        
        // Verify results - check both managers
        assertEquals(2, results.size());
        assertTrue(results.contains("Manager A: 5 subordinates"));
        assertTrue(results.contains("Manager B: 1 subordinates"));
    }
}