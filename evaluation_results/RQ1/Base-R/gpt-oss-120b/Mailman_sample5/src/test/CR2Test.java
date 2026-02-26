import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR2Test {
    
    private GeographicalArea uptown;
    private GeographicalArea suburb;
    private GeographicalArea midtown;
    private GeographicalArea harbor;
    
    @Before
    public void setUp() {
        // Initialize geographical areas for tests
        uptown = new GeographicalArea("Uptown");
        suburb = new GeographicalArea("Suburb");
        midtown = new GeographicalArea("Midtown");
        harbor = new GeographicalArea("Harbor");
    }
    
    @Test
    public void testCase1_RetrieveDeliveriesForBusyArea() {
        // SetUp: Create GeographicalArea "Uptown"
        // Add Mailman "James" to Uptown
        Mailman james = new Mailman("James");
        uptown.addMailman(james);
        
        // Add Inhabitants "Grace" and "Henry" to Uptown
        Inhabitant grace = new Inhabitant("Grace");
        Inhabitant henry = new Inhabitant("Henry");
        uptown.addInhabitant(grace);
        uptown.addInhabitant(henry);
        
        // Create and assign Letter1 for Grace (James)
        Letter letter1 = new Letter("L001", grace, "Important document");
        uptown.assignMail(letter1, james);
        
        // Create and assign Parcel1 for Henry (James)
        Parcel parcel1 = new Parcel("P001", henry, 2.5);
        uptown.assignMail(parcel1, james);
        
        // Action: Retrieve all deliveries for Uptown
        List<DeliveryRecord> deliveries = uptown.retrieveDeliveries();
        
        // Expected Output: List containing both mail items with James as carrier
        assertEquals(2, deliveries.size());
        
        // Verify first delivery record
        DeliveryRecord record1 = deliveries.get(0);
        assertEquals(letter1, record1.getMail());
        assertEquals(james, record1.getMailman());
        assertEquals(grace, record1.getAddressee());
        
        // Verify second delivery record
        DeliveryRecord record2 = deliveries.get(1);
        assertEquals(parcel1, record2.getMail());
        assertEquals(james, record2.getMailman());
        assertEquals(henry, record2.getAddressee());
    }
    
    @Test
    public void testCase2_RetrieveDeliveriesForEmptyArea() {
        // SetUp: Create empty GeographicalArea "Suburb"
        // No inhabitants, mailmen, or registered mails added
        
        // Action: Retrieve deliveries for Suburb
        List<DeliveryRecord> deliveries = suburb.retrieveDeliveries();
        
        // Expected Output: Empty list
        assertTrue(deliveries.isEmpty());
    }
    
    @Test
    public void testCase3_RetrieveDeliveriesForAreaWithUnassignedMail() {
        // SetUp: Create GeographicalArea "Midtown"
        // Add Inhabitant "Oscar" to Midtown
        Inhabitant oscar = new Inhabitant("Oscar");
        midtown.addInhabitant(oscar);
        
        // Create RegisteredLetter "Letter4" for Oscar (unassigned)
        Letter letter4 = new Letter("L004", oscar, "Unassigned letter");
        // Note: Not assigned to any mailman, just created
        
        // Action: Retrieve deliveries for Midtown
        List<DeliveryRecord> deliveries = midtown.retrieveDeliveries();
        
        // Expected Output: Empty list (mail not assigned to any mailman)
        assertTrue(deliveries.isEmpty());
    }
    
    @Test
    public void testCase4_RetrieveDeliveriesForAreaWithMultipleMailmen() {
        // SetUp: Create GeographicalArea "Harbor"
        // Add Mailmen "Lucy" and "Rick" to Harbor
        Mailman lucy = new Mailman("Lucy");
        Mailman rick = new Mailman("Rick");
        harbor.addMailman(lucy);
        harbor.addMailman(rick);
        
        // Add Inhabitant "Tina" to Harbor
        Inhabitant tina = new Inhabitant("Tina");
        harbor.addInhabitant(tina);
        
        // Create and assign Letter5 for Tina (Lucy)
        Letter letter5 = new Letter("L005", tina, "Letter from Lucy");
        harbor.assignMail(letter5, lucy);
        
        // Create and assign Parcel3 for Tina (Rick)
        Parcel parcel3 = new Parcel("P003", tina, 5.0);
        harbor.assignMail(parcel3, rick);
        
        // Action: Retrieve deliveries for Harbor
        List<DeliveryRecord> deliveries = harbor.retrieveDeliveries();
        
        // Expected Output: List containing both mail items with respective carriers
        assertEquals(2, deliveries.size());
        
        // Verify deliveries contain both mail items with correct mailmen
        boolean foundLucyDelivery = false;
        boolean foundRickDelivery = false;
        
        for (DeliveryRecord record : deliveries) {
            if (record.getMailman().equals(lucy) && record.getMail().equals(letter5)) {
                foundLucyDelivery = true;
                assertEquals(tina, record.getAddressee());
            }
            if (record.getMailman().equals(rick) && record.getMail().equals(parcel3)) {
                foundRickDelivery = true;
                assertEquals(tina, record.getAddressee());
            }
        }
        
        assertTrue("Should contain delivery by Lucy", foundLucyDelivery);
        assertTrue("Should contain delivery by Rick", foundRickDelivery);
    }
    
    @Test
    public void testCase5_RetrieveDeliveriesForNonExistentArea() {
        // Action: Retrieve deliveries for non-existent "MountainView"
        GeographicalArea mountainView = new GeographicalArea("MountainView");
        List<DeliveryRecord> deliveries = mountainView.retrieveDeliveries();
        
        // Expected Output: Empty list
        assertTrue(deliveries.isEmpty());
    }
}