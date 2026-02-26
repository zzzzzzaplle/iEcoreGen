import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
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
        company = new Company();
        engineering = new Department();
        engineering.setName("Engineering");
        
        marketing = new Department();
        marketing.setName("Marketing");
        
        hr = new Department();
        hr.setName("HR");
        
        ghostDept = new Department();
        ghostDept.setName("ghostDept");
        
        officeA = new Office();
        officeA.setLocation("OfficeA");
        
        officeB = new Office();
        officeB.setLocation("OfficeB");
        
        hrMain = new Office();
        hrMain.setLocation("HR-Main");
        
        officeX = new Office();
        officeX.setLocation("OfficeX");
    }
    
    @Test
    public void testCase1_AssignValidHeadquarterToDepartmentWithoutExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering"
        company.addDepartment(engineering);
        
        // SetUp: Add Office "OfficeA" to Engineering
        engineering.addOffice(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = company.assignHeadquarters(engineering, officeA);
        
        // Expected Output: true
        assertTrue("Assignment should succeed when office belongs to department without headquarters", result);
        
        // Verification: OfficeA is the headquarter of Engineering
        assertEquals("OfficeA should be set as headquarters", officeA, engineering.getHeadquarters());
    }
    
    @Test
    public void testCase2_AssignOfficeNotBelongingToDepartment() {
        // SetUp: Create Company with Departments "Engineering" and "Marketing"
        company.addDepartment(engineering);
        company.addDepartment(marketing);
        
        // SetUp: Add Office "OfficeA" to Marketing
        marketing.addOffice(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = company.assignHeadquarters(engineering, officeA);
        
        // Expected Output: false
        assertFalse("Assignment should fail when office doesn't belong to department", result);
        
        // Verification: Engineering do not have headquarter
        assertNull("Engineering should not have a headquarters", engineering.getHeadquarters());
    }
    
    @Test
    public void testCase3_ReassignExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering"
        company.addDepartment(engineering);
        
        // SetUp: Add Offices "OfficeA" and "OfficeB" to Engineering
        engineering.addOffice(officeA);
        engineering.addOffice(officeB);
        
        // SetUp: Set "OfficeA" as headquarter
        company.assignHeadquarters(engineering, officeA);
        
        // Action: Set "OfficeB" as new headquarter
        boolean result = company.assignHeadquarters(engineering, officeB);
        
        // Expected Output: false
        assertFalse("Reassignment should fail when department already has headquarters", result);
        
        // Verification: Engineering's headquarter remains OfficeA
        assertEquals("Headquarters should remain OfficeA", officeA, engineering.getHeadquarters());
    }
    
    @Test
    public void testCase4_AssignToDepartmentWithSingleOffice() {
        // SetUp: Create Company with Department "HR"
        company.addDepartment(hr);
        
        // SetUp: Add only Office "HR-Main" to HR
        hr.addOffice(hrMain);
        
        // Action: Set "HR-Main" as headquarter
        boolean result = company.assignHeadquarters(hr, hrMain);
        
        // Expected Output: true
        assertTrue("Assignment should succeed with single office", result);
        
        // Verification: HR's headquarter is HR-Main
        assertEquals("HR-Main should be set as headquarters", hrMain, hr.getHeadquarters());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create Company with no departments
        // (company starts with no departments)
        
        // SetUp: Create Department "ghostDept" (but don't add to company)
        // ghostDept is created in setUp() but not added to company
        
        // Action: Set "OfficeX" as headquarter for "ghostDept"
        boolean result = company.assignHeadquarters(ghostDept, officeX);
        
        // Expected Output: false (because the department "ghostDept" does not belong to the Company)
        assertFalse("Assignment should fail for department not in company", result);
        
        // Note: No verification needed since department is not in company
    }
}