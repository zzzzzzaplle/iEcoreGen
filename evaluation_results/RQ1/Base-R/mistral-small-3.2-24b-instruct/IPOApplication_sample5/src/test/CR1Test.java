import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Bank bank;
    private Customer eligibleCustomer;
    private Customer ineligibleCustomer;
    private Customer customerWithApprovedApp;
    
    @Before
    public void setUp() {
        bank = new Bank();
        
        // Setup eligible customer (C001)
        eligibleCustomer = new Customer();
        eligibleCustomer.setName("John");
        eligibleCustomer.setSurname("Smith");
        eligibleCustomer.setEmail("john.smith@example.com");
        eligibleCustomer.setTelephone("555-1234");
        eligibleCustomer.setEligible(true);
        
        // Setup ineligible customer (C002)
        ineligibleCustomer = new Customer();
        ineligibleCustomer.setName("Alice");
        ineligibleCustomer.setSurname("Johnson");
        ineligibleCustomer.setEmail("alice.j@example.com");
        ineligibleCustomer.setTelephone("555-5678");
        ineligibleCustomer.setEligible(false);
        
        // Setup customer with approved application (C003)
        customerWithApprovedApp = new Customer();
        customerWithApprovedApp.setName("Robert");
        customerWithApprovedApp.setSurname("Chen");
        customerWithApprovedApp.setEmail("r.chen@example.com");
        customerWithApprovedApp.setTelephone("555-9012");
        customerWithApprovedApp.setEligible(true);
    }
    
    @Test
    public void testCase1_standardEligibleSubmission() {
        // Test Case 1: "Standard eligible submission"
        // Input: An eligible customer applies for an IPO with company "TechCorp"
        boolean result = bank.createIpoApplication(eligibleCustomer, "TechCorp", 100, 5000.0, "A");
        
        // Expected Output: True (application is successfully created)
        assertTrue("Application should be created successfully for eligible customer", result);
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Test Case 2: "Customer not eligible"
        // Input: Ineligible customer attempts to apply for an IPO
        boolean result = bank.createIpoApplication(ineligibleCustomer, "BioMed", 50, 2500.0, "B");
        
        // Expected Output: False
        assertFalse("Application should fail for ineligible customer", result);
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Test Case 3: "Duplicate approved application"
        // Setup: Create and approve an application first
        boolean firstResult = bank.createIpoApplication(customerWithApprovedApp, "GreenEnergy", 10, 300.0, "G");
        assertTrue("First application should be created successfully", firstResult);
        
        // Get the application ID to approve it
        String applicationId = customerWithApprovedApp.getEmail() + "_" + "GreenEnergy";
        boolean approvalResult = bank.approveOrRejectApplication(applicationId, true);
        assertTrue("Application should be approved successfully", approvalResult);
        
        // Now attempt to create another application for the same company
        boolean secondResult = bank.createIpoApplication(customerWithApprovedApp, "GreenEnergy", 10, 300.0, "G");
        
        // Expected Output: False (only one approved application per company allowed)
        assertFalse("Second application should fail due to existing approved application", secondResult);
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Test Case 4: "Missing document"
        // Input: Eligible customer applies but forgets to upload any document
        boolean result = bank.createIpoApplication(eligibleCustomer, "AutoFuture", 25, 1000.0, null);
        
        // Expected Output: False (document upload is mandatory)
        assertFalse("Application should fail when document is null", result);
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Test Case 5: "Zero-share application"
        // Input: Eligible customer applies for 0 shares
        boolean result = bank.createIpoApplication(eligibleCustomer, "NanoChip", 0, 0.0, "N");
        
        // Expected Output: False
        assertFalse("Application should fail for zero shares", result);
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Test Case 6: "Negative share count"
        // Input: Eligible customer attempts to apply for negative shares
        boolean result = bank.createIpoApplication(eligibleCustomer, "CloudServ", -5, -200.0, "C");
        
        // Expected Output: False (negative shares/amount are invalid)
        assertFalse("Application should fail for negative shares", result);
    }
}