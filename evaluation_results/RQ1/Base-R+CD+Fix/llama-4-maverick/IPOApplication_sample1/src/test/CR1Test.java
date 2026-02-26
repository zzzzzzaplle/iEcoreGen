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
        // Setup common test data
        eligibleCustomer = new Customer("John", "Smith", "john.smith@example.com", "555-1234");
        eligibleCustomer.setCanApplyForIPO(true);
        
        ineligibleCustomer = new Customer("Alice", "Johnson", "alice.j@example.com", "555-5678");
        ineligibleCustomer.setCanApplyForIPO(false);
        
        techCorp = new Company("TechCorp", "techcorp@gmail.com");
        bioMed = new Company("BioMed", "biomed@gmail.com");
        greenEnergy = new Company("GreenEnergy", "greenenergy@gmail.com");
        autoFuture = new Company("AutoFuture", "autofuture@gmail.com");
        nanoChip = new Company("NanoChip", "nanotech@gmail.com");
        cloudServ = new Company("CloudServ", "cloudserv@gmail.com");
        
        documentA = new Document();
        documentB = new Document();
        documentG = new Document();
        documentN = new Document();
        documentC = new Document();
    }
    
    @Test
    public void testCase1_standardEligibleSubmission() {
        // Test Case 1: Standard eligible submission
        // Input: An eligible customer applies for an IPO with company "TechCorp", 
        // requesting 100 shares with a payment of $5,000, and uploads the allowance document 'A'
        
        boolean result = eligibleCustomer.createApplication(techCorp, 100, 5000.0, documentA);
        
        // Expected Output: True (application is successfully created)
        assertTrue("Application should be created successfully for eligible customer", result);
        assertEquals("Customer should have one application", 1, eligibleCustomer.getApplications().size());
        assertEquals("Application status should be PENDING", ApplicationStatus.PENDING, 
                     eligibleCustomer.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Test Case 2: Customer not eligible
        // Input: Customer "C002" attempts to apply for an IPO with company "BioMed", 
        // requesting 50 shares with $2,500 payment, and uploads document 'B'
        
        boolean result = ineligibleCustomer.createApplication(bioMed, 50, 2500.0, documentB);
        
        // Expected Output: False
        assertFalse("Application should not be created for ineligible customer", result);
        assertEquals("Ineligible customer should have no applications", 0, ineligibleCustomer.getApplications().size());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Test Case 3: Duplicate approved application
        // Input: Customer submits another application to "GreenEnergy" for 10 shares ($300) with document 'G'
        
        // Setup: Create and approve first application
        Customer customer = new Customer("Robert", "Chen", "r.chen@example.com", "555-9012");
        customer.setCanApplyForIPO(true);
        
        // Create first application
        boolean firstResult = customer.createApplication(greenEnergy, 10, 300.0, documentG);
        assertTrue("First application should be created successfully", firstResult);
        
        // Approve the first application
        Application firstApp = customer.getApplications().get(0);
        firstApp.approve();
        
        // Attempt to create second application for same company
        boolean secondResult = customer.createApplication(greenEnergy, 10, 300.0, documentG);
        
        // Expected Output: False (only one approved application per company allowed)
        assertFalse("Second application should not be created for same company", secondResult);
        assertEquals("Customer should still have only one application", 1, customer.getApplications().size());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Test Case 4: Missing document
        // Input: Eligible customer applies to "AutoFuture" for 25 shares ($1,000) but forgets to upload any document
        
        Customer customer = new Customer("Emma", "Davis", "emma.d@example.com", "555-3456");
        customer.setCanApplyForIPO(true);
        
        boolean result = customer.createApplication(autoFuture, 25, 1000.0, null);
        
        // Expected Output: False (document upload is mandatory)
        assertFalse("Application should not be created without document", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Test Case 5: Zero-share application
        // Input: Eligible customer applies to "NanoChip" for 0 shares ($0) with document 'N'
        
        Customer customer = new Customer("James", "Wilson", "j.wilson@example.com", "555-7890");
        customer.setCanApplyForIPO(true);
        
        boolean result = customer.createApplication(nanoChip, 0, 0.0, documentN);
        
        // Expected Output: False
        assertFalse("Application should not be created with zero shares", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Test Case 6: Negative share count
        // Input: Eligible customer attempts to apply to "CloudServ" for -5 shares (-$200) with document 'C'
        
        Customer customer = new Customer("Sophia", "Martinez", "s.m@example.com", "555-2345");
        customer.setCanApplyForIPO(true);
        
        boolean result = customer.createApplication(cloudServ, -5, -200.0, documentC);
        
        // Expected Output: False (negative shares/amount are invalid)
        assertFalse("Application should not be created with negative shares", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
}