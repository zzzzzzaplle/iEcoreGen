import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    private Customer customer;
    private Company company;
    private Document document;
    
    @Before
    public void setUp() {
        document = new Document();
    }
    
    @Test
    public void testCase1_cancelPendingRequest() {
        // Setup: Customer "C301" with pending application for "EcoWave"
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
        
        // Verify application is in pending status
        assertEquals("Application should be pending", 0, customer.getApplicationCount());
        
        // Test: Cancel the pending application
        boolean cancellationResult = customer.cancelApplication("EcoWave");
        
        // Verify: Cancellation should succeed
        assertTrue("Cancellation of pending application should return true", cancellationResult);
    }
    
    @Test
    public void testCase2_cancelApprovedRequest() {
        // Setup: Customer "C302" with approved application for "SmartGrid"
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
        
        // Get the application and approve it
        Application app = customer.getApplications().get(0);
        boolean approvalResult = app.approve();
        assertTrue("Application should be approved successfully", approvalResult);
        
        // Test: Try to cancel the approved application
        boolean cancellationResult = customer.cancelApplication("SmartGrid");
        
        // Verify: Cancellation should fail
        assertFalse("Cancellation of approved application should return false", cancellationResult);
    }
    
    @Test
    public void testCase3_cancelRejectedRequest() {
        // Setup: Customer "C303" with rejected application for "MedLife"
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
        
        // Get the application and reject it
        Application app = customer.getApplications().get(0);
        boolean rejectionResult = app.reject();
        assertTrue("Application should be rejected successfully", rejectionResult);
        
        // Test: Try to cancel the rejected application
        boolean cancellationResult = customer.cancelApplication("MedLife");
        
        // Verify: Cancellation should fail
        assertFalse("Cancellation of rejected application should return false", cancellationResult);
    }
    
    @Test
    public void testCase4_cancelNonexistentCompany() {
        // Setup: Customer "C304" with no application for "UnknownCorp"
        customer = new Customer();
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmail("a.clark@example.com");
        customer.setTelephone("555-4040");
        customer.setCanApplyForIPO(true);
        
        // No applications created for UnknownCorp
        
        // Test: Try to cancel application for non-existent company
        boolean cancellationResult = customer.cancelApplication("UnknownCorp");
        
        // Verify: Cancellation should fail
        assertFalse("Cancellation for non-existent company should return false", cancellationResult);
    }
    
    @Test
    public void testCase5_cancelAfterPriorCancellation() {
        // Setup: Customer "C306" with two pending applications
        customer = new Customer();
        customer.setName("Mia");
        customer.setSurname("Anderson");
        customer.setEmail("m.anderson@example.com");
        customer.setTelephone("555-6060");
        customer.setCanApplyForIPO(true);
        
        // Create UrbanTech application
        Company urbanTech = new Company();
        urbanTech.setName("UrbanTech");
        urbanTech.setEmail("urbantech@innovate.com");
        boolean urbanTechCreated = customer.createApplication(urbanTech, 25, 1250.0, document);
        assertTrue("UrbanTech application should be created successfully", urbanTechCreated);
        
        // Create AgroSeed application
        Company agroSeed = new Company();
        agroSeed.setName("AgroSeed");
        agroSeed.setEmail("agroseed@business.com");
        boolean agroSeedCreated = customer.createApplication(agroSeed, 40, 2000.0, document);
        assertTrue("AgroSeed application should be created successfully", agroSeedCreated);
        
        // Verify both applications are pending
        assertEquals("Both applications should be pending", 0, customer.getApplicationCount());
        assertEquals("Should have 2 applications total", 2, customer.getApplications().size());
        
        // Test: Cancel UrbanTech application
        boolean cancellationResult = customer.cancelApplication("UrbanTech");
        
        // Verify: UrbanTech cancellation should succeed
        assertTrue("Cancellation of UrbanTech application should return true", cancellationResult);
        
        // Verify: AgroSeed application should remain unaffected (still pending)
        boolean agroSeedStillExists = false;
        for (Application app : customer.getApplications()) {
            if (app.getCompany().getName().equals("AgroSeed") && 
                app.getStatus() == ApplicationStatus.PENDING) {
                agroSeedStillExists = true;
                break;
            }
        }
        assertTrue("AgroSeed application should remain pending", agroSeedStillExists);
    }
}