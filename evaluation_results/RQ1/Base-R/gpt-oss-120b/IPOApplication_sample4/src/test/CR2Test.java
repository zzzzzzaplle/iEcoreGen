import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    private IPOSystem ipoSystem;
    private Customer customerC007;
    private Customer customerC008;
    private Customer customerC009;
    private Customer customerC010;
    private Customer customerC011;
    private Company companySolarMax;
    private Company companyHealthPlus;
    private Company companyHealth;
    private Company companyCloud;
    private Document documentS;
    private Document documentH;
    
    @Before
    public void setUp() {
        ipoSystem = new IPOSystem();
        
        // Create companies
        companySolarMax = new Company();
        companySolarMax.setName("SolarMax");
        
        companyHealthPlus = new Company();
        companyHealthPlus.setName("HealthPlus");
        
        companyHealth = new Company();
        companyHealth.setName("Health");
        
        companyCloud = new Company();
        companyCloud.setName("Cloud");
        
        // Create documents
        documentS = new Document();
        documentS.setFileName("S");
        
        documentH = new Document();
        documentH.setFileName("H");
        
        // Create and setup customers
        customerC007 = new Customer();
        customerC007.setName("Michael");
        customerC007.setSurname("Brown");
        customerC007.setEmail("m.brown@example.com");
        customerC007.setTelephone("555-1122");
        customerC007.setEligible(true);
        
        customerC008 = new Customer();
        customerC008.setName("Olivia");
        customerC008.setSurname("Lee");
        customerC008.setEmail("olivia.l@example.com");
        customerC008.setTelephone("555-3344");
        customerC008.setEligible(true);
        
        customerC009 = new Customer();
        customerC009.setName("Daniel");
        customerC009.setSurname("Kim");
        customerC009.setEmail("d.kim@example.com");
        customerC009.setTelephone("555-5566");
        customerC009.setEligible(true);
        
        customerC010 = new Customer();
        customerC010.setName("Sophie");
        customerC010.setSurname("Zhang");
        customerC010.setEmail("s.zhang@example.com");
        customerC010.setTelephone("555-7788");
        customerC010.setEligible(true);
        
        customerC011 = new Customer();
        customerC011.setName("William");
        customerC011.setSurname("Wang");
        customerC011.setEmail("will.w@example.com");
        customerC011.setTelephone("555-9900");
        customerC011.setEligible(false); // Ineligible customer
    }
    
    @Test
    public void testCase1_ApprovePendingRequest() {
        // Setup: Create pending application for SolarMax
        ipoSystem.createIPOApplication(customerC007, companySolarMax, 10, 200.0, documentS);
        
        // Test: Approve the pending application
        boolean result = ipoSystem.approveApplication(customerC007, "SolarMax");
        
        // Verify: Approval should succeed
        assertTrue("Application should be approved successfully", result);
        
        // Verify: Application status should be APPROVED
        IPOApplication app = findApplicationByCompany(customerC007, "SolarMax");
        assertNotNull("Application should exist", app);
        assertEquals("Application status should be APPROVED", ApplicationStatus.APPROVED, app.getStatus());
    }
    
    @Test
    public void testCase2_RejectPendingRequest() {
        // Setup: Create pending application for HealthPlus
        ipoSystem.createIPOApplication(customerC008, companyHealthPlus, 10, 5000.0, documentH);
        
        // Test: Reject the pending application
        boolean result = ipoSystem.rejectApplication(customerC008, "HealthPlus");
        
        // Verify: Rejection should succeed
        assertTrue("Application should be rejected successfully", result);
        
        // Verify: Application status should be REJECTED
        IPOApplication app = findApplicationByCompany(customerC008, "HealthPlus");
        assertNotNull("Application should exist", app);
        assertEquals("Application status should be REJECTED", ApplicationStatus.REJECTED, app.getStatus());
        
        // Verify: Failed attempts should be incremented
        assertEquals("Failed attempts should be incremented", 1, customerC008.getFailedAttempts());
    }
    
    @Test
    public void testCase3_ApproveAlreadyApprovedRecord() {
        // Setup: Create and approve application for HealthPlus
        ipoSystem.createIPOApplication(customerC009, companyHealthPlus, 10, 5000.0, documentH);
        ipoSystem.approveApplication(customerC009, "HealthPlus");
        
        // Test: Attempt to approve the already approved application
        boolean result = ipoSystem.approveApplication(customerC009, "HealthPlus");
        
        // Verify: Approval should fail for already approved application
        assertFalse("Should not be able to approve already approved application", result);
        
        // Verify: Application status remains APPROVED
        IPOApplication app = findApplicationByCompany(customerC009, "HealthPlus");
        assertNotNull("Application should exist", app);
        assertEquals("Application status should remain APPROVED", ApplicationStatus.APPROVED, app.getStatus());
    }
    
    @Test
    public void testCase4_RejectAlreadyRejectedRecord() {
        // Setup: Create and reject application for Health
        ipoSystem.createIPOApplication(customerC010, companyHealth, 10, 5000.0, documentH);
        ipoSystem.rejectApplication(customerC010, "Health");
        
        // Test: Attempt to reject the already rejected application
        boolean result = ipoSystem.rejectApplication(customerC010, "Health");
        
        // Verify: Rejection should fail for already rejected application
        assertFalse("Should not be able to reject already rejected application", result);
        
        // Verify: Application status remains REJECTED
        IPOApplication app = findApplicationByCompany(customerC010, "Health");
        assertNotNull("Application should exist", app);
        assertEquals("Application status should remain REJECTED", ApplicationStatus.REJECTED, app.getStatus());
    }
    
    @Test
    public void testCase5_ApproveRecordTiedToIneligibleCustomer() {
        // Setup: Create pending application for Cloud (customer is ineligible)
        ipoSystem.createIPOApplication(customerC011, companyCloud, 10, 5000.0, documentH);
        
        // Test: Attempt to approve application for ineligible customer
        boolean result = ipoSystem.approveApplication(customerC011, "Cloud");
        
        // Verify: Approval should fail due to ineligible customer
        assertFalse("Should not be able to approve application for ineligible customer", result);
        
        // Verify: Application status remains PENDING
        IPOApplication app = findApplicationByCompany(customerC011, "Cloud");
        assertNotNull("Application should exist", app);
        assertEquals("Application status should remain PENDING", ApplicationStatus.PENDING, app.getStatus());
    }
    
    /**
     * Helper method to find application by company name for a customer
     */
    private IPOApplication findApplicationByCompany(Customer customer, String companyName) {
        for (IPOApplication app : customer.getApplications()) {
            if (app.getCompany().getName().equalsIgnoreCase(companyName)) {
                return app;
            }
        }
        return null;
    }
}