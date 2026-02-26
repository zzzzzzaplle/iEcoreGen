import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    private Customer customer;
    private Company company;
    private Document document;
    
    @Before
    public void setUp() {
        // Common setup that runs before each test
        document = new Document();
    }
    
    @Test
    public void testCase1_cancelStillPendingRequest() {
        // Setup: Customer "C301" has a pending application for "EcoWave"
        customer = new Customer("Benjamin", "Taylor", "b.taylor@example.com", "555-1010");
        company = new Company("EcoWave", "ecowave@gmail.com");
        
        // Create pending application
        boolean applicationCreated = customer.createApplication(company, 15, 750.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Verify the application is initially pending
        assertEquals("Application should be pending", ApplicationStatus.PENDING, 
                    customer.getApplications().get(0).getStatus());
        
        // Test: Cancel the pending application for "EcoWave"
        boolean result = customer.cancelApplication("EcoWave");
        
        // Verify cancellation succeeded
        assertTrue("Cancellation of pending application should return true", result);
        assertEquals("Application status should be REJECTED after cancellation", 
                    ApplicationStatus.REJECTED, customer.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase2_cancelApprovedRequest() {
        // Setup: Customer "C302" has an approved application for "SmartGrid"
        customer = new Customer("Charlotte", "Lee", "c.lee@example.com", "555-2020");
        company = new Company("SmartGrid", "smartgrid@business.com");
        
        // Create application and approve it
        boolean applicationCreated = customer.createApplication(company, 30, 3000.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        Application app = customer.getApplications().get(0);
        boolean approved = app.approve();
        assertTrue("Application should be approved successfully", approved);
        
        // Test: Try to cancel the approved application
        boolean result = customer.cancelApplication("SmartGrid");
        
        // Verify cancellation failed
        assertFalse("Cancellation of approved application should return false", result);
        assertEquals("Application status should remain APPROVED", 
                    ApplicationStatus.APPROVAL, app.getStatus());
    }
    
    @Test
    public void testCase3_cancelRejectedRequest() {
        // Setup: Customer "C303" has a rejected application for "MedLife"
        customer = new Customer("Lucas", "Martin", "l.martin@example.com", "555-3030");
        company = new Company("MedLife", "medlife@health.com");
        
        // Create application and reject it
        boolean applicationCreated = customer.createApplication(company, 20, 1000.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        Application app = customer.getApplications().get(0);
        boolean rejected = app.reject();
        assertTrue("Application should be rejected successfully", rejected);
        
        // Test: Try to cancel the rejected application
        boolean result = customer.cancelApplication("MedLife");
        
        // Verify cancellation failed
        assertFalse("Cancellation of rejected application should return false", result);
        assertEquals("Application status should remain REJECTED", 
                    ApplicationStatus.REJECTED, app.getStatus());
    }
    
    @Test
    public void testCase4_cancelNonexistentCompany() {
        // Setup: Customer "C304" has no application for "UnknownCorp"
        customer = new Customer("Amelia", "Clark", "a.clark@example.com", "555-4040");
        
        // Verify no applications exist initially
        assertEquals("Customer should have no applications initially", 0, customer.getApplications().size());
        
        // Test: Try to cancel application for non-existent company
        boolean result = customer.cancelApplication("UnknownCorp");
        
        // Verify cancellation failed
        assertFalse("Cancellation for non-existent company should return false", result);
        assertEquals("Customer should still have no applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase5_cancelAfterPriorCancellation() {
        // Setup: Customer "C306" has two pending applications
        customer = new Customer("Mia", "Anderson", "m.anderson@example.com", "555-6060");
        Company urbanTech = new Company("UrbanTech", "urbantech@innovate.com");
        Company agroSeed = new Company("AgroSeed", "agroseed@farm.com");
        
        // Create first application for UrbanTech
        boolean app1Created = customer.createApplication(urbanTech, 25, 1250.0, document);
        assertTrue("First application should be created successfully", app1Created);
        
        // Create second application for AgroSeed
        boolean app2Created = customer.createApplication(agroSeed, 40, 2000.0, document);
        assertTrue("Second application should be created successfully", app2Created);
        
        // Verify both applications are pending
        assertEquals("Customer should have 2 applications", 2, customer.getApplications().size());
        assertEquals("First application should be pending", ApplicationStatus.PENDING, 
                    customer.getApplications().get(0).getStatus());
        assertEquals("Second application should be pending", ApplicationStatus.PENDING, 
                    customer.getApplications().get(1).getStatus());
        
        // Test: Cancel the UrbanTech application
        boolean result = customer.cancelApplication("UrbanTech");
        
        // Verify cancellation succeeded
        assertTrue("Cancellation of UrbanTech application should return true", result);
        
        // Verify UrbanTech application is now rejected (cancelled)
        Application urbanTechApp = findApplicationByCompany(customer, "UrbanTech");
        assertNotNull("UrbanTech application should exist", urbanTechApp);
        assertEquals("UrbanTech application should be REJECTED after cancellation", 
                    ApplicationStatus.REJECTED, urbanTechApp.getStatus());
        
        // Verify AgroSeed application remains unaffected (still pending)
        Application agroSeedApp = findApplicationByCompany(customer, "AgroSeed");
        assertNotNull("AgroSeed application should exist", agroSeedApp);
        assertEquals("AgroSeed application should remain PENDING", 
                    ApplicationStatus.PENDING, agroSeedApp.getStatus());
    }
    
    // Helper method to find application by company name
    private Application findApplicationByCompany(Customer customer, String companyName) {
        for (Application app : customer.getApplications()) {
            if (app.getCompany() != null && app.getCompany().getName().equals(companyName)) {
                return app;
            }
        }
        return null;
    }
}