import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR2Test {
    
    private GeographicalArea uptown;
    private GeographicalArea suburb;
    private GeographicalArea midtown;
    private GeographicalArea harbor;
    private Mailman james;
    private Mailman lucy;
    private Mailman rick;
    private Inhabitant grace;
    private Inhabitant henry;
    private Inhabitant oscar;
    private Inhabitant tina;
    private Letter letter1;
    private Parcel parcel1;
    private Letter letter4;
    private Letter letter5;
    private Parcel parcel3;
    
    @Before
    public void setUp() {
        // Initialize test objects that can be reused across tests
        uptown = new GeographicalArea();
        uptown.setName("Uptown");
        
        suburb = new GeographicalArea();
        suburb.setName("Suburb");
        
        midtown = new GeographicalArea();
        midtown.setName("Midtown");
        
        harbor = new GeographicalArea();
        harbor.setName("Harbor");
        
        james = new Mailman();
        james.setId("M001");
        james.setName("James");
        
        lucy = new Mailman();
        lucy.setId("M002");
        lucy.setName("Lucy");
        
        rick = new Mailman();
        rick.setId("M003");
        rick.setName("Rick");
        
        grace = new Inhabitant();
        grace.setId("I001");
        grace.setName("Grace");
        
        henry = new Inhabitant();
        henry.setId("I002");
        henry.setName("Henry");
        
        oscar = new Inhabitant();
        oscar.setId("I003");
        oscar.setName("Oscar");
        
        tina = new Inhabitant();
        tina.setId("I004");
        tina.setName("Tina");
        
        letter1 = new Letter();
        letter1.setTrackingNumber("L001");
        letter1.setContent("Important document");
        
        parcel1 = new Parcel();
        parcel1.setTrackingNumber("P001");
        parcel1.setWeight(2.5);
        parcel1.setDescription("Electronics");
        
        letter4 = new Letter();
        letter4.setTrackingNumber("L004");
        letter4.setContent("Personal letter");
        
        letter5 = new Letter();
        letter5.setTrackingNumber("L005");
        letter5.setContent("Business correspondence");
        
        parcel3 = new Parcel();
        parcel3.setTrackingNumber("P003");
        parcel3.setWeight(5.0);
        parcel3.setDescription("Books");
    }
    
    @Test
    public void testCase1_RetrieveDeliveriesForBusyArea() {
        // SetUp: Create GeographicalArea "Uptown"
        // Add Mailman "James" to Uptown
        uptown.addMailman(james);
        // Add Inhabitants "Grace" and "Henry" to Uptown
        uptown.addInhabitant(grace);
        uptown.addInhabitant(henry);
        
        // Create and assign: Letter1 for Grace (James)
        letter1.setAddressee(grace);
        uptown.addRegisteredMail(letter1);
        uptown.assignMail(letter1, james);
        
        // Create and assign: Parcel1 for Henry (James)
        parcel1.setAddressee(henry);
        uptown.addRegisteredMail(parcel1);
        uptown.assignMail(parcel1, james);
        
        // Action: Retrieve all deliveries for Uptown
        List<DeliveryInfo> deliveries = uptown.getAllDeliveries();
        
        // Expected Output: List containing both mail items with James as carrier
        assertEquals("Should contain 2 deliveries", 2, deliveries.size());
        
        // Verify first delivery (Letter1 for Grace with James)
        DeliveryInfo delivery1 = deliveries.get(0);
        assertEquals("Mail should be letter1", letter1, delivery1.getMail());
        assertEquals("Mailman should be James", james, delivery1.getMailman());
        assertEquals("Addressee should be Grace", grace, delivery1.getAddressee());
        
        // Verify second delivery (Parcel1 for Henry with James)
        DeliveryInfo delivery2 = deliveries.get(1);
        assertEquals("Mail should be parcel1", parcel1, delivery2.getMail());
        assertEquals("Mailman should be James", james, delivery2.getMailman());
        assertEquals("Addressee should be Henry", henry, delivery2.getAddressee());
    }
    
    @Test
    public void testCase2_RetrieveDeliveriesForEmptyArea() {
        // SetUp: Create empty GeographicalArea "Suburb"
        // (Already created in setUp)
        
        // Action: Retrieve deliveries for Suburb
        List<DeliveryInfo> deliveries = suburb.getAllDeliveries();
        
        // Expected Output: Empty list
        assertTrue("Deliveries list should be empty", deliveries.isEmpty());
    }
    
    @Test
    public void testCase3_RetrieveDeliveriesForAreaWithUnassignedMail() {
        // SetUp: Create GeographicalArea "Midtown"
        // Add Inhabitant "Oscar" to Midtown
        midtown.addInhabitant(oscar);
        
        // Create RegisteredLetter "Letter4" for Oscar (unassigned)
        letter4.setAddressee(oscar);
        midtown.addRegisteredMail(letter4);
        // Note: letter4 is not assigned to any mailman
        
        // Action: Retrieve deliveries for Midtown
        List<DeliveryInfo> deliveries = midtown.getAllDeliveries();
        
        // Expected Output: Empty list (mail not assigned to any mailman)
        assertTrue("Deliveries list should be empty for unassigned mail", deliveries.isEmpty());
    }
    
    @Test
    public void testCase4_RetrieveDeliveriesForAreaWithMultipleMailmen() {
        // SetUp: Create GeographicalArea "Harbor"
        // Add Mailmen "Lucy" and "Rick" to Harbor
        harbor.addMailman(lucy);
        harbor.addMailman(rick);
        // Add Inhabitant "Tina" to Harbor
        harbor.addInhabitant(tina);
        
        // Create and assign: Letter5 for Tina (Lucy)
        letter5.setAddressee(tina);
        harbor.addRegisteredMail(letter5);
        harbor.assignMail(letter5, lucy);
        
        // Create and assign: Parcel3 for Tina (Rick)
        parcel3.setAddressee(tina);
        harbor.addRegisteredMail(parcel3);
        harbor.assignMail(parcel3, rick);
        
        // Action: Retrieve deliveries for Harbor
        List<DeliveryInfo> deliveries = harbor.getAllDeliveries();
        
        // Expected Output: List containing both mail items with respective carriers
        assertEquals("Should contain 2 deliveries", 2, deliveries.size());
        
        // Verify the deliveries contain both mail items with correct mailmen
        boolean foundLetterWithLucy = false;
        boolean foundParcelWithRick = false;
        
        for (DeliveryInfo delivery : deliveries) {
            if (delivery.getMail().equals(letter5) && delivery.getMailman().equals(lucy)) {
                foundLetterWithLucy = true;
                assertEquals("Addressee should be Tina", tina, delivery.getAddressee());
            }
            if (delivery.getMail().equals(parcel3) && delivery.getMailman().equals(rick)) {
                foundParcelWithRick = true;
                assertEquals("Addressee should be Tina", tina, delivery.getAddressee());
            }
        }
        
        assertTrue("Should find letter5 assigned to Lucy", foundLetterWithLucy);
        assertTrue("Should find parcel3 assigned to Rick", foundParcelWithRick);
    }
    
    @Test
    public void testCase5_RetrieveDeliveriesForNonExistentArea() {
        // Action: Retrieve deliveries for non-existent "MountainView"
        // Create a new area that wasn't set up with any content
        GeographicalArea mountainView = new GeographicalArea();
        mountainView.setName("MountainView");
        
        List<DeliveryInfo> deliveries = mountainView.getAllDeliveries();
        
        // Expected Output: Empty list
        assertTrue("Deliveries list should be empty for non-existent area", deliveries.isEmpty());
    }
}