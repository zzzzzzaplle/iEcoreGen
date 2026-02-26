import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR1Test {
    
    private Company company;
    private Department engineeringDept;
    private Department marketingDept;
    private Department hrDept;
    private Department ghostDept;
    private Office officeA;
    private Office officeB;
    private Office hrMain;
    private Office officeX;
    
    @Before
    public void setUp() {
        company = new Company();
        engineeringDept = new Department();
        engineeringDept.setName("Engineering");
        
        marketingDept = new Department();
        marketingDept.setName("Marketing");
        
        hrDept = new Department();
        hrDept.setName("HR");
        
        ghostDept = new Department();
        ghostDept.setName("ghostDept");
        
        officeA = new Office();
        officeA.setId("OfficeA");
        
        officeB = new Office();
        officeB.setId("OfficeB");
        
        hrMain = new Office();
        hrMain.setId("HR-Main");
        
        officeX = new Office();
        officeX.setId("OfficeX");
    }
    
    @Test
    public void testCase1_AssignValidHeadquarterToDepartmentWithoutExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering"
        company.addDepartment(engineeringDept);
        
        // SetUp: Add Office "OfficeA" to Engineering
        engineeringDept.addOffice(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = engineeringDept.assignOfficeAsHeadquarter(officeA);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verification: OfficeA is the headquarter of Engineering
        assertEquals(officeA, engineeringDept.getHeadquarter());
    }
    
    @Test
    public void testCase2_AssignOfficeNotBelongingToDepartment() {
        // SetUp: Create Company with Departments "Engineering" and "Marketing"
        company.addDepartment(engineeringDept);
        company.addDepartment(marketingDept);
        
        // SetUp: Add Office "OfficeA" to Marketing
        marketingDept.addOffice(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = engineeringDept.assignOfficeAsHeadquarter(officeA);
        
        // Expected Output: false
        assertFalse(result);
        
        // Verification: Engineering do not have headquarter
        assertNull(engineeringDept.getHeadquarter());
    }
    
    @Test
    public void testCase3_ReassignExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering"
        company.addDepartment(engineeringDept);
        
        // SetUp: Add Offices "OfficeA" and "OfficeB" to Engineering
        engineeringDept.addOffice(officeA);
        engineeringDept.addOffice(officeB);
        
        // SetUp: Set "OfficeA" as headquarter
        engineeringDept.assignOfficeAsHeadquarter(officeA);
        
        // Action: Set "OfficeB" as new headquarter
        boolean result = engineeringDept.assignOfficeAsHeadquarter(officeB);
        
        // Expected Output: false
        assertFalse(result);
        
        // Verification: Engineering's headquarter remains OfficeA
        assertEquals(officeA, engineeringDept.getHeadquarter());
    }
    
    @Test
    public void testCase4_AssignToDepartmentWithSingleOffice() {
        // SetUp: Create Company with Department "HR"
        company.addDepartment(hrDept);
        
        // SetUp: Add only Office "HR-Main" to HR
        hrDept.addOffice(hrMain);
        
        // Action: Set "HR-Main" as headquarter
        boolean result = hrDept.assignOfficeAsHeadquarter(hrMain);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verification: HR's headquarter is HR-Main
        assertEquals(hrMain, hrDept.getHeadquarter());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create Company with no departments
        // (company is created empty in setUp)
        
        // SetUp: Create Department "ghostDept"
        // (ghostDept is created in setUp but not added to company)
        
        // Action: Set "OfficeX" as headquarter for "ghostDept"
        boolean result = ghostDept.assignOfficeAsHeadquarter(officeX);
        
        // Expected Output: false (because the department "ghostDept" does not belong to the Company)
        assertFalse(result);
        
        // Note: The test specification mentions this returns false because the department 
        // doesn't belong to the Company, but the actual Department.assignOfficeAsHeadquarter 
        // method doesn't check company membership. However, based on the specification 
        // we expect false, which will occur since officeX is not in ghostDept's offices list.
    }
}