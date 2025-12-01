import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private BankSystem bankSystem;
    private Customer eligibleCustomer;
    private Customer restrictedCustomer;
    private Customer customerWithApprovedApp;
    
    @Before
    public void setUp() {
        bankSystem = new BankSystem();
        
        // Setup eligible customer for Test Case 1
        eligibleCustomer = new Customer();
        eligibleCustomer.setName("John");
        eligibleCustomer.setSurname("Smith");
        eligibleCustomer.setEmail("john.smith@example.com");
        eligibleCustomer.setTelephone("555-1234");
        eligibleCustomer.setRestricted(false);
        eligibleCustomer.setApplications(new ArrayList<>());
        
        // Setup restricted customer for Test Case 2
        restrictedCustomer = new Customer();
        restrictedCustomer.setName("Alice");
        restrictedCustomer.setSurname("Johnson");
        restrictedCustomer.setEmail("alice.j@example.com");
        restrictedCustomer.setTelephone("555-5678");
        restrictedCustomer.setRestricted(true); // Customer is restricted
        restrictedCustomer.setApplications(new ArrayList<>());
        
        // Setup customer with approved application for Test Case 3
        customerWithApprovedApp = new Customer();
        customerWithApprovedApp.setName("Robert");
        customerWithApprovedApp.setSurname("Chen");
        customerWithApprovedApp.setEmail("r.chen@example.com");
        customerWithApprovedApp.setTelephone("555-9012");
        customerWithApprovedApp.setRestricted(false);
        
        // Create and approve an application for GreenEnergy
        IPOApplication existingApp = new IPOApplication();
        existingApp.setCustomer(customerWithApprovedApp);
        existingApp.setCompanyName("GreenEnergy");
        existingApp.setNumberOfShares(10);
        existingApp.setAmountPaid(300.0);
        existingApp.setDocument("G");
        existingApp.setStatus(ApplicationStatus.APPROVED);
        
        List<IPOApplication> apps = new ArrayList<>();
        apps.add(existingApp);
        customerWithApprovedApp.setApplications(apps);
    }
    
    @Test
    public void testCase1_standardEligibleSubmission() {
        // Test Case 1: Standard eligible submission
        boolean result = bankSystem.createIPOApplication(
            eligibleCustomer, 
            "TechCorp", 
            100, 
            5000.0, 
            "A"
        );
        
        // Verify application was created successfully
        assertTrue("Application should be created successfully for eligible customer", result);
        assertEquals("Customer should have one application", 1, eligibleCustomer.getApplications().size());
        
        IPOApplication createdApp = eligibleCustomer.getApplications().get(0);
        assertEquals("Application company should match", "TechCorp", createdApp.getCompanyName());
        assertEquals("Number of shares should match", 100, createdApp.getNumberOfShares());
        assertEquals("Amount paid should match", 5000.0, createdApp.getAmountPaid(), 0.001);
        assertEquals("Document should match", "A", createdApp.getDocument());
        assertEquals("Status should be pending", ApplicationStatus.PENDING, createdApp.getStatus());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Test Case 2: Customer not eligible
        boolean result = bankSystem.createIPOApplication(
            restrictedCustomer, 
            "BioMed", 
            50, 
            2500.0, 
            "B"
        );
        
        // Verify application was NOT created due to customer restriction
        assertFalse("Application should fail for restricted customer", result);
        assertEquals("Customer should have no applications", 0, restrictedCustomer.getApplications().size());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Test Case 3: Duplicate approved application
        boolean result = bankSystem.createIPOApplication(
            customerWithApprovedApp, 
            "GreenEnergy", 
            10, 
            300.0, 
            "G"
        );
        
        // Verify application was NOT created due to existing approved application
        assertFalse("Application should fail due to existing approved application", result);
        assertEquals("Customer should still have only one application", 1, customerWithApprovedApp.getApplications().size());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Test Case 4: Missing document
        Customer customer = new Customer();
        customer.setName("Emma");
        customer.setSurname("Davis");
        customer.setEmail("emma.d@example.com");
        customer.setTelephone("555-3456");
        customer.setRestricted(false);
        customer.setApplications(new ArrayList<>());
        
        boolean result = bankSystem.createIPOApplication(
            customer, 
            "AutoFuture", 
            25, 
            1000.0, 
            null
        );
        
        // Verify application was NOT created due to missing document
        assertFalse("Application should fail with null document", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Test Case 5: Zero-share application
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@example.com");
        customer.setTelephone("555-7890");
        customer.setRestricted(false);
        customer.setApplications(new ArrayList<>());
        
        boolean result = bankSystem.createIPOApplication(
            customer, 
            "NanoChip", 
            0, 
            0.0, 
            "N"
        );
        
        // Verify application was NOT created due to zero shares
        assertFalse("Application should fail with zero shares", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Test Case 6: Negative share count
        Customer customer = new Customer();
        customer.setName("Sophia");
        customer.setSurname("Martinez");
        customer.setEmail("s.m@example.com");
        customer.setTelephone("555-2345");
        customer.setRestricted(false);
        customer.setApplications(new ArrayList<>());
        
        boolean result = bankSystem.createIPOApplication(
            customer, 
            "CloudServ", 
            -5, 
            -200.0, 
            "C"
        );
        
        // Verify application was NOT created due to negative shares
        assertFalse("Application should fail with negative shares", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
}