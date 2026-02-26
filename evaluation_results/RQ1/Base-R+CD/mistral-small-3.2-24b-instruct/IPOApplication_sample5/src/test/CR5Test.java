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
        boolean cancellationResult = customer.cancelApplication("EcoWave");
        
        // Verify: Cancellation should succeed
        assertTrue("Cancellation of pending application should return true", cancellationResult);
        
        // Verify application status changed to REJECTED
        assertEquals("Application count should be 1", 1, customer.getApplicationCount());
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
        
        Application application = customer.getApplications().get(0);
        boolean approvalResult = application.approve();
        assertTrue("Application should be approved successfully", approvalResult);
        
        // Test: Try to cancel approved application
        boolean cancellationResult = customer.cancelApplication("SmartGrid");
        
        // Verify: Cancellation should fail for approved application
        assertFalse("Cancellation of approved application should return false", cancellationResult);
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
        
        Application application = customer.getApplications().get(0);
        boolean rejectionResult = application.reject();
        assertTrue("Application should be rejected successfully", rejectionResult);
        
        // Test: Try to cancel rejected application
        boolean cancellationResult = customer.cancelApplication("MedLife");
        
        // Verify: Cancellation should fail for rejected application
        assertFalse("Cancellation of rejected application should return false", cancellationResult);
    }
    
    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Setup: Customer "C304" with no application for "UnknownCorp"
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmail("a.clark@example.com");
        customer.setTelephone("555-4040");
        customer.setCanApplyForIPO(true);
        
        // Create an application for a different company
        company.setName("SomeOtherCompany");
        company.setEmail("other@company.com");
        
        boolean applicationCreated = customer.createApplication(company, 10, 500.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Test: Try to cancel application for non-existent company "UnknownCorp"
        boolean cancellationResult = customer.cancelApplication("UnknownCorp");
        
        // Verify: Cancellation should fail when no application exists for specified company
        assertFalse("Cancellation for non-existent company should return false", cancellationResult);
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
        Company urbanTech = new Company();
        urbanTech.setName("UrbanTech");
        urbanTech.setEmail("urbantech@innovate.com");
        
        boolean app1Created = customer.createApplication(urbanTech, 25, 1250.0, document);
        assertTrue("First application should be created successfully", app1Created);
        
        // Create second application for "AgroSeed"
        Company agroSeed = new Company();
        agroSeed.setName("AgroSeed");
        agroSeed.setEmail("agroseed@agriculture.com");
        
        boolean app2Created = customer.createApplication(agroSeed, 40, 2000.0, document);
        assertTrue("Second application should be created successfully", app2Created);
        
        // Verify both applications are pending
        assertEquals("Should have 2 applications", 2, customer.getApplications().size());
        assertEquals("Application count should be 0 since both are pending", 0, customer.getApplicationCount());
        
        // Test: Cancel "UrbanTech" application
        boolean cancellationResult = customer.cancelApplication("UrbanTech");
        
        // Verify: Cancellation should succeed
        assertTrue("Cancellation of UrbanTech application should return true", cancellationResult);
        
        // Verify only "UrbanTech" application was canceled, "AgroSeed" remains pending
        assertEquals("Should still have 2 applications", 2, customer.getApplications().size());
        assertEquals("Application count should be 1 after cancellation", 1, customer.getApplicationCount());
        
        // Verify "UrbanTech" application status is REJECTED
        Application urbanTechApp = customer.getApplications().stream()
            .filter(app -> app.getCompany().getName().equals("UrbanTech"))
            .findFirst()
            .orElse(null);
        assertNotNull("UrbanTech application should exist", urbanTechApp);
        assertEquals("UrbanTech application should be REJECTED", ApplicationStatus.REJECTED, urbanTechApp.getStatus());
        
        // Verify "AgroSeed" application status is still PENDING
        Application agroSeedApp = customer.getApplications().stream()
            .filter(app -> app.getCompany().getName().equals("AgroSeed"))
            .findFirst()
            .orElse(null);
        assertNotNull("AgroSeed application should exist", agroSeedApp);
        assertEquals("AgroSeed application should remain PENDING", ApplicationStatus.PENDING, agroSeedApp.getStatus());
    }
}