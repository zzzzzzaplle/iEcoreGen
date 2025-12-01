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
        // Test standard eligible submission
        boolean result = eligibleCustomer.createApplication(techCorp, 100, 5000.0, documentA);
        
        assertTrue("Application should be successfully created for eligible customer", result);
        assertEquals("Customer should have 1 application", 1, eligibleCustomer.getApplicationCount());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Test customer not eligible
        boolean result = ineligibleCustomer.createApplication(bioMed, 50, 2500.0, documentB);
        
        assertFalse("Application should fail for ineligible customer", result);
        assertEquals("Customer should have 0 applications", 0, ineligibleCustomer.getApplicationCount());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Setup customer C003
        Customer customerC003 = new Customer();
        customerC003.setName("Robert");
        customerC003.setSurname("Chen");
        customerC003.setEmail("r.chen@example.com");
        customerC003.setTelephone("555-9012");
        customerC003.setCanApplyForIPO(true);
        
        // Create and approve first application
        boolean firstResult = customerC003.createApplication(greenEnergy, 10, 300.0, documentG);
        assertTrue("First application should be created successfully", firstResult);
        
        // Get the application and approve it
        Application firstApp = customerC003.getApplications().get(0);
        boolean approvalResult = firstApp.approve();
        assertTrue("First application should be approved successfully", approvalResult);
        
        // Attempt to create duplicate application
        boolean duplicateResult = customerC003.createApplication(greenEnergy, 10, 300.0, documentG);
        
        assertFalse("Duplicate application should fail", duplicateResult);
        assertEquals("Customer should have only 1 application", 1, customerC003.getApplicationCount());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Test missing document (null document)
        boolean result = eligibleCustomer.createApplication(autoFuture, 25, 1000.0, null);
        
        assertFalse("Application should fail when document is null", result);
        assertEquals("Customer should have 0 applications", 0, eligibleCustomer.getApplicationCount());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Test zero share application
        boolean result = eligibleCustomer.createApplication(nanoChip, 0, 0.0, documentN);
        
        assertFalse("Application should fail for zero shares", result);
        assertEquals("Customer should have 0 applications", 0, eligibleCustomer.getApplicationCount());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Test negative share count
        boolean result = eligibleCustomer.createApplication(cloudServ, -5, -200.0, documentC);
        
        assertFalse("Application should fail for negative shares", result);
        assertEquals("Customer should have 0 applications", 0, eligibleCustomer.getApplicationCount());
    }
}