import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private Customer customer1;
    private Customer customer2;
    private Customer customer3;
    private Customer customer4;
    private Customer customer5;
    private Customer customer6;
    
    @Before
    public void setUp() {
        // Initialize test customers
        customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Smith");
        customer1.setEmail("john.smith@example.com");
        customer1.setTelephone("555-1234");
        
        customer2 = new Customer();
        customer2.setName("Alice");
        customer2.setSurname("Johnson");
        customer2.setEmail("alice.j@example.com");
        customer2.setTelephone("555-5678");
        
        customer3 = new Customer();
        customer3.setName("Robert");
        customer3.setSurname("Chen");
        customer3.setEmail("r.chen@example.com");
        customer3.setTelephone("555-9012");
        
        customer4 = new Customer();
        customer4.setName("Emma");
        customer4.setSurname("Davis");
        customer4.setEmail("emma.d@example.com");
        customer4.setTelephone("555-3456");
        
        customer5 = new Customer();
        customer5.setName("James");
        customer5.setSurname("Wilson");
        customer5.setEmail("j.wilson@example.com");
        customer5.setTelephone("555-7890");
        
        customer6 = new Customer();
        customer6.setName("Sophia");
        customer6.setSurname("Martinez");
        customer6.setEmail("s.m@example.com");
        customer6.setTelephone("555-2345");
    }
    
    @Test
    public void testCase1_StandardEligibleSubmission() {
        // Test Case 1: Standard eligible submission
        // Setup: Customer "C001" allows IPO applications and has no previously approved applications for "TechCorp"
        
        // Execute the application creation
        boolean result = IPOApplication.createIPOApplication(customer1, "TechCorp", 100, 5000.0, "A");
        
        // Verify the result
        assertTrue("Application should be successfully created for eligible customer", result);
        assertEquals("Customer should have one application", 1, customer1.getApplications().size());
        assertEquals("Company name should match", "TechCorp", customer1.getApplications().get(0).getCompanyName());
    }
    
    @Test
    public void testCase2_CustomerNotEligible() {
        // Test Case 2: Customer not eligible
        // Setup: Customer has lost IPO eligibility after repeated failed attempts
        customer2.incrementFailedAttempts();
        customer2.incrementFailedAttempts();
        customer2.incrementFailedAttempts(); // This should restrict the customer
        
        // Execute the application creation
        boolean result = IPOApplication.createIPOApplication(customer2, "BioMed", 50, 2500.0, "B");
        
        // Verify the result
        assertFalse("Application should fail for restricted customer", result);
        assertTrue("Customer should be restricted", customer2.isRestricted());
        assertEquals("Failed attempts should be 3", 3, customer2.getFailedAttempts());
        assertEquals("Customer should have no applications", 0, customer2.getApplications().size());
    }
    
    @Test
    public void testCase3_DuplicateApprovedApplication() {
        // Test Case 3: Duplicate approved application
        // Setup: Customer remains eligible, submit and approve first application
        IPOApplication.createIPOApplication(customer3, "GreenEnergy", 10, 300.0, "G");
        IPOApplication firstApp = customer3.getApplications().get(0);
        IPOApplication.approveOrRejectApplication(firstApp, true);
        
        // Execute second application creation for same company
        boolean result = IPOApplication.createIPOApplication(customer3, "GreenEnergy", 10, 300.0, "G");
        
        // Verify the result
        assertFalse("Application should fail due to duplicate approved application", result);
        assertEquals("Customer should have only one application", 1, customer3.getApplications().size());
        assertEquals("Application status should remain APPROVED", ApplicationStatus.APPROVED, firstApp.getStatus());
    }
    
    @Test
    public void testCase4_MissingDocument() {
        // Test Case 4: Missing document
        // Setup: No prior applications for "AutoFuture"
        
        // Execute the application creation with null document
        boolean result = IPOApplication.createIPOApplication(customer4, "AutoFuture", 25, 1000.0, null);
        
        // Verify the result
        assertFalse("Application should fail due to missing document", result);
        assertEquals("Customer should have no applications", 0, customer4.getApplications().size());
        assertEquals("Failed attempts should be incremented", 1, customer4.getFailedAttempts());
    }
    
    @Test
    public void testCase5_ZeroShareApplication() {
        // Test Case 5: Zero-share application
        // Setup: No existing applications for "NanoChip"
        
        // Execute the application creation with zero shares
        boolean result = IPOApplication.createIPOApplication(customer5, "NanoChip", 0, 0.0, "N");
        
        // Verify the result
        assertFalse("Application should fail due to zero shares", result);
        assertEquals("Customer should have no applications", 0, customer5.getApplications().size());
        assertEquals("Failed attempts should be incremented", 1, customer5.getFailedAttempts());
    }
    
    @Test
    public void testCase6_NegativeShareCount() {
        // Test Case 6: Negative share count
        // Setup: No prior applications for "CloudServ"
        
        // Execute the application creation with negative shares
        boolean result = IPOApplication.createIPOApplication(customer6, "CloudServ", -5, -200.0, "C");
        
        // Verify the result
        assertFalse("Application should fail due to negative shares", result);
        assertEquals("Customer should have no applications", 0, customer6.getApplications().size());
        assertEquals("Failed attempts should be incremented", 1, customer6.getFailedAttempts());
    }
}