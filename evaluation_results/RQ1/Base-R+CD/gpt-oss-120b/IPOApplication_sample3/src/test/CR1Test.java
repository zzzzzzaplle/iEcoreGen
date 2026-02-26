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
        // Setup: An eligible customer with no prior approved applications
        customer.setName("John");
        customer.setSurname("Smith");
        customer.setEmail("john.smith@example.com");
        customer.setTelephone("555-1234");
        customer.setCanApplyForIPO(true);
        
        company.setName("TechCorp");
        company.setEmail("techcorp@gmail.com");
        
        // Execute: Apply for IPO with valid parameters
        boolean result = customer.createApplication(company, 100, 5000.0, document);
        
        // Verify: Application should be successfully created
        assertTrue("Standard eligible submission should return true", result);
        assertEquals("Customer should have one application", 1, customer.getApplications().size());
        
        Application createdApp = customer.getApplications().get(0);
        assertEquals("Application should be for TechCorp", "TechCorp", createdApp.getCompany().getName());
        assertEquals("Application should have 100 shares", 100, createdApp.getShare());
        assertEquals("Application should have $5000 amount", 5000.0, createdApp.getAmountOfMoney(), 0.001);
        assertEquals("Application status should be PENDING", ApplicationStatus.PENDING, createdApp.getStatus());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Setup: Customer who is not eligible for IPO applications
        customer.setName("Alice");
        customer.setSurname("Johnson");
        customer.setEmail("alice.j@example.com");
        customer.setTelephone("555-5678");
        customer.setCanApplyForIPO(false); // Customer lost IPO eligibility
        
        company.setName("BioMed");
        company.setEmail("biomed@gmail.com");
        
        // Execute: Attempt to apply for IPO
        boolean result = customer.createApplication(company, 50, 2500.0, document);
        
        // Verify: Application should be rejected due to ineligibility
        assertFalse("Ineligible customer should return false", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Setup: Customer with existing approved application for the same company
        customer.setName("Robert");
        customer.setSurname("Chen");
        customer.setEmail("r.chen@example.com");
        customer.setTelephone("555-9012");
        customer.setCanApplyForIPO(true);
        
        company.setName("GreenEnergy");
        company.setEmail("greenenergy@gmail.com");
        
        // Create and approve first application
        Application firstApp = new Application();
        firstApp.setCustomer(customer);
        firstApp.setCompany(company);
        firstApp.setShare(10);
        firstApp.setAmountOfMoney(300.0);
        firstApp.setAllowance(document);
        firstApp.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(firstApp);
        
        // Execute: Attempt to create another application for the same company
        boolean result = customer.createApplication(company, 10, 300.0, document);
        
        // Verify: Second application should be rejected due to duplicate approved application
        assertFalse("Duplicate approved application should return false", result);
        assertEquals("Customer should still have only one application", 1, customer.getApplications().size());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Setup: Eligible customer with no prior applications
        customer.setName("Emma");
        customer.setSurname("Davis");
        customer.setEmail("emma.d@example.com");
        customer.setTelephone("555-3456");
        customer.setCanApplyForIPO(true);
        
        company.setName("AutoFuture");
        company.setEmail("autofuture@gmail.com");
        
        // Execute: Apply without uploading document (null document)
        boolean result = customer.createApplication(company, 25, 1000.0, null);
        
        // Verify: Application should be rejected due to missing document
        assertFalse("Application with null document should return false", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Setup: Eligible customer with no prior applications
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@example.com");
        customer.setTelephone("555-7890");
        customer.setCanApplyForIPO(true);
        
        company.setName("NanoChip");
        company.setEmail("nanotech@gmail.com");
        
        // Execute: Apply with zero shares
        boolean result = customer.createApplication(company, 0, 0.0, document);
        
        // Verify: Application should be rejected due to zero shares
        assertFalse("Application with zero shares should return false", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Setup: Eligible customer with no prior applications
        customer.setName("Sophia");
        customer.setSurname("Martinez");
        customer.setEmail("s.m@example.com");
        customer.setTelephone("555-2345");
        customer.setCanApplyForIPO(true);
        
        company.setName("CloudServ");
        company.setEmail("cloudserv@gmail.com");
        
        // Execute: Apply with negative shares
        boolean result = customer.createApplication(company, -5, -200.0, document);
        
        // Verify: Application should be rejected due to negative shares
        assertFalse("Application with negative shares should return false", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
}