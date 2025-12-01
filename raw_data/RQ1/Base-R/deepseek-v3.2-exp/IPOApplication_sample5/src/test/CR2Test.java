import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    private BankSystem bankSystem;
    private Customer customer;
    private IPOApplication application;
    
    @Before
    public void setUp() {
        bankSystem = new BankSystem();
    }
    
    @Test
    public void testCase1_ApprovePendingRequest() {
        // Setup: Create customer "C007" who can apply for IPO
        customer = new Customer();
        customer.setName("Michael");
        customer.setSurname("Brown");
        customer.setEmail("m.brown@example.com");
        customer.setTelephone("555-1122");
        customer.setRestricted(false); // Can apply for IPO
        
        // Create pending application "APP-1001" for company "SolarMax"
        application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompanyName("SolarMax");
        application.setNumberOfShares(10);
        application.setAmountPaid(200.0);
        application.setDocument("S");
        application.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(application);
        bankSystem.getCustomers().add(customer);
        
        // Execute: Bank approves the application
        boolean result = bankSystem.approveOrRejectApplication(application, true);
        
        // Verify: Operation returns true and status changes to APPROVED
        assertTrue("Application should be approved successfully", result);
        assertEquals("Application status should be APPROVED", ApplicationStatus.APPROVED, application.getStatus());
    }
    
    @Test
    public void testCase2_RejectPendingRequest() {
        // Setup: Create customer "C008" who can apply for IPO
        customer = new Customer();
        customer.setName("Olivia");
        customer.setSurname("Lee");
        customer.setEmail("olivia.l@example.com");
        customer.setTelephone("555-3344");
        customer.setRestricted(false); // Can apply for IPO
        
        // Create pending application "APP-1002" for company "HealthPlus"
        application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompanyName("HealthPlus");
        application.setNumberOfShares(10);
        application.setAmountPaid(5000.0);
        application.setDocument("H");
        application.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(application);
        bankSystem.getCustomers().add(customer);
        
        // Execute: Bank rejects the application
        boolean result = bankSystem.approveOrRejectApplication(application, false);
        
        // Verify: Operation returns true and status changes to REJECTED
        assertTrue("Application should be rejected successfully", result);
        assertEquals("Application status should be REJECTED", ApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase3_ApproveAlreadyApprovedRecord() {
        // Setup: Create customer "C009" with already approved application
        customer = new Customer();
        customer.setName("Daniel");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-5566");
        customer.setRestricted(false);
        
        // Create already approved application "APP-1003" for "HealthPlus"
        application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompanyName("HealthPlus");
        application.setNumberOfShares(10);
        application.setAmountPaid(5000.0);
        application.setDocument("H");
        application.setStatus(ApplicationStatus.APPROVED); // Already approved
        
        customer.getApplications().add(application);
        bankSystem.getCustomers().add(customer);
        
        // Execute: Bank attempts to re-approve the already approved application
        boolean result = bankSystem.approveOrRejectApplication(application, true);
        
        // Verify: Operation returns false (cannot modify approved applications)
        assertFalse("Should not be able to modify approved applications", result);
        assertEquals("Application status should remain APPROVED", ApplicationStatus.APPROVED, application.getStatus());
    }
    
    @Test
    public void testCase4_RejectAlreadyRejectedRecord() {
        // Setup: Create customer "C010" with already rejected application
        customer = new Customer();
        customer.setName("Sophie");
        customer.setSurname("Zhang");
        customer.setEmail("s.zhang@example.com");
        customer.setTelephone("555-7788");
        customer.setRestricted(false);
        
        // Create already rejected application "APP-1004" for "Health"
        application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompanyName("Health");
        application.setNumberOfShares(10);
        application.setAmountPaid(5000.0);
        application.setDocument("H");
        application.setStatus(ApplicationStatus.REJECTED); // Already rejected
        
        customer.getApplications().add(application);
        bankSystem.getCustomers().add(customer);
        
        // Execute: Bank tries to reject the already rejected application
        boolean result = bankSystem.approveOrRejectApplication(application, false);
        
        // Verify: Operation returns false (cannot modify final decisions)
        assertFalse("Should not be able to modify rejected applications", result);
        assertEquals("Application status should remain REJECTED", ApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase5_ApproveRecordTiedToIneligibleCustomer() {
        // Setup: Create customer "C011" who cannot apply for IPO
        customer = new Customer();
        customer.setName("William");
        customer.setSurname("Wang");
        customer.setEmail("will.w@example.com");
        customer.setTelephone("555-9900");
        customer.setRestricted(true); // Cannot apply for IPO (eligibility revoked)
        
        // Create pending application "APP-1005" for "Cloud"
        application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompanyName("Cloud");
        application.setNumberOfShares(10);
        application.setAmountPaid(5000.0);
        application.setDocument("C");
        application.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(application);
        bankSystem.getCustomers().add(customer);
        
        // Execute: Bank attempts to approve application for ineligible customer
        boolean result = bankSystem.approveOrRejectApplication(application, true);
        
        // Verify: Operation returns false (must maintain eligibility throughout process)
        assertFalse("Should not be able to approve application for ineligible customer", result);
        assertEquals("Application status should remain PENDING", ApplicationStatus.PENDING, application.getStatus());
    }
}