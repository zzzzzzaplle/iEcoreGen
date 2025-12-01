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
    private Office officeHRMain;
    private Office officeX;
    
    @Before
    public void setUp() {
        company = new Company();
        engineering = new Department();
        marketing = new Department();
        hr = new Department();
        ghostDept = new Department();
        
        officeA = new Office();
        officeB = new Office();
        officeHRMain = new Office();
        officeX = new Office();
        
        // Set up common test data
        engineering.setDepartmentId("ENG");
        engineering.setName("Engineering");
        
        marketing.setDepartmentId("MKT");
        marketing.setName("Marketing");
        
        hr.setDepartmentId("HR");
        hr.setName("HR");
        
        ghostDept.setDepartmentId("GHOST");
        ghostDept.setName("Ghost Department");
        
        officeA.setOfficeId("A");
        officeA.setLocation("Location A");
        
        officeB.setOfficeId("B");
        officeB.setLocation("Location B");
        
        officeHRMain.setOfficeId("HR-MAIN");
        officeHRMain.setLocation("HR Location");
        
        officeX.setOfficeId("X");
        officeX.setLocation("Location X");
    }
    
    @Test
    public void testCase1_AssignValidHeadquarterToDepartmentWithoutExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering" and add Office "OfficeA" to Engineering
        company.getDepartments().add(engineering);
        engineering.getOffices().add(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = company.assignHeadquarters(engineering, officeA);
        
        // Expected Output: true
        assertTrue("Should successfully assign headquarter", result);
        
        // Verification: OfficeA is the headquarter of Engineering
        assertTrue("OfficeA should be headquarters", officeA.isHeadquarters());
        assertEquals("Engineering's headquarters should be OfficeA", officeA, engineering.getHeadquarters());
    }
    
    @Test
    public void testCase2_AssignOfficeNotBelongingToDepartment() {
        // SetUp: Create Company with Departments "Engineering" and "Marketing", add Office "OfficeA" to Marketing
        company.getDepartments().add(engineering);
        company.getDepartments().add(marketing);
        marketing.getOffices().add(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = company.assignHeadquarters(engineering, officeA);
        
        // Expected Output: false
        assertFalse("Should fail to assign headquarter from different department", result);
        
        // Verification: Engineering do not have headquarter
        assertFalse("Engineering should not have headquarters", engineering.hasHeadquarters());
        assertNull("Engineering should not have headquarters", engineering.getHeadquarters());
    }
    
    @Test
    public void testCase3_ReassignExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering", add Offices "OfficeA" and "OfficeB" to Engineering, set "OfficeA" as headquarter
        company.getDepartments().add(engineering);
        engineering.getOffices().add(officeA);
        engineering.getOffices().add(officeB);
        company.assignHeadquarters(engineering, officeA);
        
        // Action: Set "OfficeB" as new headquarter
        boolean result = company.assignHeadquarters(engineering, officeB);
        
        // Expected Output: false
        assertFalse("Should fail to reassign headquarter", result);
        
        // Verification: Engineering's headquarter remains OfficeA
        assertTrue("OfficeA should remain headquarters", officeA.isHeadquarters());
        assertFalse("OfficeB should not be headquarters", officeB.isHeadquarters());
        assertEquals("Engineering's headquarters should remain OfficeA", officeA, engineering.getHeadquarters());
    }
    
    @Test
    public void testCase4_AssignToDepartmentWithSingleOffice() {
        // SetUp: Create Company with Department "HR", add only Office "HR-Main" to HR
        company.getDepartments().add(hr);
        hr.getOffices().add(officeHRMain);
        
        // Action: Set "HR-Main" as headquarter
        boolean result = company.assignHeadquarters(hr, officeHRMain);
        
        // Expected Output: true
        assertTrue("Should successfully assign headquarter to department with single office", result);
        
        // Verification: HR's headquarter is HR-Main
        assertTrue("HR-Main should be headquarters", officeHRMain.isHeadquarters());
        assertEquals("HR's headquarters should be HR-Main", officeHRMain, hr.getHeadquarters());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create Company with no departments, create Department "ghostDept"
        // Note: ghostDept is not added to company
        
        // Action: Set "OfficeX" as headquarter for "ghostDept"
        boolean result = company.assignHeadquarters(ghostDept, officeX);
        
        // Expected Output: false (because the department "ghostDept" does not belong to the Company)
        assertFalse("Should fail to assign headquarter to non-existent department", result);
    }
}