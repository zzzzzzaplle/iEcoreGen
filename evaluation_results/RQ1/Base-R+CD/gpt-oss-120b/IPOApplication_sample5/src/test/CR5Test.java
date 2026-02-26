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
    public void testCase1_CancelStillPendingRequest() {
        // Setup: Customer "C301" with pending application for "EcoWave"
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
        
        // Test: Cancel pending application for "EcoWave"
        boolean result = customer.cancelApplication("EcoWave");
        
        // Verify: Cancellation should succeed
        assertTrue("Pending application should be cancelled successfully", result);
        
        // Verify application status is now rejected (cancelled)
        List<Application> applications = customer.getApplications();
        assertEquals("Should have one application", 1, applications.size());
        assertEquals("Application status should be REJECTED after cancellation", 
                     ApplicationStatus.REJECTED, applications.get(0).getStatus());
    }
    
    @Test
    public void testCase2_CancelApprovedRequest() {
        // Setup: Customer "C302" with approved application for "SmartGrid"
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
        
        Application app = customer.getApplications().get(0);
        boolean approved = app.approve();
        assertTrue("Application should be approved successfully", approved);
        
        // Test: Try to cancel approved application
        boolean result = customer.cancelApplication("SmartGrid");
        
        // Verify: Cancellation should fail for approved application
        assertFalse("Approved application should not be cancellable", result);
        
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
        
        company.setName("MedLife");
        company.setEmail("medlife@health.com");
        
        // Create application and reject it
        boolean applicationCreated = customer.createApplication(company, 20, 1000.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        Application app = customer.getApplications().get(0);
        boolean rejected = app.reject();
        assertTrue("Application should be rejected successfully", rejected);
        
        // Test: Try to cancel rejected application
        boolean result = customer.cancelApplication("MedLife");
        
        // Verify: Cancellation should fail for rejected application
        assertFalse("Rejected application should not be cancellable", result);
        
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
        
        // Create application for a different company
        company.setName("SomeOtherCompany");
        company.setEmail("other@company.com");
        boolean applicationCreated = customer.createApplication(company, 10, 500.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Test: Try to cancel application for non-existent company
        boolean result = customer.cancelApplication("UnknownCorp");
        
        // Verify: Cancellation should fail when no application exists for specified company
        assertFalse("Cancellation should fail for non-existent company", result);
        
        // Verify existing application remains unchanged
        List<Application> applications = customer.getApplications();
        assertEquals("Should have one application", 1, applications.size());
        assertEquals("Existing application status should remain PENDING", 
                     ApplicationStatus.PENDING, applications.get(0).getStatus());
    }
    
    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Setup: Customer "C306" with two pending applications
        customer.setName("Mia");
        customer.setSurname("Anderson");
        customer.setEmail("m.anderson@example.com");
        customer.setTelephone("555-6060");
        customer.setCanApplyForIPO(true);
        
        // Create first application for "UrbanTech"
        Company company1 = new Company();
        company1.setName("UrbanTech");
        company1.setEmail("urbantech@innovate.com");
        boolean app1Created = customer.createApplication(company1, 25, 1250.0, document);
        assertTrue("First application should be created successfully", app1Created);
        
        // Create second application for "AgroSeed"
        Company company2 = new Company();
        company2.setName("AgroSeed");
        company2.setEmail("agroseed@agriculture.com");
        boolean app2Created = customer.createApplication(company2, 40, 2000.0, document);
        assertTrue("Second application should be created successfully", app2Created);
        
        // Verify both applications are pending
        List<Application> applications = customer.getApplications();
        assertEquals("Should have two applications", 2, applications.size());
        assertEquals("First application should be PENDING", 
                     ApplicationStatus.PENDING, applications.get(0).getStatus());
        assertEquals("Second application should be PENDING", 
                     ApplicationStatus.PENDING, applications.get(1).getStatus());
        
        // Test: Cancel "UrbanTech" application
        boolean result = customer.cancelApplication("UrbanTech");
        
        // Verify: Cancellation should succeed for "UrbanTech"
        assertTrue("UrbanTech application should be cancelled successfully", result);
        
        // Verify "UrbanTech" application is rejected (cancelled) and "AgroSeed" remains pending
        applications = customer.getApplications();
        assertEquals("Should still have two applications", 2, applications.size());
        
        // Find UrbanTech application
        Application urbanTechApp = null;
        Application agroSeedApp = null;
        for (Application app : applications) {
            if ("UrbanTech".equals(app.getCompany().getName())) {
                urbanTechApp = app;
            } else if ("AgroSeed".equals(app.getCompany().getName())) {
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