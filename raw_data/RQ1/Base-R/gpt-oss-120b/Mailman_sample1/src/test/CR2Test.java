import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR2Test {
    
    private MailService mailService;
    
    @Before
    public void setUp() {
        mailService = new MailService();
    }
    
    @Test
    public void testCase1_RetrieveDeliveriesForBusyArea() {
        // SetUp: Create GeographicalArea "Uptown"
        GeographicalArea uptown = new GeographicalArea("A1", "Uptown");
        mailService.addArea(uptown);
        
        // SetUp: Add Mailman "James" to Uptown
        Mailman james = new Mailman("M1", "James", null);
        mailService.addMailman("A1", james);
        
        // SetUp: Add Inhabitants "Grace" and "Henry" to Uptown
        Inhabitant grace = new Inhabitant("I1", "Grace", null);
        Inhabitant henry = new Inhabitant("I2", "Henry", null);
        mailService.addInhabitant("A1", grace);
        mailService.addInhabitant("A1", henry);
        
        // SetUp: Create and assign Letter1 for Grace (James)
        Letter letter1 = new Letter("L1", grace, "Subject1", "Content1");
        mailService.registerMailItem(letter1);
        mailService.assignMailItemToMailman("L1", "M1");
        
        // SetUp: Create and assign Parcel1 for Henry (James)
        Parcel parcel1 = new Parcel("P1", henry, 2.5, "Package");
        mailService.registerMailItem(parcel1);
        mailService.assignMailItemToMailman("P1", "M1");
        
        // Action: Retrieve all deliveries for Uptown
        List<DeliveryInfo> deliveries = mailService.getDeliveriesByArea("A1");
        
        // Expected Output: List containing both mail items with James as carrier
        assertEquals(2, deliveries.size());
        
        // Verify first delivery (Letter1 for Grace)
        DeliveryInfo delivery1 = deliveries.get(0);
        assertEquals("L1", delivery1.getMailItem().getId());
        assertEquals("M1", delivery1.getMailman().getId());
        assertEquals("I1", delivery1.getAddressee().getId());
        
        // Verify second delivery (Parcel1 for Henry)
        DeliveryInfo delivery2 = deliveries.get(1);
        assertEquals("P1", delivery2.getMailItem().getId());
        assertEquals("M1", delivery2.getMailman().getId());
        assertEquals("I2", delivery2.getAddressee().getId());
    }
    
    @Test
    public void testCase2_RetrieveDeliveriesForEmptyArea() {
        // SetUp: Create empty GeographicalArea "Suburb"
        GeographicalArea suburb = new GeographicalArea("A2", "Suburb");
        mailService.addArea(suburb);
        
        // Action: Retrieve deliveries for Suburb
        List<DeliveryInfo> deliveries = mailService.getDeliveriesByArea("A2");
        
        // Expected Output: Empty list
        assertTrue(deliveries.isEmpty());
    }
    
    @Test
    public void testCase3_RetrieveDeliveriesForAreaWithUnassignedMail() {
        // SetUp: Create GeographicalArea "Midtown"
        GeographicalArea midtown = new GeographicalArea("A3", "Midtown");
        mailService.addArea(midtown);
        
        // SetUp: Add Inhabitant "Oscar" to Midtown
        Inhabitant oscar = new Inhabitant("I3", "Oscar", null);
        mailService.addInhabitant("A3", oscar);
        
        // SetUp: Create Letter4 for Oscar (unassigned)
        Letter letter4 = new Letter("L4", oscar, "Subject4", "Content4");
        mailService.registerMailItem(letter4);
        // Note: Not assigning to any mailman
        
        // Action: Retrieve deliveries for Midtown
        List<DeliveryInfo> deliveries = mailService.getDeliveriesByArea("A3");
        
        // Expected Output: Empty list (mail not assigned to any mailman)
        assertTrue(deliveries.isEmpty());
    }
    
    @Test
    public void testCase4_RetrieveDeliveriesForAreaWithMultipleMailmen() {
        // SetUp: Create GeographicalArea "Harbor"
        GeographicalArea harbor = new GeographicalArea("A4", "Harbor");
        mailService.addArea(harbor);
        
        // SetUp: Add Mailmen "Lucy" and "Rick" to Harbor
        Mailman lucy = new Mailman("M2", "Lucy", null);
        Mailman rick = new Mailman("M3", "Rick", null);
        mailService.addMailman("A4", lucy);
        mailService.addMailman("A4", rick);
        
        // SetUp: Add Inhabitant "Tina" to Harbor
        Inhabitant tina = new Inhabitant("I4", "Tina", null);
        mailService.addInhabitant("A4", tina);
        
        // SetUp: Create and assign Letter5 for Tina (Lucy)
        Letter letter5 = new Letter("L5", tina, "Subject5", "Content5");
        mailService.registerMailItem(letter5);
        mailService.assignMailItemToMailman("L5", "M2");
        
        // SetUp: Create and assign Parcel3 for Tina (Rick)
        Parcel parcel3 = new Parcel("P3", tina, 1.8, "Small package");
        mailService.registerMailItem(parcel3);
        mailService.assignMailItemToMailman("P3", "M3");
        
        // Action: Retrieve deliveries for Harbor
        List<DeliveryInfo> deliveries = mailService.getDeliveriesByArea("A4");
        
        // Expected Output: List containing both mail items with respective carriers
        assertEquals(2, deliveries.size());
        
        // Verify deliveries contain correct mailman assignments
        boolean foundLucyDelivery = false;
        boolean foundRickDelivery = false;
        
        for (DeliveryInfo delivery : deliveries) {
            if ("L5".equals(delivery.getMailItem().getId()) && "M2".equals(delivery.getMailman().getId())) {
                foundLucyDelivery = true;
            }
            if ("P3".equals(delivery.getMailItem().getId()) && "M3".equals(delivery.getMailman().getId())) {
                foundRickDelivery = true;
            }
        }
        
        assertTrue("Should contain delivery by Lucy", foundLucyDelivery);
        assertTrue("Should contain delivery by Rick", foundRickDelivery);
    }
    
    @Test
    public void testCase5_RetrieveDeliveriesForNonExistentArea() {
        // Action: Retrieve deliveries for non-existent "MountainView"
        List<DeliveryInfo> deliveries = mailService.getDeliveriesByArea("MountainView");
        
        // Expected Output: Empty list
        assertTrue(deliveries.isEmpty());
    }
}