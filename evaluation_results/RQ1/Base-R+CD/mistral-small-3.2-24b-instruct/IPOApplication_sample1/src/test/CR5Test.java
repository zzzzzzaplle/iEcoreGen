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
        // Setup: Customer "C301" has a pending application for "EcoWave"
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
        
        // Test: Cancel the pending application
        boolean result = customer.cancelApplication("EcoWave");
        
        // Verify: Cancellation should succeed
        assertTrue("Cancellation of pending application should return true", result);
    }
    
    @Test
    public void testCase2_CancelApprovedRequest() {
        // Setup: Customer "C302" has an approved application for "SmartGrid"
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
        
        // Get the application and approve it
        Application application = customer.getApplications().get(0);
        boolean approved = application.approve();
        assertTrue("Application should be approved successfully", approved);
        
        // Test: Try to cancel the approved application
        boolean result = customer.cancelApplication("SmartGrid");
        
        // Verify: Cancellation should fail
        assertFalse("Cancellation of approved application should return false", result);
    }
    
    @Test
    public void testCase3_CancelRejectedRequest() {
        // Setup: Customer "C303" has a rejected application for "MedLife"
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
        
        // Get the application and reject it
        Application application = customer.getApplications().get(0);
        boolean rejected = application.reject();
        assertTrue("Application should be rejected successfully", rejected);
        
        // Test: Try to cancel the rejected application
        boolean result = customer.cancelApplication("MedLife");
        
        // Verify: Cancellation should fail
        assertFalse("Cancellation of rejected application should return false", result);
    }
    
    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Setup: Customer "C304" has no application for "UnknownCorp"
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmail("a.clark@example.com");
        customer.setTelephone("555-4040");
        customer.setCanApplyForIPO(true);
        
        // Test: Try to cancel application for non-existent company
        boolean result = customer.cancelApplication("UnknownCorp");
        
        // Verify: Cancellation should fail
        assertFalse("Cancellation of non-existent application should return false", result);
    }
    
    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Setup: Customer "C306" has two pending applications
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
        agroSeed.setEmail("agroseed@business.com");
        
        boolean app2Created = customer.createApplication(agroSeed, 40, 2000.0, document);
        assertTrue("Second application should be created successfully", app2Created);
        
        // Verify both applications exist and are pending
        assertEquals("Customer should have 2 applications", 2, customer.getApplications().size());
        
        // Test: Cancel "UrbanTech" application
        boolean result = customer.cancelApplication("UrbanTech");
        
        // Verify: Cancellation should succeed
        assertTrue("Cancellation of UrbanTech application should return true", result);
        
        // Verify: "UrbanTech" application is now rejected (cancelled)
        boolean urbanTechCancelled = false;
        boolean agroSeedStillPending = false;
        
        for (Application app : customer.getApplications()) {
            if (app.getCompany().getName().equals("UrbanTech")) {
                urbanTechCancelled = (app.getStatus() == ApplicationStatus.REJECTED);
            }
            if (app.getCompany().getName().equals("AgroSeed")) {
                agroSeedStillPending = (app.getStatus() == ApplicationStatus.PENDING);
            }
        }
        
        assertTrue("UrbanTech application should be rejected after cancellation", urbanTechCancelled);
        assertTrue("AgroSeed application should remain pending", agroSeedStillPending);
    }
}