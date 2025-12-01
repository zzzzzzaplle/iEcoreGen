import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    private IPOApplicationService service;
    private Customer customerC007;
    private Customer customerC008;
    private Customer customerC009;
    private Customer customerC010;
    private Customer customerC011;
    
    @Before
    public void setUp() {
        service = new IPOApplicationService();
        
        // Setup Customer C007 for Test Case 1
        customerC007 = new Customer();
        customerC007.setName("Michael");
        customerC007.setSurname("Brown");
        customerC007.setEmail("m.brown@example.com");
        customerC007.setTelephoneNumber("555-1122");
        customerC007.setRestricted(false);
        
        IPOApplication app1001 = new IPOApplication();
        app1001.setCustomer(customerC007);
        app1001.setCompanyName("SolarMax");
        app1001.setNumberOfShares(10);
        app1001.setAmount(200.0);
        app1001.setDocument("S");
        app1001.setStatus(ApplicationStatus.PENDING);
        customerC007.getApplications().add(app1001);
        
        // Setup Customer C008 for Test Case 2
        customerC008 = new Customer();
        customerC008.setName("Olivia");
        customerC008.setSurname("Lee");
        customerC008.setEmail("olivia.l@example.com");
        customerC008.setTelephoneNumber("555-3344");
        customerC008.setRestricted(false);
        
        IPOApplication app1002 = new IPOApplication();
        app1002.setCustomer(customerC008);
        app1002.setCompanyName("HealthPlus");
        app1002.setNumberOfShares(10);
        app1002.setAmount(5000.0);
        app1002.setDocument("H");
        app1002.setStatus(ApplicationStatus.PENDING);
        customerC008.getApplications().add(app1002);
        
        // Setup Customer C009 for Test Case 3
        customerC009 = new Customer();
        customerC009.setName("Daniel");
        customerC009.setSurname("Kim");
        customerC009.setEmail("d.kim@example.com");
        customerC009.setTelephoneNumber("555-5566");
        customerC009.setRestricted(false);
        
        IPOApplication app1003 = new IPOApplication();
        app1003.setCustomer(customerC009);
        app1003.setCompanyName("HealthPlus");
        app1003.setNumberOfShares(10);
        app1003.setAmount(5000.0);
        app1003.setDocument("H");
        app1003.setStatus(ApplicationStatus.APPROVED);
        customerC009.getApplications().add(app1003);
        
        // Setup Customer C010 for Test Case 4
        customerC010 = new Customer();
        customerC010.setName("Sophie");
        customerC010.setSurname("Zhang");
        customerC010.setEmail("s.zhang@example.com");
        customerC010.setTelephoneNumber("555-7788");
        customerC010.setRestricted(false);
        
        IPOApplication app1004 = new IPOApplication();
        app1004.setCustomer(customerC010);
        app1004.setCompanyName("Health");
        app1004.setNumberOfShares(10);
        app1004.setAmount(5000.0);
        app1004.setDocument("H");
        app1004.setStatus(ApplicationStatus.REJECTED);
        customerC010.getApplications().add(app1004);
        
        // Setup Customer C011 for Test Case 5
        customerC011 = new Customer();
        customerC011.setName("William");
        customerC011.setSurname("Wang");
        customerC011.setEmail("will.w@example.com");
        customerC011.setTelephoneNumber("555-9900");
        customerC011.setRestricted(true); // Customer is restricted/ineligible
        
        IPOApplication app1005 = new IPOApplication();
        app1005.setCustomer(customerC011);
        app1005.setCompanyName("Cloud");
        app1005.setNumberOfShares(10);
        app1005.setAmount(5000.0);
        app1005.setDocument("H");
        app1005.setStatus(ApplicationStatus.PENDING);
        customerC011.getApplications().add(app1005);
    }
    
    @Test
    public void testCase1_ApprovePendingRequest() {
        // Get the pending application for Customer C007
        IPOApplication application = customerC007.getApplications().get(0);
        
        // Bank approves the application
        boolean result = service.approveOrRejectApplication(application, true);
        
        // Verify the operation returns true and status changes to APPROVED
        assertTrue("Application approval should succeed", result);
        assertEquals("Application status should be APPROVED", 
                     ApplicationStatus.APPROVED, application.getStatus());
    }
    
    @Test
    public void testCase2_RejectPendingRequest() {
        // Get the pending application for Customer C008
        IPOApplication application = customerC008.getApplications().get(0);
        
        // Bank rejects the application
        boolean result = service.approveOrRejectApplication(application, false);
        
        // Verify the operation returns true and status changes to REJECTED
        assertTrue("Application rejection should succeed", result);
        assertEquals("Application status should be REJECTED", 
                     ApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase3_ApproveAlreadyApprovedRecord() {
        // Get the already approved application for Customer C009
        IPOApplication application = customerC009.getApplications().get(0);
        
        // Bank attempts to re-approve the application
        boolean result = service.approveOrRejectApplication(application, true);
        
        // Verify the operation returns false (cannot modify approved applications)
        assertFalse("Re-approving approved application should fail", result);
        // Status should remain APPROVED
        assertEquals("Application status should remain APPROVED", 
                     ApplicationStatus.APPROVED, application.getStatus());
    }
    
    @Test
    public void testCase4_RejectAlreadyRejectedRecord() {
        // Get the already rejected application for Customer C010
        IPOApplication application = customerC010.getApplications().get(0);
        
        // Bank attempts to re-reject the application
        boolean result = service.approveOrRejectApplication(application, false);
        
        // Verify the operation returns false (cannot modify final decisions)
        assertFalse("Re-rejecting rejected application should fail", result);
        // Status should remain REJECTED
        assertEquals("Application status should remain REJECTED", 
                     ApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase5_ApproveRecordTiedToIneligibleCustomer() {
        // Get the pending application for restricted Customer C011
        IPOApplication application = customerC011.getApplications().get(0);
        
        // Bank attempts to approve the application for ineligible customer
        boolean result = service.approveOrRejectApplication(application, true);
        
        // Verify the operation returns false (customer must maintain eligibility)
        assertFalse("Approving application for restricted customer should fail", result);
        // Status should remain PENDING since the operation failed
        assertEquals("Application status should remain PENDING", 
                     ApplicationStatus.PENDING, application.getStatus());
    }
}