import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Customer customer;
    
    @Before
    public void setUp() {
        customer = new Customer();
    }
    
    @Test
    public void testCase1_standardEligibleSubmission() {
        // Setup: Customer "C001" allows IPO applications and has no previously approved applications for "TechCorp"
        customer.setName("John");
        customer.setSurname("Smith");
        customer.setEmail("john.smith@example.com");
        customer.setTelephone("555-1234");
        customer.setFailedAttempts(0); // Eligible customer
        
        // Execute: Apply for IPO with company "TechCorp", 100 shares, $5000, document 'A'
        boolean result = IpoApplication.createApplication(customer, "TechCorp", 100, 5000.0, "A");
        
        // Verify: Application should be successfully created
        assertTrue("Standard eligible submission should return true", result);
        assertEquals("Failed attempts should be reset after successful application", 0, customer.getFailedAttempts());
        assertEquals("Application list should contain one application", 1, customer.getApplications().size());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Setup: Customer "C002" has lost IPO eligibility after repeated failed attempts
        customer.setName("Alice");
        customer.setSurname("Johnson");
        customer.setEmail("alice.j@example.com");
        customer.setTelephone("555-5678");
        customer.setFailedAttempts(3); // Not eligible (MAX_FAILED_ATTEMPTS = 3)
        
        // Execute: Attempt to apply for IPO with company "BioMed", 50 shares, $2500, document 'B'
        boolean result = IpoApplication.createApplication(customer, "BioMed", 50, 2500.0, "B");
        
        // Verify: Application should fail due to ineligibility
        assertFalse("Customer not eligible should return false", result);
        assertEquals("Failed attempts should be incremented", 4, customer.getFailedAttempts());
        assertEquals("Application list should be empty", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Setup: Customer "C003" remains eligible and has an approved application for "GreenEnergy"
        customer.setName("Robert");
        customer.setSurname("Chen");
        customer.setEmail("r.chen@example.com");
        customer.setTelephone("555-9012");
        customer.setFailedAttempts(0); // Eligible customer
        
        // Create and approve first application for "GreenEnergy"
        IpoApplication firstApp = new IpoApplication();
        firstApp.setCompanyName("GreenEnergy");
        firstApp.setShares(10);
        firstApp.setAmount(300.0);
        firstApp.setDocument("G");
        firstApp.setApproved(true);
        
        List<IpoApplication> apps = new ArrayList<>();
        apps.add(firstApp);
        customer.setApplications(apps);
        
        // Execute: Submit another application to "GreenEnergy" for 10 shares, $300, document 'G'
        boolean result = IpoApplication.createApplication(customer, "GreenEnergy", 10, 300.0, "G");
        
        // Verify: Should return false due to duplicate approved application
        assertFalse("Duplicate approved application should return false", result);
        assertEquals("Application list should still contain only one application", 1, customer.getApplications().size());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Setup: Eligible customer "C004" with no prior applications for "AutoFuture"
        customer.setName("Emma");
        customer.setSurname("Davis");
        customer.setEmail("emma.d@example.com");
        customer.setTelephone("555-3456");
        customer.setFailedAttempts(0); // Eligible customer
        
        // Execute: Apply to "AutoFuture" for 25 shares, $1000, but with null document
        boolean result = IpoApplication.createApplication(customer, "AutoFuture", 25, 1000.0, null);
        
        // Verify: Should return false due to missing document
        assertFalse("Missing document should return false", result);
        assertEquals("Failed attempts should be incremented", 1, customer.getFailedAttempts());
        assertEquals("Application list should be empty", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Setup: Eligible customer "C005" with no existing applications for "NanoChip"
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@example.com");
        customer.setTelephone("555-7890");
        customer.setFailedAttempts(0); // Eligible customer
        
        // Execute: Apply to "NanoChip" for 0 shares, $0, document 'N'
        boolean result = IpoApplication.createApplication(customer, "NanoChip", 0, 0.0, "N");
        
        // Verify: Should return false due to zero shares
        assertFalse("Zero-share application should return false", result);
        assertEquals("Failed attempts should be incremented", 1, customer.getFailedAttempts());
        assertEquals("Application list should be empty", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Setup: Eligible customer "C006" with no prior applications for "CloudServ"
        customer.setName("Sophia");
        customer.setSurname("Martinez");
        customer.setEmail("s.m@example.com");
        customer.setTelephone("555-2345");
        customer.setFailedAttempts(0); // Eligible customer
        
        // Execute: Apply to "CloudServ" for -5 shares, -$200, document 'C'
        boolean result = IpoApplication.createApplication(customer, "CloudServ", -5, -200.0, "C");
        
        // Verify: Should return false due to negative shares
        assertFalse("Negative share count should return false", result);
        assertEquals("Failed attempts should be incremented", 1, customer.getFailedAttempts());
        assertEquals("Application list should be empty", 0, customer.getApplications().size());
    }
}