import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Customer customer;
    private Bank bank;
    
    @Before
    public void setUp() {
        customer = new Customer();
        bank = new Bank();
    }
    
    @Test
    public void testCase1_standardEligibleSubmission() {
        // Setup: Customer "C001" allows IPO applications and has no previously approved applications for "TechCorp"
        customer.setName("John");
        customer.setSurname("Smith");
        customer.setEmail("john.smith@example.com");
        customer.setTelephoneNumber("555-1234");
        customer.setRestricted(false);
        customer.setApplications(new ArrayList<>());
        
        // Input: Apply for IPO with company "TechCorp", 100 shares, $5000 payment, document 'A'
        boolean result = customer.createIPOApplication("TechCorp", 100, 5000.0, "A");
        
        // Expected Output: True (application is successfully created)
        assertTrue("Application should be created successfully for eligible customer", result);
        assertEquals("Application should be added to customer's applications", 1, customer.getApplications().size());
        assertEquals("Application company name should be TechCorp", "TechCorp", customer.getApplications().get(0).getCompanyName());
    }
    
    @Test
    public void testCase2_customerNotEligible() {
        // Setup: Customer "C002" has lost IPO eligibility after repeated failed attempts
        customer.setName("Alice");
        customer.setSurname("Johnson");
        customer.setEmail("alice.j@example.com");
        customer.setTelephoneNumber("555-5678");
        customer.setRestricted(true);
        customer.setApplications(new ArrayList<>());
        
        // Input: Attempt to apply for IPO with company "BioMed", 50 shares, $2500 payment, document 'B'
        boolean result = customer.createIPOApplication("BioMed", 50, 2500.0, "B");
        
        // Expected Output: False
        assertFalse("Application should fail for restricted customer", result);
        assertEquals("No application should be added for restricted customer", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase3_duplicateApprovedApplication() {
        // Setup: Customer "C003" remains eligible and has approved application for "GreenEnergy"
        customer.setName("Robert");
        customer.setSurname("Chen");
        customer.setEmail("r.chen@example.com");
        customer.setTelephoneNumber("555-9012");
        customer.setRestricted(false);
        
        List<IPOApplication> applications = new ArrayList<>();
        IPOApplication existingApp = new IPOApplication("GreenEnergy", 10, 300.0, "G");
        existingApp.setStatus(IPOApplication.Status.APPROVED);
        applications.add(existingApp);
        customer.setApplications(applications);
        
        // Input: Submit another application to "GreenEnergy" for 10 shares ($300) with document 'G'
        boolean result = customer.createIPOApplication("GreenEnergy", 10, 300.0, "G");
        
        // Expected Output: False (only one approved application per company allowed)
        assertFalse("Application should fail due to duplicate approved application for same company", result);
        assertEquals("No new application should be added", 1, customer.getApplications().size());
    }
    
    @Test
    public void testCase4_missingDocument() {
        // Setup: Eligible customer "C004" with no prior applications for "AutoFuture"
        customer.setName("Emma");
        customer.setSurname("Davis");
        customer.setEmail("emma.d@example.com");
        customer.setTelephoneNumber("555-3456");
        customer.setRestricted(false);
        customer.setApplications(new ArrayList<>());
        
        // Input: Apply to "AutoFuture" for 25 shares ($1000) with null document
        boolean result = customer.createIPOApplication("AutoFuture", 25, 1000.0, null);
        
        // Expected Output: False (document upload is mandatory)
        assertFalse("Application should fail when document is null", result);
        assertEquals("No application should be added when document is null", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase5_zeroShareApplication() {
        // Setup: Eligible customer "C005" with no existing applications for "NanoChip"
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@example.com");
        customer.setTelephoneNumber("555-7890");
        customer.setRestricted(false);
        customer.setApplications(new ArrayList<>());
        
        // Input: Apply to "NanoChip" for 0 shares ($0) with document 'N'
        boolean result = customer.createIPOApplication("NanoChip", 0, 0.0, "N");
        
        // Expected Output: False
        assertFalse("Application should fail when number of shares is zero", result);
        assertEquals("No application should be added when shares are zero", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase6_negativeShareCount() {
        // Setup: Eligible customer "C006" with no prior applications for "CloudServ"
        customer.setName("Sophia");
        customer.setSurname("Martinez");
        customer.setEmail("s.m@example.com");
        customer.setTelephoneNumber("555-2345");
        customer.setRestricted(false);
        customer.setApplications(new ArrayList<>());
        
        // Input: Attempt to apply to "CloudServ" for -5 shares (-$200) with document 'C'
        boolean result = customer.createIPOApplication("CloudServ", -5, -200.0, "C");
        
        // Expected Output: False (negative shares/amount are invalid)
        assertFalse("Application should fail when number of shares is negative", result);
        assertEquals("No application should be added when shares are negative", 0, customer.getApplications().size());
    }
}