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
        // Setup customer details
        customer.setName("John");
        customer.setSurname("Smith");
        customer.setEmail("john.smith@example.com");
        customer.setTelephone("555-1234");
        customer.setCanApplyForIPO(true);
        
        // Setup company details
        company.setName("TechCorp");
        company.setEmail("techcorp@gmail.com");
        
        // Customer has no previously approved applications for TechCorp (default state)
        
        // Execute the application creation
        boolean result = customer.createApplication(company, 100, 5000.0, document);
        
        // Verify the application was created successfully
        assertTrue("Application should be created successfully for eligible customer", result);
        assertEquals("Customer should have 1 application", 1, customer.getApplications().size());
        assertEquals("Application status should be PENDING", ApplicationStatus.PENDING, customer.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Setup customer details
        customer.setName("Alice");
        customer.setSurname("Johnson");
        customer.setEmail("alice.j@example.com");
        customer.setTelephone("555-5678");
        customer.setCanApplyForIPO(false); // Customer is not eligible
        
        // Setup company details
        company.setName("BioMed");
        company.setEmail("biomed@gmail.com");
        
        // Customer has no existing applications for BioMed (default state)
        
        // Execute the application creation
        boolean result = customer.createApplication(company, 50, 2500.0, document);
        
        // Verify the application was not created due to ineligibility
        assertFalse("Application should not be created for ineligible customer", result);
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Setup customer details
        customer.setName("Robert");
        customer.setSurname("Chen");
        customer.setEmail("r.chen@example.com");
        customer.setTelephone("555-9012");
        customer.setCanApplyForIPO(true);
        
        // Setup company details
        company.setName("GreenEnergy");
        company.setEmail("greenenergy@gmail.com");
        
        // Create and approve first application for GreenEnergy
        Application existingApp = new Application();
        existingApp.setShare(10);
        existingApp.setAmountOfMoney(300.0);
        existingApp.setStatus(ApplicationStatus.APPROVED);
        existingApp.setCustomer(customer);
        existingApp.setCompany(company);
        existingApp.setAllowance(document);
        
        customer.getApplications().add(existingApp);
        
        // Attempt to create another application for the same company
        boolean result = customer.createApplication(company, 10, 300.0, document);
        
        // Verify the second application was not created due to duplicate approved application
        assertFalse("Application should not be created due to existing approved application", result);
        assertEquals("Customer should still have only 1 application", 1, customer.getApplications().size());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Setup customer details
        customer.setName("Emma");
        customer.setSurname("Davis");
        customer.setEmail("emma.d@example.com");
        customer.setTelephone("555-3456");
        customer.setCanApplyForIPO(true);
        
        // Setup company details
        company.setName("AutoFuture");
        company.setEmail("autofuture@gmail.com");
        
        // Customer has no prior applications for AutoFuture (default state)
        
        // Execute the application creation with null document
        boolean result = customer.createApplication(company, 25, 1000.0, null);
        
        // Verify the application was not created due to missing document
        assertFalse("Application should not be created without document", result);
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Setup customer details
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@example.com");
        customer.setTelephone("555-7890");
        customer.setCanApplyForIPO(true);
        
        // Setup company details
        company.setName("NanoChip");
        company.setEmail("nanotech@gmail.com");
        
        // Customer has no existing applications for NanoChip (default state)
        
        // Execute the application creation with 0 shares
        boolean result = customer.createApplication(company, 0, 0.0, document);
        
        // Verify the application was not created due to invalid share count
        assertFalse("Application should not be created with 0 shares", result);
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Setup customer details
        customer.setName("Sophia");
        customer.setSurname("Martinez");
        customer.setEmail("s.m@example.com");
        customer.setTelephone("555-2345");
        customer.setCanApplyForIPO(true);
        
        // Setup company details
        company.setName("CloudServ");
        company.setEmail("cloudserv@gmail.com");
        
        // Customer has no prior applications for CloudServ (default state)
        
        // Execute the application creation with negative shares
        boolean result = customer.createApplication(company, -5, -200.0, document);
        
        // Verify the application was not created due to negative values
        assertFalse("Application should not be created with negative shares/amount", result);
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
    }
}