import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR1Test {
    private Company company;
    private Department engineering;
    private Department marketing;
    private Department hr;
    private Office officeA;
    private Office officeB;
    private Office hrMain;
    
    @Before
    public void setUp() {
        company = new Company();
        
        // Create departments
        engineering = new Department();
        marketing = new Department();
        hr = new Department();
        
        // Create offices
        officeA = new Office("OfficeA");
        officeB = new Office("OfficeB");
        hrMain = new Office("HR-Main");
    }
    
    @Test
    public void testCase1_AssignValidHeadquarterToDepartmentWithoutExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering"
        company.addDepartment(engineering);
        
        // SetUp: Add Office "OfficeA" to Engineering
        engineering.getOffices().add(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = engineering.assignOfficeAsHeadquarter(officeA);
        
        // Expected Output: true
        assertTrue("Should successfully assign headquarter", result);
        
        // Verification: OfficeA is the headquarter of Engineering.
        assertEquals("OfficeA should be the headquarter", officeA, engineering.getHeadquarter());
    }
    
    @Test
    public void testCase2_AssignOfficeNotBelongingToDepartment() {
        // SetUp: Create Company with Departments "Engineering" and "Marketing"
        company.addDepartment(engineering);
        company.addDepartment(marketing);
        
        // SetUp: Add Office "OfficeA" to Marketing
        marketing.getOffices().add(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = engineering.assignOfficeAsHeadquarter(officeA);
        
        // Expected Output: false
        assertFalse("Should fail to assign headquarter - office not in department", result);
        
        // Verification: Engineering do not have headquarter
        assertNull("Engineering should not have a headquarter", engineering.getHeadquarter());
    }
    
    @Test
    public void testCase3_ReassignExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering"
        company.addDepartment(engineering);
        
        // SetUp: Add Offices "OfficeA" and "OfficeB" to Engineering
        engineering.getOffices().add(officeA);
        engineering.getOffices().add(officeB);
        
        // SetUp: Set "OfficeA" as headquarter
        engineering.assignOfficeAsHeadquarter(officeA);
        
        // Action: Set "OfficeB" as new headquarter
        boolean result = engineering.assignOfficeAsHeadquarter(officeB);
        
        // Expected Output: false
        assertFalse("Should fail to reassign headquarter - department already has one", result);
        
        // Verification: Engineering's headquarter remains OfficeA
        assertEquals("Headquarter should remain OfficeA", officeA, engineering.getHeadquarter());
    }
    
    @Test
    public void testCase4_AssignToDepartmentWithSingleOffice() {
        // SetUp: Create Company with Department "HR"
        company.addDepartment(hr);
        
        // SetUp: Add only Office "HR-Main" to HR
        hr.getOffices().add(hrMain);
        
        // Action: Set "HR-Main" as headquarter
        boolean result = hr.assignOfficeAsHeadquarter(hrMain);
        
        // Expected Output: true
        assertTrue("Should successfully assign headquarter", result);
        
        // Verification: HR's headquarter is HR-Main
        assertEquals("HR-Main should be the headquarter", hrMain, hr.getHeadquarter());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create Company with no departments
        // (company already created with no departments in setUp)
        
        // SetUp: Create Department "ghostDept"
        Department ghostDept = new Department();
        // Note: ghostDept is not added to company
        
        // Action: Set "OfficeX" as headquarter for "ghostDept"
        Office officeX = new Office("OfficeX");
        boolean result = ghostDept.assignOfficeAsHeadquarter(officeX);
        
        // Expected Output: false (because the office doesn't exist in the department)
        assertFalse("Should fail to assign headquarter - office not in department", result);
        
        // Note: The method only checks if the office belongs to the department, not if
        // the department belongs to the company, so the test case specification may be
        // slightly misleading. The actual logic only requires that the office exists
        // in the department's office list.
    }
}