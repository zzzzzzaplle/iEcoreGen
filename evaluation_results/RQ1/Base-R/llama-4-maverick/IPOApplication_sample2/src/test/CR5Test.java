import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    private IPOApplicationService service;
    
    @Before
    public void setUp() {
        service = new IPOApplicationService();
    }
    
    @Test
    public void testCase1_cancelPendingRequest() {
        // Setup: Create customer C301 with pending application for EcoWave
        Customer customer = new Customer();
        customer.setName("Benjamin");
        customer.setSurname("Taylor");
        customer.setEmail("b.taylor@example.com");
        customer.setTelephoneNumber("555-1010");
        
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompanyName("EcoWave");
        application.setNumberOfShares(15);
        application.setAmount(750.0);
        application.setDocument("EW-2024-03");
        application.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(application);
        
        // Test: Cancel pending application for EcoWave
        boolean result = service.cancelPendingApplication(customer, "EcoWave");
        
        // Verify: Cancellation should succeed
        assertTrue("Pending application should be cancellable", result);
        assertEquals("Application status should be REJECTED after cancellation", 
                     ApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase2_cancelApprovedRequest() {
        // Setup: Create customer C302 with approved application for SmartGrid
        Customer customer = new Customer();
        customer.setName("Charlotte");
        customer.setSurname("Lee");
        customer.setEmail("c.lee@example.com");
        customer.setTelephoneNumber("555-2020");
        
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompanyName("SmartGrid");
        application.setNumberOfShares(30);
        application.setAmount(3000.0);
        application.setDocument("SG-2024-01");
        application.setStatus(ApplicationStatus.APPROVED);
        
        customer.getApplications().add(application);
        
        // Test: Try to cancel approved application for SmartGrid
        boolean result = service.cancelPendingApplication(customer, "SmartGrid");
        
        // Verify: Cancellation should fail (cannot cancel approved applications)
        assertFalse("Approved application should not be cancellable", result);
        assertEquals("Application status should remain APPROVED", 
                     ApplicationStatus.APPROVED, application.getStatus());
    }
    
    @Test
    public void testCase3_cancelRejectedRequest() {
        // Setup: Create customer C303 with rejected application for MedLife
        Customer customer = new Customer();
        customer.setName("Lucas");
        customer.setSurname("Martin");
        customer.setEmail("l.martin@example.com");
        customer.setTelephoneNumber("555-3030");
        
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompanyName("MedLife");
        application.setNumberOfShares(20);
        application.setAmount(1000.0);
        application.setDocument("SG-2024-03");
        application.setStatus(ApplicationStatus.REJECTED);
        
        customer.getApplications().add(application);
        
        // Test: Try to cancel rejected application for MedLife
        boolean result = service.cancelPendingApplication(customer, "MedLife");
        
        // Verify: Cancellation should fail (application already finalized)
        assertFalse("Rejected application should not be cancellable", result);
        assertEquals("Application status should remain REJECTED", 
                     ApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase4_cancelNonexistentCompany() {
        // Setup: Create customer C304 with no applications
        Customer customer = new Customer();
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmail("a.clark@example.com");
        customer.setTelephoneNumber("555-4040");
        
        // Test: Try to cancel application for non-existent company UnknownCorp
        boolean result = service.cancelPendingApplication(customer, "UnknownCorp");
        
        // Verify: Cancellation should fail (no application found)
        assertFalse("Cancellation should fail for non-existent application", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase5_cancelAfterPriorCancellation() {
        // Setup: Create customer C306 with two pending applications
        Customer customer = new Customer();
        customer.setName("Mia");
        customer.setSurname("Anderson");
        customer.setEmail("m.anderson@example.com");
        customer.setTelephoneNumber("555-6060");
        
        // Create UrbanTech application
        IPOApplication urbanTechApp = new IPOApplication();
        urbanTechApp.setCustomer(customer);
        urbanTechApp.setCompanyName("UrbanTech");
        urbanTechApp.setNumberOfShares(25);
        urbanTechApp.setAmount(1250.0);
        urbanTechApp.setDocument("SG-2024-005");
        urbanTechApp.setStatus(ApplicationStatus.PENDING);
        
        // Create AgroSeed application
        IPOApplication agroSeedApp = new IPOApplication();
        agroSeedApp.setCustomer(customer);
        agroSeedApp.setCompanyName("AgroSeed");
        agroSeedApp.setNumberOfShares(40);
        agroSeedApp.setAmount(2000.0);
        agroSeedApp.setDocument("SG-2024-006");
        agroSeedApp.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(urbanTechApp);
        customer.getApplications().add(agroSeedApp);
        
        // Test: Cancel UrbanTech application
        boolean result = service.cancelPendingApplication(customer, "UrbanTech");
        
        // Verify: UrbanTech cancellation should succeed
        assertTrue("UrbanTech application should be cancellable", result);
        assertEquals("UrbanTech status should be REJECTED", 
                     ApplicationStatus.REJECTED, urbanTechApp.getStatus());
        assertEquals("AgroSeed status should remain PENDING", 
                     ApplicationStatus.PENDING, agroSeedApp.getStatus());
        assertEquals("Customer should have 2 applications total", 2, customer.getApplications().size());
    }
}