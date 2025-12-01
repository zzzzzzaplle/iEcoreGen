import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    private Customer customer;
    
    @Before
    public void setUp() {
        customer = new Customer();
    }
    
    @Test
    public void testCase1_NoApprovedRequests() {
        // Setup: Customer "C201" (named "Emily Chen", email "e.chen@example.com", phone "555-1212")
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephone("555-1212");
        customer.setRestricted(false);
        
        // Create PENDING application: "APP-3001": PENDING (10 shares, $1,500, TechInc, document: "QT-3001")
        IPOApplication pendingApp = new IPOApplication();
        pendingApp.setCompanyName("TechInc");
        pendingApp.setShares(10);
        pendingApp.setAmount(1500.0);
        pendingApp.setDocument("QT-3001");
        pendingApp.setStatus(IPOApplication.Status.PENDING);
        pendingApp.setCustomer(customer);
        
        // Create REJECTED application: "APP-3002": REJECTED (10 shares, $2,000, BioMed, document: "QT-3002")
        IPOApplication rejectedApp = new IPOApplication();
        rejectedApp.setCompanyName("BioMed");
        rejectedApp.setShares(10);
        rejectedApp.setAmount(2000.0);
        rejectedApp.setDocument("QT-3002");
        rejectedApp.setStatus(IPOApplication.Status.REJECTED);
        rejectedApp.setCustomer(customer);
        
        // Add applications to customer
        customer.getApplications().add(pendingApp);
        customer.getApplications().add(rejectedApp);
        
        // Test: Customer "C201" requests total approved amount
        double result = customer.getTotalApprovedAmount();
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_SingleApproval() {
        // Setup: Customer "C202" (named "Robert Johnson", email "r.johnson@example.com", phone "555-2323")
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephone("555-2323");
        customer.setRestricted(false);
        
        // Create approved application: "APP-3003": SolarMax, $4,200, 84 shares, Document: 'SM-2024-Q1'
        IPOApplication approvedApp = new IPOApplication();
        approvedApp.setCompanyName("SolarMax");
        approvedApp.setShares(84);
        approvedApp.setAmount(4200.0);
        approvedApp.setDocument("SM-2024-Q1");
        approvedApp.setStatus(IPOApplication.Status.APPROVED);
        approvedApp.setCustomer(customer);
        
        // Add application to customer
        customer.getApplications().add(approvedApp);
        
        // Test: Customer "C202" checks the total approved sum
        double result = customer.getTotalApprovedAmount();
        
        // Expected Output: 4,200
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleApprovalsDifferentFirms() {
        // Setup: Customer "C203" (named "Sophia Williams", email "s.williams@example.com", phone "555-3434")
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        customer.setRestricted(false);
        
        // Create first approved application: "APP-3004": QuantumTech, $2,000, 40 shares, Document: 'SM-2024-Q3004'
        IPOApplication app1 = new IPOApplication();
        app1.setCompanyName("QuantumTech");
        app1.setShares(40);
        app1.setAmount(2000.0);
        app1.setDocument("SM-2024-Q3004");
        app1.setStatus(IPOApplication.Status.APPROVED);
        app1.setCustomer(customer);
        
        // Create second approved application: "APP-3005": Neuralink, $3,500, 70 shares, Document: 'SM-2024-Q3005'
        IPOApplication app2 = new IPOApplication();
        app2.setCompanyName("Neuralink");
        app2.setShares(70);
        app2.setAmount(3500.0);
        app2.setDocument("SM-2024-Q3005");
        app2.setStatus(IPOApplication.Status.APPROVED);
        app2.setCustomer(customer);
        
        // Add applications to customer
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        
        // Test: Customer "C203" asks for the combined figure
        double result = customer.getTotalApprovedAmount();
        
        // Expected Output: 5,500
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_LargePortfolio() {
        // Setup: Customer "C204" (named "James Wilson", email "j.wilson@vip.example.com", phone "555-4545")
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        customer.setRestricted(false);
        
        // Create 5 approved applications, each $10,000
        String[] companies = {"TechGiant", "AutoFuture", "AeroSpace", "BioGenius", "GreenEnergy"};
        int[] shares = {200, 250, 125, 500, 200};
        String[] documents = {"SM-3006", "SM-3007", "SM-3008", "SM-3009", "SM-3010"};
        
        for (int i = 0; i < 5; i++) {
            IPOApplication app = new IPOApplication();
            app.setCompanyName(companies[i]);
            app.setShares(shares[i]);
            app.setAmount(10000.0);
            app.setDocument(documents[i]);
            app.setStatus(IPOApplication.Status.APPROVED);
            app.setCustomer(customer);
            customer.getApplications().add(app);
        }
        
        // Test: Customer "C204" requests total amount
        double result = customer.getTotalApprovedAmount();
        
        // Expected Output: 50,000
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_ApprovalsPlusPending() {
        // Setup: Customer "C205" (named "Olivia Brown", email "o.brown@example.com", phone "555-5656")
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephone("555-5656");
        customer.setRestricted(false);
        
        // Create approved applications
        IPOApplication app1 = new IPOApplication(); // APP-3011: 100 shares, $3,000 (CloudServ)
        app1.setCompanyName("CloudServ");
        app1.setShares(100);
        app1.setAmount(3000.0);
        app1.setDocument("SM-3011");
        app1.setStatus(IPOApplication.Status.APPROVED);
        app1.setCustomer(customer);
        
        IPOApplication app2 = new IPOApplication(); // APP-3012: 20 shares, $2,750 (DataCore)
        app2.setCompanyName("DataCore");
        app2.setShares(20);
        app2.setAmount(2750.0);
        app2.setDocument("SM-3012");
        app2.setStatus(IPOApplication.Status.APPROVED);
        app2.setCustomer(customer);
        
        IPOApplication app3 = new IPOApplication(); // APP-3013: 30 shares, $3,000 (AI Ventures)
        app3.setCompanyName("AI Ventures");
        app3.setShares(30);
        app3.setAmount(3000.0);
        app3.setDocument("SM-3013");
        app3.setStatus(IPOApplication.Status.APPROVED);
        app3.setCustomer(customer);
        
        // Create pending applications (should not be included in total)
        IPOApplication pending1 = new IPOApplication(); // APP-3014: 10 shares, $600 (NanoTech)
        pending1.setCompanyName("NanoTech");
        pending1.setShares(10);
        pending1.setAmount(600.0);
        pending1.setDocument("SM-3014");
        pending1.setStatus(IPOApplication.Status.PENDING);
        pending1.setCustomer(customer);
        
        IPOApplication pending2 = new IPOApplication(); // APP-3015: 50 shares, $600 (RoboWorks)
        pending2.setCompanyName("RoboWorks");
        pending2.setShares(50);
        pending2.setAmount(600.0);
        pending2.setDocument("SM-3015");
        pending2.setStatus(IPOApplication.Status.PENDING);
        pending2.setCustomer(customer);
        
        // Add all applications to customer
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        customer.getApplications().add(pending1);
        customer.getApplications().add(pending2);
        
        // Test: Customer "C205" asks for aggregate approved spending
        double result = customer.getTotalApprovedAmount();
        
        // Expected Output: 8,750
        assertEquals(8750.0, result, 0.001);
    }
}