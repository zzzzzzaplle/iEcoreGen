import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR1Test {
    
    private Company company;
    private Department engineering;
    private Department marketing;
    private Department hr;
    private Department ghostDept;
    private Office officeA;
    private Office officeB;
    private Office hrMain;
    private Office officeX;
    
    @Before
    public void setUp() {
        // Initialize common test objects
        company = new Company();
        engineering = new Department();
        marketing = new Department();
        hr = new Department();
        ghostDept = new Department();
        
        officeA = new Office();
        officeA.setIdentifier("OfficeA");
        
        officeB = new Office();
        officeB.setIdentifier("OfficeB");
        
        hrMain = new Office();
        hrMain.setIdentifier("HR-Main");
        
        officeX = new Office();
        officeX.setIdentifier("OfficeX");
    }
    
    @Test
    public void testCase1_AssignValidHeadquarterToDepartmentWithoutExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering"
        company.addDepartment(engineering);
        
        // SetUp: Add Office "OfficeA" to Engineering
        engineering.addOffice(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = engineering.assignOfficeAsHeadquarter(officeA);
        
        // Expected Output: true
        assertTrue("Should return true when assigning valid headquarter", result);
        
        // Verification: OfficeA is the headquarter of Engineering
        assertEquals("OfficeA should be set as headquarter", officeA, engineering.getHeadquarter());
    }
    
    @Test
    public void testCase2_AssignOfficeNotBelongingToDepartment() {
        // SetUp: Create Company with Departments "Engineering" and "Marketing"
        company.addDepartment(engineering);
        company.addDepartment(marketing);
        
        // SetUp: Add Office "OfficeA" to Marketing
        marketing.addOffice(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = engineering.assignOfficeAsHeadquarter(officeA);
        
        // Expected Output: false
        assertFalse("Should return false when office doesn't belong to department", result);
        
        // Verification: Engineering do not have headquarter
        assertNull("Engineering should not have a headquarter", engineering.getHeadquarter());
    }
    
    @Test
    public void testCase3_ReassignExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering"
        company.addDepartment(engineering);
        
        // SetUp: Add Offices "OfficeA" and "OfficeB" to Engineering
        engineering.addOffice(officeA);
        engineering.addOffice(officeB);
        
        // SetUp: Set "OfficeA" as headquarter
        engineering.assignOfficeAsHeadquarter(officeA);
        
        // Action: Set "OfficeB" as new headquarter
        boolean result = engineering.assignOfficeAsHeadquarter(officeB);
        
        // Expected Output: false
        assertFalse("Should return false when reassigning existing headquarter", result);
        
        // Verification: Engineering's headquarter remains OfficeA
        assertEquals("Headquarter should remain OfficeA", officeA, engineering.getHeadquarter());
    }
    
    @Test
    public void testCase4_AssignToDepartmentWithSingleOffice() {
        // SetUp: Create Company with Department "HR"
        company.addDepartment(hr);
        
        // SetUp: Add only Office "HR-Main" to HR
        hr.addOffice(hrMain);
        
        // Action: Set "HR-Main" as headquarter
        boolean result = hr.assignOfficeAsHeadquarter(hrMain);
        
        // Expected Output: true
        assertTrue("Should return true when assigning to department with single office", result);
        
        // Verification: HR's headquarter is HR-Main
        assertEquals("HR-Main should be set as headquarter", hrMain, hr.getHeadquarter());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create Company with no departments
        // (company is already created with no departments in setUp)
        
        // SetUp: Create Department "ghostDept" (but not added to company)
        // ghostDept is created in setUp but not added to company
        
        // Action: Set "OfficeX" as headquarter for "ghostDept"
        // Note: This test doesn't require the department to be in the company
        // since we're testing Department.assignOfficeAsHeadquarter directly
        boolean result = ghostDept.assignOfficeAsHeadquarter(officeX);
        
        // Expected Output: false (because the office doesn't exist in the department)
        assertFalse("Should return false when office doesn't exist in department", result);
        
        // Verification: ghostDept should not have a headquarter
        assertNull("ghostDept should not have a headquarter", ghostDept.getHeadquarter());
    }
}