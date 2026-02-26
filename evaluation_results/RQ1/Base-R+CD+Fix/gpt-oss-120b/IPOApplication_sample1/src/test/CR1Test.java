import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private Customer eligibleCustomer;
    private Customer ineligibleCustomer;
    private Customer customerWithExistingApproval;
    private Company techCorp;
    private Company bioMed;
    private Company greenEnergy;
    private Company autoFuture;
    private Company nanoChip;
    private Company cloudServ;
    private Document validDocument;
    
    @Before
    public void setUp() {
        // Setup eligible customer for Test Case 1
        eligibleCustomer = new Customer("John", "Smith", "john.smith@example.com", "555-1234");
        eligibleCustomer.setCanApplyForIPO(true);
        
        // Setup ineligible customer for Test Case 2
        ineligibleCustomer = new Customer("Alice", "Johnson", "alice.j@example.com", "555-5678");
        ineligibleCustomer.setCanApplyForIPO(false);
        
        // Setup customer with existing approval for Test Case 3
        customerWithExistingApproval = new Customer("Robert", "Chen", "r.chen@example.com", "555-9012");
        customerWithExistingApproval.setCanApplyForIPO(true);
        
        // Setup companies
        techCorp = new Company("TechCorp", "techcorp@gmail.com");
        bioMed = new Company("BioMed", "biomed@gmail.com");
        greenEnergy = new Company("GreenEnergy", "greenenergy@gmail.com");
        autoFuture = new Company("AutoFuture", "autofuture@gmail.com");
        nanoChip = new Company("NanoChip", "nanotech@gmail.com");
        cloudServ = new Company("CloudServ", "cloudserv@gmail.com");
        
        // Setup valid document
        validDocument = new Document();
    }
    
    @Test
    public void testCase1_standardEligibleSubmission() {
        // Test eligible customer with valid parameters and no existing approval for the company
        boolean result = eligibleCustomer.createApplication(techCorp, 100, 5000.0, validDocument);
        
        // Verify application was created successfully
        assertTrue("Application should be created successfully for eligible customer", result);
        assertEquals("Customer should have one application", 1, eligibleCustomer.getApplications().size());
        
        Application createdApp = eligibleCustomer.getApplications().get(0);
        assertEquals("Application should be in PENDING status", ApplicationStatus.PENDING, createdApp.getStatus());
        assertEquals("Application should be for TechCorp", techCorp, createdApp.getCompany());
        assertEquals("Application should have 100 shares", 100, createdApp.getShare());
        assertEquals("Application should have $5000 amount", 5000.0, createdApp.getAmountOfMoney(), 0.001);
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Test ineligible customer attempting to create application
        boolean result = ineligibleCustomer.createApplication(bioMed, 50, 2500.0, validDocument);
        
        // Verify application creation fails due to ineligibility
        assertFalse("Application should not be created for ineligible customer", result);
        assertEquals("Customer should have no applications", 0, ineligibleCustomer.getApplications().size());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // First, create and approve an application for GreenEnergy
        boolean firstResult = customerWithExistingApproval.createApplication(greenEnergy, 10, 300.0, validDocument);
        assertTrue("First application should be created successfully", firstResult);
        
        // Approve the first application
        Application firstApp = customerWithExistingApproval.getApplications().get(0);
        boolean approvalResult = firstApp.approve();
        assertTrue("First application should be approved", approvalResult);
        assertEquals("First application should be in APPROVAL status", ApplicationStatus.APPROVAL, firstApp.getStatus());
        
        // Attempt to create another application for the same company
        boolean secondResult = customerWithExistingApproval.createApplication(greenEnergy, 15, 450.0, validDocument);
        
        // Verify second application creation fails due to existing approved application
        assertFalse("Second application should not be created due to existing approved application", secondResult);
        assertEquals("Customer should still have only one application", 1, customerWithExistingApproval.getApplications().size());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Test application creation with null document
        boolean result = eligibleCustomer.createApplication(autoFuture, 25, 1000.0, null);
        
        // Verify application creation fails due to missing document
        assertFalse("Application should not be created without document", result);
        assertEquals("Customer should have no applications", 0, eligibleCustomer.getApplications().size());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Test application creation with zero shares
        boolean result = eligibleCustomer.createApplication(nanoChip, 0, 0.0, validDocument);
        
        // Verify application creation fails due to zero shares/amount
        assertFalse("Application should not be created with zero shares", result);
        assertEquals("Customer should have no applications", 0, eligibleCustomer.getApplications().size());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Test application creation with negative shares and amount
        boolean result = eligibleCustomer.createApplication(cloudServ, -5, -200.0, validDocument);
        
        // Verify application creation fails due to negative values
        assertFalse("Application should not be created with negative shares/amount", result);
        assertEquals("Customer should have no applications", 0, eligibleCustomer.getApplications().size());
    }
}