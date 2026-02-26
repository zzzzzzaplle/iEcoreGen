package edu.ipo.ipo4.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.ipo.Application;
import edu.ipo.ApplicationStatus;
import edu.ipo.Company;
import edu.ipo.Customer;
import edu.ipo.Document;
import edu.ipo.IpoFactory;

public class CR1Test {
    
    private IpoFactory factory;
    private Customer customerC001;
    private Customer customerC002;
    private Customer customerC003;
    private Customer customerC004;
    private Customer customerC005;
    private Customer customerC006;
    private Company techCorp;
    private Company bioMed;
    private Company greenEnergy;
    private Company autoFuture;
    private Company nanoChip;
    private Company cloudServ;
    private Document docA;
    private Document docB;
    private Document docG;
    private Document docN;
    private Document docC;
    
    @Before
    public void setUp() {
        factory = IpoFactory.eINSTANCE;
        
        // Create customers
        customerC001 = factory.createCustomer();
        customerC001.setName("John");
        customerC001.setSurname("Smith");
        customerC001.setEmail("john.smith@example.com");
        customerC001.setTelephone("555-1234");
        customerC001.setCanApplyForIPO(true);
        
        customerC002 = factory.createCustomer();
        customerC002.setName("Alice");
        customerC002.setSurname("Johnson");
        customerC002.setEmail("alice.j@example.com");
        customerC002.setTelephone("555-5678");
        customerC002.setCanApplyForIPO(false); // Not eligible
        
        customerC003 = factory.createCustomer();
        customerC003.setName("Robert");
        customerC003.setSurname("Chen");
        customerC003.setEmail("r.chen@example.com");
        customerC003.setTelephone("555-9012");
        customerC003.setCanApplyForIPO(true);
        
        customerC004 = factory.createCustomer();
        customerC004.setName("Emma");
        customerC004.setSurname("Davis");
        customerC004.setEmail("emma.d@example.com");
        customerC004.setTelephone("555-3456");
        customerC004.setCanApplyForIPO(true);
        
        customerC005 = factory.createCustomer();
        customerC005.setName("James");
        customerC005.setSurname("Wilson");
        customerC005.setEmail("j.wilson@example.com");
        customerC005.setTelephone("555-7890");
        customerC005.setCanApplyForIPO(true);
        
        customerC006 = factory.createCustomer();
        customerC006.setName("Sophia");
        customerC006.setSurname("Martinez");
        customerC006.setEmail("s.m@example.com");
        customerC006.setTelephone("555-2345");
        customerC006.setCanApplyForIPO(true);
        
        // Create companies
        techCorp = factory.createCompany();
        techCorp.setName("TechCorp");
        techCorp.setEmail("techcorp@gmail.com");
        
        bioMed = factory.createCompany();
        bioMed.setName("BioMed");
        bioMed.setEmail("biomed@gmail.com");
        
        greenEnergy = factory.createCompany();
        greenEnergy.setName("GreenEnergy");
        greenEnergy.setEmail("greenenergy@gmail.com");
        
        autoFuture = factory.createCompany();
        autoFuture.setName("AutoFuture");
        autoFuture.setEmail("autofuture@gmail.com");
        
        nanoChip = factory.createCompany();
        nanoChip.setName("NanoChip");
        nanoChip.setEmail("nanotech@gmail.com");
        
        cloudServ = factory.createCompany();
        cloudServ.setName("CloudServ");
        cloudServ.setEmail("cloudserv@gmail.com");
        
        // Create documents
        docA = factory.createDocument();
        docB = factory.createDocument();
        docG = factory.createDocument();
        docN = factory.createDocument();
        docC = factory.createDocument();
    }
    
    @Test
    public void testCase1_StandardEligibleSubmission() {
        // Test Case 1: Standard eligible submission
        // Customer "C001" applies for TechCorp with valid parameters
        
        boolean result = customerC001.createApplication(techCorp, 100, 5000.0, docA);
        
        assertTrue("Application should be created successfully for eligible customer", result);
        assertEquals("Customer should have one application", 1, customerC001.getApplications().size());
        
        Application createdApp = customerC001.getApplications().get(0);
        assertEquals("Application should be for TechCorp", techCorp, createdApp.getCompany());
        assertEquals("Share count should be 100", 100, createdApp.getShare());
        assertEquals("Amount should be 5000.0", 5000.0, createdApp.getAmountOfMoney(), 0.001);
        assertEquals("Status should be PENDING", ApplicationStatus.PENDING, createdApp.getStatus());
        assertEquals("Customer should be set correctly", customerC001, createdApp.getCustomer());
        assertEquals("Document should be set correctly", docA, createdApp.getAllowance());
    }
    
    @Test
    public void testCase2_CustomerNotEligible() {
        // Test Case 2: Customer not eligible
        // Customer "C002" (not eligible) attempts to apply for BioMed
        
        boolean result = customerC002.createApplication(bioMed, 50, 2500.0, docB);
        
        assertFalse("Application should not be created for ineligible customer", result);
        assertTrue("Customer should have no applications", customerC002.getApplications().isEmpty());
    }
    
    @Test
    public void testCase3_DuplicateApprovedApplication() {
        // Test Case 3: Duplicate approved application
        // Setup: Create and approve first application for GreenEnergy
        boolean firstResult = customerC003.createApplication(greenEnergy, 10, 300.0, docG);
        assertTrue("First application should be created successfully", firstResult);
        
        Application firstApp = customerC003.getApplications().get(0);
        firstApp.setStatus(ApplicationStatus.APPROVAL); // Simulate bank approval
        
        // Attempt to create second application for same company
        boolean secondResult = customerC003.createApplication(greenEnergy, 15, 450.0, docG);
        
        assertFalse("Second application should not be created for same company with approved application", secondResult);
        assertEquals("Customer should still have only one application", 1, customerC003.getApplications().size());
    }
    
    @Test
    public void testCase4_MissingDocument() {
        // Test Case 4: Missing document
        // Customer "C004" applies without uploading document
        
        boolean result = customerC004.createApplication(autoFuture, 25, 1000.0, null);
        
        assertFalse("Application should not be created without document", result);
        assertTrue("Customer should have no applications", customerC004.getApplications().isEmpty());
    }
    
    @Test
    public void testCase5_ZeroShareApplication() {
        // Test Case 5: Zero-share application
        // Customer "C005" applies with zero shares
        
        boolean result = customerC005.createApplication(nanoChip, 0, 0.0, docN);
        
        assertFalse("Application should not be created with zero shares", result);
        assertTrue("Customer should have no applications", customerC005.getApplications().isEmpty());
    }
    
    @Test
    public void testCase6_NegativeShareCount() {
        // Test Case 6: Negative share count
        // Customer "C006" applies with negative shares and amount
        
        boolean result = customerC006.createApplication(cloudServ, -5, -200.0, docC);
        
        assertFalse("Application should not be created with negative shares", result);
        assertTrue("Customer should have no applications", customerC006.getApplications().isEmpty());
    }
}