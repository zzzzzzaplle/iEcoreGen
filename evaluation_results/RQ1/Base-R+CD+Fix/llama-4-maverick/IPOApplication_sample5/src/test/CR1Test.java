import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private Customer customer;
    private Company company;
    private Document document;
    
    @Before
    public void setUp() {
        // This method runs before each test, but we'll create specific objects in each test
        // since the test cases require different setups
    }
    
    @Test
    public void testCase1_StandardEligibleSubmission() {
        // Setup: Create eligible customer with no prior approved applications for TechCorp
        customer = new Customer();
        customer.setName("John");
        customer.setSurname("Smith");
        customer.setEmail("john.smith@example.com");
        customer.setTelephone("555-1234");
        customer.setCanApplyForIPO(true);
        
        company = new Company("TechCorp", "techcorp@gmail.com");
        document = new Document(); // Document 'A'
        
        // Execute: Create application for TechCorp with 100 shares, $5000
        boolean result = customer.createApplication(company, 100, 5000.0, document);
        
        // Verify: Application should be successfully created
        assertTrue("Application should be created successfully for eligible customer", result);
        assertEquals("Customer should have 1 application", 1, customer.getApplications().size());
    }
    
    @Test
    public void testCase2_CustomerNotEligible() {
        // Setup: Create ineligible customer with no prior applications for BioMed
        customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        customer.setEmail("alice.j@example.com");
        customer.setTelephone("555-5678");
        customer.setCanApplyForIPO(false); // Customer not eligible
        
        company = new Company("BioMed", "biomed@gmail.com");
        document = new Document(); // Document 'B'
        
        // Execute: Attempt to create application for BioMed with 50 shares, $2500
        boolean result = customer.createApplication(company, 50, 2500.0, document);
        
        // Verify: Application should fail due to customer ineligibility
        assertFalse("Application should fail for ineligible customer", result);
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase3_DuplicateApprovedApplication() {
        // Setup: Create eligible customer with approved application for GreenEnergy
        customer = new Customer();
        customer.setName("Robert");
        customer.setSurname("Chen");
        customer.setEmail("r.chen@example.com");
        customer.setTelephone("555-9012");
        customer.setCanApplyForIPO(true);
        
        company = new Company("GreenEnergy", "greenenergy@gmail.com");
        document = new Document(); // Document 'G'
        
        // Create and approve first application
        Application firstApp = new Application(10, 300.0, customer, company, document);
        firstApp.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(firstApp);
        
        // Execute: Attempt to create another application for the same company
        boolean result = customer.createApplication(company, 10, 300.0, document);
        
        // Verify: Second application should fail due to existing approved application
        assertFalse("Application should fail due to duplicate approved application", result);
        assertEquals("Customer should have only 1 application", 1, customer.getApplications().size());
    }
    
    @Test
    public void testCase4_MissingDocument() {
        // Setup: Create eligible customer with no prior applications for AutoFuture
        customer = new Customer();
        customer.setName("Emma");
        customer.setSurname("Davis");
        customer.setEmail("emma.d@example.com");
        customer.setTelephone("555-3456");
        customer.setCanApplyForIPO(true);
        
        company = new Company("AutoFuture", "autofuture@gmail.com");
        // No document provided (null)
        
        // Execute: Attempt to create application without document
        boolean result = customer.createApplication(company, 25, 1000.0, null);
        
        // Verify: Application should fail due to missing document
        assertFalse("Application should fail without document", result);
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase5_ZeroShareApplication() {
        // Setup: Create eligible customer with no prior applications for NanoChip
        customer = new Customer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@example.com");
        customer.setTelephone("555-7890");
        customer.setCanApplyForIPO(true);
        
        company = new Company("NanoChip", "nanotech@gmail.com");
        document = new Document(); // Document 'N'
        
        // Execute: Attempt to create application with 0 shares
        boolean result = customer.createApplication(company, 0, 0.0, document);
        
        // Verify: Application should fail due to zero shares
        assertFalse("Application should fail with zero shares", result);
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase6_NegativeShareCount() {
        // Setup: Create eligible customer with no prior applications for CloudServ
        customer = new Customer();
        customer.setName("Sophia");
        customer.setSurname("Martinez");
        customer.setEmail("s.m@example.com");
        customer.setTelephone("555-2345");
        customer.setCanApplyForIPO(true);
        
        company = new Company("CloudServ", "cloudserv@gmail.com");
        document = new Document(); // Document 'C'
        
        // Execute: Attempt to create application with negative shares
        boolean result = customer.createApplication(company, -5, -200.0, document);
        
        // Verify: Application should fail due to negative shares
        assertFalse("Application should fail with negative shares", result);
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
    }
}