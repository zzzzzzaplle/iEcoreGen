import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private IPOApplicationSystem system;
    
    @Before
    public void setUp() {
        // Reset the system before each test
        system = new IPOApplicationSystem();
    }
    
    @Test
    public void testCase1_standardEligibleSubmission() {
        // Setup: Create eligible customer with no prior approved applications for TechCorp
        Customer customer = new Customer();
        customer.setEligibleForIPO(true);
        
        // Input: Apply for TechCorp with valid parameters
        boolean result = system.createIPOApplication(customer, "TechCorp", 100, 5000.0, "A");
        
        // Expected Output: True (application is successfully created)
        assertTrue("Application should be created successfully for eligible customer", result);
        
        // Verify application was added to customer's list
        assertEquals("Customer should have 1 application", 1, customer.getApplications().size());
        
        // Verify application was added to system's list
        assertEquals("System should have 1 application", 1, system.getApplications().size());
        
        // Verify application details
        IPOApplication app = customer.getApplications().get(0);
        assertEquals("Company name should be TechCorp", "TechCorp", app.getCompanyName());
        assertEquals("Number of shares should be 100", 100, app.getNumberOfShares());
        assertEquals("Amount should be 5000.0", 5000.0, app.getAmountOfMoney(), 0.001);
        assertEquals("Document should be A", "A", app.getDocument());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Setup: Create ineligible customer with no prior applications for BioMed
        Customer customer = new Customer();
        customer.setEligibleForIPO(false); // Customer lost eligibility
        
        // Input: Attempt to apply for BioMed with valid parameters
        boolean result = system.createIPOApplication(customer, "BioMed", 50, 2500.0, "B");
        
        // Expected Output: False
        assertFalse("Application should fail for ineligible customer", result);
        
        // Verify no application was created
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
        assertEquals("System should have 0 applications", 0, system.getApplications().size());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Setup: Create eligible customer with approved application for GreenEnergy
        Customer customer = new Customer();
        customer.setEligibleForIPO(true);
        
        // Create and approve first application for GreenEnergy
        IPOApplication firstApp = new IPOApplication();
        firstApp.setCompanyName("GreenEnergy");
        firstApp.setNumberOfShares(10);
        firstApp.setAmountOfMoney(300.0);
        firstApp.setDocument("G");
        firstApp.setApproved(true); // Mark as approved
        
        customer.getApplications().add(firstApp);
        system.getApplications().add(firstApp);
        
        // Input: Attempt to submit another application to the same company
        boolean result = system.createIPOApplication(customer, "GreenEnergy", 10, 300.0, "G");
        
        // Expected Output: False (only one approved application per company allowed)
        assertFalse("Duplicate application should be rejected", result);
        
        // Verify only one application exists (the original approved one)
        assertEquals("Customer should still have only 1 application", 1, customer.getApplications().size());
        assertEquals("System should still have only 1 application", 1, system.getApplications().size());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Setup: Create eligible customer with no prior applications for AutoFuture
        Customer customer = new Customer();
        customer.setEligibleForIPO(true);
        
        // Input: Apply without uploading document (null document)
        boolean result = system.createIPOApplication(customer, "AutoFuture", 25, 1000.0, null);
        
        // Expected Output: False (document upload is mandatory)
        assertFalse("Application should fail without document", result);
        
        // Verify no application was created
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
        assertEquals("System should have 0 applications", 0, system.getApplications().size());
        
        // Verify failed attempts were incremented
        assertEquals("Failed attempts should be incremented", 1, customer.getFailedAttempts());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Setup: Create eligible customer with no prior applications for NanoChip
        Customer customer = new Customer();
        customer.setEligibleForIPO(true);
        
        // Input: Apply with 0 shares
        boolean result = system.createIPOApplication(customer, "NanoChip", 0, 0.0, "N");
        
        // Expected Output: False
        assertFalse("Application should fail with 0 shares", result);
        
        // Verify no application was created
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
        assertEquals("System should have 0 applications", 0, system.getApplications().size());
        
        // Verify failed attempts were incremented
        assertEquals("Failed attempts should be incremented", 1, customer.getFailedAttempts());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Setup: Create eligible customer with no prior applications for CloudServ
        Customer customer = new Customer();
        customer.setEligibleForIPO(true);
        
        // Input: Apply with negative shares
        boolean result = system.createIPOApplication(customer, "CloudServ", -5, -200.0, "C");
        
        // Expected Output: False (negative shares/amount are invalid)
        assertFalse("Application should fail with negative shares", result);
        
        // Verify no application was created
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
        assertEquals("System should have 0 applications", 0, system.getApplications().size());
        
        // Verify failed attempts were incremented
        assertEquals("Failed attempts should be incremented", 1, customer.getFailedAttempts());
    }
}