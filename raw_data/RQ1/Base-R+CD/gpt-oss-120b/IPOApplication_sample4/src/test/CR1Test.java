import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private Customer customer;
    private Company company;
    private Document document;
    
    @Before
    public void setUp() {
        // Common setup that runs before each test
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
        
        // Input: Apply for 100 shares with $5,000 payment and upload document 'A'
        boolean result = customer.createApplication(company, 100, 5000.0, document);
        
        // Expected Output: True (application is successfully created)
        assertTrue("Standard eligible submission should return true", result);
        assertEquals("Customer should have 1 application", 1, customer.getApplications().size());
        assertEquals("Application status should be PENDING", ApplicationStatus.PENDING, customer.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Setup: Customer "C002" has lost IPO eligibility after repeated failed attempts
        customer.setName("Alice");
        customer.setSurname("Johnson");
        customer.setEmail("alice.j@example.com");
        customer.setTelephone("555-5678");
        customer.setCanApplyForIPO(false); // Customer not eligible
        
        company.setName("BioMed");
        company.setEmail("biomed@gmail.com");
        
        // Input: Apply for 50 shares with $2,500 payment and upload document 'B'
        boolean result = customer.createApplication(company, 50, 2500.0, document);
        
        // Expected Output: False
        assertFalse("Ineligible customer submission should return false", result);
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
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
        boolean firstResult = customer.createApplication(company, 10, 300.0, document);
        assertTrue("First application should be created successfully", firstResult);
        
        // Approve the first application
        Application firstApp = customer.getApplications().get(0);
        boolean approvalResult = firstApp.approve();
        assertTrue("First application should be approved", approvalResult);
        assertEquals("First application status should be APPROVAL", ApplicationStatus.APPROVAL, firstApp.getStatus());
        
        // Input: Submit another application to the same company
        Document secondDoc = new Document();
        boolean secondResult = customer.createApplication(company, 10, 300.0, secondDoc);
        
        // Expected Output: False (only one approved application per company allowed)
        assertFalse("Duplicate approved application should return false", secondResult);
        assertEquals("Customer should still have only 1 application", 1, customer.getApplications().size());
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
        
        // Input: Apply for 25 shares with $1,000 payment but no document (null)
        boolean result = customer.createApplication(company, 25, 1000.0, null);
        
        // Expected Output: False (document upload is mandatory)
        assertFalse("Application with null document should return false", result);
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
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
        
        // Input: Apply for 0 shares with $0 payment and document 'N'
        boolean result = customer.createApplication(company, 0, 0.0, document);
        
        // Expected Output: False
        assertFalse("Application with zero shares should return false", result);
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
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
        
        // Input: Apply for -5 shares with -$200 payment and document 'C'
        boolean result = customer.createApplication(company, -5, -200.0, document);
        
        // Expected Output: False (negative shares/amount are invalid)
        assertFalse("Application with negative shares should return false", result);
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
    }
}