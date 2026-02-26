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
        // Add Office "OfficeA" to Engineering
        engineering.addOffice(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = company.assignHeadquarter(engineering, officeA);
        
        // Expected Output: true
        assertTrue("Assignment should succeed", result);
        // Verification: OfficeA is the headquarter of Engineering.
        assertEquals("Engineering should have OfficeA as headquarter", officeA, engineering.getHeadquarter());
        assertTrue("OfficeA should be marked as headquarter", officeA.isHeadquarter());
    }
    
    @Test
    public void testCase2_AssignOfficeNotBelongingToDepartment() {
        // SetUp: Create Company with Departments "Engineering" and "Marketing"
        company.addDepartment(engineering);
        company.addDepartment(marketing);
        // Add Office "OfficeA" to Marketing
        marketing.addOffice(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = company.assignHeadquarter(engineering, officeA);
        
        // Expected Output: false
        assertFalse("Assignment should fail", result);
        // Verification: Engineering do not have headquarter
        assertNull("Engineering should not have a headquarter", engineering.getHeadquarter());
    }
    
    @Test
    public void testCase3_ReassignExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering"
        company.addDepartment(engineering);
        // Add Offices "OfficeA" and "OfficeB" to Engineering
        engineering.addOffice(officeA);
        engineering.addOffice(officeB);
        // Set "OfficeA" as headquarter
        company.assignHeadquarter(engineering, officeA);
        
        // Action: Set "OfficeB" as new headquarter
        boolean result = company.assignHeadquarter(engineering, officeB);
        
        // Expected Output: false
        assertFalse("Reassignment should fail", result);
        // Verification: Engineering's headquarter remains OfficeA
        assertEquals("Engineering's headquarter should remain OfficeA", officeA, engineering.getHeadquarter());
        assertTrue("OfficeA should still be headquarter", officeA.isHeadquarter());
        assertFalse("OfficeB should not be headquarter", officeB.isHeadquarter());
    }
    
    @Test
    public void testCase4_AssignToDepartmentWithSingleOffice() {
        // SetUp: Create Company with Department "HR"
        company.addDepartment(hr);
        // Add only Office "HR-Main" to HR
        hr.addOffice(hrMain);
        
        // Action: Set "HR-Main" as headquarter
        boolean result = company.assignHeadquarter(hr, hrMain);
        
        // Expected Output: true
        assertTrue("Assignment should succeed", result);
        // Verification: HR's headquarter is HR-Main
        assertEquals("HR should have HR-Main as headquarter", hrMain, hr.getHeadquarter());
        assertTrue("HR-Main should be marked as headquarter", hrMain.isHeadquarter());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create Company with no departments
        // Create Department "ghostDept" (but not added to company)
        
        // Action: Set "OfficeX" as headquarter for "ghostDept"
        boolean result = company.assignHeadquarter(ghostDept, officeX);
        
        // Expected Output: false (because the department "ghostDept" does not belong to the Company)
        assertFalse("Assignment should fail for non-existent department", result);
    }
}