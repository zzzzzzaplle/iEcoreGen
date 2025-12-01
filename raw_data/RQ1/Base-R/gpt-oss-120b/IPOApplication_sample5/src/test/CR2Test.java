import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    private Customer customer;
    private Company solarMax;
    private Company healthPlus;
    private Company health;
    private Company cloud;
    private Document documentS;
    private Document documentH;
    private Document documentCloud;
    private IPOApplication appSolarMax;
    private IPOApplication appHealthPlus;
    private IPOApplication appHealth;
    private IPOApplication appCloud;
    
    @Before
    public void setUp() {
        // Create companies
        solarMax = new Company("SolarMax", "solarmax@gmail.com");
        healthPlus = new Company("HealthPlus", "healthplus@gmail.com");
        health = new Company("Health", "health@gmail.com");
        cloud = new Company("Cloud", "Cloud@gmail.com");
        
        // Create documents
        documentS = new Document("S", new byte[]{1, 2, 3});
        documentH = new Document("H", new byte[]{4, 5, 6});
        documentCloud = new Document("CloudDoc", new byte[]{7, 8, 9});
    }
    
    @Test
    public void testCase1_ApprovePendingRequest() {
        // Setup: Customer "C007" with pending application for SolarMax
        customer = new Customer();
        customer.setFirstName("Michael");
        customer.setLastName("Brown");
        customer.setEmail("m.brown@example.com");
        customer.setTelephone("555-1122");
        customer.setFailedAttempts(0); // Eligible to apply
        
        // Create pending application
        appSolarMax = new IPOApplication(customer, solarMax, 10, 200.0, documentS);
        customer.getApplications().add(appSolarMax);
        appSolarMax.setStatus(ApplicationStatus.PENDING);
        
        // Test: Approve the pending application
        boolean result = customer.approveOrRejectApplication(appSolarMax, true);
        
        // Verify: Should return true and status should be APPROVED
        assertTrue("Application should be approved successfully", result);
        assertEquals("Application status should be APPROVED", 
                     ApplicationStatus.APPROVED, appSolarMax.getStatus());
    }
    
    @Test
    public void testCase2_RejectPendingRequest() {
        // Setup: Customer "C008" with pending application for HealthPlus
        customer = new Customer();
        customer.setFirstName("Olivia");
        customer.setLastName("Lee");
        customer.setEmail("olivia.l@example.com");
        customer.setTelephone("555-3344");
        customer.setFailedAttempts(0); // Eligible to apply
        
        // Create pending application
        appHealthPlus = new IPOApplication(customer, healthPlus, 10, 5000.0, documentH);
        customer.getApplications().add(appHealthPlus);
        appHealthPlus.setStatus(ApplicationStatus.PENDING);
        
        // Test: Reject the pending application
        boolean result = customer.approveOrRejectApplication(appHealthPlus, false);
        
        // Verify: Should return true and status should be REJECTED
        assertTrue("Application should be rejected successfully", result);
        assertEquals("Application status should be REJECTED", 
                     ApplicationStatus.REJECTED, appHealthPlus.getStatus());
        assertEquals("Failed attempts should be incremented", 1, customer.getFailedAttempts());
    }
    
    @Test
    public void testCase3_ApproveAlreadyApprovedRecord() {
        // Setup: Customer "C009" with already approved application for HealthPlus
        customer = new Customer();
        customer.setFirstName("Daniel");
        customer.setLastName("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-5566");
        customer.setFailedAttempts(0);
        
        // Create already approved application
        appHealthPlus = new IPOApplication(customer, healthPlus, 10, 5000.0, documentH);
        customer.getApplications().add(appHealthPlus);
        appHealthPlus.setStatus(ApplicationStatus.APPROVED);
        
        // Test: Attempt to approve an already approved application
        boolean result = customer.approveOrRejectApplication(appHealthPlus, true);
        
        // Verify: Should return false since approved applications cannot be modified
        assertFalse("Should not be able to approve an already approved application", result);
        assertEquals("Application status should remain APPROVED", 
                     ApplicationStatus.APPROVED, appHealthPlus.getStatus());
    }
    
    @Test
    public void testCase4_RejectAlreadyRejectedRecord() {
        // Setup: Customer "C010" with already rejected application for Health
        customer = new Customer();
        customer.setFirstName("Sophie");
        customer.setLastName("Zhang");
        customer.setEmail("s.zhang@example.com");
        customer.setTelephone("555-7788");
        customer.setFailedAttempts(1); // Had one previous rejection
        
        // Create already rejected application
        appHealth = new IPOApplication(customer, health, 10, 5000.0, documentH);
        customer.getApplications().add(appHealth);
        appHealth.setStatus(ApplicationStatus.REJECTED);
        
        // Test: Attempt to reject an already rejected application
        boolean result = customer.approveOrRejectApplication(appHealth, false);
        
        // Verify: Should return false since rejected applications cannot be modified
        assertFalse("Should not be able to reject an already rejected application", result);
        assertEquals("Application status should remain REJECTED", 
                     ApplicationStatus.REJECTED, appHealth.getStatus());
    }
    
    @Test
    public void testCase5_ApproveRecordTiedToIneligibleCustomer() {
        // Setup: Customer "C011" who is ineligible (KYC expired) with pending application
        customer = new Customer();
        customer.setFirstName("William");
        customer.setLastName("Wang");
        customer.setEmail("will.w@example.com");
        customer.setTelephone("555-9900");
        customer.setFailedAttempts(3); // Ineligible due to max failed attempts
        
        // Create pending application
        appCloud = new IPOApplication(customer, cloud, 10, 5000.0, documentCloud);
        customer.getApplications().add(appCloud);
        appCloud.setStatus(ApplicationStatus.PENDING);
        
        // Test: Attempt to approve application for ineligible customer
        boolean result = customer.approveOrRejectApplication(appCloud, true);
        
        // Verify: Should return false since customer is not eligible
        assertFalse("Should not be able to approve application for ineligible customer", result);
        assertEquals("Application status should remain PENDING", 
                     ApplicationStatus.PENDING, appCloud.getStatus());
    }
}