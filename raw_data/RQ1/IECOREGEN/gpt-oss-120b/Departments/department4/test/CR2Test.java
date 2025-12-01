package edu.department.department4.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.department.DepartmentFactory;
import edu.department.DepartmentPackage;
import edu.department.Company;
import edu.department.Department;
import edu.department.Employee;

public class CR2Test {
    
    private DepartmentFactory factory;
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize the factory and package
        DepartmentPackage.eINSTANCE.eClass();
        factory = DepartmentFactory.eINSTANCE;
        company = factory.createCompany();
    }
    
    @Test
    public void testCase1_AssignValidEmployeeToManagerlessDepartment() {
        // SetUp: Create Company with Department "Sales"
        Department salesDept = factory.createDepartment();
        company.getDepartments().add(salesDept);
        
        // SetUp: Add Employee "E001" to Company
        Employee employeeE001 = factory.createEmployee();
        employeeE001.setID("E001");
        company.getEmployees().add(employeeE001);
        
        // Action: Assign "E001" as manager to "Sales"
        boolean result = company.assignManager(employeeE001, salesDept);
        
        // Expected Output: true
        assertTrue("Employee should be successfully assigned as manager", result);
        
        // Verification: Sales's manager == E001
        assertEquals("Sales department should have E001 as manager", employeeE001, salesDept.getManager());
        assertEquals("Employee's department should be set to Sales", salesDept, employeeE001.getDepartment());
    }
    
    @Test
    public void testCase2_AssignNonExistentEmployee() {
        // SetUp: Create Company with Department "Finance"
        Department financeDept = factory.createDepartment();
        company.getDepartments().add(financeDept);
        
        // SetUp: Create Employee "E999" (but not added to company)
        Employee employeeE999 = factory.createEmployee();
        employeeE999.setID("E999");
        // Note: Employee is NOT added to company employees list
        
        // Action: Assign "E999" (non-existent) as manager
        boolean result = company.assignManager(employeeE999, financeDept);
        
        // Expected Output: false
        assertFalse("Non-existent employee should not be assignable as manager", result);
        
        // Verification: Finance do not have a manager
        assertNull("Finance department should not have a manager", financeDept.getManager());
    }
    
    @Test
    public void testCase3_ReassignToDepartmentWithExistingManager() {
        // SetUp: Create Company with Department "IT"
        Department itDept = factory.createDepartment();
        company.getDepartments().add(itDept);
        
        // SetUp: Add Employees "E002" and "E003" to Company
        Employee employeeE002 = factory.createEmployee();
        employeeE002.setID("E002");
        company.getEmployees().add(employeeE002);
        
        Employee employeeE003 = factory.createEmployee();
        employeeE003.setID("E003");
        company.getEmployees().add(employeeE003);
        
        // SetUp: Assign "E002" as IT manager
        boolean firstAssignment = company.assignManager(employeeE002, itDept);
        assertTrue("First assignment should succeed", firstAssignment);
        
        // Action: Assign "E003" as new manager
        boolean secondAssignment = company.assignManager(employeeE003, itDept);
        
        // Expected Output: false
        assertFalse("Should not be able to reassign manager to department with existing manager", secondAssignment);
        
        // Verification: IT's manager remains E002
        assertEquals("IT department manager should remain E002", employeeE002, itDept.getManager());
        assertEquals("E002's department should remain IT", itDept, employeeE002.getDepartment());
        assertNull("E003 should not have a department assigned", employeeE003.getDepartment());
    }
    
    @Test
    public void testCase4_AssignEmployeeFromDifferentDepartment() {
        // SetUp: Create Company with Departments "R&D" and "Support"
        Department rdDept = factory.createDepartment();
        Department supportDept = factory.createDepartment();
        company.getDepartments().add(rdDept);
        company.getDepartments().add(supportDept);
        
        // SetUp: Add Employee "E004" to R&D
        Employee employeeE004 = factory.createEmployee();
        employeeE004.setID("E004");
        company.getEmployees().add(employeeE004);
        
        // First assign E004 to R&D department
        employeeE004.setDepartment(rdDept);
        
        // Action: Assign "E004" as Support manager
        boolean result = company.assignManager(employeeE004, supportDept);
        
        // Expected Output: true (since employee belongs to company)
        assertTrue("Employee from different department should be assignable as manager", result);
        
        // Verification: Support's manager is E004
        assertEquals("Support department should have E004 as manager", employeeE004, supportDept.getManager());
        assertEquals("E004's department should be updated to Support", supportDept, employeeE004.getDepartment());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create empty Company
        // Company is already empty from setUp()
        
        // SetUp: Create department "GhostDept" (but not added to company)
        Department ghostDept = factory.createDepartment();
        ghostDept.setManager(null);
        // Note: Department is NOT added to company departments list
        
        // SetUp: Create and add employee to company
        Employee employeeE001 = factory.createEmployee();
        employeeE001.setID("E001");
        company.getEmployees().add(employeeE001);
        
        // Action: Assign Employee "E001" to "GhostDept"
        boolean result = company.assignManager(employeeE001, ghostDept);
        
        // Expected Output: false
        assertFalse("Should not be able to assign manager to non-existent department", result);
        
        // Verification: Employee should not have department assigned
        assertNull("Employee should not have department assigned", employeeE001.getDepartment());
    }
}