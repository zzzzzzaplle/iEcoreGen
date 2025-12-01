import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    private Customer eligibleCustomer;
    private Customer ineligibleCustomer;
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
        // Setup eligible customer (C001)
        eligibleCustomer = new Customer();
        eligibleCustomer.setName("John");
        eligibleCustomer.setSurname("Smith");
        eligibleCustomer.setEmail("john.smith@example.com");
        eligibleCustomer.setTelephone("555-1234");
        eligibleCustomer.setCanApplyForIPO(true);
        
        // Setup ineligible customer (C002)
        ineligibleCustomer = new Customer();
        ineligibleCustomer.setName("Alice");
        ineligibleCustomer.setSurname("Johnson");
        ineligibleCustomer.setEmail("alice.j@example.com");
        ineligibleCustomer.setTelephone("555-5678");
        ineligibleCustomer.setCanApplyForIPO(false);
        
        // Setup companies
        techCorp = new Company("TechCorp", "techcorp@gmail.com");
        bioMed = new Company("BioMed", "biomed@gmail.com");
        greenEnergy = new Company("GreenEnergy", "greenenergy@gmail.com");
        autoFuture = new Company("AutoFuture", "autofuture@gmail.com");
        nanoChip = new Company("NanoChip", "nanotech@gmail.com");
        cloudServ = new Company("CloudServ", "cloudserv@gmail.com");
        
        // Setup documents
        documentA = new Document();
        documentB = new Document();
        documentG = new Document();
        documentN = new Document();
        documentC = new Document();
    }
    
    @Test
    public void testCase1_StandardEligibleSubmission() {
        // Test Case 1: Standard eligible submission
        boolean result = eligibleCustomer.createApplication(techCorp, 100, 5000.0, documentA);
        assertTrue("Application should be successfully created for eligible customer", result);
    }
    
    @Test
    public void testCase2_CustomerNotEligible() {
        // Test Case 2: Customer not eligible
        boolean result = ineligibleCustomer.createApplication(bioMed, 50, 2500.0, documentB);
        assertFalse("Application should fail for ineligible customer", result);
    }
    
    @Test
    public void testCase3_DuplicateApprovedApplication() {
        // Test Case 3: Duplicate approved application
        // First create and approve an application
        boolean firstResult = eligibleCustomer.createApplication(greenEnergy, 10, 300.0, documentG);
        assertTrue("First application should be created successfully", firstResult);
        
        // Approve the application
        Application firstApp = eligibleCustomer.getApplications().get(0);
        firstApp.approve();
        
        // Try to create another application for the same company
        boolean secondResult = eligibleCustomer.createApplication(greenEnergy, 10, 300.0, documentG);
        assertFalse("Second application for same company should fail", secondResult);
    }
    
    @Test
    public void testCase4_MissingDocument() {
        // Test Case 4: Missing document
        boolean result = eligibleCustomer.createApplication(autoFuture, 25, 1000.0, null);
        assertFalse("Application should fail when document is null", result);
    }
    
    @Test
    public void testCase5_ZeroShareApplication() {
        // Test Case 5: Zero-share application
        boolean result = eligibleCustomer.createApplication(nanoChip, 0, 0.0, documentN);
        assertFalse("Application should fail with zero shares", result);
    }
    
    @Test
    public void testCase6_NegativeShareCount() {
        // Test Case 6: Negative share count
        boolean result = eligibleCustomer.createApplication(cloudServ, -5, -200.0, documentC);
        assertFalse("Application should fail with negative shares", result);
    }
}