import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private BankSystem bankSystem;
    
    @Before
    public void setUp() {
        bankSystem = new BankSystem();
    }
    
    @Test
    public void testCase1_CancelStillPendingRequest() {
        // Setup: Create customer C301 with pending application for EcoWave
        Customer customer = new Customer();
        customer.setName("Benjamin");
        customer.setSurname("Taylor");
        customer.setEmail("b.taylor@example.com");
        customer.setTelephone("555-1010");
        
        IPOApplication pendingApp = new IPOApplication();
        pendingApp.setCustomer(customer);
        pendingApp.setCompanyName("EcoWave");
        pendingApp.setNumberOfShares(15);
        pendingApp.setAmountPaid(750.0);
        pendingApp.setDocument("EW-2024-03");
        pendingApp.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(pendingApp);
        bankSystem.getAllApplications().add(pendingApp);
        
        // Execute: Cancel pending application for EcoWave
        boolean result = bankSystem.cancelPendingApplication(customer, "EcoWave");
        
        // Verify: Cancellation should succeed
        assertTrue("Cancellation of pending application should return true", result);
        assertEquals("Application status should be REJECTED after cancellation", 
                     ApplicationStatus.REJECTED, pendingApp.getStatus());
    }
    
    @Test
    public void testCase2_CancelApprovedRequest() {
        // Setup: Create customer C302 with approved application for SmartGrid
        Customer customer = new Customer();
        customer.setName("Charlotte");
        customer.setSurname("Lee");
        customer.setEmail("c.lee@example.com");
        customer.setTelephone("555-2020");
        
        IPOApplication approvedApp = new IPOApplication();
        approvedApp.setCustomer(customer);
        approvedApp.setCompanyName("SmartGrid");
        approvedApp.setNumberOfShares(30);
        approvedApp.setAmountPaid(3000.0);
        approvedApp.setDocument("SG-2024-01");
        approvedApp.setStatus(ApplicationStatus.APPROVED);
        
        customer.getApplications().add(approvedApp);
        bankSystem.getAllApplications().add(approvedApp);
        
        // Execute: Try to cancel approved application
        boolean result = bankSystem.cancelPendingApplication(customer, "SmartGrid");
        
        // Verify: Cancellation should fail for approved application
        assertFalse("Cancellation of approved application should return false", result);
        assertEquals("Approved application status should remain APPROVED", 
                     ApplicationStatus.APPROVED, approvedApp.getStatus());
    }
    
    @Test
    public void testCase3_CancelRejectedRequest() {
        // Setup: Create customer C303 with rejected application for MedLife
        Customer customer = new Customer();
        customer.setName("Lucas");
        customer.setSurname("Martin");
        customer.setEmail("l.martin@example.com");
        customer.setTelephone("555-3030");
        
        IPOApplication rejectedApp = new IPOApplication();
        rejectedApp.setCustomer(customer);
        rejectedApp.setCompanyName("MedLife");
        rejectedApp.setNumberOfShares(20);
        rejectedApp.setAmountPaid(1000.0);
        rejectedApp.setDocument("SG-2024-03");
        rejectedApp.setStatus(ApplicationStatus.REJECTED);
        
        customer.getApplications().add(rejectedApp);
        bankSystem.getAllApplications().add(rejectedApp);
        
        // Execute: Try to cancel rejected application
        boolean result = bankSystem.cancelPendingApplication(customer, "MedLife");
        
        // Verify: Cancellation should fail for rejected application
        assertFalse("Cancellation of rejected application should return false", result);
        assertEquals("Rejected application status should remain REJECTED", 
                     ApplicationStatus.REJECTED, rejectedApp.getStatus());
    }
    
    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Setup: Create customer C304 with no applications
        Customer customer = new Customer();
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmail("a.clark@example.com");
        customer.setTelephone("555-4040");
        
        // Execute: Try to cancel application for non-existent company
        boolean result = bankSystem.cancelPendingApplication(customer, "UnknownCorp");
        
        // Verify: Cancellation should fail when no application exists
        assertFalse("Cancellation for non-existent company should return false", result);
    }
    
    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Setup: Create customer C306 with two pending applications
        Customer customer = new Customer();
        customer.setName("Mia");
        customer.setSurname("Anderson");
        customer.setEmail("m.anderson@example.com");
        customer.setTelephone("555-6060");
        
        // First pending application for UrbanTech
        IPOApplication urbanTechApp = new IPOApplication();
        urbanTechApp.setCustomer(customer);
        urbanTechApp.setCompanyName("UrbanTech");
        urbanTechApp.setNumberOfShares(25);
        urbanTechApp.setAmountPaid(1250.0);
        urbanTechApp.setDocument("SG-2024-005");
        urbanTechApp.setStatus(ApplicationStatus.PENDING);
        
        // Second pending application for AgroSeed
        IPOApplication agroSeedApp = new IPOApplication();
        agroSeedApp.setCustomer(customer);
        agroSeedApp.setCompanyName("AgroSeed");
        agroSeedApp.setNumberOfShares(40);
        agroTechApp.setAmountPaid(2000.0);
        agroSeedApp.setDocument("SG-2024-006");
        agroSeedApp.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(urbanTechApp);
        customer.getApplications().add(agroSeedApp);
        bankSystem.getAllApplications().add(urbanTechApp);
        bankSystem.getAllApplications().add(agroSeedApp);
        
        // Execute: Cancel UrbanTech application
        boolean result = bankSystem.cancelPendingApplication(customer, "UrbanTech");
        
        // Verify: UrbanTech cancellation should succeed
        assertTrue("Cancellation of UrbanTech application should return true", result);
        assertEquals("UrbanTech application status should be REJECTED after cancellation", 
                     ApplicationStatus.REJECTED, urbanTechApp.getStatus());
        
        // Verify: AgroSeed application should remain unaffected (still PENDING)
        assertEquals("AgroSeed application status should remain PENDING", 
                     ApplicationStatus.PENDING, agroSeedApp.getStatus());
    }
}