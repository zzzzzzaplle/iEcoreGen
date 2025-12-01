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
    public void testCase1_StandardEligibleSubmission() {
        // Setup customer C001
        customer.setName("John");
        customer.setSurname("Smith");
        customer.setEmail("john.smith@example.com");
        customer.setTelephone("555-1234");
        customer.setCanApplyForIPO(true);
        customer.setFailedAttempts(0);
        
        // Setup company TechCorp
        company.setName("TechCorp");
        company.setEmail("techcorp@gmail.com");
        
        // Test application creation
        boolean result = customer.createApplication(company, 100, 5000.0, document);
        
        // Verify application was created successfully
        assertTrue("Application should be created successfully for eligible customer", result);
        assertEquals("Failed attempts should be reset to 0 after successful application", 0, customer.getFailedAttempts());
        assertEquals("Customer should have one application", 1, customer.getApplications().size());
    }
    
    @Test
    public void testCase2_CustomerNotEligible() {
        // Setup customer C002 with lost eligibility
        customer.setName("Alice");
        customer.setSurname("Johnson");
        customer.setEmail("alice.j@example.com");
        customer.setTelephone("555-5678");
        customer.setCanApplyForIPO(false); // Customer lost eligibility
        customer.setFailedAttempts(0);
        
        // Setup company BioMed
        company.setName("BioMed");
        company.setEmail("biomed@gmail.com");
        
        // Test application creation
        boolean result = customer.createApplication(company, 50, 2500.0, document);
        
        // Verify application was not created
        assertFalse("Application should fail for ineligible customer", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase3_DuplicateApprovedApplication() {
        // Setup customer C003
        customer.setName("Robert");
        customer.setSurname("Chen");
        customer.setEmail("r.chen@example.com");
        customer.setTelephone("555-9012");
        customer.setCanApplyForIPO(true);
        customer.setFailedAttempts(0);
        
        // Setup company GreenEnergy
        company.setName("GreenEnergy");
        company.setEmail("greenenergy@gmail.com");
        
        // Create first application and approve it
        Application firstApp = new Application();
        firstApp.setCustomer(customer);
        firstApp.setCompany(company);
        firstApp.setShare(10);
        firstApp.setAmountOfMoney(300.0);
        firstApp.setAllowance(document);
        firstApp.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(firstApp);
        
        // Attempt to create duplicate application
        boolean result = customer.createApplication(company, 10, 300.0, document);
        
        // Verify duplicate application was rejected
        assertFalse("Duplicate application should be rejected", result);
        assertEquals("Customer should still have only one application", 1, customer.getApplications().size());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCase4_MissingDocument() {
        // Setup customer C004
        customer.setName("Emma");
        customer.setSurname("Davis");
        customer.setEmail("emma.d@example.com");
        customer.setTelephone("555-3456");
        customer.setCanApplyForIPO(true);
        customer.setFailedAttempts(0);
        
        // Setup company AutoFuture
        company.setName("AutoFuture");
        company.setEmail("autofuture@gmail.com");
        
        // Attempt to create application without document (null)
        customer.createApplication(company, 25, 1000.0, null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCase5_ZeroShareApplication() {
        // Setup customer C005
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@example.com");
        customer.setTelephone("555-7890");
        customer.setCanApplyForIPO(true);
        customer.setFailedAttempts(0);
        
        // Setup company NanoChip
        company.setName("NanoChip");
        company.setEmail("nanotech@gmail.com");
        
        // Attempt to create application with 0 shares
        customer.createApplication(company, 0, 0.0, document);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCase6_NegativeShareCount() {
        // Setup customer C006
        customer.setName("Sophia");
        customer.setSurname("Martinez");
        customer.setEmail("s.m@example.com");
        customer.setTelephone("555-2345");
        customer.setCanApplyForIPO(true);
        customer.setFailedAttempts(0);
        
        // Setup company CloudServ
        company.setName("CloudServ");
        company.setEmail("cloudserv@gmail.com");
        
        // Attempt to create application with negative shares
        customer.createApplication(company, -5, -200.0, document);
    }
}