import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    private BankSystem bankSystem;
    
    @Before
    public void setUp() {
        bankSystem = new BankSystem();
    }
    
    @Test
    public void testCase1_standardEligibleSubmission() {
        // Setup: Create eligible customer with no prior approved applications for TechCorp
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Smith");
        customer.setEmail("john.smith@example.com");
        customer.setTelephone("555-1234");
        customer.setEligibleForIPO(true);
        
        Document document = new Document();
        document.setContent("A");
        
        // Execute: Create IPO application
        boolean result = bankSystem.createIPOApplication(customer, "TechCorp", 100, 5000.0, document);
        
        // Verify: Application should be created successfully
        assertTrue("Application should be created successfully for eligible customer", result);
        assertEquals("Customer should have 1 application", 1, customer.getApplications().size());
        assertEquals("Bank system should have 1 application", 1, bankSystem.getApplications().size());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Setup: Create ineligible customer
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        customer.setEmail("alice.j@example.com");
        customer.setTelephone("555-5678");
        customer.setEligibleForIPO(false); // Customer is not eligible
        
        Document document = new Document();
        document.setContent("B");
        
        // Execute: Attempt to create IPO application
        boolean result = bankSystem.createIPOApplication(customer, "BioMed", 50, 2500.0, document);
        
        // Verify: Application should not be created
        assertFalse("Application should fail for ineligible customer", result);
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
        assertEquals("Bank system should have 0 applications", 0, bankSystem.getApplications().size());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Setup: Create customer with approved application for GreenEnergy
        Customer customer = new Customer();
        customer.setName("Robert");
        customer.setSurname("Chen");
        customer.setEmail("r.chen@example.com");
        customer.setTelephone("555-9012");
        customer.setEligibleForIPO(true);
        
        // Create and approve first application
        Document document1 = new Document();
        document1.setContent("G");
        bankSystem.createIPOApplication(customer, "GreenEnergy", 10, 300.0, document1);
        
        // Approve the first application
        IPOApplication firstApplication = customer.getApplications().get(0);
        bankSystem.approveOrRejectApplication(firstApplication, true);
        
        // Execute: Attempt to create second application for same company
        Document document2 = new Document();
        document2.setContent("G");
        boolean result = bankSystem.createIPOApplication(customer, "GreenEnergy", 10, 300.0, document2);
        
        // Verify: Second application should fail due to duplicate approved application
        assertFalse("Application should fail due to existing approved application for same company", result);
        assertEquals("Customer should have only 1 application", 1, customer.getApplications().size());
        assertEquals("Bank system should have only 1 application", 1, bankSystem.getApplications().size());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Setup: Create eligible customer
        Customer customer = new Customer();
        customer.setName("Emma");
        customer.setSurname("Davis");
        customer.setEmail("emma.d@example.com");
        customer.setTelephone("555-3456");
        customer.setEligibleForIPO(true);
        
        // Execute: Attempt to create application with null document
        boolean result = bankSystem.createIPOApplication(customer, "AutoFuture", 25, 1000.0, null);
        
        // Verify: Application should fail due to missing document
        assertFalse("Application should fail when document is null", result);
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
        assertEquals("Bank system should have 0 applications", 0, bankSystem.getApplications().size());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Setup: Create eligible customer
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@example.com");
        customer.setTelephone("555-7890");
        customer.setEligibleForIPO(true);
        
        Document document = new Document();
        document.setContent("N");
        
        // Execute: Attempt to create application with zero shares
        boolean result = bankSystem.createIPOApplication(customer, "NanoChip", 0, 0.0, document);
        
        // Verify: Application should fail due to zero shares
        assertFalse("Application should fail when number of shares is zero", result);
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
        assertEquals("Bank system should have 0 applications", 0, bankSystem.getApplications().size());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Setup: Create eligible customer
        Customer customer = new Customer();
        customer.setName("Sophia");
        customer.setSurname("Martinez");
        customer.setEmail("s.m@example.com");
        customer.setTelephone("555-2345");
        customer.setEligibleForIPO(true);
        
        Document document = new Document();
        document.setContent("C");
        
        // Execute: Attempt to create application with negative shares
        boolean result = bankSystem.createIPOApplication(customer, "CloudServ", -5, -200.0, document);
        
        // Verify: Application should fail due to negative shares
        assertFalse("Application should fail when number of shares is negative", result);
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
        assertEquals("Bank system should have 0 applications", 0, bankSystem.getApplications().size());
    }
}