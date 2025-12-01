import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private Customer customer;
    private Object documentA;
    private Object documentB;
    private Object documentG;
    private Object documentN;
    private Object documentC;
    
    @Before
    public void setUp() {
        customer = new Customer();
        documentA = new Object();
        documentB = new Object();
        documentG = new Object();
        documentN = new Object();
        documentC = new Object();
    }
    
    @Test
    public void testCase1_standardEligibleSubmission() {
        // Setup: Customer "C001" allows IPO applications and has no previously approved applications for "TechCorp"
        customer.setName("John");
        customer.setSurname("Smith");
        customer.setEmail("john.smith@example.com");
        customer.setTelephone("555-1234");
        customer.setCanApplyForIPO(true);
        
        // Input: Apply for IPO with company "TechCorp", 100 shares, $5,000, document 'A'
        boolean result = customer.createIPOApplication("TechCorp", 100, 5000.0, documentA);
        
        // Expected Output: True (application is successfully created)
        assertTrue("Application should be created successfully for eligible customer", result);
        assertEquals("Application list should contain 1 application", 1, customer.getApplications().size());
        
        Application createdApp = customer.getApplications().get(0);
        assertEquals("Company should be TechCorp", "TechCorp", createdApp.getCompany());
        assertEquals("Shares should be 100", 100, createdApp.getShares());
        assertEquals("Amount should be 5000.0", 5000.0, createdApp.getAmount(), 0.001);
        assertEquals("Document should match", documentA, createdApp.getDocument());
        assertEquals("Status should be PENDING", ApplicationStatus.PENDING, createdApp.getStatus());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Setup: Customer "C002" has lost IPO eligibility after repeated failed attempts
        customer.setName("Alice");
        customer.setSurname("Johnson");
        customer.setEmail("alice.j@example.com");
        customer.setTelephone("555-5678");
        customer.setCanApplyForIPO(false); // Customer not eligible
        customer.setFailedAttempts(3); // Exceeded maximum failed attempts
        
        // Input: Attempt to apply for IPO with company "BioMed", 50 shares, $2,500, document 'B'
        boolean result = customer.createIPOApplication("BioMed", 50, 2500.0, documentB);
        
        // Expected Output: False
        assertFalse("Application should fail for ineligible customer", result);
        assertTrue("Application list should be empty", customer.getApplications().isEmpty());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Setup: Customer "C003" remains eligible and has an approved application for "GreenEnergy"
        customer.setName("Robert");
        customer.setSurname("Chen");
        customer.setEmail("r.chen@example.com");
        customer.setTelephone("555-9012");
        customer.setCanApplyForIPO(true);
        
        // Create and approve first application for GreenEnergy
        customer.createIPOApplication("GreenEnergy", 10, 300.0, documentG);
        customer.approveOrRejectApplication("GreenEnergy", true);
        
        // Input: Submit another application to "GreenEnergy" for 10 shares ($300) with document 'G'
        boolean result = customer.createIPOApplication("GreenEnergy", 10, 300.0, documentG);
        
        // Expected Output: False (only one approved application per company allowed)
        assertFalse("Should not allow duplicate approved application for same company", result);
        assertEquals("Should still have only 1 application", 1, customer.getApplications().size());
        
        Application existingApp = customer.getApplications().get(0);
        assertEquals("Existing application should remain approved", ApplicationStatus.APPROVED, existingApp.getStatus());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Setup: Eligible customer "C004" with no prior applications for "AutoFuture"
        customer.setName("Emma");
        customer.setSurname("Davis");
        customer.setEmail("emma.d@example.com");
        customer.setTelephone("555-3456");
        customer.setCanApplyForIPO(true);
        
        // Input: Apply to "AutoFuture" for 25 shares ($1,000) with null document
        boolean result = customer.createIPOApplication("AutoFuture", 25, 1000.0, null);
        
        // Expected Output: False (document upload is mandatory)
        assertFalse("Application should fail when document is null", result);
        assertTrue("Application list should be empty", customer.getApplications().isEmpty());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Setup: Eligible customer "C005" with no existing applications for "NanoChip"
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@example.com");
        customer.setTelephone("555-7890");
        customer.setCanApplyForIPO(true);
        
        // Input: Apply to "NanoChip" for 0 shares ($0) with document 'N'
        boolean result = customer.createIPOApplication("NanoChip", 0, 0.0, documentN);
        
        // Expected Output: False
        assertFalse("Application should fail with zero shares", result);
        assertTrue("Application list should be empty", customer.getApplications().isEmpty());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Setup: Eligible customer "C006" with no prior applications for "CloudServ"
        customer.setName("Sophia");
        customer.setSurname("Martinez");
        customer.setEmail("s.m@example.com");
        customer.setTelephone("555-2345");
        customer.setCanApplyForIPO(true);
        
        // Input: Attempt to apply to "CloudServ" for -5 shares (-$200) with document 'C'
        boolean result = customer.createIPOApplication("CloudServ", -5, -200.0, documentC);
        
        // Expected Output: False (negative shares/amount are invalid)
        assertFalse("Application should fail with negative shares", result);
        assertTrue("Application list should be empty", customer.getApplications().isEmpty());
    }
}