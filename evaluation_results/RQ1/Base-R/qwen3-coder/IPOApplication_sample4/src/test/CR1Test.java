import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private BankSystem bankSystem;
    private Customer customerC001;
    private Customer customerC002;
    private Customer customerC003;
    private Customer customerC004;
    private Customer customerC005;
    private Customer customerC006;
    
    @Before
    public void setUp() {
        bankSystem = new BankSystem();
        
        // Initialize customer C001 - eligible customer for standard submission
        customerC001 = new Customer();
        customerC001.setName("John");
        customerC001.setSurname("Smith");
        customerC001.setEmail("john.smith@example.com");
        customerC001.setTelephone("555-1234");
        customerC001.setEligibleForIPO(true);
        
        // Initialize customer C002 - not eligible customer
        customerC002 = new Customer();
        customerC002.setName("Alice");
        customerC002.setSurname("Johnson");
        customerC002.setEmail("alice.j@example.com");
        customerC002.setTelephone("555-5678");
        customerC002.setEligibleForIPO(false); // Lost IPO eligibility
        
        // Initialize customer C003 - customer with duplicate approved application
        customerC003 = new Customer();
        customerC003.setName("Robert");
        customerC003.setSurname("Chen");
        customerC003.setEmail("r.chen@example.com");
        customerC003.setTelephone("555-9012");
        customerC003.setEligibleForIPO(true);
        
        // Initialize customer C004 - eligible customer for missing document test
        customerC004 = new Customer();
        customerC004.setName("Emma");
        customerC004.setSurname("Davis");
        customerC004.setEmail("emma.d@example.com");
        customerC004.setTelephone("555-3456");
        customerC004.setEligibleForIPO(true);
        
        // Initialize customer C005 - eligible customer for zero-share test
        customerC005 = new Customer();
        customerC005.setName("James");
        customerC005.setSurname("Wilson");
        customerC005.setEmail("j.wilson@example.com");
        customerC005.setTelephone("555-7890");
        customerC005.setEligibleForIPO(true);
        
        // Initialize customer C006 - eligible customer for negative share test
        customerC006 = new Customer();
        customerC006.setName("Sophia");
        customerC006.setSurname("Martinez");
        customerC006.setEmail("s.m@example.com");
        customerC006.setTelephone("555-2345");
        customerC006.setEligibleForIPO(true);
    }
    
    @Test
    public void testCase1_standardEligibleSubmission() {
        // Test Case 1: Standard eligible submission
        // Setup: Customer "C001" allows IPO applications and has no previously approved applications for "TechCorp"
        
        boolean result = bankSystem.createIPOApplication(customerC001, "TechCorp", 100, 5000.0, "A");
        
        // Verify application was successfully created
        assertTrue("Application should be successfully created for eligible customer", result);
        assertEquals("Customer should have 1 application", 1, customerC001.getApplications().size());
        assertEquals("Failed attempts should be reset to 0", 0, customerC001.getFailedAttempts());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Test Case 2: Customer not eligible
        // Setup: Customer "C002" has lost IPO eligibility after repeated failed attempts
        
        boolean result = bankSystem.createIPOApplication(customerC002, "BioMed", 50, 2500.0, "B");
        
        // Verify application creation fails due to ineligibility
        assertFalse("Application should fail for ineligible customer", result);
        assertEquals("Customer should have 0 applications", 0, customerC002.getApplications().size());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Test Case 3: Duplicate approved application
        // Setup: Customer "C003" remains eligible but has approved application for same company
        
        // First, create and approve an application for GreenEnergy
        boolean firstApplication = bankSystem.createIPOApplication(customerC003, "GreenEnergy", 10, 300.0, "G");
        assertTrue("First application should be created successfully", firstApplication);
        
        // Approve the first application
        IPOApplication firstApp = customerC003.getApplications().get(0);
        boolean approvalResult = bankSystem.processApplication(firstApp, true);
        assertTrue("First application should be approved successfully", approvalResult);
        
        // Now attempt to create another application for the same company
        boolean secondApplication = bankSystem.createIPOApplication(customerC003, "GreenEnergy", 10, 300.0, "G");
        
        // Verify second application creation fails due to duplicate approved application
        assertFalse("Second application should fail due to duplicate approved application", secondApplication);
        assertEquals("Customer should have only 1 application", 1, customerC003.getApplications().size());
        assertEquals("Failed attempts should be 1 after duplicate attempt", 1, customerC003.getFailedAttempts());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Test Case 4: Missing document
        // Setup: No prior applications for "AutoFuture"
        
        boolean result = bankSystem.createIPOApplication(customerC004, "AutoFuture", 25, 1000.0, null);
        
        // Verify application creation fails due to missing document
        assertFalse("Application should fail when document is null", result);
        assertEquals("Customer should have 0 applications", 0, customerC004.getApplications().size());
        assertEquals("Failed attempts should be 1 after invalid submission", 1, customerC004.getFailedAttempts());
        
        // Test with empty document as well
        boolean resultEmptyDoc = bankSystem.createIPOApplication(customerC004, "AutoFuture", 25, 1000.0, "");
        assertFalse("Application should fail when document is empty", resultEmptyDoc);
        assertEquals("Failed attempts should be 2 after second invalid submission", 2, customerC004.getFailedAttempts());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Test Case 5: Zero-share application
        // Setup: No existing applications for "NanoChip"
        
        boolean result = bankSystem.createIPOApplication(customerC005, "NanoChip", 0, 0.0, "N");
        
        // Verify application creation fails due to zero shares
        assertFalse("Application should fail when number of shares is zero", result);
        assertEquals("Customer should have 0 applications", 0, customerC005.getApplications().size());
        assertEquals("Failed attempts should be 1 after invalid submission", 1, customerC005.getFailedAttempts());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Test Case 6: Negative share count
        // Setup: No prior applications for "CloudServ"
        
        boolean result = bankSystem.createIPOApplication(customerC006, "CloudServ", -5, -200.0, "C");
        
        // Verify application creation fails due to negative shares
        assertFalse("Application should fail when number of shares is negative", result);
        assertEquals("Customer should have 0 applications", 0, customerC006.getApplications().size());
        assertEquals("Failed attempts should be 1 after invalid submission", 1, customerC006.getFailedAttempts());
    }
}