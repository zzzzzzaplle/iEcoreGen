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
    private Document validDocument;
    
    @Before
    public void setUp() {
        // Create test customers
        eligibleCustomer = new Customer("John", "Smith", "john.smith@example.com", "555-1234");
        eligibleCustomer.setCanApplyForIPO(true);
        
        ineligibleCustomer = new Customer("Alice", "Johnson", "alice.j@example.com", "555-5678");
        ineligibleCustomer.setCanApplyForIPO(false);
        
        // Create test companies
        techCorp = new Company("TechCorp", "techcorp@gmail.com");
        bioMed = new Company("BioMed", "biomed@gmail.com");
        greenEnergy = new Company("GreenEnergy", "greenenergy@gmail.com");
        autoFuture = new Company("AutoFuture", "autofuture@gmail.com");
        nanoChip = new Company("NanoChip", "nanotech@gmail.com");
        cloudServ = new Company("CloudServ", "cloudserv@gmail.com");
        
        // Create a valid document
        validDocument = new Document();
    }
    
    @Test
    public void testCase1_StandardEligibleSubmission() {
        // Test Case 1: Standard eligible submission
        // Setup: Eligible customer with no prior approved applications for TechCorp
        boolean result = eligibleCustomer.createApplication(techCorp, 100, 5000.0, validDocument);
        
        // Verify application was created successfully
        assertTrue("Application should be created successfully for eligible customer", result);
        assertEquals("Customer should have 1 application", 1, eligibleCustomer.getApplications().size());
        
        Application createdApp = eligibleCustomer.getApplications().get(0);
        assertEquals("Application status should be PENDING", ApplicationStatus.PENDING, createdApp.getStatus());
        assertEquals("Share count should match", 100, createdApp.getShare());
        assertEquals("Amount should match", 5000.0, createdApp.getAmountOfMoney(), 0.001);
        assertEquals("Company should match", techCorp, createdApp.getCompany());
        assertEquals("Customer should match", eligibleCustomer, createdApp.getCustomer());
    }
    
    @Test
    public void testCase2_CustomerNotEligible() {
        // Test Case 2: Customer not eligible
        // Setup: Ineligible customer attempting to apply
        boolean result = ineligibleCustomer.createApplication(bioMed, 50, 2500.0, validDocument);
        
        // Verify application was rejected due to ineligibility
        assertFalse("Application should fail for ineligible customer", result);
        assertTrue("Customer should have no applications", ineligibleCustomer.getApplications().isEmpty());
    }
    
    @Test
    public void testCase3_DuplicateApprovedApplication() {
        // Test Case 3: Duplicate approved application
        // Setup: Create and approve first application
        Customer customer = new Customer("Robert", "Chen", "r.chen@example.com", "555-9012");
        customer.setCanApplyForIPO(true);
        
        // Create first application and approve it
        boolean firstResult = customer.createApplication(greenEnergy, 10, 300.0, validDocument);
        assertTrue("First application should be created", firstResult);
        
        Application firstApp = customer.getApplications().get(0);
        firstApp.approve(); // Approve the application
        
        // Attempt to create second application for same company
        boolean secondResult = customer.createApplication(greenEnergy, 10, 300.0, validDocument);
        
        // Verify second application was rejected due to duplicate approved application
        assertFalse("Second application should fail due to duplicate approved application", secondResult);
        assertEquals("Customer should still have only 1 application", 1, customer.getApplications().size());
    }
    
    @Test
    public void testCase4_MissingDocument() {
        // Test Case 4: Missing document
        // Setup: Eligible customer with null document
        Customer customer = new Customer("Emma", "Davis", "emma.d@example.com", "555-3456");
        customer.setCanApplyForIPO(true);
        
        boolean result = customer.createApplication(autoFuture, 25, 1000.0, null);
        
        // Verify application was rejected due to missing document
        assertFalse("Application should fail with null document", result);
        assertTrue("Customer should have no applications", customer.getApplications().isEmpty());
    }
    
    @Test
    public void testCase5_ZeroShareApplication() {
        // Test Case 5: Zero-share application
        // Setup: Eligible customer with zero shares
        Customer customer = new Customer("James", "Wilson", "j.wilson@example.com", "555-7890");
        customer.setCanApplyForIPO(true);
        
        boolean result = customer.createApplication(nanoChip, 0, 0.0, validDocument);
        
        // Verify application was rejected due to zero shares
        assertFalse("Application should fail with zero shares", result);
        assertTrue("Customer should have no applications", customer.getApplications().isEmpty());
    }
    
    @Test
    public void testCase6_NegativeShareCount() {
        // Test Case 6: Negative share count
        // Setup: Eligible customer with negative shares
        Customer customer = new Customer("Sophia", "Martinez", "s.m@example.com", "555-2345");
        customer.setCanApplyForIPO(true);
        
        boolean result = customer.createApplication(cloudServ, -5, -200.0, validDocument);
        
        // Verify application was rejected due to negative shares
        assertFalse("Application should fail with negative shares", result);
        assertTrue("Customer should have no applications", customer.getApplications().isEmpty());
    }
}