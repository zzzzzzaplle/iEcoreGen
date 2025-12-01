import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    private Customer customerC301;
    private Customer customerC302;
    private Customer customerC303;
    private Customer customerC304;
    private Customer customerC305;
    private Company ecoWave;
    private Company smartGrid;
    private Company medLife;
    private Company urbanTech;
    private Company agroSeed;
    
    @Before
    public void setUp() {
        // Setup companies
        ecoWave = new Company("EcoWave", "ecowave@gmail.com");
        smartGrid = new Company("SmartGrid", "smartgrid@business.com");
        medLife = new Company("MedLife", "medlife@health.com");
        urbanTech = new Company("UrbanTech", "urbantech@innovate.com");
        agroSeed = new Company("AgroSeed", "agroseed@business.com");
        
        // Setup customer C301 with pending application for EcoWave
        customerC301 = new Customer();
        customerC301.setName("Benjamin");
        customerC301.setSurname("Taylor");
        customerC301.setEmail("b.taylor@example.com");
        customerC301.setTelephone("555-1010");
        Document doc301 = new Document();
        Application app301 = new Application(15, 750.0, customerC301, ecoWave, doc301);
        app301.setStatus(ApplicationStatus.PENDING);
        customerC301.getApplications().add(app301);
        
        // Setup customer C302 with approved application for SmartGrid
        customerC302 = new Customer();
        customerC302.setName("Charlotte");
        customerC302.setSurname("Lee");
        customerC302.setEmail("c.lee@example.com");
        customerC302.setTelephone("555-2020");
        Document doc302 = new Document();
        Application app302 = new Application(30, 3000.0, customerC302, smartGrid, doc302);
        app302.setStatus(ApplicationStatus.APPROVAL);
        customerC302.getApplications().add(app302);
        
        // Setup customer C303 with rejected application for MedLife
        customerC303 = new Customer();
        customerC303.setName("Lucas");
        customerC303.setSurname("Martin");
        customerC303.setEmail("l.martin@example.com");
        customerC303.setTelephone("555-3030");
        Document doc303 = new Document();
        Application app303 = new Application(20, 1000.0, customerC303, medLife, doc303);
        app303.setStatus(ApplicationStatus.REJECTED);
        customerC303.getApplications().add(app303);
        
        // Setup customer C304 with no applications
        customerC304 = new Customer();
        customerC304.setName("Amelia");
        customerC304.setSurname("Clark");
        customerC304.setEmail("a.clark@example.com");
        customerC304.setTelephone("555-4040");
        
        // Setup customer C305 with two pending applications
        customerC305 = new Customer();
        customerC305.setName("Mia");
        customerC305.setSurname("Anderson");
        customerC305.setEmail("m.anderson@example.com");
        customerC305.setTelephone("555-6060");
        Document doc305 = new Document();
        Application app305 = new Application(25, 1250.0, customerC305, urbanTech, doc305);
        app305.setStatus(ApplicationStatus.PENDING);
        Document doc306 = new Document();
        Application app306 = new Application(40, 2000.0, customerC305, agroSeed, doc306);
        app306.setStatus(ApplicationStatus.PENDING);
        customerC305.getApplications().add(app305);
        customerC305.getApplications().add(app306);
    }
    
    @Test
    public void testCase1_CancelStillPendingRequest() {
        // Test canceling a pending application
        boolean result = customerC301.cancelApplication("EcoWave");
        
        // Verify cancellation succeeded
        assertTrue("Pending application should be cancellable", result);
        
        // Verify application status is now REJECTED (cancelled)
        Application cancelledApp = customerC301.getApplications().get(0);
        assertEquals("Cancelled application should have REJECTED status", 
                     ApplicationStatus.REJECTED, cancelledApp.getStatus());
    }
    
    @Test
    public void testCase2_CancelApprovedRequest() {
        // Test canceling an approved application
        boolean result = customerC302.cancelApplication("SmartGrid");
        
        // Verify cancellation failed
        assertFalse("Approved application should not be cancellable", result);
        
        // Verify application status remains APPROVAL
        Application approvedApp = customerC302.getApplications().get(0);
        assertEquals("Approved application status should remain unchanged", 
                     ApplicationStatus.APPROVAL, approvedApp.getStatus());
    }
    
    @Test
    public void testCase3_CancelRejectedRequest() {
        // Test canceling a rejected application
        boolean result = customerC303.cancelApplication("MedLife");
        
        // Verify cancellation failed
        assertFalse("Rejected application should not be cancellable", result);
        
        // Verify application status remains REJECTED
        Application rejectedApp = customerC303.getApplications().get(0);
        assertEquals("Rejected application status should remain unchanged", 
                     ApplicationStatus.REJECTED, rejectedApp.getStatus());
    }
    
    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Test canceling application for a company that doesn't exist in customer's applications
        boolean result = customerC304.cancelApplication("UnknownCorp");
        
        // Verify cancellation failed
        assertFalse("Non-existent application should not be cancellable", result);
        
        // Verify customer has no applications
        assertEquals("Customer should have no applications", 
                     0, customerC304.getApplications().size());
    }
    
    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Test canceling one application while another remains unaffected
        boolean result = customerC305.cancelApplication("UrbanTech");
        
        // Verify cancellation succeeded
        assertTrue("Pending UrbanTech application should be cancellable", result);
        
        // Verify UrbanTech application status is REJECTED (cancelled)
        Application urbanTechApp = null;
        Application agroSeedApp = null;
        for (Application app : customerC305.getApplications()) {
            if (app.getCompany().getName().equals("UrbanTech")) {
                urbanTechApp = app;
            } else if (app.getCompany().getName().equals("AgroSeed")) {
                agroSeedApp = app;
            }
        }
        
        assertNotNull("UrbanTech application should exist", urbanTechApp);
        assertEquals("Cancelled UrbanTech application should have REJECTED status", 
                     ApplicationStatus.REJECTED, urbanTechApp.getStatus());
        
        // Verify AgroSeed application remains PENDING and unaffected
        assertNotNull("AgroSeed application should exist", agroSeedApp);
        assertEquals("AgroSeed application should remain PENDING", 
                     ApplicationStatus.PENDING, agroSeedApp.getStatus());
        
        // Verify customer still has 2 applications
        assertEquals("Customer should still have 2 applications", 
                     2, customerC305.getApplications().size());
    }
}