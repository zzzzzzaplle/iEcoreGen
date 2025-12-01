import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    private Customer customer;
    private Company company;
    private Document document;
    
    @Before
    public void setUp() {
        customer = new Customer();
        company = new Company();
        document = new Document();
    }
    
    @Test
    public void testCase1_cancelPendingRequest() {
        // Setup Customer C301
        customer.setName("Benjamin");
        customer.setSurname("Taylor");
        customer.setEmail("b.taylor@example.com");
        customer.setTelephone("555-1010");
        customer.setCanApplyForIPO(true);
        
        // Setup Company EcoWave
        company.setName("EcoWave");
        company.setEmail("ecowave@gmail.com");
        
        // Create pending application for EcoWave
        boolean applicationCreated = customer.createApplication(company, 15, 750.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        assertEquals("Should have one application", 1, customer.getApplications().size());
        
        // Test cancellation
        boolean result = customer.cancelApplication("EcoWave");
        assertTrue("Cancellation of pending application should succeed", result);
        
        // Verify application status is now rejected after cancellation
        assertEquals("Application status should be REJECTED after cancellation", 
                     ApplicationStatus.REJECTED, customer.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase2_cancelApprovedRequest() {
        // Setup Customer C302
        customer.setName("Charlotte");
        customer.setSurname("Lee");
        customer.setEmail("c.lee@example.com");
        customer.setTelephone("555-2020");
        customer.setCanApplyForIPO(true);
        
        // Setup Company SmartGrid
        company.setName("SmartGrid");
        company.setEmail("smartgrid@business.com");
        
        // Create and approve application for SmartGrid
        boolean applicationCreated = customer.createApplication(company, 30, 3000.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        Application app = customer.getApplications().get(0);
        boolean approvalResult = app.approve();
        assertTrue("Application should be approved successfully", approvalResult);
        assertEquals("Application status should be APPROVAL", 
                     ApplicationStatus.APPROVAL, app.getStatus());
        
        // Test cancellation - should fail for approved application
        boolean result = customer.cancelApplication("SmartGrid");
        assertFalse("Cancellation of approved application should fail", result);
    }
    
    @Test
    public void testCase3_cancelRejectedRequest() {
        // Setup Customer C303
        customer.setName("Lucas");
        customer.setSurname("Martin");
        customer.setEmail("l.martin@example.com");
        customer.setTelephone("555-3030");
        customer.setCanApplyForIPO(true);
        
        // Setup Company MedLife
        company.setName("MedLife");
        company.setEmail("medlife@health.com");
        
        // Create and reject application for MedLife
        boolean applicationCreated = customer.createApplication(company, 20, 1000.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        Application app = customer.getApplications().get(0);
        boolean rejectionResult = app.reject();
        assertTrue("Application should be rejected successfully", rejectionResult);
        assertEquals("Application status should be REJECTED", 
                     ApplicationStatus.REJECTED, app.getStatus());
        
        // Test cancellation - should fail for rejected application
        boolean result = customer.cancelApplication("MedLife");
        assertFalse("Cancellation of rejected application should fail", result);
    }
    
    @Test
    public void testCase4_cancelNonexistentCompany() {
        // Setup Customer C304
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmail("a.clark@example.com");
        customer.setTelephone("555-4040");
        customer.setCanApplyForIPO(true);
        
        // No applications created for this customer
        
        // Test cancellation for non-existent company
        boolean result = customer.cancelApplication("UnknownCorp");
        assertFalse("Cancellation for non-existent company should fail", result);
    }
    
    @Test
    public void testCase5_cancelAfterPriorCancellation() {
        // Setup Customer C306 (note: corrected from C305 as per specification)
        customer.setName("Mia");
        customer.setSurname("Anderson");
        customer.setEmail("m.anderson@example.com");
        customer.setTelephone("555-6060");
        customer.setCanApplyForIPO(true);
        
        // Setup Companies
        Company urbanTech = new Company();
        urbanTech.setName("UrbanTech");
        urbanTech.setEmail("urbantech@innovate.com");
        
        Company agroSeed = new Company();
        agroSeed.setName("AgroSeed");
        agroSeed.setEmail("agroseed@business.com");
        
        // Create two pending applications
        boolean app1Created = customer.createApplication(urbanTech, 25, 1250.0, document);
        boolean app2Created = customer.createApplication(agroSeed, 40, 2000.0, document);
        
        assertTrue("First application should be created successfully", app1Created);
        assertTrue("Second application should be created successfully", app2Created);
        assertEquals("Should have two applications", 2, customer.getApplications().size());
        
        // Cancel UrbanTech application
        boolean result = customer.cancelApplication("UrbanTech");
        assertTrue("Cancellation of UrbanTech application should succeed", result);
        
        // Verify UrbanTech application is rejected
        Application urbanTechApp = findApplicationByCompanyName(customer, "UrbanTech");
        assertNotNull("UrbanTech application should exist", urbanTechApp);
        assertEquals("UrbanTech application status should be REJECTED", 
                     ApplicationStatus.REJECTED, urbanTechApp.getStatus());
        
        // Verify AgroSeed application remains pending and unaffected
        Application agroSeedApp = findApplicationByCompanyName(customer, "AgroSeed");
        assertNotNull("AgroSeed application should exist", agroSeedApp);
        assertEquals("AgroSeed application status should remain PENDING", 
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