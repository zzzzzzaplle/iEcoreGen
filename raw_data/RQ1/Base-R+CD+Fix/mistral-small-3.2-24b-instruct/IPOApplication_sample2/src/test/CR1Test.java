import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private Customer customer;
    private Company company;
    private Document document;
    
    @Before
    public void setUp() {
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
        
        // Test: Apply for IPO with valid parameters
        boolean result = customer.createApplication(company, 100, 5000.0, document);
        
        // Verify: Application is successfully created
        assertTrue("Application should be created successfully for eligible customer", result);
        assertEquals("Customer should have one application", 1, customer.getApplications().size());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Setup: Customer "C002" has lost IPO eligibility
        customer.setName("Alice");
        customer.setSurname("Johnson");
        customer.setEmail("alice.j@example.com");
        customer.setTelephone("555-5678");
        customer.setCanApplyForIPO(false);
        
        company.setName("BioMed");
        company.setEmail("biomed@gmail.com");
        
        // Test: Attempt to apply for IPO with ineligible customer
        boolean result = customer.createApplication(company, 50, 2500.0, document);
        
        // Verify: Application creation should fail
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
        
        // Create first application and approve it
        boolean firstResult = customer.createApplication(company, 10, 300.0, document);
        assertTrue("First application should be created successfully", firstResult);
        
        Application firstApp = customer.getApplications().get(0);
        firstApp.approve();
        
        // Test: Submit another application to the same company
        boolean secondResult = customer.createApplication(company, 10, 300.0, document);
        
        // Verify: Second application should be rejected due to duplicate approved application
        assertFalse("Second application should not be created due to existing approved application", secondResult);
        assertEquals("Customer should have only one application", 1, customer.getApplications().size());
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
        
        // Test: Apply for IPO without uploading document (null document)
        boolean result = customer.createApplication(company, 25, 1000.0, null);
        
        // Verify: Application creation should fail due to missing document
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
        
        // Test: Apply for IPO with zero shares
        boolean result = customer.createApplication(company, 0, 0.0, document);
        
        // Verify: Application creation should fail due to zero shares
        assertFalse("Application should not be created with zero shares", result);
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
        
        // Test: Apply for IPO with negative shares
        boolean result = customer.createApplication(company, -5, -200.0, document);
        
        // Verify: Application creation should fail due to negative shares
        assertFalse("Application should not be created with negative shares", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
}