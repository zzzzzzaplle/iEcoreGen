import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Customer customer;
    
    @Before
    public void setUp() {
        customer = new Customer();
    }
    
    @Test
    public void testCase1_noApplicationsAtAll() {
        // Test Case 1: "No applications at all"
        // Input: Customer "C101" requests a count summary.
        // Setup:
        // 1. Customer "C101" (named "Thomas Anderson", email "t.anderson@example.com", phone "555-0101", can apply for IPO) 
        // 2. No IPO requests have ever been filed.
        // Expected Output: 0
        
        // Arrange
        customer.setName("Thomas");
        customer.setSurname("Anderson");
        customer.setEmail("t.anderson@example.com");
        customer.setTelephone("555-0101");
        
        // Act
        int result = customer.getApplicationCountSummary();
        
        // Assert
        assertEquals("Customer with no applications should return 0", 0, result);
    }
    
    @Test
    public void testCase2_singlePendingRequest() {
        // Test Case 2: "Single pending request"
        // Input: Customer "C102" asks for the total number of filings.
        // Setup:
        // 1. Customer "C102" (named "Lisa Rodriguez", email "l.rodriguez@example.com", phone "555-0202", can apply for IPO)
        // 2. One record exists in pending status:
        //     Application ID: "APP-2024-001"
        //     Company: "QuantumTech" (quantumtech@gmail.com)
        //     Shares: 50 ($2,500)
        //     Document: 'QT-2024-FormA'
        //     Status: approval
        // Expected Output: 1
        
        // Arrange
        customer.setName("Lisa");
        customer.setSurname("Rodriguez");
        customer.setEmail("l.rodriguez@example.com");
        customer.setTelephone("555-0202");
        
        // Create and add pending application
        IpoApplication application = new IpoApplication();
        application.setCompanyName("QuantumTech");
        application.setShares(50);
        application.setAmount(2500.0);
        application.setDocument("QT-2024-FormA");
        // Status remains pending (neither approved nor rejected)
        
        List<IpoApplication> applications = new ArrayList<>();
        applications.add(application);
        customer.setApplications(applications);
        
        // Act
        int result = customer.getApplicationCountSummary();
        
        // Assert
        assertEquals("Customer with one pending application should return 0", 0, result);
    }
    
    @Test
    public void testCase3_mixOfApprovedAndRejected() {
        // Test Case 3: "Mix of approved and rejected"
        // Input: Customer "C103" checks total filings.
        // Setup:
        // 1. Customer "C103" (named "David Kim", email "d.kim@example.com", phone "555-0303", can not apply for IPO) 
        // 2. Two APPROVAL records and one REJECTED record are stored:
        // - Approved applications:
        //     "APP-2023-101" (Neuralink, 100 shares/$10,000, Document: 'QT-22023-101')
        //     "APP-2023-102" (SpaceY, 30 shares/$15,000, Document: 'QT-2023-102')
        // - Rejected application:
        //     "APP-2024-002" (BioGen, 20 shares/$1,000, Document: 'QT-2024-002')
        // Expected Output: 3
        
        // Arrange
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-0303");
        customer.setFailedAttempts(3); // Customer cannot apply for IPO
        
        List<IpoApplication> applications = new ArrayList<>();
        
        // Create approved applications
        IpoApplication app1 = new IpoApplication();
        app1.setCompanyName("Neuralink");
        app1.setShares(100);
        app1.setAmount(10000.0);
        app1.setDocument("QT-22023-101");
        app1.setApproved(true);
        applications.add(app1);
        
        IpoApplication app2 = new IpoApplication();
        app2.setCompanyName("SpaceY");
        app2.setShares(30);
        app2.setAmount(15000.0);
        app2.setDocument("QT-2023-102");
        app2.setApproved(true);
        applications.add(app2);
        
        // Create rejected application
        IpoApplication app3 = new IpoApplication();
        app3.setCompanyName("BioGen");
        app3.setShares(20);
        app3.setAmount(1000.0);
        app3.setDocument("QT-2024-002");
        app3.setRejected(true);
        applications.add(app3);
        
        customer.setApplications(applications);
        
        // Act
        int result = customer.getApplicationCountSummary();
        
        // Assert
        assertEquals("Customer with 2 approved and 1 rejected applications should return 3", 3, result);
    }
    
    @Test
    public void testCase4_fiveHistoricalRequests() {
        // Test Case 4: "Five historical requests"
        // Input: Customer "C104" queries the overall count.
        // Setup:
        // 1. Customer "C104" (named "Emma Wilson", email "e.wilson@example.com", phone "555-0404", can apply for IPO)
        // 2. Five records exist: 1 APPROVAL, 2 REJECTED, 2 pending:
        // - APPROVED: "APP-2023-105" (RoboCorp, 100 shares/$10,000, Document: 'QT-2023-105')
        // - REJECTED:
        //     "APP-2023-106" (AI Ventures, 100 shares/$10,000, Document: 'QT-2023-106')
        //     "APP-2024-003" (NanoMed, 100 shares/$10,000, Document: 'QT-2024-003')
        // - PENDING:
        //     "APP-2024-004" (GreenEnergy, 100 shares/$10,000, Document: 'QT-2024-004')
        //     "APP-2024-005" (CloudScale, 100 shares/$10,000, Document: 'QT-2024-005')
        // Expected Output: 3
        
        // Arrange
        customer.setName("Emma");
        customer.setSurname("Wilson");
        customer.setEmail("e.wilson@example.com");
        customer.setTelephone("555-0404");
        
        List<IpoApplication> applications = new ArrayList<>();
        
        // Create approved application
        IpoApplication app1 = new IpoApplication();
        app1.setCompanyName("RoboCorp");
        app1.setShares(100);
        app1.setAmount(10000.0);
        app1.setDocument("QT-2023-105");
        app1.setApproved(true);
        applications.add(app1);
        
        // Create rejected applications
        IpoApplication app2 = new IpoApplication();
        app2.setCompanyName("AI Ventures");
        app2.setShares(100);
        app2.setAmount(10000.0);
        app2.setDocument("QT-2023-106");
        app2.setRejected(true);
        applications.add(app2);
        
        IpoApplication app3 = new IpoApplication();
        app3.setCompanyName("NanoMed");
        app3.setShares(100);
        app3.setAmount(10000.0);
        app3.setDocument("QT-2024-003");
        app3.setRejected(true);
        applications.add(app3);
        
        // Create pending applications (neither approved nor rejected)
        IpoApplication app4 = new IpoApplication();
        app4.setCompanyName("GreenEnergy");
        app4.setShares(100);
        app4.setAmount(10000.0);
        app4.setDocument("QT-2024-004");
        applications.add(app4);
        
        IpoApplication app5 = new IpoApplication();
        app5.setCompanyName("CloudScale");
        app5.setShares(100);
        app5.setAmount(10000.0);
        app5.setDocument("QT-2024-005");
        applications.add(app5);
        
        customer.setApplications(applications);
        
        // Act
        int result = customer.getApplicationCountSummary();
        
        // Assert
        assertEquals("Customer with 1 approved, 2 rejected, and 2 pending applications should return 3", 3, result);
    }
    
    @Test
    public void testCase5_allRequestsCanceled() {
        // Test Case 5: "All requests canceled"
        // Input: Customer "C105" asks for the figure.
        // Setup:
        // 1. Customer "C105" (named "James Chen", email "j.chen@example.com", phone "555-0505", can apply for IPO)
        // 2. Create a pending application "APP-1010" : Applied for 10 shares ($5000) in "Cloud" (email: Cloud@gmail.com), Document: 'QT-1010'
        // 3. Cancel application "APP-1010" to "Cloud"
        // Expected Output: 0
        
        // Arrange
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephone("555-0505");
        
        // Create pending application
        IpoApplication application = new IpoApplication();
        application.setCompanyName("Cloud");
        application.setShares(10);
        application.setAmount(5000.0);
        application.setDocument("QT-1010");
        // Status remains pending (neither approved nor rejected)
        
        List<IpoApplication> applications = new ArrayList<>();
        applications.add(application);
        customer.setApplications(applications);
        
        // Cancel the pending application
        boolean cancellationResult = customer.cancelPendingApplication("Cloud");
        assertTrue("Cancellation should succeed", cancellationResult);
        
        // Act
        int result = customer.getApplicationCountSummary();
        
        // Assert
        assertEquals("Customer with canceled application should return 0", 0, result);
    }
}