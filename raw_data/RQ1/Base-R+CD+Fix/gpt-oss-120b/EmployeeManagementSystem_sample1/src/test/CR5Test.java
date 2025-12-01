import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR5Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_SingleManagerWithMultipleSubordinates() throws ParseException {
        // Setup: Create Manager M1
        Manager m1 = new Manager();
        m1.setName("Manager M1");
        m1.setBirthDate(dateFormat.parse("1980-01-01 00:00:00"));
        m1.setSocialInsuranceNumber("123-456-789");
        m1.setDepartment("PRODUCTION");
        m1.setSalary(50000.0);
        m1.setPosition("Senior Manager");
        
        // Create Worker W1
        Worker w1 = new OffShiftWorker();
        w1.setName("Worker W1");
        w1.setBirthDate(dateFormat.parse("1990-02-02 00:00:00"));
        w1.setSocialInsuranceNumber("234-567-890");
        w1.setDepartment("PRODUCTION");
        w1.setWeeklyWorkingHour(40);
        w1.setHourlyRates(25.0);
        
        // Create SalesPerson S1
        SalesPeople s1 = new SalesPeople();
        s1.setName("SalesPerson S1");
        s1.setBirthDate(dateFormat.parse("1985-03-03 00:00:00"));
        s1.setSocialInsuranceNumber("345-678-901");
        s1.setDepartment("SALES");
        s1.setSalary(30000.0);
        s1.setAmountOfSales(100000.0);
        s1.setCommissionPercentage(0.05);
        
        // Create Manager M2
        Manager m2 = new Manager();
        m2.setName("Manager M2");
        m2.setBirthDate(dateFormat.parse("1975-04-04 00:00:00"));
        m2.setSocialInsuranceNumber("456-789-012");
        m2.setDepartment("CONTROL");
        m2.setSalary(45000.0);
        m2.setPosition("Junior Manager");
        
        // Assign subordinates to M1
        m1.addSubordinate(w1);
        m1.addSubordinate(s1);
        m1.addSubordinate(m2);
        
        // Expected Output: "The number of direct subordinates for Manager M1 is 3."
        assertEquals("The number of direct subordinates for Manager M1 should be 3", 
                     3, m1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase2_ManagerWithNoSubordinates() throws ParseException {
        // Setup: Create Manager M1 with no subordinates
        Manager m1 = new Manager();
        m1.setName("Manager M1");
        m1.setBirthDate(dateFormat.parse("1980-01-01 00:00:00"));
        m1.setSocialInsuranceNumber("123-456-789");
        m1.setDepartment("PRODUCTION");
        m1.setSalary(50000.0);
        m1.setPosition("Senior Manager");
        
        // No subordinates assigned
        
        // Expected Output: "The number of subordinates for the manager is 0."
        assertEquals("The number of subordinates for the manager should be 0", 
                     0, m1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase3_MultipleManagersWithDifferentNumberOfSubordinates() throws ParseException {
        // Setup: Create Manager A with 3 subordinates
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        managerA.setBirthDate(dateFormat.parse("1970-05-05 00:00:00"));
        managerA.setSocialInsuranceNumber("567-890-123");
        managerA.setDepartment("PRODUCTION");
        managerA.setSalary(60000.0);
        managerA.setPosition("Production Manager");
        
        // Create subordinates for Manager A
        ShiftWorker sw1 = new ShiftWorker();
        sw1.setName("ShiftWorker SW1");
        sw1.setBirthDate(dateFormat.parse("1992-06-06 00:00:00"));
        sw1.setSocialInsuranceNumber("678-901-234");
        sw1.setDepartment("DELIVERY");
        sw1.setWeeklyWorkingHour(35);
        sw1.setHourlyRates(30.0);
        sw1.setHolidayPremium(50.0);
        
        OffShiftWorker osw1 = new OffShiftWorker();
        osw1.setName("OffShiftWorker OSW1");
        osw1.setBirthDate(dateFormat.parse("1993-07-07 00:00:00"));
        osw1.setSocialInsuranceNumber("789-012-345");
        osw1.setDepartment("PRODUCTION");
        osw1.setWeeklyWorkingHour(40);
        osw1.setHourlyRates(25.0);
        
        SalesPeople sp1 = new SalesPeople();
        sp1.setName("SalesPerson SP1");
        sp1.setBirthDate(dateFormat.parse("1988-08-08 00:00:00"));
        sp1.setSocialInsuranceNumber("890-123-456");
        sp1.setDepartment("SALES");
        sp1.setSalary(35000.0);
        sp1.setAmountOfSales(150000.0);
        sp1.setCommissionPercentage(0.06);
        
        // Assign subordinates to Manager A
        managerA.addSubordinate(sw1);
        managerA.addSubordinate(osw1);
        managerA.addSubordinate(sp1);
        
        // Setup: Create Manager B with 7 subordinates
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        managerB.setBirthDate(dateFormat.parse("1975-09-09 00:00:00"));
        managerB.setSocialInsuranceNumber("901-234-567");
        managerB.setDepartment("CONTROL");
        managerB.setSalary(55000.0);
        managerB.setPosition("Control Manager");
        
        // Create subordinates for Manager B
        ShiftWorker sw2 = new ShiftWorker();
        sw2.setName("ShiftWorker SW2");
        sw2.setBirthDate(dateFormat.parse("1994-10-10 00:00:00"));
        sw2.setSocialInsuranceNumber("012-345-678");
        sw2.setDepartment("DELIVERY");
        sw2.setWeeklyWorkingHour(30);
        sw2.setHourlyRates(28.0);
        sw2.setHolidayPremium(45.0);
        
        ShiftWorker sw3 = new ShiftWorker();
        sw3.setName("ShiftWorker SW3");
        sw3.setBirthDate(dateFormat.parse("1995-11-11 00:00:00"));
        sw3.setSocialInsuranceNumber("123-456-789");
        sw3.setDepartment("DELIVERY");
        sw3.setWeeklyWorkingHour(32);
        sw3.setHourlyRates(26.0);
        sw3.setHolidayPremium(40.0);
        
        OffShiftWorker osw2 = new OffShiftWorker();
        osw2.setName("OffShiftWorker OSW2");
        osw2.setBirthDate(dateFormat.parse("1996-12-12 00:00:00"));
        osw2.setSocialInsuranceNumber("234-567-890");
        osw2.setDepartment("PRODUCTION");
        osw2.setWeeklyWorkingHour(38);
        osw2.setHourlyRates(24.0);
        
        OffShiftWorker osw3 = new OffShiftWorker();
        osw3.setName("OffShiftWorker OSW3");
        osw3.setBirthDate(dateFormat.parse("1997-01-13 00:00:00"));
        osw3.setSocialInsuranceNumber("345-678-901");
        osw3.setDepartment("PRODUCTION");
        osw3.setWeeklyWorkingHour(42);
        osw3.setHourlyRates(27.0);
        
        SalesPeople sp2 = new SalesPeople();
        sp2.setName("SalesPerson SP2");
        sp2.setBirthDate(dateFormat.parse("1989-02-14 00:00:00"));
        sp2.setSocialInsuranceNumber("456-789-012");
        sp2.setDepartment("SALES");
        sp2.setSalary(32000.0);
        sp2.setAmountOfSales(120000.0);
        sp2.setCommissionPercentage(0.05);
        
        SalesPeople sp3 = new SalesPeople();
        sp3.setName("SalesPerson SP3");
        sp3.setBirthDate(dateFormat.parse("1990-03-15 00:00:00"));
        sp3.setSocialInsuranceNumber("567-890-123");
        sp3.setDepartment("SALES");
        sp3.setSalary(33000.0);
        sp3.setAmountOfSales(130000.0);
        sp3.setCommissionPercentage(0.055);
        
        SalesPeople sp4 = new SalesPeople();
        sp4.setName("SalesPerson SP4");
        sp4.setBirthDate(dateFormat.parse("1991-04-16 00:00:00"));
        sp4.setSocialInsuranceNumber("678-901-234");
        sp4.setDepartment("SALES");
        sp4.setSalary(34000.0);
        sp4.setAmountOfSales(140000.0);
        sp4.setCommissionPercentage(0.06);
        
        // Assign subordinates to Manager B
        managerB.addSubordinate(sw2);
        managerB.addSubordinate(sw3);
        managerB.addSubordinate(osw2);
        managerB.addSubordinate(osw3);
        managerB.addSubordinate(sp2);
        managerB.addSubordinate(sp3);
        managerB.addSubordinate(sp4);
        
        // Expected Output: "The number of subordinates for Manager A is 3. The number of subordinates for Manager B is 7."
        assertEquals("The number of subordinates for Manager A should be 3", 
                     3, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("The number of subordinates for Manager B should be 7", 
                     7, managerB.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase4_ManagerWithSingleSubordinate() throws ParseException {
        // Setup: Create Manager M1
        Manager m1 = new Manager();
        m1.setName("Manager M1");
        m1.setBirthDate(dateFormat.parse("1980-01-01 00:00:00"));
        m1.setSocialInsuranceNumber("123-456-789");
        m1.setDepartment("PRODUCTION");
        m1.setSalary(50000.0);
        m1.setPosition("Senior Manager");
        
        // Create Worker W1
        Worker w1 = new OffShiftWorker();
        w1.setName("Worker W1");
        w1.setBirthDate(dateFormat.parse("1990-02-02 00:00:00"));
        w1.setSocialInsuranceNumber("234-567-890");
        w1.setDepartment("PRODUCTION");
        w1.setWeeklyWorkingHour(40);
        w1.setHourlyRates(25.0);
        
        // Assign subordinate to M1
        m1.addSubordinate(w1);
        
        // Expected Output: "The number of subordinates for the manager is 1."
        assertEquals("The number of subordinates for the manager should be 1", 
                     1, m1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase5_MultipleManagersWithDifferentNumberOfSubordinatesComplex() throws ParseException {
        // Setup: Create Manager A
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        managerA.setBirthDate(dateFormat.parse("1970-05-05 00:00:00"));
        managerA.setSocialInsuranceNumber("567-890-123");
        managerA.setDepartment("PRODUCTION");
        managerA.setSalary(60000.0);
        managerA.setPosition("Production Manager");
        
        // Setup: Create Manager B
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        managerB.setBirthDate(dateFormat.parse("1975-09-09 00:00:00"));
        managerB.setSocialInsuranceNumber("901-234-567");
        managerB.setDepartment("CONTROL");
        managerB.setSalary(55000.0);
        managerB.setPosition("Control Manager");
        
        // Create Worker W1
        Worker w1 = new OffShiftWorker();
        w1.setName("Worker W1");
        w1.setBirthDate(dateFormat.parse("1990-02-02 00:00:00"));
        w1.setSocialInsuranceNumber("234-567-890");
        w1.setDepartment("PRODUCTION");
        w1.setWeeklyWorkingHour(40);
        w1.setHourlyRates(25.0);
        
        // Create SalesPerson S1, S2, S3
        SalesPeople s1 = new SalesPeople();
        s1.setName("SalesPerson S1");
        s1.setBirthDate(dateFormat.parse("1985-03-03 00:00:00"));
        s1.setSocialInsuranceNumber("345-678-901");
        s1.setDepartment("SALES");
        s1.setSalary(30000.0);
        s1.setAmountOfSales(100000.0);
        s1.setCommissionPercentage(0.05);
        
        SalesPeople s2 = new SalesPeople();
        s2.setName("SalesPerson S2");
        s2.setBirthDate(dateFormat.parse("1986-04-04 00:00:00"));
        s2.setSocialInsuranceNumber("456-789-012");
        s2.setDepartment("SALES");
        s2.setSalary(31000.0);
        s2.setAmountOfSales(110000.0);
        s2.setCommissionPercentage(0.05);
        
        SalesPeople s3 = new SalesPeople();
        s3.setName("SalesPerson S3");
        s3.setBirthDate(dateFormat.parse("1987-05-05 00:00:00"));
        s3.setSocialInsuranceNumber("567-890-123");
        s3.setDepartment("SALES");
        s3.setSalary(32000.0);
        s3.setAmountOfSales(120000.0);
        s3.setCommissionPercentage(0.05);
        
        // Assign subordinates to Manager A
        managerA.addSubordinate(w1);
        managerA.addSubordinate(s1);
        managerA.addSubordinate(s2);
        managerA.addSubordinate(s3);
        managerA.addSubordinate(managerB);
        
        // Assign subordinate to Manager B
        managerB.addSubordinate(w1); // Note: Same worker can be subordinate to multiple managers
        
        // Expected Output: "The number of subordinates for Manager A is 5. The number of subordinates for Manager B is 1."
        assertEquals("The number of subordinates for Manager A should be 5", 
                     5, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("The number of subordinates for Manager B should be 1", 
                     1, managerB.getDirectSubordinateEmployeesCount());
    }
}