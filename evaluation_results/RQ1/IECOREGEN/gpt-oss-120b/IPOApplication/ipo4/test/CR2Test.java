package edu.ipo.ipo4.test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import edu.ipo.Application;
import edu.ipo.ApplicationStatus;
import edu.ipo.Company;
import edu.ipo.Customer;
import edu.ipo.Document;
import edu.ipo.Email;
import edu.ipo.IpoFactory;

public class CR2Test {

    private IpoFactory factory;

    @Before
    public void setUp() {
        factory = IpoFactory.eINSTANCE;
    }

    /**
     * Test Case 1: "Approve pending request"
     * Input: Bank approves application "APP-1001" for company "SolarMax"
     * Setup:
     * 1. Customer "C007" (named "Michael Brown", email "m.brown@example.com", phone "555-1122")
     * - Applied for 10 shares ($200) in "SolarMax" (email: solarmax@gmail.com)
     * - Uploaded document 'S'
     * - Can apply for IPO
     * 2. The customer is pending the application.
     * Expected Output: True (status changes to APPROVAL)
     */
    @Test
    public void testCase1_approvePendingRequest() {
        // Create customer
        Customer customer = factory.createCustomer();
        customer.setName("Michael");
        customer.setSurname("Brown");
        customer.setEmail("m.brown@example.com");
        customer.setTelephone("555-1122");
        customer.setCanApplyForIPO(true);

        // Create company
        Company company = factory.createCompany();
        company.setName("SolarMax");
        company.setEmail("solarmax@gmail.com");

        // Create document
        Document document = factory.createDocument();

        // Create application using customer's createApplication method
        boolean created = customer.createApplication(company, 10, 200.0, document);
        assertTrue("Application should be created successfully", created);
        
        // Get the created application
        Application application = customer.getApplications().get(0);
        assertEquals("Application should be in PENDING status", ApplicationStatus.PENDING, application.getStatus());

        // Approve the application
        boolean result = application.approve();
        
        // Verify results
        assertTrue("Approve operation should succeed", result);
        assertEquals("Application status should be APPROVAL", ApplicationStatus.APPROVAL, application.getStatus());
        assertEquals("Should have 2 emails", 2, application.getEmails().size());
        
        // Verify email content
        Email customerEmail = application.getEmails().get(0);
        Email companyEmail = application.getEmails().get(1);
        
        assertEquals("Customer email should be sent to customer", "m.brown@example.com", customerEmail.getReceiver());
        assertEquals("Company email should be sent to company", "solarmax@gmail.com", companyEmail.getReceiver());
        assertNotNull("Email content should not be null", customerEmail.getContent());
        assertNotNull("Email content should not be null", companyEmail.getContent());
    }

    /**
     * Test Case 2: "Reject pending request"
     * Input: Bank rejects application "APP-1002" for "HealthPlus"
     * Setup:
     * 1. Customer "C008" (named "Olivia Lee", email "olivia.l@example.com", phone "555-3344")
     * - Applied for 10 shares ($5000) in "HealthPlus" (email: healthplus@gmail.com)
     * - Uploaded document 'H'
     * - Can apply for IPO
     * 2. The customer is pending the application.
     * Expected Output: True (status changes to REJECTED)
     */
    @Test
    public void testCase2_rejectPendingRequest() {
        // Create customer
        Customer customer = factory.createCustomer();
        customer.setName("Olivia");
        customer.setSurname("Lee");
        customer.setEmail("olivia.l@example.com");
        customer.setTelephone("555-3344");
        customer.setCanApplyForIPO(true);

        // Create company
        Company company = factory.createCompany();
        company.setName("HealthPlus");
        company.setEmail("healthplus@gmail.com");

        // Create document
        Document document = factory.createDocument();

        // Create application using customer's createApplication method
        boolean created = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", created);
        
        // Get the created application
        Application application = customer.getApplications().get(0);
        assertEquals("Application should be in PENDING status", ApplicationStatus.PENDING, application.getStatus());

        // Reject the application
        boolean result = application.reject();
        
        // Verify results
        assertTrue("Reject operation should succeed", result);
        assertEquals("Application status should be REJECTED", ApplicationStatus.REJECTED, application.getStatus());
        assertEquals("Should have 1 email", 1, application.getEmails().size());
        
        // Verify email content
        Email rejectionEmail = application.getEmails().get(0);
        assertEquals("Rejection email should be sent to customer", "olivia.l@example.com", rejectionEmail.getReceiver());
        assertTrue("Email content should contain rejection message", rejectionEmail.getContent().contains("has been rejected"));
    }

    /**
     * Test Case 3: "Approve already approved record"
     * Input: Bank attempts to re-approve application "APP-1003"
     * Setup:
     * 1. Customer "C009" (named "Daniel Kim", email "d.kim@example.com", phone "555-5566")
     * - Existing approved application "APP-1003" for "HealthPlus" : Applied for 10 shares ($5000) in "HealthPlus" (email: healthplus@gmail.com)
     * - Status = APPROVAL
     * - Document 'H' on file
     * Expected Output: False (cannot modify approved applications)
     */
    @Test
    public void testCase3_approveAlreadyApprovedRecord() {
        // Create customer
        Customer customer = factory.createCustomer();
        customer.setName("Daniel");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-5566");
        customer.setCanApplyForIPO(true);

        // Create company
        Company company = factory.createCompany();
        company.setName("HealthPlus");
        company.setEmail("healthplus@gmail.com");

        // Create document
        Document document = factory.createDocument();

        // Create application using customer's createApplication method
        boolean created = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", created);
        
        // Get the created application
        Application application = customer.getApplications().get(0);
        
        // Manually set status to APPROVAL to simulate existing approved application
        application.setStatus(ApplicationStatus.APPROVAL);
        
        // Try to approve again
        boolean result = application.approve();
        
        // Verify results
        assertFalse("Approve operation should fail", result);
        assertEquals("Application status should remain APPROVAL", ApplicationStatus.APPROVAL, application.getStatus());
    }

    /**
     * Test Case 4: "Reject already rejected record"
     * Input: Bank tries to reject application "APP-1004"
     * Setup:
     * 1. Customer "C010" (named "Sophie Zhang", email "s.zhang@example.com", phone "555-7788")
     * - Previous application "APP-1004" marked REJECTED : Applied for 10 shares ($5000) in "Health" (email: health@gmail.com)
     * - Rejection reason: "Insufficient documentation"
     * Expected Output: False (cannot modify final decisions)
     */
    @Test
    public void testCase4_rejectAlreadyRejectedRecord() {
        // Create customer
        Customer customer = factory.createCustomer();
        customer.setName("Sophie");
        customer.setSurname("Zhang");
        customer.setEmail("s.zhang@example.com");
        customer.setTelephone("555-7788");
        customer.setCanApplyForIPO(true);

        // Create company
        Company company = factory.createCompany();
        company.setName("Health");
        company.setEmail("health@gmail.com");

        // Create document
        Document document = factory.createDocument();

        // Create application using customer's createApplication method
        boolean created = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", created);
        
        // Get the created application
        Application application = customer.getApplications().get(0);
        
        // Manually set status to REJECTED to simulate existing rejected application
        application.setStatus(ApplicationStatus.REJECTED);
        
        // Try to reject again
        boolean result = application.reject();
        
        // Verify results
        assertFalse("Reject operation should fail", result);
        assertEquals("Application status should remain REJECTED", ApplicationStatus.REJECTED, application.getStatus());
    }

    /**
     * Test Case 5: "Approve record tied to ineligible customer"
     * Input: Bank processes application "APP-1005"
     * Setup:
     * 1. Customer "C011" (named "William Wang", email "will.w@example.com", phone "555-9900")
     * - Can not apply for IPO
     * - Existing pending application "APP-1005" : Applied for 10 shares ($5000) in "Cloud" (email: Cloud@gmail.com)
     * - Eligibility revoked due to KYC expiration
     * Expected Output: False (must maintain eligibility throughout process)
     */
    @Test
    public void testCase5_approveRecordTiedToIneligibleCustomer() {
        // Create customer
        Customer customer = factory.createCustomer();
        customer.setName("William");
        customer.setSurname("Wang");
        customer.setEmail("will.w@example.com");
        customer.setTelephone("555-9900");
        customer.setCanApplyForIPO(false); // Not eligible

        // Create company
        Company company = factory.createCompany();
        company.setName("Cloud");
        company.setEmail("Cloud@gmail.com");

        // Create document
        Document document = factory.createDocument();

        // Create application using customer's createApplication method
        boolean created = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", created);
        
        // Get the created application
        Application application = customer.getApplications().get(0);
        assertEquals("Application should be in PENDING status", ApplicationStatus.PENDING, application.getStatus());

        // Try to approve the application
        boolean result = application.approve();
        
        // Verify results
        assertFalse("Approve operation should fail", result);
        assertEquals("Application status should remain PENDING", ApplicationStatus.PENDING, application.getStatus());
        assertEquals("Should have no emails", 0, application.getEmails().size());
    }
}