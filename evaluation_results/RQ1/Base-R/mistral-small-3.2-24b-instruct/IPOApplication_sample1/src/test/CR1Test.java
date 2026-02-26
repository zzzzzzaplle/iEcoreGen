import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private Bank bank;
    
    @Before
    public void setUp() {
        bank = new Bank();
    }
    
    @Test
    public void testCase1_standardEligibleSubmission() {
        // Setup: Create eligible customer with no prior applications for TechCorp
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Smith");
        customer.setEmail("john.smith@example.com");
        customer.setTelephone("555-1234");
        
        // Execute: Create IPO application with valid parameters
        boolean result = bank.createIPOApplication(customer, "TechCorp", 100, 5000.0, "A");
        
        // Verify: Application should be successfully created
        assertTrue("Application should be created successfully for eligible customer", result);
        assertEquals("Customer should have one application", 1, customer.getApplications().size());
        assertEquals("Application company name should match", "TechCorp", customer.getApplications().get(0).getCompanyName());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Setup: Create customer and make them ineligible
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        customer.setEmail("alice.j@example.com");
        customer.setTelephone("555-5678");
        customer.setRestricted(true); // Make customer ineligible
        
        // Execute: Attempt to create IPO application
        boolean result = bank.createIPOApplication(customer, "BioMed", 50, 2500.0, "B");
        
        // Verify: Application should be rejected due to ineligible customer
        assertFalse("Application should fail for ineligible customer", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Setup: Create eligible customer with approved application for GreenEnergy
        Customer customer = new Customer();
        customer.setName("Robert");
        customer.setSurname("Chen");
        customer.setEmail("r.chen@example.com");
        customer.setTelephone("555-9012");
        
        // Create and approve first application for GreenEnergy
        IPOApplication firstApp = new IPOApplication();
        firstApp.setCompanyName("GreenEnergy");
        firstApp.setShares(10);
        firstApp.setAmount(300.0);
        firstApp.setDocument("G");
        firstApp.setCustomer(customer);
        firstApp.setStatus(IPOApplication.Status.APPROVED);
        customer.getApplications().add(firstApp);
        
        // Execute: Attempt to create another application for same company
        boolean result = bank.createIPOApplication(customer, "GreenEnergy", 10, 300.0, "G");
        
        // Verify: Application should be rejected due to duplicate approved application
        assertFalse("Application should fail for duplicate approved application", result);
        assertEquals("Customer should still have only one application", 1, customer.getApplications().size());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Setup: Create eligible customer with no prior applications
        Customer customer = new Customer();
        customer.setName("Emma");
        customer.setSurname("Davis");
        customer.setEmail("emma.d@example.com");
        customer.setTelephone("555-3456");
        
        // Execute: Attempt to create IPO application with null document
        boolean result = bank.createIPOApplication(customer, "AutoFuture", 25, 1000.0, null);
        
        // Verify: Application should be rejected due to missing document
        assertFalse("Application should fail when document is null", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
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
        
        // Execute: Attempt to create IPO application with zero shares
        boolean result = bank.createIPOApplication(customer, "NanoChip", 0, 0.0, "N");
        
        // Verify: Application should be rejected due to zero shares
        assertFalse("Application should fail when shares are zero", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
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
        
        // Execute: Attempt to create IPO application with negative shares
        boolean result = bank.createIPOApplication(customer, "CloudServ", -5, -200.0, "C");
        
        // Verify: Application should be rejected due to negative shares
        assertFalse("Application should fail when shares are negative", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
        assertEquals("Failed attempts should be incremented", 1, customer.getFailedAttempts());
    }
}