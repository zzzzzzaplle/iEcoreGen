import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

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
        // SetUp: Create GeographicalArea "Riverside"
        riverside.setAreaName("Riverside");
        
        // Action: Add new Inhabitant "Linda" to Riverside
        Inhabitant linda = new Inhabitant();
        linda.setName("Linda");
        linda.setId("LIN001");
        linda.setAddress("123 Riverside Dr");
        
        boolean result = riverside.addInhabitant(linda);
        
        // Expected Output: true (successful addition)
        assertTrue("Adding Linda to Riverside should return true", result);
        assertTrue("Riverside should contain Linda after addition", 
                   riverside.getInhabitants().contains(linda));
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // SetUp:
        // 1. Create GeographicalArea "Lakeside"
        lakeside.setAreaName("Lakeside");
        
        // 2. Add Mailman "Ken" to Lakeside
        Mailman ken = new Mailman();
        ken.setName("Ken");
        ken.setId("KEN001");
        lakeside.addMailman(ken);
        
        // 3. Add Inhabitant "Paul" to Lakeside
        Inhabitant paul = new Inhabitant();
        paul.setName("Paul");
        paul.setId("PAU001");
        paul.setAddress("456 Lakeview St");
        lakeside.addInhabitant(paul);
        
        // 4. Create and assign Letter6 for Paul (Ken)
        Letter letter6 = new Letter();
        letter6.setTrackingNumber("LETTER6");
        letter6.setWeight(0.5);
        letter6.setUrgent(false);
        
        lakeside.assignRegisteredMailDeliver(ken, paul, letter6);
        
        // Action: Remove Paul from Lakeside
        boolean result = lakeside.removeInhabitant(paul);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue("Removing Paul from Lakeside should return true", result);
        assertFalse("Lakeside should not contain Paul after removal", 
                    lakeside.getInhabitants().contains(paul));
        
        // Verify that Letter6 was deleted from allMails
        List<RegisteredMail> paulsMail = lakeside.listRegisteredMailWithInhabitant(paul);
        assertNull("No mail should exist for Paul after removal", paulsMail);
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // SetUp: Create GeographicalArea "Downtown"
        downtown.setAreaName("Downtown");
        
        // Action & Expected Output: 
        // Add new Inhabitant "Linda" to Downtown, true
        Inhabitant linda = new Inhabitant();
        linda.setName("Linda");
        linda.setId("LIN002");
        linda.setAddress("789 Downtown Ave");
        boolean result1 = downtown.addInhabitant(linda);
        assertTrue("Adding Linda to Downtown should return true", result1);
        
        // Add new Inhabitant "Becy" to Downtown, true
        Inhabitant becy = new Inhabitant();
        becy.setName("Becy");
        becy.setId("BEC001");
        becy.setAddress("101 Downtown Blvd");
        boolean result2 = downtown.addInhabitant(becy);
        assertTrue("Adding Becy to Downtown should return true", result2);
        
        // Remove Inhabitant "Linda" to Downtown, true
        boolean result3 = downtown.removeInhabitant(linda);
        assertTrue("Removing Linda from Downtown should return true", result3);
        
        // Verify final state
        assertFalse("Downtown should not contain Linda after removal", 
                    downtown.getInhabitants().contains(linda));
        assertTrue("Downtown should still contain Becy", 
                   downtown.getInhabitants().contains(becy));
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "Hillside"
        hillside.setAreaName("Hillside");
        
        // Action: Remove non-existent "Victor" from Hillside
        Inhabitant victor = new Inhabitant();
        victor.setName("Victor");
        victor.setId("VIC001");
        victor.setAddress("222 Hilltop Rd");
        
        boolean result = hillside.removeInhabitant(victor);
        
        // Expected Output: false (inhabitant doesn't exist)
        assertFalse("Removing non-existent Victor should return false", result);
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // SetUp:
        // 1. Create GeographicalArea "Beachfront"
        beachfront.setAreaName("Beachfront");
        
        // 2. Add Mailman "Amy" to Beachfront
        Mailman amy = new Mailman();
        amy.setName("Amy");
        amy.setId("AMY001");
        beachfront.addMailman(amy);
        
        // 3. Add Inhabitant "Rachel" to Beachfront
        Inhabitant rachel = new Inhabitant();
        rachel.setName("Rachel");
        rachel.setId("RAC001");
        rachel.setAddress("333 Beach Blvd");
        beachfront.addInhabitant(rachel);
        
        // 4. Create and assign:
        // - Letter7 for Rachel (Amy)
        Letter letter7 = new Letter();
        letter7.setTrackingNumber("LETTER7");
        letter7.setWeight(0.3);
        letter7.setUrgent(true);
        beachfront.assignRegisteredMailDeliver(amy, rachel, letter7);
        
        // - Parcel4 for Rachel (Amy)
        Parcel parcel4 = new Parcel();
        parcel4.setTrackingNumber("PARCEL4");
        parcel4.setWeight(2.5);
        parcel4.setDimensions(15.0);
        parcel4.setRequiresSignature(true);
        beachfront.assignRegisteredMailDeliver(amy, rachel, parcel4);
        
        // Verify mail was assigned before removal
        List<RegisteredMail> rachelsMailBefore = beachfront.listRegisteredMailWithInhabitant(rachel);
        assertNotNull("Rachel should have mail before removal", rachelsMailBefore);
        assertEquals("Rachel should have 2 mail items before removal", 2, rachelsMailBefore.size());
        
        // Action: Remove Rachel from Beachfront
        boolean result = beachfront.removeInhabitant(rachel);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue("Removing Rachel from Beachfront should return true", result);
        assertFalse("Beachfront should not contain Rachel after removal", 
                    beachfront.getInhabitants().contains(rachel));
        
        // Verify that both mail items were deleted
        List<RegisteredMail> rachelsMailAfter = beachfront.listRegisteredMailWithInhabitant(rachel);
        assertNull("No mail should exist for Rachel after removal", rachelsMailAfter);
    }
}