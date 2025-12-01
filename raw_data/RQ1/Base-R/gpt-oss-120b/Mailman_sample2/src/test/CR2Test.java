import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR2Test {
    
    private MailDeliverySystem system;
    private GeographicalArea uptown;
    private GeographicalArea suburb;
    private GeographicalArea midtown;
    private GeographicalArea harbor;
    
    @Before
    public void setUp() {
        system = new MailDeliverySystem();
        
        // Create areas for reuse in tests
        uptown = new GeographicalArea("UPT1", "Uptown");
        suburb = new GeographicalArea("SUB1", "Suburb");
        midtown = new GeographicalArea("MID1", "Midtown");
        harbor = new GeographicalArea("HAR1", "Harbor");
        
        // Add areas to system
        system.addArea(uptown);
        system.addArea(suburb);
        system.addArea(midtown);
        system.addArea(harbor);
    }
    
    @Test
    public void testCase1_RetrieveDeliveriesForBusyArea() {
        // SetUp: Create GeographicalArea "Uptown"
        // SetUp: Add Mailman "James" to Uptown
        Mailman james = new Mailman("M001", "James");
        system.addMailman(uptown, james);
        
        // SetUp: Add Inhabitants "Grace" and "Henry" to Uptown
        Inhabitant grace = new Inhabitant("I001", "Grace");
        Inhabitant henry = new Inhabitant("I002", "Henry");
        system.addInhabitant(uptown, grace);
        system.addInhabitant(uptown, henry);
        
        // SetUp: Create and assign Letter1 for Grace (James)
        Letter letter1 = new Letter("L001", grace, "Test Subject", "Test Body");
        uptown.internalAddRegisteredMail(letter1);
        system.assignMailToMailman(letter1, james);
        
        // SetUp: Create and assign Parcel1 for Henry (James)
        Parcel parcel1 = new Parcel("P001", henry, 2.5, "Test Package");
        uptown.internalAddRegisteredMail(parcel1);
        system.assignMailToMailman(parcel1, james);
        
        // Action: Retrieve all deliveries for Uptown
        List<MailDeliverySystem.DeliveryInfo> deliveries = system.getDeliveriesByArea(uptown);
        
        // Expected Output: List containing both mail items with James as carrier
        assertEquals(2, deliveries.size());
        
        // Verify first delivery (Letter1)
        MailDeliverySystem.DeliveryInfo delivery1 = deliveries.get(0);
        assertEquals(letter1, delivery1.mail);
        assertEquals(james, delivery1.mailman);
        assertEquals(grace, delivery1.addressee);
        
        // Verify second delivery (Parcel1)
        MailDeliverySystem.DeliveryInfo delivery2 = deliveries.get(1);
        assertEquals(parcel1, delivery2.mail);
        assertEquals(james, delivery2.mailman);
        assertEquals(henry, delivery2.addressee);
    }
    
    @Test
    public void testCase2_RetrieveDeliveriesForEmptyArea() {
        // SetUp: Create empty GeographicalArea "Suburb" (already created in setUp)
        
        // Action: Retrieve deliveries for Suburb
        List<MailDeliverySystem.DeliveryInfo> deliveries = system.getDeliveriesByArea(suburb);
        
        // Expected Output: Empty list
        assertTrue(deliveries.isEmpty());
    }
    
    @Test
    public void testCase3_RetrieveDeliveriesForAreaWithUnassignedMail() {
        // SetUp: Create GeographicalArea "Midtown"
        // SetUp: Add Inhabitant "Oscar" to Midtown
        Inhabitant oscar = new Inhabitant("I003", "Oscar");
        system.addInhabitant(midtown, oscar);
        
        // SetUp: Create RegisteredLetter "Letter4" for Oscar (unassigned)
        Letter letter4 = new Letter("L004", oscar, "Unassigned Letter", "Body");
        midtown.internalAddRegisteredMail(letter4);
        // Note: Not assigned to any mailman
        
        // Action: Retrieve deliveries for Midtown
        List<MailDeliverySystem.DeliveryInfo> deliveries = system.getDeliveriesByArea(midtown);
        
        // Expected Output: Empty list (mail not assigned to any mailman)
        assertTrue(deliveries.isEmpty());
    }
    
    @Test
    public void testCase4_RetrieveDeliveriesForAreaWithMultipleMailmen() {
        // SetUp: Create GeographicalArea "Harbor"
        // SetUp: Add Mailmen "Lucy" and "Rick" to Harbor
        Mailman lucy = new Mailman("M002", "Lucy");
        Mailman rick = new Mailman("M003", "Rick");
        system.addMailman(harbor, lucy);
        system.addMailman(harbor, rick);
        
        // SetUp: Add Inhabitant "Tina" to Harbor
        Inhabitant tina = new Inhabitant("I004", "Tina");
        system.addInhabitant(harbor, tina);
        
        // SetUp: Create and assign Letter5 for Tina (Lucy)
        Letter letter5 = new Letter("L005", tina, "Letter Subject", "Letter Body");
        harbor.internalAddRegisteredMail(letter5);
        system.assignMailToMailman(letter5, lucy);
        
        // SetUp: Create and assign Parcel3 for Tina (Rick)
        Parcel parcel3 = new Parcel("P003", tina, 1.8, "Small Package");
        harbor.internalAddRegisteredMail(parcel3);
        system.assignMailToMailman(parcel3, rick);
        
        // Action: Retrieve deliveries for Harbor
        List<MailDeliverySystem.DeliveryInfo> deliveries = system.getDeliveriesByArea(harbor);
        
        // Expected Output: List containing both mail items with respective carriers
        assertEquals(2, deliveries.size());
        
        // Verify deliveries contain both mail items with correct assignments
        boolean foundLucyDelivery = false;
        boolean foundRickDelivery = false;
        
        for (MailDeliverySystem.DeliveryInfo delivery : deliveries) {
            if (delivery.mailman.equals(lucy) && delivery.mail.equals(letter5)) {
                foundLucyDelivery = true;
                assertEquals(tina, delivery.addressee);
            }
            if (delivery.mailman.equals(rick) && delivery.mail.equals(parcel3)) {
                foundRickDelivery = true;
                assertEquals(tina, delivery.addressee);
            }
        }
        
        assertTrue(foundLucyDelivery);
        assertTrue(foundRickDelivery);
    }
    
    @Test
    public void testCase5_RetrieveDeliveriesForNonExistentArea() {
        // Action: Retrieve deliveries for non-existent "MountainView"
        GeographicalArea mountainView = new GeographicalArea("MTN1", "MountainView");
        // Note: MountainView was not added to the system
        
        List<MailDeliverySystem.DeliveryInfo> deliveries = system.getDeliveriesByArea(mountainView);
        
        // Expected Output: Empty list
        assertTrue(deliveries.isEmpty());
    }
}