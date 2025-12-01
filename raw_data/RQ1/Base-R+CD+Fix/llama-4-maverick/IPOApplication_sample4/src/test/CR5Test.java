import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    private Customer customerC301;
    private Customer customerC302;
    private Customer customerC303;
    private Customer customerC304;
    private Customer customerC306;
    private Company ecoWave;
    private Company smartGrid;
    private Company medLife;
    private Company urbanTech;
    private Company agroSeed;
    private Document docEW202403;
    private Document docSG202401;
    private Document docSG202403;
    private Document docSG2024005;
    private Document docSG2024006;
    
    @Before
    public void setUp() {
        // Initialize companies
        ecoWave = new Company("EcoWave", "ecowave@gmail.com");
        smartGrid = new Company("SmartGrid", "smartgrid@business.com");
        medLife = new Company("MedLife", "medlife@health.com");
        urbanTech = new Company("UrbanTech", "urbantech@innovate.com");
        agroSeed = new Company("AgroSeed", "agroseed@example.com");
        
        // Initialize documents
        docEW202403 = new Document();
        docSG202401 = new Document();
        docSG202403 = new Document();
        docSG2024005 = new Document();
        docSG2024006 = new Document();
        
        // Initialize customer C301 with pending application for EcoWave
        customerC301 = new Customer();
        customerC301.setName("Benjamin");
        customerC301.setSurname("Taylor");
        customerC301.setEmail("b.taylor@example.com");
        customerC301.setTelephone("555-1010");
        customerC301.setCanApplyForIPO(true);
        
        Application app4001 = new Application(15, 750.0, customerC301, ecoWave, docEW202403);
        app4001.setStatus(ApplicationStatus.PENDING);
        customerC301.getApplications().add(app4001);
        
        // Initialize customer C302 with approved application for SmartGrid
        customerC302 = new Customer();
        customerC302.setName("Charlotte");
        customerC302.setSurname("Lee");
        customerC302.setEmail("c.lee@example.com");
        customerC302.setTelephone("555-2020");
        customerC302.setCanApplyForIPO(true);
        
        Application app4002 = new Application(30, 3000.0, customerC302, smartGrid, docSG202401);
        app4002.setStatus(ApplicationStatus.APPROVAL);
        customerC302.getApplications().add(app4002);
        
        // Initialize customer C303 with rejected application for MedLife
        customerC303 = new Customer();
        customerC303.setName("Lucas");
        customerC303.setSurname("Martin");
        customerC303.setEmail("l.martin@example.com");
        customerC303.setTelephone("555-3030");
        customerC303.setCanApplyForIPO(true);
        
        Application app4003 = new Application(20, 1000.0, customerC303, medLife, docSG202403);
        app4003.setStatus(ApplicationStatus.REJECTED);
        customerC303.getApplications().add(app4003);
        
        // Initialize customer C304 with no applications
        customerC304 = new Customer();
        customerC304.setName("Amelia");
        customerC304.setSurname("Clark");
        customerC304.setEmail("a.clark@example.com");
        customerC304.setTelephone("555-4040");
        customerC304.setCanApplyForIPO(true);
        
        // Initialize customer C306 with two pending applications
        customerC306 = new Customer();
        customerC306.setName("Mia");
        customerC306.setSurname("Anderson");
        customerC306.setEmail("m.anderson@example.com");
        customerC306.setTelephone("555-6060");
        customerC306.setCanApplyForIPO(true);
        
        Application app4005 = new Application(25, 1250.0, customerC306, urbanTech, docSG2024005);
        app4005.setStatus(ApplicationStatus.PENDING);
        customerC306.getApplications().add(app4005);
        
        Application app4006 = new Application(40, 2000.0, customerC306, agroSeed, docSG2024006);
        app4006.setStatus(ApplicationStatus.PENDING);
        customerC306.getApplications().add(app4006);
    }
    
    @Test
    public void testCase1_CancelStillPendingRequest() {
        // Test Case 1: Cancel still-pending request
        // Customer "C301" requests cancellation for "EcoWave"
        
        // Execute cancellation
        boolean result = customerC301.cancelApplication("EcoWave");
        
        // Verify result is true
        assertTrue("Cancellation of pending application should return true", result);
        
        // Verify application status changed to REJECTED (cancellation treated as rejection)
        Application cancelledApp = customerC301.getApplications().get(0);
        assertEquals("Application status should be REJECTED after cancellation", 
                     ApplicationStatus.REJECTED, cancelledApp.getStatus());
    }
    
    @Test
    public void testCase2_CancelApprovedRequest() {
        // Test Case 2: Cancel approved request
        // Customer "C302" tries to cancel IPO for "SmartGrid"
        
        // Execute cancellation
        boolean result = customerC302.cancelApplication("SmartGrid");
        
        // Verify result is false (cannot cancel approved applications)
        assertFalse("Cancellation of approved application should return false", result);
        
        // Verify application status remains APPROVAL
        Application approvedApp = customerC302.getApplications().get(0);
        assertEquals("Application status should remain APPROVAL", 
                     ApplicationStatus.APPROVAL, approvedApp.getStatus());
    }
    
    @Test
    public void testCase3_CancelRejectedRequest() {
        // Test Case 3: Cancel rejected request
        // Customer "C303" tries to cancel the filing for "MedLife"
        
        // Execute cancellation
        boolean result = customerC303.cancelApplication("MedLife");
        
        // Verify result is false (cannot cancel rejected applications)
        assertFalse("Cancellation of rejected application should return false", result);
        
        // Verify application status remains REJECTED
        Application rejectedApp = customerC303.getApplications().get(0);
        assertEquals("Application status should remain REJECTED", 
                     ApplicationStatus.REJECTED, rejectedApp.getStatus());
    }
    
    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Test Case 4: Cancel nonexistent company
        // Customer "C304" requests cancellation for "UnknownCorp"
        
        // Execute cancellation for non-existent company
        boolean result = customerC304.cancelApplication("UnknownCorp");
        
        // Verify result is false (no application found for specified company)
        assertFalse("Cancellation for non-existent company should return false", result);
        
        // Verify customer has no applications
        assertEquals("Customer should have no applications", 
                     0, customerC304.getApplications().size());
    }
    
    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Test Case 5: Cancel after prior cancellation
        // Customer "C306" cancels "UrbanTech" filing
        
        // Execute cancellation for UrbanTech
        boolean result = customerC306.cancelApplication("UrbanTech");
        
        // Verify result is true
        assertTrue("Cancellation of pending UrbanTech application should return true", result);
        
        // Verify UrbanTech application status changed to REJECTED
        Application urbanTechApp = null;
        Application agroSeedApp = null;
        for (Application app : customerC306.getApplications()) {
            if (app.getCompany().getName().equals("UrbanTech")) {
                urbanTechApp = app;
            } else if (app.getCompany().getName().equals("AgroSeed")) {
                agroSeedApp = app;
            }
        }
        
        assertNotNull("UrbanTech application should exist", urbanTechApp);
        assertEquals("UrbanTech application status should be REJECTED", 
                     ApplicationStatus.REJECTED, urbanTechApp.getStatus());
        
        // Verify AgroSeed application remains PENDING (unaffected)
        assertNotNull("AgroSeed application should exist", agroSeedApp);
        assertEquals("AgroSeed application status should remain PENDING", 
                     ApplicationStatus.PENDING, agroSeedApp.getStatus());
        
        // Verify customer still has 2 applications
        assertEquals("Customer should still have 2 applications", 
                     2, customerC306.getApplications().size());
    }
}