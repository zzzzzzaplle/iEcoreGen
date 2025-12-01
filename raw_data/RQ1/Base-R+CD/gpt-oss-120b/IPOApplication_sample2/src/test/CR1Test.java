import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

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
        // Setup customer C001
        customer.setName("John");
        customer.setSurname("Smith");
        customer.setEmail("john.smith@example.com");
        customer.setTelephone("555-1234");
        customer.setCanApplyForIPO(true);
        customer.setApplications(new ArrayList<>());
        
        // Setup company TechCorp
        company.setName("TechCorp");
        company.setEmail("techcorp@gmail.com");
        
        // Execute application creation
        boolean result = customer.createApplication(company, 100, 5000.0, document);
        
        // Verify successful application creation
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
        // Setup customer C002 with lost eligibility
        customer.setName("Alice");
        customer.setSurname("Johnson");
        customer.setEmail("alice.j@example.com");
        customer.setTelephone("555-5678");
        customer.setCanApplyForIPO(false); // Customer not eligible
        customer.setApplications(new ArrayList<>());
        
        // Setup company BioMed
        company.setName("BioMed");
        company.setEmail("biomed@gmail.com");
        
        // Execute application creation
        boolean result = customer.createApplication(company, 50, 2500.0, document);
        
        // Verify application creation fails due to ineligibility
        assertFalse("Application should fail for ineligible customer", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Setup customer C003
        customer.setName("Robert");
        customer.setSurname("Chen");
        customer.setEmail("r.chen@example.com");
        customer.setTelephone("555-9012");
        customer.setCanApplyForIPO(true);
        customer.setApplications(new ArrayList<>());
        
        // Setup company GreenEnergy
        company.setName("GreenEnergy");
        company.setEmail("greenenergy@gmail.com");
        
        // Create and approve first application
        boolean firstResult = customer.createApplication(company, 10, 300.0, document);
        assertTrue("First application should be created successfully", firstResult);
        
        Application firstApp = customer.getApplications().get(0);
        boolean approvalResult = firstApp.approve();
        assertTrue("First application should be approved successfully", approvalResult);
        assertEquals("First application status should be APPROVAL", ApplicationStatus.APPROVAL, firstApp.getStatus());
        
        // Attempt to create second application for same company
        boolean secondResult = customer.createApplication(company, 10, 300.0, document);
        
        // Verify second application creation fails due to duplicate approved application
        assertFalse("Second application should fail due to existing approved application", secondResult);
        assertEquals("Customer should still have only 1 application", 1, customer.getApplications().size());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Setup customer C004
        customer.setName("Emma");
        customer.setSurname("Davis");
        customer.setEmail("emma.d@example.com");
        customer.setTelephone("555-3456");
        customer.setCanApplyForIPO(true);
        customer.setApplications(new ArrayList<>());
        
        // Setup company AutoFuture
        company.setName("AutoFuture");
        company.setEmail("autofuture@gmail.com");
        
        // Execute application creation with null document
        boolean result = customer.createApplication(company, 25, 1000.0, null);
        
        // Verify application creation fails due to missing document
        assertFalse("Application should fail when document is null", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Setup customer C005
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@example.com");
        customer.setTelephone("555-7890");
        customer.setCanApplyForIPO(true);
        customer.setApplications(new ArrayList<>());
        
        // Setup company NanoChip
        company.setName("NanoChip");
        company.setEmail("nanotech@gmail.com");
        
        // Execute application creation with 0 shares
        boolean result = customer.createApplication(company, 0, 0.0, document);
        
        // Verify application creation fails due to zero shares
        assertFalse("Application should fail when shares are zero", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Setup customer C006
        customer.setName("Sophia");
        customer.setSurname("Martinez");
        customer.setEmail("s.m@example.com");
        customer.setTelephone("555-2345");
        customer.setCanApplyForIPO(true);
        customer.setApplications(new ArrayList<>());
        
        // Setup company CloudServ
        company.setName("CloudServ");
        company.setEmail("cloudserv@gmail.com");
        
        // Execute application creation with negative shares
        boolean result = customer.createApplication(company, -5, -200.0, document);
        
        // Verify application creation fails due to negative shares
        assertFalse("Application should fail when shares are negative", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
}