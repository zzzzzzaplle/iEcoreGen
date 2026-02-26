import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

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
        // Act: Customer 1 applies for IPO with TechCorp
        boolean result = customer1.createApplication(techCorp, 100, 5000.0, documentA);
        
        // Assert: Application should be created successfully
        assertTrue(result);
        
        // Verify application details
        List<Application> applications = customer1.getApplications();
        assertEquals(1, applications.size());
        Application app = applications.get(0);
        assertEquals(100, app.getShare());
        assertEquals(5000.0, app.getAmountOfMoney(), 0.01);
        assertEquals(ApplicationStatus.PENDING, app.getStatus());
        assertEquals(customer1, app.getCustomer());
        assertEquals(techCorp, app.getCompany());
        assertEquals(documentA, app.getAllowance());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Act: Customer 2 (not eligible) attempts to apply for IPO
        boolean result = customer2.createApplication(bioMed, 50, 2500.0, documentB);
        
        // Assert: Application should not be created
        assertFalse(result);
        
        // Verify no application was created
        assertEquals(0, customer2.getApplications().size());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Setup: Create and approve an application first
        customer3.createApplication(greenEnergy, 10, 300.0, documentG);
        Application existingApp = customer3.getApplications().get(0);
        existingApp.approve(); // Approve the application
        
        // Act: Try to create another application for the same company
        boolean result = customer3.createApplication(greenEnergy, 10, 300.0, documentG);
        
        // Assert: Application should not be created due to existing approved application
        assertFalse(result);
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Act: Customer 4 attempts to apply without a document (null)
        boolean result = customer4.createApplication(autoFuture, 25, 1000.0, null);
        
        // Assert: Application should not be created
        assertFalse(result);
        
        // Verify no application was created
        assertEquals(0, customer4.getApplications().size());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Act: Customer 5 attempts to apply for 0 shares
        boolean result = customer5.createApplication(nanoChip, 0, 0.0, documentN);
        
        // Assert: Application should not be created
        assertFalse(result);
        
        // Verify no application was created
        assertEquals(0, customer5.getApplications().size());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Act: Customer 6 attempts to apply for negative shares
        boolean result = customer6.createApplication(cloudServ, -5, -200.0, documentC);
        
        // Assert: Application should not be created
        assertFalse(result);
        
        // Verify no application was created
        assertEquals(0, customer6.getApplications().size());
    }
}