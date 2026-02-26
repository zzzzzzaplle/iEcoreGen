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
    public void testCase1_cancelStillPendingRequest() {
        // Setup: Customer "C301" has a pending application for "EcoWave"
        customer.setName("Benjamin");
        customer.setSurname("Taylor");
        customer.setEmail("b.taylor@example.com");
        customer.setTelephone("555-1010");
        customer.setCanApplyForIPO(true);
        
        company.setName("EcoWave");
        company.setEmail("ecowave@gmail.com");
        
        // Create pending application
        Document doc = new Document();
        boolean applicationCreated = customer.createApplication(company, 15, 750.0, doc);
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Verify application is in pending state before cancellation
        assertEquals("Should have 1 application", 1, customer.getApplications().size());
        assertEquals("Application should be PENDING", ApplicationStatus.PENDING, 
                     customer.getApplications().get(0).getStatus());
        
        // Test: Cancel the pending application
        boolean result = customer.cancelApplication("EcoWave");
        
        // Verify: Should return true for successful cancellation
        assertTrue("Cancellation of pending application should succeed", result);
        
        // Verify application status changed to REJECTED after cancellation
        assertEquals("Application status should be REJECTED after cancellation", 
                     ApplicationStatus.REJECTED, customer.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase2_cancelApprovedRequest() {
        // Setup: Customer "C302" has an approved application for "SmartGrid"
        customer.setName("Charlotte");
        customer.setSurname("Lee");
        customer.setEmail("c.lee@example.com");
        customer.setTelephone("555-2020");
        customer.setCanApplyForIPO(true);
        
        company.setName("SmartGrid");
        company.setEmail("smartgrid@business.com");
        
        // Create application and approve it
        Document doc = new Document();
        boolean applicationCreated = customer.createApplication(company, 30, 3000.0, doc);
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Approve the application
        Application app = customer.getApplications().get(0);
        boolean approved = app.approve();
        assertTrue("Application should be approved successfully", approved);
        assertEquals("Application should be APPROVAL", ApplicationStatus.APPROVAL, app.getStatus());
        
        // Test: Try to cancel approved application
        boolean result = customer.cancelApplication("SmartGrid");
        
        // Verify: Should return false (cannot cancel approved applications)
        assertFalse("Cancellation of approved application should fail", result);
        
        // Verify application status remains APPROVAL
        assertEquals("Application status should remain APPROVAL", 
                     ApplicationStatus.APPROVAL, app.getStatus());
    }
    
    @Test
    public void testCase3_cancelRejectedRequest() {
        // Setup: Customer "C303" has a rejected application for "MedLife"
        customer.setName("Lucas");
        customer.setSurname("Martin");
        customer.setEmail("l.martin@example.com");
        customer.setTelephone("555-3030");
        customer.setCanApplyForIPO(true);
        
        company.setName("MedLife");
        company.setEmail("medlife@health.com");
        
        // Create application and reject it
        Document doc = new Document();
        boolean applicationCreated = customer.createApplication(company, 20, 1000.0, doc);
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Reject the application
        Application app = customer.getApplications().get(0);
        boolean rejected = app.reject();
        assertTrue("Application should be rejected successfully", rejected);
        assertEquals("Application should be REJECTED", ApplicationStatus.REJECTED, app.getStatus());
        
        // Test: Try to cancel rejected application
        boolean result = customer.cancelApplication("MedLife");
        
        // Verify: Should return false (cannot cancel rejected applications)
        assertFalse("Cancellation of rejected application should fail", result);
        
        // Verify application status remains REJECTED
        assertEquals("Application status should remain REJECTED", 
                     ApplicationStatus.REJECTED, app.getStatus());
    }
    
    @Test
    public void testCase4_cancelNonexistentCompany() {
        // Setup: Customer "C304" has no application for "UnknownCorp"
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmail("a.clark@example.com");
        customer.setTelephone("555-4040");
        customer.setCanApplyForIPO(true);
        
        // Customer has no applications initially
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
        
        // Test: Try to cancel application for non-existent company
        boolean result = customer.cancelApplication("UnknownCorp");
        
        // Verify: Should return false (no application found for specified company)
        assertFalse("Cancellation for non-existent company should fail", result);
    }
    
    @Test
    public void testCase5_cancelAfterPriorCancellation() {
        // Setup: Customer "C306" has two pending applications
        customer.setName("Mia");
        customer.setSurname("Anderson");
        customer.setEmail("m.anderson@example.com");
        customer.setTelephone("555-6060");
        customer.setCanApplyForIPO(true);
        
        // Create first application for UrbanTech
        Company urbanTech = new Company();
        urbanTech.setName("UrbanTech");
        urbanTech.setEmail("urbantech@innovate.com");
        Document doc1 = new Document();
        boolean app1Created = customer.createApplication(urbanTech, 25, 1250.0, doc1);
        assertTrue("UrbanTech application should be created", app1Created);
        
        // Create second application for AgroSeed
        Company agroSeed = new Company();
        agroSeed.setName("AgroSeed");
        agroSeed.setEmail("agroseed@business.com"); // Email not specified in test case, using generic
        Document doc2 = new Document();
        boolean app2Created = customer.createApplication(agroSeed, 40, 2000.0, doc2);
        assertTrue("AgroSeed application should be created", app2Created);
        
        // Verify both applications are pending
        assertEquals("Should have 2 applications", 2, customer.getApplications().size());
        assertEquals("UrbanTech application should be PENDING", ApplicationStatus.PENDING, 
                     customer.getApplications().get(0).getStatus());
        assertEquals("AgroSeed application should be PENDING", ApplicationStatus.PENDING, 
                     customer.getApplications().get(1).getStatus());
        
        // Test: Cancel UrbanTech application
        boolean result = customer.cancelApplication("UrbanTech");
        
        // Verify: Should return true (UrbanTech application canceled successfully)
        assertTrue("Cancellation of UrbanTech application should succeed", result);
        
        // Verify UrbanTech application status changed to REJECTED
        Application urbanTechApp = findApplicationByCompanyName(customer, "UrbanTech");
        assertNotNull("UrbanTech application should exist", urbanTechApp);
        assertEquals("UrbanTech application should be REJECTED after cancellation", 
                     ApplicationStatus.REJECTED, urbanTechApp.getStatus());
        
        // Verify AgroSeed application remains PENDING and unaffected
        Application agroSeedApp = findApplicationByCompanyName(customer, "AgroSeed");
        assertNotNull("AgroSeed application should exist", agroSeedApp);
        assertEquals("AgroSeed application should remain PENDING", 
                     ApplicationStatus.PENDING, agroSeedApp.getStatus());
    }
    
    // Helper method to find application by company name
    private Application findApplicationByCompanyName(Customer customer, String companyName) {
        for (Application app : customer.getApplications()) {
            if (app.getCompany().getName().equals(companyName)) {
                return app;
            }
        }
        return null;
    }
}