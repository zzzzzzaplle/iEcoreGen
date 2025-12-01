import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private Customer customer;
    private Company company;
    private Document document;
    
    @Before
    public void setUp() {
        // Common setup for all test cases
        customer = new Customer();
        company = new Company();
        document = new Document();
    }
    
    @Test
    public void testCase1_standardEligibleSubmission() {
        // Setup customer data
        customer.setName("John");
        customer.setSurname("Smith");
        customer.setEmail("john.smith@example.com");
        customer.setTelephone("555-1234");
        customer.setCanApplyForIPO(true);
        
        // Setup company data
        company.setName("TechCorp");
        company.setEmail("techcorp@gmail.com");
        
        // Test application creation
        boolean result = customer.createApplication(company, 100, 5000.0, document);
        
        // Verify successful application creation
        assertTrue("Application should be created successfully for eligible customer", result);
        assertEquals("Customer should have 1 application", 1, customer.getApplications().size());
        assertEquals("Failed attempts should be reset to 0", 0, customer.getFailedAttempts());
        assertTrue("Customer should remain eligible", customer.isEligibleForIPO());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Setup customer data
        customer.setName("Alice");
        customer.setSurname("Johnson");
        customer.setEmail("alice.j@example.com");
        customer.setTelephone("555-5678");
        customer.setCanApplyForIPO(false); // Customer not eligible
        
        // Setup company data
        company.setName("BioMed");
        company.setEmail("biomed@gmail.com");
        
        // Test application creation
        boolean result = customer.createApplication(company, 50, 2500.0, document);
        
        // Verify application creation fails due to ineligibility
        assertFalse("Application should not be created for ineligible customer", result);
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
        assertEquals("Failed attempts should be 1", 1, customer.getFailedAttempts());
        assertFalse("Customer should remain ineligible", customer.isEligibleForIPO());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Setup customer data
        customer.setName("Robert");
        customer.setSurname("Chen");
        customer.setEmail("r.chen@example.com");
        customer.setTelephone("555-9012");
        customer.setCanApplyForIPO(true);
        
        // Setup company data
        company.setName("GreenEnergy");
        company.setEmail("greenenergy@gmail.com");
        
        // Create first application and approve it
        boolean firstResult = customer.createApplication(company, 10, 300.0, document);
        assertTrue("First application should be created successfully", firstResult);
        
        // Approve the first application
        Application firstApp = customer.getApplications().get(0);
        firstApp.setStatus(ApplicationStatus.APPROVAL);
        
        // Try to create duplicate application for same company
        boolean secondResult = customer.createApplication(company, 10, 300.0, document);
        
        // Verify duplicate application creation fails
        assertFalse("Duplicate application should not be created", secondResult);
        assertEquals("Customer should have only 1 application", 1, customer.getApplications().size());
        assertEquals("Failed attempts should be 1", 1, customer.getFailedAttempts());
        assertTrue("Customer should remain eligible", customer.isEligibleForIPO());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Setup customer data
        customer.setName("Emma");
        customer.setSurname("Davis");
        customer.setEmail("emma.d@example.com");
        customer.setTelephone("555-3456");
        customer.setCanApplyForIPO(true);
        
        // Setup company data
        company.setName("AutoFuture");
        company.setEmail("autofuture@gmail.com");
        
        // Test application creation with null document
        boolean result = customer.createApplication(company, 25, 1000.0, null);
        
        // Verify application creation fails due to missing document
        assertFalse("Application should not be created without document", result);
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
        assertEquals("Failed attempts should be 1", 1, customer.getFailedAttempts());
        assertTrue("Customer should remain eligible", customer.isEligibleForIPO());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Setup customer data
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@example.com");
        customer.setTelephone("555-7890");
        customer.setCanApplyForIPO(true);
        
        // Setup company data
        company.setName("NanoChip");
        company.setEmail("nanotech@gmail.com");
        
        // Test application creation with 0 shares
        boolean result = customer.createApplication(company, 0, 0.0, document);
        
        // Verify application creation fails due to zero shares
        assertFalse("Application should not be created with 0 shares", result);
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
        assertEquals("Failed attempts should be 1", 1, customer.getFailedAttempts());
        assertTrue("Customer should remain eligible", customer.isEligibleForIPO());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Setup customer data
        customer.setName("Sophia");
        customer.setSurname("Martinez");
        customer.setEmail("s.m@example.com");
        customer.setTelephone("555-2345");
        customer.setCanApplyForIPO(true);
        
        // Setup company data
        company.setName("CloudServ");
        company.setEmail("cloudserv@gmail.com");
        
        // Test application creation with negative shares
        boolean result = customer.createApplication(company, -5, -200.0, document);
        
        // Verify application creation fails due to negative shares
        assertFalse("Application should not be created with negative shares", result);
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
        assertEquals("Failed attempts should be 1", 1, customer.getFailedAttempts());
        assertTrue("Customer should remain eligible", customer.isEligibleForIPO());
    }
}