import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private SoftwareSystem system;
    
    @Before
    public void setUp() {
        // Initialize the software system before each test
        system = new SoftwareSystem();
    }
    
    @Test
    public void testCase1_standardEligibleSubmission() {
        // Setup: Create an eligible customer with no prior applications
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Smith");
        customer.setEmail("john.smith@example.com");
        customer.setTelephone("555-1234");
        customer.setEligibleForIPO(true);
        customer.setFailedAttempts(0);
        
        Document document = new Document();
        document.setContent("Document A");
        
        // Execute: Create IPO application
        boolean result = system.createIPOApplication(customer, "TechCorp", 100, 5000.0, document);
        
        // Verify: Application should be created successfully
        assertTrue("Application should be created successfully for eligible customer", result);
        assertEquals("Customer should have 1 application", 1, customer.getApplications().size());
        assertEquals("Application company should match", "TechCorp", customer.getApplications().get(0).getCompany());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Setup: Create an ineligible customer
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        customer.setEmail("alice.j@example.com");
        customer.setTelephone("555-5678");
        customer.setEligibleForIPO(false); // Customer is not eligible
        customer.setFailedAttempts(0);
        
        Document document = new Document();
        document.setContent("Document B");
        
        // Execute: Create IPO application
        boolean result = system.createIPOApplication(customer, "BioMed", 50, 2500.0, document);
        
        // Verify: Application should be rejected due to ineligible customer
        assertFalse("Application should be rejected for ineligible customer", result);
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Setup: Create eligible customer with an approved application
        Customer customer = new Customer();
        customer.setName("Robert");
        customer.setSurname("Chen");
        customer.setEmail("r.chen@example.com");
        customer.setTelephone("555-9012");
        customer.setEligibleForIPO(true);
        customer.setFailedAttempts(0);
        
        // Create and add an approved application for GreenEnergy
        IPOApplication existingApp = new IPOApplication();
        existingApp.setCompany("GreenEnergy");
        existingApp.setNumberOfShares(10);
        existingApp.setAmount(300.0);
        existingApp.setApproved(true);
        existingApp.setCustomer(customer);
        customer.getApplications().add(existingApp);
        
        Document document = new Document();
        document.setContent("Document G");
        
        // Execute: Try to create another application for the same company
        boolean result = system.createIPOApplication(customer, "GreenEnergy", 10, 300.0, document);
        
        // Verify: Application should be rejected due to duplicate approved application
        assertFalse("Application should be rejected due to existing approved application", result);
        assertEquals("Customer should still have only 1 application", 1, customer.getApplications().size());
        assertEquals("Failed attempts should be incremented", 1, customer.getFailedAttempts());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Setup: Create eligible customer with no prior applications
        Customer customer = new Customer();
        customer.setName("Emma");
        customer.setSurname("Davis");
        customer.setEmail("emma.d@example.com");
        customer.setTelephone("555-3456");
        customer.setEligibleForIPO(true);
        customer.setFailedAttempts(0);
        
        // Execute: Create IPO application with null document
        boolean result = system.createIPOApplication(customer, "AutoFuture", 25, 1000.0, null);
        
        // Verify: Application should be rejected due to missing document
        assertFalse("Application should be rejected due to missing document", result);
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
        assertEquals("Failed attempts should be incremented", 1, customer.getFailedAttempts());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Setup: Create eligible customer with no prior applications
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@example.com");
        customer.setTelephone("555-7890");
        customer.setEligibleForIPO(true);
        customer.setFailedAttempts(0);
        
        Document document = new Document();
        document.setContent("Document N");
        
        // Execute: Create IPO application with 0 shares
        boolean result = system.createIPOApplication(customer, "NanoChip", 0, 0.0, document);
        
        // Verify: Application should be rejected due to zero shares
        assertFalse("Application should be rejected due to zero shares", result);
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
        assertEquals("Failed attempts should be incremented", 1, customer.getFailedAttempts());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Setup: Create eligible customer with no prior applications
        Customer customer = new Customer();
        customer.setName("Sophia");
        customer.setSurname("Martinez");
        customer.setEmail("s.m@example.com");
        customer.setTelephone("555-2345");
        customer.setEligibleForIPO(true);
        customer.setFailedAttempts(0);
        
        Document document = new Document();
        document.setContent("Document C");
        
        // Execute: Create IPO application with negative shares and amount
        boolean result = system.createIPOApplication(customer, "CloudServ", -5, -200.0, document);
        
        // Verify: Application should be rejected due to negative values
        assertFalse("Application should be rejected due to negative shares/amount", result);
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
        assertEquals("Failed attempts should be incremented", 1, customer.getFailedAttempts());
    }
}