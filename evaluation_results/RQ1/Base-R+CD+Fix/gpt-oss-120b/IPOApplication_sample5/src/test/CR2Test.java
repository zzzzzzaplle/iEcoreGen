import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Customer customer;
    private Company company;
    private Document document;
    private Application application;
    
    @Before
    public void setUp() {
        // Common setup that can be reused across tests
        customer = new Customer();
        company = new Company();
        document = new Document();
    }
    
    @Test
    public void testCase1_ApprovePendingRequest() {
        // Setup for Test Case 1: "Approve pending request"
        customer.setName("Michael");
        customer.setSurname("Brown");
        customer.setEmail("m.brown@example.com");
        customer.setTelephone("555-1122");
        customer.setCanApplyForIPO(true);
        
        company.setName("SolarMax");
        company.setEmail("solarmax@gmail.com");
        
        // Create application with pending status
        application = new Application(10, 200.0, customer, company, document);
        
        // Verify initial status is PENDING
        assertEquals(ApplicationStatus.PENDING, application.getStatus());
        
        // Execute approval
        boolean result = application.approve();
        
        // Verify approval succeeded and status changed to APPROVAL
        assertTrue("Approval should succeed for pending application", result);
        assertEquals("Application status should be APPROVAL after approval", 
                     ApplicationStatus.APPROVAL, application.getStatus());
    }
    
    @Test
    public void testCase2_RejectPendingRequest() {
        // Setup for Test Case 2: "Reject pending request"
        customer.setName("Olivia");
        customer.setSurname("Lee");
        customer.setEmail("olivia.l@example.com");
        customer.setTelephone("555-3344");
        customer.setCanApplyForIPO(true);
        
        company.setName("HealthPlus");
        company.setEmail("healthplus@gmail.com");
        
        // Create application with pending status
        application = new Application(10, 5000.0, customer, company, document);
        
        // Verify initial status is PENDING
        assertEquals(ApplicationStatus.PENDING, application.getStatus());
        
        // Execute rejection
        boolean result = application.reject();
        
        // Verify rejection succeeded and status changed to REJECTED
        assertTrue("Rejection should succeed for pending application", result);
        assertEquals("Application status should be REJECTED after rejection", 
                     ApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase3_ApproveAlreadyApprovedRecord() {
        // Setup for Test Case 3: "Approve already approved record"
        customer.setName("Daniel");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-5566");
        customer.setCanApplyForIPO(true);
        
        company.setName("HealthPlus");
        company.setEmail("healthplus@gmail.com");
        
        // Create application and set status to APPROVAL
        application = new Application(10, 5000.0, customer, company, document);
        application.setStatus(ApplicationStatus.APPROVAL);
        
        // Verify initial status is APPROVAL
        assertEquals(ApplicationStatus.APPROVAL, application.getStatus());
        
        // Attempt to approve already approved application
        boolean result = application.approve();
        
        // Verify approval fails for already approved application
        assertFalse("Approval should fail for already approved application", result);
        assertEquals("Application status should remain APPROVAL", 
                     ApplicationStatus.APPROVAL, application.getStatus());
    }
    
    @Test
    public void testCase4_RejectAlreadyRejectedRecord() {
        // Setup for Test Case 4: "Reject already rejected record"
        customer.setName("Sophie");
        customer.setSurname("Zhang");
        customer.setEmail("s.zhang@example.com");
        customer.setTelephone("555-7788");
        customer.setCanApplyForIPO(true);
        
        company.setName("Health");
        company.setEmail("health@gmail.com");
        
        // Create application and set status to REJECTED
        application = new Application(10, 5000.0, customer, company, document);
        application.setStatus(ApplicationStatus.REJECTED);
        
        // Verify initial status is REJECTED
        assertEquals(ApplicationStatus.REJECTED, application.getStatus());
        
        // Attempt to reject already rejected application
        boolean result = application.reject();
        
        // Verify rejection fails for already rejected application
        assertFalse("Rejection should fail for already rejected application", result);
        assertEquals("Application status should remain REJECTED", 
                     ApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase5_ApproveRecordTiedToIneligibleCustomer() {
        // Setup for Test Case 5: "Approve record tied to ineligible customer"
        customer.setName("William");
        customer.setSurname("Wang");
        customer.setEmail("will.w@example.com");
        customer.setTelephone("555-9900");
        customer.setCanApplyForIPO(false); // Customer is ineligible
        
        company.setName("Cloud");
        company.setEmail("Cloud@gmail.com");
        
        // Create application with pending status
        application = new Application(10, 5000.0, customer, company, document);
        
        // Verify initial status is PENDING
        assertEquals(ApplicationStatus.PENDING, application.getStatus());
        
        // Attempt to approve application for ineligible customer
        boolean result = application.approve();
        
        // Verify approval fails due to customer ineligibility
        assertFalse("Approval should fail for ineligible customer", result);
        assertEquals("Application status should remain PENDING", 
                     ApplicationStatus.PENDING, application.getStatus());
    }
}