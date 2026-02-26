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
        marketing = new Department();
        hr = new Department();
        ghostDept = new Department();
        officeA = new Office();
        officeB = new Office();
        hrMain = new Office();
        officeX = new Office();
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
        assertTrue(result);
        
        // Verification: OfficeA is the headquarter of Engineering
        assertEquals(officeA, engineering.getHeadquarter());
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
        assertFalse(result);
        
        // Verification: Engineering do not have headquarter
        assertNull(engineering.getHeadquarter());
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
        assertFalse(result);
        
        // Verification: Engineering's headquarter remains OfficeA
        assertEquals(officeA, engineering.getHeadquarter());
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
        assertTrue(result);
        
        // Verification: HR's headquarter is HR-Main
        assertEquals(hrMain, hr.getHeadquarter());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create Company with no departments
        // (No departments added to company)
        
        // SetUp: Create Department "ghostDept"
        // (ghostDept created but not added to company)
        
        // Action: Set "OfficeX" as headquarter for "ghostDept"
        boolean result = ghostDept.assignOfficeAsHeadquarter(officeX);
        
        // Expected Output: false (because the office doesn't exist in the department)
        assertFalse(result);
        
        // Verification: ghostDept has no headquarter
        assertNull(ghostDept.getHeadquarter());
    }
}