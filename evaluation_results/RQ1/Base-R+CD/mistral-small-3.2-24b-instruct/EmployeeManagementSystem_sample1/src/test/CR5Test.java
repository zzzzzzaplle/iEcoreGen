import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    private Manager managerA;
    private Manager managerB;
    private Manager managerM1;
    private Manager managerM2;
    private Worker workerW1;
    private SalesPeople salesPersonS1;
    private SalesPeople salesPersonS2;
    private SalesPeople salesPersonS3;
    private ShiftWorker shiftWorker1;
    private ShiftWorker shiftWorker2;
    private OffShiftWorker offShiftWorker1;
    private OffShiftWorker offShiftWorker2;
    
    @Before
    public void setUp() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // Initialize common test objects
        managerA = new Manager();
        managerA.setName("Manager A");
        managerA.setBirthDate(sdf.parse("1970-01-01 00:00:00"));
        managerA.setSocialInsuranceNumber("123456789");
        managerA.setDepartment("Sales");
        managerA.setSalary(50000.0);
        managerA.setPosition("Senior Manager");
        
        managerB = new Manager();
        managerB.setName("Manager B");
        managerB.setBirthDate(sdf.parse("1975-05-15 09:30:00"));
        managerB.setSocialInsuranceNumber("987654321");
        managerB.setDepartment("Operations");
        managerB.setSalary(45000.0);
        managerB.setPosition("Manager");
        
        managerM1 = new Manager();
        managerM1.setName("Manager M1");
        managerM1.setBirthDate(sdf.parse("1980-03-20 14:45:00"));
        managerM1.setSocialInsuranceNumber("111222333");
        managerM1.setDepartment("IT");
        managerM1.setSalary(60000.0);
        managerM1.setPosition("IT Manager");
        
        managerM2 = new Manager();
        managerM2.setName("Manager M2");
        managerM2.setBirthDate(sdf.parse("1982-07-10 10:15:00"));
        managerM2.setSocialInsuranceNumber("444555666");
        managerM2.setDepartment("HR");
        managerM2.setSalary(55000.0);
        managerM2.setPosition("HR Manager");
        
        workerW1 = new OffShiftWorker();
        workerW1.setName("Worker W1");
        workerW1.setBirthDate(sdf.parse("1990-12-05 08:00:00"));
        workerW1.setSocialInsuranceNumber("777888999");
        workerW1.setDepartment("Production");
        workerW1.setWeeklyWorkingHour(40);
        workerW1.setHourlyRates(20.0);
        
        salesPersonS1 = new SalesPeople();
        salesPersonS1.setName("SalesPerson S1");
        salesPersonS1.setBirthDate(sdf.parse("1988-09-25 11:20:00"));
        salesPersonS1.setSocialInsuranceNumber("333444555");
        salesPersonS1.setDepartment("Sales");
        salesPersonS1.setSalary(30000.0);
        salesPersonS1.setAmountOfSales(100000.0);
        salesPersonS1.setCommissionPercentage(0.1);
        
        salesPersonS2 = new SalesPeople();
        salesPersonS2.setName("SalesPerson S2");
        salesPersonS2.setBirthDate(sdf.parse("1987-04-12 13:45:00"));
        salesPersonS2.setSocialInsuranceNumber("666777888");
        salesPersonS2.setDepartment("Sales");
        salesPersonS2.setSalary(32000.0);
        salesPersonS2.setAmountOfSales(120000.0);
        salesPersonS2.setCommissionPercentage(0.08);
        
        salesPersonS3 = new SalesPeople();
        salesPersonS3.setName("SalesPerson S3");
        salesPersonS3.setBirthDate(sdf.parse("1991-11-30 16:10:00"));
        salesPersonS3.setSocialInsuranceNumber("999000111");
        salesPersonS3.setDepartment("Sales");
        salesPersonS3.setSalary(28000.0);
        salesPersonS3.setAmountOfSales(80000.0);
        salesPersonS3.setCommissionPercentage(0.12);
        
        shiftWorker1 = new ShiftWorker();
        shiftWorker1.setName("ShiftWorker 1");
        shiftWorker1.setBirthDate(sdf.parse("1985-06-18 07:30:00"));
        shiftWorker1.setSocialInsuranceNumber("222333444");
        shiftWorker1.setDepartment("Production");
        shiftWorker1.setWeeklyWorkingHour(35);
        shiftWorker1.setHourlyRates(25.0);
        shiftWorker1.setHolidayPremium(500.0);
        
        shiftWorker2 = new ShiftWorker();
        shiftWorker2.setName("ShiftWorker 2");
        shiftWorker2.setBirthDate(sdf.parse("1983-02-14 09:45:00"));
        shiftWorker2.setSocialInsuranceNumber("555666777");
        shiftWorker2.setDepartment("Production");
        shiftWorker2.setWeeklyWorkingHour(40);
        shiftWorker2.setHourlyRates(22.0);
        shiftWorker2.setHolidayPremium(450.0);
        
        offShiftWorker1 = new OffShiftWorker();
        offShiftWorker1.setName("OffShiftWorker 1");
        offShiftWorker1.setBirthDate(sdf.parse("1989-08-22 12:00:00"));
        offShiftWorker1.setSocialInsuranceNumber("888999000");
        offShiftWorker1.setDepartment("Production");
        offShiftWorker1.setWeeklyWorkingHour(38);
        offShiftWorker1.setHourlyRates(18.0);
        
        offShiftWorker2 = new OffShiftWorker();
        offShiftWorker2.setName("OffShiftWorker 2");
        offShiftWorker2.setBirthDate(sdf.parse("1992-01-08 15:20:00"));
        offShiftWorker2.setSocialInsuranceNumber("111222333");
        offShiftWorker2.setDepartment("Production");
        offShiftWorker2.setWeeklyWorkingHour(42);
        offShiftWorker2.setHourlyRates(19.5);
    }
    
    @Test
    public void testCase1_SingleManagerWithMultipleSubordinates() {
        // Setup: Create Manager M1 and assign Worker W1, SalesPerson S1, and Manager M2 as direct subordinates
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
        managerM1.setSubordinates(new ArrayList<>());
        
        // Expected Output: The number of subordinates for the manager is 0
        assertEquals("The number of subordinates for Manager M1 should be 0", 
                     0, managerM1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase3_MultipleManagersWithDifferentNumberOfSubordinates() {
        // Setup: Create Manager A with 3 subordinates (1 ShiftWorker, 1 OffShiftWorker, 1 SalesPeople)
        List<Employee> subordinatesA = new ArrayList<>();
        subordinatesA.add(shiftWorker1);
        subordinatesA.add(offShiftWorker1);
        subordinatesA.add(salesPersonS1);
        managerA.setSubordinates(subordinatesA);
        
        // Setup: Create Manager B with 7 subordinates (2 ShiftWorker, 2 OffShiftWorker, 3 SalesPeople)
        List<Employee> subordinatesB = new ArrayList<>();
        subordinatesB.add(shiftWorker1);
        subordinatesB.add(shiftWorker2);
        subordinatesB.add(offShiftWorker1);
        subordinatesB.add(offShiftWorker2);
        subordinatesB.add(salesPersonS1);
        subordinatesB.add(salesPersonS2);
        subordinatesB.add(salesPersonS3);
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
        List<Employee> subordinates = new ArrayList<>();
        subordinates.add(workerW1);
        managerM1.setSubordinates(subordinates);
        
        // Expected Output: The number of subordinates for the manager is 1
        assertEquals("The number of subordinates for Manager M1 should be 1", 
                     1, managerM1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase5_MultipleManagersWithDifferentNumberOfSubordinatesComplex() {
        // Setup: Create Manager A with 5 subordinates: worker W1, SalesPerson S1, S2, S3, and Manager B
        List<Employee> subordinatesA = new ArrayList<>();
        subordinatesA.add(workerW1);
        subordinatesA.add(salesPersonS1);
        subordinatesA.add(salesPersonS2);
        subordinatesA.add(salesPersonS3);
        subordinatesA.add(managerB);
        managerA.setSubordinates(subordinatesA);
        
        // Setup: Create Manager B with 1 subordinate: Worker W1
        List<Employee> subordinatesB = new ArrayList<>();
        subordinatesB.add(workerW1);
        managerB.setSubordinates(subordinatesB);
        
        // Expected Output: The number of subordinates for Manager A is 5. The number of subordinates for Manager B is 1
        assertEquals("The number of subordinates for Manager A should be 5", 
                     5, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("The number of subordinates for Manager B should be 1", 
                     1, managerB.getDirectSubordinateEmployeesCount());
    }
}