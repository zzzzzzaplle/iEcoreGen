package edu.ipo.ipo1.test;

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
    
    private IpoFactory factory;
    
    @Before
    public void setUp() {
        factory = IpoFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_approvePendingRequest() {
        // Setup Customer "C007" (named "Michael Brown", email "m.brown@example.com", phone "555-1122")
        Customer customer = factory.createCustomer();
        customer.setName("Michael");
        customer.setSurname("Brown");
        customer.setEmail("m.brown@example.com");
        customer.setTelephone("555-1122");
        customer.setCanApplyForIPO(true);
        
        // Setup Company "SolarMax" (email: solarmax@gmail.com)
        Company company = factory.createCompany();
        company.setName("SolarMax");
        company.setEmail("solarmax@gmail.com");
        
        // Setup Document 'S'
        Document document = factory.createDocument();
        
        // Create pending application for 10 shares ($200)
        Application application = factory.createApplication();
        application.setShare(10);
        application.setAmountOfMoney(200.0);
        application.setStatus(ApplicationStatus.PENDING);
        application.setCompany(company);
        application.setAllowance(document);
        application.setCustomer(customer);
        
        // Add application to customer
        customer.getApplications().add(application);
        
        // Execute: Bank approves the application
        boolean result = application.approve();
        
        // Verify: True (status changes to APPROVAL)
        assertTrue("Approving a pending application should return true", result);
        assertEquals("Application status should be APPROVAL after approval", ApplicationStatus.APPROVAL, application.getStatus());
        
        // Verify that two emails were created (one to customer, one to company)
        assertEquals("Two emails should be created after approval", 2, application.getEmails().size());
        
        // Verify email content and receivers
        boolean foundCustomerEmail = false;
        boolean foundCompanyEmail = false;
        
        for (Email email : application.getEmails()) {
            if (email.getReceiver().equals(customer.getEmail())) {
                foundCustomerEmail = true;
                assertNotNull("Email content should not be null", email.getContent());
                assertTrue("Email content should contain customer information", email.getContent().contains("Michael Brown"));
                assertTrue("Email content should contain company information", email.getContent().contains("SolarMax"));
            } else if (email.getReceiver().equals(company.getEmail())) {
                foundCompanyEmail = true;
                assertNotNull("Email content should not be null", email.getContent());
                assertTrue("Email content should contain customer information", email.getContent().contains("Michael Brown"));
                assertTrue("Email content should contain company information", email.getContent().contains("SolarMax"));
            }
        }
        
        assertTrue("Should have email to customer", foundCustomerEmail);
        assertTrue("Should have email to company", foundCompanyEmail);
    }
    
    @Test
    public void testCase2_rejectPendingRequest() {
        // Setup Customer "C008" (named "Olivia Lee", email "olivia.l@example.com", phone "555-3344")
        Customer customer = factory.createCustomer();
        customer.setName("Olivia");
        customer.setSurname("Lee");
        customer.setEmail("olivia.l@example.com");
        customer.setTelephone("555-3344");
        customer.setCanApplyForIPO(true);
        
        // Setup Company "HealthPlus" (email: healthplus@gmail.com)
        Company company = factory.createCompany();
        company.setName("HealthPlus");
        company.setEmail("healthplus@gmail.com");
        
        // Setup Document 'H'
        Document document = factory.createDocument();
        
        // Create pending application for 10 shares ($5000)
        Application application = factory.createApplication();
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setStatus(ApplicationStatus.PENDING);
        application.setCompany(company);
        application.setAllowance(document);
        application.setCustomer(customer);
        
        // Add application to customer
        customer.getApplications().add(application);
        
        // Execute: Bank rejects the application
        boolean result = application.reject();
        
        // Verify: True (status changes to REJECTED)
        assertTrue("Rejecting a pending application should return true", result);
        assertEquals("Application status should be REJECTED after rejection", ApplicationStatus.REJECTED, application.getStatus());
        
        // Verify that one email was created (to customer)
        assertEquals("One email should be created after rejection", 1, application.getEmails().size());
        
        // Verify email content and receiver
        Email rejectionEmail = application.getEmails().get(0);
        assertEquals("Email should be sent to customer", customer.getEmail(), rejectionEmail.getReceiver());
        assertNotNull("Email content should not be null", rejectionEmail.getContent());
        assertTrue("Email content should contain customer information", rejectionEmail.getContent().contains("Olivia Lee"));
        assertTrue("Email content should contain company information", rejectionEmail.getContent().contains("HealthPlus"));
    }
    
    @Test
    public void testCase3_approveAlreadyApprovedRecord() {
        // Setup Customer "C009" (named "Daniel Kim", email "d.kim@example.com", phone "555-5566")
        Customer customer = factory.createCustomer();
        customer.setName("Daniel");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-5566");
        customer.setCanApplyForIPO(true);
        
        // Setup Company "HealthPlus" (email: healthplus@gmail.com)
        Company company = factory.createCompany();
        company.setName("HealthPlus");
        company.setEmail("healthplus@gmail.com");
        
        // Setup Document 'H'
        Document document = factory.createDocument();
        
        // Create already approved application "APP-1003"
        Application application = factory.createApplication();
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setStatus(ApplicationStatus.APPROVAL); // Already approved
        application.setCompany(company);
        application.setAllowance(document);
        application.setCustomer(customer);
        
        // Add application to customer
        customer.getApplications().add(application);
        
        // Execute: Bank attempts to re-approve the application
        boolean result = application.approve();
        
        // Verify: False (cannot modify approved applications)
        assertFalse("Approving an already approved application should return false", result);
        assertEquals("Application status should remain APPROVAL", ApplicationStatus.APPROVAL, application.getStatus());
    }
    
    @Test
    public void testCase4_rejectAlreadyRejectedRecord() {
        // Setup Customer "C010" (named "Sophie Zhang", email "s.zhang@example.com", phone "555-7788")
        Customer customer = factory.createCustomer();
        customer.setName("Sophie");
        customer.setSurname("Zhang");
        customer.setEmail("s.zhang@example.com");
        customer.setTelephone("555-7788");
        customer.setCanApplyForIPO(true);
        
        // Setup Company "Health" (email: health@gmail.com)
        Company company = factory.createCompany();
        company.setName("Health");
        company.setEmail("health@gmail.com");
        
        // Setup Document
        Document document = factory.createDocument();
        
        // Create already rejected application "APP-1004"
        Application application = factory.createApplication();
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setStatus(ApplicationStatus.REJECTED); // Already rejected
        application.setCompany(company);
        application.setAllowance(document);
        application.setCustomer(customer);
        
        // Add application to customer
        customer.getApplications().add(application);
        
        // Execute: Bank tries to reject the application
        boolean result = application.reject();
        
        // Verify: False (cannot modify final decisions)
        assertFalse("Rejecting an already rejected application should return false", result);
        assertEquals("Application status should remain REJECTED", ApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase5_approveRecordTiedToIneligibleCustomer() {
        // Setup Customer "C011" (named "William Wang", email "will.w@example.com", phone "555-9900")
        Customer customer = factory.createCustomer();
        customer.setName("William");
        customer.setSurname("Wang");
        customer.setEmail("will.w@example.com");
        customer.setTelephone("555-9900");
        customer.setCanApplyForIPO(false); // Cannot apply for IPO
        
        // Setup Company "Cloud" (email: Cloud@gmail.com)
        Company company = factory.createCompany();
        company.setName("Cloud");
        company.setEmail("Cloud@gmail.com");
        
        // Setup Document
        Document document = factory.createDocument();
        
        // Create pending application "APP-1005"
        Application application = factory.createApplication();
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setStatus(ApplicationStatus.PENDING);
        application.setCompany(company);
        application.setAllowance(document);
        application.setCustomer(customer);
        
        // Add application to customer
        customer.getApplications().add(application);
        
        // Execute: Bank processes the application
        boolean result = application.approve();
        
        // Verify: False (must maintain eligibility throughout process)
        assertFalse("Approving an application for an ineligible customer should return false", result);
        assertEquals("Application status should remain PENDING", ApplicationStatus.PENDING, application.getStatus());
    }
}