import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    private BankSystem bankSystem;
    private Customer customer1;
    private Customer customer2;
    private Customer customer3;
    private Customer customer4;
    private Customer customer5;
    private Document documentS;
    private Document documentH;
    private Document documentCloud;
    private IPOApplication application1;
    private IPOApplication application2;
    private IPOApplication application3;
    private IPOApplication application4;
    private IPOApplication application5;
    
    @Before
    public void setUp() {
        bankSystem = new BankSystem();
        
        // Initialize documents
        documentS = new Document();
        documentS.setContent("SolarMax Document");
        
        documentH = new Document();
        documentH.setContent("HealthPlus Document");
        
        documentCloud = new Document();
        documentCloud.setContent("Cloud Document");
        
        // Setup Customer 1 (Michael Brown) - eligible, pending application
        customer1 = new Customer();
        customer1.setName("Michael");
        customer1.setSurname("Brown");
        customer1.setEmail("m.brown@example.com");
        customer1.setTelephone("555-1122");
        customer1.setEligibleForIPO(true);
        
        application1 = new IPOApplication();
        application1.setCustomer(customer1);
        application1.setCompany("SolarMax");
        application1.setNumberOfShares(10);
        application1.setAmount(200.0);
        application1.setDocument(documentS);
        // Application is pending (default state)
        
        customer1.getApplications().add(application1);
        bankSystem.getApplications().add(application1);
        
        // Setup Customer 2 (Olivia Lee) - eligible, pending application
        customer2 = new Customer();
        customer2.setName("Olivia");
        customer2.setSurname("Lee");
        customer2.setEmail("olivia.l@example.com");
        customer2.setTelephone("555-3344");
        customer2.setEligibleForIPO(true);
        
        application2 = new IPOApplication();
        application2.setCustomer(customer2);
        application2.setCompany("HealthPlus");
        application2.setNumberOfShares(10);
        application2.setAmount(5000.0);
        application2.setDocument(documentH);
        // Application is pending (default state)
        
        customer2.getApplications().add(application2);
        bankSystem.getApplications().add(application2);
        
        // Setup Customer 3 (Daniel Kim) - eligible, already approved application
        customer3 = new Customer();
        customer3.setName("Daniel");
        customer3.setSurname("Kim");
        customer3.setEmail("d.kim@example.com");
        customer3.setTelephone("555-5566");
        customer3.setEligibleForIPO(true);
        
        application3 = new IPOApplication();
        application3.setCustomer(customer3);
        application3.setCompany("HealthPlus");
        application3.setNumberOfShares(10);
        application3.setAmount(5000.0);
        application3.setDocument(documentH);
        application3.setApproved(true); // Already approved
        
        customer3.getApplications().add(application3);
        bankSystem.getApplications().add(application3);
        
        // Setup Customer 4 (Sophie Zhang) - eligible, already rejected application
        customer4 = new Customer();
        customer4.setName("Sophie");
        customer4.setSurname("Zhang");
        customer4.setEmail("s.zhang@example.com");
        customer4.setTelephone("555-7788");
        customer4.setEligibleForIPO(true);
        
        application4 = new IPOApplication();
        application4.setCustomer(customer4);
        application4.setCompany("Health");
        application4.setNumberOfShares(10);
        application4.setAmount(5000.0);
        application4.setDocument(documentH);
        application4.setRejected(true); // Already rejected
        
        customer4.getApplications().add(application4);
        bankSystem.getApplications().add(application4);
        
        // Setup Customer 5 (William Wang) - ineligible, pending application
        customer5 = new Customer();
        customer5.setName("William");
        customer5.setSurname("Wang");
        customer5.setEmail("will.w@example.com");
        customer5.setTelephone("555-9900");
        customer5.setEligibleForIPO(false); // Not eligible
        
        application5 = new IPOApplication();
        application5.setCustomer(customer5);
        application5.setCompany("Cloud");
        application5.setNumberOfShares(10);
        application5.setAmount(5000.0);
        application5.setDocument(documentCloud);
        // Application is pending (default state)
        
        customer5.getApplications().add(application5);
        bankSystem.getApplications().add(application5);
    }
    
    @Test
    public void testCase1_ApprovePendingRequest() {
        // Test Case 1: "Approve pending request"
        // Input: Bank approves application "APP-1001" for company "SolarMax"
        // Setup: Customer "C007" (Michael Brown) with pending application
        // Expected Output: True (status changes to APPROVAL)
        
        boolean result = bankSystem.approveOrRejectApplication(application1, true);
        
        assertTrue("Application should be approved successfully", result);
        assertTrue("Application status should be approved", application1.isApproved());
        assertFalse("Application should not be rejected", application1.isRejected());
        assertTrue("Application should be reviewed", application1.isReviewed());
    }
    
    @Test
    public void testCase2_RejectPendingRequest() {
        // Test Case 2: "Reject pending request"
        // Input: Bank rejects application "APP-1002" for "HealthPlus"
        // Setup: Customer "C008" (Olivia Lee) with pending application
        // Expected Output: True (status changes to REJECTED)
        
        boolean result = bankSystem.approveOrRejectApplication(application2, false);
        
        assertTrue("Application should be rejected successfully", result);
        assertFalse("Application should not be approved", application2.isApproved());
        assertTrue("Application status should be rejected", application2.isRejected());
        assertTrue("Application should be reviewed", application2.isReviewed());
    }
    
    @Test
    public void testCase3_ApproveAlreadyApprovedRecord() {
        // Test Case 3: "Approve already approved record"
        // Input: Bank attempts to re-approve application "APP-1003"
        // Setup: Customer "C009" (Daniel Kim) with already approved application
        // Expected Output: False (cannot modify approved applications)
        
        boolean result = bankSystem.approveOrRejectApplication(application3, true);
        
        assertFalse("Should not be able to approve an already approved application", result);
        assertTrue("Application should remain approved", application3.isApproved());
        assertFalse("Application should not be rejected", application3.isRejected());
        assertTrue("Application should remain reviewed", application3.isReviewed());
    }
    
    @Test
    public void testCase4_RejectAlreadyRejectedRecord() {
        // Test Case 4: "Reject already rejected record"
        // Input: Bank tries to reject application "APP-1004"
        // Setup: Customer "C010" (Sophie Zhang) with already rejected application
        // Expected Output: False (cannot modify final decisions)
        
        boolean result = bankSystem.approveOrRejectApplication(application4, false);
        
        assertFalse("Should not be able to reject an already rejected application", result);
        assertFalse("Application should not be approved", application4.isApproved());
        assertTrue("Application should remain rejected", application4.isRejected());
        assertTrue("Application should remain reviewed", application4.isReviewed());
    }
    
    @Test
    public void testCase5_ApproveRecordTiedToIneligibleCustomer() {
        // Test Case 5: "Approve record tied to ineligible customer"
        // Input: Bank processes application "APP-1005"
        // Setup: Customer "C011" (William Wang) is ineligible with pending application
        // Expected Output: False (must maintain eligibility throughout process)
        
        boolean result = bankSystem.approveOrRejectApplication(application5, true);
        
        assertFalse("Should not be able to approve application for ineligible customer", result);
        assertFalse("Application should not be approved", application5.isApproved());
        assertFalse("Application should not be rejected", application5.isRejected());
        assertFalse("Application should remain unreviewed", application5.isReviewed());
        assertTrue("Application should remain pending", application5.isPending());
    }
}