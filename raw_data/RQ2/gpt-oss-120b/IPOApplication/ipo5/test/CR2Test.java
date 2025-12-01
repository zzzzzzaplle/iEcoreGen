package edu.ipo.ipo5.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.ipo.IpoFactory;
import edu.ipo.Application;
import edu.ipo.Customer;
import edu.ipo.Company;
import edu.ipo.Document;
import edu.ipo.ApplicationStatus;
import edu.ipo.Email;

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
        
        // Add application to customer's applications
        customer.getApplications().add(application);
        
        // Execute approve operation
        boolean result = application.approve();
        
        // Verify result and status change
        assertTrue("Approve should return true for valid pending application", result);
        assertEquals("Application status should be APPROVAL after approve", ApplicationStatus.APPROVAL, application.getStatus());
        
        // Verify emails were created
        assertEquals("Two emails should be created", 2, application.getEmails().size());
        
        // Check customer email
        Email customerEmail = application.getEmails().get(0);
        assertEquals("Customer email should be sent to customer's email", customer.getEmail(), customerEmail.getReceiver());
        assertNotNull("Customer email content should not be null", customerEmail.getContent());
        assertTrue("Customer email content should contain customer name", customerEmail.getContent().contains(customer.getName()));
        assertTrue("Customer email content should contain customer surname", customerEmail.getContent().contains(customer.getSurname()));
        assertTrue("Customer email content should contain company name", customerEmail.getContent().contains(company.getName()));
        assertTrue("Customer email content should contain shares", customerEmail.getContent().contains("10"));
        assertTrue("Customer email content should contain amount", customerEmail.getContent().contains("$200.00"));
        
        // Check company email
        Email companyEmail = application.getEmails().get(1);
        assertEquals("Company email should be sent to company's email", company.getEmail(), companyEmail.getReceiver());
        assertNotNull("Company email content should not be null", companyEmail.getContent());
        assertTrue("Company email content should contain customer name", companyEmail.getContent().contains(customer.getName()));
        assertTrue("Company email content should contain customer surname", companyEmail.getContent().contains(customer.getSurname()));
        assertTrue("Company email content should contain company name", companyEmail.getContent().contains(company.getName()));
        assertTrue("Company email content should contain shares", companyEmail.getContent().contains("10"));
        assertTrue("Company email content should contain amount", companyEmail.getContent().contains("$200.00"));
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
        
        // Add application to customer's applications
        customer.getApplications().add(application);
        
        // Execute reject operation
        boolean result = application.reject();
        
        // Verify result and status change
        assertTrue("Reject should return true for valid pending application", result);
        assertEquals("Application status should be REJECTED after reject", ApplicationStatus.REJECTED, application.getStatus());
        
        // Verify email was created
        assertEquals("One email should be created", 1, application.getEmails().size());
        
        // Check rejection email
        Email rejectionEmail = application.getEmails().get(0);
        assertEquals("Rejection email should be sent to customer's email", customer.getEmail(), rejectionEmail.getReceiver());
        assertNotNull("Rejection email content should not be null", rejectionEmail.getContent());
        assertTrue("Rejection email content should contain customer name", rejectionEmail.getContent().contains(customer.getName()));
        assertTrue("Rejection email content should contain customer surname", rejectionEmail.getContent().contains(customer.getSurname()));
        assertTrue("Rejection email content should contain company name", rejectionEmail.getContent().contains(company.getName()));
        assertTrue("Rejection email content should contain shares", rejectionEmail.getContent().contains("10"));
        assertTrue("Rejection email content should contain amount", rejectionEmail.getContent().contains("$5000.00"));
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
        
        // Create already approved application
        Application application = factory.createApplication();
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setStatus(ApplicationStatus.APPROVAL); // Already approved
        application.setCompany(company);
        application.setAllowance(document);
        application.setCustomer(customer);
        
        // Add application to customer's applications
        customer.getApplications().add(application);
        
        // Execute approve operation on already approved application
        boolean result = application.approve();
        
        // Verify result is false (cannot modify approved applications)
        assertFalse("Approve should return false for already approved application", result);
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
        
        // Create already rejected application
        Application application = factory.createApplication();
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setStatus(ApplicationStatus.REJECTED); // Already rejected
        application.setCompany(company);
        application.setCustomer(customer);
        
        // Add application to customer's applications
        customer.getApplications().add(application);
        
        // Execute reject operation on already rejected application
        boolean result = application.reject();
        
        // Verify result is false (cannot modify final decisions)
        assertFalse("Reject should return false for already rejected application", result);
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
        customer.setCanApplyForIPO(false); // Customer cannot apply for IPO
        
        // Setup Company "Cloud" (email: Cloud@gmail.com)
        Company company = factory.createCompany();
        company.setName("Cloud");
        company.setEmail("Cloud@gmail.com");
        
        // Setup Document
        Document document = factory.createDocument();
        
        // Create pending application
        Application application = factory.createApplication();
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setStatus(ApplicationStatus.PENDING);
        application.setCompany(company);
        application.setAllowance(document);
        application.setCustomer(customer);
        
        // Add application to customer's applications
        customer.getApplications().add(application);
        
        // Execute approve operation on application with ineligible customer
        boolean result = application.approve();
        
        // Verify result is false (must maintain eligibility throughout process)
        assertFalse("Approve should return false for application with ineligible customer", result);
        assertEquals("Application status should remain PENDING", ApplicationStatus.PENDING, application.getStatus());
    }
}