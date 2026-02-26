import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    private Customer customerC301;
    private Customer customerC302;
    private Customer customerC303;
    private Customer customerC304;
    private Customer customerC306;
    
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
        // Set up customers
        customerC301 = new Customer("Benjamin", "Taylor", "b.taylor@example.com", "555-1010");
        customerC302 = new Customer("Charlotte", "Lee", "c.lee@example.com", "555-2020");
        customerC303 = new Customer("Lucas", "Martin", "l.martin@example.com", "555-3030");
        customerC304 = new Customer("Amelia", "Clark", "a.clark@example.com", "555-4040");
        customerC306 = new Customer("Mia", "Anderson", "m.anderson@example.com", "555-6060");
        
        // Set up companies
        companyEcoWave = new Company("EcoWave", "ecowave@gmail.com");
        companySmartGrid = new Company("SmartGrid", "smartgrid@business.com");
        companyMedLife = new Company("MedLife", "medlife@health.com");
        companyUrbanTech = new Company("UrbanTech", "urbantech@innovate.com");
        companyAgroSeed = new Company("AgroSeed", "agroseed@innovate.com");
        
        // Set up documents
        documentEW = new Document();
        documentSG1 = new Document();
        documentSG3 = new Document();
        documentSG5 = new Document();
        documentSG6 = new Document();
    }
    
    @Test
    public void testCase1_cancelStillPendingRequest() {
        // Setup: Customer "C301" has a pending application for "EcoWave"
        customerC301.createApplication(companyEcoWave, 15, 750.0, documentEW);
        
        // Execute: Cancel application for "EcoWave"
        boolean result = customerC301.cancelApplication("EcoWave");
        
        // Verify: Cancellation should succeed
        assertTrue("Pending application should be cancellable", result);
        
        // Verify application status is changed to REJECTED (as per cancel() implementation)
        Application app = customerC301.getApplications().get(0);
        assertEquals("Application status should be REJECTED after cancellation", 
                     ApplicationStatus.REJECTED, app.getStatus());
    }
    
    @Test
    public void testCase2_cancelApprovedRequest() {
        // Setup: Customer "C302" has an approved application for "SmartGrid"
        customerC302.createApplication(companySmartGrid, 30, 3000.0, documentSG1);
        Application app = customerC302.getApplications().get(0);
        
        // Manually set status to APPROVAL since approve() requires eligibility check
        app.setStatus(ApplicationStatus.APPROVAL);
        
        // Execute: Try to cancel approved application
        boolean result = customerC302.cancelApplication("SmartGrid");
        
        // Verify: Cancellation should fail for approved application
        assertFalse("Approved application should not be cancellable", result);
        
        // Verify application status remains APPROVAL
        assertEquals("Application status should remain APPROVAL", 
                     ApplicationStatus.APPROVAL, app.getStatus());
    }
    
    @Test
    public void testCase3_cancelRejectedRequest() {
        // Setup: Customer "C303" has a rejected application for "MedLife"
        customerC303.createApplication(companyMedLife, 20, 1000.0, documentSG3);
        Application app = customerC303.getApplications().get(0);
        
        // Manually set status to REJECTED
        app.setStatus(ApplicationStatus.REJECTED);
        
        // Execute: Try to cancel rejected application
        boolean result = customerC303.cancelApplication("MedLife");
        
        // Verify: Cancellation should fail for rejected application
        assertFalse("Rejected application should not be cancellable", result);
        
        // Verify application status remains REJECTED
        assertEquals("Application status should remain REJECTED", 
                     ApplicationStatus.REJECTED, app.getStatus());
    }
    
    @Test
    public void testCase4_cancelNonexistentCompany() {
        // Setup: Customer "C304" has no applications
        // No setup needed - customer has no applications by default
        
        // Execute: Try to cancel application for non-existent company
        boolean result = customerC304.cancelApplication("UnknownCorp");
        
        // Verify: Cancellation should fail when no application exists
        assertFalse("Cancellation should fail for non-existent company", result);
    }
    
    @Test
    public void testCase5_cancelAfterPriorCancellation() {
        // Setup: Customer "C306" has two pending applications
        customerC306.createApplication(companyUrbanTech, 25, 1250.0, documentSG5);
        customerC306.createApplication(companyAgroSeed, 40, 2000.0, documentSG6);
        
        // Verify initial state - both applications should be PENDING
        assertEquals("Should have 2 applications", 2, customerC306.getApplications().size());
        assertEquals("UrbanTech application should be PENDING", 
                     ApplicationStatus.PENDING, customerC306.getApplications().get(0).getStatus());
        assertEquals("AgroSeed application should be PENDING", 
                     ApplicationStatus.PENDING, customerC306.getApplications().get(1).getStatus());
        
        // Execute: Cancel "UrbanTech" application
        boolean result = customerC306.cancelApplication("UrbanTech");
        
        // Verify: Cancellation should succeed for UrbanTech
        assertTrue("UrbanTech application should be cancellable", result);
        
        // Verify UrbanTech application status is changed to REJECTED
        Application urbanTechApp = customerC306.getApplications().get(0);
        assertEquals("UrbanTech application should be REJECTED after cancellation", 
                     ApplicationStatus.REJECTED, urbanTechApp.getStatus());
        
        // Verify AgroSeed application remains PENDING and unaffected
        Application agroSeedApp = customerC306.getApplications().get(1);
        assertEquals("AgroSeed application should remain PENDING", 
                     ApplicationStatus.PENDING, agroSeedApp.getStatus());
        
        // Verify both applications still exist
        assertEquals("Should still have 2 applications after cancellation", 
                     2, customerC306.getApplications().size());
    }
}