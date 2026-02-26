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
        customer = new Customer();
        company = new Company();
        document = new Document();
    }
    
    @Test
    public void testCase1_standardEligibleSubmission() {
        // Setup: Customer "C001" allows IPO applications and has no previously approved applications for "TechCorp"
        customer.setName("John");
        customer.setSurname("Smith");
        customer.setEmail("john.smith@example.com");
        customer.setTelephone("555-1234");
        customer.setCanApplyForIPO(true);
        
        company.setName("TechCorp");
        company.setEmail("techcorp@gmail.com");
        
        // Execute: Apply for IPO with 100 shares, $5000 payment, and document 'A'
        boolean result = customer.createApplication(company, 100, 5000.0, document);
        
        // Verify: Application is successfully created (true)
        assertTrue("Application should be created successfully for eligible customer", result);
        assertEquals("Customer should have exactly 1 application", 1, customer.getApplications().size());
        assertEquals("Application status should be PENDING", ApplicationStatus.PENDING, customer.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Setup: Customer "C002" has lost IPO eligibility
        customer.setName("Alice");
        customer.setSurname("Johnson");
        customer.setEmail("alice.j@example.com");
        customer.setTelephone("555-5678");
        customer.setCanApplyForIPO(false); // Customer not eligible
        
        company.setName("BioMed");
        company.setEmail("biomed@gmail.com");
        
        // Execute: Attempt to apply for IPO with 50 shares, $2500 payment, and document 'B'
        boolean result = customer.createApplication(company, 50, 2500.0, document);
        
        // Verify: Application creation should fail (false)
        assertFalse("Application should not be created for ineligible customer", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Setup: Customer "C003" remains eligible and has an approved application for "GreenEnergy"
        customer.setName("Robert");
        customer.setSurname("Chen");
        customer.setEmail("r.chen@example.com");
        customer.setTelephone("555-9012");
        customer.setCanApplyForIPO(true);
        
        company.setName("GreenEnergy");
        company.setEmail("greenenergy@gmail.com");
        
        // Create and approve first application
        boolean firstApplication = customer.createApplication(company, 10, 300.0, document);
        assertTrue("First application should be created", firstApplication);
        
        Application app = customer.getApplications().get(0);
        app.approve(); // Approve the application
        
        // Execute: Submit another application to the same company
        boolean secondApplication = customer.createApplication(company, 10, 300.0, document);
        
        // Verify: Second application should fail (false) - only one approved application per company allowed
        assertFalse("Second application to same company should not be allowed", secondApplication);
        assertEquals("Customer should have only 1 application", 1, customer.getApplications().size());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Setup: Eligible customer "C004" with no prior applications for "AutoFuture"
        customer.setName("Emma");
        customer.setSurname("Davis");
        customer.setEmail("emma.d@example.com");
        customer.setTelephone("555-3456");
        customer.setCanApplyForIPO(true);
        
        company.setName("AutoFuture");
        company.setEmail("autofuture@gmail.com");
        
        // Execute: Apply for IPO with 25 shares, $1000 payment, but no document (null)
        boolean result = customer.createApplication(company, 25, 1000.0, null);
        
        // Verify: Application creation should fail (false) - document upload is mandatory
        assertFalse("Application should not be created without document", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Setup: Eligible customer "C005" with no existing applications for "NanoChip"
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@example.com");
        customer.setTelephone("555-7890");
        customer.setCanApplyForIPO(true);
        
        company.setName("NanoChip");
        company.setEmail("nanotech@gmail.com");
        
        // Execute: Apply for IPO with 0 shares, $0 payment, and document 'N'
        boolean result = customer.createApplication(company, 0, 0.0, document);
        
        // Verify: Application creation should fail (false) - shares must be > 0
        assertFalse("Application should not be created with 0 shares", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Setup: Eligible customer "C006" with no prior applications for "CloudServ"
        customer.setName("Sophia");
        customer.setSurname("Martinez");
        customer.setEmail("s.m@example.com");
        customer.setTelephone("555-2345");
        customer.setCanApplyForIPO(true);
        
        company.setName("CloudServ");
        company.setEmail("cloudserv@gmail.com");
        
        // Execute: Apply for IPO with -5 shares, -$200 payment, and document 'C'
        boolean result = customer.createApplication(company, -5, -200.0, document);
        
        // Verify: Application creation should fail (false) - negative shares/amount are invalid
        assertFalse("Application should not be created with negative shares", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
}