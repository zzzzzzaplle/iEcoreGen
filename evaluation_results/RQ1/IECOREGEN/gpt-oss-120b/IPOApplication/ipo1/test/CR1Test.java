package edu.ipo.ipo1.test;

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

    private Customer customer1, customer2, customer3, customer4, customer5, customer6;
    private Company techCorp, bioMed, greenEnergy, autoFuture, nanoChip, cloudServ;
    private Document docA, docB, docG, docN, docC;

    @Before
    public void setUp() {
        // Create documents
        docA = IpoFactory.eINSTANCE.createDocument();
        docB = IpoFactory.eINSTANCE.createDocument();
        docG = IpoFactory.eINSTANCE.createDocument();
        docN = IpoFactory.eINSTANCE.createDocument();
        docC = IpoFactory.eINSTANCE.createDocument();

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

        // Create customers
        customer1 = IpoFactory.eINSTANCE.createCustomer();
        customer1.setName("John");
        customer1.setSurname("Smith");
        customer1.setEmail("john.smith@example.com");
        customer1.setTelephone("555-1234");
        customer1.setCanApplyForIPO(true);

        customer2 = IpoFactory.eINSTANCE.createCustomer();
        customer2.setName("Alice");
        customer2.setSurname("Johnson");
        customer2.setEmail("alice.j@example.com");
        customer2.setTelephone("555-5678");
        customer2.setCanApplyForIPO(false); // Not eligible

        customer3 = IpoFactory.eINSTANCE.createCustomer();
        customer3.setName("Robert");
        customer3.setSurname("Chen");
        customer3.setEmail("r.chen@example.com");
        customer3.setTelephone("555-9012");
        customer3.setCanApplyForIPO(true);

        customer4 = IpoFactory.eINSTANCE.createCustomer();
        customer4.setName("Emma");
        customer4.setSurname("Davis");
        customer4.setEmail("emma.d@example.com");
        customer4.setTelephone("555-3456");
        customer4.setCanApplyForIPO(true);

        customer5 = IpoFactory.eINSTANCE.createCustomer();
        customer5.setName("James");
        customer5.setSurname("Wilson");
        customer5.setEmail("j.wilson@example.com");
        customer5.setTelephone("555-7890");
        customer5.setCanApplyForIPO(true);

        customer6 = IpoFactory.eINSTANCE.createCustomer();
        customer6.setName("Sophia");
        customer6.setSurname("Martinez");
        customer6.setEmail("s.m@example.com");
        customer6.setTelephone("555-2345");
        customer6.setCanApplyForIPO(true);
    }

    /**
     * Test Case 1: "Standard eligible submission"
     * Input: An eligible customer applies for an IPO with company "TechCorp",
     * requesting 100 shares with a payment of $5,000, and uploads the allowance document 'A'.
     * Setup:
     * 1. Customer allows IPO applications.
     * 2. Customer has no previously approved applications for "TechCorp".
     * Expected Output: True (application is successfully created)
     */
    @Test
    public void testCase1_standardEligibleSubmission() {
        boolean result = customer1.createApplication(techCorp, 100, 5000.0, docA);
        assertTrue("Application should be created successfully", result);
        
        // Verify that one application was added
        assertEquals("One application should be added", 1, customer1.getApplications().size());
        
        // Verify the application details
        Application app = customer1.getApplications().get(0);
        assertEquals("Share count should match", 100, app.getShare());
        assertEquals("Amount should match", 5000.0, app.getAmountOfMoney(), 0.01);
        assertEquals("Status should be PENDING", ApplicationStatus.PENDING, app.getStatus());
        assertEquals("Company should match", techCorp, app.getCompany());
        assertEquals("Allowance document should match", docA, app.getAllowance());
        assertEquals("Customer should match", customer1, app.getCustomer());
    }

    /**
     * Test Case 2: "Customer not eligible"
     * Input: Customer "C002" attempts to apply for an IPO with company "BioMed",
     * requesting 50 shares with $2,500 payment, and uploads document 'B'.
     * Setup:
     * 1. Customer has lost IPO eligibility after repeated failed attempts.
     * 2. No existing applications for "BioMed"
     * Expected Output: False
     */
    @Test
    public void testCase2_customerNotEligible() {
        boolean result = customer2.createApplication(bioMed, 50, 2500.0, docB);
        assertFalse("Application should not be created as customer is not eligible", result);
        
        // Verify that no application was added
        assertEquals("No application should be added", 0, customer2.getApplications().size());
    }

    /**
     * Test Case 3: "Duplicate approved application"
     * Input: Customer "C003" submits another application to "GreenEnergy" for 10 shares ($300) with document 'G'.
     * Setup:
     * 1. Customer remains eligible.
     * 2. Submit the application to "GreenEnergy" for 10 shares ($300) with document 'G'.
     * 3. The bank approve the application
     * Expected Output: False (only one approved application per company allowed)
     */
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // First, create and approve an application
        boolean firstResult = customer3.createApplication(greenEnergy, 10, 300.0, docG);
        assertTrue("First application should be created", firstResult);
        
        Application firstApp = customer3.getApplications().get(0);
        firstApp.setStatus(ApplicationStatus.APPROVAL); // Simulate bank approval
        
        // Try to create another application for the same company
        boolean secondResult = customer3.createApplication(greenEnergy, 5, 150.0, docG);
        assertFalse("Second application should not be created due to existing approved application", secondResult);
        
        // Verify only one application exists
        assertEquals("Only one application should exist", 1, customer3.getApplications().size());
    }

    /**
     * Test Case 4: "Missing document"
     * Input: Eligible customer "C004" applies to "AutoFuture" for 25 shares ($1,000) but forgets to upload any document.
     * Setup:
     * 1. No prior applications for "AutoFuture"
     * Expected Output: False (document upload is mandatory)
     */
    @Test
    public void testCase4_missingDocument() {
        boolean result = customer4.createApplication(autoFuture, 25, 1000.0, null);
        assertFalse("Application should not be created without a document", result);
        
        // Verify that no application was added
        assertEquals("No application should be added", 0, customer4.getApplications().size());
    }

    /**
     * Test Case 5: "Zero-share application"
     * Input: Eligible customer "C005" applies to "NanoChip" for 0 shares ($0) with document 'N'.
     * Setup:
     * 1. No existing applications for "NanoChip"
     * Expected Output: False
     */
    @Test
    public void testCase5_zeroShareApplication() {
        boolean result = customer5.createApplication(nanoChip, 0, 0.0, docN);
        assertFalse("Application should not be created with zero shares", result);
        
        // Verify that no application was added
        assertEquals("No application should be added", 0, customer5.getApplications().size());
    }

    /**
     * Test Case 6: "Negative share count"
     * Input: Eligible customer "C006" attempts to apply to "CloudServ" for -5 shares (-$200) with document 'C'.
     * Setup:
     * 1. No prior applications for "CloudServ"
     * Expected Output: False (negative shares/amount are invalid)
     */
    @Test
    public void testCase6_negativeShareCount() {
        boolean result = customer6.createApplication(cloudServ, -5, -200.0, docC);
        assertFalse("Application should not be created with negative shares", result);
        
        // Verify that no application was added
        assertEquals("No application should be added", 0, customer6.getApplications().size());
    }
}