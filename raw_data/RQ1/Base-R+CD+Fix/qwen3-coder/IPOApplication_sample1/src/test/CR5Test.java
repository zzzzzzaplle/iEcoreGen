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
    public void testCase1_CancelPendingRequest() {
        // Setup for Test Case 1: Cancel still-pending request
        Customer c301 = new Customer();
        c301.setName("Benjamin");
        c301.setSurname("Taylor");
        c301.setEmail("b.taylor@example.com");
        c301.setTelephone("555-1010");
        c301.setCanApplyForIPO(true);
        
        Company ecoWave = new Company();
        ecoWave.setName("EcoWave");
        ecoWave.setEmail("ecowave@gmail.com");
        
        // Create pending application
        boolean applicationCreated = c301.createApplication(ecoWave, 15, 750.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Test cancellation
        boolean cancellationResult = c301.cancelApplication("EcoWave");
        assertTrue("Cancellation should succeed for pending application", cancellationResult);
    }
    
    @Test
    public void testCase2_CancelApprovedRequest() {
        // Setup for Test Case 2: Cancel approved request
        Customer c302 = new Customer();
        c302.setName("Charlotte");
        c302.setSurname("Lee");
        c302.setEmail("c.lee@example.com");
        c302.setTelephone("555-2020");
        c302.setCanApplyForIPO(true);
        
        Company smartGrid = new Company();
        smartGrid.setName("SmartGrid");
        smartGrid.setEmail("smartgrid@business.com");
        
        // Create application and manually set to approved status
        boolean applicationCreated = c302.createApplication(smartGrid, 30, 3000.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Get the application and approve it
        Application app = c302.getApplications().get(0);
        app.setStatus(ApplicationStatus.APPROVED);
        
        // Test cancellation - should fail for approved application
        boolean cancellationResult = c302.cancelApplication("SmartGrid");
        assertFalse("Cancellation should fail for approved application", cancellationResult);
    }
    
    @Test
    public void testCase3_CancelRejectedRequest() {
        // Setup for Test Case 3: Cancel rejected request
        Customer c303 = new Customer();
        c303.setName("Lucas");
        c303.setSurname("Martin");
        c303.setEmail("l.martin@example.com");
        c303.setTelephone("555-3030");
        c303.setCanApplyForIPO(true);
        
        Company medLife = new Company();
        medLife.setName("MedLife");
        medLife.setEmail("medlife@health.com");
        
        // Create application and manually set to rejected status
        boolean applicationCreated = c303.createApplication(medLife, 20, 1000.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Get the application and reject it
        Application app = c303.getApplications().get(0);
        app.setStatus(ApplicationStatus.REJECTED);
        
        // Test cancellation - should fail for rejected application
        boolean cancellationResult = c303.cancelApplication("MedLife");
        assertFalse("Cancellation should fail for rejected application", cancellationResult);
    }
    
    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Setup for Test Case 4: Cancel nonexistent company
        Customer c304 = new Customer();
        c304.setName("Amelia");
        c304.setSurname("Clark");
        c304.setEmail("a.clark@example.com");
        c304.setTelephone("555-4040");
        c304.setCanApplyForIPO(true);
        
        // No applications created for this customer
        
        // Test cancellation for non-existent company
        boolean cancellationResult = c304.cancelApplication("UnknownCorp");
        assertFalse("Cancellation should fail for non-existent company", cancellationResult);
    }
    
    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Setup for Test Case 5: Cancel after prior cancellation
        Customer c306 = new Customer();
        c306.setName("Mia");
        c306.setSurname("Anderson");
        c306.setEmail("m.anderson@example.com");
        c306.setTelephone("555-6060");
        c306.setCanApplyForIPO(true);
        
        Company urbanTech = new Company();
        urbanTech.setName("UrbanTech");
        urbanTech.setEmail("urbantech@innovate.com");
        
        Company agroSeed = new Company();
        agroSeed.setName("AgroSeed");
        agroSeed.setEmail("agroseed@innovate.com");
        
        // Create two pending applications
        boolean app1Created = c306.createApplication(urbanTech, 25, 1250.0, document);
        boolean app2Created = c306.createApplication(agroSeed, 40, 2000.0, document);
        
        assertTrue("First application should be created successfully", app1Created);
        assertTrue("Second application should be created successfully", app2Created);
        assertEquals("Customer should have 2 applications", 2, c306.getApplications().size());
        
        // Cancel UrbanTech application
        boolean cancellationResult = c306.cancelApplication("UrbanTech");
        assertTrue("Cancellation should succeed for UrbanTech application", cancellationResult);
        
        // Verify UrbanTech application is rejected (cancelled)
        Application urbanTechApp = findApplicationByCompany(c306, "UrbanTech");
        assertNotNull("UrbanTech application should exist", urbanTechApp);
        assertEquals("UrbanTech application should be rejected after cancellation", 
                     ApplicationStatus.REJECTED, urbanTechApp.getStatus());
        
        // Verify AgroSeed application remains pending
        Application agroSeedApp = findApplicationByCompany(c306, "AgroSeed");
        assertNotNull("AgroSeed application should exist", agroSeedApp);
        assertEquals("AgroSeed application should remain pending", 
                     ApplicationStatus.PENDING, agroSeedApp.getStatus());
    }
    
    // Helper method to find application by company name
    private Application findApplicationByCompany(Customer customer, String companyName) {
        for (Application app : customer.getApplications()) {
            if (app.getCompany().getName().equals(companyName)) {
                return app;
            }
        }
        return null;
    }
}