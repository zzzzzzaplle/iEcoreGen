package edu.ipo.ipo4.test;

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
    
    private Document documentA;
    private Document documentB;
    private Document documentG;
    private Document documentN;
    private Document documentC;

    @Before
    public void setUp() {
        // Create companies
        techCorp = IpoFactory.eINSTANCE.createCompany();
        techCorp.setName("TechCorp");
        techCorp.setEmail("techcorp@gmail.com");
        
        bioMed = IpoFactory.eINSTANCE.createCompany();
        bioMed.setName("BioMed");
        bioMed.setEmail("biomed@gmail.com");
        
        greenEnergy = IpoFactory.eINSTANCE.createCompany();
        greenEnergy.setName("GreenEnergy");
        greenEnergy.setEmail("greenenergy@gmail.com");
        
        autoFuture = IpoFactory.eINSTANCE.createCompany();
        autoFuture.setName("AutoFuture");
        autoFuture.setEmail("autofuture@gmail.com");
        
        nanoChip = IpoFactory.eINSTANCE.createCompany();
        nanoChip.setName("NanoChip");
        nanoChip.setEmail("nanotech@gmail.com");
        
        cloudServ = IpoFactory.eINSTANCE.createCompany();
        cloudServ.setName("CloudServ");
        cloudServ.setEmail("cloudserv@gmail.com");
        
        // Create documents
        documentA = IpoFactory.eINSTANCE.createDocument();
        documentB = IpoFactory.eINSTANCE.createDocument();
        documentG = IpoFactory.eINSTANCE.createDocument();
        documentN = IpoFactory.eINSTANCE.createDocument();
        documentC = IpoFactory.eINSTANCE.createDocument();
        
        // Create customers
        customerC001 = IpoFactory.eINSTANCE.createCustomer();
        customerC001.setName("John");
        customerC001.setSurname("Smith");
        customerC001.setEmail("john.smith@example.com");
        customerC001.setTelephone("555-1234");
        customerC001.setCanApplyForIPO(true);
        
        customerC002 = IpoFactory.eINSTANCE.createCustomer();
        customerC002.setName("Alice");
        customerC002.setSurname("Johnson");
        customerC002.setEmail("alice.j@example.com");
        customerC002.setTelephone("555-5678");
        customerC002.setCanApplyForIPO(false); // Not eligible
        
        customerC003 = IpoFactory.eINSTANCE.createCustomer();
        customerC003.setName("Robert");
        customerC003.setSurname("Chen");
        customerC003.setEmail("r.chen@example.com");
        customerC003.setTelephone("555-9012");
        customerC003.setCanApplyForIPO(true);
        
        customerC004 = IpoFactory.eINSTANCE.createCustomer();
        customerC004.setName("Emma");
        customerC004.setSurname("Davis");
        customerC004.setEmail("emma.d@example.com");
        customerC004.setTelephone("555-3456");
        customerC004.setCanApplyForIPO(true);
        
        customerC005 = IpoFactory.eINSTANCE.createCustomer();
        customerC005.setName("James");
        customerC005.setSurname("Wilson");
        customerC005.setEmail("j.wilson@example.com");
        customerC005.setTelephone("555-7890");
        customerC005.setCanApplyForIPO(true);
        
        customerC006 = IpoFactory.eINSTANCE.createCustomer();
        customerC006.setName("Sophia");
        customerC006.setSurname("Martinez");
        customerC006.setEmail("s.m@example.com");
        customerC006.setTelephone("555-2345");
        customerC006.setCanApplyForIPO(true);
    }

    @Test
    public void testCase1_standardEligibleSubmission() {
        // Setup: Customer "C001" allows IPO applications and has no previously approved applications for "TechCorp"
        // This is already set up in the setUp method
        
        // Input: Customer applies for an IPO with company "TechCorp", requesting 100 shares with a payment of $5,000, and uploads document 'A'
        boolean result = customerC001.createApplication(techCorp, 100, 5000.0, documentA);
        
        // Expected Output: True (application is successfully created)
        assertTrue("Application should be created successfully", result);
        
        // Verify that an application was created
        assertEquals("Customer should have one application", 1, customerC001.getApplications().size());
        
        Application app = customerC001.getApplications().get(0);
        assertEquals("Application should be for TechCorp", techCorp, app.getCompany());
        assertEquals("Application should have 100 shares", 100, app.getShare());
        assertEquals("Application should have amount of $5000.0", 5000.0, app.getAmountOfMoney(), 0.0);
        assertEquals("Application should have document A", documentA, app.getAllowance());
        assertEquals("Application status should be PENDING", ApplicationStatus.PENDING, app.getStatus());
    }

    @Test
    public void testCase2_customerNotEligible() {
        // Setup: Customer has lost IPO eligibility after repeated failed attempts
        // This is already set up in the setUp method (customerC002.setCanApplyForIPO(false))
        
        // Input: Customer "C002" attempts to apply for an IPO with company "BioMed"
        boolean result = customerC002.createApplication(bioMed, 50, 2500.0, documentB);
        
        // Expected Output: False
        assertFalse("Application should not be created as customer is not eligible", result);
        
        // Verify that no application was created
        assertEquals("Customer should have no applications", 0, customerC002.getApplications().size());
    }

    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Setup: Customer remains eligible
        // Submit the application to "GreenEnergy" for 10 shares ($300) with document 'G'
        boolean firstResult = customerC003.createApplication(greenEnergy, 10, 300.0, documentG);
        assertTrue("First application should be created successfully", firstResult);
        
        // The bank approves the application
        Application app = customerC003.getApplications().get(0);
        app.setStatus(ApplicationStatus.APPROVAL);
        
        // Input: Customer submits another application to "GreenEnergy"
        boolean result = customerC003.createApplication(greenEnergy, 10, 300.0, documentG);
        
        // Expected Output: False (only one approved application per company allowed)
        assertFalse("Second application should not be created due to duplicate approved application", result);
        
        // Verify that only one application exists
        assertEquals("Customer should still have only one application", 1, customerC003.getApplications().size());
    }

    @Test
    public void testCase4_missingDocument() {
        // Setup: No prior applications for "AutoFuture"
        // This is already set up
        
        // Input: Eligible customer "C004" applies to "AutoFuture" but forgets to upload any document (passing null)
        boolean result = customerC004.createApplication(autoFuture, 25, 1000.0, null);
        
        // Expected Output: False (document upload is mandatory)
        assertFalse("Application should not be created as document is missing", result);
        
        // Verify that no application was created
        assertEquals("Customer should have no applications", 0, customerC004.getApplications().size());
    }

    @Test
    public void testCase5_zeroShareApplication() {
        // Setup: No existing applications for "NanoChip"
        // This is already set up
        
        // Input: Eligible customer "C005" applies to "NanoChip" for 0 shares ($0) with document 'N'
        boolean result = customerC005.createApplication(nanoChip, 0, 0.0, documentN);
        
        // Expected Output: False
        assertFalse("Application should not be created as share count is zero", result);
        
        // Verify that no application was created
        assertEquals("Customer should have no applications", 0, customerC005.getApplications().size());
    }

    @Test
    public void testCase6_negativeShareCount() {
        // Setup: No prior applications for "CloudServ"
        // This is already set up
        
        // Input: Eligible customer "C006" attempts to apply to "CloudServ" for -5 shares (-$200) with document 'C'
        boolean result = customerC006.createApplication(cloudServ, -5, -200.0, documentC);
        
        // Expected Output: False (negative shares/amount are invalid)
        assertFalse("Application should not be created as share count is negative", result);
        
        // Verify that no application was created
        assertEquals("Customer should have no applications", 0, customerC006.getApplications().size());
    }
}