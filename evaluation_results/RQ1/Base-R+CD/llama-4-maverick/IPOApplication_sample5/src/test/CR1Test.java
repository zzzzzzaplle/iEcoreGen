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
    private Document docA;
    private Document docB;
    private Document docG;
    private Document docN;
    private Document docC;
    
    @Before
    public void setUp() {
        // Setup eligible customer (C001)
        eligibleCustomer = new Customer("John", "Smith", "john.smith@example.com", "555-1234", true);
        
        // Setup ineligible customer (C002)
        ineligibleCustomer = new Customer("Alice", "Johnson", "alice.j@example.com", "555-5678", false);
        
        // Setup companies
        techCorp = new Company("TechCorp", "techcorp@gmail.com");
        bioMed = new Company("BioMed", "biomed@gmail.com");
        greenEnergy = new Company("GreenEnergy", "greenenergy@gmail.com");
        autoFuture = new Company("AutoFuture", "autofuture@gmail.com");
        nanoChip = new Company("NanoChip", "nanotech@gmail.com");
        cloudServ = new Company("CloudServ", "cloudserv@gmail.com");
        
        // Setup documents
        docA = new Document();
        docB = new Document();
        docG = new Document();
        docN = new Document();
        docC = new Document();
    }
    
    @Test
    public void testCase1_StandardEligibleSubmission() {
        // Test standard eligible submission - should return true
        boolean result = eligibleCustomer.createApplication(techCorp, 100, 5000.0, docA);
        assertTrue("Application should be created successfully for eligible customer", result);
    }
    
    @Test
    public void testCase2_CustomerNotEligible() {
        // Test ineligible customer - should return false
        boolean result = ineligibleCustomer.createApplication(bioMed, 50, 2500.0, docB);
        assertFalse("Application should not be created for ineligible customer", result);
    }
    
    @Test
    public void testCase3_DuplicateApprovedApplication() {
        // Setup: Create and approve first application for GreenEnergy
        boolean firstResult = eligibleCustomer.createApplication(greenEnergy, 10, 300.0, docG);
        assertTrue("First application should be created successfully", firstResult);
        
        // Get the created application and approve it
        Application firstApp = eligibleCustomer.getApplications().get(0);
        boolean approved = firstApp.approve();
        assertTrue("First application should be approved", approved);
        
        // Test duplicate application for same company - should return false
        boolean secondResult = eligibleCustomer.createApplication(greenEnergy, 10, 300.0, docG);
        assertFalse("Second application for same company should not be created", secondResult);
    }
    
    @Test
    public void testCase4_MissingDocument() {
        // Test application with null document - should return false
        boolean result = eligibleCustomer.createApplication(autoFuture, 25, 1000.0, null);
        assertFalse("Application should not be created without document", result);
    }
    
    @Test
    public void testCase5_ZeroShareApplication() {
        // Test application with zero shares - should return false
        boolean result = eligibleCustomer.createApplication(nanoChip, 0, 0.0, docN);
        assertFalse("Application should not be created with zero shares", result);
    }
    
    @Test
    public void testCase6_NegativeShareCount() {
        // Test application with negative shares - should return false
        boolean result = eligibleCustomer.createApplication(cloudServ, -5, -200.0, docC);
        assertFalse("Application should not be created with negative shares", result);
    }
}