import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

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
        // Create Manager M1
        Manager m1 = new Manager();
        m1.setName("Manager M1");
        m1.setBirthDate(dateFormat.parse("1980-01-01 00:00:00"));
        m1.setSocialInsuranceNumber("123-45-6789");
        m1.setDepartment(DepartmentType.PRODUCTION);
        m1.setSalary(5000.0);
        m1.setPosition("Senior Manager");
        
        // Create Worker W1
        Worker w1 = new OffShiftWorker();
        w1.setName("Worker W1");
        w1.setBirthDate(dateFormat.parse("1990-02-02 00:00:00"));
        w1.setSocialInsuranceNumber("234-56-7890");
        w1.setDepartment(DepartmentType.PRODUCTION);
        w1.setWeeklyWorkingHour(40);
        w1.setHourlyRates(25.0);
        
        // Create SalesPerson S1
        SalesPeople s1 = new SalesPeople();
        s1.setName("SalesPerson S1");
        s1.setBirthDate(dateFormat.parse("1985-03-03 00:00:00"));
        s1.setSocialInsuranceNumber("345-67-8901");
        s1.setDepartment(DepartmentType.PRODUCTION);
        s1.setSalary(3000.0);
        s1.setAmountOfSales(10000.0);
        s1.setCommissionPercentage(0.1);
        
        // Create Manager M2
        Manager m2 = new Manager();
        m2.setName("Manager M2");
        m2.setBirthDate(dateFormat.parse("1975-04-04 00:00:00"));
        m2.setSocialInsuranceNumber("456-78-9012");
        m2.setDepartment(DepartmentType.PRODUCTION);
        m2.setSalary(4000.0);
        m2.setPosition("Junior Manager");
        
        // Assign subordinates to M1
        m1.addSubordinate(w1);
        m1.addSubordinate(s1);
        m1.addSubordinate(m2);
        
        // Verify the number of direct subordinates for Manager M1 is 3
        assertEquals("The number of direct subordinates for Manager M1 is 3", 3, m1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase2_ManagerWithNoSubordinates() throws Exception {
        // Create Manager M1
        Manager m1 = new Manager();
        m1.setName("Manager M1");
        m1.setBirthDate(dateFormat.parse("1980-01-01 00:00:00"));
        m1.setSocialInsuranceNumber("123-45-6789");
        m1.setDepartment(DepartmentType.CONTROL);
        m1.setSalary(5000.0);
        m1.setPosition("Department Head");
        
        // Do not assign any subordinates
        
        // Verify the number of subordinates for the manager is 0
        assertEquals("The number of subordinates for the manager is 0", 0, m1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase3_MultipleManagersWithDifferentNumberOfSubordinates() throws Exception {
        // Create Manager A
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        managerA.setBirthDate(dateFormat.parse("1970-01-01 00:00:00"));
        managerA.setSocialInsuranceNumber("111-11-1111");
        managerA.setDepartment(DepartmentType.PRODUCTION);
        managerA.setSalary(6000.0);
        managerA.setPosition("Production Manager");
        
        // Create Manager B
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        managerB.setBirthDate(dateFormat.parse("1975-02-02 00:00:00"));
        managerB.setSocialInsuranceNumber("222-22-2222");
        managerB.setDepartment(DepartmentType.DELIVERY);
        managerB.setSalary(5500.0);
        managerB.setPosition("Delivery Manager");
        
        // Create subordinates for Manager A: 1 ShiftWorker, 1 OffShiftWorker, 1 SalesPeople
        ShiftWorker sw1 = new ShiftWorker();
        sw1.setName("ShiftWorker 1");
        sw1.setBirthDate(dateFormat.parse("1990-03-03 00:00:00"));
        sw1.setSocialInsuranceNumber("333-33-3333");
        sw1.setDepartment(DepartmentType.PRODUCTION);
        sw1.setWeeklyWorkingHour(35);
        sw1.setHourlyRates(30.0);
        sw1.setHolidayPremium(200.0);
        
        OffShiftWorker osw1 = new OffShiftWorker();
        osw1.setName("OffShiftWorker 1");
        osw1.setBirthDate(dateFormat.parse("1991-04-04 00:00:00"));
        osw1.setSocialInsuranceNumber("444-44-4444");
        osw1.setDepartment(DepartmentType.PRODUCTION);
        osw1.setWeeklyWorkingHour(40);
        osw1.setHourlyRates(25.0);
        
        SalesPeople sp1 = new SalesPeople();
        sp1.setName("SalesPeople 1");
        sp1.setBirthDate(dateFormat.parse("1985-05-05 00:00:00"));
        sp1.setSocialInsuranceNumber("555-55-5555");
        sp1.setDepartment(DepartmentType.PRODUCTION);
        sp1.setSalary(3500.0);
        sp1.setAmountOfSales(15000.0);
        sp1.setCommissionPercentage(0.08);
        
        // Assign subordinates to Manager A
        managerA.addSubordinate(sw1);
        managerA.addSubordinate(osw1);
        managerA.addSubordinate(sp1);
        
        // Create subordinates for Manager B: 2 ShiftWorker, 2 OffShiftWorker, 3 SalesPeople
        for (int i = 1; i <= 2; i++) {
            ShiftWorker sw = new ShiftWorker();
            sw.setName("ShiftWorker B" + i);
            sw.setBirthDate(dateFormat.parse("1992-0" + i + "-01 00:00:00"));
            sw.setSocialInsuranceNumber("666-66-666" + i);
            sw.setDepartment(DepartmentType.DELIVERY);
            sw.setWeeklyWorkingHour(38);
            sw.setHourlyRates(28.0);
            sw.setHolidayPremium(180.0);
            managerB.addSubordinate(sw);
        }
        
        for (int i = 1; i <= 2; i++) {
            OffShiftWorker osw = new OffShiftWorker();
            osw.setName("OffShiftWorker B" + i);
            osw.setBirthDate(dateFormat.parse("1993-0" + i + "-01 00:00:00"));
            osw.setSocialInsuranceNumber("777-77-777" + i);
            osw.setDepartment(DepartmentType.DELIVERY);
            osw.setWeeklyWorkingHour(42);
            osw.setHourlyRates(26.0);
            managerB.addSubordinate(osw);
        }
        
        for (int i = 1; i <= 3; i++) {
            SalesPeople sp = new SalesPeople();
            sp.setName("SalesPeople B" + i);
            sp.setBirthDate(dateFormat.parse("1988-0" + i + "-01 00:00:00"));
            sp.setSocialInsuranceNumber("888-88-888" + i);
            sp.setDepartment(DepartmentType.DELIVERY);
            sp.setSalary(3200.0);
            sp.setAmountOfSales(12000.0);
            sp.setCommissionPercentage(0.09);
            managerB.addSubordinate(sp);
        }
        
        // Verify the number of subordinates for Manager A is 3 and Manager B is 7
        assertEquals("The number of subordinates for Manager A is 3", 3, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("The number of subordinates for Manager B is 7", 7, managerB.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase4_ManagerWithSingleSubordinate() throws Exception {
        // Create Manager M1
        Manager m1 = new Manager();
        m1.setName("Manager M1");
        m1.setBirthDate(dateFormat.parse("1980-01-01 00:00:00"));
        m1.setSocialInsuranceNumber("123-45-6789");
        m1.setDepartment(DepartmentType.CONTROL);
        m1.setSalary(5000.0);
        m1.setPosition("Team Lead");
        
        // Create Worker W1
        Worker w1 = new OffShiftWorker();
        w1.setName("Worker W1");
        w1.setBirthDate(dateFormat.parse("1990-02-02 00:00:00"));
        w1.setSocialInsuranceNumber("234-56-7890");
        w1.setDepartment(DepartmentType.CONTROL);
        w1.setWeeklyWorkingHour(40);
        w1.setHourlyRates(25.0);
        
        // Assign Worker W1 as subordinate to M1
        m1.addSubordinate(w1);
        
        // Verify the number of subordinates for the manager is 1
        assertEquals("The number of subordinates for the manager is 1", 1, m1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase5_MultipleManagersWithDifferentNumberOfSubordinatesComplex() throws Exception {
        // Create Manager A
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        managerA.setBirthDate(dateFormat.parse("1970-01-01 00:00:00"));
        managerA.setSocialInsuranceNumber("111-11-1111");
        managerA.setDepartment(DepartmentType.PRODUCTION);
        managerA.setSalary(6000.0);
        managerA.setPosition("Senior Manager");
        
        // Create Manager B
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        managerB.setBirthDate(dateFormat.parse("1975-02-02 00:00:00"));
        managerB.setSocialInsuranceNumber("222-22-2222");
        managerB.setDepartment(DepartmentType.PRODUCTION);
        managerB.setSalary(4500.0);
        managerB.setPosition("Junior Manager");
        
        // Create Worker W1
        Worker w1 = new OffShiftWorker();
        w1.setName("Worker W1");
        w1.setBirthDate(dateFormat.parse("1990-03-03 00:00:00"));
        w1.setSocialInsuranceNumber("333-33-3333");
        w1.setDepartment(DepartmentType.PRODUCTION);
        w1.setWeeklyWorkingHour(40);
        w1.setHourlyRates(25.0);
        
        // Create SalesPerson S1, S2, S3
        SalesPeople s1 = new SalesPeople();
        s1.setName("SalesPerson S1");
        s1.setBirthDate(dateFormat.parse("1985-04-04 00:00:00"));
        s1.setSocialInsuranceNumber("444-44-4444");
        s1.setDepartment(DepartmentType.PRODUCTION);
        s1.setSalary(3000.0);
        s1.setAmountOfSales(10000.0);
        s1.setCommissionPercentage(0.1);
        
        SalesPeople s2 = new SalesPeople();
        s2.setName("SalesPerson S2");
        s2.setBirthDate(dateFormat.parse("1986-05-05 00:00:00"));
        s2.setSocialInsuranceNumber("555-55-5555");
        s2.setDepartment(DepartmentType.PRODUCTION);
        s2.setSalary(3200.0);
        s2.setAmountOfSales(12000.0);
        s2.setCommissionPercentage(0.1);
        
        SalesPeople s3 = new SalesPeople();
        s3.setName("SalesPerson S3");
        s3.setBirthDate(dateFormat.parse("1987-06-06 00:00:00"));
        s3.setSocialInsuranceNumber("666-66-6666");
        s3.setDepartment(DepartmentType.PRODUCTION);
        s3.setSalary(3100.0);
        s3.setAmountOfSales(11000.0);
        s3.setCommissionPercentage(0.1);
        
        // Create Worker W1 for Manager B
        Worker w1b = new OffShiftWorker();
        w1b.setName("Worker W1");
        w1b.setBirthDate(dateFormat.parse("1991-07-07 00:00:00"));
        w1b.setSocialInsuranceNumber("777-77-7777");
        w1b.setDepartment(DepartmentType.PRODUCTION);
        w1b.setWeeklyWorkingHour(35);
        w1b.setHourlyRates(28.0);
        
        // Assign subordinates to Manager A: Worker W1, SalesPerson S1, S2, S3, Manager B
        managerA.addSubordinate(w1);
        managerA.addSubordinate(s1);
        managerA.addSubordinate(s2);
        managerA.addSubordinate(s3);
        managerA.addSubordinate(managerB);
        
        // Assign subordinate to Manager B: Worker W1
        managerB.addSubordinate(w1b);
        
        // Verify the number of subordinates for Manager A is 5 and Manager B is 1
        assertEquals("The number of subordinates for Manager A is 5", 5, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("The number of subordinates for Manager B is 1", 1, managerB.getDirectSubordinateEmployeesCount());
    }
}