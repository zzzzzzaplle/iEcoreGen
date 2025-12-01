import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Arrays;

public class CR5Test {
    
    private IPOService ipoService;
    private Customer customerC301;
    private Customer customerC302;
    private Customer customerC303;
    private Customer customerC304;
    private Customer customerC306;
    private Company ecoWave;
    private Company smartGrid;
    private Company medLife;
    private Company urbanTech;
    private Company agroSeed;
    private IPOApplication app4001;
    private IPOApplication app4002;
    private IPOApplication app4003;
    private IPOApplication app4005;
    private IPOApplication app4006;
    
    @Before
    public void setUp() {
        ipoService = new IPOService();
        
        // Initialize companies
        ecoWave = new Company();
        ecoWave.setName("EcoWave");
        
        smartGrid = new Company();
        smartGrid.setName("SmartGrid");
        
        medLife = new Company();
        medLife.setName("MedLife");
        
        urbanTech = new Company();
        urbanTech.setName("UrbanTech");
        
        agroSeed = new Company();
        agroSeed.setName("AgroSeed");
        
        // Initialize customer C301
        customerC301 = new Customer();
        customerC301.setName("Benjamin");
        customerC301.setSurname("Taylor");
        customerC301.setEmail("b.taylor@example.com");
        customerC301.setTelephone("555-1010");
        customerC301.setRetail(true);
        customerC301.setLocked(false);
        customerC301.setFailedAttempts(0);
        
        // Initialize customer C302
        customerC302 = new Customer();
        customerC302.setName("Charlotte");
        customerC302.setSurname("Lee");
        customerC302.setEmail("c.lee@example.com");
        customerC302.setTelephone("555-2020");
        customerC302.setRetail(true);
        customerC302.setLocked(false);
        customerC302.setFailedAttempts(0);
        
        // Initialize customer C303
        customerC303 = new Customer();
        customerC303.setName("Lucas");
        customerC303.setSurname("Martin");
        customerC303.setEmail("l.martin@example.com");
        customerC303.setTelephone("555-3030");
        customerC303.setRetail(true);
        customerC303.setLocked(false);
        customerC303.setFailedAttempts(0);
        
        // Initialize customer C304
        customerC304 = new Customer();
        customerC304.setName("Amelia");
        customerC304.setSurname("Clark");
        customerC304.setEmail("a.clark@example.com");
        customerC304.setTelephone("555-4040");
        customerC304.setRetail(true);
        customerC304.setLocked(false);
        customerC304.setFailedAttempts(0);
        
        // Initialize customer C306
        customerC306 = new Customer();
        customerC306.setName("Mia");
        customerC306.setSurname("Anderson");
        customerC306.setEmail("m.anderson@example.com");
        customerC306.setTelephone("555-6060");
        customerC306.setRetail(true);
        customerC306.setLocked(false);
        customerC306.setFailedAttempts(0);
        
        // Initialize applications
        app4001 = new IPOApplication();
        app4001.setCustomer(customerC301);
        app4001.setCompany(ecoWave);
        app4001.setNumberOfShares(15);
        app4001.setAmount(750.0);
        app4001.setDocument("EW-2024-03".getBytes());
        app4001.setStatus(ApplicationStatus.PENDING);
        
        app4002 = new IPOApplication();
        app4002.setCustomer(customerC302);
        app4002.setCompany(smartGrid);
        app4002.setNumberOfShares(30);
        app4002.setAmount(3000.0);
        app4002.setDocument("SG-2024-01".getBytes());
        app4002.setStatus(ApplicationStatus.APPROVED);
        
        app4003 = new IPOApplication();
        app4003.setCustomer(customerC303);
        app4003.setCompany(medLife);
        app4003.setNumberOfShares(20);
        app4003.setAmount(1000.0);
        app4003.setDocument("SG-2024-03".getBytes());
        app4003.setStatus(ApplicationStatus.REJECTED);
        
        app4005 = new IPOApplication();
        app4005.setCustomer(customerC306);
        app4005.setCompany(urbanTech);
        app4005.setNumberOfShares(25);
        app4005.setAmount(1250.0);
        app4005.setDocument("SG-2024-005".getBytes());
        app4005.setStatus(ApplicationStatus.PENDING);
        
        app4006 = new IPOApplication();
        app4006.setCustomer(customerC306);
        app4006.setCompany(agroSeed);
        app4006.setNumberOfShares(40);
        app4006.setAmount(2000.0);
        app4006.setDocument("SG-2024-006".getBytes());
        app4006.setStatus(ApplicationStatus.PENDING);
        
        // Set up customer applications
        customerC301.addApplication(app4001);
        customerC302.addApplication(app4002);
        customerC303.addApplication(app4003);
        customerC306.addApplication(app4005);
        customerC306.addApplication(app4006);
    }
    
    @Test
    public void testCase1_CancelStillPendingRequest() {
        // Test Case 1: "Cancel still-pending request"
        // Customer "C301" requests cancellation for "EcoWave"
        // Expected Output: True
        
        boolean result = ipoService.cancelPendingApplication(customerC301, "EcoWave");
        
        assertTrue("Should successfully cancel pending application", result);
        assertEquals("Customer should have no applications after cancellation", 
                     0, customerC301.getApplications().size());
    }
    
    @Test
    public void testCase2_CancelApprovedRequest() {
        // Test Case 2: "Cancel approved request"
        // Customer "C302" tries to cancel IPO for "SmartGrid"
        // Expected Output: False (Cannot cancel approved applications)
        
        boolean result = ipoService.cancelPendingApplication(customerC302, "SmartGrid");
        
        assertFalse("Should not be able to cancel approved application", result);
        assertEquals("Customer should still have 1 application", 
                     1, customerC302.getApplications().size());
        assertEquals("Application status should remain APPROVED", 
                     ApplicationStatus.APPROVED, customerC302.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase3_CancelRejectedRequest() {
        // Test Case 3: "Cancel rejected request"
        // Customer "C303" tries to cancel the filing for "MedLife"
        // Expected Output: False (Application already finalized)
        
        boolean result = ipoService.cancelPendingApplication(customerC303, "MedLife");
        
        assertFalse("Should not be able to cancel rejected application", result);
        assertEquals("Customer should still have 1 application", 
                     1, customerC303.getApplications().size());
        assertEquals("Application status should remain REJECTED", 
                     ApplicationStatus.REJECTED, customerC303.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Test Case 4: "Cancel nonexistent company"
        // Customer "C304" requests cancellation for "UnknownCorp"
        // Expected Output: False (No application found for specified company)
        
        boolean result = ipoService.cancelPendingApplication(customerC304, "UnknownCorp");
        
        assertFalse("Should return false when no application exists for specified company", result);
        assertEquals("Customer should still have no applications", 
                     0, customerC304.getApplications().size());
    }
    
    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Test Case 5: "Cancel after prior cancellation"
        // Customer "C306" cancels "UrbanTech" filing
        // Expected Output: True ("UrbanTech" application canceled, "AgroSeed" remains unaffected)
        
        int initialCount = customerC306.getApplications().size();
        boolean result = ipoService.cancelPendingApplication(customerC306, "UrbanTech");
        
        assertTrue("Should successfully cancel pending application", result);
        assertEquals("Customer should have one less application after cancellation", 
                     initialCount - 1, customerC306.getApplications().size());
        
        // Verify only the UrbanTech application was removed
        boolean urbanTechFound = false;
        boolean agroSeedFound = false;
        
        for (IPOApplication app : customerC306.getApplications()) {
            if (app.getCompany().getName().equals("UrbanTech")) {
                urbanTechFound = true;
            }
            if (app.getCompany().getName().equals("AgroSeed")) {
                agroSeedFound = true;
            }
        }
        
        assertFalse("UrbanTech application should be removed", urbanTechFound);
        assertTrue("AgroSeed application should remain", agroSeedFound);
    }
}