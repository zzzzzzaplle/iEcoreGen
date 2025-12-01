import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

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
        // Initialize customers
        customerC001 = new Customer();
        customerC001.setFirstName("John");
        customerC001.setLastName("Smith");
        customerC001.setEmail("john.smith@example.com");
        customerC001.setTelephone("555-1234");
        
        customerC002 = new Customer();
        customerC002.setFirstName("Alice");
        customerC002.setLastName("Johnson");
        customerC002.setEmail("alice.j@example.com");
        customerC002.setTelephone("555-5678");
        
        customerC003 = new Customer();
        customerC003.setFirstName("Robert");
        customerC003.setLastName("Chen");
        customerC003.setEmail("r.chen@example.com");
        customerC003.setTelephone("555-9012");
        
        customerC004 = new Customer();
        customerC004.setFirstName("Emma");
        customerC004.setLastName("Davis");
        customerC004.setEmail("emma.d@example.com");
        customerC004.setTelephone("555-3456");
        
        customerC005 = new Customer();
        customerC005.setFirstName("James");
        customerC005.setLastName("Wilson");
        customerC005.setEmail("j.wilson@example.com");
        customerC005.setTelephone("555-7890");
        
        customerC006 = new Customer();
        customerC006.setFirstName("Sophia");
        customerC006.setLastName("Martinez");
        customerC006.setEmail("s.m@example.com");
        customerC006.setTelephone("555-2345");
        
        // Initialize companies
        techCorp = new Company("TechCorp", "techcorp@gmail.com");
        bioMed = new Company("BioMed", "biomed@gmail.com");
        greenEnergy = new Company("GreenEnergy", "greenenergy@gmail.com");
        autoFuture = new Company("AutoFuture", "autofuture@gmail.com");
        nanoChip = new Company("NanoChip", "nanotech@gmail.com");
        cloudServ = new Company("CloudServ", "cloudserv@gmail.com");
        
        // Initialize documents
        documentA = new Document("documentA.pdf", new byte[]{1, 2, 3});
        documentB = new Document("documentB.pdf", new byte[]{4, 5, 6});
        documentG = new Document("documentG.pdf", new byte[]{7, 8, 9});
        documentN = new Document("documentN.pdf", new byte[]{10, 11, 12});
        documentC = new Document("documentC.pdf", new byte[]{13, 14, 15});
    }
    
    @Test
    public void testCase1_standardEligibleSubmission() {
        // Setup: Customer "C001" allows IPO applications and has no previously approved applications for "TechCorp"
        boolean result = customerC001.createIPOApplication(techCorp, 100, 5000.0, documentA);
        
        // Verify that application creation was successful
        assertTrue("Application should be successfully created for eligible customer", result);
        assertEquals("Customer should have one application", 1, customerC001.getApplications().size());
        
        IPOApplication createdApp = customerC001.getApplications().get(0);
        assertEquals("Application should be for TechCorp", techCorp, createdApp.getCompany());
        assertEquals("Application should have 100 shares", 100, createdApp.getNumberOfShares());
        assertEquals("Application should have $5000 amount", 5000.0, createdApp.getAmountPaid(), 0.001);
        assertEquals("Application should have documentA", documentA, createdApp.getLegalDocument());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Setup: Customer has lost IPO eligibility after repeated failed attempts
        customerC002.setFailedAttempts(3); // Set to maximum failed attempts to make ineligible
        
        boolean result = customerC002.createIPOApplication(bioMed, 50, 2500.0, documentB);
        
        // Verify that application creation failed due to ineligibility
        assertFalse("Application should fail for ineligible customer", result);
        assertEquals("Customer should have no applications", 0, customerC002.getApplications().size());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Setup: Customer remains eligible and submits first application
        boolean firstResult = customerC003.createIPOApplication(greenEnergy, 10, 300.0, documentG);
        assertTrue("First application should be created successfully", firstResult);
        
        // Approve the first application
        IPOApplication firstApp = customerC003.getApplications().get(0);
        boolean approvalResult = customerC003.approveOrRejectApplication(firstApp, true);
        assertTrue("First application should be approved successfully", approvalResult);
        assertEquals("Application status should be APPROVED", ApplicationStatus.APPROVED, firstApp.getStatus());
        
        // Attempt to create another application for the same company
        boolean secondResult = customerC003.createIPOApplication(greenEnergy, 10, 300.0, documentG);
        
        // Verify that second application creation fails due to duplicate approved application
        assertFalse("Second application should fail due to existing approved application", secondResult);
        assertEquals("Customer should still have only one application", 1, customerC003.getApplications().size());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Setup: No prior applications for AutoFuture
        boolean result = customerC004.createIPOApplication(autoFuture, 25, 1000.0, null);
        
        // Verify that application creation fails due to missing document
        assertFalse("Application should fail when document is null", result);
        assertEquals("Customer should have no applications", 0, customerC004.getApplications().size());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Setup: No existing applications for NanoChip
        boolean result = customerC005.createIPOApplication(nanoChip, 0, 0.0, documentN);
        
        // Verify that application creation fails due to zero shares
        assertFalse("Application should fail when shares are zero", result);
        assertEquals("Customer should have no applications", 0, customerC005.getApplications().size());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Setup: No prior applications for CloudServ
        boolean result = customerC006.createIPOApplication(cloudServ, -5, -200.0, documentC);
        
        // Verify that application creation fails due to negative shares
        assertFalse("Application should fail when shares are negative", result);
        assertEquals("Customer should have no applications", 0, customerC006.getApplications().size());
    }
}