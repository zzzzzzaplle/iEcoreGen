package edu.department.department5.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.department.DepartmentFactory;
import edu.department.DepartmentPackage;
import edu.department.Company;
import edu.department.Department;
import edu.department.Employee;
import org.eclipse.emf.common.util.EList;

public class CR5Test {
    
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
    public void testCase1_CompanyWithBothManagedAndUnmanagedDepartments() {
        // SetUp: Create Company with Departments
        Department managedDept = factory.createDepartment();
        // Note: Department class doesn't have setName method, removing this call
        
        Department unmanagedDept = factory.createDepartment();
        // Note: Department class doesn't have setName method, removing this call
        
        Department newDept = factory.createDepartment();
        // Note: Department class doesn't have setName method, removing this call
        
        // Add departments to company
        company.getDepartments().add(managedDept);
        company.getDepartments().add(unmanagedDept);
        company.getDepartments().add(newDept);
        
        // Assign manager to "Managed" department only
        Employee manager = factory.createEmployee();
        manager.setName("M1");
        company.getEmployees().add(manager);
        managedDept.setManager(manager);
        
        // Action: List departments without manager
        EList<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: should contain unmanagedDept and newDept, but not managedDept
        assertNotNull("Result should not be null", result);
        assertEquals("Should have 2 departments without manager", 2, result.size());
        assertTrue("Should contain unmanaged department", result.contains(unmanagedDept));
        assertTrue("Should contain new department", result.contains(newDept));
        assertFalse("Should not contain managed department", result.contains(managedDept));
    }
    
    @Test
    public void testCase2_FullyManagedCompany() {
        // SetUp: Create Company with 3 departments
        Department dept1 = factory.createDepartment();
        // Note: Department class doesn't have setName method, removing this call
        
        Department dept2 = factory.createDepartment();
        // Note: Department class doesn't have setName method, removing this call
        
        Department dept3 = factory.createDepartment();
        // Note: Department class doesn't have setName method, removing this call
        
        // Add departments to company
        company.getDepartments().add(dept1);
        company.getDepartments().add(dept2);
        company.getDepartments().add(dept3);
        
        // Assign managers to each department
        Employee m1 = factory.createEmployee();
        m1.setName("M1");
        Employee m2 = factory.createEmployee();
        m2.setName("M2");
        Employee m3 = factory.createEmployee();
        m3.setName("M3");
        
        company.getEmployees().add(m1);
        company.getEmployees().add(m2);
        company.getEmployees().add(m3);
        
        dept1.setManager(m1);
        dept2.setManager(m2);
        dept3.setManager(m3);
        
        // Action: List departments without manager
        EList<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Result should be null when all departments have managers", result);
    }
    
    @Test
    public void testCase3_EmptyCompany() {
        // SetUp: Create Company with no departments
        // (company is already empty from setUp)
        
        // Action: List departments without manager
        EList<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: null
        assertNull("Result should be null when company has no departments", result);
    }
    
    @Test
    public void testCase4_SingleUnmanagedDepartment() {
        // SetUp: Create Company with only "Orphan" department
        Department orphanDept = factory.createDepartment();
        // Note: Department class doesn't have setName method, removing this call
        company.getDepartments().add(orphanDept);
        
        // Action: List departments without manager
        EList<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Orphan"]
        assertNotNull("Result should not be null", result);
        assertEquals("Should have 1 department without manager", 1, result.size());
        assertTrue("Should contain orphan department", result.contains(orphanDept));
    }
    
    @Test
    public void testCase5_AfterManagerRemoval() {
        // SetUp: Create Company with Department "Volatile"
        Department volatileDept = factory.createDepartment();
        // Note: Department class doesn't have setName method, removing this call
        company.getDepartments().add(volatileDept);
        
        // Assign manager M1 and then remove manager M1
        Employee m1 = factory.createEmployee();
        m1.setName("M1");
        company.getEmployees().add(m1);
        volatileDept.setManager(m1);
        
        // Remove the manager
        volatileDept.setManager(null);
        
        // Action: List departments without manager
        EList<Department> result = company.listDepartmentsWithoutManager();
        
        // Expected Output: ["Volatile"]
        assertNotNull("Result should not be null", result);
        assertEquals("Should have 1 department without manager", 1, result.size());
        assertTrue("Should contain volatile department", result.contains(volatileDept));
    }
}