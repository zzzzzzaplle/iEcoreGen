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
        // Setup customer C001
        customer.setName("John");
        customer.setSurname("Smith");
        customer.setEmail("john.smith@example.com");
        customer.setTelephone("555-1234");
        customer.setCanApplyForIPO(true);
        
        // Setup company TechCorp
        company.setName("TechCorp");
        company.setEmail("techcorp@gmail.com");
        
        // Execute application creation
        boolean result = customer.createApplication(company, 100, 5000.0, document);
        
        // Verify successful application creation
        assertTrue("Application should be created successfully for eligible customer", result);
        assertEquals("Customer should have 1 application", 1, customer.getApplications().size());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Setup customer C002 with IPO eligibility disabled
        customer.setName("Alice");
        customer.setSurname("Johnson");
        customer.setEmail("alice.j@example.com");
        customer.setTelephone("555-5678");
        customer.setCanApplyForIPO(false); // Customer not eligible
        
        // Setup company BioMed
        company.setName("BioMed");
        company.setEmail("biomed@gmail.com");
        
        // Execute application creation
        boolean result = customer.createApplication(company, 50, 2500.0, document);
        
        // Verify application creation fails due to ineligibility
        assertFalse("Application should not be created for ineligible customer", result);
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Setup customer C003
        customer.setName("Robert");
        customer.setSurname("Chen");
        customer.setEmail("r.chen@example.com");
        customer.setTelephone("555-9012");
        customer.setCanApplyForIPO(true);
        
        // Setup company GreenEnergy
        company.setName("GreenEnergy");
        company.setEmail("greenenergy@gmail.com");
        
        // Create first application and approve it
        boolean firstResult = customer.createApplication(company, 10, 300.0, document);
        assertTrue("First application should be created successfully", firstResult);
        
        // Approve the first application
        Application firstApp = customer.getApplications().get(0);
        firstApp.setStatus(ApplicationStatus.APPROVAL);
        
        // Attempt to create second application for same company
        boolean secondResult = customer.createApplication(company, 10, 300.0, document);
        
        // Verify second application creation fails due to existing approved application
        assertFalse("Second application should not be created due to existing approved application", secondResult);
        assertEquals("Customer should have only 1 application", 1, customer.getApplications().size());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCase4_missingDocument() {
        // Setup customer C004
        customer.setName("Emma");
        customer.setSurname("Davis");
        customer.setEmail("emma.d@example.com");
        customer.setTelephone("555-3456");
        customer.setCanApplyForIPO(true);
        
        // Setup company AutoFuture
        company.setName("AutoFuture");
        company.setEmail("autofuture@gmail.com");
        
        // Attempt to create application with null document - should throw exception
        customer.createApplication(company, 25, 1000.0, null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCase5_zeroShareApplication() {
        // Setup customer C005
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@example.com");
        customer.setTelephone("555-7890");
        customer.setCanApplyForIPO(true);
        
        // Setup company NanoChip
        company.setName("NanoChip");
        company.setEmail("nanotech@gmail.com");
        
        // Attempt to create application with 0 shares - should throw exception
        customer.createApplication(company, 0, 0.0, document);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCase6_negativeShareCount() {
        // Setup customer C006
        customer.setName("Sophia");
        customer.setSurname("Martinez");
        customer.setEmail("s.m@example.com");
        customer.setTelephone("555-2345");
        customer.setCanApplyForIPO(true);
        
        // Setup company CloudServ
        company.setName("CloudServ");
        company.setEmail("cloudserv@gmail.com");
        
        // Attempt to create application with negative shares - should throw exception
        customer.createApplication(company, -5, -200.0, document);
    }
}