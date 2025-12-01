import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private IPOApplicationSystem system;
    
    @Before
    public void setUp() {
        system = new IPOApplicationSystem();
    }
    
    @Test
    public void testCase1_standardEligibleSubmission() {
        // Setup: Create eligible customer with no prior approved applications for TechCorp
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Smith");
        customer.setEmailAddress("john.smith@example.com");
        customer.setTelephoneNumber("555-1234");
        customer.setEligibleForIPO(true);
        
        Company company = new Company();
        company.setName("TechCorp");
        company.setEmailAddress("techcorp@gmail.com");
        
        // Execute: Apply for IPO with valid parameters
        boolean result = system.createIPOApplication(customer, company, 100, 5000.0, "A");
        
        // Verify: Application should be successfully created
        assertTrue("Application should be created successfully for eligible customer", result);
        assertEquals("Customer should have 1 application", 1, customer.getApplications().size());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Setup: Create ineligible customer with no prior applications
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        customer.setEmailAddress("alice.j@example.com");
        customer.setTelephoneNumber("555-5678");
        customer.setEligibleForIPO(false); // Customer is not eligible
        
        Company company = new Company();
        company.setName("BioMed");
        company.setEmailAddress("biomed@gmail.com");
        
        // Execute: Attempt to apply for IPO
        boolean result = system.createIPOApplication(customer, company, 50, 2500.0, "B");
        
        // Verify: Application should be rejected due to ineligibility
        assertFalse("Application should be rejected for ineligible customer", result);
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Setup: Create eligible customer with approved application for GreenEnergy
        Customer customer = new Customer();
        customer.setName("Robert");
        customer.setSurname("Chen");
        customer.setEmailAddress("r.chen@example.com");
        customer.setTelephoneNumber("555-9012");
        customer.setEligibleForIPO(true);
        
        Company company = new Company();
        company.setName("GreenEnergy");
        company.setEmailAddress("greenenergy@gmail.com");
        
        // Create and approve first application
        IPOApplication firstApp = new IPOApplication();
        firstApp.setCustomer(customer);
        firstApp.setCompany(company);
        firstApp.setNumberOfShares(10);
        firstApp.setAmount(300.0);
        firstApp.setDocument("G");
        firstApp.setApproved(true); // Application is approved
        
        customer.getApplications().add(firstApp);
        
        // Execute: Attempt to create another application for the same company
        boolean result = system.createIPOApplication(customer, company, 10, 300.0, "G");
        
        // Verify: Second application should be rejected due to existing approved application
        assertFalse("Application should be rejected due to duplicate approved application", result);
        assertEquals("Customer should still have only 1 application", 1, customer.getApplications().size());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Setup: Create eligible customer with no prior applications
        Customer customer = new Customer();
        customer.setName("Emma");
        customer.setSurname("Davis");
        customer.setEmailAddress("emma.d@example.com");
        customer.setTelephoneNumber("555-3456");
        customer.setEligibleForIPO(true);
        
        Company company = new Company();
        company.setName("AutoFuture");
        company.setEmailAddress("autofuture@gmail.com");
        
        // Execute: Attempt to apply without document (null)
        boolean result = system.createIPOApplication(customer, company, 25, 1000.0, null);
        
        // Verify: Application should be rejected due to missing document
        assertFalse("Application should be rejected due to missing document", result);
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Setup: Create eligible customer with no prior applications
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmailAddress("j.wilson@example.com");
        customer.setTelephoneNumber("555-7890");
        customer.setEligibleForIPO(true);
        
        Company company = new Company();
        company.setName("NanoChip");
        company.setEmailAddress("nanotech@gmail.com");
        
        // Execute: Attempt to apply with 0 shares
        boolean result = system.createIPOApplication(customer, company, 0, 0.0, "N");
        
        // Verify: Application should be rejected due to zero shares
        assertFalse("Application should be rejected due to zero shares", result);
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Setup: Create eligible customer with no prior applications
        Customer customer = new Customer();
        customer.setName("Sophia");
        customer.setSurname("Martinez");
        customer.setEmailAddress("s.m@example.com");
        customer.setTelephoneNumber("555-2345");
        customer.setEligibleForIPO(true);
        
        Company company = new Company();
        company.setName("CloudServ");
        company.setEmailAddress("cloudserv@gmail.com");
        
        // Execute: Attempt to apply with negative shares
        boolean result = system.createIPOApplication(customer, company, -5, -200.0, "C");
        
        // Verify: Application should be rejected due to negative shares
        assertFalse("Application should be rejected due to negative shares", result);
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
    }
}