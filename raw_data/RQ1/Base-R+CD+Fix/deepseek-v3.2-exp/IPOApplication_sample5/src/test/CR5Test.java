import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    private Customer customer;
    private Company company;
    private Document document;
    
    @Before
    public void setUp() {
        // Common setup for test cases
        document = new Document();
    }
    
    @Test
    public void testCase1_CancelStillPendingRequest() {
        // Setup
        customer = new Customer();
        customer.setName("Benjamin");
        customer.setSurname("Taylor");
        customer.setEmail("b.taylor@example.com");
        customer.setTelephone("555-1010");
        customer.setCanApplyForIPO(true);
        
        company = new Company();
        company.setName("EcoWave");
        company.setEmail("ecowave@gmail.com");
        
        // Create pending application
        boolean applicationCreated = customer.createApplication(company, 15, 750.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Verify application is pending
        assertEquals("Application should be in PENDING status", 
                     ApplicationStatus.PENDING, customer.getApplications().get(0).getStatus());
        
        // Test: Cancel the pending application
        boolean result = customer.cancelApplication("EcoWave");
        
        // Verify result
        assertTrue("Cancellation of pending application should return true", result);
    }
    
    @Test
    public void testCase2_CancelApprovedRequest() {
        // Setup
        customer = new Customer();
        customer.setName("Charlotte");
        customer.setSurname("Lee");
        customer.setEmail("c.lee@example.com");
        customer.setTelephone("555-2020");
        customer.setCanApplyForIPO(true);
        
        company = new Company();
        company.setName("SmartGrid");
        company.setEmail("smartgrid@business.com");
        
        // Create application and approve it
        boolean applicationCreated = customer.createApplication(company, 30, 3000.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        Application application = customer.getApplications().get(0);
        boolean approved = application.approve();
        assertTrue("Application should be approved successfully", approved);
        
        // Verify application is approved
        assertEquals("Application should be in APPROVAL status", 
                     ApplicationStatus.APPROVAL, application.getStatus());
        
        // Test: Try to cancel approved application
        boolean result = customer.cancelApplication("SmartGrid");
        
        // Verify result
        assertFalse("Cancellation of approved application should return false", result);
    }
    
    @Test
    public void testCase3_CancelRejectedRequest() {
        // Setup
        customer = new Customer();
        customer.setName("Lucas");
        customer.setSurname("Martin");
        customer.setEmail("l.martin@example.com");
        customer.setTelephone("555-3030");
        customer.setCanApplyForIPO(true);
        
        company = new Company();
        company.setName("MedLife");
        company.setEmail("medlife@health.com");
        
        // Create application and reject it
        boolean applicationCreated = customer.createApplication(company, 20, 1000.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        Application application = customer.getApplications().get(0);
        boolean rejected = application.reject();
        assertTrue("Application should be rejected successfully", rejected);
        
        // Verify application is rejected
        assertEquals("Application should be in REJECTED status", 
                     ApplicationStatus.REJECTED, application.getStatus());
        
        // Test: Try to cancel rejected application
        boolean result = customer.cancelApplication("MedLife");
        
        // Verify result
        assertFalse("Cancellation of rejected application should return false", result);
    }
    
    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Setup
        customer = new Customer();
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmail("a.clark@example.com");
        customer.setTelephone("555-4040");
        customer.setCanApplyForIPO(true);
        
        // Customer has no applications
        
        // Test: Try to cancel application for non-existent company
        boolean result = customer.cancelApplication("UnknownCorp");
        
        // Verify result
        assertFalse("Cancellation for non-existent company should return false", result);
    }
    
    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Setup - Note: Corrected customer ID from C305 to C306 as per test specification
        customer = new Customer();
        customer.setName("Mia");
        customer.setSurname("Anderson");
        customer.setEmail("m.anderson@example.com");
        customer.setTelephone("555-6060");
        customer.setCanApplyForIPO(true);
        
        Company company1 = new Company();
        company1.setName("UrbanTech");
        company1.setEmail("urbantech@innovate.com");
        
        Company company2 = new Company();
        company2.setName("AgroSeed");
        company2.setEmail("agroseed@agriculture.com");
        
        // Create two pending applications
        boolean app1Created = customer.createApplication(company1, 25, 1250.0, document);
        assertTrue("First application should be created successfully", app1Created);
        
        boolean app2Created = customer.createApplication(company2, 40, 2000.0, document);
        assertTrue("Second application should be created successfully", app2Created);
        
        // Verify both applications are pending and exist
        assertEquals("Customer should have 2 applications", 2, customer.getApplications().size());
        assertEquals("First application should be PENDING", 
                     ApplicationStatus.PENDING, customer.getApplications().get(0).getStatus());
        assertEquals("Second application should be PENDING", 
                     ApplicationStatus.PENDING, customer.getApplications().get(1).getStatus());
        
        // Test: Cancel the UrbanTech application
        boolean result = customer.cancelApplication("UrbanTech");
        
        // Verify cancellation result
        assertTrue("Cancellation of UrbanTech application should return true", result);
        
        // Verify UrbanTech application is now rejected (cancelled)
        Application urbanTechApp = findApplicationByCompanyName(customer, "UrbanTech");
        assertNotNull("UrbanTech application should exist", urbanTechApp);
        assertEquals("UrbanTech application should be REJECTED after cancellation", 
                     ApplicationStatus.REJECTED, urbanTechApp.getStatus());
        
        // Verify AgroSeed application remains unaffected (still pending)
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