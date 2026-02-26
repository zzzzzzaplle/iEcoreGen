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
        // Setup
        customer.setName("John");
        customer.setSurname("Smith");
        customer.setEmail("john.smith@example.com");
        customer.setTelephone("555-1234");
        customer.setCanApplyForIPO(true);
        
        company.setName("TechCorp");
        company.setEmail("techcorp@gmail.com");
        
        // Execute
        boolean result = customer.createApplication(company, 100, 5000.0, document);
        
        // Verify
        assertTrue("Application should be created successfully for eligible customer", result);
        assertEquals("Customer should have 1 application", 1, customer.getApplications().size());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Setup
        customer.setName("Alice");
        customer.setSurname("Johnson");
        customer.setEmail("alice.j@example.com");
        customer.setTelephone("555-5678");
        customer.setCanApplyForIPO(false); // Customer not eligible
        
        company.setName("BioMed");
        company.setEmail("biomed@gmail.com");
        
        // Execute
        boolean result = customer.createApplication(company, 50, 2500.0, document);
        
        // Verify
        assertFalse("Application should fail for ineligible customer", result);
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Setup
        customer.setName("Robert");
        customer.setSurname("Chen");
        customer.setEmail("r.chen@example.com");
        customer.setTelephone("555-9012");
        customer.setCanApplyForIPO(true);
        
        company.setName("GreenEnergy");
        company.setEmail("greenenergy@gmail.com");
        
        // Create first application and approve it
        Application firstApp = new Application();
        firstApp.setShare(10);
        firstApp.setAmountOfMoney(300.0);
        firstApp.setStatus(ApplicationStatus.APPROVED);
        firstApp.setCustomer(customer);
        firstApp.setCompany(company);
        firstApp.setAllowance(document);
        customer.getApplications().add(firstApp);
        
        // Execute - try to create another application for same company
        boolean result = customer.createApplication(company, 10, 300.0, document);
        
        // Verify
        assertFalse("Should not allow duplicate approved application for same company", result);
        assertEquals("Customer should still have only 1 application", 1, customer.getApplications().size());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Setup
        customer.setName("Emma");
        customer.setSurname("Davis");
        customer.setEmail("emma.d@example.com");
        customer.setTelephone("555-3456");
        customer.setCanApplyForIPO(true);
        
        company.setName("AutoFuture");
        company.setEmail("autofuture@gmail.com");
        
        // Execute with null document
        boolean result = customer.createApplication(company, 25, 1000.0, null);
        
        // Verify
        assertFalse("Application should fail when document is null", result);
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Setup
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@example.com");
        customer.setTelephone("555-7890");
        customer.setCanApplyForIPO(true);
        
        company.setName("NanoChip");
        company.setEmail("nanotech@gmail.com");
        
        // Execute with 0 shares
        boolean result = customer.createApplication(company, 0, 0.0, document);
        
        // Verify
        assertFalse("Application should fail with 0 shares", result);
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Setup
        customer.setName("Sophia");
        customer.setSurname("Martinez");
        customer.setEmail("s.m@example.com");
        customer.setTelephone("555-2345");
        customer.setCanApplyForIPO(true);
        
        company.setName("CloudServ");
        company.setEmail("cloudserv@gmail.com");
        
        // Execute with negative shares
        boolean result = customer.createApplication(company, -5, -200.0, document);
        
        // Verify
        assertFalse("Application should fail with negative shares", result);
        assertEquals("Customer should have 0 applications", 0, customer.getApplications().size());
    }
}