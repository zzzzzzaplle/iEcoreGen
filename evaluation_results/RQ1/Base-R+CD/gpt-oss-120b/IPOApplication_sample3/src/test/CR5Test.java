import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    private Customer customer;
    private Company company;
    
    @Before
    public void setUp() {
        customer = new Customer();
        company = new Company();
    }
    
    @Test
    public void testCase1_CancelStillPendingRequest() {
        // Setup: Customer "C301" with pending application for "EcoWave"
        customer.setName("Benjamin");
        customer.setSurname("Taylor");
        customer.setEmail("b.taylor@example.com");
        customer.setTelephone("555-1010");
        customer.setCanApplyForIPO(true);
        
        Company ecoWave = new Company();
        ecoWave.setName("EcoWave");
        ecoWave.setEmail("ecowave@gmail.com");
        
        Document doc = new Document();
        
        // Create pending application
        boolean applicationCreated = customer.createApplication(ecoWave, 15, 750.0, doc);
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Test: Cancel the pending application
        boolean result = customer.cancelApplication("EcoWave");
        
        // Verify: Cancellation should succeed
        assertTrue("Cancellation of pending application should return true", result);
        
        // Verify application status is now rejected (cancelled)
        assertEquals("Application should have REJECTED status after cancellation", 
                     ApplicationStatus.REJECTED, customer.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase2_CancelApprovedRequest() {
        // Setup: Customer "C302" with approved application for "SmartGrid"
        customer.setName("Charlotte");
        customer.setSurname("Lee");
        customer.setEmail("c.lee@example.com");
        customer.setTelephone("555-2020");
        customer.setCanApplyForIPO(true);
        
        Company smartGrid = new Company();
        smartGrid.setName("SmartGrid");
        smartGrid.setEmail("smartgrid@business.com");
        
        Document doc = new Document();
        
        // Create application and approve it
        boolean applicationCreated = customer.createApplication(smartGrid, 30, 3000.0, doc);
        assertTrue("Application should be created successfully", applicationCreated);
        
        Application app = customer.getApplications().get(0);
        boolean approved = app.approve();
        assertTrue("Application should be approved successfully", approved);
        
        // Test: Try to cancel approved application
        boolean result = customer.cancelApplication("SmartGrid");
        
        // Verify: Cancellation should fail
        assertFalse("Cancellation of approved application should return false", result);
        
        // Verify application status remains approved
        assertEquals("Application status should remain APPROVAL", 
                     ApplicationStatus.APPROVAL, app.getStatus());
    }
    
    @Test
    public void testCase3_CancelRejectedRequest() {
        // Setup: Customer "C303" with rejected application for "MedLife"
        customer.setName("Lucas");
        customer.setSurname("Martin");
        customer.setEmail("l.martin@example.com");
        customer.setTelephone("555-3030");
        customer.setCanApplyForIPO(true);
        
        Company medLife = new Company();
        medLife.setName("MedLife");
        medLife.setEmail("medlife@health.com");
        
        Document doc = new Document();
        
        // Create application and reject it
        boolean applicationCreated = customer.createApplication(medLife, 20, 1000.0, doc);
        assertTrue("Application should be created successfully", applicationCreated);
        
        Application app = customer.getApplications().get(0);
        boolean rejected = app.reject();
        assertTrue("Application should be rejected successfully", rejected);
        
        // Test: Try to cancel rejected application
        boolean result = customer.cancelApplication("MedLife");
        
        // Verify: Cancellation should fail
        assertFalse("Cancellation of rejected application should return false", result);
        
        // Verify application status remains rejected
        assertEquals("Application status should remain REJECTED", 
                     ApplicationStatus.REJECTED, app.getStatus());
    }
    
    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Setup: Customer "C304" with no application for "UnknownCorp"
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmail("a.clark@example.com");
        customer.setTelephone("555-4040");
        customer.setCanApplyForIPO(true);
        
        // No applications created for "UnknownCorp"
        
        // Test: Try to cancel application for non-existent company
        boolean result = customer.cancelApplication("UnknownCorp");
        
        // Verify: Cancellation should fail
        assertFalse("Cancellation for non-existent company should return false", result);
        
        // Verify no applications were affected
        assertEquals("No applications should exist", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Setup: Customer "C306" with two pending applications
        customer.setName("Mia");
        customer.setSurname("Anderson");
        customer.setEmail("m.anderson@example.com");
        customer.setTelephone("555-6060");
        customer.setCanApplyForIPO(true);
        
        Company urbanTech = new Company();
        urbanTech.setName("UrbanTech");
        urbanTech.setEmail("urbantech@innovate.com");
        
        Company agroSeed = new Company();
        agroSeed.setName("AgroSeed");
        agroSeed.setEmail("agroseed@business.com");
        
        Document doc1 = new Document();
        Document doc2 = new Document();
        
        // Create two pending applications
        boolean app1Created = customer.createApplication(urbanTech, 25, 1250.0, doc1);
        boolean app2Created = customer.createApplication(agroSeed, 40, 2000.0, doc2);
        
        assertTrue("First application should be created successfully", app1Created);
        assertTrue("Second application should be created successfully", app2Created);
        assertEquals("Should have 2 applications", 2, customer.getApplications().size());
        
        // Test: Cancel "UrbanTech" application
        boolean result = customer.cancelApplication("UrbanTech");
        
        // Verify: Cancellation should succeed
        assertTrue("Cancellation of UrbanTech application should return true", result);
        
        // Verify only UrbanTech application was cancelled (status REJECTED)
        Application urbanTechApp = null;
        Application agroSeedApp = null;
        
        for (Application app : customer.getApplications()) {
            if ("UrbanTech".equals(app.getCompany().getName())) {
                urbanTechApp = app;
            } else if ("AgroSeed".equals(app.getCompany().getName())) {
                agroSeedApp = app;
            }
        }
        
        assertNotNull("UrbanTech application should exist", urbanTechApp);
        assertNotNull("AgroSeed application should exist", agroSeedApp);
        assertEquals("UrbanTech application should be REJECTED", 
                     ApplicationStatus.REJECTED, urbanTechApp.getStatus());
        assertEquals("AgroSeed application should remain PENDING", 
                     ApplicationStatus.PENDING, agroSeedApp.getStatus());
    }
}