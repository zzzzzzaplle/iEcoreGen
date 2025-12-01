import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    private BankSystem bankSystem;
    private Customer eligibleCustomer;
    private Customer ineligibleCustomer;
    private IPOApplication pendingApplication;
    private IPOApplication approvedApplication;
    private IPOApplication rejectedApplication;

    @Before
    public void setUp() {
        bankSystem = new BankSystem();
        
        // Setup eligible customer for Test Case 1 and 2
        eligibleCustomer = new Customer();
        eligibleCustomer.setName("Michael");
        eligibleCustomer.setSurname("Brown");
        eligibleCustomer.setEmail("m.brown@example.com");
        eligibleCustomer.setTelephone("555-1122");
        eligibleCustomer.setEligibleForIPO(true);
        
        // Setup another eligible customer for Test Case 2
        Customer customer2 = new Customer();
        customer2.setName("Olivia");
        customer2.setSurname("Lee");
        customer2.setEmail("olivia.l@example.com");
        customer2.setTelephone("555-3344");
        customer2.setEligibleForIPO(true);
        
        // Setup customer with approved application for Test Case 3
        Customer customer3 = new Customer();
        customer3.setName("Daniel");
        customer3.setSurname("Kim");
        customer3.setEmail("d.kim@example.com");
        customer3.setTelephone("555-5566");
        customer3.setEligibleForIPO(true);
        
        // Setup customer with rejected application for Test Case 4
        Customer customer4 = new Customer();
        customer4.setName("Sophie");
        customer4.setSurname("Zhang");
        customer4.setEmail("s.zhang@example.com");
        customer4.setTelephone("555-7788");
        customer4.setEligibleForIPO(true);
        
        // Setup ineligible customer for Test Case 5
        ineligibleCustomer = new Customer();
        ineligibleCustomer.setName("William");
        ineligibleCustomer.setSurname("Wang");
        ineligibleCustomer.setEmail("will.w@example.com");
        ineligibleCustomer.setTelephone("555-9900");
        ineligibleCustomer.setEligibleForIPO(false);
        
        // Create pending application for Test Case 1
        pendingApplication = new IPOApplication();
        pendingApplication.setCustomer(eligibleCustomer);
        pendingApplication.setCompany("SolarMax");
        pendingApplication.setNumberOfShares(10);
        pendingApplication.setAmount(200.0);
        pendingApplication.setDocument("S");
        
        // Create pending application for Test Case 2
        IPOApplication pendingApplication2 = new IPOApplication();
        pendingApplication2.setCustomer(customer2);
        pendingApplication2.setCompany("HealthPlus");
        pendingApplication2.setNumberOfShares(10);
        pendingApplication2.setAmount(5000.0);
        pendingApplication2.setDocument("H");
        
        // Create approved application for Test Case 3
        approvedApplication = new IPOApplication();
        approvedApplication.setCustomer(customer3);
        approvedApplication.setCompany("HealthPlus");
        approvedApplication.setNumberOfShares(10);
        approvedApplication.setAmount(5000.0);
        approvedApplication.setDocument("H");
        approvedApplication.setApproved(true);
        
        // Create rejected application for Test Case 4
        rejectedApplication = new IPOApplication();
        rejectedApplication.setCustomer(customer4);
        rejectedApplication.setCompany("Health");
        rejectedApplication.setNumberOfShares(10);
        rejectedApplication.setAmount(5000.0);
        rejectedApplication.setDocument("H");
        rejectedApplication.setRejected(true);
        
        // Create pending application for ineligible customer for Test Case 5
        IPOApplication pendingApplication5 = new IPOApplication();
        pendingApplication5.setCustomer(ineligibleCustomer);
        pendingApplication5.setCompany("Cloud");
        pendingApplication5.setNumberOfShares(10);
        pendingApplication5.setAmount(5000.0);
        pendingApplication5.setDocument("H");
        
        // Add applications to bank system
        bankSystem.getApplications().add(pendingApplication);
        bankSystem.getApplications().add(pendingApplication2);
        bankSystem.getApplications().add(approvedApplication);
        bankSystem.getApplications().add(rejectedApplication);
        bankSystem.getApplications().add(pendingApplication5);
        
        // Add applications to customers
        eligibleCustomer.getApplications().add(pendingApplication);
        customer2.getApplications().add(pendingApplication2);
        customer3.getApplications().add(approvedApplication);
        customer4.getApplications().add(rejectedApplication);
        ineligibleCustomer.getApplications().add(pendingApplication5);
    }

    @Test
    public void testCase1_ApprovePendingRequest() {
        // Test Case 1: "Approve pending request"
        // Input: Bank approves application "APP-1001" for company "SolarMax"
        // Expected Output: True (status changes to APPROVAL)
        
        // Execute the approval process
        boolean result = bankSystem.processApplication(pendingApplication, true);
        
        // Verify the result is true
        assertTrue("Application should be successfully approved", result);
        
        // Verify the application status changed to approved
        assertTrue("Application should be approved", pendingApplication.isApproved());
        assertFalse("Application should not be rejected", pendingApplication.isRejected());
        assertTrue("Application should be reviewed", pendingApplication.isReviewed());
    }

    @Test
    public void testCase2_RejectPendingRequest() {
        // Test Case 2: "Reject pending request"
        // Input: Bank rejects application "APP-1002" for "HealthPlus"
        // Expected Output: True (status changes to REJECTED)
        
        // Find the pending application for HealthPlus
        IPOApplication healthPlusApplication = bankSystem.getApplications().stream()
                .filter(app -> "HealthPlus".equals(app.getCompany()) && !app.isReviewed())
                .findFirst()
                .orElse(null);
        
        assertNotNull("Should find pending HealthPlus application", healthPlusApplication);
        
        // Execute the rejection process
        boolean result = bankSystem.processApplication(healthPlusApplication, false);
        
        // Verify the result is true
        assertTrue("Application should be successfully rejected", result);
        
        // Verify the application status changed to rejected
        assertFalse("Application should not be approved", healthPlusApplication.isApproved());
        assertTrue("Application should be rejected", healthPlusApplication.isRejected());
        assertTrue("Application should be reviewed", healthPlusApplication.isReviewed());
    }

    @Test
    public void testCase3_ApproveAlreadyApprovedRecord() {
        // Test Case 3: "Approve already approved record"
        // Input: Bank attempts to re-approve application "APP-1003"
        // Expected Output: False (cannot modify approved applications)
        
        // Execute the approval process on already approved application
        boolean result = bankSystem.processApplication(approvedApplication, true);
        
        // Verify the result is false
        assertFalse("Should not be able to approve already approved application", result);
        
        // Verify the application status remains approved
        assertTrue("Application should remain approved", approvedApplication.isApproved());
        assertFalse("Application should not be rejected", approvedApplication.isRejected());
        assertTrue("Application should remain reviewed", approvedApplication.isReviewed());
    }

    @Test
    public void testCase4_RejectAlreadyRejectedRecord() {
        // Test Case 4: "Reject already rejected record"
        // Input: Bank tries to reject application "APP-1004"
        // Expected Output: False (cannot modify final decisions)
        
        // Execute the rejection process on already rejected application
        boolean result = bankSystem.processApplication(rejectedApplication, false);
        
        // Verify the result is false
        assertFalse("Should not be able to reject already rejected application", result);
        
        // Verify the application status remains rejected
        assertFalse("Application should not be approved", rejectedApplication.isApproved());
        assertTrue("Application should remain rejected", rejectedApplication.isRejected());
        assertTrue("Application should remain reviewed", rejectedApplication.isReviewed());
    }

    @Test
    public void testCase5_ApproveRecordTiedToIneligibleCustomer() {
        // Test Case 5: "Approve record tied to ineligible customer"
        // Input: Bank processes application "APP-1005"
        // Expected Output: False (must maintain eligibility throughout process)
        
        // Find the pending application for ineligible customer
        IPOApplication ineligibleApplication = bankSystem.getApplications().stream()
                .filter(app -> "Cloud".equals(app.getCompany()) && app.getCustomer() == ineligibleCustomer)
                .findFirst()
                .orElse(null);
        
        assertNotNull("Should find pending Cloud application for ineligible customer", ineligibleApplication);
        
        // Execute the approval process on application with ineligible customer
        boolean result = bankSystem.processApplication(ineligibleApplication, true);
        
        // Verify the result is false
        assertFalse("Should not be able to approve application for ineligible customer", result);
        
        // Verify the application status remains unchanged (not reviewed)
        assertFalse("Application should not be approved", ineligibleApplication.isApproved());
        assertFalse("Application should not be rejected", ineligibleApplication.isRejected());
        assertFalse("Application should not be reviewed", ineligibleApplication.isReviewed());
    }
}