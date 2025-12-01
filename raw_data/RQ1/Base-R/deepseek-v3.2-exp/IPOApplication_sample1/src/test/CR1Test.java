import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private Customer customer;
    private Document documentA;
    private Document documentB;
    private Document documentG;
    private Document documentN;
    private Document documentC;
    
    @Before
    public void setUp() {
        // Setup common test objects
        documentA = new Document();
        documentA.setDocumentId("DOC_A");
        documentA.setDocumentType("Allowance");
        documentA.setContent("Legal allowance document A");
        
        documentB = new Document();
        documentB.setDocumentId("DOC_B");
        documentB.setDocumentType("Allowance");
        documentB.setContent("Legal allowance document B");
        
        documentG = new Document();
        documentG.setDocumentId("DOC_G");
        documentG.setDocumentType("Allowance");
        documentG.setContent("Legal allowance document G");
        
        documentN = new Document();
        documentN.setDocumentId("DOC_N");
        documentN.setDocumentType("Allowance");
        documentN.setContent("Legal allowance document N");
        
        documentC = new Document();
        documentC.setDocumentId("DOC_C");
        documentC.setDocumentType("Allowance");
        documentC.setContent("Legal allowance document C");
    }
    
    @Test
    public void testCase1_standardEligibleSubmission() {
        // Setup: Create eligible customer with no prior applications for TechCorp
        customer = new Customer();
        customer.setName("John");
        customer.setSurname("Smith");
        customer.setEmail("john.smith@example.com");
        customer.setTelephone("555-1234");
        customer.setEligibleForIPO(true);
        customer.setRestricted(false);
        
        // Execute: Create IPO application for TechCorp
        boolean result = customer.createIPOApplication("TechCorp", 100, 5000.0, documentA);
        
        // Verify: Application should be successfully created
        assertTrue("Application should be created successfully for eligible customer", result);
        assertEquals("Customer should have 1 application", 1, customer.getApplications().size());
        assertEquals("Application company name should be TechCorp", "TechCorp", customer.getApplications().get(0).getCompanyName());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Setup: Create ineligible customer (lost IPO eligibility)
        customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        customer.setEmail("alice.j@example.com");
        customer.setTelephone("555-5678");
        customer.setEligibleForIPO(false); // Customer is not eligible
        customer.setRestricted(false);
        
        // Execute: Attempt to create IPO application for BioMed
        boolean result = customer.createIPOApplication("BioMed", 50, 2500.0, documentB);
        
        // Verify: Application should fail due to ineligibility
        assertFalse("Application should fail for ineligible customer", result);
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Setup: Create eligible customer and approve first application
        customer = new Customer();
        customer.setName("Robert");
        customer.setSurname("Chen");
        customer.setEmail("r.chen@example.com");
        customer.setTelephone("555-9012");
        customer.setEligibleForIPO(true);
        customer.setRestricted(false);
        
        // Create and approve first application for GreenEnergy
        boolean firstResult = customer.createIPOApplication("GreenEnergy", 10, 300.0, documentG);
        assertTrue("First application should be created successfully", firstResult);
        
        // Approve the application
        IPOApplication firstApp = customer.getApplications().get(0);
        firstApp.setStatus(ApplicationStatus.APPROVED);
        
        // Execute: Attempt to create second application for same company
        boolean secondResult = customer.createIPOApplication("GreenEnergy", 10, 300.0, documentG);
        
        // Verify: Second application should fail due to duplicate approved application
        assertFalse("Second application should fail due to duplicate approved application", secondResult);
        assertEquals("Customer should still have only 1 application", 1, customer.getApplications().size());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Setup: Create eligible customer with no prior applications
        customer = new Customer();
        customer.setName("Emma");
        customer.setSurname("Davis");
        customer.setEmail("emma.d@example.com");
        customer.setTelephone("555-3456");
        customer.setEligibleForIPO(true);
        customer.setRestricted(false);
        
        // Execute: Attempt to create application without document (null document)
        boolean result = customer.createIPOApplication("AutoFuture", 25, 1000.0, null);
        
        // Verify: Application should fail due to missing document
        assertFalse("Application should fail when document is null", result);
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Setup: Create eligible customer with no prior applications
        customer = new Customer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@example.com");
        customer.setTelephone("555-7890");
        customer.setEligibleForIPO(true);
        customer.setRestricted(false);
        
        // Execute: Attempt to create application with 0 shares and $0 amount
        boolean result = customer.createIPOApplication("NanoChip", 0, 0.0, documentN);
        
        // Verify: Application should fail due to invalid share count and amount
        assertFalse("Application should fail with 0 shares and 0 amount", result);
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Setup: Create eligible customer with no prior applications
        customer = new Customer();
        customer.setName("Sophia");
        customer.setSurname("Martinez");
        customer.setEmail("s.m@example.com");
        customer.setTelephone("555-2345");
        customer.setEligibleForIPO(true);
        customer.setRestricted(false);
        
        // Execute: Attempt to create application with negative shares and negative amount
        boolean result = customer.createIPOApplication("CloudServ", -5, -200.0, documentC);
        
        // Verify: Application should fail due to negative share count and amount
        assertFalse("Application should fail with negative shares and negative amount", result);
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
    }
}