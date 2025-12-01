import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private Customer customerC001;
    private Customer customerC002;
    private Customer customerC003;
    private Customer customerC004;
    private Customer customerC005;
    private Customer customerC006;
    
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
        // Initialize customers
        customerC001 = new Customer();
        customerC001.setName("John");
        customerC001.setSurname("Smith");
        customerC001.setEmail("john.smith@example.com");
        customerC001.setTelephone("555-1234");
        customerC001.setCanApplyForIPO(true);
        
        customerC002 = new Customer();
        customerC002.setName("Alice");
        customerC002.setSurname("Johnson");
        customerC002.setEmail("alice.j@example.com");
        customerC002.setTelephone("555-5678");
        customerC002.setCanApplyForIPO(false); // Not eligible
        
        customerC003 = new Customer();
        customerC003.setName("Robert");
        customerC003.setSurname("Chen");
        customerC003.setEmail("r.chen@example.com");
        customerC003.setTelephone("555-9012");
        customerC003.setCanApplyForIPO(true);
        
        customerC004 = new Customer();
        customerC004.setName("Emma");
        customerC004.setSurname("Davis");
        customerC004.setEmail("emma.d@example.com");
        customerC004.setTelephone("555-3456");
        customerC004.setCanApplyForIPO(true);
        
        customerC005 = new Customer();
        customerC005.setName("James");
        customerC005.setSurname("Wilson");
        customerC005.setEmail("j.wilson@example.com");
        customerC005.setTelephone("555-7890");
        customerC005.setCanApplyForIPO(true);
        
        customerC006 = new Customer();
        customerC006.setName("Sophia");
        customerC006.setSurname("Martinez");
        customerC006.setEmail("s.m@example.com");
        customerC006.setTelephone("555-2345");
        customerC006.setCanApplyForIPO(true);
        
        // Initialize companies
        techCorp = new Company("TechCorp", "techcorp@gmail.com");
        bioMed = new Company("BioMed", "biomed@gmail.com");
        greenEnergy = new Company("GreenEnergy", "greenenergy@gmail.com");
        autoFuture = new Company("AutoFuture", "autofuture@gmail.com");
        nanoChip = new Company("NanoChip", "nanotech@gmail.com");
        cloudServ = new Company("CloudServ", "cloudserv@gmail.com");
        
        // Initialize documents
        documentA = new Document();
        documentB = new Document();
        documentG = new Document();
        documentN = new Document();
        documentC = new Document();
    }
    
    @Test
    public void testCase1_standardEligibleSubmission() {
        // Test standard eligible IPO application submission
        boolean result = customerC001.createApplication(techCorp, 100, 5000.0, documentA);
        assertTrue("Application should be created successfully for eligible customer", result);
        assertEquals("Customer should have exactly 1 application", 1, customerC001.getApplications().size());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Test IPO application when customer is not eligible
        boolean result = customerC002.createApplication(bioMed, 50, 2500.0, documentB);
        assertFalse("Application should fail for ineligible customer", result);
        assertEquals("Customer should have no applications", 0, customerC002.getApplications().size());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Test duplicate approved application for same company
        // First application - should succeed
        boolean firstResult = customerC003.createApplication(greenEnergy, 10, 300.0, documentG);
        assertTrue("First application should be created successfully", firstResult);
        
        // Get the first application and approve it
        Application firstApp = customerC003.getApplications().get(0);
        boolean approved = firstApp.approve();
        assertTrue("First application should be approved", approved);
        
        // Second application for same company - should fail
        boolean secondResult = customerC003.createApplication(greenEnergy, 10, 300.0, documentG);
        assertFalse("Second application should fail due to duplicate approved application", secondResult);
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Test IPO application without uploading document
        boolean result = customerC004.createApplication(autoFuture, 25, 1000.0, null);
        assertFalse("Application should fail when document is null", result);
        assertEquals("Customer should have no applications", 0, customerC004.getApplications().size());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Test IPO application with zero shares
        boolean result = customerC005.createApplication(nanoChip, 0, 0.0, documentN);
        assertFalse("Application should fail with zero shares", result);
        assertEquals("Customer should have no applications", 0, customerC005.getApplications().size());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Test IPO application with negative shares
        boolean result = customerC006.createApplication(cloudServ, -5, -200.0, documentC);
        assertFalse("Application should fail with negative shares", result);
        assertEquals("Customer should have no applications", 0, customerC006.getApplications().size());
    }
}