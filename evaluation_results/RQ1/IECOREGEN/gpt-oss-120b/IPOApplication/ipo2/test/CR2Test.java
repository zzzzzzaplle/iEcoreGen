package edu.ipo.ipo2.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.ipo.Application;
import edu.ipo.ApplicationStatus;
import edu.ipo.Company;
import edu.ipo.Customer;
import edu.ipo.Document;
import edu.ipo.Email;
import edu.ipo.IpoFactory;

public class CR2Test {
    
    private Customer customer1, customer2, customer3, customer4, customer5;
    private Company company1, company2, company3, company4;
    private Document document1, document2, document3;
    private Application app1, app2, app3, app4, app5;
    
    @Before
    public void setUp() {
        // Create documents
        document1 = IpoFactory.eINSTANCE.createDocument();
        document2 = IpoFactory.eINSTANCE.createDocument();
        document3 = IpoFactory.eINSTANCE.createDocument();
        
        // Create companies
        company1 = IpoFactory.eINSTANCE.createCompany();
        company1.setName("SolarMax");
        company1.setEmail("solarmax@gmail.com");
        
        company2 = IpoFactory.eINSTANCE.createCompany();
        company2.setName("HealthPlus");
        company2.setEmail("healthplus@gmail.com");
        
        company3 = IpoFactory.eINSTANCE.createCompany();
        company3.setName("Health");
        company3.setEmail("health@gmail.com");
        
        company4 = IpoFactory.eINSTANCE.createCompany();
        company4.setName("Cloud");
        company4.setEmail("Cloud@gmail.com");
        
        // Create customers and applications according to test specifications
        
        // Test Case 1: Customer "C007" - Michael Brown
        customer1 = IpoFactory.eINSTANCE.createCustomer();
        customer1.setName("Michael");
        customer1.setSurname("Brown");
        customer1.setEmail("m.brown@example.com");
        customer1.setTelephone("555-1122");
        customer1.setCanApplyForIPO(true);
        
        app1 = IpoFactory.eINSTANCE.createApplication();
        app1.setShare(10);
        app1.setAmountOfMoney(200.0);
        app1.setStatus(ApplicationStatus.PENDING);
        app1.setCompany(company1);
        app1.setAllowance(document1);
        app1.setCustomer(customer1);
        
        // Test Case 2: Customer "C008" - Olivia Lee
        customer2 = IpoFactory.eINSTANCE.createCustomer();
        customer2.setName("Olivia");
        customer2.setSurname("Lee");
        customer2.setEmail("olivia.l@example.com");
        customer2.setTelephone("555-3344");
        customer2.setCanApplyForIPO(true);
        
        app2 = IpoFactory.eINSTANCE.createApplication();
        app2.setShare(10);
        app2.setAmountOfMoney(5000.0);
        app2.setStatus(ApplicationStatus.PENDING);
        app2.setCompany(company2);
        app2.setAllowance(document2);
        app2.setCustomer(customer2);
        
        // Test Case 3: Customer "C009" - Daniel Kim with already approved application
        customer3 = IpoFactory.eINSTANCE.createCustomer();
        customer3.setName("Daniel");
        customer3.setSurname("Kim");
        customer3.setEmail("d.kim@example.com");
        customer3.setTelephone("555-5566");
        customer3.setCanApplyForIPO(true);
        
        app3 = IpoFactory.eINSTANCE.createApplication();
        app3.setShare(10);
        app3.setAmountOfMoney(5000.0);
        app3.setStatus(ApplicationStatus.APPROVAL); // Already approved
        app3.setCompany(company2);
        app3.setAllowance(document2);
        app3.setCustomer(customer3);
        
        // Test Case 4: Customer "C010" - Sophie Zhang with already rejected application
        customer4 = IpoFactory.eINSTANCE.createCustomer();
        customer4.setName("Sophie");
        customer4.setSurname("Zhang");
        customer4.setEmail("s.zhang@example.com");
        customer4.setTelephone("555-7788");
        customer4.setCanApplyForIPO(true);
        
        app4 = IpoFactory.eINSTANCE.createApplication();
        app4.setShare(10);
        app4.setAmountOfMoney(5000.0);
        app4.setStatus(ApplicationStatus.REJECTED); // Already rejected
        app4.setCompany(company3);
        app4.setAllowance(document2);
        app4.setCustomer(customer4);
        
        // Test Case 5: Customer "C011" - William Wang (ineligible)
        customer5 = IpoFactory.eINSTANCE.createCustomer();
        customer5.setName("William");
        customer5.setSurname("Wang");
        customer5.setEmail("will.w@example.com");
        customer5.setTelephone("555-9900");
        customer5.setCanApplyForIPO(false); // Not eligible
        
        app5 = IpoFactory.eINSTANCE.createApplication();
        app5.setShare(10);
        app5.setAmountOfMoney(5000.0);
        app5.setStatus(ApplicationStatus.PENDING);
        app5.setCompany(company4);
        app5.setAllowance(document3);
        app5.setCustomer(customer5);
    }
    
    @Test
    public void testCase1_approvePendingRequest() {
        // Setup is done in @Before method
        // Customer "C007" (Michael Brown) has a pending application for SolarMax
        
        // Execute: Bank approves the application
        boolean result = app1.approve();
        
        // Verify: Application is approved and emails are sent
        assertTrue("Approving a pending application for an eligible customer should return true", result);
        assertEquals("Application status should be APPROVAL after approval", ApplicationStatus.APPROVAL, app1.getStatus());
        assertEquals("Two emails should be created after approval", 2, app1.getEmails().size());
        
        // Check that emails were sent to both customer and company
        boolean customerEmailFound = false;
        boolean companyEmailFound = false;
        
        for (Email email : app1.getEmails()) {
            if (email.getReceiver().equals(customer1.getEmail())) {
                customerEmailFound = true;
                assertTrue("Customer email content should not be empty", email.getContent() != null && !email.getContent().isEmpty());
            } else if (email.getReceiver().equals(company1.getEmail())) {
                companyEmailFound = true;
                assertTrue("Company email content should not be empty", email.getContent() != null && !email.getContent().isEmpty());
            }
        }
        
        assertTrue("Email should be sent to customer", customerEmailFound);
        assertTrue("Email should be sent to company", companyEmailFound);
    }
    
    @Test
    public void testCase2_rejectPendingRequest() {
        // Setup is done in @Before method
        // Customer "C008" (Olivia Lee) has a pending application for HealthPlus
        
        // Execute: Bank rejects the application
        boolean result = app2.reject();
        
        // Verify: Application is rejected and rejection email is sent
        assertTrue("Rejecting a pending application should return true", result);
        assertEquals("Application status should be REJECTED after rejection", ApplicationStatus.REJECTED, app2.getStatus());
        assertEquals("One email should be created after rejection", 1, app2.getEmails().size());
        
        // Check that rejection email was sent to customer
        Email rejectionEmail = app2.getEmails().get(0);
        assertEquals("Email should be sent to customer", customer2.getEmail(), rejectionEmail.getReceiver());
        assertTrue("Rejection email content should not be empty", rejectionEmail.getContent() != null && !rejectionEmail.getContent().isEmpty());
        assertTrue("Rejection email should contain rejection message", rejectionEmail.getContent().contains("rejected"));
    }
    
    @Test
    public void testCase3_approveAlreadyApprovedRecord() {
        // Setup is done in @Before method
        // Customer "C009" (Daniel Kim) has an already approved application APP-1003
        
        // Execute: Bank attempts to re-approve the application
        boolean result = app3.approve();
        
        // Verify: Cannot approve an already approved application
        assertFalse("Approving an already approved application should return false", result);
        assertEquals("Application status should remain APPROVAL", ApplicationStatus.APPROVAL, app3.getStatus());
        assertEquals("No additional emails should be sent", 0, app3.getEmails().size());
    }
    
    @Test
    public void testCase4_rejectAlreadyRejectedRecord() {
        // Setup is done in @Before method
        // Customer "C010" (Sophie Zhang) has an already rejected application APP-1004
        
        // Execute: Bank attempts to reject the application again
        boolean result = app4.reject();
        
        // Verify: Cannot reject an already rejected application
        assertFalse("Rejecting an already rejected application should return false", result);
        assertEquals("Application status should remain REJECTED", ApplicationStatus.REJECTED, app4.getStatus());
        assertEquals("No additional emails should be sent", 0, app4.getEmails().size());
    }
    
    @Test
    public void testCase5_approveRecordTiedToIneligibleCustomer() {
        // Setup is done in @Before method
        // Customer "C011" (William Wang) is not eligible for IPO application APP-1005
        
        // Execute: Bank attempts to approve the application
        boolean result = app5.approve();
        
        // Verify: Cannot approve application for ineligible customer
        assertFalse("Approving an application for an ineligible customer should return false", result);
        assertEquals("Application status should remain PENDING", ApplicationStatus.PENDING, app5.getStatus());
        assertEquals("No emails should be sent", 0, app5.getEmails().size());
    }
}