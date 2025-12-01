import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private Document validDocument;
    private Document nullDocument;
    
    @Before
    public void setUp() {
        // Create a valid document for testing
        validDocument = new Document();
        nullDocument = null;
    }
    
    @Test
    public void testCase1_standardEligibleSubmission() {
        // Setup: Create eligible customer with no prior applications for TechCorp
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Smith");
        customer.setEmail("john.smith@example.com");
        customer.setTelephone("555-1234");
        customer.setCanApplyForIPO(true);
        
        Company company = new Company("TechCorp", "techcorp@gmail.com");
        
        // Test: Customer applies for IPO with valid parameters
        boolean result = customer.createApplication(company, 100, 5000.0, validDocument);
        
        // Verify application was created successfully
        assertTrue("Application should be created successfully for eligible customer", result);
        assertEquals("Customer should have one application", 1, customer.getApplications().size());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Setup: Create ineligible customer with no prior applications for BioMed
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        customer.setEmail("alice.j@example.com");
        customer.setTelephone("555-5678");
        customer.setCanApplyForIPO(false); // Customer is not eligible
        
        Company company = new Company("BioMed", "biomed@gmail.com");
        
        // Test: Ineligible customer attempts to apply for IPO
        boolean result = customer.createApplication(company, 50, 2500.0, validDocument);
        
        // Verify application was not created
        assertFalse("Application should not be created for ineligible customer", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Setup: Create eligible customer
        Customer customer = new Customer();
        customer.setName("Robert");
        customer.setSurname("Chen");
        customer.setEmail("r.chen@example.com");
        customer.setTelephone("555-9012");
        customer.setCanApplyForIPO(true);
        
        Company company = new Company("GreenEnergy", "greenenergy@gmail.com");
        
        // First application creation and approval
        boolean firstResult = customer.createApplication(company, 10, 300.0, validDocument);
        assertTrue("First application should be created successfully", firstResult);
        
        // Approve the first application
        Application firstApp = customer.getApplications().get(0);
        firstApp.approve();
        
        // Test: Attempt to create second application for same company
        boolean secondResult = customer.createApplication(company, 10, 300.0, validDocument);
        
        // Verify second application was not created due to existing approved application
        assertFalse("Second application should not be created for same company", secondResult);
        assertEquals("Customer should have only one application", 1, customer.getApplications().size());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Setup: Create eligible customer with no prior applications for AutoFuture
        Customer customer = new Customer();
        customer.setName("Emma");
        customer.setSurname("Davis");
        customer.setEmail("emma.d@example.com");
        customer.setTelephone("555-3456");
        customer.setCanApplyForIPO(true);
        
        Company company = new Company("AutoFuture", "autofuture@gmail.com");
        
        // Test: Customer applies without uploading document
        boolean result = customer.createApplication(company, 25, 1000.0, nullDocument);
        
        // Verify application was not created due to missing document
        assertFalse("Application should not be created without document", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Setup: Create eligible customer with no prior applications for NanoChip
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@example.com");
        customer.setTelephone("555-7890");
        customer.setCanApplyForIPO(true);
        
        Company company = new Company("NanoChip", "nanotech@gmail.com");
        
        // Test: Customer applies with zero shares
        boolean result = customer.createApplication(company, 0, 0.0, validDocument);
        
        // Verify application was not created due to zero shares
        assertFalse("Application should not be created with zero shares", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Setup: Create eligible customer with no prior applications for CloudServ
        Customer customer = new Customer();
        customer.setName("Sophia");
        customer.setSurname("Martinez");
        customer.setEmail("s.m@example.com");
        customer.setTelephone("555-2345");
        customer.setCanApplyForIPO(true);
        
        Company company = new Company("CloudServ", "cloudserv@gmail.com");
        
        // Test: Customer applies with negative shares
        boolean result = customer.createApplication(company, -5, -200.0, validDocument);
        
        // Verify application was not created due to negative shares
        assertFalse("Application should not be created with negative shares", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
}