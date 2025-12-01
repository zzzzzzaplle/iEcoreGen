package edu.ipo.ipo3.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.ipo.IpoFactory;
import edu.ipo.Customer;
import edu.ipo.Company;
import edu.ipo.Document;
import edu.ipo.Application;
import edu.ipo.ApplicationStatus;

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
        customer.setCanApplyForIPO(true); // Eligible
        
        // Setup company
        Company company = factory.createCompany();
        company.setName("TechCorp");
        company.setEmail("techcorp@gmail.com");
        
        // Setup document
        Document document = factory.createDocument();
        
        // Test: Create IPO application with valid inputs
        boolean result = customer.createApplication(company, 100, 5000.0, document);
        
        // Verify
        assertTrue("Application should be created successfully", result);
        assertEquals("Customer should have one application", 1, customer.getApplications().size());
        
        Application app = customer.getApplications().get(0);
        assertEquals("Application share count should match", 100, app.getShare());
        assertEquals("Application amount should match", 5000.0, app.getAmountOfMoney(), 0.01);
        assertEquals("Application company should match", company, app.getCompany());
        assertEquals("Application customer should match", customer, app.getCustomer());
        assertEquals("Application allowance should match", document, app.getAllowance());
        assertEquals("Application status should be PENDING", ApplicationStatus.PENDING, app.getStatus());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Setup customer (not eligible)
        Customer customer = factory.createCustomer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        customer.setEmail("alice.j@example.com");
        customer.setTelephone("555-5678");
        customer.setCanApplyForIPO(false); // Not eligible
        
        // Setup company
        Company company = factory.createCompany();
        company.setName("BioMed");
        company.setEmail("biomed@gmail.com");
        
        // Setup document
        Document document = factory.createDocument();
        
        // Test: Try to create IPO application when customer is not eligible
        boolean result = customer.createApplication(company, 50, 2500.0, document);
        
        // Verify
        assertFalse("Application should not be created when customer is not eligible", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Setup customer (eligible)
        Customer customer = factory.createCustomer();
        customer.setName("Robert");
        customer.setSurname("Chen");
        customer.setEmail("r.chen@example.com");
        customer.setTelephone("555-9012");
        customer.setCanApplyForIPO(true); // Eligible
        
        // Setup company
        Company company = factory.createCompany();
        company.setName("GreenEnergy");
        company.setEmail("greenenergy@gmail.com");
        
        // Setup document
        Document document = factory.createDocument();
        
        // Create first application and approve it
        customer.createApplication(company, 10, 300.0, document);
        Application firstApp = customer.getApplications().get(0);
        firstApp.setStatus(ApplicationStatus.APPROVAL); // Approved
        
        // Test: Try to create another application for the same company
        boolean result = customer.createApplication(company, 10, 300.0, document);
        
        // Verify
        assertFalse("Application should not be created when there's already an approved application for the same company", result);
        assertEquals("Customer should still have only one application", 1, customer.getApplications().size());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Setup customer (eligible)
        Customer customer = factory.createCustomer();
        customer.setName("Emma");
        customer.setSurname("Davis");
        customer.setEmail("emma.d@example.com");
        customer.setTelephone("555-3456");
        customer.setCanApplyForIPO(true); // Eligible
        
        // Setup company
        Company company = factory.createCompany();
        company.setName("AutoFuture");
        company.setEmail("autofuture@gmail.com");
        
        // Test: Try to create IPO application without document (null)
        boolean result = customer.createApplication(company, 25, 1000.0, null);
        
        // Verify
        assertFalse("Application should not be created when document is missing", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Setup customer (eligible)
        Customer customer = factory.createCustomer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@example.com");
        customer.setTelephone("555-7890");
        customer.setCanApplyForIPO(true); // Eligible
        
        // Setup company
        Company company = factory.createCompany();
        company.setName("NanoChip");
        company.setEmail("nanotech@gmail.com");
        
        // Setup document
        Document document = factory.createDocument();
        
        // Test: Try to create IPO application with zero shares
        boolean result = customer.createApplication(company, 0, 0.0, document);
        
        // Verify
        assertFalse("Application should not be created when share count is zero", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Setup customer (eligible)
        Customer customer = factory.createCustomer();
        customer.setName("Sophia");
        customer.setSurname("Martinez");
        customer.setEmail("s.m@example.com");
        customer.setTelephone("555-2345");
        customer.setCanApplyForIPO(true); // Eligible
        
        // Setup company
        Company company = factory.createCompany();
        company.setName("CloudServ");
        company.setEmail("cloudserv@gmail.com");
        
        // Setup document
        Document document = factory.createDocument();
        
        // Test: Try to create IPO application with negative shares
        boolean result = customer.createApplication(company, -5, -200.0, document);
        
        // Verify
        assertFalse("Application should not be created when share count is negative", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
}