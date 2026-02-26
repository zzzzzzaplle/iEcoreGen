import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Customer customer1;
    private Customer customer2;
    private Customer customer3;
    private Customer customer4;
    private Customer customer5;
    private Customer customer6;
    private IPOService ipoService;
    
    @Before
    public void setUp() {
        // Initialize IPO service
        ipoService = new IPOService();
        
        // Setup customer1 (eligible customer for Test Case 1)
        customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Smith");
        customer1.setEmail("john.smith@example.com");
        customer1.setTelephoneNumber("555-1234");
        customer1.setFailedAttempts(0); // Eligible (less than 3 failed attempts)
        
        // Setup customer2 (not eligible customer for Test Case 2)
        customer2 = new Customer();
        customer2.setName("Alice");
        customer2.setSurname("Johnson");
        customer2.setEmail("alice.j@example.com");
        customer2.setTelephoneNumber("555-5678");
        customer2.setFailedAttempts(3); // Not eligible (3 failed attempts)
        
        // Setup customer3 (eligible customer with approved application for Test Case 3)
        customer3 = new Customer();
        customer3.setName("Robert");
        customer3.setSurname("Chen");
        customer3.setEmail("r.chen@example.com");
        customer3.setTelephoneNumber("555-9012");
        customer3.setFailedAttempts(0); // Eligible
        
        // Create and approve an application for GreenEnergy
        IPOApplication existingApp = new IPOApplication();
        existingApp.setCompanyName("GreenEnergy");
        existingApp.setNumberOfShares(10);
        existingApp.setAmount(300.0);
        existingApp.setDocument("G");
        existingApp.setStatus(IPOApplicationStatus.APPROVED);
        List<IPOApplication> apps3 = new ArrayList<>();
        apps3.add(existingApp);
        customer3.setIpoApplications(apps3);
        
        // Setup customer4 (eligible customer for Test Case 4)
        customer4 = new Customer();
        customer4.setName("Emma");
        customer4.setSurname("Davis");
        customer4.setEmail("emma.d@example.com");
        customer4.setTelephoneNumber("555-3456");
        customer4.setFailedAttempts(0); // Eligible
        
        // Setup customer5 (eligible customer for Test Case 5)
        customer5 = new Customer();
        customer5.setName("James");
        customer5.setSurname("Wilson");
        customer5.setEmail("j.wilson@example.com");
        customer5.setTelephoneNumber("555-7890");
        customer5.setFailedAttempts(0); // Eligible
        
        // Setup customer6 (eligible customer for Test Case 6)
        customer6 = new Customer();
        customer6.setName("Sophia");
        customer6.setSurname("Martinez");
        customer6.setEmail("s.m@example.com");
        customer6.setTelephoneNumber("555-2345");
        customer6.setFailedAttempts(0); // Eligible
    }
    
    @Test
    public void testCase1_standardEligibleSubmission() {
        // Test Case 1: Standard eligible submission
        // Input: Eligible customer applies for IPO with valid data
        boolean result = ipoService.createIPOApplication(customer1, "TechCorp", 100, 5000.0, "A");
        
        // Verify application was created successfully
        assertTrue("Application should be created successfully for eligible customer", result);
        assertEquals("Customer should have 1 application", 1, customer1.getIpoApplications().size());
        assertEquals("Application company name should match", "TechCorp", customer1.getIpoApplications().get(0).getCompanyName());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Test Case 2: Customer not eligible
        // Input: Ineligible customer attempts to apply
        boolean result = ipoService.createIPOApplication(customer2, "BioMed", 50, 2500.0, "B");
        
        // Verify application was rejected due to ineligibility
        assertFalse("Application should be rejected for ineligible customer", result);
        assertEquals("Customer should have 0 applications", 0, customer2.getIpoApplications().size());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Test Case 3: Duplicate approved application
        // Input: Customer with existing approved application for same company
        boolean result = ipoService.createIPOApplication(customer3, "GreenEnergy", 10, 300.0, "G");
        
        // Verify application was rejected due to duplicate approved application
        assertFalse("Application should be rejected for duplicate approved company", result);
        assertEquals("Customer should still have 1 application", 1, customer3.getIpoApplications().size());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Test Case 4: Missing document
        // Input: Customer applies without uploading document
        boolean result = ipoService.createIPOApplication(customer4, "AutoFuture", 25, 1000.0, null);
        
        // Verify application was rejected due to missing document
        assertFalse("Application should be rejected when document is null", result);
        assertEquals("Customer should have 0 applications", 0, customer4.getIpoApplications().size());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Test Case 5: Zero-share application
        // Input: Customer applies with 0 shares
        boolean result = ipoService.createIPOApplication(customer5, "NanoChip", 0, 0.0, "N");
        
        // Verify application was rejected due to zero shares
        assertFalse("Application should be rejected for 0 shares", result);
        assertEquals("Customer should have 0 applications", 0, customer5.getIpoApplications().size());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Test Case 6: Negative share count
        // Input: Customer applies with negative shares
        boolean result = ipoService.createIPOApplication(customer6, "CloudServ", -5, -200.0, "C");
        
        // Verify application was rejected due to negative shares
        assertFalse("Application should be rejected for negative shares", result);
        assertEquals("Customer should have 0 applications", 0, customer6.getIpoApplications().size());
    }
}