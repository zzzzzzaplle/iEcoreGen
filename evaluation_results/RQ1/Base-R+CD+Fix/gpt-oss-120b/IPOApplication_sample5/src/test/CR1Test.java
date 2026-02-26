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
        // Create eligible customer C001
        eligibleCustomer = new Customer("John", "Smith", "john.smith@example.com", "555-1234", true);
        
        // Create ineligible customer C002
        ineligibleCustomer = new Customer("Alice", "Johnson", "alice.j@example.com", "555-5678", false);
        
        // Create companies
        techCorp = new Company("TechCorp", "techcorp@gmail.com");
        bioMed = new Company("BioMed", "biomed@gmail.com");
        greenEnergy = new Company("GreenEnergy", "greenenergy@gmail.com");
        autoFuture = new Company("AutoFuture", "autofuture@gmail.com");
        nanoChip = new Company("NanoChip", "nanotech@gmail.com");
        cloudServ = new Company("CloudServ", "cloudserv@gmail.com");
        
        // Create documents
        documentA = new Document();
        documentB = new Document();
        documentG = new Document();
        documentN = new Document();
        documentC = new Document();
    }
    
    @Test
    public void testCase1_standardEligibleSubmission() {
        // Test Case 1: Standard eligible submission
        // Setup: Customer "C001" allows IPO applications and has no previously approved applications for "TechCorp"
        boolean result = eligibleCustomer.createApplication(techCorp, 100, 5000.0, documentA);
        
        // Expected Output: True (application is successfully created)
        assertTrue("Application should be created successfully for eligible customer", result);
        assertEquals("Customer should have exactly 1 application", 1, eligibleCustomer.getApplications().size());
        
        Application createdApp = eligibleCustomer.getApplications().get(0);
        assertEquals("Application status should be PENDING", ApplicationStatus.PENDING, createdApp.getStatus());
        assertEquals("Application should be for TechCorp", "TechCorp", createdApp.getCompany().getName());
        assertEquals("Application should have 100 shares", 100, createdApp.getShare());
        assertEquals("Application should have $5000 amount", 5000.0, createdApp.getAmountOfMoney(), 0.001);
        assertSame("Application should reference the correct customer", eligibleCustomer, createdApp.getCustomer());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Test Case 2: Customer not eligible
        // Setup: Customer has lost IPO eligibility after repeated failed attempts
        boolean result = ineligibleCustomer.createApplication(bioMed, 50, 2500.0, documentB);
        
        // Expected Output: False
        assertFalse("Application should not be created for ineligible customer", result);
        assertTrue("Customer should have no applications", ineligibleCustomer.getApplications().isEmpty());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Test Case 3: Duplicate approved application
        // Setup: Customer remains eligible and has an approved application for GreenEnergy
        
        // First application - should succeed
        boolean firstResult = eligibleCustomer.createApplication(greenEnergy, 10, 300.0, documentG);
        assertTrue("First application should be created successfully", firstResult);
        
        // Approve the first application
        Application firstApp = eligibleCustomer.getApplications().get(0);
        boolean approvalResult = firstApp.approve();
        assertTrue("First application should be approved successfully", approvalResult);
        assertEquals("First application status should be APPROVAL", ApplicationStatus.APPROVAL, firstApp.getStatus());
        
        // Second application for same company - should fail
        boolean secondResult = eligibleCustomer.createApplication(greenEnergy, 15, 450.0, documentG);
        
        // Expected Output: False (only one approved application per company allowed)
        assertFalse("Second application for same company should not be created", secondResult);
        assertEquals("Customer should have exactly 1 application", 1, eligibleCustomer.getApplications().size());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Test Case 4: Missing document
        // Setup: No prior applications for AutoFuture
        boolean result = eligibleCustomer.createApplication(autoFuture, 25, 1000.0, null);
        
        // Expected Output: False (document upload is mandatory)
        assertFalse("Application should not be created without document", result);
        assertTrue("Customer should have no applications", eligibleCustomer.getApplications().isEmpty());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Test Case 5: Zero-share application
        // Setup: No existing applications for NanoChip
        boolean result = eligibleCustomer.createApplication(nanoChip, 0, 0.0, documentN);
        
        // Expected Output: False
        assertFalse("Application should not be created with zero shares", result);
        assertTrue("Customer should have no applications", eligibleCustomer.getApplications().isEmpty());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Test Case 6: Negative share count
        // Setup: No prior applications for CloudServ
        boolean result = eligibleCustomer.createApplication(cloudServ, -5, -200.0, documentC);
        
        // Expected Output: False (negative shares/amount are invalid)
        assertFalse("Application should not be created with negative shares", result);
        assertTrue("Customer should have no applications", eligibleCustomer.getApplications().isEmpty());
    }
}