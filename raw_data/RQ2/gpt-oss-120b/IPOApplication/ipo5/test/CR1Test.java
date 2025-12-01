package edu.ipo.ipo5.test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import edu.ipo.Application;
import edu.ipo.ApplicationStatus;
import edu.ipo.Company;
import edu.ipo.Customer;
import edu.ipo.Document;
import edu.ipo.IpoFactory;

public class CR1Test {

    private IpoFactory factory;
    
    @Before
    public void setUp() {
        factory = IpoFactory.eINSTANCE;
    }

    @Test
    public void testCase1_standardEligibleSubmission() {
        // Setup customer
        Customer customer = factory.createCustomer();
        customer.setName("John");
        customer.setSurname("Smith");
        customer.setEmail("john.smith@example.com");
        customer.setTelephone("555-1234");
        customer.setCanApplyForIPO(true); // Customer is eligible

        // Setup company
        Company company = factory.createCompany();
        company.setName("TechCorp");
        company.setEmail("techcorp@gmail.com");

        // Setup document
        Document document = factory.createDocument();

        // Execute createApplication
        boolean result = customer.createApplication(company, 100, 5000.0, document);

        // Verify result
        assertTrue("Application should be created successfully", result);

        // Verify application was added
        assertEquals("One application should be created", 1, customer.getApplications().size());
        
        Application app = customer.getApplications().get(0);
        assertEquals("Share count should match", 100, app.getShare());
        assertEquals("Amount should match", 5000.0, app.getAmountOfMoney(), 0.01);
        assertEquals("Status should be PENDING", ApplicationStatus.PENDING, app.getStatus());
        assertSame("Company should match", company, app.getCompany());
        assertSame("Document should match", document, app.getAllowance());
        assertSame("Customer should match", customer, app.getCustomer());
    }

    @Test
    public void testCase2_customerNotEligible() {
        // Setup customer (not eligible)
        Customer customer = factory.createCustomer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        customer.setEmail("alice.j@example.com");
        customer.setTelephone("555-5678");
        customer.setCanApplyForIPO(false); // Customer is NOT eligible

        // Setup company
        Company company = factory.createCompany();
        company.setName("BioMed");
        company.setEmail("biomed@gmail.com");

        // Setup document
        Document document = factory.createDocument();

        // Execute createApplication
        boolean result = customer.createApplication(company, 50, 2500.0, document);

        // Verify result
        assertFalse("Application should not be created when customer is not eligible", result);

        // Verify no application was added
        assertEquals("No application should be created", 0, customer.getApplications().size());
    }

    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Setup customer
        Customer customer = factory.createCustomer();
        customer.setName("Robert");
        customer.setSurname("Chen");
        customer.setEmail("r.chen@example.com");
        customer.setTelephone("555-9012");
        customer.setCanApplyForIPO(true); // Customer is eligible

        // Setup company
        Company company = factory.createCompany();
        company.setName("GreenEnergy");
        company.setEmail("greenenergy@gmail.com");

        // Setup document
        Document document = factory.createDocument();

        // Create and approve an existing application
        Application existingApp = factory.createApplication();
        existingApp.setCompany(company);
        existingApp.setCustomer(customer);
        existingApp.setStatus(ApplicationStatus.APPROVAL); // Already approved
        customer.getApplications().add(existingApp);

        // Try to create another application for the same company
        boolean result = customer.createApplication(company, 10, 300.0, document);

        // Verify result
        assertFalse("Application should not be created when there's an existing approved application", result);
    }

    @Test
    public void testCase4_missingDocument() {
        // Setup customer
        Customer customer = factory.createCustomer();
        customer.setName("Emma");
        customer.setSurname("Davis");
        customer.setEmail("emma.d@example.com");
        customer.setTelephone("555-3456");
        customer.setCanApplyForIPO(true); // Customer is eligible

        // Setup company
        Company company = factory.createCompany();
        company.setName("AutoFuture");
        company.setEmail("autofuture@gmail.com");

        // Execute createApplication with null document
        boolean result = customer.createApplication(company, 25, 1000.0, null);

        // Verify result
        assertFalse("Application should not be created when document is missing", result);

        // Verify no application was added
        assertEquals("No application should be created", 0, customer.getApplications().size());
    }

    @Test
    public void testCase5_zeroShareApplication() {
        // Setup customer
        Customer customer = factory.createCustomer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@example.com");
        customer.setTelephone("555-7890");
        customer.setCanApplyForIPO(true); // Customer is eligible

        // Setup company
        Company company = factory.createCompany();
        company.setName("NanoChip");
        company.setEmail("nanotech@gmail.com");

        // Setup document
        Document document = factory.createDocument();

        // Execute createApplication with zero shares
        boolean result = customer.createApplication(company, 0, 0.0, document);

        // Verify result
        assertFalse("Application should not be created when share count is zero", result);

        // Verify no application was added
        assertEquals("No application should be created", 0, customer.getApplications().size());
    }

    @Test
    public void testCase6_negativeShareCount() {
        // Setup customer
        Customer customer = factory.createCustomer();
        customer.setName("Sophia");
        customer.setSurname("Martinez");
        customer.setEmail("s.m@example.com");
        customer.setTelephone("555-2345");
        customer.setCanApplyForIPO(true); // Customer is eligible

        // Setup company
        Company company = factory.createCompany();
        company.setName("CloudServ");
        company.setEmail("cloudserv@gmail.com");

        // Setup document
        Document document = factory.createDocument();

        // Execute createApplication with negative shares and amount
        boolean result = customer.createApplication(company, -5, -200.0, document);

        // Verify result
        assertFalse("Application should not be created when share count is negative", result);

        // Verify no application was added
        assertEquals("No application should be created", 0, customer.getApplications().size());
    }
}