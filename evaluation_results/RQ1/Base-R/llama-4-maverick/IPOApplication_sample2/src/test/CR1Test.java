import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private IPOApplicationService service;
    private Customer eligibleCustomer;
    private Customer restrictedCustomer;
    
    @Before
    public void setUp() {
        service = new IPOApplicationService();
        
        // Setup eligible customer (C001)
        eligibleCustomer = new Customer();
        eligibleCustomer.setName("John");
        eligibleCustomer.setSurname("Smith");
        eligibleCustomer.setEmail("john.smith@example.com");
        eligibleCustomer.setTelephoneNumber("555-1234");
        eligibleCustomer.setRestricted(false);
        
        // Setup restricted customer (C002)
        restrictedCustomer = new Customer();
        restrictedCustomer.setName("Alice");
        restrictedCustomer.setSurname("Johnson");
        restrictedCustomer.setEmail("alice.j@example.com");
        restrictedCustomer.setTelephoneNumber("555-5678");
        restrictedCustomer.setRestricted(true);
    }
    
    @Test
    public void testCase1_standardEligibleSubmission() {
        // Test Case 1: Standard eligible submission
        // Input: Eligible customer applies for IPO with valid parameters
        boolean result = service.createApplication(eligibleCustomer, "TechCorp", 100, 5000.0, "A");
        
        // Verify application was created successfully
        assertTrue("Application should be created successfully for eligible customer", result);
        assertEquals("Customer should have one application", 1, eligibleCustomer.getApplications().size());
        
        IPOApplication createdApp = eligibleCustomer.getApplications().get(0);
        assertEquals("Application should be for TechCorp", "TechCorp", createdApp.getCompanyName());
        assertEquals("Number of shares should be 100", 100, createdApp.getNumberOfShares());
        assertEquals("Amount should be 5000.0", 5000.0, createdApp.getAmount(), 0.001);
        assertEquals("Document should be 'A'", "A", createdApp.getDocument());
        assertEquals("Status should be PENDING", ApplicationStatus.PENDING, createdApp.getStatus());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Test Case 2: Customer not eligible
        // Input: Restricted customer attempts to apply for IPO
        boolean result = service.createApplication(restrictedCustomer, "BioMed", 50, 2500.0, "B");
        
        // Verify application creation fails due to customer restriction
        assertFalse("Application should fail for restricted customer", result);
        assertEquals("Restricted customer should have no applications", 0, restrictedCustomer.getApplications().size());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Test Case 3: Duplicate approved application
        // Setup: Create and approve first application for GreenEnergy
        IPOApplication firstApp = new IPOApplication();
        firstApp.setCustomer(eligibleCustomer);
        firstApp.setCompanyName("GreenEnergy");
        firstApp.setNumberOfShares(10);
        firstApp.setAmount(300.0);
        firstApp.setDocument("G");
        firstApp.setStatus(ApplicationStatus.APPROVED);
        eligibleCustomer.getApplications().add(firstApp);
        
        // Input: Attempt to create another application for the same company
        boolean result = service.createApplication(eligibleCustomer, "GreenEnergy", 10, 300.0, "G");
        
        // Verify second application creation fails due to existing approved application
        assertFalse("Application should fail due to duplicate approved application", result);
        assertEquals("Customer should still have only one application", 1, eligibleCustomer.getApplications().size());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Test Case 4: Missing document
        // Input: Eligible customer applies without uploading document
        boolean result = service.createApplication(eligibleCustomer, "AutoFuture", 25, 1000.0, null);
        
        // Verify application creation fails due to missing document
        assertFalse("Application should fail when document is null", result);
        assertEquals("Customer should have no applications", 0, eligibleCustomer.getApplications().size());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Test Case 5: Zero-share application
        // Input: Eligible customer applies with zero shares
        boolean result = service.createApplication(eligibleCustomer, "NanoChip", 0, 0.0, "N");
        
        // Verify application creation fails due to zero shares
        assertFalse("Application should fail when number of shares is zero", result);
        assertEquals("Customer should have no applications", 0, eligibleCustomer.getApplications().size());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Test Case 6: Negative share count
        // Input: Eligible customer applies with negative shares
        boolean result = service.createApplication(eligibleCustomer, "CloudServ", -5, -200.0, "C");
        
        // Verify application creation fails due to negative shares
        assertFalse("Application should fail when number of shares is negative", result);
        assertEquals("Customer should have no applications", 0, eligibleCustomer.getApplications().size());
    }
}