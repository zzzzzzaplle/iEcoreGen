import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private Customer customer;
    
    @Before
    public void setUp() {
        customer = new Customer();
    }
    
    @Test
    public void testCase1_standardEligibleSubmission() {
        // Setup customer C001
        customer.setName("John");
        customer.setSurname("Smith");
        customer.setEmail("john.smith@example.com");
        customer.setTelephone("555-1234");
        customer.setEligible(true);
        
        // Create IPO application for TechCorp
        boolean result = customer.createIPOApplication("TechCorp", 100, 5000.0, "A");
        
        // Verify application was created successfully
        assertTrue("Application should be created successfully", result);
        assertEquals("Failed attempts should be reset to 0", 0, customer.getFailedAttempts());
        assertEquals("Should have 1 application", 1, customer.getApplications().size());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Setup customer C002 as ineligible
        customer.setName("Alice");
        customer.setSurname("Johnson");
        customer.setEmail("alice.j@example.com");
        customer.setTelephone("555-5678");
        customer.setEligible(false);
        
        // Attempt to create IPO application for BioMed
        boolean result = customer.createIPOApplication("BioMed", 50, 2500.0, "B");
        
        // Verify application creation failed
        assertFalse("Application should fail for ineligible customer", result);
        assertEquals("Failed attempts should be incremented", 1, customer.getFailedAttempts());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Setup customer C003
        customer.setName("Robert");
        customer.setSurname("Chen");
        customer.setEmail("r.chen@example.com");
        customer.setTelephone("555-9012");
        customer.setEligible(true);
        
        // Create first application for GreenEnergy
        boolean firstResult = customer.createIPOApplication("GreenEnergy", 10, 300.0, "G");
        assertTrue("First application should succeed", firstResult);
        
        // Approve the first application
        IPOApplication firstApp = customer.getApplications().get(0);
        firstApp.setStatus(ApplicationStatus.APPROVED);
        
        // Attempt to create second application for same company
        boolean secondResult = customer.createIPOApplication("GreenEnergy", 10, 300.0, "G");
        
        // Verify second application fails due to duplicate approved application
        assertFalse("Second application should fail due to duplicate", secondResult);
        assertEquals("Failed attempts should be incremented", 1, customer.getFailedAttempts());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Setup customer C004
        customer.setName("Emma");
        customer.setSurname("Davis");
        customer.setEmail("emma.d@example.com");
        customer.setTelephone("555-3456");
        customer.setEligible(true);
        
        // Attempt to create IPO application with null document
        boolean result = customer.createIPOApplication("AutoFuture", 25, 1000.0, null);
        
        // Verify application creation fails due to missing document
        assertFalse("Application should fail with null document", result);
        assertEquals("Failed attempts should be incremented", 1, customer.getFailedAttempts());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Setup customer C005
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@example.com");
        customer.setTelephone("555-7890");
        customer.setEligible(true);
        
        // Attempt to create IPO application with 0 shares
        boolean result = customer.createIPOApplication("NanoChip", 0, 0.0, "N");
        
        // Verify application creation fails due to zero shares
        assertFalse("Application should fail with 0 shares", result);
        assertEquals("Failed attempts should be incremented", 1, customer.getFailedAttempts());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Setup customer C006
        customer.setName("Sophia");
        customer.setSurname("Martinez");
        customer.setEmail("s.m@example.com");
        customer.setTelephone("555-2345");
        customer.setEligible(true);
        
        // Attempt to create IPO application with negative shares
        boolean result = customer.createIPOApplication("CloudServ", -5, -200.0, "C");
        
        // Verify application creation fails due to negative shares
        assertFalse("Application should fail with negative shares", result);
        assertEquals("Failed attempts should be incremented", 1, customer.getFailedAttempts());
    }
}