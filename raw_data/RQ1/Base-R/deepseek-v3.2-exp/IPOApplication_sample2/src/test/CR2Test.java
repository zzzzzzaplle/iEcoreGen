import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    private IPOApplicationSystem system;
    private EmailService emailService;
    
    @Before
    public void setUp() {
        system = new IPOApplicationSystem();
        emailService = new EmailService();
        system.setEmailService(emailService);
    }
    
    @Test
    public void testCase1_ApprovePendingRequest() {
        // Setup: Create customer "C007" who can apply for IPO
        Customer customer = new Customer();
        customer.setName("Michael");
        customer.setSurname("Brown");
        customer.setEmail("m.brown@example.com");
        customer.setTelephone("555-1122");
        customer.setRestricted(false); // Eligible for IPO
        
        // Create pending application "APP-1001" for company "SolarMax"
        IPOApplication application = new IPOApplication();
        application.setCompanyName("SolarMax");
        application.setNumberOfShares(10);
        application.setAmountPaid(200.0);
        Document document = new Document();
        document.setFileName("S");
        application.setDocument(document);
        application.setCustomer(customer);
        application.setStatus(ApplicationStatus.PENDING);
        
        // Add application to customer's list
        customer.getApplications().add(application);
        
        // Test: Approve the pending application
        boolean result = system.approveOrRejectApplication(application, true);
        
        // Verify: Operation should succeed and status should be APPROVED
        assertTrue("Application should be approved successfully", result);
        assertEquals("Application status should be APPROVED", ApplicationStatus.APPROVED, application.getStatus());
    }
    
    @Test
    public void testCase2_RejectPendingRequest() {
        // Setup: Create customer "C008" who can apply for IPO
        Customer customer = new Customer();
        customer.setName("Olivia");
        customer.setSurname("Lee");
        customer.setEmail("olivia.l@example.com");
        customer.setTelephone("555-3344");
        customer.setRestricted(false); // Eligible for IPO
        
        // Create pending application "APP-1002" for company "HealthPlus"
        IPOApplication application = new IPOApplication();
        application.setCompanyName("HealthPlus");
        application.setNumberOfShares(10);
        application.setAmountPaid(5000.0);
        Document document = new Document();
        document.setFileName("H");
        application.setDocument(document);
        application.setCustomer(customer);
        application.setStatus(ApplicationStatus.PENDING);
        
        // Add application to customer's list
        customer.getApplications().add(application);
        
        // Test: Reject the pending application
        boolean result = system.approveOrRejectApplication(application, false);
        
        // Verify: Operation should succeed and status should be REJECTED
        assertTrue("Application should be rejected successfully", result);
        assertEquals("Application status should be REJECTED", ApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase3_ApproveAlreadyApprovedRecord() {
        // Setup: Create customer "C009" with already approved application
        Customer customer = new Customer();
        customer.setName("Daniel");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-5566");
        
        // Create already approved application "APP-1003" for company "HealthPlus"
        IPOApplication application = new IPOApplication();
        application.setCompanyName("HealthPlus");
        application.setNumberOfShares(10);
        application.setAmountPaid(5000.0);
        Document document = new Document();
        document.setFileName("H");
        application.setDocument(document);
        application.setCustomer(customer);
        application.setStatus(ApplicationStatus.APPROVED); // Already approved
        
        // Add application to customer's list
        customer.getApplications().add(application);
        
        // Test: Try to approve already approved application
        boolean result = system.approveOrRejectApplication(application, true);
        
        // Verify: Operation should fail (cannot modify approved applications)
        assertFalse("Should not be able to approve already approved application", result);
        assertEquals("Application status should remain APPROVED", ApplicationStatus.APPROVED, application.getStatus());
    }
    
    @Test
    public void testCase4_RejectAlreadyRejectedRecord() {
        // Setup: Create customer "C010" with already rejected application
        Customer customer = new Customer();
        customer.setName("Sophie");
        customer.setSurname("Zhang");
        customer.setEmail("s.zhang@example.com");
        customer.setTelephone("555-7788");
        
        // Create already rejected application "APP-1004" for company "Health"
        IPOApplication application = new IPOApplication();
        application.setCompanyName("Health");
        application.setNumberOfShares(10);
        application.setAmountPaid(5000.0);
        Document document = new Document();
        document.setFileName("H");
        application.setDocument(document);
        application.setCustomer(customer);
        application.setStatus(ApplicationStatus.REJECTED); // Already rejected
        
        // Add application to customer's list
        customer.getApplications().add(application);
        
        // Test: Try to reject already rejected application
        boolean result = system.approveOrRejectApplication(application, false);
        
        // Verify: Operation should fail (cannot modify final decisions)
        assertFalse("Should not be able to reject already rejected application", result);
        assertEquals("Application status should remain REJECTED", ApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase5_ApproveRecordTiedToIneligibleCustomer() {
        // Setup: Create customer "C011" who cannot apply for IPO
        Customer customer = new Customer();
        customer.setName("William");
        customer.setSurname("Wang");
        customer.setEmail("will.w@example.com");
        customer.setTelephone("555-9900");
        customer.setRestricted(true); // Not eligible for IPO
        
        // Create pending application "APP-1005" for company "Cloud"
        IPOApplication application = new IPOApplication();
        application.setCompanyName("Cloud");
        application.setNumberOfShares(10);
        application.setAmountPaid(5000.0);
        Document document = new Document();
        document.setFileName("C");
        application.setDocument(document);
        application.setCustomer(customer);
        application.setStatus(ApplicationStatus.PENDING);
        
        // Add application to customer's list
        customer.getApplications().add(application);
        
        // Test: Try to approve application for ineligible customer
        boolean result = system.approveOrRejectApplication(application, true);
        
        // Verify: Operation should fail (customer must maintain eligibility)
        assertFalse("Should not be able to approve application for ineligible customer", result);
        assertEquals("Application status should remain PENDING", ApplicationStatus.PENDING, application.getStatus());
    }
}