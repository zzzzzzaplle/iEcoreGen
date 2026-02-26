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
        // Create companies
        ecoWave = new Company("EcoWave", "ecowave@gmail.com");
        smartGrid = new Company("SmartGrid", "smartgrid@business.com");
        medLife = new Company("MedLife", "medlife@health.com");
        urbanTech = new Company("UrbanTech", "urbantech@innovate.com");
        agroSeed = new Company("AgroSeed", "agroseed@business.com");
        
        // Create documents
        docEW202403 = new Document();
        docSG202401 = new Document();
        docSG202403 = new Document();
        docSG2024005 = new Document();
        docSG2024006 = new Document();
        
        // Create customers and set up their applications
        customerC301 = new Customer();
        customerC301.setName("Benjamin");
        customerC301.setSurname("Taylor");
        customerC301.setEmail("b.taylor@example.com");
        customerC301.setTelephone("555-1010");
        customerC301.setCanApplyForIPO(true);
        
        // Create pending application for EcoWave
        Application app4001 = new Application(15, 750.0, customerC301, ecoWave, docEW202403);
        app4001.setStatus(ApplicationStatus.PENDING);
        customerC301.getApplications().add(app4001);
        
        customerC302 = new Customer();
        customerC302.setName("Charlotte");
        customerC302.setSurname("Lee");
        customerC302.setEmail("c.lee@example.com");
        customerC302.setTelephone("555-2020");
        customerC302.setCanApplyForIPO(true);
        
        // Create approved application for SmartGrid
        Application app4002 = new Application(30, 3000.0, customerC302, smartGrid, docSG202401);
        app4002.setStatus(ApplicationStatus.APPROVAL);
        customerC302.getApplications().add(app4002);
        
        customerC303 = new Customer();
        customerC303.setName("Lucas");
        customerC303.setSurname("Martin");
        customerC303.setEmail("l.martin@example.com");
        customerC303.setTelephone("555-3030");
        customerC303.setCanApplyForIPO(true);
        
        // Create rejected application for MedLife
        Application app4003 = new Application(20, 1000.0, customerC303, medLife, docSG202403);
        app4003.setStatus(ApplicationStatus.REJECTED);
        customerC303.getApplications().add(app4003);
        
        customerC304 = new Customer();
        customerC304.setName("Amelia");
        customerC304.setSurname("Clark");
        customerC304.setEmail("a.clark@example.com");
        customerC304.setTelephone("555-4040");
        customerC304.setCanApplyForIPO(true);
        // No applications for this customer
        
        customerC306 = new Customer();
        customerC306.setName("Mia");
        customerC306.setSurname("Anderson");
        customerC306.setEmail("m.anderson@example.com");
        customerC306.setTelephone("555-6060");
        customerC306.setCanApplyForIPO(true);
        
        // Create two pending applications for customer C306
        Application app4005 = new Application(25, 1250.0, customerC306, urbanTech, docSG2024005);
        app4005.setStatus(ApplicationStatus.PENDING);
        
        Application app4006 = new Application(40, 2000.0, customerC306, agroSeed, docSG2024006);
        app4006.setStatus(ApplicationStatus.PENDING);
        
        customerC306.getApplications().add(app4005);
        customerC306.getApplications().add(app4006);
    }
    
    @Test
    public void testCase1_CancelPendingRequest() {
        // Test cancellation of pending application for EcoWave
        boolean result = customerC301.cancelApplication("EcoWave");
        
        assertTrue("Pending application should be cancellable", result);
        
        // Verify the application status was updated to REJECTED (cancelled)
        Application cancelledApp = customerC301.getApplications().get(0);
        assertEquals("Application status should be REJECTED after cancellation", 
                     ApplicationStatus.REJECTED, cancelledApp.getStatus());
    }
    
    @Test
    public void testCase2_CancelApprovedRequest() {
        // Test cancellation of approved application for SmartGrid
        boolean result = customerC302.cancelApplication("SmartGrid");
        
        assertFalse("Approved application should not be cancellable", result);
        
        // Verify the application status remains APPROVAL
        Application approvedApp = customerC302.getApplications().get(0);
        assertEquals("Application status should remain APPROVAL", 
                     ApplicationStatus.APPROVAL, approvedApp.getStatus());
    }
    
    @Test
    public void testCase3_CancelRejectedRequest() {
        // Test cancellation of rejected application for MedLife
        boolean result = customerC303.cancelApplication("MedLife");
        
        assertFalse("Rejected application should not be cancellable", result);
        
        // Verify the application status remains REJECTED
        Application rejectedApp = customerC303.getApplications().get(0);
        assertEquals("Application status should remain REJECTED", 
                     ApplicationStatus.REJECTED, rejectedApp.getStatus());
    }
    
    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Test cancellation for a company that doesn't exist in customer's applications
        boolean result = customerC304.cancelApplication("UnknownCorp");
        
        assertFalse("Non-existent application should return false", result);
        
        // Verify no applications exist for this customer
        assertEquals("Customer should have no applications", 0, customerC304.getApplications().size());
    }
    
    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Test cancellation of UrbanTech application and verify AgroSeed remains unaffected
        boolean result = customerC306.cancelApplication("UrbanTech");
        
        assertTrue("Pending UrbanTech application should be cancellable", result);
        
        // Verify UrbanTech application was cancelled (status set to REJECTED)
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
        assertNotNull("AgroSeed application should exist", agroSeedApp);
        
        assertEquals("UrbanTech application should be REJECTED after cancellation", 
                     ApplicationStatus.REJECTED, urbanTechApp.getStatus());
        assertEquals("AgroSeed application should remain PENDING", 
                     ApplicationStatus.PENDING, agroSeedApp.getStatus());
    }
}