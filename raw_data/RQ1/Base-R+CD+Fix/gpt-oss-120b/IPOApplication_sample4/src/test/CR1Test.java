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
        // Setup for Test Case 1: Standard eligible submission
        customer.setName("John");
        customer.setSurname("Smith");
        customer.setEmail("john.smith@example.com");
        customer.setTelephone("555-1234");
        customer.setCanApplyForIPO(true);
        
        company.setName("TechCorp");
        company.setEmail("techcorp@gmail.com");
        
        // Execute the application creation
        boolean result = customer.createApplication(company, 100, 5000.0, document);
        
        // Verify the result is true (application successfully created)
        assertTrue("Application should be created successfully for eligible customer", result);
        
        // Verify the application was added to customer's applications
        assertEquals("Customer should have 1 application", 1, customer.getApplications().size());
        
        Application createdApp = customer.getApplications().get(0);
        assertEquals("Application status should be PENDING", ApplicationStatus.PENDING, createdApp.getStatus());
        assertEquals("Application should have correct share count", 100, createdApp.getShare());
        assertEquals("Application should have correct amount", 5000.0, createdApp.getAmountOfMoney(), 0.001);
        assertEquals("Application should reference the correct customer", customer, createdApp.getCustomer());
        assertEquals("Application should reference the correct company", company, createdApp.getCompany());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Setup for Test Case 2: Customer not eligible
        customer.setName("Alice");
        customer.setSurname("Johnson");
        customer.setEmail("alice.j@example.com");
        customer.setTelephone("555-5678");
        customer.setCanApplyForIPO(false); // Customer is not eligible
        
        company.setName("BioMed");
        company.setEmail("biomed@gmail.com");
        
        // Execute the application creation
        boolean result = customer.createApplication(company, 50, 2500.0, document);
        
        // Verify the result is false (customer not eligible)
        assertFalse("Application should fail for ineligible customer", result);
        
        // Verify no application was created
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Setup for Test Case 3: Duplicate approved application
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
        
        Application firstApp = customer.getApplications().get(0);
        firstApp.setStatus(ApplicationStatus.APPROVAL); // Approve the application
        
        // Attempt to create second application for same company
        Document secondDoc = new Document();
        boolean secondResult = customer.createApplication(company, 10, 300.0, secondDoc);
        
        // Verify the result is false (duplicate approved application not allowed)
        assertFalse("Second application should fail due to existing approved application", secondResult);
        
        // Verify only one application exists
        assertEquals("Customer should have only 1 application", 1, customer.getApplications().size());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Setup for Test Case 4: Missing document
        customer.setName("Emma");
        customer.setSurname("Davis");
        customer.setEmail("emma.d@example.com");
        customer.setTelephone("555-3456");
        customer.setCanApplyForIPO(true);
        
        company.setName("AutoFuture");
        company.setEmail("autofuture@gmail.com");
        
        // Execute the application creation with null document
        boolean result = customer.createApplication(company, 25, 1000.0, null);
        
        // Verify the result is false (document is required)
        assertFalse("Application should fail when document is null", result);
        
        // Verify no application was created
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Setup for Test Case 5: Zero-share application
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@example.com");
        customer.setTelephone("555-7890");
        customer.setCanApplyForIPO(true);
        
        company.setName("NanoChip");
        company.setEmail("nanotech@gmail.com");
        
        // Execute the application creation with 0 shares
        boolean result = customer.createApplication(company, 0, 0.0, document);
        
        // Verify the result is false (shares must be > 0)
        assertFalse("Application should fail with 0 shares", result);
        
        // Verify no application was created
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Setup for Test Case 6: Negative share count
        customer.setName("Sophia");
        customer.setSurname("Martinez");
        customer.setEmail("s.m@example.com");
        customer.setTelephone("555-2345");
        customer.setCanApplyForIPO(true);
        
        company.setName("CloudServ");
        company.setEmail("cloudserv@gmail.com");
        
        // Execute the application creation with negative shares
        boolean result = customer.createApplication(company, -5, -200.0, document);
        
        // Verify the result is false (shares must be > 0)
        assertFalse("Application should fail with negative shares", result);
        
        // Verify no application was created
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
    }
}