import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    private Customer customerC301;
    private Customer customerC302;
    private Customer customerC303;
    private Customer customerC304;
    private Customer customerC306;
    private Company ecoWaveCompany;
    private Company smartGridCompany;
    private Company medLifeCompany;
    private Company urbanTechCompany;
    private Company agroSeedCompany;
    private Document docEW202403;
    private Document docSG202401;
    private Document docSG202403;
    private Document docSG2024005;
    private Document docSG2024006;
    private Application appC301Pending;
    private Application appC302Approved;
    private Application appC303Rejected;
    private Application appC306UrbanTech;
    private Application appC306AgroSeed;

    @Before
    public void setUp() {
        // Initialize companies
        ecoWaveCompany = new Company();
        ecoWaveCompany.setName("EcoWave");
        ecoWaveCompany.setEmail("ecowave@gmail.com");
        
        smartGridCompany = new Company();
        smartGridCompany.setName("SmartGrid");
        smartGridCompany.setEmail("smartgrid@business.com");
        
        medLifeCompany = new Company();
        medLifeCompany.setName("MedLife");
        medLifeCompany.setEmail("medlife@health.com");
        
        urbanTechCompany = new Company();
        urbanTechCompany.setName("UrbanTech");
        urbanTechCompany.setEmail("urbantech@innovate.com");
        
        agroSeedCompany = new Company();
        agroSeedCompany.setName("AgroSeed");
        agroSeedCompany.setEmail("agroseed@business.com");
        
        // Initialize documents
        docEW202403 = new Document();
        docSG202401 = new Document();
        docSG202403 = new Document();
        docSG2024005 = new Document();
        docSG2024006 = new Document();
        
        // Initialize customers
        customerC301 = new Customer();
        customerC301.setName("Benjamin");
        customerC301.setSurname("Taylor");
        customerC301.setEmail("b.taylor@example.com");
        customerC301.setTelephone("555-1010");
        customerC301.setCanApplyForIPO(true);
        
        customerC302 = new Customer();
        customerC302.setName("Charlotte");
        customerC302.setSurname("Lee");
        customerC302.setEmail("c.lee@example.com");
        customerC302.setTelephone("555-2020");
        customerC302.setCanApplyForIPO(true);
        
        customerC303 = new Customer();
        customerC303.setName("Lucas");
        customerC303.setSurname("Martin");
        customerC303.setEmail("l.martin@example.com");
        customerC303.setTelephone("555-3030");
        customerC303.setCanApplyForIPO(true);
        
        customerC304 = new Customer();
        customerC304.setName("Amelia");
        customerC304.setSurname("Clark");
        customerC304.setEmail("a.clark@example.com");
        customerC304.setTelephone("555-4040");
        customerC304.setCanApplyForIPO(true);
        
        customerC306 = new Customer();
        customerC306.setName("Mia");
        customerC306.setSurname("Anderson");
        customerC306.setEmail("m.anderson@example.com");
        customerC306.setTelephone("555-6060");
        customerC306.setCanApplyForIPO(true);
        
        // Create applications
        appC301Pending = new Application();
        appC301Pending.setShare(15);
        appC301Pending.setAmountOfMoney(750);
        appC301Pending.setStatus(ApplicationStatus.PENDING);
        appC301Pending.setCustomer(customerC301);
        appC301Pending.setCompany(ecoWaveCompany);
        appC301Pending.setAllowance(docEW202403);
        customerC301.getApplications().add(appC301Pending);
        
        appC302Approved = new Application();
        appC302Approved.setShare(30);
        appC302Approved.setAmountOfMoney(3000);
        appC302Approved.setStatus(ApplicationStatus.APPROVED);
        appC302Approved.setCustomer(customerC302);
        appC302Approved.setCompany(smartGridCompany);
        appC302Approved.setAllowance(docSG202401);
        customerC302.getApplications().add(appC302Approved);
        
        appC303Rejected = new Application();
        appC303Rejected.setShare(20);
        appC303Rejected.setAmountOfMoney(1000);
        appC303Rejected.setStatus(ApplicationStatus.REJECTED);
        appC303Rejected.setCustomer(customerC303);
        appC303Rejected.setCompany(medLifeCompany);
        appC303Rejected.setAllowance(docSG202403);
        customerC303.getApplications().add(appC303Rejected);
        
        appC306UrbanTech = new Application();
        appC306UrbanTech.setShare(25);
        appC306UrbanTech.setAmountOfMoney(1250);
        appC306UrbanTech.setStatus(ApplicationStatus.PENDING);
        appC306UrbanTech.setCustomer(customerC306);
        appC306UrbanTech.setCompany(urbanTechCompany);
        appC306UrbanTech.setAllowance(docSG2024005);
        
        appC306AgroSeed = new Application();
        appC306AgroSeed.setShare(40);
        appC306AgroSeed.setAmountOfMoney(2000);
        appC306AgroSeed.setStatus(ApplicationStatus.PENDING);
        appC306AgroSeed.setCustomer(customerC306);
        appC306AgroSeed.setCompany(agroSeedCompany);
        appC306AgroSeed.setAllowance(docSG2024006);
        
        customerC306.getApplications().add(appC306UrbanTech);
        customerC306.getApplications().add(appC306AgroSeed);
    }

    @Test
    public void testCase1_CancelStillPendingRequest() {
        // Test canceling a still-pending application
        boolean result = customerC301.cancelApplication("EcoWave");
        assertTrue("Should return true when canceling pending application", result);
        assertEquals("Application status should be REJECTED after cancellation", 
                     ApplicationStatus.REJECTED, appC301Pending.getStatus());
    }

    @Test
    public void testCase2_CancelApprovedRequest() {
        // Test canceling an approved application
        boolean result = customerC302.cancelApplication("SmartGrid");
        assertFalse("Should return false when canceling approved application", result);
        assertEquals("Application status should remain APPROVED", 
                     ApplicationStatus.APPROVED, appC302Approved.getStatus());
    }

    @Test
    public void testCase3_CancelRejectedRequest() {
        // Test canceling a rejected application
        boolean result = customerC303.cancelApplication("MedLife");
        assertFalse("Should return false when canceling rejected application", result);
        assertEquals("Application status should remain REJECTED", 
                     ApplicationStatus.REJECTED, appC303Rejected.getStatus());
    }

    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Test canceling application for non-existent company
        boolean result = customerC304.cancelApplication("UnknownCorp");
        assertFalse("Should return false when no application exists for specified company", result);
        assertTrue("Customer should have no applications", customerC304.getApplications().isEmpty());
    }

    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Test canceling one application while another remains unaffected
        boolean result = customerC306.cancelApplication("UrbanTech");
        assertTrue("Should return true when canceling pending application", result);
        assertEquals("UrbanTech application status should be REJECTED after cancellation", 
                     ApplicationStatus.REJECTED, appC306UrbanTech.getStatus());
        assertEquals("AgroSeed application status should remain PENDING", 
                     ApplicationStatus.PENDING, appC306AgroSeed.getStatus());
        assertEquals("Customer should still have 2 applications", 
                     2, customerC306.getApplications().size());
    }
}