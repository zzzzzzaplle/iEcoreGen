import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Customer customer1, customer2, customer3, customer4, customer5;
    private Company company1, company2, company3, company4, company5;
    private Document document1, document2, document3, document4, document5;
    private Application application1, application2, application3, application4, application5;

    @Before
    public void setUp() {
        // Test Case 1 Setup
        customer1 = new Customer();
        customer1.setName("Michael");
        customer1.setSurname("Brown");
        customer1.setEmail("m.brown@example.com");
        customer1.setTelephone("555-1122");
        customer1.setCanApplyForIPO(true);

        company1 = new Company();
        company1.setName("SolarMax");
        company1.setEmail("solarmax@gmail.com");

        document1 = new Document();

        application1 = new Application();
        application1.setShare(10);
        application1.setAmountOfMoney(200.0);
        application1.setStatus(ApplicationStatus.PENDING);
        application1.setCustomer(customer1);
        application1.setCompany(company1);
        application1.setAllowance(document1);

        customer1.getApplications().add(application1);

        // Test Case 2 Setup
        customer2 = new Customer();
        customer2.setName("Olivia");
        customer2.setSurname("Lee");
        customer2.setEmail("olivia.l@example.com");
        customer2.setTelephone("555-3344");
        customer2.setCanApplyForIPO(true);

        company2 = new Company();
        company2.setName("HealthPlus");
        company2.setEmail("healthplus@gmail.com");

        document2 = new Document();

        application2 = new Application();
        application2.setShare(10);
        application2.setAmountOfMoney(5000.0);
        application2.setStatus(ApplicationStatus.PENDING);
        application2.setCustomer(customer2);
        application2.setCompany(company2);
        application2.setAllowance(document2);

        customer2.getApplications().add(application2);

        // Test Case 3 Setup
        customer3 = new Customer();
        customer3.setName("Daniel");
        customer3.setSurname("Kim");
        customer3.setEmail("d.kim@example.com");
        customer3.setTelephone("555-5566");
        customer3.setCanApplyForIPO(true);

        company3 = new Company();
        company3.setName("HealthPlus");
        company3.setEmail("healthplus@gmail.com");

        document3 = new Document();

        application3 = new Application();
        application3.setShare(10);
        application3.setAmountOfMoney(5000.0);
        application3.setStatus(ApplicationStatus.APPROVAL);
        application3.setCustomer(customer3);
        application3.setCompany(company3);
        application3.setAllowance(document3);

        customer3.getApplications().add(application3);

        // Test Case 4 Setup
        customer4 = new Customer();
        customer4.setName("Sophie");
        customer4.setSurname("Zhang");
        customer4.setEmail("s.zhang@example.com");
        customer4.setTelephone("555-7788");
        customer4.setCanApplyForIPO(true);

        company4 = new Company();
        company4.setName("Health");
        company4.setEmail("health@gmail.com");

        document4 = new Document();

        application4 = new Application();
        application4.setShare(10);
        application4.setAmountOfMoney(5000.0);
        application4.setStatus(ApplicationStatus.REJECTED);
        application4.setCustomer(customer4);
        application4.setCompany(company4);
        application4.setAllowance(document4);

        customer4.getApplications().add(application4);

        // Test Case 5 Setup
        customer5 = new Customer();
        customer5.setName("William");
        customer5.setSurname("Wang");
        customer5.setEmail("will.w@example.com");
        customer5.setTelephone("555-9900");
        customer5.setCanApplyForIPO(false); // Not eligible

        company5 = new Company();
        company5.setName("Cloud");
        company5.setEmail("Cloud@gmail.com");

        document5 = new Document();

        application5 = new Application();
        application5.setShare(10);
        application5.setAmountOfMoney(5000.0);
        application5.setStatus(ApplicationStatus.PENDING);
        application5.setCustomer(customer5);
        application5.setCompany(company5);
        application5.setAllowance(document5);

        customer5.getApplications().add(application5);
    }

    @Test
    public void testCase1_approvePendingRequest() {
        // Input: Bank approves application for company "SolarMax"
        // Setup is done in @Before method
        // Expected Output: True (status changes to APPROVAL)
        
        boolean result = application1.approve();
        
        assertTrue("Application approval should succeed", result);
        assertEquals("Application status should be APPROVAL", ApplicationStatus.APPROVAL, application1.getStatus());
        
        // Check that emails were sent
        assertEquals("Two emails should be sent (to customer and company)", 2, application1.getEmails().size());
    }

    @Test
    public void testCase2_rejectPendingRequest() {
        // Input: Bank rejects application for "HealthPlus"
        // Setup is done in @Before method
        // Expected Output: True (status changes to REJECTED)
        
        boolean result = application2.reject();
        
        assertTrue("Application rejection should succeed", result);
        assertEquals("Application status should be REJECTED", ApplicationStatus.REJECTED, application2.getStatus());
        
        // Check that rejection email was sent
        assertEquals("One email should be sent (rejection notice)", 1, application2.getEmails().size());
        assertEquals("Email content should contain rejection message", 
                    "Your IPO application for HealthPlus has been rejected.", 
                    application2.getEmails().get(0).getContent());
    }

    @Test
    public void testCase3_approveAlreadyApprovedRecord() {
        // Input: Bank attempts to re-approve application "APP-1003"
        // Setup is done in @Before method (application3 is already approved)
        // Expected Output: False (cannot modify approved applications)
        
        boolean result = application3.approve();
        
        assertFalse("Cannot approve already approved application", result);
        assertEquals("Application status should remain APPROVAL", ApplicationStatus.APPROVAL, application3.getStatus());
    }

    @Test
    public void testCase4_rejectAlreadyRejectedRecord() {
        // Input: Bank tries to reject application "APP-1004"
        // Setup is done in @Before method (application4 is already rejected)
        // Expected Output: False (cannot modify final decisions)
        
        boolean result = application4.reject();
        
        assertFalse("Cannot reject already rejected application", result);
        assertEquals("Application status should remain REJECTED", ApplicationStatus.REJECTED, application4.getStatus());
    }

    @Test
    public void testCase5_approveRecordTiedToIneligibleCustomer() {
        // Input: Bank processes application "APP-1005"
        // Setup is done in @Before method (customer5 is not eligible)
        // Expected Output: False (must maintain eligibility throughout process)
        
        boolean result = application5.approve();
        
        assertFalse("Cannot approve application for ineligible customer", result);
        assertEquals("Application status should remain PENDING", ApplicationStatus.PENDING, application5.getStatus());
    }
}