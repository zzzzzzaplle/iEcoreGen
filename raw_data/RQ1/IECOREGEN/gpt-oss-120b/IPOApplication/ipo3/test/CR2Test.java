package edu.ipo.ipo3.test;

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

    // Test objects
    private Customer customer1, customer2, customer3, customer4, customer5;
    private Company company1, company2, company3, company4, company5;
    private Document document1, document2, document3, document4, document5;
    private Application app1, app2, app3, app4, app5;

    @Before
    public void setUp() {
        // Initialize all required objects using the Ecore factory pattern
        customer1 = IpoFactory.eINSTANCE.createCustomer();
        customer1.setName("Michael");
        customer1.setSurname("Brown");
        customer1.setEmail("m.brown@example.com");
        customer1.setTelephone("555-1122");
        customer1.setCanApplyForIPO(true);

        customer2 = IpoFactory.eINSTANCE.createCustomer();
        customer2.setName("Olivia");
        customer2.setSurname("Lee");
        customer2.setEmail("olivia.l@example.com");
        customer2.setTelephone("555-3344");
        customer2.setCanApplyForIPO(true);

        customer3 = IpoFactory.eINSTANCE.createCustomer();
        customer3.setName("Daniel");
        customer3.setSurname("Kim");
        customer3.setEmail("d.kim@example.com");
        customer3.setTelephone("555-5566");
        customer3.setCanApplyForIPO(true);

        customer4 = IpoFactory.eINSTANCE.createCustomer();
        customer4.setName("Sophie");
        customer4.setSurname("Zhang");
        customer4.setEmail("s.zhang@example.com");
        customer4.setTelephone("555-7788");
        customer4.setCanApplyForIPO(true);

        customer5 = IpoFactory.eINSTANCE.createCustomer();
        customer5.setName("William");
        customer5.setSurname("Wang");
        customer5.setEmail("will.w@example.com");
        customer5.setTelephone("555-9900");
        customer5.setCanApplyForIPO(false); // Ineligible customer

        company1 = IpoFactory.eINSTANCE.createCompany();
        company1.setName("SolarMax");
        company1.setEmail("solarmax@gmail.com");

        company2 = IpoFactory.eINSTANCE.createCompany();
        company2.setName("HealthPlus");
        company2.setEmail("healthplus@gmail.com");

        company3 = IpoFactory.eINSTANCE.createCompany();
        company3.setName("HealthPlus");
        company3.setEmail("healthplus@gmail.com");

        company4 = IpoFactory.eINSTANCE.createCompany();
        company4.setName("Health");
        company4.setEmail("health@gmail.com");

        company5 = IpoFactory.eINSTANCE.createCompany();
        company5.setName("Cloud");
        company5.setEmail("Cloud@gmail.com");

        document1 = IpoFactory.eINSTANCE.createDocument();
        document2 = IpoFactory.eINSTANCE.createDocument();
        document3 = IpoFactory.eINSTANCE.createDocument();
        document4 = IpoFactory.eINSTANCE.createDocument();
        document5 = IpoFactory.eINSTANCE.createDocument();

        // Create applications
        app1 = IpoFactory.eINSTANCE.createApplication();
        app1.setShare(10);
        app1.setAmountOfMoney(200.0);
        app1.setCompany(company1);
        app1.setAllowance(document1);
        app1.setStatus(ApplicationStatus.PENDING);
        app1.setCustomer(customer1);

        app2 = IpoFactory.eINSTANCE.createApplication();
        app2.setShare(10);
        app2.setAmountOfMoney(5000.0);
        app2.setCompany(company2);
        app2.setAllowance(document2);
        app2.setStatus(ApplicationStatus.PENDING);
        app2.setCustomer(customer2);

        app3 = IpoFactory.eINSTANCE.createApplication();
        app3.setShare(10);
        app3.setAmountOfMoney(5000.0);
        app3.setCompany(company3);
        app3.setAllowance(document3);
        app3.setStatus(ApplicationStatus.APPROVAL); // Already approved
        app3.setCustomer(customer3);

        app4 = IpoFactory.eINSTANCE.createApplication();
        app4.setShare(10);
        app4.setAmountOfMoney(5000.0);
        app4.setCompany(company4);
        app4.setAllowance(document4);
        app4.setStatus(ApplicationStatus.REJECTED); // Already rejected
        app4.setCustomer(customer4);

        app5 = IpoFactory.eINSTANCE.createApplication();
        app5.setShare(10);
        app5.setAmountOfMoney(5000.0);
        app5.setCompany(company5);
        app5.setAllowance(document5);
        app5.setStatus(ApplicationStatus.PENDING);
        app5.setCustomer(customer5);
    }

    @Test
    public void testCase1_approvePendingRequest() {
        // Test Case 1: Approve pending request
        // Input: Bank approves application for company "SolarMax"
        // Setup: Customer "C007" with pending application
        // Expected Output: True (status changes to APPROVAL)

        boolean result = app1.approve();

        assertTrue("Approving a pending application from an eligible customer should return true", result);
        assertEquals("Application status should change to APPROVAL", ApplicationStatus.APPROVAL, app1.getStatus());
        
        // Check that two emails were created
        assertEquals("Two emails should be created", 2, app1.getEmails().size());
        
        // Check email contents
        boolean foundCustomerEmail = false;
        boolean foundCompanyEmail = false;
        for (Email email : app1.getEmails()) {
            if (email.getReceiver().equals(customer1.getEmail())) {
                foundCustomerEmail = true;
                assertTrue("Customer email content should not be null", email.getContent() != null);
                assertTrue("Customer email content should contain customer name", email.getContent().contains("Michael Brown"));
            }
            if (email.getReceiver().equals(company1.getEmail())) {
                foundCompanyEmail = true;
                assertTrue("Company email content should not be null", email.getContent() != null);
                assertTrue("Company email content should contain company name", email.getContent().contains("SolarMax"));
            }
        }
        assertTrue("Should have sent email to customer", foundCustomerEmail);
        assertTrue("Should have sent email to company", foundCompanyEmail);
    }

    @Test
    public void testCase2_rejectPendingRequest() {
        // Test Case 2: Reject pending request
        // Input: Bank rejects application for "HealthPlus"
        // Setup: Customer "C008" with pending application
        // Expected Output: True (status changes to REJECTED)

        boolean result = app2.reject();

        assertTrue("Rejecting a pending application should return true", result);
        assertEquals("Application status should change to REJECTED", ApplicationStatus.REJECTED, app2.getStatus());
        
        // Check that one email was created
        assertEquals("One rejection email should be created", 1, app2.getEmails().size());
        
        Email rejectionEmail = app2.getEmails().get(0);
        assertEquals("Email should be sent to customer", customer2.getEmail(), rejectionEmail.getReceiver());
        assertTrue("Email content should mention company name", rejectionEmail.getContent().contains("HealthPlus"));
        assertTrue("Email content should indicate rejection", rejectionEmail.getContent().contains("rejected"));
    }

    @Test
    public void testCase3_approveAlreadyApprovedRecord() {
        // Test Case 3: Approve already approved record
        // Input: Bank attempts to re-approve application "APP-1003"
        // Setup: Application already has status APPROVAL
        // Expected Output: False (cannot modify approved applications)

        boolean result = app3.approve();

        assertFalse("Approving an already approved application should return false", result);
        assertEquals("Application status should remain APPROVAL", ApplicationStatus.APPROVAL, app3.getStatus());
    }

    @Test
    public void testCase4_rejectAlreadyRejectedRecord() {
        // Test Case 4: Reject already rejected record
        // Input: Bank tries to reject application "APP-1004"
        // Setup: Application already has status REJECTED
        // Expected Output: False (cannot modify final decisions)

        boolean result = app4.reject();

        assertFalse("Rejecting an already rejected application should return false", result);
        assertEquals("Application status should remain REJECTED", ApplicationStatus.REJECTED, app4.getStatus());
    }

    @Test
    public void testCase5_approveRecordTiedToIneligibleCustomer() {
        // Test Case 5: Approve record tied to ineligible customer
        // Input: Bank processes application "APP-1005"
        // Setup: Customer cannot apply for IPO
        // Expected Output: False (must maintain eligibility throughout process)

        boolean result = app5.approve();

        assertFalse("Approving an application from an ineligible customer should return false", result);
        assertNotEquals("Application status should not change to APPROVAL", ApplicationStatus.APPROVAL, app5.getStatus());
    }
}