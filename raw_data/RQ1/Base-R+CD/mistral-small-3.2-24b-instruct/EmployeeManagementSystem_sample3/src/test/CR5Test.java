import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        // Create Manager M1
        Manager managerM1 = new Manager();
        managerM1.setName("Manager M1");
        managerM1.setSocialInsuranceNumber("M1-SIN");
        managerM1.setBirthDate(dateFormat.parse("1980-01-01 00:00:00"));
        managerM1.setDepartment("Management");
        managerM1.setSalary(50000.0);
        managerM1.setPosition("Senior Manager");
        
        // Create Worker W1 (subordinate of M1)
        Worker workerW1 = new Worker() {};
        workerW1.setName("Worker W1");
        workerW1.setSocialInsuranceNumber("W1-SIN");
        workerW1.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
        workerW1.setDepartment("Production");
        workerW1.setWeeklyWorkingHour(40);
        workerW1.setHourlyRates(25.0);
        
        // Create SalesPerson S1 (subordinate of M1)
        SalesPeople salesPersonS1 = new SalesPeople();
        salesPersonS1.setName("SalesPerson S1");
        salesPersonS1.setSocialInsuranceNumber("S1-SIN");
        salesPersonS1.setBirthDate(dateFormat.parse("1985-01-01 00:00:00"));
        salesPersonS1.setDepartment("Sales");
        salesPersonS1.setSalary(30000.0);
        salesPersonS1.setAmountOfSales(100000.0);
        salesPersonS1.setCommissionPercentage(0.1);
        
        // Create Manager M2 (subordinate of M1)
        Manager managerM2 = new Manager();
        managerM2.setName("Manager M2");
        managerM2.setSocialInsuranceNumber("M2-SIN");
        managerM2.setBirthDate(dateFormat.parse("1982-01-01 00:00:00"));
        managerM2.setDepartment("Management");
        managerM2.setSalary(40000.0);
        managerM2.setPosition("Junior Manager");
        
        // Add all employees to company
        company.addEmployee(managerM1);
        company.addEmployee(workerW1);
        company.addEmployee(salesPersonS1);
        company.addEmployee(managerM2);
        
        // Test: The number of direct subordinates for Manager M1 is 3
        int subordinateCount = managerM1.getDirectSubordinateEmployeesCount(company);
        assertEquals("Manager M1 should have 3 direct subordinates", 3, subordinateCount);
    }
    
    @Test
    public void testCase2_ManagerWithNoSubordinates() throws Exception {
        // Create Manager M1 with no subordinates
        Manager managerM1 = new Manager();
        managerM1.setName("Manager M1");
        managerM1.setSocialInsuranceNumber("M1-SIN");
        managerM1.setBirthDate(dateFormat.parse("1980-01-01 00:00:00"));
        managerM1.setDepartment("Management");
        managerM1.setSalary(50000.0);
        managerM1.setPosition("Senior Manager");
        
        // Add only manager to company (no subordinates)
        company.addEmployee(managerM1);
        
        // Test: The number of subordinates for the manager is 0
        int subordinateCount = managerM1.getDirectSubordinateEmployeesCount(company);
        assertEquals("Manager M1 should have 0 subordinates", 0, subordinateCount);
    }
    
    @Test
    public void testCase3_MultipleManagersWithDifferentNumberOfSubordinates() throws Exception {
        // Create Manager A
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        managerA.setSocialInsuranceNumber("A-SIN");
        managerA.setBirthDate(dateFormat.parse("1975-01-01 00:00:00"));
        managerA.setDepartment("Management");
        managerA.setSalary(60000.0);
        managerA.setPosition("Director");
        
        // Create Manager B
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        managerB.setSocialInsuranceNumber("B-SIN");
        managerB.setBirthDate(dateFormat.parse("1978-01-01 00:00:00"));
        managerB.setDepartment("Management");
        managerB.setSalary(55000.0);
        managerB.setPosition("Manager");
        
        // Create 3 subordinates for Manager A: 1 ShiftWorker, 1 OffShiftWorker, 1 SalesPeople
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setName("Shift Worker 1");
        shiftWorker1.setSocialInsuranceNumber("SW1-SIN");
        shiftWorker1.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
        shiftWorker1.setDepartment("Production");
        shiftWorker1.setWeeklyWorkingHour(40);
        shiftWorker1.setHourlyRates(20.0);
        shiftWorker1.setHolidayPremium(200.0);
        
        OffShiftWorker offShiftWorker1 = new OffShiftWorker();
        offShiftWorker1.setName("OffShift Worker 1");
        offShiftWorker1.setSocialInsuranceNumber("OSW1-SIN");
        offShiftWorker1.setBirthDate(dateFormat.parse("1991-01-01 00:00:00"));
        offShiftWorker1.setDepartment("Production");
        offShiftWorker1.setWeeklyWorkingHour(35);
        offShiftWorker1.setHourlyRates(18.0);
        
        SalesPeople salesPerson1 = new SalesPeople();
        salesPerson1.setName("Sales Person 1");
        salesPerson1.setSocialInsuranceNumber("SP1-SIN");
        salesPerson1.setBirthDate(dateFormat.parse("1985-01-01 00:00:00"));
        salesPerson1.setDepartment("Sales");
        salesPerson1.setSalary(35000.0);
        salesPerson1.setAmountOfSales(150000.0);
        salesPerson1.setCommissionPercentage(0.08);
        
        // Create 7 subordinates for Manager B: 2 ShiftWorker, 2 OffShiftWorker, 3 SalesPeople
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setName("Shift Worker 2");
        shiftWorker2.setSocialInsuranceNumber("SW2-SIN");
        shiftWorker2.setBirthDate(dateFormat.parse("1992-01-01 00:00:00"));
        shiftWorker2.setDepartment("Production");
        shiftWorker2.setWeeklyWorkingHour(42);
        shiftWorker2.setHourlyRates(22.0);
        shiftWorker2.setHolidayPremium(220.0);
        
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setName("Shift Worker 3");
        shiftWorker3.setSocialInsuranceNumber("SW3-SIN");
        shiftWorker3.setBirthDate(dateFormat.parse("1993-01-01 00:00:00"));
        shiftWorker3.setDepartment("Production");
        shiftWorker3.setWeeklyWorkingHour(38);
        shiftWorker3.setHourlyRates(21.0);
        shiftWorker3.setHolidayPremium(210.0);
        
        OffShiftWorker offShiftWorker2 = new OffShiftWorker();
        offShiftWorker2.setName("OffShift Worker 2");
        offShiftWorker2.setSocialInsuranceNumber("OSW2-SIN");
        offShiftWorker2.setBirthDate(dateFormat.parse("1994-01-01 00:00:00"));
        offShiftWorker2.setDepartment("Production");
        offShiftWorker2.setWeeklyWorkingHour(36);
        offShiftWorker2.setHourlyRates(19.0);
        
        OffShiftWorker offShiftWorker3 = new OffShiftWorker();
        offShiftWorker3.setName("OffShift Worker 3");
        offShiftWorker3.setSocialInsuranceNumber("OSW3-SIN");
        offShiftWorker3.setBirthDate(dateFormat.parse("1995-01-01 00:00:00"));
        offShiftWorker3.setDepartment("Production");
        offShiftWorker3.setWeeklyWorkingHour(34);
        offShiftWorker3.setHourlyRates(17.0);
        
        SalesPeople salesPerson2 = new SalesPeople();
        salesPerson2.setName("Sales Person 2");
        salesPerson2.setSocialInsuranceNumber("SP2-SIN");
        salesPerson2.setBirthDate(dateFormat.parse("1986-01-01 00:00:00"));
        salesPerson2.setDepartment("Sales");
        salesPerson2.setSalary(32000.0);
        salesPerson2.setAmountOfSales(120000.0);
        salesPerson2.setCommissionPercentage(0.07);
        
        SalesPeople salesPerson3 = new SalesPeople();
        salesPerson3.setName("Sales Person 3");
        salesPerson3.setSocialInsuranceNumber("SP3-SIN");
        salesPerson3.setBirthDate(dateFormat.parse("1987-01-01 00:00:00"));
        salesPerson3.setDepartment("Sales");
        salesPerson3.setSalary(33000.0);
        salesPerson3.setAmountOfSales(130000.0);
        salesPerson3.setCommissionPercentage(0.075);
        
        SalesPeople salesPerson4 = new SalesPeople();
        salesPerson4.setName("Sales Person 4");
        salesPerson4.setSocialInsuranceNumber("SP4-SIN");
        salesPerson4.setBirthDate(dateFormat.parse("1988-01-01 00:00:00"));
        salesPerson4.setDepartment("Sales");
        salesPerson4.setSalary(34000.0);
        salesPerson4.setAmountOfSales(140000.0);
        salesPerson4.setCommissionPercentage(0.08);
        
        // Add all employees to company
        company.addEmployee(managerA);
        company.addEmployee(managerB);
        company.addEmployee(shiftWorker1);
        company.addEmployee(offShiftWorker1);
        company.addEmployee(salesPerson1);
        company.addEmployee(shiftWorker2);
        company.addEmployee(shiftWorker3);
        company.addEmployee(offShiftWorker2);
        company.addEmployee(offShiftWorker3);
        company.addEmployee(salesPerson2);
        company.addEmployee(salesPerson3);
        company.addEmployee(salesPerson4);
        
        // Test: The number of subordinates for Manager A is 3
        int subordinateCountA = managerA.getDirectSubordinateEmployeesCount(company);
        assertEquals("Manager A should have 3 subordinates", 3, subordinateCountA);
        
        // Test: The number of subordinates for Manager B is 7
        int subordinateCountB = managerB.getDirectSubordinateEmployeesCount(company);
        assertEquals("Manager B should have 7 subordinates", 7, subordinateCountB);
    }
    
    @Test
    public void testCase4_ManagerWithSingleSubordinate() throws Exception {
        // Create Manager M1
        Manager managerM1 = new Manager();
        managerM1.setName("Manager M1");
        managerM1.setSocialInsuranceNumber("M1-SIN");
        managerM1.setBirthDate(dateFormat.parse("1980-01-01 00:00:00"));
        managerM1.setDepartment("Management");
        managerM1.setSalary(50000.0);
        managerM1.setPosition("Senior Manager");
        
        // Create Worker W1 (subordinate of M1)
        Worker workerW1 = new Worker() {};
        workerW1.setName("Worker W1");
        workerW1.setSocialInsuranceNumber("W1-SIN");
        workerW1.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
        workerW1.setDepartment("Production");
        workerW1.setWeeklyWorkingHour(40);
        workerW1.setHourlyRates(25.0);
        
        // Add employees to company
        company.addEmployee(managerM1);
        company.addEmployee(workerW1);
        
        // Test: The number of subordinates for the manager is 1
        int subordinateCount = managerM1.getDirectSubordinateEmployeesCount(company);
        assertEquals("Manager M1 should have 1 subordinate", 1, subordinateCount);
    }
    
    @Test
    public void testCase5_MultipleManagersWithHierarchicalSubordinates() throws Exception {
        // Create Manager A
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        managerA.setSocialInsuranceNumber("A-SIN");
        managerA.setBirthDate(dateFormat.parse("1975-01-01 00:00:00"));
        managerA.setDepartment("Management");
        managerA.setSalary(60000.0);
        managerA.setPosition("Director");
        
        // Create Manager B (subordinate of Manager A)
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        managerB.setSocialInsuranceNumber("B-SIN");
        managerB.setBirthDate(dateFormat.parse("1978-01-01 00:00:00"));
        managerB.setDepartment("Management");
        managerB.setSalary(55000.0);
        managerB.setPosition("Manager");
        
        // Create Worker W1 (subordinate of Manager A)
        Worker workerW1 = new Worker() {};
        workerW1.setName("Worker W1");
        workerW1.setSocialInsuranceNumber("W1-SIN");
        workerW1.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
        workerW1.setDepartment("Production");
        workerW1.setWeeklyWorkingHour(40);
        workerW1.setHourlyRates(25.0);
        
        // Create SalesPerson S1, S2, S3 (subordinates of Manager A)
        SalesPeople salesPerson1 = new SalesPeople();
        salesPerson1.setName("Sales Person 1");
        salesPerson1.setSocialInsuranceNumber("S1-SIN");
        salesPerson1.setBirthDate(dateFormat.parse("1985-01-01 00:00:00"));
        salesPerson1.setDepartment("Sales");
        salesPerson1.setSalary(35000.0);
        salesPerson1.setAmountOfSales(150000.0);
        salesPerson1.setCommissionPercentage(0.08);
        
        SalesPeople salesPerson2 = new SalesPeople();
        salesPerson2.setName("Sales Person 2");
        salesPerson2.setSocialInsuranceNumber("S2-SIN");
        salesPerson2.setBirthDate(dateFormat.parse("1986-01-01 00:00:00"));
        salesPerson2.setDepartment("Sales");
        salesPerson2.setSalary(32000.0);
        salesPerson2.setAmountOfSales(120000.0);
        salesPerson2.setCommissionPercentage(0.07);
        
        SalesPeople salesPerson3 = new SalesPeople();
        salesPerson3.setName("Sales Person 3");
        salesPerson3.setSocialInsuranceNumber("S3-SIN");
        salesPerson3.setBirthDate(dateFormat.parse("1987-01-01 00:00:00"));
        salesPerson3.setDepartment("Sales");
        salesPerson3.setSalary(33000.0);
        salesPerson3.setAmountOfSales(130000.0);
        salesPerson3.setCommissionPercentage(0.075);
        
        // Create Worker W2 (subordinate of Manager B)
        Worker workerW2 = new Worker() {};
        workerW2.setName("Worker W2");
        workerW2.setSocialInsuranceNumber("W2-SIN");
        workerW2.setBirthDate(dateFormat.parse("1991-01-01 00:00:00"));
        workerW2.setDepartment("Production");
        workerW2.setWeeklyWorkingHour(35);
        workerW2.setHourlyRates(22.0);
        
        // Add all employees to company
        company.addEmployee(managerA);
        company.addEmployee(managerB);
        company.addEmployee(workerW1);
        company.addEmployee(salesPerson1);
        company.addEmployee(salesPerson2);
        company.addEmployee(salesPerson3);
        company.addEmployee(workerW2);
        
        // Test: The number of subordinates for Manager A is 5
        int subordinateCountA = managerA.getDirectSubordinateEmployeesCount(company);
        assertEquals("Manager A should have 5 subordinates", 5, subordinateCountA);
        
        // Test: The number of subordinates for Manager B is 1
        int subordinateCountB = managerB.getDirectSubordinateEmployeesCount(company);
        assertEquals("Manager B should have 1 subordinate", 1, subordinateCountB);
    }
}