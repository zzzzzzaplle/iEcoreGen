import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    private Customer customerC301;
    private Customer customerC302;
    private Customer customerC303;
    private Customer customerC304;
    private Customer customerC305;
    private Company companyEcoWave;
    private Company companySmartGrid;
    private Company companyMedLife;
    private Company companyUrbanTech;
    private Company companyAgroSeed;
    private Document documentEW;
    private Document documentSG1;
    private Document documentSG3;
    private Document documentSG5;
    private Document documentSG6;
    
    @Before
    public void setUp() {
        // Setup for Test Case 1
        customerC301 = new Customer("Benjamin", "Taylor", "b.taylor@example.com", "555-1010");
        companyEcoWave = new Company("EcoWave", "ecowave@gmail.com");
        documentEW = new Document();
        
        // Setup for Test Case 2
        customerC302 = new Customer("Charlotte", "Lee", "c.lee@example.com", "555-2020");
        companySmartGrid = new Company("SmartGrid", "smartgrid@business.com");
        documentSG1 = new Document();
        
        // Setup for Test Case 3
        customerC303 = new Customer("Lucas", "Martin", "l.martin@example.com", "555-3030");
        companyMedLife = new Company("MedLife", "medlife@health.com");
        documentSG3 = new Document();
        
        // Setup for Test Case 4
        customerC304 = new Customer("Amelia", "Clark", "a.clark@example.com", "555-4040");
        
        // Setup for Test Case 5
        customerC305 = new Customer("Mia", "Anderson", "m.anderson@example.com", "555-6060");
        companyUrbanTech = new Company("UrbanTech", "urbantech@innovate.com");
        companyAgroSeed = new Company("AgroSeed", "agroseed@innovate.com");
        documentSG5 = new Document();
        documentSG6 = new Document();
    }
    
    @Test
    public void testCase1_CancelStillPendingRequest() {
        // Setup: Create pending application for EcoWave
        Application app = new Application(15, 750.0, customerC301, companyEcoWave, documentEW);
        customerC301.getApplications().add(app);
        
        // Execute: Cancel the pending application
        boolean result = customerC301.cancelApplication("EcoWave");
        
        // Verify: Cancellation should succeed
        assertTrue("Pending application should be cancellable", result);
        assertEquals("Application status should be REJECTED after cancellation", 
                     ApplicationStatus.REJECTED, app.getStatus());
    }
    
    @Test
    public void testCase2_CancelApprovedRequest() {
        // Setup: Create and approve application for SmartGrid
        Application app = new Application(30, 3000.0, customerC302, companySmartGrid, documentSG1);
        app.setStatus(ApplicationStatus.APPROVAL);
        customerC302.getApplications().add(app);
        
        // Execute: Try to cancel approved application
        boolean result = customerC302.cancelApplication("SmartGrid");
        
        // Verify: Cancellation should fail
        assertFalse("Approved application should not be cancellable", result);
        assertEquals("Application status should remain APPROVED", 
                     ApplicationStatus.APPROVAL, app.getStatus());
    }
    
    @Test
    public void testCase3_CancelRejectedRequest() {
        // Setup: Create and reject application for MedLife
        Application app = new Application(20, 1000.0, customerC303, companyMedLife, documentSG3);
        app.setStatus(ApplicationStatus.REJECTED);
        customerC303.getApplications().add(app);
        
        // Execute: Try to cancel rejected application
        boolean result = customerC303.cancelApplication("MedLife");
        
        // Verify: Cancellation should fail
        assertFalse("Rejected application should not be cancellable", result);
        assertEquals("Application status should remain REJECTED", 
                     ApplicationStatus.REJECTED, app.getStatus());
    }
    
    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Setup: Customer has no applications
        // (No setup needed as customerC304 has no applications by default)
        
        // Execute: Try to cancel application for non-existent company
        boolean result = customerC304.cancelApplication("UnknownCorp");
        
        // Verify: Cancellation should fail
        assertFalse("Cancellation should fail for non-existent company application", result);
        assertTrue("Customer should have no applications", customerC304.getApplications().isEmpty());
    }
    
    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Setup: Create two pending applications
        Application appUrbanTech = new Application(25, 1250.0, customerC305, companyUrbanTech, documentSG5);
        Application appAgroSeed = new Application(40, 2000.0, customerC305, companyAgroSeed, documentSG6);
        
        customerC305.getApplications().add(appUrbanTech);
        customerC305.getApplications().add(appAgroSeed);
        
        // Execute: Cancel UrbanTech application
        boolean result = customerC305.cancelApplication("UrbanTech");
        
        // Verify: UrbanTech cancellation should succeed, AgroSeed should remain unaffected
        assertTrue("UrbanTech application should be cancellable", result);
        assertEquals("UrbanTech application status should be REJECTED", 
                     ApplicationStatus.REJECTED, appUrbanTech.getStatus());
        assertEquals("AgroSeed application status should remain PENDING", 
                     ApplicationStatus.PENDING, appAgroSeed.getStatus());
        assertEquals("Customer should still have 2 applications", 
                     2, customerC305.getApplications().size());
    }
}