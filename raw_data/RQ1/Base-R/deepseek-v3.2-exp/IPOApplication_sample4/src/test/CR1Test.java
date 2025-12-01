import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private BankSystem bankSystem;
    private Customer eligibleCustomer;
    private Customer ineligibleCustomer;
    private Customer duplicateCustomer;
    private Customer missingDocCustomer;
    private Customer zeroShareCustomer;
    private Customer negativeShareCustomer;
    
    @Before
    public void setUp() {
        bankSystem = new BankSystem();
        
        // Setup eligible customer for Test Case 1
        eligibleCustomer = new Customer();
        eligibleCustomer.setName("John");
        eligibleCustomer.setSurname("Smith");
        eligibleCustomer.setEmail("john.smith@example.com");
        eligibleCustomer.setTelephone("555-1234");
        eligibleCustomer.setEligibleForIPO(true);
        
        // Setup ineligible customer for Test Case 2
        ineligibleCustomer = new Customer();
        ineligibleCustomer.setName("Alice");
        ineligibleCustomer.setSurname("Johnson");
        ineligibleCustomer.setEmail("alice.j@example.com");
        ineligibleCustomer.setTelephone("555-5678");
        ineligibleCustomer.setEligibleForIPO(false);
        
        // Setup duplicate customer for Test Case 3
        duplicateCustomer = new Customer();
        duplicateCustomer.setName("Robert");
        duplicateCustomer.setSurname("Chen");
        duplicateCustomer.setEmail("r.chen@example.com");
        duplicateCustomer.setTelephone("555-9012");
        duplicateCustomer.setEligibleForIPO(true);
        
        // Setup missing document customer for Test Case 4
        missingDocCustomer = new Customer();
        missingDocCustomer.setName("Emma");
        missingDocCustomer.setSurname("Davis");
        missingDocCustomer.setEmail("emma.d@example.com");
        missingDocCustomer.setTelephone("555-3456");
        missingDocCustomer.setEligibleForIPO(true);
        
        // Setup zero share customer for Test Case 5
        zeroShareCustomer = new Customer();
        zeroShareCustomer.setName("James");
        zeroShareCustomer.setSurname("Wilson");
        zeroShareCustomer.setEmail("j.wilson@example.com");
        zeroShareCustomer.setTelephone("555-7890");
        zeroShareCustomer.setEligibleForIPO(true);
        
        // Setup negative share customer for Test Case 6
        negativeShareCustomer = new Customer();
        negativeShareCustomer.setName("Sophia");
        negativeShareCustomer.setSurname("Martinez");
        negativeShareCustomer.setEmail("s.m@example.com");
        negativeShareCustomer.setTelephone("555-2345");
        negativeShareCustomer.setEligibleForIPO(true);
    }
    
    @Test
    public void testCase1_standardEligibleSubmission() {
        // Test Case 1: Standard eligible submission
        // Setup: Customer is eligible and has no prior approved applications for TechCorp
        boolean result = bankSystem.createIPOApplication(
            eligibleCustomer, "TechCorp", 100, 5000.0, "A"
        );
        
        assertTrue("Application should be created successfully for eligible customer", result);
        assertEquals("Customer should have 1 application", 1, eligibleCustomer.getApplications().size());
        assertEquals("Bank system should have 1 application", 1, bankSystem.getAllApplications().size());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Test Case 2: Customer not eligible
        // Setup: Customer has lost IPO eligibility
        boolean result = bankSystem.createIPOApplication(
            ineligibleCustomer, "BioMed", 50, 2500.0, "B"
        );
        
        assertFalse("Application should fail for ineligible customer", result);
        assertEquals("Customer should have 0 applications", 0, ineligibleCustomer.getApplications().size());
        assertEquals("Bank system should have 0 applications", 0, bankSystem.getAllApplications().size());
        assertEquals("Failed attempts should be incremented", 1, ineligibleCustomer.getFailedAttempts());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Test Case 3: Duplicate approved application
        // Setup: Create and approve first application
        boolean firstApplication = bankSystem.createIPOApplication(
            duplicateCustomer, "GreenEnergy", 10, 300.0, "G"
        );
        assertTrue("First application should be created successfully", firstApplication);
        
        // Approve the first application
        IPOApplication firstApp = duplicateCustomer.getApplications().get(0);
        boolean approvalResult = bankSystem.approveOrRejectApplication(firstApp, true);
        assertTrue("First application should be approved successfully", approvalResult);
        assertEquals("First application status should be APPROVED", ApplicationStatus.APPROVED, firstApp.getStatus());
        
        // Attempt to create second application for same company
        boolean secondApplication = bankSystem.createIPOApplication(
            duplicateCustomer, "GreenEnergy", 15, 450.0, "G"
        );
        
        assertFalse("Second application for same company should fail", secondApplication);
        assertEquals("Customer should still have only 1 application", 1, duplicateCustomer.getApplications().size());
        assertEquals("Failed attempts should be incremented", 1, duplicateCustomer.getFailedAttempts());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Test Case 4: Missing document
        // Setup: Customer forgets to upload document (null document)
        boolean result = bankSystem.createIPOApplication(
            missingDocCustomer, "AutoFuture", 25, 1000.0, null
        );
        
        assertFalse("Application should fail with null document", result);
        assertEquals("Customer should have 0 applications", 0, missingDocCustomer.getApplications().size());
        assertEquals("Bank system should have 0 applications", 0, bankSystem.getAllApplications().size());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Test Case 5: Zero-share application
        // Setup: Customer applies for 0 shares
        boolean result = bankSystem.createIPOApplication(
            zeroShareCustomer, "NanoChip", 0, 0.0, "N"
        );
        
        assertFalse("Application should fail with 0 shares", result);
        assertEquals("Customer should have 0 applications", 0, zeroShareCustomer.getApplications().size());
        assertEquals("Bank system should have 0 applications", 0, bankSystem.getAllApplications().size());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Test Case 6: Negative share count
        // Setup: Customer applies for negative shares
        boolean result = bankSystem.createIPOApplication(
            negativeShareCustomer, "CloudServ", -5, -200.0, "C"
        );
        
        assertFalse("Application should fail with negative shares", result);
        assertEquals("Customer should have 0 applications", 0, negativeShareCustomer.getApplications().size());
        assertEquals("Bank system should have 0 applications", 0, bankSystem.getAllApplications().size());
    }
}