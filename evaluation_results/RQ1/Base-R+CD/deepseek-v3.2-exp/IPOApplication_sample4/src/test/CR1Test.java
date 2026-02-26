import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private Customer customer;
    private Company company;
    private Document document;
    
    @Before
    public void setUp() {
        customer = new Customer();
        company = new Company();
        document = new Document();
    }
    
    @Test
    public void testCase1_standardEligibleSubmission() {
        // Setup customer
        customer.setName("John");
        customer.setSurname("Smith");
        customer.setEmail("john.smith@example.com");
        customer.setTelephone("555-1234");
        customer.setCanApplyForIPO(true);
        
        // Setup company
        company.setName("TechCorp");
        company.setEmail("techcorp@gmail.com");
        
        // Test application creation
        boolean result = customer.createApplication(company, 100, 5000.0, document);
        
        // Verify application was created successfully
        assertTrue("Application should be created successfully for eligible customer", result);
        assertEquals("Customer should have 1 application", 1, customer.getApplications().size());
        assertEquals("Failed attempts should be reset to 0", 0, customer.getFailedAttempts());
        assertTrue("Customer should remain eligible for IPO", customer.isEligibleForIPO());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Setup customer as not eligible
        customer.setName("Alice");
        customer.setSurname("Johnson");
        customer.setEmail("alice.j@example.com");
        customer.setTelephone("555-5678");
        customer.setCanApplyForIPO(false);
        
        // Setup company
        company.setName("BioMed");
        company.setEmail("biomed@gmail.com");
        
        // Test application creation
        boolean result = customer.createApplication(company, 50, 2500.0, document);
        
        // Verify application was not created
        assertFalse("Application should not be created for ineligible customer", result);
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Setup customer
        customer.setName("Robert");
        customer.setSurname("Chen");
        customer.setEmail("r.chen@example.com");
        customer.setTelephone("555-9012");
        customer.setCanApplyForIPO(true);
        
        // Setup company
        company.setName("GreenEnergy");
        company.setEmail("greenenergy@gmail.com");
        
        // Create first application
        boolean firstResult = customer.createApplication(company, 10, 300.0, document);
        assertTrue("First application should be created successfully", firstResult);
        
        // Approve the first application
        Application firstApp = customer.getApplications().get(0);
        firstApp.setStatus(ApplicationStatus.APPROVAL);
        
        // Try to create another application for the same company
        boolean secondResult = customer.createApplication(company, 10, 300.0, document);
        
        // Verify second application was not created
        assertFalse("Second application should not be created for same company with approved application", secondResult);
        assertEquals("Customer should still have only 1 application", 1, customer.getApplications().size());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Setup customer
        customer.setName("Emma");
        customer.setSurname("Davis");
        customer.setEmail("emma.d@example.com");
        customer.setTelephone("555-3456");
        customer.setCanApplyForIPO(true);
        
        // Setup company
        company.setName("AutoFuture");
        company.setEmail("autofuture@gmail.com");
        
        // Test application creation with null document
        try {
            customer.createApplication(company, 25, 1000.0, null);
            fail("Should have thrown IllegalArgumentException for null document");
        } catch (IllegalArgumentException e) {
            assertEquals("Exception message should match", "Document cannot be null", e.getMessage());
        }
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Setup customer
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@example.com");
        customer.setTelephone("555-7890");
        customer.setCanApplyForIPO(true);
        
        // Setup company
        company.setName("NanoChip");
        company.setEmail("nanotech@gmail.com");
        
        // Test application creation with zero shares
        try {
            customer.createApplication(company, 0, 0.0, document);
            fail("Should have thrown IllegalArgumentException for zero shares");
        } catch (IllegalArgumentException e) {
            assertEquals("Exception message should match", "Number of shares must be greater than 0", e.getMessage());
        }
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Setup customer
        customer.setName("Sophia");
        customer.setSurname("Martinez");
        customer.setEmail("s.m@example.com");
        customer.setTelephone("555-2345");
        customer.setCanApplyForIPO(true);
        
        // Setup company
        company.setName("CloudServ");
        company.setEmail("cloudserv@gmail.com");
        
        // Test application creation with negative shares
        try {
            customer.createApplication(company, -5, -200.0, document);
            fail("Should have thrown IllegalArgumentException for negative shares");
        } catch (IllegalArgumentException e) {
            assertEquals("Exception message should match", "Number of shares must be greater than 0", e.getMessage());
        }
    }
}