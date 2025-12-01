import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private Customer customer;
    private IPOApplicationService service;
    
    @Before
    public void setUp() {
        service = new IPOApplicationService();
    }
    
    @Test
    public void testCase1_standardEligibleSubmission() {
        // Setup: Create eligible customer with no prior approved applications
        customer = new Customer();
        customer.setName("John");
        customer.setSurname("Smith");
        customer.setEmail("john.smith@example.com");
        customer.setTelephoneNumber("555-1234");
        customer.setRestricted(false); // Eligible customer
        
        // Execute: Apply for IPO with valid parameters
        boolean result = service.createIPOApplication(customer, "TechCorp", 100, 5000.0, "A");
        
        // Verify: Application should be successfully created
        assertTrue("Application should be created successfully for eligible customer", result);
        assertEquals("Customer should have one IPO application", 1, customer.getIpoApplications().size());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Setup: Create restricted customer
        customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        customer.setEmail("alice.j@example.com");
        customer.setTelephoneNumber("555-5678");
        customer.setRestricted(true); // Customer not eligible
        
        // Execute: Attempt to apply for IPO
        boolean result = service.createIPOApplication(customer, "BioMed", 50, 2500.0, "B");
        
        // Verify: Application should be rejected due to customer restriction
        assertFalse("Application should be rejected for restricted customer", result);
        assertEquals("Customer should have no IPO applications", 0, customer.getIpoApplications().size());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Setup: Create eligible customer with approved application
        customer = new Customer();
        customer.setName("Robert");
        customer.setSurname("Chen");
        customer.setEmail("r.chen@example.com");
        customer.setTelephoneNumber("555-9012");
        customer.setRestricted(false); // Eligible customer
        
        // Create and approve first application
        IPOApplication firstApp = new IPOApplication();
        firstApp.setCompanyName("GreenEnergy");
        firstApp.setNumberOfShares(10);
        firstApp.setAmountOfMoney(300.0);
        firstApp.setDocument("G");
        firstApp.setStatus(ApplicationStatus.APPROVED);
        customer.getIpoApplications().add(firstApp);
        
        // Execute: Attempt to create another application for same company
        boolean result = service.createIPOApplication(customer, "GreenEnergy", 10, 300.0, "G");
        
        // Verify: Second application should be rejected due to duplicate approved application
        assertFalse("Application should be rejected for duplicate approved company", result);
        assertEquals("Customer should still have only one IPO application", 1, customer.getIpoApplications().size());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Setup: Create eligible customer
        customer = new Customer();
        customer.setName("Emma");
        customer.setSurname("Davis");
        customer.setEmail("emma.d@example.com");
        customer.setTelephoneNumber("555-3456");
        customer.setRestricted(false); // Eligible customer
        
        // Execute: Apply for IPO with null document
        boolean result = service.createIPOApplication(customer, "AutoFuture", 25, 1000.0, null);
        
        // Verify: Application should be rejected due to missing document
        assertFalse("Application should be rejected when document is null", result);
        assertEquals("Customer should have no IPO applications", 0, customer.getIpoApplications().size());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Setup: Create eligible customer
        customer = new Customer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@example.com");
        customer.setTelephoneNumber("555-7890");
        customer.setRestricted(false); // Eligible customer
        
        // Execute: Apply for IPO with zero shares
        boolean result = service.createIPOApplication(customer, "NanoChip", 0, 0.0, "N");
        
        // Verify: Application should be rejected due to zero shares
        assertFalse("Application should be rejected when number of shares is zero", result);
        assertEquals("Customer should have no IPO applications", 0, customer.getIpoApplications().size());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Setup: Create eligible customer
        customer = new Customer();
        customer.setName("Sophia");
        customer.setSurname("Martinez");
        customer.setEmail("s.m@example.com");
        customer.setTelephoneNumber("555-2345");
        customer.setRestricted(false); // Eligible customer
        
        // Execute: Apply for IPO with negative shares
        boolean result = service.createIPOApplication(customer, "CloudServ", -5, -200.0, "C");
        
        // Verify: Application should be rejected due to negative shares
        assertFalse("Application should be rejected when number of shares is negative", result);
        assertEquals("Customer should have no IPO applications", 0, customer.getIpoApplications().size());
    }
}