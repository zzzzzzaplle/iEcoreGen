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
        // Setup: Create eligible customer with no prior approved applications for TechCorp
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Smith");
        customer.setEmail("john.smith@example.com");
        customer.setTelephone("555-1234");
        customer.setEligible(true);
        customer.setFailedAttempts(0); // Ensures not restricted
        
        Company company = new Company("TechCorp");
        
        // Execute: Create application with valid parameters
        boolean result = bank.createApplication(customer, company, 100, 5000.0, "A");
        
        // Verify: Application should be successfully created
        assertTrue("Application should be created successfully for eligible customer", result);
        assertEquals("Customer should have exactly 1 application", 1, customer.getApplications().size());
        assertEquals("Application status should be PENDING", ApplicationStatus.PENDING, customer.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Setup: Create ineligible customer (restricted due to failed attempts)
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        customer.setEmail("alice.j@example.com");
        customer.setTelephone("555-5678");
        customer.setEligible(false); // Explicitly not eligible
        customer.setFailedAttempts(3); // Should make customer restricted
        
        Company company = new Company("BioMed");
        
        // Execute: Attempt to create application
        boolean result = bank.createApplication(customer, company, 50, 2500.0, "B");
        
        // Verify: Application should be rejected due to ineligibility
        assertFalse("Application should not be created for ineligible customer", result);
        assertEquals("Failed attempts should increment", 4, customer.getFailedAttempts());
        assertTrue("Customer should be restricted", customer.isRestricted());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Setup: Create eligible customer with existing approved application for GreenEnergy
        Customer customer = new Customer();
        customer.setName("Robert");
        customer.setSurname("Chen");
        customer.setEmail("r.chen@example.com");
        customer.setTelephone("555-9012");
        customer.setEligible(true);
        
        Company company = new Company("GreenEnergy");
        
        // Create and approve first application
        IPOApplication existingApp = new IPOApplication();
        existingApp.setCustomer(customer);
        existingApp.setCompany(company);
        existingApp.setNumberOfShares(10);
        existingApp.setAmount(300.0);
        existingApp.setDocument("G");
        existingApp.setStatus(ApplicationStatus.APPROVED);
        customer.getApplications().add(existingApp);
        
        // Execute: Attempt to create another application for same company
        boolean result = bank.createApplication(customer, company, 10, 300.0, "G");
        
        // Verify: Second application should be rejected due to existing approved application
        assertFalse("Application should not be created when approved application exists for same company", result);
        assertEquals("Failed attempts should increment", 1, customer.getFailedAttempts());
        assertEquals("Customer should still have only 1 application", 1, customer.getApplications().size());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Setup: Create eligible customer with no prior applications
        Customer customer = new Customer();
        customer.setName("Emma");
        customer.setSurname("Davis");
        customer.setEmail("emma.d@example.com");
        customer.setTelephone("555-3456");
        customer.setEligible(true);
        
        Company company = new Company("AutoFuture");
        
        // Execute: Attempt to create application with null document
        boolean result = bank.createApplication(customer, company, 25, 1000.0, null);
        
        // Verify: Application should be rejected due to missing document
        assertFalse("Application should not be created with null document", result);
        assertEquals("Failed attempts should increment", 1, customer.getFailedAttempts());
        assertTrue("Customer applications should remain empty", customer.getApplications().isEmpty());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Setup: Create eligible customer with no prior applications
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@example.com");
        customer.setTelephone("555-7890");
        customer.setEligible(true);
        
        Company company = new Company("NanoChip");
        
        // Execute: Attempt to create application with 0 shares
        boolean result = bank.createApplication(customer, company, 0, 0.0, "N");
        
        // Verify: Application should be rejected due to zero shares
        assertFalse("Application should not be created with 0 shares", result);
        assertEquals("Failed attempts should increment", 1, customer.getFailedAttempts());
        assertTrue("Customer applications should remain empty", customer.getApplications().isEmpty());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Setup: Create eligible customer with no prior applications
        Customer customer = new Customer();
        customer.setName("Sophia");
        customer.setSurname("Martinez");
        customer.setEmail("s.m@example.com");
        customer.setTelephone("555-2345");
        customer.setEligible(true);
        
        Company company = new Company("CloudServ");
        
        // Execute: Attempt to create application with negative shares
        boolean result = bank.createApplication(customer, company, -5, -200.0, "C");
        
        // Verify: Application should be rejected due to negative shares
        assertFalse("Application should not be created with negative shares", result);
        assertEquals("Failed attempts should increment", 1, customer.getFailedAttempts());
        assertTrue("Customer applications should remain empty", customer.getApplications().isEmpty());
    }
}