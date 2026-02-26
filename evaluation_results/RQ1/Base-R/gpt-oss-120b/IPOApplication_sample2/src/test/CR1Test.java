import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private IPOService ipoService;
    private EmailService emailService;
    
    @Before
    public void setUp() {
        ipoService = new IPOService();
        emailService = new EmailService();
    }
    
    @Test
    public void testCase1_standardEligibleSubmission() {
        // Setup: Create eligible customer with no prior approved applications
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Smith");
        customer.setEmail("john.smith@example.com");
        customer.setTelephone("555-1234");
        customer.setRetail(true);
        customer.setLocked(false);
        customer.setFailedAttempts(0);
        
        Company company = new Company();
        company.setName("TechCorp");
        
        byte[] document = new byte[]{'A'};
        int shares = 100;
        double amount = 5000.0;
        
        // Execute: Create application
        boolean result = ipoService.createApplication(customer, company, shares, amount, document);
        
        // Verify: Application should be created successfully
        assertTrue("Application should be created successfully for eligible customer", result);
        assertEquals("Customer should have one application", 1, customer.getApplications().size());
        
        IPOApplication createdApp = customer.getApplications().get(0);
        assertEquals("Application should be for correct customer", customer, createdApp.getCustomer());
        assertEquals("Application should be for correct company", company, createdApp.getCompany());
        assertEquals("Shares should match", shares, createdApp.getNumberOfShares());
        assertEquals("Amount should match", amount, createdApp.getAmount(), 0.001);
        assertArrayEquals("Document should match", document, createdApp.getDocument());
        assertEquals("Status should be pending", ApplicationStatus.PENDING, createdApp.getStatus());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Setup: Create ineligible customer (locked due to failed attempts)
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        customer.setEmail("alice.j@example.com");
        customer.setTelephone("555-5678");
        customer.setRetail(true);
        customer.setLocked(true); // Customer is locked/restricted
        customer.setFailedAttempts(3);
        
        Company company = new Company();
        company.setName("BioMed");
        
        byte[] document = new byte[]{'B'};
        int shares = 50;
        double amount = 2500.0;
        
        // Execute: Attempt to create application
        boolean result = ipoService.createApplication(customer, company, shares, amount, document);
        
        // Verify: Application should be rejected due to customer ineligibility
        assertFalse("Application should fail for ineligible customer", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Setup: Create customer with existing approved application for same company
        Customer customer = new Customer();
        customer.setName("Robert");
        customer.setSurname("Chen");
        customer.setEmail("r.chen@example.com");
        customer.setTelephone("555-9012");
        customer.setRetail(true);
        customer.setLocked(false);
        customer.setFailedAttempts(0);
        
        Company company = new Company();
        company.setName("GreenEnergy");
        
        // Create and approve first application
        IPOApplication firstApp = new IPOApplication();
        firstApp.setCustomer(customer);
        firstApp.setCompany(company);
        firstApp.setNumberOfShares(10);
        firstApp.setAmount(300.0);
        firstApp.setDocument(new byte[]{'G'});
        firstApp.setStatus(ApplicationStatus.APPROVED);
        customer.addApplication(firstApp);
        
        byte[] document = new byte[]{'G'};
        int shares = 10;
        double amount = 300.0;
        
        // Execute: Attempt to create duplicate approved application
        boolean result = ipoService.createApplication(customer, company, shares, amount, document);
        
        // Verify: Second application should be rejected
        assertFalse("Application should fail due to duplicate approved application", result);
        assertEquals("Customer should still have only one application", 1, customer.getApplications().size());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Setup: Create eligible customer with no document
        Customer customer = new Customer();
        customer.setName("Emma");
        customer.setSurname("Davis");
        customer.setEmail("emma.d@example.com");
        customer.setTelephone("555-3456");
        customer.setRetail(true);
        customer.setLocked(false);
        customer.setFailedAttempts(0);
        
        Company company = new Company();
        company.setName("AutoFuture");
        
        byte[] document = null; // Missing document
        int shares = 25;
        double amount = 1000.0;
        
        // Execute: Attempt to create application without document
        boolean result = ipoService.createApplication(customer, company, shares, amount, document);
        
        // Verify: Application should be rejected due to missing document
        assertFalse("Application should fail when document is null", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Setup: Create eligible customer attempting zero shares
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@example.com");
        customer.setTelephone("555-7890");
        customer.setRetail(true);
        customer.setLocked(false);
        customer.setFailedAttempts(0);
        
        Company company = new Company();
        company.setName("NanoChip");
        
        byte[] document = new byte[]{'N'};
        int shares = 0; // Zero shares
        double amount = 0.0;
        
        // Execute: Attempt to create application with zero shares
        boolean result = ipoService.createApplication(customer, company, shares, amount, document);
        
        // Verify: Application should be rejected due to zero shares
        assertFalse("Application should fail for zero shares", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Setup: Create eligible customer attempting negative shares
        Customer customer = new Customer();
        customer.setName("Sophia");
        customer.setSurname("Martinez");
        customer.setEmail("s.m@example.com");
        customer.setTelephone("555-2345");
        customer.setRetail(true);
        customer.setLocked(false);
        customer.setFailedAttempts(0);
        
        Company company = new Company();
        company.setName("CloudServ");
        
        byte[] document = new byte[]{'C'};
        int shares = -5; // Negative shares
        double amount = -200.0;
        
        // Execute: Attempt to create application with negative shares
        boolean result = ipoService.createApplication(customer, company, shares, amount, document);
        
        // Verify: Application should be rejected due to negative shares
        assertFalse("Application should fail for negative shares", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
}