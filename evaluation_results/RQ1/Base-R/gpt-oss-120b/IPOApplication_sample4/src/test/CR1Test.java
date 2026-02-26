import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private IPOSystem ipoSystem;
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
        // Initialize the IPO system
        ipoSystem = new IPOSystem();
        
        // Create customers
        customerC001 = new Customer();
        customerC001.setName("John");
        customerC001.setSurname("Smith");
        customerC001.setEmail("john.smith@example.com");
        customerC001.setTelephone("555-1234");
        customerC001.setEligible(true);
        
        customerC002 = new Customer();
        customerC002.setName("Alice");
        customerC002.setSurname("Johnson");
        customerC002.setEmail("alice.j@example.com");
        customerC002.setTelephone("555-5678");
        customerC002.setEligible(false); // Not eligible
        
        customerC003 = new Customer();
        customerC003.setName("Robert");
        customerC003.setSurname("Chen");
        customerC003.setEmail("r.chen@example.com");
        customerC003.setTelephone("555-9012");
        customerC003.setEligible(true);
        
        customerC004 = new Customer();
        customerC004.setName("Emma");
        customerC004.setSurname("Davis");
        customerC004.setEmail("emma.d@example.com");
        customerC004.setTelephone("555-3456");
        customerC004.setEligible(true);
        
        customerC005 = new Customer();
        customerC005.setName("James");
        customerC005.setSurname("Wilson");
        customerC005.setEmail("j.wilson@example.com");
        customerC005.setTelephone("555-7890");
        customerC005.setEligible(true);
        
        customerC006 = new Customer();
        customerC006.setName("Sophia");
        customerC006.setSurname("Martinez");
        customerC006.setEmail("s.m@example.com");
        customerC006.setTelephone("555-2345");
        customerC006.setEligible(true);
        
        // Create companies
        techCorp = new Company();
        techCorp.setName("TechCorp");
        
        bioMed = new Company();
        bioMed.setName("BioMed");
        
        greenEnergy = new Company();
        greenEnergy.setName("GreenEnergy");
        
        autoFuture = new Company();
        autoFuture.setName("AutoFuture");
        
        nanoChip = new Company();
        nanoChip.setName("NanoChip");
        
        cloudServ = new Company();
        cloudServ.setName("CloudServ");
        
        // Create documents
        documentA = new Document();
        documentA.setFileName("A");
        
        documentB = new Document();
        documentB.setFileName("B");
        
        documentG = new Document();
        documentG.setFileName("G");
        
        documentN = new Document();
        documentN.setFileName("N");
        
        documentC = new Document();
        documentC.setFileName("C");
    }
    
    @Test
    public void testCase1_standardEligibleSubmission() {
        // Test Case 1: "Standard eligible submission"
        // Input: An eligible customer applies for an IPO with valid parameters
        // Expected Output: True (application is successfully created)
        
        // Execute the application creation
        boolean result = ipoSystem.createIPOApplication(customerC001, techCorp, 100, 5000.0, documentA);
        
        // Verify the result is true
        assertTrue("Application should be successfully created for eligible customer", result);
        
        // Verify the application was added to customer's applications
        assertEquals("Customer should have 1 application", 1, customerC001.getApplications().size());
        
        // Verify the application was added to system's applications
        assertEquals("System should have 1 application", 1, ipoSystem.getAllApplications().size());
        
        // Verify application details
        IPOApplication app = customerC001.getApplications().get(0);
        assertEquals("Customer should match", customerC001, app.getCustomer());
        assertEquals("Company should match", techCorp, app.getCompany());
        assertEquals("Shares should match", 100, app.getShares());
        assertEquals("Amount should match", 5000.0, app.getAmount(), 0.001);
        assertEquals("Document should match", documentA, app.getDocument());
        assertEquals("Status should be PENDING", ApplicationStatus.PENDING, app.getStatus());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Test Case 2: "Customer not eligible"
        // Input: Ineligible customer attempts to apply for an IPO
        // Expected Output: False
        
        // Execute the application creation with ineligible customer
        boolean result = ipoSystem.createIPOApplication(customerC002, bioMed, 50, 2500.0, documentB);
        
        // Verify the result is false
        assertFalse("Application should fail for ineligible customer", result);
        
        // Verify no applications were created
        assertEquals("Customer should have 0 applications", 0, customerC002.getApplications().size());
        assertEquals("System should have 0 applications", 0, ipoSystem.getAllApplications().size());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Test Case 3: "Duplicate approved application"
        // Input: Customer submits another application to same company after approval
        // Expected Output: False (only one approved application per company allowed)
        
        // Step 1: Create and approve first application
        boolean firstResult = ipoSystem.createIPOApplication(customerC003, greenEnergy, 10, 300.0, documentG);
        assertTrue("First application should be created successfully", firstResult);
        
        // Approve the first application
        boolean approvalResult = ipoSystem.approveApplication(customerC003, "GreenEnergy");
        assertTrue("First application should be approved successfully", approvalResult);
        
        // Step 2: Attempt to create second application for same company
        boolean secondResult = ipoSystem.createIPOApplication(customerC003, greenEnergy, 15, 450.0, documentG);
        
        // Verify the result is false
        assertFalse("Second application should fail due to duplicate approved application", secondResult);
        
        // Verify only one application exists for the customer
        assertEquals("Customer should have only 1 application", 1, customerC003.getApplications().size());
        
        // Verify the first application is still approved
        assertEquals("First application should remain APPROVED", 
                     ApplicationStatus.APPROVED, customerC003.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Test Case 4: "Missing document"
        // Input: Eligible customer applies but forgets to upload document
        // Expected Output: False (document upload is mandatory)
        
        // Execute the application creation with null document
        boolean result = ipoSystem.createIPOApplication(customerC004, autoFuture, 25, 1000.0, null);
        
        // Verify the result is false
        assertFalse("Application should fail with null document", result);
        
        // Verify no applications were created
        assertEquals("Customer should have 0 applications", 0, customerC004.getApplications().size());
        assertEquals("System should have 0 applications", 0, ipoSystem.getAllApplications().size());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Test Case 5: "Zero-share application"
        // Input: Eligible customer applies for 0 shares
        // Expected Output: False
        
        // Execute the application creation with 0 shares
        boolean result = ipoSystem.createIPOApplication(customerC005, nanoChip, 0, 0.0, documentN);
        
        // Verify the result is false
        assertFalse("Application should fail with 0 shares", result);
        
        // Verify no applications were created
        assertEquals("Customer should have 0 applications", 0, customerC005.getApplications().size());
        assertEquals("System should have 0 applications", 0, ipoSystem.getAllApplications().size());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Test Case 6: "Negative share count"
        // Input: Eligible customer attempts to apply for negative shares
        // Expected Output: False (negative shares/amount are invalid)
        
        // Execute the application creation with negative shares
        boolean result = ipoSystem.createIPOApplication(customerC006, cloudServ, -5, -200.0, documentC);
        
        // Verify the result is false
        assertFalse("Application should fail with negative shares", result);
        
        // Verify no applications were created
        assertEquals("Customer should have 0 applications", 0, customerC006.getApplications().size());
        assertEquals("System should have 0 applications", 0, ipoSystem.getAllApplications().size());
    }
}