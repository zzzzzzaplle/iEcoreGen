import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

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
        officeA = new Office();
        officeB = new Office();
        hrMain = new Office();
        officeX = new Office();
        
        engineering.setName("Engineering");
        marketing.setName("Marketing");
        hr.setName("HR");
        
        officeA.setLocation("OfficeA");
        officeB.setLocation("OfficeB");
        hrMain.setLocation("HR-Main");
        officeX.setLocation("OfficeX");
    }
    
    @Test
    public void testCase1_AssignValidHeadquarterToDepartmentWithoutExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering"
        company.getDepartments().add(engineering);
        
        // SetUp: Add Office "OfficeA" to Engineering
        engineering.addOffice(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = engineering.assignHeadquarters(officeA);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verification: OfficeA is the headquarter of Engineering
        assertEquals(officeA, engineering.getHeadquarters());
    }
    
    @Test
    public void testCase2_AssignOfficeNotBelongingToDepartment() {
        // SetUp: Create Company with Departments "Engineering" and "Marketing"
        company.getDepartments().add(engineering);
        company.getDepartments().add(marketing);
        
        // SetUp: Add Office "OfficeA" to Marketing
        marketing.addOffice(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = engineering.assignHeadquarters(officeA);
        
        // Expected Output: false
        assertFalse(result);
        
        // Verification: Engineering do not have headquarter
        assertNull(engineering.getHeadquarters());
    }
    
    @Test
    public void testCase3_ReassignExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering"
        company.getDepartments().add(engineering);
        
        // SetUp: Add Offices "OfficeA" and "OfficeB" to Engineering
        engineering.addOffice(officeA);
        engineering.addOffice(officeB);
        
        // SetUp: Set "OfficeA" as headquarter
        engineering.assignHeadquarters(officeA);
        
        // Action: Set "OfficeB" as new headquarter
        boolean result = engineering.assignHeadquarters(officeB);
        
        // Expected Output: false
        assertFalse(result);
        
        // Verification: Engineering's headquarter remains OfficeA
        assertEquals(officeA, engineering.getHeadquarters());
    }
    
    @Test
    public void testCase4_AssignToDepartmentWithSingleOffice() {
        // SetUp: Create Company with Department "HR"
        company.getDepartments().add(hr);
        
        // SetUp: Add only Office "HR-Main" to HR
        hr.addOffice(hrMain);
        
        // Action: Set "HR-Main" as headquarter
        boolean result = hr.assignHeadquarters(hrMain);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verification: HR's headquarter is HR-Main
        assertEquals(hrMain, hr.getHeadquarters());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create Company with no departments
        // (company already has empty departments list from setUp)
        
        // SetUp: Create Department "ghostDept"
        Department ghostDept = new Department();
        ghostDept.setName("ghostDept");
        
        // Action: Set "OfficeX" as headquarter for "ghostDept"
        boolean result = ghostDept.assignHeadquarters(officeX);
        
        // Expected Output: false (because the department "ghostDept" does not belong to the Company)
        assertFalse(result);
    }
}