import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private Customer eligibleCustomer;
    private Customer ineligibleCustomer;
    private Company techCorp;
    private Company bioMed;
    private Company greenEnergy;
    private Company autoFuture;
    private Company nanoChip;
    private Company cloudServ;
    private Document documentA;
    private Document documentB;
    private Document documentG;
    private Document documentN;
    private Document documentC;
    
    @Before
    public void setUp() {
        // Setup eligible customer C001
        eligibleCustomer = new Customer();
        eligibleCustomer.setName("John");
        eligibleCustomer.setSurname("Smith");
        eligibleCustomer.setEmail("john.smith@example.com");
        eligibleCustomer.setTelephone("555-1234");
        eligibleCustomer.setCanApplyForIPO(true);
        
        // Setup ineligible customer C002
        ineligibleCustomer = new Customer();
        ineligibleCustomer.setName("Alice");
        ineligibleCustomer.setSurname("Johnson");
        ineligibleCustomer.setEmail("alice.j@example.com");
        ineligibleCustomer.setTelephone("555-5678");
        ineligibleCustomer.setCanApplyForIPO(false);
        
        // Setup companies
        techCorp = new Company("TechCorp", "techcorp@gmail.com");
        bioMed = new Company("BioMed", "biomed@gmail.com");
        greenEnergy = new Company("GreenEnergy", "greenenergy@gmail.com");
        autoFuture = new Company("AutoFuture", "autofuture@gmail.com");
        nanoChip = new Company("NanoChip", "nanotech@gmail.com");
        cloudServ = new Company("CloudServ", "cloudserv@gmail.com");
        
        // Setup documents
        documentA = new Document();
        documentB = new Document();
        documentG = new Document();
        documentN = new Document();
        documentC = new Document();
    }
    
    @Test
    public void testCase1_standardEligibleSubmission() {
        // Test Case 1: Standard eligible submission
        // Input: An eligible customer applies for IPO with valid parameters
        boolean result = eligibleCustomer.createApplication(techCorp, 100, 5000.0, documentA);
        
        // Expected Output: True (application is successfully created)
        assertTrue("Application should be created successfully for eligible customer", result);
        
        // Verify application was actually created
        assertEquals("Customer should have 1 pending application", 0, eligibleCustomer.getApplicationCount());
        assertEquals("Application should be in pending status", 
                     ApplicationStatus.PENDING, 
                     eligibleCustomer.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Test Case 2: Customer not eligible
        // Input: Ineligible customer attempts to apply for IPO
        boolean result = ineligibleCustomer.createApplication(bioMed, 50, 2500.0, documentB);
        
        // Expected Output: False
        assertFalse("Application should not be created for ineligible customer", result);
        
        // Verify no application was created
        assertEquals("Ineligible customer should have no applications", 0, ineligibleCustomer.getApplicationCount());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Test Case 3: Duplicate approved application
        // Setup: Create and approve first application
        Customer customerC003 = new Customer();
        customerC003.setName("Robert");
        customerC003.setSurname("Chen");
        customerC003.setEmail("r.chen@example.com");
        customerC003.setTelephone("555-9012");
        customerC003.setCanApplyForIPO(true);
        
        // Create first application and approve it
        boolean firstResult = customerC003.createApplication(greenEnergy, 10, 300.0, documentG);
        assertTrue("First application should be created successfully", firstResult);
        
        Application firstApp = customerC003.getApplications().get(0);
        firstApp.approve(); // Approve the application
        
        // Input: Submit another application to the same company
        boolean secondResult = customerC003.createApplication(greenEnergy, 10, 300.0, documentG);
        
        // Expected Output: False (only one approved application per company allowed)
        assertFalse("Second application to same company should be rejected", secondResult);
        
        // Verify only one application exists
        assertEquals("Customer should have only 1 approved application", 1, customerC003.getApplicationCount());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Test Case 4: Missing document
        // Input: Eligible customer applies but forgets to upload document
        Customer customerC004 = new Customer();
        customerC004.setName("Emma");
        customerC004.setSurname("Davis");
        customerC004.setEmail("emma.d@example.com");
        customerC004.setTelephone("555-3456");
        customerC004.setCanApplyForIPO(true);
        
        boolean result = customerC004.createApplication(autoFuture, 25, 1000.0, null);
        
        // Expected Output: False (document upload is mandatory)
        assertFalse("Application should be rejected when document is null", result);
        
        // Verify no application was created
        assertEquals("Customer should have no applications when document is null", 0, customerC004.getApplicationCount());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Test Case 5: Zero-share application
        // Input: Eligible customer applies for 0 shares
        Customer customerC005 = new Customer();
        customerC005.setName("James");
        customerC005.setSurname("Wilson");
        customerC005.setEmail("j.wilson@example.com");
        customerC005.setTelephone("555-7890");
        customerC005.setCanApplyForIPO(true);
        
        boolean result = customerC005.createApplication(nanoChip, 0, 0.0, documentN);
        
        // Expected Output: False
        assertFalse("Application should be rejected for 0 shares", result);
        
        // Verify no application was created
        assertEquals("Customer should have no applications for 0 shares", 0, customerC005.getApplicationCount());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Test Case 6: Negative share count
        // Input: Eligible customer applies for negative shares
        Customer customerC006 = new Customer();
        customerC006.setName("Sophia");
        customerC006.setSurname("Martinez");
        customerC006.setEmail("s.m@example.com");
        customerC006.setTelephone("555-2345");
        customerC006.setCanApplyForIPO(true);
        
        boolean result = customerC006.createApplication(cloudServ, -5, -200.0, documentC);
        
        // Expected Output: False (negative shares/amount are invalid)
        assertFalse("Application should be rejected for negative shares", result);
        
        // Verify no application was created
        assertEquals("Customer should have no applications for negative shares", 0, customerC006.getApplicationCount());
    }
}