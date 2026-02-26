import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    private Customer customer1;
    private Customer customer2;
    private Customer customer3;
    private Customer customer4;
    private Customer customer5;
    private IPOApplication app1;
    private IPOApplication app2;
    private IPOApplication app3;
    private IPOApplication app4;
    private IPOApplication app5;
    
    @Before
    public void setUp() {
        // Setup for Test Case 1
        customer1 = new Customer();
        customer1.setName("Michael");
        customer1.setSurname("Brown");
        customer1.setEmail("m.brown@example.com");
        customer1.setTelephone("555-1122");
        customer1.resetFailedAttempts(); // Ensure eligibility
        
        app1 = new IPOApplication();
        app1.setCustomer(customer1);
        app1.setCompanyName("SolarMax");
        app1.setNumberOfShares(10);
        app1.setAmount(200.0);
        app1.setDocument("S");
        app1.setStatus(ApplicationStatus.PENDING);
        customer1.getApplications().add(app1);
        
        // Setup for Test Case 2
        customer2 = new Customer();
        customer2.setName("Olivia");
        customer2.setSurname("Lee");
        customer2.setEmail("olivia.l@example.com");
        customer2.setTelephone("555-3344");
        customer2.resetFailedAttempts(); // Ensure eligibility
        
        app2 = new IPOApplication();
        app2.setCustomer(customer2);
        app2.setCompanyName("HealthPlus");
        app2.setNumberOfShares(10);
        app2.setAmount(5000.0);
        app2.setDocument("H");
        app2.setStatus(ApplicationStatus.PENDING);
        customer2.getApplications().add(app2);
        
        // Setup for Test Case 3
        customer3 = new Customer();
        customer3.setName("Daniel");
        customer3.setSurname("Kim");
        customer3.setEmail("d.kim@example.com");
        customer3.setTelephone("555-5566");
        customer3.resetFailedAttempts(); // Ensure eligibility
        
        app3 = new IPOApplication();
        app3.setCustomer(customer3);
        app3.setCompanyName("HealthPlus");
        app3.setNumberOfShares(10);
        app3.setAmount(5000.0);
        app3.setDocument("H");
        app3.setStatus(ApplicationStatus.APPROVED);
        customer3.getApplications().add(app3);
        
        // Setup for Test Case 4
        customer4 = new Customer();
        customer4.setName("Sophie");
        customer4.setSurname("Zhang");
        customer4.setEmail("s.zhang@example.com");
        customer4.setTelephone("555-7788");
        customer4.resetFailedAttempts(); // Ensure eligibility
        
        app4 = new IPOApplication();
        app4.setCustomer(customer4);
        app4.setCompanyName("Health");
        app4.setNumberOfShares(10);
        app4.setAmount(5000.0);
        app4.setDocument("H");
        app4.setStatus(ApplicationStatus.REJECTED);
        customer4.getApplications().add(app4);
        
        // Setup for Test Case 5
        customer5 = new Customer();
        customer5.setName("William");
        customer5.setSurname("Wang");
        customer5.setEmail("will.w@example.com");
        customer5.setTelephone("555-9900");
        customer5.incrementFailedAttempts();
        customer5.incrementFailedAttempts();
        customer5.incrementFailedAttempts(); // Make customer ineligible
        
        app5 = new IPOApplication();
        app5.setCustomer(customer5);
        app5.setCompanyName("Cloud");
        app5.setNumberOfShares(10);
        app5.setAmount(5000.0);
        app5.setDocument("C");
        app5.setStatus(ApplicationStatus.PENDING);
        customer5.getApplications().add(app5);
    }
    
    @Test
    public void testCase1_approvePendingRequest() {
        // Test Case 1: "Approve pending request"
        // Input: Bank approves application "APP-1001" for company "SolarMax"
        // Expected Output: True (status changes to APPROVED)
        
        boolean result = IPOApplication.approveOrRejectApplication(app1, true);
        
        assertTrue("Application should be approved successfully", result);
        assertEquals("Application status should be APPROVED", ApplicationStatus.APPROVED, app1.getStatus());
    }
    
    @Test
    public void testCase2_rejectPendingRequest() {
        // Test Case 2: "Reject pending request"
        // Input: Bank rejects application "APP-1002" for "HealthPlus"
        // Expected Output: True (status changes to REJECTED)
        
        boolean result = IPOApplication.approveOrRejectApplication(app2, false);
        
        assertTrue("Application should be rejected successfully", result);
        assertEquals("Application status should be REJECTED", ApplicationStatus.REJECTED, app2.getStatus());
    }
    
    @Test
    public void testCase3_approveAlreadyApprovedRecord() {
        // Test Case 3: "Approve already approved record"
        // Input: Bank attempts to re-approve application "APP-1003"
        // Expected Output: False (cannot modify approved applications)
        
        boolean result = IPOApplication.approveOrRejectApplication(app3, true);
        
        assertFalse("Should not be able to approve an already approved application", result);
        assertEquals("Application status should remain APPROVED", ApplicationStatus.APPROVED, app3.getStatus());
    }
    
    @Test
    public void testCase4_rejectAlreadyRejectedRecord() {
        // Test Case 4: "Reject already rejected record"
        // Input: Bank tries to reject application "APP-1004"
        // Expected Output: False (cannot modify final decisions)
        
        boolean result = IPOApplication.approveOrRejectApplication(app4, false);
        
        assertFalse("Should not be able to reject an already rejected application", result);
        assertEquals("Application status should remain REJECTED", ApplicationStatus.REJECTED, app4.getStatus());
    }
    
    @Test
    public void testCase5_approveRecordTiedToIneligibleCustomer() {
        // Test Case 5: "Approve record tied to ineligible customer"
        // Input: Bank processes application "APP-1005"
        // Expected Output: False (must maintain eligibility throughout process)
        
        boolean result = IPOApplication.approveOrRejectApplication(app5, true);
        
        assertFalse("Should not be able to approve application for ineligible customer", result);
        assertEquals("Application status should remain PENDING", ApplicationStatus.PENDING, app5.getStatus());
    }
}