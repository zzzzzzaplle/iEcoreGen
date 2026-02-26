import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private Customer customer;
    private Company company;
    private Document document;
    
    @Before
    public void setUp() {
        customer = new Customer();
        company = new Company();
        document = new Document();
    }
    
    @Test
    public void testCase1_StandardEligibleSubmission() {
        // Setup customer details
        customer.setName("John");
        customer.setSurname("Smith");
        customer.setEmail("john.smith@example.com");
        customer.setTelephone("555-1234");
        customer.setCanApplyForIPO(true); // Eligible for IPO
        
        // Setup company details
        company.setName("TechCorp");
        company.setEmail("techcorp@gmail.com");
        
        // Execute: create application
        boolean result = customer.createApplication(company, 100, 5000.0, document);
        
        // Verify: application created successfully
        assertTrue(result);
        assertEquals(1, customer.getApplications().size());
        assertEquals(ApplicationStatus.PENDING, customer.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase2_CustomerNotEligible() {
        // Setup customer details
        customer.setName("Alice");
        customer.setSurname("Johnson");
        customer.setEmail("alice.j@example.com");
        customer.setTelephone("555-5678");
        customer.setCanApplyForIPO(false); // Not eligible for IPO
        
        // Setup company details
        company.setName("BioMed");
        company.setEmail("biomed@gmail.com");
        
        // Execute: create application
        boolean result = customer.createApplication(company, 50, 2500.0, document);
        
        // Verify: application creation failed due to ineligibility
        assertFalse(result);
        assertEquals(0, customer.getApplications().size());
    }
    
    @Test
    public void testCase3_DuplicateApprovedApplication() {
        // Setup customer details
        customer.setName("Robert");
        customer.setSurname("Chen");
        customer.setEmail("r.chen@example.com");
        customer.setTelephone("555-9012");
        customer.setCanApplyForIPO(true); // Eligible for IPO
        
        // Setup company details
        company.setName("GreenEnergy");
        company.setEmail("greenenergy@gmail.com");
        
        // Create first application and approve it
        boolean firstResult = customer.createApplication(company, 10, 300.0, document);
        assertTrue(firstResult);
        
        // Approve the first application
        Application firstApp = customer.getApplications().get(0);
        firstApp.setStatus(ApplicationStatus.APPROVED);
        
        // Execute: try to create second application for same company
        boolean secondResult = customer.createApplication(company, 10, 300.0, document);
        
        // Verify: second application creation failed due to existing approved application
        assertFalse(secondResult);
        assertEquals(1, customer.getApplications().size()); // Only one application should exist
    }
    
    @Test
    public void testCase4_MissingDocument() {
        // Setup customer details
        customer.setName("Emma");
        customer.setSurname("Davis");
        customer.setEmail("emma.d@example.com");
        customer.setTelephone("555-3456");
        customer.setCanApplyForIPO(true); // Eligible for IPO
        
        // Setup company details
        company.setName("AutoFuture");
        company.setEmail("autofuture@gmail.com");
        
        // Execute: create application with null document
        boolean result = customer.createApplication(company, 25, 1000.0, null);
        
        // Verify: application creation failed due to missing document
        assertFalse(result);
        assertEquals(0, customer.getApplications().size());
    }
    
    @Test
    public void testCase5_ZeroShareApplication() {
        // Setup customer details
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@example.com");
        customer.setTelephone("555-7890");
        customer.setCanApplyForIPO(true); // Eligible for IPO
        
        // Setup company details
        company.setName("NanoChip");
        company.setEmail("nanotech@gmail.com");
        
        // Execute: create application with zero shares
        boolean result = customer.createApplication(company, 0, 0.0, document);
        
        // Verify: application creation failed due to zero shares
        assertFalse(result);
        assertEquals(0, customer.getApplications().size());
    }
    
    @Test
    public void testCase6_NegativeShareCount() {
        // Setup customer details
        customer.setName("Sophia");
        customer.setSurname("Martinez");
        customer.setEmail("s.m@example.com");
        customer.setTelephone("555-2345");
        customer.setCanApplyForIPO(true); // Eligible for IPO
        
        // Setup company details
        company.setName("CloudServ");
        company.setEmail("cloudserv@gmail.com");
        
        // Execute: create application with negative shares
        boolean result = customer.createApplication(company, -5, -200.0, document);
        
        // Verify: application creation failed due to negative shares
        assertFalse(result);
        assertEquals(0, customer.getApplications().size());
    }
}