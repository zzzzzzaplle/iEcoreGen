import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Customer customer;
    
    @Before
    public void setUp() {
        customer = new Customer();
    }
    
    @Test
    public void testCase1_noApprovedRequests() {
        // Setup: Customer "C201" with pending and rejected applications
        customer.setName("Emily Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephone("555-1212");
        customer.setCanApplyForIPO(true);
        
        // Create applications
        List<Application> applications = new ArrayList<>();
        applications.add(new Application("TechInc", 10, 1500.0, "QT-3001"));
        applications.add(new Application("BioMed", 10, 2000.0, "QT-3002"));
        
        // Set statuses
        applications.get(0).setStatus(ApplicationStatus.PENDING);
        applications.get(1).setStatus(ApplicationStatus.REJECTED);
        
        customer.setApplications(applications);
        
        // Test: Query total approved amount
        double result = customer.getTotalApprovedAmount();
        
        // Verify: Should be 0 since no approved applications
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_singleApproval() {
        // Setup: Customer "C202" with one approved application
        customer.setName("Robert Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephone("555-2323");
        customer.setCanApplyForIPO(true);
        
        // Create and add approved application
        Application approvedApp = new Application("SolarMax", 84, 4200.0, "SM-2024-Q1");
        approvedApp.setStatus(ApplicationStatus.APPROVED);
        
        List<Application> applications = new ArrayList<>();
        applications.add(approvedApp);
        customer.setApplications(applications);
        
        // Test: Query total approved amount
        double result = customer.getTotalApprovedAmount();
        
        // Verify: Should be 4200.0
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleApprovalsDifferentFirms() {
        // Setup: Customer "C203" with two approved applications
        customer.setName("Sophia Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        customer.setCanApplyForIPO(true);
        
        // Create and add approved applications
        List<Application> applications = new ArrayList<>();
        
        Application app1 = new Application("QuantumTech", 40, 2000.0, "SM-2024-Q3004");
        app1.setStatus(ApplicationStatus.APPROVED);
        applications.add(app1);
        
        Application app2 = new Application("Neuralink", 70, 3500.0, "SM-2024-Q3005");
        app2.setStatus(ApplicationStatus.APPROVED);
        applications.add(app2);
        
        customer.setApplications(applications);
        
        // Test: Query total approved amount
        double result = customer.getTotalApprovedAmount();
        
        // Verify: Should be 5500.0 (2000 + 3500)
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_largePortfolio() {
        // Setup: Customer "C204" with five approved applications
        customer.setName("James Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        customer.setCanApplyForIPO(true);
        
        // Create and add approved applications (all $10,000 each)
        List<Application> applications = new ArrayList<>();
        
        applications.add(createApprovedApplication("TechGiant", 200, 10000.0, "SM-3006"));
        applications.add(createApprovedApplication("AutoFuture", 250, 10000.0, "SM-3007"));
        applications.add(createApprovedApplication("AeroSpace", 125, 10000.0, "SM-3008"));
        applications.add(createApprovedApplication("BioGenius", 500, 10000.0, "SM-3009"));
        applications.add(createApprovedApplication("GreenEnergy", 200, 10000.0, "SM-3010"));
        
        customer.setApplications(applications);
        
        // Test: Query total approved amount
        double result = customer.getTotalApprovedAmount();
        
        // Verify: Should be 50000.0 (5 * 10000)
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_approvalsPlusPending() {
        // Setup: Customer "C205" with approved and pending applications
        customer.setName("Olivia Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephone("555-5656");
        customer.setCanApplyForIPO(true);
        
        // Create and add applications
        List<Application> applications = new ArrayList<>();
        
        // Approved applications
        applications.add(createApprovedApplication("CloudServ", 100, 3000.0, "SM-3011"));
        applications.add(createApprovedApplication("DataCore", 20, 2750.0, "SM-3012"));
        applications.add(createApprovedApplication("AI Ventures", 30, 3000.0, "SM-3013"));
        
        // Pending applications (should not be counted)
        Application pendingApp1 = new Application("NanoTech", 10, 600.0, "SM-3014");
        pendingApp1.setStatus(ApplicationStatus.PENDING);
        applications.add(pendingApp1);
        
        Application pendingApp2 = new Application("RoboWorks", 50, 600.0, "SM-3015");
        pendingApp2.setStatus(ApplicationStatus.PENDING);
        applications.add(pendingApp2);
        
        customer.setApplications(applications);
        
        // Test: Query total approved amount
        double result = customer.getTotalApprovedAmount();
        
        // Verify: Should be 8750.0 (3000 + 2750 + 3000)
        assertEquals(8750.0, result, 0.001);
    }
    
    // Helper method to create approved applications
    private Application createApprovedApplication(String company, int shares, double amount, Object document) {
        Application app = new Application(company, shares, amount, document);
        app.setStatus(ApplicationStatus.APPROVED);
        return app;
    }
}