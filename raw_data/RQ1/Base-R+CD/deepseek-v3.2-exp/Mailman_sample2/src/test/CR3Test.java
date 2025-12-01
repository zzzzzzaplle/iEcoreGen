import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    private GeographicalArea riverside;
    private GeographicalArea lakeside;
    private GeographicalArea downtown;
    private GeographicalArea hillside;
    private GeographicalArea beachfront;
    
    @Before
    public void setUp() {
        // Initialize geographical areas for each test
        riverside = new GeographicalArea();
        lakeside = new GeographicalArea();
        downtown = new GeographicalArea();
        hillside = new GeographicalArea();
        beachfront = new GeographicalArea();
    }
    
    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // Test Case 1: Add New Inhabitant to Area
        Inhabitant linda = new Inhabitant();
        linda.setName("Linda");
        
        // Action: Add new Inhabitant "Linda" to Riverside
        boolean result = riverside.addInhabitant(linda);
        
        // Expected Output: true (successful addition)
        assertTrue(result);
        assertTrue(riverside.getInhabitants().contains(linda));
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // Test Case 2: Remove Inhabitant with Assigned Mail
        // SetUp: Add Mailman "Ken" to Lakeside
        Mailman ken = new Mailman();
        ken.setName("Ken");
        lakeside.addMailman(ken);
        
        // SetUp: Add Inhabitant "Paul" to Lakeside
        Inhabitant paul = new Inhabitant();
        paul.setName("Paul");
        lakeside.addInhabitant(paul);
        
        // SetUp: Create and assign Letter6 for Paul (Ken)
        Letter letter6 = new Letter();
        letter6.setAddressee(paul);
        lakeside.getAllMails().add(letter6);
        lakeside.assignRegisteredMailDeliver(ken, paul, letter6);
        
        // Verify setup is correct
        assertNotNull(letter6.getCarrier());
        assertEquals(ken, letter6.getCarrier());
        
        // Action: Remove Paul from Lakeside
        boolean result = lakeside.removeInhabitant(paul);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue(result);
        assertFalse(lakeside.getInhabitants().contains(paul));
        assertFalse(lakeside.getAllMails().contains(letter6));
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // Test Case 3: Add Multiple Inhabitants to Area
        Inhabitant linda = new Inhabitant();
        linda.setName("Linda");
        
        Inhabitant becy = new Inhabitant();
        becy.setName("Becy");
        
        // Action & Expected Output: Add new Inhabitant "Linda" to Downtown, true
        boolean result1 = downtown.addInhabitant(linda);
        assertTrue(result1);
        assertTrue(downtown.getInhabitants().contains(linda));
        
        // Action & Expected Output: Add new Inhabitant "Becy" to Downtown, true
        boolean result2 = downtown.addInhabitant(becy);
        assertTrue(result2);
        assertTrue(downtown.getInhabitants().contains(becy));
        
        // Action & Expected Output: Remove Inhabitant "Linda" to Downtown, true
        boolean result3 = downtown.removeInhabitant(linda);
        assertTrue(result3);
        assertFalse(downtown.getInhabitants().contains(linda));
        assertTrue(downtown.getInhabitants().contains(becy)); // Becy should still be there
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // Test Case 4: Remove Non-existent Inhabitant
        Inhabitant victor = new Inhabitant();
        victor.setName("Victor");
        
        // Action: Remove non-existent "Victor" from Hillside
        boolean result = hillside.removeInhabitant(victor);
        
        // Expected Output: false (inhabitant doesn't exist)
        assertFalse(result);
        assertTrue(hillside.getInhabitants().isEmpty());
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // Test Case 5: Remove Inhabitant with Multiple Mail Items
        // SetUp: Add Mailman "Amy" to Beachfront
        Mailman amy = new Mailman();
        amy.setName("Amy");
        beachfront.addMailman(amy);
        
        // SetUp: Add Inhabitant "Rachel" to Beachfront
        Inhabitant rachel = new Inhabitant();
        rachel.setName("Rachel");
        beachfront.addInhabitant(rachel);
        
        // SetUp: Create and assign Letter7 for Rachel (Amy)
        Letter letter7 = new Letter();
        letter7.setAddressee(rachel);
        beachfront.getAllMails().add(letter7);
        beachfront.assignRegisteredMailDeliver(amy, rachel, letter7);
        
        // SetUp: Create and assign Parcel4 for Rachel (Amy)
        Parcel parcel4 = new Parcel();
        parcel4.setAddressee(rachel);
        beachfront.getAllMails().add(parcel4);
        beachfront.assignRegisteredMailDeliver(amy, rachel, parcel4);
        
        // Verify setup is correct
        assertNotNull(letter7.getCarrier());
        assertNotNull(parcel4.getCarrier());
        assertEquals(amy, letter7.getCarrier());
        assertEquals(amy, parcel4.getCarrier());
        assertEquals(2, beachfront.getAllMails().size());
        
        // Action: Remove Rachel from Beachfront
        boolean result = beachfront.removeInhabitant(rachel);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue(result);
        assertFalse(beachfront.getInhabitants().contains(rachel));
        assertTrue(beachfront.getAllMails().isEmpty()); // Both mail items should be deleted
    }
}