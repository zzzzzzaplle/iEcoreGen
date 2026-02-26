import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

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
        Manager managerM1 = new Manager();
        managerM1.setName("M1");
        managerM1.setBirthDate(dateFormat.parse("1980-01-01 00:00:00"));
        managerM1.setSocialInsuranceNumber("123456789");
        managerM1.setDepartment("Production");
        managerM1.setSalary(50000.0);
        managerM1.setPosition("Senior Manager");
        
        // Create Worker W1
        OffShiftWorker workerW1 = new OffShiftWorker();
        workerW1.setName("W1");
        workerW1.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
        workerW1.setSocialInsuranceNumber("987654321");
        workerW1.setDepartment("Production");
        workerW1.setWeeklyWorkingHour(40);
        workerW1.setHourlyRates(25.0);
        
        // Create SalesPerson S1
        SalesPeople salesPersonS1 = new SalesPeople();
        salesPersonS1.setName("S1");
        salesPersonS1.setBirthDate(dateFormat.parse("1985-01-01 00:00:00"));
        salesPersonS1.setSocialInsuranceNumber("555555555");
        salesPersonS1.setDepartment("Sales");
        salesPersonS1.setSalary(30000.0);
        salesPersonS1.setAmountOfSales(100000.0);
        salesPersonS1.setCommissionPercentage(0.1);
        
        // Create Manager M2
        Manager managerM2 = new Manager();
        managerM2.setName("M2");
        managerM2.setBirthDate(dateFormat.parse("1975-01-01 00:00:00"));
        managerM2.setSocialInsuranceNumber("111111111");
        managerM2.setDepartment("Control");
        managerM2.setSalary(45000.0);
        managerM2.setPosition("Junior Manager");
        
        // Assign subordinates to M1
        managerM1.addSubordinate(workerW1);
        managerM1.addSubordinate(salesPersonS1);
        managerM1.addSubordinate(managerM2);
        
        company.addEmployee(managerM1);
        company.addEmployee(workerW1);
        company.addEmployee(salesPersonS1);
        company.addEmployee(managerM2);
        
        // Execute: Get manager subordinate counts
        Map<String, Integer> result = company.getManagerSubordinateCounts();
        
        // Verify: The number of direct subordinates for Manager M1 is 3
        assertEquals("Manager M1 should have 3 subordinates", Integer.valueOf(3), result.get("M1"));
    }
    
    @Test
    public void testCase2_ManagerWithNoSubordinates() throws Exception {
        // Setup: Create Manager M1 with no subordinates
        Manager managerM1 = new Manager();
        managerM1.setName("M1");
        managerM1.setBirthDate(dateFormat.parse("1980-01-01 00:00:00"));
        managerM1.setSocialInsuranceNumber("123456789");
        managerM1.setDepartment("Production");
        managerM1.setSalary(50000.0);
        managerM1.setPosition("Senior Manager");
        
        company.addEmployee(managerM1);
        
        // Execute: Get manager subordinate counts
        Map<String, Integer> result = company.getManagerSubordinateCounts();
        
        // Verify: The number of subordinates for the manager is 0
        assertEquals("Manager M1 should have 0 subordinates", Integer.valueOf(0), result.get("M1"));
    }
    
    @Test
    public void testCase3_MultipleManagersWithDifferentNumberOfSubordinates() throws Exception {
        // Setup: Create Manager A
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        managerA.setBirthDate(dateFormat.parse("1970-01-01 00:00:00"));
        managerA.setSocialInsuranceNumber("111111111");
        managerA.setDepartment("Production");
        managerA.setSalary(60000.0);
        managerA.setPosition("Director");
        
        // Create ShiftWorker for Manager A
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setName("SW1");
        shiftWorker1.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
        shiftWorker1.setSocialInsuranceNumber("222222222");
        shiftWorker1.setDepartment("Production");
        shiftWorker1.setWeeklyWorkingHour(35);
        shiftWorker1.setHourlyRates(20.0);
        shiftWorker1.setHolidayPremium(500.0);
        
        // Create OffShiftWorker for Manager A
        OffShiftWorker offShiftWorker1 = new OffShiftWorker();
        offShiftWorker1.setName("OSW1");
        offShiftWorker1.setBirthDate(dateFormat.parse("1991-01-01 00:00:00"));
        offShiftWorker1.setSocialInsuranceNumber("333333333");
        offShiftWorker1.setDepartment("Production");
        offShiftWorker1.setWeeklyWorkingHour(40);
        offShiftWorker1.setHourlyRates(22.0);
        
        // Create SalesPeople for Manager A
        SalesPeople salesPerson1 = new SalesPeople();
        salesPerson1.setName("SP1");
        salesPerson1.setBirthDate(dateFormat.parse("1985-01-01 00:00:00"));
        salesPerson1.setSocialInsuranceNumber("444444444");
        salesPerson1.setDepartment("Sales");
        salesPerson1.setSalary(35000.0);
        salesPerson1.setAmountOfSales(150000.0);
        salesPerson1.setCommissionPercentage(0.08);
        
        // Assign 3 subordinates to Manager A
        managerA.addSubordinate(shiftWorker1);
        managerA.addSubordinate(offShiftWorker1);
        managerA.addSubordinate(salesPerson1);
        
        // Setup: Create Manager B
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        managerB.setBirthDate(dateFormat.parse("1975-01-01 00:00:00"));
        managerB.setSocialInsuranceNumber("555555555");
        managerB.setDepartment("Control");
        managerB.setSalary(55000.0);
        managerB.setPosition("Supervisor");
        
        // Create 7 subordinates for Manager B (2 ShiftWorker, 2 OffShiftWorker, 3 SalesPeople)
        for (int i = 1; i <= 2; i++) {
            ShiftWorker sw = new ShiftWorker();
            sw.setName("SW_B" + i);
            sw.setBirthDate(dateFormat.parse("1992-01-01 00:00:00"));
            sw.setSocialInsuranceNumber("66666666" + i);
            sw.setDepartment("Control");
            sw.setWeeklyWorkingHour(38);
            sw.setHourlyRates(21.0);
            sw.setHolidayPremium(450.0);
            managerB.addSubordinate(sw);
            company.addEmployee(sw);
        }
        
        for (int i = 1; i <= 2; i++) {
            OffShiftWorker osw = new OffShiftWorker();
            osw.setName("OSW_B" + i);
            osw.setBirthDate(dateFormat.parse("1993-01-01 00:00:00"));
            osw.setSocialInsuranceNumber("77777777" + i);
            osw.setDepartment("Control");
            osw.setWeeklyWorkingHour(42);
            osw.setHourlyRates(23.0);
            managerB.addSubordinate(osw);
            company.addEmployee(osw);
        }
        
        for (int i = 1; i <= 3; i++) {
            SalesPeople sp = new SalesPeople();
            sp.setName("SP_B" + i);
            sp.setBirthDate(dateFormat.parse("1988-01-01 00:00:00"));
            sp.setSocialInsuranceNumber("88888888" + i);
            sp.setDepartment("Sales");
            sp.setSalary(32000.0);
            sp.setAmountOfSales(120000.0);
            sp.setCommissionPercentage(0.07);
            managerB.addSubordinate(sp);
            company.addEmployee(sp);
        }
        
        // Add all employees to company
        company.addEmployee(managerA);
        company.addEmployee(shiftWorker1);
        company.addEmployee(offShiftWorker1);
        company.addEmployee(salesPerson1);
        company.addEmployee(managerB);
        
        // Execute: Get manager subordinate counts
        Map<String, Integer> result = company.getManagerSubordinateCounts();
        
        // Verify: The number of subordinates for Manager A is 3. The number of subordinates for Manager B is 7.
        assertEquals("Manager A should have 3 subordinates", Integer.valueOf(3), result.get("Manager A"));
        assertEquals("Manager B should have 7 subordinates", Integer.valueOf(7), result.get("Manager B"));
    }
    
    @Test
    public void testCase4_ManagerWithSingleSubordinate() throws Exception {
        // Setup: Create Manager M1
        Manager managerM1 = new Manager();
        managerM1.setName("M1");
        managerM1.setBirthDate(dateFormat.parse("1980-01-01 00:00:00"));
        managerM1.setSocialInsuranceNumber("123456789");
        managerM1.setDepartment("Production");
        managerM1.setSalary(50000.0);
        managerM1.setPosition("Senior Manager");
        
        // Create Worker W1
        OffShiftWorker workerW1 = new OffShiftWorker();
        workerW1.setName("W1");
        workerW1.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
        workerW1.setSocialInsuranceNumber("987654321");
        workerW1.setDepartment("Production");
        workerW1.setWeeklyWorkingHour(40);
        workerW1.setHourlyRates(25.0);
        
        // Assign Worker W1 as subordinate to Manager M1
        managerM1.addSubordinate(workerW1);
        
        company.addEmployee(managerM1);
        company.addEmployee(workerW1);
        
        // Execute: Get manager subordinate counts
        Map<String, Integer> result = company.getManagerSubordinateCounts();
        
        // Verify: The number of subordinates for the manager is 1
        assertEquals("Manager M1 should have 1 subordinate", Integer.valueOf(1), result.get("M1"));
    }
    
    @Test
    public void testCase5_MultipleManagersWithNestedSubordinates() throws Exception {
        // Setup: Create Manager A
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        managerA.setBirthDate(dateFormat.parse("1970-01-01 00:00:00"));
        managerA.setSocialInsuranceNumber("111111111");
        managerA.setDepartment("Production");
        managerA.setSalary(60000.0);
        managerA.setPosition("Director");
        
        // Create Worker W1 for Manager A
        OffShiftWorker workerW1 = new OffShiftWorker();
        workerW1.setName("W1");
        workerW1.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
        workerW1.setSocialInsuranceNumber("222222222");
        workerW1.setDepartment("Production");
        workerW1.setWeeklyWorkingHour(40);
        workerW1.setHourlyRates(25.0);
        
        // Create SalesPerson S1, S2, S3 for Manager A
        SalesPeople salesPerson1 = new SalesPeople();
        salesPerson1.setName("S1");
        salesPerson1.setBirthDate(dateFormat.parse("1985-01-01 00:00:00"));
        salesPerson1.setSocialInsuranceNumber("333333333");
        salesPerson1.setDepartment("Sales");
        salesPerson1.setSalary(30000.0);
        salesPerson1.setAmountOfSales(100000.0);
        salesPerson1.setCommissionPercentage(0.1);
        
        SalesPeople salesPerson2 = new SalesPeople();
        salesPerson2.setName("S2");
        salesPerson2.setBirthDate(dateFormat.parse("1986-01-01 00:00:00"));
        salesPerson2.setSocialInsuranceNumber("444444444");
        salesPerson2.setDepartment("Sales");
        salesPerson2.setSalary(32000.0);
        salesPerson2.setAmountOfSales(120000.0);
        salesPerson2.setCommissionPercentage(0.1);
        
        SalesPeople salesPerson3 = new SalesPeople();
        salesPerson3.setName("S3");
        salesPerson3.setBirthDate(dateFormat.parse("1987-01-01 00:00:00"));
        salesPerson3.setSocialInsuranceNumber("555555555");
        salesPerson3.setDepartment("Sales");
        salesPerson3.setSalary(31000.0);
        salesPerson3.setAmountOfSales(110000.0);
        salesPerson3.setCommissionPercentage(0.1);
        
        // Create Manager B for Manager A
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        managerB.setBirthDate(dateFormat.parse("1975-01-01 00:00:00"));
        managerB.setSocialInsuranceNumber("666666666");
        managerB.setDepartment("Control");
        managerB.setSalary(45000.0);
        managerB.setPosition("Supervisor");
        
        // Assign subordinates to Manager A (Worker W1, SalesPerson S1, S2, S3, Manager B)
        managerA.addSubordinate(workerW1);
        managerA.addSubordinate(salesPerson1);
        managerA.addSubordinate(salesPerson2);
        managerA.addSubordinate(salesPerson3);
        managerA.addSubordinate(managerB);
        
        // Create Worker W1 for Manager B (same worker, but can be different)
        OffShiftWorker workerW1ForB = new OffShiftWorker();
        workerW1ForB.setName("W1");
        workerW1ForB.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
        workerW1ForB.setSocialInsuranceNumber("777777777");
        workerW1ForB.setDepartment("Control");
        workerW1ForB.setWeeklyWorkingHour(35);
        workerW1ForB.setHourlyRates(22.0);
        
        // Assign subordinate to Manager B
        managerB.addSubordinate(workerW1ForB);
        
        // Add all employees to company
        company.addEmployee(managerA);
        company.addEmployee(workerW1);
        company.addEmployee(salesPerson1);
        company.addEmployee(salesPerson2);
        company.addEmployee(salesPerson3);
        company.addEmployee(managerB);
        company.addEmployee(workerW1ForB);
        
        // Execute: Get manager subordinate counts
        Map<String, Integer> result = company.getManagerSubordinateCounts();
        
        // Verify: The number of subordinates for Manager A is 5. The number of subordinates for Manager B is 1.
        assertEquals("Manager A should have 5 subordinates", Integer.valueOf(5), result.get("Manager A"));
        assertEquals("Manager B should have 1 subordinate", Integer.valueOf(1), result.get("Manager B"));
    }
}