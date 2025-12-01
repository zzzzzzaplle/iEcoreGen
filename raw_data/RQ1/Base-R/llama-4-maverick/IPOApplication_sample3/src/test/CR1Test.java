import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Bank bank;
    private Customer eligibleCustomer;
    private Customer restrictedCustomer;
    private Customer customerWithApprovedApp;
    
    @Before
    public void setUp() {
        bank = new Bank();
        
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
        
        // Setup customer with approved application (C003)
        customerWithApprovedApp = new Customer();
        customerWithApprovedApp.setName("Robert");
        customerWithApprovedApp.setSurname("Chen");
        customerWithApprovedApp.setEmail("r.chen@example.com");
        customerWithApprovedApp.setTelephoneNumber("555-9012");
        customerWithApprovedApp.setRestricted(false);
        
        // Add customer with approved application to bank
        bank.customers.add(customerWithApprovedApp);
    }
    
    @Test
    public void testCase1_standardEligibleSubmission() {
        // Test Case 1: Standard eligible submission
        // Setup: Customer "C001" allows IPO applications and has no previously approved applications for "TechCorp"
        
        boolean result = bank.createIPOApplication(eligibleCustomer, "TechCorp", 100, 5000.0, "A");
        
        assertTrue("Application should be successfully created for eligible customer", result);
        assertEquals("Customer should have one application", 1, eligibleCustomer.getApplications().size());
        assertEquals("Application company should be TechCorp", "TechCorp", eligibleCustomer.getApplications().get(0).getCompanyName());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Test Case 2: Customer not eligible
        // Setup: Customer has lost IPO eligibility after repeated failed attempts
        
        boolean result = bank.createIPOApplication(restrictedCustomer, "BioMed", 50, 2500.0, "B");
        
        assertFalse("Application should be rejected for restricted customer", result);
        assertEquals("Restricted customer should have no applications", 0, restrictedCustomer.getApplications().size());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Test Case 3: Duplicate approved application
        // Setup: Customer remains eligible, submit application to "GreenEnergy", then approve it
        
        // First application creation and approval
        boolean firstResult = bank.createIPOApplication(customerWithApprovedApp, "GreenEnergy", 10, 300.0, "G");
        assertTrue("First application should be created successfully", firstResult);
        
        // Get the application and approve it
        IPOApplication firstApplication = customerWithApprovedApp.getApplications().get(0);
        boolean approvalResult = bank.approveOrRejectApplication(firstApplication, true);
        assertTrue("First application should be approved successfully", approvalResult);
        assertEquals("First application status should be APPROVED", ApplicationStatus.APPROVED, firstApplication.getStatus());
        
        // Attempt to create second application for same company
        boolean secondResult = bank.createIPOApplication(customerWithApprovedApp, "GreenEnergy", 10, 300.0, "G");
        
        assertFalse("Second application should be rejected due to existing approved application", secondResult);
        assertEquals("Customer should still have only one application", 1, customerWithApprovedApp.getApplications().size());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Test Case 4: Missing document
        // Setup: No prior applications for "AutoFuture"
        
        boolean result = bank.createIPOApplication(eligibleCustomer, "AutoFuture", 25, 1000.0, null);
        
        assertFalse("Application should be rejected when document is null", result);
        assertEquals("Customer should have no applications", 0, eligibleCustomer.getApplications().size());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Test Case 5: Zero-share application
        // Setup: No existing applications for "NanoChip"
        
        boolean result = bank.createIPOApplication(eligibleCustomer, "NanoChip", 0, 0.0, "N");
        
        assertFalse("Application should be rejected when number of shares is zero", result);
        assertEquals("Customer should have no applications", 0, eligibleCustomer.getApplications().size());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Test Case 6: Negative share count
        // Setup: No prior applications for "CloudServ"
        
        boolean result = bank.createIPOApplication(eligibleCustomer, "CloudServ", -5, -200.0, "C");
        
        assertFalse("Application should be rejected when number of shares is negative", result);
        assertEquals("Customer should have no applications", 0, eligibleCustomer.getApplications().size());
    }
}