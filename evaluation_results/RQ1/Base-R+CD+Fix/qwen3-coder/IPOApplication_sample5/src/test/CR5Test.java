import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

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
    public void testCase1_cancelStillPendingRequest() {
        // Setup
        customer.setName("Benjamin");
        customer.setSurname("Taylor");
        customer.setEmail("b.taylor@example.com");
        customer.setTelephone("555-1010");
        customer.setCanApplyForIPO(true);
        
        company.setName("EcoWave");
        company.setEmail("ecowave@gmail.com");
        
        // Create a pending application
        boolean applicationCreated = customer.createApplication(company, 15, 750.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Verify the application is pending
        List<Application> applications = customer.getApplications();
        assertEquals("Should have 1 application", 1, applications.size());
        assertEquals("Application should be pending", ApplicationStatus.PENDING, applications.get(0).getStatus());
        
        // Test: Cancel the pending application
        boolean result = customer.cancelApplication("EcoWave");
        
        // Verify
        assertTrue("Cancellation should succeed for pending application", result);
        assertEquals("Application status should be rejected after cancellation", ApplicationStatus.REJECTED, applications.get(0).getStatus());
    }
    
    @Test
    public void testCase2_cancelApprovedRequest() {
        // Setup
        customer.setName("Charlotte");
        customer.setSurname("Lee");
        customer.setEmail("c.lee@example.com");
        customer.setTelephone("555-2020");
        customer.setCanApplyForIPO(true);
        
        company.setName("SmartGrid");
        company.setEmail("smartgrid@business.com");
        
        // Create application
        boolean applicationCreated = customer.createApplication(company, 30, 3000.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Approve the application
        Application app = customer.getApplications().get(0);
        boolean approvalResult = app.approve();
        assertTrue("Application should be approved successfully", approvalResult);
        assertEquals("Application status should be approval", ApplicationStatus.APPROVAL, app.getStatus());
        
        // Test: Try to cancel approved application
        boolean result = customer.cancelApplication("SmartGrid");
        
        // Verify
        assertFalse("Cancellation should fail for approved application", result);
        assertEquals("Application status should remain approved", ApplicationStatus.APPROVAL, app.getStatus());
    }
    
    @Test
    public void testCase3_cancelRejectedRequest() {
        // Setup
        customer.setName("Lucas");
        customer.setSurname("Martin");
        customer.setEmail("l.martin@example.com");
        customer.setTelephone("555-3030");
        customer.setCanApplyForIPO(true);
        
        company.setName("MedLife");
        company.setEmail("medlife@health.com");
        
        // Create application
        boolean applicationCreated = customer.createApplication(company, 20, 1000.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Reject the application
        Application app = customer.getApplications().get(0);
        boolean rejectionResult = app.reject();
        assertTrue("Application should be rejected successfully", rejectionResult);
        assertEquals("Application status should be rejected", ApplicationStatus.REJECTED, app.getStatus());
        
        // Test: Try to cancel rejected application
        boolean result = customer.cancelApplication("MedLife");
        
        // Verify
        assertFalse("Cancellation should fail for rejected application", result);
        assertEquals("Application status should remain rejected", ApplicationStatus.REJECTED, app.getStatus());
    }
    
    @Test
    public void testCase4_cancelNonexistentCompany() {
        // Setup
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmail("a.clark@example.com");
        customer.setTelephone("555-4040");
        customer.setCanApplyForIPO(true);
        
        // No applications created for this customer
        
        // Test: Try to cancel application for non-existent company
        boolean result = customer.cancelApplication("UnknownCorp");
        
        // Verify
        assertFalse("Cancellation should fail for non-existent company", result);
        assertEquals("Should have no applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase5_cancelAfterPriorCancellation() {
        // Setup
        customer.setName("Mia");
        customer.setSurname("Anderson");
        customer.setEmail("m.anderson@example.com");
        customer.setTelephone("555-6060");
        customer.setCanApplyForIPO(true);
        
        // Create first company and application
        Company company1 = new Company();
        company1.setName("UrbanTech");
        company1.setEmail("urbantech@innovate.com");
        
        // Create second company and application
        Company company2 = new Company();
        company2.setName("AgroSeed");
        company2.setEmail("agroseed@business.com");
        
        // Create both applications
        boolean app1Created = customer.createApplication(company1, 25, 1250.0, document);
        assertTrue("First application should be created successfully", app1Created);
        
        boolean app2Created = customer.createApplication(company2, 40, 2000.0, document);
        assertTrue("Second application should be created successfully", app2Created);
        
        // Verify both applications are pending
        List<Application> applications = customer.getApplications();
        assertEquals("Should have 2 applications", 2, applications.size());
        assertEquals("First application should be pending", ApplicationStatus.PENDING, applications.get(0).getStatus());
        assertEquals("Second application should be pending", ApplicationStatus.PENDING, applications.get(1).getStatus());
        
        // Test: Cancel first application (UrbanTech)
        boolean result = customer.cancelApplication("UrbanTech");
        
        // Verify
        assertTrue("Cancellation should succeed for UrbanTech application", result);
        
        // Check that UrbanTech application is now rejected
        Application urbanTechApp = null;
        Application agroSeedApp = null;
        
        for (Application app : applications) {
            if (app.getCompany().getName().equals("UrbanTech")) {
                urbanTechApp = app;
            } else if (app.getCompany().getName().equals("AgroSeed")) {
                agroSeedApp = app;
            }
        }
        
        assertNotNull("UrbanTech application should exist", urbanTechApp);
        assertNotNull("AgroSeed application should exist", agroSeedApp);
        
        assertEquals("UrbanTech application should be rejected", ApplicationStatus.REJECTED, urbanTechApp.getStatus());
        assertEquals("AgroSeed application should remain pending", ApplicationStatus.PENDING, agroSeedApp.getStatus());
    }
}