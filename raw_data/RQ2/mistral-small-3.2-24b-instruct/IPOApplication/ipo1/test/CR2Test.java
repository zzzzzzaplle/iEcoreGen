package edu.ipo.ipo1.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.ipo.*;

public class CR2Test {
    
    private IpoFactory factory;
    private Customer customerC007;
    private Customer customerC008;
    private Customer customerC009;
    private Customer customerC010;
    private Customer customerC011;
    private Company companySolarMax;
    private Company companyHealthPlus;
    private Company companyHealth;
    private Company companyCloud;
    private Document documentS;
    private Document documentH;
    private Application app1001;
    private Application app1002;
    private Application app1003;
    private Application app1004;
    private Application app1005;
    
    @Before
    public void setUp() {
        factory = IpoFactory.eINSTANCE;
        
        // Create companies
        companySolarMax = factory.createCompany();
        companySolarMax.setName("SolarMax");
        companySolarMax.setEmail("solarmax@gmail.com");
        
        companyHealthPlus = factory.createCompany();
        companyHealthPlus.setName("HealthPlus");
        companyHealthPlus.setEmail("healthplus@gmail.com");
        
        companyHealth = factory.createCompany();
        companyHealth.setName("Health");
        companyHealth.setEmail("health@gmail.com");
        
        companyCloud = factory.createCompany();
        companyCloud.setName("Cloud");
        companyCloud.setEmail("Cloud@gmail.com");
        
        // Create documents
        documentS = factory.createDocument();
        documentH = factory.createDocument();
        
        // Create customers
        customerC007 = factory.createCustomer();
        customerC007.setName("Michael");
        customerC007.setSurname("Brown");
        customerC007.setEmail("m.brown@example.com");
        customerC007.setTelephone("555-1122");
        customerC007.setCanApplyForIPO(true);
        
        customerC008 = factory.createCustomer();
        customerC008.setName("Olivia");
        customerC008.setSurname("Lee");
        customerC008.setEmail("olivia.l@example.com");
        customerC008.setTelephone("555-3344");
        customerC008.setCanApplyForIPO(true);
        
        customerC009 = factory.createCustomer();
        customerC009.setName("Daniel");
        customerC009.setSurname("Kim");
        customerC009.setEmail("d.kim@example.com");
        customerC009.setTelephone("555-5566");
        customerC009.setCanApplyForIPO(true);
        
        customerC010 = factory.createCustomer();
        customerC010.setName("Sophie");
        customerC010.setSurname("Zhang");
        customerC010.setEmail("s.zhang@example.com");
        customerC010.setTelephone("555-7788");
        customerC010.setCanApplyForIPO(true);
        
        customerC011 = factory.createCustomer();
        customerC011.setName("William");
        customerC011.setSurname("Wang");
        customerC011.setEmail("will.w@example.com");
        customerC011.setTelephone("555-9900");
        customerC011.setCanApplyForIPO(false);
        
        // Create applications
        app1001 = factory.createApplication();
        app1001.setShare(10);
        app1001.setAmountOfMoney(200.0);
        app1001.setStatus(ApplicationStatus.PENDING);
        app1001.setCustomer(customerC007);
        app1001.setCompany(companySolarMax);
        app1001.setAllowance(documentS);
        customerC007.getApplications().add(app1001);
        
        app1002 = factory.createApplication();
        app1002.setShare(10);
        app1002.setAmountOfMoney(5000.0);
        app1002.setStatus(ApplicationStatus.PENDING);
        app1002.setCustomer(customerC008);
        app1002.setCompany(companyHealthPlus);
        app1002.setAllowance(documentH);
        customerC008.getApplications().add(app1002);
        
        app1003 = factory.createApplication();
        app1003.setShare(10);
        app1003.setAmountOfMoney(5000.0);
        app1003.setStatus(ApplicationStatus.APPROVAL);
        app1003.setCustomer(customerC009);
        app1003.setCompany(companyHealthPlus);
        app1003.setAllowance(documentH);
        customerC009.getApplications().add(app1003);
        
        app1004 = factory.createApplication();
        app1004.setShare(10);
        app1004.setAmountOfMoney(5000.0);
        app1004.setStatus(ApplicationStatus.REJECTED);
        app1004.setCustomer(customerC010);
        app1004.setCompany(companyHealth);
        app1004.setAllowance(documentH);
        customerC010.getApplications().add(app1004);
        
        app1005 = factory.createApplication();
        app1005.setShare(10);
        app1005.setAmountOfMoney(5000.0);
        app1005.setStatus(ApplicationStatus.PENDING);
        app1005.setCustomer(customerC011);
        app1005.setCompany(companyCloud);
        app1005.setAllowance(documentH);
        customerC011.getApplications().add(app1005);
    }
    
    @Test
    public void testCase1_ApprovePendingRequest() {
        // Test Case 1: "Approve pending request"
        // Input: Bank approves application "APP-1001" for company "SolarMax"
        
        // Verify initial state
        assertEquals(ApplicationStatus.PENDING, app1001.getStatus());
        assertTrue(customerC007.isCanApplyForIPO());
        
        // Execute approve operation
        boolean result = app1001.approve();
        
        // Verify expected output
        assertTrue("Approval should succeed for pending application", result);
        assertEquals("Status should change to APPROVAL", ApplicationStatus.APPROVAL, app1001.getStatus());
        
        // Verify emails were sent
        assertNotNull("Emails list should not be null", app1001.getEmails());
        assertEquals("Two emails should be created", 2, app1001.getEmails().size());
    }
    
    @Test
    public void testCase2_RejectPendingRequest() {
        // Test Case 2: "Reject pending request"
        // Input: Bank rejects application "APP-1002" for "HealthPlus"
        
        // Verify initial state
        assertEquals(ApplicationStatus.PENDING, app1002.getStatus());
        assertTrue(customerC008.isCanApplyForIPO());
        
        // Execute reject operation
        boolean result = app1002.reject();
        
        // Verify expected output
        assertTrue("Rejection should succeed for pending application", result);
        assertEquals("Status should change to REJECTED", ApplicationStatus.REJECTED, app1002.getStatus());
        
        // Verify rejection email was sent
        assertNotNull("Emails list should not be null", app1002.getEmails());
        assertEquals("One rejection email should be created", 1, app1002.getEmails().size());
    }
    
    @Test
    public void testCase3_ApproveAlreadyApprovedRecord() {
        // Test Case 3: "Approve already approved record"
        // Input: Bank attempts to re-approve application "APP-1003"
        
        // Verify initial state
        assertEquals(ApplicationStatus.APPROVAL, app1003.getStatus());
        
        // Execute approve operation on already approved application
        boolean result = app1003.approve();
        
        // Verify expected output
        assertFalse("Approval should fail for already approved application", result);
        assertEquals("Status should remain APPROVAL", ApplicationStatus.APPROVAL, app1003.getStatus());
    }
    
    @Test
    public void testCase4_RejectAlreadyRejectedRecord() {
        // Test Case 4: "Reject already rejected record"
        // Input: Bank tries to reject application "APP-1004"
        
        // Verify initial state
        assertEquals(ApplicationStatus.REJECTED, app1004.getStatus());
        
        // Execute reject operation on already rejected application
        boolean result = app1004.reject();
        
        // Verify expected output
        assertFalse("Rejection should fail for already rejected application", result);
        assertEquals("Status should remain REJECTED", ApplicationStatus.REJECTED, app1004.getStatus());
    }
    
    @Test
    public void testCase5_ApproveRecordTiedToIneligibleCustomer() {
        // Test Case 5: "Approve record tied to ineligible customer"
        // Input: Bank processes application "APP-1005"
        
        // Verify initial state
        assertEquals(ApplicationStatus.PENDING, app1005.getStatus());
        assertFalse("Customer should not be eligible for IPO", customerC011.isCanApplyForIPO());
        
        // Execute approve operation for ineligible customer
        boolean result = app1005.approve();
        
        // Verify expected output
        assertFalse("Approval should fail for ineligible customer", result);
        assertEquals("Status should remain PENDING", ApplicationStatus.PENDING, app1005.getStatus());
    }
}