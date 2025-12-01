import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    private IPOService ipoService;
    
    @Before
    public void setUp() {
        ipoService = new IPOService();
    }
    
    @Test
    public void testCase1_cancelPendingRequest() {
        // Setup: Create customer C301 with pending EcoWave application
        Customer customer = new Customer();
        customer.setName("Benjamin");
        customer.setSurname("Taylor");
        customer.setEmail("b.taylor@example.com");
        customer.setTelephoneNumber("555-1010");
        
        IPOApplication pendingApp = new IPOApplication();
        pendingApp.setCompanyName("EcoWave");
        pendingApp.setNumberOfShares(15);
        pendingApp.setAmount(750.0);
        pendingApp.setDocument("EW-2024-03");
        pendingApp.setStatus(IPOApplicationStatus.PENDING);
        
        customer.getIpoApplications().add(pendingApp);
        
        // Test: Cancel the pending EcoWave application
        boolean result = ipoService.cancelApplication(customer, "EcoWave");
        
        // Verify: Cancellation should succeed
        assertTrue("Pending application should be cancellable", result);
    }
    
    @Test
    public void testCase2_cancelApprovedRequest() {
        // Setup: Create customer C302 with approved SmartGrid application
        Customer customer = new Customer();
        customer.setName("Charlotte");
        customer.setSurname("Lee");
        customer.setEmail("c.lee@example.com");
        customer.setTelephoneNumber("555-2020");
        
        IPOApplication approvedApp = new IPOApplication();
        approvedApp.setCompanyName("SmartGrid");
        approvedApp.setNumberOfShares(30);
        approvedApp.setAmount(3000.0);
        approvedApp.setDocument("SG-2024-01");
        approvedApp.setStatus(IPOApplicationStatus.APPROVED);
        
        customer.getIpoApplications().add(approvedApp);
        
        // Test: Try to cancel the approved SmartGrid application
        boolean result = ipoService.cancelApplication(customer, "SmartGrid");
        
        // Verify: Cancellation should fail for approved applications
        assertFalse("Approved application should not be cancellable", result);
    }
    
    @Test
    public void testCase3_cancelRejectedRequest() {
        // Setup: Create customer C303 with rejected MedLife application
        Customer customer = new Customer();
        customer.setName("Lucas");
        customer.setSurname("Martin");
        customer.setEmail("l.martin@example.com");
        customer.setTelephoneNumber("555-3030");
        
        IPOApplication rejectedApp = new IPOApplication();
        rejectedApp.setCompanyName("MedLife");
        rejectedApp.setNumberOfShares(20);
        rejectedApp.setAmount(1000.0);
        rejectedApp.setDocument("SG-2024-03");
        rejectedApp.setStatus(IPOApplicationStatus.REJECTED);
        
        customer.getIpoApplications().add(rejectedApp);
        
        // Test: Try to cancel the rejected MedLife application
        boolean result = ipoService.cancelApplication(customer, "MedLife");
        
        // Verify: Cancellation should fail for rejected applications
        assertFalse("Rejected application should not be cancellable", result);
    }
    
    @Test
    public void testCase4_cancelNonexistentCompany() {
        // Setup: Create customer C304 with no applications
        Customer customer = new Customer();
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmail("a.clark@example.com");
        customer.setTelephoneNumber("555-4040");
        
        // Test: Try to cancel application for non-existent company
        boolean result = ipoService.cancelApplication(customer, "UnknownCorp");
        
        // Verify: Cancellation should fail when no application exists
        assertFalse("Cancellation should fail for non-existent application", result);
    }
    
    @Test
    public void testCase5_cancelAfterPriorCancellation() {
        // Setup: Create customer C306 with two pending applications
        Customer customer = new Customer();
        customer.setName("Mia");
        customer.setSurname("Anderson");
        customer.setEmail("m.anderson@example.com");
        customer.setTelephoneNumber("555-6060");
        
        IPOApplication urbanTechApp = new IPOApplication();
        urbanTechApp.setCompanyName("UrbanTech");
        urbanTechApp.setNumberOfShares(25);
        urbanTechApp.setAmount(1250.0);
        urbanTechApp.setDocument("SG-2024-005");
        urbanTechApp.setStatus(IPOApplicationStatus.PENDING);
        
        IPOApplication agroSeedApp = new IPOApplication();
        agroSeedApp.setCompanyName("AgroSeed");
        agroSeedApp.setNumberOfShares(40);
        agroSeedApp.setAmount(2000.0);
        agroSeedApp.setDocument("SG-2024-006");
        agroSeedApp.setStatus(IPOApplicationStatus.PENDING);
        
        customer.getIpoApplications().add(urbanTechApp);
        customer.getIpoApplications().add(agroSeedApp);
        
        // Test: Cancel the UrbanTech application
        boolean result = ipoService.cancelApplication(customer, "UrbanTech");
        
        // Verify: UrbanTech cancellation should succeed
        assertTrue("UrbanTech application should be cancellable", result);
        
        // Verify: AgroSeed application should remain unaffected (still present)
        boolean agroSeedStillExists = false;
        for (IPOApplication app : customer.getIpoApplications()) {
            if ("AgroSeed".equals(app.getCompanyName())) {
                agroSeedStillExists = true;
                break;
            }
        }
        assertTrue("AgroSeed application should remain after UrbanTech cancellation", agroSeedStillExists);
    }
}