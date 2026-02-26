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
    private Office officeX;
    
    @Before
    public void setUp() {
        company = new Company();
        engineering = new Department();
        marketing = new Department();
        hr = new Department();
        officeA = new Office("OfficeA");
        officeB = new Office("OfficeB");
        hrMain = new Office("HR-Main");
        officeX = new Office("OfficeX");
    }
    
    @Test
    public void testCase1_AssignValidHeadquarterToDepartmentWithoutExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering" and add Office "OfficeA" to Engineering
        company.addDepartment(engineering);
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
        // SetUp: Create Company with Departments "Engineering" and "Marketing", add Office "OfficeA" to Marketing
        company.addDepartment(engineering);
        company.addDepartment(marketing);
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
        // SetUp: Create Company with Department "Engineering", add Offices "OfficeA" and "OfficeB" to Engineering
        company.addDepartment(engineering);
        engineering.addOffice(officeA);
        engineering.addOffice(officeB);
        
        // Set "OfficeA" as headquarter
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
        // SetUp: Create Company with Department "HR", add only Office "HR-Main" to HR
        company.addDepartment(hr);
        hr.addOffice(hrMain);
        
        // Action: Set "HR-Main" as headquarter
        boolean result = hr.assignOfficeAsHeadquarter(hrMain);
        
        // Expected Output: true
        assertTrue("Should return true when assigning headquarter to department with single office", result);
        
        // Verification: HR's headquarter is HR-Main
        assertEquals("HR-Main should be set as headquarter", hrMain, hr.getHeadquarter());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create Company with no departments, create Department "ghostDept"
        // Note: ghostDept is not added to company, so it's effectively non-existent in company context
        Department ghostDept = new Department();
        
        // Action: Set "OfficeX" as headquarter for "ghostDept"
        // Note: The test specification mentions this should return false because the department doesn't belong to the Company,
        // but the assignOfficeAsHeadquarter method only checks if the office belongs to the department, not if the department belongs to a company
        boolean result = ghostDept.assignOfficeAsHeadquarter(officeX);
        
        // Expected Output: false (because the office doesn't belong to the department)
        assertFalse("Should return false when office doesn't belong to department", result);
        
        // Additional verification: ghostDept should not have a headquarter
        assertNull("ghostDept should not have a headquarter", ghostDept.getHeadquarter());
    }
}