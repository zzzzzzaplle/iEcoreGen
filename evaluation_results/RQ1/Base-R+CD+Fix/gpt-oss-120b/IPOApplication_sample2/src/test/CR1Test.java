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
        document = new Document();
    }
    
    @Test
    public void testCase1_standardEligibleSubmission() {
        // Setup
        customer = new Customer("John", "Smith", "john.smith@example.com", "555-1234");
        customer.setCanApplyForIPO(true);
        company = new Company("TechCorp", "techcorp@gmail.com");
        
        // Execute
        boolean result = customer.createApplication(company, 100, 5000.0, document);
        
        // Verify
        assertTrue("Application should be successfully created for eligible customer", result);
        assertEquals("Customer should have exactly 1 application", 1, customer.getApplications().size());
        
        Application createdApp = customer.getApplications().get(0);
        assertEquals("Application shares should match input", 100, createdApp.getShare());
        assertEquals("Application amount should match input", 5000.0, createdApp.getAmountOfMoney(), 0.001);
        assertEquals("Application status should be PENDING", ApplicationStatus.PENDING, createdApp.getStatus());
        assertEquals("Application company should match", company, createdApp.getCompany());
        assertEquals("Application customer should match", customer, createdApp.getCustomer());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Setup
        customer = new Customer("Alice", "Johnson", "alice.j@example.com", "555-5678");
        customer.setCanApplyForIPO(false); // Customer not eligible
        company = new Company("BioMed", "biomed@gmail.com");
        
        // Execute
        boolean result = customer.createApplication(company, 50, 2500.0, document);
        
        // Verify
        assertFalse("Application should fail for ineligible customer", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Setup
        customer = new Customer("Robert", "Chen", "r.chen@example.com", "555-9012");
        customer.setCanApplyForIPO(true);
        company = new Company("GreenEnergy", "greenenergy@gmail.com");
        
        // Create first application and approve it
        boolean firstResult = customer.createApplication(company, 10, 300.0, document);
        assertTrue("First application should be created successfully", firstResult);
        
        Application firstApp = customer.getApplications().get(0);
        boolean approvalResult = firstApp.approve();
        assertTrue("First application should be approved successfully", approvalResult);
        
        // Execute - attempt to create second application for same company
        Document secondDoc = new Document();
        boolean secondResult = customer.createApplication(company, 15, 450.0, secondDoc);
        
        // Verify
        assertFalse("Second application should fail due to existing approved application", secondResult);
        assertEquals("Customer should have exactly 1 application", 1, customer.getApplications().size());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Setup
        customer = new Customer("Emma", "Davis", "emma.d@example.com", "555-3456");
        customer.setCanApplyForIPO(true);
        company = new Company("AutoFuture", "autofuture@gmail.com");
        
        // Execute - pass null document
        boolean result = customer.createApplication(company, 25, 1000.0, null);
        
        // Verify
        assertFalse("Application should fail with null document", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Setup
        customer = new Customer("James", "Wilson", "j.wilson@example.com", "555-7890");
        customer.setCanApplyForIPO(true);
        company = new Company("NanoChip", "nanotech@gmail.com");
        
        // Execute - pass 0 shares
        boolean result = customer.createApplication(company, 0, 0.0, document);
        
        // Verify
        assertFalse("Application should fail with zero shares", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Setup
        customer = new Customer("Sophia", "Martinez", "s.m@example.com", "555-2345");
        customer.setCanApplyForIPO(true);
        company = new Company("CloudServ", "cloudserv@gmail.com");
        
        // Execute - pass negative shares
        boolean result = customer.createApplication(company, -5, -200.0, document);
        
        // Verify
        assertFalse("Application should fail with negative shares", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
}