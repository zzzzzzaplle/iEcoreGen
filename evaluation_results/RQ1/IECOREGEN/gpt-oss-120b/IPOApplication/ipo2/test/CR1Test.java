package edu.ipo.ipo2.test;

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
    
    @Test
    public void testCase1_standardEligibleSubmission() {
        // Setup: Customer "C001" allows IPO applications and has no previously approved applications for "TechCorp"
        // This is already set up in the setUp method
        
        // Input: Eligible customer applies for an IPO with company "TechCorp", requesting 100 shares with $5,000, and uploads document 'A'
        boolean result = customer1.createApplication(techCorp, 100, 5000.0, docA);
        
        // Expected Output: True (application is successfully created)
        assertTrue(result);
        
        // Verify that an application was created
        assertEquals(1, customer1.getApplications().size());
        Application app = customer1.getApplications().get(0);
        assertEquals(100, app.getShare());
        assertEquals(5000.0, app.getAmountOfMoney(), 0.01);
        assertEquals(ApplicationStatus.PENDING, app.getStatus());
        assertEquals(techCorp, app.getCompany());
        assertEquals(docA, app.getAllowance());
        assertEquals(customer1, app.getCustomer());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Setup: Customer has lost IPO eligibility after repeated failed attempts
        // No existing applications for "BioMed" (already set up)
        
        // Input: Customer "C002" attempts to apply for an IPO with company "BioMed"
        boolean result = customer2.createApplication(bioMed, 50, 2500.0, docB);
        
        // Expected Output: False
        assertFalse(result);
        
        // Verify that no application was created
        assertEquals(0, customer2.getApplications().size());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Setup: Customer remains eligible
        // Submit the application to "GreenEnergy" for 10 shares ($300) with document 'G'
        boolean firstResult = customer3.createApplication(greenEnergy, 10, 300.0, docG);
        assertTrue(firstResult);
        
        // The bank approves the application
        Application app = customer3.getApplications().get(0);
        app.setStatus(ApplicationStatus.APPROVAL);
        
        // Input: Submit another application to the same company
        boolean result = customer3.createApplication(greenEnergy, 10, 300.0, docG);
        
        // Expected Output: False (only one approved application per company allowed)
        assertFalse(result);
        
        // Verify that only one application exists
        assertEquals(1, customer3.getApplications().size());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Setup: No prior applications for "AutoFuture"
        // Input: Eligible customer applies without uploading any document (null document)
        boolean result = customer4.createApplication(autoFuture, 25, 1000.0, null);
        
        // Expected Output: False (document upload is mandatory)
        assertFalse(result);
        
        // Verify that no application was created
        assertEquals(0, customer4.getApplications().size());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Setup: No existing applications for "NanoChip"
        // Input: Eligible customer applies for 0 shares
        boolean result = customer5.createApplication(nanoChip, 0, 1000.0, docN);
        
        // Expected Output: False
        assertFalse(result);
        
        // Verify that no application was created
        assertEquals(0, customer5.getApplications().size());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Setup: No prior applications for "CloudServ"
        // Input: Eligible customer attempts to apply for -5 shares
        boolean result = customer6.createApplication(cloudServ, -5, -200.0, docC);
        
        // Expected Output: False (negative shares/amount are invalid)
        assertFalse(result);
        
        // Verify that no application was created
        assertEquals(0, customer6.getApplications().size());
    }
}