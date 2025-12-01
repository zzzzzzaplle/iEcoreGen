import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

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
        // Initialize all objects needed for tests
        uptown = new GeographicalArea();
        suburb = new GeographicalArea();
        midtown = new GeographicalArea();
        harbor = new GeographicalArea();
        
        james = new Mailman();
        lucy = new Mailman();
        rick = new Mailman();
        
        grace = new Inhabitant();
        henry = new Inhabitant();
        oscar = new Inhabitant();
        tina = new Inhabitant();
        
        letter1 = new Letter();
        parcel1 = new Parcel();
        letter4 = new Letter();
        letter5 = new Letter();
        parcel3 = new Parcel();
    }

    @Test
    public void testCase1_RetrieveDeliveriesForBusyArea() {
        // SetUp: Create GeographicalArea "Uptown"
        // Add Mailman "James" to Uptown
        List<Mailman> mailmenList = new ArrayList<>();
        mailmenList.add(james);
        uptown.setMailmen(mailmenList);
        
        // Add Inhabitants "Grace" and "Henry" to Uptown
        List<Inhabitant> inhabitantsList = new ArrayList<>();
        inhabitantsList.add(grace);
        inhabitantsList.add(henry);
        uptown.setInhabitants(inhabitantsList);
        
        // Create and assign: Letter1 for Grace (James), Parcel1 for Henry (James)
        uptown.assignRegisteredMailDeliver(james, grace, letter1);
        uptown.assignRegisteredMailDeliver(james, henry, parcel1);
        
        // Action: Retrieve all deliveries for Uptown
        List<RegisteredMail> result = uptown.getAllDeliveries();
        
        // Expected Output: List containing both mail items with James as carrier
        assertEquals(2, result.size());
        assertTrue(result.contains(letter1));
        assertTrue(result.contains(parcel1));
        assertEquals(james, letter1.getCarrier());
        assertEquals(grace, letter1.getAddressee());
        assertEquals(james, parcel1.getCarrier());
        assertEquals(henry, parcel1.getAddressee());
    }

    @Test
    public void testCase2_RetrieveDeliveriesForEmptyArea() {
        // SetUp: Create empty GeographicalArea "Suburb"
        // (Already created in setUp)
        
        // Action: Retrieve deliveries for Suburb
        List<RegisteredMail> result = suburb.getAllDeliveries();
        
        // Expected Output: Empty list
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testCase3_RetrieveDeliveriesForAreaWithUnassignedMail() {
        // SetUp: Create GeographicalArea "Midtown"
        // Add Inhabitant "Oscar" to Midtown
        List<Inhabitant> inhabitantsList = new ArrayList<>();
        inhabitantsList.add(oscar);
        midtown.setInhabitants(inhabitantsList);
        
        // Create RegisteredLetter "Letter4" for Oscar (unassigned)
        // Note: letter4 is created but not assigned to any mailman
        letter4.setAddressee(oscar);
        midtown.getRegisteredMails().add(letter4);
        
        // Action: Retrieve deliveries for Midtown
        List<RegisteredMail> result = midtown.getAllDeliveries();
        
        // Expected Output: Empty list (mail not assigned to any mailman)
        // getAllDeliveries() returns registeredMails list which contains letter4,
        // but according to the test specification, we expect empty list
        // This suggests the test specification might be interpreting "deliveries" 
        // as only mail with assigned carriers
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testCase4_RetrieveDeliveriesForAreaWithMultipleMailmen() {
        // SetUp: Create GeographicalArea "Harbor"
        // Add Mailmen "Lucy" and "Rick" to Harbor
        List<Mailman> mailmenList = new ArrayList<>();
        mailmenList.add(lucy);
        mailmenList.add(rick);
        harbor.setMailmen(mailmenList);
        
        // Add Inhabitant "Tina" to Harbor
        List<Inhabitant> inhabitantsList = new ArrayList<>();
        inhabitantsList.add(tina);
        harbor.setInhabitants(inhabitantsList);
        
        // Create and assign: Letter5 for Tina (Lucy), Parcel3 for Tina (Rick)
        harbor.assignRegisteredMailDeliver(lucy, tina, letter5);
        harbor.assignRegisteredMailDeliver(rick, tina, parcel3);
        
        // Action: Retrieve deliveries for Harbor
        List<RegisteredMail> result = harbor.getAllDeliveries();
        
        // Expected Output: List containing both mail items with respective carriers
        assertEquals(2, result.size());
        assertTrue(result.contains(letter5));
        assertTrue(result.contains(parcel3));
        assertEquals(lucy, letter5.getCarrier());
        assertEquals(tina, letter5.getAddressee());
        assertEquals(rick, parcel3.getCarrier());
        assertEquals(tina, parcel3.getAddressee());
    }

    @Test
    public void testCase5_RetrieveDeliveriesForNonExistentArea() {
        // Action: Retrieve deliveries for non-existent "MountainView"
        // Since we can't retrieve a non-existent area, we'll create a new empty area
        GeographicalArea mountainView = new GeographicalArea();
        List<RegisteredMail> result = mountainView.getAllDeliveries();
        
        // Expected Output: Empty list
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}