import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    private SoftwareSystem softwareSystem;
    private Customer customer;
    private IPOApplication application;
    private Document document;
    
    @Before
    public void setUp() {
        softwareSystem = new SoftwareSystem();
        customer = new Customer();
        application = new IPOApplication();
        document = new Document();
    }
    
    @Test
    public void testCase1_ApprovePendingRequest() {
        // Setup
        customer.setName("Michael");
        customer.setSurname("Brown");
        customer.setEmail("m.brown@example.com");
        customer.setTelephone("555-1122");
        customer.setEligibleForIPO(true);
        
        application.setCompany("SolarMax");
        application.setNumberOfShares(10);
        application.setAmount(200.0);
        application.setDocument(document);
        application.setCustomer(customer);
        application.setApproved(false);
        application.setRejected(false);
        
        customer.getApplications().add(application);
        
        // Test approve operation
        boolean result = softwareSystem.approveOrRejectApplication(application, true);
        
        // Verify
        assertTrue("Application should be approved successfully", result);
        assertTrue("Application status should be approved", application.isApproved());
        assertFalse("Application should not be rejected", application.isRejected());
    }
    
    @Test
    public void testCase2_RejectPendingRequest() {
        // Setup
        customer.setName("Olivia");
        customer.setSurname("Lee");
        customer.setEmail("olivia.l@example.com");
        customer.setTelephone("555-3344");
        customer.setEligibleForIPO(true);
        
        application.setCompany("HealthPlus");
        application.setNumberOfShares(10);
        application.setAmount(5000.0);
        application.setDocument(document);
        application.setCustomer(customer);
        application.setApproved(false);
        application.setRejected(false);
        
        customer.getApplications().add(application);
        
        // Test reject operation
        boolean result = softwareSystem.approveOrRejectApplication(application, false);
        
        // Verify
        assertTrue("Application should be rejected successfully", result);
        assertFalse("Application should not be approved", application.isApproved());
        assertTrue("Application status should be rejected", application.isRejected());
    }
    
    @Test
    public void testCase3_ApproveAlreadyApprovedRecord() {
        // Setup
        customer.setName("Daniel");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-5566");
        customer.setEligibleForIPO(true);
        
        application.setCompany("HealthPlus");
        application.setNumberOfShares(10);
        application.setAmount(5000.0);
        application.setDocument(document);
        application.setCustomer(customer);
        application.setApproved(true); // Already approved
        application.setRejected(false);
        
        customer.getApplications().add(application);
        
        // Test approve already approved application
        boolean result = softwareSystem.approveOrRejectApplication(application, true);
        
        // Verify
        assertFalse("Cannot approve an already approved application", result);
        assertTrue("Application should remain approved", application.isApproved());
    }
    
    @Test
    public void testCase4_RejectAlreadyRejectedRecord() {
        // Setup
        customer.setName("Sophie");
        customer.setSurname("Zhang");
        customer.setEmail("s.zhang@example.com");
        customer.setTelephone("555-7788");
        customer.setEligibleForIPO(true);
        
        application.setCompany("Health");
        application.setNumberOfShares(10);
        application.setAmount(5000.0);
        application.setDocument(document);
        application.setCustomer(customer);
        application.setApproved(false);
        application.setRejected(true); // Already rejected
        
        customer.getApplications().add(application);
        
        // Test reject already rejected application
        boolean result = softwareSystem.approveOrRejectApplication(application, false);
        
        // Verify
        assertFalse("Cannot reject an already rejected application", result);
        assertTrue("Application should remain rejected", application.isRejected());
    }
    
    @Test
    public void testCase5_ApproveRecordTiedToIneligibleCustomer() {
        // Setup
        customer.setName("William");
        customer.setSurname("Wang");
        customer.setEmail("will.w@example.com");
        customer.setTelephone("555-9900");
        customer.setEligibleForIPO(false); // Ineligible customer
        
        application.setCompany("Cloud");
        application.setNumberOfShares(10);
        application.setAmount(5000.0);
        application.setDocument(document);
        application.setCustomer(customer);
        application.setApproved(false);
        application.setRejected(false);
        
        customer.getApplications().add(application);
        
        // Test approve application for ineligible customer
        boolean result = softwareSystem.approveOrRejectApplication(application, true);
        
        // Verify
        assertFalse("Cannot approve application for ineligible customer", result);
        assertFalse("Application should not be approved", application.isApproved());
        assertFalse("Application should not be rejected", application.isRejected());
    }
}