import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    private IPOApplicationSystem system;
    private Customer customer;
    private Company company;
    private IPOApplication application;
    
    @Before
    public void setUp() {
        system = new IPOApplicationSystem();
        customer = new Customer();
        company = new Company();
        application = new IPOApplication();
    }
    
    @Test
    public void testCase1_ApprovePendingRequest() {
        // Setup
        customer.setName("Michael");
        customer.setSurname("Brown");
        customer.setEmailAddress("m.brown@example.com");
        customer.setTelephoneNumber("555-1122");
        customer.setEligibleForIPO(true);
        
        company.setName("SolarMax");
        
        application.setCompanyName("SolarMax");
        application.setNumberOfShares(10);
        application.setAmountOfMoney(200.0);
        application.setDocument("S");
        application.setReviewed(false);
        application.setApproved(false);
        application.setRejected(false);
        
        customer.getApplications().add(application);
        system.getApplications().add(application);
        
        // Execute: Approve the application
        boolean result = system.approveOrRejectApplication(application, true, customer, company);
        
        // Verify
        assertTrue("Application should be approved successfully", result);
        assertTrue("Application status should be approved", application.isApproved());
        assertTrue("Application should be marked as reviewed", application.isReviewed());
        assertFalse("Application should not be rejected", application.isRejected());
    }
    
    @Test
    public void testCase2_RejectPendingRequest() {
        // Setup
        customer.setName("Olivia");
        customer.setSurname("Lee");
        customer.setEmailAddress("olivia.l@example.com");
        customer.setTelephoneNumber("555-3344");
        customer.setEligibleForIPO(true);
        
        company.setName("HealthPlus");
        
        application.setCompanyName("HealthPlus");
        application.setNumberOfShares(10);
        application.setAmountOfMoney(5000.0);
        application.setDocument("H");
        application.setReviewed(false);
        application.setApproved(false);
        application.setRejected(false);
        
        customer.getApplications().add(application);
        system.getApplications().add(application);
        
        // Execute: Reject the application
        boolean result = system.approveOrRejectApplication(application, false, customer, company);
        
        // Verify
        assertTrue("Application should be rejected successfully", result);
        assertTrue("Application should be marked as rejected", application.isRejected());
        assertTrue("Application should be marked as reviewed", application.isReviewed());
        assertFalse("Application should not be approved", application.isApproved());
    }
    
    @Test
    public void testCase3_ApproveAlreadyApprovedRecord() {
        // Setup
        customer.setName("Daniel");
        customer.setSurname("Kim");
        customer.setEmailAddress("d.kim@example.com");
        customer.setTelephoneNumber("555-5566");
        customer.setEligibleForIPO(true);
        
        company.setName("HealthPlus");
        
        application.setCompanyName("HealthPlus");
        application.setNumberOfShares(10);
        application.setAmountOfMoney(5000.0);
        application.setDocument("H");
        application.setReviewed(true);
        application.setApproved(true);
        application.setRejected(false);
        
        customer.getApplications().add(application);
        system.getApplications().add(application);
        
        // Execute: Attempt to approve an already approved application
        boolean result = system.approveOrRejectApplication(application, true, customer, company);
        
        // Verify
        assertTrue("Operation should return true even for already approved application", result);
        assertTrue("Application should remain approved", application.isApproved());
        assertTrue("Application should remain reviewed", application.isReviewed());
        assertFalse("Application should not be rejected", application.isRejected());
    }
    
    @Test
    public void testCase4_RejectAlreadyRejectedRecord() {
        // Setup
        customer.setName("Sophie");
        customer.setSurname("Zhang");
        customer.setEmailAddress("s.zhang@example.com");
        customer.setTelephoneNumber("555-7788");
        customer.setEligibleForIPO(true);
        
        company.setName("Health");
        
        application.setCompanyName("Health");
        application.setNumberOfShares(10);
        application.setAmountOfMoney(5000.0);
        application.setDocument("H");
        application.setReviewed(true);
        application.setApproved(false);
        application.setRejected(true);
        
        customer.getApplications().add(application);
        system.getApplications().add(application);
        
        // Execute: Attempt to reject an already rejected application
        boolean result = system.approveOrRejectApplication(application, false, customer, company);
        
        // Verify
        assertTrue("Operation should return true even for already rejected application", result);
        assertTrue("Application should remain rejected", application.isRejected());
        assertTrue("Application should remain reviewed", application.isReviewed());
        assertFalse("Application should not be approved", application.isApproved());
    }
    
    @Test
    public void testCase5_ApproveRecordTiedToIneligibleCustomer() {
        // Setup
        customer.setName("William");
        customer.setSurname("Wang");
        customer.setEmailAddress("will.w@example.com");
        customer.setTelephoneNumber("555-9900");
        customer.setEligibleForIPO(false); // Customer is ineligible
        
        company.setName("Cloud");
        
        application.setCompanyName("Cloud");
        application.setNumberOfShares(10);
        application.setAmountOfMoney(5000.0);
        application.setDocument("C");
        application.setReviewed(false);
        application.setApproved(false);
        application.setRejected(false);
        
        customer.getApplications().add(application);
        system.getApplications().add(application);
        
        // Execute: Attempt to approve application for ineligible customer
        boolean result = system.approveOrRejectApplication(application, true, customer, company);
        
        // Verify
        assertFalse("Should return false when customer is ineligible", result);
        assertFalse("Application should not be approved", application.isApproved());
        assertFalse("Application should not be reviewed", application.isReviewed());
        assertFalse("Application should not be rejected", application.isRejected());
    }
}