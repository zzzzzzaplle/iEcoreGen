import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    private Customer customer1;
    private Customer customer2;
    private Customer customer3;
    private Customer customer4;
    private Customer customer5;
    private IpoApplication app1;
    private IpoApplication app2;
    private IpoApplication app3;
    private IpoApplication app4;
    private IpoApplication app5;
    
    @Before
    public void setUp() {
        // Setup for Test Case 1: "Approve pending request"
        customer1 = new Customer();
        customer1.setName("Michael");
        customer1.setSurname("Brown");
        customer1.setEmail("m.brown@example.com");
        customer1.setTelephone("555-1122");
        customer1.setFailedAttempts(0); // Eligible customer
        
        app1 = new IpoApplication();
        app1.setCompanyName("SolarMax");
        app1.setShares(10);
        app1.setAmount(200);
        app1.setDocument("S");
        app1.setApproved(false);
        app1.setRejected(false);
        
        customer1.getApplications().add(app1);
        
        // Setup for Test Case 2: "Reject pending request"
        customer2 = new Customer();
        customer2.setName("Olivia");
        customer2.setSurname("Lee");
        customer2.setEmail("olivia.l@example.com");
        customer2.setTelephone("555-3344");
        customer2.setFailedAttempts(0); // Eligible customer
        
        app2 = new IpoApplication();
        app2.setCompanyName("HealthPlus");
        app2.setShares(10);
        app2.setAmount(5000);
        app2.setDocument("H");
        app2.setApproved(false);
        app2.setRejected(false);
        
        customer2.getApplications().add(app2);
        
        // Setup for Test Case 3: "Approve already approved record"
        customer3 = new Customer();
        customer3.setName("Daniel");
        customer3.setSurname("Kim");
        customer3.setEmail("d.kim@example.com");
        customer3.setTelephone("555-5566");
        customer3.setFailedAttempts(0); // Eligible customer
        
        app3 = new IpoApplication();
        app3.setCompanyName("HealthPlus");
        app3.setShares(10);
        app3.setAmount(5000);
        app3.setDocument("H");
        app3.setApproved(true); // Already approved
        app3.setRejected(false);
        
        customer3.getApplications().add(app3);
        
        // Setup for Test Case 4: "Reject already rejected record"
        customer4 = new Customer();
        customer4.setName("Sophie");
        customer4.setSurname("Zhang");
        customer4.setEmail("s.zhang@example.com");
        customer4.setTelephone("555-7788");
        customer4.setFailedAttempts(0); // Eligible customer
        
        app4 = new IpoApplication();
        app4.setCompanyName("Health");
        app4.setShares(10);
        app4.setAmount(5000);
        app4.setDocument("H");
        app4.setApproved(false);
        app4.setRejected(true); // Already rejected
        
        customer4.getApplications().add(app4);
        
        // Setup for Test Case 5: "Approve record tied to ineligible customer"
        customer5 = new Customer();
        customer5.setName("William");
        customer5.setSurname("Wang");
        customer5.setEmail("will.w@example.com");
        customer5.setTelephone("555-9900");
        customer5.setFailedAttempts(3); // Ineligible customer (3 failed attempts)
        
        app5 = new IpoApplication();
        app5.setCompanyName("Cloud");
        app5.setShares(10);
        app5.setAmount(5000);
        app5.setDocument("H");
        app5.setApproved(false);
        app5.setRejected(false);
        
        customer5.getApplications().add(app5);
    }
    
    @Test
    public void testCase1_approvePendingRequest() {
        // Test Case 1: "Approve pending request"
        // Input: Bank approves application "APP-1001" for company "SolarMax"
        
        // Execute approve operation
        boolean result = IpoApplication.approveOrReject(app1, true);
        
        // Verify the operation returns true
        assertTrue("Approval should succeed for pending application", result);
        
        // Verify application status changed to approved
        assertTrue("Application should be approved", app1.isApproved());
        assertFalse("Application should not be rejected", app1.isRejected());
    }
    
    @Test
    public void testCase2_rejectPendingRequest() {
        // Test Case 2: "Reject pending request"
        // Input: Bank rejects application "APP-1002" for "HealthPlus"
        
        // Execute reject operation
        boolean result = IpoApplication.approveOrReject(app2, false);
        
        // Verify the operation returns true
        assertTrue("Rejection should succeed for pending application", result);
        
        // Verify application status changed to rejected
        assertFalse("Application should not be approved", app2.isApproved());
        assertTrue("Application should be rejected", app2.isRejected());
    }
    
    @Test
    public void testCase3_approveAlreadyApprovedRecord() {
        // Test Case 3: "Approve already approved record"
        // Input: Bank attempts to re-approve application "APP-1003"
        
        // Execute approve operation on already approved application
        boolean result = IpoApplication.approveOrReject(app3, true);
        
        // Verify the operation returns false (cannot modify approved applications)
        assertFalse("Should not be able to modify already approved application", result);
        
        // Verify application status remains approved
        assertTrue("Application should remain approved", app3.isApproved());
        assertFalse("Application should not be rejected", app3.isRejected());
    }
    
    @Test
    public void testCase4_rejectAlreadyRejectedRecord() {
        // Test Case 4: "Reject already rejected record"
        // Input: Bank tries to reject application "APP-1004"
        
        // Execute reject operation on already rejected application
        boolean result = IpoApplication.approveOrReject(app4, false);
        
        // Verify the operation returns false (cannot modify final decisions)
        assertFalse("Should not be able to modify already rejected application", result);
        
        // Verify application status remains rejected
        assertFalse("Application should not be approved", app4.isApproved());
        assertTrue("Application should remain rejected", app4.isRejected());
    }
    
    @Test
    public void testCase5_approveRecordTiedToIneligibleCustomer() {
        // Test Case 5: "Approve record tied to ineligible customer"
        // Input: Bank processes application "APP-1005"
        
        // Verify customer is ineligible
        assertFalse("Customer should be ineligible", customer5.isEligible());
        
        // Execute approve operation
        boolean result = IpoApplication.approveOrReject(app5, true);
        
        // Verify the operation returns false (must maintain eligibility throughout process)
        assertFalse("Should not be able to approve application for ineligible customer", result);
        
        // Verify application status remains unchanged (still pending)
        assertFalse("Application should not be approved", app5.isApproved());
        assertFalse("Application should not be rejected", app5.isRejected());
    }
}