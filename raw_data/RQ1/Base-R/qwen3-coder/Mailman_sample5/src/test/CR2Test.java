import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR2Test {
    
    private Area area;
    private Mailman mailman1;
    private Mailman mailman2;
    private Inhabitant inhabitant1;
    private Inhabitant inhabitant2;
    
    @Before
    public void setUp() {
        // Common setup for test cases
        area = new Area();
        mailman1 = new Mailman();
        mailman2 = new Mailman();
        inhabitant1 = new Inhabitant();
        inhabitant2 = new Inhabitant();
    }
    
    @Test
    public void testCase1_RetrieveDeliveriesForBusyArea() {
        // SetUp: Create GeographicalArea "Uptown"
        area.setName("Uptown");
        
        // SetUp: Add Mailman "James" to Uptown
        mailman1.setName("James");
        area.addMailman(mailman1);
        
        // SetUp: Add Inhabitants "Grace" and "Henry" to Uptown
        inhabitant1.setName("Grace");
        inhabitant2.setName("Henry");
        area.addInhabitant(inhabitant1);
        area.addInhabitant(inhabitant2);
        
        // SetUp: Create and assign Letter1 for Grace (James)
        Letter letter1 = new Letter();
        letter1.setAddressee(inhabitant1);
        letter1.setMailman(mailman1);
        area.getMails().add(letter1);
        
        // SetUp: Create and assign Parcel1 for Henry (James)
        Parcel parcel1 = new Parcel();
        parcel1.setAddressee(inhabitant2);
        parcel1.setMailman(mailman1);
        area.getMails().add(parcel1);
        
        // Action: Retrieve all deliveries for Uptown
        List<MailDeliveryInfo> deliveries = area.getMailDeliveries();
        
        // Expected Output: List containing both mail items with James as carrier
        assertEquals(2, deliveries.size());
        
        // Verify first delivery (Letter1 for Grace by James)
        MailDeliveryInfo delivery1 = deliveries.get(0);
        assertEquals(letter1, delivery1.getMail());
        assertEquals(mailman1, delivery1.getMailman());
        assertEquals(inhabitant1, delivery1.getAddressee());
        
        // Verify second delivery (Parcel1 for Henry by James)
        MailDeliveryInfo delivery2 = deliveries.get(1);
        assertEquals(parcel1, delivery2.getMail());
        assertEquals(mailman1, delivery2.getMailman());
        assertEquals(inhabitant2, delivery2.getAddressee());
    }
    
    @Test
    public void testCase2_RetrieveDeliveriesForEmptyArea() {
        // SetUp: Create empty GeographicalArea "Suburb"
        area.setName("Suburb");
        
        // Action: Retrieve deliveries for Suburb
        List<MailDeliveryInfo> deliveries = area.getMailDeliveries();
        
        // Expected Output: Empty list
        assertTrue(deliveries.isEmpty());
    }
    
    @Test
    public void testCase3_RetrieveDeliveriesForAreaWithUnassignedMail() {
        // SetUp: Create GeographicalArea "Midtown"
        area.setName("Midtown");
        
        // SetUp: Add Inhabitant "Oscar" to Midtown
        inhabitant1.setName("Oscar");
        area.addInhabitant(inhabitant1);
        
        // SetUp: Create RegisteredLetter "Letter4" for Oscar (unassigned)
        Letter letter4 = new Letter();
        letter4.setAddressee(inhabitant1);
        // Mailman is not assigned (null)
        area.getMails().add(letter4);
        
        // Action: Retrieve deliveries for Midtown
        List<MailDeliveryInfo> deliveries = area.getMailDeliveries();
        
        // Expected Output: Empty list (mail not assigned to any mailman)
        assertTrue(deliveries.isEmpty());
    }
    
    @Test
    public void testCase4_RetrieveDeliveriesForAreaWithMultipleMailmen() {
        // SetUp: Create GeographicalArea "Harbor"
        area.setName("Harbor");
        
        // SetUp: Add Mailmen "Lucy" and "Rick" to Harbor
        mailman1.setName("Lucy");
        mailman2.setName("Rick");
        area.addMailman(mailman1);
        area.addMailman(mailman2);
        
        // SetUp: Add Inhabitant "Tina" to Harbor
        inhabitant1.setName("Tina");
        area.addInhabitant(inhabitant1);
        
        // SetUp: Create and assign Letter5 for Tina (Lucy)
        Letter letter5 = new Letter();
        letter5.setAddressee(inhabitant1);
        letter5.setMailman(mailman1);
        area.getMails().add(letter5);
        
        // SetUp: Create and assign Parcel3 for Tina (Rick)
        Parcel parcel3 = new Parcel();
        parcel3.setAddressee(inhabitant1);
        parcel3.setMailman(mailman2);
        area.getMails().add(parcel3);
        
        // Action: Retrieve deliveries for Harbor
        List<MailDeliveryInfo> deliveries = area.getMailDeliveries();
        
        // Expected Output: List containing both mail items with respective carriers
        assertEquals(2, deliveries.size());
        
        // Find deliveries by mailman
        MailDeliveryInfo lucyDelivery = null;
        MailDeliveryInfo rickDelivery = null;
        
        for (MailDeliveryInfo delivery : deliveries) {
            if (delivery.getMailman().equals(mailman1)) {
                lucyDelivery = delivery;
            } else if (delivery.getMailman().equals(mailman2)) {
                rickDelivery = delivery;
            }
        }
        
        // Verify Lucy's delivery
        assertNotNull(lucyDelivery);
        assertEquals(letter5, lucyDelivery.getMail());
        assertEquals(mailman1, lucyDelivery.getMailman());
        assertEquals(inhabitant1, lucyDelivery.getAddressee());
        
        // Verify Rick's delivery
        assertNotNull(rickDelivery);
        assertEquals(parcel3, rickDelivery.getMail());
        assertEquals(mailman2, rickDelivery.getMailman());
        assertEquals(inhabitant1, rickDelivery.getAddressee());
    }
    
    @Test
    public void testCase5_RetrieveDeliveriesForNonExistentArea() {
        // Action: Retrieve deliveries for non-existent "MountainView"
        // Since we're testing a specific area instance, we'll create a new area with the name
        Area mountainView = new Area();
        mountainView.setName("MountainView");
        
        List<MailDeliveryInfo> deliveries = mountainView.getMailDeliveries();
        
        // Expected Output: Empty list
        assertTrue(deliveries.isEmpty());
    }
}