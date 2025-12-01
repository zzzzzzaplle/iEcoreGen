import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private Customer customer;
    private Company company;
    private Document document;
    
    @Before
    public void setUp() {
        // Common setup for test cases
        document = new Document();
    }
    
    @Test
    public void testCase1_standardEligibleSubmission() {
        // Setup
        customer = new Customer();
        customer.setName("John");
        customer.setSurname("Smith");
        customer.setEmail("john.smith@example.com");
        customer.setTelephone("555-1234");
        customer.setCanApplyForIPO(true);
        
        company = new Company();
        company.setName("TechCorp");
        company.setEmail("techcorp@gmail.com");
        
        // Execute test
        boolean result = customer.createApplication(company, 100, 5000.0, document);
        
        // Verify results
        assertTrue("Application should be successfully created", result);
        assertEquals("Customer should have one application", 1, customer.getApplications().size());
        assertEquals("Application status should be PENDING", ApplicationStatus.PENDING, customer.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Setup
        customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        customer.setEmail("alice.j@example.com");
        customer.setTelephone("555-5678");
        customer.setCanApplyForIPO(false); // Customer not eligible
        
        company = new Company();
        company.setName("BioMed");
        company.setEmail("biomed@gmail.com");
        
        // Execute test
        boolean result = customer.createApplication(company, 50, 2500.0, document);
        
        // Verify results
        assertFalse("Application should fail due to customer ineligibility", result);
        assertEquals("No applications should be created", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Setup
        customer = new Customer();
        customer.setName("Robert");
        customer.setSurname("Chen");
        customer.setEmail("r.chen@example.com");
        customer.setTelephone("555-9012");
        customer.setCanApplyForIPO(true);
        
        company = new Company();
        company.setName("GreenEnergy");
        company.setEmail("greenenergy@gmail.com");
        
        // Create and approve first application
        boolean firstResult = customer.createApplication(company, 10, 300.0, document);
        assertTrue("First application should be created successfully", firstResult);
        
        Application firstApp = customer.getApplications().get(0);
        firstApp.setStatus(ApplicationStatus.APPROVAL); // Simulate approval
        
        // Execute test - attempt to create duplicate application
        boolean secondResult = customer.createApplication(company, 10, 300.0, document);
        
        // Verify results
        assertFalse("Second application should fail due to duplicate approved application", secondResult);
        assertEquals("Only one application should exist", 1, customer.getApplications().size());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Setup
        customer = new Customer();
        customer.setName("Emma");
        customer.setSurname("Davis");
        customer.setEmail("emma.d@example.com");
        customer.setTelephone("555-3456");
        customer.setCanApplyForIPO(true);
        
        company = new Company();
        company.setName("AutoFuture");
        company.setEmail("autofuture@gmail.com");
        
        // Execute test with null document
        try {
            customer.createApplication(company, 25, 1000.0, null);
            fail("Should have thrown IllegalArgumentException for null document");
        } catch (IllegalArgumentException e) {
            // Expected exception
            assertEquals("Document cannot be null", e.getMessage());
        }
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Setup
        customer = new Customer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@example.com");
        customer.setTelephone("555-7890");
        customer.setCanApplyForIPO(true);
        
        company = new Company();
        company.setName("NanoChip");
        company.setEmail("nanotech@gmail.com");
        
        // Execute test with zero shares
        try {
            customer.createApplication(company, 0, 0.0, document);
            fail("Should have thrown IllegalArgumentException for zero shares");
        } catch (IllegalArgumentException e) {
            // Expected exception
            assertEquals("Number of shares must be greater than 0", e.getMessage());
        }
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Setup
        customer = new Customer();
        customer.setName("Sophia");
        customer.setSurname("Martinez");
        customer.setEmail("s.m@example.com");
        customer.setTelephone("555-2345");
        customer.setCanApplyForIPO(true);
        
        company = new Company();
        company.setName("CloudServ");
        company.setEmail("cloudserv@gmail.com");
        
        // Execute test with negative shares
        try {
            customer.createApplication(company, -5, -200.0, document);
            fail("Should have thrown IllegalArgumentException for negative shares");
        } catch (IllegalArgumentException e) {
            // Expected exception
            assertEquals("Number of shares must be greater than 0", e.getMessage());
        }
    }
}