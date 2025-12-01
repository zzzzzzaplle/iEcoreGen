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
        // Setup
        customer.setName("John");
        customer.setSurname("Smith");
        customer.setEmail("john.smith@example.com");
        customer.setTelephone("555-1234");
        customer.setCanApplyForIPO(true);
        
        company.setName("TechCorp");
        company.setEmail("techcorp@gmail.com");
        
        // Execute test
        boolean result = customer.createApplication(company, 100, 5000.0, document);
        
        // Verify
        assertTrue("Application should be created successfully for eligible customer", result);
        assertEquals("Customer should have one application", 1, customer.getApplications().size());
        Application createdApp = customer.getApplications().get(0);
        assertEquals("Application should be in PENDING status", ApplicationStatus.PENDING, createdApp.getStatus());
    }
    
    @Test
    public void testCase2_CustomerNotEligible() {
        // Setup
        customer.setName("Alice");
        customer.setSurname("Johnson");
        customer.setEmail("alice.j@example.com");
        customer.setTelephone("555-5678");
        customer.setCanApplyForIPO(false); // Customer is not eligible
        
        company.setName("BioMed");
        company.setEmail("biomed@gmail.com");
        
        // Execute test
        boolean result = customer.createApplication(company, 50, 2500.0, document);
        
        // Verify
        assertFalse("Application should not be created for ineligible customer", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase3_DuplicateApprovedApplication() {
        // Setup
        customer.setName("Robert");
        customer.setSurname("Chen");
        customer.setEmail("r.chen@example.com");
        customer.setTelephone("555-9012");
        customer.setCanApplyForIPO(true);
        
        company.setName("GreenEnergy");
        company.setEmail("greenenergy@gmail.com");
        
        // Create and approve first application
        Application existingApp = new Application();
        existingApp.setShare(10);
        existingApp.setAmountOfMoney(300.0);
        existingApp.setStatus(ApplicationStatus.APPROVAL);
        existingApp.setCustomer(customer);
        existingApp.setCompany(company);
        existingApp.setAllowance(document);
        customer.getApplications().add(existingApp);
        
        // Execute test - try to create another application for same company
        boolean result = customer.createApplication(company, 10, 300.0, document);
        
        // Verify
        assertFalse("Application should not be created when approved application exists for same company", result);
        assertEquals("Customer should still have only one application", 1, customer.getApplications().size());
    }
    
    @Test
    public void testCase4_MissingDocument() {
        // Setup
        customer.setName("Emma");
        customer.setSurname("Davis");
        customer.setEmail("emma.d@example.com");
        customer.setTelephone("555-3456");
        customer.setCanApplyForIPO(true);
        
        company.setName("AutoFuture");
        company.setEmail("autofuture@gmail.com");
        
        // Execute test with null document
        boolean result = customer.createApplication(company, 25, 1000.0, null);
        
        // Verify
        assertFalse("Application should not be created without document", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase5_ZeroShareApplication() {
        // Setup
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@example.com");
        customer.setTelephone("555-7890");
        customer.setCanApplyForIPO(true);
        
        company.setName("NanoChip");
        company.setEmail("nanotech@gmail.com");
        
        // Execute test with zero shares
        boolean result = customer.createApplication(company, 0, 0.0, document);
        
        // Verify
        assertFalse("Application should not be created with zero shares", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase6_NegativeShareCount() {
        // Setup
        customer.setName("Sophia");
        customer.setSurname("Martinez");
        customer.setEmail("s.m@example.com");
        customer.setTelephone("555-2345");
        customer.setCanApplyForIPO(true);
        
        company.setName("CloudServ");
        company.setEmail("cloudserv@gmail.com");
        
        // Execute test with negative shares
        boolean result = customer.createApplication(company, -5, -200.0, document);
        
        // Verify
        assertFalse("Application should not be created with negative shares", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
}