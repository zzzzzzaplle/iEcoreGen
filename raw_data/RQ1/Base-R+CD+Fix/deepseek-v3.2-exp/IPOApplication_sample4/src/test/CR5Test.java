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
    public void testCase1_CancelStillPendingRequest() {
        // Setup for Test Case 1
        customer.setName("Benjamin");
        customer.setSurname("Taylor");
        customer.setEmail("b.taylor@example.com");
        customer.setTelephone("555-1010");
        customer.setCanApplyForIPO(true);
        
        company.setName("EcoWave");
        company.setEmail("ecowave@gmail.com");
        
        // Create pending application
        boolean applicationCreated = customer.createApplication(company, 15, 750.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Verify application is pending
        assertEquals("Application should be in pending status", 
                     ApplicationStatus.PENDING, 
                     customer.getApplications().get(0).getStatus());
        
        // Test cancellation
        boolean cancellationResult = customer.cancelApplication("EcoWave");
        assertTrue("Cancellation of pending application should succeed", cancellationResult);
        
        // Verify application status changed to rejected after cancellation
        assertEquals("Application status should be REJECTED after cancellation", 
                     ApplicationStatus.REJECTED, 
                     customer.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase2_CancelApprovedRequest() {
        // Setup for Test Case 2
        customer.setName("Charlotte");
        customer.setSurname("Lee");
        customer.setEmail("c.lee@example.com");
        customer.setTelephone("555-2020");
        customer.setCanApplyForIPO(true);
        
        company.setName("SmartGrid");
        company.setEmail("smartgrid@business.com");
        
        // Create application and approve it
        boolean applicationCreated = customer.createApplication(company, 30, 3000.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        Application application = customer.getApplications().get(0);
        boolean approvalResult = application.approve();
        assertTrue("Application should be approved successfully", approvalResult);
        
        // Verify application is approved
        assertEquals("Application should be in approval status", 
                     ApplicationStatus.APPROVAL, 
                     application.getStatus());
        
        // Test cancellation - should fail
        boolean cancellationResult = customer.cancelApplication("SmartGrid");
        assertFalse("Cancellation of approved application should fail", cancellationResult);
        
        // Verify application status remains approved
        assertEquals("Application status should remain APPROVAL", 
                     ApplicationStatus.APPROVAL, 
                     application.getStatus());
    }
    
    @Test
    public void testCase3_CancelRejectedRequest() {
        // Setup for Test Case 3
        customer.setName("Lucas");
        customer.setSurname("Martin");
        customer.setEmail("l.martin@example.com");
        customer.setTelephone("555-3030");
        customer.setCanApplyForIPO(true);
        
        company.setName("MedLife");
        company.setEmail("medlife@health.com");
        
        // Create application and reject it
        boolean applicationCreated = customer.createApplication(company, 20, 1000.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        Application application = customer.getApplications().get(0);
        boolean rejectionResult = application.reject();
        assertTrue("Application should be rejected successfully", rejectionResult);
        
        // Verify application is rejected
        assertEquals("Application should be in rejected status", 
                     ApplicationStatus.REJECTED, 
                     application.getStatus());
        
        // Test cancellation - should fail
        boolean cancellationResult = customer.cancelApplication("MedLife");
        assertFalse("Cancellation of rejected application should fail", cancellationResult);
        
        // Verify application status remains rejected
        assertEquals("Application status should remain REJECTED", 
                     ApplicationStatus.REJECTED, 
                     application.getStatus());
    }
    
    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Setup for Test Case 4
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmail("a.clark@example.com");
        customer.setTelephone("555-4040");
        customer.setCanApplyForIPO(true);
        
        // No applications created for this customer
        
        // Test cancellation for non-existent company
        boolean cancellationResult = customer.cancelApplication("UnknownCorp");
        assertFalse("Cancellation for non-existent company should fail", cancellationResult);
        
        // Verify no applications exist
        assertEquals("Customer should have no applications", 
                     0, 
                     customer.getApplications().size());
    }
    
    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Setup for Test Case 5
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
        
        // Create two pending applications
        boolean app1Created = customer.createApplication(urbanTech, 25, 1250.0, document);
        boolean app2Created = customer.createApplication(agroSeed, 40, 2000.0, document);
        
        assertTrue("First application should be created successfully", app1Created);
        assertTrue("Second application should be created successfully", app2Created);
        
        // Verify both applications are pending
        assertEquals("Customer should have 2 applications", 
                     2, 
                     customer.getApplications().size());
        
        assertEquals("First application should be pending", 
                     ApplicationStatus.PENDING, 
                     customer.getApplications().get(0).getStatus());
        
        assertEquals("Second application should be pending", 
                     ApplicationStatus.PENDING, 
                     customer.getApplications().get(1).getStatus());
        
        // Test cancellation for UrbanTech
        boolean cancellationResult = customer.cancelApplication("UrbanTech");
        assertTrue("Cancellation of UrbanTech application should succeed", cancellationResult);
        
        // Verify UrbanTech application is rejected (cancelled)
        Application urbanTechApp = findApplicationByCompanyName(customer, "UrbanTech");
        assertNotNull("UrbanTech application should exist", urbanTechApp);
        assertEquals("UrbanTech application should be REJECTED after cancellation", 
                     ApplicationStatus.REJECTED, 
                     urbanTechApp.getStatus());
        
        // Verify AgroSeed application remains pending
        Application agroSeedApp = findApplicationByCompanyName(customer, "AgroSeed");
        assertNotNull("AgroSeed application should exist", agroSeedApp);
        assertEquals("AgroSeed application should remain PENDING", 
                     ApplicationStatus.PENDING, 
                     agroSeedApp.getStatus());
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