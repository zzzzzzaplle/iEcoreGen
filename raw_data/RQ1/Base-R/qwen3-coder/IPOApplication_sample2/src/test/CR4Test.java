import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    private IPOApplicationSystem system;
    
    @Before
    public void setUp() {
        system = new IPOApplicationSystem();
    }
    
    @Test
    public void testCase1_noApprovedRequests() {
        // Setup: Create customer C201 with pending and rejected applications
        Customer customer = new Customer();
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmailAddress("e.chen@example.com");
        customer.setTelephoneNumber("555-1212");
        customer.setEligibleForIPO(true);
        
        // Create pending application
        IPOApplication pendingApp = new IPOApplication();
        pendingApp.setCompanyName("TechInc");
        pendingApp.setNumberOfShares(10);
        pendingApp.setAmountOfMoney(1500.0);
        pendingApp.setDocument("QT-3001");
        // Application remains pending (not reviewed)
        
        // Create rejected application
        IPOApplication rejectedApp = new IPOApplication();
        rejectedApp.setCompanyName("BioMed");
        rejectedApp.setNumberOfShares(10);
        rejectedApp.setAmountOfMoney(2000.0);
        rejectedApp.setDocument("QT-3002");
        rejectedApp.setRejected(true); // Mark as rejected
        
        // Add applications to customer
        customer.getApplications().add(pendingApp);
        customer.getApplications().add(rejectedApp);
        
        // Test: Query total approved amount
        double result = system.getTotalApprovedAmount(customer);
        
        // Verify: Expected output is 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_singleApproval() {
        // Setup: Create customer C202 with one approved application
        Customer customer = new Customer();
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmailAddress("r.johnson@example.com");
        customer.setTelephoneNumber("555-2323");
        
        // Create approved application
        IPOApplication approvedApp = new IPOApplication();
        approvedApp.setCompanyName("SolarMax");
        approvedApp.setNumberOfShares(84);
        approvedApp.setAmountOfMoney(4200.0);
        approvedApp.setDocument("SM-2024-Q1");
        approvedApp.setApproved(true); // Mark as approved
        
        // Add application to customer
        customer.getApplications().add(approvedApp);
        
        // Test: Query total approved amount
        double result = system.getTotalApprovedAmount(customer);
        
        // Verify: Expected output is 4,200
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleApprovalsDifferentFirms() {
        // Setup: Create customer C203 with multiple approved applications
        Customer customer = new Customer();
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmailAddress("s.williams@example.com");
        customer.setTelephoneNumber("555-3434");
        
        // Create first approved application
        IPOApplication app1 = new IPOApplication();
        app1.setCompanyName("QuantumTech");
        app1.setNumberOfShares(40);
        app1.setAmountOfMoney(2000.0);
        app1.setDocument("SM-2024-Q3004");
        app1.setApproved(true);
        
        // Create second approved application
        IPOApplication app2 = new IPOApplication();
        app2.setCompanyName("Neuralink");
        app2.setNumberOfShares(70);
        app2.setAmountOfMoney(3500.0);
        app2.setDocument("SM-2024-Q3005");
        app2.setApproved(true);
        
        // Add applications to customer
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        
        // Test: Query total approved amount
        double result = system.getTotalApprovedAmount(customer);
        
        // Verify: Expected output is 5,500
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_largePortfolio() {
        // Setup: Create customer C204 with multiple approved applications
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmailAddress("j.wilson@vip.example.com");
        customer.setTelephoneNumber("555-4545");
        
        // Create 5 approved applications, each with $10,000
        IPOApplication app1 = new IPOApplication();
        app1.setCompanyName("TechGiant");
        app1.setNumberOfShares(200);
        app1.setAmountOfMoney(10000.0);
        app1.setDocument("SM-3006");
        app1.setApproved(true);
        
        IPOApplication app2 = new IPOApplication();
        app2.setCompanyName("AutoFuture");
        app2.setNumberOfShares(250);
        app2.setAmountOfMoney(10000.0);
        app2.setDocument("SM-3007");
        app2.setApproved(true);
        
        IPOApplication app3 = new IPOApplication();
        app3.setCompanyName("AeroSpace");
        app3.setNumberOfShares(125);
        app3.setAmountOfMoney(10000.0);
        app3.setDocument("SM-3008");
        app3.setApproved(true);
        
        IPOApplication app4 = new IPOApplication();
        app4.setCompanyName("BioGenius");
        app4.setNumberOfShares(500);
        app4.setAmountOfMoney(10000.0);
        app4.setDocument("SM-3009");
        app4.setApproved(true);
        
        IPOApplication app5 = new IPOApplication();
        app5.setCompanyName("GreenEnergy");
        app5.setNumberOfShares(200);
        app5.setAmountOfMoney(10000.0);
        app5.setDocument("SM-3010");
        app5.setApproved(true);
        
        // Add applications to customer
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        customer.getApplications().add(app4);
        customer.getApplications().add(app5);
        
        // Test: Query total approved amount
        double result = system.getTotalApprovedAmount(customer);
        
        // Verify: Expected output is 50,000
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_approvalsPlusPending() {
        // Setup: Create customer C205 with approved and pending applications
        Customer customer = new Customer();
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmailAddress("o.brown@example.com");
        customer.setTelephoneNumber("555-5656");
        
        // Create approved applications
        IPOApplication approvedApp1 = new IPOApplication();
        approvedApp1.setCompanyName("CloudServ");
        approvedApp1.setNumberOfShares(100);
        approvedApp1.setAmountOfMoney(3000.0);
        approvedApp1.setDocument("SM-3011");
        approvedApp1.setApproved(true);
        
        IPOApplication approvedApp2 = new IPOApplication();
        approvedApp2.setCompanyName("DataCore");
        approvedApp2.setNumberOfShares(20);
        approvedApp2.setAmountOfMoney(2750.0);
        approvedApp2.setDocument("SM-3012");
        approvedApp2.setApproved(true);
        
        IPOApplication approvedApp3 = new IPOApplication();
        approvedApp3.setCompanyName("AI Ventures");
        approvedApp3.setNumberOfShares(30);
        approvedApp3.setAmountOfMoney(3000.0);
        approvedApp3.setDocument("SM-3013");
        approvedApp3.setApproved(true);
        
        // Create pending applications (not reviewed)
        IPOApplication pendingApp1 = new IPOApplication();
        pendingApp1.setCompanyName("NanoTech");
        pendingApp1.setNumberOfShares(10);
        pendingApp1.setAmountOfMoney(600.0);
        pendingApp1.setDocument("SM-3014");
        // Not approved or rejected - remains pending
        
        IPOApplication pendingApp2 = new IPOApplication();
        pendingApp2.setCompanyName("RoboWorks");
        pendingApp2.setNumberOfShares(50);
        pendingApp2.setAmountOfMoney(600.0);
        pendingApp2.setDocument("SM-3015");
        // Not approved or rejected - remains pending
        
        // Add all applications to customer
        customer.getApplications().add(approvedApp1);
        customer.getApplications().add(approvedApp2);
        customer.getApplications().add(approvedApp3);
        customer.getApplications().add(pendingApp1);
        customer.getApplications().add(pendingApp2);
        
        // Test: Query total approved amount
        double result = system.getTotalApprovedAmount(customer);
        
        // Verify: Expected output is 8,750 (only approved applications counted)
        assertEquals(8750.0, result, 0.001);
    }
}