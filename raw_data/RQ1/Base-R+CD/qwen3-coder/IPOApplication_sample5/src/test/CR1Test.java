import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR1Test {
    
    private Customer customer1, customer2, customer3, customer4, customer5, customer6;
    private Company techCorp, bioMed, greenEnergy, autoFuture, nanoChip, cloudServ;
    private Document documentA, documentB, documentG, documentN, documentC;
    
    @Before
    public void setUp() {
        // Initialize customers
        customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Smith");
        customer1.setEmail("john.smith@example.com");
        customer1.setTelephone("555-1234");
        customer1.setCanApplyForIPO(true);
        
        customer2 = new Customer();
        customer2.setName("Alice");
        customer2.setSurname("Johnson");
        customer2.setEmail("alice.j@example.com");
        customer2.setTelephone("555-5678");
        customer2.setCanApplyForIPO(false); // Not eligible
        
        customer3 = new Customer();
        customer3.setName("Robert");
        customer3.setSurname("Chen");
        customer3.setEmail("r.chen@example.com");
        customer3.setTelephone("555-9012");
        customer3.setCanApplyForIPO(true);
        
        customer4 = new Customer();
        customer4.setName("Emma");
        customer4.setSurname("Davis");
        customer4.setEmail("emma.d@example.com");
        customer4.setTelephone("555-3456");
        customer4.setCanApplyForIPO(true);
        
        customer5 = new Customer();
        customer5.setName("James");
        customer5.setSurname("Wilson");
        customer5.setEmail("j.wilson@example.com");
        customer5.setTelephone("555-7890");
        customer5.setCanApplyForIPO(true);
        
        customer6 = new Customer();
        customer6.setName("Sophia");
        customer6.setSurname("Martinez");
        customer6.setEmail("s.m@example.com");
        customer6.setTelephone("555-2345");
        customer6.setCanApplyForIPO(true);
        
        // Initialize companies
        techCorp = new Company();
        techCorp.setName("TechCorp");
        techCorp.setEmail("techcorp@gmail.com");
        
        bioMed = new Company();
        bioMed.setName("BioMed");
        bioMed.setEmail("biomed@gmail.com");
        
        greenEnergy = new Company();
        greenEnergy.setName("GreenEnergy");
        greenEnergy.setEmail("greenenergy@gmail.com");
        
        autoFuture = new Company();
        autoFuture.setName("AutoFuture");
        autoFuture.setEmail("autofuture@gmail.com");
        
        nanoChip = new Company();
        nanoChip.setName("NanoChip");
        nanoChip.setEmail("nanotech@gmail.com");
        
        cloudServ = new Company();
        cloudServ.setName("CloudServ");
        cloudServ.setEmail("cloudserv@gmail.com");
        
        // Initialize documents
        documentA = new Document();
        documentB = new Document();
        documentG = new Document();
        documentN = new Document();
        documentC = new Document();
    }
    
    @Test
    public void testCase1_standardEligibleSubmission() {
        // Test Case 1: Standard eligible submission
        // Customer is eligible and has no approved applications for the company
        boolean result = customer1.createApplication(techCorp, 100, 5000.0, documentA);
        
        // Expected Output: True (application is successfully created)
        assertTrue(result);
        
        // Verify that the application was added
        assertEquals(1, customer1.getApplications().size());
        Application app = customer1.getApplications().get(0);
        assertEquals(100, app.getShare());
        assertEquals(5000.0, app.getAmountOfMoney(), 0.01);
        assertEquals(ApplicationStatus.PENDING, app.getStatus());
        assertEquals(techCorp, app.getCompany());
        assertEquals(documentA, app.getAllowance());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Test Case 2: Customer not eligible
        // Customer has lost IPO eligibility
        boolean result = customer2.createApplication(bioMed, 50, 2500.0, documentB);
        
        // Expected Output: False
        assertFalse(result);
        
        // Verify that no application was added
        assertEquals(0, customer2.getApplications().size());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Test Case 3: Duplicate approved application
        // First create and approve an application
        customer3.createApplication(greenEnergy, 10, 300.0, documentG);
        Application app = customer3.getApplications().get(0);
        app.approve(); // Approve the application
        
        // Try to create another application for the same company
        boolean result = customer3.createApplication(greenEnergy, 10, 300.0, documentG);
        
        // Expected Output: False (only one approved application per company allowed)
        assertFalse(result);
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Test Case 4: Missing document
        // Customer forgets to upload any document (pass null)
        boolean result = customer4.createApplication(autoFuture, 25, 1000.0, null);
        
        // Expected Output: False (document upload is mandatory)
        assertFalse(result);
        
        // Verify that no application was added
        assertEquals(0, customer4.getApplications().size());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Test Case 5: Zero-share application
        // Customer applies for 0 shares
        boolean result = customer5.createApplication(nanoChip, 0, 0.0, documentN);
        
        // Expected Output: False
        assertFalse(result);
        
        // Verify that no application was added
        assertEquals(0, customer5.getApplications().size());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Test Case 6: Negative share count
        // Customer attempts to apply for -5 shares
        boolean result = customer6.createApplication(cloudServ, -5, -200.0, documentC);
        
        // Expected Output: False (negative shares/amount are invalid)
        assertFalse(result);
        
        // Verify that no application was added
        assertEquals(0, customer6.getApplications().size());
    }
}