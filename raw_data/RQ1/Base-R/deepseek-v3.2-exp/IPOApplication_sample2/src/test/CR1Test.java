import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private IPOApplicationSystem system;
    private Customer eligibleCustomer;
    private Customer restrictedCustomer;
    private Customer customerWithApprovedApp;
    private Document validDocument;
    
    @Before
    public void setUp() {
        system = new IPOApplicationSystem();
        
        // Setup eligible customer (C001)
        eligibleCustomer = new Customer();
        eligibleCustomer.setName("John");
        eligibleCustomer.setSurname("Smith");
        eligibleCustomer.setEmail("john.smith@example.com");
        eligibleCustomer.setTelephone("555-1234");
        eligibleCustomer.setRestricted(false);
        eligibleCustomer.setFailedAttempts(0);
        
        // Setup restricted customer (C002)
        restrictedCustomer = new Customer();
        restrictedCustomer.setName("Alice");
        restrictedCustomer.setSurname("Johnson");
        restrictedCustomer.setEmail("alice.j@example.com");
        restrictedCustomer.setTelephone("555-5678");
        restrictedCustomer.setRestricted(true);
        restrictedCustomer.setFailedAttempts(3);
        
        // Setup customer with approved application (C003)
        customerWithApprovedApp = new Customer();
        customerWithApprovedApp.setName("Robert");
        customerWithApprovedApp.setSurname("Chen");
        customerWithApprovedApp.setEmail("r.chen@example.com");
        customerWithApprovedApp.setTelephone("555-9012");
        customerWithApprovedApp.setRestricted(false);
        customerWithApprovedApp.setFailedAttempts(0);
        
        // Create and add approved application for GreenEnergy
        IPOApplication approvedApp = new IPOApplication();
        approvedApp.setCompanyName("GreenEnergy");
        approvedApp.setNumberOfShares(10);
        approvedApp.setAmountPaid(300.0);
        approvedApp.setStatus(ApplicationStatus.APPROVED);
        approvedApp.setCustomer(customerWithApprovedApp);
        
        Document docG = new Document();
        docG.setFileName("documentG.pdf");
        approvedApp.setDocument(docG);
        
        customerWithApprovedApp.getApplications().add(approvedApp);
        
        // Setup valid document
        validDocument = new Document();
        validDocument.setFileName("allowance.pdf");
        validDocument.setDocumentType("LegalAllowance");
        validDocument.setContent(new byte[]{1, 2, 3});
    }
    
    @Test
    public void testCase1_standardEligibleSubmission() {
        // Test standard eligible customer submission
        boolean result = system.createIPOApplication(
            eligibleCustomer, 
            "TechCorp", 
            100, 
            5000.0, 
            validDocument
        );
        
        // Verify application was created successfully
        assertTrue("Application should be created successfully for eligible customer", result);
        assertEquals("Customer should have 1 application", 1, eligibleCustomer.getApplications().size());
        
        IPOApplication createdApp = eligibleCustomer.getApplications().get(0);
        assertEquals("Company name should match", "TechCorp", createdApp.getCompanyName());
        assertEquals("Number of shares should match", 100, createdApp.getNumberOfShares());
        assertEquals("Amount paid should match", 5000.0, createdApp.getAmountPaid(), 0.001);
        assertEquals("Status should be pending", ApplicationStatus.PENDING, createdApp.getStatus());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Setup restricted customer with failed attempts
        Customer restrictedCustomer = new Customer();
        restrictedCustomer.setName("Alice");
        restrictedCustomer.setSurname("Johnson");
        restrictedCustomer.setEmail("alice.j@example.com");
        restrictedCustomer.setTelephone("555-5678");
        restrictedCustomer.setRestricted(true);
        restrictedCustomer.setFailedAttempts(3);
        
        // Test application creation for restricted customer
        boolean result = system.createIPOApplication(
            restrictedCustomer, 
            "BioMed", 
            50, 
            2500.0, 
            validDocument
        );
        
        // Verify application was rejected due to customer restriction
        assertFalse("Application should fail for restricted customer", result);
        assertEquals("Customer should have 0 applications", 0, restrictedCustomer.getApplications().size());
        assertEquals("Failed attempts should remain at 3", 3, restrictedCustomer.getFailedAttempts());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Test duplicate application for same company where previous was approved
        boolean result = system.createIPOApplication(
            customerWithApprovedApp, 
            "GreenEnergy", 
            10, 
            300.0, 
            validDocument
        );
        
        // Verify application was rejected due to duplicate approved application
        assertFalse("Application should fail for duplicate approved application", result);
        assertEquals("Customer should still have only 1 application", 1, customerWithApprovedApp.getApplications().size());
        assertEquals("Failed attempts should be increased", 1, customerWithApprovedApp.getFailedAttempts());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Test application creation with null document
        boolean result = system.createIPOApplication(
            eligibleCustomer, 
            "AutoFuture", 
            25, 
            1000.0, 
            null
        );
        
        // Verify application was rejected due to missing document
        assertFalse("Application should fail when document is null", result);
        assertEquals("Customer should have 0 applications", 0, eligibleCustomer.getApplications().size());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Test application creation with zero shares
        boolean result = system.createIPOApplication(
            eligibleCustomer, 
            "NanoChip", 
            0, 
            0.0, 
            validDocument
        );
        
        // Verify application was rejected due to zero shares
        assertFalse("Application should fail for zero shares", result);
        assertEquals("Customer should have 0 applications", 0, eligibleCustomer.getApplications().size());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Test application creation with negative shares
        boolean result = system.createIPOApplication(
            eligibleCustomer, 
            "CloudServ", 
            -5, 
            -200.0, 
            validDocument
        );
        
        // Verify application was rejected due to negative shares/amount
        assertFalse("Application should fail for negative shares and amount", result);
        assertEquals("Customer should have 0 applications", 0, eligibleCustomer.getApplications().size());
    }
}